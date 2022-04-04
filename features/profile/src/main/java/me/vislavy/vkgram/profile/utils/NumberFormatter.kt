package me.vislavy.vkgram.profile.utils

fun formatNumber(value: Int): String {
    return when {
        value >= 1000000000 -> "${String.format("%.1f", value / 1000000000F)}B"
        value >= 1000000 -> "${String.format("%.1f", value / 1000000F)}M"
        value >= 1000 -> "${String.format("%.1f", value / 1000F)}K"
        else -> value.toString()
    }
}