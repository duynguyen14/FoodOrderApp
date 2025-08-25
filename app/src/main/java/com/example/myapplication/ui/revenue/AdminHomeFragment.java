package com.example.myapplication.ui.revenue;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminHomeFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private Spinner statsSpinner;
    private BarChart barChart;
    private LineChart lineChart;
    private PieChart pieChart;
    private RecyclerView productsRecyclerView;
    private TextView totalQuantityTextView;
    private Button filterDateButton;
    private RevenueProductAdapter productAdapter;

    private String startDate = "2025-01-01";
    private String endDate = "2025-12-31";

    private static final String[] STATS_OPTIONS = {
            "Doanh thu theo tháng (Cột)",
            "Doanh thu theo tháng (Đường)",
            "Doanh thu theo ngày (Cột)",
            "Doanh thu theo ngày (Đường)"
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_admin_home,container,false);
        dbHelper = new DatabaseHelper(requireContext());

        // Find views trên root
        statsSpinner = root.findViewById(R.id.stats_spinner);
        barChart = root.findViewById(R.id.bar_chart);
        lineChart = root.findViewById(R.id.line_chart);
        pieChart = root.findViewById(R.id.pie_chart);
        productsRecyclerView = root.findViewById(R.id.products_recycler_view);
        totalQuantityTextView = root.findViewById(R.id.total_quantity_text);
        filterDateButton = root.findViewById(R.id.filter_date_button);

        // Thiết lập RecyclerView
        productAdapter = new RevenueProductAdapter(requireContext(), null);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        productsRecyclerView.setAdapter(productAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, STATS_OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statsSpinner.setAdapter(adapter);

        // Thiết lập bộ lọc thời gian
        filterDateButton.setOnClickListener(v -> showDateRangePicker());

        // Luôn hiển thị sản phẩm bán chạy và tổng số lượng
        updateBestSellingProducts();
        updateTotalQuantity();
        statsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateStatistics(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        updateStatistics(0);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    private void showDateRangePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog startDatePicker = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            Calendar startCal = Calendar.getInstance();
            startCal.set(year, month, dayOfMonth);
            startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(startCal.getTime());

            // Mở DatePicker thứ hai để chọn endDate
            DatePickerDialog endDatePicker = new DatePickerDialog(requireContext(), (view1, year1, month1, dayOfMonth1) -> {
                Calendar endCal = Calendar.getInstance();
                endCal.set(year1, month1, dayOfMonth1);
                endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(endCal.getTime());

                // Cập nhật lại dữ liệu
                updateBestSellingProducts();
                updateTotalQuantity();
                updateStatistics(statsSpinner.getSelectedItemPosition());
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            endDatePicker.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        startDatePicker.show();
    }

    private void updateStatistics(int position) {
        // Ẩn tất cả biểu đồ
        barChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);

        switch (position) {
            case 0: // Doanh thu theo tháng (Cột)
                showRevenueBarChart(true);
                break;
            case 1: // Doanh thu theo tháng (Đường)
                showRevenueLineChart(true);
                break;
            case 2: // Doanh thu theo ngày (Cột)
                showRevenueBarChart(false);
                break;
            case 3: // Doanh thu theo ngày (Đường)
                showRevenueLineChart(false);
                break;
            case 4: // Doanh thu theo danh mục (Tròn)
                showRevenuePieChart();
                break;
        }
    }

    private void showRevenueBarChart(boolean isMonthly) {
        barChart.setVisibility(View.VISIBLE);
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        Cursor cursor = isMonthly ? dbHelper.getRevenueByMonth(startDate, endDate) : dbHelper.getRevenueByDay(startDate, endDate);
        int index = 0;
        while (cursor.moveToNext()) {
            String period = cursor.getString(cursor.getColumnIndexOrThrow("Period"));
            float revenue = cursor.getFloat(cursor.getColumnIndexOrThrow("TotalRevenue"));
            entries.add(new BarEntry(index, revenue));
            labels.add(period);
            index++;
        }
        cursor.close();

        if (entries.isEmpty()) {
            Toast.makeText(requireContext(), "Không có dữ liệu doanh thu trong khoảng thời gian này", Toast.LENGTH_SHORT).show();
            barChart.setData(null);
            barChart.invalidate();
            return;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu (VND)");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(45);

        Description desc = new Description();
        desc.setText(isMonthly ? "Doanh thu theo tháng" : "Doanh thu theo ngày");
        barChart.setDescription(desc);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void showRevenueLineChart(boolean isMonthly) {
        lineChart.setVisibility(View.VISIBLE);
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        Cursor cursor = isMonthly ? dbHelper.getRevenueByMonth(startDate, endDate) : dbHelper.getRevenueByDay(startDate, endDate);
        int index = 0;
        while (cursor.moveToNext()) {
            String period = cursor.getString(cursor.getColumnIndexOrThrow("Period"));
            float revenue = cursor.getFloat(cursor.getColumnIndexOrThrow("TotalRevenue"));
            entries.add(new Entry(index, revenue));
            labels.add(period);
            index++;
        }
        cursor.close();

        if (entries.isEmpty()) {
            Toast.makeText(requireContext(), "Không có dữ liệu doanh thu trong khoảng thời gian này", Toast.LENGTH_SHORT).show();
            lineChart.setData(null);
            lineChart.invalidate();
            return;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Doanh thu (VND)");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setFillAlpha(110);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(45);

        Description desc = new Description();
        desc.setText(isMonthly ? "Doanh thu theo tháng" : "Doanh thu theo ngày");
        lineChart.setDescription(desc);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.animateX(1000);
        lineChart.invalidate();
    }

    private void showRevenuePieChart() {
        pieChart.setVisibility(View.VISIBLE);
        List<PieEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        Cursor cursor = dbHelper.getRevenueByCategory(startDate, endDate);
        int index = 0;
        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_NAME));
            float revenue = cursor.getFloat(cursor.getColumnIndexOrThrow("TotalRevenue"));
            entries.add(new PieEntry(revenue, category));
            labels.add(category);
            index++;
        }
        cursor.close();

        if (entries.isEmpty()) {
            Toast.makeText(requireContext(), "Không có dữ liệu doanh thu theo danh mục trong khoảng thời gian này", Toast.LENGTH_SHORT).show();
            pieChart.setData(null);
            pieChart.invalidate();
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Doanh thu theo danh mục (USD)");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        Description desc = new Description();
        desc.setText("Doanh thu theo danh mục");
        pieChart.setDescription(desc);
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void updateBestSellingProducts() {
        Cursor cursor = dbHelper.getBestSellingProducts(3, startDate, endDate);
        if (cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu sản phẩm bán chạy trong khoảng thời gian này", Toast.LENGTH_SHORT).show();
        }
        productAdapter.swapCursor(cursor);
    }

    private void updateTotalQuantity() {
        int totalQuantity = dbHelper.getTotalQuantitySold(startDate, endDate);
        totalQuantityTextView.setText("Tổng số lượng sản phẩm bán ra: " + totalQuantity);
    }



}
