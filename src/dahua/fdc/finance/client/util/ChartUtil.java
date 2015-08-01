package com.kingdee.eas.fdc.finance.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;

import com.kingdee.bos.ctrl.swing.KDChart;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.chart.data.CommonChartData;
import com.kingdee.bos.ctrl.swing.chart.util.ChartDataUtil;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.finance.client.FinaceMapUI;
import com.kingdee.eas.framework.report.util.ChartData;
import com.kingdee.eas.framework.report.util.ChartTypeEnum;

public class ChartUtil {
	private static ChartType[] supports = {
		ChartType.CT_PIE, // ��ͼ
        ChartType.CT_PIEEXPLODED, // �����ͱ�ͼ
        ChartType.CT_PIE3D, // ��ά��ͼ
        ChartType.CT_COLUMNCLUSTERED, // ��״����ͼ
        ChartType.CT_COLUMNSTACKED, // �ѻ�����ͼ
        ChartType.CT_COLUMNCLUSTERED3D, // ��ά��״����ͼ
        ChartType.CT_COLUMNSTACKED3D, // ��ά�ѻ�����ͼ
        ChartType.CT_BARCLUSTERED, // ��״����ͼ
        ChartType.CT_BARSTACKED, // �ѻ�����ͼ
        ChartType.CT_BARCLUSTERED3D, // ��ά��״����ͼ
        ChartType.CT_BARSTACKED3D, // ��ά�ѻ�����ͼ
        ChartType.CT_LINE, // ����ͼ
        ChartType.CT_LINESTACKED, // �ѻ�����ͼ
        ChartType.CT_LINEMARKERS, // ���ݵ�����ͼ
        ChartType.CT_LINEMARKERSSTACKED, // �ѻ����ݵ�����ͼ
        ChartType.CT_XYSCATTER, // ɢ��ͼ
        ChartType.CT_XYSCATTERLINES, // ����ɢ��ͼ
        ChartType.CT_XYSCATTERLINESNOMARKERS, // �����ݵ�����ɢ��ͼ
        ChartType.CT_AREA, // ���ͼ
        ChartType.CT_AREASTACKED, // �ѻ����ͼ
        ChartType.CT_GANTT, // ����ͼ
        ChartType.CT_COMBINED_CATEGORYPLOT,
        ChartType.CT_COMBINED_XYPLOT
};
	

	/**
	 * 
	 * @param map����Ϊ��
	 * @param title��ͼƬ�ı���
	 * @param windTitle:���ڱ���
	 * @param seriesKeys:����
	 * @param groupNames:����
	 * @param values:ֵ
	 * @param type:����
	 * @throws UIException
	 */
	public static void openMapUI(Map map,String title,String windTitle, String[] seriesKeys, String[] groupNames, double[][] values,int type) throws UIException{
		if(map==null){
			map=new HashMap();
		}
		map.put("title", title);
		map.put("seriesKeys", seriesKeys);
		map.put("groupNames", groupNames);
		map.put("type", new Integer(type));
		map.put("values", values);
		map.put("windTitle", windTitle);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(FinaceMapUI.class.getName(), map, null,OprtState.VIEW);
		uiWindow.show();
	}
	
//    /**
//     * ������������״ͼ��
//     * @param panel
//     * @param value
//     */
//    public static void buildColumnChart(KDPanel panel,String title,String[] seriesKeys,String[] cloumnChartGroup,
//			double[][] cloumnChartValue){
//    	KDChart chart = buildColumnChart(title,seriesKeys,cloumnChartGroup,cloumnChartValue);
//    	chart.setBounds(new Rectangle(0,0,panel.getWidth(),panel.getHeight()));
//    	panel.add(chart);
//    }
    
    
    public static KDChart buildChart(String title,String[] seriesKeys,String[] groupName,double[][] value,int type){
		KDChart chart = new KDChart();
		ChartData data = new ChartData();
		data.setType(type);
		data.setTitle(title);
		data.setSeriesKeys(seriesKeys);
		data.setGroupsKeys(groupName);
		data.setValues(value);
		drawChart(chart, data);
		return chart;
    }
    
//    /**
//     * ������������״ͼ
//     * @param panel
//     * @param title
//     * @param groupName
//     * @param value = new double[serialsize][groupsize];
//     * @return
//     */
//    public static KDChart buildColumnChart(String title,String[] seriesKeys,String[] groupName,double[][] value){
//		KDChart chart = new KDChart();
//		ChartData data = new ChartData();
//		data.setType(5);
//		data.setTitle(title);
//		data.setSeriesKeys(seriesKeys);
//		data.setGroupsKeys(groupName);
//		data.setValues(value);
//		drawChart(chart, data);
//		return chart;
//    }
//    
//    public static KDChart buildLineChart(String title,String[] seriesKeys,String[] groupName,double[][] value){
//		KDChart chart = new KDChart();
//		ChartData data = new ChartData();
//		data.setType(14);
//		data.setTitle(title);
//		data.setSeriesKeys(seriesKeys);
//		data.setGroupsKeys(groupName);
//		data.setValues(value);
//		drawChart(chart, data);
//		return chart;
//    }
    
    /**
     * 
     * ��������ʼ��ͨ������ͼ��
     */
    private static void initCommChart(KDChart chart, ChartData data) {
        chart.setChartType(supports[data.getType()]);
        chart.getChartTitle().setText(data.getTitle());
        chart.getAxes(0).getCategotyAxis().getTitle().setText(data.getCategoryTitle());
        
        chart.getAxes(0).getValueAxis().getTitle().setText(data.getValueTitle());
        CommonChartData commonChartData = ChartDataUtil.createCommonChartData(data.getSeriesKeys(), data.getGroupsKeys(),
                data.getValues());
        chart.addChartData(commonChartData);
        //this.add(chart, BorderLayout.CENTER);
        
    }
    
    /**
     * ����������������͵�ͼ��
     */
    
    private static void initCombindChart(KDChart chart, ChartData data) {
        chart.setChartType(supports[data.getType()]);
        chart.getChartTitle().setText(data.getTitle());
        chart.getAxes(0).getCategotyAxis().getTitle().setText(data.getCategoryTitle());
        
        chart.getAxes(0).getValueAxis().getTitle().setText(data.getValueTitle());
        for(int i=0; i<data.getCount(); i++) {
            CommonChartData commonChartData = ChartDataUtil.createCommonChartData(data.getSeriesKeys(), data.getGroupsKeys(),
                data.getCombinedValues()[i]);
            chart.addChartData(commonChartData);
        }
        if(data.getCombinedTitles() != null) {
            String titles[] = data.getCombinedTitles();
            CombinedDomainCategoryPlot plot = (CombinedDomainCategoryPlot) chart.getDelegate().getPlot();
            List subs = plot.getSubplots();
            for (int i = 0; i < subs.size() && i<titles.length; i++){
                ((CategoryPlot) subs.get(i)).getRangeAxis().setLabel(titles[i]);
            }

        }
        //this.add(chart, BorderLayout.CENTER);
        
    }  
    
    private static void drawChart(KDChart chart, ChartData data) {
        if(data.getType()==ChartTypeEnum.CT_COMBINED_XYPLOT || data.getType()==ChartTypeEnum.CT_COMBINED_CATEGORYPLOT){
            initCombindChart(chart, data);
        }else {
            initCommChart(chart, data);
        }
    }
}
