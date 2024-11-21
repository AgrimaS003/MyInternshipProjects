package com.example.recipeshare;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleEditText, ingredientsEditText, instructionsEditText;
    private ImageView recipeImageView;
    private Button uploadImageButton, addRecipeButton;
    private Uri imageUri;

    private DatabaseReference recipeDatabase;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        titleEditText = findViewById(R.id.titleEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        instructionsEditText = findViewById(R.id.instructionsEditText);
        recipeImageView = findViewById(R.id.recipeImageView);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        recipeDatabase = FirebaseDatabase.getInstance().getReference("Recipes");
        storageReference = FirebaseStorage.getInstance().getReference("RecipeImages");

        uploadImageButton.setOnClickListener(v -> openFileChooser());
        addRecipeButton.setOnClickListener(v -> uploadRecipe());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            recipeImageView.setImageURI(imageUri);
        }
    }

    private void uploadRecipe() {
        String title = titleEditText.getText().toString();
        String ingredients = ingredientsEditText.getText().toString();
        String instructions = instructionsEditText.getText().toString();

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    Recipe recipe = new Recipe(title, ingredients, instructions, uri.toString());
                    recipeDatabase.push().setValue(recipe);
                    Toast.makeText(AddRecipeActivity.this, "Recipe added!", Toast.LENGTH_SHORT).show();
                });
            });
        } else {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
        }
    }
}
