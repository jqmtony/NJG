/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述：  <p>
 * @author 刘一珉
 * @version EAS 7.0
 * @see 
 */
public class JfreeChartLineMap  extends JPanel {

	/**缺省版本标识*/
	private static final long serialVersionUID = 1L;

	public static JPanel getLineChartMap(String title,XYDataset dataSet,String titleName,String yName) {
		
		JPanel jPanel = createPanel(dataSet,titleName,yName);
		
		return jPanel;
	}

	
	 private static JFreeChart createChart(XYDataset xydataset,String titleName,String yName)
	 {
	     /*创建jfreeChart图表*/   
		 JFreeChart jfreechart = 
	        	ChartFactory.createTimeSeriesChart(titleName, "时间", yName, xydataset, true, true, false);
	    
		/*设置plot显示*/
		XYPlot xyplot = (XYPlot)jfreechart.getPlot(); 
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        if(xyitemrenderer instanceof XYLineAndShapeRenderer){
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
            /*数据点可见*/
            xylineandshaperenderer.setShapesVisible(true); 
            /*数据点是实心点*/
            xylineandshaperenderer.setShapesVisible(true); 
        }
        /*对domain 轴上日期显示格式定义*/
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