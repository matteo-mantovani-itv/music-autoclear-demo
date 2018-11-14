package com.itv.autoclear.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itv.autoclear.demo.client.PrsResult;
import com.itv.autoclear.demo.db.MusicAccessEntity;
import com.itv.autoclear.demo.db.OverrideEntity;
import org.hibernate.validator.constraints.Length;


public class AutoClearResult extends Addtrack {
    @Length(max=50)
    private String clearedCssClass;
    private boolean override;

    @Length(max = 50)
    private boolean success;
    private boolean manual;

    public AutoClearResult() {
        // Jackson deserialization
    }

    public AutoClearResult(String isrcNumber, String recordLabel, String tuneCode) {
        this.isrcNumber = isrcNumber;
        this.recordLabel = recordLabel;
        this.tuneCode = tuneCode;
        this.success = false;
        this.override = false;
    }

    public AutoClearResult(MusicAccessEntity dbResult){
        this.success = true;
        this.id = dbResult.getId();
        this.cleared = dbResult.getCleared();
        this.clearedCssClass = makeClearedCssClass(this.cleared);
        this.override = false;
        this.manual = true;
        this.isrcNumber = dbResult.getIsrcNumber();
        this.recordLabel = dbResult.getRecordLabel();
        this.tuneCode = dbResult.getTuneCode();
        this.composer = dbResult.getComposer();
        this.publisher = dbResult.getPublisher();
        this.artist = dbResult.getArtist();
        this.track = dbResult.getTrack();
    }

    public AutoClearResult(PrsResult prsResult, OverrideEntity dbOverride){
        boolean isOverride = false;
        String clearedFinal = prsResult.getCleared();
        if (dbOverride != null){
            isOverride = true;
            clearedFinal = dbOverride.getCleared();
        }

        this.success = true;
        this.id = null;
        this.cleared = clearedFinal;
        this.clearedCssClass = makeClearedCssClass(clearedFinal);
        this.override = isOverride;
        this.manual = false;
        this.isrcNumber = prsResult.getIsrcNumber();
        this.recordLabel = prsResult.getRecordLabel();
        this.tuneCode = prsResult.getTuneCode();
        this.composer = prsResult.getComposer();
        this.publisher = prsResult.getPublisher();
        this.artist = prsResult.getArtist();
        this.track = prsResult.getTrack();




    }

    @JsonProperty
    public String getClearedCssClass() {
        return clearedCssClass;
    }

    @JsonProperty
    public boolean getOverride() {
        return override;
    }

    @JsonProperty
    public boolean getManual() {
        return manual;
    }

    @JsonProperty
    public boolean getSuccess() {
        return success;
    }

    private static String makeClearedCssClass(String cleared){
        if ("Clear".equals(cleared)){
            return "cleared-yes";
        } else if ("Not cleared".equals(cleared)){
            return "cleared-no";
        } else {
            return "cleared-check";
        }
    }
}
