package me.vislavy.vkgram.api.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object BooleanSerializer : KSerializer<Boolean> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("VKBoolean", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Boolean) {
        val encodedValue = if (value) 1 else 0
        encoder.encodeInt(encodedValue)
    }

    override fun deserialize(decoder: Decoder): Boolean {
        return (decoder.decodeInt() == 1)
    }
}