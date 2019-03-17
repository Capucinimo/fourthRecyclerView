package tinkoff.hw.fourthrecyclerview

import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_card.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class NewsDelegateAdapter(private val clickListener: NewsElementClickListener) : BaseDelegateAdapter<NewsDelegateAdapter.NewsViewHolder, NewsElement>() {

    override fun onBindViewHolder(
        view: View,
        newsElement: NewsElement,
        newsViewHolder: NewsViewHolder
    ) {
        newsViewHolder.textTitle.text = newsElement.title
        newsViewHolder.textDescription.text = newsElement.description
        newsViewHolder.datePublication.text = newsElement.publicationDate.format(DateTimeFormatter.ofPattern("d MMMM, yyyy"))
        newsViewHolder.itemView.setOnClickListener {
            val pos = newsViewHolder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                clickListener.onNewsElementClick(pos)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.news_card
    }

    override fun createViewHolder(parent: View): NewsViewHolder {
        return NewsViewHolder(parent)
    }

    override fun isForViewType(items: List<*>, position: Int): Boolean {
        return items[position] is NewsElement
    }

    class NewsViewHolder(parent: View) : BaseViewHolder(parent) {
        val textTitle: TextView = itemView.news_title
        val textDescription: TextView = itemView.news_desc
        val datePublication: TextView = itemView.news_pubdate
    }

    interface NewsElementClickListener {
        fun onNewsElementClick(position: Int)
    }
}
class NewsElement(var title: String?,
                  var description: String?, var publicationDate: LocalDate, var content: String?, var id: Int,
                  var favourite: Boolean
) : Parcelable, IViewModel {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        LocalDate.parse(parcel.readString()),
        parcel.readString(),
        parcel.readInt(),
        parcel.readValue(null) as Boolean
    )

    constructor() : this("", "", LocalDate.MIN, "", 0, false)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(publicationDate.toString())
        parcel.writeString(content)
        parcel.writeInt(id)
        parcel.writeValue(favourite)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsElement> {
        override fun createFromParcel(parcel: Parcel): NewsElement {
            return NewsElement(parcel)
        }

        override fun newArray(size: Int): Array<NewsElement?> {
            return arrayOfNulls(size)
        }
    }

}