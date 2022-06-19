package sh.lrk.soundboard;

import static sh.lrk.soundboard.MainActivity.HIT;
import static sh.lrk.soundboard.MainActivity.IT_JUST_WORKS;
import static sh.lrk.soundboard.settings.SettingsActivity.DEFAULT_NUM_COLS;
import static sh.lrk.soundboard.settings.SettingsActivity.KEY_NUM_COLS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import sh.lrk.soundboard.settings.SettingsActivity;

public class HsoundsActivity extends AppCompatActivity {

    // static values
    private static final String TAG = HsoundsActivity.class.getCanonicalName();

    public static final String HANS = "hans";
    public static final String KEY_SOUNDBOARD_DATA = "soundboard_data";
    public static final String DEFAULT_SOUNDBOARD_DATA = "{}";
    public static final int REQUEST_CODE = 696;
    public static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private HashMap<String, String> soundboardData;
    private SharedPreferences preferences;
    private SampleAdapter adapter;
    private GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsounds);

        gridView = findViewById(R.id.sampleListView);
        FloatingActionButton fab = findViewById(R.id.addBtn);

        adapter = new SampleAdapter(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        soundboardData = new Gson()
                .fromJson(preferences.getString(KEY_SOUNDBOARD_DATA, DEFAULT_SOUNDBOARD_DATA),
                        new TypeToken<HashMap<String, String>>() {
                        }.getType());

        fab.setOnClickListener(v -> {
            Intent intent = new Intent()
                    .setType("audio/*")
                    .setAction(Intent.ACTION_OPEN_DOCUMENT);

            startActivityForResult(Intent.createChooser(intent,
                    getText(R.string.select_an_audio_file)), REQUEST_CODE);
        });

        addInitialSamples();
        initAdapter();
        gridView.setAdapter(adapter);

        applyNumColsPreference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
            }
        }

    }

    private void applyNumColsPreference() {
        String numColsPreference = preferences.getString(KEY_NUM_COLS, DEFAULT_NUM_COLS);
        if (numColsPreference == null) { // 👀
            numColsPreference = DEFAULT_NUM_COLS;
        }
        gridView.setNumColumns(Integer.parseInt(numColsPreference));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        applyNumColsPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        return true;
//        if (itemId == R.id.settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        } else if (itemId == R.id.findSounds) {
//            new AlertDialog.Builder(this)
//                    .setNeutralButton(R.string.okay, (d, w) -> d.dismiss())
//                    .setTitle(R.string.diag_title_find_sounds)
//                    .setMessage(R.string.diag_message_find_sounds)
//                    .setIcon(R.drawable.ic_help_outline_white_24dp)
//                    .create().show();
//        }
//        // return false;
//
//        switch (item.getItemId()) {
//            case R.id.item1:
//                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item2:
//                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.item3:
//                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.subitem1:
//                //Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
//                Toast.makeText(HsoundsActivity.this, "H Selected", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(HsoundsActivity.this, HsoundsActivity.class));//start activity
//                return true;
//            case R.id.subitem2:
//                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
    }

    private void initAdapter() {
        adapter.clear();
        Set<String> sampleNames = soundboardData.keySet();
        for (String name : sampleNames) {
            if (name.equals(HANS)) {
                adapter.add(new SoundboardSample(new File(soundboardData.get(name)), name));
            }
        }
        adapter.sort((a, b) -> a.getName().compareTo(b.getName())); // sort by name
    }

    private void addInitialSamples() {
        String hansPath = soundboardData.get(HANS);
        if (hansPath == null || !new File(hansPath).exists()) {
            // createHansSampleTempFile();
            createSampleTempFile(HANS);
        }

    }

    // general function to add sample files
    private void createSampleTempFile(String fileToAdd) {
        try {
            File file = File.createTempFile(fileToAdd, "wav", getCacheDir());

            try (FileOutputStream out = new FileOutputStream(file)) {
                // InputStream in = getResources().openRawResource(R.raw.hans);
                InputStream in = getResources().openRawResource(HsoundsActivity.this.getResources().getIdentifier(fileToAdd, "drawable", getPackageName()));
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(HANS, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    // below function works, trying to generalize above

    private void createHansSampleTempFile() {
        try {
            File file = File.createTempFile("hans", "wav", getCacheDir());

            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.hans);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(HANS, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    // copy other functions over
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedFile = data.getData(); //The uri with the location of the file
            if (selectedFile != null) {
                File file = new File(FilePathResolver.getPath(this, selectedFile));
                AlertDialog dia = new AlertDialog.Builder(this)
                        .setView(R.layout.add_prompt)
                        .setNegativeButton(R.string.cancel, (dialog, which) -> Log.d(TAG, "Adding canceled."))
                        .create();

                dia.setButton(AlertDialog.BUTTON_POSITIVE, getText(R.string.okay), (d, w) -> {
                    EditText sampleName = dia.findViewById(R.id.sampleName);
                    String name = sampleName.getText().toString();
                    if (name.isEmpty()) {
                        name = getString(R.string.unnamed_sample);
                    }
                    soundboardData.put(name, file.getAbsolutePath());
                    saveSoundboardData();
                    initAdapter();
                });

                dia.show();
            } else {
                Snackbar.make(getWindow().getDecorView(),
                        getText(R.string.no_selection_made),
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void saveSoundboardData() {
        preferences.edit().putString(KEY_SOUNDBOARD_DATA, new Gson().toJson(soundboardData)).apply();
    }
}
