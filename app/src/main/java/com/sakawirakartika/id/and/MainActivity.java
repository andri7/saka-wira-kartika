package com.sakawirakartika.id.and;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ListView listViewMateri;
    Menu menu;
    String [] materi;
    protected Cursor cursor;
    private Toolbar mToolbar;
    public static MainActivity ma;
    private ProgressDialog pDialog;
    private NavigationMenuView navView;
    private DrawerLayout drawLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        listViewMateri = (ListView)findViewById(R.id.listMateri);

        //Array holding our data
        String[] materi = {"Lambang Saka Wira Kartika","Tujuan Saka Wira Kartika", "Kirda Survival",
                "Krida Pioneering", "Krida Mountaineering", "Krida Navigasi Darat","Krida Penanggulangan Alam"};
        //adapter which will convert each data item into view item.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.listText, materi);
        listViewMateri.setAdapter(adapter);
        listViewMateri.setOnItemClickListener(new ListClickHandler());

        }

    public class ListClickHandler implements  AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            //TODO Auto-generated method stub

            TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = listText.getText().toString();
            Intent intent = new Intent(MainActivity.this, Webview_materi.class);
            intent.putExtra("selected-item", text);
            startActivity(intent);
        }
    }


}

