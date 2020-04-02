package tech.alvarez.numbers.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.search_fragment.*
import tech.alvarez.numbers.R
import tech.alvarez.numbers.adapters.OnItemClickListener
import tech.alvarez.numbers.adapters.SearchChannelsAdapter
import tech.alvarez.numbers.model.Item


class SearchFragment : Fragment(), OnItemClickListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: SearchChannelsAdapter

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        adapter = SearchChannelsAdapter(this)
        recyclerView.adapter = adapter

        viewModel.channels.observe(viewLifecycleOwner, Observer {
//            Log.i(Constants.TAG, it.toString())
            adapter.setDataset(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(channel: Item?) {
        Toast.makeText(context, "click", Toast.LENGTH_LONG).show()
    }

}
