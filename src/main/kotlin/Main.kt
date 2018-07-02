import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URL

private fun Element.findAllTimeTitle(): Element? {
    if (this.tagName() == "h3" && this.ownText() == "All Time") {
        return this
    } else {
        children().forEach { child ->
            val node = child.findAllTimeTitle()
            if (node != null) {
                return node
            }
        }
    }
    return null
}

fun main(args: Array<String>) {
    val document: Document = Jsoup.parse(URL("https://stackoverflow.com/tags/kotlin/topusers"), 5000)

    val allTimeTitle = document.findAllTimeTitle() ?: throw IllegalStateException("No title found")

    val table = allTimeTitle.parent().child(1)
    val tableBody = table.child(0)

    val rows = tableBody.children()

    data class ScoreEntry(val name: String, val score: Int)

    val scoreEntries = rows.map { row ->
        val score = row.child(0).child(0).attr("title").substringAfterLast(' ').toInt()
        val name = row.child(2).selectFirst(".user-details").child(0).text()
        ScoreEntry(name, score)
    }

    println(scoreEntries)
}
