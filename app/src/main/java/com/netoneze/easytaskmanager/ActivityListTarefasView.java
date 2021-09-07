package com.netoneze.easytaskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.Utils.AdapterListHome;
import com.netoneze.easytaskmanager.Utils.UtilsGUI;
import com.netoneze.easytaskmanager.modelo.Tarefa;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityListTarefasView extends AppCompatActivity {

    ExpandableListView listViewTarefas;
    public static final String ID = "ID";
    public static final String MODO = "MODO";

    public static final int PEDIR_CADASTRO = 1;
    public static final int ALTERAR_CADASTRO = 2;
    public int posic = -1;
    private final ArrayList<Tarefa> tarefas = new ArrayList<>();

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

        List<String> lstGrupos = new ArrayList<>();

        for (int i = 0 ; i < lista.size() ; i++){
            lstGrupos.add(lista.get(i).getTitulo());
        }

        HashMap<String, List<Tarefa>> lstItensGrupo = new HashMap<>();

        for (int i = 0 ; i < lista.size() ; i++){
            lstItensGrupo.put(lstGrupos.get(i), lista.subList(i, i+1));
        }

        AdapterListHome adapter = new AdapterListHome(this, lstGrupos, lstItensGrupo);

        listViewTarefas.setAdapter(adapter);
    }

    public void vaiParaTelaDeCadastro(View view){
        Intent intentCadastro = new Intent(this, ActivityCadastraTarefasView.class);

        startActivityForResult(intentCadastro, PEDIR_CADASTRO);
    }

    public void vaiParaTelaDeCadastroEditar(int posicao){
        Tarefa tarefa = (Tarefa) listViewTarefas.getExpandableListAdapter().getChild(posicao, 0);
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


    private void excluir(int posicao){
        Tarefa tarefa = (Tarefa) listViewTarefas.getExpandableListAdapter().getChild(posicao, 0);
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        String mensagem = getString(R.string.deseja_realmente_apagar)
                + "\n" + tarefa.getTitulo();

        DialogInterface.OnClickListener listener =
                (dialog, which) -> {

                    switch(which){
                        case DialogInterface.BUTTON_POSITIVE:

                            database.tarefaDao().delete(tarefa);

                            populaLista();

                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
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
        ExpandableListView.ExpandableListContextMenuInfo info;
        info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        int groupPos = 0;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
        {
            groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        }

        switch(item.getItemId()){

            case R.id.editar_menu_item:
                vaiParaTelaDeCadastroEditar(groupPos);
                return true;

            case R.id.excluir_menu_item:
                excluir(groupPos);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();

        ExpandableListView.ExpandableListContextMenuInfo info;

        info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);

        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            inflater.inflate(R.menu.menu_contexto_list_tarefas, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_top_menu, menu);
        return true;
    }

}