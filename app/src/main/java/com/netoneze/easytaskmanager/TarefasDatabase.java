package com.netoneze.easytaskmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Tarefa.class}, version = 1, exportSchema = false)
public abstract class TarefasDatabase extends RoomDatabase {
    public abstract TarefaDao tarefaDao();

    private static TarefasDatabase instance;

    public static TarefasDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (TarefasDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            TarefasDatabase.class,
                            "tarefas.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
