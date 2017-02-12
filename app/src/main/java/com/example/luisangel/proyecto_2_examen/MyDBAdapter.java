package com.example.luisangel.proyecto_2_examen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class MyDBAdapter{//Clase auxiliar para conectar con la BBDD

    // Definiciones y constantes
    private static final String DATABASE_NAME = "mathdice.db";//Nombre de la BBDD
    private static final String DATABASE_TABLE = "jugador";//Nombre de la tabla 1
    private static final int DATABASE_VERSION = 1;// Versión de la base datos

    //Campos de las tablas
    private static final String NOMBRE = "nombre";
    private static final String NICK = "nick";
    private static final String PUNTOS = "puntos";

    //Variables para la creación de la BBDD
    private static final String DATABASE_CREATE = "CREATE TABLE "+DATABASE_TABLE+" (_id integer primary key autoincrement, nombre text, nick text, puntos int);";
    private static final String DATABASE_DROP= "DROP TABLE IF EXISTS "+DATABASE_TABLE+";";

    //Cursor para consultar la BBDD
    private Cursor cursor;

    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    private MyDBAdapter dbAdapter;

    Jugador jugador = new Jugador();




    public MyDBAdapter(Context c){
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Abrimos la base de datos
    public void open(){

        try{
            db = dbHelper.getWritableDatabase();//abrirla BBDD de lectura y escritura

        }catch(SQLiteException e){
            //Por si falla
            db = dbHelper.getReadableDatabase();//abrirla BBDD solo lectura
        }

    }

    //MÉTODO DE CONSULTA DE LA BBDD
    public ArrayList<String> recuperarnombre(String nick){
        ArrayList<String> nombre = new ArrayList<String>();

            //Recuperamos en un cursor la consulta realizada
        cursor = db.rawQuery("SELECT * FROM jugador WHERE nick ="+"'"+nick+"'",null);

        //Recorremos el cursor
        if (cursor != null){
            cursor.moveToFirst();}
        do{
            nombre.add(cursor.getString(1));
        }while (cursor.moveToNext());
        return nombre;
}
    public ArrayList<String> recuperarpuntos(String nick){
        ArrayList<String> puntos = new ArrayList<String>();

        //Recuperamos en un cursor la consulta realizada
        cursor = db.rawQuery("SELECT * FROM jugador WHERE nick ="+"'"+nick+"'",null);

        //Recorremos el cursor
        if (cursor != null){
            cursor.moveToFirst();}
        do{
            puntos.add(cursor.getString(3));
        }while (cursor.moveToNext());
        return puntos;
    }

    public ArrayList<String> recuperaralias(String nick){
        ArrayList<String> alias = new ArrayList<String>();

        //Recuperamos en un cursor la consulta realizada
        cursor = db.rawQuery("SELECT * FROM jugador WHERE nick ="+"'"+nick+"'",null);

        //Recorremos el cursor
        if (cursor != null){
            cursor.moveToFirst();}
        do{
            alias.add(cursor.getString(2));
        }while (cursor.moveToNext());
        return alias;
    }

    public void modificar(String alias,int id){
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo con el método put de la clase ContentValues
        newValues.put(PUNTOS,id);
        db.update(DATABASE_TABLE, newValues, "nick="+"'"+alias+"'", null);
    }

    //INSERTAMOS LOS VALORES
    public void insertarAlumno(String a, String b,int c){
        //Creamos un nuevo registro de valores a insertar
        //Utilizamos los métodos de la clase ContentValues para representar una única fila de una
        //tabla emparejando los nombres de las columnas con los valores que queremos insertar
        //No es necesario especificar el tipo de valor
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo con el método put de la clase ContentValues
        newValues.put(NOMBRE,a);
        newValues.put(NICK,b);
        newValues.put(PUNTOS,c);

        db.insert(DATABASE_TABLE,null,newValues);//para insertar los valores en la BBDD le pasamos la tabla y un ContentValues

    }

    //BORRAMOS LOS VALORES
    public void borrarAlumno(String b){
        db.delete("jugador", b, null);//

    }

    //SQLiteOpenHelper abre la base de datos si exite o crearla sino existe
    private static class MyDbHelper extends SQLiteOpenHelper {//Definimos la clase MyDbHelper que extiende de SQLieteOpenHelper

            public MyDbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
                super(context,name,factory,version);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {//Creación de la base de datos
                db.execSQL(DATABASE_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//Actualización de la base de datos
                db.execSQL(DATABASE_DROP);
                onCreate(db);
            }

    }



}