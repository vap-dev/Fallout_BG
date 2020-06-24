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

public class CheckScenarioActivity extends ListActivity {

    private Cursor c = null;
    private Scenario[] scenarios;
    private String[] scenarios_str;
    private Integer i = 0;
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_scenario);

        String[] scenarios_str;

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
        scenarios = new Scenario[c.getCount()];
        Log.d(TAG, "Создали объект список сценариев. Количеством " + c.getCount());

        if (c.moveToFirst()) {
            do {
                scenarios[i] = new Scenario( Integer.parseInt( c.getString(0)),c.getString(1));
                Log.d(TAG, "Чтение сценария" + scenarios[i].getName() );
                i++;
            } while (c.moveToNext());
        }

        Toast.makeText(CheckScenarioActivity.this, "Сценариев - " +scenarios.length, Toast.LENGTH_SHORT).show();
        scenarios_str = new String[scenarios.length];
        Log.d(TAG, "инициация scenarios_str");

        for(int i = 0; i < scenarios.length; i++)
        {
            Log.d(TAG, "i = " + i );
            Log.d(TAG,  " Scenario " + scenarios[i].getName());
            scenarios_str[i] = scenarios[i].getName();
        }
        Log.d(TAG, "scenarios_str заполнен");
//        scenarios_str = MainActivity.getScenarios_str();

        // находим список
      //   ListView lvMain = (ListView) findViewById(R.id.list);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, scenarios_str);

        // присваиваем адаптер списку
      //  lvMain.setAdapter(adapter);
         setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }
}