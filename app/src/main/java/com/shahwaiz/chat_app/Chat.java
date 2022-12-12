package com.shahwaiz.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    TextView username;
    EditText Message;
    ImageButton back_btn,send_btn;
    CircleImageView dp;
    String user_mob="";
    String Chat_key="";
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://cp-firebase-8de7e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        username=findViewById(R.id.username);
        Message=findViewById(R.id.message);
        back_btn=findViewById(R.id.back);
        send_btn=findViewById(R.id.send_btn);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String dpURL=intent.getStringExtra("profile_pic");
        String mob=intent.getStringExtra("mobile");
        Chat_key=intent.getStringExtra("chat_key");


        if(Chat_key.isEmpty())
        {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Chat_key="1";
                    if(snapshot.hasChild("chat"))
                    {
                        Chat_key=String.valueOf(snapshot.child("chat").getChildrenCount()+1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        MemoryData memoryData=new MemoryData();
        user_mob=memoryData.getMobile(Chat.this);
        username.setText(name);
        if(dpURL!="")
        {
            //Picasso.get().load(dpURL).into(dp);
        }



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time=String.valueOf(System.currentTimeMillis()).substring(0,10);
                String msg_text=Message.getText().toString().trim();
                MemoryData.saveLastMsg(time,Chat.this,Chat_key);
                databaseReference.child("chat").child(Chat_key).child("user_1").setValue(user_mob);
                databaseReference.child("chat").child(Chat_key).child("user_2").setValue(mob);
                databaseReference.child("chat").child(Chat_key).child("messages").child(time).child("msg").setValue(msg_text);
                databaseReference.child("chat").child(Chat_key).child("messages").child(time).child("mobile").setValue(user_mob);

                Message.setText("");
            }
        });


    }
}