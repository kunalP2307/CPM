package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cpm.model.Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowTaskDocumentActivity extends AppCompatActivity {

    ImageView imageViewShowDoc;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task_document);


        bindComponents();
        showDocument();
    }

    private void bindComponents(){
        imageViewShowDoc = findViewById(R.id.image_view_show_doc);
        progressBar = findViewById(R.id.progress_bar_view_doc);
    }

    public void showDocument(){
        String url = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url  = extras.getString("EXTRA_DOC_URL");
        }

        StorageReference mImageRef =
                FirebaseStorage.getInstance().getReference(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        mImageRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);

                        progressBar.setVisibility(View.INVISIBLE);
                        imageViewShowDoc.setVisibility(View.VISIBLE);
                        imageViewShowDoc.setMinimumHeight(300);
                        imageViewShowDoc.setMinimumWidth(300);
                        imageViewShowDoc.setImageBitmap(bm);
                        Log.d("TAG", "onSuccess: "+bm.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressBar.setVisibility(View.INVISIBLE);
                        imageViewShowDoc.setVisibility(View.VISIBLE);
                        Toast.makeText(ShowTaskDocumentActivity.this, "No Document Available for this Task!!", Toast.LENGTH_LONG).show();
                    }
                });

    }

}