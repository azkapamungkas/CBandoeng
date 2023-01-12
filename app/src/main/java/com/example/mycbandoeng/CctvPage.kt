package com.example.mycbandoeng

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class CctvPage : AppCompatActivity() {

    private lateinit var root: ConstraintLayout
    private lateinit var videoView: StyledPlayerView

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSource: MediaSource

    private lateinit var urlType: URLType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cctv_page)

        supportActionBar?.hide()

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        findView()
        initPlayer()

        val title : TextView = findViewById(R.id.title)
        title.text = intent.getStringExtra("title")

    }

    private fun findView() {
        root = findViewById(R.id.root)
        videoView = findViewById(R.id.videoView)
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.addListener(playerListener)

        videoView.player = exoPlayer

        createMediaSource()

        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
    }

    private fun createMediaSource() {
        urlType = URLType.HLS

        urlType.url = intent.getStringExtra("link").toString()

        val dataSourceFactory :DataSource.Factory =DefaultDataSource.Factory(this)

        mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(Uri.parse(urlType.url))
        )
    }

    override fun onResume() {
        super.onResume()

        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()

        exoPlayer.removeListener(playerListener)
        exoPlayer.stop()
        exoPlayer.clearMediaItems()

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private var playerListener = object : Player.Listener {
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()

            videoView.useController = true
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)

            Toast.makeText(this@CctvPage, "${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

enum class URLType(var url: String) {
    HLS("")
}