package com.example.smm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smm.R
import com.example.smm.adapter.MySmmAdapter
import com.example.smm.databinding.FragmentHomeBinding
import com.example.smm.models.MySmm
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    lateinit var firebaseFireStore: FirebaseFirestore
    lateinit var mySmmAdapter: MySmmAdapter
    private val binding by lazy {  FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

    firebaseFireStore = FirebaseFirestore.getInstance()
        mySmmAdapter = MySmmAdapter()
        binding.viewPagerImage.adapter = mySmmAdapter

        firebaseFireStore.collection("my_smm")
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful){
                    val result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val myString = queryDocumentSnapshot.toObject(MySmm::class.java)
                        mySmmAdapter.list.add(myString)
                    }
                    mySmmAdapter.notifyDataSetChanged()
                }
            }
        return binding.root
    }
}