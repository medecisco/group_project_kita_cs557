package com.example.groupprojectkita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DbHelper handles SQLite DB operations: creation, upgrades, and CRUD.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Database config
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    // Table & column names
    private static final String TABLE_STUDENT = "_student";
    private static final String TABLE_PRODUCT = "_product";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PRODID = "prodid";
    private static final String KEY_PRODNAME = "prodname";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create student table
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_MOBILE + " TEXT, "
                + KEY_PASSWORD + " TEXT);");

        // Create product table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCT + " ("
                + KEY_PRODID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PRODNAME + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    // ================= User Methods ================= //

    // Validate login credentials
    public boolean validateLogin(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT,
                new String[]{KEY_ID},
                KEY_NAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{name, password},
                null, null, null);
        boolean found = cursor.getCount() > 0;
        cursor.close();
        return found;
    }

    // Insert new student
    public long insertStudent(String name, String email, String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_EMAIL, email);
        cv.put(KEY_MOBILE, mobile);
        cv.put(KEY_PASSWORD, password);
        return db.insert(TABLE_STUDENT, null, cv);
    }

    // Fetch all students as formatted string
    public String getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_STUDENT,
                null, null, null, null, null, null);
        StringBuilder sb = new StringBuilder();
        while (c.moveToNext()) {
            sb.append("Id: ").append(c.getInt(c.getColumnIndexOrThrow(KEY_ID))).append("\n")
                    .append("Name: ").append(c.getString(c.getColumnIndexOrThrow(KEY_NAME))).append("\n")
                    .append("Email: ").append(c.getString(c.getColumnIndexOrThrow(KEY_EMAIL))).append("\n")
                    .append("Mobile: ").append(c.getString(c.getColumnIndexOrThrow(KEY_MOBILE))).append("\n")
                    .append("Password: ").append(c.getString(c.getColumnIndexOrThrow(KEY_PASSWORD))).append("\n\n");
        }
        c.close();
        return sb.toString();
    }

    // Delete a student by ID
    public void deleteStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Update student info
    public void updateStudent(long id, String name, String email, String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_EMAIL, email);
        cv.put(KEY_MOBILE, mobile);
        cv.put(KEY_PASSWORD, password);
        db.update(TABLE_STUDENT, cv, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Get field by student ID
    private String getStudentField(long id, String column, int index) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_STUDENT, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c != null && c.moveToFirst()) {
            String result = c.getString(index);
            c.close();
            return result;
        }
        return null;
    }

    public String getName(long id)    { return getStudentField(id, KEY_NAME, 1); }
    public String getEmail(long id)   { return getStudentField(id, KEY_EMAIL, 2); }
    public String getMobile(long id)  { return getStudentField(id, KEY_MOBILE, 3); }
    public String getPassword(long id){ return getStudentField(id, KEY_PASSWORD, 4); }

    // ================ Product Methods ================ //

    // Insert new product
    public long insertProduct(String prodname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_PRODNAME, prodname);
        return db.insert(TABLE_PRODUCT, null, cv);
    }

    // Fetch all products as formatted string
    public String getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_PRODUCT,
                null, null, null, null, null, null);
        StringBuilder sb = new StringBuilder();
        while (c.moveToNext()) {
            sb.append("Id: ").append(c.getInt(c.getColumnIndexOrThrow(KEY_PRODID))).append("\n")
                    .append("Product Name: ").append(c.getString(c.getColumnIndexOrThrow(KEY_PRODNAME))).append("\n\n");
        }
        c.close();
        return sb.toString();
    }

    // Delete a product by ID
    public void deleteProduct(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, KEY_PRODID + "=?", new String[]{String.valueOf(id)});
    }

    // Update product name by ID
    public void updateProduct(long id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_PRODNAME, name);
        db.update(TABLE_PRODUCT, cv, KEY_PRODID + "=?", new String[]{String.valueOf(id)});
    }

    // Get product name by ID
    public String getProductName(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_PRODUCT, null, KEY_PRODID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c != null && c.moveToFirst()) {
            String name = c.getString(c.getColumnIndexOrThrow(KEY_PRODNAME));
            c.close();
            return name;
        }
        return null;
    }
}
