package br.pro.hashi.ensino.desagil.firebase;

import android.os.Bundle;
import android.widget.ListView;
import java.util.HashMap;
import android.widget.TextView;

public class TimeStampActivity extends Json {
    private ListView summaryView;
    private HashMap<String, String> tempSintomasData;
    private TextView patientNameView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_stamp);
        patientNameView = findViewById(R.id.name);

    }
}
