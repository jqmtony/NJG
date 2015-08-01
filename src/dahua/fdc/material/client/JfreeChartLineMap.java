/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.material.client;


import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.axis.DateAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.plot.XYPlot;
import com.kingdee.bos.ctrl.freechart.chart.renderer.xy.XYItemRenderer;
import com.kingdee.bos.ctrl.freechart.chart.renderer.xy.XYLineAndShapeRenderer;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.bos.ctrl.freechart.ui.RectangleInsets;

/**
 * ������  <p>
 * @author ��һ��
 * @version EAS 7.0
 * @see 
 */
public class JfreeChartLineMap  extends JPanel {

	/**ȱʡ�汾��ʶ*/
	private static final long serialVersionUID = 1L;

	public static JPanel getLineChartMap(String title,XYDataset dataSet,String titleName,String yName) {
		
		JPanel jPanel = createPanel(dataSet,titleName,yName);
		
		return jPanel;
	}

	
	 private static JFreeChart createChart(XYDataset xydataset,String titleName,String yName)
	 {
	     /*����jfreeChartͼ��*/   
		 JFreeChart jfreechart = 
	        	ChartFactory.createTimeSeriesChart(titleName, "ʱ��", yName, xydataset, true, true, false);
	    
		/*����plot��ʾ*/
		XYPlot xyplot = (XYPlot)jfreechart.getPlot(); 
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        if(xyitemrenderer instanceof XYLineAndShapeRenderer){
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
            /*���ݵ�ɼ�*/
            xylineandshaperenderer.setShapesVisible(true); 
            /*���ݵ���ʵ�ĵ�*/
            xylineandshaperenderer.setShapesVisible(true); 
        }
        /*��domain ����������ʾ��ʽ����*/
        DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
        NumberAxis numberAxis = (NumberAxis)xyplot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        return jfreechart;    
	 }

	 public static JPanel createPanel(XYDataset dataSet,String materialName,String materialModel) {
			JFreeChart chart = createChart(dataSet,materialName,materialModel);
			return new ChartPanel(chart);
	}
}