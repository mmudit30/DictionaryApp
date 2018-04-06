package com.example.hp.dictionaryapp;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Dictionary extends AppCompatActivity {
TextView tv_word;
    TextView textViewTitle;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        tv_word = (TextView) findViewById(R.id.tv_world);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
    }
    public void translate(View v)
    {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getClass().getPackage().getName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                   startActivityForResult(i,VOICE_RECOGNITION_REQUEST_CODE);

    }
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
        {

        }
        if(resultCode == RESULT_OK){
            ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (!textMatchList.isEmpty()){
                if (textMatchList.get(0).contains("search")){
                    String searchQuery = textMatchList.get(0).replace("search"," ");
                    Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                    search.putExtra(SearchManager.QUERY, searchQuery);
                    startActivity(search);
                }
                else{
                    String word = textMatchList.get(0);
                    tv_word.setText("You Spoke: "+word);
                    getMeaning(word);
                }
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    private void getMeaning(String word2)
    {
        Intent wrdintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.oxforddictionaries.com/definition/" + word2));
        startActivity(wrdintent);
    }
}
