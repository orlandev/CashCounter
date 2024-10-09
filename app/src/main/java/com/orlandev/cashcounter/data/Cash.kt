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



//Extension
fun List<Cash>.sumAll(): Long {
    return this.sumOf {
        it.calculate()
    }
}


sealed class CashType(val value: Int) {
    data object D1000 : CashType(1000)
    data object D500 : CashType(500)
    data object D200 : CashType(200)
    data object D100 : CashType(100)
    data object D50 : CashType(50)
    data object D20 : CashType(20)
    data object D10 : CashType(10)
    data object D5 : CashType(5)
    data object D3 : CashType(3)
    data object D1 : CashType(1)
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
