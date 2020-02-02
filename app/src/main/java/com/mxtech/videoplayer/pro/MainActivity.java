package com.mxtech.videoplayer.pro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        view = findViewById(R.id.textView);

        view.setText(intentToString(getIntent()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String intentToString(Intent intent) {
        if (intent == null)
            return "";

        String str = Uri.decode(intent.getDataString());

        StringBuilder stringBuilder = new StringBuilder("action: ")
                .append(intent.getAction())
                .append("\n\n data: \n")
                .append(intent.getDataString())
                .append("\n\n")
                .append(str)
                .append("\n\n extras: \n")
                ;

        if(intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet())
                stringBuilder
                .append(key)
                .append("=")
                .append(intent.getExtras().get(key))
                .append("\n");
        }

        return stringBuilder.toString();

    }

}
