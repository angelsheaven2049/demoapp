package com.angelsheaven.teremdemoapp.ui.viewNewsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.angelsheaven.teremdemoapp.databinding.FragmentViewEventDetailBinding
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import com.angelsheaven.teremdemoapp.utilities.provideViewNewsDetailFragmentViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class ViewNewsDetailFragment : Fragment(), MyLogger {

    private val disposable = CompositeDisposable()

    private val viewModel by lazy {
        val factory: ViewNewsDetailFragmentViewModelFactory?
                = context?.let { provideViewNewsDetailFragmentViewModelFactory(it,activity as FragmentActivity) }
        ViewModelProviders.of(this,factory).get(ViewNewsDetailFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentViewEventDetailBinding.inflate(inflater, container, false)

        binding.viewmodel = viewModel
        arguments?.let { bundle ->
            val safeArgs = ViewNewsDetailFragmentArgs.fromBundle(bundle)

            val mNewsId = safeArgs.newsId

            viewModel.getNewsDetailObservable(mNewsId)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ returnedNewsDetail ->
                    binding.viewmodel?.newsDetail?.set(returnedNewsDetail)
                }, { error ->
                    log("Unable to get news detail ${error.message}")
                })
                ?.let {
                    disposable.add(
                        it
                    )
                }
        }

        return binding.root
    }

}
