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
class ThirdFragment : BaseTransFragment() {
    override fun getFragmentContainer(): Int {
        return R.id.fragmentContainer
    }

    override fun getBackFragmentClass(): Class<*> {
        return SecondFragment::class.java
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View? = inflater!!.inflate(R.layout.fragment_third, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton.setOnClickListener {
            performBack()
        }


    }
}