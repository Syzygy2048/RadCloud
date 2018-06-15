package io.github.syzygy2048.radcloud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoadingScreenActivity extends AppCompatActivity {
    /**
     * Delay on opening the new activity
     */
    private final int WAIT_TIME = 2500;

    /**
     * Waits until new activity is loaded already
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        System.out.println("LoadingScreenActivity  screen started");
        setContentView(R.layout.activity_loading_screen);
        findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

        int idfMethod = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idfMethod = extras.getInt("idfMethod");
            //The key argument here must match that used in the other activity
        }
        final int chosenIdfMethod = idfMethod;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                DocumentManager.getInstance().process(chosenIdfMethod);
	  /* Create an Intent that will start the ProfileData-Activity. */
                Intent mainIntent = new Intent(LoadingScreenActivity.this,RadCloudActivity.class);
                LoadingScreenActivity.this.startActivity(mainIntent);
                LoadingScreenActivity.this.finish();
            }
        }, WAIT_TIME);
    }
}
