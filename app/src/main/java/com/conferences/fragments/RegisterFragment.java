package com.conferences.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conferences.R;
import com.conferences.helpers.EditTextHelper;
import com.conferences.providers.ConferencesProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {
    EditText email, password;
    Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register, container, false);

        email = root.findViewById(R.id.input_register_email);
        password = root.findViewById(R.id.input_register_password);
        registerButton = root.findViewById(R.id.button_register);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerButton.setOnClickListener(cView -> {
            if (EditTextHelper.CheckMandatoryText(email, cView) && EditTextHelper.CheckMandatoryText(password, cView)) {
                mAuth = FirebaseAuth.getInstance();

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(cView.getContext(), "Ide gas ide register", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(cView.getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
