package com.example.stressapp

import android.content.Intent
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
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import java.sql.Timestamp


class StressPlotFragment : Fragment() {
    private lateinit var viewModel: StressPlotViewModel
    private lateinit var anyChartView: AnyChartView
    private lateinit var table: TableLayout
    private lateinit var lineChart: Cartesian
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stress_plot, container, false)

        lineChart = AnyChart.line()
        lineChart.noData().label().enabled(true)
        lineChart.xAxis(0).title("Instance")
        lineChart.yScale().minimum(0).maximum(16)
        lineChart.yAxis(0).title("Stress Level")
        anyChartView = view.findViewById(R.id.any_chart_view)

        table = view.findViewById(R.id.tlTable)

        viewModel = ViewModelProvider(this).get(StressPlotViewModel::class.java)
        viewModel.stressData.observe(viewLifecycleOwner) { stressDataList ->
            setChartData(stressDataList)
            setTableData(stressDataList)
        }

        Intent(requireContext(), SoundService::class.java).also {
            requireContext().stopService(it)
        }

        Intent(requireContext(), VibrationService::class.java).also {
            requireContext().stopService(it)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        anyChartView.setChart(lineChart)
        viewModel.fetchStressData()
    }

   private fun setChartData(stressDataList: List<StressData>) {
//       Cannot get chart to render when there is no data so setting placeholder until there is data
        val chartData = if (stressDataList.isEmpty()) {
            listOf(ValueDataEntry(0, 0))
        } else {
            stressDataList.mapIndexed { index, stressData ->
                ValueDataEntry((index + 1), stressData.stressLevel)
            }
        }
        lineChart.data(chartData)
        lineChart.marker(chartData)

        anyChartView.setChart(lineChart)
    }

    private fun setTableData(stressDataList: List<StressData>) {
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