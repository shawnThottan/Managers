package com.example.android.managers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.managers.R.id.emergencyMessageListView;


public class EmergencyActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private ListView mMessageListView;
    private EmergenciesAdapter mEmergenciesAdapter;
    int flagdup=0;


    List<Emergencies> emergencies = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        mMessageListView = (ListView) findViewById(emergencyMessageListView);



        myRef = database.getReference("Emergencies");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                emergencies.clear();



                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Emergencies user = postSnapshot.getValue(Emergencies.class);
                    flagdup = 0;
                    for (int i = 0; i < emergencies.size(); i++)
                        if (emergencies.get(i).emergencyDetails.getUsername().equals(user.emergencyDetails.getUsername()))
                        {
                            flagdup = 1;
                            break;
                        }

                        int h=Integer.valueOf(user.emergencyDetails.getSi());
                        if(flagdup==0)
                            if(h==3) {
                                emergencies.add(user);
                            }

                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Emergencies user = postSnapshot.getValue(Emergencies.class);
                    Log.v("emer", "" + postSnapshot.getValue(Emergencies.class));
                    flagdup = 0;
                    for (int i = 0; i < emergencies.size(); i++)
                        if (emergencies.get(i).emergencyDetails.getUsername().equals(user.emergencyDetails.getUsername()))
                        {
                            flagdup = 1;
                            break;
                        }

                    int h=Integer.valueOf(user.emergencyDetails.getSi());
                    if(flagdup==0)
                        if(h==2) {
                            emergencies.add(user);
                        }

                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Emergencies user = postSnapshot.getValue(Emergencies.class);
                    Log.v("emer", "" + postSnapshot.getValue(Emergencies.class));
                    flagdup = 0;
                    for (int i = 0; i < emergencies.size(); i++)
                        if (emergencies.get(i).emergencyDetails.getUsername().equals(user.emergencyDetails.getUsername()))
                        {
                            flagdup = 1;
                            break;
                        }

                    int h=Integer.valueOf(user.emergencyDetails.getSi());
                    if(flagdup==0)
                        if(h==1) {
                            emergencies.add(user);
                        }

                }
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void setAdapter(){


        mEmergenciesAdapter = new EmergenciesAdapter(this, R.layout.item_emergency_message, emergencies);

        mMessageListView.setAdapter(mEmergenciesAdapter);

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EmergencyActivity.this,Locate.class);
                TextView username =(TextView)view.findViewById(R.id.EmergenciesUsername);
                intent.putExtra("user",username.getText().toString());
                startActivity(intent);

//                mEmergenciesAdapter.remove(mEmergenciesAdapter);

            }
        });
    }
}
