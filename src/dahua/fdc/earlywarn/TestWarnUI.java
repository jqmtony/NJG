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
    	kDComboBox1.addItems(new String[]{"��Լ�滮Ԥ��","������Ԥ��","�����걨��Ԥ��","��Լ���ٵ�Ԥ��","��̬�ɱ�������Ԥ��"});
    }

    /**
     * output btnOk_actionPerformed method
     */
    protected void btnOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception{
    	String typeName = kDComboBox1.getSelectedItem().toString();
    	if(FDCMsgBox.showConfirm2(this, "��ǰ������ʱ��Ƚϳ���ȷ��ִ��["+kDComboBox1.getSelectedItem()+"] ��")!= FDCMsgBox.YES)
    		return;
    	IDHWarnMsgFacade IDHWarnMsgFacade = DHWarnMsgFacadeFactory.getRemoteInstance();
    	if(typeName.equals("��Լ�滮Ԥ��"))
    		IDHWarnMsgFacade.programmingWarnMsg();
    	if(typeName.equals("������Ԥ��"))
    		IDHWarnMsgFacade.dhScheduleWarnMsg();
    	if(typeName.equals("�����걨��Ԥ��"))
    		IDHWarnMsgFacade.settleDeclarationWarnMsg();
    	if(typeName.equals("��Լ���ٵ�Ԥ��"))
    		IDHWarnMsgFacade.programmingGZWarnMsg("P24AAABizi7wSYYL",5);
    	if(typeName.equals("��̬�ɱ�������Ԥ��"))
    		IDHWarnMsgFacade.aimCostDiffWarnMsg("");
    	FDCMsgBox.showInfo("ִ�гɹ�����鿴��Ϣ����Ԥ����Ϣ��");
    }


}