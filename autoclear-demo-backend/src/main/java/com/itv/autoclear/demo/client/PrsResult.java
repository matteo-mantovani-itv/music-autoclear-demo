package com.itv.autoclear.demo.client;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PrsResult {
    private String cleared;
    private String isrcNumber;
    private String recordLabel;
    private String tuneCode;
    private String composer;
    private String publisher;
    private String artist;
    private String track;

    public PrsResult() {
        // Jackson deserialization
    }

    public PrsResult(String cleared,
                     String isrcNumber,
                     String recordLabel,
                     String tuneCode,
                     String composer,
                     String publisher,
                     String artist,
                     String track) {
        this.cleared = cleared;
        this.isrcNumber = isrcNumber;
        this.recordLabel = recordLabel;
        this.tuneCode = tuneCode;
        this.composer = composer;
        this.publisher = publisher;
        this.artist = artist;
        this.track = track;
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
