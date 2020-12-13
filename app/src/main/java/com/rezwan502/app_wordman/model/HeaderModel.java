package com.rezwan502.app_wordman.model;

public class HeaderModel {

    String audio;
    String header;

    public HeaderModel(String header, String audio) {
        this.audio = audio;
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }


}
