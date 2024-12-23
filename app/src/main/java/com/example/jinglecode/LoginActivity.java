package com.example.jinglecode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.view.LayoutInflater;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton b1, b2; // b1 for Login, b2 for Sign Up
    EditText e1, e2;
    String s1, s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        b1 = findViewById(R.id.btn1); // Login Button
        b2 = findViewById(R.id.btn2); // Sign Up Button
        e1 = findViewById(R.id.ed1); // Username EditText
        e2 = findViewById(R.id.ed2); // Password EditText

        // Click listener for the Login button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = e1.getText().toString();
                s2 = e2.getText().toString();

                // Check credentials
                if (s1.equals("admin") && s2.equals("1234")) {
                    // If credentials are correct, navigate to UserActivity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish(); // Close LoginActivity
                } else {
                    // If credentials are incorrect, show a Toast message
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Click listener for the Sign Up button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the Sign-Up dialog
                showSignUpDialog();
            }
        });
    }

    // Method to show the Sign-Up dialog
    private void showSignUpDialog() {
        // Create the dialog builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // Inflate the sign-up form layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View signUpView = inflater.inflate(R.layout.signup, null);
        dialogBuilder.setView(signUpView);

        // Find the fields from the dialog view
        EditText usernameField = signUpView.findViewById(R.id.ed_signup_username);
        EditText passwordField = signUpView.findViewById(R.id.ed_signup_password);
        EditText confirmPasswordField = signUpView.findViewById(R.id.ed_signup_confirm_password);
        AppCompatButton signUpButton = signUpView.findViewById(R.id.btn_signup);

        // Set the dialog properties
        dialogBuilder.setCancelable(true);

        // Set the positive button for the dialog (Sign Up action)
        final AlertDialog signUpDialog = dialogBuilder.create();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                // Simple validation check
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Display a success message for sign-up
                    Toast.makeText(getApplicationContext(), "Sign-Up Successful! Welcome, " + username, Toast.LENGTH_SHORT).show();
                    // Clear fields after successful sign-up
                    usernameField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                    signUpDialog.dismiss(); // Close the dialog
                }
            }
        });

        // Show the dialog
        signUpDialog.show();
    }
}
