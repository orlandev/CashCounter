package com.orlandev.cashcounter.data

data class Cash(
    val type: CashType,
    val cant: Long
) {
    fun calculate(): Long {
        return cant * type.value
    }
}

object CashPrinter {
    fun desgloseMinBills(money: Long): HashMap<Int, Long> {

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
