package com.example.exp_04;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private Button btn_go;
    private EditText et_url;
    private String urlHead="https://";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = (EditText) findViewById(R.id.editText);
        btn_go = (Button) findViewById(R.id.button);
        btn_go.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText et = findViewById(R.id.editText);
                String n;
                n = et.getText().toString();
//                Intent loadWeb = new Intent();
//                loadWeb.setAction(Intent.ACTION_VIEW);
//                loadWeb.setData(Uri.parse(n));
//                startActivity(loadWeb);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://www.baidu.com"));

                startActivity(intent);
            }
        });

    }
}
