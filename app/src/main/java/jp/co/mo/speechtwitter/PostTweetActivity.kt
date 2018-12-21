package jp.co.mo.speechtwitter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.activity_post_tweet.*
import kotlinx.android.synthetic.main.content_post_tweet.*
import retrofit2.Call

class PostTweetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_tweet)
        setSupportActionBar(toolBar)

        fabActionBtn.setOnClickListener({
            val text = etPostText.text.toString()

            val replyToStatusId: Long = intent.getLongExtra(KEY_REPLY_TO_STATUS_ID, KEY_IS_REPLY_INT)

            if(replyToStatusId == KEY_IS_REPLY_INT) post(text) else postAsReply(text, replyToStatusId)
        })
    }

    private fun post(postText: String) {
        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().getApiClient()

        val statusesService = twitterApiClient.statusesService
        val call: Call<Tweet> = statusesService.update(postText, null, null, null, null, null, null, null, null)
        call.enqueue(object: Callback<Tweet>(){
            override fun success(result: Result<Tweet>?) {
Toast.makeText(applicationContext, "success tweet", Toast.LENGTH_LONG).show()
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(applicationContext, "failed tweet", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun postAsReply(postText:String, replyStatusId: Long) {
        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().getApiClient()

        val statusesService = twitterApiClient.statusesService
        val call: Call<Tweet> = statusesService.update(postText, replyStatusId, null, null, null, null, null, null, null)
        call.enqueue(object: Callback<Tweet>(){
            override fun success(result: Result<Tweet>?) {
                Toast.makeText(applicationContext, "success to reply tweet", Toast.LENGTH_LONG).show()
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(applicationContext, "failed to reply tweet", Toast.LENGTH_LONG).show()
            }

        })
    }

    companion object {
        const val KEY_REPLY_TO_STATUS_ID: String = "REPLY_TO_STATUS_ID"
        const val KEY_IS_REPLY_INT: Long = -1
    }
}
