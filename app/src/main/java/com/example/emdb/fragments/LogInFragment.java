package com.example.emdb.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.emdb.R;
import com.example.emdb.activities.MainActivity;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;
import com.example.emdb.classes.InputValidator;
import com.example.emdb.interfaces.OnButtonClickListener;
import com.example.emdb.models.User;

public class LogInFragment extends Fragment {

    private OnButtonClickListener buttonClickListener;

    private EditText loginInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView forgotPassword;
    private TextView signUp;
    private ImageView backImage;

    private InputValidator inputValidator = new InputValidator();
    private Database database = Database.getInstance();
    private Client client = Client.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickListener) {
            buttonClickListener = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnButtonClickListener");
        }
    }

    private void initView(View view) {
        loginInput = view.findViewById(R.id.editTextLogin);
        passwordInput = view.findViewById(R.id.editTextPassword);
        loginButton = view.findViewById(R.id.loginButton);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        signUp = view.findViewById(R.id.register);
        backImage = view.findViewById(R.id.backImageLogIn);

        loginButton.setOnClickListener(view1 -> {
            String loginText = loginInput.getText().toString();
            String passwordText = passwordInput.getText().toString();

            FragmentActivity currentActivity = getActivity();

            if (!inputValidator.validInput(loginText) || !inputValidator.validInput(passwordText)) {
                Toast.makeText(currentActivity, "Invalid or empty input", Toast.LENGTH_SHORT).show();
            } else {
                User user = database.logIn(loginText, passwordText);

                if (user != null) {
                    client.logIn(user);

                    Intent intent = new Intent(currentActivity, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(currentActivity, "Account not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword.setOnClickListener(view1 -> {
            if (getActivity() instanceof OnButtonClickListener) {
                ((OnButtonClickListener) getActivity()).onRecoverPasswordClicked();
            }
        });

        signUp.setOnClickListener(view1 -> {
            if (getActivity() instanceof OnButtonClickListener) {
                ((OnButtonClickListener) getActivity()).onSignUpClicked();
            }
        });

        backImage.setOnClickListener(view1 -> {
            if (getActivity() instanceof OnButtonClickListener) {
                ((OnButtonClickListener) getActivity()).onHomeClicked();
            }
        });
    }
}