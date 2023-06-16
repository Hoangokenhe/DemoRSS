package com.androidnangcao.demorss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvTinTuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String urlRss = "https://vnexpress.net/rss/cuoi.rss";

        DownloadTinTuc downloadTinTuc = new DownloadTinTuc();
        downloadTinTuc.execute(urlRss );

    }

    public class DownloadTinTuc extends AsyncTask<String, Void, List<TinTuc>>{

        @Override
        protected List<TinTuc> doInBackground(String... strings) {
            List<TinTuc> list = new ArrayList<>();
            try{
                URL url = new URL( strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();// bắt đầu kết nối

                if(urlConnection.getResponseCode() == 200){
                    // kết nối thành công
                    InputStream inputStream = urlConnection.getInputStream();

                    list = new TinTucXML().getList(  inputStream );

                }
                urlConnection.disconnect();

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return list;
        }


        @Override
        protected void onPostExecute(List<TinTuc> tinTucs) {
            super.onPostExecute(tinTucs);

            ArrayAdapter<TinTuc> adapter =
                    new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, tinTucs);

            lvTinTuc.setAdapter( adapter );


        }

            // đổ lên listView thì viết code ở đây
            // ListView lv = activity.findViewById(R.id.lv01);
            // làm tiếp với adapter....



        /*
        quay sang activity viết code thực thi như sau:
         String urlRss = "https://vnexpress.net/rss/cuoi.rss";

        DownloadTinTuc downloadTinTuc = new DownloadTinTuc(MainActivity.this);
        downloadTinTuc.execute(urlRss );
         */


        }
    }


