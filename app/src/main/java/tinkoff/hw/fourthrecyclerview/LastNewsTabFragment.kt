package tinkoff.hw.fourthrecyclerview

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment


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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lastnewstab, container, false)
    }
}