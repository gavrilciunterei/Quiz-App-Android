package com.example.diurno.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class VerTrofeos extends AppCompatActivity {

    private long backPressedTime;
    private CardView bankcardId, bankcardId1, bankcardId2, bankcardId3;
    private int mayor_puntuacionn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_trofeos);
        getSupportActionBar().hide();

        Bundle bundle=getIntent().getExtras();
        mayor_puntuacionn = bundle.getInt("mayor_puntuacionn");


        bankcardId = (CardView)findViewById(R.id.bankcardId);
        bankcardId1 = (CardView)findViewById(R.id.bankcardId1);
        bankcardId2 = (CardView)findViewById(R.id.bankcardId2);
        bankcardId3 = (CardView)findViewById(R.id.bankcardId3);

        comprobarVisiblidad();

    }

    private void comprobarVisiblidad(){

        if(mayor_puntuacionn < 1){
            bankcardId.setVisibility(View.INVISIBLE);
        }


        if(mayor_puntuacionn < 3){
            bankcardId1.setVisibility(View.INVISIBLE);
        }


        if(mayor_puntuacionn < 5){
            bankcardId2.setVisibility(View.INVISIBLE);
        }

        if(mayor_puntuacionn < 10){
            bankcardId3.setVisibility(View.INVISIBLE);
        }

    }



    private void finalizarPrograma() {
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

}
