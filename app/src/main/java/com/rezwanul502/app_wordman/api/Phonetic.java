package com.rezwanul502.app_wordman.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phonetic implements Serializable
{

    @SerializedName("text")
    private String text;
    @SerializedName("audio")
    private String audio;
    private final static long serialVersionUID = -2676149252442574489L;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

}