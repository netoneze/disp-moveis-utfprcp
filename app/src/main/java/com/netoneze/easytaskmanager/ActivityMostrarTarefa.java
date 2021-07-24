package com.netoneze.easytaskmanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMostrarTarefa extends AppCompatActivity {

    private TextView textViewTitulo, textViewLocal, textViewDescricao, textViewPrioridade, textViewPeriodo, textViewDataTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.informacaoTarefa));
        setContentView(R.layout.activity_mostrar_tarefa);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        textViewTitulo = findViewById(R.id.textViewTituloMostrarConteudo);
        textViewLocal = findViewById(R.id.textViewLocalMostrarContetudo);
        textViewDescricao = findViewById(R.id.textViewDescricaoMostrarConteudo);
        textViewPrioridade = findViewById(R.id.textViewPrioridadeMostrarConteudo);
        textViewPeriodo = findViewById(R.id.textViewPeriodoMostrarConteudo);
        textViewDataTarefa = findViewById(R.id.textViewDataMostrarConteudo);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            String titulo = bundle.getString(ActivityListTarefasView.TITULO);
            String local = bundle.getString(ActivityListTarefasView.LOCAL);
            String descricao = bundle.getString(ActivityListTarefasView.DESCRICAO);
            String prioridade = bundle.getString(ActivityListTarefasView.PRIORIDADE);
            String periodo = bundle.getString(ActivityListTarefasView.PERIODO);
            String data_tarefa = bundle.getString(ActivityListTarefasView.DATA);

            textViewTitulo.setText(titulo);
            textViewDescricao.setText(descricao);
            textViewLocal.setText(local);
            textViewPrioridade.setText(prioridade);
            textViewPeriodo.setText(periodo);
            textViewTitulo.setText(titulo);
            textViewDataTarefa.setText(data_tarefa);
        }
    }


}