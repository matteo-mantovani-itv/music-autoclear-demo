package com.itv.autoclear.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IsrcSearch {
    private String isrcNumber;
    private String recordLabel;
    private String databaseId;

    public IsrcSearch() {
        // Jackson deserialization
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
    public String getDatabaseId(){ return databaseId;}
}
