package br.pro.hashi.ensino.desagil.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
public class MainActivity extends AppCompatActivity implements ValueEventListener {

    private TextView textExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textExample = findViewById(R.id.text_example);

        EditText editExample = findViewById(R.id.edit_example);
        Button buttonExample = findViewById(R.id.button_example);

        // Obtém uma referência para o banco de dados.
        // que foi especificado durante a configuração.
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Obtém uma referência para o caminho /a do banco de dados.
        DatabaseReference referenceA = database.getReference("a");

        // Obtém uma referência para o caminho /b do banco de dados.
        DatabaseReference referenceB = database.getReference("b");

        buttonExample.setOnClickListener((view) -> {
            String text = editExample.getText().toString();

            // O método setValue aceita qualquer objeto e tenta
            // deduzir qual é o tipo que deve ser escrito no banco.
            // Nem sempre consegue, então cuidado com exceptions.
            referenceA.setValue(text);
        });

        // Adiciona esta Activity à lista de
        // observadores de mudanças em /b.
        referenceB.addValueEventListener(this);
    }

    // Este método é chamado uma vez durante a chamada
    // de addValueEventListener acima e depois sempre
    // que algum valor em /b sofrer alguma mudança.
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String text;
        try {

            // O método getValue recebe como parâmetro uma
            // classe Java que representa o tipo de dado
            // que você acredita estar lá. Se você errar,
            // esse método vai lançar uma DatabaseException.
            text = dataSnapshot.getValue(String.class);
        } catch (DatabaseException exception) {
            text = "Failed to parse value";
        }
        textExample.setText(text);
    }

    // Este método é chamado caso ocorra algum problema
    // com a conexão ao banco de dados do Firebase.
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        textExample.setText("Failed to read value");
    }
}
