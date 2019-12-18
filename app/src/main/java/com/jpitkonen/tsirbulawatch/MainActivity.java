package com.jpitkonen.tsirbulawatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Adapter.BirdListAdapter;
import Model.Bird;
import ViewModel.BirdViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_BIRD_ACTIVITY_REQUEST_CODE = 1;

    private FloatingActionButton floatingActionButton;
    private BirdViewModel birdViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewId);
        final BirdListAdapter adapter = new BirdListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        birdViewModel = new ViewModelProvider(this).get(BirdViewModel.class);

        birdViewModel.getAllBirds().observe(this, new Observer<List<Bird>>() {
            @Override
            public void onChanged(@Nullable final List<Bird> birds) {
                adapter.setBirds(birds);
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBirdActivity.class);
                startActivityForResult(intent, NEW_BIRD_ACTIVITY_REQUEST_CODE);
            }
        });
    }
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

}
