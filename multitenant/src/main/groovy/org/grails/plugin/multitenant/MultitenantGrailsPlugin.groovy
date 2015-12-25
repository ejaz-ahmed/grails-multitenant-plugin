package org.grails.plugin.multitenant

import grails.core.GrailsClass
import grails.plugins.*
import org.grails.core.artefact.DomainClassArtefactHandler

class MultitenantGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.11 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Multitenant" // Headline display name of the plugin
    def author = "Ejaz Ahmed"
    def authorEmail = "ejaz_ahmed@troutbird.com"
    def description = '''\
This plugin adds multitenant support for grails 3 based on hibernate filters.
Tenants are resolved using spring security. It does not support URL based tenant
 resolver for the time being.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/multitenant"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "TroutBird", url: "http://troutbird.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    void doWithDynamicMethods() {
        for (domainClass in grailsApplication.domainClasses) {
            if (domainClass.clazz in TenantIdentifier) {
                MultitenantDomainUtils.addDomainClassMethods(domainClass.clazz, getApplicationContext())
            }

        }
    }

}
