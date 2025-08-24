package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.models.HomeHorModel;
import com.example.myapplication.models.HomeVerModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 7;

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
    public static final String COLUMN_CATEGORY_IMAGE = "CategoryImage";


    // ------------------ FOOD ------------------
    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_FOOD_ID = "FoodID";
    public static final String COLUMN_FOOD_NAME = "FoodName";
    public static final String COLUMN_FOOD_PRICE = "Price";
    public static final String COLUMN_FOOD_IMAGE = "Image";
    public static final String COLUMN_FOOD_TIME = "Time";

    public static final String COLUMN_FOOD_DESCRIPTION = "Description";
    public static final String COLUMN_FOOD_QUANTITY = "Quantity";
    public static final String COLUMN_FOOD_SOLID_COUNT = "SolidCount";


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

//        ADDRESS
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
                COLUMN_CATEGORY_NAME + " TEXT, " +
                COLUMN_CATEGORY_IMAGE + " INTEGER)");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMAGE +
                ") VALUES ('Pizza', " + R.drawable.pizza + ")");

        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMAGE +
                ") VALUES ('HamBurger', " + R.drawable.hamburger + ")");

        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMAGE +
                ") VALUES ('Fries', " + R.drawable.fried_potatoes + ")");

        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMAGE +
                ") VALUES ('Cream', " + R.drawable.ice_cream + ")");

        db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMAGE +
                ") VALUES ('Sandwich', " + R.drawable.sandwich + ")");




        // FOOD
        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (" +
                COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_ID + " INTEGER, " +
                COLUMN_FOOD_NAME + " TEXT, " +
                COLUMN_FOOD_PRICE + " REAL, " +
                COLUMN_FOOD_IMAGE + " INTEGER, " +
                COLUMN_FOOD_TIME + " TEXT, " +
                COLUMN_FOOD_DESCRIPTION + " TEXT, " +
                COLUMN_FOOD_QUANTITY + " TEXT, " +
                COLUMN_FOOD_SOLID_COUNT + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "))");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (1, 'Pizza', 34.0, " + R.drawable.pizza1 + ", '10:00-23:00', 'Delicious cheese pizza', '1', '0')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (1, 'Pizza', 34.0, " + R.drawable.pizza2 + ", '10:00-23:00', 'Delicious cheese pizza', '1', '0')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (1, 'Pizza', 34.0, " + R.drawable.pizza3 + ", '10:00-23:00', 'Delicious cheese pizza', '1', '0')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (2, 'HamBurger', 34.0, " + R.drawable.burger2 + ", '10:00-23:00', 'Delicious cheese hambuger', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (2, 'HamBurger', 34.0, " + R.drawable.burger4 + ", '10:00-23:00', 'Delicious cheese hambuger', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (3, 'Fries', 34.0, " + R.drawable.fries1 + ", '10:00-23:00', 'Delicious cheese fries', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (3, 'Fries', 34.0, " + R.drawable.fries2 + ", '10:00-23:00', 'Delicious cheese fries', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (3, 'Fries', 34.0, " + R.drawable.fries3 + ", '10:00-23:00', 'Delicious cheese fries', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (3, 'Fries', 34.0, " + R.drawable.fries4 + ", '10:00-23:00', 'Delicious cheese fries', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (4, 'Cream', 34.0, " + R.drawable.icecream1 + ", '10:00-23:00', 'Delicious cheese cream', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (4, 'Cream', 34.0, " + R.drawable.icecream2 + ", '10:00-23:00', 'Delicious cheese cream', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (4, 'Cream', 34.0, " + R.drawable.icecream3 + ", '10:00-23:00', 'Delicious cheese cream', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (4, 'Cream', 34.0, " + R.drawable.icecream4 + ", '10:00-23:00', 'Delicious cheese cream', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (5, 'Sandwich', 34.0, " + R.drawable.sandwich1 + ", '10:00-23:00', 'Delicious cheese Sandwich', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (5, 'Sandwich', 34.0, " + R.drawable.sandwich2 + ", '10:00-23:00', 'Delicious cheese Sandwich', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (5, 'Sandwich', 34.0, " + R.drawable.sandwich3 + ", '10:00-23:00', 'Delicious cheese Sandwich', '100', '20')");
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + ", " +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_FOOD_PRICE + ", " +
                COLUMN_FOOD_IMAGE + ", " +
                COLUMN_FOOD_TIME + ", " +
                COLUMN_FOOD_DESCRIPTION + ", " +
                COLUMN_FOOD_QUANTITY + ", " +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (5, 'Sandwich', 34.0, " + R.drawable.sandwich4 + ", '10:00-23:00', 'Delicious cheese Sandwich', '100', '20')");

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
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (1, 1, 5, 'Pizza rất ngon, phô mai béo ngậy!')");

// User 2 (role admin) đánh giá Pizza1
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (2, 1, 4, 'Pizza ổn nhưng hơi nhiều dầu.')");

// User 1 đánh giá Pizza2
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (1, 2, 5, 'Pizza2 giòn rụm, rất tuyệt vời!')");

// User 2 đánh giá Pizza3
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (2, 3, 3, 'Pizza3 bình thường, chưa đặc biệt.')");
        // ===== Thêm review cho Hamburger =====
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (1, 4, 5, 'Hamburger rất ngon, thịt mềm và thơm!')");

        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (2, 5, 4, 'Burger ổn nhưng hơi nhiều sốt.')");

// ===== Thêm review cho Fries =====
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (1, 6, 5, 'Khoai tây chiên giòn rụm, siêu ngon!')");

        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (2, 7, 3, 'Khoai tây hơi ỉu, chưa ngon lắm.')");

// ===== Thêm review cho Cream =====
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (1, 9, 5, 'Kem mát lạnh, ngọt vừa đủ!')");

        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (2, 10, 4, 'Kem ngon nhưng hơi ngọt so với mình.')");

// ===== Thêm review cho Sandwich =====
        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (1, 13, 4, 'Sandwich mềm, nhân nhiều, khá ngon!')");

        db.execSQL("INSERT INTO " + TABLE_REVIEW + " (" +
                COLUMN_USER_ID + ", " +
                COLUMN_FOOD_ID + ", " +
                COLUMN_REVIEW_RATING + ", " +
                COLUMN_REVIEW_COMMENT +
                ") VALUES (2, 14, 5, 'Sandwich tuyệt vời, rất hợp khẩu vị mình!')");

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
                COLUMN_BILL_STATUS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))");

        // BILL DETAIL
        db.execSQL("CREATE TABLE " + TABLE_BILL_DETAIL + " (" +
                COLUMN_BILL_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BILL_ID + " INTEGER, " +
                COLUMN_FOOD_ID + " INTEGER, " +
                COLUMN_BILL_DETAIL_QUANTITY + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_BILL_ID + ") REFERENCES " + TABLE_BILL + "(" + COLUMN_BILL_ID + "), " +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + TABLE_FOOD + "(" + COLUMN_FOOD_ID + "))");
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
        return db.query(TABLE_USER, null, null, null, null, null, COLUMN_USER_ID + " DESC");
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

    public long insertCategory(String name, int imageResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, name);
        values.put(COLUMN_CATEGORY_IMAGE, imageResId);
        return db.insert(TABLE_CATEGORY, null, values);
    }
    public ArrayList<HomeHorModel> getAllCategoriesList() {
        ArrayList<HomeHorModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY, null, null, null, null, null, COLUMN_CATEGORY_ID + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME));
                int imageRes = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_IMAGE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID));
                list.add(new HomeHorModel(imageRes, name,id));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
    public ArrayList<HomeVerModel> getAllFoodWithCategory(int categoryId) {
        ArrayList<HomeVerModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT f." + COLUMN_FOOD_NAME + ", "
                + "f." + COLUMN_FOOD_IMAGE + ", "
                + "f." + COLUMN_FOOD_TIME + ", "
                + "f." + COLUMN_FOOD_PRICE + ", "
                + "IFNULL(AVG(r." + COLUMN_REVIEW_RATING + "), 0) AS avg_rating " +
                "FROM " + TABLE_FOOD + " f " +
                "LEFT JOIN " + TABLE_REVIEW + " r " +
                "ON f." + COLUMN_FOOD_ID + " = r." + COLUMN_FOOD_ID + " " +
                "WHERE f." + COLUMN_CATEGORY_ID + " = ? " +   // lọc theo categoryId
                "GROUP BY f." + COLUMN_FOOD_ID + ", f." + COLUMN_FOOD_NAME + ", f." + COLUMN_FOOD_IMAGE + ", f." + COLUMN_FOOD_TIME + ", f." + COLUMN_FOOD_PRICE + " " +
                "ORDER BY f." + COLUMN_FOOD_ID + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOOD_NAME));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FOOD_IMAGE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOOD_TIME));
                double priceValue = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_FOOD_PRICE));
                double avgRating = cursor.getDouble(cursor.getColumnIndexOrThrow("avg_rating"));

                @SuppressLint("DefaultLocale")
                String rating = String.format("%.1f", avgRating);
                String price = "Min- " + priceValue + "$";

                list.add(new HomeVerModel(image, name, time, rating, price));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }
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
    public long insertFood(int categoryId, String foodName, double price, int image, String description, int quantity, int soldCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, categoryId);
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_IMAGE, image);
        values.put(COLUMN_FOOD_DESCRIPTION, description);
        values.put(COLUMN_FOOD_QUANTITY, quantity);
        values.put(COLUMN_FOOD_SOLID_COUNT, soldCount);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        values.put(COLUMN_FOOD_TIME, currentDate);
        return db.insert(TABLE_FOOD, null, values);
    }
    public int updateFood(int foodId, int categoryId, String foodName, double price, int image, String description, int quantity, int soldCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_ID, categoryId);
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_FOOD_PRICE, price);
        values.put(COLUMN_FOOD_IMAGE, image);
        values.put(COLUMN_FOOD_DESCRIPTION, description);
        values.put(COLUMN_FOOD_QUANTITY, quantity);
        values.put(COLUMN_FOOD_SOLID_COUNT, soldCount);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        values.put(COLUMN_FOOD_TIME, currentDate);
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
