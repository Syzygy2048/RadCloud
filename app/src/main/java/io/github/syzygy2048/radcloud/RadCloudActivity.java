package io.github.syzygy2048.radcloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RadCloudActivity extends AppCompatActivity {
    private ImageView radCloudView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rad_cloud);


        radCloudView = (ImageView) findViewById(R.id.radCloud);
    }
}
