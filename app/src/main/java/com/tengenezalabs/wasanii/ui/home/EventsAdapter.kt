package com.tengenezalabs.wasanii.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tengenezalabs.wasanii.R
import com.tengenezalabs.wasanii.data.responses.Event
import org.jsoup.Jsoup
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.lang.Exception

class EventsAdapter(context: Context, listdata: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.MyHolder>() {
    var context: Context = context
    var listdata: List<Event> = listdata
    var DURATION: Long = 200
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_event, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val eventModel: Event = listdata[position]
        /**
         * Adapter animation
         */
        setAnimation(holder.itemView, position)
        /**
         * Set widget values
         */
        holder.eventTitle.text = eventModel.title

        /**
         * Click listener on our card
         */
        holder.cardView.setOnClickListener {
            Toast.makeText(context, eventModel.title, Toast.LENGTH_SHORT).show()
        }

        /**
         * Load image url onto imageview
         */

        try {
            Picasso.get()
                .load(getCoverImage(eventModel.content_html))
                .error(R.drawable.default_event)
                .into(holder.eventImage, object : Callback {
                    override fun onSuccess() {
                        //
                    }
                    override fun onError(e: Exception?) {
                        Log.e("Error Loading Image: ", e.toString())
                    }
                })
        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) }
        }
    }

    /** receives content_htmland e.g.
     * "<figure class=\"wp-block-image size-large\"><a href=\"https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg\"><img data-attachment-id=\"40798\" data-permalink=\"https://nairobinow.wordpress.com/blowing-in-the-wind-ow/\" data-orig-file=\"https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg\" data-orig-size=\"800,558\" data-comments-opened=\"1\" data-image-meta=\"{&quot;aperture&quot;:&quot;0&quot;,&quot;credit&quot;:&quot;&quot;,&quot;camera&quot;:&quot;&quot;,&quot;caption&quot;:&quot;&quot;,&quot;created_timestamp&quot;:&quot;0&quot;,&quot;copyright&quot;:&quot;&quot;,&quot;focal_length&quot;:&quot;0&quot;,&quot;iso&quot;:&quot;0&quot;,&quot;shutter_speed&quot;:&quot;0&quot;,&quot;title&quot;:&quot;&quot;,&quot;orientation&quot;:&quot;0&quot;}\" data-image-title=\"blowing-in-the-wind-ow\" data-image-description=\"\" data-image-caption=\"\" data-medium-file=\"https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg?w=300\" data-large-file=\"https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg?w=455\" src=\"https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg?w=800\" alt=\"\" class=\"wp-image-40798\" srcset=\"https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg 800w, https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg?w=150 150w, https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg?w=300 300w, https://nairobinow.files.wordpress.com/2022/03/blowing-in-the-wind-ow.jpg?w=768 768w\" sizes=\"(max-width: 800px) 100vw, 800px\" /></a><figcaption>Image via the Gallery.</figcaption></figure>\n\n\n\n<p>Opening: Sunday 27th March 2022 </p>\n\n\n\n<p>Venue: Red Hill Art Gallery</p>\n\n\n\n<p>Time: from 11.00 am to 5.00 pm.<br><br>The exhibition is on view until 8th May 2022.</p>"
     * and returns value of data-large-file
     */
    private fun getCoverImage(contentHtml: String): String {
        return try {
            val doc: org.jsoup.nodes.Document? = Jsoup.parse(contentHtml)
            val img: org.jsoup.nodes.Element? = doc!!.select("img").first()
            val src: String = img!!.attr("data-medium-file")
            src
        } catch (e: Exception) {
            Log.e("Error: ", e.toString())
            ""
        }
    }

    /**
     * @https://medium.com/better-programming/android-recyclerview-with-beautiful-animations-5e9b34dbb0fa
     */
    private fun setAnimation(itemView: View, i: Int) {
        var i = i
        val on_attach = true
        if (!on_attach) {
            i = -1
        }
        val isNotFirstItem = i == -1
        i++
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animator.startDelay = if (isNotFirstItem) DURATION / 2 else i * DURATION / 3
        animator.duration = 500
        animatorSet.play(animator)
        animator.start()
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventImage: ImageView
        var eventTitle: TextView
        var cardView: CardView

        init {
            eventImage = itemView.findViewById(R.id.eventImage)
            cardView = itemView.findViewById(R.id.card_view)
            eventTitle = itemView.findViewById(R.id.eventTitleTV)

            //Long Press
            itemView.setOnLongClickListener { false }
        }
    }

}