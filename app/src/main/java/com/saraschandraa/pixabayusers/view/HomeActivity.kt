package com.saraschandraa.pixabayusers.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saraschandraa.pixabayusers.databinding.ActivityHomeBinding
import com.saraschandraa.pixabayusers.model.Sports
import com.saraschandraa.pixabayusers.util.EndlessRecyclerViewScrollListener
import com.saraschandraa.pixabayusers.viewmodel.ListViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var listViewModel: ListViewModel

    private lateinit var sportsListAdapter: SportsListAdapter


    private val sportsListObserver = Observer<List<Sports>> { sports ->
        sportsListAdapter.updateList(sports)
    }

    private var currentPage = 1

    private val linearLayoutManger: LinearLayoutManager =
        LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    private var paginationListener =
        object : EndlessRecyclerViewScrollListener(linearLayoutManger) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView?,
                firstVisiblePostion: Int
            ) {
                if (totalItemsCount < 10) {
                    return
                }
                currentPage++
                listViewModel.getValues(currentPage)
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sportsListAdapter = SportsListAdapter(this@HomeActivity, arrayListOf())


        setSupportActionBar(binding.tlHome)

        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        listViewModel.sportsLists.observe(this, sportsListObserver)
        listViewModel.getValues(1)


        binding.rcSports.apply {
            layoutManager = linearLayoutManger
            adapter = sportsListAdapter
            addOnScrollListener(paginationListener)
        }

    }
}