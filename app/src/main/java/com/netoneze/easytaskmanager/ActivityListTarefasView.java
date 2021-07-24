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

import java.util.ArrayList;

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
        setContentView(R.layout.activity_list_view);

        listViewTarefas = findViewById(R.id.listViewTarefas);

        listViewTarefas.setOnItemClickListener((parent, view, position, id) -> {
            Tarefa tarefa = (Tarefa) listViewTarefas.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), tarefa.getTitulo() + getString(R.string.foi_clicado), Toast.LENGTH_LONG).show();
        });

        registerForContextMenu(listViewTarefas);

        populaLista();
    }

    public void populaLista(){
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefas);

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

    public void adicionar(Bundle bundle){

        String titulo = bundle.getString(TITULO);
        String local = bundle.getString(LOCAL);
        String descricao = bundle.getString(DESCRICAO);
        String prioridade = bundle.getString(PRIORIDADE);
        String periodo = bundle.getString(PERIODO);
        String data_tarefa = bundle.getString(DATA);

        this.tarefas.add(new Tarefa(titulo, local, descricao, prioridade, periodo, data_tarefa));

        this.adapter.notifyDataSetChanged();

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
            assert data != null;
            Bundle bundle = data.getExtras();

            if(bundle != null) {
                adicionar(bundle);
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.adicionar_botao_menu) {
            vaiParaTelaDeAutoria(this.findViewById(R.id.content_frame));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}