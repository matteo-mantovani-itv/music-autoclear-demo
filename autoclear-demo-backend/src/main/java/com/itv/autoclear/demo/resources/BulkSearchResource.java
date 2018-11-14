package com.itv.autoclear.demo.resources;


import com.codahale.metrics.annotation.Timed;
import com.itv.autoclear.demo.api.AutoClearResult;
import com.itv.autoclear.demo.api.IsrcSearch;
import com.itv.autoclear.demo.client.PrsResult;
import com.itv.autoclear.demo.db.MusicAccessDAO;
import com.itv.autoclear.demo.db.OverrideDAO;
import com.itv.autoclear.demo.db.OverrideEntity;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Path("/bulkSearch")
@Produces(MediaType.APPLICATION_JSON)
public class BulkSearchResource {
    private MusicAccessDAO dao;
    private OverrideDAO overrideDao;

    public BulkSearchResource(final MusicAccessDAO dao, final OverrideDAO overrideDao) {
        this.dao = dao;
        this.overrideDao = overrideDao;
    }

    @POST
    @Timed
    @UnitOfWork
    public long bulkSearch(List<IsrcSearch> search) {
        return 55;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<AutoClearResult> getResults(@QueryParam("databaseId") Long databaseId) {
        Random r = new Random();

        List <AutoClearResult> result = new ArrayList<>();
        if (r.nextInt(6) >= 4){
            return result;
        }

        result.add(create("GBBKS0700570", "XL Recordings Limited", "8531366A"));
        result.add(create("GBBKS0700571", "XL Recordings Limited", "8531366B"));
        result.add(create("GBBKS0700572", "XL Recordings Limited", "8531366C"));
        result.add(create("GBBKS0700573", "XL Recordings Limited", "8531366D"));
        result.add(create("GBBKS0700574", "XL Recordings Limited", "8531366E"));
        result.add(create("GBBKS0700575", "XL Recordings Limited", "8531366F"));
        result.add(create("GBBKS0700576", "XL Recordings Limited", "8531366G"));
        result.add(create("GBBKS0700577", "XL Recordings Limited", "8531366H"));
        result.add(create("GBBKS0700578", "XL Recordings Limited", "8531366I"));
        result.add(create("GBBKS0700579", "XL Recordings Limited", "8531366J"));

        return result;
    }


    private AutoClearResult create(String isrcNumber, String recordLabel, String tuneCode){
        PrsResult prsResult = this.createPrsResult(isrcNumber, recordLabel, tuneCode);
        OverrideEntity dbOverride = this.overrideDao.search(prsResult.getTuneCode());
        return new AutoClearResult(prsResult, dbOverride);
    }

    private PrsResult createPrsResult(String isrcNumber, String recordLabel, String tuneCode){
        return new PrsResult(
                "Clear",
                isrcNumber,
                recordLabel,
                tuneCode,
                "Adkins/White",
                "UNIVERSAL MUSIC PUBLISHING LIMITED / KOBALT MUSIC PUBLISHING LTD",
                "Adele",
                "Chasing Pavements");
    }
}
