package exercise.android.sandwich

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PlaceOrder : AppCompatActivity() {
	private var fullName: String? = null
	private var numOfPickles = 0
	private var addHummus = false
	private var addTahini = false
	private var comment: String? = null
	private var dataManager: DataManager? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportActionBar!!.hide()
		if (dataManager == null) {
			dataManager = SandwichApplication.getInstance().dataManager
		}
		setContentView(R.layout.activity_place_order)
		fullName = dataManager?.getFullName()
		val fullNameField = findViewById<View>(R.id.name) as EditText
		fullNameField.setText(fullName)
		val numOfPicklesField = findViewById<View>(R.id.picklesPicker) as NumberPicker
		val addHummusField = findViewById<View>(R.id.hummusCheckBox) as CheckBox
		val addTahiniField = findViewById<View>(R.id.tahiniCheckBox) as CheckBox
		val commentField = findViewById<View>(R.id.commentContent) as EditText
		val placeOrder = findViewById<View>(R.id.placeOrderBtn) as Button
		numOfPicklesField.minValue = 0
		numOfPicklesField.maxValue = 10
		numOfPicklesField.setOnValueChangedListener { picker: NumberPicker?, oldVal: Int, newVal: Int -> numOfPickles = newVal }
		fullNameField.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
			override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
			override fun afterTextChanged(editable: Editable) {
				fullName = fullNameField.text.toString()
			}
		})
		commentField.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
			override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
			override fun afterTextChanged(editable: Editable) {
				comment = commentField.text.toString()
			}
		})
		addHummusField.setOnCheckedChangeListener { compoundButton, isChecked -> addHummus = isChecked }
		addTahiniField.setOnCheckedChangeListener { compoundButton, isChecked -> addTahini = isChecked }
		placeOrder.setOnClickListener { view ->
			dataManager!!.addOrderToDB(
					FirestoreOrder(fullName!!, numOfPickles, addHummus, addTahini, comment!!, 0, ""),
					::placeOrderHandler
			)

		}
	}

	fun placeOrderHandler(isSuccess: Boolean, orderID: String?){
		if(isSuccess){
			if(dataManager?.getFullName() != fullName){
				dataManager?.setFullName(fullName)
			}
			dataManager?.setLastOrderID(orderID)
		}
		else{
			println("FAIL!!!!")
		}

	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putString("fullName", fullName)
		outState.putInt("numOfPickles", numOfPickles)
		outState.putBoolean("addHummus", addHummus)
		outState.putBoolean("addTahini", addTahini)
		outState.putString("comment", comment)
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		fullName = savedInstanceState.getString("fullName")
		numOfPickles = savedInstanceState.getInt("numOfPickles")
		addHummus = savedInstanceState.getBoolean("addHummus")
		addTahini = savedInstanceState.getBoolean("addTahini")
		comment = savedInstanceState.getString("comment")
		val fullNameField = findViewById<View>(R.id.name) as EditText
		val numOfPicklesField = findViewById<View>(R.id.picklesPicker) as NumberPicker
		val addHummusField = findViewById<View>(R.id.hummusCheckBox) as CheckBox
		val addTahiniField = findViewById<View>(R.id.tahiniCheckBox) as CheckBox
		val commentField = findViewById<View>(R.id.commentContent) as EditText
		fullNameField.setText(fullName)
		numOfPicklesField.value = numOfPickles
		addHummusField.isChecked = addHummus
		addTahiniField.isChecked = addTahini
		commentField.setText(comment)
	}
}