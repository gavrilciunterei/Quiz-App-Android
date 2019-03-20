package com.example.diurno.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {

    private Spinner spinner;

    private static final int REQUEST_CODE_QUIZ = 1;
    private static final String PREFERENCIAS = "preferenciqs";
    private static final String MAYOR_PUNTUACION = "puntuacion";
    public static final String DIFICULTAD_EXTRA = "dificultad";

    private TextView textViewHPuntuacion;
    private int mayor_puntuacionn;

    private Button buttonStart, buttonNormal, buttonTrofeos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportActionBar().hide();
        buttonStart = (Button) findViewById(R.id.btn_tiempo);
        buttonStart.setOnClickListener(new StartTiempo());
        buttonNormal = (Button)findViewById(R.id.btn_normal);
        buttonNormal.setOnClickListener(new StartNormal());
        buttonTrofeos = (Button)findViewById(R.id.btn_trofeos);
        buttonTrofeos.setOnClickListener(new StartTrofeos());

        textViewHPuntuacion = findViewById(R.id.text_view_highscore);
        loadHighscore();

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_gav,opciones());
        spinner.setAdapter(adapter);




    }

    private class StartTiempo implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startQuizTiempo();
        }
    }

    private class StartNormal implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startQuiz();
        }
    }

/*    public void ComprobarPuntuación(){

        if(mayor_puntuacionn == 3) {
            Intent intent1 = new Intent(getApplicationContext(), Inicio.class);
            notificar(intent1);
        }
    }*/



    private class StartTrofeos implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(Inicio.this, VerTrofeos.class);
            intent.putExtra("mayor_puntuacionn",mayor_puntuacionn);
            startActivity(intent);


        }
    }



    public void startQuizTiempo() {
        String dificultad = spinner.getSelectedItem().toString();

        Intent intent = new Intent(this, M_Tiempo.class);
        intent.putExtra(DIFICULTAD_EXTRA, dificultad);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }

    public void startQuiz() {
        String dificultad = spinner.getSelectedItem().toString();

        Intent intent = new Intent(this, M_Normal.class);
        intent.putExtra(DIFICULTAD_EXTRA, dificultad);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(M_Tiempo.EXTRA_SCORE, 0);
                if (score > mayor_puntuacionn) {
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        mayor_puntuacionn = prefs.getInt(MAYOR_PUNTUACION, 0);
        textViewHPuntuacion.setText("Mejor puntuacuón: " + mayor_puntuacionn);
    }

    private void updateHighscore(int highscoreNew) {
        mayor_puntuacionn = highscoreNew;
        textViewHPuntuacion.setText("Mejor puntuacuón: : " + mayor_puntuacionn);

        SharedPreferences prefs = getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MAYOR_PUNTUACION, mayor_puntuacionn);
        editor.apply();
    }


    //Spinner
    private  String[]  opciones()  {
        String [] valores= {"Facil","Media","Dificil"};
        return valores  ;
    }


   /* private void notificar(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(Inicio.this
                , 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(Inicio.this);
        Notification notification;
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle("Infinite Quiz")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(Inicio.this.getResources()
                        , R.mipmap.ic_launcher))
                .setContentText("¡Felicidades, has acertado 3 preguntas!")
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) Inicio.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }*/

}
