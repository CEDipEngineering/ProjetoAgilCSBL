package br.pro.hashi.ensino.desagil.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Para ler dados do Firebase, esta classe deve
// implementar a interface ValueEventListener.
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pacienteButton = findViewById(R.id.button_activityPatient);
        Button mapButton = findViewById(R.id.button_activityMap);
        Button loginButton = findViewById(R.id.button_activityLogin);
        Button editorButton = findViewById(R.id.button_activityEditor);

        pacienteButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, PatientActivity.class);
            startActivity(intent);
        });

        mapButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        editorButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            startActivity(intent);
        });
    }
}
