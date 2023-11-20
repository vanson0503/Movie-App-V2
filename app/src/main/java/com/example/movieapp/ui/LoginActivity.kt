package com.example.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnLogin.setOnClickListener { 
            loginHandle()
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

    }

    private fun loginHandle() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        if(email.isNotEmpty()&&password.isNotEmpty()){
            val progressDialog = showProgressDialog()
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        progressDialog.dismiss()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }else{
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
}