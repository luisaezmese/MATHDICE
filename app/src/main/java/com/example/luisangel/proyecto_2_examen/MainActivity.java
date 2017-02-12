package com.example.luisangel.proyecto_2_examen;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.example.luisangel.splashimage.R;



public class MainActivity extends AppCompatActivity implements MenuFragment.MenuFragmentListener, PerfilFragment.buttonListener{

   //Definimos un objeto de la clase jugador
    public Jugador jugador=new Jugador();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bienvenida();
    }

    public void Bienvenida(){

        //Declaramos nuevo Fragment para mostrar
        DinamicFragment fragment1 = new DinamicFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenido, fragment1).commit();

    }

    @Override

    //Acciones a realizar al hacer click en cada elemento de la lista según su posición
   public void onListSelected(int position, String item) {
        switch (position) {
            case 0:
                //Si realizamos click en la opción 1=PERFIL cargamos PerfilFragment para introducir los datos del jugador
                PerfilFragment perfil = new PerfilFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenido, perfil).commit();
                break;
            case 1:
                //Cargamos el Fragment del juego pasando el jugador y puntos
                JuegoFragment NuevoJuego = new JuegoFragment();
                Bundle args = new Bundle();
                args.putString(JuegoFragment.ARG_ALIAS, jugador.getNick());
                args.putString(JuegoFragment.ARG_PUNTOS, String.valueOf(jugador.getPuntos()));
                NuevoJuego.setArguments(args);//Encapsulamos dentro del Bundle
                //Capturamos el cargador dinámico y abrimos el Fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.contenido, NuevoJuego).commit();
                break;
            case 2:
                MusicFragment music = new MusicFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenido, music).commit();
                break;
            case 3:
                Toast.makeText(this, item, Toast.LENGTH_LONG).show();
                break;
        }

    }


    @Override
    public void onClickButton(String nombre, String nick, int puntos) {
        jugador.setNombre(nombre); //Pasamos en nombre introducido
        jugador.setNick(nick);// Pasamos el nick introducido
        jugador.setPuntos(puntos);// Pasamos el puntos introducido
        //Sacamos mensaje con nombre jugador y nickname
        Toast.makeText(this, "Bienvenido :"+jugador.getNombre()+", con NickName :"+jugador.getNick(),Toast.LENGTH_LONG).show();
        //Vamos a la pantalla inicial e Bienvenida
        Bienvenida();
    }
}
