package com.bucketsoft.user.wishi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bucketsoft.user.wishi.dataClasses.AnswerObject;
import com.bucketsoft.user.wishi.dataClasses.WishObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WishItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String categorical = "ALL";
    private OnListFragmentInteractionListener mListener;
    private List<WishObject> wishObjectsFromFB = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "WishItemFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WishItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WishItemFragment newInstance(int columnCount, String categorical) {
        WishItemFragment fragment = new WishItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString("CATEGORY_KEY", categorical);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            categorical = getArguments().getString("CATEGORY_KEY");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wishitem_list, container, false);
        final RecyclerView mRecyclerView = view.findViewById(R.id.list);
        LinearLayout catLinearLayout = view.findViewById(R.id.category_selector_linear_layout_in_fragment);

        if (categorical.equals("ALL")) {

            db.collection("wishObject").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());


                            WishObject trivialWishForAnswer = document.toObject(WishObject.class);

                            ArrayList<AnswerObject> tempAnswers = trivialWishForAnswer.getAnswers();
                            if (tempAnswers.size() != 0) {
                                Log.e("ANSWERS", tempAnswers.get(0).getAnswerBody());
                            }


                            Log.e("ANSWERS", tempAnswers.toString());
                            String tempCategory = (String) document.get("category");
                            String tempQuestion = (String) document.get("question");
                            Date tempDate = (Date) document.get("wishDate");
                            String tempId = document.getId();
                            Integer tempAge = Integer.valueOf(document.get("wisherAge").toString());
                            String tempDisplayName = (String) document.get("wisherDisplayName");
                            String tempWisherUid = (String) document.get("wisherUid");

                            WishObject tempWish = new WishObject(tempWisherUid, tempDisplayName, tempQuestion, tempCategory, tempAge, tempDate);
                            tempWish.setAnswers(tempAnswers);
                            tempWish.setWishId(tempId);


                            wishObjectsFromFB.add(tempWish);


                        }

                        if (task.isComplete()) {

                            if (mRecyclerView instanceof RecyclerView) {
                                Context context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) mRecyclerView;
                                if (mColumnCount <= 1) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                } else {
                                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                }
                                recyclerView.setAdapter(new WishItemRecyclerViewAdapter(wishObjectsFromFB, mListener));
                            }

                        }


                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }

                }
            });

        } else {
            mRecyclerView.setVisibility(View.GONE);
            catLinearLayout.setVisibility(View.VISIBLE);

            Button travelButton = view.findViewById(R.id.category_button_travel);
            travelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateRecyclerView("travel");
                }
            });

            Button musicButton = view.findViewById(R.id.category_button_music);
            musicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateRecyclerView("music");
                }
            });

            Button careerButton = view.findViewById(R.id.category_button_career);
            careerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateRecyclerView("career");
                }
            });

            Button sportsButton = view.findViewById(R.id.category_button_sports);
            sportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateRecyclerView("sports");
                }
            });

            Button relationshipButton = view.findViewById(R.id.category_button_relationship);
            relationshipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateRecyclerView("relationship");
                }
            });

            Button generalButton = view.findViewById(R.id.category_button_general);
            generalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateRecyclerView("general");
                }
            });
                /*
            int i = view.getId();
            if (i == R.id.category_button_travel) {

            } else if (i == R.id.category_button_relationship) {
                populateRecyclerView("relationship");
            } else if (i == R.id.category_button_career) {
                populateRecyclerView("career");
            } else if (i == R.id.category_button_sports) {
                populateRecyclerView("sports");
            } else if (i == R.id.category_button_music) {
                populateRecyclerView("music");
                Log.e(TAG, "onClick: TIKTITKTIKTTIKTITKT" );
            } else if (i == R.id.category_button_general) {
                populateRecyclerView("general");
            }
                */


        }

        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void populateRecyclerView(final String catString) {

        db.collection("wishObject").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    wishObjectsFromFB.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {


                        WishObject trivialWishForAnswer = document.toObject(WishObject.class);

                        ArrayList<AnswerObject> tempAnswers = trivialWishForAnswer.getAnswers();
                        String tempCategory = (String) document.get("category");
                        Log.e("CAT", tempCategory);


                        String tempQuestion = (String) document.get("question");
                        Date tempDate = (Date) document.get("wishDate");
                        String tempId = document.getId();
                        Integer tempAge = Integer.valueOf(document.get("wisherAge").toString());
                        String tempDisplayName = (String) document.get("wisherDisplayName");
                        String tempWisherUid = (String) document.get("wisherUid");

                        WishObject tempWish = new WishObject(tempWisherUid, tempDisplayName, tempQuestion, tempCategory, tempAge, tempDate);
                        tempWish.setAnswers(tempAnswers);
                        tempWish.setWishId(tempId);


                        if (tempCategory.equals(catString)){
                            wishObjectsFromFB.add(tempWish);
                        }


                    }

                    if (task.isComplete()) {

                        getView().findViewById(R.id.category_selector_linear_layout_in_fragment).setVisibility(View.GONE);
                        getView().findViewById(R.id.list).setVisibility(View.VISIBLE);


                        Context context = getView().getContext();
                        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.list);
                        if (mColumnCount <= 1) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }
                        recyclerView.setAdapter(new WishItemRecyclerViewAdapter(wishObjectsFromFB, mListener));


                    }


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(WishObject item);
    }
}
