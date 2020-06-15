package br.pro.hashi.ensino.desagil.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

//Git

public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText senha;
    private Button avançar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_first);
        senha = findViewById(R.id.password);
        avançar = findViewById(R.id.advance);

        this.avançar.setOnClickListener((view) ->{
            Intent intent = new Intent(LoginActivity.this, AlaActivity.class);
            startActivity(intent);

        });
    }

}
