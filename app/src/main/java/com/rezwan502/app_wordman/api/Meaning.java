package com.rezwan502.app_wordman.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Meaning implements Serializable
{

    @SerializedName("partOfSpeech")
    private String partOfSpeech;
    @SerializedName("definitions")
    private List<Definition> definitions = null;
    private final static long serialVersionUID = -6567623920221533166L;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

}
