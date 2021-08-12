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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.Utils.UtilsGUI;
import com.netoneze.easytaskmanager.modelo.Disciplina;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.List;

public class ActivityListDisciplinasView extends AppCompatActivity {

    public static final String ID = "ID";
    public static final String MODO = "MODO";

    public static final int ALTERAR_CADASTRO = 2;

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

    private void excluirDisciplina(int posicao){
        Disciplina disciplina = (Disciplina) listViewDisciplinas.getItemAtPosition(posicao);
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        String mensagem = getString(R.string.deseja_realmente_apagar)
                + "\n" + disciplina.getTitulo();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                database.disciplinaDao().delete(disciplina);
                                populaLista();

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    public void vaiParaTelaDeCadastroDisciplina(MenuItem item){
        Intent intentCadastroDisciplina = new Intent(this, ActivityCadastraDisciplinasView.class);

        startActivityForResult(intentCadastroDisciplina, PEDIR_CADASTRO_DISCIPLINA);
    }

    public void vaiParaTelaDeCadastroEditarDisciplina(int posicao){
        Disciplina disciplina = (Disciplina) listViewDisciplinas.getItemAtPosition(posicao);
        int id = disciplina.getId();

        Intent intentAlterarCadastro = new Intent(this, ActivityCadastraDisciplinasView.class);

        intentAlterarCadastro.putExtra(ID, id);
        intentAlterarCadastro.putExtra(MODO, ALTERAR_CADASTRO);

        startActivityForResult(intentAlterarCadastro, ALTERAR_CADASTRO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){

            case R.id.editar_menu_item_disciplina:
                vaiParaTelaDeCadastroEditarDisciplina(info.position);
                return true;

            case R.id.excluir_menu_disciplina:
                excluirDisciplina(info.position);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}