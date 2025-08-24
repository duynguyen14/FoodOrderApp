package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.HomeHorModel;
import com.example.myapplication.models.HomeVerModel;

import java.util.ArrayList;
import java.util.List;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {

    UpdateVerticalRec updateVerticalRec;
    Activity activity;
    ArrayList<HomeHorModel> list;

    boolean check = true;
    boolean select = true;
    int row_index = 0;
    DatabaseHelper databaseHelper;


    @NonNull
    @Override
    public HomeHorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item,parent,false));
    }

    public HomeHorAdapter(Activity activity, UpdateVerticalRec updateVerticalRec, ArrayList<HomeHorModel> list) {
        this.activity = activity;
        this.updateVerticalRec = updateVerticalRec;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Gán dữ liệu vào item tại đây
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());

        // Khởi tạo databaseHelper nếu null
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(activity);
        }

        // Nếu là lần đầu tiên load, thì load mặc định category 1 (hoặc theo vị trí đầu)
        if (check && position == 0) {
            ArrayList<HomeVerModel> homeVerModels = databaseHelper.getAllFoodWithCategory(list.get(0).getId());
            updateVerticalRec.callBack(position, homeVerModels);
            check = false;
        }
        // Highlight item khi chọn
        if (row_index == position) {
            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.teal_200)); // màu khi chọn
        } else {
            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.white)); // màu mặc định
        }

        // Bắt sự kiện click
        holder.cardView.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();

            // Lấy danh sách food theo categoryId (giả sử trong HomeHorModel có trường id = categoryId)
            ArrayList<HomeVerModel> homeVerModels = databaseHelper.getAllFoodWithCategory(list.get(position).getId());

            // Callback để update vertical RecyclerView
            updateVerticalRec.callBack(position, homeVerModels);
        });
    }


    @Override
    public int getItemCount() {
        return list.size(); // test tạm
    }

    // ===== Định nghĩa ViewHolder =====
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hor_img);
            cardView =itemView.findViewById(R.id.cardView);
            name = itemView.findViewById(R.id.hor_text);
        }
    }
}
