package com.example.mediastreaming.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediastreaming.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes

class ViewPagerAdapter(
    private val url1: String,
    private val url2: String,
    private val url3: String, var context: Context
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    val mediaUrls = listOf(url1, url2, url3)
    private var player: ExoPlayer? = null

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var exoPlayer: com.google.android.exoplayer2.ui.PlayerView  = itemView.findViewById(R.id.exo_fullscreen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val media = mediaUrls[position]
      //  passMediaUrl.onMediaUrlSuccess(media, holder)
        initializePlayer(holder, media)

    }

    private fun initializePlayer(holder: ViewPagerViewHolder, media: String) {

        player = ExoPlayer.Builder(context) // <- context
            .build()

        // create a media item.
        val mediaItem = MediaItem.Builder()
            .setUri(media)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()

        // Create a media source and pass the media item
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(context) // <- context
        )
            .createMediaSource(mediaItem)

        // Finally assign this media source to the player
        player!!.apply {
            setMediaSource(mediaSource)
            playWhenReady = true // start playing when the exoplayer has setup
            seekTo(0, 0L) // Start from the beginning
            prepare() // Change the state from idle.
        }.also {
            // Do not forget to attach the player to the view
            holder.exoPlayer.player = it
        }
    }

    override fun getItemCount(): Int {
        return mediaUrls.size
    }

}