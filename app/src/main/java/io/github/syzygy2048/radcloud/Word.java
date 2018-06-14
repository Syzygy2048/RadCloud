package io.github.syzygy2048.radcloud;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.HashMap;

/**
 * Created by rebeb on 5/8/2018.
 */
public class Word {

    /**
     * Term
     */
    private String term;

    /**
     * Term frequency of word in the documents
     */
    private HashMap<String, Float> termFrequency = new HashMap<>();

    /**
     * Number of occurrences in each document
     */
    private HashMap<String, Integer> countByDocument = new HashMap<>();

    /**
     * inverse document frequency of the word
     */
    private float inverseDocumentFrequency;

    /**
     * Normalized weight for each document
     */
    private HashMap<String, Float> normalizedWeights;

    /**
     * Category weights for each document
     */
    private HashMap<String, Float> categoryWeights;

    /**
     * Placement weights for each word.
     */
    private HashMap<String, Float> placementWeights;

    /**
     * Original position of word
     */
    private DocumentManager.Vec2 intendedPosition;

    /**
     * maximum achieved relevance of the word
     */
    private float maximumRelevance;

    /**
     * Constructor
     * @param term
     */
    Word(String term){
        this.term = term;
    }

    /**
     * add word to document
     * @param document name of the document
     */
    void add(String document){
        if(countByDocument.containsKey(document)){
            countByDocument.put(document, countByDocument.get(document) + 1);
        } else {
            countByDocument.put(document, 1);
        }
    }

    /**
     * getter for maximum relevance
     * @return maximum relevance
     */
    public float getMaximumRelevance() {
        return maximumRelevance;
    }

    /**
     * setter for maximum relevance
     * @param maximumRelevance
     */
    public void setMaximumRelevance(float maximumRelevance) {
        this.maximumRelevance = maximumRelevance;
    }

    /**
     * getter for category weights
     * @return category weights
     */
    public HashMap<String, Float> getCategoryWeights() {
        return categoryWeights;
    }

    /**
     * setter for category weights
     * @param categoryWeights
     */
    public void setCategoryWeights(HashMap<String, Float> categoryWeights) {
        this.categoryWeights = categoryWeights;
    }

    /**
     * getter for placement weights
     * @return
     */
    public HashMap<String, Float> getPlacementWeights() {
        return placementWeights;
    }

    /**
     * setter for placment weights
     * @param placementWeights
     */
    public void setPlacementWeights(HashMap<String, Float> placementWeights) {
        this.placementWeights = placementWeights;
    }

    /**
     * getter for term
     * @return
     */
    public String getTerm() {
        return term;
    }

    /**
     * getter for term frequency
     * @return
     */
    public HashMap<String, Float> getTermFrequency(){
        return termFrequency;
    }

    /**
     * getter for word occurances
     * @return
     */
    public HashMap<String, Integer> getWordCount(){
        return countByDocument;
    }

    /**
     * setter for term frequency
     * @param tf
     */
    public void setTermFrequency(HashMap<String, Float> tf){
        termFrequency = tf;
    }

    /**
     * setter for inverse document frequency
     * @param idf
     */
    public void setInverseDocumentFrequency(float idf) {
        this.inverseDocumentFrequency = idf;
    }

    /**
     * getter for inverse document ferquency
     * @return
     */
    public float getInverseDocumentFrequency() { return inverseDocumentFrequency; }

    /**
     * Compares two words
     * @param obj word to compare
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return term.equals(((Word)obj).getTerm());
    }

    /**
     * setter for normalized weights
     * @param normalizedWeights
     */
    public void setNormalizedWeights(HashMap<String, Float> normalizedWeights) {
        this.normalizedWeights = normalizedWeights;
    }

    /**
     * getter for normalized weights
     * @return
     */
    public HashMap<String, Float> getNormalizedWeights() {
        return normalizedWeights;
    }

    /**
     * setter for intended position
     * @param intendedPosition
     */
    public void setIntendedPosition(DocumentManager.Vec2 intendedPosition) {
        this.intendedPosition = intendedPosition;
    }

    /**
     * getter for word position
     * @return
     */
    public DocumentManager.Vec2 getPosition(){
        return intendedPosition;
    }

    /**
     * calculates the bounding box of the word
     * @param maximumRelevance
     */
    public void calculateBoundingBox(float maximumRelevance){
        Paint paint = new Paint();
        calculateBoundingBox(paint, maximumRelevance);
    }

    /**
     * Calculates the bounding box according to word size
     * @param paint
     * @param maximumWordRelevance
     */
    public void calculateBoundingBox(Paint paint, float maximumWordRelevance) {
        float textSize = RadCloudActivity.MAXIMUM_TEXT_SIZE * (getMaximumRelevance() / maximumWordRelevance);
        if (textSize < 30.0) {
            textSize = 30;
        }
        paint.setTextSize(textSize);
        float textWidth = paint.measureText(getTerm());

        Rect rect = new Rect(
                (int) (intendedPosition.x - textWidth / 2),
                (int) (intendedPosition.y + textSize / 2),
                (int) (intendedPosition.x + textWidth / 2),
                (int) (intendedPosition.y - textSize / 2));
        intendedPosition.boundingBox = rect;
    }
}
