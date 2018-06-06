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
import java.util.HashMap;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RadCloudActivity extends AppCompatActivity {
    private ImageView radCloudView;

    public static final int MAXIMUM_TEXT_SIZE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rad_cloud);


        radCloudView = (ImageView) findViewById(R.id.radCloud);
        ArrayList<Integer> colors = new ArrayList<>();
        HashMap<String, Integer> categoryColorCodes = new HashMap<>();
        colors.add(Color.argb(255, 0, 0, 255));
        colors.add(Color.argb(255, 0, 255, 0));
        colors.add(Color.argb(255, 255, 0, 0));
        colors.add(Color.argb(255, 255, 0, 255));
        colors.add(Color.argb(255, 0, 255, 255));
        colors.add(Color.argb(255, 255, 255, 0));


        Bitmap bm = Bitmap.createBitmap(2560, 1440, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint ovalPaint = new Paint();
        ovalPaint.setColor(Color.CYAN);
        ovalPaint.setStyle(Paint.Style.STROKE);
        ovalPaint.setStrokeWidth(50);

        Paint categoryTextPaint = new Paint();
        categoryTextPaint.setColor(Color.BLACK);
        categoryTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        categoryTextPaint.setTextSize(50);

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
        int categoryTh = (tmpDocuments.size() / 2) - 1;
        for (int i = 0; i < tmpDocuments.size(); i++) {
            System.out.println("Reihenfolge Original" +
                    ": " + tmpDocuments.get(i));
            if (i > categoryTh) {
                documents.add(tmpDocuments.get(i));
                categoryColorCodes.put(tmpDocuments.get(i), colors.get(i));
            } else {
                documents.add(tmpDocuments.get(categoryTh - i));
                categoryColorCodes.put(tmpDocuments.get(categoryTh - i), colors.get(categoryTh - i));
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
                    mainTextPath.addArc(100, 100, 2460, 1340, ((180 - (stepDegree / 2)) - (startDegree) - (stepDegree / 3)), -stepDegree);
                }
            }
            canvas.drawTextOnPath(documents.get(i), mainTextPath, 0, 0, categoryTextPaint);
            ovalPaint.setColor(colors.get(i));
            //TODO: the parameters of the ellipse are also used in overlap detection!
            canvas.drawArc(200, 200, 2360, 1240, startDegree, stepDegree, false, ovalPaint);

            startDegree += stepDegree;
        }


        startDegree = 0;
        for (int i = 0; i < documents.size(); i++) {
//        for (String name : dm.getDocumentList().keySet()) {
            System.out.println("Reihenfolge: " + documents.get(i));
            Path smallTextPath = new Path();
            if (i > categoryTh) {
                smallTextPath.addArc(200, 200, 2360, 1240, startDegree + (stepDegree / 3), stepDegree);
            } else {
                if (docCount % 2 == 0) {
                    smallTextPath.addArc(200, 200, 2360, 1240, (180 - (startDegree) - (stepDegree / 3)), -stepDegree);
                } else {
                    smallTextPath.addArc(200, 200, 2360, 1240, ((180 - (stepDegree / 2)) - (startDegree) - (stepDegree / 3)), -stepDegree);
                }
            }
            canvas.drawTextOnPath(documents.get(i), smallTextPath, 0, 0, smallTextPaint);
            startDegree += stepDegree;

        }

        float maximumWordRelevance = dm.getMaximumWordRelevance();

        ovalPaint.setStrokeWidth(1);
        ovalPaint.setColor(Color.BLACK);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint barChartPaint = new Paint();
        barChartPaint.setStrokeWidth(5);
        float textWidth;

        for (Word word : dm.getWordList()) {
            float maximumRelevance = word.getMaximumRelevance();
//            canvas.drawLine(1280, 770, word.getPosition().x, word.getPosition().y, ovalPaint);
            float textSize = MAXIMUM_TEXT_SIZE * (maximumRelevance / maximumWordRelevance);
            if (textSize < 5.0) {
                textSize = 5;
            }
            textPaint.setTextSize(textSize);
            Integer textColor = Color.argb(255, 0, 0, 0);
            HashMap<String, Float> weights = word.getPlacementWeights();
            for (String document : weights.keySet()) {
                int r = Math.round((float) Color.red(categoryColorCodes.get(document)) * weights.get(document));
                int g = Math.round((float) Color.green(categoryColorCodes.get(document)) * weights.get(document));
                int b = Math.round((float) Color.blue(categoryColorCodes.get(document)) * weights.get(document));


                int oldR = Color.red(textColor);
                int oldG = Color.green(textColor);
                int oldB = Color.blue(textColor);

                int newR = r + oldR;
                if (newR > 255) {
                    newR = 255;
                }
                int newG = g + oldG;
                if (newG > 255) {
                    newG = 255;
                }
                int newB = b + oldB;
                if (newB > 255) {
                    newB = 255;
                }
                textColor = Color.argb(255, newR, newG, newB);
            }
            if (textColor == Color.WHITE) {
                textColor = Color.BLACK;
            }
            textPaint.setColor(textColor);
            //TODO: eliminate magic numbers, also pay attention for barchart
            String term = "l:" + word.getPosition().boundingBox.left + ",r:" + word.getPosition().boundingBox.right + ",b:" + word.getPosition().boundingBox.bottom + ",t:" + word.getPosition().boundingBox.top;
            textWidth = textPaint.measureText(word.getTerm());
            canvas.drawText(word.getTerm(), word.getPosition().x - textWidth / 2, word.getPosition().y + textSize / 2, textPaint);
            Random rnd = new Random();
            textPaint.setColor(Color.argb(100, rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100)));
            canvas.drawRect(word.getPosition().boundingBox, textPaint);


            //TODO: is this the right relevance measure?
            HashMap<String, Float> relevance = word.getPlacementWeights();
            float barchartX = word.getPosition().x - textWidth / 2;
            float barchartY = word.getPosition().y + 5 + textSize / 2; //offset
            barChartPaint.setStrokeWidth(5);
            for (String doc : relevance.keySet()) {
                //canvas.drawLine(1280, 770, 1280 + 640 * word.getPosition().x, 770 +  385 * word.getPosition().y, ovalPaint);
                System.out.println("Word: " + word.getTerm() + " Doc: " + doc + " textWidth: " + textWidth + " category weight: " + word.getCategoryWeights().get(doc) + " relevance: " + maximumRelevance);
                float barWidth = textWidth * relevance.get(doc);
                barChartPaint.setColor(categoryColorCodes.get(doc));
                canvas.drawLine(barchartX, barchartY, (barchartX + barWidth), barchartY, barChartPaint);
                barchartX += barWidth;
            }
        }

//        canvas.drawRect(new Rect(0,0,50,50), barChartPaint);
//        Paint linePaint = new Paint();
//        for (int i = 0; i < 2700 ; i = i + 100){
//            canvas.drawLine(0, i, 2700, i, linePaint);
//            canvas.drawLine(i, 0 , i, 2700, linePaint);
//        }

        Paint spiralPaint = new Paint();
        Paint spiralLinePaint = new Paint();
        spiralPaint.setStrokeWidth(5);
        DocumentManager.Vec2 oldOffset = new DocumentManager.Vec2(0,0);
        for (int i = 0; i <= 500; i++){
            DocumentManager.Vec2 offset = SpiralUtil.calculateSpiral(i);
            canvas.drawPoint(1280 + offset.x, 770 + offset.y, spiralPaint);
            canvas.drawLine(1280 + oldOffset.x, 770 + oldOffset.y, 1280 + offset.x, 770 + offset.y, spiralLinePaint);
            oldOffset = offset;
        }

        radCloudView.setImageBitmap(bm);

    }
}
