package org.example.udprojects;

import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    /*final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private CategoryAdapter categoryAdapter;
    private LatLng currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setup client
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();
        //setup ViewPager
        categoryAdapter = new CategoryAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.categories));
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception ge) {
            ge.printStackTrace();
            onConnectionFailed(new ConnectionResult(ConnectionResult.NETWORK_ERROR));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void foo() {
        try {
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
            result.setResultCallback(this);
        }
        catch (SecurityException ge) {
            ge.printStackTrace();
            onConnectionFailed(new ConnectionResult(ConnectionResult.NETWORK_ERROR));
        }
    }

    @Override
    public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
            Place place = placeLikelihood.getPlace();
            Log.i("place", String.format("Place '%s' is a %s",
                    place.getName(),
                   place.getPlaceTypes().toString()));
            PendingResult<PlacePhotoMetadataResult> pendingResult = Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, place.getId());
            //pendingResult.setResultCallback(new PhotoManager(mGoogleApiClient, this));
        }
        likelyPlaces.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                // set adapter AFTER location is determined
                currentLocation = PlacePicker.getPlace(this, data).getLatLng();
                viewPager.setAdapter(categoryAdapter);
            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(findViewById(R.id.root), "Connection failed: " + connectionResult.getErrorMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public GoogleApiClient getClient() {
        return null;
    }
}
*/
}
/* RBY minisprites:
Plant - Bulbasaur line, Oddish line, Bellsprout line, Exeggcute line, Tangela
Monster - Charmander line, Sandshrew line, Nidoran lines, Mankey line, Abra line, Machop line, Slowpoke line, Grimer line, Drowzee line, Cubone line, Hitmonlee, Hitmonchan, Lickitung, Rhyhorn line, Kangaskhan, Mr. Mime, Jynx, Electabuzz, Magmar, Aerodactyl, Snorlax, Mewtwo
Sea creature - Squirtle line, Psyduck line, Poliwag line, Tentacool line, Seel line, Goldeen line, Magikarp, Lapras
Insect - Caterpie line, Weedle line, Paras line, Venonat line, Scyther, Pinsir
Bird - Pidgey line, Spearow line, Zubat line, Farfetch'd, Doduo line, Articuno, Zapdos, Moltres
Bovine - Rattata line, Vulpix line, Diglett line, Meowth line, Growlithe line, Ponyta line, Tauros, Eevee line
Serpent - Ekans line, Onix, Horsea line, Gyarados, Dratini line
Fairy - Pikachu (RB), Raichu, Clefairy line, Jigglypuff line, Chansey, Mew
Poké Ball - Magnemite line, Gastly line, Voltorb line, Koffing line, Ditto, Porygon
Helix - Shellder line, Krabby line, Staryu line, Omanyte line, Kabuto line
Pikachu (Yellow only)

GSC minisprites:
Oddish - Oddish line, Bellsprout line, Exeggcute line, Tangela, Chikorita line, Hoppip line, Sunkern line
Cat - Rattata line, Sandshrew line, Vulpix line, Meowth line, Growlithe line, Eevee line, Cyndaquil line, Sentret line, Sneasel, Raikou, Entei, Suicune
Monster - Nidoran lines, Cubone line, Lickitung, Rhyhorn line, Kangaskhan, Aerodactyl, Totodile line, Mareep line, Wooper line, Snubbull line, Teddiursa line, Larvitar line
Bird - Pidgey line, Spearow line, Farfetch'd, Doduo line, Articuno, Zapdos, Moltres, Hoothoot line, Natu line, Murkrow, Delibird, Skarmory
Pikachu - Pikachu line
Weedle - Caterpie, Metapod, Weedle, Kakuna, Venonat, Shuckle
Butterfly - Butterfree, Venomoth, Ledyba line, Yanma
Insect - Paras line, Scyther line, Pinsir, Spinarak line, Pineco line, Gligar, Heracross
Geodude - Geodude line
Bat - Zubat line
Clefairy - Clefairy line, Chansey line, Mew, Togepi line, Celebi
Jigglypuff - Jigglypuff line, Marill line
Serpent - Ekans line, Onix line, Horsea, Seadra, Dratini, Dragonair, Dunsparce
Gengar - Gastly line, Misdreavus
Unown
Poliwag - Psyduck line, Poliwag line
Fish - Goldeen line, Magikarp, Chinchou line, Qwilfish, Remoraid, Mantine
Gyarados
Slowpoke - Slowpoke line
Mr. Mime - Abra line, Drowzee line, Mr. Mime, Jynx line, Electabuzz line, Magmar line, Smeargle
Amorphous - Grimer line, Koffing line, Ditto, Wobbuffet, Slugma line
Sudowoodo
Voltorb - Magnemite line, Voltorb line, Porygon line
Diglett - Diglett line
Machop - Mankey line, Machop line, Tyrogue line
Bovine - Ponyta line, Tauros, Girafarig, Houndour line, Swinub line, Phanpy line
Tentacool - Tentacool line, Octillery
Clam - Shellder line, Krabby line, Omanyte line, Kabuto line, Corsola
Staryu - Staryu line
Lapras - Seel line, Lapras
Charizard - Charizard, Dragonite, Kingdra
Snorlax
Bulbasaur - Bulbasaur line
Charmander - Charmander, Charmeleon
Squirtle - Squirtle line
Lugia
Ho-oh*/