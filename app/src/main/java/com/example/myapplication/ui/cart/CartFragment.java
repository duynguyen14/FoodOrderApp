package com.example.myapplication.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.CartItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private RecyclerView recyclerCart;
    private Button btnCheckout;
    private com.example.myapplication.ui.cart.CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPref;
    int userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        recyclerCart = view.findViewById(R.id.recycler_cart);
        btnCheckout = view.findViewById(R.id.btn_checkout);
        sharedPref = requireContext().getSharedPreferences("UserSession", MODE_PRIVATE);

        dbHelper = new DatabaseHelper(getContext());
        userID = sharedPref.getInt("user_id", 0);

        // Load dữ liệu giỏ hàng
        cartItemList = new ArrayList<>();
        cartItemList = loadCartFromDb(userID);
        cartAdapter = new com.example.myapplication.ui.cart.CartAdapter(getContext(), cartItemList);
        recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCart.setAdapter(cartAdapter);

        btnCheckout.setOnClickListener(v -> {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            double total = 0;
            for (CartItem item : cartItemList) {
                total += Integer.parseInt(item.getPrice()) * item.getQuantity();
            }

            long billId = dbHelper.insertBill(userID, today, total, "Chờ xác nhận", cartItemList);

            if (billId != -1) {
                showAlert("Thành công", "Tạo hóa đơn thành công. Mã: " + billId);
                cartItemList.clear(); // clear giỏ
                cartAdapter.notifyDataSetChanged();
            } else {
                showAlert("Lỗi", "Tạo hóa đơn thất bại!");
            }
        });

        return view;
    }

    private List<CartItem> loadCartFromDb(int userId) {
        List<CartItem> list = new ArrayList<>();
        Cursor cursor = dbHelper.getCartItemsByUserId(userId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int cartDetailId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CART_DETAIL_ID));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_NAME));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_PRICE));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_IMAGE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CART_DETAIL_QUANTITY));

                list.add(new CartItem(cartDetailId, productId, name, price, image, quantity));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
