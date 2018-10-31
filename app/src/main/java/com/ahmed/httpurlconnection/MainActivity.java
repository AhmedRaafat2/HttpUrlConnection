package com.ahmed.httpurlconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    URL url;
    InputStream inputStream;
    StringBuffer stringBuffer;
    String result;
    String firstData;
    String link="https://jsonplaceholder.typicode.com/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.btn_load);
        textView=findViewById(R.id.text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            url=new URL(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        try {
                            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            inputStream=urlConnection.getInputStream();
                            int c=0;
                            stringBuffer=new StringBuffer();
                            int responseCode=urlConnection.getResponseCode();
                            if(responseCode==HttpURLConnection.HTTP_OK){
                                while ((c=inputStream.read())!=-1){
                                    stringBuffer.append((char)c);
                                }

                            }
                            result=stringBuffer.toString();
                            JSONArray array=new JSONArray(result);
                            JSONObject object=array.getJSONObject(0);
                            int id=object.getInt("id");
                            String name=object.getString("name");
                            firstData=id+" "+name;
                            inputStream.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(firstData);
                    }
                });

            }
        });
    }
}
