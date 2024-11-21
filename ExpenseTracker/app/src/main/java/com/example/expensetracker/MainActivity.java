// MainActivity.java
package com.example.expensetracker;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;

    private ActivityResultLauncher<Intent> expenseInputLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the expense list and adapter
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        ListView expenseListView = findViewById(R.id.expenseListView);
        expenseListView.setAdapter(expenseAdapter);

        // Initialize the ActivityResultLauncher
        expenseInputLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Expense expense = (Expense) data.getSerializableExtra("expense");
                            expenseList.add(expense);
                            expenseAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        // Set up the button to open ExpenseInputActivity
        Button addExpenseButton = findViewById(R.id.addExpenseButton);
        addExpenseButton.setOnClickListener(v -> openExpenseInputActivity());
    }

    private void openExpenseInputActivity() {
        Intent intent = new Intent(MainActivity.this, ExpenseInputActivity.class);
        expenseInputLauncher.launch(intent);
    }

}
