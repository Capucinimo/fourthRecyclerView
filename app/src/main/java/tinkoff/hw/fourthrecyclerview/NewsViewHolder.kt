package tinkoff.hw.fourthrecyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_card.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle: TextView = itemView.news_title
    val textDescription: TextView = itemView.news_desc
    val datePublication: TextView = itemView.news_pubdate
}
