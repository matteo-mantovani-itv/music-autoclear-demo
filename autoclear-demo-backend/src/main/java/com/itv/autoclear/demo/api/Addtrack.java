package com.itv.autoclear.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Addtrack {
    protected Long id;
    protected String cleared;
    protected String isrcNumber;
    protected String recordLabel;
    protected String tuneCode;
    protected String composer;
    protected String publisher;
    protected String artist;
    protected String track;

    public Addtrack(){}

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public String getCleared() {
        return cleared;
    }

    @JsonProperty
    public String getIsrcNumber() {
        return isrcNumber;
    }

    @JsonProperty
    public String getRecordLabel() {
        return recordLabel;
    }

    @JsonProperty
    public String getTuneCode() {
        return tuneCode;
    }

    @JsonProperty
    public String getComposer() {
        return composer;
    }

    @JsonProperty
    public String getPublisher() {
        return publisher;
    }

    @JsonProperty
    public String getArtist() {
        return artist;
    }

    @JsonProperty
    public String getTrack() {
        return track;
    }
}
