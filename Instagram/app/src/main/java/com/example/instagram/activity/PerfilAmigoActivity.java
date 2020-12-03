package com.example.instagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.example.instagram.adapter.AdapterGrid;
import com.example.instagram.helper.ConfiguracaoFirebase;
import com.example.instagram.helper.UsuarioFirebase;
import com.example.instagram.model.Postagem;
import com.example.instagram.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilAmigoActivity extends AppCompatActivity {

    private Usuario usuarioSelecionado;
    private Usuario usuarioLogado;
    private Button buttonAcaoPerfil;
    private CircleImageView imagePerfil;
    private TextView textPublicacoes, textSeguidores, textSeguindo;
    private GridView gridViewPerfil;
    private AdapterGrid adapterGrid;

    private DatabaseReference firebaseRef;
    private DatabaseReference usuariosRef;
    private DatabaseReference usuarioLogadoRef;
    private DatabaseReference usuarioAmigoRef;
    private DatabaseReference seguidoresRef;
    private DatabaseReference postagensUsuarioRef;
    private ValueEventListener valueEventListenerPerfilAmigo;

    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amigo);

        //Configurações iniciais
        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        usuariosRef = firebaseRef.child("usuarios");
        seguidoresRef = firebaseRef.child("seguidores");
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        //Inicializar os componentes
        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_preto_24);

        //Recupera usuario selecionado
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            usuarioSelecionado =(Usuario) bundle.getSerializable("usuarioSelecionado");

            postagensUsuarioRef = ConfiguracaoFirebase.getFirebaseDatabase().child("postagens").child(usuarioSelecionado.getId());

            //Configura nome do usuário na toolbar
            getSupportActionBar().setTitle(usuarioSelecionado.getNome());

            //Recupera foto do usuario
            String caminhoFoto = usuarioSelecionado.getCaminhoFoto();
            if(caminhoFoto != null){
                Uri url = Uri.parse(caminhoFoto);
                Glide.with(PerfilAmigoActivity.this).load(url).into(imagePerfil);
            }

        }


        inicializarImageLoader();
        carregarFotosPostagem();

    }

    private void inicializarComponentes(){
        imagePerfil = findViewById(R.id.imagePerfil);
        gridViewPerfil = findViewById(R.id.gridViewPerfil);
        buttonAcaoPerfil = findViewById(R.id.buttonAcaoPerfil);
        textPublicacoes = findViewById(R.id.textPublicacoes);
        textSeguidores = findViewById(R.id.textSeguidores);
        textSeguindo = findViewById(R.id.textSeguindo);
        buttonAcaoPerfil.setText("Carregando");
    }

    public void inicializarImageLoader(){

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);

    }

    public void carregarFotosPostagem(){

        postagensUsuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int tamanhoGrid = getResources().getDisplayMetrics().widthPixels;
                int tamanhoImagem = tamanhoGrid/3;
                gridViewPerfil.setColumnWidth(tamanhoImagem);

                List<String> urlFotos = new ArrayList<>();
                for( DataSnapshot ds: dataSnapshot.getChildren() ){
                    Postagem postagem = ds.getValue(Postagem.class);
                    urlFotos.add(postagem.getCaminhoFoto());
                }

                int qtdPostagem = urlFotos.size();
                textPublicacoes.setText(String.valueOf(qtdPostagem));

                //Configurar adapter
                adapterGrid = new AdapterGrid(getApplicationContext(), R.layout.grid_postagem, urlFotos);
                gridViewPerfil.setAdapter(adapterGrid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void recuperaDadosUsuarioLogado(){

        usuarioLogadoRef = usuariosRef.child( idUsuarioLogado );
        usuarioLogadoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Recupera dados de usuário logado
                usuarioLogado = dataSnapshot.getValue(Usuario.class);

                //Verifica se  usuário já está seguindo amigo selecionado
                verificaSegueUsuarioAmigo();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void verificaSegueUsuarioAmigo(){
        DatabaseReference seguidorRef = seguidoresRef.child(idUsuarioLogado).child(usuarioSelecionado.getId());

        seguidorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Já está seguindo
                    Log.i("dadosUsuairo", ": Seguindo");
                    habilitarBotaoSeguir(true);
                }else{
                    //Ainda não está sgeuindo
                    Log.i("dadosUsuairo", ": seguir");
                    habilitarBotaoSeguir(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void habilitarBotaoSeguir( boolean segueUsuario){

        if(segueUsuario){
            buttonAcaoPerfil.setText("Seguindo");
        }else{
            buttonAcaoPerfil.setText("Seguir");

            //Adicionar evento para seguir usuário
            buttonAcaoPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Salvar seguidor
                    salvarSeguidor(usuarioLogado, usuarioSelecionado);
                }
            });
        }

    }

    private void salvarSeguidor(Usuario uLogado, Usuario uAmigo){

        HashMap<String, Object> dadosAmigo = new HashMap<>();
        dadosAmigo.put("nome", uAmigo.getNome());
        dadosAmigo.put("caminhoFoto", uAmigo.getCaminhoFoto());

        DatabaseReference seguidorRef = seguidoresRef.child(uLogado.getId()).child(uAmigo.getId());
        seguidorRef.setValue(dadosAmigo);

        //Alterar botao açao para seguindo
        buttonAcaoPerfil.setText("Seguindo");
        buttonAcaoPerfil.setOnClickListener(null);

        //Incrementar seguindo do usuário logado
        int seguindo  = uLogado.getSeguindo() + 1;
        HashMap<String, Object> dadosSeguindo = new HashMap<>();
        dadosSeguindo.put("seguindo", seguindo);

        DatabaseReference usuarioSeguindo = usuariosRef.child(uLogado.getId());
        usuarioSeguindo.updateChildren(dadosSeguindo);

        //Incrementar seguidores do amigo
        int seguidores  = uAmigo.getSeguidores() + 1;
        HashMap<String, Object> dadosSeguidores = new HashMap<>();
        dadosSeguidores.put("seguidores", seguidores);

        DatabaseReference usuarioSeguidores = usuariosRef.child(uAmigo.getId());
        usuarioSeguidores.updateChildren(dadosSeguidores);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Recuperar dados do amigo selecionado
        recuperarDadosPerfilAmigos();

        //Recuperar dados usuario logado
        recuperaDadosUsuarioLogado();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioAmigoRef.removeEventListener(valueEventListenerPerfilAmigo);
    }

    private void recuperarDadosPerfilAmigos(){
        usuarioAmigoRef = usuariosRef.child(usuarioSelecionado.getId());

        valueEventListenerPerfilAmigo = usuarioAmigoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                //String postagens = String.valueOf(usuario.getPostagens());
                String seguindo = String.valueOf(usuario.getSeguindo());
                String seguidores = String.valueOf(usuario.getSeguidores());

                //Configura valores recuperados
                //textPublicacoes.setText(postagens);
                textSeguidores.setText(seguidores);
                textSeguindo.setText(seguindo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}