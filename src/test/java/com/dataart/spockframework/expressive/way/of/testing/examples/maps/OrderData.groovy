package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Address
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Customer
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Order

import java.time.LocalDate

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.FOOD
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CARD

trait OrderData {

    Order getOrder(def with) {
        def map = (with != null) ? orderMap() << with : orderMap()
        map.items = map.items as Item[]
        return new Order(map)
    }

    Customer getCustomer(def with) {
        return (with != null) ? customerMap() << with : customerMap() as Customer
    }

    Address getAddress(def with) {
        return (with != null) ? addressMap() << with : addressMap() as Address
    }

    Item getItem(def with) {
        return (with != null) ? itemMap() << with : itemMap() as Item
    }

    private Map orderMap() {
        [
                customer       : customerMap(),
                shipmentAddress: addressMap(),
                items          : [itemMap()],
                price          : 100,
                paymentType    : CARD
        ]
    }

    private Map customerMap() {
        [
                firstName  : 'Jan',
                lastName   : 'Kowalski',
                dateOfBirth: LocalDate.of(1996, 5, 23),
                address    : addressMap()
        ]
    }

    private Map addressMap() {
        [
                country     : 'USA',
                province    : 'California',
                city        : 'Folsom',
                postalCode  : 'CA 95630',
                street      : 'St Louis Street',
                streetNumber: '681',
                homeNumber  : '23'
        ]
    }

    private Map itemMap() {
        [
                category: FOOD,
                name    : 'Item name',
                price   : 200
        ]
    }

}