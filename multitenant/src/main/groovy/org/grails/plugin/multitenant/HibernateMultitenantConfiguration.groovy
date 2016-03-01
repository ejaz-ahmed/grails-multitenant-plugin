package org.grails.plugin.multitenant

/**
 * Created by ejaz on 12/25/15.
 */
import org.grails.orm.hibernate.cfg.HibernateMappingContextConfiguration
import org.hibernate.Session
import org.hibernate.engine.spi.FilterDefinition
import grails.core.GrailsApplication
import org.hibernate.MappingException


class HibernateMultitenantConfiguration extends HibernateMappingContextConfiguration {

    private GrailsApplication grailsApplication = grails.util.Holders.findApplication()
    private boolean configLocked

//    @Override
//    void setApplicationContext(org.springframework.context.ApplicationContext applicationContext){
//        GrailsApplication app = grails.util.Holders.findApplication()
//        app.setApplicationContext(applicationContext)
//        this.grailsApplication = app
//    }

    
    protected void secondPassCompile() throws MappingException {
        if (configLocked) {
            return
        }

        super.secondPassCompile()

        this.addFilterDefinition new FilterDefinition("tenant", "tenant_id = :tenant", [tenant: this.typeResolver.basic('long')])
        for (domainClass in grailsApplication.getArtefacts("domain")) {
            if (domainClass.clazz in Multitenant) {
                def entity = this.getClassMapping(domainClass.fullName)
                entity.addFilter("tenant", "tenant_id = :tenant", false, null, null)
            }
        }

        configLocked = true
    }
}
