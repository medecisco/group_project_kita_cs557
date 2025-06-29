package com.example.groupprojectkita;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Home screen after login: options to manage users/products or logout.
 */
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.button_manage_user).setOnClickListener(v ->
                startActivity(new Intent(this, ManageUserActivity.class)));

        findViewById(R.id.button_manage_product).setOnClickListener(v ->
                startActivity(new Intent(this, ManageProductActivity.class)));

        findViewById(R.id.button_logout).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
