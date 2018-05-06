package com.bucketsoft.user.wishi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class WisherProfileFragment extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wisher_profile, container, false);
    }

}
