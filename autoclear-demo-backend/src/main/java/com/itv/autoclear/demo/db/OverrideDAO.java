package com.itv.autoclear.demo.db;


import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class OverrideDAO extends AbstractDAO<OverrideEntity> {
    public OverrideDAO(SessionFactory factory) {
        super(factory);
    }

    public long create(OverrideEntity result) {
        OverrideEntity existingOverride = this.search(result.getTuneCode());

        if (existingOverride != null){
            existingOverride.setCleared(result.getCleared());
            this.currentSession().update(existingOverride);
            return existingOverride.getId();
        } else {
            return persist(result).getId();
        }

    }

    public void delete(String tuneCode) {
        OverrideEntity override = this.search(tuneCode);
        this.currentSession().delete(override);
    }

    public OverrideEntity search(String tuneCode){
        EntityManager em = this.currentSession();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OverrideEntity> cr = cb.createQuery(OverrideEntity.class);
        Root<OverrideEntity> root = cr.from(OverrideEntity.class);

        Predicate tuneCodePredicate = cb.equal(root.get("tuneCode"), tuneCode);

        cr.select(root).where(cb.and(tuneCodePredicate));
        TypedQuery<OverrideEntity> query = em.createQuery(cr);
        List<OverrideEntity> results = query.getResultList();

        if (results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }
}