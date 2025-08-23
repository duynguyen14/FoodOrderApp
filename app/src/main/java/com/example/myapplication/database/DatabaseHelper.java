package com.example.myapplication.database;
import com.example.myapplication.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 16;

    // Bảng Users
    public static final String TABLE_USER = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_DOB = "dob";
    public static final String COLUMN_USER_ROLE = "role";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ID = "AddressID";
    public static final String COLUMN_ADDRESS_CITY = "City";
    public static final String COLUMN_ADDRESS_DISTRICT = "District";
    public static final String COLUMN_ADDRESS_WARD = "Ward";
    public static final String COLUMN_ADDRESS_DETAIL = "AddressDetail";

    // ------------------ CATEGORY ------------------
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "CategoryID";
    public static final String COLUMN_CATEGORY_NAME = "CategoryName";

    // ------------------ FOOD ------------------
    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_FOOD_ID = "FoodID";
    public static final String COLUMN_FOOD_NAME = "FoodName";
    public static final String COLUMN_FOOD_PRICE = "Price";
    public static final String COLUMN_FOOD_IMAGE = "Image";
    public static final String COLUMN_FOOD_DESCRIPTION = "Description";
    public static final String COLUMN_FOOD_QUANTITY = "Quantity";
    public static final String COLUMN_FOOD_SOLD_COUNT = "SoldCount";


    // ------------------ SHOPPING CART ------------------
    public static final String TABLE_CART = "shoppingcart";
    public static final String COLUMN_CART_ID = "CartID";

    public static final String TABLE_CART_DETAIL = "shoppingcartdetail";
    public static final String COLUMN_CART_DETAIL_ID = "CartDetailID";
    public static final String COLUMN_CART_DETAIL_QUANTITY = "Quantity";

    // ------------------ REVIEW ------------------
    public static final String TABLE_REVIEW = "review";
    public static final String COLUMN_REVIEW_ID = "ReviewID";
    public static final String COLUMN_REVIEW_RATING = "Rating";
    public static final String COLUMN_REVIEW_COMMENT = "Comment";

    // ------------------ FAVORITE FOOD ------------------
    public static final String TABLE_FAVORITE = "favorite_food";

    // ------------------ BILL ------------------
    public static final String TABLE_BILL = "bill";
    public static final String COLUMN_BILL_ID = "BillID";
    public static final String COLUMN_BILL_DATE = "BillDate";
    public static final String COLUMN_BILL_TOTAL = "TotalAmount";

    public static  final  String COLUMN_BILL_STATUS ="status";
    public static final String TABLE_BILL_DETAIL = "billdetail";
    public static final String COLUMN_BILL_DETAIL_ID = "BillDetailID";
    public static final String COLUMN_BILL_DETAIL_QUANTITY = "Quantity";




    @Override
    public void onCreate(SQLiteDatabase db) {

        int imageResId1 = R.drawable.breakfast;
        int imageResId2 = R.drawable.burger2;
        int imageResId3 = R.drawable.burger4;
        int imageResId4 = R.drawable.coffe;

        db.execSQL("PRAGMA foreign_keys=ON");
        // USERS
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_USERNAME + " TEXT NOT NULL, " +
                COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_USER_GENDER + " TEXT, " +
                COLUMN_USER_DOB + " TEXT, " +
                COLUMN_USER_ROLE + " TEXT)");

        // User Admin
        db.execSQL("INSERT INTO " + TABLE_USER + " (" +
                COLUMN_USER_USERNAME + "," +
                COLUMN_USER_EMAIL + "," +
                COLUMN_USER_PASSWORD + "," +
                COLUMN_USER_GENDER + "," +
                COLUMN_USER_DOB + "," +
                COLUMN_USER_ROLE +
                ") VALUES ('admin','admin@gmail.com','123456','Male','1990-01-01','ADMIN')");

// User thường
        db.execSQL("INSERT INTO " + TABLE_USER + " (" +
                COLUMN_USER_USERNAME + "," +
                COLUMN_USER_EMAIL + "," +
                COLUMN_USER_PASSWORD + "," +
                COLUMN_USER_GENDER + "," +
                COLUMN_USER_DOB + "," +
                COLUMN_USER_ROLE +
                ") VALUES ('duy','duy@gmail.com','123456','Female','1995-05-20','USER')");
        db.execSQL("CREATE TABLE " + TABLE_ADDRESS + " (" +
                COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_ADDRESS_CITY + " TEXT, " +
                COLUMN_ADDRESS_DISTRICT + " TEXT, " +
                COLUMN_ADDRESS_WARD + " TEXT, " +
                COLUMN_ADDRESS_DETAIL + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");

        // CATEGORY
        db.execSQL("CREATE TABLE " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT)");

        // FOOD
        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (" +
                COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_ID + " INTEGER, " +
                COLUMN_FOOD_NAME + " TEXT, " +
                COLUMN_FOOD_PRICE + " REAL, " +
                COLUMN_FOOD_IMAGE + " INTEGER, " +
                COLUMN_FOOD_DESCRIPTION + " TEXT, " +
                COLUMN_FOOD_QUANTITY + " TEXT, " +
                COLUMN_FOOD_SOLD_COUNT + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "))");

        // SHOPPING CART
        db.execSQL("CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");

        // SHOPPING CART DETAIL
        db.execSQL("CREATE TABLE " + TABLE_CART_DETAIL + " (" +
                COLUMN_CART_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CART_ID + " INTEGER, " +
                COLUMN_FOOD_ID + " INTEGER, " +
                COLUMN_CART_DETAIL_QUANTITY + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_CART_ID + ") REFERENCES " + TABLE_CART + "(" + COLUMN_CART_ID + "), " +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + TABLE_FOOD + "(" + COLUMN_FOOD_ID + "))");

        // REVIEW
        db.execSQL("CREATE TABLE " + TABLE_REVIEW + " (" +
                COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_FOOD_ID + " INTEGER, " +
                COLUMN_REVIEW_RATING + " INTEGER, " +
                COLUMN_REVIEW_COMMENT + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + TABLE_FOOD + "(" + COLUMN_FOOD_ID + "))");

        // FAVORITE FOOD
        db.execSQL("CREATE TABLE " + TABLE_FAVORITE + " (" +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_FOOD_ID + " INTEGER, " +
                "PRIMARY KEY(" + COLUMN_USER_ID + ", " + COLUMN_FOOD_ID + "), " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + TABLE_FOOD + "(" + COLUMN_FOOD_ID + "))");

        // BILL
        db.execSQL("CREATE TABLE " + TABLE_BILL + " (" +
                COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_BILL_DATE + " TEXT, " +
                COLUMN_BILL_TOTAL + " REAL, " +
                COLUMN_BILL_STATUS + " TEXT NOT NULL DEFAULT 'Chờ xác nhận', " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");

        // BILL DETAIL
        db.execSQL("CREATE TABLE " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BILL_ID + " INTEGER, " +
                COLUMN_FOOD_ID + " INTEGER, " +
                COLUMN_BILL_DETAIL_QUANTITY + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_BILL_ID + ") REFERENCES " + TABLE_BILL + "(" + COLUMN_BILL_ID + "), " +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + TABLE_FOOD + "(" + COLUMN_FOOD_ID + "))");

        db.execSQL("PRAGMA foreign_keys=ON");

// ========== USERS ==========
        db.execSQL("INSERT INTO " + TABLE_USER + " (" +
                COLUMN_USER_USERNAME + "," +
                COLUMN_USER_EMAIL + "," +
                COLUMN_USER_PASSWORD + "," +
                COLUMN_USER_GENDER + "," +
                COLUMN_USER_DOB + "," +
                COLUMN_USER_ROLE +
                ") VALUES ('admin','admin@gmail.com','123456','Male','1990-01-01','ADMIN')");

        db.execSQL("INSERT INTO " + TABLE_USER + " (" +
                COLUMN_USER_USERNAME + "," +
                COLUMN_USER_EMAIL + "," +
                COLUMN_USER_PASSWORD + "," +
                COLUMN_USER_GENDER + "," +
                COLUMN_USER_DOB + "," +
                COLUMN_USER_ROLE +
                ") VALUES ('duy','duy@gmail.com','123456','Female','1995-05-20','USER')");

        db.execSQL("INSERT INTO " + TABLE_USER + " (" +
                COLUMN_USER_USERNAME + "," +
                COLUMN_USER_EMAIL + "," +
                COLUMN_USER_PASSWORD + "," +
                COLUMN_USER_GENDER + "," +
                COLUMN_USER_DOB + "," +
                COLUMN_USER_ROLE +
                ") VALUES ('minh','minh@gmail.com','123456','Male','1998-07-15','USER')");

        db.execSQL("INSERT INTO " + TABLE_USER + " (" +
                COLUMN_USER_USERNAME + "," +
                COLUMN_USER_EMAIL + "," +
                COLUMN_USER_PASSWORD + "," +
                COLUMN_USER_GENDER + "," +
                COLUMN_USER_DOB + "," +
                COLUMN_USER_ROLE +
                ") VALUES ('trang','trang@gmail.com','123456','Female','2000-03-10','USER')");

// ========== CATEGORY ==========
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + COLUMN_CATEGORY_NAME + ") VALUES ('Món chính')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + COLUMN_CATEGORY_NAME + ") VALUES ('Nước uống')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + COLUMN_CATEGORY_NAME + ") VALUES ('Bánh')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + COLUMN_CATEGORY_NAME + ") VALUES ('Tráng miệng')");

//// ========== FOOD ==========

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLD_COUNT +
                ") VALUES (1,'Phở bò',50000," + imageResId1 + ",'Phở bò thơm ngon đặc biệt','100','20')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLD_COUNT +
                ") VALUES (2,'Nước cam',30000," + imageResId2 + ",'Nước cam tươi mát lạnh','200','50')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLD_COUNT +
                ") VALUES (3,'Bánh mì thịt',20000, " + imageResId3 + ",'Bánh mì thịt nóng giòn','150','40')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLD_COUNT +
                ") VALUES (4,'Chè đậu xanh',25000, " + imageResId4 + " ,'Chè đậu xanh ngọt mát','120','30')");

// ========== BILL ==========
        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (2,'2025-08-01',100000,'Chờ xác nhận')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (3,'2025-08-02',75000,'Xác nhận')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (4,'2025-08-03',95000,'Đang giao')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (2,'2025-08-04',125000,'Đã giao')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (3,'2025-08-05',60000,'Hủy')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (4,'2025-08-06',150000,'Chờ xác nhận')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (2,'2025-08-07',85000,'Xác nhận')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (3,'2025-08-08',105000,'Đang giao')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (4,'2025-08-09',200000,'Đã giao')");

        db.execSQL("INSERT INTO " + TABLE_BILL + " (" +
                COLUMN_USER_ID + "," + COLUMN_BILL_DATE + "," + COLUMN_BILL_TOTAL + "," + COLUMN_BILL_STATUS +
                ") VALUES (2,'2025-08-10',50000,'Hủy')");

// ========== BILL DETAIL ==========
        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (1,1,2)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (2,2,1)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (3,3,2)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (4,4,3)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (5,1,1)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (6,2,2)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (7,3,1)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (8,4,2)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (9,1,4)");

        db.execSQL("INSERT INTO " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_ID + "," + COLUMN_FOOD_ID + "," + COLUMN_BILL_DETAIL_QUANTITY +
                ") VALUES (10,2,1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public long insertUser(String username, String email, String password, String gender, String dob, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_DOB, dob);
        values.put(COLUMN_USER_ROLE, role);
        return db.insert(TABLE_USER, null, values);
    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER, null, null, null, null, null, COLUMN_USER_ID + " ASC");
    }

    // Lấy user theo ID
    public Cursor getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER, null, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }
    // Lấy user theo Email
    public Cursor findUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_USER,
                null, // Lấy tất cả các cột
                COLUMN_USER_EMAIL + "=?",
                new String[]{email},
                null,
                null,
                null
        );
    }

    public int updateUser(int id, String username, String email, String password, String gender, String dob, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_DOB, dob);
        values.put(COLUMN_USER_ROLE, role);
        return db.update(TABLE_USER, values, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Xóa user
    public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
    }


    // Thêm đơn hàng mới
    public long insertBill(int userId, String date, double totalAmount, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_BILL_DATE, date);
        values.put(COLUMN_BILL_TOTAL, totalAmount);
        values.put(COLUMN_BILL_STATUS, status);
        return db.insert(TABLE_BILL, null, values);
    }
//    public void updateOrderStatus(int orderId, String status) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_BILL_STATUS, status);
//        try {
//            db.update(TABLE_BILL, values, COLUMN_BILL_ID + "=?", new String[]{String.valueOf(orderId)});
//        } catch (Exception e) {
//            // Có thể ghi log lỗi nếu cần
//        } finally {
//            db.close();
//        }
//    }
//
//    public void cancelOrder(int orderId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_BILL_STATUS, "Hủy");
//        try {
//            db.update(TABLE_BILL, values, COLUMN_BILL_ID + "=?", new String[]{String.valueOf(orderId)});
//        } catch (Exception e) {
//            // Có thể ghi log lỗi nếu cần
//        } finally {
//            db.close();
//        }
//    }

    // Cập nhật trạng thái đơn
    public boolean updateOrderStatus(int orderId, String status) {
        Log.d("OrderManagement", "BẮT ĐẦU updateOrderStatus với orderId=" + orderId + ", status=" + status);


        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("OrderManagement", "Đã lấy được database");

        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_STATUS, status);
        Log.d("OrderManagement", "Đã set ContentValues");

        int rows = 0;
        try {
            rows = db.update(TABLE_BILL, values, COLUMN_BILL_ID + "=?", new String[]{String.valueOf(orderId)});
            Log.d("OrderManagement", "Cập nhật đơn #" + orderId + " sang " + status + ", rows: " + rows);
        } catch (Exception e) {
            Log.e("OrderManagement", "Lỗi cập nhật đơn #" + orderId + ": " + e.getMessage());
        } finally {
            db.close();
        }
        return rows > 0;
    }

    // Hủy đơn (set trạng thái = "Hủy")
    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, "Hủy");
    }

    // Trong DatabaseHelper.java
//    public boolean insertSampleData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            // Chèn dữ liệu vào bảng users
//            ContentValues userValues = new ContentValues();
//            userValues.put(COLUMN_USER_USERNAME, "NguyenVanA");
//            userValues.put(COLUMN_USER_EMAIL, "nguyenvana@example.com");
//            userValues.put(COLUMN_USER_PASSWORD, "password123");
//            userValues.put(COLUMN_USER_GENDER, "Nam");
//            userValues.put(COLUMN_USER_DOB, "1990-01-01");
//            userValues.put(COLUMN_USER_ROLE, "user");
//            long userId1 = db.insertOrThrow(TABLE_USER, null, userValues);
//            if (userId1 == -1) return false;
//
//            userValues.clear();
//            userValues.put(COLUMN_USER_USERNAME, "TranThiB");
//            userValues.put(COLUMN_USER_EMAIL, "tranthib@example.com");
//            userValues.put(COLUMN_USER_PASSWORD, "password456");
//            userValues.put(COLUMN_USER_GENDER, "Nữ");
//            userValues.put(COLUMN_USER_DOB, "1995-05-10");
//            userValues.put(COLUMN_USER_ROLE, "user");
//            long userId2 = db.insertOrThrow(TABLE_USER, null, userValues);
//            if (userId2 == -1) return false;
//
//            // Chèn dữ liệu vào bảng category
//            ContentValues categoryValues = new ContentValues();
//            categoryValues.put(COLUMN_CATEGORY_NAME, "Món chính");
//            long categoryId1 = db.insertOrThrow(TABLE_CATEGORY, null, categoryValues);
//            if (categoryId1 == -1) return false;
//
//            categoryValues.clear();
//            categoryValues.put(COLUMN_CATEGORY_NAME, "Đồ uống");
//            long categoryId2 = db.insertOrThrow(TABLE_CATEGORY, null, categoryValues);
//            if (categoryId2 == -1) return false;
//
//            // Chèn dữ liệu vào bảng food
//            ContentValues foodValues = new ContentValues();
//            foodValues.put(COLUMN_CATEGORY_ID, categoryId1);
//            foodValues.put(COLUMN_FOOD_NAME, "Phở bò");
//            foodValues.put(COLUMN_FOOD_PRICE, 50000.0);
//            foodValues.put(COLUMN_FOOD_IMAGE, "pho_bo.jpg");
//            foodValues.put(COLUMN_FOOD_DESCRIPTION, "Phở bò thơm ngon");
//            foodValues.put(COLUMN_FOOD_QUANTITY, "100"); // Lưu ý: TEXT trong lược đồ
//            foodValues.put(COLUMN_FOOD_SOLID_COUNT, "50");
//            long foodId1 = db.insertOrThrow(TABLE_FOOD, null, foodValues);
//            if (foodId1 == -1) return false;
//
//            foodValues.clear();
//            foodValues.put(COLUMN_CATEGORY_ID, categoryId2);
//            foodValues.put(COLUMN_FOOD_NAME, "Nước cam");
//            foodValues.put(COLUMN_FOOD_PRICE, 20000.0);
//            foodValues.put(COLUMN_FOOD_IMAGE, "nuoc_cam.jpg");
//            foodValues.put(COLUMN_FOOD_DESCRIPTION, "Nước cam tươi");
//            foodValues.put(COLUMN_FOOD_QUANTITY, "200");
//            foodValues.put(COLUMN_FOOD_SOLID_COUNT, "100");
//            long foodId2 = db.insertOrThrow(TABLE_FOOD, null, foodValues);
//            if (foodId2 == -1) return false;
//
//            // Chèn dữ liệu vào bảng bill
//            ContentValues billValues = new ContentValues();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-20");
//            billValues.put(COLUMN_BILL_TOTAL, 150000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Chờ xác nhận");
//            long billId1 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId1 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId2);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-21");
//            billValues.put(COLUMN_BILL_TOTAL, 80000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Xác nhận");
//            long billId2 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId2 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-22");
//            billValues.put(COLUMN_BILL_TOTAL, 220000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Đang giao");
//            long billId3 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId3 == -1) return false;
//
//
//            // Sau billId3 bạn thêm vào
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId2);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-23");
//            billValues.put(COLUMN_BILL_TOTAL, 120000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Đã giao");
//            long billId4 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId4 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-24");
//            billValues.put(COLUMN_BILL_TOTAL, 90000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Hủy");
//            long billId5 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId5 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId2);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-25");
//            billValues.put(COLUMN_BILL_TOTAL, 450000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Chờ xác nhận");
//            long billId6 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId6 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-26");
//            billValues.put(COLUMN_BILL_TOTAL, 300000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Xác nhận");
//            long billId7 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId7 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId2);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-27");
//            billValues.put(COLUMN_BILL_TOTAL, 270000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Đang giao");
//            long billId8 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId8 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-28");
//            billValues.put(COLUMN_BILL_TOTAL, 110000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Đã giao");
//            long billId9 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId9 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId2);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-29");
//            billValues.put(COLUMN_BILL_TOTAL, 60000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Chờ xác nhận");
//            long billId10 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId10 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-30");
//            billValues.put(COLUMN_BILL_TOTAL, 190000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Xác nhận");
//            long billId11 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId11 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId2);
//            billValues.put(COLUMN_BILL_DATE, "2025-08-31");
//            billValues.put(COLUMN_BILL_TOTAL, 250000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Đang giao");
//            long billId12 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId12 == -1) return false;
//
//            billValues.clear();
//            billValues.put(COLUMN_USER_ID, userId1);
//            billValues.put(COLUMN_BILL_DATE, "2025-09-01");
//            billValues.put(COLUMN_BILL_TOTAL, 130000.0);
//            billValues.put(COLUMN_BILL_STATUS, "Đã giao");
//            long billId13 = db.insertOrThrow(TABLE_BILL, null, billValues);
//            if (billId13 == -1) return false;
//
//// Thêm chi tiết cho các bill vừa tạo
//            long[] newBills = {billId4, billId5, billId6, billId7, billId8, billId9, billId10, billId11, billId12, billId13};
//
//            for (long bId : newBills) {
//                ContentValues detail = new ContentValues();
//                detail.put(COLUMN_BILL_ID, bId);
//                detail.put(COLUMN_FOOD_ID, foodId1);
//                detail.put(COLUMN_BILL_DETAIL_QUANTITY, 1);
//                if (db.insertOrThrow(TABLE_BILL_DETAIL, null, detail) == -1) return false;
//
//                detail.clear();
//                detail.put(COLUMN_BILL_ID, bId);
//                detail.put(COLUMN_FOOD_ID, foodId2);
//                detail.put(COLUMN_BILL_DETAIL_QUANTITY, 2);
//                if (db.insertOrThrow(TABLE_BILL_DETAIL, null, detail) == -1) return false;
//            }
//
//
//            // Chèn dữ liệu vào bảng billdetail
//            ContentValues billDetailValues = new ContentValues();
//            billDetailValues.put(COLUMN_BILL_ID, billId1);
//            billDetailValues.put(COLUMN_FOOD_ID, foodId1);
//            billDetailValues.put(COLUMN_BILL_DETAIL_QUANTITY, 3);
//            long detailId1 = db.insertOrThrow(TABLE_BILL_DETAIL, null, billDetailValues);
//            if (detailId1 == -1) return false;
//
//            billDetailValues.clear();
//            billDetailValues.put(COLUMN_BILL_ID, billId2);
//            billDetailValues.put(COLUMN_FOOD_ID, foodId2);
//            billDetailValues.put(COLUMN_BILL_DETAIL_QUANTITY, 4);
//            long detailId2 = db.insertOrThrow(TABLE_BILL_DETAIL, null, billDetailValues);
//            if (detailId2 == -1) return false;
//
//            billDetailValues.clear();
//            billDetailValues.put(COLUMN_BILL_ID, billId3);
//            billDetailValues.put(COLUMN_FOOD_ID, foodId1);
//            billDetailValues.put(COLUMN_BILL_DETAIL_QUANTITY, 2);
//            long detailId3 = db.insertOrThrow(TABLE_BILL_DETAIL, null, billDetailValues);
//            if (detailId3 == -1) return false;
//
//            billDetailValues.clear();
//            billDetailValues.put(COLUMN_BILL_ID, billId3);
//            billDetailValues.put(COLUMN_FOOD_ID, foodId2);
//            billDetailValues.put(COLUMN_BILL_DETAIL_QUANTITY, 3);
//            long detailId4 = db.insertOrThrow(TABLE_BILL_DETAIL, null, billDetailValues);
//            if (detailId4 == -1) return false;
//
//            return true;
//        } catch (Exception e) {
//            // Ghi log lỗi để kiểm tra
//            android.util.Log.e("DatabaseHelper", "Error inserting sample data: " + e.getMessage());
//            return false;
//        } finally {
//            db.close();
//        }
//    }


    // duy trinh

// Lấy sản phẩm theo trang
    public Cursor getProductsByPage(int page, int pageSize) {
        SQLiteDatabase db = this.getReadableDatabase();
        int offset = (page - 1) * pageSize;
        return db.rawQuery("SELECT * FROM " + TABLE_FOOD + " LIMIT ? OFFSET ?",
                new String[]{String.valueOf(pageSize), String.valueOf(offset)});
    }

    // Lấy tổng số sản phẩm
    public int getTotalProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FOOD, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    // Xóa sản phẩm
    public int deleteFood(int foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FOOD, COLUMN_FOOD_ID + "=?", new String[]{String.valueOf(foodId)});
    }

    // Thêm sản phẩm vào bảng food
    public long insertFood(int categoryId, String foodName, double price, int image, String description, int quantity, int soldCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, categoryId);
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_IMAGE, image);
        values.put(COLUMN_FOOD_DESCRIPTION, description);
        values.put(COLUMN_FOOD_QUANTITY, quantity);
        values.put(COLUMN_FOOD_SOLD_COUNT, soldCount);
        return db.insert(TABLE_FOOD, null, values);
    }

    // Cập nhật sản phẩm
    public int updateFood(int foodId, int categoryId, String foodName, double price, int image, String description, int quantity, int soldCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, categoryId);
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_IMAGE, image);
        values.put(COLUMN_FOOD_DESCRIPTION, description);
        values.put(COLUMN_FOOD_QUANTITY, quantity);
        values.put(COLUMN_FOOD_SOLD_COUNT, soldCount);
        return db.update(TABLE_FOOD, values, COLUMN_FOOD_ID + "=?", new String[]{String.valueOf(foodId)});
    }

    // Thống kê doanh thu theo tháng
    public Cursor getRevenueByMonth(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT strftime('%Y-%m', " + COLUMN_BILL_DATE + ") AS Period, " +
                "SUM(" + COLUMN_BILL_TOTAL + ") AS TotalRevenue " +
                "FROM " + TABLE_BILL + " " +
                "WHERE " + COLUMN_BILL_DATE + " BETWEEN ? AND ? " +
                "AND " + COLUMN_BILL_STATUS + " = 'Đã giao' " +
                "GROUP BY strftime('%Y-%m', " + COLUMN_BILL_DATE + ") " +
                "ORDER BY Period";
        return db.rawQuery(query, new String[]{startDate, endDate});
    }

    // Thống kê doanh thu theo ngày
    public Cursor getRevenueByDay(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT strftime('%Y-%m-%d', " + COLUMN_BILL_DATE + ") AS Period, " +
                "SUM(" + COLUMN_BILL_TOTAL + ") AS TotalRevenue " +
                "FROM " + TABLE_BILL + " " +
                "WHERE " + COLUMN_BILL_DATE + " BETWEEN ? AND ? " +
                "AND " + COLUMN_BILL_STATUS + " = 'Đã giao' " +
                "GROUP BY strftime('%Y-%m-%d', " + COLUMN_BILL_DATE + ") " +
                "ORDER BY Period";
        return db.rawQuery(query, new String[]{startDate, endDate});
    }


    // Thống kê doanh thu theo danh mục
    public Cursor getRevenueByCategory(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c." + COLUMN_CATEGORY_NAME + ", SUM(b." + COLUMN_BILL_TOTAL + ") AS TotalRevenue " +
                "FROM " + TABLE_BILL + " b " +
                "JOIN " + TABLE_BILL_DETAIL + " bd ON b." + COLUMN_BILL_ID + " = bd." + COLUMN_BILL_ID + " " +
                "JOIN " + TABLE_FOOD + " f ON bd." + COLUMN_FOOD_ID + " = f." + COLUMN_FOOD_ID + " " +
                "JOIN " + TABLE_CATEGORY + " c ON f." + COLUMN_CATEGORY_ID + " = c." + COLUMN_CATEGORY_ID + " " +
                "WHERE b." + COLUMN_BILL_DATE + " BETWEEN ? AND ? " +
                "AND b." + COLUMN_BILL_STATUS + " = 'Đã giao' " +
                "GROUP BY c." + COLUMN_CATEGORY_ID + " " +
                "ORDER BY TotalRevenue DESC";
        return db.rawQuery(query, new String[]{startDate, endDate});
    }


    public Cursor getBestSellingProducts(int limit, String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT f." + COLUMN_FOOD_ID + ", f." + COLUMN_FOOD_NAME + ", f." + COLUMN_FOOD_PRICE + ", f."
                + COLUMN_FOOD_IMAGE + ", f." + COLUMN_CATEGORY_ID + ", "
                + "SUM(bd." + COLUMN_BILL_DETAIL_QUANTITY + ") AS TotalQuantity "
                + "FROM " + TABLE_BILL_DETAIL + " bd "
                + "JOIN " + TABLE_BILL + " b ON bd." + COLUMN_BILL_ID + " = b." + COLUMN_BILL_ID + " "
                + "JOIN " + TABLE_FOOD + " f ON bd." + COLUMN_FOOD_ID + " = f." + COLUMN_FOOD_ID + " "
                + "WHERE b." + COLUMN_BILL_DATE + " BETWEEN ? AND ? "
                + "AND b." + COLUMN_BILL_STATUS + " = 'Đã giao' "
                + "GROUP BY bd." + COLUMN_FOOD_ID + " "
                + "ORDER BY TotalQuantity DESC "
                + "LIMIT ?";
        return db.rawQuery(query, new String[]{startDate, endDate, String.valueOf(limit)});
    }



    public int getTotalQuantitySold(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int totalQuantity = 0;
        try {
            String query = "SELECT SUM(" + COLUMN_BILL_DETAIL_QUANTITY + ") FROM " + TABLE_BILL_DETAIL + " bd " +
                    "JOIN " + TABLE_BILL + " b ON bd." + COLUMN_BILL_ID + " = b." + COLUMN_BILL_ID +
                    " WHERE b." + COLUMN_BILL_DATE + " BETWEEN ? AND ? " +
                    "AND b." + COLUMN_BILL_STATUS + " = 'Đã giao'";
            cursor = db.rawQuery(query, new String[]{startDate, endDate});
            if (cursor.moveToFirst()) {
                totalQuantity = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return totalQuantity;
    }


}
