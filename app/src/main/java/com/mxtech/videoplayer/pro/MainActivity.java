package com.mxtech.videoplayer.pro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();

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

        String str = intentToString(getIntent());

        Log.e(TAG, str);
        view.setText(str);

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

    private static int hexToBin( char ch ) {
        if( '0'<=ch && ch<='9' )    return ch-'0';
        if( 'A'<=ch && ch<='F' )    return ch-'A'+10;
        if( 'a'<=ch && ch<='f' )    return ch-'a'+10;
        return -1;
    }

    private static byte[] parseHexBinary(String s) {
        final int len = s.length();

        // "111" is not a valid hex encoding.
        if( len%2 != 0 )
            throw new IllegalArgumentException("hexBinary needs to be even-length: "+s);

        byte[] out = new byte[len/2];

        for( int i=0; i<len; i+=2 ) {
            int h = hexToBin(s.charAt(i  ));
            int l = hexToBin(s.charAt(i+1));
            if( h==-1 || l==-1 )
                throw new IllegalArgumentException("contains illegal character for hexBinary: "+s);

            out[i/2] = (byte)(h*16+l);
        }

        return out;
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    private static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String intentToString(Intent intent) {
        if (intent == null)
            return "";

        String str = Uri.decode(intent.getDataString());

        String data = intent.getDataString();

        if(data != null) {
            int pos = data.indexOf("dlink=");
            if(pos > 0) {

                String dlink = data.substring(pos+9, data.indexOf("%22", pos+9));
                String decode = new String(parseHexBinary(dlink));
                String encode = printHexBinary(decode.getBytes());

                Log.e(TAG, dlink);
                Log.e(TAG, decode);
                Log.e(TAG, encode);
                Log.e(TAG, "compare: " + Boolean.toString(dlink.equals(encode)));

            }
        }

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
