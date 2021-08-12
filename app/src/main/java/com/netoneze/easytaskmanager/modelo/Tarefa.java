package com.netoneze.easytaskmanager.modelo;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.netoneze.easytaskmanager.Utils.UtilsDate;

import java.util.Date;

@Entity(tableName = "tarefas",
        foreignKeys = @ForeignKey(entity = Disciplina.class, parentColumns = "id", childColumns = "disciplinaId", onDelete = CASCADE))
public class Tarefa {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String titulo = "";
    @NonNull
    private String local = "";
    @NonNull
    private String descricao = "";
    @NonNull
    private String prioridade = "";
    @NonNull
    private String periodo = "";
    @NonNull
    private Date data;
    @ColumnInfo(index = true)
    private int disciplinaId;

    public Tarefa(String titulo, String local, String descricao, String prioridade, String periodo, Date data) {
        this.setTitulo(titulo);
        this.setLocal(local);
        this.setDescricao(descricao);
        this.setPrioridade(prioridade);
        this.setPeriodo(periodo);
        this.setData(data);
    }

    @NonNull
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NonNull String titulo) {
        this.titulo = titulo;
    }

    @NonNull
    public String getLocal() {
        return local;
    }

    public void setLocal(@NonNull String local) {
        this.local = local;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(@NonNull String prioridade) {
        this.prioridade = prioridade;
    }

    @NonNull
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(@NonNull String periodo) {
        this.periodo = periodo;
    }

    @NonNull
    public Date getData() {
        return data;
    }

    public void setData(@NonNull Date data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitulo() + "\n" +
                UtilsDate.formatDate(getData());
    }


}
