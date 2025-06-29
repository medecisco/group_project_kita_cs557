package com.example.groupprojectkita;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Displays all users in a scrollable list.
 */
public class ManageUserActivityViewList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_view_list);
        TextView tv = findViewById(R.id.view_data);
        tv.setText(new DbHelper(this).getAllStudents());
    }
}
