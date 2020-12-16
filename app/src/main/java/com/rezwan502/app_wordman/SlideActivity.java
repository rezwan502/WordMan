package com.rezwan502.app_wordman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.rezwan502.app_wordman.adapter.SlideAdapter;

public class SlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mdotsLayout;
    private LinearLayout mDescLayout;

    SlideAdapter slideAdapter;

    TextView[] mDots;
    TextView mText;
    ImageView mImage;

    Button mPrevbtn;
    Button mNextbtn;
    Button mSkip;

    int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        if(isOpenAlread()){
            Intent intent = new Intent(SlideActivity.this,MainActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }else{
            SharedPreferences.Editor editor = getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }


 */


        setContentView(R.layout.activity_slide);


        mSlideViewPager = findViewById(R.id.slideViewPager);
        mdotsLayout = findViewById(R.id.dotsLayout);
        mDescLayout = findViewById(R.id.descLayout);

        mPrevbtn = findViewById(R.id.prevbtn);
        mNextbtn = findViewById(R.id.nextbtn);
        mSkip = findViewById(R.id.skipbtn);

        addDotsIndicator(0);
        addDescription(mCurrentPage);

        slideAdapter = new SlideAdapter(this);
        mSlideViewPager.setAdapter(slideAdapter);



        mSlideViewPager.addOnPageChangeListener(viewListener);


        mNextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNextbtn.getText() == "Finish"){
                    Intent intent = new Intent(SlideActivity.this,MainActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mPrevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideActivity.this,MainActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });


    }

    private boolean isOpenAlread(){
        SharedPreferences sharedPreferences = getSharedPreferences("slide",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("slide",false);
        return result;
    }

    private void addDotsIndicator(int position) {
        mDots = new TextView[4];
        mdotsLayout.removeAllViews();

        for(int i=0; i<mDots.length; i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#9673;"));
            mDots[i].setTextSize(15);
            mDots[i].setTextColor(getResources().getColor(R.color.dotColor2));
            (mdotsLayout).addView(mDots[i]);

        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.dotColor));
        }



    }

    private void addDescription(int page) {
        mText = new TextView(this);
        mImage = new ImageView(this);

        ImageView mImage1 = new ImageView(this);
        TextView mText1 = new TextView(this);
        TextView mText2 = new TextView(this);


        mDescLayout.removeAllViews();

        if(page == 0){
            mText.setText("Write your letters to make words (if possible)");
            mText.setTextColor(getResources().getColor(R.color.textBlack));
            mText.setTypeface(mText.getTypeface(), Typeface.BOLD);
            mText.setTextSize(15);
            mDescLayout.addView(mText);
        }
        else if(page == 1){
            mText.setText("Click on Words");
            mText.setTextColor(getResources().getColor(R.color.textBlack));
            mText.setTypeface(mText.getTypeface(), Typeface.BOLD);
            mText.setTextSize(15);
            mDescLayout.addView(mText);
        }
        else if(page == 2){
            mImage.setImageResource(R.drawable.ic_baseline_star_24);
            mDescLayout.addView(mImage);
            mText.setText("Click on Star to add in Star Words");
            mText.setTextColor(getResources().getColor(R.color.textBlack));
            mText.setTypeface(mText.getTypeface(), Typeface.BOLD);
            mText.setTextSize(15);
            mDescLayout.addView(mText);

            mText2.setText("");
            mText2.setTextColor(getResources().getColor(R.color.textBlack));
            mText2.setTypeface(mText2.getTypeface(), Typeface.BOLD);
            mText2.setTextSize(15);
            mDescLayout.addView(mText2);


            mImage1.setImageResource(R.drawable.ic_baseline_volume_up_24);
            mDescLayout.addView(mImage1);
            mText1.setText("Click on Sound to hear pronunciation");
            mText1.setTextColor(getResources().getColor(R.color.textBlack));
            mText1.setTypeface(mText1.getTypeface(), Typeface.BOLD);
            mText1.setTextSize(15);
            mDescLayout.addView(mText1);
        }
        else if(page == 3){
            mImage.setImageResource(R.drawable.ic_baseline_delete_forever_24);
            mDescLayout.addView(mImage);
            mText.setText("Click on delete to remove from Star Words");
            mText.setTextColor(getResources().getColor(R.color.textBlack));
            mText.setTypeface(mText.getTypeface(), Typeface.BOLD);
            mText.setTextSize(15);
            mDescLayout.addView(mText);
        }

    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            addDescription(position);
            mCurrentPage = position;

            if(position == 0){
                mNextbtn.setEnabled(true);
                mPrevbtn.setEnabled(false);
                mPrevbtn.setVisibility(View.INVISIBLE);

                mNextbtn.setText("Next");
                mPrevbtn.setText("");


            }else if(position == mDots.length - 1){
                mNextbtn.setEnabled(true);
                mPrevbtn.setEnabled(true);
                mPrevbtn.setVisibility(View.VISIBLE);

                mNextbtn.setText("Finish");
                mPrevbtn.setText("Back");
            }else{
                mNextbtn.setEnabled(true);
                mPrevbtn.setEnabled(true);
                mPrevbtn.setVisibility(View.VISIBLE);

                mNextbtn.setText("Next");
                mPrevbtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}