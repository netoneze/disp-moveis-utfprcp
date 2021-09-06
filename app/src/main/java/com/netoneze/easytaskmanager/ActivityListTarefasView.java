package com.netoneze.easytaskmanager;

import android.content.DialogInterface;
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

import com.netoneze.easytaskmanager.Utils.UtilsGUI;
import com.netoneze.easytaskmanager.modelo.Tarefa;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActivityListTarefasView extends AppCompatActivity {

    ListView listViewTarefas;
    public static final String ID = "ID";
    public static final String MODO = "MODO";

    public static final int PEDIR_CADASTRO = 1;
    public static final int ALTERAR_CADASTRO = 2;
    public int posic = -1;
    private final ArrayList<Tarefa> tarefas = new ArrayList<>();
    private ArrayAdapter<Tarefa> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tarefas_view);

        listViewTarefas = findViewById(R.id.listViewTarefas);

        registerForContextMenu(listViewTarefas);

        populaLista();
    }

    public void populaLista(){
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        List<Tarefa> lista = database.tarefaDao().queryAll();

        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        listViewTarefas.setAdapter(adapter);
    }

    public void vaiParaTelaDeCadastro(View view){
        if (verificaDisciplinas()) {
            Intent intentCadastro = new Intent(this, ActivityCadastraTarefasView.class);

            startActivityForResult(intentCadastro, PEDIR_CADASTRO);
        } else {
            Toast.makeText(getApplicationContext(),  getString(R.string.sem_disciplina), Toast.LENGTH_LONG).show();
        }
    }

    public void vaiParaTelaDeCadastroEditar(int posicao){
        Tarefa tarefa = (Tarefa) listViewTarefas.getItemAtPosition(posicao);
        int id = (int) tarefa.getId();

        Intent intentAlterarCadastro = new Intent(this, ActivityCadastraTarefasView.class);

        intentAlterarCadastro.putExtra(ID, id);
        intentAlterarCadastro.putExtra(MODO, ALTERAR_CADASTRO);

        startActivityForResult(intentAlterarCadastro, ALTERAR_CADASTRO);
    }

    public void vaiParaTelaDeAutoria(MenuItem item){
        Intent intentAutoria = new Intent(this, ActivityAutoriaView.class);

        startActivity(intentAutoria);
    }


    public void vaiParaTelaDeMostrar(int posicao){
        Tarefa tarefa = (Tarefa) listViewTarefas.getItemAtPosition(posicao);
        int id = (int) tarefa.getId();

        Intent intentMostrarTarefa = new Intent(this, ActivityMostrarTarefa.class);

        intentMostrarTarefa.putExtra(ID, id);

        startActivity(intentMostrarTarefa);
    }

    public void vaiParaTelaDeDisciplinas(MenuItem item){
        Intent intentDisciplina = new Intent(this, ActivityListDisciplinasView.class);

        startActivity(intentDisciplina);
    }

    private void excluir(int posicao){
        Tarefa tarefa = (Tarefa) listViewTarefas.getItemAtPosition(posicao);
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        String mensagem = getString(R.string.deseja_realmente_apagar)
                + "\n" + tarefa.getTitulo();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                database.tarefaDao().delete(tarefa);

                                populaLista();

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    public boolean verificaDisciplinas(){
        TarefasDatabase database = TarefasDatabase.getDatabase(this);
        int total = database.disciplinaDao().total();
        return total > 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            populaLista();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

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