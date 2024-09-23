package org.tbank

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.io.File
import java.time.LocalDate

private val LOGGER = LoggerFactory.getLogger("Lesson4MainNews")

fun main() {
    val news = getNews(777)
    val mostRatedNews = news.getMostRatedNews(42, LocalDate.parse("2012-10-10")..LocalDate.parse("2012-11-01"))
    saveNews("data_output/news.csv", mostRatedNews)
}

fun getNews(count: Int = 100): List<News> {
    if (count <= 0) return emptyList()

    val news = mutableListOf<News>()
    val json = Json { ignoreUnknownKeys = true }

    runBlocking {
        val client = HttpClient(CIO)
        try {
            val pageCount = ((count - 1) / 100) + 1
            repeat(pageCount) {
                val page = it + 1
                LOGGER.info("Load page #$page ...")
                val response = client.get("https://kudago.com/public-api/v1.4/news/?fields=id,title,place,description,publication_date,site_url,favorites_count,comments_count&text_format=text&order_by=publication_date&location=msk&page_size=100&page=$page")
                val parsedResponse = json.decodeFromString<ParsedResponse>(response.bodyAsText())
                news.addAll(parsedResponse.results)
            }
            LOGGER.info("${news.size} news have been successfully loaded")
        } catch (e: Exception) {
            LOGGER.error("Error while loading news", e)
        } finally {
            client.close()
        }
    }

    return news.take(count)
}

fun List<News>.getMostRatedNews(count: Int, period: ClosedRange<LocalDate>): List<News> {
    return filter { it.date in period }
        .sortedBy { it.rating }
        .take(count)
}

fun saveNews(path: String, news: Collection<News>) {
    val file = File(path)
    if (path.isEmpty() || file.isDirectory || file.exists()) {
        LOGGER.error("Wrong path or file already exists: '$path'")
        return
    }
    try {
        file.printWriter().use { out ->
            out.println("id,title,place,publicationDate,description,siteUrl,favoritesCount,commentsCount")
            news.forEach {
                out.println("${it.id},${it.title.escapeIfNeeded()},${it.place?.id},${it.publicationDate},${it.description.escapeIfNeeded()},\"${it.siteUrl}\",${it.favoritesCount},${it.commentsCount}")
            }
        }
        LOGGER.info("News (${news.size}) was successfully written to '$path'")
    } catch (e: Exception) {
        LOGGER.error("Error while writing news to '$path'")
    }
}
