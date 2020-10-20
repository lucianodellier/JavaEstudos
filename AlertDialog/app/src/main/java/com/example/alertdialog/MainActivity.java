package com.example.alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirDialog(View view){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configurar Titulo e Mensagem
        dialog.setTitle("Título da Dialog");
        dialog.setMessage("Mensagem da Dialog");

        //Configurar Cancelamento
        dialog.setCancelable(false);

        //Configurar Icone
        dialog.setIcon(android.R.drawable.ic_btn_speak_now);

        //Configurar ações para sim e não

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(
                        getApplicationContext(),
                        "Executar a ação ao clicar no botão sim",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(
                        getApplicationContext(),
                        "Executar a ação ao clicar no botão não",
                        Toast.LENGTH_LONG
                ).show();

            }
        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();

    }

}