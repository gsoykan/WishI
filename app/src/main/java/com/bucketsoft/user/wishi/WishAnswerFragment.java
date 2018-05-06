package com.bucketsoft.user.wishi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishAnswerFragment extends Fragment {


    public WishAnswerFragment() {
        // Required empty public constructor
    }

    public static WishAnswerFragment newInstance() {
        WishAnswerFragment fragment = new WishAnswerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wish_answer, container, false);
        TextView textView = rootView.findViewById(R.id.wish_answer_question_text_view);
        textView.setText("This is going to be a question, basically somebody is looking for help!");
        return rootView;
    }

}
