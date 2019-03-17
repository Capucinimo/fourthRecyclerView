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


class FavouritesTabFragment : Fragment(), NewsDelegateAdapter.NewsElementClickListener {
    override fun onNewsElementClick(position: Int) {
        val intent = Intent(activity!!, NewsActivity::class.java)
        intent.putExtra("newsElementData", dataSet[position] as NewsElement)
        startActivity(intent)
    }
    fun sortNewsByPubDate() {
        //sortedList = ArrayList(news.sortedWith(compareByDescending { it.publicationDate }))
        favNews.sortByDescending { it.publicationDate }
    }

    fun updateDataSet() {
        dataSet.clear()
        if (favNews.isEmpty()) return
        dataSet.add(DayHeader(favNews[0].publicationDate))
        dataSet.add(favNews[0])
        for (i in 1 until favNews.size) {
            if ((dataSet.last() as NewsElement).publicationDate != favNews[i].publicationDate) dataSet.add(
                DayHeader(
                    favNews[i].publicationDate
                )
            )
            dataSet.add(favNews[i])
        }
    }
    private val ACTION_CHANGING_FAVOURITES = "tinkoff.hw.fourthrecyclerview.changingFavourites"
    private val KEY_NEWS = "news"
    var favNews = ArrayList<NewsElement>()
    var dataSet = ArrayList<IViewModel>()
    var recyclerView:RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favouritestab, container, false)
        recyclerView = rootView.findViewById(R.id.favouritestab_recyclerview)
        val adapter = CompositeDelegateAdapter.Builder<IViewModel>()
            .add(NewsDelegateAdapter(this))
            .add(DayHeaderDelegateAdapter())
            .build(dataSet)
        //adapter.swapData(dataSet)
        recyclerView?.adapter = adapter
        //recyclerView?.adapter = NewsAdapter(favNews,this)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(MyItemDecoration(activity!!))
        val intentFilter = IntentFilter(ACTION_CHANGING_FAVOURITES)
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(myReceiver, intentFilter)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            favNews = savedInstanceState.getParcelableArrayList(KEY_NEWS)!!
            sortNewsByPubDate()
            updateDataSet()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_NEWS, favNews)
    }
    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_CHANGING_FAVOURITES -> {
                    val newsElement = intent.getParcelableExtra<NewsElement>("newsElementData")
                    if (newsElement.favourite) {
                        favNews.add(newsElement)
                        sortNewsByPubDate()
                        updateDataSet()
                        //(recyclerView?.adapter as CompositeDelegateAdapter<IViewModel>).swapData(dataSet)
                        recyclerView?.adapter?.notifyDataSetChanged()
                        //recyclerView?.adapter?.notifyItemInserted(favNews.count()-1)
                    }
                    else for (i in 0 until favNews.size)
                        if (newsElement.id == favNews[i].id){
                            favNews.remove(favNews[i])
                            updateDataSet()
                            recyclerView?.adapter?.notifyDataSetChanged()
                            //recyclerView?.adapter?.notifyItemRemoved(i)
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