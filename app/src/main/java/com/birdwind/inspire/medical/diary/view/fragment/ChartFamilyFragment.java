package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.DateTimeFormatUtils;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentChartBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.ChartResponse;
import com.birdwind.inspire.medical.diary.presenter.ChartPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.ScoreDetailDialog;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChartView;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ChartFamilyFragment extends AbstractFragment<ChartPresenter, FragmentChartBinding>
    implements OnChartValueSelectedListener, ChartView {

    private long uid;

    private XAxis chart_xAxis;

    private YAxis chart_yAxis;

    private Description chartDescription;

    private Legend chartLegend;

    private final int maxShowChartX = 4;

    private ScoreDetailDialog scoreDetailDialog;

    @Override
    public ChartPresenter createPresenter() {
        return new ChartPresenter(this);
    }

    @Override
    public FragmentChartBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentChartBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.lcChartFragment.setOnChartValueSelectedListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getLong("UID", App.userModel.getUid());
        } else {
            uid = App.userModel.getUid();
        }
    }

    @Override
    public void initView() {
        initChartX_Style();
        initChartY_Style();
        initChartFormat();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getChartData(uid, true);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        ChartResponse.Response response = (ChartResponse.Response) e.getData();
        scoreDetailDialog = new ScoreDetailDialog(context, (int) response.getTID(),
            DiseaseEnums.parseEnumsByType(response.getDisease()), true);
        scoreDetailDialog.show();
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
        set.setValueTextSize(Utils.dp2px(context, 8));

        return set;
    }

    @Override
    public void onGetChart(boolean isSuccess, List<ChartResponse.Response> responses) {
        List<String> dateList = new ArrayList<>();
        Collections.reverse(responses);
        for (int i = 0; i < responses.size(); i++) {
            ChartResponse.Response response = responses.get(i);
            dateList.add(DateTimeFormatUtils.monthDayFormat(response.getTimeC()));
        }

        initChartX(dateList);

        // TODO:更新字串
        binding.lcChartFragment.clear();
        updateChartData("平均分數", responses);
    }

    private void initChartX_Style() {
        chart_xAxis = binding.lcChartFragment.getXAxis();
        chart_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_xAxis.setTextSize(Utils.dp2px(context, 8));
        chart_xAxis.setTextColor(ContextCompat.getColor(context, R.color.colorGray_4D4D4D));
        chart_xAxis.setSpaceMin(0.5f);// 折線起點距離左側Y軸距離
        chart_xAxis.setSpaceMax(0.5f);// 折線終點距離右側Y軸距離
        chart_xAxis.setDrawGridLines(false);// 不顯示每個座標點對應X軸的線 (預設顯示)
        chart_xAxis.setDrawLabels(true);// 不顯示X軸的對應標籤 (預設顯示)
        // chart_xAxis.setLabelRotationAngle(-25);// X軸數字旋轉角度

        // xAxis.enableGridDashedLine(10f, 10f, 0f);
    }

    private void initChartY_Style() {
        chart_yAxis = binding.lcChartFragment.getAxisLeft();
        binding.lcChartFragment.getAxisRight().setEnabled(false);
        // chart_yAxis.setAxisMaximum(10f);
        chart_yAxis.setAxisMinimum(0f);
        chart_yAxis.setLabelCount(10);// X軸標籤個數
    }

    private void initChartFormat() {
        chartDescription = binding.lcChartFragment.getDescription();
        chartLegend = binding.lcChartFragment.getLegend();

        chartDescription.setEnabled(false);// 不顯示Description Label (預設顯示)
        // chartLegend.setEnabled(false);// 不顯示圖例 (預設顯示)

        // TODO:更新字串
        binding.lcChartFragment.setNoDataText("暫時沒有數據");
        binding.lcChartFragment.setPinchZoom(false); // true->X、Y軸同時按比例縮放、false:X、Y可單獨縮放

        binding.lcChartFragment.setTouchEnabled(true);
        binding.lcChartFragment.setDragEnabled(true);
        binding.lcChartFragment.setScaleEnabled(false);
        binding.lcChartFragment.setHighlightPerDragEnabled(false);
        binding.lcChartFragment.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite_FFFFFF));
    }

    private void initChartX(List<String> xList) {
        chart_xAxis.setLabelCount(Math.min(xList.size(), maxShowChartX));// X軸標籤個數
        chart_xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
    }

    private void updateChartData(String name, List<ChartResponse.Response> chartResponses) {
        for (ChartResponse.Response object : chartResponses) {
            addChartData(name, object.getScore(), object);
        }
    }

    private void addChartData(String name, Number yValue, ChartResponse.Response chartResponse) {
        addChartData(name, null, yValue, chartResponse);
    }

    private void addChartData(String name, @Nullable Number xValue, Number yValue,
        ChartResponse.Response chartResponse) {
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

        set.addEntry(
            new Entry(xValue == null ? set.getEntryCount() : xValue.intValue(), yValue.floatValue(), chartResponse));
        data.notifyDataChanged();
        binding.lcChartFragment.notifyDataSetChanged();
        binding.lcChartFragment.setVisibleXRangeMaximum(maxShowChartX);

        binding.lcChartFragment.moveViewTo(data.getEntryCount() - maxShowChartX + 1, 50f, YAxis.AxisDependency.LEFT);
    }

    @Override
    public boolean isShowTopBar() {
        return App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR;
    }
}
