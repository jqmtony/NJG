/**
 * 
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;

import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartFrame;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.axis.DateAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.DateTickMarkPosition;
import com.kingdee.bos.ctrl.freechart.chart.axis.DateTickUnit;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardXYToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.FastScatterPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.plot.XYPlot;
import com.kingdee.bos.ctrl.freechart.chart.renderer.xy.XYItemRenderer;
import com.kingdee.bos.ctrl.freechart.chart.renderer.xy.XYLineAndShapeRenderer;
import com.kingdee.bos.ctrl.freechart.data.time.Month;
import com.kingdee.bos.ctrl.freechart.data.time.TimeSeries;
import com.kingdee.bos.ctrl.freechart.data.time.TimeSeriesCollection;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.bos.ctrl.freechart.demo.ScatterPlotDemo1;
import com.kingdee.bos.ctrl.freechart.ui.ApplicationFrame;
import com.kingdee.bos.ctrl.freechart.ui.RectangleInsets;
import com.kingdee.bos.ctrl.freechart.ui.RefineryUtilities;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 刘一珉
 * @version EAS1.0
 * @createDate 2010-11-8
 * @see
 */
public class ScatterPlotsMap  extends JPanel{

	public static JPanel getScatterPlotsMap(String title, XYDataset dataSet,String materialName,String materialModel) {
		
		JPanel jPanel = createPanel(dataSet,materialName,materialModel);
		
		return jPanel;
		
	}

	private static JFreeChart createChart(XYDataset xydataset,String materialName,String materialModel) {
		JFreeChart jfreechart = ChartFactory.createScatterPlot(
				materialName, "", materialModel, xydataset,
				PlotOrientation.VERTICAL, true, false, false);

		XYPlot plot = jfreechart.getXYPlot(); 
		plot.setForegroundAlpha(0.5f);                          
		/*设置X轴按照日期显示*/
		XYItemRenderer renderer = plot.getRenderer();                         
		renderer.setToolTipGenerator( 
	         new StandardXYToolTipGenerator(                                                            
                 StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, 
                 new SimpleDateFormat("yyyy-MM-dd"),   
                 new DecimalFormat("0")                                     
	         )                         
		);     
		/*设置显示格式*/
		DateAxis dateAxis = new DateAxis( "时间 ");                          
		dateAxis.setDateFormatOverride(new  SimpleDateFormat( "yyyy-MM-dd"));                          
		dateAxis.setAutoRange(true);                          
		dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);                          
		/*设置Y轴显示整数*/
		plot.setDomainAxis(dateAxis); 
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jfreechart;
	}

	public static JPanel createPanel(XYDataset dataSet,String materialName,String materialModel) {
		JFreeChart chart = createChart(dataSet,materialName,materialModel);
		return new ChartPanel(chart);
	}
}
