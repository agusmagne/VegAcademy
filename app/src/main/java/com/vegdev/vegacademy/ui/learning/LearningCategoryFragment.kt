package com.vegdev.vegacademy.ui.learning


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.ILayoutManager
import com.vegdev.vegacademy.IYoutubePlayer
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.data.models.Category
import com.vegdev.vegacademy.data.models.LearningElement
import com.vegdev.vegacademy.data.repositories.learningelements.LearningRepositoryImpl
import com.vegdev.vegacademy.utils.GenericUtils
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.fragment_learning_category.*
import kotlinx.android.synthetic.main.fragment_news_element.view.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class LearningCategoryFragment : Fragment() {

    private val layoutUtils = LayoutUtils()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvAdapter: FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder>
    private var youtubePlayer: IYoutubePlayer? = null
    private var isLayoutLoaded: Boolean = false
    private var iLayoutManager: ILayoutManager? = null
    private val args: LearningCategoryFragmentArgs by navArgs()

    private val repository = LearningRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iLayoutManager?.currentlyLoading()
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_learning_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category

        val categoryImage = category.src!!
        val categoryTitle = category.title!!
        val categorySocials = category.socials!!

        // create background color
        Glide.with(this).asBitmap().load(categoryImage).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                val colors = layoutUtils.getAverageColor(bitmap)
                back.setBackgroundColor(Color.rgb(colors[0], colors[1], colors[2]))
                adjustLayout(bitmap, categorySocials, categoryTitle)
            }

        })

        if (who.text == "") {
            adjustRvToTitle()
        }

        rvAdapter = fetchLearningElements(firestore, category, { element ->
            if (category.type == "videos") {
                youtubePlayer?.openYoutubePlayer(element)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(element.link))
                startActivity(intent)
            }
        }, {
            if (!isLayoutLoaded) {
                isLayoutLoaded = true
                iLayoutManager?.finishedLoading()
                fragment_video_list.visibility = View.VISIBLE
                layoutUtils.animateViews(
                    requireContext(),
                    R.anim.fragment_in,
                    listOf(src, who, title, videos_rv)
                )
            }
        })

        videos_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        who.setOnClickListener {
            if (who.text != "") {
                val uri = Uri.parse("http://instagram.com/_u/$categorySocials")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.instagram.android")

                if (isInstagramIntentAvailable(context, intent)) {
                    startActivity(intent)
                } else {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                "http://instagram" +
                                        ".com/$categorySocials"
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
        if (context is IYoutubePlayer) {
            youtubePlayer = context
        }
        if (context is ILayoutManager) {
            iLayoutManager = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        youtubePlayer = null
        iLayoutManager = null
    }

    private fun adjustRvToTitle() {
        val params = back.layoutParams
        params.height = 275
        back.layoutParams = params
    }

    private fun isInstagramIntentAvailable(context: Context?, intent: Intent): Boolean {
        val packageManager = context?.packageManager
        val list = packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list?.size!! > 0
    }

    private fun adjustLayout(
        categoryImage: Bitmap,
        categoryInstagram: String,
        categoryTitle: String
    ) {
        if (categoryInstagram != "") {
            val text = "@$categoryInstagram"
            who.text = text
        } else {
            who.text = ""
        }
        title.text = categoryTitle
        src.setImageBitmap(categoryImage)
    }

    private fun fetchLearningElements(
        firestore: FirebaseFirestore,
        category: Category,
        onElementClick: (LearningElement) -> Unit,
        onFinishLoadingImages: () -> Unit
    ): FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder> {

        val query = firestore.collection("learning").document(category.type!!)
            .collection(category.cat!!)
        val response = FirestoreRecyclerOptions.Builder<LearningElement>()
            .setQuery(query, LearningElement::class.java).build()
        return object :
            FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder>(response) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): LearningElementViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_news_element, parent, false)
                return LearningElementViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: LearningElementViewHolder,
                position: Int,
                learningElement: LearningElement
            ) {
                holder.bindElement(learningElement) {
                    onFinishLoadingImages()
                }
                holder.itemView.isClickable = false
                holder.itemView.src.setOnClickListener { onElementClick(learningElement) }
            }
        }

    }
}

class LearningElementViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement, listener: () -> Unit) {
        itemView.title.text = learningElement.title
        Glide.with(itemView.context).load(learningElement.icon).into(itemView.cat)

        val days =
            GenericUtils().getDateDifference(
                learningElement.date,
                Timestamp.now(),
                TimeUnit.DAYS
            )
        val dateDiff = if (days != 0L) {
            "Hace $days días"
        } else {
            "Hoy"
        }

        itemView.days_ago.text = dateDiff
        itemView.desc.text = learningElement.desc
        Glide.with(itemView.context).load(learningElement.src)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener()
                    return false
                }
            })
            .into(itemView.src)
    }
}