package com.development.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbMamager extends SQLiteOpenHelper {

    //  Creamos la tabla del jugador
    public String playerTable = "CREATE TABLE jugador (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "nombre VARCHAR(255)," +
                                "nivel INTEGER );";
    public Context cnt;

    public DbMamager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.cnt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(playerTable);
        } catch (Exception e) {
            Toast.makeText(this.cnt, "Tabla creada con exito.", Toast.LENGTH_LONG).show();
        }
    }

    public long crearJugador(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("nivel", 1);
        try {
            long id = db.insert("jugador", null, contentValues);
            return id;
        }catch(Exception ex){
            return -1;
        }
    }

    public Cursor obtenerJugadores() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = db.query("jugador", null, null, null, null, null, null);
            return c;
        }catch (Exception ex) {
            return null;
        }
    }

    public boolean borrarJugador(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] argumentos ={String.valueOf(id)};
        try {
            db.delete("jugador", "_id = ?", argumentos);
            return true;
        }catch(Exception ex) {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
