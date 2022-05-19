package com.tengenezalabs.wasanii.ui.viewevent

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tengenezalabs.wasanii.R
import com.tengenezalabs.wasanii.data.models.responses.Event
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(context: Context, listdata: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.MyHolder>() {
    var context: Context = context
    var listdata: List<Event> = listdata
    private var DURATION: Long = 200

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_event, parent, false)
        return MyHolder(view)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(
            oldItem: Event,
            newItem: Event
        ): Boolean {
            return oldItem.guid == newItem.guid

        }

        override fun areContentsTheSame(
            oldItem: Event,
            newItem: Event
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(usersResponse: List<Event>) {
        asyncListDiffer.submitList(usersResponse)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val eventModel: Event = listdata[position]

        if(eventModel != null){
            /**
             * Adapter animation
             */
            setAnimation(holder.itemView, position)
            /**
             * Set widget values
             */
            holder.eventTitle.text = eventModel.title
            holder.postedOn.text = "Posted on: ${formatTime(eventModel.pubDate)}"

            /**
             * Click listener on our card
             */
            holder.cardView.setOnClickListener {
                val intent = Intent(context, ViewEvent::class.java)
                intent.putExtra("eventTitle", eventModel.title)
                intent.putExtra("eventDescription", eventModel.description)
                intent.putExtra("eventContent", eventModel.content)
                intent.putExtra("eventImage", eventModel.thumbnail)
                intent.putExtra("eventLink", eventModel.link)
                intent.putExtra("eventPubDate", eventModel.pubDate)

                context.startActivity(intent)
            }

            /**
             * Load image url onto imageview
             */

            try {
                if(eventModel.thumbnail.isNullOrBlank()) {
                    holder.eventImage.setImageResource(R.drawable.default_event)
                } else {
                    Picasso.get()
                        .load(eventModel.thumbnail)
                        .error(R.drawable.default_event)
                        .into(holder.eventImage, object : Callback {
                            override fun onSuccess() {
                                //
                            }
                            override fun onError(e: Exception?) {
                                Log.e("Error Loading Image: ", e.toString())
                            }
                        })
                }

            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
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
        var postedOn: TextView
        var cardView: CardView

        init {
            eventImage = itemView.findViewById(R.id.eventImage)
            cardView = itemView.findViewById(R.id.card_view)
            eventTitle = itemView.findViewById(R.id.eventTitleTV)
            postedOn = itemView.findViewById(R.id.postedOnTV)

            //Long Press
            itemView.setOnLongClickListener { false }
        }
    }

    /**
     * a function that takes a date in format 2022-04-09 03:43:35 and return in format Saturday, April 9, 2022
     */
    fun formatTime(date: String): String {
        val dateFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        val dateObj = sdf.parse(date)
        val newFormat = "EEEE, MMMM d, yyyy"
        val finalDate = SimpleDateFormat(newFormat, Locale.getDefault())
        return finalDate.format(dateObj)
    }
}