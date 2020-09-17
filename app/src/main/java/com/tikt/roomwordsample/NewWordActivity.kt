package com.tikt.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        editWordView = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replayIntetn = Intent()
            if(TextUtils.isEmpty(editWordView.text)){
                setResult(Activity.RESULT_CANCELED,replayIntetn)
            }else{
                val word = editWordView.text.toString()
                replayIntetn.putExtra(EXTRA_REPLAY,word)
                setResult(Activity.RESULT_OK,replayIntetn)


            }

            finish()
        }



    }


    companion object{
        const val EXTRA_REPLAY = "com.example.android.wordlistsql.REPLY"
    }
}