package com.vap.fallout_bg;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
public class CheckScenarioActivity extends ListActivity {

    private Cursor c = null;
    HashMap<Integer, String> Scenarios = new HashMap<>();
    HashMap<String, Integer> Scenarios_b = new HashMap<>();
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_scenario);

        DBHelper myDbHelper = new DBHelper(CheckScenarioActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        Log.d(TAG, "Открываем ДБ");
        Toast.makeText(CheckScenarioActivity.this, "Success", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "Читаем таблицу сценариев");
        c = myDbHelper.query("SCENARIO", null, null, null, null, null, null);
        Log.d(TAG, "Создаем списоки");

        if (c.moveToFirst()) {
            do {
                 Scenarios.put(Integer.parseInt( c.getString(0)),c.getString(1));
                Scenarios_b.put(c.getString(1),Integer.parseInt(c.getString(0)));
               Log.d(TAG, "Чтение сценария " + Scenarios.get(Integer.parseInt( c.getString(0))) );
            } while (c.moveToNext());
        }

        Log.d(TAG, "сценария " + Scenarios.values() );
        // создаем адаптер

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, (List<String>) Scenarios.values().parallelStream().collect(Collectors.<String>toList()));

        Log.d(TAG, "Создали адатер "  );
        // присваиваем адаптер списку
         setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected id = " + Scenarios_b.get(item) , Toast.LENGTH_LONG).show();
    }
}