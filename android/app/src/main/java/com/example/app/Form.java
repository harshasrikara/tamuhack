package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent result = getIntent();
        db = FirebaseFirestore.getInstance();

        if(result != null) {
            updateUI(result);
        }

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Form.this, "button clicked", Toast.LENGTH_SHORT);
                saveData();
            }
        });
    }

    private void updateUI(Intent intent) {
        String username = intent.getStringExtra("USERNAME");
        EditText name = findViewById(R.id.name);
        if(name != null) {
            name.setText(username);
        }

        String email = intent.getStringExtra("EMAIL");
        EditText mail = findViewById(R.id.eamil);
        if(email!=null) {
            mail.setText(email);
        }
    }

    private void saveData() {
        Map<String, String> map = new HashMap<>();
        EditText name = findViewById(R.id.name);
        map.put("username",name.getText().toString());
        EditText email = findViewById(R.id.eamil);
        map.put("email", email.getText().toString());
        EditText location = findViewById(R.id.location);
        map.put("location", location.getText().toString());
        EditText stuff = findViewById(R.id.stuff);
        map.put("stuff", stuff.getText().toString());

        db.collection("test")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Form.this, "Request successfully sent", Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Form.this, "Request failed", Toast.LENGTH_SHORT);
                    }
                });
    }
}
