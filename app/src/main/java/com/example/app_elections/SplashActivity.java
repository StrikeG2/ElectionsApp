package com.example.app_elections;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 5000; // 10 secondes

    private TextView logoText;
    private ImageView dot1, dot2, dot3;
    private View backgroundView, logoCircle;

    private boolean longClickDetected = false; // Nouveau : pour bloquer la transition normale

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        setupGradientBackground();
        setupColoredLogo();
        startAnimations();

        // Nouveau : écouteur de long clic
        backgroundView.setOnLongClickListener(v -> {
            longClickDetected = true;
            Intent intent = new Intent(SplashActivity.this, AdminLoginActivity.class);
            startActivity(intent);
            finish(); // pour empêcher le retour à la splash
            return true;
        });

        // Transition vers MainActivity après un délai si aucun appui long
        new Handler().postDelayed(() -> {
            if (!longClickDetected) {
                Intent intent = new Intent(SplashActivity.this, UserHomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, SPLASH_DELAY);
    }

    private void initViews() {
        logoText = findViewById(R.id.logo_text);
        logoCircle = findViewById(R.id.logo_circle);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        backgroundView = findViewById(R.id.background_view);
    }

    private void setupGradientBackground() {
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{
                        ContextCompat.getColor(this, R.color.gradient_blue),    // Bleu en haut
                        ContextCompat.getColor(this, R.color.gradient_green),   // Vert au milieu
                        ContextCompat.getColor(this, R.color.gradient_yellow)   // Jaune en bas
                }
        );
        backgroundView.setBackground(gradientDrawable);
    }

    private void setupColoredLogo() {
        String logoTextString = "Result241";
        SpannableString spannableString = new SpannableString(logoTextString);

        spannableString.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.text_primary)),
                0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.number_green)),
                6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.number_yellow)),
                7, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.number_blue)),
                8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        logoText.setText(spannableString);
        logoText.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
    }

    private void startAnimations() {
        animateLogoCircle();
        animateLogo();
        animateDecorativeDots();
    }

    private void animateLogoCircle() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logoCircle, "alpha", 0f, 0.9f);
        fadeIn.setDuration(800);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logoCircle, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logoCircle, "scaleY", 0f, 1f);
        scaleX.setDuration(800);
        scaleY.setDuration(800);

        AnimatorSet circleAnimationSet = new AnimatorSet();
        circleAnimationSet.playTogether(fadeIn, scaleX, scaleY);
        circleAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        circleAnimationSet.setStartDelay(300);
        circleAnimationSet.start();
    }

    private void animateLogo() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logoText, "alpha", 0f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logoText, "scaleX", 0.7f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logoText, "scaleY", 0.7f, 1f);
        fadeIn.setDuration(1000);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);

        AnimatorSet logoAnimationSet = new AnimatorSet();
        logoAnimationSet.playTogether(fadeIn, scaleX, scaleY);
        logoAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        logoAnimationSet.setStartDelay(700);
        logoAnimationSet.start();
    }

    private void animateDecorativeDots() {
        animateDot(dot1, 1000, 200);
        animateDot(dot2, 1200, 500);
        animateDot(dot3, 1400, 800);
    }

    private void animateDot(ImageView dot, int duration, int delay) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(dot, "scaleX", 0f, 1.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(dot, "scaleY", 0f, 1.3f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dot, "alpha", 0f, 1f);
        scaleX.setDuration(duration);
        scaleY.setDuration(duration);
        alpha.setDuration(duration);

        AnimatorSet dotAnimationSet = new AnimatorSet();
        dotAnimationSet.playTogether(scaleX, scaleY, alpha);
        dotAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        dotAnimationSet.setStartDelay(delay);
        dotAnimationSet.start();

        startContinuousPulse(dot, delay + duration);
    }

    private void startContinuousPulse(ImageView dot, int startDelay) {
        new Handler().postDelayed(() -> {
            ObjectAnimator pulseX = ObjectAnimator.ofFloat(dot, "scaleX", 1f, 1.05f, 1f);
            ObjectAnimator pulseY = ObjectAnimator.ofFloat(dot, "scaleY", 1f, 1.05f, 1f);
            pulseX.setDuration(2000);
            pulseY.setDuration(2000);
            pulseX.setRepeatCount(ObjectAnimator.INFINITE);
            pulseY.setRepeatCount(ObjectAnimator.INFINITE);
            pulseX.start();
            pulseY.start();
        }, startDelay);
    }
}
