package com.example.myapplication.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.MyFavoriteAdapter;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.HomeVerModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyFavoriteAdapter adapter;
    private ArrayList<HomeVerModel> favoriteList;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = root.findViewById(R.id.favorite_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getContext());

        loadFavoriteItems();

        return root;
    }

    private void loadFavoriteItems() {
        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPref = getContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPref.getInt("user_id", -1);

        if (userId != -1) {
            Cursor cursor = databaseHelper.getFavoriteFoodsByUserId(userId);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int foodId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_NAME));
                    int image = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_IMAGE));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_PRICE));
                    String priceStr = "Giá - " + NumberFormat.getInstance(new Locale("vi", "VN")).format(price) + "đ";

                    favoriteList.add(new HomeVerModel(foodId, image, name, "N/A", "N/A", priceStr, "N/A"));

                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        adapter = new MyFavoriteAdapter(getContext(), favoriteList, userId);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}