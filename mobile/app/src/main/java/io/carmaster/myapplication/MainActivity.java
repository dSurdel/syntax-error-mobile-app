package io.carmaster.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.IconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;


public class MainActivity extends AppCompatActivity {
    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 75, 75, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    MapView map = null;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // super.addOverlays();

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        final Context ctx;
        ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
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
//build the marker
        Marker m = new Marker(map);

        Drawable icon = getResources().getDrawable(R.drawable.marker);
        m.setImage(getResources().getDrawable(R.drawable.krzysztof_klis).mutate());
        m.setTitle("Jutro praktyczka xDddddD");
//must set the icon to null last
        m.setIcon(resize(icon));
        m.setDraggable(true);
        m.setPosition(new GeoPoint(50.04589598d,21.39814854d));
        m.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker item, MapView arg1) {
                //item.showInfoWindow();
                CustomMarkerDialog cdd=new CustomMarkerDialog(MainActivity.this);
                cdd.show();
                Toast.makeText(
                        ctx,
                        "Item "+item, Toast.LENGTH_LONG).show();
                return true;
            }
        });
        map.getOverlays().add(m);
        //the overlay


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
