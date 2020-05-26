package com.example.b10730218_hw2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.b10730218_hw2.data.WaitlistContract;
import com.example.b10730218_hw2.data.WaitlistDbHelper;

public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private SQLiteDatabase mDb;
    RecyclerView recyclerView;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllName();

        myAdapter = new MyAdapter(this, cursor);
        recyclerView.setAdapter(myAdapter);

        setupSharedPreferences();
        
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                opDialog(id);
//                myAdapter.swapCursor(getAllName());
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(recyclerView);
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadColorFromPreferences(sharedPreferences);
    }

    private void loadColorFromPreferences(SharedPreferences sharedPreferences) {
//        myAdapter.setColor(sharedPreferences.getString(getString(R.string.pref_color_key),
//                getString(R.string.pref_color_red_value)));
    }

    public boolean opDialog(final long direction){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("警告視窗")
                .setMessage("是否要刪除資料?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeGuest(direction);
                        myAdapter.swapCursor(getAllName());
//                        adapter.removeItem(direction);
//                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myAdapter.swapCursor(getAllName());
//                        adapter.notifyDataSetChanged();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

    protected void onResume() {
        super.onResume();
        String name,num;
        try {
            Bundle bundle = getIntent().getExtras();
            name = bundle.getString("name");
            num = bundle.getString("num");
            //Toast.makeText(this, name + num, Toast.LENGTH_SHORT).show();
            addNewGuest(name, num);
        }catch (Exception e){

        }
        myAdapter.swapCursor(getAllName());
    }
    private long addNewGuest(String name, String partySize) {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    private boolean removeGuest(long id) {
        // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
        return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;
    }

    private Cursor getAllName() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    public boolean onCreateOptionsMenu(Menu menu){          //Menu Create
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){    //Menu Click Setting
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:
                Intent intent = new Intent(this, AddMain.class);
                startActivity(intent);
                return true;
            case R.id.action_setting:
                Intent intent1 = new Intent(this, SettingMain.class);
                startActivity(intent1);
                return true;
            default:
                return onOptionsItemSelected(item);
        }

    }
}
