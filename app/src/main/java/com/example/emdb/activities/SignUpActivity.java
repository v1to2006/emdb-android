package com.example.emdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.emdb.R;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;
import com.example.emdb.classes.InputValidator;
import com.example.emdb.models.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private AppCompatButton signUpButton;
    private TextView login;
    private ImageView backImage;

    private InputValidator inputValidator = new InputValidator();
    private Database database = Database.getInstance();
    private Client client = Client.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signUpButton = findViewById(R.id.signUpButton);
        login = findViewById(R.id.login);
        backImage = findViewById(R.id.backImageSignUp);

        login.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
        });

        onSignUpButton();

        backImage.setOnClickListener(view -> finish());
    }

    private void onSignUpButton() {
        signUpButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            boolean validUsername = inputValidator.validUsername(username);
            boolean validEmail = inputValidator.validEmail(email);
            boolean validPassword = inputValidator.validPassword(password);
            boolean passwordMatching = inputValidator.passwordMatching(password, confirmPassword);
            boolean userAvailable = !database.userAlreadyExists(username, email);

            if (validUsername && validEmail && validPassword && passwordMatching) {
                if (userAvailable) {
                    User user = new User(0, username, email, password);
                    database.signUpUser(user);
                    client.logIn(user);

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            } else {
                if (!validUsername) {
                    Toast.makeText(SignUpActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                } else if (!validEmail) {
                    Toast.makeText(SignUpActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                } else if (!validPassword) {
                    Toast.makeText(SignUpActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else if (!passwordMatching) {
                    Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                } else if (!userAvailable) {
                    Toast.makeText(SignUpActivity.this, "Username or email not available", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}