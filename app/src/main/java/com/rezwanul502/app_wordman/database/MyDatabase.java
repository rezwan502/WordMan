package com.rezwanul502.app_wordman.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "dictionary_3.db";
    private static final int DATABASE_VERSION = 2;

    private Context context;


    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertFavourite(String mString){
        Log.d("checkword",mString);
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkWord(mString) == 0) {
            db.execSQL("INSERT INTO Favourite VALUES('" + mString + "');");
        }
    }

    public int checkWord(String mString){
        String SELECT_ALL = "SELECT * FROM Favourite WHERE Word Like '"+mString+"'";
        int cnt = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        cnt = cursor.getCount();
        //Toast.makeText(this,""+cnt,Toast.LENGTH_LONG).show();
        if(cnt>0) mString = mString + "help";
        Log.d("cntWordFav",""+cnt);

        return cnt;
    }

    public Cursor DisplayFavourite(){

        String SELECT_ALL = "SELECT * FROM Favourite";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        return cursor;
    }


    public Cursor displayAllData(String mString){

        int len = mString.length();

        String Que="Like ";

        // '%n%' OR '%e%' OR '%t%'

        for(int i=0; i<len; i++){
            Que+="'"+"%"+mString.charAt(i)+"%"+"'";
            if(i != len -1 ){
                Que+=" OR ";
            }
        }


        String SELECT_ALL = "SELECT * FROM dictionary WHERE Length(Word)="+len+" and Word "+Que;
        Log.d("SQL",SELECT_ALL);


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
       // SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

       // String [] sqlSelect = {"0 _id", "Word"};
       // String sqlTables = "Dictionary";

       // qb.setTables(sqlTables);
       // Cursor c = qb.query(db, sqlSelect, "Word =" + mString, null,null, null, null);

        return cursor;


    }

    public void DeleteData(String str){
       // String delete = "DELETE FROM Favourite WHERE Word='"+str+"'";
        //Log.d("DeleteSql",delete);
        SQLiteDatabase db = this.getWritableDatabase();
        //String [] arg = new String[2];
        //arg[0] = str;
        db.execSQL("DELETE FROM Favourite WHERE Word='"+str+"';");
        //Toast.makeText(context,"delete",Toast.LENGTH_LONG).show();

    }

}
