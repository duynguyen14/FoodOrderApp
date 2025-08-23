package com.example.myapplication.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPref;
    TextView textViewName;
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedPref = requireActivity().getSharedPreferences("UserSession", MODE_PRIVATE);
        String name = sharedPref.getString("name", "");
        String email = sharedPref.getString("email", "No Email");

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        textViewName = root.findViewById(R.id.textViewName);
        textViewName.setText("Xin chào "+ name);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}