package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderModel> orderList;
    private OnCancelClickListener cancelClickListener;

    public interface OnCancelClickListener {
        void onCancelClick(OrderModel order);
    }

    public OrderAdapter(Context context, List<OrderModel> orderList, OnCancelClickListener cancelClickListener) {
        this.context = context;
        this.orderList = orderList;
        this.cancelClickListener = cancelClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order1, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.txtOrderCode.setText("Mã đơn: " + order.getOrderCode());
        holder.txtOrderStatus.setText("Trạng thái: " + order.getStatus());
        holder.txtOrderDate.setText("Ngày đặt: " + order.getOrderDate());
        holder.txtOrderTotal.setText("Tổng tiền: " + order.getTotal() + "$");

        // Chỉ hiện nút hủy nếu trạng thái là "Chờ xác nhận"
        if ("Chờ xác nhận".equals(order.getStatus())) {
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setOnClickListener(v -> {
                if (cancelClickListener != null) {
                    cancelClickListener.onCancelClick(order);
                }
            });
        } else {
            holder.btnCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderCode, txtOrderStatus, txtOrderDate, txtOrderTotal;
        Button btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderCode = itemView.findViewById(R.id.txtOrderCode);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtOrderTotal = itemView.findViewById(R.id.txtOrderTotal);
            btnCancel = itemView.findViewById(R.id.btnCancelOrder);
        }
    }
}
