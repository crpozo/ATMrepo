package com.example.thom.googlemapstest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.ImageButton;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;


public class MainActivity extends ActionBarActivity
        implements Menu_Navegacion.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private Menu_Navegacion mMenuNavegacion;
    DrawerLayout mDrawerLayout;



    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private static GoogleMap googleMap;


    //Added for custom buttons
     ImageButton meetUpButton;
    ImageButton menuButton;
    ImageButton addButton;

    ImageButton searchBarButton;

    SearchView searchView;
    boolean hide = true;

    public void Ingresar_atm(View view) {
        Intent nombre = new Intent(this,activity_Ingreso_atm.class);
        startActivity(new Intent(getApplicationContext(),activity_Ingreso_atm.class));
    }
    public static GoogleMap getMapa()
    {

        return googleMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        mMenuNavegacion = (Menu_Navegacion)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mMenuNavegacion.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);






        createMapView();
        //addMarker();
        //plotMarkers(customMarkersArray);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);

        addListenerOnMenuButton();

    }
    /*Opciones del Mapa de navegacion */
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0:

                break;
            case 1:
                Intent Consulta = new Intent(this,Anadir_Marcadores.class);
                startActivity(new Intent(getApplicationContext(),Anadir_Marcadores.class));
                break;
            case 2:
                Intent nombre = new Intent(this,Acerca_De.class);
                startActivity(new Intent(getApplicationContext(),Acerca_De.class));
                break;

        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .commit();

    }

    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creando mapa", Toast.LENGTH_SHORT).show();
                }
                else {
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                            marker.showInfoWindow();
                            return true;
                        }
                    });
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }
    /**
     * Adds a marker to the map
     */


public  GoogleMap get_Mapa()
{
    return googleMap;
}

    private int manageMarkerPhoto(String photoName) {
        return R.drawable.cafe;
    }

    private int manageMarkerRating(String ratingName) {
        return R.drawable.fourandahalf2;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mMenuNavegacion.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void addListenerOnMenuButton() {

        menuButton = (ImageButton) findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mDrawerLayout.openDrawer(Gravity.LEFT);

            }

        });

    }



    public void doLocationSearch(String query) {

    }

    public void hide(View v) {
        if (hide) {
            searchView.setVisibility(View.VISIBLE);
            searchView.setQuery("", false);
            searchView.clearFocus();
            createSearchBarBackground();
            hide = false;
        }
        else {
            searchView.setVisibility(View.GONE);
            hide = true;
        }
    }

    public void createSearchBarBackground() {
        searchView.setQueryHint("Search for an Address");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);

        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.WHITE);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);

            if (searchText != null) {
                searchText.setTextColor(Color.DKGRAY);
                searchText.setHintTextColor(Color.DKGRAY);
            }
        }
    }

    }



