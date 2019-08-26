package loc.example.breakingnews


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingnews.R
import com.example.breakingnews.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: NewsAdapter

    companion object {
        val TAG: String = MainFragment::class.java.simpleName

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.news.observe(this) {
            Log.d(TAG, "news: ${it.articles}")
            if (!it.articles.isEmpty()) {
                adapter.items = it.articles
                adapter.notifyDataSetChanged()
//                binding.notifyChange()
                hideSoftKeyboard()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsList.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(emptyList())
        binding.newsList.adapter = adapter
        binding.newsList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val searchView: SearchView = menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(TAG, "query: $query")
                viewModel.submitQuery(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    private fun hideSoftKeyboard() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val view: View? = activity?.currentFocus
        view?.let {
            val inputMethodMngr: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodMngr.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}