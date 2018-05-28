package com.bucketsoft.user.wishi;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bucketsoft.user.wishi.dataClasses.WisherUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserFormActivity extends AppCompatActivity {


    private static final String TAG = "UserFormActivity";

    //
    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    EditText ageET;
    EditText bioET;
    EditText displayNameET;
    EditText photoUrlET;
    Button submitButton;

    WisherUser userToBe = new WisherUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);


        ageET = findViewById(R.id.ask_age_form_user_edit_text);
        bioET = findViewById(R.id.ask_bio_form_user_edit_text);
        displayNameET = findViewById(R.id.ask_display_name_form_user_edit_text);
        photoUrlET = findViewById(R.id.ask_photo_url_form_user_edit_text);
        submitButton = findViewById(R.id.save_user_to_db_form_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if ( checkRequiredFields() ) {

                   Integer age = Integer.valueOf(ageET.getText().toString());
                   String bio = bioET.getText().toString();
                   String dpName = displayNameET.getText().toString();
                   String photo = photoUrlET.getText().toString();

                   userToBe.setAge(age);
                   userToBe.setBio(bio);
                   userToBe.setDisplayName(dpName);
                   userToBe.setPhotoURL(photo);


                   db.collection("users").document(userToBe.getUid()).set(userToBe);

                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                           .setDisplayName(dpName)
                           .build();

                   user.updateProfile(profileUpdates)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {
                                       Log.d(TAG, "User profile updated.");
                                   }
                               }
                           });


                   Intent intent = new Intent(UserFormActivity.this, MainActivity.class);
                   startActivity(intent);

                   finish();


               } else {

                   Toast.makeText(UserFormActivity.this, "PLS make sure that everything is filled except photoURL that is optional ", Toast.LENGTH_LONG).show();

               }
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

            String email = user.getEmail();
            String uid = user.getUid();

            userToBe.setEmail(email);
            userToBe.setUid(uid);
            userToBe.setInfoCheck(true);
        } else {
            // No user is signed in
        }


    }

    private Boolean checkRequiredFields() {

       return ( ageET.getText().toString().length() >= 1 && bioET.getText().toString().length() >= 1 && displayNameET.getText().toString().length() >= 1 );

    }
}
