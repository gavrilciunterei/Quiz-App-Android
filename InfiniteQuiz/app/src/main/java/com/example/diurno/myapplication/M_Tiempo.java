package com.example.diurno.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class M_Tiempo extends AppCompatActivity {

    public static final String EXTRA_SCORE="extraScore";
    private  static final long CUENTA_ATRAS= 30000;

    private static final String CLAVE_PUNTUACION = "clavePuntuacion";
    private static final String CLAVE_CONTADOR_PREGUNTAS = "claveContadorPreguntas";
    private static final String CLAVE_TIEMPO="claveTiempo";
    private static final String CLAVE_CONTESTADAS ="claveContestadas";
    private static final String CLAVE_LISTA_PREGUNTAS = "claveListaPReguntas";


    private TextView textViewPregunta;
    private TextView textViewPuntuacion;
    private TextView textViewPreguntaContador;
    private TextView textViewCuentaAtras;
    private TextView textViewDificultad;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmarSiguiente;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private ArrayList<Pregunta> ListaPregunta;
    private int contadorPreguntas;
    private int contadorPreguntasTotales;
    private Pregunta preguntaActual;

    private int puntuacion;
    private boolean contestada;
    private long backPressedTime;

    private CountDownTimer cuentaAtras;
    private long tiempoFalta;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_tiempo);


        /*Aqui unimos los elementos que hemos creado arriba con los de la vista*/
        textViewPregunta = findViewById(R.id.text_view_question);
        textViewPuntuacion = findViewById(R.id.text_view_puntuacion);
        textViewPreguntaContador = findViewById(R.id.text_view_question_count);
        textViewCuentaAtras = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmarSiguiente = findViewById(R.id.button_confirm_next);
        textViewDificultad = findViewById(R.id.textViewDificultad);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCuentaAtras.getTextColors();
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String dificultad = intent.getStringExtra(Inicio.DIFICULTAD_EXTRA);

        textViewDificultad.setText(dificultad);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);




        if(savedInstanceState == null) {
            BaseDatos dbHelper = new BaseDatos(this);
            ListaPregunta = dbHelper.getPreguntas(dificultad);
            contadorPreguntasTotales = ListaPregunta.size();
            Collections.shuffle(ListaPregunta);

            verSiguientePregunta();
        }else{
            ListaPregunta = savedInstanceState.getParcelableArrayList(CLAVE_LISTA_PREGUNTAS);
            contadorPreguntasTotales = ListaPregunta.size();
            contadorPreguntas = savedInstanceState.getInt(CLAVE_CONTADOR_PREGUNTAS);
            preguntaActual = ListaPregunta.get(contadorPreguntas -1);
            puntuacion = savedInstanceState.getInt(CLAVE_PUNTUACION);
            tiempoFalta = savedInstanceState.getLong(CLAVE_TIEMPO);
            contestada = savedInstanceState.getBoolean(CLAVE_CONTESTADAS);

            if(!contestada) {
                empezarCuentaAtras();
            }else{
                actualizarTextoTiempo();
                verSolucion();
            }
        }

        buttonConfirmarSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (!contestada) {
                    /*Si hay una de ellas chequeada, llamamos al metodo comprobarRespuesta*/
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        comprobarRespuesta();
                    } else {
                        /*Si no hay ninguna chequeada da el siguiente mensaje en la parte inferior*/
                        Toast.makeText(M_Tiempo.this, "Porfavor, seleccione una respuesta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    /*sino siguiente pregunta*/
                    verSiguientePregunta();
                }
            }
        });
    }

    private void verSiguientePregunta() {
        /*Colores por defecto*/
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        /*Todos los checks deseleccionados*/
        rbGroup.clearCheck();

        /*Si el contador es menos que el numero total de preguntas*/
        if (contadorPreguntas < contadorPreguntasTotales) {
            /*Seteamos la pregunta actual con una de la lista */
            preguntaActual = ListaPregunta.get(contadorPreguntas);

            textViewPregunta.setText(preguntaActual.getPregunta());
            /*Añadimos las opciones en los Radio Button */
            rb1.setText(preguntaActual.getopcion1());
            rb2.setText(preguntaActual.getopcion2());
            rb3.setText(preguntaActual.getopcion3());

            contadorPreguntas++;
            /*Total de preguntas que aparece (4/3 preguntas respsondidas*/
            textViewPreguntaContador.setText("Pregunta: " + contadorPreguntas + "/" + contadorPreguntasTotales);
            contestada = false;
            /*Botón siguiente pregunta*/
            buttonConfirmarSiguiente.setText("Confirmar");

            tiempoFalta = CUENTA_ATRAS;
            empezarCuentaAtras();

            progressBar.setMax(contadorPreguntasTotales);
            progressBar.setProgress(contadorPreguntas);





        } else {
            /*Si el contador = total preguntas finaliza*/
            finalizarPrograma();
        }
    }

    private void empezarCuentaAtras(){
        cuentaAtras = new CountDownTimer(tiempoFalta, 1000) {
            @Override
            public void onTick(long l) {
                tiempoFalta = l;
                actualizarTextoTiempo();
            }

            @Override
            public void onFinish() {
              tiempoFalta = 0;
              actualizarTextoTiempo();
              comprobarRespuesta();
            }
        }.start();
    }


    private void actualizarTextoTiempo(){
        int minutos = (int) (tiempoFalta / 1000) / 60;
        int segundos = (int) (tiempoFalta / 1000) % 60;

        String formatoTiempo = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
        textViewCuentaAtras.setText(formatoTiempo);

        // aqui comprobamos cuanto tiempo queda, si es menor de 10 segundos el color se pone en rojo
        if(tiempoFalta < 10000 ){
            textViewCuentaAtras.setTextColor(Color.RED);
        }else {
            textViewCuentaAtras.setTextColor(textColorDefaultCd);
        }
    }

    private void comprobarRespuesta() {
        contestada = true;

        cuentaAtras.cancel();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == preguntaActual.getnumero_respuesta()) {
            puntuacion++;
            textViewPuntuacion.setText("Puntuación: " + puntuacion);
        }else{
            v.vibrate(500);
        }

        verSolucion();
    }

    private void verSolucion() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (preguntaActual.getnumero_respuesta()) {
            case 1:
                rb1.setTextColor(getResources().getColor(R.color.correcto));
                textViewPregunta.setText("Respuesta 1 es correcta");
                break;
            case 2:
                rb2.setTextColor(getResources().getColor(R.color.correcto));
                textViewPregunta.setText("Respuesta 2 es correcta");
                break;
            case 3:
                rb3.setTextColor(getResources().getColor(R.color.correcto));
                textViewPregunta.setText("Respuesta 3 es correcta");
                break;
        }

        if (contadorPreguntas < contadorPreguntasTotales) {
            buttonConfirmarSiguiente.setText("Siguiente");
        } else {
            buttonConfirmarSiguiente.setText("Terminar");
        }
    }

    private void finalizarPrograma() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, puntuacion);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finalizarPrograma();
        } else {
            Toast.makeText(this, "Pulsa otra vez para salir.", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cuentaAtras != null){
            cuentaAtras.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CLAVE_PUNTUACION, puntuacion);
        outState.putInt(CLAVE_CONTADOR_PREGUNTAS, contadorPreguntas);
        outState.putLong(CLAVE_TIEMPO, tiempoFalta);
        outState.putBoolean(CLAVE_CONTESTADAS, contestada);
        outState.putParcelableArrayList(CLAVE_LISTA_PREGUNTAS, ListaPregunta);
    }

}