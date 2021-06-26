package exercise.android.sandwich

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	var dataManager: DataManager? = null;

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportActionBar!!.hide()

		if(dataManager == null){
			dataManager = SandwichApplication.getInstance().dataManager
		}

		setContentView(R.layout.activity_main)
		val lastOrderID: String? = dataManager?.getLastOrderID()
		if(lastOrderID == null){
			val intent = Intent(this, PlaceOrder::class.java)
			startActivity(intent)
		}
		else{
			dataManager?.getOrderFromDB(lastOrderID, ::setOrderDetails)
		}


	}


	fun setOrderDetails(orderToSet :FirestoreOrder?){
		if(orderToSet != null){
			val intent = Intent(this, EditOrder::class.java)
			intent.putExtra("orderDetails", orderToSet)
			startActivity(intent)
		}
	}
}