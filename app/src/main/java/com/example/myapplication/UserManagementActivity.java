package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.DatabaseHelper;
import com.google.android.material.appbar.MaterialToolbar;

public class UserManagementActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private LinearLayout userListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // Ẩn status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        dbHelper = new DatabaseHelper(this);
        userListContainer = findViewById(R.id.user_list_container);

        loadUsers();
        // Gắn sự kiện cho nút quay lại
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

    }

    private void loadUsers() {
        userListContainer.removeAllViews();

        Cursor cursor = dbHelper.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_USERNAME));

//                // Inflate layout item_user_admin
//                View userItem = getLayoutInflater().inflate(R.layout.item_user, null);

                View userItem = getLayoutInflater().inflate(R.layout.item_user, userListContainer, false);


                TextView tvUserId = userItem.findViewById(R.id.tvUserId);
                TextView tvUserName = userItem.findViewById(R.id.tvUserName);
                Button btnView = userItem.findViewById(R.id.btnView);
                Button btnDelete = userItem.findViewById(R.id.btnDelete);

                tvUserId.setText("ID: " + id);
                tvUserName.setText("Username: " + username);

                btnView.setOnClickListener(v -> {
                    Intent intent = new Intent(UserManagementActivity.this, UserInfoActivity.class);
                    intent.putExtra("user_id", id);
                    startActivity(intent);
                });

                btnDelete.setOnClickListener(v -> {
                    if ("admin".equalsIgnoreCase(username)) {
                        // Chặn xóa tài khoản admin
                        Toast.makeText(UserManagementActivity.this, "Không thể xóa tài khoản admin!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new androidx.appcompat.app.AlertDialog.Builder(UserManagementActivity.this)
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa user \"" + username + "\" không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                int rows = dbHelper.deleteUser(id);
                                if (rows > 0) {
                                    Toast.makeText(this, "Đã xóa user " + username, Toast.LENGTH_SHORT).show();
                                    loadUsers(); // reload lại danh sách
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                });



                userListContainer.addView(userItem);

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Không có người dùng nào", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
