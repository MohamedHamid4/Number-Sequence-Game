package com.example.numbersequencegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.numbersequencegame.databinding.ActivityGameBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Game extends AppCompatActivity {
    // عملت ال DILOG ب FIND VIEW BR ID
    Dialog dilog ;
    Button btn_nextgame;
    Button btn_cancel;


    ActivityGameBinding binding;
    int hid;
    SharedPreferences preferences ;
    int age ;
    GameDatabase database;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        dilog = new Dialog(this);


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        binding.tvDate.setText(currentDate + "");

//  ____________________________________________________________________________________________

        //   تابع جملة احتساب العمر مع نقل الاسم
        preferences = getSharedPreferences("information",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        binding.tvName.setText(preferences.getString("Full_Name",""));
        age = preferences.getInt("KEY_AGE",0);
        binding.tvAge.setText(String.valueOf(age));

        //  ____________________________________________________________________________________________

        setSupportActionBar(binding.ToolBar);
        setTitle("  ");
        answer();

//  ____________________________________________________________________________________________

        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.etEnterNumber.getText().toString().isEmpty()) {
                    if (binding.etEnterNumber.getText().toString().equals(String.valueOf(hid))) {
                        // خاص بال dilog
                        openWinDilog();

                        // Music
                        MediaPlayer correct = MediaPlayer.create(Game.this, R.raw.music_correct);
                        correct.start();
                        // Score
                        int oldScore = Integer.parseInt(binding.tvScore.getText().toString());
                        int newScore = oldScore + 5;
                        binding.tvScore.setText(String.valueOf(newScore));

                    } else {
                        // Custom Tost
                        View v = getLayoutInflater().inflate(R.layout.custom_tost, null);
                        Toast toast = new Toast(getBaseContext());
                        toast.setView(v);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        // Music
                        MediaPlayer incorrect = MediaPlayer.create(Game.this, R.raw.music_incorrect);
                        incorrect.start();
                        // Score
                        int oldScore = Integer.parseInt(binding.tvScore.getText().toString());
                        int newScore = oldScore - 1;
                        binding.tvScore.setText(String.valueOf(newScore));
                    }

                } else
                    Toast.makeText(Game.this,R.string.You_must_enter_a_value, Toast.LENGTH_SHORT).show();
                int mark = Integer.parseInt(binding.tvScore.getText().toString());
                String name = binding.tvName.getText().toString();

                Calendar dt = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy (HH:MM:SS)");
                String date = format.format(dt.getTime());


                GameVeribel d = new GameVeribel(name,mark,date);
                database = new GameDatabase(Game.this);
                database.insertGameHistory(d);
            }

        });


        //  ____________________________________________________________________________________________
            // عند الدعس على زر new game يبدء لعبة جديدة
        binding.btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer();
            }
        });
             }
    //  ____________________________________________________________________________________________
         //تابع بالكود الخاص بالDILOG خارج ON CREATE
    private void openWinDilog() {
        dilog.setContentView(R.layout.custom_dilog);
        dilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_next_game = dilog.findViewById(R.id.btn_nextgame);
        Button btn_cancel = dilog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View b = getLayoutInflater().inflate(R.layout.custom_cancel, null);
                Toast toast = new Toast(getBaseContext());
                toast.setView(b);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                dilog.dismiss();
            }
        });

        btn_next_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer();
                View w = getLayoutInflater().inflate(R.layout.custom_nextgame, null);
                Toast toast = new Toast(getBaseContext());
                toast.setView(w);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                dilog.dismiss();
            }

        });

        dilog.show();

    }

    //  ____________________________________________________________________________________________

    // tollbar (menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.menu1,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Logout:
                preferences = getSharedPreferences("information",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent ss = new Intent(getApplicationContext(),Login.class);
                startActivity(ss);
                finish();
                return true ;

            case R.id.menu_Settings:
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //  ____________________________________________________________________________________________
    // عمل مصفوفة على شكل شبكة مكونة من 9 ارقام
    public void answer(){
        Util util = new Util();
        Question question = util.generteQuestion();
        binding.tv1.setText(question.getData()[0][0]);
        binding.tv2.setText(question.getData()[0][1]);
        binding.tv3.setText(question.getData()[0][2]);
        binding.tv4.setText(question.getData()[1][0]);
        binding.tv5.setText(question.getData()[1][1]);
        binding.tv6.setText(question.getData()[1][2]);
        binding.tv7.setText(question.getData()[2][0]);
        binding.tv8.setText(question.getData()[2][1]);
        binding.tv9.setText(question.getData()[2][2]);
        hid = question.getHidNumber();

        // لمعرفة الرقم المخفي (يظهر فقط لي)
        System.out.println("Hid Numper Is : " + hid);

    }
}