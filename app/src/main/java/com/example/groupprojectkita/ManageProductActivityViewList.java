package com.example.groupprojectkita;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Displays all products in a scrollable list.
 */
public class ManageProductActivityViewList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product_view_list);
        TextView tv = findViewById(R.id.view_data);
        tv.setText(new DbHelper(this).getAllProducts());
    }
}
