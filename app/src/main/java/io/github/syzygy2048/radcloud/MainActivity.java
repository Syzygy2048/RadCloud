package io.github.syzygy2048.radcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DocumentManager dm = DocumentManager.getInstance();
        long time = System.currentTimeMillis();

//        dm.readDocument(this, R.raw.test, "Chapter 1");
//        dm.readDocument(this, R.raw.test2, "Chapter 2");
//        dm.readDocument(this, R.raw.test3, "Chapter 3");
//        dm.readDocument(this, R.raw.test4, "Chapter 4");
//        dm.readDocument(this, R.raw.test5, "Chapter 5");

        dm.readDocument(this, R.raw.wdqk1, "Chapter 1");
        dm.readDocument(this, R.raw.wdqk2, "Chapter 2");
        dm.readDocument(this, R.raw.wdqk3, "Chapter 3");
        dm.readDocument(this, R.raw.wdqk4, "Chapter 4");
        dm.readDocument(this, R.raw.wdqk5, "Chapter 5");

        long time2 = System.currentTimeMillis();
        Log.d("Performance", "loading documents took " + ((time2 - time)) + " ms");

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dm.process();
                startActivity(new Intent(MainActivity.this, RadCloudActivity.class));
                Log.d("RadCloud","started");
            }
        });
    }
}
