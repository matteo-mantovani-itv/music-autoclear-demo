package com.itv.autoclear.demo.configuration;



import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.validation.constraints.NotNull;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import io.dropwizard.validation.ValidationMethod;


/**
 * A utility class for configuring cross origin settings for an application. Uses of this class should be used with
 * dropwizard's {@link Configuration} to enable this properties to be set via a yaml file.
 * <p>
 * <b>Configuration Parameters:</b>
 * <table>
 * <tr>
 * <th>Name</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>{@code crossOriginEnabled}</td>
 * <td>Whether origin should be enabled, if true the rest of the properties <em>must</em> be set.
 * </tr>
 * <tr>
 * <td>{@code allowedMethods}</td>
 * <td>The allowed HTTP methods (e.g. GET,PUT,POST). This should be a comma separated list with no spaces (e.g.
 * "PUT,POST,GET").
 * </tr>
 * <tr>
 * <td>{@code allowedOrigins}</td>
 * <td>A comma separated list with no spaces of origins which are allowed. Can also be an asterisk "*" so all origins
 * are allowed.
 * </tr>
 * <tr>
 * <td>{@code accessControlAllowOrigin}</td>
 * <td>Can be set to an asterisk "*" to allow all</td>
 * </tr>
 * <tr>
 * <td>{@code allowedHeaders}</td>
 * <td>A comma separated list of all allowed headers on the request</td>
 * </tr>
 * <tr>
 * <td>{@code allowCredentials}</td>
 * <td>A true/false value specifying whether credentials should be allowed</td>
 * </tr>
 * </table>
 */
public class CrossOriginBuilder
{
    private static final Logger LOG = LoggerFactory.getLogger(CrossOriginBuilder.class);

    @NotNull
    private Boolean crossOriginEnabled;

    private String allowedMethods;

    private String allowedOrigins;

    private String accessControlAllowOrigin;

    private String allowedHeaders;

    private Boolean allowCredentials;

    private String exposedHeaders;


    @JsonProperty
    public Boolean getCrossOriginEnabled()
    {
        return this.crossOriginEnabled;
    }


    @JsonProperty
    public void setCrossOriginEnabled(Boolean crossOriginEnabled)
    {
        this.crossOriginEnabled = crossOriginEnabled;
    }


    @JsonProperty
    public String getAllowedMethods()
    {
        return this.allowedMethods;
    }


    @JsonProperty
    public void setAllowedMethods(String allowedMethods)
    {
        this.allowedMethods = allowedMethods;
    }


    @JsonProperty
    public String getAllowedOrigins()
    {
        return this.allowedOrigins;
    }


    @JsonProperty
    public void setAllowedOrigins(String allowedOrigins)
    {
        this.allowedOrigins = allowedOrigins;
    }


    @JsonProperty
    public String getAccessControlAllowOrigin()
    {
        return this.accessControlAllowOrigin;
    }


    @JsonProperty
    public void setAccessControlAllowOrigin(String accessControlAllowOrigin)
    {
        this.accessControlAllowOrigin = accessControlAllowOrigin;
    }


    @JsonProperty
    public String getAllowedHeaders()
    {
        return this.allowedHeaders;
    }


    @JsonProperty
    public void setAllowedHeaders(String allowedHeaders)
    {
        this.allowedHeaders = allowedHeaders;
    }


    @JsonProperty
    public Boolean getAllowCredentials()
    {
        return this.allowCredentials;
    }


    @JsonProperty
    public void setAllowCredentials(Boolean allowCredentials)
    {
        this.allowCredentials = allowCredentials;
    }


    @JsonProperty
    public String getExposedHeaders()
    {
        return this.exposedHeaders;
    }


    @JsonProperty
    public void setExposedHeaders(String exposedHeaders)
    {
        this.exposedHeaders = exposedHeaders;
    }


    @JsonIgnore
    @ValidationMethod(message = ".allowedMethods must be set if cross origin enabled")
    public boolean isAllowedMethodsEnabled()
    {
        return this.crossOriginEnabled ? this.allowedMethods != null : true;
    }


    @JsonIgnore
    @ValidationMethod(message = ".allowedOrigins must be set if cross origin enabled")
    public boolean isAllowedOriginsEnabled()
    {
        return this.crossOriginEnabled ? this.allowedOrigins != null : true;
    }


    @JsonIgnore
    @ValidationMethod(message = ".accessControlAllowOrigin must be set if cross origin enabled")
    public boolean isAccessControlAllowOriginEnabled()
    {
        return this.crossOriginEnabled ? this.accessControlAllowOrigin != null : true;
    }


    @JsonIgnore
    @ValidationMethod(message = ".allowedHeaders must be set if cross origin enabled")
    public boolean isAllowedHeadersEnabled()
    {
        return this.crossOriginEnabled ? this.allowedHeaders != null : true;
    }


    @JsonIgnore
    @ValidationMethod(message = ".allowCredentials must be set if cross origin enabled")
    public boolean isAllowCredentialsEnabled()
    {
        return this.crossOriginEnabled ? this.allowCredentials != null : true;
    }


    @JsonIgnore
    @ValidationMethod(message = ".exposedHeaders must be set if cross origin enabled")
    public boolean isExposedHeadersEnabled()
    {
        return this.crossOriginEnabled ? this.exposedHeaders != null : true;
    }


    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("crossOriginEnabled", this.crossOriginEnabled)
                .add("allowedMethods", this.allowedMethods)
                .add("allowedOrigins", this.allowedOrigins)
                .add("accessControlAllowOrigin", this.accessControlAllowOrigin)
                .add("allowedHeaders", this.allowedHeaders)
                .add("allowCredentials", this.allowCredentials)
                .add("exposedHeaders", this.exposedHeaders)
                .toString();
    }


    /**
     * Configures CORS for the given dropwizard environment.
     *
     * @param environment The {@link Environment} to configure.
     */
    @SuppressWarnings("squid:S2629")
    public void configure(final Environment environment)
    {
        if (this.crossOriginEnabled)
        {
            LOG.info("Cross Origin is enabled - configuring cross origin...");

            final Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
            filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
            filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, this.allowedMethods);
            filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, this.allowedOrigins);
            filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER,
                    this.accessControlAllowOrigin);
            filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, this.allowedHeaders);
            filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, this.allowCredentials.toString());
            filter.setInitParameter(CrossOriginFilter.EXPOSED_HEADERS_PARAM, this.exposedHeaders);

            LOG.info("Cross Origin configured, properties are: {}", toString());
        }
        else
        {
            LOG.info("Cross Origin not enabled - skipping configuring cross origin.");
        }
    }
}
