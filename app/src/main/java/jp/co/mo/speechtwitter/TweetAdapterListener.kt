package jp.co.mo.speechtwitter

import com.twitter.sdk.android.core.models.Tweet

interface TweetAdapterListener {
    fun onClickReplyButton(tweet: Tweet)
}