package com.rezwanul502.app_wordman.wordfind;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.rezwanul502.app_wordman.R;
import com.rezwanul502.app_wordman.adapter.CustomAdapter;
import com.rezwanul502.app_wordman.api.Definition;
import com.rezwanul502.app_wordman.api.DefinitionInterface;
import com.rezwanul502.app_wordman.api.Example;
import com.rezwanul502.app_wordman.api.Meaning;
import com.rezwanul502.app_wordman.api.Phonetic;
import com.rezwanul502.app_wordman.api.RetrofitApiClient;
import com.rezwanul502.app_wordman.database.MyDatabase;
import com.rezwanul502.app_wordman.model.HeaderModel;
import com.rezwanul502.app_wordman.tabs.FavWord;
import com.rezwanul502.app_wordman.tabs.WordSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DisplayWords {

    private ExpandableListView expandeblelist;
    private RelativeLayout relativeLayout;
    private CustomAdapter customAdapter;
    private ImageView headerImageView;
    private SearchView searchView;
    private ProgressBar pb;

    public List<HeaderModel> Listheader;

    HashMap<String,List<String>> Listchild;
    List<String> child;

    HashMap<String,List<String>> Listpartsofspeech;
    List<String> partsofspeech;

    HashMap<String,List<String>> ListExample;
    List<String> example;

    private int expandedposition = -1;
    private String input = "";

    // HashMap<String,List<String>> ListAudio;
    // List<String> audio;

    MyDatabase myDataBaseHelper;

    boolean check  = false;
    int number;
    public String currentFrag;

    View view;
    Context context;
    public void setContext(Context context){
        this.context = context;
    }

    public void setPb(ProgressBar pb) {
        this.pb = pb;
    }

    public void setMyDataBaseHelper(MyDatabase myDataBaseHelper) {
        this.myDataBaseHelper = myDataBaseHelper;
    }
    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }
    public void setFrag(String str){
        currentFrag = str;
    }

    public void setView(View view) {
        this.view = view;
    }
    public void setHeader(List<HeaderModel> listHeader) {
        this.Listheader = listHeader;
    }

    public void retrieve_data(){

        Listchild = new HashMap<String, List<String>>();
        Listpartsofspeech = new HashMap<String, List<String>>();
        ListExample = new HashMap<String, List<String>>();

        int headerlen = Listheader.size();
        for(int i=0; i<headerlen; i++){
            String str = Listheader.get(i).getHeader();
            Definition(str,i);
        }
    }

    public void listprocessing(){

        expandeblelist = view.findViewById(R.id.ExpandListViewId);
        customAdapter = new CustomAdapter(context,Listheader,Listchild,Listpartsofspeech,ListExample,currentFrag);
        expandeblelist.setAdapter(customAdapter);
    }




    public void Definition(String strword, final int indexofaudio){
        DefinitionInterface definitionInterface = RetrofitApiClient.getClient().create(DefinitionInterface.class);

        Call<List<Example>> call = definitionInterface.getDefinitions(strword);

        try{
            Response<List<Example>> response = call.execute();
            List<Example> datarespond = response.body();

            if(datarespond == null){

                child = new ArrayList<String>();
                partsofspeech = new ArrayList<>();
                example = new ArrayList<>();

                child.add("Not found in server. Please check in internet");
                example.add("Not found in server. Please check in internet");
                // partsofspeech
                partsofspeech.add("404");

                ListExample.put(strword,example);
                Listpartsofspeech.put(strword,partsofspeech);
                Listchild.put(strword,child);


                return;
            }

            for(int t=0;t<datarespond.size();t++) {

                Example objectdata = datarespond.get(t);

                //audio

                List<Phonetic> phonetics = objectdata.getPhonetics();
                //  audio = new ArrayList<>();
                String AudioURL = "";
                int p = 0;
                for (Phonetic phone : phonetics) {
                    AudioURL = phone.getAudio();
                    //audio.add(AudioURL);
                    break;
                }
                Listheader.get(indexofaudio).setAudio(AudioURL);
                //Toast.makeText(MainActivity.this,"AudioURL: " + AudioURL,Toast.LENGTH_LONG).show();


                // meaning

                List<Meaning> meanings = objectdata.getMeanings();

                child = new ArrayList<String>();
                partsofspeech = new ArrayList<>();
                example = new ArrayList<>();
                //Meaning partsofSpeech = meanings.get(0);
                //String pos = partsofSpeech.getPartOfSpeech();

                // Log.d("PartsOfSpeech: ",pos);

                for (Meaning mean : meanings) {


                    List<Definition> def = mean.getDefinitions();

                    for (int i = 0; i < def.size(); i++) {

                        Definition definition = def.get(i);

                        //textViewResult.append(mean.getPartOfSpeech()+"\n"+definition.getDefinition());
                        //textViewResult.append("\n");
                        String defi = definition.getDefinition();
                        String exp = definition.getExample();
                        // audio.add(AudioURL);
                        child.add(defi);
                        example.add(exp);
                        // partsofspeech
                        String pos = mean.getPartOfSpeech();
                        Log.d("PartsOfSpeech: ", pos);
                        partsofspeech.add(pos);
                    }


                }

                //  ListAudio.put(strword,audio);
                ListExample.put(strword, example);
                Listpartsofspeech.put(strword, partsofspeech);
                Listchild.put(strword, child);

            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public void startAsyncTask() {
        //relativeLayout = view.findViewById(R.id.relativeId);
       // pb = view.findViewById(R.id.progressbarId);
      //  myDataBaseHelper = new MyDatabase(view.getContext());
        WordSearch.listCons(Listheader);
        FavWord.listCons(Listheader);
        ExampleAsyncTask task = new ExampleAsyncTask();
        task.execute();
    }
    private class ExampleAsyncTask extends AsyncTask<Void, Void, Void> {
        /*
        private WeakReference<MainActivity> activityWeakReference;
        ExampleAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

         */

        @Override
        protected void onPreExecute() {

            relativeLayout.setVisibility(View.VISIBLE);
            pb.setVisibility(View.VISIBLE);
            super.onPreExecute();
            /*
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

             */
            // activity.progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            // MainActivity activity = activityWeakReference.get();
            /*
            for(int i=0; i<10; i++){
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

             */

            retrieve_data();
            /*
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
           // definition(input);

             */

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            relativeLayout.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);
/*
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

 */
            Log.d("onPostExecute","Done ");
            //Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
            // activity.progressBar.setVisibility(View.INVISIBLE);
            listprocessing();

        }
    }



}
