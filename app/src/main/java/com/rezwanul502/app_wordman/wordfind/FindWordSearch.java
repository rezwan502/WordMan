package com.rezwanul502.app_wordman.wordfind;


import android.database.Cursor;

import com.rezwanul502.app_wordman.database.MyDatabase;
import com.rezwanul502.app_wordman.model.HeaderModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindWordSearch {

    MyDatabase myDataBaseHelper;
    List<HeaderModel> Listheader;

    public void setMyDataBaseHelper(MyDatabase myDataBaseHelper) {
        this.myDataBaseHelper = myDataBaseHelper;
    }

    // Method to sort a string alphabetically
    public static String sortString(String inputString)
    {
        // convert input string to char array
        char tempArray[] = inputString.toCharArray();

        // sort tempArray
        Arrays.sort(tempArray);

        // return new sorted string
        return new String(tempArray);
    }


    public List<HeaderModel> findWords(String mstr){

        final String input = sortString(mstr);

        Cursor cursor = myDataBaseHelper.displayAllData(mstr);
        StringBuffer stringBuffer = new StringBuffer();

        Listheader = new ArrayList<HeaderModel>();

        while(cursor.moveToNext()){

            String str = cursor.getString(1).toString();
            //str = str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();

            String outputString = sortString(str);
            if(outputString.equals(input))
                Listheader.add(new HeaderModel(str,""));
        }
        // Log.d("NUMBER",(String) number);

        return Listheader;
    }

}
