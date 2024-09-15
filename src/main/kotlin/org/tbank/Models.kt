package org.tbank

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.exp

@Serializable
data class ParsedResponse(val results: List<News>)

@Serializable
data class News(
    val id: Int,
    val title: String,
    val place: Place?,
    @SerialName("publication_date")
    val publicationDate: Long,
    val description: String,
    @SerialName("site_url")
    val siteUrl: String?,
    @SerialName("favorites_count")
    val favoritesCount: Int,
    @SerialName("comments_count")
    val commentsCount: Int,
) {
    val rating: Double by lazy { 1 / (1 + exp(-(favoritesCount.toDouble() / (commentsCount + 1)))) }
    val date: LocalDate by lazy { Instant.ofEpochSecond(publicationDate).atZone(ZoneId.systemDefault()).toLocalDate() }
}

@Serializable
data class Place(val id: Int)
