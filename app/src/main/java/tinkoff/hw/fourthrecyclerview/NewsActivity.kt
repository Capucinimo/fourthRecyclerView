package tinkoff.hw.fourthrecyclerview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.time.Duration
import java.time.format.DateTimeFormatter

class NewsActivity : AppCompatActivity() {
    private var newsElement = NewsElement()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsElement = intent.getParcelableExtra("newsElementData")
        supportActionBar?.title = newsElement.title
        findViewById<TextView>(R.id.news_content).text = newsElement.content
        findViewById<TextView>(R.id.news_pubdate).text =
            newsElement.publicationDate.format(DateTimeFormatter.ofPattern("d MMMM, yyyy"))
    }

    private var menu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourite, menu)
        this.menu = menu
        updateAddToFavouriteMenuItem()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_to_favourites) {
            onMenuAddToFavouritesClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var toastObject: Toast? = null
    private fun onMenuAddToFavouritesClicked() {
        intent.putExtra("newsElementData", newsElement).action = "tinkoff.hw.fourthrecyclerview.changingFavourites"
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast((intent))
        toastObject?.cancel()
        toastObject = Toast.makeText(
            this,
            if (newsElement.favourite) "Новость удалена из избранного" else "Новость добавлена в избранное",
            Toast.LENGTH_SHORT
        )
        toastObject?.show()
        newsElement.favourite = !newsElement.favourite
        updateAddToFavouriteMenuItem()
    }

    private fun updateAddToFavouriteMenuItem() {
        val addToFavouritesMenuItem = menu?.findItem(R.id.add_to_favourites)
        if (newsElement.favourite) {
            addToFavouritesMenuItem?.title = "\uD83D\uDDD1"
        } else {
            addToFavouritesMenuItem?.title = "⭐"
        }
    }
}