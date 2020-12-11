package com.example.app_wordman.wordfind;


import android.database.Cursor;

import com.example.app_wordman.database.MyDatabase;
import com.example.app_wordman.model.HeaderModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindWordSearch {

    MyDatabase myDataBaseHelper;
    List<HeaderModel> Listheader;

    public void setMyDataBaseHelper(MyDatabase myDataBaseHelper) {
        this.myDataBaseHelper = myDataBaseHelper;
    }

    public List<HeaderModel> findWords(String mstr){

        Cursor cursor = myDataBaseHelper.displayAllData(mstr);
        StringBuffer stringBuffer = new StringBuffer();

        Listheader = new ArrayList<HeaderModel>();

        while(cursor.moveToNext()){

            String str = cursor.getString(1).toString();
            //str = str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
            Listheader.add(new HeaderModel(str,""));
        }
        // Log.d("NUMBER",(String) number);

        return Listheader;
    }

}
