package com.liu.demo.wanandroid.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

//
abstract class BaseFragment<VB : ViewBinding>(val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.i("SSS2","onViewCreated")
//        init()
//    }
//
//    abstract fun init()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//abstract class BaseFragment<VB : ViewBinding>(id:Int) : Fragment(id) {
//    private var _binding: VB? = null
//        val binding: VB get() = _binding!!
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = initBinding(view)
//        init()
//    }
//
//    abstract fun initBinding(view: View):VB
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//    abstract fun init()
//}
