package exercise.android.sandwich

import android.content.Context
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LooperMode(LooperMode.Mode.PAUSED)
class Tests {
	private var mainActivityController: ActivityController<MainActivity>? = null
	private var editOrderActivityController: ActivityController<EditOrder>? = null
	private var dataManager: DataManager ?= null;

	@Mock
	private val context: Context? = null

	@Before
	fun setup() {
		MockitoAnnotations.openMocks(this)
		mainActivityController = Robolectric.buildActivity(MainActivity::class.java)
		editOrderActivityController = Robolectric.buildActivity(EditOrder::class.java)
	}

	@Test
	fun when_orderStatusChange_then_editOrder_should_finish() {
		// setup
		val order = FirestoreOrder("Testing Order", 5, true, false, "Test Comment", 0, "nV9zmiM6i2rRdj958koN")
		val intent = Intent(context, EditOrder::class.java)
		intent.putExtra("orderDetails", order)
		editOrderActivityController = Robolectric.buildActivity(EditOrder::class.java, intent)

		editOrderActivityController!!.create().visible()
		val activityUnderTest = editOrderActivityController!!.get()

		activityUnderTest.listenerHandler(order)
		assertFalse(activityUnderTest.isFinishing)
		order.status = 1
		activityUnderTest.listenerHandler(order)
		assertTrue(activityUnderTest.isFinishing)
	}

	@Test
	fun when_settingFullName_Then_FullName_should_be_set(){
		mainActivityController!!.create().visible()
		val activityUnderTest = mainActivityController!!.get()
		dataManager = DataManager(activityUnderTest)
		dataManager!!.setFullName("Testing Name")
		assertEquals("Testing Name", dataManager!!.getFullName())
	}

	@Test
	fun when_LastID_Then_LastID_should_be_set(){
		mainActivityController!!.create().visible()
		val activityUnderTest = mainActivityController!!.get()
		dataManager = DataManager(activityUnderTest)
		dataManager!!.setLastOrderID("nV9zmiM6i2rRdj958koN")
		assertEquals("nV9zmiM6i2rRdj958koN", dataManager!!.getLastOrderID())
	}



}