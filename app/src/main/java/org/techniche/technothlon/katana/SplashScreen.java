package org.techniche.technothlon.katana;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    private boolean isBackPressed = false;
    private int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);
        ImageView head = (ImageView) findViewById(R.id.logo_head),
                text = (ImageView) findViewById(R.id.logo_text),
                tag = (ImageView) findViewById(R.id.logo_tag);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_fly_in_top);
        head.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.anim_fly_in_left);
        text.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.anim_fly_in_right);
        tag.startAnimation(anim);
    }

    @Override
    protected void onStart() {
        super.onStart();
        (new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                SplashScreen.this.proceed();
            }
        }).execute();
    }

    private void proceed() {
        if (!isBackPressed) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, id);
            this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            isBackPressed = true;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.finish();
    }
}
