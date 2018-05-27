package com.bucketsoft.user.wishi;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bucketsoft.user.wishi.dataClasses.WishObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;


public class AskWishActivity extends AppCompatActivity {

    /*

     private  String wisherUid;
    private String wisherDisplayName;
    private  String question;
    private ArrayList<AnswerObject> answers = new ArrayList<>();
    private  String category;
    private Integer wisherAge;
    private Date wishDate;

     */

    private static final String TAG = "AskWishActivity";

    //
    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText askWishEdit;
    Button sendWishButton;
    RadioGroup categoryRadioGrup;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_wish);


        askWishEdit = findViewById(R.id.ask_wish_edit_text);
        sendWishButton = findViewById(R.id.ask_your_wish_button);
        categoryRadioGrup = findViewById(R.id.categories_radio_grup);


        sendWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (categoryRadioGrup.getCheckedRadioButtonId() != -1 && askWishEdit.getText().toString().length() > 10) {

                    RadioButton selectedButton = findViewById(categoryRadioGrup.getCheckedRadioButtonId());
                    final String category = selectedButton.getText().toString();

                    final String question = askWishEdit.getText().toString();
                    final String wisherUid = user.getUid();
                    final Date wishDate = new Date(System.currentTimeMillis());


                    db.collection("users").whereEqualTo("uid", wisherUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            String wisherDisplayName = "default";
                            Integer wisherAge = 0;

                            if (task.isSuccessful()) {

                                wisherDisplayName = task.getResult().getDocuments().get(0).getString("displayName");
                                wisherAge = Integer.valueOf( task.getResult().getDocuments().get(0).get("age").toString() );
                            }

                            if (task.isComplete()) {

                                WishObject newWish = new WishObject(wisherUid, wisherDisplayName, question, category, wisherAge, wishDate);

                                db.collection("wishObject").add(newWish).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        Toast.makeText(AskWishActivity.this, "added : " + documentReference.getId(), Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Error adding document", e);
                                    }
                                });

                            }
                        }
                    });

                } else {

                    Toast.makeText(AskWishActivity.this, "Please fill the required fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
