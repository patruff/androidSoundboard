package sh.lrk.soundboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
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

    public static final String americanpsycho_yalethingmp3 = "a mp3yalething";

    public static final String aliens_gameover = "a gameover";
    public static final String aliens_getaway = "a getaway";
    public static final String americanpsycho_yalething = "a yalething";

    public static final String armageddon_nonukes = "a nonukes";
    public static final String armageddon_whitehouse = "a whitehouse";
    public static final String backtofuture2_kidsmarty = "b kidsmarty";
    public static final String backtofuture_thinkmcfly = "b thinkmcfly";
    public static final String batman_thinkfuture = "b thinkfuture";
    public static final String bigtrouble_paiddues = "b paiddues";
    public static final String bigtrouble_reflexes = "b reflexes";
    public static final String bladerunner_itsatest = "b itsatest";
    public static final String bladerunner_seenthings = "b seenthings";
    public static final String boondock_flipside = "b flipside";
    public static final String braveheart_deadcostnothing = "b deadcostnothing";
    public static final String braveheart_urfooked = "b urfooked";
    public static final String caddyshack_cinderella = "c cinderella";
    public static final String caddyshack_hatsoup = "c hatsoup";
    public static final String commando_steam = "c steam";
    public static final String conan_kromlaughs = "c kromlaughs";
    public static final String conan_lament = "c lament";
    public static final String dbz_dbzintro = "d dbzintro";
    public static final String diehard2_notyet = "d notyet";
    public static final String diehard2_tincan = "d tincan";
    public static final String diehard3_hardly = "d hardly";
    public static final String diehard3_heyzues = "d heyzues";
    public static final String diehard_hans = "d hans";
    public static final String diehard_hohoho = "d hohoho";
    public static final String diehard_oops = "d oops";
    public static final String donniedarko_suckafuck = "d suckafuck";
    public static final String fifthelement_whocares = "f whocares";
    public static final String fzero_falconpunch = "f falconpunch";
    public static final String ghostbusters_nodick = "g nodick";
    public static final String glengarry_helpus = "g helpus";
    public static final String glengarry_leads = "g leads";
    public static final String happygilmore_pricewrong = "h pricewrong";
    public static final String jackson_whosbad = "j whosbad";
    public static final String karatekid_bestaround = "k bestaround";
    public static final String kindergartencop_shutup = "k shutup";
    public static final String kindergartencop_stopit = "k stopit";
    public static final String kindergartencop_tuma = "k tuma";
    public static final String labrynth_tobyslave = "l tobyslave";
    public static final String lastofthemohicans_takeme = "l takeme";
    public static final String legacyofkain_laugh = "l laugh";
    public static final String legacyofkain_vaevictus = "l vaevictus";
    public static final String livecrew_getitgirl = "l getitgirl";
    public static final String metallica_sowhat = "m sowhat";
    public static final String microsoft_giveitup = "m giveitup";
    public static final String moderntalking_winifyouwant = "m winifyouwant";
    public static final String network_getmad = "n getmad";
    public static final String network_lifevalue = "n lifevalue";
    public static final String neverendingstory_bastionyeah = "n bastionyeah";
    public static final String parappa_gottabelieve = "p gottabelieve";
    public static final String runningman_plainzero = "r plainzero";
    public static final String runningman_roomfist = "r roomfist";
    public static final String scentofwoman_outoforder = "s outoforder";
    public static final String snatch_lostgeorge = "s lostgeorge";
    public static final String spiderman_crapmegacrap = "s crapmegacrap";
    public static final String starfox_starfoxdog = "s starfoxdog";
    public static final String starfox_starfoxdonkey = "s starfoxdonkey";
    public static final String starfox_starfoxfalco = "s starfoxfalco";
    public static final String starfox_starfoxrabbit = "s starfoxrabbit";
    public static final String starfox_starfoxslippy = "s starfoxslippy";
    public static final String startrek_makeitso = "s makeitso";
    public static final String starwars_lackfaith = "s lackfaith";
    public static final String streetfighter2_round1 = "s round1";
    public static final String streetfigther2_knockout = "s knockout";
    public static final String terminator2_boots = "t boots";
    public static final String terminator_fooker = "t fooker";
    public static final String transformers_idiotstarscream = "t idiotstarscream";
    public static final String transformers_megatronstopped = "t megatronstopped";
    public static final String transformers_ripoutoptics = "t ripoutoptics";
    public static final String walken_cowbellpants = "w cowbellpants";
    public static final String whycant_whycantwebe = "w whycantwebe";
    public static final String willywonka_youlose = "w youlose";
    public static final String wwf_ricflair = "w ricflair";
    public static final String xmen_nochoice = "x nochoice";
    public static final String zelda_linksounds = "z linksounds";

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
        if (numColsPreference == null) { // ????
            numColsPreference = DEFAULT_NUM_COLS;
        }
        gridView.setNumColumns(Integer.parseInt(numColsPreference));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        clearApplicationData();
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("EEEEEERRRRRROOOOOOORRRR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int i = 0;
            while (i < children.length) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
                i++;
            }
        }

        assert dir != null;
        return dir.delete();
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
        // second part, part 2, of process_wavs.py


        String gameoverPath = soundboardData.get(aliens_gameover);
        if (gameoverPath == null || !new File(gameoverPath).exists()) {
            creategameoverSampleTempFile();
        }
        String getawayPath = soundboardData.get(aliens_getaway);
        if (getawayPath == null || !new File(getawayPath).exists()) {
            creategetawaySampleTempFile();
        }
        String yalethingPath = soundboardData.get(americanpsycho_yalething);
        if (yalethingPath == null || !new File(yalethingPath).exists()) {
            createyalethingSampleTempFile();
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
        String thinkfuturePath = soundboardData.get(batman_thinkfuture);
        if (thinkfuturePath == null || !new File(thinkfuturePath).exists()) {
            createthinkfutureSampleTempFile();
        }
        String paidduesPath = soundboardData.get(bigtrouble_paiddues);
        if (paidduesPath == null || !new File(paidduesPath).exists()) {
            createpaidduesSampleTempFile();
        }
        String reflexesPath = soundboardData.get(bigtrouble_reflexes);
        if (reflexesPath == null || !new File(reflexesPath).exists()) {
            createreflexesSampleTempFile();
        }
        String itsatestPath = soundboardData.get(bladerunner_itsatest);
        if (itsatestPath == null || !new File(itsatestPath).exists()) {
            createitsatestSampleTempFile();
        }
        String seenthingsPath = soundboardData.get(bladerunner_seenthings);
        if (seenthingsPath == null || !new File(seenthingsPath).exists()) {
            createseenthingsSampleTempFile();
        }
        String flipsidePath = soundboardData.get(boondock_flipside);
        if (flipsidePath == null || !new File(flipsidePath).exists()) {
            createflipsideSampleTempFile();
        }
        String deadcostnothingPath = soundboardData.get(braveheart_deadcostnothing);
        if (deadcostnothingPath == null || !new File(deadcostnothingPath).exists()) {
            createdeadcostnothingSampleTempFile();
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
        String dbzintroPath = soundboardData.get(dbz_dbzintro);
        if (dbzintroPath == null || !new File(dbzintroPath).exists()) {
            createdbzintroSampleTempFile();
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
        String hansPath = soundboardData.get(diehard_hans);
        if (hansPath == null || !new File(hansPath).exists()) {
            createhansSampleTempFile();
        }
        String hohohoPath = soundboardData.get(diehard_hohoho);
        if (hohohoPath == null || !new File(hohohoPath).exists()) {
            createhohohoSampleTempFile();
        }
        String oopsPath = soundboardData.get(diehard_oops);
        if (oopsPath == null || !new File(oopsPath).exists()) {
            createoopsSampleTempFile();
        }
        String suckafuckPath = soundboardData.get(donniedarko_suckafuck);
        if (suckafuckPath == null || !new File(suckafuckPath).exists()) {
            createsuckafuckSampleTempFile();
        }
        String whocaresPath = soundboardData.get(fifthelement_whocares);
        if (whocaresPath == null || !new File(whocaresPath).exists()) {
            createwhocaresSampleTempFile();
        }
        String falconpunchPath = soundboardData.get(fzero_falconpunch);
        if (falconpunchPath == null || !new File(falconpunchPath).exists()) {
            createfalconpunchSampleTempFile();
        }
        String nodickPath = soundboardData.get(ghostbusters_nodick);
        if (nodickPath == null || !new File(nodickPath).exists()) {
            createnodickSampleTempFile();
        }
        String helpusPath = soundboardData.get(glengarry_helpus);
        if (helpusPath == null || !new File(helpusPath).exists()) {
            createhelpusSampleTempFile();
        }
        String leadsPath = soundboardData.get(glengarry_leads);
        if (leadsPath == null || !new File(leadsPath).exists()) {
            createleadsSampleTempFile();
        }
        String pricewrongPath = soundboardData.get(happygilmore_pricewrong);
        if (pricewrongPath == null || !new File(pricewrongPath).exists()) {
            createpricewrongSampleTempFile();
        }
        String whosbadPath = soundboardData.get(jackson_whosbad);
        if (whosbadPath == null || !new File(whosbadPath).exists()) {
            createwhosbadSampleTempFile();
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
        String takemePath = soundboardData.get(lastofthemohicans_takeme);
        if (takemePath == null || !new File(takemePath).exists()) {
            createtakemeSampleTempFile();
        }
        String laughPath = soundboardData.get(legacyofkain_laugh);
        if (laughPath == null || !new File(laughPath).exists()) {
            createlaughSampleTempFile();
        }
        String vaevictusPath = soundboardData.get(legacyofkain_vaevictus);
        if (vaevictusPath == null || !new File(vaevictusPath).exists()) {
            createvaevictusSampleTempFile();
        }
        String getitgirlPath = soundboardData.get(livecrew_getitgirl);
        if (getitgirlPath == null || !new File(getitgirlPath).exists()) {
            creategetitgirlSampleTempFile();
        }
        String sowhatPath = soundboardData.get(metallica_sowhat);
        if (sowhatPath == null || !new File(sowhatPath).exists()) {
            createsowhatSampleTempFile();
        }
        String giveitupPath = soundboardData.get(microsoft_giveitup);
        if (giveitupPath == null || !new File(giveitupPath).exists()) {
            creategiveitupSampleTempFile();
        }
        String winifyouwantPath = soundboardData.get(moderntalking_winifyouwant);
        if (winifyouwantPath == null || !new File(winifyouwantPath).exists()) {
            createwinifyouwantSampleTempFile();
        }
        String getmadPath = soundboardData.get(network_getmad);
        if (getmadPath == null || !new File(getmadPath).exists()) {
            creategetmadSampleTempFile();
        }
        String lifevaluePath = soundboardData.get(network_lifevalue);
        if (lifevaluePath == null || !new File(lifevaluePath).exists()) {
            createlifevalueSampleTempFile();
        }
        String bastionyeahPath = soundboardData.get(neverendingstory_bastionyeah);
        if (bastionyeahPath == null || !new File(bastionyeahPath).exists()) {
            createbastionyeahSampleTempFile();
        }
        String gottabelievePath = soundboardData.get(parappa_gottabelieve);
        if (gottabelievePath == null || !new File(gottabelievePath).exists()) {
            creategottabelieveSampleTempFile();
        }
        String plainzeroPath = soundboardData.get(runningman_plainzero);
        if (plainzeroPath == null || !new File(plainzeroPath).exists()) {
            createplainzeroSampleTempFile();
        }
        String roomfistPath = soundboardData.get(runningman_roomfist);
        if (roomfistPath == null || !new File(roomfistPath).exists()) {
            createroomfistSampleTempFile();
        }
        String outoforderPath = soundboardData.get(scentofwoman_outoforder);
        if (outoforderPath == null || !new File(outoforderPath).exists()) {
            createoutoforderSampleTempFile();
        }
        String lostgeorgePath = soundboardData.get(snatch_lostgeorge);
        if (lostgeorgePath == null || !new File(lostgeorgePath).exists()) {
            createlostgeorgeSampleTempFile();
        }
        String crapmegacrapPath = soundboardData.get(spiderman_crapmegacrap);
        if (crapmegacrapPath == null || !new File(crapmegacrapPath).exists()) {
            createcrapmegacrapSampleTempFile();
        }
        String starfoxdogPath = soundboardData.get(starfox_starfoxdog);
        if (starfoxdogPath == null || !new File(starfoxdogPath).exists()) {
            createstarfoxdogSampleTempFile();
        }
        String starfoxdonkeyPath = soundboardData.get(starfox_starfoxdonkey);
        if (starfoxdonkeyPath == null || !new File(starfoxdonkeyPath).exists()) {
            createstarfoxdonkeySampleTempFile();
        }
        String starfoxfalcoPath = soundboardData.get(starfox_starfoxfalco);
        if (starfoxfalcoPath == null || !new File(starfoxfalcoPath).exists()) {
            createstarfoxfalcoSampleTempFile();
        }
        String starfoxrabbitPath = soundboardData.get(starfox_starfoxrabbit);
        if (starfoxrabbitPath == null || !new File(starfoxrabbitPath).exists()) {
            createstarfoxrabbitSampleTempFile();
        }
        String starfoxslippyPath = soundboardData.get(starfox_starfoxslippy);
        if (starfoxslippyPath == null || !new File(starfoxslippyPath).exists()) {
            createstarfoxslippySampleTempFile();
        }
        String makeitsoPath = soundboardData.get(startrek_makeitso);
        if (makeitsoPath == null || !new File(makeitsoPath).exists()) {
            createmakeitsoSampleTempFile();
        }
        String lackfaithPath = soundboardData.get(starwars_lackfaith);
        if (lackfaithPath == null || !new File(lackfaithPath).exists()) {
            createlackfaithSampleTempFile();
        }
        String round1Path = soundboardData.get(streetfighter2_round1);
        if (round1Path == null || !new File(round1Path).exists()) {
            createround1SampleTempFile();
        }
        String knockoutPath = soundboardData.get(streetfigther2_knockout);
        if (knockoutPath == null || !new File(knockoutPath).exists()) {
            createknockoutSampleTempFile();
        }
        String bootsPath = soundboardData.get(terminator2_boots);
        if (bootsPath == null || !new File(bootsPath).exists()) {
            createbootsSampleTempFile();
        }
        String fookerPath = soundboardData.get(terminator_fooker);
        if (fookerPath == null || !new File(fookerPath).exists()) {
            createfookerSampleTempFile();
        }
        String idiotstarscreamPath = soundboardData.get(transformers_idiotstarscream);
        if (idiotstarscreamPath == null || !new File(idiotstarscreamPath).exists()) {
            createidiotstarscreamSampleTempFile();
        }
        String megatronstoppedPath = soundboardData.get(transformers_megatronstopped);
        if (megatronstoppedPath == null || !new File(megatronstoppedPath).exists()) {
            createmegatronstoppedSampleTempFile();
        }
        String ripoutopticsPath = soundboardData.get(transformers_ripoutoptics);
        if (ripoutopticsPath == null || !new File(ripoutopticsPath).exists()) {
            createripoutopticsSampleTempFile();
        }
        String cowbellpantsPath = soundboardData.get(walken_cowbellpants);
        if (cowbellpantsPath == null || !new File(cowbellpantsPath).exists()) {
            createcowbellpantsSampleTempFile();
        }
        String whycantwebePath = soundboardData.get(whycant_whycantwebe);
        if (whycantwebePath == null || !new File(whycantwebePath).exists()) {
            createwhycantwebeSampleTempFile();
        }
        String youlosePath = soundboardData.get(willywonka_youlose);
        if (youlosePath == null || !new File(youlosePath).exists()) {
            createyouloseSampleTempFile();
        }
        String ricflairPath = soundboardData.get(wwf_ricflair);
        if (ricflairPath == null || !new File(ricflairPath).exists()) {
            createricflairSampleTempFile();
        }
        String nochoicePath = soundboardData.get(xmen_nochoice);
        if (nochoicePath == null || !new File(nochoicePath).exists()) {
            createnochoiceSampleTempFile();
        }
        String linksoundsPath = soundboardData.get(zelda_linksounds);
        if (linksoundsPath == null || !new File(linksoundsPath).exists()) {
            createlinksoundsSampleTempFile();
        }
    }

    // part 3 of the process_wavs.py output
    // the functions for the .wav files



    private void creategameoverSampleTempFile() {
        try {
            File file = File.createTempFile("aliens_gameover", "mp3", getCacheDir());
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
            File file = File.createTempFile("aliens_getaway", "mp3", getCacheDir());
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
    private void createyalethingSampleTempFile() {
        try {
            File file = File.createTempFile("americanpsycho_yalething", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.americanpsycho_yalething);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(americanpsycho_yalething, file.getPath());
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
            File file = File.createTempFile("armageddon_nonukes", "mp3", getCacheDir());
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
            File file = File.createTempFile("armageddon_whitehouse", "mp3", getCacheDir());
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
            File file = File.createTempFile("backtofuture2_kidsmarty", "mp3", getCacheDir());
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
            File file = File.createTempFile("backtofuture_thinkmcfly", "mp3", getCacheDir());
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
    private void createthinkfutureSampleTempFile() {
        try {
            File file = File.createTempFile("batman_thinkfuture", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.batman_thinkfuture);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(batman_thinkfuture, file.getPath());
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
            File file = File.createTempFile("bigtrouble_paiddues", "mp3", getCacheDir());
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
            File file = File.createTempFile("bigtrouble_reflexes", "mp3", getCacheDir());
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
    private void createitsatestSampleTempFile() {
        try {
            File file = File.createTempFile("bladerunner_itsatest", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.bladerunner_itsatest);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(bladerunner_itsatest, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createseenthingsSampleTempFile() {
        try {
            File file = File.createTempFile("bladerunner_seenthings", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.bladerunner_seenthings);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(bladerunner_seenthings, file.getPath());
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
            File file = File.createTempFile("boondock_flipside", "mp3", getCacheDir());
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
    private void createdeadcostnothingSampleTempFile() {
        try {
            File file = File.createTempFile("braveheart_deadcostnothing", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.braveheart_deadcostnothing);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(braveheart_deadcostnothing, file.getPath());
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
            File file = File.createTempFile("braveheart_urfooked", "mp3", getCacheDir());
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
            File file = File.createTempFile("caddyshack_cinderella", "mp3", getCacheDir());
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
            File file = File.createTempFile("caddyshack_hatsoup", "mp3", getCacheDir());
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
            File file = File.createTempFile("commando_steam", "mp3", getCacheDir());
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
            File file = File.createTempFile("conan_kromlaughs", "mp3", getCacheDir());
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
            File file = File.createTempFile("conan_lament", "mp3", getCacheDir());
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
    private void createdbzintroSampleTempFile() {
        try {
            File file = File.createTempFile("dbz_dbzintro", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.dbz_dbzintro);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(dbz_dbzintro, file.getPath());
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
            File file = File.createTempFile("diehard2_notyet", "mp3", getCacheDir());
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
            File file = File.createTempFile("diehard2_tincan", "mp3", getCacheDir());
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
            File file = File.createTempFile("diehard3_hardly", "mp3", getCacheDir());
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
            File file = File.createTempFile("diehard3_heyzues", "mp3", getCacheDir());
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
    private void createhansSampleTempFile() {
        try {
            File file = File.createTempFile("diehard_hans", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.diehard_hans);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(diehard_hans, file.getPath());
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
            File file = File.createTempFile("diehard_hohoho", "mp3", getCacheDir());
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
            File file = File.createTempFile("diehard_oops", "mp3", getCacheDir());
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
    private void createsuckafuckSampleTempFile() {
        try {
            File file = File.createTempFile("donniedarko_suckafuck", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.donniedarko_suckafuck);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(donniedarko_suckafuck, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createwhocaresSampleTempFile() {
        try {
            File file = File.createTempFile("fifthelement_whocares", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.fifthelement_whocares);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(fifthelement_whocares, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createfalconpunchSampleTempFile() {
        try {
            File file = File.createTempFile("fzero_falconpunch", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.fzero_falconpunch);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(fzero_falconpunch, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createnodickSampleTempFile() {
        try {
            File file = File.createTempFile("ghostbusters_nodick", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.ghostbusters_nodick);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(ghostbusters_nodick, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createhelpusSampleTempFile() {
        try {
            File file = File.createTempFile("glengarry_helpus", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.glengarry_helpus);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(glengarry_helpus, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createleadsSampleTempFile() {
        try {
            File file = File.createTempFile("glengarry_leads", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.glengarry_leads);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(glengarry_leads, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createpricewrongSampleTempFile() {
        try {
            File file = File.createTempFile("happygilmore_pricewrong", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.happygilmore_pricewrong);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(happygilmore_pricewrong, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createwhosbadSampleTempFile() {
        try {
            File file = File.createTempFile("jackson_whosbad", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.jackson_whosbad);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(jackson_whosbad, file.getPath());
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
            File file = File.createTempFile("karatekid_bestaround", "mp3", getCacheDir());
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
            File file = File.createTempFile("kindergartencop_shutup", "mp3", getCacheDir());
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
            File file = File.createTempFile("kindergartencop_stopit", "mp3", getCacheDir());
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
            File file = File.createTempFile("kindergartencop_tuma", "mp3", getCacheDir());
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
            File file = File.createTempFile("labrynth_tobyslave", "mp3", getCacheDir());
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
    private void createtakemeSampleTempFile() {
        try {
            File file = File.createTempFile("lastofthemohicans_takeme", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.lastofthemohicans_takeme);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(lastofthemohicans_takeme, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createlaughSampleTempFile() {
        try {
            File file = File.createTempFile("legacyofkain_laugh", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.legacyofkain_laugh);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(legacyofkain_laugh, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createvaevictusSampleTempFile() {
        try {
            File file = File.createTempFile("legacyofkain_vaevictus", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.legacyofkain_vaevictus);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(legacyofkain_vaevictus, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void creategetitgirlSampleTempFile() {
        try {
            File file = File.createTempFile("livecrew_getitgirl", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.livecrew_getitgirl);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(livecrew_getitgirl, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createsowhatSampleTempFile() {
        try {
            File file = File.createTempFile("metallica_sowhat", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.metallica_sowhat);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(metallica_sowhat, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void creategiveitupSampleTempFile() {
        try {
            File file = File.createTempFile("microsoft_giveitup", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.microsoft_giveitup);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(microsoft_giveitup, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createwinifyouwantSampleTempFile() {
        try {
            File file = File.createTempFile("moderntalking_winifyouwant", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.moderntalking_winifyouwant);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(moderntalking_winifyouwant, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void creategetmadSampleTempFile() {
        try {
            File file = File.createTempFile("network_getmad", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.network_getmad);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(network_getmad, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createlifevalueSampleTempFile() {
        try {
            File file = File.createTempFile("network_lifevalue", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.network_lifevalue);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(network_lifevalue, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createbastionyeahSampleTempFile() {
        try {
            File file = File.createTempFile("neverendingstory_bastionyeah", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.neverendingstory_bastionyeah);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(neverendingstory_bastionyeah, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void creategottabelieveSampleTempFile() {
        try {
            File file = File.createTempFile("parappa_gottabelieve", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.parappa_gottabelieve);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(parappa_gottabelieve, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createplainzeroSampleTempFile() {
        try {
            File file = File.createTempFile("runningman_plainzero", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.runningman_plainzero);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(runningman_plainzero, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createroomfistSampleTempFile() {
        try {
            File file = File.createTempFile("runningman_roomfist", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.runningman_roomfist);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(runningman_roomfist, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createoutoforderSampleTempFile() {
        try {
            File file = File.createTempFile("scentofwoman_outoforder", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.scentofwoman_outoforder);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(scentofwoman_outoforder, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createlostgeorgeSampleTempFile() {
        try {
            File file = File.createTempFile("snatch_lostgeorge", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.snatch_lostgeorge);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(snatch_lostgeorge, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createcrapmegacrapSampleTempFile() {
        try {
            File file = File.createTempFile("spiderman_crapmegacrap", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.spiderman_crapmegacrap);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(spiderman_crapmegacrap, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createstarfoxdogSampleTempFile() {
        try {
            File file = File.createTempFile("starfox_starfoxdog", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.starfox_starfoxdog);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(starfox_starfoxdog, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createstarfoxdonkeySampleTempFile() {
        try {
            File file = File.createTempFile("starfox_starfoxdonkey", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.starfox_starfoxdonkey);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(starfox_starfoxdonkey, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createstarfoxfalcoSampleTempFile() {
        try {
            File file = File.createTempFile("starfox_starfoxfalco", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.starfox_starfoxfalco);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(starfox_starfoxfalco, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createstarfoxrabbitSampleTempFile() {
        try {
            File file = File.createTempFile("starfox_starfoxrabbit", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.starfox_starfoxrabbit);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(starfox_starfoxrabbit, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createstarfoxslippySampleTempFile() {
        try {
            File file = File.createTempFile("starfox_starfoxslippy", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.starfox_starfoxslippy);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(starfox_starfoxslippy, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createmakeitsoSampleTempFile() {
        try {
            File file = File.createTempFile("startrek_makeitso", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.startrek_makeitso);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(startrek_makeitso, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createlackfaithSampleTempFile() {
        try {
            File file = File.createTempFile("starwars_lackfaith", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.starwars_lackfaith);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(starwars_lackfaith, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createround1SampleTempFile() {
        try {
            File file = File.createTempFile("streetfighter2_round1", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.streetfighter2_round1);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(streetfighter2_round1, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createknockoutSampleTempFile() {
        try {
            File file = File.createTempFile("streetfigther2_knockout", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.streetfigther2_knockout);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(streetfigther2_knockout, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createbootsSampleTempFile() {
        try {
            File file = File.createTempFile("terminator2_boots", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.terminator2_boots);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(terminator2_boots, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createfookerSampleTempFile() {
        try {
            File file = File.createTempFile("terminator_fooker", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.terminator_fooker);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(terminator_fooker, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createidiotstarscreamSampleTempFile() {
        try {
            File file = File.createTempFile("transformers_idiotstarscream", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.transformers_idiotstarscream);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(transformers_idiotstarscream, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createmegatronstoppedSampleTempFile() {
        try {
            File file = File.createTempFile("transformers_megatronstopped", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.transformers_megatronstopped);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(transformers_megatronstopped, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createripoutopticsSampleTempFile() {
        try {
            File file = File.createTempFile("transformers_ripoutoptics", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.transformers_ripoutoptics);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(transformers_ripoutoptics, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createcowbellpantsSampleTempFile() {
        try {
            File file = File.createTempFile("walken_cowbellpants", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.walken_cowbellpants);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(walken_cowbellpants, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createwhycantwebeSampleTempFile() {
        try {
            File file = File.createTempFile("whycant_whycantwebe", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.whycant_whycantwebe);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(whycant_whycantwebe, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createyouloseSampleTempFile() {
        try {
            File file = File.createTempFile("willywonka_youlose", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.willywonka_youlose);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(willywonka_youlose, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createricflairSampleTempFile() {
        try {
            File file = File.createTempFile("wwf_ricflair", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.wwf_ricflair);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(wwf_ricflair, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createnochoiceSampleTempFile() {
        try {
            File file = File.createTempFile("xmen_nochoice", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.xmen_nochoice);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(xmen_nochoice, file.getPath());
                saveSoundboardData();
            } catch (IOException e) {
                Log.w(TAG, "Unable to write tmp file!", e);
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to create tmp file!", e);
        }
    }
    private void createlinksoundsSampleTempFile() {
        try {
            File file = File.createTempFile("zelda_linksounds", "mp3", getCacheDir());
            try (FileOutputStream out = new FileOutputStream(file)) {
                InputStream in = getResources().openRawResource(R.raw.zelda_linksounds);
                ByteStreams.copy(in, out);
                out.flush();
                in.close();

                soundboardData.put(zelda_linksounds, file.getPath());
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
