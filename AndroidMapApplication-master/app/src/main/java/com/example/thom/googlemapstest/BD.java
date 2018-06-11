package com.example.thom.googlemapstest;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {

    String query="CREATE TABLE Cajeros(Nombre TEXT,Banco TEXT, latitud DOUBLE, longitud DOUBLE)";
    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(query); //Ejecuta query para crear la tabla
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i2)
    {
        db.execSQL("DROP TABLE IF EXISTS Cajeros"); //*hace actualizaciones de la tabla
        db.execSQL(query);
    }

}
