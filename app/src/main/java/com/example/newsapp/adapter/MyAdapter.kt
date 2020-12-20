package com.example.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.activities.NewsDetailActivity
import com.example.newsapp.R
import com.example.newsapp.models.News
import kotlinx.android.synthetic.main.item_view.view.*
import java.lang.NullPointerException

class MyAdapter(private val context:Context, private val list:List<News>): RecyclerView.Adapter<MyAdapter.MyViewholder>() {
    private var last_position=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_view,parent,false)
        return MyViewholder(view)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        var item: News = list[position]
        holder.setData(item,position)
        //Animation for each item to be displayed
        if(position>last_position) {
            var animation = AnimationUtils.loadAnimation(context, R.anim.items_down_animation)
            holder.itemView.animation = animation
            holder.itemView.animate()
            last_position=position
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewholder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var position: Int? = null
        var URL: String? = null

        fun setData(news: News?, pos: Int) {
            var urltoImage: String? =news?.urlToImage
            urltoImage?.let {
                var uri: Uri? = Uri.parse(news?.urlToImage)
                Glide.with(context).asBitmap().load(uri).into(itemView.news_image)
            }
            itemView.heading.text = news?.title
            itemView.description.text = news?.description
            itemView.publish.text = news?.publishedAt
            position = pos
            URL=news?.url
        }
        init {
            itemview.setOnClickListener{
            var intent=Intent(context,NewsDetailActivity::class.java)
            intent.putExtra("url",URL)
            context.startActivity(intent)
            }
            itemView.menu.setOnClickListener {
                var menu = PopupMenu(context, it)
                menu.menuInflater.inflate(R.menu.options, menu.menu)
                menu.show()
                menu.setOnMenuItemClickListener {
                   var variable=menu(it,URL!!)
                    return@setOnMenuItemClickListener variable
                }
            }
        }
    }

    private fun menu(it: MenuItem?,url:String):Boolean {
        var intent=Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,url)
        intent.type="text/plain"
        context.startActivity(Intent.createChooser(intent,"Share to:"))
        return true
    }
}


