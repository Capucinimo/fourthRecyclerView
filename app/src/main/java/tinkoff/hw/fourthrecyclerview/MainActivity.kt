package tinkoff.hw.fourthrecyclerview

/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}*/
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity() {
    private var adapter: TabAdapter? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewpager)
        tabLayout = findViewById(R.id.sliding_tabs)
        adapter = TabAdapter(supportFragmentManager)
        adapter!!.addFragment(LastNewsTabFragment(), "Последние")
        adapter!!.addFragment(FavouritesTabFragment(), "Избранное")
        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)
        supportActionBar?.hide()
    }
}
private class TabAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {
    private val mFragmentList :ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList :ArrayList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        // в этом методе мы должны вернуть экземпляр фрагмента для страницы на позиции position
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        // количество страниц
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}
/*
class MainActivity : AppCompatActivity(), ColorFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            onMenuAddClicked()
            return true
        }
        else if (item.itemId == R.id.action_delete) {
            onMenuDeleteClicked()
            supportActionBar?.hide()
            supportActionBar?.show()
            supportActionBar?.title = "Пока"
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onColorClick(color: Int) {
        // при клике на цвет просто показываем его значение
        Toast.makeText(this, Integer.toHexString(color), Toast.LENGTH_SHORT).show()
    }

    private fun onMenuAddClicked() {
        // при клике на пункт меню добавляем новый фрагмент с ViewPager'ом
        supportFragmentManager.beginTransaction()
            // добавляем анимации
            .setCustomAnimations(R.animator.enter, R.animator.exit)
            // добавляем фрагмент в container
            .add(R.id.container, ColorPagerFragment.newInstance(4))
            // добавляем в бэкстек
            .addToBackStack(null)
            // записываем изменения
            .commit()
    }

    private fun onMenuDeleteClicked() {
        // при клике на пункт меню добавляем новый фрагмент с ViewPager'ом
        supportFragmentManager.beginTransaction()
            // добавляем анимации
            .setCustomAnimations(R.animator.enter, R.animator.exit)
            // добавляем фрагмент в container
            .remove(ColorFragment())
            // добавляем в бэкстек
            //.addToBackStack(null)

            // записываем изменения
            .commit()
        supportFragmentManager.popBackStack()
    }
}
*/