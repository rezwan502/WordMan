package com.example.app_wordman.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Definition implements Serializable
{

    @SerializedName("definition")
    private String definition;
    @SerializedName("example")
    private String example;
    @SerializedName("synonyms")
    private List<String> synonyms = null;
    private final static long serialVersionUID = -5000020816772960333L;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

}