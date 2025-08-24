package com.example.myapplication.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.UserInfoActivity;
import com.example.myapplication.UserManagementActivity;
import com.example.myapplication.database.DatabaseHelper;

public class UserFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private LinearLayout userListContainer;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user,container,false);
        dbHelper = new DatabaseHelper(requireContext());
        userListContainer = root.findViewById(R.id.user_list_container);
        loadUsers();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
                    Intent intent = new Intent(requireContext(), UserInfoActivity.class);
                    intent.putExtra("user_id", id);
                    startActivity(intent);
                });

                btnDelete.setOnClickListener(v -> {
                    if ("admin".equalsIgnoreCase(username)) {
                        // Chặn xóa tài khoản admin
                        Toast.makeText(requireContext(), "Không thể xóa tài khoản admin!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa user \"" + username + "\" không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                int rows = dbHelper.deleteUser(id);
                                if (rows > 0) {
                                    Toast.makeText(requireContext(), "Đã xóa user " + username, Toast.LENGTH_SHORT).show();
                                    loadUsers(); // reload lại danh sách
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                });



                userListContainer.addView(userItem);

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(requireContext(), "Không có người dùng nào", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
