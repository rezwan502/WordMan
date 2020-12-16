package com.rezwan502.app_wordman.tabs;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.rezwan502.app_wordman.R;
import com.rezwan502.app_wordman.adapter.CustomAdapter;
import com.rezwan502.app_wordman.database.MyDatabase;
import com.rezwan502.app_wordman.model.HeaderModel;
import com.rezwan502.app_wordman.wordfind.FindWordSearch;
import com.rezwan502.app_wordman.wordfind.DisplayWords;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordSearch extends Fragment {

    private static List<HeaderModel> ListHeader;
    private ExpandableListView expandeblelist;
    private RelativeLayout relativeLayout;
    private CustomAdapter customAdapter;
    private ImageView headerImageView;
    private SearchView searchView;
    private ProgressBar pb;


    // public List<HeaderModel> ListHeader;

    HashMap<String, List<String>> Listchild;
    List<String> child;

    HashMap<String, List<String>> Listpartsofspeech;
    List<String> partsofspeech;

    HashMap<String, List<String>> ListExample;
    List<String> example;

    private int expandedposition = -1;
    private String input = "";

    // HashMap<String,List<String>> ListAudio;
    // List<String> audio;

    MyDatabase myDataBaseHelper;

    boolean check = false;
    int number;

    MediaPlayer mediaPlayer;
    View colV;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static void listCons(List<HeaderModel> Listheader) {
        ListHeader = Listheader;

    }


    public WordSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static WordSearch newInstance(String param1, String param2) {
        WordSearch fragment = new WordSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_word_search, container, false);

        // Inflate the layout for this fragment

        myDataBaseHelper = new MyDatabase(getContext());
        // myDataBaseHelper.CreateTableFavourite();

        // myDataBaseHelper.insertFavourite("admin");
        // favouriteList();

        searchView = view.findViewById(R.id.searchviewid);
        relativeLayout = view.findViewById(R.id.relativeId);
        pb = view.findViewById(R.id.progressbarId);

        //String input="";


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                check = true;
                input = query.trim();
                /**
                 This code works fine
                 */
                //startAsyncTask();
                /**
                 below codes is under testing
                 */
                DisplayWords displayWords = new DisplayWords();
                FindWordSearch findWordSearch = new FindWordSearch();
                findWordSearch.setMyDataBaseHelper(myDataBaseHelper);

                displayWords.setView(view);
                displayWords.setRelativeLayout(relativeLayout);
                displayWords.setMyDataBaseHelper(myDataBaseHelper);
                displayWords.setPb(pb);
                displayWords.setContext(getContext());
                displayWords.setFrag("Search");
                List<HeaderModel> searchedwords = findWordSearch.findWords(input);

                if(searchedwords.size()<1){
                    Toast.makeText(getContext(),"No Word Found",Toast.LENGTH_LONG).show();
                    return false;
                }


                displayWords.setHeader(searchedwords);
                displayWords.startAsyncTask();

                //retrieve_data(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        expandeblelist = view.findViewById(R.id.ExpandListViewId);

            expandeblelist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (expandedposition != -1 && expandedposition != groupPosition) {
                        expandeblelist.collapseGroup(expandedposition);
                        final ImageView play = colV.findViewById(R.id.AudioId_hader);
                        play.setClickable(false);

                    }
                    expandedposition = groupPosition;
                }
            });

        expandeblelist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                final ImageView play = v.findViewById(R.id.AudioId_hader);
                final CheckBox star = v.findViewById(R.id.starId);
                colV = v;

                play.setClickable(true);

                String status;
                if (play.isClickable()) {
                    status = "true";
                } else {
                    status = "false";
                }
                Log.d("playStatus", status);
               // Toast.makeText(getContext(), status, Toast.LENGTH_LONG).show();

                if(status == "true"){

                HeaderModel headerModel = (HeaderModel) getGroupPos(groupPosition);
                final String header = headerModel.getHeader();
                final String AudioURL = headerModel.getAudio();

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                preparemediaplayer(AudioURL);

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(AudioURL == ""){
                            Toast.makeText(getContext(), "No Audio Found", Toast.LENGTH_LONG).show();
                        }

                        if (mediaPlayer.isPlaying()) {
                            //play.setEnabled(true);
                            mediaPlayer.stop();

                        } else {
                            // play.setEnabled(false);
                            mediaPlayer.start();
                        }
                        //play.setEnabled(false);
                    }

                });

                    star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(star.isChecked()){
                                myDataBaseHelper.insertFavourite(header);
                            }
                        }
                    });

                }

               // play.setClickable(false);

                return false;
            }

        });


        return view;
    }


    public Object getGroupPos(int groupPosition) {
        return ListHeader.get(groupPosition);
    }

    public void preparemediaplayer(String URL){

        try{
            mediaPlayer.setDataSource(URL);
            mediaPlayer.prepare();
        }catch (Exception e){
            // Toast.makeText(context,"MediaPlayer: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


}