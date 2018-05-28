package com.bucketsoft.user.wishi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bucketsoft.user.wishi.dataClasses.AnswerObject;
import com.bucketsoft.user.wishi.dataClasses.WishObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class WishObjectDetailActivity extends AppCompatActivity {

    WishObject theComingWishObject;
    ListView mListView;
    ArrayList<AnswerObject> answers = new ArrayList<>();

    TextView wishQuestionText;
    TextView wisherText;
    TextView categoryWish;
    TextView dateWish;

    EditText writtenAnswer;
    Button submitAnswerButton;

    AnswerAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_object_detail);


        wishQuestionText = findViewById(R.id.question_wish_object_detail);
        wisherText = findViewById(R.id.wisher_wish_object_detail);
        categoryWish = findViewById(R.id.category_wish_object_detail);
        dateWish = findViewById(R.id.date_wish_object_detail);

        writtenAnswer = findViewById(R.id.write_your_answer_edit_text);
        submitAnswerButton = findViewById(R.id.submit_answer_button);

        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (writtenAnswer.getText().toString().length() > 10) {

                    AnswerObject newAnswer = new AnswerObject();
                    newAnswer.setAnswerBody(writtenAnswer.getText().toString());
                    newAnswer.setAnswerDate(new Date(System.currentTimeMillis()));
                    newAnswer.setAnswereDisplayName(user.getDisplayName());
                    newAnswer.setAnswererUid(user.getUid());

                    theComingWishObject.addAnswer(newAnswer);
                    answers.add(newAnswer);

                    db.collection("wishObject").document(theComingWishObject.getWishId()).set(theComingWishObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if ( task.isComplete() ) {
                               adapter.notifyDataSetChanged();
                           }

                        }
                    });





                } else {
                    Toast.makeText(WishObjectDetailActivity.this, "Pls enter something longer than that", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Intent theComingIntent = getIntent();
        theComingWishObject = (WishObject) theComingIntent.getSerializableExtra("WISH_OBJECT");
        answers = (ArrayList<AnswerObject>) theComingIntent.getSerializableExtra("ANSWERS_TO_WISH");


        wishQuestionText.setText(theComingWishObject.getQuestion());
        wisherText.setText(theComingWishObject.getWisherDisplayName());
        categoryWish.setText(theComingWishObject.getCategory());
        dateWish.setText(theComingWishObject.getWishDate().toString());


        mListView = findViewById(R.id.answer_list_view);

        Log.e("LETSEE", answers.toString());

        adapter = new AnswerAdapter(this, answers);
        mListView.setAdapter(adapter);
    }
}
