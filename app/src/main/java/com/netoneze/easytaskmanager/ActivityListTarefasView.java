package com.netoneze.easytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.modelo.Tarefa;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActivityListTarefasView extends AppCompatActivity {

    ListView listViewTarefas;
    public static final String TITULO = "TITULO";
    public static final String LOCAL = "LOCAL";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String PRIORIDADE = "PRIORIDADE";
    public static final String PERIODO = "PERIODO";
    public static final String DATA = "DATA";
    public static final int PEDIR_CADASTRO = 1;
    public static final int ALTERAR_CADASTRO = 2;
    public int posic = -1;
    private final ArrayList<Tarefa> tarefas = new ArrayList<>();
    private ArrayAdapter<Tarefa> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tarefas_view);

        listViewTarefas = findViewById(R.id.listViewTarefas);

        listViewTarefas.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefa = (Tarefa) listViewTarefas.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), tarefa.getTitulo() + getString(R.string.foi_clicado), Toast.LENGTH_LONG).show();
        });

        registerForContextMenu(listViewTarefas);

        populaLista();
    }

    public void populaLista(){
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        List<Tarefa> lista = database.tarefaDao().queryAll();

        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        listViewTarefas.setAdapter(adapter);
    }

    public void vaiParaTelaDeCadastro(MenuItem item){
        Intent intentCadastro = new Intent(this, ActivityCadastraTarefasView.class);

        startActivityForResult(intentCadastro, PEDIR_CADASTRO);
    }

    public void vaiParaTelaDeCadastroEditar(int posicao){
        String titulo = adapter.getItem(posicao).getTitulo();
        String local = adapter.getItem(posicao).getLocal();
        String descricao = adapter.getItem(posicao).getDescricao();
        String prioridade = adapter.getItem(posicao).getPrioridade();
        String periodo = adapter.getItem(posicao).getPeriodo();
        String data_tarefa = adapter.getItem(posicao).getData();

        Intent intentAlterarCadastro = new Intent(this, ActivityCadastraTarefasView.class);

        intentAlterarCadastro.putExtra(TITULO, titulo);
        intentAlterarCadastro.putExtra(LOCAL, local);
        intentAlterarCadastro.putExtra(DESCRICAO, descricao);
        intentAlterarCadastro.putExtra(PRIORIDADE, prioridade);
        intentAlterarCadastro.putExtra(PERIODO, periodo);
        intentAlterarCadastro.putExtra(DATA, data_tarefa);

        startActivityForResult(intentAlterarCadastro, ALTERAR_CADASTRO);
    }

    public void vaiParaTelaDeAutoria(MenuItem item){
        Intent intentAutoria = new Intent(this, ActivityAutoriaView.class);

        startActivity(intentAutoria);
    }


    public void vaiParaTelaDeMostrar(int posicao){
        String titulo = adapter.getItem(posicao).getTitulo();
        String local = adapter.getItem(posicao).getLocal();
        String descricao = adapter.getItem(posicao).getDescricao();
        String prioridade = adapter.getItem(posicao).getPrioridade();
        String periodo = adapter.getItem(posicao).getPeriodo();
        String data_tarefa = adapter.getItem(posicao).getData();

        Intent intentMostrarTarefa = new Intent(this, ActivityMostrarTarefa.class);

        intentMostrarTarefa.putExtra(TITULO, titulo);
        intentMostrarTarefa.putExtra(LOCAL, local);
        intentMostrarTarefa.putExtra(DESCRICAO, descricao);
        intentMostrarTarefa.putExtra(PRIORIDADE, prioridade);
        intentMostrarTarefa.putExtra(PERIODO, periodo);
        intentMostrarTarefa.putExtra(DATA, data_tarefa);

        startActivity(intentMostrarTarefa);
    }

    public void vaiParaTelaDeDisciplinas(MenuItem item){
        Intent intentDisciplina = new Intent(this, ActivityListDisciplinasView.class);

        startActivity(intentDisciplina);
    }

    public void alterar(int posicao, Bundle bundle){

        String titulo = bundle.getString(TITULO);
        String local = bundle.getString(LOCAL);
        String descricao = bundle.getString(DESCRICAO);
        String prioridade = bundle.getString(PRIORIDADE);
        String periodo = bundle.getString(PERIODO);
        String data_tarefa = bundle.getString(DATA);

        this.tarefas.remove(posicao);
        this.tarefas.add(posicao, new Tarefa(titulo, local, descricao, prioridade, periodo, data_tarefa));

        this.adapter.notifyDataSetChanged();

    }

    private void excluir(int posicao){
        this.tarefas.remove(posicao);
        this.adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PEDIR_CADASTRO && resultCode == RESULT_OK){
            populaLista();
        }
        if (requestCode == ALTERAR_CADASTRO && resultCode == RESULT_OK){
            assert data != null;
            Bundle bundle = data.getExtras();

            if(bundle != null) {
                alterar(this.posic, bundle);
                this.posic = -1;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        this.posic = info.position;

        switch(item.getItemId()){

            case R.id.editar_menu_item:
                vaiParaTelaDeCadastroEditar(info.position);
                return true;

            case R.id.excluir_menu_item:
                excluir(info.position);
                return true;

            case R.id.mostrar_menu_item:
                vaiParaTelaDeMostrar(info.position);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_list_tarefas, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_top_menu, menu);
        return true;
    }

}