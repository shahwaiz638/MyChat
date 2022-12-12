package com.shahwaiz.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Authentication extends AppCompatActivity {

    MemoryData memoryData=new MemoryData();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CP firebase");
    TextView name,email,mobile;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        name=findViewById(R.id.username);
        email=findViewById(R.id.Email);
        mobile=findViewById(R.id.mobile);
        register=findViewById(R.id.register);

//        ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

//        LinearLayout layout = findViewById(R.id.display_authentication);
//        ProgressBar progressBar = new ProgressBar(Authentication.this, null, android.R.attr.progressBarStyleLarge);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,100);
//        layout.addView(progressBar,params);
//        progressBar.setVisibility(View.VISIBLE);
        String getMobile=memoryData.getMobile(Authentication.this);
        if(getMobile!=null)
        {
            if(!getMobile.isEmpty())
            {
                Intent intent=new Intent(Authentication.this,MainActivity.class);
                intent.putExtra("mobile",memoryData.getMobile(Authentication.this));
                intent.putExtra("name",memoryData.getName(Authentication.this));
                intent.putExtra("email",memoryData.getEmail(Authentication.this));
                setResult(RESULT_OK,intent);
                finish();
            }
        }


        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nametxt=name.getText().toString().trim();
                String emailtxt=email.getText().toString().trim();
                String mobilttxt=mobile.getText().toString().trim();

                if(nametxt.isEmpty() || emailtxt.isEmpty() || mobilttxt.isEmpty())
                {
                    Toast.makeText(Authentication.this, "Field is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            progressBar.setVisibility(View.GONE);
                            //progressDialog.dismiss();
                            if(snapshot.child("users").hasChild(nametxt))
                            {
                                Toast.makeText(Authentication.this, "this number already exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                myRef.child("users").child(mobilttxt).child("email").setValue(emailtxt);
                                myRef.child("users").child(mobilttxt).child("name").setValue(nametxt);
                                myRef.child("users").child(mobilttxt).child("profile_pic").setValue("");
                                MemoryData.saveMobile(mobilttxt,Authentication.this);
                                MemoryData.saveName(nametxt,Authentication.this);
                                MemoryData.saveEmail(emailtxt,Authentication.this);


                                Intent intent=new Intent(Authentication.this,MainActivity.class);
                                intent.putExtra("mobile",mobilttxt);
                                intent.putExtra("name",nametxt);
                                intent.putExtra("email",emailtxt);

                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //progressBar.setVisibility(View.GONE);
                            //progressDialog.dismiss();
                            finish();

                        }
                    });
                }

            }
        });
    }
}