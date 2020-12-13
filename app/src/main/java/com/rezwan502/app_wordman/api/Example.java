package com.rezwan502.app_wordman.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Example implements Serializable
{

    @SerializedName("word")
    private String word;
    @SerializedName("phonetics")
    private List<Phonetic> phonetics = null;
    @SerializedName("meanings")
    private List<Meaning> meanings = null;
    private final static long serialVersionUID = 6031820720685049939L;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

}
