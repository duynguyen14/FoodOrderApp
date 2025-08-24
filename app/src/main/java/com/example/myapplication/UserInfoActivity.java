package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.DatabaseHelper;

public class UserInfoActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView tvUserId, tvUserName, tvUserEmail, tvUserPassword, tvUserGender, tvUserDob, tvUserRole;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Ẩn status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        dbHelper = new DatabaseHelper(this);

        tvUserId = findViewById(R.id.tvUserId);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserPassword = findViewById(R.id.tvUserPassword);
        tvUserGender = findViewById(R.id.tvUserGender);
        tvUserDob = findViewById(R.id.tvUserDob);
        tvUserRole = findViewById(R.id.tvUserRole);

        userId = getIntent().getIntExtra("user_id", -1);

        if (userId != -1) {
            loadUserDetail(userId);
        }
        // Gắn sự kiện cho nút quay lại
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadUserDetail(int id) {
        Cursor cursor = dbHelper.getUserById(id);
        if (cursor != null && cursor.moveToFirst()) {
            tvUserId.setText("ID: " + cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)));
            tvUserName.setText("Username: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_USERNAME)));
            tvUserEmail.setText("Email: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL)));
            tvUserPassword.setText("Password: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PASSWORD)));
            tvUserGender.setText("Giới tính: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_GENDER)));
            tvUserDob.setText("Ngày sinh: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_DOB)));
            tvUserRole.setText("Vai trò: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ROLE)));
        } else {
            tvUserName.setText("Không tìm thấy user có ID = " + id);
        }
        if (cursor != null) cursor.close();
    }
}
