package jp.co.mo.speechtwitter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.twitter.sdk.android.core.models.Tweet

class TweetAdapter(val context: Context, val tweetList: ArrayList<Tweet>) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Any {
        return tweetList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return tweetList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: TweetViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.tweet_item, parent, false)
            viewHolder = TweetViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as TweetViewHolder
        }

        val currentTweet = tweetList[position]
        viewHolder.userName.text = currentTweet.userName
        viewHolder.userIcon.text = currentTweet.userIconPath
        viewHolder.tweet.text = currentTweet.userTweet

        return view
    }

}

class TweetViewHolder(v: View) {
    val userName: TextView = v.findViewById(R.id.username)
    val userIcon: TextView = v.findViewById(R.id.userIcon)
    val tweet: TextView = v.findViewById(R.id.usertweet)
}