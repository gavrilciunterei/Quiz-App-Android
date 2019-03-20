package com.example.diurno.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by diurno on 18/04/18.
 */

public class Pregunta implements Parcelable {

    public static final String DIFICULTAD_FACIL = "Facil";
    public static final String DIFICULTAD_MEDIA = "Media";
    public static final String DIFICULTAD_DIFICIL = "Dificil";

    private String pregunta;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private int numero_respuesta;
    private String dificultad;


    public Pregunta() {
    }

    public Pregunta(String pregunta, String opcion1, String opcion2, int numero_respuesta, String dificultad) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.numero_respuesta = numero_respuesta;
        this.dificultad = dificultad;

    }

    public Pregunta(String pregunta, String opcion1, String opcion2, String opcion3, int numero_respuesta, String dificultad) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.numero_respuesta = numero_respuesta;
        this.dificultad = dificultad;

    }

    protected Pregunta(Parcel in) {
        pregunta = in.readString();
        opcion1 = in.readString();
        opcion2 = in.readString();
        opcion3 = in.readString();
        numero_respuesta = in.readInt();
        dificultad = in.readString();

    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pregunta);
        dest.writeString(opcion1);
        dest.writeString(opcion2);
        dest.writeString(opcion3);
        dest.writeInt(numero_respuesta);
        dest.writeString(dificultad);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pregunta> CREATOR = new Creator<Pregunta>() {
        @Override
        public Pregunta createFromParcel(Parcel in) {
            return new Pregunta(in);
        }

        @Override
        public Pregunta[] newArray(int size) {
            return new Pregunta[size];
        }
    };

    public String getPregunta() {

        return pregunta;
    }

    public void setPregunta(String pregunta) {

        this.pregunta = pregunta;
    }

    public String getopcion1() {

        return opcion1;
    }

    public void setopcion1(String opcion1) {

        this.opcion1 = opcion1;
    }

    public String getopcion2() {
        return opcion2;
    }

    public void setopcion2(String opcion2) {

        this.opcion2 = opcion2;
    }

    public String getopcion3() {

        return opcion3;
    }

    public void setopcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public int getnumero_respuesta() {

        return numero_respuesta;
    }

    public void setnumero_respuesta(int numero_respuesta) {
        this.numero_respuesta = numero_respuesta;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public static String[] dificultades(){
        return new String[]{
                DIFICULTAD_FACIL,
                DIFICULTAD_MEDIA,
                DIFICULTAD_DIFICIL
        };
    }

}