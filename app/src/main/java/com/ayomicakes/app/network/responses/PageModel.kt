package com.ayomicakes.app.network.responses

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PageModel<T>(

	@SerialName("result")
	val result: List<T>? = null,

	@SerialName("page")
	val page: Int,

	@SerialName("pageCount")
	val pageCount: Int,

	@SerialName("perPage")
	val perPage: Int,

	@SerialName("totalCount")
	val totalCount: Int
)
