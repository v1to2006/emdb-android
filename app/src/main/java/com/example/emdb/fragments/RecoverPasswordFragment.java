package com.example.emdb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.emdb.R;
import com.example.emdb.interfaces.OnButtonClickListener;

public class RecoverPasswordFragment extends Fragment {
    private ImageView backImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recover_password, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        backImage = view.findViewById(R.id.backImageRecoverPassword);

        backImage.setOnClickListener(view1 -> {
            if (getActivity() instanceof OnButtonClickListener) {
                ((OnButtonClickListener) getActivity()).onLoginClicked();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
