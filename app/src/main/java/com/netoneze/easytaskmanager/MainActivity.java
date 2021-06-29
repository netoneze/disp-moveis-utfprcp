package com.netoneze.easytaskmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextData, editTextLocal, editTextDescricao;
    private CheckBox cbDiaInteiro;
    private RadioGroup radioGroupPeriodo;

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

    @SuppressLint("NonConstantResourceId")
    public void mostrarTitulo (View view){
        String titulo = editTextTitulo.getText().toString();

        if (titulo == null || titulo.trim().isEmpty() || radioGroupPeriodo.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,
                    R.string.erro_campo_vazio,
                    Toast.LENGTH_SHORT).show();
            editTextTitulo.requestFocus();

            return;
        }

        switch(radioGroupPeriodo.getCheckedRadioButtonId()){
            case R.id.radioButtonPeriodoManha:
                Toast.makeText(this, "Periodo manhã selecionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButtonPeriodoTarde:
                Toast.makeText(this, "Periodo tarde selecionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id .radioButtonPeriodoNoite:
                Toast.makeText(this, "Periodo noite selecionado", Toast.LENGTH_SHORT).show();
                break;

            default:

        }

        Toast.makeText(this, titulo.trim(), Toast.LENGTH_SHORT).show();
    }

}