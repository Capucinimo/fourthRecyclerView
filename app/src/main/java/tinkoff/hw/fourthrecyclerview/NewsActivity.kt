package tinkoff.hw.fourthrecyclerview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.time.format.DateTimeFormatter

class NewsActivity : AppCompatActivity() {
    var newsElement = NewsElement()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsElement = intent.getParcelableExtra<NewsElement>("newsElementData")
        supportActionBar?.title = newsElement.title
        findViewById<TextView>(R.id.news_content).text = newsElement.content
        findViewById<TextView>(R.id.news_pubdate).text =
            newsElement.publicationDate?.format(DateTimeFormatter.ofPattern("d MMMM, yyyy"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_to_favourites) {
            onMenuAddToFavouritesClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onMenuAddToFavouritesClicked() {
        newsElement.favourite = !newsElement.favourite!!
        intent.putExtra("newsElementData", newsElement).action = "tinkoff.hw.fourthrecyclerview.changingFavourites"
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast((intent))
    }
}