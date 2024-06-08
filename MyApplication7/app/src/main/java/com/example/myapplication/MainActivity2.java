package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
TextView textView2;
Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView2=findViewById(R.id.textView2);
        logout=findViewById(R.id.button2);
        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        if(sp.contains("username")){
            textView2.setText(sp.getString("username",""));
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        if(sp.contains("username")){
            SharedPreferences.Editor editor=sp.edit();
            editor.remove("username");
            editor.putString("logoutmsg","logged out suucessfully");
            editor.commit();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}