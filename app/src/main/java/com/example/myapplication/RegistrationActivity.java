package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class RegistrationActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText editName;
    EditText editEmail;
    EditText editPassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(this);
        mapping();
    }
    public void mapping(){
        editName = findViewById(R.id.editTextUserName);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btn2);
    }
    public void handleRegister(View view){
        String username = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String role = "user";
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!isValidEmail(email)) {
            showAlert("Lỗi", "Email không hợp lệ");
            return;
        }

        // Gọi DB Helper để lưu
        long result = databaseHelper.insertUser(username, email, password, "", "", role);

        if (result != -1) {
            showAlert("Thành công", "Đăng ký thành công!");
            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        } else {
            showAlert("Lỗi", "Đăng ký thất bại! Email có thể đã tồn tại.");
        }
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
    public void login(View view){
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

}