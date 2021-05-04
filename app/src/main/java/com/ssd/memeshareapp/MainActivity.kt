package com.ssd.memeshareapp

import MySingleton
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

//    private val btnToggleDark Button
//    private var btnToggleDark: Button? = null

    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Saving state of our app
        // using SharedPreferences
        // Saving state of our app
        // using SharedPreferences
        val sharedPreferences = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val isDarkModeOn = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false)

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            darkModeButton.setText(
                    "Disable Dark Mode");
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            darkModeButton
                    .setText(
                            "Enable Dark Mode");
        }


//        darkModeButton.setOnClickListener(View.OnClickListener {
//            public void onClick(View view){
//                AppCompatDelegate
//                        .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            }
//        })


//        darkModeButton.setOnClickListener()

//        darkModeButton.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View?) {
//                // Do some work here
//                darkMode()
//            }
//
//        })

        loadMeme()
    }

    fun darkMode(view: View){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }




    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
//        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    currentImageUrl = response.getString("url")
                    val memeImageView = findViewById<ImageView>(R.id.imageView)
                    Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    }).into(memeImageView)
                },
                {
                    Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show()
                })

        // Add the request to the RequestQueue.
//        queue.add(jsonObjectRequest)
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type ="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Share through My app $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share meme using........ ")
        startActivity(chooser)
    }

//    fun darkMode(view: View) {}
}