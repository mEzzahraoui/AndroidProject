package com.example.meriame.authenticationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoPlace extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_place);

       Bundle extras = getIntent().getExtras();
        String TaString = extras.getString("PlaceName");
        text=(TextView)findViewById(R.id.textView);
        text.setText(TaString);

    }
}
