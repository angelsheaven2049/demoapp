package com.angelsheaven.teremdemoapp.ui.listNews.newNews


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.angelsheaven.teremdemoapp.R
import com.angelsheaven.teremdemoapp.data.database.News
import com.angelsheaven.teremdemoapp.ui.MainActivity
import com.angelsheaven.teremdemoapp.ui.listNews.NewsAdapter
import com.angelsheaven.teremdemoapp.utilities.*
import com.angelsheaven.teremdemoapp.widgets.GridItemDecoration
import com.angelsheaven.teremdemoapp.widgets.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_list_event.*

/**
 * A simple [Fragment] subclass.
 */
class ListNewsFragment : Fragment(), MyLogger {

    private val mViewModel by lazy {
        val factory = context?.let {
            provideListNewsFragmentViewModelFactory(it, activity as MainActivity)
        }

        ViewModelProviders.of(this, factory).get(ListNewsFragmentViewModel::class.java)
    }
    private val onUserClickItem: (Int) -> Unit = { newsId ->
        val newsDetailsScreen =
            ListNewsFragmentDirections.moveFromListEventToDetailEvent(newsId)

        findNavController().navigate(newsDetailsScreen)
    }
    private val onUserMarkRead: (News?) -> Unit = { news -> mViewModel.markReadNews(news) }
    private val onUserBookmark: (News?) -> Unit = { news ->
        mViewModel.bookmarkNews(news)
    }
    private val onUserUnBookmark: (News?) -> Unit = { news ->
        mViewModel.unBookmarkNews(news)
    }
    private var mAdapter: NewsAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val lastSearchCondition = savedInstanceState?.getBundle(LAST_SEARCH_QUERY) ?: defaultSearchCondition

        mViewModel.run {
            this.searchCondition = lastSearchCondition
        }
        mViewModel.loadNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        lv_news_items.setHasFixedSize(true)

        lv_news_items.layoutManager = GridLayoutManager(
            context
            , 2
            , GridLayoutManager.VERTICAL
            , false
        )

        initAdapter()

        val padding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)

        lv_news_items.addItemDecoration(GridItemDecoration(padding))

        //Implement swipe to mark read
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(mAdapter))

        itemTouchHelper.attachToRecyclerView(lv_news_items)

        swipe_refresh.setOnRefreshListener {
            mViewModel.loadNews()
        }

        mViewModel.isCompleteReloadingData.observe(this, Observer {
            swipe_refresh.isRefreshing = false
        })

    }

    private fun initAdapter() {
        mAdapter = NewsAdapter(
            onUserClickItem,
            onUserMarkRead,
            onUserBookmark,
            onUserUnBookmark
        )

        lv_news_items.adapter = mAdapter

        mViewModel
            .news.observe(this, Observer {
            mAdapter?.submitList(it)
        })

        mViewModel
            .networkErrors.observe(this, Observer { errorMsg ->
            this@ListNewsFragment.view.let {
                it?.mySnackBar(
                    errorMsg
                    , R.color.snackbar_background_color
                )?.show()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_news_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    mViewModel.run {
                        this.setQueryString(query)
                        this.loadNews()
                    }
                    return false
                }

            })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.refresh -> refreshNews()
            R.id.filter -> filterNews()
            R.id.sort -> sortNews()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshNews() {
        mViewModel.refreshNews()
    }

    private fun filterNews() {
        val filterChoices = resources.getStringArray(R.array.dialog_filter_option_array)
        val itemSelected = mViewModel.getFilterOptionIndex()
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.label_dialog_filter_news_type))
                .setSingleChoiceItems(filterChoices, itemSelected) { _, selectedIndex ->
                    mViewModel.setFilterOptionIndex(selectedIndex)
                }
                .setPositiveButton("Ok") { _, _ ->
                    mViewModel.loadNews()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun sortNews() {

        val sortChoices = resources.getStringArray(R.array.dialog_sort_option_array)
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.label_dialog_sort_news_type))
                .setSingleChoiceItems(sortChoices, mViewModel.getSortOptionIndex()) { _, selectedIndex ->
                    mViewModel.setSortOptionIndex(selectedIndex)
                }
                .setPositiveButton("Ok") { _, _ ->
                    mViewModel.loadNews()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(LAST_SEARCH_QUERY, mViewModel.lastQueryValue())
    }
}

