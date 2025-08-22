package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.myapplication.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseHelper databaseHelper;
    private ActivityMainBinding binding;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        String name = sharedPref.getString("name", "No Name");
        String email = sharedPref.getString("email", "No Email");
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textName = headerView.findViewById(R.id.textName);
        TextView textSubtitle = headerView.findViewById(R.id.textSubtitle);
        textName.setText(name);
        textSubtitle.setText(email);
//        databaseHelper = new DatabaseHelper(this);
//        databaseHelper.insertCategory("Pizza", R.drawable.pizza);
//        databaseHelper.insertCategory("HamBurger", R.drawable.hamburger);
//        databaseHelper.insertCategory("Fries", R.drawable.fried_potatoes);
//        databaseHelper.insertCategory("Cream", R.drawable.ice_cream);
//        databaseHelper.insertCategory("Sandwich", R.drawable.sandwich);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_daily_meal, R.id.nav_favorite,R.id.nav_cart)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void handleLogOut(View view){
        new AlertDialog.Builder(this).setMessage("Bạn có chắc chắn muốn đăng xuất")
                .setTitle("Thông báo")
                .setPositiveButton("OK",(dialog, which) -> {
                    sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear(); // Xoá tất cả dữ liệu
                    editor.apply();

                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Đăng xuất thành công")
                            .setPositiveButton("OK", (d, w) -> {
                                // Chuyển qua màn WelcomeActivity
                                Intent intent = new Intent(this, WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); // Đóng activity hiện tại
                            })
                            .show();

                })
                .setNegativeButton("Huỷ",null)
                .show();

    }
}