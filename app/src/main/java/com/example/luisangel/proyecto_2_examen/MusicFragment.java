package com.example.luisangel.proyecto_2_examen;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.luisangel.splashimage.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
   //   private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Control de volumen
    private int mVolume = 6, mVolumeMax = 10, mVolumeMin = 0;
    //Control sonando
    private int sonando = 0; //0=cancion no comenzada; 1=comenzada; 2=pause;
    private MediaPlayer mPlayer;
    private int mSoundId;
    AudioManager mAudioManager;
    private boolean mCanPlayAudio;
    private static final String TAG = "ACTIVITY: ";
    Context context;

    private OnFragmentInteractionListener mListener;

    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static MusicFragment newInstance(String param1, String param2) {
        /*MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context= this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_music, container, false);


        // Capturamos el servicio que nos proporciona manejar Sonidos
        mAudioManager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

        // Volumen actual programado
        final TextView tv = (TextView) v.findViewById(R.id.textView1);
        tv.setText(String.valueOf(mVolume));

        // Subir volumen
        final Button upButton = (Button) v.findViewById(R.id.button2);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Hacer sonar el efecto de click
                mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, mVolume);
                if (mVolume < mVolumeMax) {
                    mVolume += 2;
                    tv.setText(String.valueOf(mVolume));
                    //Cargamos el valor del volumen en los dos canales, lo que permite control
                    //del volumen en tiempo de ejecución de la cancion.
                    mPlayer.setVolume((float) mVolume / mVolumeMax,
                            (float) mVolume / mVolumeMax);
                }
            }
        });

        // Bajar Volumne
        final Button downButton = (Button) v.findViewById(R.id.button1);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Hacer sonar el efecto de click
                mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, mVolume);

                if (mVolume > mVolumeMin) {
                    mVolume -= 2;
                    tv.setText(String.valueOf(mVolume));
                }
                //Cargamos el valor del volumen en los dos canales, lo que permite control
                //del volumen en tiempo de ejecución de la cancion.
                mPlayer.setVolume((float) mVolume / mVolumeMax,
                        (float) mVolume / mVolumeMax);
            }
        });

        // Desactivamos el boton del play
        final Button playButton = (Button) v.findViewById(R.id.button3);
        playButton.setEnabled(true);

        mPlayer = MediaPlayer.create(getActivity(), R.raw.juegodetronos);

        //Comprobamos que se ha cargado la canción y entonces activamos boton play
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                          @Override
                                          public void onPrepared(MediaPlayer mp) {
                                              Log.d("AUDIO", "Cargada la cancion");
                                              playButton.setEnabled(true);
                                          }
                                      }
        );

        // Suena la cancion
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonando == 0) {
                    playButton.setText("||");
                    sonando = 1;
                    if (mCanPlayAudio)

                        mPlayer.start();
                } else if (sonando == 1) {
                    playButton.setText("Play");
                    sonando = 2;
                    mPlayer.pause();

                } else {
                    playButton.setText("||");
                    sonando = 1;
                    mPlayer.start();

                }
            }

        });
        int result = mAudioManager.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        mCanPlayAudio = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;

        return v;

    }

    // Get ready to play sound effects
    @Override
    public void onResume() {
        Log.d(TAG, "VOLVIENDO A TOCAR");
        //Como en onPause hemos liberado recursos. Se debería volver a cargar la canción
        //en onResume porqué sino daría error
        super.onResume();
        mAudioManager.setSpeakerphoneOn(true);
        mAudioManager.loadSoundEffects();
    }

    // Release resources & clean up
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "EN PAUSA");
        //Liberamos recursos
        mPlayer.release();

        mAudioManager.setSpeakerphoneOn(false);
        mAudioManager.unloadSoundEffects();
    }
    // Listen for Audio focus changes
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mAudioManager.abandonAudioFocus(afChangeListener);
                mCanPlayAudio = false;
            }
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
