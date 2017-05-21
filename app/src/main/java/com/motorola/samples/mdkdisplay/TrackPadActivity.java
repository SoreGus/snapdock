package com.motorola.samples.mdkdisplay;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;


public class TrackPadActivity extends Presentation {
    public TrackPadActivity(Context outerContext, Display display) {
        super(outerContext, display);
    }

    private String tag = "EVENTO DE TOQUE";
    private String tagMovimentoX = "COORDENADA X";
    private String tagMovimentoY = "COORDENADA Y";
    private String tagCliqueX = "CLIQUE X";
    private String tagCliqueY = "CLIQUE Y";
    private String tagSoltarX = "SOLTAR X";
    private String tagSoltarY = "SOLTAR Y";
    private SharedPreferences coordenadas;
    private SharedPreferences.Editor editor;

    private int aux = 0;

    private float[] coordenadasTrackPad;

    private MovimentoTrackPad TrackPadSimulado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_pad);

        TrackPadSimulado = new MovimentoTrackPad(144000, 256000);
        coordenadas = this.getContext().getSharedPreferences("COORDENADAS", Context.MODE_PRIVATE); //1

        final View decorView = getWindow().getDecorView();

        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                            decorView.setSystemUiVisibility(uiOptions);
                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                        }
                    }
                });
    }


    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);


        switch(action) {
            case MotionEvent.ACTION_DOWN:

                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                //Log.d(DEBUG_TAG, "X velocity: " +              VelocityTrackerCompat.getXVelocity(mVelocityTracker,                   pointerId));
                //Log.d(DEBUG_TAG, "Y velocity: " +                    VelocityTrackerCompat.getYVelocity(mVelocityTracker,                             pointerId));
                TrackPadSimulado.movimentarEixoX(VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
                TrackPadSimulado.movimentarEixoY(VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId));
                //Log.d(tagMovimentoX, "X: "+TrackPadSimulado.obterCoordenadas()[0]+" "+"Y: "+TrackPadSimulado.obterCoordenadas()[1]);


                editor = coordenadas.edit(); //2
                editor.putFloat("X", TrackPadSimulado.obterCoordenadas()[0]); //3
                editor.putFloat("Y", TrackPadSimulado.obterCoordenadas()[1]); //3
                editor.commit(); //4
                break;
            case MotionEvent.ACTION_UP:
                editor = coordenadas.edit(); //2
                editor.putFloat("C", aux++); //3
                editor.commit(); //4
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
                //mVelocityTracker.recycle();
                break;
        }
        return true;
    }

    public void setarWebView(){

    }

}