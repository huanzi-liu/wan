package com.liu.demo.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<V : ViewBinding>(val inflate: (LayoutInflater) -> V) :
    AppCompatActivity() {

    lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
//        val cla = type.actualTypeArguments[0]
//        try {
//            val inflate = cla.javaClass.getDeclaredMethod("inflate",LayoutInflater::class.java)
//            binding = inflate.invoke(null, layoutInflater) as V
//            setContentView(binding.root)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        supportActionBar?.hide()
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

}