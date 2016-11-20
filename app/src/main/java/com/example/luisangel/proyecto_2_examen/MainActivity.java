package com.example.luisangel.proyecto_2_examen;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.luisangel.splashimage.R;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    ListView v1; //Creamos variable ListView para poder utilizarla en todo el Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = (ListView) findViewById(R.id.listaNombres); //asociamos la variable creada anteriormente a ListView listaNombres creado en actity_main


        String[] datos = new String[]{"PERFIL","JUEGO","INSTRUCCIONES","INFORMACIÓN"}; //String de datos donde introducimos los nombres

        ArrayList<String> lista = new ArrayList<>(Arrays.asList(datos));

       // ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, datos); //asociamos String de datos al array

        MenuAdapter adapter = new MenuAdapter(this,lista);


        //INTERFAZ o VISIÓN
        final ListView listView = (ListView) findViewById(R.id.listaNombres);

        listView.setAdapter(adapter); //asociamos el ListView con los datos del array


    }

}
