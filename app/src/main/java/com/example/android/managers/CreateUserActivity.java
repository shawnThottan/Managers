package com.example.android.managers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity {

    boolean flag = true;
    ArrayList<User> users = new ArrayList<>();

    EditText name ;
    EditText phoneno ;
    EditText username ;
    EditText password ;
    EditText specialization;

    User newuser = new User();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        name = (EditText) findViewById(R.id.name);
        phoneno = (EditText) findViewById(R.id.phoneno);
//        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        specialization = (EditText)findViewById(R.id.special);



        Bundle bundle = getIntent().getExtras();
        final String userinfo=bundle.getString("user");
        final Boolean edit=bundle.getBoolean("edit");
        final String username=bundle.getString("username");
        final String userpass=bundle.getString("userpass");
        final String userphno=Long.toString(bundle.getLong("userphno"));
        final String userspec=bundle.getString("userspec");

        if(edit){
            name.setText(username);
            phoneno.setText(userphno);
            password.setText(userpass);
            specialization.setText(userspec);
        }

        if(userinfo.equals("ambulance")){
            specialization.setVisibility(View.GONE);
             myRef = database.getReference("UserCategories/AmbulanceDrivers");


        }
        else if(userinfo.equals("doctor")){
             myRef = database.getReference("UserCategories/Doctors");

        }
        else{
            myRef = database.getReference("UserCategories/Otheruser");
        }


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button create = (Button)findViewById(R.id.create);
        if(edit)
            create.setText("Accept Changes");


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newuser.setPassword(password.getText().toString());
                newuser.setPhno(Long.parseLong(phoneno.getText().toString()));
                newuser.setUsername(name.getText().toString());
                newuser.setname(name.getText().toString());
                flag=true;
                if(userinfo.equals("doctor")||userinfo.equals("otheruser"))
                    newuser.setSpeciality(specialization.getText().toString());

                if(!(newuser.name.equals("") || newuser.password.equals("") || newuser.phno.equals("")))
                {

                    if((userinfo.equals("doctor")||userinfo.equals("otheruser"))&&newuser.speciality.equals(""))
                        Toast.makeText(getApplicationContext(),"Field(s) cannot be left blank",Toast.LENGTH_SHORT).show();
                    else{


                for(int i=0;i<users.size();i++){
                    if ((users.get(i).getUsername()).equals(newuser.getUsername())){
                        flag=false;
                        if(!edit || name.equals(newuser.getUsername()))
                        Toast.makeText(CreateUserActivity.this,"Username already exists",Toast.LENGTH_LONG).show();
                        else
                            flag=true;
                        break;
                    }
                }

                if(users.size()==0){
                    flag=true;
                    //Toast.makeText(CreateUserActivity.this,"Username already exists",Toast.LENGTH_LONG).show();

                }

                if(flag==true){
                    myRef.child(newuser.getUsername()).setValue(newuser);
                    if(!edit)
                        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Changes Accepted",Toast.LENGTH_SHORT).show();
                    Intent s= new Intent(CreateUserActivity.this, Managers_main_activity.class);
                    startActivity(s);

                }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Field(s) cannot be left blank"+ newuser.name,Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
