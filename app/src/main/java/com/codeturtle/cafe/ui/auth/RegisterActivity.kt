package com.codeturtle.cafe.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codeturtle.cafe.MainActivity
import com.codeturtle.cafe.databinding.ActivityRegisterBinding
import com.codeturtle.cafe.utils.Validation
import com.codeturtle.cafe.utils.toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private val tag = "Register"
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = Firebase.auth
        handleEvent()
    }

    private fun handleEvent() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(doValidation()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(tag, "createUserWithEmail:success")
                            val user = auth.currentUser
                            toast("Success")
                            gotoHome()
                        } else {
                            try {
                                throw task.exception ?: java.lang.Exception("Invalid authentication")
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                toast("Authentication failed, Password should be at least 6 characters")
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                toast("Authentication failed, Invalid email entered")
                            } catch (e: FirebaseAuthUserCollisionException) {
                                toast("Authentication failed, Email already registered.")
                            } catch (e: Exception) {
                                toast(e.message)
                            }
                        }
                    }
            }else{
                Toast.makeText(this@RegisterActivity,"validation failed", Toast.LENGTH_SHORT).show()
            }

        }

        binding.llLogin.setOnClickListener {
            gotoLogin()
        }
    }

    private fun gotoHome() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun gotoLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }


    private fun doValidation(): Boolean {
        val email = binding.etEmail.text
        val password = binding.etPassword.text
        val confirmPassword = binding.etConfirmPassword.text
        val name = binding.etName.text
        var isValid = false
        if (
            Validation.isEmptyOrNot(
                name.toString(),
                binding.textFieldName
            ) and
            Validation.isEmptyOrNot(
                email.toString(),
                binding.textFieldEmail
            ) and
            Validation.isEmptyOrNot(
                password.toString(),
                binding.textFieldPassword
            ) and
            Validation.isEmptyOrNot(
                confirmPassword.toString(),
                binding.textFieldConfirmPassword
            ) and
            Validation.isValidEmail(
                email.toString(),
                binding.textFieldEmail
            ) and
            Validation.isPasswordSame(
                password.toString(),
                confirmPassword.toString(),
                binding.textFieldPassword,
                binding.textFieldConfirmPassword
            )

        ) {
            isValid = true
        }
        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}