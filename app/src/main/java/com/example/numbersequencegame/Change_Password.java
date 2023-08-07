package com.example.numbersequencegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.numbersequencegame.databinding.ActivityChangePasswordBinding;

public class Change_Password extends AppCompatActivity {
ActivityChangePasswordBinding binding ;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("information",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirm_Password = binding.ConfirmPassword.getText().toString();
                String newPassword = binding.NewPassword.getText().toString();
                String password = preferences.getString("Password", "") ;

                if (password.equals(binding.OldPassword.getText().toString())){
                    if (!confirm_Password.isEmpty() && newPassword.equals(confirm_Password)){
                        editor.putString("Password",confirm_Password);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(),Game.class);
                        startActivity(intent);
                        finish();

                        View b = getLayoutInflater().inflate(R.layout.custom_change, null);
                        Toast toast = new Toast(getBaseContext());
                        toast.setView(b);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else
                        Toast.makeText(Change_Password.this,R.string.Password_does_not_match, Toast.LENGTH_SHORT).show();


                }
                else
                    Toast.makeText(Change_Password.this, R.string.Enter_the_old_password_correctly, Toast.LENGTH_SHORT).show();
            }
        });
        // ------------------------------------------------------------------------------------------------
        //    زر الرجوع للخلف
        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Settings.class);
                startActivity(intent);
                finish();
            }
        });

    }
}