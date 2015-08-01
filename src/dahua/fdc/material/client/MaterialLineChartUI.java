/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;

/**
 * 承载JfreeChart 图标的页面
 * output class name
 */
public class MaterialLineChartUI extends AbstractMaterialLineChartUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialLineChartUI.class);
    
    /**
     * output class constructor
     */
    public MaterialLineChartUI() throws Exception
    {
        super();
    }

    /*jfreechar图表*/
    private JPanel chart;
    
    /*jfreechar格式数据*/
    private static XYDataset dataSet;
    
    private static String materialModel ;

 
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 弹出本页面
     * @description		
     * @author			刘一珉	
     * @createDate		2010-11-30
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see
     */
    public static boolean showChart(IUIObject ui, XYDataset data,String materialModel)
		    throws UIException
			{
	    UIContext uiContext = new UIContext(ui);
	    uiContext.put("data", data);
	    /*获取图表参数*/
	    getData(data,materialModel);
	    IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create((com.kingdee.eas.fdc.material.client.MaterialLineChartUI.class).getName(), uiContext, null, "View");
	    uiWindow.show();
	    return true;
	}
	
	
	/**
	 * 添加JfreeChart图片
	 * @description		
	 * @author			刘一珉		
	 * @createDate		2010-11-30
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see
	 */
	public void initUIContentLayout()
	{
		/*设置大小及显示文字*/
	    setBounds(new Rectangle(10, 10, 800, 600));
	    setLayout(new KDLayout());
	    this.setUITitle("趋势图");
	    putClientProperty("OriginalBounds", new Rectangle(10, 10, 780, 580));
	    /*获取图片*/
	    chart =  JfreeChartLineMap.getLineChartMap("", dataSet, "", materialModel);
	    /*将获取的JfreeChart图片添加到本页面*/
	    add(chart, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(11, 11, 760, 560, 15));
	    menuBar.setVisible(false);
	}
	
	public void onLoad()
	    throws Exception
	{
	}
	
	public void setTitle(String text)
	{
//	    chart.getChartTitle().setText(text);
	}
	
	private static void getData(XYDataset data ,String model){
		
		dataSet = data;
		materialModel = model;
		
	}

}