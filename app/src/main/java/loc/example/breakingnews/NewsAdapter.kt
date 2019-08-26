package loc.example.breakingnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(var items: List<Article>) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return NewsViewHolder(view);
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val item: Article = items.get(position)
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(item: Article) {
        val textView = itemView.findViewById<TextView>(android.R.id.text1)
        textView.text = item.title
    }
}
