package com.example.b10730218_hw2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


public class SettingMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        Button back = (Button) findViewById(R.id.action_back);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingMain.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
