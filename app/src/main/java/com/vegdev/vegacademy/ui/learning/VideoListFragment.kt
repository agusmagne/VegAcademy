package com.vegdev.vegacademy.ui.learning


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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

    private val VEGACADEMY_POSITION = 0
    private val CEVA_POSITION = 1
    private val CARNISM_POSITION = 2
    private val layoutUtils = LayoutUtils()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvAdapter: FirestoreRecyclerAdapter<Video, VideoListViewHolder>
    private var youTubePlayer: YouTubePlayer? = null
    private var linkCurrent: String = ""
    private var linkIncoming: String = ""
    private var iToogleToolbar: IToogleToolbar? = null
    private var isYoutubeFragmentActive: Boolean = false
    private val args: VideoListFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category
        val collection = category.collection!!
        val source = category.source
        val instagram = category.instagram!!

        adjustLayout(collection, source, instagram)

        initializeYoutubePlayer()

        rvAdapter = VideoListRvAdapter().fetchVideos(firestore, collection) { video ->
            linkIncoming = video.link

            if (!isYoutubeFragmentActive) {
                isYoutubeFragmentActive = true
                videos_rv.smoothScrollToPosition(0)
                fragment_video_list.transitionToState(R.id.onclick)

            } else {
                if (linkIncoming == linkCurrent) {
                    layoutUtils.createToast(requireContext(), "Ya estás reproduciendo este video")
                } else {
                    youTubePlayer?.loadVideo(video.link)
                    linkCurrent = linkIncoming
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IToogleToolbar) {
            iToogleToolbar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        iToogleToolbar?.toolbarOn()
        iToogleToolbar = null
    }

    override fun onFragmentBackPressed(): Boolean {
        return if (youTubePlayer != null) {
            if (youTubePlayer?.isPlaying!!) {
                youTubePlayer?.pause()
                videos_rv.smoothScrollToPosition(0)
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    private fun initializeYoutubePlayer() {

        fragment_video_list.setTransitionListener(object : MotionLayout.TransitionListener {

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if ((p1 == R.id.onclick) && (youTubePlayer == null)) {
                    val youTubePlayerSupportFragmentX =
                            YouTubePlayerSupportFragmentX.newInstance()

                    val transaction = childFragmentManager.beginTransaction()
                    transaction.add(R.id.player_inlist, youTubePlayerSupportFragmentX).commit()
                    youTubePlayerSupportFragmentX.initialize(
                            resources.getString(R.string.API_KEY),
                            object : YouTubePlayer.OnInitializedListener {
                                override fun onInitializationSuccess(
                                        p0: YouTubePlayer.Provider?,
                                        p1: YouTubePlayer?,
                                        p2: Boolean
                                ) {
                                    iToogleToolbar?.toolbarOff()

                                    youTubePlayer = p1

                                    linkCurrent = linkIncoming
                                    youTubePlayer?.loadVideo(linkIncoming)

                                }

                                override fun onInitializationFailure(
                                        p0: YouTubePlayer.Provider?,
                                        p1: YouTubeInitializationResult?
                                ) {
                                    layoutUtils.createToast(requireContext(), "Error al cargar video")
                                }
                            })
                }
            }
        })

    }

    private fun adjustLayout(collection: String, source: Int, instagram: String) {
        who.text = instagram

        val array = resources.obtainTypedArray(R.array.videos_categories_drawables)
        src.background = context?.getDrawable(source)
        array.recycle()
    }
}