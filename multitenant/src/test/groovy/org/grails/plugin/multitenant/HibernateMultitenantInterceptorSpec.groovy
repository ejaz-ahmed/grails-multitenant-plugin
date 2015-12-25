package org.grails.plugin.multitenant


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HibernateMultitenantInterceptor)
class HibernateMultitenantInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test hibernateMultitenant interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"hibernateMultitenant")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
