package com.example.numbersequencegame;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.numbersequencegame.databinding.ActivityMainBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
ActivityMainBinding binding ;
SharedPreferences preferences;
int age = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//  ____________________________________________________________________________________________

// عمل انتقل للكاميرا
        ActivityResultLauncher<String> arl = registerForActivityResult(new ActivityResultContracts.GetContent()
                ,new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        binding.imageView.setImageURI(result);
                    }
                });
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch("image/*");
            }
        });


//  ____________________________________________________________________________________________

// permission (camera)
        ActivityResultLauncher<String> moh = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                    }
                }
        );
        moh.launch(Manifest.permission.CAMERA);

//  ____________________________________________________________________________________________

// Datepicker
        binding.tvBirthdate.setOnClickListener(new View.OnClickListener() {
            // هاد السطر بيقول للبرنامج ما تدق على الخطأ
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int month, int day) {
                                binding.tvBirthdate.setText(year+"/"+(month+1)+"/"+day);
                                // احتساب العمر
                                  age = now.get(Calendar.YEAR)-year;

                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getSupportFragmentManager(),"date");
            }
        });

//  ____________________________________________________________________________________________


// Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_country);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

//  ____________________________________________________________________________________________

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailValidator(binding.etEmail);
            }
        });
     }
    private void emailValidator(EditText etEmail) {
        String emailToText = binding.etEmail.getText().toString();
        String EtPassword = binding.etPassword.getText().toString();
        String EtRePassword = binding.etRePassword.getText().toString();
        if (!binding.etFullName.getText().toString().isEmpty()){
             if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
                 if (!binding.etUserName.getText().toString().isEmpty()){
                     //شرط تساوي كلمة المرور مع المربعين
                     if (!EtPassword.isEmpty() && EtRePassword.equals(EtPassword) && EtPassword.length() ==4){
                         if (!binding.tvBirthdate.getText().toString().isEmpty()){

                                 preferences = getSharedPreferences("information",MODE_PRIVATE);
                                 SharedPreferences.Editor editor = preferences.edit();
                                 editor.putString("Full_Name",binding.etFullName.getText().toString());
                                 editor.putString("Email_Address",binding.etEmail.getText().toString());
                                 editor.putString("User_Name",binding.etUserName.getText().toString());
                                 editor.putString("Password",binding.etPassword.getText().toString());
                                 editor.putString("Birthdate",binding.tvBirthdate.getText().toString());
                                 editor.putInt("KEY_AGE",age);
                                 editor.apply();

                                 Intent intent = new Intent(getApplicationContext(),Login.class);
                                 startActivity(intent);
                                 finish();
                                 // custom Tost عند دخول صفحة login
                                 View v = getLayoutInflater().inflate(R.layout.custom_tost_login,null);
                                 Toast toast = new Toast(getBaseContext());
                                 toast.setView(v);
                                 toast.setDuration(Toast.LENGTH_LONG);
                                 toast.show();
                         }
                         else
                             binding.tvBirthdate.setError("Plese Enter Birthdate");
                     }
                     else{
                         binding.etPassword.setError("Plese Enter Password");
                         binding.etRePassword.setError("Plese Enter Re_Password");}
                 }
                 else
                     binding.etUserName.setError("Plese Enter User Name");

             } else
                 binding.etEmail.setError("Not Succeeded");
        }
        else
            binding.etFullName.setError("Plese Enter Full Name");
    }


    //تابع لل spinner
        @Override
        public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
        }
        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){
        }


    }
