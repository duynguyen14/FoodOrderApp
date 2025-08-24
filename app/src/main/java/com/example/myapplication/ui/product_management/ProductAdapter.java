package com.example.myapplication.ui.product_management;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private Cursor cursor;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onEditClick(int foodId);
        void onDeleteClick(int foodId);
    }

    public ProductAdapter(Context context, Cursor cursor, OnProductClickListener listener) {
        this.context = context;
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public void closeCursor() {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

@SuppressLint("Range")
@Override
public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    if (!cursor.moveToPosition(position)) {
        return;
    }
    int foodId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_ID));
    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_NAME));
    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_PRICE));
    int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_IMAGE));
    String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_DESCRIPTION));
    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_QUANTITY));
    int soldCount = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_SOLID_COUNT));
    // Dòng này cần truy vấn để lấy CategoryName
    String categoryName = getCategoryName(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_ID)));

    holder.tvName.setText(name);
    holder.tvPrice.setText(String.format("$%.2f", price));
    holder.tvDescription.setText(description);
    holder.tvCategoryName.setText(categoryName);
    holder.tvQuantity.setText(String.valueOf(quantity));
    holder.tvSold.setText(String.valueOf(soldCount));
    Glide.with(context).load(imageResId).into(holder.ivImage);

    holder.btnEdit.setOnClickListener(v -> {
        if (listener != null) listener.onEditClick(foodId);
    });
    holder.btnDelete.setOnClickListener(v -> {
        if (listener != null) listener.onDeleteClick(foodId);
    });
}

    // Sửa phương thức này để sử dụng cursor đã có
    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvPrice, tvDescription, tvCategoryName, tvQuantity, tvSold;
        Button btnEdit, btnDelete;

        ProductViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.item_img);
            tvName = itemView.findViewById(R.id.item_name);
            tvPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvSold = itemView.findViewById(R.id.tvSold);
        }
    }

    private String getCategoryName(int categoryId) {
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor categoryCursor = null;
        String categoryName = "N/A";
        try {
            categoryCursor = db.query(DatabaseHelper.TABLE_CATEGORY,
                    new String[]{DatabaseHelper.COLUMN_CATEGORY_NAME},
                    DatabaseHelper.COLUMN_CATEGORY_ID + "=?",
                    new String[]{String.valueOf(categoryId)},
                    null, null, null);
            if (categoryCursor != null && categoryCursor.moveToFirst()) {
                categoryName = categoryCursor.getString(categoryCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_NAME));
            }
        } finally {
            if (categoryCursor != null) {
                categoryCursor.close();
            }
        }
        return categoryName;
    }
}