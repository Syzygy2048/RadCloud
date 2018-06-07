package io.github.syzygy2048.radcloud;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    Button openResButton, openResButton2;
    TextView resTextPath, resTextPath2;
    final DocumentManager dm = DocumentManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //++++++++++++++++++++++++++++
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        openResButton = (Button) findViewById(R.id.OpenResButton);
        openResButton2 = (Button) findViewById(R.id.OpenResButton2);
        resTextPath = (TextView) findViewById(R.id.ResPath);
        resTextPath2 = (TextView) findViewById(R.id.ResPath2);

        openResButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1000)
                        //.withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        openResButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(2000)
                        //.withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        //++++++++++++++++++++++++++++
        //TODO: use chosen files xor default + readDocument if new Activity requested + nothing rendered if only two files used

        long time = System.currentTimeMillis();

//        dm.readDocument(this, R.raw.test, "Chapter 1");
//        dm.readDocument(this, R.raw.test2, "Chapter 2");
//        dm.readDocument(this, R.raw.test3, "Chapter 3");
//        dm.readDocument(this, R.raw.test4, "Chapter 4");
//        dm.readDocument(this, R.raw.test5, "Chapter 5");

//        dm.readDocument(this, R.raw.wdqk1, "Chapter 1");
//        dm.readDocument(this, R.raw.wdqk2, "Chapter 2");
//        dm.readDocument(this, R.raw.wdqk3, "Chapter 3");
//        dm.readDocument(this, R.raw.wdqk4, "Chapter 4");
//        dm.readDocument(this, R.raw.wdqk5, "Chapter 5");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filePath;
        try {
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            resTextPath.setText(filePath);

            dm.readDocument(filePath, "Test1");

        }
        if (requestCode == 2000 && resultCode == RESULT_OK) {
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            resTextPath2.setText(filePath);
            dm.readDocument(filePath, "Test2");
        }
        } catch (FileNotFoundException e) {
            Log.e("Error", "File not found " + '\n' + e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else  {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }
    }
}
