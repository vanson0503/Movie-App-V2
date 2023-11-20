package com.example.movieapp.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentAccountBinding
import com.example.movieapp.model.User
import com.example.movieapp.utils.Constrain.PICK_IMAGE_REQUEST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user = snapshot.getValue(User::class.java)

                    if(user!!.avatarUrl==""){
                        binding.imgAvatar.setImageResource(R.drawable.ic_launcher_background)
                    }
                    else{
                        Glide.with(binding.root)
                            .load(user.avatarUrl)
                            .into(binding.imgAvatar)
                    }
                    binding.txtFullName.text = user.fullName
                    binding.txtEmail.text = user.email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.details, )
            }

        })
        binding.btnChangeAvatar.setOnClickListener {
            changeAvatar()
        }
    }
    private fun changeAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val filePath = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)
                binding.imgAvatar.setImageBitmap(bitmap)
                uploadImageToFirebaseStorage(filePath)
            } catch (e: Exception) {
                Log.e("ERROR", "onActivityResult: ${e.message}", e)
            }
        }
    }
    private fun uploadImageToFirebaseStorage(imageUri: Uri?) {
        val imageRef = storageRef.child("profileImages/${auth.currentUser?.uid}")
        val uploadTask: UploadTask = imageRef.putFile(imageUri!!)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                val uid = auth.currentUser?.uid
                if (uid != null) {
                    databaseReference.child("avatarUrl").setValue(imageUrl)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("ERROR", "Image upload failed: ${e.message}")
        }
    }
}