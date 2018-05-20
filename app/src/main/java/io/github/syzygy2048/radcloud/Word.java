package io.github.syzygy2048.radcloud;

import java.util.HashMap;

/**
 * Created by rebeb on 5/8/2018.
 */

public class Word {

    private String term;
    private HashMap<String, Float> termFrequency = new HashMap<>();
    private HashMap<String, Integer> countByDocument = new HashMap<>();
    private float inverseDocumentFeature;
    private HashMap<String, Float> normalizedWeights;
    private HashMap<String, Float> documentWeights;
    private DocumentManager.Vec2 intendedPosition;

    Word(String term){
        this.term = term;
    }

    void add(String document){
        if(countByDocument.containsKey(document)){
            countByDocument.put(document, countByDocument.get(document) + 1);
        } else {
            countByDocument.put(document, 1);
        }
    }

    public String getTerm() {
        return term;
    }

    public HashMap<String, Float> getTermFrequency(){
        return termFrequency;
    }

    public HashMap<String, Integer> getWordCount(){
        return countByDocument;
    }

    public void setTermFrequency(HashMap<String, Float> tf){
        termFrequency = tf;
    }

    public void setInverseDocumentFrequency(float idf) {
        this.inverseDocumentFeature = idf;
    }

    @Override
    public boolean equals(Object obj) {
        return term.equals(((Word)obj).getTerm());
    }

    public void setNormalizedWeights(HashMap<String, Float> normalizedWeights) {
        this.normalizedWeights = normalizedWeights;
    }

    public HashMap<String, Float> getNormalizedWeights() {
        return normalizedWeights;
    }

    public void setDocumentWeights(HashMap<String, Float> documentWeights) {
        this.documentWeights = documentWeights;
    }

    public HashMap<String, Float> getDocumentWeights() {
        return documentWeights;
    }

    public void setIntendedPosition(DocumentManager.Vec2 intendedPosition) {
        this.intendedPosition = intendedPosition;
    }

    public DocumentManager.Vec2 getPosition(){
        return intendedPosition;
    }
}
