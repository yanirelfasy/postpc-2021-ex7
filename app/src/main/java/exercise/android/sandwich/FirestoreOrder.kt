package exercise.android.sandwich

data class FirestoreOrder (
	var firstName: String = "",
	var numOfPickles: Int = 0,
	var addHummus: Boolean = false,
	var addTahini: Boolean = false,
	var comment: String = "",
	var status: Int = 0,
	var orderID: String = ""
)