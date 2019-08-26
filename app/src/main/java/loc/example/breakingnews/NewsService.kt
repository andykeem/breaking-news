package loc.example.breakingnews

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// https://newsapi.org/v2/everything?q=bitcoin&from=2019-07-25&sortBy=publishedAt&apiKey=987aa97c056746e6856b61f06abfc951
interface NewsService {

//    @Headers("x-api-key HTTP header: 987aa97c056746e6856b61f06abfc951")
    @GET("?apiKey=987aa97c056746e6856b61f06abfc951&sortBy=publishedAt&from=2019-08-21")
    fun listNews(@Query("q") q: String): Call<News>
}