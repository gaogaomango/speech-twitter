package jp.co.mo.speechtwitter

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var tts: TextToSpeech

    private val SPEECH_MAX_SPEED: Int = 40
    private val SPEECH_MIN_SPEED: Int = 1
    private var speechSpeed: Int = 10

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

        this.speechSpeedSeekBar.max = SPEECH_MAX_SPEED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.speechSpeedSeekBar.min = SPEECH_MIN_SPEED
        }
        this.speechSpeedSeekBar.progress = speechSpeed
        this.speechSpeedSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                speechSpeed = progress
                setSpeehRate(speechSpeed)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // TODO: pause. use synthesizeToFile and MediaPlayer
                if (tts.isSpeaking()) {
                    tts.stop()
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // TODO: restart
            }

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

        if (tts.isSpeaking()) {
            tts.stop()
            return
        }

        setSpeehRate(speechSpeed)
        tts.setPitch(1.0f)

        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(speechTxt, TextToSpeech.QUEUE_FLUSH, null, "messageID")
        } else {
            val map = HashMap<String, String>()
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID")
            tts.speak(speechTxt, TextToSpeech.QUEUE_FLUSH, map)
        }
        setTtsListener()
    }

    private fun setSpeehRate(speedInt: Int) {
        val speed: Float = speedInt.toFloat() / 10
        tts.setSpeechRate(speed)
    }

    private fun setTtsListener() {
        if (Build.VERSION.SDK_INT >= 15) {
            val listerResult = tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
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
