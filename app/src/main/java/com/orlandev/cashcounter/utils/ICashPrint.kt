package com.orlandev.cashcounter.utils

import com.orlandev.cashcounter.data.Cash

interface ICashPrint {
    fun print(listOfCash: List<Cash>, date: String): String
}

class CashPrintImpl : ICashPrint {

    override fun print(listOfCash: List<Cash>, date: String): String {

        val result = StringBuilder()

        listOfCash.forEach {
            val str = it.type.value.toString()
            var spaces = ""
            val maxSpaces = (4 - str.length) + 1
            repeat((0..maxSpaces).count()) {
                spaces += "-"
            }

            result.append("$str $spaces---->  ${it.cant}  =  $${it.calculate()} \n")
        }
        val total = listOfCash.sumOf { it.calculate() }
        result.append("-----------------------\n")
        result.append("")
        result.append("Total: $$total \n")
        result.append("")
        result.append(
            date
        )

        return result.toString()
    }
}
