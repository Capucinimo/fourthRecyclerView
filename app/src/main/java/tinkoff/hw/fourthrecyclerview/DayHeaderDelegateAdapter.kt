package tinkoff.hw.fourthrecyclerview

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.day_header.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayHeaderDelegateAdapter : BaseDelegateAdapter<DayHeaderDelegateAdapter.DayViewHolder, DayHeader>() {

    override fun onBindViewHolder(
        view: View,
        item: DayHeader,
        viewHolder: DayViewHolder
    ) {
        viewHolder.dayHeader.text = item.pubDate.format(DateTimeFormatter.ofPattern("d MMMM, yyyy"))
    }

    override fun getLayoutId(): Int = R.layout.day_header

    override fun createViewHolder(parent: View): DayViewHolder =
        DayViewHolder(parent)

    override fun isForViewType(items: List<*>, position: Int): Boolean = items[position] is DayHeader

    class DayViewHolder (parent: View) : BaseViewHolder(parent) {
        val dayHeader: TextView = itemView.day_header
    }
}
class DayHeader(val pubDate:LocalDate) : IViewModel