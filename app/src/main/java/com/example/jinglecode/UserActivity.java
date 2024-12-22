package com.example.jinglecode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private LinearLayout activitiesList, addScheduleSection;
    private TextView timerTextView, activitiesTitle;
    private EditText activityInput, activityTimeInput;
    private Button addActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize Views
        addScheduleSection = findViewById(R.id.addScheduleSection);
        activitiesList = findViewById(R.id.activitiesList);  // Initialize activitiesList here
        timerTextView = findViewById(R.id.timerTextView);
        activitiesTitle = findViewById(R.id.activitiesTitle);
        activityInput = findViewById(R.id.activityInput);
        activityTimeInput = findViewById(R.id.activityTimeInput);
        addActivityButton = findViewById(R.id.addActivityButton);

        // Get the remaining time passed from MainActivity
        long remainingTime = getIntent().getLongExtra("remainingTime", 0);

        // Convert remaining time to a readable format (HH:MM:SS)
        String formattedTime = formatTime(remainingTime);
        timerTextView.setText("Holiday Timer: " + formattedTime);

        // Add click listener to show/hide the "Add Schedule" section
        activitiesTitle.setOnClickListener(v -> {
            if (addScheduleSection.getVisibility() == View.GONE) {
                addScheduleSection.setVisibility(View.VISIBLE); // Show input fields
            } else {
                addScheduleSection.setVisibility(View.GONE); // Hide input fields
            }
        });

        // Add click listener for adding new activity
        addActivityButton.setOnClickListener(v -> {
            String activityName = activityInput.getText().toString().trim();
            String activityTime = activityTimeInput.getText().toString().trim();

            // Check if both fields are filled
            if (!activityName.isEmpty() && !activityTime.isEmpty()) {
                createActivity(activityName, activityTime);
                activityInput.setText(""); // Clear activity input
                activityTimeInput.setText(""); // Clear time input
            } else {
                Toast.makeText(UserActivity.this, "Please fill both fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to create and add activities dynamically
    private void createActivity(String activityName, String activityTime) {
        // Create a new layout for the activity
        LinearLayout activityLayout = new LinearLayout(this);
        activityLayout.setOrientation(LinearLayout.HORIZONTAL);
        activityLayout.setPadding(16, 16, 16, 16);

        // Create a CheckBox for the activity
        CheckBox activityCheckBox = new CheckBox(this);
        activityCheckBox.setText(activityName);
        activityCheckBox.setTextSize(18);
        activityCheckBox.setTextColor(getResources().getColor(android.R.color.black));

        // Create TextView for activity time
        TextView activityTimeText = new TextView(this);
        activityTimeText.setText(activityTime);
        activityTimeText.setTextSize(18);
        activityTimeText.setTextColor(getResources().getColor(android.R.color.black));

        // Create a button to remove the activity
        Button removeButton = new Button(this);
        removeButton.setText("Remove");
        removeButton.setTextSize(14);
        removeButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        removeButton.setTextColor(getResources().getColor(android.R.color.white));

        // Set a click listener for the remove button to delete the activity
        removeButton.setOnClickListener(v -> {
            activitiesList.removeView(activityLayout); // Remove the activity layout from the list
            Toast.makeText(UserActivity.this, "Activity removed", Toast.LENGTH_SHORT).show();
        });

        // Add CheckBox, activity time, and remove button to the activity layout
        activityLayout.addView(activityCheckBox);
        activityLayout.addView(activityTimeText);
        activityLayout.addView(removeButton);

        // Add the activity layout to the list
        activitiesList.addView(activityLayout);
    }

    // Helper method to format time (milliseconds to HH:MM:SS)
    private String formatTime(long timeInMillis) {
        int hours = (int) (timeInMillis / (1000 * 60 * 60));
        int minutes = (int) ((timeInMillis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((timeInMillis % (1000 * 60)) / 1000);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
