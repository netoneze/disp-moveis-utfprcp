package com.netoneze.easytaskmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAutoriaView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.sobre));
        setContentView(R.layout.activity_autoria_view);
    }
}