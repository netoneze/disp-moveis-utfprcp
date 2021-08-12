package com.netoneze.easytaskmanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.Utils.UtilsDate;
import com.netoneze.easytaskmanager.modelo.Disciplina;
import com.netoneze.easytaskmanager.modelo.Tarefa;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.Date;

public class ActivityMostrarTarefa extends AppCompatActivity {

    private TextView textViewTitulo, textViewLocal, textViewDescricao, textViewPrioridade, textViewPeriodo, textViewDataTarefa, textViewDisciplina;

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
        textViewDisciplina = findViewById(R.id.textViewDisciplinaMostrarConteudo);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            TarefasDatabase database = TarefasDatabase.getDatabase(this);

            Tarefa tarefa = database.tarefaDao().queryForId(bundle.getInt(ActivityListTarefasView.ID));
            Disciplina disciplina = database.disciplinaDao().queryForId(tarefa.getDisciplinaId());

            String titulo = tarefa.getTitulo();
            String local = tarefa.getLocal();
            String descricao = tarefa.getDescricao();
            String prioridade = tarefa.getPrioridade();
            String periodo = tarefa.getPeriodo();
            Date data_tarefa = tarefa.getData();
            String disciplina2 = disciplina.getTitulo();

            textViewTitulo.setText(titulo);
            textViewDescricao.setText(descricao);
            textViewLocal.setText(local);
            textViewPrioridade.setText(prioridade);
            textViewPeriodo.setText(periodo);
            textViewTitulo.setText(titulo);
            textViewDataTarefa.setText(UtilsDate.formatDate(data_tarefa));
            textViewDisciplina.setText(disciplina2);
        }
    }


}