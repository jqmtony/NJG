/**
\ * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDComboColor;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.BackdropColor;
import com.kingdee.eas.fdc.basedata.BackdropColorCollection;
import com.kingdee.eas.fdc.basedata.BackdropColorFactory;
import com.kingdee.eas.fdc.basedata.BackdropColorInfo;
import com.kingdee.eas.fdc.basedata.IBackdropColor;
import com.kingdee.eas.fdc.sellhouse.BaseRoomSetting;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * output class name
 */
public class BackdropColorListUI extends AbstractBackdropColorListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BackdropColorListUI.class);
    private BackdropColor back=new BackdropColor();
    
    private BaseRoomSetting baseRoomSetting = new BaseRoomSetting();
    
    public BaseRoomSetting getBaseRoomSetting() {
		return baseRoomSetting;
	}

	public void setBaseRoomSetting(BaseRoomSetting baseRoomSetting) {
		this.baseRoomSetting = baseRoomSetting;
	}
	
	private Color convertToColor(BackdropColorInfo info)
	{
		try {
			byte[] b = info.getColor();
			ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0, b.length));
			Color settingColor = (Color)objectInputStream.readObject();
			return settingColor;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		} 
		return null;
	}
	
	public void onLoad() throws Exception
    {
    	super.onLoad();
    	
    	Map colorSetting = BytesToObject();
    	Component[] compList = kDContainer1.getComponents();

    	int maxLevel = getMaxLevel();
    	
    	if(colorSetting != null  && !colorSetting.isEmpty()){
        	
        	Iterator iter = colorSetting.entrySet().iterator();
        	int i=1;
        	while(iter.hasNext()){
        		if(maxLevel <i){
        			break;
        		}

        		Entry entry = (Entry)iter.next();
        		
        		Integer level =  (Integer) entry.getKey();
        		Color currentColor = (Color) convertToColor((BackdropColorInfo)entry.getValue());
        		
        		KDComboColor colorComp = (KDComboColor)keyCompMap.get(level);
        		
        		if(currentColor == null){
    				colorComp.setColor(Color.WHITE);
    			}else{
    				colorComp.setColor(currentColor);
    				
    			}
        		i++;
        	}
        	
    	}else{
    		
    		for(int i=0;i<compList.length;i++){
    			Component comp = compList[i];
    			if(comp instanceof KDComboColor ){
    				KDComboColor colorComp = (KDComboColor)comp ;
    				colorComp.setColor(Color.WHITE);
    			}
    		}
    	}
    	
    }
	Map keyCompMap = new HashMap();
	
    /**
     * output class constructor
     */
    public BackdropColorListUI() throws Exception
    {
        super();
        
        if(!keyCompMap.isEmpty()){
        	keyCompMap.clear();
        }
        
        int maxLevel = getMaxLevel();
        for(int i=1;i<=maxLevel;i++){
        	KDComboColor colorCtl = new com.kingdee.bos.ctrl.swing.KDComboColor();
        	colorCtl.setName("color"+i);
        	colorCtl.setUserObject(new Integer(i));
        	colorCtl.setVisible(true);
        	colorCtl.setColor(Color.WHITE);
        	String name=""+i+"级科目颜色";
        	KDLabel label = new KDLabel();
        	label.setText(name);
        	colorCtl.setBounds(new Rectangle(150,i*30,200, 20));
        	label.setBounds(new Rectangle(30,i*30,200,20));
        	keyCompMap.put(new Integer(i), colorCtl);
        	kDContainer1.add(colorCtl, null);	
        	kDContainer1.add(label, null);
        	
        	keyCompMap.put(new Integer(i), colorCtl);
        }
        
        this.repaint();
    }

	private int getMaxLevel() throws BOSException, SQLException {
		String sql = "select max(FLevel) maxLevel  from T_FDC_CostAccount" ;
        ISQLExecutor exe = SQLExecutorFactory.getRemoteInstance(sql);
        IRowSet rowSet = exe.executeSQL();
        
        int maxLevel = 0 ;
        rowSet.beforeFirst();
        if(rowSet.next())
        {
        	maxLevel =rowSet.getInt("maxLevel") ;///从数据库取值;
            
        }
		return maxLevel;
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 抽象出一个方法
     * @param n
     * @param name
     * @return
     */
    private BackdropColorInfo backdropColor(int n,Object name)
    {
    	try {
			BackdropColorInfo colorSet= new BackdropColorInfo();
			colorSet.setLevel(n);
			colorSet.setColor(getBytesFromObject(name));
			return colorSet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
		return null;
    }
    /**
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	super.btnYes_actionPerformed(e);
    	
    	CoreBaseCollection cbc = new CoreBaseCollection();	

    	IBackdropColor service = BackdropColorFactory.getRemoteInstance();
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",null,CompareType.NOTEQUALS));
    	
    	service.delete(filter);
    	
    	Component[] compList = kDContainer1.getComponents();
    	
    	Map colorMap = new HashMap();
    	
    	for(int i=0;i<compList.length;i++){
    		Component comp = compList[i];
    		if(comp instanceof KDComboColor ){
    			KDComboColor colorComp = (KDComboColor)comp ;
    			Integer level = (Integer) colorComp.getUserObject();
    			colorMap.put(level,colorComp.getColor());
    		}
    	}
    	Iterator iter = colorMap.entrySet().iterator();
    	
    	while(iter.hasNext()){
    		
    		Entry entry =  (Entry)iter.next();
    		entry.getValue();
   		    cbc.add(backdropColor(((Integer)entry.getKey()).intValue(),(Color)entry.getValue()));
    	}
    	service.saveBatchData(cbc);
    	MsgBox.showInfo(this, "保存成功！");
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.actionExitCurrent_actionPerformed(e);
    }

    Map colorSetting = new HashMap();
    
    private Map BytesToObject(){
    	Map levelMap = new java.util.TreeMap();
    	try {
    		IBackdropColor iface = null;
			
    		iface = BackdropColorFactory.getRemoteInstance();
			
    		BackdropColorCollection colorSetCollection = iface.getBackdropColorCollection("select level,color");
			for(int i=0;i<colorSetCollection.size();i++) {
				BackdropColorInfo setInfo = colorSetCollection.get(i);
				int level = setInfo.getLevel();
				levelMap.put(new Integer(level), setInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
		return levelMap ;
    }
    
    
    private byte[] getBytesFromObject(Object obj) throws Exception {
		ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream oOutputStream = new ObjectOutputStream(bOutputStream);
		oOutputStream.writeObject(obj);  
		return bOutputStream.toByteArray();		
	}
}