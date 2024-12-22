package com.example.jinglecode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessage, selectedDates, timerDisplay;
    private Button startDateButton, endDateButton, setTimerButton, resetTimerButton;
    private Calendar startDate, endDate;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 0;
    private EditText activityNameEditText;
    private TextView activityNameTextView;
    private CheckBox activityCheckBox;
    private Button setActivityNameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        welcomeMessage = findViewById(R.id.welcomeMessage);
        selectedDates = findViewById(R.id.selectedDates);
        timerDisplay = findViewById(R.id.timerDisplay);
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        setTimerButton = findViewById(R.id.setTimerButton);
        resetTimerButton = findViewById(R.id.resetTimerButton);

        // Set a greeting message
        welcomeMessage.setText("Welcome to Jingle Code!");


        // Set listeners for the buttons
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true); // Show start date picker
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false); // Show end date picker
            }
        });

        setTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDate != null && endDate != null) {
                    setHolidayTimer(); // Set timer based on the selected dates
                    // Pass the remaining time to UserActivity
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("remainingTime", timeLeftInMillis);
                    startActivity(intent);
                } else {
                    selectedDates.setText("Please select both start and end dates.");
                }
            }
        });

        resetTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetHolidayTimer(); // Reset the timer
            }
        });
    }

    private void showDatePickerDialog(final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        if (isStartDate) {
                            startDate = selectedDate;
                            selectedDates.setText("Start Date: " + formatDate(startDate) + "\n\n");
                        } else {
                            endDate = selectedDate;
                            selectedDates.append("End Date: " + formatDate(endDate));
                        }
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private String formatDate(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }

    private void setHolidayTimer() {
        if (startDate != null && endDate != null) {
            long startTime = startDate.getTimeInMillis();
            long endTime = endDate.getTimeInMillis();

            timeLeftInMillis = endTime - startTime;

            // Check if the start date is before the end date
            if (timeLeftInMillis > 0) {
                // Start a countdown timer
                countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Update the timer display
                        timerDisplay.setText(formatTime(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        // Handle when the timer finishes
                        timerDisplay.setText("Holiday is over!");
                    }
                }.start();
            } else {
                timerDisplay.setText("Start date should be before end date.");
            }
        }
    }

    private String formatTime(long timeInMillis) {
        int hours = (int) (timeInMillis / (1000 * 60 * 60));
        int minutes = (int) ((timeInMillis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((timeInMillis % (1000 * 60)) / 1000);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void resetHolidayTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop the countdown timer
        }
        timerDisplay.setText("Holiday Timer Reset");
        selectedDates.setText(""); // Clear the selected dates
    }
}
