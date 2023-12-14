package com.example.smm.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.smm.R
import com.example.smm.databinding.FragmentAddBinding
import com.example.smm.models.MySmm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

private const val TAG = "AddFragment"
class AddFragment : Fragment() {
    lateinit var firebaseFireStore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        firebaseFireStore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("my_images")

        binding.imageAdd.setOnClickListener {
            getImageContent.launch("image/*")
        }

        binding.btnSave.setOnClickListener {

            val name = binding.edtName.text.toString()

            if (imageUri != null && name != "") {

                binding.btnSave.setText("Yuklanmoqda")
                binding.btnSave.isEnabled = false

                var imageUrl = ""
                val m = System.currentTimeMillis()
                val uploadTask = reference.child(m.toString()).putFile(imageUri!!)
                uploadTask.addOnSuccessListener {
                    if (it.task.isSuccessful) {
                        val downloadUrl = it.metadata?.reference?.downloadUrl
                        downloadUrl?.addOnSuccessListener { imageUri ->
                            imageUrl = imageUri.toString()


                            val mySmm = MySmm(name, imageUrl)
                            firebaseFireStore.collection("my_smm")
                                .add(mySmm)
                                .addOnSuccessListener{
                                    Toast.makeText(context, "Saqlandi", Toast.LENGTH_SHORT).show()
                                    binding.btnSave.setText("Saqlandi")
                                    binding.btnSave.isEnabled = true
                                }
                                .addOnFailureListener{
                                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                                    Log.e(TAG, "onCreateView: $it", )
                                    binding.btnSave.setText("Joylash")
                                    binding.btnSave.isEnabled = true
                                }



                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.btnSave.setText("Joylash")
                    binding.btnSave.isEnabled = true
                }
            }else{
                Toast.makeText(context, "Avval ma'lumotlarni to'ldiring", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    var imageUri: Uri? = null
    private var getImageContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        binding.imageAdd.setImageURI(uri)
    }
}

