package com.example.emdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.emdb.R;
import com.example.emdb.classes.InputValidator;

public class LogInActivity extends AppCompatActivity {
    private InputValidator inputValidator = new InputValidator();

    private EditText loginInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView forgotPassword;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        loginInput = findViewById(R.id.editTextLogin);
        passwordInput = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        register = findViewById(R.id.register);

        loginButton.setOnClickListener(view -> {
            if (!inputValidator.validInput(loginInput.getText().toString()) || !inputValidator.validInput(passwordInput.getText().toString())) {
                Toast.makeText(LogInActivity.this, "Your input was invalid, make sure it's not empty or doesn't contain symbols (\", ') ", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(LogInActivity.this, RecoverPasswordActivity.class));
        });

        register.setOnClickListener(view -> {
            startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
        });
    }
}