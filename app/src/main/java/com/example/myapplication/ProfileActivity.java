package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private DatabaseHelper databaseHelper;

    private EditText etName, etEmail, etGender, etDob, etPassword;
//    private ImageButton btnBack;
    TextView txtBack;
    RadioGroup rgGender;
    RadioButton rbMale;
    RadioButton rbFemale;
    private ImageView imgAvatar;
    private Button btnUpdate, btnLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_acyivity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ---- Khởi tạo các view ----
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);

        txtBack = findViewById(R.id.txtBack);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLogout = findViewById(R.id.btnLogout);

        sharedPref = this.getSharedPreferences("UserSession", MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(this);

        // ---- Lấy userId từ session ----
        int userId = sharedPref.getInt("user_id", -1);
        if (userId != -1) {
            Cursor cursor = databaseHelper.getUserById(userId);
            if (cursor != null && cursor.moveToFirst()) {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_USERNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_GENDER));
                String dob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_DOB));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PASSWORD));

                // ---- Gán dữ liệu vào EditText ----
                etName.setText(username);
                etEmail.setText(email);
                if (gender.equalsIgnoreCase("Male")) {
                    rbMale.setChecked(true);
                } else if (gender.equalsIgnoreCase("Female")) {
                    rbFemale.setChecked(true);
                }
                etDob.setText(dob);
                etPassword.setText(password);

                cursor.close();
            }
        }

        // ---- Nút quay lại ----
        txtBack.setOnClickListener(v -> finish());

        // ---- Nút cập nhật ----
        btnUpdate.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newEmail = etEmail.getText().toString();
            int selectedId = rgGender.getCheckedRadioButtonId();
            String selectedGender = "";
            if(selectedId == rbMale.getId()) selectedGender = "Nam";
            else if(selectedId == rbFemale.getId()) selectedGender = "Nữ";
            String newDob = etDob.getText().toString();
            String newPassword = etPassword.getText().toString();

//            databaseHelper.updateUser(userId, newName, newEmail, newGender, newDob, newRole);
        });

        // ---- Nút đăng xuất ----
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();
            finish(); // quay về màn hình login/main
        });
    }
}
