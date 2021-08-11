package com.netoneze.easytaskmanager.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "disciplinas",
        indices = @Index(value = {"titulo"}, unique = true))
public class Disciplina {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String titulo = "";

    public Disciplina(String titulo){
        setTitulo(titulo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NonNull String titulo) {
        this.titulo = titulo;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitulo();
    }
}
