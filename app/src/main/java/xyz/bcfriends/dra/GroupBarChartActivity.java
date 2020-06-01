package xyz.bcfriends.dra;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GroupBarChartActivity extends AppCompatActivity {

    BarChart mpBarChart;

    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
    ArrayList<BarEntry> barEntries2 = new ArrayList<>();
    ArrayList<BarEntry> barEntries3 = new ArrayList<>();
    ArrayList<BarEntry> barEntries4 = new ArrayList<>();
    ArrayList<BarEntry> barEntries5 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_stat);
        mpBarChart = findViewById(R.id.barchart);

        some();

        BarDataSet barDataSet1 = new BarDataSet(barEntries1,"1주차");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2,"2주차");
        barDataSet2.setColor(Color.BLUE);
        BarDataSet barDataSet3 = new BarDataSet(barEntries3,"3주차");
        barDataSet3.setColor(Color.MAGENTA);
        BarDataSet barDataSet4 = new BarDataSet(barEntries4,"3주차");
        barDataSet4.setColor(Color.GRAY);
        BarDataSet barDataSet5 = new BarDataSet(barEntries5,"3주차");
        barDataSet5.setColor(Color.GREEN);

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5);
        mpBarChart.setData(data);

        String[] days = new String[] {"1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
        XAxis xAxis = mpBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        mpBarChart.setDragEnabled(true);
        mpBarChart.setVisibleXRangeMaximum(10);

        float barSpace = 0.1f;
        float groupSpace = 0.05f;
        data.setBarWidth(0.09f);

        mpBarChart.getXAxis().setAxisMinimum(0);
        mpBarChart.getXAxis().setAxisMaximum(0 + mpBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * 12);
//        mpBarChart.getAxisLeft().setAxisMinimum(0);

        mpBarChart.groupBars(0,groupSpace,barSpace);

        mpBarChart.invalidate();
    }

//    [4,5,6,7,8], [10,4,2,6,8]
    private void some() {
        // 배열 길이: [31][5]
        int[][] in = new int[31][5];
        in[0] = new int[]{4, 5, 6, 7, 8};
        in[1] = new int[]{10, 4, 2, 6, 8};
        in[2] = new int[]{10, 4, 2, 6, 8};


        for (int i = 0; i < 31; i++){
            barEntries1.add(new BarEntry(i, in[i][0]));
            barEntries2.add(new BarEntry(i, in[i][1]));
            barEntries3.add(new BarEntry(i, in[i][2]));
            barEntries4.add(new BarEntry(i, in[i][3]));
            barEntries5.add(new BarEntry(i, in[i][4]));
        }
    }
//
//    private ArrayList<BarEntry> barEntries1 () {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(1,100));
//        barEntries.add(new BarEntry(2,591));
//        barEntries.add(new BarEntry(3,200));
//        return barEntries;
//    }
//
//    private ArrayList<BarEntry> barEntries2 () {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(1,500));
//        barEntries.add(new BarEntry(2,631));
//        barEntries.add(new BarEntry(3,350));
//
//        return barEntries;
//    }
//
//    private ArrayList<BarEntry> barEntries3 () {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(1,400));
//        barEntries.add(new BarEntry(2,291));
//        barEntries.add(new BarEntry(3,224));
//        return barEntries;
//    }
}
