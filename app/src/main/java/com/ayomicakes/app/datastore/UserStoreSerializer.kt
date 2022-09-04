package com.ayomicakes.app.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.ayomicakes.app.datastore.serializer.UserStore
import com.google.crypto.tink.Aead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

@ExperimentalSerializationApi
class UserStoreSerializer (
    private val aeAd: Aead
) : Serializer<UserStore> {
    override val defaultValue: UserStore
        get() = UserStore()

    override suspend fun readFrom(input: InputStream): UserStore {
        return try {
            val encryptedInput = input.readBytes()
            val decryptedInput = if (encryptedInput.isNotEmpty()) {
                aeAd.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }

            ProtoBuf.decodeFromByteArray(UserStore.serializer(), decryptedInput)
        } catch (e: SerializationException) {
            throw CorruptionException("Error deserializing proto", e)
        }
    }

    override suspend fun writeTo(t: UserStore, output: OutputStream) {
        val byteArray = ProtoBuf.encodeToByteArray(UserStore.serializer(), t)
        val encryptedBytes = aeAd.encrypt(byteArray, null)

        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }
}