package jp.co.mo.speechtwitter

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.StatusesService
import kotlinx.android.synthetic.main.activity_time_line.*
import kotlinx.android.synthetic.main.content_timeline.*
import retrofit2.Call

class TimeLineActivity : AppCompatActivity() {

    private lateinit var tweetList: ArrayList<Tweet>
    private lateinit var tweetAdapter: TweetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        if (TwitterCore.getInstance().sessionManager.activeSession == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "ログイン中", Toast.LENGTH_LONG).show()
        }

        setSupportActionBar(toolBar)
        fabActionBtn.setOnClickListener({
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        })

        tweetList = ArrayList<Tweet>()
        tweetAdapter = TweetAdapter(this, tweetList)
        lvTimeLine.adapter = tweetAdapter

        getHomeTimeLine()
    }

    private fun getHomeTimeLine() {
        val twitterApiClient: TwitterApiClient = TwitterCore.getInstance().getApiClient()
        val statusesService: StatusesService = twitterApiClient.statusesService

        val call: Call<List<Tweet>> = statusesService.homeTimeline(20, null, null, false, false, false, false)
        call.enqueue(object: Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>?) {
                result?.data?.forEach {
                    tweetList.add(it)
                }
                tweetAdapter.notifyDataSetChanged()
                Toast.makeText(applicationContext, "success getting timeline", Toast.LENGTH_LONG).show()
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(applicationContext, "failed getting timeline", Toast.LENGTH_LONG).show()
            }

        })
    }
}
