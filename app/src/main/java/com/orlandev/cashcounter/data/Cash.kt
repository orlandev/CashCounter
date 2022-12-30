package com.orlandev.cashcounter.data

/**
 * A Cash is a type of money that has a type and a quantity.
 *
 * The type of money is defined by the CashType enum.
 *
 * The quantity is defined by the cant property.
 *
 * The calculate() method returns the value of the cash.
 * @property {CashType} type - CashType
 * @property {Long} cant - The amount of money of that type.
 */
data class Cash(
    val type: CashType,
    val cant: Long
) {
    fun calculate(): Long {
        return cant * type.value
    }
}

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

//Extension
fun List<Cash>.sumAll(): Long {
    return this.sumOf {
        it.calculate()
    }
}


sealed class CashType(val value: Int) {
    object D1000 : CashType(1000)
    object D500 : CashType(500)
    object D200 : CashType(200)
    object D100 : CashType(100)
    object D50 : CashType(50)
    object D20 : CashType(20)
    object D10 : CashType(10)
    object D5 : CashType(5)
    object D3 : CashType(3)
    object D1 : CashType(1)
}

fun cashTypesInList(): List<CashType> {
    return listOf(
        CashType.D1000,
        CashType.D500,
        CashType.D200,
        CashType.D100,
        CashType.D50,
        CashType.D20,
        CashType.D10,
        CashType.D5,
        CashType.D3,
        CashType.D1,
    )
}
