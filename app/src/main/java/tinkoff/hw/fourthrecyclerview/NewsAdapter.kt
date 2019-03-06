package tinkoff.hw.fourthrecyclerview

import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.news_card.view.*
import java.time.format.DateTimeFormatter


class NewsAdapter(private var news: ArrayList<NewsElement>, listener: Listener) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_card, parent, false)
        val holder = NewsViewHolder(view)
        view.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener?.onNewsElementClick(pos, news[pos])
            }
        }
        return holder
    }

    interface Listener {
        fun onNewsElementClick(position: Int, newsElement: NewsElement)
    }

    private var listener: Listener? = listener

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsElement = news[position]
        holder.textTitle.text = newsElement.title
        holder.textDescription.text = newsElement.description
        holder.datePublication.text = newsElement.publicationDate.format(DateTimeFormatter.ofPattern("d MMMM, yyyy"))
    }
}
//data class NewsElement(var title: String, var description: String, var publicationDate: LocalDate, var content: String)
class NewsElement(var title: String?,
                  var description: String?, var publicationDate: LocalDate, var content: String?, var id: Int,
                  var favourite: Boolean
) : Parcelable {
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
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle: TextView = itemView.news_title
    val textDescription: TextView = itemView.news_desc
    val datePublication: TextView = itemView.news_pubdate
}