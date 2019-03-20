package com.example.diurno.myapplication;

import android.provider.BaseColumns;

public final class TablasyColumnas {

    private TablasyColumnas(){}

    public static class QuestionsTable implements BaseColumns{

            public static final String NOMBRE_TABLA = "preguntas";
            public static final String COLUMNA_PREGUNTA = "pregunta";
            public static final String COLUMNA_OPCION1 = "opcion1";
            public static final String COLUMNA_OPCION2 = "opcion2";
            public static final String COLUMNA_OPCION3 = "opcion3";
            public static final String COLUMNA_NUMERO_RESSPUESTA = "numero_respuesta";
            public static final String COLUMNA_DIFICULTAD = "dificultad";
    }


}
