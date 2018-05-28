package com.bucketsoft.user.wishi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bucketsoft.user.wishi.dataClasses.AnswerObject;

import java.util.ArrayList;

public class AnswerAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AnswerObject> mDataSource;

    public AnswerAdapter(Context context, ArrayList<AnswerObject> dataSource) {
        mContext = context;
        mDataSource = dataSource;

    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public AnswerObject getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_answer, parent, false);
        }



        TextView answerBodyText = convertView.findViewById(R.id.answer_body_list_item);
        TextView answererNameText = convertView.findViewById(R.id.answer_display_name_list_item);
        TextView answerDateText = convertView.findViewById(R.id.answer_date_list_item);

        // 1
        AnswerObject answerTemp =  getItem(position);

        Log.e("HADIBAKALIM", answerTemp.getAnswerBody());

// 2
        answerBodyText.setText(answerTemp.getAnswerBody());
        answererNameText.setText(answerTemp.getAnswereDisplayName());
        answerDateText.setText(answerTemp.getAnswerDate().toString());

        return convertView;
    }
}
