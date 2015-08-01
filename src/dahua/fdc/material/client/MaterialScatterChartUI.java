/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.eas.common.client.UIFactoryName;
import java.awt.Rectangle;

import javax.swing.JPanel;


/**
 * output class name
 */
public class MaterialScatterChartUI extends AbstractMaterialScatterChartUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5270359794697213062L;

	private JPanel chart;
    
    /*ͼ������*/
    private static XYDataset dataSet;
    private static String materialModel;
    /**
     * output class constructor 
     */
    public MaterialScatterChartUI() throws Exception
    {
    }
 
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 
     * @description		����ͼ����
     * @author			��һ��	
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
	    /*��ȡ����*/
	    getData(data,materialModel);
	    /*���ɴ���*/
	    IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create((com.kingdee.eas.fdc.material.client.MaterialScatterChartUI.class).getName(), uiContext, null, "View");
	    uiWindow.show();
	    return true;
	}
	
	
	/**
	 * 
	 * @description		����ͼ��
	 * @author			��һ��		
	 * @createDate		2010-11-30
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see
	 */
	public void initUIContentLayout()
	{
		/*����ͼ����*/
	    setBounds(new Rectangle(10, 10, 800, 600));
	    setLayout(new KDLayout());
	    this.setUITitle("�ֲ�ͼ");
	    putClientProperty("OriginalBounds", new Rectangle(10, 10, 780, 580));
	    /*����ͼ��*/
	    chart =  ScatterPlotsMap.getScatterPlotsMap("", dataSet, "", materialModel);
	    /*���ͼ��������*/
	    add(chart, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(11, 11, 760, 560, 15));
	    menuBar.setVisible(false);
	}
	
	public void onLoad()
	    throws Exception
	{
	}
	
	
	
	private static void getData(XYDataset data,String model){
		
		dataSet = data;
		materialModel = model;
		
	}
}