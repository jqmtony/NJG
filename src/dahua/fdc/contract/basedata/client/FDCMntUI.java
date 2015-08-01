/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 房地产不规则维护界面UI基类
 * @author liupd
 *
 */
public abstract class FDCMntUI extends AbstractFDCMntUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCMntUI.class);
    
    private final static String CANTAUDIT = "cantAudit";

	private final static String CANTUNAUDIT = "cantUnAudit";

    /**
     * output class constructor
     */
    public FDCMntUI() throws Exception
    {
        super();
    }

    protected abstract KDTable getDetailTable();
    
    
    public void onLoad() throws Exception {
    	
    	super.onLoad();
    	getDetailTable().checkParsed();
    }
    
    private FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) getDataObject();
	}
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		String id = getId();
		if (id != null) {
			getFDCBillInterface().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		getFDCBillInfo().setState(FDCBillStateEnum.AUDITTED);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(true);

	}
    
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		String id = getId();
		if (id != null) {
			getFDCBillInterface().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		getFDCBillInfo().setState(FDCBillStateEnum.SUBMITTED);
		actionUnAudit.setEnabled(false);
		actionAudit.setEnabled(true);
	}
	
	protected String getId() {
		CoreBaseInfo info = (CoreBaseInfo)getDataObject();
		return info.getId() == null ? null : info.getId().toString();
	}
	
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {
		if(getId() == null || !getFDCBillInterface().exists(new ObjectUuidPK(getId()))) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("notSaveCantAudit"));
			SysUtil.abort();
		}

		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getId());
	
		boolean b = getFDCBillInfo() != null
				&& getFDCBillInfo().getState() != null
				&& getFDCBillInfo().getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	/**
	 * 
	 * 描述：获得FDCBill远程接口
	 * @return
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-10-16 <p>
	 */
	protected abstract IFDCBill getFDCBillInterface() throws Exception;
	
	
}