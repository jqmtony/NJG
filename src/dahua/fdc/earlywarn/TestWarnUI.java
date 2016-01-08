/**
 * output package name
 */
package com.kingdee.eas.fdc.earlywarn;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class TestWarnUI extends AbstractTestWarnUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestWarnUI.class);
    
    /**
     * output class constructor
     */
    public TestWarnUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	kDComboBox1.addItems(new String[]{"合约规划预警","进度项预警","结算申报单预警","合约跟踪单预警","动态成本差异率预警"});
    }

    /**
     * output btnOk_actionPerformed method
     */
    protected void btnOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception{
    	String typeName = kDComboBox1.getSelectedItem().toString();
    	if(FDCMsgBox.showConfirm2(this, "当前操作耗时会比较长，确认执行["+kDComboBox1.getSelectedItem()+"] ？")!= FDCMsgBox.YES)
    		return;
    	IDHWarnMsgFacade IDHWarnMsgFacade = DHWarnMsgFacadeFactory.getRemoteInstance();
    	if(typeName.equals("合约规划预警"))
    		IDHWarnMsgFacade.programmingWarnMsg();
    	if(typeName.equals("进度项预警"))
    		IDHWarnMsgFacade.dhScheduleWarnMsg();
    	if(typeName.equals("结算申报单预警"))
    		IDHWarnMsgFacade.settleDeclarationWarnMsg();
    	if(typeName.equals("合约跟踪单预警"))
    		IDHWarnMsgFacade.programmingGZWarnMsg("P24AAABizi7wSYYL",5);
    	if(typeName.equals("动态成本差异率预警"))
    		IDHWarnMsgFacade.aimCostDiffWarnMsg("");
    	FDCMsgBox.showInfo("执行成功，请查看消息中心预警信息！");
    }


}