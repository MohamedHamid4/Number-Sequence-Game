package com.example.numbersequencegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.numbersequencegame.databinding.ActivitySettingsBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Settings extends AppCompatActivity {
ActivitySettingsBinding binding ;
GameDatabase database ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnShowGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShowLastGame_Date.class);
                startActivity(intent);
            }
        });

        binding.btnShowLastGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                Toast.makeText(Settings.this, currentDate, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Change_Password.class);
                startActivity(intent);
            }
        });

        binding.btnClearGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = new GameDatabase(Settings.this);
                database.delete_tabl();
                Toast.makeText(Settings.this,R.string.Data_has_been_deleted, Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Game.class);
                startActivity(intent);
            }
        });
    }
}