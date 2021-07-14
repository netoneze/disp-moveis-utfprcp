package com.netoneze.easytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityListTarefasView extends AppCompatActivity {

    ListView listViewTarefas;
    public static final String TITULO = "TITULO";
    public static final String LOCAL = "LOCAL";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String PRIORIDADE = "PRIORIDADE";
    public static final String PERIODO = "PERIODO";
    public static final String DATA = "DATA";
    public static final String DIA_TODO = "DIA_TODO";
    public static final int PEDIR_CADASTRO = 1;

    private final ArrayList<Tarefa> tarefas = new ArrayList<>();
    private ArrayAdapter<Tarefa> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listViewTarefas = findViewById(R.id.listViewTarefas);

        listViewTarefas.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefa = (Tarefa) listViewTarefas.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), tarefa.getTitulo() + getString(R.string.foi_clicado), Toast.LENGTH_LONG).show();
        });

        populaLista();
    }

    public void populaLista(){
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefas);

        listViewTarefas.setAdapter(adapter);

    }

    public void vaiParaTelaDeCadastro(View view){
        Intent intentCadastro = new Intent(this, ActivityCadastraTarefasView.class);

        startActivityForResult(intentCadastro, PEDIR_CADASTRO);
    }

    public void vaiParaTelaDeAutoria(View view){
        Intent intentAutoria = new Intent(this, ActivityAutoriaView.class);

        startActivity(intentAutoria);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PEDIR_CADASTRO && resultCode == RESULT_OK){
            assert data != null;
            Bundle bundle = data.getExtras();

            if(bundle != null) {
                String titulo = bundle.getString(TITULO);
                String local = bundle.getString(LOCAL);
                String descricao = bundle.getString(DESCRICAO);
                String prioridade = bundle.getString(PRIORIDADE);
                String periodo = bundle.getString(PERIODO);
                String data_tarefa = bundle.getString(DATA);
                String dia_todo = bundle.getString(DIA_TODO);

                this.tarefas.add(new Tarefa(titulo, local, descricao, prioridade, periodo, data_tarefa));
                this.adapter.notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sobre_botao_menu) {
            vaiParaTelaDeAutoria(this.findViewById(R.id.content_frame));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}