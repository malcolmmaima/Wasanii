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
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tengenezalabs.wasanii.R
import com.tengenezalabs.wasanii.data.models.responses.Event
import org.jsoup.Jsoup
import java.lang.Exception

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