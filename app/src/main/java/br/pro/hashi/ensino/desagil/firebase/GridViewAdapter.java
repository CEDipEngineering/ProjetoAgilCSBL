package br.pro.hashi.ensino.desagil.firebase;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter
{
    private ArrayList<Integer> idLeito;
    private Activity activity;
    private ArrayList<String> namePatient;

    public GridViewAdapter(Activity activity, ArrayList<Integer> idGridView, ArrayList<String> namePatient) {
        super();
        this.idLeito = idGridView;
        this.namePatient = namePatient;
        this.activity = activity;
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
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            convertView = inflator.inflate(R.layout.leito_gridview, null);
        }

        txtViewTitle = convertView.findViewById(R.id.textView);
        txtViewTitle2 = convertView.findViewById(R.id.textViewNome);
        txtViewTitle.setText(String.valueOf(idLeito.get(position)));
        if(namePatient.get(position) != null) {
            txtViewTitle2.setText(String.valueOf(namePatient.get(position)));
        } else {
            txtViewTitle2.setText("");
        }
        return convertView;
    }
}