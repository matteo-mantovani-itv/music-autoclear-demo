package com.itv.autoclear.demo.db;


import javax.persistence.*;

@Entity
@Table(name="music_access",
        uniqueConstraints = {@UniqueConstraint(columnNames={"isrc_number", "record_label"})}
)
public class MusicAccessEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manual_generator")
    @SequenceGenerator(name="manual_generator", sequenceName = "manual_seq", allocationSize=50)
    @Id private Long id;

    @Column(name="cleared") private String cleared;
    @Column(name="isrc_number") private String isrcNumber;
    @Column(name="record_label") private String recordLabel;
    @Column(name="tunecode") private String tuneCode;
    @Column(name="composer") private String composer;
    @Column(name="publisher") private String publisher;
    @Column(name="artist") private String artist;
    @Column(name="track") private String track;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCleared() {
        return cleared;
    }

    public void setCleared(String cleared) {
        this.cleared = cleared;
    }

    public String getIsrcNumber() {
        return isrcNumber;
    }

    public void setIsrcNumber(String isrcNumber) {
        this.isrcNumber = isrcNumber;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public void setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
    }

    public String getTuneCode() {
        return tuneCode;
    }

    public void setTuneCode(String tuneCode) {
        this.tuneCode = tuneCode;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
