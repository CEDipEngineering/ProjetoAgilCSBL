package br.pro.hashi.ensino.desagil.firebase;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SymptomGridAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Sintoma> symptomArray;
    private Paciente patient;
    private ImageView imagem;

    public SymptomGridAdapter(Activity activity, ArrayList<Sintoma> symptomArray, Paciente patient) {
        super();
        this.symptomArray = symptomArray;
        this.activity = activity;
        this.patient = patient;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return symptomArray.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return symptomArray.get(position).getNome();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView txtViewTitle;
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null)
        {
            convertView = inflator.inflate(R.layout.symptom_gridview, null);
        }

        imagem = convertView.findViewById(R.id.imagem);
        txtViewTitle = convertView.findViewById(R.id.nomeSintoma);
        txtViewTitle.setText(symptomArray.get(position).getNome());
        int color = this.colorizeView(position);
        imagem.setBackgroundColor(color);
        
        return convertView;
    }


    private int colorizeView(int i) {
        Sintoma currSymptom = symptomArray.get(i);
        if(patient.getSintomas().contains(currSymptom)){
            return Color.GREEN;
        }

        return Color.RED;
    }
}

