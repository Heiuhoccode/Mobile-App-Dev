package com.example.moblie_app_dev.ui.auth;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.moblie_app_dev.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        // ── Blob pulse animations (pulse-slow: opacity 1→0.5→1, 4s infinite) ──
        startBlobPulse(findViewById(R.id.blob_pink),   0);
        startBlobPulse(findViewById(R.id.blob_yellow), 1000);
        startBlobPulse(findViewById(R.id.blob_blue),   2000);

        // ── Floating icon animations (float: translateY 0→-20dp, 1.5s infinite) ──
        startFloatAnimation(findViewById(R.id.floating_star),   0);
        startFloatAnimation(findViewById(R.id.floating_heart),  500);
        startFloatAnimation(findViewById(R.id.floating_rocket), 1000);

        // ── FadeInUp: logo card ──
        View logoContainer = findViewById(R.id.logo_container);
        fadeInUp(logoContainer, 0);

        // ── FadeInUp: title + subtitle (animate-fadeInUp) ──
        fadeInUp(findViewById(R.id.app_title), 100);
        fadeInUp(findViewById(R.id.subtitle),  200);

        // ── FadeInUp: login form card (login-card) ──
        fadeInUp(findViewById(R.id.login_form_card), 300);

        // ── Logo rotate on touch (hover:rotate-12) ──
        logoContainer.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ObjectAnimator.ofFloat(v, "rotation", 0f, 12f)
                        .setDuration(300)
                        .start();
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                ObjectAnimator.ofFloat(v, "rotation", 12f, 0f)
                        .setDuration(300)
                        .start();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
            }
            return true;
        });

        // ── Button push effect (btn-push: translateY 0→4dp on press, back on release) ──
        Button loginButton = findViewById(R.id.login_button);
        float density = getResources().getDisplayMetrics().density;
        float pushDown = 4 * density; // 4dp in pixels
        CardView loginButtonCard = (CardView) loginButton.getParent();

        loginButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ObjectAnimator.ofFloat(v, "translationY", 0f, pushDown)
                        .setDuration(80)
                        .start();
                ObjectAnimator.ofFloat(loginButtonCard, "cardElevation", 8 * density, 2 * density)
                        .setDuration(80)
                        .start();
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                ObjectAnimator.ofFloat(v, "translationY", pushDown, 0f)
                        .setDuration(120)
                        .start();
                ObjectAnimator.ofFloat(loginButtonCard, "cardElevation", 2 * density, 8 * density)
                        .setDuration(120)
                        .start();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
            }
            return true;
        });

        // ── Register Link Click ──
        TextView registerLink = findViewById(R.id.register_link);
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Blob pulse: alpha oscillates between 0.3 and 0.15 (mirrors HTML pulse-slow opacity 1→0.5)
     */
    private void startBlobPulse(View view, long startDelay) {
        if (view == null) return;
        ObjectAnimator pulse = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 0.15f);
        pulse.setDuration(2000);
        pulse.setStartDelay(startDelay);
        pulse.setRepeatMode(ObjectAnimator.REVERSE);
        pulse.setRepeatCount(ObjectAnimator.INFINITE);
        pulse.setInterpolator(new AccelerateDecelerateInterpolator());
        pulse.start();
    }

    /**
     * Float animation: translationY 0 → -20dp → 0, 1.5s infinite (matches HTML animate-float)
     */
    private void startFloatAnimation(View view, long startDelay) {
        if (view == null) return;
        float density = getResources().getDisplayMetrics().density;
        ObjectAnimator floatAnim = ObjectAnimator.ofFloat(view, "translationY", 0f, -20f * density);
        floatAnim.setDuration(1500);
        floatAnim.setStartDelay(startDelay);
        floatAnim.setRepeatMode(ObjectAnimator.REVERSE);
        floatAnim.setRepeatCount(ObjectAnimator.INFINITE);
        floatAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        floatAnim.start();
    }

    /**
     * FadeInUp: alpha 0→1, translationY 30dp→0, 600ms (matches HTML animate-fadeInUp)
     */
    private void fadeInUp(View view, long startDelay) {
        if (view == null) return;
        float density = getResources().getDisplayMetrics().density;
        view.setAlpha(0f);
        view.setTranslationY(30f * density);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        ObjectAnimator slideUp = ObjectAnimator.ofFloat(view, "translationY", 30f * density, 0f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(fadeIn, slideUp);
        set.setDuration(600);
        set.setStartDelay(startDelay);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
    }
}

