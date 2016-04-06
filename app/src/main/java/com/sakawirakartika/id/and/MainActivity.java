package com.sakawirakartika.id.and;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.design.internal.NavigationMenuView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ListView ListViewMateri;
    Menu menu;
    protected Cursor cursor;
    public static MainActivity ma;
    private Toolbar mToolbar;
    private ProgressDialog pDialog;
    private NavigationMenuView navView;
    private DrawerLayout drawLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
