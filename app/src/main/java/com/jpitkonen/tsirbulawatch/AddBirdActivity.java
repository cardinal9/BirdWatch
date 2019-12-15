package com.jpitkonen.tsirbulawatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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


public class AddBirdActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY";
    public static final String EXTRA_REPLY_NOTES = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_NOTES";
    public static final String EXTRA_REPLY_TIME = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_TIME";
    public static final String EXTRA_REPLY_RARITY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY_RARITY";
    public static final int GALLERY_REQUEST_CODE = 192;

    private TextView birdAddText;
    private EditText bird_name, bird_notes, time_stamp;
    private Spinner spinner;
    private Button button, uploadButton;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bird);

        birdAddText = (TextView) findViewById(R.id.birdAddText);
        bird_name = (EditText) findViewById(R.id.new_bird_name_text);
        bird_notes = (EditText) findViewById(R.id.new_notes_text);
        time_stamp = (EditText) findViewById(R.id.timestampText);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.saveButtonId);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        imageView = (ImageView) findViewById(R.id.imageViewId);

        String[] rarity_list = getResources().getStringArray(R.array.rarity_array);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rarity_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(bird_name.getText())) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    String birdName = bird_name.getText().toString();
                    String birdNotes = bird_notes.getText().toString();

                    intent.putExtra(EXTRA_REPLY, birdName);
                    intent.putExtra(EXTRA_REPLY, birdNotes);
                    String timeStamp = time_stamp.getText().toString();
                    String spinnerRarity = spinner.getSelectedItem().toString();

                    intent.putExtra(EXTRA_REPLY, birdName);
                    intent.putExtra(EXTRA_REPLY_RARITY, spinnerRarity);
                    intent.putExtra(EXTRA_REPLY_NOTES, birdNotes);
                    intent.putExtra(EXTRA_REPLY_TIME, timeStamp);


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

       /* uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jped", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        }); */

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
                Picasso.get()
                        .load(data.getData())
                        .noPlaceholder()
                        .centerCrop()
                        .fit()
                        .into(imageView);

                Uri selectedImageUri = data.getData();
                selectedImageUri.toString();
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
