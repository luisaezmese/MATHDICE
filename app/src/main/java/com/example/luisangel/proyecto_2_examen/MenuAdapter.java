package com.example.luisangel.proyecto_2_examen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisangel.splashimage.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class MenuAdapter extends ArrayAdapter{

    private Context context;
    private ArrayList<String> datos;

    public MenuAdapter(Context context, ArrayList<String> datos) {
        super(context,0, datos);
        this.context=context;
        this.datos=datos;
    }
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_menu,parent,false);
        }
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imageView2);
        TextView texto = (TextView) convertView.findViewById(R.id.textView);

        texto.setText(datos.get(position));

        switch (position){
            case 0:
                imagen.setImageDrawable(context.getDrawable(R.drawable.ic_accessibility_black_24dp));
                texto.setBackgroundColor(Color.LTGRAY);
                imagen.setBackgroundColor(Color.MAGENTA);
                break;
            case 1:
                imagen.setImageDrawable(context.getDrawable(R.drawable.ic_bug_report_black_24dp));
                texto.setBackgroundColor(Color.MAGENTA);
                imagen.setBackgroundColor(Color.CYAN);
                break;
            case 2:
                imagen.setImageDrawable(context.getDrawable(R.drawable.ic_assignment_black_24dp));
                texto.setBackgroundColor(Color.CYAN);
                imagen.setBackgroundColor(Color.BLUE);
                break;
            case 3:
                imagen.setImageDrawable(context.getDrawable(R.drawable.ic_lightbulb_outline_black_24dp));
                texto.setBackgroundColor(Color.BLUE);
                imagen.setBackgroundColor(Color.LTGRAY);
                break;
            default:
                imagen.setImageResource(R.drawable.imagen2);
                break;
        }

        return convertView;
    }

}
