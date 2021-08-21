package ru.vyapps.vkgram.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class VKDateAsLongSerializer : KSerializer<VKDate> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("VKDate", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: VKDate) {
        val date = value.date / 1000
        encoder.encodeLong(date)
    }

    override fun deserialize(decoder: Decoder): VKDate {
        val date = decoder.decodeLong() * 1000
        return VKDate(date)
    }
}