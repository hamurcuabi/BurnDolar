package com.emrehmrc.burndolar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnStart;
    private EditText edtNick;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnStart=findViewById(R.id.btnStart);
        edtNick=findViewById(R.id.nickname_edittext);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name=edtNick.getText().toString().toLowerCase();
                if(name==""){
                    name="player";
                }
                //go main
                if(isNetworkConnected()){
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    i.putExtra("nick",name);
                    startActivity(i);
                }
                else Toast.makeText(getApplicationContext(),"İnternet Bağlantınız Yok!",Toast
                        .LENGTH_SHORT).show();

            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
