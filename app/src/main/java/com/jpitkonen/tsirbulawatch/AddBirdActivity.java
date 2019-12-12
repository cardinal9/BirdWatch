package com.jpitkonen.tsirbulawatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBirdActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REPLY = "com.jpitkonen.tsirbulawatch.birdlistsql.REPLY";

    private TextView birdAddText;
    private EditText bird_name, bird_notes;
    private Spinner spinner;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bird);

        birdAddText = (TextView) findViewById(R.id.birdAddText);
        bird_name = (EditText) findViewById(R.id.new_bird_name_text);
        bird_notes = (EditText) findViewById(R.id.new_notes_text);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.saveButtonId);

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
                    String bird = bird_name.getText().toString();
                    intent.putExtra(EXTRA_REPLY, bird);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String item = adapterView.getItemAtPosition(pos).toString();
        Toast.makeText(adapterView.getContext(), "Selected bird: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
