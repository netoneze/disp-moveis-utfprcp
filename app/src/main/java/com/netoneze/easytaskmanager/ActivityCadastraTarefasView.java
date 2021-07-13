package com.netoneze.easytaskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityCadastraTarefasView extends AppCompatActivity {

    private EditText editTextTitulo, editTextData, editTextLocal, editTextDescricao;
    private CheckBox cbDiaInteiro;
    private RadioGroup radioGroupPeriodo;
    private Spinner spinnerPrioridade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextData = findViewById(R.id.editTextData);
        editTextLocal = findViewById(R.id.editTextOnde);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        cbDiaInteiro = findViewById(R.id.checkBoxDiaInteiro);
        radioGroupPeriodo = findViewById(R.id.radioGroupPeriodo);
        spinnerPrioridade = findViewById(R.id.spinnerPrioridade);

        populaSpinner();
    }

    public void limparCampos(View view){
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

    public void salvarConteudo(View view){
        String titulo = editTextTitulo.getText().toString();
        String data = editTextData.getText().toString();
        String local = editTextLocal.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        Boolean checkBoxDiaInteiro = cbDiaInteiro.isChecked();
        int radioGroupPeriodoId = radioGroupPeriodo.getCheckedRadioButtonId();
        String spinner = spinnerPrioridade.getSelectedItem().toString();

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

        Intent intentListagem = new Intent(this, ActivityListTarefasView.class);

        intentListagem.putExtra(ActivityListTarefasView.TITULO, titulo);
        intentListagem.putExtra(ActivityListTarefasView.DATA, data);
        intentListagem.putExtra(ActivityListTarefasView.LOCAL, local);
        intentListagem.putExtra(ActivityListTarefasView.DESCRICAO, descricao);
        intentListagem.putExtra(ActivityListTarefasView.PRIORIDADE, spinner);
        intentListagem.putExtra(ActivityListTarefasView.DIA_TODO, checkBoxDiaInteiro);
        intentListagem.putExtra(ActivityListTarefasView.PERIODO, radioGroupPeriodoId);

        setResult(Activity.RESULT_OK, intentListagem);

        finish();
    }

    @Override
    public void onBackPressed() {

        setResult(Activity.RESULT_CANCELED);

        finish();

        super.onBackPressed();
    }
}