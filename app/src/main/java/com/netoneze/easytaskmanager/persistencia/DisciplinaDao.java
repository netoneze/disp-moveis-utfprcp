package com.netoneze.easytaskmanager.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.netoneze.easytaskmanager.modelo.Disciplina;

import java.util.List;

@Dao
public interface DisciplinaDao {
    @Insert
    long insert(Disciplina disciplina);

    @Delete
    void delete(Disciplina disciplina);

    @Update
    void update(Disciplina discplina);

    @Query("SELECT * FROM disciplinas WHERE id = :id")
    Disciplina queryForId(long id);

    @Query("SELECT * FROM disciplinas ORDER BY titulo ASC")
    List<Disciplina> queryAll();

    @Query("SELECT * FROM disciplinas WHERE titulo = :descricao ORDER BY titulo ASC")
    List<Disciplina> queryForDescricao(String descricao);

    @Query("SELECT count(*) FROM disciplinas")
    int total();
}
