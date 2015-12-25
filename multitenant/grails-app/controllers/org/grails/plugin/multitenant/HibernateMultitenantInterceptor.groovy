package org.grails.plugin.multitenant


class HibernateMultitenantInterceptor {
    def tenantResolverService
    def sessionFactory

    HibernateMultitenantInterceptor() {
        match(controller: '*', action: '*')
    }

    boolean before() {
        def tenantId = tenantResolverService.resolveTenant()
        if (tenantId == null) {

        } else {
            sessionFactory.currentSession?.enableFilter('tenant')?.setParameter("tenant", tenantId)
            log.debug("Tenant with name $tenantId selected")
        }

        true
    }
}
