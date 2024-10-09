package com.orlandev.cashcounter.utils

import com.orlandev.cashcounter.data.cashTypesInList

object CashBreakdown {

    //TODO CREATE A FUNCTION TO GIVE THE GREATEST AMOUNT OF BILLS POSSIBLE


    fun breakdownMinBills(money: Long): HashMap<Int, Long> {

        var restOfMoney = money

        val listOfMoneyValues = cashTypesInList()

        val result: HashMap<Int, Long> = HashMap()

        for (cashType in listOfMoneyValues) {

            if (restOfMoney >= cashType.value) {

                val rest = restOfMoney / cashType.value

                result[cashType.value] = rest

                //result.append("\n${cashType.value}  ------->  $rest")
                restOfMoney = money % cashType.value

            }
        }

        return result
    }
}