package com.tengenezalabs.wasanii.ui.viewevent

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tengenezalabs.wasanii.R
import com.tengenezalabs.wasanii.databinding.ActivityViewEventBinding
import java.text.SimpleDateFormat
import java.util.*

class ViewEvent : AppCompatActivity() {

    private lateinit var binding: ActivityViewEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventTitle = intent.getStringExtra("eventTitle")
        val eventDescription = intent.getStringExtra("eventDescription")
        val eventContent = intent.getStringExtra("eventContent")
        val eventImage = intent.getStringExtra("eventImage")
        val eventLink = intent.getStringExtra("eventLink")
        val eventDate = intent.getStringExtra("eventPubDate")

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = " "
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "In Development...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        try {
        Picasso.get()
            .load(eventImage)
            .error(R.drawable.default_event)
            .into(binding.appBar.findViewById(R.id.eventImage), object : Callback {
                override fun onSuccess() {
                    //
                }
                override fun onError(e: Exception?) {
                    Log.e("ViewEvent", e.toString())
                }
            })
        } catch (e: Exception) {
            Log.e("ViewEvent", e.toString())
        }

        binding.eventContent.text = Html.fromHtml("<h1>$eventTitle</h1>\n\n<p>${eventContent}</p></br><b>Posted: ${formatDate(eventDate!!)}</b>", Html.FROM_HTML_OPTION_USE_CSS_COLORS)
        binding.eventContent.movementMethod = LinkMovementMethod.getInstance()
    }

    //a function that format date to n days ago, n hours ago, n minutes ago, n seconds ago and returns it
    private fun formatDate(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val now = Calendar.getInstance().time
        val then = dateFormat.parse(date)
        val diff = now.time - then.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        if (days > 0) {
            return "$days days ago"
        } else if (hours > 0) {
            return "$hours hours ago"
        } else if (minutes > 0) {
            return "$minutes minutes ago"
        } else {
            return "$seconds seconds ago"
        }
    }
}