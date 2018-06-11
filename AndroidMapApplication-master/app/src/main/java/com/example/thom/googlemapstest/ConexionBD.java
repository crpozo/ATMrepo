package com.example.thom.googlemapstest;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.view.View;
import android.widget.Toast;

public class ConexionBD {

    public static SQLiteDatabase db;
    private static Context nContext;
    private static BD objBD;

    public ConexionBD(Context o)
    {
        nContext=o;
    }

    public SQLiteDatabase abrirConexion()
    {
        objBD = new BD(nContext,"BDatmRepo",null,1);
        db = objBD.getWritableDatabase();
        return db;
    }

    public void cerrarConexiones()
    {
        db.close();
    }

    public boolean insertar(String nombre,String banco, double latitud, double longitud)
    {
        boolean resultado = false;
        try{
            String query="INSERT INTO Cajeros (Nombre,banco,latitud,longitud) VALUES('"+nombre+"','"+banco+"',"+latitud+","+longitud+")";

            db.execSQL(query);
            resultado = true;
            return resultado;

        }catch(Exception e)
        {
            resultado = false;
            return resultado;
        }
    }



}
