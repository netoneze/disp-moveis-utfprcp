package com.netoneze.easytaskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextData, editTextLocal, editTextDescricao;
    private CheckBox cbDiaInteiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextData = findViewById(R.id.editTextData);
        editTextLocal = findViewById(R.id.editTextOnde);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        cbDiaInteiro = findViewById(R.id.checkBoxDiaInteiro);
    }

    public void limparCampos(View view){
        editTextTitulo.setText(null);
        editTextData.setText(null);
        editTextLocal.setText(null);
        editTextDescricao.setText(null);
        cbDiaInteiro.setChecked(false);
        editTextTitulo.requestFocus();

        Toast.makeText(this,
                R.string.campos_limpos,
                Toast.LENGTH_SHORT).show();
    }

    public void mostrarTitulo (View view){
        String titulo = editTextTitulo.getText().toString();

        if (titulo == null || titulo.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.erro_campo_vazio,
                    Toast.LENGTH_SHORT).show();
            editTextTitulo.requestFocus();

            return;
        }

        Toast.makeText(this, titulo.trim(), Toast.LENGTH_SHORT).show();
    }

}