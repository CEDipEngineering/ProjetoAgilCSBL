package br.pro.hashi.ensino.desagil.firebase;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter
{
    private ArrayList<Integer> idLeito;
    private Activity activity;
    private ArrayList<String> namePatient;
    private ArrayList<Double> riskPatient;

    public GridViewAdapter(Activity activity, ArrayList<Integer> idGridView, ArrayList<String> namePatient, ArrayList<Double> riskPatient) {
        super();
        this.idLeito = idGridView;
        this.namePatient = namePatient;
        this.activity = activity;
        this.riskPatient = riskPatient;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return idLeito.size();
    }

    @Override
    public Integer getItem(int position) {
        // TODO Auto-generated method stub
        return idLeito.get(position);
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
        TextView txtViewTitle2;
        ImageView imageView;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            convertView = inflator.inflate(R.layout.leito_gridview, null);
        }
        imageView = convertView.findViewById(R.id.imageView);
        txtViewTitle = convertView.findViewById(R.id.textView);
        txtViewTitle2 = convertView.findViewById(R.id.textViewNome);
        txtViewTitle.setText(String.valueOf(idLeito.get(position)));
        if(namePatient.get(position) != null) {
            txtViewTitle2.setText(String.valueOf(namePatient.get(position)));
            if (riskPatient.get(position).equals((double) -1)) {
                imageView.setImageResource(R.drawable.selected_leito_vazio);
            } else if (riskPatient.get(position) < (double) 10) {
                imageView.setImageResource(R.drawable.selected_leito);
            } else if (riskPatient.get(position) < (double) 30) {
                imageView.setImageResource(R.drawable.selected_leito_medium);
            } else {
                imageView.setImageResource((R.drawable.selected_leito_danger));
            }
        } else {
            txtViewTitle2.setText("");
        }
        return convertView;
    }
}