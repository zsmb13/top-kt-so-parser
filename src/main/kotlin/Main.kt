import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URL
import java.time.LocalDate

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

data class ScoreEntry(val name: String, val score: Int)

private fun Document.getScoreEntries(): List<ScoreEntry> {
    val allTimeTitle = this.findAllTimeTitle() ?: throw IllegalStateException("No title found")

    val table = allTimeTitle.parent().child(1)
    val tableBody = table.child(0)

    val rows = tableBody.children()

    return rows.map { row ->
        val score = row.child(0).child(0).attr("title").substringAfterLast(' ').toInt()
        val name = row.child(2).selectFirst(".user-details").child(0).text()
        ScoreEntry(name, score)
    }
}

fun LocalDate.format() = String.format("%04d%02d%02d", year, monthValue, dayOfMonth)

fun main(args: Array<String>) {
    val machine = WaybackMachine()

    val now = LocalDate.now()
    val archiveUrl = machine.getArchiveUrl("https://stackoverflow.com/tags/kotlin/topusers", now.format())

    println(archiveUrl)

    val document: Document = Jsoup.parse(URL(archiveUrl), 5000)
    val scoreEntries = document.getScoreEntries()

    println(scoreEntries)
}
