// ExpenseAdapter.java
package com.example.expensetracker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, 0, expenses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = getItem(position);
        TextView txtAmount = convertView.findViewById(R.id.txt_amount);
        TextView txtDate = convertView.findViewById(R.id.txt_date);
        TextView txtCategory = convertView.findViewById(R.id.txt_category);
        TextView txtNotes = convertView.findViewById(R.id.txt_notes);

        txtAmount.setText("Amount: " + expense.getAmount());
        txtDate.setText("Date: " + expense.getDate());
        txtCategory.setText("Category: " + expense.getCategory());
        txtNotes.setText("Notes: " + expense.getNotes());

        return convertView;
    }
}
