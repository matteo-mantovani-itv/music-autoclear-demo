package com.itv.autoclear.demo.db;


import javax.persistence.*;

@Entity
@Table(name="override",
        uniqueConstraints = {@UniqueConstraint(columnNames={"tunecode"})}
)
public class OverrideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "override_generator")
    @SequenceGenerator(name="override_generator", sequenceName = "override_seq", allocationSize=50)
    private Long id;

    @Column(name="cleared") private String cleared;
    @Column(name="tunecode") private String tuneCode;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCleared() {
        return cleared;
    }

    public void setCleared(String cleared) {
        this.cleared = cleared;
    }


    public String getTuneCode() {
        return tuneCode;
    }

    public void setTuneCode(String tuneCode) {
        this.tuneCode = tuneCode;
    }

}
