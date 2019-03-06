package tinkoff.hw.fourthrecyclerview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class LastNewsTabFragment : Fragment() {
    /*companion object {

        @JvmStatic
        fun newInstance(color: Int): LastNewsTabFragment {
            // аналогично ColorPagerFragment, записываем аргументы в Bundle и передаем фрагменту
            val args = Bundle()
            args.putInt(ARG_COLOR, color)
            val fragment = LastNewsTabFragment()
            fragment.arguments = args
            return fragment
        }

        /**
         * ключ, по которому будет храниться цвет фрагмента
         */
        private const val ARG_COLOR = "color"
    }*/
    var recyclerView:RecyclerView? = null
    private val KEY_NEWS = "news"
    var news = ArrayList<NewsElement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // проверяем, есть ли у нас сохраненное состояние
        if (savedInstanceState != null) {
            // если есть, то достаем оттуда список цветов
            news = savedInstanceState.getParcelableArrayList(KEY_NEWS)!!
        } else {
            // иначе генерируем список случайных цветов, состоящий из pageCount елементов
            for (i in 0..30)
                news.add(
                    NewsElement(
                        java.util.UUID.randomUUID().toString(),
                        "Описание новости. Достаточно длинное, чтобы убедиться в том, что все равботает, как надо.",
                        LocalDate.now(),
                        "content",
                        false,
                        i
                    )
                )
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_lastnewstab, container, false)
        recyclerView= rootView.findViewById(R.id.lastnewstab_recyclerview)
        class Listener : NewsAdapter.Listener {
            override fun onNewsElementClick(position: Int, newsElement: NewsElement) {
                val intent = Intent(activity!!, NewsActivity::class.java)
                intent.putExtra("newsElementData", newsElement)
                startActivity(intent)
            }
        }

        recyclerView?.adapter = NewsAdapter(news,Listener())
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val intentFilter = IntentFilter("tinkoff.hw.fourthrecyclerview.changingFavourites")
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(myReceiver, intentFilter)
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // сохраняем список цветов в outState, чтобы потом вытащить его в onCreate
        outState.putParcelableArrayList(KEY_NEWS, news)
    }
    val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                "tinkoff.hw.fourthrecyclerview.changingFavourites" -> {
                    val newNewsElement = intent.getParcelableExtra<NewsElement>("newsElementData")
                    for (i in 0 until news.count())
                        if (news[i].id == newNewsElement.id){
                            news[i].favourite = newNewsElement.favourite
                            recyclerView?.adapter?.notifyItemChanged(i)
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