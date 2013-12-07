package com.example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.htmlcleaner.TagNode;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity {

    //Диалог ожидания
    private ProgressDialog processDialog;

    private EditText textForSearch;
    private Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textForSearch = (EditText) findViewById(R.id.text);
        searchButton = (Button)findViewById(R.id.parse);
        searchButton.setOnClickListener(myListener);
    }


    //Слушатель OnClickListener для кнопки searchButton
    private View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Показать диалог ожидания
            processDialog = ProgressDialog.show(MyActivity.this, "Working...", "request to server", true, false);
            //Запустить парсинг
            new ParseSite().execute("http://www.google.com.tj/search?q=" + textForSearch.getText().toString());
        }
    };

    private class ParseSite extends AsyncTask<String, Void, List<String>> {
        //Фоновая операция
        protected List<String> doInBackground(String... params) {
            List<String> listOfUrls = new ArrayList<String>();
            try {
                ParseHTML hh = new ParseHTML(new URL(params[0]));
                List<TagNode> TagNodes = hh.getLinksByClass();
                for (TagNode tagNode : TagNodes) {
                    TagNode divElement = tagNode;
                    listOfUrls.add(divElement.getAttributeByName("href"));
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return listOfUrls;
        }

        //Событие по окончанию парсинга
        protected void onPostExecute(List<String> listOfUrls) {
            //Убрать диалог загрузки
            processDialog.dismiss();

            ListView listView = (ListView) findViewById(R.id.listViewData);

            //Загрузить в listView результат работы doInBackground
            listView.setAdapter(new ArrayAdapter<String>(MyActivity.this,
                    android.R.layout.simple_list_item_1, listOfUrls));
        }
    }
}


