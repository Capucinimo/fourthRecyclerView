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

class LastNewsTabFragment : Fragment(), NewsDelegateAdapter.NewsElementClickListener {
    override fun onNewsElementClick(position: Int) {
        val intent = Intent(activity!!, NewsActivity::class.java)
        intent.putExtra("newsElementData", dataSet[position] as NewsElement)
        startActivity(intent)
    }

    private var recyclerView: RecyclerView? = null
    private val KEY_NEWS = "news"
    private val ACTION_CHANGING_FAVOURITES = "tinkoff.hw.fourthrecyclerview.changingFavourites"
    var news = ArrayList<NewsElement>()
    var dataSet = ArrayList<IViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            news = savedInstanceState.getParcelableArrayList(KEY_NEWS)!!
            sortNewsByPubDate()
            updateDataSet()
        } else {
            val loremIpsumString =
                getString(R.string.lorem_ipsum)
            var content = loremIpsumString
            val maxNewsDesc =
                getString(R.string.full_news_desc)
            for (i in 1..30) {
                news.add(
                    NewsElement(
                        randomSizeString(),
                        randomSizeString(1, 200, maxNewsDesc),
                        LocalDate.now().minusDays((i / 3).toLong()),
                        content,
                        i,
                        false
                    )
                )
                content += "\n$i\n$loremIpsumString"
            }
            sortNewsByPubDate()
            updateDataSet()
        }
    }

    fun sortNewsByPubDate() {
        news.sortByDescending { it.publicationDate }
    }

    fun updateDataSet() {
        dataSet.clear()
        if (news.isEmpty()) return
        dataSet.add(DayHeader(news[0].publicationDate))
        dataSet.add(news[0])
        for (i in 1 until news.size) {
            if ((dataSet.last() as NewsElement).publicationDate != news[i].publicationDate) dataSet.add(
                DayHeader(
                    news[i].publicationDate
                )
            )
            dataSet.add(news[i])
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_lastnewstab, container, false)
        recyclerView = rootView.findViewById(R.id.lastnewstab_recyclerview)
        val adapter = CompositeDelegateAdapter.Builder<IViewModel>()
            .add(NewsDelegateAdapter(this))
            .add(DayHeaderDelegateAdapter())
            .build(dataSet)
        //adapter.swapData(dataSet)
        recyclerView?.adapter = adapter
        //recyclerView?.adapter = NewsAdapter(news, this)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(MyItemDecoration(context!!))
        val intentFilter = IntentFilter(ACTION_CHANGING_FAVOURITES)
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(myReceiver, intentFilter)
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_NEWS, news)
    }

    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_CHANGING_FAVOURITES -> {
                    val newNewsElement = intent.getParcelableExtra<NewsElement>("newsElementData")
                    for (i in 0 until news.count())
                        if (news[i].id == newNewsElement.id) {
                            news[i].favourite = newNewsElement.favourite
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