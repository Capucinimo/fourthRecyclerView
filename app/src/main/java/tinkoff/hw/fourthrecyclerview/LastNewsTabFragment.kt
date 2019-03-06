package tinkoff.hw.fourthrecyclerview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class LastNewsTabFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private val KEY_NEWS = "news"
    var news = ArrayList<NewsElement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // проверяем, есть ли у нас сохраненное состояние
        if (savedInstanceState != null) {
            // если есть, то достаем оттуда список цветов
            news = savedInstanceState.getParcelableArrayList(KEY_NEWS)!!
        } else {
            val loremIpsumString =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            var content = loremIpsumString
            val maxNewsDesc =
                "Описание новости. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
            // иначе генерируем список случайных цветов, состоящий из pageCount елементов
            for (i in 1..30) {
                news.add(
                    NewsElement(
                        randomSizeString(),
                        randomSizeString(1, 200, maxNewsDesc),
                        LocalDate.now(),
                        content,
                        i,
                        false
                    )
                )
                content += "\n$i\n$loremIpsumString"
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_lastnewstab, container, false)
        recyclerView = rootView.findViewById(R.id.lastnewstab_recyclerview)
        class Listener : NewsAdapter.Listener {
            override fun onNewsElementClick(position: Int, newsElement: NewsElement) {
                val intent = Intent(activity!!, NewsActivity::class.java)
                intent.putExtra("newsElementData", newsElement)
                startActivity(intent)
            }
        }

        recyclerView?.adapter = NewsAdapter(news, Listener())
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(MyItemDecoration(context!!))
        val intentFilter = IntentFilter("tinkoff.hw.fourthrecyclerview.changingFavourites")
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(myReceiver, intentFilter)
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // сохраняем список цветов в outState, чтобы потом вытащить его в onCreate
        outState.putParcelableArrayList(KEY_NEWS, news)
    }

    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                "tinkoff.hw.fourthrecyclerview.changingFavourites" -> {
                    val newNewsElement = intent.getParcelableExtra<NewsElement>("newsElementData")
                    for (i in 0 until news.count())
                        if (news[i].id == newNewsElement.id) {
                            news[i].favourite = newNewsElement.favourite
                            //recyclerView?.adapter?.notifyItemChanged(i)
                            break
                        }
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(myReceiver)
        super.onDestroy()
    }

}