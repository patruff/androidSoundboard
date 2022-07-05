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
    public static final String aliens_gameover = "a gameover";
    public static final String aliens_getaway = "a getaway";
    public static final String armageddon_nonukes = "a nonukes";
    public static final String armageddon_whitehouse = "a whitehouse";
    public static final String backtofuture2_kidsmarty = "b kidsmarty";
    public static final String backtofuture_thinkmcfly = "b thinkmcfly";
    public static final String bigtrouble_paiddues = "b paiddues";
    public static final String bigtrouble_reflexes = "b reflexes";
    public static final String boondock_flipside = "b flipside";
    public static final String braveheart_urfooked = "b urfooked";
    public static final String caddyshack_cinderella = "c cinderella";
    public static final String caddyshack_hatsoup = "c hatsoup";
    public static final String commando_steam = "c steam";
    public static final String conan_kromlaughs = "c kromlaughs";
    public static final String conan_lament = "c lament";
    public static final String diehard2_notyet = "d notyet";
    public static final String diehard2_tincan = "d tincan";
    public static final String diehard3_hardly = "d hardly";
    public static final String diehard3_heyzues = "d heyzues";
    public static final String diehard_hohoho = "d hohoho";
    public static final String diehard_oops = "d oops";
    public static final String karatekid_bestaround = "k bestaround";
    public static final String kindergartencop_shutup = "k shutup";
    public static final String kindergartencop_stopit = "k stopit";
    public static final String kindergartencop_tuma = "k tuma";
    public static final String labrynth_tobyslave = "l tobyslave";

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
                return true;
            case "E_G":
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "E through G selected", Toast.LENGTH_SHORT).show();
                return true;
            case "item3":
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;

            // A-Z options, I generated this with Python in a separate file
            case "A":
                Intent intentA = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterA = item.getTitle().toString().toLowerCase().charAt(0);
                intentA.putExtra("key2",Character.toString(firstLetterA));

                startActivity(intentA);
                return true;
            case "B":
                Intent intentB = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterB = item.getTitle().toString().toLowerCase().charAt(0);
                intentB.putExtra("key2",Character.toString(firstLetterB));

                startActivity(intentB);
                return true;
            case "C":
                Intent intentC = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterC = item.getTitle().toString().toLowerCase().charAt(0);
                intentC.putExtra("key2",Character.toString(firstLetterC));

                startActivity(intentC);
                return true;
            case "D":
                Intent intentD = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterD = item.getTitle().toString().toLowerCase().charAt(0);
                intentD.putExtra("key2",Character.toString(firstLetterD));

                startActivity(intentD);
                return true;
            case "E":
                Intent intentE = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterE = item.getTitle().toString().toLowerCase().charAt(0);
                intentE.putExtra("key2",Character.toString(firstLetterE));

                startActivity(intentE);
                return true;
            case "F":
                Intent intentF = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterF = item.getTitle().toString().toLowerCase().charAt(0);
                intentF.putExtra("key2",Character.toString(firstLetterF));

                startActivity(intentF);
                return true;
            case "G":
                Intent intentG = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterG = item.getTitle().toString().toLowerCase().charAt(0);
                intentG.putExtra("key2",Character.toString(firstLetterG));

                startActivity(intentG);
                return true;
            case "H":
                Intent intentH = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterH = item.getTitle().toString().toLowerCase().charAt(0);
                intentH.putExtra("key2",Character.toString(firstLetterH));

                startActivity(intentH);
                return true;
            case "I":
                Intent intentI = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterI = item.getTitle().toString().toLowerCase().charAt(0);
                intentI.putExtra("key2",Character.toString(firstLetterI));

                startActivity(intentI);
                return true;
            case "J":
                Intent intentJ = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterJ = item.getTitle().toString().toLowerCase().charAt(0);
                intentJ.putExtra("key2",Character.toString(firstLetterJ));

                startActivity(intentJ);
                return true;
            case "K":
                Intent intentK = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterK = item.getTitle().toString().toLowerCase().charAt(0);
                intentK.putExtra("key2",Character.toString(firstLetterK));

                startActivity(intentK);
                return true;
            case "L":
                Intent intentL = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterL = item.getTitle().toString().toLowerCase().charAt(0);
                intentL.putExtra("key2",Character.toString(firstLetterL));

                startActivity(intentL);
                return true;
            case "M":
                Intent intentM = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterM = item.getTitle().toString().toLowerCase().charAt(0);
                intentM.putExtra("key2",Character.toString(firstLetterM));

                startActivity(intentM);
                return true;
            case "N":
                Intent intentN = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterN = item.getTitle().toString().toLowerCase().charAt(0);
                intentN.putExtra("key2",Character.toString(firstLetterN));

                startActivity(intentN);
                return true;
            case "O":
                Intent intentO = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterO = item.getTitle().toString().toLowerCase().charAt(0);
                intentO.putExtra("key2",Character.toString(firstLetterO));

                startActivity(intentO);
                return true;
            case "P":
                Intent intentP = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterP = item.getTitle().toString().toLowerCase().charAt(0);
                intentP.putExtra("key2",Character.toString(firstLetterP));

                startActivity(intentP);
                return true;
            case "Q":
                Intent intentQ = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterQ = item.getTitle().toString().toLowerCase().charAt(0);
                intentQ.putExtra("key2",Character.toString(firstLetterQ));

                startActivity(intentQ);
                return true;
            case "R":
                Intent intentR = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterR = item.getTitle().toString().toLowerCase().charAt(0);
                intentR.putExtra("key2",Character.toString(firstLetterR));

                startActivity(intentR);
                return true;
            case "S":
                Intent intentS = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterS = item.getTitle().toString().toLowerCase().charAt(0);
                intentS.putExtra("key2",Character.toString(firstLetterS));

                startActivity(intentS);
                return true;
            case "T":
                Intent intentT = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterT = item.getTitle().toString().toLowerCase().charAt(0);
                intentT.putExtra("key2",Character.toString(firstLetterT));

                startActivity(intentT);
                return true;
            case "U":
                Intent intentU = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterU = item.getTitle().toString().toLowerCase().charAt(0);
                intentU.putExtra("key2",Character.toString(firstLetterU));

                startActivity(intentU);
                return true;
            case "V":
                Intent intentV = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterV = item.getTitle().toString().toLowerCase().charAt(0);
                intentV.putExtra("key2",Character.toString(firstLetterV));

                startActivity(intentV);
                return true;
            case "W":
                Intent intentW = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterW = item.getTitle().toString().toLowerCase().charAt(0);
                intentW.putExtra("key2",Character.toString(firstLetterW));

                startActivity(intentW);
                return true;
            case "X":
                Intent intentX = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterX = item.getTitle().toString().toLowerCase().charAt(0);
                intentX.putExtra("key2",Character.toString(firstLetterX));

                startActivity(intentX);
                return true;
            case "Y":
                Intent intentY = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterY = item.getTitle().toString().toLowerCase().charAt(0);
                intentY.putExtra("key2",Character.toString(firstLetterY));

                startActivity(intentY);
                return true;
            case "Z":
                Intent intentZ = new Intent(getApplicationContext(),HsoundsActivity.class);
                char firstLetterZ = item.getTitle().toString().toLowerCase().charAt(0);
                intentZ.putExtra("key2",Character.toString(firstLetterZ));

                startActivity(intentZ);
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
        String gameoverPath = soundboardData.get(aliens_gameover);
        if (gameoverPath == null || !new File(gameoverPath).exists()) {
            creategameoverSampleTempFile();
        }

        String getawayPath = soundboardData.get(aliens_getaway);
        if (getawayPath == null || !new File(getawayPath).exists()) {
            creategetawaySampleTempFile();
        }

        String nonukesPath = soundboardData.get(armageddon_nonukes);
        if (nonukesPath == null || !new File(nonukesPath).exists()) {
            createnonukesSampleTempFile();
        }

        String whitehousePath = soundboardData.get(armageddon_whitehouse);
        if (whitehousePath == null || !new File(whitehousePath).exists()) {
            createwhitehouseSampleTempFile();
        }

        String kidsmartyPath = soundboardData.get(backtofuture2_kidsmarty);
        if (kidsmartyPath == null || !new File(kidsmartyPath).exists()) {
            createkidsmartySampleTempFile();
        }

        String thinkmcflyPath = soundboardData.get(backtofuture_thinkmcfly);
        if (thinkmcflyPath == null || !new File(thinkmcflyPath).exists()) {
            createthinkmcflySampleTempFile();
        }

        String paidduesPath = soundboardData.get(bigtrouble_paiddues);
        if (paidduesPath == null || !new File(paidduesPath).exists()) {
            createpaidduesSampleTempFile();
        }

        String reflexesPath = soundboardData.get(bigtrouble_reflexes);
        if (reflexesPath == null || !new File(reflexesPath).exists()) {
            createreflexesSampleTempFile();
        }

        String flipsidePath = soundboardData.get(boondock_flipside);
        if (flipsidePath == null || !new File(flipsidePath).exists()) {
            createflipsideSampleTempFile();
        }

        String urfookedPath = soundboardData.get(braveheart_urfooked);
        if (urfookedPath == null || !new File(urfookedPath).exists()) {
            createurfookedSampleTempFile();
        }

        String cinderellaPath = soundboardData.get(caddyshack_cinderella);
        if (cinderellaPath == null || !new File(cinderellaPath).exists()) {
            createcinderellaSampleTempFile();
        }

        String hatsoupPath = soundboardData.get(caddyshack_hatsoup);
        if (hatsoupPath == null || !new File(hatsoupPath).exists()) {
            createhatsoupSampleTempFile();
        }

        String steamPath = soundboardData.get(commando_steam);
        if (steamPath == null || !new File(steamPath).exists()) {
            createsteamSampleTempFile();
        }

        String kromlaughsPath = soundboardData.get(conan_kromlaughs);
        if (kromlaughsPath == null || !new File(kromlaughsPath).exists()) {
            createkromlaughsSampleTempFile();
        }

        String lamentPath = soundboardData.get(conan_lament);
        if (lamentPath == null || !new File(lamentPath).exists()) {
            createlamentSampleTempFile();
        }

        String notyetPath = soundboardData.get(diehard2_notyet);
        if (notyetPath == null || !new File(notyetPath).exists()) {
            createnotyetSampleTempFile();
        }

        String tincanPath = soundboardData.get(diehard2_tincan);
        if (tincanPath == null || !new File(tincanPath).exists()) {
            createtincanSampleTempFile();
        }

        String hardlyPath = soundboardData.get(diehard3_hardly);
        if (hardlyPath == null || !new File(hardlyPath).exists()) {
            createhardlySampleTempFile();
        }

        String heyzuesPath = soundboardData.get(diehard3_heyzues);
        if (heyzuesPath == null || !new File(heyzuesPath).exists()) {
            createheyzuesSampleTempFile();
        }

        String hohohoPath = soundboardData.get(diehard_hohoho);
        if (hohohoPath == null || !new File(hohohoPath).exists()) {
            createhohohoSampleTempFile();
        }

        String oopsPath = soundboardData.get(diehard_oops);
        if (oopsPath == null || !new File(oopsPath).exists()) {
            createoopsSampleTempFile();
        }

        String bestaroundPath = soundboardData.get(karatekid_bestaround);
        if (bestaroundPath == null || !new File(bestaroundPath).exists()) {
            createbestaroundSampleTempFile();
        }

        String shutupPath = soundboardData.get(kindergartencop_shutup);
        if (shutupPath == null || !new File(shutupPath).exists()) {
            createshutupSampleTempFile();
        }

        String stopitPath = soundboardData.get(kindergartencop_stopit);
        if (stopitPath == null || !new File(stopitPath).exists()) {
            createstopitSampleTempFile();
        }

        String tumaPath = soundboardData.get(kindergartencop_tuma);
        if (tumaPath == null || !new File(tumaPath).exists()) {
            createtumaSampleTempFile();
        }

        String tobyslavePath = soundboardData.get(labrynth_tobyslave);
        if (tobyslavePath == null || !new File(tobyslavePath).exists()) {
            createtobyslaveSampleTempFile();
        }

    }

    // part 3 of the process_wavs.py output
    // the functions for the .wav files

    private void creategameoverSampleTempFile() {
        try {
            File file = File.createTempFile("aliens_gameover", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.aliens_gameover);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(aliens_gameover, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void creategetawaySampleTempFile() {
        try {
            File file = File.createTempFile("aliens_getaway", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.aliens_getaway);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(aliens_getaway, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

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

    private void createwhitehouseSampleTempFile() {
        try {
            File file = File.createTempFile("armageddon_whitehouse", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.armageddon_whitehouse);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(armageddon_whitehouse, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createkidsmartySampleTempFile() {
        try {
            File file = File.createTempFile("backtofuture2_kidsmarty", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.backtofuture2_kidsmarty);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(backtofuture2_kidsmarty, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createthinkmcflySampleTempFile() {
        try {
            File file = File.createTempFile("backtofuture_thinkmcfly", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.backtofuture_thinkmcfly);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(backtofuture_thinkmcfly, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createpaidduesSampleTempFile() {
        try {
            File file = File.createTempFile("bigtrouble_paiddues", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.bigtrouble_paiddues);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(bigtrouble_paiddues, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createreflexesSampleTempFile() {
        try {
            File file = File.createTempFile("bigtrouble_reflexes", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.bigtrouble_reflexes);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(bigtrouble_reflexes, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createflipsideSampleTempFile() {
        try {
            File file = File.createTempFile("boondock_flipside", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.boondock_flipside);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(boondock_flipside, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createurfookedSampleTempFile() {
        try {
            File file = File.createTempFile("braveheart_urfooked", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.braveheart_urfooked);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(braveheart_urfooked, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createcinderellaSampleTempFile() {
        try {
            File file = File.createTempFile("caddyshack_cinderella", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.caddyshack_cinderella);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(caddyshack_cinderella, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createhatsoupSampleTempFile() {
        try {
            File file = File.createTempFile("caddyshack_hatsoup", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.caddyshack_hatsoup);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(caddyshack_hatsoup, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createsteamSampleTempFile() {
        try {
            File file = File.createTempFile("commando_steam", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.commando_steam);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(commando_steam, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createkromlaughsSampleTempFile() {
        try {
            File file = File.createTempFile("conan_kromlaughs", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.conan_kromlaughs);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(conan_kromlaughs, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createlamentSampleTempFile() {
        try {
            File file = File.createTempFile("conan_lament", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.conan_lament);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(conan_lament, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createnotyetSampleTempFile() {
        try {
            File file = File.createTempFile("diehard2_notyet", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard2_notyet);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard2_notyet, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createtincanSampleTempFile() {
        try {
            File file = File.createTempFile("diehard2_tincan", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard2_tincan);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard2_tincan, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createhardlySampleTempFile() {
        try {
            File file = File.createTempFile("diehard3_hardly", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard3_hardly);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard3_hardly, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createheyzuesSampleTempFile() {
        try {
            File file = File.createTempFile("diehard3_heyzues", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard3_heyzues);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard3_heyzues, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createhohohoSampleTempFile() {
        try {
            File file = File.createTempFile("diehard_hohoho", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard_hohoho);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard_hohoho, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createoopsSampleTempFile() {
        try {
            File file = File.createTempFile("diehard_oops", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard_oops);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard_oops, file.getPath());
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

    private void createshutupSampleTempFile() {
        try {
            File file = File.createTempFile("kindergartencop_shutup", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.kindergartencop_shutup);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(kindergartencop_shutup, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createstopitSampleTempFile() {
        try {
            File file = File.createTempFile("kindergartencop_stopit", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.kindergartencop_stopit);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(kindergartencop_stopit, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createtumaSampleTempFile() {
        try {
            File file = File.createTempFile("kindergartencop_tuma", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.kindergartencop_tuma);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(kindergartencop_tuma, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }

    private void createtobyslaveSampleTempFile() {
        try {
            File file = File.createTempFile("labrynth_tobyslave", "wav", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.labrynth_tobyslave);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(labrynth_tobyslave, file.getPath());
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
