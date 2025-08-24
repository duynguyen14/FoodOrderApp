//package com.example.myapplication.ui.product_management;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.R;
//import com.example.myapplication.database.DatabaseHelper;
//
//public class ProductManagement extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
//
//    private RecyclerView rvProducts;
//    private Button btnPrevious, btnNext, btnAddProduct;
//    private TextView tvPageInfo;
//    private ProgressBar progressBar;
//    private DatabaseHelper dbHelper;
//    private ProductAdapter adapter;
//    private int currentPage = 1;
//    private final int PAGE_SIZE = 10;
//    private ActivityResultLauncher<Intent> productLauncher;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_product_management);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Khởi tạo các view
//        rvProducts = findViewById(R.id.rvProducts);
//        btnPrevious = findViewById(R.id.btnPrevious);
//        btnNext = findViewById(R.id.btnNext);
//        btnAddProduct = findViewById(R.id.btnAddProduct); // Nút thêm sản phẩm mới
//        tvPageInfo = findViewById(R.id.tvPageInfo);
//        progressBar = findViewById(R.id.progressBar);
//
//        dbHelper = new DatabaseHelper(this);
//
//        // Chèn dữ liệu mẫu nếu chưa có
//        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
//        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
//        if (isFirstRun) {
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            db.delete(DatabaseHelper.TABLE_FOOD, null, null);
//            db.delete(DatabaseHelper.TABLE_CATEGORY, null, null);
//            dbHelper.insertSampleData();
//            prefs.edit().putBoolean("isFirstRun", false).apply();
//        }
//
//        // Thiết lập RecyclerView
//        rvProducts.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ProductAdapter(this, null, this);
//        rvProducts.setAdapter(adapter);
//
//        // Tải trang đầu tiên
//        loadProducts(currentPage);
//
//        // Xử lý nút Previous
//        btnPrevious.setOnClickListener(v -> {
//            if (currentPage > 1) {
//                currentPage--;
//                loadProducts(currentPage);
//            }
//        });
//
//        // Xử lý nút Next
//        btnNext.setOnClickListener(v -> {
//            int totalProducts = dbHelper.getTotalProducts();
//            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
//            if (currentPage < totalPages) {
//                currentPage++;
//                loadProducts(currentPage);
//            }
//        });
//
//        // Xử lý nút Thêm sản phẩm mới
//        btnAddProduct.setOnClickListener(v -> {
//            Intent intent = new Intent(ProductManagement.this, ProductUpdate.class);
//            productLauncher.launch(intent);
//        });
//
//        // Khởi tạo ActivityResultLauncher
//        productLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK) {
//                        // Tải lại danh sách sản phẩm khi cập nhật hoặc thêm mới thành công
//                        loadProducts(currentPage);
//                        Toast.makeText(this, "Sản phẩm đã được cập nhật hoặc thêm mới", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void loadProducts(int page) {
//        progressBar.setVisibility(View.VISIBLE);
//        Cursor cursor = dbHelper.getProductsByPage(page, PAGE_SIZE);
//        adapter.swapCursor(cursor);
//        int totalProducts = dbHelper.getTotalProducts();
//        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
//        tvPageInfo.setText(String.format("Page %d of %d", page, totalPages));
//        btnPrevious.setEnabled(page > 1);
//        btnNext.setEnabled(page < totalPages);
//        progressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onEditClick(int foodId) {
//        Toast.makeText(this, "Chỉnh sửa sản phẩm ID: " + foodId, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(ProductManagement.this, ProductUpdate.class);
//        intent.putExtra("FOOD_ID", foodId);
//        productLauncher.launch(intent); // Sử dụng launcher để khởi chạy
//    }
//
//    @Override
//    public void onDeleteClick(int foodId) {
//
//        int result = dbHelper.deleteFood(foodId);
//        if (result > 0) {
//            Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
//            loadProducts(currentPage);
//        } else {
//            Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
package com.example.myapplication.ui.product_management;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;

public class ProductManagement extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private RecyclerView rvProducts;
    private Button btnPrevious, btnNext, btnAddProduct;
    private TextView tvPageInfo;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;
    private ProductAdapter adapter;
    private int currentPage = 1;
    private final int PAGE_SIZE = 4;
    private ActivityResultLauncher<Intent> productLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view
        rvProducts = findViewById(R.id.rvProducts);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        tvPageInfo = findViewById(R.id.tvPageInfo);
        progressBar = findViewById(R.id.progressBar);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int savedDbVersion = prefs.getInt("db_version", 0);

        // Thiết lập RecyclerView
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, null, this);
        rvProducts.setAdapter(adapter);

        // Tải trang đầu tiên
        loadProducts(currentPage);

        // Xử lý nút Previous
        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadProducts(currentPage);
            }
        });

        // Xử lý nút Next
        btnNext.setOnClickListener(v -> {
            int totalProducts = dbHelper.getTotalProducts();
            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
            if (currentPage < totalPages) {
                currentPage++;
                loadProducts(currentPage);
            }
        });

        // Xử lý nút Thêm sản phẩm mới
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductManagement.this, ProductUpdate.class);
            productLauncher.launch(intent);
        });

        // Khởi tạo ActivityResultLauncher
        productLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadProducts(currentPage);
                        Toast.makeText(this, "Sản phẩm đã được cập nhật hoặc thêm mới", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadProducts(int page) {
        progressBar.setVisibility(View.VISIBLE);
        Cursor cursor = dbHelper.getProductsByPage(page, PAGE_SIZE);
        adapter.swapCursor(cursor);
        int totalProducts = dbHelper.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
        tvPageInfo.setText(String.format("Page %d of %d", page, totalPages));
        btnPrevious.setEnabled(page > 1);
        btnNext.setEnabled(page < totalPages);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onEditClick(int foodId) {
        Toast.makeText(this, "Chỉnh sửa sản phẩm ID: " + foodId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProductManagement.this, ProductUpdate.class);
        intent.putExtra("FOOD_ID", foodId);
        productLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(int foodId) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int result = dbHelper.deleteFood(foodId);
                    if (result > 0) {
                        Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();

                        // Cập nhật lại số trang sau khi xóa
                        int totalProducts = dbHelper.getTotalProducts();
                        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

                        // Nếu trang hiện tại không còn sản phẩm, chuyển về trang trước
                        if (currentPage > totalPages && totalPages > 0) {
                            currentPage = totalPages;
                        } else if (totalPages == 0) {
                            currentPage = 1;
                        }
                        loadProducts(currentPage);
                    } else {
                        Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

}