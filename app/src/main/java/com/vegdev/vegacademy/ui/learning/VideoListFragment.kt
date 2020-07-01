package com.vegdev.vegacademy.ui.learning


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.InhabilitateToolbar

import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Video
import kotlinx.android.synthetic.main.fragment_video_list.*

/**
 * A simple [Fragment] subclass.
 */
class VideoListFragment : Fragment() {

    lateinit var firestore: FirebaseFirestore
    lateinit var rvAdapter: FirestoreRecyclerAdapter<Video, VideoListViewHolder>
    lateinit var videoPlaying: Video
    var videoPlayer: YouTubePlayer? = null
    var youtubeFragment: YouTubePlayerSupportFragmentX? = null
    var readyToExit: Boolean = true
    lateinit var trans: FragmentTransaction
    var inhabilitateToolbar: InhabilitateToolbar? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = VideoListRvAdapter().fetchVideos(firestore) { video ->
//            fragment_video_list.transitionToState(R.id.onclick)

            readyToExit = false
            videoPlaying = video

            if (videoPlayer == null) {
                initializeYoutubePlayer(fragment_video_list)
            } else {
                videoPlayer?.loadVideo(video.link)
            }
        }

        videos_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        rvAdapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        rvAdapter.stopListening()


    }

    fun initializeYoutubePlayer(view: MotionLayout) {
        view.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(
                p0: MotionLayout?,
                p1: Int,
                p2: Boolean,
                p3: Float
            ) {
            }


            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(
                p0: MotionLayout?,
                p1: Int,
                p2: Int,
                p3: Float
            ) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                val youTubePlayerSupportFragmentX =
                    YouTubePlayerSupportFragmentX.newInstance()
                youtubeFragment = youTubePlayerSupportFragmentX
                val transaction = childFragmentManager.beginTransaction()
                trans = transaction
                transaction.add(R.id.player_inlist, youTubePlayerSupportFragmentX).commit()
                youTubePlayerSupportFragmentX.initialize(
                    resources.getString(R.string.API_KEY),
                    object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubePlayer?,
                            p2: Boolean
                        ) {
                            p1?.loadVideo(videoPlaying.link)
                            videoPlayer = p1!!
                        }

                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {
                            TODO("Not yet implemented")
                        }
                    }
                )
            }
        })
    }
}