package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.lang.UScript;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cpm.model.Login;
import com.example.cpm.model.UserEngg;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.xml.transform.Result;


public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    List<UserEngg> userEnggsList = new ArrayList<>();

    private static final String TAG = "MainActivity";

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        progressBar = findViewById(R.id.progressBar);
//
//        //storeToFireStore();
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//
//        // Declare a CountDownLatch with a count of 1
//        final CountDownLatch latch = new CountDownLatch(1);
//
//        // Declare a variable to hold the loaded data
//        final DataSnapshot[] dataSnapshot = {null};
//
//        // Attach a listener to the database reference
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // Set the data snapshot and count down the latch
//                dataSnapshot[0] = snapshot;
//                for(DataSnapshot poDataSnapshot : snapshot.getChildren()){
//                    UserEngg livingPlace = poDataSnapshot.getValue(UserEngg.class);
//                    userEnggsList.add(livingPlace);
//                    Log.d(TAG, "onDataChange: "+livingPlace.toString());
//                }
//                latch.countDown();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle any errors that occur
//                Log.e(TAG, "Error loading data: " + error.getMessage());
//            }
//        });
//
//        try {
//            // Wait for the latch to be counted down
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Use the loaded data
//        if (dataSnapshot[0] != null) {
//            UserEngg user = dataSnapshot[0].getValue(UserEngg.class);
//            Log.d(TAG, "Loaded user: " + user.toString());
//        } else {
//            // Handle the case where the data is null
//            Log.e(TAG, "Data not found");
//        }
//

    }

    public void storeToFireStore(){

        CollectionReference reference = FirebaseFirestore.getInstance().collection("Login");
        Login login = new Login("temp","temp","temp");

        Log.d("", "before firestore fetching: ");
        reference.add(login).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
//
//
//    private class MyTask extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... params) {
//            final String[] temp = {null};
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("Users");
//                    Log.d("", "Started fetching: ");
//
//                    Task<DataSnapshot> task = myRef.get();
//                    try {
//                        Tasks.await(task);
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(task.isSuccessful()){
//                        DataSnapshot dataSnapshot = task.getResult();
//                        Log.d("", "storeToFireStore: "+dataSnapshot.toString());
//                        temp[0] = dataSnapshot.toString();
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // Update UI on main thread with the result
//                        }
//                    });
//                }
//            }).start();
//            return temp[0];
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // Update UI on main thread with the result
//        }
//    }

}