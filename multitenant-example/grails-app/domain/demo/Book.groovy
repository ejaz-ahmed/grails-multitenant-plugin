package demo

import org.grails.plugin.multitenant.Multitenant

class Book implements Multitenant{
    String title
    Double price

    static constraints = {
    }

    Book(String title, Double price) {
        this.title = title
        this.price = price
    }

    Book(String title, Double price, Long tenantId) {
        this.title = title
        this.price = price
        this.tenantId = tenantId
    }
}
