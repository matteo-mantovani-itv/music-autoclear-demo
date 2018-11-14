package com.itv.autoclear.demo.db;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;


public class MusicAccessDAO extends AbstractDAO<MusicAccessEntity> {
    public MusicAccessDAO(SessionFactory factory) {
        super(factory);
    }

    public long create(MusicAccessEntity result) {
        return persist(result).getId();
    }

    public MusicAccessEntity search(String isrcNumber, String recordLabel){
        EntityManager em = this.currentSession();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MusicAccessEntity> cr = cb.createQuery(MusicAccessEntity.class);
        Root<MusicAccessEntity> root = cr.from(MusicAccessEntity.class);


        Predicate isrcNumberPredicate = cb.equal(root.get("isrcNumber"), isrcNumber);
        Predicate recordLabelPredicate = cb.equal(root.get("recordLabel"), recordLabel);

        cr.select(root).where(cb.and(isrcNumberPredicate, recordLabelPredicate));
        TypedQuery<MusicAccessEntity> query = em.createQuery(cr);
        List<MusicAccessEntity> results = query.getResultList();

        if (results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }

    public MusicAccessEntity search(String tuneCode){
        EntityManager em = this.currentSession();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MusicAccessEntity> cr = cb.createQuery(MusicAccessEntity.class);
        Root<MusicAccessEntity> root = cr.from(MusicAccessEntity.class);

        Predicate isrcNumberPredicate = cb.equal(root.get("tuneCode"), tuneCode);

        cr.select(root).where(cb.and(isrcNumberPredicate));
        TypedQuery<MusicAccessEntity> query = em.createQuery(cr);
        List<MusicAccessEntity> results = query.getResultList();

        if (results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }
}