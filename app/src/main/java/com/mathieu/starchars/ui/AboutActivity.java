package com.mathieu.starchars.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mathieu.starchars.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_lets_go)
    protected void onButtonLetsGoClick() {
        finish();
    }
}
