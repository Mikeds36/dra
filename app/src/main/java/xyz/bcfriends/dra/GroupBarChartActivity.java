package xyz.bcfriends.dra;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class GroupBarChartActivity extends AppCompatActivity {

    BarChart mpBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_stat);
        mpBarChart = findViewById(R.id.barchart);

        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"1주차");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"2주차");
        barDataSet2.setColor(Color.BLUE);
        BarDataSet barDataSet3 = new BarDataSet(barEntries3(),"3주차");
        barDataSet3.setColor(Color.MAGENTA);
        BarDataSet barDataSet4 = new BarDataSet(barEntries4(),"4주차");
        barDataSet4.setColor(Color.GREEN);

        BarData data = new BarData(barDataSet1,barDataSet2,barDataSet3,barDataSet4);
        mpBarChart.setData(data);

        String[] days = new String[] {"1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
        XAxis xAxis = mpBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        mpBarChart.setDragEnabled(true);
        mpBarChart.setVisibleXRangeMaximum(3);

        float barSpace = 0.1f;
        float groupSpace = 0.16f;
        data.setBarWidth(0.10f);

        mpBarChart.getXAxis().setAxisMinimum(0);
        mpBarChart.getXAxis().setAxisMaximum(0+mpBarChart.getBarData().getGroupWidth(groupSpace,barSpace)*7);
        mpBarChart.getAxisLeft().setAxisMinimum(0);

        mpBarChart.groupBars(0,groupSpace,barSpace);

        mpBarChart.invalidate();
    }

    private ArrayList<BarEntry> barEntries1 () {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,2000));
        barEntries.add(new BarEntry(2,791));
        barEntries.add(new BarEntry(3,630));
        barEntries.add(new BarEntry(4,458));
        barEntries.add(new BarEntry(5,2724));
        barEntries.add(new BarEntry(6,500));
        barEntries.add(new BarEntry(7,2173));
        return barEntries;
    }


    private ArrayList<BarEntry> barEntries2 () {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,900));
        barEntries.add(new BarEntry(2,631));
        barEntries.add(new BarEntry(3,1040));
        barEntries.add(new BarEntry(4,382));
        barEntries.add(new BarEntry(5,2614));
        barEntries.add(new BarEntry(6,5000));
        barEntries.add(new BarEntry(7,2173));
        return barEntries;
    }


    private ArrayList<BarEntry> barEntries3 () {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,400));
        barEntries.add(new BarEntry(2,291));
        barEntries.add(new BarEntry(3,1230));
        barEntries.add(new BarEntry(4,1168));
        barEntries.add(new BarEntry(5,114));
        barEntries.add(new BarEntry(6,950));
        barEntries.add(new BarEntry(7,173));
        return barEntries;
    }


    private ArrayList<BarEntry> barEntries4 () {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,100));
        barEntries.add(new BarEntry(2,291));
        barEntries.add(new BarEntry(3,1230));
        barEntries.add(new BarEntry(4,1168));
        barEntries.add(new BarEntry(5,114));
        barEntries.add(new BarEntry(6,960));
        barEntries.add(new BarEntry(7,173));
        return barEntries;
    }
}
