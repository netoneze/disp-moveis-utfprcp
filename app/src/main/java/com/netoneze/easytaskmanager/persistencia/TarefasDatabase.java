package com.netoneze.easytaskmanager.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.netoneze.easytaskmanager.modelo.Tarefa;

@Database(entities = {Tarefa.class}, version = 4, exportSchema = false)
@TypeConverters({ConversorData.class})
public abstract class TarefasDatabase extends RoomDatabase {
    public abstract TarefaDao tarefaDao();

    private static TarefasDatabase instance;

    public static TarefasDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (TarefasDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            TarefasDatabase.class,
                            "tarefas.db").allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
