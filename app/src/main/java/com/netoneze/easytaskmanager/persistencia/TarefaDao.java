package com.netoneze.easytaskmanager.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.netoneze.easytaskmanager.modelo.Tarefa;

import java.util.List;

@Dao
public interface TarefaDao {
    @Insert
    long insert(Tarefa tarefa);

    @Delete
    void delete(Tarefa tarefa);

    @Update
    void update(Tarefa tarefa);

    @Query("SELECT * FROM tarefa WHERE id = :id")
    Tarefa queryForId(long id);

    @Query("SELECT * FROM tarefa ORDER BY id ASC")
    List<Tarefa> queryAll();
}
