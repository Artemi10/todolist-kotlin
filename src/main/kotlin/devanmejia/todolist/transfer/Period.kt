package devanmejia.todolist.transfer

import java.util.*

data class Period(val firstDate: Date, val lastDate: Date){
    fun isPeriodValid(): Boolean{
        return firstDate.before(lastDate)
    }
}
