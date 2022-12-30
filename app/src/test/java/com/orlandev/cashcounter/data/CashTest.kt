package com.orlandev.cashcounter.data

import org.junit.Assert
import org.junit.Test

internal class CashTest {

    @Test
    fun calculate() {
        val cash1 = Cash(cant = 100, type = CashType.D1000) //Result= 100_000
        val cash2 = Cash(cant = 200, type = CashType.D100) // Result= 20_000
        //Sum of cash1 and cash2 equals to 120_000

        val finalResult = cash1.calculate() + cash2.calculate()
        Assert.assertEquals(120_000, finalResult)

    }

    @Test
    fun sumAll() {
        //Result of this sum is 35750
        val list = listOf<Cash>(
            Cash(cant = 100, type = CashType.D100), // 10_000
            Cash(cant = 200, type = CashType.D200), // 40_000
            Cash(cant = 3, type = CashType.D1000),  //  3_000
            Cash(cant = 5, type = CashType.D500),   //  2_500
            Cash(cant = 50, type = CashType.D5),    //    250
        )                                     //Result 55_750

        val result = list.sumAll()

        Assert.assertEquals(55750, result)
    }

}