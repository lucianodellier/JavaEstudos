package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    //private FirebaseAuth usuario = FirebaseAuth.getInstance();

    private ImageView imageFoto;
    private Button buttonUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpload = findViewById(R.id.buttonUpload);
        imageFoto = findViewById(R.id.imageFoto);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Configura para imagem ser salva em memoria
                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem (image a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //Comprimo bitmap para um formatop png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);

                //Converte o baos para pixel brutos em uma matriz de bytes
                // (dados da imagem)
                byte[] dadosImagem = baos.toByteArray();

                //Define nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");
                final StorageReference imagemRef = imagens.child("celular.jpeg");

                imagemRef.getDownloadUrl().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(MainActivity.this)
                                .load(uri)
                                .into(imageFoto);
                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                MainActivity.this,
                                "Erro ao fazer download: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

                /*imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                MainActivity.this,
                                "Erro ao deletar",
                                Toast.LENGTH_LONG).show();

                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(
                                MainActivity.this,
                                "Sucesso ao deletar",
                                Toast.LENGTH_LONG).show();
                    }
                });*/

                //Nome da imagem
                //String nomeArquivo = UUID.randomUUID().toString();
                //final StorageReference imagemRef = imagens.child("celular.jpeg");

                //Retorna objeto que irá controlar o upload
                /*UploadTask uploadTask = imagemRef.putBytes( dadosImagem );

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                MainActivity.this,
                                "Upload da imagem falhou: " + e.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri url = task.getResult();
                                Toast.makeText(
                                        MainActivity.this,
                                        "Sucesso ao fazer upload " + url.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });*/
            }
        });



        //DatabaseReference usuarios = referencia.child("usuarios");

        //DatabaseReference usuarioPesquisa = usuarios.child("-MHNBBox66XCuNGNlOo_");
        //Query usuarioPesquisa = usuarios.orderByChild("nome").equalTo("Marcelo");
        //Query usuarioPesquisa = usuarios.orderByKey().limitToLast(3);
        //Query usuarioPesquisa = usuarios.orderByKey().limitToLast(3);

        //Maior ou igual (>=)
        //Query usuarioPesquisa = usuarios.orderByChild("idade").startAt(35);

        //Menor ou igual (<=)
        //Query usuarioPesquisa = usuarios.orderByChild("idade").endAt(22);

        //Entre dois valores
        //Query usuarioPesquisa = usuarios.orderByChild("idade")
                                        //.startAt(18)
                                        //.endAt(22);

        //Filtrar palavras
        /*Query usuarioPesquisa = usuarios.orderByChild("nome")
                                        .startAt("J")
                                        .endAt("J" + "\uf8ff");

        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Usuario dadosUsuario = dataSnapshot.getValue(Usuario.class);
                //Log.i("Dados usuario: ", " nome: " + dadosUsuario.getNome() + " idade: " + dadosUsuario.getIdade() );
                Log.i("Dados usuario: ", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*Usuario usuario = new Usuario();
        usuario.setNome("Mariana");
        usuario.setSobrenome("Silvério");
        usuario.setIdade(45);

        Usuario usuario1 = new Usuario();
        usuario1.setNome("Marcelo");
        usuario1.setSobrenome("Farias");
        usuario1.setIdade(18);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Ana");
        usuario2.setSobrenome("Ribeiro");
        usuario2.setIdade(18);

        Usuario usuario3 = new Usuario();
        usuario3.setNome("Rodrigo");
        usuario3.setSobrenome("Matos");
        usuario3.setIdade(35);

        usuarios.push().setValue( usuario );
        usuarios.push().setValue( usuario1 );
        usuarios.push().setValue( usuario2 );
        usuarios.push().setValue( usuario3 );*/

        /*Deslogar usuario*/
        //usuario.signOut();

        /*Logar usuario
        usuario.signInWithEmailAndPassword(
                "ludellier@gmail.com",
                "lu12345")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("signIn", "Sucesso ao logar usuario!");
                        }else {
                            Log.i("signIn", "Erro ao logar usuario!");
                        }
                    }
                });*/

        /*Verifica usuario logado
        if( usuario.getCurrentUser() != null ){
            Log.i("CreateUser", "Usuario logado!" );
        }else {
            Log.i("CreateUser", "Usuario nao logado!" );
        }*/

        /*//Cadastro de usuario
        usuario.createUserWithEmailAndPassword(
                "ludellier@gmail.com",
                "lu12345")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser", "Sucesso ao cadastrar usuario!");
                        }else {
                            Log.i("CreateUser", "Erro ao cadastrar usuario!");
                        }
                    }
                });*/


        /*
        DatabaseReference usuarios = referencia.child( "usuarios" );
        DatabaseReference produtos = referencia.child("produto");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Salvar dados no firebase

        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setSobrenome("Silva");
        usuario.setIdade(45);

        usuarios.child("002").setValue( usuario );

        Produto produto = new Produto();
        produto.setDescricao("iphone");
        produto.setMarca("apple");
        produto.setPreco(3000);

        produtos.child("001").setValue(produto);*/


    }
}