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
import com.tengenezalabs.wasanii.data.models.responses.Event
import com.tengenezalabs.wasanii.data.respository.APIResource
import com.tengenezalabs.wasanii.databinding.HomeFragmentBinding
import com.tengenezalabs.wasanii.ui.main.MainActivity
import com.tengenezalabs.wasanii.utils.apiKey
import com.tengenezalabs.wasanii.utils.fetchFrom
import com.tengenezalabs.wasanii.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val events = arrayListOf<Event>()

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private val count = 20
    private var fetchFromURL = fetchFrom[0]

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
        /**
         * Category buttons
         */
        setButtonBackground(binding.allBTN)

        binding.allBTN.setOnClickListener {
            setButtonBackground(binding.allBTN)
            viewModel.getEvents(fetchFrom[0], apiKey, count)
        }
        binding.artExhibitsBTN.setOnClickListener {
            setButtonBackground(binding.artExhibitsBTN)
            viewModel.getEvents(fetchFromCategory("art-exhibits"), apiKey, count)
        }
        binding.concertsBTN.setOnClickListener {
            setButtonBackground(binding.concertsBTN)
            viewModel.getEvents(fetchFromCategory("concerts"), apiKey, count)
        }
        binding.partiesBTN.setOnClickListener {
            setButtonBackground(binding.partiesBTN)
            viewModel.getEvents(fetchFromCategory("parties"), apiKey, count)
        }
        binding.moviesBTN.setOnClickListener {
            setButtonBackground(binding.moviesBTN)
            viewModel.getEvents(fetchFromCategory("movies"), apiKey, count)
        }
        binding.playsBTN.setOnClickListener {
            setButtonBackground(binding.playsBTN)
            viewModel.getEvents(fetchFromCategory("theater-performances"), apiKey, count)
        }
        binding.charityBTN.setOnClickListener {
            setButtonBackground(binding.charityBTN)
            viewModel.getEvents(fetchFromCategory("charity"), apiKey, count)
        }
        binding.openmicBTN.setOnClickListener {
            setButtonBackground(binding.openmicBTN)
            viewModel.getEvents(fetchFromCategory("open-mic-2"), apiKey, count)
        }
        binding.festivalsBTN.setOnClickListener {
            setButtonBackground(binding.festivalsBTN)
            viewModel.getEvents(fetchFromCategory("festival"), apiKey, count)
        }
        binding.lecturesBTN.setOnClickListener {
            setButtonBackground(binding.lecturesBTN)
            viewModel.getEvents(fetchFromCategory("lectures-readings"), apiKey, count)
        }
        binding.fashionBTN.setOnClickListener {
            setButtonBackground(binding.fashionBTN)
            viewModel.getEvents(fetchFromCategory("fashion"), apiKey, count)
        }
        binding.classesBTN.setOnClickListener {
            setButtonBackground(binding.classesBTN)
            viewModel.getEvents(fetchFromCategory("classes"), apiKey, count)
        }
        binding.outoftownBTN.setOnClickListener {
            setButtonBackground(binding.outoftownBTN)
            viewModel.getEvents(fetchFromCategory("out-of-town"), apiKey, count)
        }
        /**
         * Category buttons
         */

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun fetchFromCategory(category: String): String {
        fetchFromURL = fetchFromURL.replaceBeforeLast("/feed", "category/$category")
        fetchFromURL = fetchFrom[0].replaceAfterLast(".com/", fetchFromURL)
        return fetchFromURL
    }

    private fun setButtonBackground(button: View) {
        binding.allBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.artExhibitsBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.concertsBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.partiesBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.moviesBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.playsBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.charityBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.openmicBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.festivalsBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.lecturesBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.fashionBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.classesBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)
        binding.outoftownBTN.setBackgroundResource(R.drawable.round_shape_inactive_btn)

        button.setBackgroundResource(R.drawable.round_shape_active_btn)
    }

    private fun showEmptyState(visible: Int) {
        binding.emptyIcon.visibility = visible
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        (activity as MainActivity).supportActionBar?.title = "Arts, culture and events"

        viewModel.getEvents(fetchFrom[0], apiKey, count)

        viewModel.events.observe(viewLifecycleOwner) { it ->
            when (it) {
                is APIResource.Success -> {
                    if (events.isNotEmpty()) events.clear()

                    binding.swipeContainer.isRefreshing = false

                    it.value.items.forEach {
                        events.add(it)
                    }

                    Log.d("HomeFragment", "Success: ${it}")

                    //filter events by only showing events whose pubDate is from this year
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                    val filteredEvents = events.filter { it.pubDate.contains(currentYear) }

                    if (filteredEvents.isEmpty()) {
                        showEmptyState(View.VISIBLE)
                    } else {
                        showEmptyState(View.GONE)
                    }

                    //Setup RecyclerView
                    try {
                        var eventsAdapter = EventsAdapter(requireActivity(), filteredEvents)
                        binding.recyclerView.itemAnimator = DefaultItemAnimator()
                        binding.recyclerView.adapter = eventsAdapter
                        eventsAdapter.saveData(filteredEvents)
                        binding.recyclerView.scrollToPosition(
                            0
                        )
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
        viewModel.getEvents(fetchFromURL, apiKey, count)
    }

    override fun onRefresh() {
        fetchEvents()
    }
}