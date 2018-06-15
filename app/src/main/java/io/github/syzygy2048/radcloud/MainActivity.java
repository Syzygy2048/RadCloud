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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Entry point for the App. Let's you select and read documents.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * Buttons for file pickers.
     */
    Button openResButton0, openResButton1, openResButton2, openResButton3, openResButton4, openResButton5;
    /**
     * Reset path if onResume is called
     */
    boolean resetText = true;
    /**
     * Dropdown menu for idf method
     */
    Spinner spinner;
    /**
     * Idf methods
     */
    static final String spinnerContent[] = {"Normal", "Inverse", "Set 0 to 1"};
    /**
     * 0 normal
     * 1 1-idf
     * 2 set idf to 1 if it would be 0
     */
    int idfMethod;
    /**
     * Collection of EditTexts which are used to display which file is selected.
     */
    ArrayList<EditText> resourceTexts = new ArrayList<>();

    /**
     * The document manager that all files are read into.
     */
    final DocumentManager dm = DocumentManager.getInstance();


    /**
     * Create UI structure and define behavior.
     *
     * @param savedInstanceState - activity properties
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //++++++++++++++++++++++++++++
        dm.clear();

        spinner = (Spinner)findViewById(R.id.spinnerIDFMethod);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,spinnerContent);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        idfMethod = 0;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1100);
        }


        openResButton0 = (Button) findViewById(R.id.OpenResButton0);
        openResButton1 = (Button) findViewById(R.id.OpenResButton1);
        openResButton2 = (Button) findViewById(R.id.OpenResButton2);
        openResButton3 = (Button) findViewById(R.id.OpenResButton3);
        openResButton4 = (Button) findViewById(R.id.OpenResButton4);
        openResButton5 = (Button) findViewById(R.id.OpenResButton5);
        resourceTexts.add((EditText) findViewById(R.id.ResPath0));
        resourceTexts.add((EditText) findViewById(R.id.ResPath1));
        resourceTexts.add((EditText) findViewById(R.id.ResPath2));
        resourceTexts.add((EditText) findViewById(R.id.ResPath3));
        resourceTexts.add((EditText) findViewById(R.id.ResPath4));
        resourceTexts.add((EditText) findViewById(R.id.ResPath5));

        openResButton0.setOnClickListener(new View.OnClickListener() {
            /**
             * Start file picker
             * @param v clicked button
             */
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1000)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        openResButton1.setOnClickListener(new View.OnClickListener() {
            /**
             * Start file picker
             * @param v clicked button
             */
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1001)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        openResButton2.setOnClickListener(new View.OnClickListener() {
            /**
             * Start file picker
             * @param v clicked button
             */
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1002)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        openResButton3.setOnClickListener(new View.OnClickListener() {
            /**
             * Start file picker
             * @param v clicked button
             */
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1003)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        openResButton4.setOnClickListener(new View.OnClickListener() {
            /**
             * Start file picker
             * @param v clicked button
             */
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1004)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        openResButton5.setOnClickListener(new View.OnClickListener() {
            /**
             * Start file picker
             * @param v clicked button
             */
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1005)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
//                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
        //++++++++++++++++++++++++++++
//        dm.readDocument(this, R.raw.wdqk1, "Chapter 1");
        long time = System.currentTimeMillis();

        long time2 = System.currentTimeMillis();
        Log.d("Performance", "loading documents took " + ((time2 - time)) + " ms");

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            /**
             * Read selected files into the document manager for further processing and rendering and start LoadingScreenActivity.
             * @param view The clicked view.
             */
            @Override
            public void onClick(View view) {
                //read documents
                ArrayList<String> files = new ArrayList<>();
                for (EditText et : resourceTexts) {
                    String currentFile = et.getText().toString();
                    if (!currentFile.isEmpty() && currentFile.compareTo("Path to source") != 0) {
                        files.add(currentFile);
                    }
                }
                if (files.size() > 1) {
                    for (String s : files) {
                        String name = s.substring(s.lastIndexOf('/') + 1);
                        name = name.substring(0, name.lastIndexOf('.'));
                        try {
                            dm.readDocument(s, name);
                        } catch (FileNotFoundException e) {
                            Log.e("Error", "Document read error");
                        }
                    }
                } else {
//                            dm.readDocument(getBaseContext(), R.raw.test, "Chapter 1");
//                            dm.readDocument(getBaseContext(), R.raw.test2, "Chapter 2");
                    //        dm.readDocument(this, R.raw.test3, "Chapter 3");
                    //        dm.readDocument(this, R.raw.test4, "Chapter 4");
//                            dm.readDocument(this, R.raw.test5, "Chapter 5");

                    dm.readDocument(getBaseContext(), R.raw.wdqk1, "Scottish Fold");
                    dm.readDocument(getBaseContext(), R.raw.wdqk2, "British Shorthair");
                    dm.readDocument(getBaseContext(), R.raw.wdqk3, "Domestic Cat ");
                    dm.readDocument(getBaseContext(), R.raw.wdqk4, "Wildcat");
                    //        dm.readDocument(this, R.raw.wdqk5, "Chapter 5");
                }
                Intent loadingScreen = new Intent(MainActivity.this, LoadingScreenActivity.class);
                loadingScreen.putExtra("idfMethod", idfMethod);
                startActivity(loadingScreen);
                Log.d("RadCloud", "started");
            }
        });
    }

    /**
     * Resume Activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        dm.clear();
        for (EditText et: resourceTexts) {
            if (resetText) {
                et.setText("Path to source");
            }
        }
        idfMethod = 0;
        resetText = true;
    }

    /**
     * Results of file pickers.
     *
     * @param requestCode Request code that was used when starting the file picking.
     * @param resultCode  A result indicating success of failure.
     * @param data        An intent containing the picked data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filePath;
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                resourceTexts.get(0).setText(filePath);
            } else if (requestCode == 1001) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                resourceTexts.get(1).setText(filePath);
            } else if (requestCode == 1002) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                resourceTexts.get(2).setText(filePath);
            } else if (requestCode == 1003) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                resourceTexts.get(3).setText(filePath);
            } else if (requestCode == 1004) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                resourceTexts.get(4).setText(filePath);
            } else if (requestCode == 1005) {
                filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                resourceTexts.get(5).setText(filePath);
            }
        }
        resetText = false;
    }


    /**
     * Gives the result of a permission request. (Write External Memory)
     *
     * @param requestCode  - Request code that was used when requesting the permission.
     * @param permissions  - Array of requested permissions.
     * @param grantResults - Array of grant results.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1100: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }
    }

    /**
     * Sets the idf method
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                idfMethod = 0;
                break;
            case 1:
                idfMethod = 1;
                break;
            case 2:
                idfMethod = 2;
                break;

        }
    }

    /**
     * Sets the default idf method(normal)
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinner.setSelection(0);
        idfMethod = 0;
    }
}
