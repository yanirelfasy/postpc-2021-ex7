package exercise.android.sandwich

import java.io.Serializable

data class FirestoreOrder (
		var fullName: String = "",
		var numOfPickles: Int = 0,
		var addHummus: Boolean = false,
		var addTahini: Boolean = false,
		var comment: String = "",
		var status: Int = 0,
		var orderID: String = ""
): Serializable