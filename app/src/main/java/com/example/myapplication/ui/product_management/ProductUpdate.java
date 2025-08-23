//package com.example.myapplication.ui.product_management;
//
//import android.annotation.SuppressLint;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.example.myapplication.R;
//import com.example.myapplication.database.DatabaseHelper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ProductUpdate extends AppCompatActivity {
//
//    private DatabaseHelper dbHelper;
//    private EditText etName, etPrice, etDescription, etQuantity;
//    private Spinner spinnerCategory, spinnerImage;
//    private ImageView ivProductImage;
//    private Button btnSave;
//    private int foodId = -1; // Khởi tạo với giá trị -1 để xác định sản phẩm mới
//    private Map<String, Integer> imageMap;
//    private List<String> categoryNames;
//    private List<Integer> categoryIds;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_product);
//
//        // Khởi tạo các view
//        etName = findViewById(R.id.etName);
//        etPrice = findViewById(R.id.etPrice);
//        etDescription = findViewById(R.id.etDescription);
//        spinnerCategory = findViewById(R.id.spinnerCategory);
//        spinnerImage = findViewById(R.id.spinnerImage);
//        ivProductImage = findViewById(R.id.ivProductImage);
//        btnSave = findViewById(R.id.btnSave);
//        etQuantity = findViewById(R.id.etQuantity);
//        dbHelper = new DatabaseHelper(this);
//
//        // Lấy foodId từ Intent
//        if (getIntent().hasExtra("FOOD_ID")) {
//            foodId = getIntent().getIntExtra("FOOD_ID", -1);
//        }
//
//        // Tải danh sách danh mục
//        loadCategories();
//
//        // Tải danh sách ảnh từ res/drawable
//        loadImageResources();
//
//        // Tải thông tin sản phẩm nếu đang chỉnh sửa
//        if (foodId != -1) {
//            loadProductData();
//            btnSave.setText("Lưu thay đổi");
//        } else {
//            btnSave.setText("Thêm sản phẩm");
//            // Hiển thị ảnh mặc định
//            if (!imageMap.isEmpty()) {
//                Glide.with(this).load(imageMap.get(new ArrayList<>(imageMap.keySet()).get(0))).into(ivProductImage);
//            }
//        }
//
//        // Xử lý nút Lưu
//        btnSave.setOnClickListener(v -> saveProduct());
//    }
//
//    private void loadCategories() {
//        categoryNames = new ArrayList<>();
//        categoryIds = new ArrayList<>();
//        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY, null);
//        while (cursor.moveToNext()) {
//            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
//            categoryIds.add(id);
//            categoryNames.add(name);
//        }
//        cursor.close();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(adapter);
//    }
//
//    private void loadImageResources() {
//        imageMap = new HashMap<>();
//        imageMap.put("breakfast", R.drawable.breakfast);
//        imageMap.put("burger2", R.drawable.burger2);
//        imageMap.put("burger4", R.drawable.burger4);
//        imageMap.put("coffe", R.drawable.coffe);
//        imageMap.put("dinner", R.drawable.dinner);
//        imageMap.put("fav1", R.drawable.fav1);
//        imageMap.put("fav2", R.drawable.fav2);
//        imageMap.put("fav3", R.drawable.fav3);
//        imageMap.put("fried_potatoes", R.drawable.fried_potatoes);
//        imageMap.put("fries1", R.drawable.fries1);
//        imageMap.put("fries2", R.drawable.fries2);
//        imageMap.put("fries3", R.drawable.fries3);
//        imageMap.put("fries4", R.drawable.fries4);
//        imageMap.put("hamburger", R.drawable.hamburger);
//        imageMap.put("ice_cream", R.drawable.ice_cream);
//        imageMap.put("ice_cream1", R.drawable.icecream1);
//        imageMap.put("ice_cream2", R.drawable.icecream2);
//        imageMap.put("ice_cream3", R.drawable.icecream3);
//        imageMap.put("ice_cream4", R.drawable.icecream4);
//        imageMap.put("lunch", R.drawable.lunch);
//        imageMap.put("pizza", R.drawable.pizza);
//        imageMap.put("pizza1", R.drawable.pizza1);
//        imageMap.put("pizza2", R.drawable.pizza2);
//        imageMap.put("pizza3", R.drawable.pizza3);
//        imageMap.put("pizza4", R.drawable.pizza4);
//        imageMap.put("ratingbar", R.drawable.ratingbar);
//        imageMap.put("s1", R.drawable.s1);
//        imageMap.put("s2", R.drawable.s2);
//        imageMap.put("s3", R.drawable.s3);
//        imageMap.put("s4", R.drawable.s4);
//        imageMap.put("sandwich", R.drawable.sandwich);
//        imageMap.put("sandwich1", R.drawable.sandwich1);
//        imageMap.put("sandwich2", R.drawable.sandwich2);
//        imageMap.put("sandwich3", R.drawable.sandwich3);
//        imageMap.put("sandwich4", R.drawable.sandwich4);
//        imageMap.put("sweets", R.drawable.sweets);
//        imageMap.put("ver1", R.drawable.ver1);
//        imageMap.put("ver2", R.drawable.ver2);
//        imageMap.put("ver3", R.drawable.ver3);
//        List<String> imageNames = new ArrayList<>(imageMap.keySet());
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imageNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerImage.setAdapter(adapter);
//    }
//
//    private void loadProductData() {
//        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
//                "SELECT * FROM " + DatabaseHelper.TABLE_FOOD + " WHERE " + DatabaseHelper.COLUMN_FOOD_ID + " = ?",
//                new String[]{String.valueOf(foodId)});
//        if (cursor.moveToFirst()) {
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME));
//            @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_PRICE));
//            @SuppressLint("Range") int imageResId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_IMAGE));
//            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_DESCRIPTION));
//            @SuppressLint("Range") int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
//
//            etName.setText(name);
//            etPrice.setText(String.format("%.2f", price));
//            etDescription.setText(description);
//            Glide.with(this).load(imageResId).into(ivProductImage);
//            int categoryIndex = categoryIds.indexOf(categoryId);
//            if (categoryIndex >= 0) {
//                spinnerCategory.setSelection(categoryIndex);
//            }
//            String currentImageName = null;
//            for (Map.Entry<String, Integer> entry : imageMap.entrySet()) {
//                if (entry.getValue() == imageResId) {
//                    currentImageName = entry.getKey();
//                    break;
//                }
//            }
//            if (currentImageName != null) {
//                int imageIndex = new ArrayList<>(imageMap.keySet()).indexOf(currentImageName);
//                if (imageIndex >= 0) {
//                    spinnerImage.setSelection(imageIndex);
//                }
//            }
//        } else {
//            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        cursor.close();
//    }
//
//    private void saveProduct() {
//        String name = etName.getText().toString().trim();
//        String priceStr = etPrice.getText().toString().trim();
//        String description = etDescription.getText().toString().trim();
//        int categoryId = categoryIds.get(spinnerCategory.getSelectedItemPosition());
//        String imageName = (String) spinnerImage.getSelectedItem();
//        int imageResId = imageMap.get(imageName);
//
//        if (name.isEmpty() || priceStr.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập đầy đủ tên và giá", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        double price;
//        try {
//            price = Double.parseDouble(priceStr);
//        } catch (NumberFormatException e) {
//            Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Logic thêm mới hoặc cập nhật
//        if (foodId == -1) {
//            long newRowId = dbHelper.insertFood(categoryId, name, price, imageResId, description, 10, 10);
//            if (newRowId > 0) {
//                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                setResult(RESULT_OK); // Thiết lập kết quả thành công
//                finish();
//            } else {
//                Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//                Log.e("ProductUpdate", "Thêm thất bại");
//            }
//        } else {
//            int result = dbHelper.updateFood(foodId, categoryId, name, price, imageResId, description, 10, 10);
//            if (result > 0) {
//                Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                setResult(RESULT_OK); // Thiết lập kết quả thành công
//                finish();
//            } else {
//                Toast.makeText(this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//                Log.e("ProductUpdate", "Cập nhật thất bại cho foodId: " + foodId);
//            }
//        }
//    }
//}

package com.example.myapplication.ui.product_management;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductUpdate extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etName, etPrice, etDescription, etQuantity;
    private Spinner spinnerCategory, spinnerImage;
    private ImageView ivProductImage;
    private Button btnSave, btnClose;
    private int foodId = -1; // Khởi tạo với giá trị -1 để xác định sản phẩm mới
    private Map<String, Integer> imageMap;
    private List<String> categoryNames;
    private List<Integer> categoryIds;
    private int soldCount = 0; // Thêm soldCount để lưu giá trị ban đầu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        // Khởi tạo các view
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerImage = findViewById(R.id.spinnerImage);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnSave = findViewById(R.id.btnSave);
        etQuantity = findViewById(R.id.etQuantity);
        btnClose = findViewById(R.id.btnCancel);
        dbHelper = new DatabaseHelper(this);

        // Lấy foodId từ Intent
        if (getIntent().hasExtra("FOOD_ID")) {
            foodId = getIntent().getIntExtra("FOOD_ID", -1);
        }

        // Tải danh sách danh mục
        loadCategories();

        // Tải danh sách ảnh từ res/drawable
        loadImageResources();

        // Tải thông tin sản phẩm nếu đang chỉnh sửa
        if (foodId != -1) {
            loadProductData();
            btnSave.setText("Lưu thay đổi");
        } else {
            btnSave.setText("Thêm sản phẩm");
            // Hiển thị ảnh mặc định
            if (!imageMap.isEmpty()) {
                Glide.with(this).load(imageMap.get(new ArrayList<>(imageMap.keySet()).get(0))).into(ivProductImage);
            }
        }

        // Xử lý nút Lưu
        btnSave.setOnClickListener(v -> saveProduct());
        btnClose.setOnClickListener(v -> finish());
    }

    private void loadCategories() {
        categoryNames = new ArrayList<>();
        categoryIds = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
            categoryIds.add(id);
            categoryNames.add(name);
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void loadImageResources() {
        imageMap = new HashMap<>();
        imageMap.put("breakfast", R.drawable.breakfast);
        imageMap.put("burger2", R.drawable.burger2);
        imageMap.put("burger4", R.drawable.burger4);
        imageMap.put("coffe", R.drawable.coffe);
        imageMap.put("dinner", R.drawable.dinner);
        imageMap.put("fav1", R.drawable.fav1);
        imageMap.put("fav2", R.drawable.fav2);
        imageMap.put("fav3", R.drawable.fav3);
        imageMap.put("fried_potatoes", R.drawable.fried_potatoes);
        imageMap.put("fries1", R.drawable.fries1);
        imageMap.put("fries2", R.drawable.fries2);
        imageMap.put("fries3", R.drawable.fries3);
        imageMap.put("fries4", R.drawable.fries4);
        imageMap.put("hamburger", R.drawable.hamburger);
        imageMap.put("ice_cream", R.drawable.ice_cream);
        imageMap.put("ice_cream1", R.drawable.icecream1);
        imageMap.put("ice_cream2", R.drawable.icecream2);
        imageMap.put("ice_cream3", R.drawable.icecream3);
        imageMap.put("ice_cream4", R.drawable.icecream4);
        imageMap.put("lunch", R.drawable.lunch);
        imageMap.put("pizza", R.drawable.pizza);
        imageMap.put("pizza1", R.drawable.pizza1);
        imageMap.put("pizza2", R.drawable.pizza2);
        imageMap.put("pizza3", R.drawable.pizza3);
        imageMap.put("pizza4", R.drawable.pizza4);
        imageMap.put("ratingbar", R.drawable.ratingbar);
        imageMap.put("s1", R.drawable.s1);
        imageMap.put("s2", R.drawable.s2);
        imageMap.put("s3", R.drawable.s3);
        imageMap.put("s4", R.drawable.s4);
        imageMap.put("sandwich", R.drawable.sandwich);
        imageMap.put("sandwich1", R.drawable.sandwich1);
        imageMap.put("sandwich2", R.drawable.sandwich2);
        imageMap.put("sandwich3", R.drawable.sandwich3);
        imageMap.put("sandwich4", R.drawable.sandwich4);
        imageMap.put("sweets", R.drawable.sweets);
        imageMap.put("ver1", R.drawable.ver1);
        imageMap.put("ver2", R.drawable.ver2);
        imageMap.put("ver3", R.drawable.ver3);
        List<String> imageNames = new ArrayList<>(imageMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imageNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImage.setAdapter(adapter);
    }

    private void loadProductData() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_FOOD + " WHERE " + DatabaseHelper.COLUMN_FOOD_ID + " = ?",
                new String[]{String.valueOf(foodId)});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME));
            @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_PRICE));
            @SuppressLint("Range") int imageResId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_IMAGE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_DESCRIPTION));
            @SuppressLint("Range") int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_QUANTITY));
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_SOLD_COUNT)); // Lấy giá trị soldCount hiện tại

            etName.setText(name);
            etPrice.setText(String.format("%.2f", price));
            etDescription.setText(description);
            etQuantity.setText(String.valueOf(quantity));
            soldCount = count; // Lưu giá trị soldCount hiện tại vào biến toàn cục
            Glide.with(this).load(imageResId).into(ivProductImage);
            int categoryIndex = categoryIds.indexOf(categoryId);
            if (categoryIndex >= 0) {
                spinnerCategory.setSelection(categoryIndex);
            }
            String currentImageName = null;
            for (Map.Entry<String, Integer> entry : imageMap.entrySet()) {
                if (entry.getValue() == imageResId) {
                    currentImageName = entry.getKey();
                    break;
                }
            }
            if (currentImageName != null) {
                int imageIndex = new ArrayList<>(imageMap.keySet()).indexOf(currentImageName);
                if (imageIndex >= 0) {
                    spinnerImage.setSelection(imageIndex);
                }
            }
        } else {
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        }
        cursor.close();
    }

    private void saveProduct() {
        String name = etName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        int categoryId = categoryIds.get(spinnerCategory.getSelectedItemPosition());
        String imageName = (String) spinnerImage.getSelectedItem();
        int imageResId = imageMap.get(imageName);

        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int quantity;
        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Logic thêm mới hoặc cập nhật
        if (foodId == -1) {
            // Trường hợp thêm mới: soldCount = 0
            long newRowId = dbHelper.insertFood(categoryId, name, price, imageResId, description, quantity, 0); // soldCount = 0
            if (newRowId > 0) {
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // Thiết lập kết quả thành công
                finish();
            } else {
                Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                Log.e("ProductUpdate", "Thêm thất bại");
            }
        } else {
            // Trường hợp cập nhật: soldCount giữ nguyên giá trị đã lấy được
            int result = dbHelper.updateFood(foodId, categoryId, name, price, imageResId, description, quantity, soldCount); // Dùng soldCount đã lấy từ CSDL
            if (result > 0) {
                Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // Thiết lập kết quả thành công
                finish();
            } else {
                Toast.makeText(this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                Log.e("ProductUpdate", "Cập nhật thất bại cho foodId: " + foodId);
            }
        }
    }
}