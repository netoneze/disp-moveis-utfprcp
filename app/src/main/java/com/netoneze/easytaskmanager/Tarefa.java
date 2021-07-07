package com.netoneze.easytaskmanager;

import androidx.annotation.NonNull;

public class Tarefa {

    private String titulo;
    private String local;
    private String descricao;
    private String prioridade;
    private String periodo;
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

    @NonNull
    @Override
    public String toString() {
        return getTitulo() + "\n" +
                getLocal() + "\n" +
                getDescricao() + "\n" +
                getPrioridade() + "\n" +
                getPeriodo() + "\n" +
                getData();
    }
}
