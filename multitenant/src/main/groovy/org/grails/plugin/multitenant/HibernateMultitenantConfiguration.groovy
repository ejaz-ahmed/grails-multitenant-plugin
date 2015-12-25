package org.grails.plugin.multitenant

/**
 * Created by ejaz on 12/25/15.
 */
import org.grails.orm.hibernate.cfg.GrailsAnnotationConfiguration
import org.hibernate.Session
import org.hibernate.engine.spi.FilterDefinition
import grails.core.GrailsApplication
import org.hibernate.MappingException


class HibernateMultitenantConfiguration extends GrailsAnnotationConfiguration {

    private GrailsApplication grailsApplication
    private boolean configLocked

    @Override
    void setGrailsApplication(GrailsApplication grailsApplication) {
        super.setGrailsApplication grailsApplication
        this.grailsApplication = grailsApplication
    }

    @Override
    protected void secondPassCompile() throws MappingException {
        if (configLocked) {
            return
        }

        super.secondPassCompile()

        this.addFilterDefinition new FilterDefinition("tenant", "tenant_id = :tenant", [tenant: this.typeResolver.basic('long')])
        for (domainClass in grailsApplication.domainClasses) {
            if (domainClass.clazz in Multitenant) {
                def entity = this.getClassMapping(domainClass.fullName)
                entity.addFilter("tenant", "tenant_id = :tenant", false, null, null)
            }
        }

        configLocked = true
    }
}
