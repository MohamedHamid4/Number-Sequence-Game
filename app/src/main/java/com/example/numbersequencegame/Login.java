package com.example.numbersequencegame;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.numbersequencegame.databinding.ActivityLoginBinding;
import com.example.numbersequencegame.databinding.ActivityMainBinding;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding ;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         // Shared preferences
        preferences = getSharedPreferences("information",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        binding.etUserName.setText(preferences.getString("User_Name",""));
        binding.etPassword.setText(preferences.getString("Password",""));

        Boolean booleans = preferences.getBoolean("check",false);

        if (booleans == true){
            Intent check=new Intent(Login.this,Game.class);
            startActivity(check);
            finish();
        }

        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (binding.checkBox.isChecked()){
                    editor.putBoolean("check",true);
                    editor.apply();
                }
                else
                    editor.putBoolean("check",false);
                editor.apply();
            }
        });

//  ____________________________________________________________________________________________

//شرط عدم الانتقال للشاشة الاخرى قبل تعبئة البيانات
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = preferences.getString("User_Name","");
                String password = preferences.getString("Password","");

                String EtUserName = binding.etUserName.getText().toString();
                String EtPassword = binding.etPassword.getText().toString();

                if (!EtUserName.isEmpty() && user_name.equals(EtUserName)){
                    if (!EtPassword.isEmpty() && password.equals(EtPassword)){
                        Intent intent = new Intent(getApplicationContext(),Game.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    binding.etPassword.setError("Plese Enter Password");
                }
                else
                    binding.etUserName.setError("Plese Enter Your Name");
            }
        });
//  ____________________________________________________________________________________________

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                View v = getLayoutInflater().inflate(R.layout.custom_tost_signup,null);
                Toast toast = new Toast(getBaseContext());
                toast.setView(v);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
            }
        });

     }
}