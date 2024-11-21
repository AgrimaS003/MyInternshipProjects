// ExpenseInputActivity.java
package com.example.expensetracker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ExpenseInputActivity extends AppCompatActivity {

    private EditText amountInput, dateInput, notesInput;
    private Spinner categorySpinner;
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_input);

        // Initialize input fields
        amountInput = findViewById(R.id.amountInput);
        dateInput = findViewById(R.id.dateInput);
        notesInput = findViewById(R.id.notesInput);
        categorySpinner = findViewById(R.id.categorySpinner);

        // Initialize Spinner and Category List
        categoryList = new ArrayList<>();

        // Add default categories from resources
        String[] defaultCategories = getResources().getStringArray(R.array.category_array);
        for (String category : defaultCategories) {
            categoryList.add(category);
        }
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up save button to save expense data
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });
    }

    private void saveExpense() {
        String amount = amountInput.getText().toString();
        String date = dateInput.getText().toString();
        String notes = notesInput.getText().toString();

        // Retrieve the selected category from the Spinner
        String category = categorySpinner.getSelectedItem().toString();

        // Create an Expense object with the retrieved data
        Expense expense = new Expense(amount, date, category, notes);

        // Pass the Expense object back to MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("expense", expense);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
