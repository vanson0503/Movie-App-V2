package com.example.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityRegisterBinding
import com.example.movieapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnShowPassword.setOnClickListener {
            val currentTransformation = binding.edtPassword.transformationMethod
            if (currentTransformation is PasswordTransformationMethod) {
                binding.btnShowPassword.setImageResource(R.drawable.visibility_off_24px)
                binding.edtPassword.transformationMethod = null
            } else {
                binding.btnShowPassword.setImageResource(R.drawable.baseline_remove_red_eye_24)
                binding.edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.edtPassword.setSelection(binding.edtPassword.text.length)
        }
        binding.btnRegister.setOnClickListener {
            loginHandle()
        }
    }

    private fun loginHandle() {
        val userName = binding.edtUserName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            val progressDialog = showProgressDialog()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authResult ->
                    progressDialog.dismiss()

                    if (authResult.isSuccessful) {
                        saveUserDataToDatabase(userName, email)
                        auth.signOut()
                        finish()
                        navigateToLoginActivity()
                    }
                }
                .addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Data is not empty!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressDialog(): AlertDialog {
        val view = ProgressBar(this)

        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        return alertDialog
    }

    private fun saveUserDataToDatabase(userName: String, email: String) {
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        val user = User(auth.currentUser!!.uid, userName, email,"")

        databaseRef.setValue(user)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(this, "Failed to save user data to database", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}