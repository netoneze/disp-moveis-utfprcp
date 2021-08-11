package com.netoneze.easytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.modelo.Disciplina;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.List;

public class ActivityListDisciplinasView extends AppCompatActivity {

    ListView listViewDisciplinas;
    private ArrayAdapter<Disciplina> adapter;
    public static final int PEDIR_CADASTRO_DISCIPLINA = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disciplinas_view);

        setTitle(getString(R.string.disciplina));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listViewDisciplinas = findViewById(R.id.listViewDisciplinas);

        registerForContextMenu(listViewDisciplinas);

        populaLista();
    }

    public void populaLista(){
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        List<Disciplina> lista = database.disciplinaDao().queryAll();

        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        listViewDisciplinas.setAdapter(adapter);
    }

    public void vaiParaTelaDeCadastroDisciplina(MenuItem item){
        Intent intentCadastroDisciplina = new Intent(this, ActivityCadastraDisciplinasView.class);

        startActivityForResult(intentCadastroDisciplina, PEDIR_CADASTRO_DISCIPLINA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PEDIR_CADASTRO_DISCIPLINA && resultCode == RESULT_OK){
            populaLista();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_list_disciplinas, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.disciplina_top_menu, menu);
        return true;
    }

}