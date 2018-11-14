package com.itv.autoclear.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OverrideInput {
    private String cleared;
    private String tuneCode;

    public OverrideInput() {
        // Jackson deserialization
    }

    @JsonProperty
    public String getCleared() {
        return cleared;
    }

    @JsonProperty
    public String getTuneCode() {
        return tuneCode;
    }
}
