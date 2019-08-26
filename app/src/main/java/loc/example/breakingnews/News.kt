package loc.example.breakingnews

data class News(val status: String = "",
                val totalResults: Int = -1,
                val articles: List<Article> = emptyList())