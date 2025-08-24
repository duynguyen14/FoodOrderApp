package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.HomeHorModel;
import com.example.myapplication.models.HomeVerModel;
import com.example.myapplication.models.OrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    int imageResId1 = R.drawable.breakfast;
    int imageResId2 = R.drawable.burger2;
    int imageResId3 = R.drawable.burger4;
    int imageResId4 = R.drawable.coffe;
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 17;

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
        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (1,'Phở bò',50000," + imageResId1 + ",'Phở bò thơm ngon đặc biệt','100','20')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (2,'Nước cam',30000," + imageResId2 + ",'Nước cam tươi mát lạnh','200','50')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (3,'Bánh mì thịt',20000, " + imageResId3 + ",'Bánh mì thịt nóng giòn','150','40')");

        db.execSQL("INSERT INTO " + TABLE_FOOD + " (" +
                COLUMN_CATEGORY_ID + "," +
                COLUMN_FOOD_NAME + "," +
                COLUMN_FOOD_PRICE + "," +
                COLUMN_FOOD_IMAGE + "," +
                COLUMN_FOOD_DESCRIPTION + "," +
                COLUMN_FOOD_QUANTITY + "," +
                COLUMN_FOOD_SOLID_COUNT +
                ") VALUES (4,'Chè đậu xanh',25000, " + imageResId4 + " ,'Chè đậu xanh ngọt mát','120','30')");

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

        String query = "SELECT f." + COLUMN_FOOD_ID + ", " +
                "       f." + COLUMN_FOOD_NAME + ", " +
                "       f." + COLUMN_FOOD_IMAGE + ", " +
                "       f." + COLUMN_FOOD_TIME + ", " +
                "       f." + COLUMN_FOOD_PRICE + ", " +
                "       f." + COLUMN_FOOD_QUANTITY + ", " +
                "       IFNULL(AVG(r." + COLUMN_REVIEW_RATING + "), 0) AS avg_rating " +
                "FROM " + TABLE_FOOD + " f " +
                "LEFT JOIN " + TABLE_REVIEW + " r " +
                "       ON f." + COLUMN_FOOD_ID + " = r." + COLUMN_FOOD_ID + " " +
                "WHERE f." + COLUMN_CATEGORY_ID + " = ? " +
                "GROUP BY f." + COLUMN_FOOD_ID + ", " +
                "         f." + COLUMN_FOOD_NAME + ", " +
                "         f." + COLUMN_FOOD_IMAGE + ", " +
                "         f." + COLUMN_FOOD_TIME + ", " +
                "         f." + COLUMN_FOOD_PRICE + ", " +
                "         f." + COLUMN_FOOD_QUANTITY + " " +
                "ORDER BY f." + COLUMN_FOOD_ID + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FOOD_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOOD_NAME));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FOOD_IMAGE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOOD_TIME));
                double priceValue = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_FOOD_PRICE));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOOD_QUANTITY));

                double avgRating = cursor.getDouble(cursor.getColumnIndexOrThrow("avg_rating"));

                @SuppressLint("DefaultLocale")
                String rating = String.format("%.1f", avgRating);
                String price = "Min- " + priceValue + "$";

                list.add(new HomeVerModel(id, image, name, time, rating, price, quantity)); // Cần sửa lại HomeVerModel để nhận thêm tham số quantity
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
//    public boolean updateOrderStatus(int orderId, String status) {
//        Log.d("OrderManagement", "BẮT ĐẦU updateOrderStatus với orderId=" + orderId + ", status=" + status);
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Log.d("OrderManagement", "Đã lấy được database");
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_BILL_STATUS, status);
//        Log.d("OrderManagement", "Đã set ContentValues");
//
//        int rows = 0;
//        try {
//            rows = db.update(TABLE_BILL, values, COLUMN_BILL_ID + "=?", new String[]{String.valueOf(orderId)});
//            Log.d("OrderManagement", "Cập nhật đơn #" + orderId + " sang " + status + ", rows: " + rows);
//        } catch (Exception e) {
//            Log.e("OrderManagement", "Lỗi cập nhật đơn #" + orderId + ": " + e.getMessage());
//        } finally {
//            db.close();
//        }
//        return rows > 0;
//    }
public boolean updateOrderStatus(int orderId, String status) {
    Log.d("OrderManagement", "BẮT ĐẦU updateOrderStatus với orderId=" + orderId + ", status=" + status);

    SQLiteDatabase db = this.getWritableDatabase();
    Log.d("OrderManagement", "Đã lấy được database");

    int rowsAffected = 0;
    db.beginTransaction(); // Bắt đầu một giao dịch
    try {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_STATUS, status);

        // Cập nhật trạng thái đơn hàng
        rowsAffected = db.update(TABLE_BILL, values, COLUMN_BILL_ID + "=?", new String[]{String.valueOf(orderId)});
        Log.d("OrderManagement", "Cập nhật đơn #" + orderId + " sang " + status + ", rows: " + rowsAffected);

        // Nếu trạng thái là "Đã giao", tiến hành trừ số lượng sản phẩm
        if (rowsAffected > 0 && status.equals("Đã giao")) {
            // Lấy danh sách sản phẩm trong đơn hàng
            Cursor orderItemsCursor = getOrderItems(db, orderId);

            if (orderItemsCursor != null && orderItemsCursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    int productId = orderItemsCursor.getInt(orderItemsCursor.getColumnIndex(COLUMN_FOOD_ID));
                    @SuppressLint("Range")
                    int quantity = orderItemsCursor.getInt(orderItemsCursor.getColumnIndex(COLUMN_BILL_DETAIL_QUANTITY));

                    // Trừ số lượng sản phẩm trong kho
                    updateProductQuantity(db, productId, quantity);
                } while (orderItemsCursor.moveToNext());
            }
            if (orderItemsCursor != null) {
                orderItemsCursor.close();
            }
        }

        db.setTransactionSuccessful(); // Đánh dấu giao dịch thành công
    } catch (Exception e) {
        Log.e("OrderManagement", "Lỗi cập nhật đơn #" + orderId + ": " + e.getMessage());
        return false;
    } finally {
        db.endTransaction(); // Kết thúc giao dịch
        db.close();
    }
    return rowsAffected > 0;
}

    // Phương thức mới để lấy chi tiết các sản phẩm trong một đơn hàng
    private Cursor getOrderItems(SQLiteDatabase db, int orderId) {
        String[] projection = {
                COLUMN_FOOD_ID,
                COLUMN_BILL_DETAIL_QUANTITY
        };

        String selection = COLUMN_BILL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(orderId) };

        return db.query(
                TABLE_BILL_DETAIL,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    // Phương thức mới để cập nhật số lượng sản phẩm
    private void updateProductQuantity(SQLiteDatabase db, int productId, int quantityToSubtract) {
        String query = "UPDATE " + TABLE_FOOD +
                " SET " + COLUMN_FOOD_QUANTITY + " = " + COLUMN_FOOD_QUANTITY + " - ? " +
                " WHERE " + COLUMN_FOOD_ID + " = ?";

        db.execSQL(query, new String[]{String.valueOf(quantityToSubtract), String.valueOf(productId)});
        Log.d("OrderManagement", "Đã trừ " + quantityToSubtract + " sản phẩm #" + productId);
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
    // Thêm sản phẩm vào giỏ hàng
    // Thêm sản phẩm vào giỏ hàng
    // Thêm sản phẩm vào giỏ hàng
    public void addCart(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 1. Lấy CartID theo user
        int cartId = -1;
        Cursor cartCursor = db.rawQuery(
                "SELECT " + COLUMN_CART_ID + " FROM " + TABLE_CART + " WHERE " + COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        if (cartCursor.moveToFirst()) {
            cartId = cartCursor.getInt(0);
        } else {
            // Nếu chưa có thì tạo giỏ mới
            ContentValues cartValues = new ContentValues();
            cartValues.put(COLUMN_USER_ID, userId);
            cartId = (int) db.insert(TABLE_CART, null, cartValues);
        }
        cartCursor.close();

        // 2. Kiểm tra sản phẩm trong shoppingcartdetail
        Cursor detailCursor = db.rawQuery(
                "SELECT " + COLUMN_CART_DETAIL_QUANTITY + " FROM " + TABLE_CART_DETAIL +
                        " WHERE " + COLUMN_CART_ID + " = ? AND " + COLUMN_FOOD_ID + " = ?",
                new String[]{String.valueOf(cartId), String.valueOf(productId)}
        );

        if (detailCursor.moveToFirst()) {
            // Có rồi -> update số lượng
            int oldQuantity = detailCursor.getInt(0);
            ContentValues updateValues = new ContentValues();
            updateValues.put(COLUMN_CART_DETAIL_QUANTITY, oldQuantity + quantity);

            db.update(TABLE_CART_DETAIL, updateValues,
                    COLUMN_CART_ID + "=? AND " + COLUMN_FOOD_ID + "=?",
                    new String[]{String.valueOf(cartId), String.valueOf(productId)});
        } else {
            // Chưa có -> insert mới
            ContentValues detailValues = new ContentValues();
            detailValues.put(COLUMN_CART_ID, cartId);
            detailValues.put(COLUMN_FOOD_ID, productId);
            detailValues.put(COLUMN_CART_DETAIL_QUANTITY, quantity);

            db.insert(TABLE_CART_DETAIL, null, detailValues);
        }
        detailCursor.close();
        // ===== LOG CART =====
        Cursor logCursor = db.rawQuery(
                "SELECT c." + COLUMN_CART_ID + ", cd." + COLUMN_FOOD_ID + ", cd." + COLUMN_CART_DETAIL_QUANTITY +
                        " FROM " + TABLE_CART + " c " +
                        "LEFT JOIN " + TABLE_CART_DETAIL + " cd ON c." + COLUMN_CART_ID + " = cd." + COLUMN_CART_ID +
                        " WHERE c." + COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        if (logCursor != null && logCursor.moveToFirst()) {
            do {
                int logCartId = logCursor.getInt(0);
                int logFoodId = logCursor.getInt(1);
                int logQuantity = logCursor.getInt(2);
                android.util.Log.d("DB_LOG", "CartID=" + logCartId + " | FoodID=" + logFoodId + " | Quantity=" + logQuantity);
            } while (logCursor.moveToNext());
            logCursor.close();
        }
        db.close();
    }

    public Cursor getCartItemsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT cd." + COLUMN_CART_DETAIL_ID + ", f." + COLUMN_FOOD_ID +
                ", f." + COLUMN_FOOD_NAME + ", f." + COLUMN_FOOD_PRICE +
                ", f." + COLUMN_FOOD_IMAGE + ", cd." + COLUMN_CART_DETAIL_QUANTITY +
                " FROM " + TABLE_CART + " c " +
                " JOIN " + TABLE_CART_DETAIL + " cd ON c." + COLUMN_CART_ID + " = cd." + COLUMN_CART_ID +
                " JOIN " + TABLE_FOOD + " f ON cd." + COLUMN_FOOD_ID + " = f." + COLUMN_FOOD_ID +
                " WHERE c." + COLUMN_USER_ID + " = ?";

        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }




    // Thêm sản phẩm vào yêu thích
    public void addFavorite(int userId, int productId) {

    }
    // Lấy danh sách bill theo userId
    public List<OrderModel> getBillsByUserId(int userId) {
        List<OrderModel> billList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_BILL_ID + ", "
                + COLUMN_BILL_DATE + ", "
                + COLUMN_BILL_TOTAL + ", "
                + COLUMN_BILL_STATUS +
                " FROM " + TABLE_BILL +
                " WHERE " + COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int billId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BILL_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BILL_DATE));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BILL_TOTAL));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BILL_STATUS));

                // Dùng OrderModel để chứa dữ liệu bill
                OrderModel bill = new OrderModel(
                        billId,
                        "BILL-" + billId,   // Mã bill, bạn có thể hiển thị "BILL-xxx"
                        status,
                        date,
                        total
                );
                billList.add(bill);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return billList;
    }

    // Thêm 1 bill và các billDetail
    public long insertBill(int userId, String billDate, double total, String status, List<CartItem> cartItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        long billId = -1;
        db.beginTransaction();
        try {
            // 1. Insert BILL
            ContentValues billValues = new ContentValues();
            billValues.put(COLUMN_USER_ID, userId);
            billValues.put(COLUMN_BILL_DATE, billDate);
            billValues.put(COLUMN_BILL_TOTAL, total);
            billValues.put(COLUMN_BILL_STATUS, status);

            billId = db.insert(TABLE_BILL, null, billValues);
            Log.d("DB", "Inserted BILL id=" + billId);
            if (billId == -1) throw new Exception("Insert bill thất bại");

            // 2. Insert BILL DETAIL
            for (CartItem item : cartItems) {
                ContentValues detailValues = new ContentValues();
                detailValues.put(COLUMN_BILL_ID, billId);
                detailValues.put(COLUMN_FOOD_ID, item.getFoodId());
                detailValues.put(COLUMN_BILL_DETAIL_QUANTITY, item.getQuantity());

                long detailId = db.insert(TABLE_BILL_DETAIL, null, detailValues);
                Log.d("DB", "Inserted BILL_DETAIL id=" + detailId + " for foodId=" + item.getFoodId());
                if (detailId == -1) throw new Exception("Insert bill detail thất bại");
            }

            // 3. Lấy tất cả cart của user
            Cursor cursor = db.query(TABLE_CART, new String[]{COLUMN_CART_ID},
                    COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)},
                    null, null, null);

            if (cursor != null) {
                Log.d("DB", "Cursor count=" + cursor.getCount());
                while (cursor.moveToNext()) {
                    int cartId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_ID));
                    Log.d("DB", "Deleting CART_DETAIL for cartId=" + cartId);
                    // Xóa chi tiết giỏ hàng trước
                    int deletedDetails = db.delete(TABLE_CART_DETAIL, COLUMN_CART_ID + "=?", new String[]{String.valueOf(cartId)});
                    Log.d("DB", "Deleted " + deletedDetails + " cart details");

                    // Xóa cart chính
                    int deletedCart = db.delete(TABLE_CART, COLUMN_CART_ID + "=?", new String[]{String.valueOf(cartId)});
                    Log.d("DB", "Deleted cart id=" + cartId + " result=" + deletedCart);
                }
                cursor.close();
            }

            db.setTransactionSuccessful(); // commit
        } catch (Exception e) {
            e.printStackTrace();
            billId = -1;
        } finally {
            db.endTransaction();
        }

        return billId;
    }



}
