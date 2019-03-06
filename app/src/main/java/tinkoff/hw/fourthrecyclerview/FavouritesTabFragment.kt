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


class FavouritesTabFragment : Fragment(), NewsAdapter.Listener {
    override fun onNewsElementClick(position: Int, newsElement: NewsElement) {
        val intent = Intent(activity!!, NewsActivity::class.java)
        intent.putExtra("newsElementData", newsElement)
        startActivity(intent)
    }

    private val KEY_NEWS = "news"
    var favNews = ArrayList<NewsElement>()
    var recyclerView:RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favouritestab, container, false)
        recyclerView = rootView.findViewById(R.id.favouritestab_recyclerview)
        recyclerView!!.adapter = NewsAdapter(favNews,this)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(MyItemDecoration(activity!!))
        val intentFilter = IntentFilter("tinkoff.hw.fourthrecyclerview.changingFavourites")
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(myReceiver, intentFilter)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            favNews = savedInstanceState.getParcelableArrayList(KEY_NEWS)!!
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_NEWS, favNews)
    }
    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                "tinkoff.hw.fourthrecyclerview.changingFavourites" -> {
                    val newsElement = intent.getParcelableExtra<NewsElement>("newsElementData")
                    if (newsElement.favourite) {
                        favNews.add(newsElement)
                        recyclerView?.adapter?.notifyItemInserted(favNews.count()-1)
                    }
                    else for (i in 0 until favNews.size)
                        if (newsElement.id == favNews[i].id){
                            favNews.remove(favNews[i])
                            recyclerView?.adapter?.notifyItemRemoved(i)
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