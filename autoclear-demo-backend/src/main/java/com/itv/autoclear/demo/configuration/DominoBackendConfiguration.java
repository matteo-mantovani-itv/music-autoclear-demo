package com.itv.autoclear.demo.configuration;


import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class DominoBackendConfiguration extends Configuration {
    @NotNull
    @JsonProperty("crossOrigin")
    private CrossOriginBuilder crossOriginBuilder;

    public CrossOriginBuilder getCrossOriginBuilder()
    {
        return this.crossOriginBuilder;
    }


    public void setCrossOriginBuilder(final CrossOriginBuilder crossOriginBuilder)
    {
        this.crossOriginBuilder = crossOriginBuilder;
    }

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public void setDataSourceFactory(DataSourceFactory database) {
        this.database = database;
    }
}
