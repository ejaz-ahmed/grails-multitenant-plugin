import demo.Book
import demo.auth.Role
import demo.auth.User
import demo.auth.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role('ROLE_ADMIN').save(failOnError: true)
        def userRole = new Role('ROLE_USER').save(failOnError: true)

        def testUser1 = new User('his', 'his', 1).save(failOnError: true)
        def testUser2 = new User('her', 'her', 2).save(failOnError: true)

        UserRole.create testUser1, adminRole, true
        UserRole.create testUser2, adminRole, true

        new Book("Groovy in  Action", 63.30, 1).save(failOnError: true)
        new Book("Groovy in  Action", 53.30, 1).save(failOnError: true)

        new Book("Grails in  Action", 43.30, 2).save(failOnError: true)
        new Book("Grails quickstart guide", 33.30, 2).save(failOnError: true)

    }
    def destroy = {
    }
}
