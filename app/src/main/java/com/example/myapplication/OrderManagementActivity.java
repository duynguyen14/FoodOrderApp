package com.example.myapplication;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.DatabaseHelper;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class OrderManagementActivity extends AppCompatActivity {

    private LinearLayout containerOrders;
    private DatabaseHelper dbHelper;

    // Danh sách trạng thái chuẩn
    private final String[] statusList = {"Chờ xác nhận", "Xác nhận", "Đang giao", "Đã giao", "Hủy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        containerOrders = findViewById(R.id.containerOrders);
        dbHelper = new DatabaseHelper(this);

        // Nút Back
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Ẩn status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

//        dbHelper.insertSampleData();
        showOrders();
    }

    private void showOrders() {
        containerOrders.removeAllViews();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // JOIN để có tên món + SL, nhưng sẽ gom theo billId để không tạo nhiều view cho 1 đơn
        String query =
                "SELECT b." + DatabaseHelper.COLUMN_BILL_ID + "," +
                        " u." + DatabaseHelper.COLUMN_USER_USERNAME + "," +
                        " b." + DatabaseHelper.COLUMN_BILL_DATE + "," +
                        " b." + DatabaseHelper.COLUMN_BILL_TOTAL + "," +
                        " b." + DatabaseHelper.COLUMN_BILL_STATUS + "," +
                        " f." + DatabaseHelper.COLUMN_FOOD_NAME + "," +
                        " bd." + DatabaseHelper.COLUMN_BILL_DETAIL_QUANTITY +
                        " FROM " + DatabaseHelper.TABLE_BILL + " b" +
                        " JOIN " + DatabaseHelper.TABLE_USER + " u" +
                        " ON b." + DatabaseHelper.COLUMN_USER_ID + " = u." + DatabaseHelper.COLUMN_USER_ID +
                        " JOIN " + DatabaseHelper.TABLE_BILL_DETAIL + " bd" +
                        " ON b." + DatabaseHelper.COLUMN_BILL_ID + " = bd." + DatabaseHelper.COLUMN_BILL_ID +
                        " JOIN " + DatabaseHelper.TABLE_FOOD + " f" +
                        " ON bd." + DatabaseHelper.COLUMN_FOOD_ID + " = f." + DatabaseHelper.COLUMN_FOOD_ID +
                        " ORDER BY b." + DatabaseHelper.COLUMN_BILL_ID + " ASC";

        try {
            Cursor c = db.rawQuery(query, null);

            // Dùng LinkedHashMap để giữ thứ tự ASC theo bill_id
            Map<Integer, OrderData> map = new LinkedHashMap<>();

            if (c.moveToFirst()) {
                do {
                    int billId = c.getInt(0);
                    String buyerName = c.getString(1);
                    String date = c.getString(2);
                    double total = c.getDouble(3);
                    String status = c.getString(4);
                    String foodName = c.getString(5);
                    int qty = c.getInt(6);

                    OrderData od = map.get(billId);
                    if (od == null) {
                        od = new OrderData(billId, buyerName, date, total, status);
                        map.put(billId, od);
                    }
                    od.items.append(foodName).append("     x ").append(qty).append("\n");
                } while (c.moveToNext());
            }
            c.close();

            if (map.isEmpty()) {
                Toast.makeText(this, "Không có đơn hàng nào", Toast.LENGTH_SHORT).show();
            } else {
                for (OrderData od : map.values()) {
                    addOrderView(od.billId, od.buyerName, od.date, od.total, od.status, od.items.toString().trim());
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi tải đơn hàng: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    // ✅ thêm tham số items để hiển thị danh sách sản phẩm
    private void addOrderView(int billId, String buyerName, String date, double total, String status, String items) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_order, containerOrders, false);

        TextView tvOrderId = v.findViewById(R.id.tvOrderId);
        TextView tvBuyerName = v.findViewById(R.id.tvBuyerName);
        TextView tvOrderDate = v.findViewById(R.id.tvOrderDate);
        TextView tvTotalAmount = v.findViewById(R.id.tvTotalAmount);
        TextView tvOrderItems = v.findViewById(R.id.tvOrderItems);
        Spinner spinnerStatus = v.findViewById(R.id.spinnerStatus);
        Button btnCancel = v.findViewById(R.id.btnCancelOrder);

        tvOrderId.setText("Mã đơn: #" + billId);
        tvBuyerName.setText("Người mua: " + buyerName);
        tvOrderDate.setText("Ngày đặt: " + date);
        tvTotalAmount.setText("Tổng tiền: " + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total));
        tvOrderItems.setText("Sản phẩm:\n" + items);

        // ✅ currentStatus dùng chung cho Spinner + nút Hủy (để luôn là trạng thái mới nhất)
        final String[] currentStatus = {status};

        int curPos = Arrays.asList(statusList).indexOf(currentStatus[0]);

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == curPos) return true; // enable trạng thái hiện tại
                String item = statusList[position];
                return isValidStatusTransition(currentStatus[0], item);
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (!isEnabled(position)) {
                    tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    tv.setAlpha(0.4f);
                } else {
                    tv.setTextColor(getResources().getColor(android.R.color.black));
                    tv.setAlpha(1f);
                }
                return view;
            }
        };
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adp);

        if (curPos >= 0) {
            spinnerStatus.setSelection(curPos, false);
        }

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean first = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String picked = statusList[position];
                Log.d("OrderManagement", "picked='" + picked + "', current='" + currentStatus[0] + "'");

                if (first) {
                    first = false;
                    refreshCancelButton(btnCancel, picked);
                    if (picked.equalsIgnoreCase(currentStatus[0])) return;
                }

                if (picked.equalsIgnoreCase(currentStatus[0])) {
                    return;
                }

                // Chặn chuyển sai
                if (!isValidStatusTransition(currentStatus[0], picked)) {
                    Toast.makeText(OrderManagementActivity.this,
                            "Không thể chuyển từ " + currentStatus[0] + " sang " + picked, Toast.LENGTH_SHORT).show();
                    spinnerStatus.setSelection(Arrays.asList(statusList).indexOf(currentStatus[0]), false);
                    return;
                }

                // ✅ Update DB
                if (dbHelper.updateOrderStatus(billId, picked)) {
                    currentStatus[0] = picked; // cập nhật biến trạng thái hiện tại
                    refreshCancelButton(btnCancel, picked);
                    Toast.makeText(OrderManagementActivity.this,
                            "Đã cập nhật đơn #" + billId + " sang " + picked, Toast.LENGTH_SHORT).show();

                    // Cập nhật lại enable/disable của các lựa chọn theo trạng thái mới
                    ArrayAdapter<String> newAdapter = new ArrayAdapter<>(OrderManagementActivity.this, android.R.layout.simple_spinner_item, statusList) {
                        @Override
                        public boolean isEnabled(int pos) {
                            if (pos == Arrays.asList(statusList).indexOf(currentStatus[0])) return true;
                            String item = statusList[pos];
                            return isValidStatusTransition(currentStatus[0], item);
                        }

                        @Override
                        public View getDropDownView(int pos, View convertView, android.view.ViewGroup parent) {
                            View view = super.getDropDownView(pos, convertView, parent);
                            TextView tv = (TextView) view;
                            if (!isEnabled(pos)) {
                                tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
                                tv.setAlpha(0.4f);
                            } else {
                                tv.setTextColor(getResources().getColor(android.R.color.black));
                                tv.setAlpha(1f);
                            }
                            return view;
                        }
                    };
                    newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStatus.setAdapter(newAdapter);
                    spinnerStatus.setSelection(Arrays.asList(statusList).indexOf(currentStatus[0]), false);
                } else {
                    Toast.makeText(OrderManagementActivity.this,
                            "Lỗi: không thể cập nhật đơn #" + billId, Toast.LENGTH_SHORT).show();
                    // rollback UI
                    spinnerStatus.setSelection(Arrays.asList(statusList).indexOf(currentStatus[0]), false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        refreshCancelButton(btnCancel, currentStatus[0]);
        btnCancel.setOnClickListener(view -> {
            // ✅ kiểm tra theo trạng thái hiện tại (không dùng biến status cũ nữa)
            if (!"Chờ xác nhận".equalsIgnoreCase(currentStatus[0])) {
                Toast.makeText(this, "Chỉ hủy được khi đang ở trạng thái Chờ xác nhận", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(OrderManagementActivity.this)
                    .setTitle("Xác nhận hủy đơn")
                    .setMessage("Bạn có chắc muốn hủy đơn #" + billId + "?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        if (dbHelper.cancelOrder(billId)) {
                            Toast.makeText(this, "Đã hủy đơn #" + billId, Toast.LENGTH_SHORT).show();
                            showOrders(); // reload để đồng bộ UI
                        } else {
                            Toast.makeText(this, "Lỗi: không thể hủy đơn #" + billId, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        containerOrders.addView(v);
    }

    private void refreshCancelButton(Button btn, String status) {
        boolean canCancel = "Chờ xác nhận".equalsIgnoreCase(status);
        btn.setEnabled(canCancel);
        btn.setAlpha(canCancel ? 1f : 0.4f);
    }

    private String normalizeStatus(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }

    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        currentStatus = normalizeStatus(currentStatus);
        newStatus = normalizeStatus(newStatus);

        switch (currentStatus) {
            case "chờ xác nhận":
                return newStatus.equals("xác nhận") || newStatus.equals("hủy");
            case "xác nhận":
                return newStatus.equals("đang giao");
            case "đang giao":
                return newStatus.equals("đã giao");
            case "đã giao":
            case "hủy":
                return false;
            default:
                return false;
        }
    }

    // Dữ liệu gom cho một bill
    private static class OrderData {
        int billId;
        String buyerName;
        String date;
        double total;
        String status;
        StringBuilder items = new StringBuilder();

        OrderData(int billId, String buyerName, String date, double total, String status) {
            this.billId = billId;
            this.buyerName = buyerName;
            this.date = date;
            this.total = total;
            this.status = status;
        }
    }
}
