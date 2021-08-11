package com.netoneze.easytaskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.netoneze.easytaskmanager.modelo.Disciplina;
import com.netoneze.easytaskmanager.persistencia.TarefasDatabase;

public class ActivityCadastraDisciplinasView extends AppCompatActivity {

    EditText editTextTituloDisciplina;
    private int modo, idDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_disciplinas_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.cadastrar_disciplina));

        editTextTituloDisciplina = findViewById(R.id.editTextTituloDisciplina);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            TarefasDatabase database = TarefasDatabase.getDatabase(this);
            Disciplina disciplinaDoBd = database.disciplinaDao().queryForId(Long.parseLong(String.valueOf(bundle.getInt(ActivityListDisciplinasView.ID))));

            String titulo = disciplinaDoBd.getTitulo();

            editTextTituloDisciplina.setText(titulo);

            modo = bundle.getInt(ActivityListDisciplinasView.MODO);
            idDisciplina = bundle.getInt(ActivityListDisciplinasView.ID);
        }
    }

    public void limparCamposDisciplina(MenuItem item){
        editTextTituloDisciplina.setText(null);
        Toast.makeText(this,
                R.string.campos_limpos,
                Toast.LENGTH_SHORT).show();
    }

    public void salvarConteudoDisciplina(MenuItem item){
        String titulo = editTextTituloDisciplina.getText().toString();

        if (titulo == null || titulo.trim().isEmpty() )
        {
            Toast.makeText(this,
                    R.string.erro_campo_vazio,
                    Toast.LENGTH_SHORT).show();
            editTextTituloDisciplina.requestFocus();

            return;
        }

        Intent intentListagem = new Intent(this, ActivityListDisciplinasView.class);

        TarefasDatabase database = TarefasDatabase.getDatabase(this);

        Disciplina disciplina = new Disciplina(titulo);

        if (modo == 2){
            disciplina.setId(idDisciplina);
            database.disciplinaDao().update(disciplina);
        } else {
            database.disciplinaDao().insert(disciplina);
        }

        setResult(Activity.RESULT_OK, intentListagem);

        finish();
    }

    public void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
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
        inflater.inflate(R.menu.cadastro_disciplina_top_menu, menu);
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