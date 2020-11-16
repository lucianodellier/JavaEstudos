package com.example.instagram.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.instagram.R;
import com.example.instagram.helper.ConfiguracaoFirebase;
import com.example.instagram.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PesquisaFragment extends Fragment {

    //Widget
    private SearchView searchViewPesquisa;
    private RecyclerView recyclerPesquisa;

    private List<Usuario> listaUsuarios;
    private DatabaseReference usuariosRef;

    public PesquisaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pesquisa, container, false);

       searchViewPesquisa = view.findViewById(R.id.searchViewPesquisa);
       recyclerPesquisa = view.findViewById(R.id.recyclerPesquisa);

       //Configurações iniciais
        listaUsuarios = new ArrayList<>();
        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");

       //Configura searchView
        searchViewPesquisa.setQueryHint("Busca Usuários");
        searchViewPesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String textoDigitado = newText.toUpperCase();
                pesquisarUsuarios(textoDigitado);
                return true;
            }
        });

       return view;
    }

    private void pesquisarUsuarios(String texto){

        //limpar Lista
        listaUsuarios.clear();

        //Pesquisa usuario caso tenha algum texto na pesquisa
        if(texto.length() > 0){
            Query query = usuariosRef.orderByChild("nome").startAt(texto).endAt(texto + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){

                        listaUsuarios.add(ds.getValue(Usuario.class));

                    }

                    int total = listaUsuarios.size();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}