# TOC


# grails-multitenant-plugin - Sponsored by SaasMax
This plugin is sponsored by SaaSMax Inc. It adds multitenant support for grails 3 applications based on hibernate filters. Tenants are resolved using spring security. It does not support URL based tenant resolver for the time being however it is in pipeline and will be implemented soon.

## Installation

Add following dependency in build.gradle

```
compile 'org.grails.plugins:multitenant:0.1'
```
Add configClass attribute in application.yml under dataSource section like this:

```
 configClass: org.grails.plugin.multitenant.HibernateMultitenantConfiguration 
 ```

# Architecture

This plugin uses single database single schema differentiator based technique to identify tenants. 

## Resolving Tenant

Currently it resolves tenant using spring security. So you have to edit spring security user domain class to implement TenantIdentifier trait like this:

```
class User implements Serializable, TenantIdentifier
```
It add userTenantId property to User domain class and injects two closures dynamically to this domain

### withTenantId

This closure executes a particular code inside its scope with a tenantId supplied as parameters even if the logged in user does not belong to that tenant. You can only execute idempotent code inside this block. If your code is query, or some other read only operation, it will execute that with supplied tenantId. If your code is going to change something in database, it will use tenantId of logged in user. Be careful . .

```
User.withTenantId(12){

// Your code goes here

}

```

### withoutTenantId

As the name states, you can bypass tenantId filter temporarily to do operations not specific to any tenant.

```
User.withoutTenantId(){

// You code goes here

```

The code in this scope should be read only as is the case with withTenantId method above.
 
## Multitenat Domain Classes

You have to implement Multitenant trait in all domain classes you want to be multitenant.

```
class Book implement Multitenant
```

This will add a property tenantId to domain class and three methods as below:

```
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
    
```
So if you want to use these methods in any of multitenant domain class, you have to reproduce above code along with your own implementation as yours will overwrite these methods.

## TenantResolverService

This plugin provide TenantResolverService to your application which can be injected anywhere just like normal grails services. It provides only one method resolveTenant which provides tenantId of current user. Multitenant plugin makes extensive use of this service at various places inside the code. Multitenant filter intercepts controller actions only. If you want to do some multitenant stuff inside a service then you should call that service from controller or use withTenantId as below:

```
def tenantResolverService
User.withTenantId(tenantResolverService.resolveTenant()){
 // your code here
}
```

# About SaaSMax
[SaaSMAX](https://saasmax.com) is the growth engine for SaaS companies and their resellers. Our mission is all about recurring SaaS commissions.
