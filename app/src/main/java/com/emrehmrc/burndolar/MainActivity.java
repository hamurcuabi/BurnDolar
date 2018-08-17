package com.emrehmrc.burndolar;

import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    GifDrawable gifFromResource;
    ImageView dolar, dolar2;
    LinearLayout sources;
    int count = 0;
    int usercount = 0;
    Button btnDolar;
    Button btnUser;
    TextView txtOnline;
    MediaPlayer burning;
    DatabaseReference mtotal;
    DatabaseReference muser;
    DatabaseReference monline;
    UserModel user;
    String nickname="jojo";
    int online;


    @Override
    protected void onPause() {
        super.onPause();
        burning.stop();
        delOnline();
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        burning.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nickname=getIntent().getStringExtra("nick");
        mtotal = FirebaseDatabase.getInstance().getReference("total");
        muser = FirebaseDatabase.getInstance().getReference();
        muser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    usercount = Integer.parseInt(String.valueOf(dataSnapshot.child("user").child
                            (nickname).child("burnCount").getValue()));

                    btnUser.setText("" + usercount + "$");
                }
                catch (Exception ex){
                    addUser(muser,new UserModel(nickname,0));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        monline=FirebaseDatabase.getInstance().getReference("online");
        monline.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                online= Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                txtOnline.setText(""+(online+1)+" Online");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mtotal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                btnDolar.setText("" + count + "$");
                enableClick();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        try {
            gifFromResource = new GifDrawable(getResources(), R.drawable.giphy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dolar = findViewById(R.id.imgdolar);
        txtOnline=findViewById(R.id.btnOnline);
        dolar2 = findViewById(R.id.imgdolar2);
        sources = findViewById(R.id.sources);
        btnDolar = findViewById(R.id.btnDolar);
        btnUser = findViewById(R.id.btnUser);

    }

    @Override
    protected void onResume() {
        super.onResume();
        burning = MediaPlayer.create(this, R.raw.burningshort);
        burning.start();
        burning.setLooping(true);
        addOnline();
    }

    public void addUser(DatabaseReference databaseReference, UserModel user) {
        databaseReference.child("user").child(user.getNickName()).setValue(user);
    }

    public void addTotal(int count) {
        mtotal = FirebaseDatabase.getInstance().getReference("total");
        mtotal.setValue(count);
    }
    public void addOnline() {
        monline = FirebaseDatabase.getInstance().getReference("online");
        monline.setValue((online+1));

    }
    public void delOnline() {

        monline = FirebaseDatabase.getInstance().getReference("online");
        monline.setValue(--online);
    }
    public void addTotalUser(int count) {
        muser = FirebaseDatabase.getInstance().getReference("user").child(nickname).child
                ("burnCount");
        muser.setValue(count);
    }


    private void enableClick() {
        sources.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                switch (action) {

                    case DragEvent.ACTION_DROP: {
                        count++;
                        usercount++;
                        addTotal(count);
                        addTotalUser(usercount);
                        btnUser.setBackground(getResources().getDrawable(R.drawable.circle2));
                        break;
                    }
                    case DragEvent.ACTION_DRAG_ENDED: {
                        btnUser.setBackground(getResources().getDrawable(R.drawable.circle2));
                        break;

                    }

                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:

                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        btnUser.setBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    default:
                        break;
                }

                return true;
            }
        });
        dolar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData veri = ClipData.newPlainText("", "");
                View.DragShadowBuilder golge = new View.DragShadowBuilder(dolar);
                v.startDrag(veri, golge, null, 0);
                return false;
            }
        });
        dolar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData veri = ClipData.newPlainText("", "");
                View.DragShadowBuilder golge = new View.DragShadowBuilder(dolar);
                v.startDrag(veri, golge, null, 0);
                return false;
            }
        });
    }

}
