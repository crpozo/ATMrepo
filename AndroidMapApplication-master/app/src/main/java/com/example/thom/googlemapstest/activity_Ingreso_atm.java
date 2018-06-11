package com.example.thom.googlemapstest;

        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.*;
        import android.widget.Toast;

        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;


public class activity_Ingreso_atm extends ActionBarActivity {

    private EditText longitud,latitud,nombre;
    private Spinner banco;
    private ArrayList<customMarker> customMarkersArray = new ArrayList<customMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__ingreso_atm);

        Spinner spinner1=(Spinner) findViewById(R.id.Bancossp);

        ArrayAdapter<CharSequence> adaptadorDatos= ArrayAdapter.createFromResource(this,R.array.lista_bancos,android.R.layout.simple_spinner_item);
        adaptadorDatos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptadorDatos);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected", Toast.LENGTH_LONG);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        longitud = (EditText) findViewById(R.id.editText3);
        latitud = (EditText) findViewById(R.id.editText2);
        nombre=(EditText) findViewById(R.id.editText);
        banco=(Spinner) findViewById(R.id.Bancossp);
        Locacion();
    }

    public void Locacion()
    {
        GPSDetector detector=new GPSDetector(this.getApplicationContext());
        longitud.setText(Double.toString(detector.getLongitud()));
        latitud.setText(Double.toString(detector.getLatitud()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__ingreso_atm, menu);
        return true;
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
    private HashMap<Marker, customMarker> markersHashMap;
    public void agregar(View view)
    {
        String nombre,bancoa;
        double longituda,latituda;
        GPSDetector localizacion=new GPSDetector(this.getApplicationContext());
        nombre=this.nombre.getText().toString();
        bancoa=String.valueOf(this.banco.getSelectedItem());
        longituda=localizacion.getLongitud();
        latituda=localizacion.getLatitud();
        ConexionBD ObjCnx = new ConexionBD(this);

        ObjCnx.abrirConexion();

        if(ObjCnx.insertar(nombre,bancoa,latituda,longituda)==true)
        {
            String texto = "Elemento Agregado Correctamente";
            Toast toast = Toast.makeText(this, texto,Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            String texto = "Error al Agregar Elemento";
            Toast toast = Toast.makeText(this,texto,Toast.LENGTH_LONG);
            toast.show();
        }
        ObjCnx.cerrarConexiones();
        GPSDetector a=new GPSDetector(this.getBaseContext());
        markersHashMap = new HashMap<Marker, customMarker>();
        customMarkersArray.add(new customMarker(a.getLatitud(), a.getLongitud()));
        addMarker();
        plotMarkers(customMarkersArray);
    }
    GoogleMap googleMap;
    Marker marcador;
    private void addMarker(){

        if(null != googleMap)
        {
            GPSDetector lol=null;
            marcador = MainActivity.getMapa().addMarker(new MarkerOptions()
                            .position(new LatLng(lol.getLatitud(), lol.getLongitud()))
            );

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return false;
                }
            });
        }
    }
    private void addMarker( double latitud,double longitud) {
        /** Make sure that the map has been initialised **/

        if(null != googleMap)
        {
            GPSDetector lol=null;
            marcador = MainActivity.getMapa().addMarker(new MarkerOptions()
                            .position(new LatLng(latitud, longitud))
            );

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return false;
                }
            });
        }
    }
    private void plotMarkers(ArrayList<customMarker> markers) {
        if (markers.size() > 0) {

            for (customMarker myCustomMarker : markers) {
                MarkerOptions markerOption = new MarkerOptions()
                        .position(new LatLng(myCustomMarker.getLat(), myCustomMarker.getLng()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.atm));

                Marker currentMarker = MainActivity.getMapa().addMarker(markerOption);
                markersHashMap.put(currentMarker, myCustomMarker);
            }
        }
    }
}
