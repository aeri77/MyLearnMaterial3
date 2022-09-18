package com.ayomicakes.app.network.responses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class GeoCodeResponse(

	@SerialName("plus_code")
	val plusCode: PlusCode? = null,

	@SerialName("results")
	val results: List<ResultsItem?>? = null,

	@SerialName("status")
	val status: String? = null
)

@kotlinx.serialization.Serializable
data class Southwest(

	@SerialName("lng")
	val lng: Double? = null,

	@SerialName("lat")
	val lat: Double? = null
)

@kotlinx.serialization.Serializable
data class Geometry(

	@SerialName("viewport")
	val viewport: Viewport? = null,

	@SerialName("bounds")
	val bounds: Bounds? = null,

	@SerialName("location")
	val location: Location? = null,

	@SerialName("location_type")
	val locationType: String? = null
)

@kotlinx.serialization.Serializable
data class ResultsItem(

	@SerialName("formatted_address")
	val formattedAddress: String? = null,

	@SerialName("types")
	val types: List<String?>? = null,

	@SerialName("geometry")
	val geometry: Geometry? = null,

	@SerialName("address_components")
	val addressComponents: List<AddressComponentsItem?>? = null,

	@SerialName("place_id")
	val placeId: String? = null,

	@SerialName("plus_code")
	val plusCode: PlusCode? = null
)

@kotlinx.serialization.Serializable
data class PlusCode(

	@SerialName("compound_code")
	val compoundCode: String? = null,

	@SerialName("global_code")
	val globalCode: String? = null
)

@kotlinx.serialization.Serializable
data class AddressComponentsItem(

	@SerialName("types")
	val types: List<String?>? = null,

	@SerialName("short_name")
	val shortName: String? = null,

	@SerialName("long_name")
	val longName: String? = null
)

@kotlinx.serialization.Serializable
data class Northeast(

	@SerialName("lng")
	val lng: Double? = null,

	@SerialName("lat")
	val lat: Double? = null
)

@kotlinx.serialization.Serializable
data class Location(

	@SerialName("lng")
	val lng: Double? = null,

	@SerialName("lat")
	val lat: Double? = null
)

@kotlinx.serialization.Serializable
data class Viewport(

	@SerialName("southwest")
	val southwest: Southwest? = null,

	@SerialName("northeast")
	val northeast: Northeast? = null
)

@kotlinx.serialization.Serializable
data class Bounds(

	@SerialName("southwest")
	val southwest: Southwest? = null,

	@SerialName("northeast")
	val northeast: Northeast? = null
)
