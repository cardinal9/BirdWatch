package com.jpitkonen.tsirbulawatch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddBirdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Codes for passing info
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
        imageView = (ImageView) findViewById(R.id.birdBackgroundPic);

        String[] rarity_list = getResources().getStringArray(R.array.rarity_array);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rarity_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Ask user for permissions and then prompt for user to pick an image
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                chooseImg();
            }
        });

    }

    //Prompt for user to pick an image
    public void chooseImg() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Choose picture"), GALLERY_REQUEST_CODE);
    }

    /*
    Ask for user permission
     */
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(AddBirdActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddBirdActivity.this, new String[] {permission}, requestCode);
        } else {
            Toast.makeText(AddBirdActivity.this, "Permission already granted!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Handle the permission according to user choice, if granted: grant permissions. If not granted then do nothing.
     */
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

    /*
    Handle passing image file path and push all the user filled information to intent.
    Create timestamp on save and format it.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);

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

                            //Timestamp shows seconds just so make it easier to demo
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
