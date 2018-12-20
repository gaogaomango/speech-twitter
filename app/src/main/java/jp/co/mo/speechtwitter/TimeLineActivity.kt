package jp.co.mo.speechtwitter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.twitter.sdk.android.core.TwitterCore

class TimeLineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        if (TwitterCore.getInstance().sessionManager.activeSession == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "ログイン中", Toast.LENGTH_LONG).show()
        }

    }
}
