package com.example.groupprojectkita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button btnManageUsers, btnManageProducts; // Example buttons for CRUDS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Link to your home UI layout

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnManageProducts = findViewById(R.id.btnManageProducts);

        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to an activity for managing users (CRUDS for user_table)
                Intent intent = new Intent(HomeActivity.this, UserManagementActivity.class); // You'll create this activity
                startActivity(intent);
            }
        });

        btnManageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to an activity for managing products (CRUDS for products_table)
                Intent intent = new Intent(HomeActivity.this, ProductManagementActivity.class); // You'll create this activity
                startActivity(intent);
            }
        });


        String loggedInUser = sharedPreferences.getString("loggedInUser", "Guest");
        Toast.makeText(this, "Welcome, " + loggedInUser + "!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu); // Ensure you have a res/menu/main_menu.xml with an action_logout item
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            // Perform logout action
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.remove("loggedInUser");
            editor.apply();

            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}