package com.example.numbers.app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numbers.R
import com.example.numbers.app.presentation.rv.FactsListAdapter
import com.example.numbers.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val viewModel: FactsViewModel by inject()

    private val recyclerAdapter by lazy { FactsListAdapter() }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvStoryFacts.adapter = recyclerAdapter
            rvStoryFacts.layoutManager = LinearLayoutManager(requireContext())
            recyclerAdapter.onFactClickListener = viewModel::getDetailedFactInfo
            buttonFactRandom.setOnClickListener { viewModel.loadNewFactRandom() }
            buttonFactWithNumber.setOnClickListener { viewModel.checkInputAndLoadFact(etInputNumber.editableText) }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            when (it) {
                is MainScreenState.DetailedInfo -> {
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.add(
                            R.id.mainFrame,
                            FactDetailedFragment.newInstance(it.fact)
                        )
                        ?.addToBackStack(null)
                        ?.commit()
                }
                MainScreenState.Error -> Toast.makeText(
                    context,
                    "Відбулась помилка при завантаженні даних!",
                    Toast.LENGTH_LONG
                ).show()
                is MainScreenState.HistoryLoaded -> {
                    recyclerAdapter.submitList(it.facts)
                }
                MainScreenState.Initial -> {
                    Toast.makeText(
                        context,
                        "Завантаження даних!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}