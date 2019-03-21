package com.angelsheaven.teremdemoapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.angelsheaven.teremdemoapp.R
import kotlinx.android.synthetic.main.layout_progress_bar.*

class SpinningSynchDataDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_progress_bar, container)
    }

    init {
        this.isCancelable = false
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
    }

    companion object {

        private var mFragment: SpinningSynchDataDialogFragment? = null

        @Synchronized
        fun newInstance(): SpinningSynchDataDialogFragment? {
            if (mFragment == null) {
                mFragment = SpinningSynchDataDialogFragment()
            }

            return mFragment
        }

        fun display(fragmentManager: FragmentManager?, tag: String) {
            newInstance()?.let { dialog->
                if (!dialog.isAdded) {
                    fragmentManager?.let { manager->
                        dialog.show(manager, tag)
                    }
                }
            }

        }

        fun hide() {
            newInstance()?.run{
                if(this.isVisible)
                    dismiss()
            }
        }
    }

    fun updateNewsLeft(numberNewsLeft:Int){
        tv_donwload_news_detail.text = "Downloading $numberNewsLeft news left"
    }

}
