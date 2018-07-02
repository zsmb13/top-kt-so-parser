import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL

fun main(args: Array<String>) {
    val document: Document = Jsoup.parse(URL("https://stackoverflow.com/tags/kotlin/topusers"), 5000)
    println(document)
}
