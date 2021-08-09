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

    public Disciplina(int id, String titulo){
        setId(id);
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
