package com.example.numbersequencegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.numbersequencegame.databinding.ActivityShowLastGameDateBinding;

import java.util.ArrayList;

public class ShowLastGame_Date extends AppCompatActivity {
ActivityShowLastGameDateBinding binding ;
GameDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowLastGameDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = new GameDatabase(this);
        ArrayList<GameVeribel> gameVeribel = database.getLastGameHistory();

        recycle_addapter addapter = new recycle_addapter(gameVeribel,this);
        binding.recycle.setAdapter(addapter);
        binding.recycle.setHasFixedSize(true);
        binding.recycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));




    }
}