package com.example.b10730218_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main);

        Button ok = (Button) findViewById(R.id.action_ok);
        Button cancel = (Button) findViewById(R.id.action_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_name = (EditText) findViewById(R.id.edit_name);
                EditText edit_num = (EditText) findViewById(R.id.edit_num);
                String add_name = edit_name.getText().toString();
                String add_num = edit_num.getText().toString();
                //Toast.makeText(add_main.this,add_name + add_num,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddMain.this, MainActivity.class);
                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("name", add_name.toString());
                bundle.putString("num", add_num.toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMain.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
