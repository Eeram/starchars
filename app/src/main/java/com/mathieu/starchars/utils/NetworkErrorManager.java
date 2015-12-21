package com.mathieu.starchars.utils;

import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mathieu.starchars.R;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       21/12/2015
 */

public class NetworkErrorManager implements View.OnClickListener {

    public static final int MODE_FULL = 0;
    public static final int MODE_SNACK = 1;
    public static final int MODE_TOAST = 2;

    private ViewGroup container;

    private OnNetworkErrorClickListener listener;
    private boolean isNetworkErrorDisplayed = false;

    public void displayNetworkError(ViewGroup container, int mode, OnNetworkErrorClickListener listener) {
        switch (mode) {
            case MODE_FULL:
                if (!isNetworkErrorDisplayed) {
                    this.container = container;
                    this.listener = listener;
                    View networkErrorView = LayoutInflater.from(container.getContext()).inflate(R.layout.view_network_error, container, false);
                    networkErrorView.setOnClickListener(this);
                    container.addView(networkErrorView);
                    isNetworkErrorDisplayed = true;
                }
                break;
            case MODE_SNACK:
                this.listener = listener;
                Snackbar.make(container, R.string.error_network_short, Snackbar.LENGTH_LONG)
                        .setAction(R.string.try_again, this)
                        .show();
                break;
            case MODE_TOAST:
                Toast.makeText(container.getContext(), R.string.error_network_short, Toast.LENGTH_LONG).show();

        }
    }

    public void hideNetworkError(ViewGroup container) {
        if (isNetworkErrorDisplayed) {
            container.removeView(container.findViewById(R.id.network_error_container));
            isNetworkErrorDisplayed = false;
        }
    }

    @Override
    public void onClick(View v) {
        listener.onNetworkErrorClickListener();
        hideNetworkError(container);
    }

    public interface OnNetworkErrorClickListener {
        void onNetworkErrorClickListener();
    }
}
