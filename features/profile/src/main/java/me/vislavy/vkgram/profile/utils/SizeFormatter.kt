package me.vislavy.vkgram.profile.utils

private const val ByteInKB = 1024
private const val ByteInMB = ByteInKB * 1024

fun formatSize(value: Int): String = when {
    value < ByteInKB -> "$value K"
    value < ByteInMB -> "${value / ByteInKB} КБ"
    else -> "${value / ByteInMB} МБ"
}