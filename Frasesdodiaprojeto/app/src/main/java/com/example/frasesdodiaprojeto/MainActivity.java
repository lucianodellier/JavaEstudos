package com.example.frasesdodiaprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void geraNovaFrase(View view){

        String[] frases= {
                "Dor é a fraqueza deixando seu corpo!",
                "Lágrimas lhe trarão simpatia. Suor lhe trará resultados",
                "Louco é a palavra que os fracos usam para justificar a disciplina dos fortes.",
                "Seja mais forte que sua melhor desculpa."
        };

        int numero = new Random().nextInt(4);

        TextView texto = findViewById(R.id.textResultado);
        texto.setText(frases[numero]);

    }

}