package com.example.daidaijie.calculator;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by daidaijie on 2015/10/10.
 */
public class CalculatorAdapter extends BaseAdapter{
    private Context mContext;
    private String[] mStrs = null;

    public CalculatorAdapter(Context mContext, String[] mStrs) {
        this.mContext = mContext;
        this.mStrs = mStrs;
    }

    @Override
    public int getCount() {
        return mStrs.length;
    }

    @Override
    public Object getItem(int position) {
        return mStrs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext,R.layout.item_button,null);
        TextView textView  = (TextView) view.findViewById(R.id.txt_button);

        String str = mStrs[position];
        textView.setText(str);

        if(str.equals("Back")) {
            textView.setBackgroundResource(R.drawable.selector_button_backspace);
            textView.setTextColor(Color.WHITE);
        }
        else if(str.equals("CE")){
            textView.setBackgroundResource(R.drawable.selector_button_clear);
            textView.setTextColor(Color.WHITE);
        }
        return view;
    }
}
