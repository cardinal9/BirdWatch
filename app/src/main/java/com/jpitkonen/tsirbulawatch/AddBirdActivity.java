package com.jpitkonen.tsirbulawatch;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.TypeConverters;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddBirdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int STORAGE_PERMISSION_CODE = 101;

    public static final String EXTRA_REPLY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY";
    public static final String EXTRA_REPLY_NOTES = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_NOTES";
    public static final String EXTRA_REPLY_TIME = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_TIME";
    public static final String EXTRA_REPLY_RARITY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_RARITY";
    public static final String EXTRA_REPLY_IMAGE = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_IMAGE";
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

        /*saveButton.setOnClickListener(new View.OnClickListener() {
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
        });*/

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                chooseImg();
            }
        });

    }

    public void chooseImg() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose picture"), GALLERY_REQUEST_CODE);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(AddBirdActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddBirdActivity.this, new String[] {permission}, requestCode);
        } else {
            Toast.makeText(AddBirdActivity.this, "Permission already granted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddBirdActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                chooseImg();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);

                //Intent i = new Intent(this, MainActivity.class);
                //i.putExtra("imagePath", selectedImage.toString());

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
                            intent.putExtra(EXTRA_REPLY_IMAGE, selectedImage.toString());
                            setResult(RESULT_OK, intent);
                        }
                        finish();
                    }
                });

                /*Uri selectedImageUri = data.getData();

                Picasso.get()
                        .load(selectedImageUri)
                        .noPlaceholder()
                        .centerCrop()
                        .fit()
                        .into(imageView);*/

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
