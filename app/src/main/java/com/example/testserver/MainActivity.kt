package com.example.testserver

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import java.net.URL

class MainActivity : AppCompatActivity() {

    var tvData: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvData = findViewById(R.id.textView)
        val btn = findViewById<Button>(R.id.httpTest)
        //버튼이 클릭되면 여기 리스너로 옴
        btn.setOnClickListener {
            JSONTask().execute("http://192.249.19.254:8280/") //AsyncTask 시작시킴
        }


    }

    inner class JSONTask :
        AsyncTask<String?, String?, String?>() {
        override fun doInBackground(vararg urls: String?): String? {
            try {
                var get =
                    URL("http://192.249.19.254:8280/api/contacts/user_id/dddafd@d33d.com").readText() // 로그인한 유저가 받은 아이디로 찾기
                Log.i("get>>>>>>>>>>>>>>>>", get) //형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","phNum":"010-121324-1124","name":"dafoudfasfi"}]
                //parsing code 필요

                var post = httpPost {
                    url("http://192.249.19.254:8280/api/contacts")
                    body {
                        form("user_id=dddafd@d34d.com&" +
                                "phNum=010-2222-6666&" +
                                "name=newPeoepeee")
                    }
                }
                Log.i("post>>>>>>>>>>>>>>>>>>", post.message())

                var delete = httpDelete {//delete 왜 안되지??
                    url("http://192.249.19.254:8280/api/contacts/5e11cddde1fc032f3ba8e4c3")
                }
                Log.i("delete>>>>>>>>>>>>>>>>", delete.message())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            tvData!!.text = result //서버로부터 받은 값을 출력해주는 부분
        }
    }
}
