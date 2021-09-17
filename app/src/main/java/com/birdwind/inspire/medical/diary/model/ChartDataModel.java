package com.birdwind.inspire.medical.diary.model;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import java.util.List;

public class ChartDataModel extends ValueDataEntry {

    public ChartDataModel(String x, Number value) {
        super(x, value);
    }

    public ChartDataModel(Number x, Number value) {
        super(x, value);
    }

    public ChartDataModel(String x, List<Number> numberList) {
        super(x, numberList.get(0));

        for (int i = 1; i < numberList.size(); i++) {
            setValue("value" + i, numberList.get(i));
        }
    }
    public ChartDataModel(Number x, List<Number> numberList) {
        super(x, numberList.get(0));

        for (int i = 1; i < numberList.size(); i++) {
            setValue("value" + i, numberList.get(i));
        }
    }
}
