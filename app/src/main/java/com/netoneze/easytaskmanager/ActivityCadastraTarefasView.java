package com.netoneze.easytaskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.Utils.UtilsDate;
import com.netoneze.easytaskmanager.modelo.Disciplina;
import com.netoneze.easytaskmanager.modelo.Tarefa;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityCadastraTarefasView extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText editTextTitulo, editTextData, editTextLocal, editTextDescricao;
    private CheckBox cbSugestao;
    private RadioGroup radioGroupPeriodo;
    private Spinner spinnerPrioridade, spinnerDisciplina;
    private String tituloSugestao = "";
    private List<Disciplina> listaDisciplinas;
    private boolean sugestao = false;
    private int idTarefa;
    private int modo;
    private Calendar calendarDataTarefa;
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
        cbSugestao = findViewById(R.id.checkBoxSugestao);
        radioGroupPeriodo = findViewById(R.id.radioGroupPeriodo);
        spinnerPrioridade = findViewById(R.id.spinnerPrioridade);
        spinnerDisciplina = findViewById(R.id.spinnerDisciplina);

        lerPreferenciaSugestao();

        populaSpinner();
        carregaDisciplinas();

        calendarDataTarefa = Calendar.getInstance();
        calendarDataTarefa.add(Calendar.MONTH, -
                getResources().getInteger(R.integer.quantidadeMes));

        editTextData.setFocusable(false);
        editTextData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* Para fazer o DatePicker aparecer em modo Spinner e não Calendar
                   para SDKs iguais ou maiores que o API Level 21 foi utilizado um
                   estilo customizado, que está na pasta values-v21.

                   Versões anteriores já aparecem em modo Spinner por padrão.
                 */
                DatePickerDialog picker = new DatePickerDialog(ActivityCadastraTarefasView.this,
                        R.style.CustomDatePickerDialogTheme,
                        ActivityCadastraTarefasView.this,
                        calendarDataTarefa.get(Calendar.YEAR),
                        calendarDataTarefa.get(Calendar.MONTH),
                        calendarDataTarefa.get(Calendar.DAY_OF_MONTH));

                picker.show();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            TarefasDatabase database = TarefasDatabase.getDatabase(this);
            Tarefa tarefaDoBd = database.tarefaDao().queryForId(Long.parseLong(String.valueOf(bundle.getInt(ActivityListTarefasView.ID))));

            idTarefa = bundle.getInt(ActivityListTarefasView.ID);

            String titulo = tarefaDoBd.getTitulo();
            String local = tarefaDoBd.getLocal();
            String descricao = tarefaDoBd.getDescricao();
            String prioridade = tarefaDoBd.getPrioridade();
            String periodo = tarefaDoBd.getPeriodo();
            Date data_tarefa = tarefaDoBd.getData();

            editTextTitulo.setText(titulo);
            editTextLocal.setText(local);
            editTextDescricao.setText(descricao);
            editTextData.setText(UtilsDate.formatDate(data_tarefa));

            for(int i = 0 ; i < spinnerPrioridade.getAdapter().getCount() ; i++){
                if (spinnerPrioridade.getItemAtPosition(i).toString().equals(prioridade)){
                    spinnerPrioridade.setSelection(i);
                }
            }

            for(int i = 0 ; i < spinnerDisciplina.getAdapter().getCount() ; i++){
                if ((spinnerDisciplina.getSelectedItemPosition() + 1) == tarefaDoBd.getDisciplinaId()){
                    spinnerDisciplina.setSelection(i);
                }
            }

            if (periodo.equals(getString(R.string.periodoManha))){
                radioGroupPeriodo.check(R.id.radioButtonPeriodoManha);
            } else if (periodo.equals(getString(R.string.periodoTarde))){
                radioGroupPeriodo.check(R.id.radioButtonPeriodoTarde);
            } else if (periodo.equals(getString(R.string.periodoNoite))){
                radioGroupPeriodo.check(R.id.radioButtonPeriodoNoite);
            } else {
                radioGroupPeriodo.clearCheck();
            }

            modo = bundle.getInt(ActivityListTarefasView.MODO);
        }
    }

    public void limparCampos(MenuItem item){
        editTextTitulo.setText(null);
        editTextData.setText(null);
        editTextLocal.setText(null);
        editTextDescricao.setText(null);
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
        String dataTexto = editTextData.getText().toString();
        Date data = calendarDataTarefa.getTime();
        String local = editTextLocal.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        int radioGroupPeriodoId = radioGroupPeriodo.getCheckedRadioButtonId();
        String radioGroupPeriodo = "";
        String prioridade = spinnerPrioridade.getSelectedItem().toString();
        salvarPreferenciaSugestao(cbSugestao.isChecked(), titulo);

        if (titulo == null || titulo.trim().isEmpty() ||
                dataTexto == null || dataTexto.trim().isEmpty() ||
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

        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        Tarefa tarefa = new Tarefa(titulo, local, descricao, prioridade, radioGroupPeriodo, data);

        Disciplina disciplinaSpinner = (Disciplina) spinnerDisciplina.getSelectedItem();
        if (disciplinaSpinner != null){
            tarefa.setDisciplinaId(disciplinaSpinner.getId());
        }

        if (modo == 2){
            tarefa.setId(idTarefa);
            database.tarefaDao().update(tarefa);

        } else {
            database.tarefaDao().insert(tarefa);

        }
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

        if (sugestao){
            tituloSugestao = shared.getString(TITULO, tituloSugestao);
            editTextTitulo.setText(tituloSugestao);
        }

        cbSugestao.setChecked(sugestao);

    }

    public void carregaDisciplinas(){
        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        listaDisciplinas = database.disciplinaDao().queryAll();

        ArrayAdapter<Disciplina> spinnerAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        listaDisciplinas);

        spinnerDisciplina.setAdapter(spinnerAdapter);

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendarDataTarefa.set(year, month, dayOfMonth);

        String textoData = UtilsDate.formatDate(calendarDataTarefa.getTime());

        editTextData.setText(textoData);
    }
}