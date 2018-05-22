package io.github.syzygy2048.radcloud;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;

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
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.argb(255, 102, 102, 255));
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);


        Bitmap bm = Bitmap.createBitmap(2560, 1440, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint ovalPaint = new Paint();
        ovalPaint.setColor(Color.CYAN);
        ovalPaint.setStyle(Paint.Style.STROKE);
        ovalPaint.setStrokeWidth(50);

        Paint mainTextPaint = new Paint();
        mainTextPaint.setColor(Color.BLACK);
        mainTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mainTextPaint.setTextSize(50);

        Paint smallTextPaint = new Paint();
        smallTextPaint.setColor(Color.BLACK);
        smallTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        smallTextPaint.setTextSize(40);
        smallTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));


        DocumentManager dm = DocumentManager.getInstance();
        int docCount = dm.getDocumentList().keySet().size();
        int stepDegree = 360 / docCount;
        int startDegree = 0;
        ArrayList<String> tmpDocuments = new ArrayList<>(dm.getDocumentList().keySet());
        ArrayList<String> documents = new ArrayList<>();
        int categoryTh = (tmpDocuments.size() / 2 )- 1;
        for (int i = 0; i < tmpDocuments.size(); i++) {
            System.out.println("Reihenfolge Original" +
                    ": " + tmpDocuments.get(i));
            if (i > categoryTh) {
                documents.add(tmpDocuments.get(i));
            } else {
                documents.add(tmpDocuments.get(categoryTh - i));
            }
        }
        for (int i = 0; i < documents.size(); i++) {
//        for (String name : dm.getDocumentList().keySet()) {
            Path mainTextPath = new Path();
            //TODO: allign text on center
            if (i > categoryTh) {
                    mainTextPath.addArc(100, 100, 2460, 1340, startDegree + (stepDegree / 3), stepDegree);
            } else {
                if (docCount % 2 == 0) {
                    mainTextPath.addArc(100, 100, 2460, 1340, (180 - (startDegree) - (stepDegree / 3)), -stepDegree);
                } else {
                    mainTextPath.addArc(100, 100, 2460, 1340, ((180 - (stepDegree/2)) - (startDegree) - (stepDegree / 3)), -stepDegree);
                }
            }
            canvas.drawTextOnPath(documents.get(i), mainTextPath, 0, 0, mainTextPaint);
            ovalPaint.setColor(colors.get(i));
            canvas.drawArc(200, 200, 2360, 1240, startDegree, stepDegree, false, ovalPaint);

            startDegree += stepDegree;
        }


        startDegree = 0;
        for (int i = 0; i < documents.size(); i++) {
//        for (String name : dm.getDocumentList().keySet()) {
            System.out.println("Reihenfolge: " + documents.get(i));
            Path smallTextPath = new Path();
            if (i > categoryTh) {
                smallTextPath.addArc(200, 200, 2360, 1240, startDegree + (stepDegree/3), stepDegree);
            } else {
                if (docCount % 2 == 0) {
                    smallTextPath.addArc(200, 200, 2360, 1240, (180 - (startDegree) - (stepDegree / 3)), -stepDegree);
                } else {
                    smallTextPath.addArc(200, 200, 2360, 1240, ((180 - (stepDegree/2)) - (startDegree) - (stepDegree / 3)), -stepDegree);
                }
            }
            canvas.drawTextOnPath(documents.get(i), smallTextPath, 0, 0, smallTextPaint);
            startDegree += stepDegree;

        }

        ovalPaint.setStrokeWidth(1);
        ovalPaint.setColor(Color.BLACK);
        for (Word word : dm.getWordList()) {
            canvas.drawLine(1280, 770, 1280 + 640 * word.getPosition().x, 770 +  385 * word.getPosition().y, ovalPaint);
            canvas.drawText(word.getTerm(), 1280 + 640 * word.getPosition().x, 770 +  385 * word.getPosition().y, mainTextPaint);
        }

        radCloudView.setImageBitmap(bm);

    }
}
