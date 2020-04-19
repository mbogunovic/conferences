package com.conferences.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conferences.R;
import com.conferences.helpers.EditTextHelper;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.input_login_email);
        password = findViewById(R.id.input_login_password);
        loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(cView -> {
            if (EditTextHelper.CheckMandatoryText(email, cView) && EditTextHelper.CheckMandatoryText(password, cView)) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(cView.getContext(), cView.getResources().getString(R.string.confirmation_login), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                } else {
                                    Toast.makeText(cView.getContext(), cView.getResources().getString(R.string.confirmation_failed_login),
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
