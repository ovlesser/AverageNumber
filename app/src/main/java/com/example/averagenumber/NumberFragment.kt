package com.example.averagenumber

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.averagenumber.databinding.NumberFragmentBinding
import com.example.number.Average
import kotlinx.android.synthetic.main.number_fragment.*


class NumberFragment : Fragment() {

    companion object {
        fun newInstance() = NumberFragment()
    }

    private val TAG = NumberFragment.javaClass.simpleName
    private lateinit var viewModel: NumberViewModel
    private lateinit var dataBinding: NumberFragmentBinding
    private lateinit var average : Average

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.number_fragment, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.submit.setOnClickListener {
            viewModel.number.value?.let {
                average.addNumber(it.toDouble())
            }
        }
        dataBinding.getAverage.setOnClickListener {
            viewModel.average.value = average.getAverage().toString()
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NumberViewModel::class.java)
        // TODO: Use the ViewModel
        average = Average((context!!))
        dataBinding.viewModel = viewModel
        viewModel.number.observe(viewLifecycleOwner, Observer {
            try {
                it.toDouble()
            }
            catch (e: NumberFormatException){
                Log.d(TAG, e.stackTrace.toString())
            }
        })
    }

}
