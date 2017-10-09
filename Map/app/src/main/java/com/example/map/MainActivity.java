package com.example.map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private EditText editText1 = null;
    private EditText editText2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.et1);
        editText2 = (EditText) findViewById(R.id.et2);
        Button button = (Button) findViewById(R.id.btnLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et1 = editText1.getText().toString();
                String et2 = editText2.getText().toString();

                Uri uri = Uri.parse("http://maps.google.com/?q="+et1+","+et2);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }
}
