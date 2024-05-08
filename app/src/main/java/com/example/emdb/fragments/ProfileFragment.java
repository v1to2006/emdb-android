package com.example.emdb.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emdb.R;
import com.example.emdb.activities.LogInActivity;
import com.example.emdb.activities.MainActivity;
import com.example.emdb.activities.SignUpActivity;
import com.example.emdb.classes.Client;

public class ProfileFragment extends Fragment {
    private TextView profileText;
    private TextView profileEmailText;
    private ImageView logoutButton;
    private AppCompatButton deleteAccountButton;

    private Client client = Client.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        profileText = view.findViewById(R.id.profileText);
        profileEmailText = view.findViewById(R.id.profileEmailText);
        logoutButton = view.findViewById(R.id.logoutImage);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);

        profileText.setText("You are logged in as " + client.loggedUser.getUsername());
        profileEmailText.setText("Email: " + client.loggedUser.getEmail());

        logoutButton.setOnClickListener(view -> {
            client.logOut();

            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        });
    }
}