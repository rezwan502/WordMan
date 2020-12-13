package com.example.app_wordman.adapter;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_wordman.R;
import com.example.app_wordman.database.MyDatabase;
import com.example.app_wordman.model.HeaderModel;
import com.example.app_wordman.tabs.FavWord;

import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseExpandableListAdapter {

    Context context;
    List<HeaderModel> Listheader;
    HashMap<String,List<String>> Listchild;
    HashMap<String,List<String>> Listpartsofspeech;
    HashMap<String,List<String>> ListExample;
   // HashMap<String,List<String>> ListAudio;

    private LayoutInflater inflater;
    private CheckBox star;
    private ImageView delete;

    MyDatabase myDataBaseHelper;
    MediaPlayer mediaPlayer;
    String currentFrag;


    public CustomAdapter(Context context, List<HeaderModel> listheader, HashMap<String, List<String>> listchild, HashMap<String,List<String>> listpartsofspeech, HashMap<String,List<String>> listExample, String currentFrag) {
        this.context = context;
        Listheader = listheader;
        Listchild = listchild;
        Listpartsofspeech = listpartsofspeech;
        ListExample = listExample;
        myDataBaseHelper = new MyDatabase(context);
        this.currentFrag = currentFrag;
    }

    @Override
    public int getGroupCount() {
        return Listheader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Listchild.get(Listheader.get(groupPosition).getHeader()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Listheader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Listchild.get(Listheader.get(groupPosition).getHeader()).get(childPosition);
    }

    public Object getChild_pos(int groupPosition, int childPosition) {
        return Listpartsofspeech.get(Listheader.get(groupPosition).getHeader()).get(childPosition);
    }

    public Object getChild_exp(int groupPosition, int childPosition) {
        return ListExample.get(Listheader.get(groupPosition).getHeader()).get(childPosition);
    }



    /*
    public Object getGroup_audio(int groupPosition, int childPosition) {
        return ListAudio.get(Listheader.get(groupPosition)).get(childPosition);
    }

     */

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final HeaderModel headerModel = (HeaderModel) getGroup(groupPosition);
        final String header = headerModel.getHeader();
        final String AudioURL = headerModel.getAudio();

        if(convertView == null){

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout,parent,false);

        }

        TextView textView = convertView.findViewById(R.id.headerTextViewId);
        textView.setText(header);
        // convertView.setBackgroundColor(Color.parseColor("#00FF00"));
        Log.d("HEADER",header);

        star = convertView.findViewById(R.id.starId);
        delete = convertView.findViewById(R.id.deletebtn);

        if(currentFrag == "Fav"){
            star.setVisibility(convertView.INVISIBLE);
            delete.setVisibility(convertView.VISIBLE);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDataBaseHelper.DeleteData(header);
                    Listheader.remove(headerModel);
                    notifyDataSetChanged();
                }
            });


        }else if(currentFrag == "Search"){
            star.setVisibility(convertView.INVISIBLE);
            delete.setVisibility(convertView.INVISIBLE);
            // final MainActivity main = new MainActivity();
        }


        ImageView play = convertView.findViewById(R.id.AudioId_hader);
        if(isExpanded == false){
            play.setVisibility(convertView.INVISIBLE);
        }else{
            play.setVisibility(convertView.VISIBLE);
            if(currentFrag == "Search")
              star.setVisibility(convertView.VISIBLE);
        }

        if(isExpanded) {
            textView.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
        }else{
            textView.setTextColor(context.getResources().getColor(R.color.textBlack));
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String child = (String) getChild(groupPosition,childPosition);
        String child_pos = (String) getChild_pos(groupPosition,childPosition);
        String child_exp = (String) getChild_exp(groupPosition,childPosition);

        if(convertView == null){

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout,parent,false);

        }

        TextView textViewpos = convertView.findViewById(R.id.PartsOfSpeech);
        TextView textViewdef = convertView.findViewById(R.id.childTextDefinition);
        TextView textViewexp = convertView.findViewById(R.id.childTextex);
        //final ImageView play = convertView.findViewById(R.id.AudioId);

        if(child_exp == null){
            child_exp = "---";
        }
        if(child_exp == null){
            child_pos = "---";
        }
        if(child_exp == null){
            child = "---";
        }


        textViewpos.setText(child_pos);
        textViewdef.setText(child);
        textViewexp.setText(child_exp);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
