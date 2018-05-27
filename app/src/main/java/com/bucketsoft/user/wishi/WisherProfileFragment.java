package com.bucketsoft.user.wishi;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class WisherProfileFragment extends Fragment {

    private final String TAG = "WisherProfileFragment";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public WisherProfileFragment() {
        // Required empty public constructor
    }

    public static WisherProfileFragment newInstance() {
        WisherProfileFragment fragment = new WisherProfileFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wisher_profile, container, false);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        final TextView nameText = v.findViewById(R.id.wisher_name_text_view);
        final TextView bioText = v.findViewById(R.id.wisher_bio_text_view);

        final FirebaseUser user = mAuth.getCurrentUser();


        db.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        if( user.getUid().equals(document.getString("uid"))   ){
                            Log.d(TAG, document.getString("uid") + " => " + user.getUid());

                         String dpName =    document.getString("displayName");
                          String bio =   document.getString("bio");

                          nameText.setText(dpName);
                          bioText.setText(bio);

                        }


                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


        // Inflate the layout for this fragment
        return v ;
    }

}
