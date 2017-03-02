package com.kutuska.android.alcovol;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Results extends AppCompatActivity {

    private TextView ebacResult, drunkResult;
    private Button newDrinkButton, finishButton;
    private double ebacresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setItUp();
        getResults();
        buttons();



    }
    public void setItUp() {
        ebacResult = (TextView) findViewById(R.id.resultbac);
        drunkResult = (TextView) findViewById(R.id.resultdrunk);
        newDrinkButton = (Button) findViewById(R.id.newAlcoholButton);
        finishButton = (Button) findViewById(R.id.FinishButton);
    }
    public void getResults(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            ebacresult = extras.getDouble("ebac");
            if(ebacresult <= 0){ebacresult = 0;}
            ebacResult.setText(String.format("%.3f", ebacresult));

            if(ebacresult >= 0.4){
                drunkResult.setText("Possibly poisoned or dead...");
            } else if( ebacresult >= 0.3){
                drunkResult.setText("You are losing understanding of the world around you");
            } else if( ebacresult >= 0.2){
                drunkResult.setText("You will either vomit or beat up someone");
            } else if( ebacresult >= 0.1){
                drunkResult.setText("Your reaction time slows down, you are speaking gibberish");
            } else if( ebacresult >= 0.06){
                drunkResult.setText("You feel really good, you think you can conquer the world");
            } else if( ebacresult >= 0.03){
                drunkResult.setText("You are talking way too much and smiling all the time");
            } else{
                drunkResult.setText("You must feel normal, have a drink already!");
            }
        }
    }
    public void buttons(){
        newDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",ebacresult);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(Results.this, FinishScreen.class);
                f.putExtra("ebacresult",ebacresult);
                startActivity(f);
                finish();
            }
        });
    }

}
