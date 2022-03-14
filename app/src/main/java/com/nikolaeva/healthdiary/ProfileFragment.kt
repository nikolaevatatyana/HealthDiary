package com.nikolaeva.healthdiary

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var authUser: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginBtn = view.findViewById<Button>(R.id.btnLogin)

        val email = view.findViewById<EditText>(R.id.et_email)
        val password = view.findViewById<EditText>(R.id.et_password)

        // проверить на пустоту эти поля


        authUser = FirebaseAuth.getInstance()





        loginBtn.setOnClickListener {
            //проверка на пустоту
            createUser(email = email.text.toString(), password = password.text.toString())
            val text = "Вход упешно выполнен"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(requireContext(), text, duration)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        }
    }

    private fun createUser(email: String, password: String) {
        authUser.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        requireContext(), "Authentication completed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = authUser.currentUser
                    Toast.makeText(
                        requireContext(), user?.email + " " + user?.displayName,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception);
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /* fun viewInitializations() {
         etEmail = findViewById(R.id.et_email)
         etPassword = findViewById(R.id.et_password)

         // To show back button in actionbar
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
     }

     // Checking if the input in form is valid
     fun validateInput(): Boolean {
         if (etEmail.text.toString() == "") {
             etEmail.error = "Please Enter Email"
             return false
         }
         if (etPassword.text.toString() == "") {
             etPassword.error = "Please Enter Password"
             return false
         }

         // checking the proper email format
         if (!isEmailValid(etEmail.text.toString())) {
             etEmail.error = "Please Enter Valid Email"
             return false
         }

         // checking minimum password Length
         if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
             etPassword.error = "Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters"
             return false
         }
         return true
     }

     fun isEmailValid(email: String?): Boolean {
         return Patterns.EMAIL_ADDRESS.matcher(email).matches()
     }

     // Hook Click Event
     fun performSignUp(v: View) {
         if (validateInput()) {

             // Input is valid, here send data to your server
             val email = etEmail!!.text.toString()
             val password = etPassword!!.text.toString()
             Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
             // Here you can call you API
             // Check this tutorial to call server api through Google Volley Library https://handyopinion.com
         }
     }

     fun goToSignup(v: View) {
         // Open your SignUp Activity if the user wants to signup
         // Visit this article to get SignupActivity code https://handyopinion.com/signup-activity-in-android-studio-kotlin-java/
         val intent = Intent(this, SignupActivity::class.java)
         startActivity(intent)
     }
 }*/

    companion object {

        fun newInstance() = ProfileFragment()
    }
}



