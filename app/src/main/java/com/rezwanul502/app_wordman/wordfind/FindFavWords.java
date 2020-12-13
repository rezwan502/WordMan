package com.rezwanul502.app_wordman.wordfind;

import android.database.Cursor;

import com.rezwanul502.app_wordman.database.MyDatabase;
import com.rezwanul502.app_wordman.model.HeaderModel;

import java.util.ArrayList;
import java.util.List;

public class FindFavWords {

    MyDatabase myDataBaseHelper;
    List<HeaderModel> Listheader;

    public void setMyDataBaseHelper(MyDatabase myDataBaseHelper) {
        this.myDataBaseHelper = myDataBaseHelper;
    }

    public List<HeaderModel> findWords(){

        Cursor cursor = myDataBaseHelper.DisplayFavourite();
        StringBuffer stringBuffer = new StringBuffer();

        Listheader = new ArrayList<HeaderModel>();

        while(cursor.moveToNext()){

            String str = cursor.getString(0).toString();
            //str = str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
            Listheader.add(new HeaderModel(str,""));
        }
        // Log.d("NUMBER",(String) number);

        return Listheader;
    }



}
