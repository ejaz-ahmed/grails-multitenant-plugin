package org.grails.plugin.multitenant

import org.hibernate.Session

/**
 * Created by eejaz on 12/25/15.
 *
 * This class contains utility methods for Multitenant domain classes
 */
class MultitenantDomainUtils {

    static void addDomainClassMethods(Class clazz, context) {
        clazz.metaClass.static.withoutTenantId = { Closure closure ->
            Session session = context.sessionFactory.currentSession
            withoutTenantId(session, closure)
        }

        clazz.metaClass.static.withTenantId = { Long tenantId, Closure closure ->
            Session session = context.sessionFactory.currentSession
            withTenantId(session, tenantId, closure)
        }
    }

    static withoutTenantId(Session session, Closure closure) {
        try {
            session.disableFilter("tenant")
            closure.doCall()
        }
        finally {
            session.enableFilter("tenant")
        }
    }

    static withTenantId(Session session, Long tenantId, Closure closure) {
        try {
            session.getEnabledFilter("tenant")?.setParameter("tenant", tenantId)
            closure.doCall()
        }
        finally {
//            session.enableFilter("tenant")
        }
    }
}
