package com.example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.htmlcleaner.TagNode;

import java.net.URL;


public class MyActivity extends Activity {

    //Диалог ожидания
    private ProgressDialog processDialog;

    private EditText editTextForSearch;
    private Button searchButton;
    private String textForSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        editTextForSearch = (EditText) findViewById(R.id.text);
        searchButton = (Button)findViewById(R.id.parse);
        searchButton.setOnClickListener(myListener);
    }


    //Слушатель OnClickListener для кнопки searchButton
    private View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Показать диалог ожидания
            processDialog = ProgressDialog.show(MyActivity.this, "Working...", "request to server", true, false);
            textForSearch = editTextForSearch.getText().toString().replaceAll(" ", "%20");
            //Запустить парсинг
            new ParseSite().execute("http://www.google.com.tj/search?q=" + textForSearch);
        }
    };

    private class ParseSite extends AsyncTask<String, Void, String> {
        //Фоновая операция
        protected String doInBackground(String... params) {
            String url = "";
            try {
                ParseHTML hh = new ParseHTML(new URL(params[0]));
                TagNode tagNode = hh.getLinkFromPage();
                url = tagNode.getAttributeByName("href");

            } catch(Exception e) {
                e.printStackTrace();
            }
            return url;
        }

        //Событие по окончанию парсинга
        protected void onPostExecute(String url) {
            //Убрать диалог загрузки
            processDialog.dismiss();

            TextView textView = (TextView) findViewById(R.id.textViewData);

            //Загрузить в TextView результат работы doInBackground
            textView.setText(url);
        }
    }
}


