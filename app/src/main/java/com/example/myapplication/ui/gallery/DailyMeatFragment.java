package com.example.myapplication.ui.gallery;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.OrderAdapter;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.OrderModel;

import java.util.List;

public class DailyMeatFragment extends Fragment {
    RecyclerView recycler_orders;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.daily_meet_fragment,container,false);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userID = sharedPref.getInt("user_id", 0);
        List<OrderModel> bills = dbHelper.getBillsByUserId(userID);
        Log.d("DailyMeatFragment", "Bill list size: " + bills.size());

        OrderAdapter adapter = new OrderAdapter(getContext(), bills, order -> {
            Toast.makeText(getContext(), "Hủy bill: " + order.getOrderCode(), Toast.LENGTH_SHORT).show();
            // TODO: dbHelper.updateBillStatus(order.getId(), "Đã hủy");
        });
        recycler_orders = root.findViewById(R.id.recycler_orders);
        recycler_orders.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        recycler_orders.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}