package com.conferences.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.conferences.R;
import com.conferences.helpers.EditTextHelper;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password;
    Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.input_register_email);
        password = findViewById(R.id.input_register_password);
        registerButton = findViewById(R.id.button_register);

        registerButton.setOnClickListener(cView -> {
            if (EditTextHelper.CheckMandatoryText(email, cView) && EditTextHelper.CheckMandatoryText(password, cView)) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(cView.getContext(), getResources().getString(R.string.confirmation_register), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this, MainActivity.class));
                            } else {
                                Toast.makeText(cView.getContext(), getResources().getString(R.string.confirmation_failed_register),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
