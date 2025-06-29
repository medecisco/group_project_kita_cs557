package com.example.groupprojectkita;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * User registration screen: collects user info and inserts into DB.
 */
public class RegisterActivity extends AppCompatActivity {
    private DbHelper db;
    private EditText editName, editEmail, editMobile, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editMobile = findViewById(R.id.edit_mobile);
        editPassword = findViewById(R.id.edit_password);

        findViewById(R.id.button_register).setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String mobile = editMobile.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (db.insertStudent(name, email, mobile, pass) > 0) {
            Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Registration Failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
