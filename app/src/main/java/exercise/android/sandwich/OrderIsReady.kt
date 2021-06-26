package exercise.android.sandwich

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class OrderIsReady : AppCompatActivity() {
	private var listener: ListenerRegistration? = null
	private var dataManager: DataManager? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportActionBar!!.hide()
		if (dataManager == null) {
			dataManager = SandwichApplication.getInstance().dataManager
		}
		setContentView(R.layout.activity_order_is_ready)

		listener = FirebaseFirestore.getInstance().collection("orders").document(dataManager?.getLastOrderID()!!).addSnapshotListener { value, error ->
			if (error != null) {
				Toast.makeText(this, "Failed to add listener", Toast.LENGTH_SHORT).show()
			} else if (value == null) {
				Toast.makeText(this, "Failed to add listener", Toast.LENGTH_SHORT).show()
			} else if (!value.exists()) {
				deleteOrderHandler(true)
			} else if (value.exists()) {
				val newOrder: FirestoreOrder? = value.toObject(FirestoreOrder::class.java)
				if (newOrder?.status == 1) {
					backToInProgress()
				}
				else if(newOrder?.status == 0){
					dataManager?.getOrderFromDB(dataManager?.getLastOrderID()!!, ::backToOrderEdit)
				}
				else if(newOrder?.status == 3){
					dataManager?.deleteOrderFromDB(dataManager?.getLastOrderID()!!, ::deleteOrderHandler)
				}
			}
		}

		val gotOrderBtn = findViewById<View>(R.id.gotOrder) as Button
		gotOrderBtn.setOnClickListener {
			dataManager?.changeOrderStatus(dataManager?.getLastOrderID()!!, 3)
		}
	}

	fun deleteOrderHandler(response: Boolean){
		if(response){
			dataManager?.setLastOrderID("")
			val intent = Intent(this, PlaceOrder::class.java)
			startActivity(intent)
		}
		else{
			Toast.makeText(this, "Failed to delete order", Toast.LENGTH_SHORT).show()
		}
	}

	fun backToOrderEdit(orderDetails: FirestoreOrder?){
		val intent = Intent(this, EditOrder::class.java)
		intent.putExtra("orderDetails", orderDetails)
		startActivity(intent)
	}

	fun backToInProgress(){
		val intent = Intent(this, OrderInProgress::class.java)
		startActivity(intent)
	}

	override fun onDestroy() {
		super.onDestroy()
		if(listener != null){
			listener?.remove()
		}
	}
}