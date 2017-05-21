/**
 * Copyright (c) 2016 Motorola Mobility, LLC.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.motorola.samples.mdkdisplay;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * A class to represent the clock activity view.
 */
public class WebviewActivity extends Presentation implements View.OnTouchListener {
    public WebviewActivity(Context outerContext, Display display) {

        super(outerContext, display);
    }

    private WebviewActivity webviewActivity;

    private WebView webView;
    private WebViewClient webViewClient;
    private ImageView imageView;
    private SharedPreferences coordenadas;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private ViewGroup.MarginLayoutParams imageviewmargins;

    private TrackPadActivity TrackPadValores;

    // Obtain MotionEvent object
    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis() + 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_webview);
        coordenadas = this.getContext().getSharedPreferences("COORDENADAS", Context.MODE_PRIVATE);



        webView = (WebView) this.findViewById(R.id.webView1);
        //webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        //webView.setOnTouchListener(this);
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         Log.d("URL 1 ", url);
                                         view.loadUrl(url);
                                         return true;
                                     }


                                 });
        webView.loadUrl("http://www.uol.com.br");

        imageView = (ImageView) this.findViewById(R.id.imageView1);
        imageviewmargins = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                //Log.d("PRESENTATION", String.valueOf(prefs.getFloat(key, 0)));
                //Log.d("PRESENTATION MARGIN", String.valueOf(imageviewmargins.leftMargin));
                //Log.d("PRESENTATION", key);
                if(key == "Y"){
                    imageviewmargins.leftMargin = (int) ((imageviewmargins.leftMargin + prefs.getFloat(key, 0))/1.5);
                    imageView.setLayoutParams(imageviewmargins);
                }
                if(key == "X"){
                    imageviewmargins.topMargin = (int) ((imageviewmargins.topMargin + prefs.getFloat(key, 0))/-1.5);
                    imageView.setLayoutParams(imageviewmargins);
                }
                if(key == "C"){
                    // List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                    int metaState = 0;
                    MotionEvent motionEvent = MotionEvent.obtain(
                            downTime,
                            eventTime,
                            MotionEvent.ACTION_UP,
                            imageviewmargins.leftMargin,
                            imageviewmargins.topMargin,
                            metaState
                    );




                   // Log.d("PRESENTATION CLICK", "Clicou!!!" + imageviewmargins.leftMargin + " " + imageviewmargins.topMargin);

// Dispatch touch event to view
                    //webView.setX(imageviewmargins.leftMargin);
                    //webView.setY(imageviewmargins.topMargin);
                    //webView.performClick();



                    webView.dispatchTouchEvent(motionEvent);
                }
            }
        };

        coordenadas.registerOnSharedPreferenceChangeListener(listener);

    }


    public void setarTrackPadReferencia(TrackPadActivity TrackPadValores){
        this.TrackPadValores = TrackPadValores;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("ON TOUCH WEBVIEW", String.valueOf(event.getX()));
       v.performContextClick(event.getX(), event.getY());
        return false;
    }
}
