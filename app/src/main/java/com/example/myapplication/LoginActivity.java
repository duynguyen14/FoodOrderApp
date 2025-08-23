package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
     EditText editEmail;
     EditText editPassword;
     Button btn_Login;
     DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        dbHelper = new DatabaseHelper(this);
    }
    private void saveUserSession(int id, String email, String role, String name) {
        SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_id", id);
        editor.putString("email", email);
        editor.putString("role", role);
        editor.putString("name",name);
        editor.putBoolean("is_logged_in", true); // Đánh dấu đã login
        editor.apply();
    }
    public void register(View view){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
    public void mapping(){
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        btn_Login = findViewById(R.id.btn2);
    }
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    private boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void handleLogin(View view){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!isValidEmail(email)) {
            showAlert("Lỗi", "Email không hợp lệ");
            return;
        }
        Cursor cursor = dbHelper.findUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PASSWORD));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ROLE));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_USERNAME));
            if (storedPassword.equals(password)) {
                // Chia màn hình theo role
                saveUserSession(userId, email, role,name);
                if ("admin".equalsIgnoreCase(role)) {
                    Intent intent = new Intent(this, AdminActivity.class);
                    startActivity(intent);
                } else if ("user".equalsIgnoreCase(role)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                showAlert("Lỗi", "Sai mật khẩu!");
            }
        } else {
            showAlert("Thông báo", "Email chưa được đăng ký vui lòng kiểm tra lại");
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}