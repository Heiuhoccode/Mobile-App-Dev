package com.example.moblie_app_dev.ui.auth;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moblie_app_dev.R;
import com.google.android.material.card.MaterialCardView;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameInput, emailInput, usernameInput, passwordInput, confirmPasswordInput;
    private MaterialCardView errorCard, successCard, roleParentCard, roleTeacherCard;
    private TextView errorMessage, successMessage, loginLink;
    private Button registerButton;
    private String selectedRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);

        // Initialize views
        fullnameInput = findViewById(R.id.fullname_input);
        emailInput = findViewById(R.id.email_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        errorCard = findViewById(R.id.error_card);
        successCard = findViewById(R.id.success_card);
        errorMessage = findViewById(R.id.error_message);
        successMessage = findViewById(R.id.success_message);
        registerButton = findViewById(R.id.register_button);
        loginLink = findViewById(R.id.login_link);
        roleParentCard = findViewById(R.id.role_parent_card);
        roleTeacherCard = findViewById(R.id.role_teacher_card);

        // ── Blob pulse animations ──
        startBlobPulse(findViewById(R.id.blob_pink), 0);
        startBlobPulse(findViewById(R.id.blob_yellow), 1000);
        startBlobPulse(findViewById(R.id.blob_blue), 2000);

        // ── Floating icon animations ──
        startFloatAnimation(findViewById(R.id.floating_pencil), 0);
        startFloatAnimation(findViewById(R.id.floating_book), 500);
        startFloatAnimation(findViewById(R.id.floating_cap), 1000);

        // ── FadeInUp animations ──
        View logoContainer = findViewById(R.id.logo_container);
        fadeInUp(logoContainer, 0);
        fadeInUp(findViewById(R.id.app_title), 100);
        fadeInUp(findViewById(R.id.subtitle), 200);
        fadeInUp(findViewById(R.id.register_form_card), 300);

        // ── Logo rotate on touch ──
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

        // ── Button push effect ──
        float density = getResources().getDisplayMetrics().density;
        float pushDown = 4 * density;
        MaterialCardView registerButtonCard = (MaterialCardView) registerButton.getParent();

        registerButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ObjectAnimator.ofFloat(v, "translationY", 0f, pushDown)
                        .setDuration(80)
                        .start();
                ObjectAnimator.ofFloat(registerButtonCard, "cardElevation", 8 * density, 2 * density)
                        .setDuration(80)
                        .start();
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                ObjectAnimator.ofFloat(v, "translationY", pushDown, 0f)
                        .setDuration(120)
                        .start();
                ObjectAnimator.ofFloat(registerButtonCard, "cardElevation", 2 * density, 8 * density)
                        .setDuration(120)
                        .start();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
            }
            return true;
        });

        // ── Role Selection ──
        roleParentCard.setOnClickListener(v -> selectRole("parent"));
        roleTeacherCard.setOnClickListener(v -> selectRole("teacher"));

        // ── Register Button Click ──
        registerButton.setOnClickListener(v -> handleRegister());

        // ── Login Link Click ──
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void selectRole(String role) {
        selectedRole = role;
        float density = getResources().getDisplayMetrics().density;

        if (role.equals("parent")) {
            // Highlight parent card
            roleParentCard.setCardBackgroundColor(0xFFEEF2FF);
            roleParentCard.setStrokeColor(0xFF4F46E5);
            roleParentCard.setStrokeWidth((int) (3 * density));
            roleParentCard.setScaleX(1.05f);
            roleParentCard.setScaleY(1.05f);

            // Reset teacher card
            roleTeacherCard.setCardBackgroundColor(0xFFF0FDF4);
            roleTeacherCard.setStrokeColor(0x00000000);
            roleTeacherCard.setStrokeWidth(0);
            roleTeacherCard.setScaleX(1.0f);
            roleTeacherCard.setScaleY(1.0f);
        } else {
            // Highlight teacher card
            roleTeacherCard.setCardBackgroundColor(0xFFF0FDF4);
            roleTeacherCard.setStrokeColor(0xFF16A34A);
            roleTeacherCard.setStrokeWidth((int) (3 * density));
            roleTeacherCard.setScaleX(1.05f);
            roleTeacherCard.setScaleY(1.05f);

            // Reset parent card
            roleParentCard.setCardBackgroundColor(0xFFEEF2FF);
            roleParentCard.setStrokeColor(0x00000000);
            roleParentCard.setStrokeWidth(0);
            roleParentCard.setScaleX(1.0f);
            roleParentCard.setScaleY(1.0f);
        }
    }

    private void handleRegister() {
        String fullname = fullnameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Hide error and success cards
        errorCard.setVisibility(View.GONE);
        successCard.setVisibility(View.GONE);

        // Validate empty fields
        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword)) {
            showError(getString(R.string.error_empty_fields));
            return;
        }

        // Validate role selection
        if (TextUtils.isEmpty(selectedRole)) {
            showError(getString(R.string.error_no_role));
            return;
        }

        // Validate email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(getString(R.string.error_invalid_email));
            return;
        }

        // Validate username length
        if (username.length() < 4) {
            showError(getString(R.string.error_username_short));
            return;
        }

        // Validate password length
        if (password.length() < 4) {
            showError(getString(R.string.error_password_short));
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            showError(getString(R.string.error_password_mismatch));
            return;
        }

        // Show success and navigate to login
        showSuccess(getString(R.string.success_register));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 1500);
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorCard.setVisibility(View.VISIBLE);

        // Animate error card
        errorCard.setAlpha(0f);
        errorCard.setTranslationY(-20f);
        errorCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .start();
    }

    private void showSuccess(String message) {
        successMessage.setText(message);
        successCard.setVisibility(View.VISIBLE);

        // Animate success card
        successCard.setAlpha(0f);
        successCard.setTranslationY(-20f);
        successCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .start();
    }

    /**
     * Blob pulse: alpha oscillates between 0.3 and 0.15
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
     * Float animation: translationY 0 → -20dp → 0
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
     * FadeInUp: alpha 0→1, translationY 30dp→0
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
