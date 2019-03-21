package com.angelsheaven.teremdemoapp.ui.listNews.savedNews


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.angelsheaven.teremdemoapp.R
import com.angelsheaven.teremdemoapp.data.database.News
import com.angelsheaven.teremdemoapp.ui.MainActivity
import com.angelsheaven.teremdemoapp.ui.listNews.NewsAdapter
import com.angelsheaven.teremdemoapp.utilities.provideListSavedNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.widgets.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_bookmark_news.*

/**
 * A simple [Fragment] subclass.
 */
class ListSavedNewsFragment : Fragment() {

    private val mViewModel by lazy {
        val factory =
            context?.let { provideListSavedNewsFragmentViewModelFactory(it, activity as MainActivity) }
        ViewModelProviders.of(this, factory)
            .get(ListSavedNewsFragmentViewModel::class.java)
    }

    private var mAdapter: NewsAdapter? = null

    private val onUserClickItem: (Int) -> Unit = { newsId ->
        val newsDetailsScreen =
            ListSavedNewsFragmentDirections.actionBookmarkNewsFragmentToViewNewsDetailDest(newsId)
        findNavController().navigate(newsDetailsScreen)
    }

    private val onUserUnBookmark: (News?) -> Unit = { news ->
        mViewModel.unBookmarkNews(news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.loadSavedNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        lv_saved_news_items.setHasFixedSize(true)

        initAdapter()

        val padding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)

        lv_saved_news_items.addItemDecoration(MarginItemDecoration(padding))
    }

    private fun initAdapter() {
        mAdapter = NewsAdapter(
            onUserClickItem,
            null,
            null,
            onUserUnBookmark
        )

        lv_saved_news_items.adapter = mAdapter

        mViewModel
            .savedNews.observe(this, Observer {
            mAdapter?.submitList(it)
            mAdapter?.notifyDataSetChanged()
        })

    }

}
