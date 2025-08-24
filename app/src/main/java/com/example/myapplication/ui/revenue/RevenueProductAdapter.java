package com.example.myapplication.ui.revenue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;

public class RevenueProductAdapter extends RecyclerView.Adapter<RevenueProductAdapter.ProductViewHolder> {

    private Context context;
    private Cursor cursor;

    public RevenueProductAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_revenue_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_NAME));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_PRICE));
        // Lấy TotalQuantity từ Cursor
        int soldQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("TotalQuantity"));
        // Lấy ID hình ảnh
        int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOOD_IMAGE));
        int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_ID));
        String categoryName = getCategoryName(categoryId);
        holder.tvName.setText(name);
        holder.tvPrice.setText(String.format("Giá: $%.2f", price));
        holder.tvCategoryName.setText("Danh mục: " + categoryName);
        holder.tvQuantity.setText("Số lượng bán: " + soldQuantity);

        // Sử dụng Glide để tải ảnh
        Glide.with(holder.itemView.getContext())
                .load(imageResId)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvCategoryName, tvQuantity;
        public ImageView ivImage;

        ProductViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_name);
            tvPrice = itemView.findViewById(R.id.tv_food_price);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            ivImage = itemView.findViewById(R.id.item_image);
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
            db.close();
        }
        return categoryName;
    }
}