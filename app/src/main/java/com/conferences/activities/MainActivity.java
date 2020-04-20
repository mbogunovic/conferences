package com.conferences.activities;

import android.content.Intent;
import android.os.Bundle;

import com.conferences.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_main_logged, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout){
            mAuth.signOut();
            Toast.makeText(this, getResources().getString(R.string.confirmation_logout), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }

        if (id == R.id.action_login) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (id == R.id.action_register){
            startActivity(new Intent(this, RegisterActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
