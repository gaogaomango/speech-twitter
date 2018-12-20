package jp.co.mo.speechtwitter

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val config: TwitterConfig = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(BuildConfig.TWITTER_CUNSUMER_API_KEY, BuildConfig.TWITTER_CUNSUMER_SECRET_API_KEY))
                .debug(true)
                .build()

        Twitter.initialize(config)
    }
}