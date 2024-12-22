package com.example.jinglecode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView1, lottieAnimationView2;
    private TextView jingleodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize the Lottie views
        lottieAnimationView1 = findViewById(R.id.l1);
        lottieAnimationView2 = findViewById(R.id.l2);
        jingleodeText = findViewById(R.id.jingleCodeText);

        // Load animations directly without delay
        lottieAnimationView1.setAnimation(R.raw.light);  // Reference to the "light" animation in the raw folder
        lottieAnimationView2.setAnimation(R.raw.tree);   // Reference to the "tree" animation in the raw folder

        // Start animations immediately
        lottieAnimationView1.loop(true);  // Set animation to loop
        lottieAnimationView2.loop(true);  // Set animation to loop
        lottieAnimationView1.playAnimation(); // Start the animation
        lottieAnimationView2.playAnimation(); // Start the animation

        // Animate the TextView (heading) with a fade-in effect
        animateTextView();

        // Start MainActivity after a short delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 8000); // Delay for 8 seconds
    }

    private void animateTextView() {
        // Create an alpha animation (fade-in effect)
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1000); // 1 second duration
        fadeIn.setStartOffset(2000); // Start after 2 seconds

        // Apply animation to the TextView
        jingleodeText.setVisibility(View.VISIBLE);  // Make the TextView visible
        jingleodeText.startAnimation(fadeIn);  // Start the fade-in animation
    }
}
