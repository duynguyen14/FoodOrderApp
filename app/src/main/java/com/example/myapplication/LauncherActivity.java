package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("is_logged_in", false);

        Intent intent;
        if (isLoggedIn) {
//            // Nếu đã đăng nhập -> vào thẳng MainActivity
//            intent = new Intent(this, MainActivity.class);
            String role = sharedPref.getString("role", "user"); // mặc định user
            if ("admin".equalsIgnoreCase(role)) {
                intent = new Intent(this, AdminActivity.class);
            } else {
                intent = new Intent(this, MainActivity.class);
            }
        } else {
            // Nếu chưa đăng nhập -> vào WelcomeActivity
            intent = new Intent(this, WelcomeActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
