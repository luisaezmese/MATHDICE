package com.example.luisangel.proyecto_2_examen;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luisangel.splashimage.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    buttonListener mButton; //Comunicar el Fragment con la Activity principal
    String nombreUsuario; //Guardar nombre del usuario
    String apodoUsuario; //Guardar nick del usuario
    int puntosusuario;
    Context context;
    private MyDBAdapter dbAdapter;//Llamamos a la clase MyDBAdapter del MainActivity

    private ArrayList<String> arrayListalias;
    private ArrayList<String> arrayListnombre;
    private ArrayList<String> arrayListpuntos;
    Jugador jugador = new Jugador();

    //Listener sobre el botón para guardar los datos introducidos y pasarselos a la Actividad principal
    public interface buttonListener{
        public void onClickButton(String nombre, String nick, int puntos);

    }


    //CONSTRUCTOR
    public PerfilFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_perfil, container, false);

        context= this.getActivity();
        dbAdapter = new MyDBAdapter(v.getContext());//Instanciamos un objeto del tipo MyDBAdapter

        //Declaramos los EditText donde se recogen los datos introducidos
        //Declaramos el boton para guardar esos datos

        final EditText nombre = (EditText) v.findViewById(R.id.editTextNombre);
        final EditText nick = (EditText) v.findViewById(R.id.editTextNick);
        final Button guardar = (Button) v.findViewById(R.id.buttonGuardar);
        final Button recuperar = (Button) v.findViewById(R.id.buttonRecuperar);

        //Acción al realizar clik en el botón GUARDAR

        guardar.setOnClickListener(
                new View.OnClickListener() {
                        @Override
                        public void onClick (View v){
                        //Guardar los valores introducidos
                        nombreUsuario = nombre.getText().toString();
                        apodoUsuario = nick.getText().toString();
                        mButton.onClickButton(nombreUsuario, apodoUsuario,0); //Pasamos nombre y nick al mButton para utilizarlos posteriormente

                        dbAdapter.open();//abrimos la BBDD si ya existe o crearla sino existe
                        dbAdapter.insertarAlumno(nombreUsuario,apodoUsuario,0);//Insertamos los  datos en la BBDD
                        }
                }
        );

        //Acción al realizar clik en el botón RECUPERAR

        recuperar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick (View v){

                        String apodoUsuario1 = nick.getText().toString();
                        Toast.makeText(context,"Apodo: "+apodoUsuario1, Toast.LENGTH_SHORT).show();
                        dbAdapter.open();//abrimos la BBDD si ya existe o crearla sino existe
                        arrayListalias=dbAdapter.recuperaralias(apodoUsuario1);
                        arrayListnombre=dbAdapter.recuperarnombre(apodoUsuario1);
                        arrayListpuntos=dbAdapter.recuperarpuntos(apodoUsuario1);
                        jugador.setNombre(arrayListnombre.get(0));
                        jugador.setNick(arrayListalias.get(0));
                        jugador.setPuntos(Integer.parseInt(arrayListpuntos.get(0)));
                        nombreUsuario = jugador.getNombre();
                        apodoUsuario=jugador.getNick();
                        puntosusuario=jugador.getPuntos();
                        mButton.onClickButton(nombreUsuario, apodoUsuario,puntosusuario);
                    }
                }
        );

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mButton = (buttonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
