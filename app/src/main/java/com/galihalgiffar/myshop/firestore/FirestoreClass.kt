package com.galihalgiffar.myshop.firestore

import android.util.Log
import com.galihalgiffar.myshop.activity.RegisterActivity
import com.galihalgiffar.myshop.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        //The "users" is collcetion name, if the collection is already created then it will not create the same name
        mFireStore.collection("users")
            //document ID for users fields. here the document it is the User ID.
            .document(userInfo.id)
            //here the userInfo are field and the setOption is set to merge. It is for if we want to merge later
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                // here call a function of base activity for transferring the result to it.
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressBar()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e

                )
            }
    }


}