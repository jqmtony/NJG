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
 * ����JfreeChart ͼ���ҳ��
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

    /*jfreecharͼ��*/
    private JPanel chart;
    
    /*jfreechar��ʽ����*/
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
     * ������ҳ��
     * @description		
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
	    /*��ȡͼ�����*/
	    getData(data,materialModel);
	    IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create((com.kingdee.eas.fdc.material.client.MaterialLineChartUI.class).getName(), uiContext, null, "View");
	    uiWindow.show();
	    return true;
	}
	
	
	/**
	 * ���JfreeChartͼƬ
	 * @description		
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
		/*���ô�С����ʾ����*/
	    setBounds(new Rectangle(10, 10, 800, 600));
	    setLayout(new KDLayout());
	    this.setUITitle("����ͼ");
	    putClientProperty("OriginalBounds", new Rectangle(10, 10, 780, 580));
	    /*��ȡͼƬ*/
	    chart =  JfreeChartLineMap.getLineChartMap("", dataSet, "", materialModel);
	    /*����ȡ��JfreeChartͼƬ��ӵ���ҳ��*/
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