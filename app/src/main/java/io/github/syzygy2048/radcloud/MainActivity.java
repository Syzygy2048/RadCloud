package io.github.syzygy2048.radcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DocumentManager dm = DocumentManager.getInstance();

        dm.readDocument(this, R.raw.test, "first document");
        dm.readDocument(this, R.raw.test2, "second document");
        dm.process();

        TextView text = findViewById(R.id.text);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RadCloudActivity.class));
            }
        });
    }
}
