package com.sy.expense.tracker.identityaccess.domain

import com.sy.expense.tracker.identityaccess.sortit.Category
import spock.lang.Specification

/**
 * Test class for {@link com.sy.expense.tracker.identityaccess.sortit.Category}
 */
class CategorySpec extends  Specification {

    static final TOTAL_NUMBER_OF_CATEGORIES = 12

    def 'Should list all categories ' () {
        when:
            final Category[] categories = Category.values()
        then:
            categories.length == TOTAL_NUMBER_OF_CATEGORIES
    }
}