package com.ammar.foodnameprediction

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.github.dhaval2404.imagepicker.ImagePicker
import com.ammar.foodnameprediction.api.ServiceBuilder
import com.ammar.foodnameprediction.fragment.HomeFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

import com.ammar.foodnameprediction.fragment.FoodFragment


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var cardImage: CardView
    private lateinit var button: Button
    private var bitmapImage: Bitmap? = null
    private  val requestGallery = 2121

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragmentContainer)
            }
        }

        button = findViewById<Button>(R.id.btnOpenGallery)
        imageView = findViewById<ImageView>(R.id.imgSelected)
        cardImage = findViewById<CardView>(R.id.cardImage)

        if(bitmapImage == null){
            cardImage.visibility =  View.GONE
        }

        button.setOnClickListener {
            ImagePicker.with(this).start(requestGallery)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestGallery && data != null) {
                val fileUri = data.data
                setImage(fileUri!!)
                ImagePicker.getFile(data)?.let { doRequest(it,fileUri) } ?: run{ Toast.makeText(applicationContext,"Ops. Something went wrong.",Toast.LENGTH_SHORT)}
            }
        }
     }


    private fun setImage(uri: Uri) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            bitmapImage = bitmap
            cardImage.visibility  = View.VISIBLE
            imageView.setImageBitmap(bitmap)

        } else {
            @Suppress("DEPRECATION") val bitmap =
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            imageView.setImageBitmap(bitmap)

        }
    }

    private fun doRequest(file:File,uri: Uri) {
        val requestFile = file.asRequestBody(contentResolver.getType(uri)?.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("img", "img.jpg", requestFile)
        val serviceBuilder = ServiceBuilder.myApi
        serviceBuilder.uploadImage(body).enqueue(object : retrofit2.Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val responseCode = response.code()
                if (responseCode == 200) {
                    handleSuccess(response)
                }  else  {
                    handleFailure()
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                handleFailure()
            }
        })
    }

    private fun handleFailure() {
        val foodName = ""
        val desc = ""
        val rest = ""

        val foodFragment = FoodFragment()
        val args = Bundle()
        args.putString(FoodFragment.FOOD_NAME, foodName)
        args.putString(FoodFragment.DESC, desc)
        args.putString(FoodFragment.RESTAURANT, rest)
        foodFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, foodFragment).commit()
        Toast.makeText(this@MainActivity, "Image not kind of food", Toast.LENGTH_SHORT)
            .show()
    }

    fun handleSuccess(response: Response<Result>){
        val foodName = response.body()?.nama_makanan.toString()
        val desc = response.body()?.deskripsi.toString()
        val rest = response.body()?.nama_restoran.toString()

        val foodFragment = FoodFragment()
        val args = Bundle()
        args.putString(FoodFragment.FOOD_NAME, foodName)
        args.putString(FoodFragment.DESC, desc)
        args.putString(FoodFragment.RESTAURANT, rest)
        foodFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, foodFragment).commit()

    }
}