package com.example.groupprojectkita;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Login screen: validates credentials, navigates to Home or Register.
 */
public class LoginActivity extends AppCompatActivity {
    private DbHelper db;
    private EditText editName, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbHelper(this);
        editName = findViewById(R.id.edit_name);
        editPassword = findViewById(R.id.edit_password);

        findViewById(R.id.button_login).setOnClickListener(v -> validateLogin());
        findViewById(R.id.button_register).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void validateLogin() {
        String name = editName.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();

        if (name.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
        } else if (db.validateLogin(name, pass)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid name or password", Toast.LENGTH_SHORT).show();
        }
    }
}
