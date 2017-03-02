package com.kutuska.android.alcovol;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Rounds extends AppCompatActivity {

    private TextView nameresult, weightresult, genderresult;
    private EditText alcMlText, abvText, timeText;
    private Button CalculateButton;
    private double alcMl, probac, abv, bodyWeight, bac, ebac, rawnum, et;
    final double femaleconst = 0.55;
    final double maleconst = 0.68;
    final double alcdens = 0.78924;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds);

        setItUp();
        getThings();
        calculate();

    }

    public void setItUp(){
        nameresult = (TextView) findViewById(R.id.nameres);
        weightresult = (TextView) findViewById(R.id.weightres);
        genderresult = (TextView) findViewById(R.id.genderres);
        alcMlText = (EditText) findViewById(R.id.alcMlid);
        abvText = (EditText) findViewById(R.id.abvid);
        timeText = (EditText) findViewById(R.id.etid);
        alcMl = 0;
        abv = 0;
        et = 0;

    }
    public void getThings(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String nameResult = extras.getString("userName");
            int weightResult = extras.getInt("userWeight");
            String genderResult = extras.getString("userGender");
            nameresult.setText(nameResult);
            weightresult.setText(weightResult + " kg");
            bodyWeight = weightResult;
            genderresult.setText(genderResult);

        }
    }
    public void calculate(){
        CalculateButton = (Button) findViewById(R.id.saveAlcoholButton);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(alcMlText.getText().toString()) || TextUtils.isEmpty(abvText.getText().toString()) || TextUtils.isEmpty(timeText.getText().toString()) )
                {
                    Toast.makeText(getApplicationContext(), "Dont leave it empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(abv > 100){
                    Toast.makeText(getApplicationContext(), "Its impossible!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (rawnum==0){
                    if (genderresult.getText().toString() == "female") {
                        rawnum = (bodyWeight * 1000) * femaleconst;
                    } else {
                        rawnum = (bodyWeight * 1000) * maleconst;
                    }
                }
                alcMl = Double.parseDouble(alcMlText.getText().toString());
                abv = Double.parseDouble(abvText.getText().toString());
                et = Double.parseDouble(timeText.getText().toString());
                if(bac==0) {
                    bac = (((((alcMl * abv) / 100) * alcdens) / rawnum) * 100);
                    ebac = bac - (et * 0.015);

                } else {
                    bac = (((((alcMl * abv) / 100) * alcdens) / rawnum) * 100);
                    bac += probac;
                    ebac = bac - (et * 0.015);
                }
                Intent u = new Intent(Rounds.this, Results.class);
                u.putExtra("ebac", ebac);
                startActivityForResult(u, 1);
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                probac=data.getDoubleExtra("result",0);
                setItUp();
                getThings();
                calculate();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }

}
