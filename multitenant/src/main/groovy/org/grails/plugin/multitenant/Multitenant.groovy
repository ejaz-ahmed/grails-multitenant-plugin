package org.grails.plugin.multitenant

trait Multitenant {
    def tenantResolverService
    Long tenantId

    def beforeInsert() {
        if(tenantId == null){
            tenantId = tenantResolverService.resolveTenant()
        }
    }

    def beforeValidate() {
        if(tenantId == null){
            tenantId = tenantResolverService.resolveTenant()
        }
    }

    def beforeUpdate() {
        if(tenantId == null){
            tenantId = tenantResolverService.resolveTenant()
        }
    }

}