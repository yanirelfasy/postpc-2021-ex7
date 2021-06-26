package exercise.android.sandwich

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditOrder : AppCompatActivity() {

	private var fullName: String? = null
	private var numOfPickles = 0
	private var addHummus = false
	private var addTahini = false
	private var comment: String? = null
	private var dataManager: DataManager? = null
	private var status: String? = null
	private var orderID: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportActionBar!!.hide()
		if (dataManager == null) {
			dataManager = SandwichApplication.getInstance().dataManager
		}
		setContentView(R.layout.activity_edit_order)

		val fullNameField = findViewById<View>(R.id.name) as EditText
		val numOfPicklesField = findViewById<View>(R.id.picklesPicker) as NumberPicker
		val addHummusField = findViewById<View>(R.id.hummusCheckBox) as CheckBox
		val addTahiniField = findViewById<View>(R.id.tahiniCheckBox) as CheckBox
		val commentField = findViewById<View>(R.id.commentContent) as EditText
		val saveChanges = findViewById<View>(R.id.saveChangesBtn) as Button
		val deleteBtn = findViewById<View>(R.id.deleteOrder) as Button
		val statusField = findViewById<View>(R.id.orderStatus) as TextView

		numOfPicklesField.minValue = 0
		numOfPicklesField.maxValue = 10

		val recIntent = intent
		val orderDetails: FirestoreOrder = recIntent.getSerializableExtra("orderDetails") as FirestoreOrder

		fullName = orderDetails.fullName
		numOfPickles = orderDetails.numOfPickles
		addHummus = orderDetails.addHummus
		addTahini = orderDetails.addTahini
		comment = orderDetails.comment
		status = "Waiting..."
		orderID = orderDetails.orderID

		fullNameField.setText(fullName)
		numOfPicklesField.value = numOfPickles
		addHummusField.isChecked = addHummus
		addTahiniField.isChecked = addTahini
		commentField.setText(comment)
		statusField.text = status

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

		saveChanges.setOnClickListener { v ->
			val editedMessage = FirestoreOrder(fullName!!, numOfPickles, addHummus, addTahini, comment!!, 0, orderID!!)
			dataManager?.editOrderFromDB(orderID!!, editedMessage, ::editMessageHandler)
		}

		deleteBtn.setOnClickListener { v ->
			dataManager?.deleteOrderFromDB(orderID!!, ::deleteOrderHandler)
		}
	}

	fun editMessageHandler(response: String?){
		Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
	}

	fun deleteOrderHandler(response: Boolean){
		if(response){
			dataManager?.setLastOrderID(null)
			val intent = Intent(this, PlaceOrder::class.java)
			startActivity(intent)
		}
		else{
			Toast.makeText(this, "Failed to delete order", Toast.LENGTH_SHORT).show()
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putString("fullName", fullName)
		outState.putInt("numOfPickles", numOfPickles)
		outState.putBoolean("addHummus", addHummus)
		outState.putBoolean("addTahini", addTahini)
		outState.putString("comment", comment)
		outState.putString("status", status)
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		fullName = savedInstanceState.getString("fullName")
		numOfPickles = savedInstanceState.getInt("numOfPickles")
		addHummus = savedInstanceState.getBoolean("addHummus")
		addTahini = savedInstanceState.getBoolean("addTahini")
		comment = savedInstanceState.getString("comment")
		status = savedInstanceState.getString("status")
		val fullNameField = findViewById<View>(R.id.name) as EditText
		val numOfPicklesField = findViewById<View>(R.id.picklesPicker) as NumberPicker
		val addHummusField = findViewById<View>(R.id.hummusCheckBox) as CheckBox
		val addTahiniField = findViewById<View>(R.id.tahiniCheckBox) as CheckBox
		val commentField = findViewById<View>(R.id.commentContent) as EditText
		val statusField = findViewById<View>(R.id.orderStatus) as TextView
		fullNameField.setText(fullName)
		numOfPicklesField.value = numOfPickles
		addHummusField.isChecked = addHummus
		addTahiniField.isChecked = addTahini
		commentField.setText(comment)
		statusField.text = status
	}
}