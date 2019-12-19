package com.jpitkonen.tsirbulawatch;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddBirdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY";
    public static final String EXTRA_REPLY_NOTES = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_NOTES";
    public static final String EXTRA_REPLY_TIME = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_TIME";
    public static final String EXTRA_REPLY_RARITY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_RARITY";
    public static final int GALLERY_REQUEST_CODE = 111;

    private TextView birdAddText, time_stamp;
    private EditText bird_name, bird_notes;
    private Spinner spinner;
    private Button saveButton, uploadButton;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bird);

        birdAddText = (TextView) findViewById(R.id.birdAddText);
        bird_name = (EditText) findViewById(R.id.new_bird_name_text);
        bird_notes = (EditText) findViewById(R.id.new_notes_text);
        time_stamp = (TextView) findViewById(R.id.timeStampId);
        spinner = (Spinner) findViewById(R.id.spinner);
        saveButton = (Button) findViewById(R.id.saveButtonId);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        imageView = (ImageView) findViewById(R.id.imageViewId);

        String[] rarity_list = getResources().getStringArray(R.array.rarity_array);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rarity_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(bird_name.getText())) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    String birdName = bird_name.getText().toString();
                    String birdNotes = bird_notes.getText().toString();

                    String spinnerRarity = spinner.getSelectedItem().toString();

                    Date date = new Date();
                    DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    String formattedDate = formatDate.format(date);

                    time_stamp.setText(formattedDate);

                    intent.putExtra(EXTRA_REPLY, birdName);
                    intent.putExtra(EXTRA_REPLY_RARITY, spinnerRarity);
                    intent.putExtra(EXTRA_REPLY_NOTES, birdNotes);
                    intent.putExtra(EXTRA_REPLY_TIME, formattedDate);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImg();
            }
        });

    }

    public void chooseImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose picture"), GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {

                Uri selectedImageUri = data.getData();

                Picasso.get()
                        .load(selectedImageUri)
                        .noPlaceholder()
                        .centerCrop()
                        .fit()
                        .into(imageView);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String item = adapterView.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
