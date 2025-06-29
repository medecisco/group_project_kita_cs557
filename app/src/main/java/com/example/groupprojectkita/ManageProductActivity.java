package com.example.groupprojectkita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Manage products: insert, view, delete, update, search.
 */
public class ManageProductActivity extends AppCompatActivity implements View.OnClickListener {
    private DbHelper db;
    private EditText editProdId, editProdName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);

        db = new DbHelper(this);
        editProdId = findViewById(R.id.edit_prodid);
        editProdName = findViewById(R.id.edit_prodname);

        // Register button click listeners
        findViewById(R.id.button_insert).setOnClickListener(this);
        findViewById(R.id.button_view).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String idText = editProdId.getText().toString().trim();
        String nameText = editProdName.getText().toString().trim();
        int id = v.getId();

        if (id == R.id.button_insert) {
            if (nameText.isEmpty()) {
                showToast("Please fill the Fields");
            } else {
                db.insertProduct(nameText);
                clearFields();
                showToast("Saved successfully");
            }

        } else if (id == R.id.button_view) {
            startActivity(new Intent(this, ManageProductActivityViewList.class));

        } else if (id == R.id.button_delete) {
            if (idText.isEmpty()) {
                showToast("Please fill the Id");
            } else {
                db.deleteProduct(Long.parseLong(idText));
                clearFields();
                showToast("Deleted successfully");
            }

        } else if (id == R.id.button_update) {
            if (idText.isEmpty() || nameText.isEmpty()) {
                showToast("Please fill all the fields");
            } else {
                db.updateProduct(Long.parseLong(idText), nameText);
                clearFields();
                showToast("Updated successfully");
            }

        } else if (id == R.id.button_search) {
            if (idText.isEmpty()) {
                showToast("Please fill the Id");
            } else {
                try {
                    String prod = db.getProductName(Long.parseLong(idText));
                    editProdName.setText(prod);
                    showToast("Searched successfully");
                } catch (Exception e) {
                    showToast("Id is not available");
                }
            }
        }
    }

    private void clearFields() {
        editProdId.setText("");
        editProdName.setText("");
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
