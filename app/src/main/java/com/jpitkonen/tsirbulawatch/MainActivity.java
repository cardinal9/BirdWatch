package com.jpitkonen.tsirbulawatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Adapter.BirdListAdapter;
import Model.Bird;
import ViewModel.BirdViewModel;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int NEW_BIRD_ACTIVITY_REQUEST_CODE = 1;

    private FloatingActionButton floatingActionButton;
    private BirdViewModel birdViewModel;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    private Toolbar toolbar;
    private TextView mTitle;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTitle = (TextView) findViewById(R.id.titleText);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        spinner = (Spinner) findViewById(R.id.spinnerOrderId);

        String[] order_list = getResources().getStringArray(R.array.order_array);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, order_list);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(orderAdapter);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        final BirdListAdapter adapter = new BirdListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        birdViewModel = new ViewModelProvider(this).get(BirdViewModel.class);

        //Populate list with birds
        birdViewModel.getAllBirds().observe(this, new Observer<List<Bird>>() {
            @Override
            public void onChanged(@Nullable final List<Bird> birds) {
                adapter.setBirds(birds);
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        //Button for creating a new bird
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBirdActivity.class);
                startActivityForResult(intent, NEW_BIRD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settingsId) {
            showDialog();
        } else if (id == R.id.settingsFragmentId) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        Button submitButton = (Button) view.findViewById(R.id.submitButtonId);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birdViewModel.delete();
                dialog.hide();
            }
        });
    }

    /*
            Create new bird from data passed through form filled by user in AddBirdActivity
             */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_BIRD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Bird bird = new Bird(data.getStringExtra(AddBirdActivity.EXTRA_REPLY),
                    data.getStringExtra(AddBirdActivity.EXTRA_REPLY_RARITY),
                    data.getStringExtra(AddBirdActivity.EXTRA_REPLY_NOTES),
                    data.getStringExtra(AddBirdActivity.EXTRA_REPLY_TIME),
                    data.getStringExtra(AddBirdActivity.EXTRA_REPLY_IMAGE));
            birdViewModel.insert(bird);

            Snackbar.make(findViewById(R.id.recyclerViewId), R.string.bird_saved, Snackbar.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), R.string.not_saved, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String item = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
