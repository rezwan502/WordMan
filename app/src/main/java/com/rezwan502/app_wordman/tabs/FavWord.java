package com.rezwan502.app_wordman.tabs;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rezwan502.app_wordman.wordfind.DisplayWords;
import com.rezwan502.app_wordman.wordfind.FindFavWords;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavWord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavWord extends Fragment {

    private static List<HeaderModel> ListHeader;

    MyDatabase myDataBaseHelper;
    private ExpandableListView expandeblelist;
    private RelativeLayout relativeLayout;
    private CustomAdapter customAdapter;
    private ImageView headerImageView;
    private SearchView searchView;
    private ProgressBar pb;


    MediaPlayer mediaPlayer;
    boolean check = false;
    int number;
    private int expandedposition = -1;
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

    public FavWord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favWord.
     */
    // TODO: Rename and change types and number of parameters
    public static FavWord newInstance(String param1, String param2) {
        FavWord fragment = new FavWord();
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

        View view = inflater.inflate(R.layout.fragment_fav_word, container, false);

        myDataBaseHelper = new MyDatabase(getContext());
        // myDataBaseHelper.CreateTableFavourite();

        // myDataBaseHelper.insertFavourite("admin");
        // favouriteList();

        searchView = view.findViewById(R.id.searchviewid);
        relativeLayout = view.findViewById(R.id.relativeId);
        pb = view.findViewById(R.id.progressbarId);


        DisplayWords displayWords = new DisplayWords();
        FindFavWords findFavWords = new FindFavWords();

        findFavWords.setMyDataBaseHelper(myDataBaseHelper);

        displayWords.setView(view);
        displayWords.setRelativeLayout(relativeLayout);
        displayWords.setMyDataBaseHelper(myDataBaseHelper);
        displayWords.setPb(pb);
        displayWords.setContext(getContext());
        displayWords.setFrag("Fav");
        List<HeaderModel> searchedwords = findFavWords.findWords();
        displayWords.setHeader(searchedwords);
        displayWords.startAsyncTask();




        expandeblelist = view.findViewById(R.id.ExpandListViewId);



        expandeblelist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (expandedposition != -1 && expandedposition != groupPosition) {
                        expandeblelist.collapseGroup(expandedposition);
                    }
                    expandedposition = groupPosition;
                }
            });




        expandeblelist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView play = v.findViewById(R.id.AudioId_hader);
                HeaderModel headerModel = (HeaderModel) getGroupPos(groupPosition);
                //String header = headerModel.getHeader();
                final String AudioURL = headerModel.getAudio();



                colV = v;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                preparemediaplayer(AudioURL);

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
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(AudioURL == null){
                                Toast.makeText(getContext(), "No Audio Found", Toast.LENGTH_LONG).show();
                            }

                            if (mediaPlayer.isPlaying()) {

                                mediaPlayer.stop();

                            } else {
                                mediaPlayer.start();
                            }

                        }
                    });

                }

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