package loc.example.breakingnews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    val retrofit: Retrofit by lazy {
        setRetrofit()
    }
    val query: MutableLiveData<String> = MutableLiveData<String>()
    val news: LiveData<News> = Transformations.switchMap(query) {
        fetchNews(it)
    }

    companion object {
        val TAG = MainViewModel::class.java.simpleName
        const val HOST_URL = "https://newsapi.org/v2/everything/"
    }

    private fun setRetrofit(): Retrofit {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY // BASIC

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    }

    fun submitQuery(q: String): Unit {
        if (q.isEmpty() || q.isBlank() || q == query.value) {
            return
        }
        query.value = q
    }

    fun fetchNews(term: String): LiveData<News> {
        val news: MutableLiveData<News> = MutableLiveData<News>()
        val newsService: NewsService = retrofit.create(NewsService::class.java)
        val resp: Call<News> = newsService.listNews(term)
        resp.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                Log.d(TAG, "response: ${response}")
                news.value = response.body() ?: News()
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e(TAG, "err: ${t.message}", t)
            }
        })
        return news
    }
}