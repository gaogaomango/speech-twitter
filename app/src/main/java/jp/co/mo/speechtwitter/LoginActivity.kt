package jp.co.mo.speechtwitter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.btnLogin.callback = object: Callback<TwitterSession>(){

            override fun success(result: Result<TwitterSession>?) {
                Toast.makeText(applicationContext, "success login", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, TimeLineActivity::class.java)
                startActivity(intent)
            }

            override fun failure(exception: TwitterException?) {
                Toast.makeText(applicationContext, "failed login", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.btnLogin.onActivityResult(requestCode, resultCode, data)
    }
}
