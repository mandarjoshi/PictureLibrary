package com.mandarjoshi.picturelibrary.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.picasso.OkHttp3Downloader
import com.mandarjoshi.picturelibrary.HeaderInterceptor
import com.mandarjoshi.picturelibrary.databinding.ViewpagerLayoutBinding
import com.mandarjoshi.picturelibrary.model.PictureContainer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import javax.inject.Inject

class PictureAdapter(
    private val items: List<PictureContainer>,

) : RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewpagerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = items[position]
        holder.pictureName.text = holder.mItem.pictureDetails?.name
        val okHttpClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
        val downLoader = OkHttp3Downloader(okHttpClient)
        holder.progressBar.visibility = View.VISIBLE
        val picasso = Picasso.Builder(holder.picture.context).downloader(downLoader).build()
        picasso.load(holder.mItem.pictureDetails?.url).into(holder.picture, object: Callback {
            override fun onSuccess() {
                holder.progressBar.visibility = View.GONE
            }
            override fun onError(){
                holder.progressBar.visibility = View.GONE
                holder.errorMessage.visibility = View.VISIBLE
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ViewpagerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val pictureName: TextView = binding.pictureName
        val picture: ImageView = binding.picture
        val progressBar: ProgressBar = binding.imageProgressBar
        val errorMessage: TextView = binding.errorMessage

        lateinit var mItem: PictureContainer
        override fun toString(): String {
            return super.toString() + " '" + pictureName.text + "'"
        }
    }

}
