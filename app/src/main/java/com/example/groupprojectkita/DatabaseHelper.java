package com.example.groupprojectkita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.groupprojectkita.models.User;
import com.example.groupprojectkita.models.Product;

import java.util.ArrayList;
import java.util.List;

//DatabaseHelper class is a subclass of SQLiteOpenHelper class
//it is used to manage the database - CRUD
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "Login.db";
    private static final int DATABASE_VERSION = 1;

    // Table 1: user_table (based on your 'user' table)
    public static final String TABLE_USERS = "user";
    public static final String COL_USER_EMAIL = "email"; // Primary Key
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_NAME = "name";

    // Table 2: products_table (Example for a second table, as required by the project)
    public static final String TABLE_PRODUCTS = "products";
    public static final String COL_PRODUCT_ID = "product_id"; // Primary Key
    public static final String COL_PRODUCT_NAME = "product_name";
    public static final String COL_PRODUCT_PRICE = "product_price";
    public static final String COL_PRODUCT_DESCRIPTION = "product_description";


    // This is a normal constructor that has one formal parameter - context
    // It creates a new database named Login.db with version 1
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate function has one formal parameter - db and returns no value
    // execSQL() - creates new tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table (from your code, with constants)
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USER_EMAIL + " TEXT PRIMARY KEY,"
                + COL_USER_PASSWORD + " TEXT,"
                + COL_USER_NAME + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        // Create products table (example of a second table)
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_PRODUCT_NAME + " TEXT,"
                + COL_PRODUCT_PRICE + " REAL,"
                + COL_PRODUCT_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        // Add more table creations here if you have 3 tables
    }

    // onUpgrade function has three formal parameters - db, oldVersion and newVersion and returns no value
    // it is used to make the alteration of table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS); // Drop the second table too
        // Recreate tables
        onCreate(db);
    }

    // --- User Table Operations (CRUDS) ---

    // insert() has three formal parameters - emel, pwd and nama and returns a boolean value
    // it inserts a new row/record into the table (database) - Create
    public boolean insert(String email, String password, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_EMAIL, email);
        // IMPORTANT: In a real app, hash the password before storing!
        values.put(COL_USER_PASSWORD, password);
        values.put(COL_USER_NAME, name);

        long ins = db.insert(TABLE_USERS, null, values);
        db.close();
        return ins != -1;
    }

    // chkemail() has one formal parameter - emel and returns a boolean value
    // it checks whether the email exists or not in the table (database)
    // Returns true if email DOES NOT exist (suitable for registration)
    public boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_EMAIL + "=?", new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count <= 0; // True if email not found, False if email found
    }

    // chklogin() has two formal parameters - emel and password and returns a boolean value
    // it checks whether the email and password exist or not for login - Read/Search
    // Returns true if a match is found
    public boolean chklogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count == 1; // True if exactly one record matches (login successful)
    }

    // Get all users - Read (for displaying user list,in an admin panel)
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                // Get data from cursor and set to User object
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD))); // Be careful exposing passwords
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Update user record - Update (change name or password)
    public boolean updateUser(String oldEmail, String newEmail, String newPassword, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_EMAIL, newEmail);
        values.put(COL_USER_PASSWORD, newPassword); // Hash new password
        values.put(COL_USER_NAME, newName);

        int rowsAffected = db.update(TABLE_USERS, values, COL_USER_EMAIL + " = ?", new String[]{oldEmail});
        db.close();
        return rowsAffected > 0;
    }

    // Delete a user - Delete
    public boolean deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_USERS, COL_USER_EMAIL + " = ?", new String[]{email});
        db.close();
        return rowsAffected > 0;
    }

    // Search for a user by email - Search
    public User searchUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE " + COL_USER_EMAIL + " LIKE ?", new String[]{"%" + email + "%"});
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME)));
        }
        cursor.close();
        db.close();
        return user;
    }


    // --- Product Table Operations (CRUDS) ---

    // Insert a new product (Create)
    public boolean insertProduct(String name, double price, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_DESCRIPTION, description);
        long ins = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return ins != -1;
    }

    // Get all products (Read)
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRODUCT_PRICE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_DESCRIPTION)));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    // Update product (Update)
    public boolean updateProduct(int id, String newName, double newPrice, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, newName);
        values.put(COL_PRODUCT_PRICE, newPrice);
        values.put(COL_PRODUCT_DESCRIPTION, newDescription);
        int rowsAffected = db.update(TABLE_PRODUCTS, values, COL_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    // Delete a product (Delete)
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_PRODUCTS, COL_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    // Search for a product by name (Search)
    public Product searchProductByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + COL_PRODUCT_NAME + " LIKE ?", new String[]{"%" + name + "%"});
        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)));
            product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRODUCT_PRICE)));
            product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_DESCRIPTION)));
        }
        cursor.close();
        db.close();
        return product;
    }
}