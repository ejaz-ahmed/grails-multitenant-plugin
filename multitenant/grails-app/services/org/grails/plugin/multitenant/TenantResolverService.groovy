package org.grails.plugin.multitenant

import grails.transaction.Transactional

@Transactional
class TenantResolverService {
    def springSecurityService

    def resolveTenant(){
        if(springSecurityService.loggedIn){
            return springSecurityService.getCurrentUser().userTenantId
        } else {
            return null
        }
    }
}
