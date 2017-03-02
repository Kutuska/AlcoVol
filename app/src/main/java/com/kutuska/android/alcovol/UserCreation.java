package com.kutuska.android.alcovol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.*;
import android.widget.Toast;

public class UserCreation extends AppCompatActivity {

    private RadioGroup genderGroup;
    private RadioButton genderButton;
    private SeekBar weightSeekBar;
    private TextView weightSeekBartext;
    private EditText editName;
    private String userName, userGender;
    private int weight;
    private Button saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        setUp();

    }

    public void setUp(){
        userName = null;
        editName = (EditText) findViewById(R.id.username);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroupGender);
        weightSeekBartext = (TextView) findViewById(R.id.seekBarWeightProgress);
        weightSeekBar = (SeekBar) findViewById(R.id.seekBarWeight);
        saveProfileButton = (Button) findViewById(R.id.saveProfileButton);

        processGender(genderGroup);

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editName.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Dont leave it empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(genderGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Choose your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(weight == 0){
                    Toast.makeText(getApplicationContext(), "Set your weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                userName = editName.getText().toString();
                Intent i = new Intent(UserCreation.this, Rounds.class);
                i.putExtra("userWeight",weight);
                i.putExtra("userName",userName);
                i.putExtra("userGender",userGender);
                startActivity(i);
                finish();
            }
        });
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightSeekBartext.setText(progress + " kg");
                weight = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void processGender( final RadioGroup radioGroup){

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioId = radioGroup.getCheckedRadioButtonId();

                genderButton = (RadioButton) findViewById(radioId);

                if (genderButton.getText().equals("male")) {
                    userGender = "male";
                } else {
                    userGender = "female";
                }

            }
        });
    }
}
