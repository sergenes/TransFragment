package nes.com.transfragment

import android.os.Bundle
import com.nes.transfragment.BaseTransActivity
import com.nes.transfragment.BaseTransFragment

class TransDemoActivity : BaseTransActivity() {

    override fun getAnimationType(): BaseTransFragment.AnimationType {
        return if (super.getAnimationType() != null) {
            super.getAnimationType()
        } else BaseTransFragment.AnimationType.SLIDE_HORISONTAL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_demo)

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, FirstFragment())
                .commit()
    }
}
