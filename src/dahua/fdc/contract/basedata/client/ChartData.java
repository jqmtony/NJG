package com.kingdee.eas.fdc.basedata.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.chart.data.CommonChartData;
import com.kingdee.bos.ctrl.swing.chart.util.ChartDataUtil;
import com.kingdee.eas.util.SysUtil;

public class ChartData {
	public ChartData() {
	}

	public ChartData(String[] keys) {
		this.setSeriesKeys(keys);
	}

	private ChartType chartType = ChartType.CT_MULTIPIE;

	private String title;

	private String[] seriesKeys;

	private List groupsKeys = new ArrayList();

	private List values = new ArrayList();

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	public void setSeriesKeys(String[] keys) {

		this.seriesKeys = keys;
	}

	public void addGroupData(String groupKey, Object[] data) {
		if (data.length > seriesKeys.length) {
			SysUtil.abort(new Exception("数据列超出seriesKeys的个数"));
		}
		groupsKeys.add(groupKey);
		values.add(data);
	}

	public CommonChartData getData() {
		double[][] data = new double[this.seriesKeys.length][groupsKeys.size()];
		for (int i = 0; i < this.groupsKeys.size(); i++) {
			Object[] row = (Object[]) values.get(i);
			for (int j = 0; j < row.length; j++) {
				Object value = row[j];
				double dValue = 0;
				if (value != null) {
					if (value instanceof BigDecimal) {
						dValue = ((BigDecimal) value).doubleValue();
					} else if (value instanceof Integer) {
						dValue = ((Integer) value).doubleValue();
					} else if (value instanceof Double) {
						dValue = ((Double) value).doubleValue();
					}
				}
				data[j][i] = dValue;
			}
		}
		CommonChartData commonChartData = ChartDataUtil.createCommonChartData(
				seriesKeys, (String[]) groupsKeys.toArray(new String[] {}),
				data);
		// CommonChartData commonChartData =
		// ChartDataUtil.createCommonChartData(
		// new String[] { "结算金额", "未结算金额" }, new String[] { "1dong",
		// "2dong", "3dong" }, new double[][] { {1,1,3},{1,3,4} });
		return commonChartData;
	}

	public String[] getSeriesKeys() {
		return seriesKeys;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
