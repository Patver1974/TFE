package com.example.tft_jeu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityLogo extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_logo);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.iv_main_walkingman);
        slogan = findViewById(R.id.tv_main_slogan);

        image.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);



        new CountDownTimer(3000, 3000){
            public void onTick(long millisUntilFinished){

            }
           public  void onFinish(){
               Intent intentList = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intentList);
               finish();
           }
        }.start();




    }

}