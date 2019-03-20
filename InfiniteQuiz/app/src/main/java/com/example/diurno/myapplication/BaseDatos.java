package com.example.diurno.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.example.diurno.myapplication.TablasyColumnas.*;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "InfiniteQuiz4.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.NOMBRE_TABLA + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMNA_PREGUNTA + " TEXT, " +
                QuestionsTable.COLUMNA_OPCION1 + " TEXT, " +
                QuestionsTable.COLUMNA_OPCION2 + " TEXT, " +
                QuestionsTable.COLUMNA_OPCION3 + " TEXT, " +
                QuestionsTable.COLUMNA_NUMERO_RESSPUESTA + " INTEGER, " +
                QuestionsTable.COLUMNA_DIFICULTAD + " TEXT  "+
                ")";




        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        llenarTablaPreguntas();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.NOMBRE_TABLA);
        onCreate(db);
    }



    private void llenarTablaPreguntas() {

        Pregunta q1 = new Pregunta("¿Cuál de las siguientes declaraciones de String es correcta?", "String s = \"esto es un String\";", "string s = \"esto es un string\"", "String s = 'esto es un String'", 1, Pregunta.DIFICULTAD_FACIL);
        anadirPregunta(q1);
        Pregunta q2 = new Pregunta("¿Es correcta la siguiente sentencia?\n" +
                "char caracter = 'x';", "No, es incorrecta.", "Sí, es correcta.", 2, Pregunta.DIFICULTAD_FACIL);
        anadirPregunta(q2);
        Pregunta q3 = new Pregunta("¿Qué se imprimirá en la consola al acabar la ejecución del siguiente programa?\n" +
                "int a = 2, b = 3, x\n"+ "x = 3/2;\n"+"System.out.println(x);", "1,5", "0,5", "1", 3, Pregunta.DIFICULTAD_FACIL);
        anadirPregunta(q3);
        Pregunta q4 = new Pregunta("int, char, float, string y boolean son" , "Tipos de datos", "Instrucciones de acceso a datos", "Sentencias de control ", 1, Pregunta.DIFICULTAD_FACIL);
        anadirPregunta(q4);


        Pregunta q6 = new Pregunta("Pregunta 1: B es correcto", "A", "B", "C", 2, Pregunta.DIFICULTAD_MEDIA);
        anadirPregunta(q6);
        Pregunta q7 = new Pregunta("Pregunta 2: C es correcto", "A", "B", "C", 3, Pregunta.DIFICULTAD_MEDIA);
        anadirPregunta(q7);
        Pregunta q8 = new Pregunta("Pregunta 3: A es correcto", "A", "B", "C", 1, Pregunta.DIFICULTAD_MEDIA);
        anadirPregunta(q8);
        Pregunta q9 = new Pregunta("Pregunta 4: B es correcto", "A", "B", "C", 2, Pregunta.DIFICULTAD_MEDIA);
        anadirPregunta(q9);
        Pregunta q10 = new Pregunta("Pregunta 5: A es correcto", "A", "B", "C", 1, Pregunta.DIFICULTAD_MEDIA);
        anadirPregunta(q10);
        Pregunta q11 = new Pregunta("Pregunta 6: B es correcto", "A", "B", "C", 2, Pregunta.DIFICULTAD_MEDIA);
        anadirPregunta(q11);


        Pregunta q12 = new Pregunta("Pregunta 1: B es correcto", "A", "B", "C", 2, Pregunta.DIFICULTAD_DIFICIL);
        anadirPregunta(q12);
        Pregunta q13 = new Pregunta("Pregunta 2: C es correcto", "A", "B", "C", 3, Pregunta.DIFICULTAD_DIFICIL);
        anadirPregunta(q13);
        Pregunta q14 = new Pregunta("Pregunta 3: A es correcto", "A", "B", "C", 1, Pregunta.DIFICULTAD_DIFICIL);
        anadirPregunta(q14);
        Pregunta q15 = new Pregunta("Pregunta 4: B es correcto", "A", "B", "C", 2, Pregunta.DIFICULTAD_DIFICIL);
        anadirPregunta(q15);
        Pregunta q16 = new Pregunta("Pregunta 5: A es correcto", "A", "B", "C", 1,  Pregunta.DIFICULTAD_DIFICIL);
        anadirPregunta(q16);
        Pregunta q17 = new Pregunta("Pregunta 6: B es correcto", "A", "B", "C", 2, Pregunta.DIFICULTAD_DIFICIL);
        anadirPregunta(q17);

    }

    private void anadirPregunta(Pregunta pregunta) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMNA_PREGUNTA, pregunta.getPregunta());
        cv.put(QuestionsTable.COLUMNA_OPCION1, pregunta.getopcion1());
        cv.put(QuestionsTable.COLUMNA_OPCION2, pregunta.getopcion2());
        cv.put(QuestionsTable.COLUMNA_OPCION3, pregunta.getopcion3());
        cv.put(QuestionsTable.COLUMNA_NUMERO_RESSPUESTA, pregunta.getnumero_respuesta());
        cv.put(QuestionsTable.COLUMNA_DIFICULTAD, pregunta.getDificultad());

        db.insert(QuestionsTable.NOMBRE_TABLA, null, cv);
    }


    public ArrayList<Pregunta> getPreguntas(String dificultad) {
        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{dificultad};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.NOMBRE_TABLA +
                " WHERE " + QuestionsTable.COLUMNA_DIFICULTAD + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Pregunta pregunta = new Pregunta();
                pregunta.setPregunta(c.getString(c.getColumnIndex(QuestionsTable.COLUMNA_PREGUNTA)));
                pregunta.setopcion1(c.getString(c.getColumnIndex(QuestionsTable.COLUMNA_OPCION1)));
                pregunta.setopcion2(c.getString(c.getColumnIndex(QuestionsTable.COLUMNA_OPCION2)));
                pregunta.setopcion3(c.getString(c.getColumnIndex(QuestionsTable.COLUMNA_OPCION3)));
                pregunta.setnumero_respuesta(c.getInt(c.getColumnIndex(QuestionsTable.COLUMNA_NUMERO_RESSPUESTA)));
                pregunta.setDificultad(c.getString(c.getColumnIndex(QuestionsTable.COLUMNA_DIFICULTAD)));

                listaPreguntas.add(pregunta);
            } while (c.moveToNext());
        }

        c.close();
        return listaPreguntas;
    }
}