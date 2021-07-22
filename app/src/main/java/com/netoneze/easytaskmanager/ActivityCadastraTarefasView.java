package com.netoneze.easytaskmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityCadastraTarefasView extends AppCompatActivity {

    private EditText editTextTitulo, editTextData, editTextLocal, editTextDescricao;
    private CheckBox cbDiaInteiro, cbSugestao;
    private RadioGroup radioGroupPeriodo;
    private Spinner spinnerPrioridade;
    private String tituloSugestao = "";
    private boolean sugestao = false;
    private static final String SUGESTAO = "SUGESTAO";
    private static final String TITULO = "TITULO";
    private static final String ARQUIVO =
            "com.netoneze.easytaskmanager.sharedpreferences.PREFERENCIAS_SUGESTAO_CADASTRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.cadastrar_tarefa));
        setContentView(R.layout.activity_cadastra_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextData = findViewById(R.id.editTextData);
        editTextLocal = findViewById(R.id.editTextOnde);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        cbDiaInteiro = findViewById(R.id.checkBoxDiaInteiro);
        cbSugestao = findViewById(R.id.checkBoxSugestao);
        radioGroupPeriodo = findViewById(R.id.radioGroupPeriodo);
        spinnerPrioridade = findViewById(R.id.spinnerPrioridade);

        lerPreferenciaSugestao();

        populaSpinner();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            String titulo = bundle.getString(ActivityListTarefasView.TITULO);
            String local = bundle.getString(ActivityListTarefasView.LOCAL);
            String descricao = bundle.getString(ActivityListTarefasView.DESCRICAO);
            String prioridade = bundle.getString(ActivityListTarefasView.PRIORIDADE);
            String periodo = bundle.getString(ActivityListTarefasView.PERIODO);
            String data_tarefa = bundle.getString(ActivityListTarefasView.DATA);

            editTextTitulo.setText(titulo);
            editTextLocal.setText(local);
            editTextDescricao.setText(descricao);
            editTextData.setText(data_tarefa);

            for(int i = 0 ; i < spinnerPrioridade.getAdapter().getCount() ; i++){
                if (spinnerPrioridade.getItemAtPosition(i).toString().equals(prioridade)){
                    spinnerPrioridade.setSelection(i);
                }
            }

            switch (periodo){
                case "Manhã":
                    radioGroupPeriodo.check(R.id.radioButtonPeriodoManha);
                    break;
                case "Tarde":
                    radioGroupPeriodo.check(R.id.radioButtonPeriodoTarde);
                    break;
                case "Noite":
                    radioGroupPeriodo.check(R.id.radioButtonPeriodoNoite);
                    break;
                default:
                    radioGroupPeriodo.clearCheck();
            }
        }
    }

    public void limparCampos(MenuItem item){
        editTextTitulo.setText(null);
        editTextData.setText(null);
        editTextLocal.setText(null);
        editTextDescricao.setText(null);
        cbDiaInteiro.setChecked(false);
        editTextTitulo.requestFocus();
        radioGroupPeriodo.clearCheck();
        Toast.makeText(this,
                R.string.campos_limpos,
                Toast.LENGTH_SHORT).show();
    }

    public void populaSpinner(){
        ArrayList<String> lista = new ArrayList<>();

        lista.add(getString(R.string.prioridade_baixa));
        lista.add(getString(R.string.prioridade_media));
        lista.add(getString(R.string.prioridade_alta));

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        spinnerPrioridade.setAdapter(adapter);
    }

    public void salvarConteudo(MenuItem item){
        String titulo = editTextTitulo.getText().toString();
        String data = editTextData.getText().toString();
        String local = editTextLocal.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        Boolean checkBoxDiaInteiro = cbDiaInteiro.isChecked();
        int radioGroupPeriodoId = radioGroupPeriodo.getCheckedRadioButtonId();
        String radioGroupPeriodo = "";
        String spinner = spinnerPrioridade.getSelectedItem().toString();
        salvarPreferenciaSugestao(cbSugestao.isChecked(), titulo);
        if (titulo == null || titulo.trim().isEmpty() ||
                data == null || data.trim().isEmpty() ||
                local == null || local.trim().isEmpty() ||
                descricao == null || descricao.trim().isEmpty() ||
                radioGroupPeriodoId == -1)
        {
            Toast.makeText(this,
                    R.string.erro_campo_vazio,
                    Toast.LENGTH_SHORT).show();
            editTextTitulo.requestFocus();

            return;
        }

        switch (radioGroupPeriodoId) {
            case R.id.radioButtonPeriodoManha:
                radioGroupPeriodo = getString(R.string.periodoManha);
                break;
            case R.id.radioButtonPeriodoTarde:
                radioGroupPeriodo = getString(R.string.periodoTarde);
                break;
            case R.id.radioButtonPeriodoNoite:
                radioGroupPeriodo = getString(R.string.periodoNoite);
                break;
        }


        Intent intentListagem = new Intent(this, ActivityListTarefasView.class);

        intentListagem.putExtra(ActivityListTarefasView.TITULO, titulo);
        intentListagem.putExtra(ActivityListTarefasView.DATA, data);
        intentListagem.putExtra(ActivityListTarefasView.LOCAL, local);
        intentListagem.putExtra(ActivityListTarefasView.DESCRICAO, descricao);
        intentListagem.putExtra(ActivityListTarefasView.PRIORIDADE, spinner);
        intentListagem.putExtra(ActivityListTarefasView.DIA_TODO, checkBoxDiaInteiro);
        intentListagem.putExtra(ActivityListTarefasView.PERIODO, radioGroupPeriodo);

        setResult(Activity.RESULT_OK, intentListagem);

        finish();
    }

    public void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void salvarPreferenciaSugestao(boolean novoValor, String titulo){

        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(SUGESTAO, novoValor);

        if(novoValor){
            editor.putString(TITULO, titulo);
        }

        editor.commit();

        sugestao = novoValor;
    }

    private void lerPreferenciaSugestao(){

        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        sugestao = shared.getBoolean(SUGESTAO, sugestao);
        tituloSugestao = shared.getString(TITULO, tituloSugestao);
        cbSugestao.setChecked(sugestao);
        editTextTitulo.setText(tituloSugestao);
    }

    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_CANCELED);

        finish();

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cadastro_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            cancelar();
        }

        return super.onOptionsItemSelected(item);
    }
}