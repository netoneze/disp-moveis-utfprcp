package com.netoneze.easytaskmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity
public class Tarefa {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String titulo;
    @NonNull
    private String local;
    @NonNull
    private String descricao;
    @NonNull
    private String prioridade;
    @NonNull
    private String periodo;
    @NonNull
    private String data;


    public Tarefa(String titulo, String local, String descricao, String prioridade, String periodo, String data) {
        this.setTitulo(titulo);
        this.setLocal(local);
        this.setDescricao(descricao);
        this.setPrioridade(prioridade);
        this.setPeriodo(periodo);
        this.setData(data);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitulo() + "\n" +
                getData();
    }


}
