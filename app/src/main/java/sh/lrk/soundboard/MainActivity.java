package sh.lrk.soundboard;

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

// imports for menu
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import java.util.Locale;
import java.util.Set;

import sh.lrk.soundboard.settings.SettingsActivity;

import static sh.lrk.soundboard.settings.SettingsActivity.DEFAULT_NUM_COLS;
import static sh.lrk.soundboard.settings.SettingsActivity.KEY_NUM_COLS;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    // list of static variables

    // testing part 1 below
    public static final String armageddon_nonukes = "a nonukes";
    public static final String karatekid_bestaround = "k bestaround";

    // other variables
    public static final String IT_JUST_WORKS = "It Just Works";
    public static final String HIT = "Hit";
    public static final String IAM_MASTER = "Now I Am The Master";
    public static final String NO_NUKES = "Armageddon No Nukes";
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
        setContentView(R.layout.activity_main);

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

        addInititalSamples();
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
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if(itemId == R.id.findSounds) {
            new AlertDialog.Builder(this)
                    .setNeutralButton(R.string.okay, (d,w) -> d.dismiss())
                    .setTitle(R.string.diag_title_find_sounds)
                    .setMessage(R.string.diag_message_find_sounds)
                    .setIcon(R.drawable.ic_help_outline_white_24dp)
                    .create().show();
        }
        // return false;

        // switch based on menu item title
        switch (item.getTitle().toString()) {
            case "item1":
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case "A_D":
                //Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "A through D selected", Toast.LENGTH_SHORT).show();
                return true;
            case "E_G":
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "E through G selected", Toast.LENGTH_SHORT).show();
                return true;
            case "item3":
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case "A":
                //Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "A Selected", Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(getApplicationContext(),HsoundsActivity.class);
                String firstLetter2 = item.getTitle().toString().toLowerCase();
                char firstLetter = item.getTitle().toString().toLowerCase().charAt(0);

                //Toast.makeText(MainActivity.this, item.getTitle().toString().toLowerCase(), Toast.LENGTH_SHORT).show();
                //                TextView textViewName = item.findViewById(R.id.title);
                //                // pass the letter as a key
                intent2.putExtra("key2",Character.toString(firstLetter));

                //intent2.putExtra("firstLetterKey", firstLetter2);// if its string type
                //                Intent.putExtra("key2", var2);// if its int type
                //                startActivity(intent);

                //Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "H Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent2);//start activity
                return true;
            case "B":
                //Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "B Selected", Toast.LENGTH_SHORT).show();
                return true;
            case "H-O":
                Toast.makeText(MainActivity.this, "H Selected", Toast.LENGTH_SHORT).show();
                return true;
            case "H":
                Intent intent = new Intent(getApplicationContext(),HsoundsActivity.class);
                String firstLetter0 = item.getTitle().toString().toLowerCase();

                Toast.makeText(MainActivity.this, item.getTitle().toString().toLowerCase(), Toast.LENGTH_SHORT).show();
                startActivity(intent);//start activity
                return true;
            case "K":
                Intent intent3 = new Intent(getApplicationContext(),HsoundsActivity.class);
                //String firstLetter3 = item.getTitle().toString().toLowerCase();
                char firstLetter3 = item.getTitle().toString().toLowerCase().charAt(0);

                //Toast.makeText(MainActivity.this, item.getTitle().toString().toLowerCase(), Toast.LENGTH_SHORT).show();
                //                TextView textViewName = item.findViewById(R.id.title);
                //                // pass the letter as a key
                intent3.putExtra("key2",Character.toString(firstLetter3));

                //intent2.putExtra("firstLetterKey", firstLetter2);// if its string type
                //                Intent.putExtra("key2", var2);// if its int type
                //                startActivity(intent);

                //Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "H Selected", Toast.LENGTH_SHORT).show();
                startActivity(intent3);//start activity
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initAdapter() {
        adapter.clear();
        Set<String> sampleNames = soundboardData.keySet();
        for (String name : sampleNames) {
            adapter.add(new SoundboardSample(new File(soundboardData.get(name)), name));
        }
        adapter.sort((a, b) -> a.getName().compareTo(b.getName())); // sort by name
    }

    private void addInititalSamples() {

        // second block insertion point
        // second part of process_wavs.py
        String nonukesPath = soundboardData.get(armageddon_nonukes);
        if (nonukesPath == null || !new File(nonukesPath).exists()) {
            createnonukesSampleTempFile();
        }

        String bestaroundPath = soundboardData.get(karatekid_bestaround);
        if (bestaroundPath == null || !new File(bestaroundPath).exists()) {
            createbestaroundSampleTempFile();
        }

    }

    // part 3 of the process_wavs.py output
    // the functions for the .wav files

    private void createnonukesSampleTempFile() {
        try {
            File file = File.createTempFile("armageddon_nonukes", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.armageddon_nonukes);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(armageddon_nonukes, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createbestaroundSampleTempFile() {
        try {
            File file = File.createTempFile("karatekid_bestaround", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.karatekid_bestaround);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(karatekid_bestaround, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    // general function to add sample files
    private void createSampleTempFile(String fileToAdd) {
        try {
            File file = File.createTempFile(fileToAdd, "wav", getCacheDir());

            try (FileOutputStream out = new FileOutputStream(file)) {
                // InputStream in = getResources().openRawResource(R.raw.hans);
                InputStream in = getResources().openRawResource(MainActivity.this.getResources().getIdentifier(fileToAdd, "drawable", getPackageName()));
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(fileToAdd, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

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

    public void removeFromSamples(SoundboardSample sample) {
        soundboardData.remove(sample.getName());
        saveSoundboardData();
        initAdapter();
    }

    public void editSample(String sampleName, @NonNull String newName) {
        String samplePath = soundboardData.get(sampleName);

        if (samplePath == null) {
            Snackbar.make(getWindow().getDecorView(), R.string.sample_does_not_exist, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (newName.isEmpty()) {
            Snackbar.make(getWindow().getDecorView(), R.string.invalid_sample_name, Snackbar.LENGTH_LONG).show();
            return;
        }

        soundboardData.remove(sampleName);
        soundboardData.put(newName, samplePath);
        saveSoundboardData();
        initAdapter();
    }
}
