package com.example.networktest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {
      TextView responseText;
     @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
         ActionBar actionBar=getSupportActionBar();
         if (actionBar !=null)
             actionBar.hide();
          final Button sendRequest = (Button) findViewById(R.id.send_request);
          responseText = (TextView) findViewById(R.id.response_text);
          sendRequest.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  sendRequestWith();
              }
          });
      }
     private void sendRequestWith(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 HttpURLConnection connection = null;
                 BufferedReader reader=null;
                 try {
                     //  AnalyzingOfJson(YouDaoUrl);
                     //HttpURLConnection connection = null;
                     URL url = new URL("http://www.baidu.com");
                     connection = (HttpURLConnection) url.openConnection();
                     connection.setRequestMethod("GET");
                     InputStream in = connection.getInputStream();
                     reader = new BufferedReader(new InputStreamReader(in));
                     String line = null;
                     StringBuilder response =new StringBuilder();
                     while ((line =reader.readLine()) != null)
                         response.append(line);
                     showResponse(response.toString());
                 }catch (Exception e){
                     e.printStackTrace();
                 }finally {
                     if (reader != null){
                         try{
                             reader.close();
                         }catch (IOException e){
                             e.printStackTrace();
                         }
                     }
                     if (connection != null){
                         connection.disconnect();
                     }
                 }
             }
         }).start();
     }

     private void showResponse(final String response){
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 responseText.setText(response);
             }
         });
     }



  }