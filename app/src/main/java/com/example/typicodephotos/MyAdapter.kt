package com.example.typicodephotos

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.photos.view.*
import kotlinx.coroutines.*
import java.util.ArrayList

class MyAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    private var photosList: ArrayList<PhotosList> = ArrayList()
    private lateinit var listener:ItemOnClickListener
    fun todo(items:ArrayList<PhotosList>,listen: ItemOnClickListener){
        photosList=items
        listener = listen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photos,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val images = photosList[position]
//                val items = itemsList[position]
                holder.setData(images)
//                holder.setData(items)

    }

    override fun getItemCount(): Int {
        return photosList.size
//        return itemsList.size

    }




   inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        @SuppressLint("CheckResult")
        fun setData(images:PhotosList){
            val id=images.id.toString()
            itemView.textView.text="Id: $id"

//            Picasso.with(context).load(images.thumbnailUrl).into(itemView.imageView)
            Glide.with(itemView.context)
                .load(images.url+".png")
                .thumbnail(
                    Glide.with(itemView.context)
                        .load(images.thumbnailUrl+".png"))
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.ic_image)
//                .error(
//                    Glide.with(context)
//                        .load(images.url+".png"))
//                .override(250,250)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        itemView.progress_circular.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.progress_circular.visibility = View.VISIBLE
                        return false
                    }
                })
                .into(itemView.imageView)

            itemView.setOnLongClickListener {
                listener.onClickListener(adapterPosition)
                return@setOnLongClickListener true
            }

        }

//       init {
////           itemView.setOnClickListener {
////               listener.onClickListener(adapterPosition)
////           }
//
//
//       }

   }

}