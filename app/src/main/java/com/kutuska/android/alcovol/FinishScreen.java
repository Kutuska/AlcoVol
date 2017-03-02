package com.kutuska.android.alcovol;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;


public class FinishScreen extends AppCompatActivity {
    private static final String FORMAT = "%02d:%02d:%02d";
    private TextView countdown;
    private double ebacresult;
    private long countdowntime;
    private Button goHomeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);

        setUpTimer();


    }
    public void setUpTimer(){
        goHomeButton = (Button) findViewById(R.id.gohomebutton);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            countdown = (TextView) findViewById(R.id.counter);
            ebacresult = extras.getDouble("ebacresult");
            while (ebacresult > 0){
                ebacresult -= 0.015;
                countdowntime +=3600000;
            }
            new CountDownTimer(countdowntime, 1000) {

                public void onTick(long millisUntilFinished) {
                    countdown.setText(""+String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))+ " until sober again");
                }

                public void onFinish() {
                    countdown.setText("You are sober again!");
                }
            }.start();
        }
        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
