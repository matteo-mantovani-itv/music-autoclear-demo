package com.itv.autoclear.demo.resources;


import com.codahale.metrics.annotation.Timed;
import com.itv.autoclear.demo.api.OverrideInput;
import com.itv.autoclear.demo.db.OverrideDAO;
import com.itv.autoclear.demo.db.OverrideEntity;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/override/{tuneCode}/")
@Produces(MediaType.APPLICATION_JSON)
public class DominoOverrideResource {
    private OverrideDAO overrideDao;

    public DominoOverrideResource(final OverrideDAO overrideDao) {
        this.overrideDao = overrideDao;
    }

    @POST
    @Timed
    @UnitOfWork
    public long create(@PathParam("tuneCode") String tuneCode, OverrideInput override) {
        OverrideEntity overrideEntity = new OverrideEntity();
        overrideEntity.setCleared(override.getCleared());
        overrideEntity.setTuneCode(override.getTuneCode());
        return this.overrideDao.create(overrideEntity);
    }


    @DELETE
    @Timed
    @UnitOfWork
    public void deleteOverride(@PathParam("tuneCode") String tuneCode) {
        this.overrideDao.delete(tuneCode);
    }
}