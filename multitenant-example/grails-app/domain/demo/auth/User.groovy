package demo.auth

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.grails.plugin.multitenant.TenantIdentifier

@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable, TenantIdentifier {

    private static final long serialVersionUID = 1

    transient springSecurityService

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    User(String username, String password) {
        this()
        this.username = username
        this.password = password
    }

    User(String username, String password, userTenantId) {
        this()
        this.username = username
        this.password = password
        this.userTenantId = userTenantId
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this)*.role
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }

    static mapping = {
        password column: '`password`'
    }
}
