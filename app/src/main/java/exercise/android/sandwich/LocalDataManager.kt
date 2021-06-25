package exercise.android.sandwich

import android.content.Context
import android.content.SharedPreferences
import java.io.Serializable

class LocalDataManager(val context : Context) : Serializable{
	private var _firstName: String? = null
	private var _lastOrderID: String? = null
	private val sp : SharedPreferences = context.getSharedPreferences("sandwitch_local", Context.MODE_PRIVATE)

	init {
		this.initializeFromSP();
	}

	private fun initializeFromSP() {
		this._firstName = sp.getString("firstName", null)
		this._lastOrderID = sp.getString("lastOrderID", null)
	}

	fun getFirstName() : String? {
		return this._firstName
	}

	fun getLastOrderID(): String? {
		return this._lastOrderID;
	}

	fun setFirstName(firstName: String){
		this._firstName = firstName
		val editor : SharedPreferences.Editor = sp.edit()
		editor.putString("firstName", firstName)
		editor.apply()
	}

	fun setLastOrderID(lastOrderID: String?){
		this._lastOrderID = lastOrderID
		val editor : SharedPreferences.Editor = sp.edit()
		editor.putString("lastOrderID", lastOrderID)
		editor.apply()
	}


}