package com.orlandev.cashcounter.data

import org.junit.Assert
import org.junit.Test

internal class CashBreakdownTest {

    @Test
    fun desglose() {
        val result = CashBreakdown.breakdownMinBills(1122).toList().reversed().first()

        Assert.assertEquals("1000  - - - ->  1", "${result.first}  - - - ->  ${result.second}")
    }

}