package com.example.myapplication.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ProfileActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapters.HomeHorAdapter;
import com.example.myapplication.adapters.HomeVerAdapter;
import com.example.myapplication.adapters.UpdateVerticalRec;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.models.HomeHorModel;
import com.example.myapplication.models.HomeVerModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateVerticalRec {

    RecyclerView homeHorizontalRec, homeVerticalRec;
    ArrayList<HomeHorModel> homeHorModelList;
    ArrayList<HomeVerModel> homeVerModelList;

    HomeHorAdapter homeHorAdapter;
    HomeVerAdapter homeVerAdapter;
    DatabaseHelper databaseHelper;

    private SharedPreferences sharedPref;
    private ImageView ivProfile;
    TextView textViewName;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // ==== SharedPreferences lấy thông tin user ====
        sharedPref = requireActivity().getSharedPreferences("UserSession", MODE_PRIVATE);
        String name = sharedPref.getString("name", "Người dùng");

        // ==== DB ====
        databaseHelper = new DatabaseHelper(requireContext());

        // ==== Inflate layout ====
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textViewName = root.findViewById(R.id.textViewName);
        textViewName.setText("Xin chào " + name);
        ivProfile = root.findViewById(R.id.ivProfile);

        ivProfile.setOnClickListener(v -> {
            // chuyển sang ProfileActivity
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
        // ==== RecyclerView ngang (Category) ====
        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        homeHorModelList = databaseHelper.getAllCategoriesList();

        homeHorAdapter = new HomeHorAdapter(requireActivity(), this, homeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);

        // ==== RecyclerView dọc (Food) ====
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);

        // Load mặc định food theo category đầu tiên (nếu có)
        if (!homeHorModelList.isEmpty()) {
            int firstCategoryId = homeHorModelList.get(0).getId();

            homeVerModelList = databaseHelper.getAllFoodWithCategory(firstCategoryId);
        } else {
            homeVerModelList = new ArrayList<>();
        }

        homeVerAdapter = new HomeVerAdapter(getActivity(), homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(false);

        return root;
    }

    // ==== Nhận callback khi click Category ngang ====
    @Override
    public void callBack(int position, ArrayList<HomeVerModel> list) {
        homeVerAdapter = new HomeVerAdapter(getActivity(), list);
        homeVerticalRec.setAdapter(homeVerAdapter);
    }
}
