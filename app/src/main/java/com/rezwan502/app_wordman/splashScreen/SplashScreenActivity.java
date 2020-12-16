package com.rezwan502.app_wordman.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rezwan502.app_wordman.MainActivity;
import com.rezwan502.app_wordman.SlideActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isOpenAlread()){
            Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }else{
            SharedPreferences.Editor editor = getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();

            Intent intent = new Intent(SplashScreenActivity.this, SlideActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean isOpenAlread(){
        SharedPreferences sharedPreferences = getSharedPreferences("slide",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("slide",false);
        return result;
    }
}