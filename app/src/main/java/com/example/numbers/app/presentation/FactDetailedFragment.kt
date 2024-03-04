package com.example.numbers.app.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.numbers.data.NumberFact
import com.example.numbers.databinding.FragmentDetailedFactBinding

class FactDetailedFragment : Fragment() {

    private var _binding: FragmentDetailedFactBinding? = null
    private val binding: FragmentDetailedFactBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailedFactBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedFactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_FACT, NumberFact::class.java)
                ?: throw RuntimeException("DetailedUser arg == null")
        } else {
            @Suppress("DEPRECATION") arguments?.getParcelable(ARG_FACT) as? NumberFact
                ?: throw RuntimeException("DetailedUser arg == null")
        }
        binding.tvFact.text = fact.fact
        binding.tvSelectedNumber.text = fact.number.toString()
    }

    companion object {
        private const val ARG_FACT = "fact"

        @JvmStatic
        fun newInstance(fact: NumberFact) =
            FactDetailedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_FACT, fact)
                }
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}