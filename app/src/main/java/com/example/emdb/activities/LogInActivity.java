package com.example.emdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.emdb.R;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;
import com.example.emdb.classes.InputValidator;
import com.example.emdb.models.User;

public class LogInActivity extends AppCompatActivity {
    private InputValidator inputValidator = new InputValidator();

    private EditText loginInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView forgotPassword;
    private TextView register;
    private ImageView backImage;

    private Database database = Database.getInstance();
    private Client client = Client.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initWindowInsets();

        initView();
    }

    private void initView() {
        loginInput = findViewById(R.id.editTextLogin);
        passwordInput = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        register = findViewById(R.id.register);
        backImage = findViewById(R.id.backImageLogIn);

        loginButton.setOnClickListener(view -> {
            String loginText = loginInput.getText().toString();
            String passwordText = passwordInput.getText().toString();

            if (!inputValidator.validInput(loginText) || !inputValidator.validInput(passwordText)) {
                Toast.makeText(LogInActivity.this, "Invalid or empty input", Toast.LENGTH_SHORT).show();
            } else {
                User user = database.logIn(loginText, passwordText);

                if (user != null) {
                    client.logIn(user);

                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(LogInActivity.this, RecoverPasswordActivity.class));
        });

        register.setOnClickListener(view -> {
            startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
        });

        backImage.setOnClickListener(view -> finish());
    }

    private void initWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}