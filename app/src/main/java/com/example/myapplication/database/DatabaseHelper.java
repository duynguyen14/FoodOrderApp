package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 1;

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
                COLUMN_FOOD_IMAGE + " TEXT, " +
                COLUMN_FOOD_DESCRIPTION + " TEXT, " +
                COLUMN_FOOD_QUANTITY + " TEXT, " +
                COLUMN_FOOD_SOLID_COUNT + " TEXT, " +
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
}
