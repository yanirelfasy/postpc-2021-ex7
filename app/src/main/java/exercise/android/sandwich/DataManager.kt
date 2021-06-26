package exercise.android.sandwich

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class DataManager(val context : Context) : Serializable{
	private var _fullName: String = ""
	private var _lastOrderID: String = ""
	private val sp : SharedPreferences = context.getSharedPreferences("sandwitch_local", Context.MODE_PRIVATE)
	private val db = Firebase.firestore

	init {
		this.initializeFromSP();
	}

	private fun initializeFromSP() {
		this._fullName = sp.getString("firstName", "")!!
		this._lastOrderID = sp.getString("lastOrderID", "")!!
	}

	fun getFullName() : String {
		return this._fullName
	}

	fun getLastOrderID(): String {
		return this._lastOrderID;
	}

	fun setFullName(fullName: String){
		this._fullName = fullName
		val editor : SharedPreferences.Editor = sp.edit()
		editor.putString("firstName", fullName)
		editor.apply()
	}

	fun setLastOrderID(lastOrderID: String){
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

	fun getOrderFromDB(orderID: String, callBack: (FirestoreOrder?) -> Unit){
		db.collection("orders").document(orderID).get().addOnSuccessListener { result: DocumentSnapshot ->
			val orderDetails = result.toObject(FirestoreOrder::class.java)
			callBack(orderDetails)
		}.addOnFailureListener {
			callBack(null)
		}
	}

	fun editOrderFromDB(orderID: String, editedOrder: FirestoreOrder, callBack: (String) -> Unit){
		db.collection("orders").document(orderID).set(editedOrder).addOnSuccessListener {
			callBack("Edited Successfully")
		}.addOnFailureListener {
			callBack("Failed to Edit")
		}
	}

	fun deleteOrderFromDB(orderID: String, callBack: (Boolean) -> Unit){
		db.collection("orders").document(orderID).delete().addOnSuccessListener {
			callBack(true)
		}.addOnFailureListener {
			callBack(false)
		}
	}

	fun changeOrderStatus(orderID: String, status: Int){
		db.collection("orders").document(orderID).update("status", status)
	}
}