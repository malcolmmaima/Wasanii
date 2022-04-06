package com.tengenezalabs.wasanii.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tengenezalabs.wasanii.R
import com.tengenezalabs.wasanii.data.responses.Event
import com.tengenezalabs.wasanii.data.respository.APIResource
import com.tengenezalabs.wasanii.databinding.HomeFragmentBinding
import com.tengenezalabs.wasanii.ui.main.MainActivity
import com.tengenezalabs.wasanii.utils.fetchFrom
import com.tengenezalabs.wasanii.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val events = arrayListOf<Event>()

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.swipeContainer.setOnRefreshListener(this)
        binding.swipeContainer.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )

        binding.swipeContainer.post {
            events.clear()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun showEmptyState(visible: Int) {
        binding.emptyIcon.visibility = visible
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        (activity as MainActivity).supportActionBar?.title = "Arts, culture and events"

        viewModel.getEvents(fetchFrom)

        viewModel.events.observe(viewLifecycleOwner) { it ->
            when (it) {
                is APIResource.Success -> {
                    if (events.isNotEmpty()) events.clear()

                    binding.swipeContainer.isRefreshing = false

                    it.value.items.forEach {
                        events.add(it)
                    }

                    Log.d("HomeFragment", "Success: ${it}")

                    if (events.size == 0) {
                        showEmptyState(View.VISIBLE)

                    } else {
                        showEmptyState(View.GONE)
                    }

                    //Setup RecyclerView
                    try {
                        var eventsAdapter = EventsAdapter(requireActivity(), events)
                        binding.recyclerView.itemAnimator = DefaultItemAnimator()
                        binding.recyclerView.adapter = eventsAdapter
                    } catch (e: Exception) {
                        Log.d("HomeFragment", "Error: ${e.message}")
                    }

                }

                is APIResource.Loading -> {
                    binding.swipeContainer.isRefreshing = true
                    events.clear()
                }

                is APIResource.Error -> {
                    binding.root.handleApiError(it)
                    binding.swipeContainer.isRefreshing = false
                    showEmptyState(View.VISIBLE)
                    events.clear()
                    var eventsAdapter = EventsAdapter(requireActivity(), events)
                    binding.recyclerView.itemAnimator = DefaultItemAnimator()
                    binding.recyclerView.adapter = eventsAdapter
                    Log.d("HomeFragment", "Error: $it")
                }
            }
        }
    }

    private fun fetchEvents() {
        events.clear()
        viewModel.getEvents(fetchFrom)
    }

    override fun onRefresh() {
        fetchEvents()
    }
}