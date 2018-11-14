package com.itv.autoclear.demo.application;

import com.itv.autoclear.demo.configuration.DominoBackendConfiguration;

import com.itv.autoclear.demo.db.MusicAccessDAO;
import com.itv.autoclear.demo.db.MusicAccessEntity;
import com.itv.autoclear.demo.db.OverrideDAO;
import com.itv.autoclear.demo.db.OverrideEntity;
import com.itv.autoclear.demo.resources.BulkSearchResource;
import com.itv.autoclear.demo.resources.DominoOverrideResource;
import com.itv.autoclear.demo.resources.DominoSearchResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DominoBackendApplication extends Application<DominoBackendConfiguration> {

    private final HibernateBundle<DominoBackendConfiguration> hibernate = new HibernateBundle<DominoBackendConfiguration>(MusicAccessEntity.class, OverrideEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(DominoBackendConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new DominoBackendApplication().run(args);
    }

    @Override
    public String getName() {
        return "DominoBackend";
    }

    @Override
    public void initialize(final Bootstrap<DominoBackendConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);

        bootstrap.addBundle(new MigrationsBundle<DominoBackendConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(DominoBackendConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final DominoBackendConfiguration configuration,
                    final Environment environment) {

        configuration.getCrossOriginBuilder().configure(environment);

        final MusicAccessDAO dao = new MusicAccessDAO(hibernate.getSessionFactory());
        final OverrideDAO overrideDao = new OverrideDAO(hibernate.getSessionFactory());
        environment.jersey().register(new DominoSearchResource(dao, overrideDao));
        environment.jersey().register(new BulkSearchResource(dao, overrideDao));
        environment.jersey().register(new DominoOverrideResource(overrideDao));
    }

}
