package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PageModel<T>(

	@SerialName("result")
	val result: List<T>? = null,

	@SerialName("pageCount")
	val pageCount: Int? = null,

	@SerialName("perPage")
	val perPage: Int? = null,

	@SerialName("totalCount")
	val totalCount: Int? = null
)

@kotlinx.serialization.Serializable
data class CakeItem(

	@SerialName("uid")
	val uid: String? = null,

	@SerialName("price")
	val price: Double? = null,

	@SerialName("cakeName")
	val cakeName: String? = null,

	@SerialName("category")
	val category: String? = null,

	@SerialName("stock")
	val stock: Int? = null,
	
	@SerialName("imgUrl")
	val imgUrl: String? = null
)
