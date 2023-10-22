package com.example.stressapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import java.sql.Timestamp


class StressPlotFragment : Fragment() {

    companion object {
        fun newInstance() = StressPlotFragment()
    }

    private lateinit var viewModel: StressPlotViewModel
    private lateinit var anyChartView: AnyChartView
    private lateinit var table: TableLayout
    private lateinit var lineChart: Cartesian
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stress_plot, container, false)
        Log.i("Test", "On Create view")

        lineChart = AnyChart.line()
        lineChart.xAxis(0).title("Instance")
        lineChart.yAxis(0).title("Stress Level")
        anyChartView = view.findViewById(R.id.any_chart_view)

        table = view.findViewById(R.id.tlTable)

        viewModel = ViewModelProvider(this).get(StressPlotViewModel::class.java)
        viewModel.stressData.observe(viewLifecycleOwner) { stressDataList ->
            Log.i("Test", "Inside the stressData")
            Log.i("Test", lineChart.toString())
            setChartData(stressDataList)
            setTableData(stressDataList)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Test", "On view created")
        viewModel.fetchStressData()
    }

    fun setChartData(stressDataList: List<StressData>) {
        val chartData = stressDataList.mapIndexed { index, stressData ->
            ValueDataEntry(index, stressData.stressLevel)
        }
        lineChart.data(chartData)

        anyChartView.setChart(lineChart)
    }

    fun setTableData(stressDataList: List<StressData>) {
        table.removeViews(1, table.childCount - 1)

        for (stressData in stressDataList) {
            val row = TableRow(context)
            val timestampTextView = TextView(requireContext()).apply {
                text = Timestamp(stressData.timestamp).toString()
                gravity = Gravity.CENTER
            }
            val stressLevelTextView = TextView(requireContext()).apply {
                text = stressData.stressLevel.toString()
                gravity = Gravity.CENTER
            }

            row.addView(timestampTextView)
            row.addView(stressLevelTextView)

            table.addView(row)
        }
    }

}