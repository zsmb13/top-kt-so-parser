import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WaybackMachine {

    private val api = Retrofit.Builder()
            .baseUrl("http://archive.org/wayback/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WaybackMachineAPI::class.java)

    fun getArchiveUrl(siteUrl: String, date: String): String? {
        val result = api.getAvailable(siteUrl, date).execute()
        return result.body()?.archived_snapshots?.closest?.url
    }

}
