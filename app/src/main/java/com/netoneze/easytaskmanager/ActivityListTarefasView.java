package com.netoneze.easytaskmanager;

import android.content.Intent;
import android.os.Bundle;
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
        String[] titulo = getResources().getStringArray(R.array.titulo);
        String[] local = getResources().getStringArray(R.array.local);
        String[] descricao = getResources().getStringArray(R.array.descricao);
        String[] prioridade = getResources().getStringArray(R.array.prioridade);
        String[] periodo = getResources().getStringArray(R.array.periodo);
        String[] data = getResources().getStringArray(R.array.data);

        ArrayList<Tarefa> tarefas = new ArrayList<>();

        for(int count = 0 ; count < titulo.length ; count++){
            tarefas.add(new Tarefa(titulo[count], local[count], descricao[count], prioridade[count], periodo[count], data[count]));
        }

        ArrayAdapter<Tarefa> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefas);

        listViewTarefas.setAdapter(adapter);

    }

    public void vaiParaTelaDeCadastro(View view){
        Intent intentCadastro = new Intent(this, ActivityCadastraTarefasView.class);

        startActivityForResult(intentCadastro, PEDIR_CADASTRO);
    }
}