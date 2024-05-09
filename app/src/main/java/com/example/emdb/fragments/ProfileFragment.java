package com.example.emdb.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emdb.R;
import com.example.emdb.activities.MainActivity;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;

public class ProfileFragment extends Fragment {
    private TextView profileText;
    private TextView profileEmailText;
    private ImageView logoutButton;

    private Client client = Client.getInstance();
    private Database database = Database.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initView(View currentView) {
        profileText = currentView.findViewById(R.id.profileText);
        profileEmailText = currentView.findViewById(R.id.profileEmailText);
        logoutButton = currentView.findViewById(R.id.logoutImage);

        profileText.setText("You are logged in as " + client.loggedUser.getUsername());
        profileEmailText.setText("Email: " + client.loggedUser.getEmail());

        logoutButton.setOnClickListener(view -> {
            client.logOut();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("fragmentToLoad", "HomeFragment");
            startActivity(intent);
            getActivity().finish();

            Toast.makeText(getActivity(), "Log out successful", Toast.LENGTH_SHORT).show();
        });
    }
}