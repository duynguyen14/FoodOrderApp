package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.HomeVerModel;

import java.util.ArrayList;

public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<HomeVerModel> list;
    private final DatabaseHelper databaseHelper;
    private final int userId;

    public MyFavoriteAdapter(Context context, ArrayList<HomeVerModel> list, int userId) {
        this.context = context;
        this.list = list;
        this.databaseHelper = new DatabaseHelper(context);
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeVerModel model = list.get(position);
        holder.imageView.setImageResource(model.getImage());
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());

        holder.removeBtn.setOnClickListener(v -> {
            int foodId = model.getId();
            // Xóa item khỏi database
            databaseHelper.deleteFavorite(userId, foodId);

            // Xóa item khỏi danh sách và cập nhật RecyclerView
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
            Toast.makeText(context, "Đã xóa món ăn khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price;
        ImageButton removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fav_item_image);
            name = itemView.findViewById(R.id.fav_item_name);
            price = itemView.findViewById(R.id.fav_item_price);
            removeBtn = itemView.findViewById(R.id.fav_item_remove_btn);
        }
    }
}