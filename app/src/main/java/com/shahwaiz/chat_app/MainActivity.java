package com.shahwaiz.chat_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    private List<MessageList> conversation=new ArrayList<>();
    private CircleImageView profilepic;
    private String mobile,name,email;
    private RecyclerView msgsRV;
    private String lastMsg="",chatKey="";
    private boolean dataChange=false;
    private int unreadMessages=0;
    private MessagesAdapter messagesAdapter;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://cp-firebase-8de7e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        Intent ProfileLauncher = new Intent(this, Authentication.class);
        startActivityForResult(ProfileLauncher,1);




        profilepic=findViewById(R.id.user_pic);
        msgsRV=findViewById(R.id.msgsRV);
        msgsRV.setHasFixedSize(true);
        msgsRV.setLayoutManager(new LinearLayoutManager(this));

        messagesAdapter=new MessagesAdapter(conversation,MainActivity.this);

        msgsRV.setAdapter(messagesAdapter);

        //get Profile pic from firebase





    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String dpURL=snapshot.child("CP firebase").child("users").child(mobile).child("profile_pic").getValue(String.class);
                if(dpURL!=null)
                {
                    if(!dpURL.isEmpty())
                    {
                        Picasso.get().load(dpURL).into(profilepic);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conversation.clear();
                unreadMessages=0;
                lastMsg="";
                chatKey="";
                for(DataSnapshot dataSnapshot: snapshot.child("CP firebase").child("users").getChildren())
                {
                    dataChange=false;
                    final String mobile_friend=dataSnapshot.getKey();
                    if(!mobile_friend.equals(mobile))
                    {

                        final String name_friend=dataSnapshot.child("name").getValue(String.class);
                        final String email_friend=dataSnapshot.child("email").getValue(String.class);

                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int TotalChats=(int) snapshot.getChildrenCount();
                                if(TotalChats>0)
                                {
                                    for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                                    {
                                        String Key=dataSnapshot1.getKey();
                                        chatKey=Key;

                                        if(dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") &&dataSnapshot1.hasChild("messages"))
                                        {
                                            String user1=dataSnapshot1.child("user_1").getValue(String.class);
                                            String user2=dataSnapshot1.child("user_2").getValue(String.class);

                                            if(user1.equals(mobile) && user2.equals(mobile_friend) || user1.equals(mobile_friend) && user2.equals(mobile))
                                            {
                                                for(DataSnapshot dataSnapshot2: dataSnapshot1.child("messages").getChildren())
                                                {
                                                    long MessageKey=Long.parseLong(dataSnapshot2.getKey());

                                                    long LastReadMsgs=Long.parseLong(MemoryData.getLastMsg(Key,MainActivity.this));

                                                    lastMsg=dataSnapshot2.child("msg").getValue(String.class);
                                                    if(MessageKey>LastReadMsgs)
                                                    {
                                                        unreadMessages++;
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                                if(!dataChange)
                                {
                                    dataChange=true;
                                    MessageList msg=new MessageList(name_friend,email_friend,mobile_friend,"",lastMsg,unreadMessages,chatKey);
                                    conversation.add(msg);
                                    messagesAdapter.updateData(conversation);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
            if(resultCode==RESULT_OK)
            {
                mobile=data.getStringExtra("mobile");
                name=data.getStringExtra("name");
                email=data.getStringExtra("email");
            }
    }



}