package com.example.bancodedadossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            //Cria Banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app",MODE_PRIVATE, null);

            //Criar tabela
            //bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (nome VARCHAR, idade INT(3) ) ");
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, idade INT(3) ) ");
            //bancoDados.execSQL("DROP TABLE pessoas");

            //Inserir dados
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Luciano', 29) ");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Maria', 35) ");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Mariana', 18) ");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Pedro', 50) ");
            bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Pedro', 50) ");
            //bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Mario', 40) ");
            bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES('Mario', 40) ");

            //Atualização dedados (UPDATE)
            bancoDados.execSQL("UPDATE pessoas SET idade = 19, nome = 'Mariana Silva' WHERE nome = 'Mariana' ");
            //bancoDados.execSQL("UPDATE pessoas SET idade = 19, nome = 'Mariana Silva' WHERE id = 3");

            //Deleta a informação pelo id
            //bancoDados.execSQL("DELETE FROM pessoas WHERE id = 2");
            //Limpa os dados da tabela mas mantem ela criada
            //bancoDados.execSQL("DELETE FROM pessoas );

            //Recuperar pessoas
            /*String consulta = "SELECT nome, idade FROM pessoas" +
                              "WHERE nome = 'Luciano' AND idade = 29";*/

            /*String consulta = "SELECT nome, idade FROM pessoas " +
                              "WHERE idade >=35 OR idade = 18";*/

            /*String consulta = "SELECT nome, idade FROM pessoas " +
                              "WHERE nome IN('Luciano','Maria')";*/

            /*String consulta = "SELECT nome, idade FROM pessoas " +
                              "WHERE idade BETWEEN 29 AND 35";*/
            /* String filtro = "mar"
            String consulta = "SELECT nome, idade FROM pessoas " +
                              "WHERE nome LIKE ' %'+ filtro +'% ' ";*/

            /*String consulta = "SELECT nome, idade FROM pessoas " +
                              "WHERE 1=1 ORDER BY idade ASC LIMIT 3 ";*/

            String consulta = "SELECT * FROM pessoas " +
                              "WHERE 1=1 " ;

            Cursor cursor = bancoDados.rawQuery(consulta, null);

            //Indicies da tabela
            int indicieId = cursor.getColumnIndex("id");
            int indicieNome = cursor.getColumnIndex("nome");
            int indicieIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst();
            while (cursor != null){

                String nome = cursor.getString(indicieNome);
                String idade = cursor.getString(indicieIdade);
                String id = cursor.getString(indicieId);

                Log.i("Resultado - id ", id + "/ nome "+ nome + " / idade: " + idade);

                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}