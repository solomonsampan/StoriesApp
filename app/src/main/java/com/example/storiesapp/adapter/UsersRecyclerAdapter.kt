package com.example.storiesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storiesapp.R
import com.example.storiesapp.database.UserData
import java.util.*


class UsersRecyclerAdapter (var listUsers: List<UserData>) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    private var filteredNameList: List<UserData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_recycler, parent, false)

        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tv_story_title.text = listUsers[position].story_title
        holder.tv_story.text = listUsers[position].story
        holder.tv_author.text = "Author : "+listUsers[position].author

    }

    override fun getItemCount(): Int {
        return listUsers.size
    }


    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tv_story_title: TextView
        var tv_story: TextView
        var tv_author: TextView

        init {
            tv_story_title = view.findViewById(R.id.tv_story_title)
            tv_story = view.findViewById(R.id.tv_story)
            tv_author = view.findViewById(R.id.tv_author)

        }
    }

}