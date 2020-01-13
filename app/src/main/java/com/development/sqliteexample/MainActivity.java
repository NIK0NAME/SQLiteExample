package com.development.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public DbMamager gestorDb;

    public Button btnCrearJugador, btnPlayerBorrar;
    public TextView txtNombreJugador;
    public ListView listaJugadores;
    public Cursor jugadore;
    public int idJugadorSeleccionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //  Instanciamos el gestor de base de datos local
        gestorDb = new DbMamager(this, "new_db", null, 1);

        listaJugadores = findViewById(R.id.listaJugadore);
        listaJugadores.setOnItemClickListener(this);

        cargarJugadores();

        txtNombreJugador = findViewById(R.id.txtNombreJugador);

        btnCrearJugador = findViewById(R.id.btnCrearJugador);
        btnCrearJugador.setOnClickListener(this);
        btnPlayerBorrar = findViewById(R.id.btnPlayerRemove);
        btnPlayerBorrar.setOnClickListener(this);
    }

    public void cargarJugadores() {
        jugadore = gestorDb.obtenerJugadores();

        if(jugadore != null) {
            ArrayList<String> nombresJugador = new ArrayList<>();
            while (jugadore.moveToNext()) {
                nombresJugador.add(jugadore.getString(1));
            }
            listaJugadores.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombresJugador));
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnCrearJugador) {
            crearJugador();
        }else if(v == btnPlayerBorrar) {
            borrarJugador();
        }
    }

    public void crearJugador() {
        String nombre = txtNombreJugador.getText().toString();
        if(!nombre.equals("") && !nombre.equals("Name")) {
            long playerId = gestorDb.crearJugador(nombre);
            if(playerId != -1) {
                Toast.makeText(this, "Jugador " + playerId + "creado.", Toast.LENGTH_LONG).show();
                cargarJugadores();
                txtNombreJugador.setText("");
            }
        }else {
            Toast.makeText(this, "U BETTER HAVE A BETTER NAME !", Toast.LENGTH_LONG).show();
        }
    }

    public void borrarJugador() {
        if(idJugadorSeleccionado != -1) {
            if(gestorDb.borrarJugador(idJugadorSeleccionado)) {
                Toast.makeText(this, idJugadorSeleccionado + " Eliminado.", Toast.LENGTH_LONG).show();
                cargarJugadores();
                idJugadorSeleccionado = -1;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        view.setBackgroundColor(Color.BLUE);

        jugadore.moveToPosition(position);
        idJugadorSeleccionado = jugadore.getInt(0);
        Toast.makeText(this, position + " seleccionado - id:" + idJugadorSeleccionado, Toast.LENGTH_LONG).show();

    }
}
