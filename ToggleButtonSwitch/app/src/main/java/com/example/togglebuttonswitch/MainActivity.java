package com.example.togglebuttonswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleSenha;
    private Switch switchSenha;
    private TextView textResultado;
    private CheckBox checkSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleSenha     = findViewById(R.id.toggleSenha);
        switchSenha     = findViewById(R.id.switchSenha);
        textResultado   = findViewById(R.id.textResultado);
        checkSenha      = findViewById(R.id.checkSenha);

        adicionaListener();
    }

    public void adicionaListener(){

        //toggleSenha
        //checkSenha
        switchSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    textResultado.setText("ligado");
                }else{
                    textResultado.setText("Desligado");
                }
            }
        });


    }

    public void enviar(View view){

        //if(checkSenha.isChecked())
        //if(toggleSenha.isChecked())
        if(switchSenha.isChecked()){
            textResultado.setText("Switch ligado");
        }else{
            textResultado.setText("Switch Desligado");
        }

    }

}