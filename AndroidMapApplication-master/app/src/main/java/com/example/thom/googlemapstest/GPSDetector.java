package com.example.thom.googlemapstest;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Personal on 23/04/2015.
 */
public class GPSDetector extends Service implements LocationListener {

    private final Context mContext;

    //Estado GPS
    boolean isGPSEnabled = false;

    //Estado de Red
    boolean isNetworkEnabled = false;

    // GPS puede obtener locacion
    boolean canGetLocation = false;

    Location locacion = null; // locacion
    double latitud; // latitud
    double longitud; // longitud

    // Distancia minima para actualizar posicion
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 metros

    // Tiempo minimo para actualizar posicion en milisegundos
    private static final long MIN_TIME_BW_UPDATES =  60 * 1; // 1 minut0

    // Declaracion de un Manejador de locacion
    protected LocationManager locationManager;

    public GPSDetector(Context context) {
        this.mContext = context;
        getLocacion();
    }

    public Location getLocacion() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // Obtencion del estado del GPS
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Obtencion del estado de la red
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // Red no activada

                showSettingsAlert();
                // GPS activado
                if (isGPSEnabled) {
                    if (locacion == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            locacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (locacion != null) {
                                latitud = locacion.getLatitude();
                                longitud = locacion.getLongitude();
                            }
                        }
                    }
                }

            }
            else
            {
                this.canGetLocation = true;
                // GPS activado
                if (isGPSEnabled) {
                    if (locacion == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Activado");
                        if (locationManager != null) {
                            locacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (locacion != null) {
                                latitud = locacion.getLatitude();
                                longitud = locacion.getLongitude();
                            }
                        }
                    }
                }

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Red Activada");
                    if (locationManager != null) {
                        locacion = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (locacion != null) {
                            latitud = locacion.getLatitude();
                            longitud = locacion.getLongitude();
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return locacion;
    }
    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void detenerGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSDetector.this);
        }
    }
    /**
     * Function to get latitude
     * */
    public double getLatitud() {
        if (locacion != null) {
            latitud = locacion.getLatitude();
        }
        return latitud;
    }
    /**
     * Function to get longitude
     * */
    public double getLongitud() {
        if (locacion != null) {
            longitud = locacion.getLongitude();
        }

        return longitud;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("Ojo");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS Desactivado. ¿Ir al menu de ajustes?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Ajustes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
