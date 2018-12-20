package jp.co.mo.speechtwitter

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, {
            if (TextToSpeech.SUCCESS == it) {
                Log.d(TAG, "initialized text to speech")
            } else {
                Log.d(TAG, "failed to initialize text to speech")
            }
        })

        this.btnSpeechText.setOnClickListener({
            speechText()
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        shutDown()
    }

    private fun shutDown() {
        tts.shutdown()
    }

    private fun speechText() {
        sampleText.selectAll()
        val speechTxt: String? = sampleText.text.toString()

        if(tts.isSpeaking()) {
            tts.stop()
            return
        }

        tts.setSpeechRate(1.0f)
        tts.setPitch(1.0f)

        if(Build.VERSION.SDK_INT >= 21) {
            tts.speak(speechTxt, TextToSpeech.QUEUE_FLUSH, null, "messageID")
        } else {
//            val map = mutableMapOf<String, String>()
//            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID")
//            tts.speak(speechTxt, TextToSpeech.QUEUE_FLUSH, map)
            val map = HashMap<String, String>()
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID")
            tts.speak(speechTxt, TextToSpeech.QUEUE_FLUSH, map)
        }
        setTtsListener()
    }

    private fun setTtsListener() {
        if(Build.VERSION.SDK_INT >= 15) {
            val listerResult = tts.setOnUtteranceProgressListener(object: UtteranceProgressListener(){
                override fun onDone(utteranceId: String?) {
                    Log.e(TAG, "progress on Done $utteranceId")
                }

                override fun onError(utteranceId: String?) {
                    Log.e(TAG, "progress on error $utteranceId")
                }

                override fun onStart(utteranceId: String?) {
                    Log.e(TAG, "progress on start $utteranceId")
                }

            })
            if (listerResult != TextToSpeech.SUCCESS) Log.e(TAG, "failed to add utterance progress listener")
        } else {
            Log.e(TAG, "Build VERSION is less than API 15")
        }
    }
}
