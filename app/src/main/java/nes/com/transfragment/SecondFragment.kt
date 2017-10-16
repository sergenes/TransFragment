package nes.com.transfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nes.transfragment.BaseTransFragment
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * Created by sergenes on 10/10/17.
 */
class SecondFragment : BaseTransFragment() {

    override fun getFragmentContainer(): Int {
        return R.id.fragmentContainer
    }

    override fun getBackFragmentClass(): Class<*> {
        return FirstFragment::class.java
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View? = inflater!!.inflate(R.layout.fragment_second, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton.setOnClickListener {
            forwardToFragment(ThirdFragment())
        }

    }
}