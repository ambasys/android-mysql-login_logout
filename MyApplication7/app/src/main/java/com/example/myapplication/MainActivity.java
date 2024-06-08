package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    Button login;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        login=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        if(sp.contains("logoutmsg")){
            textView.setText(sp.getString("logoutmsg",""));
            SharedPreferences.Editor editor=sp.edit();
            editor.remove("logoutmsg");
            editor.commit();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String path="http://10.0.2.2/login.php"+"?u_name="+editText1.getText().toString()+"&u_password="+editText2.getText().toString();
        class backgroundWorker extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url =new URL(strings[0]);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    InputStream is=conn.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                    return bufferedReader.readLine();

                } catch (Exception e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if(s.equals("success")){
                    SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("username",editText1.getText().toString());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));

                }
                else{
                    editText1.setText("");
                    editText2.setText("");
                    textView.setTextColor(Color.RED);
                    textView.setText(s);
                }
            }
        }
        backgroundWorker bw=new backgroundWorker();
        bw.execute(path);
    }
}