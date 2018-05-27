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

import com.bucketsoft.user.wishi.dataClasses.AnswerObject;
import com.bucketsoft.user.wishi.dataClasses.WishObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static WishItemFragment newInstance(int columnCount) {
        WishItemFragment fragment = new WishItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final   View view = inflater.inflate(R.layout.fragment_wishitem_list, container, false);


        db.collection("wishObject").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        ArrayList<AnswerObject> tempAnswers = (ArrayList<AnswerObject>) document.get("answers");
                        String tempCategory = (String) document.get("category");
                        String tempQuestion = (String) document.get("question");
                        Date tempDate = (Date) document.get("wishDate");
                        String tempId = document.getId();
                        Integer tempAge =  Integer.valueOf(document.get("wisherAge").toString());
                        String tempDisplayName = (String) document.get("wisherDisplayName");
                        String tempWisherUid = (String) document.get("wisherUid");

                        WishObject tempWish = new WishObject(tempWisherUid, tempDisplayName, tempQuestion, tempCategory, tempAge, tempDate);
                        tempWish.setAnswers(tempAnswers);
                        tempWish.setWishId(tempId);


                        wishObjectsFromFB.add(tempWish);


                    }

                    if (task.isComplete()) {

                        if (view instanceof RecyclerView) {
                            Context context = view.getContext();
                            RecyclerView recyclerView = (RecyclerView) view;
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
