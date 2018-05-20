package io.github.syzygy2048.radcloud;

import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * Created by rebeb on 5/8/2018.
 */

public class Word {


    private String term;
    private int wordCount = -1;
    private float tf = -1.0f;
    private float idf = -1.0f;

    public Word(String term) {
        this.term = term;
        wordCount = 1;
    }

    public String getTerm() {
        return term;
    }

    public float getTf() {
        return tf;
    }

    public float getIdf() {
        return idf;
    }


    public void setTf(float tf) {
        this.tf = tf;
    }

    public void setIdf(float idf) {
        this.idf = idf;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void incrementWordCount() {
        wordCount++;
    }

    public void calculateWordFreqency(int wordsInDocument) {
        if (wordsInDocument > 0) {
            tf = (float) wordCount / (float) wordsInDocument;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word)) {
            throw new ClassCastException();
        }
        Word w = (Word) o;
        if (w.getTerm().equals(term)) {
            System.out.println("They are equal");
            return true;
        } else {
            System.out.println("They are not");
            return false;
        }
    }

    @Override
    public int hashCode() {
        System.out.println("Term: " + term + ", hash: " + term.hashCode());
        return term.hashCode();
//        int result = 17;
//        result = 31 * result + term.hashCode();

//        return result;
    }
}
