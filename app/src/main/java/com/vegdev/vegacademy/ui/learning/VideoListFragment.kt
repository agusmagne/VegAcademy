package com.vegdev.vegacademy.ui.learning


import android.content.Context
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
import com.vegdev.vegacademy.IOnFragmentBackPressed
import com.vegdev.vegacademy.IToogleToolbar

import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.Utils.LayoutUtils
import com.vegdev.vegacademy.models.Video
import kotlinx.android.synthetic.main.fragment_video_list.*

/**
 * A simple [Fragment] subclass.
 */
class VideoListFragment : Fragment(), IOnFragmentBackPressed {

    val layoutUtils = LayoutUtils()
    lateinit var firestore: FirebaseFirestore
    lateinit var rvAdapter: FirestoreRecyclerAdapter<Video, VideoListViewHolder>
    lateinit var videoCurrentlyPlaying: Video
    var youTubePlayer: YouTubePlayer? = null
    var youtubeFragment: YouTubePlayerSupportFragmentX? = null
    var readyToExit: Boolean = true
    lateinit var trans: FragmentTransaction
    var videoBeingLoaded: String = ""
    var IToogleToolbar: IToogleToolbar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IToogleToolbar) {
            IToogleToolbar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        IToogleToolbar?.toolbarOn()
        IToogleToolbar = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeYoutubePlayer(fragment_video_list)
        rvAdapter = VideoListRvAdapter().fetchVideos(firestore) { video ->

            readyToExit = false
            videoCurrentlyPlaying = video

            if (youTubePlayer == null) {
                fragment_video_list.transitionToState(R.id.onclick)
            } else {
                if (videoBeingLoaded != video.link) {
                    youTubePlayer?.loadVideo(video.link)
                    videoBeingLoaded = video.link
                } else {
                    youTubePlayer?.release()
                    layoutUtils.createToast(requireContext(), "Ya estás reproduciendo este video")
                }
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
                if (p1 == R.id.onclick) {
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
                                IToogleToolbar?.toolbarOff()
                                p1?.loadVideo(videoCurrentlyPlaying.link)
                                youTubePlayer = p1!!
                                videoBeingLoaded = videoCurrentlyPlaying.link
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
            }
        })
    }

    override fun onFragmentBackPressed(): Boolean {
        return if(!readyToExit){
            youTubePlayer?.release()
            youTubePlayer = null

            src.elevation = 20f
            back.elevation = 20f
            who.elevation = 20f
            title.elevation = 20f
            videos_rv.elevation = 20f
            fragment_video_list.transitionToState(R.id.expanded)
            fragment_video_list.rebuildScene()

            readyToExit = true
            true
        } else {
            false
        }
    }
}