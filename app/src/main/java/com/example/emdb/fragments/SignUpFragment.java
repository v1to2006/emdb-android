package com.example.emdb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.emdb.R;
import com.example.emdb.activities.MainActivity;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;
import com.example.emdb.classes.InputValidator;
import com.example.emdb.interfaces.OnButtonClickListener;
import com.example.emdb.models.User;

public class SignUpFragment extends Fragment {

    private EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private AppCompatButton signUpButton;
    private ImageView backImage;
    private TextView login;

    private InputValidator inputValidator = new InputValidator();
    private Database database = Database.getInstance();
    private Client client = Client.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        usernameInput = view.findViewById(R.id.usernameInput);
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordInput);
        signUpButton = view.findViewById(R.id.signUpButton);
        backImage = view.findViewById(R.id.backImageSignUp);
        login = view.findViewById(R.id.login);

        signUpButton.setOnClickListener(view1 -> {
            String username = usernameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            boolean validUsername = inputValidator.validUsername(username);
            boolean validEmail = inputValidator.validEmail(email);
            boolean validPassword = inputValidator.validPassword(password);
            boolean passwordMatching = inputValidator.passwordMatching(password, confirmPassword);
            boolean userAvailable = !database.userAlreadyExists(username, email);

            FragmentActivity currentActivity = getActivity();

            if (validUsername && validEmail && validPassword && passwordMatching) {
                if (userAvailable) {
                    User user = new User(0, username, email, password);
                    database.signUpUser(user);
                    client.logIn(user);

                    Intent intent = new Intent(currentActivity, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(currentActivity, "Username or email not available", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!validUsername) {
                    Toast.makeText(currentActivity, "Invalid username", Toast.LENGTH_SHORT).show();
                } else if (!validEmail) {
                    Toast.makeText(currentActivity, "Invalid email", Toast.LENGTH_SHORT).show();
                } else if (!validPassword) {
                    Toast.makeText(currentActivity, "Invalid password", Toast.LENGTH_SHORT).show();
                } else if (!passwordMatching) {
                    Toast.makeText(currentActivity, "Passwords don't match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(currentActivity, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backImage.setOnClickListener(view1 -> {
            if (getActivity() instanceof OnButtonClickListener) {
                ((OnButtonClickListener) getActivity()).onLoginClicked();
            }
        });

        login.setOnClickListener(view1 -> {
            if (getActivity() instanceof OnButtonClickListener) {
                ((OnButtonClickListener) getActivity()).onLoginClicked();
            }
        });
    }
}
