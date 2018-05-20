package io.github.syzygy2048.radcloud;

import java.util.HashMap;

/**
 * Created by rebeb on 5/8/2018.
 */

public class Word {

    private String term;
    HashMap<String, Float> termFrequency = new HashMap<>();
    HashMap<String, Integer> countByDocument = new HashMap<>();
    private float inverseDocumentFeature;

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
}
