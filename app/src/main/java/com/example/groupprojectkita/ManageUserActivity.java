package com.example.groupprojectkita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Manage users: insert, view, delete, update, search.
 */
public class ManageUserActivity extends AppCompatActivity implements View.OnClickListener {
    private DbHelper db;
    private EditText editId, editName, editEmail, editMobile, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        db = new DbHelper(this);
        editId = findViewById(R.id.edit_id);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editMobile = findViewById(R.id.edit_mobile);
        editPassword = findViewById(R.id.edit_password);

        // Register button click listeners
        findViewById(R.id.button_insert).setOnClickListener(this);
        findViewById(R.id.button_view).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String idText = editId.getText().toString().trim();
        String nameText = editName.getText().toString().trim();
        String emailText = editEmail.getText().toString().trim();
        String mobileText = editMobile.getText().toString().trim();
        String passwordText = editPassword.getText().toString().trim();
        int id = v.getId();

        if (id == R.id.button_insert) {
            if (nameText.isEmpty() || emailText.isEmpty() || mobileText.isEmpty() || passwordText.isEmpty()) {
                showToast("Please fill all fields");
            } else {
                db.insertStudent(nameText, emailText, mobileText, passwordText);
                clearFields();
                showToast("Saved successfully");
            }

        } else if (id == R.id.button_view) {
            startActivity(new Intent(this, ManageUserActivityViewList.class));

        } else if (id == R.id.button_delete) {
            if (idText.isEmpty()) {
                showToast("Please fill the Id");
            } else {
                db.deleteStudent(Long.parseLong(idText));
                clearFields();
                showToast("Deleted successfully");
            }

        } else if (id == R.id.button_update) {
            if (idText.isEmpty() || nameText.isEmpty() || emailText.isEmpty() || mobileText.isEmpty() || passwordText.isEmpty()) {
                showToast("Please fill all fields");
            } else {
                db.updateStudent(Long.parseLong(idText), nameText, emailText, mobileText, passwordText);
                clearFields();
                showToast("Updated successfully");
            }

        } else if (id == R.id.button_search) {
            if (idText.isEmpty()) {
                showToast("Please fill the Id");
            } else {
                try {
                    long userId = Long.parseLong(idText);
                    editName.setText(db.getName(userId));
                    editEmail.setText(db.getEmail(userId));
                    editMobile.setText(db.getMobile(userId));
                    editPassword.setText(db.getPassword(userId));
                    showToast("Searched successfully");
                } catch (Exception e) {
                    showToast("Id is not available");
                }
            }
        }
    }

    private void clearFields() {
        editId.setText("");
        editName.setText("");
        editEmail.setText("");
        editMobile.setText("");
        editPassword.setText("");
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
