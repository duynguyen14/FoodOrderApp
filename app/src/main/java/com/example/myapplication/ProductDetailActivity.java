//package com.example.myapplication;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.myapplication.database.DatabaseHelper;
//
//public class ProductDetailActivity extends AppCompatActivity {
//
//    ImageView productImage;
//    TextView productName, productPrice, productTime, productRating, txtQuantity;
//    Button btnAddCart, btnAddFavorite, btnIncrease, btnDecrease;
//    int quantity = 1;
//    DatabaseHelper databaseHelper;
//    int userID;
//    int productID;
//    private SharedPreferences sharedPref;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_product_detal);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        databaseHelper = new DatabaseHelper(this);
//        sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
//        userID = sharedPref.getInt("user_id", 0);
//        Log.d("CartFragment", "userID from SharedPreferences: " + userID);
//        productID = getIntent().getIntExtra("productId",0);
//        String name = getIntent().getStringExtra("productName");
//        String price = getIntent().getStringExtra("productPrice");
//        int imageRes = getIntent().getIntExtra("productImage", 0);
//        String time = getIntent().getStringExtra("productTime");
//        String rating = getIntent().getStringExtra("productRating");
//        int qu = Integer.parseInt(getIntent().getStringExtra("productQuantity"));
//        mapping();
//        productName.setText(name);
//        productPrice.setText(price);
//        productTime.setText(time);
//        productRating.setText(rating);
//
//        if (imageRes != 0) {
//            productImage.setImageResource(imageRes);
//        }
//        // Logic tăng giảm số lượng
//        btnIncrease.setOnClickListener(v -> {
//            quantity++;
//            txtQuantity.setText(String.valueOf(quantity));
//        });
//
//        btnDecrease.setOnClickListener(v -> {
//            if (quantity > 1) {
//                quantity--;
//                txtQuantity.setText(String.valueOf(quantity));
//            }
//        });
//
//    }
//    private void showAlert(String title, String message) {
//        new AlertDialog.Builder(this)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton("OK", null)
//                .show();
//    }
//    public void mapping(){
//        productImage = findViewById(R.id.product_image);
//        productName = findViewById(R.id.product_name);
//        productPrice = findViewById(R.id.product_price);
//        productTime = findViewById(R.id.product_time);
//        productRating = findViewById(R.id.product_rating);
//        txtQuantity = findViewById(R.id.txt_quantity);
//        btnAddCart = findViewById(R.id.btn_add_cart);
//        btnAddFavorite = findViewById(R.id.btn_add_favorite);
//        btnIncrease = findViewById(R.id.btn_increase);
//        btnDecrease = findViewById(R.id.btn_decrease);
//    }
//    public void handleAddCart(View v) {
//        databaseHelper.addCart(userID, productID, quantity);
//        showAlert("Giỏ hàng", "Đã thêm " + quantity + " sản phẩm vào giỏ!");
//    }
//    public void handleAddFavorite(View v){
//        databaseHelper.addFavorite(userID, productID);
//        showAlert("Yêu thích", "Sản phẩm đã được thêm vào danh sách yêu thích!");
//    }
//    public void handleBack(View v){
//        finish();
//    }
//}
package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.database.DatabaseHelper;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productPrice, productTime, productRating, txtQuantity;
    Button btnAddCart, btnAddFavorite, btnIncrease, btnDecrease;
    int quantity = 1;
    DatabaseHelper databaseHelper;
    int userID;
    int productID;
    private SharedPreferences sharedPref;
    int maxQuantity; // Biến để lưu số lượng sản phẩm tối đa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(this);
        sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        userID = sharedPref.getInt("user_id", 0);
        Log.d("CartFragment", "userID from SharedPreferences: " + userID);
        productID = getIntent().getIntExtra("productId",0);
        String name = getIntent().getStringExtra("productName");
        String price = getIntent().getStringExtra("productPrice");
        int imageRes = getIntent().getIntExtra("productImage", 0);
        String time = getIntent().getStringExtra("productTime");
        String rating = getIntent().getStringExtra("productRating");
        maxQuantity = Integer.parseInt(getIntent().getStringExtra("productQuantity"));
        mapping();
        productName.setText(name);
        productPrice.setText(price);
        productTime.setText(time);
        productRating.setText(rating);

        if (imageRes != 0) {
            productImage.setImageResource(imageRes);
        }

        // Cập nhật trạng thái ban đầu của các nút
        updateButtonState();

        // Logic tăng số lượng
        btnIncrease.setOnClickListener(v -> {
            if (quantity < maxQuantity) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
                updateButtonState();
            } else {
                showAlert("Thông báo", "Số lượng sản phẩm đã đạt tối đa.");
            }
        });

        // Logic giảm số lượng
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                txtQuantity.setText(String.valueOf(quantity));
                updateButtonState();
            }
        });

    }
    private void updateButtonState() {
        btnDecrease.setEnabled(quantity > 1);
        btnIncrease.setEnabled(quantity < maxQuantity);
    }
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    public void mapping(){
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productTime = findViewById(R.id.product_time);
        productRating = findViewById(R.id.product_rating);
        txtQuantity = findViewById(R.id.txt_quantity);
        btnAddCart = findViewById(R.id.btn_add_cart);
        btnAddFavorite = findViewById(R.id.btn_add_favorite);
        btnIncrease = findViewById(R.id.btn_increase);
        btnDecrease = findViewById(R.id.btn_decrease);
    }
    public void handleAddCart(View v) {
        databaseHelper.addCart(userID, productID, quantity);
        showAlert("Giỏ hàng", "Đã thêm " + quantity + " sản phẩm vào giỏ!");
    }
    public void handleAddFavorite(View v){
        databaseHelper.addFavorite(userID, productID);
        showAlert("Yêu thích", "Sản phẩm đã được thêm vào danh sách yêu thích!");
    }
    public void handleBack(View v){
        finish();
    }
}