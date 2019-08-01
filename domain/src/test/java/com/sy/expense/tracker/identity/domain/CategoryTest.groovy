package com.sy.expense.tracker.identity.domain

import com.sy.expense.tracker.identity.sortit.Category
import spock.lang.Specification

/**
 * Test class for {@link Category}
 */
class CategoryTest extends  Specification {

    static final TOTAL_NUMBER_OF_CATEGORIES = 12

    def 'Should list all categories ' () {
        when:
            final Category[] categories = Category.values()
        then:
            categories.length == TOTAL_NUMBER_OF_CATEGORIES
    }
}