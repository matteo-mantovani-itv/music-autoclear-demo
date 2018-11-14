package com.itv.autoclear.demo.resources;

import com.codahale.metrics.annotation.Timed;
import com.itv.autoclear.demo.api.Addtrack;
import com.itv.autoclear.demo.api.AutoClearResult;
import com.itv.autoclear.demo.client.PrsResult;
import com.itv.autoclear.demo.db.MusicAccessDAO;
import com.itv.autoclear.demo.db.MusicAccessEntity;
import com.itv.autoclear.demo.db.OverrideDAO;
import com.itv.autoclear.demo.db.OverrideEntity;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DominoSearchResource {

    private MusicAccessDAO dao;
    private OverrideDAO overrideDao;

    public DominoSearchResource(final MusicAccessDAO dao, final OverrideDAO overrideDao) {
        this.dao = dao;
        this.overrideDao = overrideDao;
    }

    @POST
    @Timed
    @UnitOfWork
    public long createTrack(Addtrack addtrack) {
        MusicAccessEntity result = new MusicAccessEntity();
        result.setArtist(addtrack.getArtist());
        result.setCleared(addtrack.getCleared());
        result.setComposer(addtrack.getComposer());
        result.setIsrcNumber(addtrack.getIsrcNumber());
        result.setPublisher(addtrack.getPublisher());
        result.setRecordLabel(addtrack.getRecordLabel());
        result.setTuneCode(addtrack.getTuneCode());
        result.setTrack(addtrack.getTrack());
        result.setId(addtrack.getId());
        return this.dao.create(result);
    }

    @GET
    @Timed
    @UnitOfWork
    public AutoClearResult getClearance(@QueryParam("isrcNumber") String isrcNumber, @QueryParam("recordLabel") String recordLabel, @QueryParam("tuneCode") String tuneCode) {
        MusicAccessEntity dbResult;
        if ("".equals(tuneCode) || tuneCode == null){
            dbResult = this.dao.search(isrcNumber, recordLabel);
        } else {
            dbResult = this.dao.search(tuneCode);
        }

        if (dbResult != null){
            return new AutoClearResult(dbResult);
        }

        PrsResult prsResult = getPrsResult(isrcNumber, recordLabel, tuneCode);

        if (prsResult == null){
            return new AutoClearResult(isrcNumber, recordLabel, tuneCode);
        }
        OverrideEntity dbOverride = this.overrideDao.search(prsResult.getTuneCode());
        return new AutoClearResult(prsResult, dbOverride);

    }

    private PrsResult getPrsResult(String isrcNumber, String recordLabel, String tuneCode){
        if ("8531366C".equals(tuneCode) || ("GBBKS0700574".equals(isrcNumber) && "XL Recordings Limited".equals(recordLabel))){
            return createYes();
        } else if ("267848GW".equals(tuneCode) || ("GBHMU1700053".equals(isrcNumber) && "Sony Music Entertainment UK Ltd".equals(recordLabel))){
            return createCheck();
        } else if ("002724CN".equals(tuneCode) || ("USBWC0110047".equals(isrcNumber) && "Demon Music Group Ltd".equals(recordLabel))) {
            return createNo();
        } else {
            return null;
        }
    }

    private PrsResult createYes(){
        return new PrsResult(
                "Clear",
                "GBBKS0700574",
                "XL Recordings Limited",
                "8531366C",
                "Adkins/White",
                "UNIVERSAL MUSIC PUBLISHING LIMITED / KOBALT MUSIC PUBLISHING LTD",
                "Adele",
                "Chasing Pavements");
    }

    private PrsResult createNo(){
        return new PrsResult("Not cleared",
                "USBWC0110047",
                "Demon Music Group Ltd",
                "002724CN",
                "Jackson/Miner/Smith",
                "WARNER/CHAPPELL NORTH AMERICA LIMITED/EMI MUSIC PUBLISHING LTD/SONY/ATV MUSIC PUBLISHING (UK) LIMITED",
                "Jackie Wilson",
                "Higher and Higer");
    }

    private PrsResult createCheck(){
        return new PrsResult("Speak to music contact",
                "GBHMU1700053",
                "Sony Music Entertainment UK Ltd",
                "267848GW",
                "Andino/Oneill Luis Angel/Perez Soto/Rivera",
                "Kobalt Music Publishing / Copyright Control",
                "CNCO & LITTLE MIX",
                "Reggaetón Lento (Remix)");
    }

}