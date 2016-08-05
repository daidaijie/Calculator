package com.example.daidaijie.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;

import org.wltea.expression.ExpressionEvaluator;

import java.util.Objects;

public class CalculatorActivity extends AppCompatActivity {

    private GridView mGridButtons = null;
    private EditText myEditinput = null;

    private BaseAdapter mAdapter = null;

    private String mPreStr = "";
    private String mLastStr = "";

    private boolean mIsExcuteNow = false;
    private final String newLine = "<br\\>";

    private final String[] mTextBtns = new String[]{
            "Back", "(", ")", "CE",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "+",
            "0",".","=","-",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mGridButtons = (GridView) findViewById(R.id.grid_buttons);
        myEditinput = (EditText) findViewById(R.id.input_edit);

        mAdapter = new CalculatorAdapter(this, mTextBtns);
        mGridButtons.setAdapter(mAdapter);

        myEditinput.setKeyListener(null);

        OnButtonItemClickListener listener = new OnButtonItemClickListener();
        mGridButtons.setOnItemClickListener(listener);

    }

    private void setText() {
        final String[] tags = new String[]{"<font color='#858585'>", "<font color='#CD2626'>", "</font> "};
        StringBuilder builder = new StringBuilder();
        builder.append(tags[0]);
        builder.append(mPreStr);
        builder.append(tags[2]);
        builder.append(tags[1]);
        builder.append(mLastStr);
        builder.append(tags[2]);

        myEditinput.setText(Html.fromHtml(builder.toString()));
        myEditinput.requestFocus();
    }

    private void execteExpression() {
        Object result = null;
        try {
            result = ExpressionEvaluator.evaluate(mLastStr);

        } catch (Exception e) {
            myEditinput.setError(e.getMessage());
            mIsExcuteNow = false;
            return;
        }

        mIsExcuteNow = true;
        mLastStr += "=" + result;
        myEditinput.setError(null);
        setText();
    }

    private class OnButtonItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String text = (String) parent.getAdapter().getItem(position);
            if (text.equals("=")) {
                execteExpression();
            } else if (text.equals("Back")) {
                if (mLastStr.length() == 0) {
                    if (mPreStr.length() != 0) {
                        mPreStr = mPreStr.substring(0, mPreStr.length() - newLine.length());
                        mIsExcuteNow = true;
                        int index = mPreStr.lastIndexOf(newLine);
                        if (index == -1) {
                            mLastStr = mPreStr;
                            mPreStr = "";
                        } else {
                            mLastStr = mPreStr.substring(index + newLine.length(), mPreStr.length());
                            mPreStr = mPreStr.substring(0, index);
                        }
                    }
                } else {
                    mLastStr = mLastStr.substring(0, mLastStr.length() - 1);
                }
                setText();
            } else if (text.equals("CE")) {
                mPreStr = "";
                mLastStr = "";
                mIsExcuteNow = false;
                myEditinput.setText("");
            } else {
                if (mIsExcuteNow) {
                    mPreStr += mLastStr + newLine;
                    mIsExcuteNow=false;
                    mLastStr=text;
                }
                else {
                    mLastStr+=text;
                }
                setText();
            }
        }
    }
}
