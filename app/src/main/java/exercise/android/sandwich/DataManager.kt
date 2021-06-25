package exercise.android.sandwich

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class DataManager(val context : Context) : Serializable{
	private var _fullName: String? = null
	private var _lastOrderID: String? = null
	private val sp : SharedPreferences = context.getSharedPreferences("sandwitch_local", Context.MODE_PRIVATE)
	private val db = Firebase.firestore

	init {
		this.initializeFromSP();
	}

	private fun initializeFromSP() {
		this._fullName = sp.getString("firstName", null)
		this._lastOrderID = sp.getString("lastOrderID", null)
	}

	fun getFullName() : String? {
		return this._fullName
	}

	fun getLastOrderID(): String? {
		return this._lastOrderID;
	}

	fun setFullName(fullName: String?){
		this._fullName = fullName
		val editor : SharedPreferences.Editor = sp.edit()
		editor.putString("firstName", fullName)
		editor.apply()
	}

	fun setLastOrderID(lastOrderID: String?){
		this._lastOrderID = lastOrderID
		val editor : SharedPreferences.Editor = sp.edit()
		editor.putString("lastOrderID", lastOrderID)
		editor.apply()
	}

	fun addOrderToDB(orderDetails: FirestoreOrder, callBack: (Boolean, FirestoreOrder?) -> Unit){
		orderDetails.orderID = db.collection("orders").document().id
		db.collection("orders").document(orderDetails.orderID).set(orderDetails).addOnSuccessListener {
			callBack(true, orderDetails)
		}.addOnFailureListener {
			callBack(false, null)
		}.addOnCanceledListener {
			callBack(false, null)
		}
	}
}