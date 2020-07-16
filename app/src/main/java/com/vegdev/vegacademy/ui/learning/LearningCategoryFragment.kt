package com.vegdev.vegacademy.ui.learning


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.IOnFragmentBackPressed
import com.vegdev.vegacademy.IProgressBar
import com.vegdev.vegacademy.IToogleToolbar
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.Utils.LayoutUtils
import com.vegdev.vegacademy.models.LearningElement
import kotlinx.android.synthetic.main.fragment_video_list.*
import kotlinx.android.synthetic.main.fragment_video_list.view.*

/**
 * A simple [Fragment] subclass.
 */
class LearningCategoryFragment : Fragment(), IOnFragmentBackPressed {

    private val layoutUtils = LayoutUtils()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvAdapter: FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder>
    private var youTubePlayer: YouTubePlayer? = null
    private var linkCurrent: String = ""
    private var linkIncoming: String = ""
    private var iToogleToolbar: IToogleToolbar? = null
    private var iProgressBar: IProgressBar? = null
    private var isYoutubeFragmentActive: Boolean = false
    private var isLayoutLoaded: Boolean = false
    private val args: LearningCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iProgressBar?.loading()

        val category = args.category

        val categoryType = category.categoryType!!
        val categoryCollection = category.categoryCollection!!
        val categoryImage = category.categoryImage!!
        val categoryTitle = category.categoryTitle!!
        val categoryInstagram = category.categoryInstagram!!

        adjustLayout(categoryImage, categoryInstagram, categoryTitle)
        if (who.text == "") {
            adjustRvToTitle()
        }

        initializeYoutubePlayer()

        if (categoryType == "videos") {
            rvAdapter =
                VideoListRvAdapter().fetchVideos(
                    firestore,
                    categoryCollection,
                    { video ->
                        linkIncoming = video.link

                        if (!isYoutubeFragmentActive) {
                            isYoutubeFragmentActive = true
                            videos_rv.smoothScrollToPosition(0)
                            fragment_video_list.transitionToState(R.id.onclick)

                        } else {
                            if (linkIncoming == linkCurrent) {
                                layoutUtils.createToast(
                                    requireContext(),
                                    "Ya estás reproduciendo este video"
                                )
                            } else {
                                youTubePlayer?.loadVideo(video.link)
                                linkCurrent = linkIncoming
                            }
                        }
                    }, {
                        if (!isLayoutLoaded) {
                            isLayoutLoaded = true
                            iProgressBar?.loaded()
                            fragment_video_list.visibility = View.VISIBLE
                            layoutUtils.animateViews(
                                requireContext(),
                                R.anim.fragment_in,
                                listOf(src, who, title, videos_rv)
                            )
                        }
                    })

        } else if (categoryType == "art") {
            rvAdapter =
                VideoListRvAdapter().fetchArticles(firestore, categoryCollection, { article ->
                    val link = article.link
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    this.startActivity(intent)
                }, {
                    if (!isLayoutLoaded) {
                        isLayoutLoaded = true
                        iProgressBar?.loaded()
                        fragment_video_list.visibility = View.VISIBLE
                        layoutUtils.animateViews(
                            requireContext(),
                            R.anim.fragment_in,
                            listOf(src, who, title, videos_rv)
                        )
                    }
                })
        }

        videos_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        who.setOnClickListener {
            if (who.text != "") {
                val uri = Uri.parse("http://instagram.com/_u/$categoryInstagram")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.instagram.android")

                if (isInstagramIntentAvailable(context, intent)) {
                    startActivity(intent)
                } else {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                "http://instagram" +
                                        ".com/$categoryInstagram"
                            )
                        )
                    )
                }
            }
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
        if (context is IProgressBar) {
            iProgressBar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        iToogleToolbar?.toolbarOn()
        iToogleToolbar = null
        iProgressBar = null
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

    private fun adjustRvToTitle() {
        val params = back.layoutParams
        params.height = 275
        back.layoutParams = params
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

    private fun isInstagramIntentAvailable(context: Context?, intent: Intent): Boolean {
        val packageManager = context?.packageManager
        val list = packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list?.size!! > 0
    }

    private fun adjustLayout(categoryImage: Int, categoryInstagram: String, categoryTitle: String) {
        if (categoryInstagram != "") {
            val text = "@$categoryInstagram"
            who.text = text
        } else {
            who.text = ""
        }
        title.text = categoryTitle
        src.background = context?.getDrawable(categoryImage)
    }
}