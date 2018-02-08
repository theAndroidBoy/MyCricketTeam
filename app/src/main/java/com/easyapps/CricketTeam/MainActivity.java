package com.easyapps.CricketTeam;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easyapps.CricketTeam.data.TestUtil;
import com.easyapps.CricketTeam.data.PlayersContract;
import com.easyapps.CricketTeam.data.PlayersDbHelper;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter mAdapter;
    private SQLiteDatabase database;
    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;
    private RecyclerView recyclerView;

    //----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        PlayersDbHelper dbHelper = new PlayersDbHelper(this);
        database = dbHelper.getWritableDatabase();

//-------------------------------------------
        // swiping stuff
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                mAdapter.swapCursor(getAllGuests());
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    } // On Create() closed

    //-----------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();

        initializeAdapter();
    }

    //-------------------------------------
    private void initializeAdapter() {
        // read db and get ther results in a cursor object
        Cursor cursor = getAllGuests();

        // pass this cursor to Adapter as a data source
        mAdapter = new RecyclerViewAdapter(this, cursor);
        recyclerView.setAdapter(mAdapter);
    }

    //-------------------------------------
    private void initializeUI() {
        mNewGuestNameEditText = findViewById(R.id.player_name_editText);
        mNewPartySizeEditText = findViewById(R.id.player_average_editText);

        //recyclerView stuff
        recyclerView = findViewById(R.id.all_guests_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
    }

//----------------------------------------------------------------

    private Cursor getAllGuests() {
        return database.query(
                PlayersContract.Team.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PlayersContract.Team.COLUMN_TIMESTAMP
        );
    }

    //-----------------------------------------------
    private long addNewGuest(String name, int partySize) {
        Log.i("flow", "addNewGuest: " + name + " " + partySize);

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_NAME, name);
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_AVERAGE, partySize);

        return database.insert(PlayersContract.Team.TABLE_NAME, null,
                contentValues);
    }

    //---------------------------------------------------
    private boolean removeGuest(long id) {
        return database.delete(PlayersContract.Team.TABLE_NAME,
                PlayersContract.Team._ID + "=" + id,
                null) > 0; //if greater the 0,means any record deleted ,so true.
    }

    //----------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    //-----------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_5_players:
                // insert Fake data to db
                TestUtil.insertFakeData(database);
                mAdapter.swapCursor(getAllGuests());
                //  adapter.updateData(getDatabaseInfo());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------
    public void addPlayerButton(View view) {
        //if any of them was empty
        if (mNewGuestNameEditText.getText().length() == 0 ||
                mNewPartySizeEditText.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Please Provide information",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int average = 27;  //default average to 27
        String playerName = mNewGuestNameEditText.getText().toString();

        try {
            // inputType="number", so this should always work
            average = Integer.parseInt(mNewPartySizeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("flow", "Failed to parse average text to number: " + ex.getMessage());
        }

        // Add guest info to database
        addNewGuest(playerName, average);
        //since we updated the db ,so read the db again and swap the datasource
        // which is cursor
        mAdapter.swapCursor(getAllGuests());

        //clear UI text fields ,as the guests were inserted
        mNewPartySizeEditText.clearFocus();
        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();
    } //addPlayerButton closing bracket
//----------------------
} //Main Activity closed