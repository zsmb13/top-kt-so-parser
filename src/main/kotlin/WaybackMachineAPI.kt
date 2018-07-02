import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WaybackMachineAPI {

    data class AvailableResponse(val archived_snapshots: SnapshotsResponse)

    data class SnapshotsResponse(val closest: SnapShotData)

    data class SnapShotData(val available: Boolean, val url: String, val timestamp: String, val status: String)

    @GET("available")
    fun getAvailable(
            @Query("url") siteUrl: String,
            @Query("timestamp") timestamp: String
    ): Call<AvailableResponse>

}
