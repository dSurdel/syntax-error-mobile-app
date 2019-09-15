package io.carmaster.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.IconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;
import io.carmaster.myapplication.services.retrofitModels.Initiative;
import io.carmaster.myapplication.services.retrofitModels.Notification;
import io.carmaster.myapplication.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_main, fab1_mail, fab2_share;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_mail, textview_share;

    Boolean isOpen = false;

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 85, 85, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    MapView map = null;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity opener
       // Intent intent = new Intent(MainActivity.this, SweepActivity.class);


        //intent.putExtra("EXIT", false);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


      //  startActivity(intent);


       // super.addOverlays();

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        final Context ctx;
        ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);



        boolean isLogged = sharedPref.getBoolean("isLogged", false);
        if (isLogged) {
            // Activity opener
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        } else {
            // Activity opener
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_main);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

       // map = (MapView) findViewById(R.id.initiative_description );
       // map.setTileSource(TileSourceFactory.MAPNIK);
       // map.setMultiTouchControls(true);





        IMapController mapController = map.getController();
        mapController.setZoom(11.0);
        GeoPoint startPoint = new GeoPoint(52.241903, 21.025242);
        mapController.setCenter(startPoint);



//build the marker
        Marker m = new Marker(map);

        Drawable icon = getResources().getDrawable(R.drawable.marker_society);
        m.setImage(getResources().getDrawable(R.drawable.krzysztof_klis).mutate());
        m.setTitle("Test");
//must set the icon to null last
        m.setIcon(resize(icon));
        m.setDraggable(true);
        m.setPosition(new GeoPoint(50.04589598d,21.39814854d));
        m.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker item, MapView arg1) {
                //item.showInfoWindow();
                CustomMarkerDialog cdd=new CustomMarkerDialog(MainActivity.this, "","", "", 0);
                cdd.show();
                Toast.makeText(
                        ctx,
                        "Item "+item, Toast.LENGTH_LONG).show();
                return true;
            }
        });
        map.getOverlays().add(m);
        ////////////////////////////////////////////
     /*   Marker m2 = new Marker(map);

        Drawable icon2 = getResources().getDrawable(R.drawable.marker_eco);
        m2.setImage(getResources().getDrawable(R.drawable.krzysztof_klis).mutate());
        m2.setTitle("Jutro praktyczka xDddddD");
//must set the icon to null last
        m2.setIcon(resize(icon));
        m2.setDraggable(true);
        m2.setPosition(new GeoPoint(50.03589598d,21.19814854d));
        m2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker item, MapView arg1) {
                //item.showInfoWindow();
                CustomMarkerDialog cdd=new CustomMarkerDialog(MainActivity.this, "dsfsdf", "", "");
                cdd.show();
                Toast.makeText(
                        ctx,
                        "Item "+item, Toast.LENGTH_LONG).show();
                return true;
            }
        });
        map.getOverlays().add(m2);
        */


        // Iterate over response from server and add markers to map with notifications GUIDs
        RetrofitClientInstance retrofitClient = new RetrofitClientInstance();
        ICSApiService service = retrofitClient.getRetrofitInstance().create(ICSApiService.class);

        Call<List<Initiative>> notificationsCall = service.getSocialInitiatives();
        notificationsCall.enqueue(new Callback<List<Initiative>>() {
            @Override
            public void onResponse(Call<List<Initiative>> call, Response<List<Initiative>> response) {
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                String gson1 = new Gson().toJson(response.body());
                try {
                    //JSONObject obj = new JSONObject(gson1);
                    JSONArray jsonarray = new JSONArray(gson1);

                   // JSONArray arr = obj.getJSONArray();
                    for (int i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                        String initiativeName = jsonobject.getString("name");
                        String description = jsonobject.getString("description");

                        String socialInitiativeId = jsonobject.getString("socialInitiativeId");


                        double latitude = jsonobject.getDouble("latitude");
                        double longitude = jsonobject.getDouble("longitude");

                        int positive = jsonobject.getInt("positive");
                       // String description = jsonobject.getString("description");


                        ////////////////////////////////////////////////////////
                        Marker m2 = new Marker(map);

                        Drawable icon2 = getResources().getDrawable(R.drawable.marker_eco);
                        m2.setImage(getResources().getDrawable(R.drawable.krzysztof_klis).mutate());
                        m2.setTitle(initiativeName);
                        //must set the icon to null last
                        m2.setIcon(resize(icon));
                        m2.setDraggable(true);
                        m2.setPosition(new GeoPoint(latitude,longitude));
                        m2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker item, MapView arg1) {
                                //item.showInfoWindow();
                                CustomMarkerDialog cdd=new CustomMarkerDialog(MainActivity.this, socialInitiativeId, initiativeName, description, positive);
                                cdd.show();
                                Toast.makeText(
                                        ctx,
                                        "Item "+item, Toast.LENGTH_LONG).show();
                                return true;
                            }
                        });
                        map.getOverlays().add(m2);
                        ///////////////////////////////////////////////////////

                    }
                    //Log.e("TAG", "response 33: "+pageName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                      /*  Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.toString(), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onFailure(Call<List<Initiative>> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "Error ", Toast.LENGTH_LONG).show();
            }
        });



        //the overlay

       //  NominatimPOIProvider poiProvider = new NominatimPOIProvider("OSMBonusPackTutoUserAgent");
       // ArrayList<POI> pois = poiProvider.getPOICloseTo(startPoint, "cinema", 50, 0.1);

       // FolderOverlay poiMarkers = new FolderOverlay(this);
     //   map.getOverlays().add(poiMarkers);

/*
        Drawable poiIcon = getResources().getDrawable(R.drawable.marker_cluster);
        for (POI poi:pois){
            Marker poiMarker = new Marker(map);
            poiMarker.setTitle(poi.mType);
            poiMarker.setSnippet(poi.mDescription);
            poiMarker.setPosition(poi.mLocation);
            poiMarker.setIcon(poiIcon);
            if (poi.mThumbnail != null){
                //poiItem.setImage(new BitmapDrawable(poi.mThumbnail));
            }
            //poiMarkers.add(poiMarker);
        }
*/


        RadiusMarkerClusterer poiMarkers = new RadiusMarkerClusterer(this);

        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();


        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
       // LocationListener locationListener = new MyLocationListener();
       // locationManager.requestLocationUpdates(
          //      LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        /*
        poiMarkers.setIcon(clusterIcon);

        NominatimPOIProvider poiProvider = new NominatimPOIProvider("OSMBonusPackTutoUserAgent");
        ArrayList<POI> pois = poiProvider.getPOICloseTo(startPoint, "cinema", 50, 0.1);

        */


        // We adding a new marker

        //public boolean dodajMarkerAkcjiDlaUrzedu () {
                // Get current coords form GPS

        //}



        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {

                // write your code here
                Toast.makeText(
                        ctx,
                        "Item "+p, Toast.LENGTH_LONG).show();



                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                // write your code here
                Toast.makeText(
                        ctx,
                        "Item long press "+p, Toast.LENGTH_LONG).show();
                Initiative initiative = new Initiative();
                //initiative.setDescription(citizenInitiativeDescriptionTextArea.getText().toString());
                //initiative.setName(citizenInitiativeDescriptionTextArea.getText().toString());

                //AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Input social inititative data");

                View viewInflated = LayoutInflater.from(getBaseContext()).inflate(R.layout.txt_dialog_custom, (ViewGroup) findViewById(android.R.id.content), false);

                final EditText initiativeNameiInput = (EditText) viewInflated.findViewById(R.id.text_custom_dialog_input);
                final EditText initiativeDescriptionInput = (EditText) viewInflated.findViewById(R.id.text_custom_dialog_input_description);
                builder.setView(viewInflated);


                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //m_Text = input.getText().toString();
                        initiative.setDescription(initiativeNameiInput.getText().toString());
                        AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                        Log.e("TAG", "response 33: " );

                        initiative.setDescription(initiativeDescriptionInput.getText().toString());
                        initiative.setCoords(p.getLatitude(), p.getLongitude());

                        Call<Initiative> addInitiativeCall = service.addSocialInitiative(
                                initiative
                        );
                        addInitiativeCall.enqueue(new Callback<Initiative>() {
                            @Override
                            public void onResponse(Call<Initiative> call, Response<Initiative> response) {
                                Log.e("TAG", "response 33: " );
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Item "+response.body(), Toast.LENGTH_LONG).show();
                                if (response.isSuccessful()) {
                                    String gson1 = new Gson().toJson(response.body());
                                    try {
                                        JSONObject obj = new JSONObject(gson1);
                                        String tokenGUID = obj.getString("token");

                                        if (tokenGUID != null) {

                                            Marker m2 = new Marker(map);

                                            Drawable icon2 = getResources().getDrawable(R.drawable.marker_eco);
                                            m2.setImage(getResources().getDrawable(R.drawable.krzysztof_klis).mutate());
                                            m2.setTitle(initiativeNameiInput.getText().toString());
//must set the icon to null last
                                            m2.setIcon(resize(icon));
                                            m2.setDraggable(true);
                                            m2.setPosition(new GeoPoint(p.getLatitude(),p.getLongitude()));
                                            m2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {

                                                @Override
                                                public boolean onMarkerClick(Marker item, MapView arg1) {
                                                    //item.showInfoWindow();
                                                    CustomMarkerDialog cdd=new CustomMarkerDialog(MainActivity.this, tokenGUID, initiativeNameiInput.getText().toString(), initiativeDescriptionInput.getText().toString(), 1);
                                                    cdd.show();
                                                    Toast.makeText(
                                                            ctx,
                                                            "Item "+item, Toast.LENGTH_LONG).show();
                                                    return true;
                                                }
                                            });
                                            map.getOverlays().add(m2);
                                        } else {
                                            // Failed login
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Invalid username or password", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Invalid username or password or connection problem", Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<Initiative> call, Throwable t) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Error "+t.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                        builder2.setTitle("Input social inititative description");

                        View viewInflated2 = LayoutInflater.from(getBaseContext()).inflate(R.layout.txt_dialog_custom2, (ViewGroup) findViewById(android.R.id.content), false);

                        final EditText initiativeDescriptionInput = (EditText) viewInflated2.findViewById(R.id.text_custom_dialog_input2);
                        builder2.setView(viewInflated2);

                        builder2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog2, int which2) {
                                        //m_Text = input.getText().toString();

                                      //  dialog2.dismiss();
                                    }
                                });

                        //dialog.dismiss();

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();





                // We can now trigger initiative add
                return false;
            }
        };

        MapEventsOverlay OverlayEvents = new MapEventsOverlay(getBaseContext(), mReceive);
        map.getOverlays().add(OverlayEvents);

        //.getOverlays().add(itemOverlay);

        final RotationGestureOverlay mRotationGestureOverlay;
        mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(false);
        map.getOverlays().add(mRotationGestureOverlay);
        //the overlay


        //Fab button
//        FloatingActionButton fab = findViewById(R.id.imageButton);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        //fab animation
        fab_main = findViewById(R.id.fab);
        fab1_mail = findViewById(R.id.fab1);
        fab2_share = findViewById(R.id.fab2);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_mail = findViewById(R.id.textview_mail);
        textview_share = findViewById(R.id.textview_share);

        fab_main.setOnClickListener(view -> {

            if (isOpen) {

                textview_mail.setVisibility(View.INVISIBLE);
                textview_share.setVisibility(View.INVISIBLE);
                fab2_share.startAnimation(fab_close);
                fab1_mail.startAnimation(fab_close);
                fab_main.startAnimation(fab_anticlock);
                fab2_share.setClickable(false);
                fab1_mail.setClickable(false);
                isOpen = false;
            } else {
                textview_mail.setVisibility(View.VISIBLE);
                textview_share.setVisibility(View.VISIBLE);
                fab2_share.startAnimation(fab_open);
                fab1_mail.startAnimation(fab_open);
                fab_main.startAnimation(fab_clock);
                fab2_share.setClickable(true);
                fab1_mail.setClickable(true);
                isOpen = true;
            }

        });


        fab2_share.setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show());

        fab1_mail.setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Email", Toast.LENGTH_SHORT).show());


    }




    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
}
