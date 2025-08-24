package com.example.myapplication.ui.product_management;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;

public class ProductFragment extends Fragment implements ProductAdapter.OnProductClickListener{
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product,container,false);
        rvProducts = root.findViewById(R.id.rvProducts);
        btnPrevious = root.findViewById(R.id.btnPrevious);
        btnNext = root.findViewById(R.id.btnNext);
        btnAddProduct = root.findViewById(R.id.btnAddProduct);
        tvPageInfo = root.findViewById(R.id.tvPageInfo);
        progressBar = root.findViewById(R.id.progressBar);

        dbHelper = new DatabaseHelper(requireContext());
        SharedPreferences prefs = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int savedDbVersion = prefs.getInt("db_version", 0);
        rvProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ProductAdapter(requireContext(), null, this);
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
            Intent intent = new Intent(requireContext(), ProductUpdate.class);
            productLauncher.launch(intent);
        });

        // Khởi tạo ActivityResultLauncher
        productLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadProducts(currentPage);
                        Toast.makeText(requireContext(), "Sản phẩm đã được cập nhật hoặc thêm mới", Toast.LENGTH_SHORT).show();
                    }
                });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
//    @Override
    public void onEditClick(int foodId) {
        Toast.makeText(requireContext(), "Chỉnh sửa sản phẩm ID: " + foodId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(requireContext(), ProductUpdate.class);
        intent.putExtra("FOOD_ID", foodId);
        productLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(int foodId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int result = dbHelper.deleteFood(foodId);
                    if (result > 0) {
                        Toast.makeText(requireContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(requireContext(), "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
