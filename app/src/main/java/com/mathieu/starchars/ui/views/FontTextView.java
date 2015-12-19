package com.mathieu.starchars.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;

import com.mathieu.starchars.R;

import java.io.IOException;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class FontTextView extends TextView {

    private static LruCache<String, Typeface> TYPEFACE_CACHE = new LruCache<String, Typeface>(12);


    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        String fontName = styledAttrs.getString(R.styleable.FontTextView_font);
        styledAttrs.recycle();
        setTypeFace(fontName);
    }

    public void setTypeFace(String fontName) {
        if (fontName != null) {
            try {
                Typeface typeface = TYPEFACE_CACHE.get(fontName);
                if (typeface == null) {
                    typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    TYPEFACE_CACHE.put(fontName, typeface);
                }
                setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
                try {
                    Log.e("FONT", "test :" + getContext().getAssets().list("").length);
                    String[] assets = getContext().getAssets().list("");
                    for (int i = 0; i < assets.length; i++) {
                        Log.e("font", assets[i]);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}