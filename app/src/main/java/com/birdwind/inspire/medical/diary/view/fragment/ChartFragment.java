package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentChartBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;

public class ChartFragment extends AbstractFragment<AbstractPresenter, FragmentChartBinding>
    implements OnChartValueSelectedListener {

    private long uid;

    private String name;

    private String avater;

    // private Cartesian cartesian;
    //
    // private List<DataEntry> seriesData;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentChartBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentChartBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.btMessageChartFragment.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("UID", uid);
            bundle.putString("name", name);
            bundle.putString("avatar", avater);

            ChatFragment chatFragment = new ChatFragment();
            chatFragment.setArguments(bundle);

            pushFragment(chatFragment);
        });

        binding.btRequestChartFragment.setOnClickListener(v -> {
            showToast(getString(R.string.function_not_complete));
        });

        binding.lcChartFragment.setOnChartValueSelectedListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getLong("UID", App.userModel.getUid());
            name = bundle.getString("name", "");
            avater = bundle.getString("avatar", "");
        } else {
            uid = App.userModel.getUid();
            name = "";
            avater = "";
        }

        // cartesian = AnyChart.line();
        // cartesian.animation(true);
        // cartesian.padding(10d, 20d, 5d, 20d);
        // cartesian.crosshair().enabled(true);
        // cartesian.crosshair().yLabel(true).yStroke((Stroke) null, null, null, (String) null, (String) null);
        //
        // cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        //
        // cartesian.title("Family Survey History");
        //
        //// cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        // cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        //
        // seriesData = new ArrayList<>();
        // seriesData.add(new ChartDataModel("1986", 3.6));
        // seriesData.add(new ChartDataModel("1987", 7.1));
        // seriesData.add(new ChartDataModel("1988", 8.5));
        // seriesData.add(new ChartDataModel("1989", 9.2));
        // seriesData.add(new ChartDataModel("1990", 10.1));
        //
        // Set set = Set.instantiate();
        // set.data(seriesData);
        // Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        //
        // Line series1 = cartesian.line(series1Mapping);
        // series1.name("Brandy");
        // series1.hovered().markers().enabled(true);
        // series1.hovered().markers().type(MarkerType.CIRCLE).size(4d);
        // series1.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);
        //
        // cartesian.legend().enabled(true);
        // cartesian.legend().fontSize(13d);
        // cartesian.legend().padding(0d, 0d, 10d, 0d);

    }

    @Override
    public void initView() {
        // binding.acvChartFragment.setProgressBar(binding.pbProgressChartFragment);
        // binding.acvChartFragment.setChart(cartesian);

        binding.lcChartFragment.setTouchEnabled(true);
        binding.lcChartFragment.setDrawGridBackground(false);
        binding.lcChartFragment.setDragEnabled(true);
        binding.lcChartFragment.setScaleEnabled(true);
        binding.lcChartFragment.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite_FFFFFF));
        // binding.lcChartFragment.setScaleXEnabled(true);
        // binding.lcChartFragment.setScaleYEnabled(true);

        // force pinch zoom along both axis
        binding.lcChartFragment.setPinchZoom(true);

        XAxis xAxis;
        { // // X-Axis Style // //
            xAxis = binding.lcChartFragment.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        { // // Y-Axis Style // //
            yAxis = binding.lcChartFragment.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            binding.lcChartFragment.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(10f);
            yAxis.setAxisMinimum(0f);
        }

        { // // Create Limit Lines // //
//            LimitLine llXAxis = new LimitLine(9f, "Index 10");
//            llXAxis.setLineWidth(4f);
//            llXAxis.enableDashedLine(10f, 10f, 0f);
//            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            llXAxis.setTextSize(10f);
//
//            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//            ll1.setLineWidth(4f);
//            ll1.enableDashedLine(10f, 10f, 0f);
//            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//            ll1.setTextSize(10f);
//
//            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//            ll2.setLineWidth(4f);
//            ll2.enableDashedLine(10f, 10f, 0f);
//            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            ll2.setTextSize(10f);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
//            yAxis.addLimitLine(ll1);
//            yAxis.addLimitLine(ll2);
            // xAxis.addLimitLine(llXAxis);
        }

    }

    @Override
    public void doSomething() {
        addDataSet("test", 1);
        addDataSet("test2", 1);
        addDataSet("test", 2);
        addDataSet("test", 1);
        addDataSet("test2", 2);
        addDataSet("test", 2);
        addDataSet("test", 1);
        addDataSet("test2", 2);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private LineDataSet createDataSet(String name) {
        LineDataSet set = new LineDataSet(null, name);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

    private void addDataSet(String name, Number value) {
        LineData data = binding.lcChartFragment.getData();
        if (data == null) {
            data = new LineData();
            binding.lcChartFragment.setData(data);
        }
        ILineDataSet set = data.getDataSetByLabel(name, false);

        if (set == null) {
            set = createDataSet(name);
            data.addDataSet(set);
        }

//        data.addEntry(new Entry(set.getEntryCount(), value.floatValue()), data.getDataSetCount());
        set.addEntry(new Entry(set.getEntryCount(), value.floatValue()));
        data.notifyDataChanged();
        binding.lcChartFragment.notifyDataSetChanged();
        binding.lcChartFragment.setVisibleXRangeMaximum(6);

        binding.lcChartFragment.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }

}
