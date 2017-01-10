package com.example.luisangel.proyecto_2_examen;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.example.luisangel.splashimage.R;



public class MainActivity extends AppCompatActivity implements MenuFragment.MenuFragmentListener{

    //ListView v1; //Creamos variable ListView para poder utilizarla en todo el Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaramos nuevo Fragment para mostrar
        DinamicFragment fragment1 = new DinamicFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenido, fragment1).commit();

    }

    @Override

    //Acciones a realizar al hacer click en cada elemento de la lista según su posición
   public void onListSelected(int position, String item) {
        switch (position) {
            case 0:
                Toast.makeText(this, item, Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this, item, Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, item, Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, item, Toast.LENGTH_LONG).show();
                break;
        }

    }
}
