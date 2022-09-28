package com.ayomicakes.app.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.ayomicakes.app.datastore.serializer.ProfileStore
import com.google.crypto.tink.Aead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

@ExperimentalSerializationApi
class ProfileStoreSerializer (
    private val aeAd: Aead
) : Serializer<ProfileStore> {
    override val defaultValue: ProfileStore
        get() = ProfileStore()

    override suspend fun readFrom(input: InputStream): ProfileStore {
        return try {
            val encryptedInput = input.readBytes()
            val decryptedInput = if (encryptedInput.isNotEmpty()) {
                aeAd.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }

            ProtoBuf.decodeFromByteArray(ProfileStore.serializer(), decryptedInput)
        } catch (e: SerializationException) {
            throw CorruptionException("Error deserializing proto", e)
        }
    }

    override suspend fun writeTo(t: ProfileStore, output: OutputStream) {
        val byteArray = ProtoBuf.encodeToByteArray(ProfileStore.serializer(), t)
        val encryptedBytes = aeAd.encrypt(byteArray, null)

        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }
}