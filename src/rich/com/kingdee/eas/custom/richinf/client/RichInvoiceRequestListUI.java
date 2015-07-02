/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.richinf.BillState;
import com.kingdee.eas.custom.richinf.IRichInvoiceRequest;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RichInvoiceRequestListUI extends AbstractRichInvoiceRequestListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichInvoiceRequestListUI.class);
    
    /**
     * output class constructor
     */
    public RichInvoiceRequestListUI() throws Exception
    {
        super();
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	setUITitle("开票申请单序时簿");
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    @Override
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = tblMain.getSelectManager();
    	IRichInvoiceRequest irr = RichInvoiceRequestFactory.getRemoteInstance();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			if(!BillState.AUDIT.equals(irr.getRichInvoiceRequestInfo(new ObjectUuidPK((String)tblMain.getCell(j,"id").getValue())).getBillState())){
    				MsgBox.showInfo("单据不是已审核状态！请先审核！");
    				SysUtil.abort();
    			}
    		}
    	}
    	
    	super.actionCreateTo_actionPerformed(e);
    }
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        String id = getSelectedKeyValue();
        if(id != null){
    		RichInvoiceRequestInfo info = null;
    		info = RichInvoiceRequestFactory.getRemoteInstance().getRichInvoiceRequestInfo(new ObjectUuidPK(id));
    		if(BillState.SAVE.equals(info.getBillState())) {
//    			MsgBox.showInfo("提交状态下的单据才能进行审核操作！");
//        		SysUtil.abort();
    			actionEdit.setEnabled(true);
    			actionRemove.setEnabled(true);
    			actionAudit.setEnabled(false);
    			actionUnAudit.setEnabled(false);
    		}else if(BillState.SUBMIT.equals(info.getBillState())) {
    			actionEdit.setEnabled(true);
    			actionRemove.setEnabled(true);
    			actionAudit.setEnabled(true);
    			actionUnAudit.setEnabled(false);
    		}else if(BillState.AUDIT.equals(info.getBillState())){
    			actionEdit.setEnabled(false);
    			actionRemove.setEnabled(false);
    			actionAudit.setEnabled(false);
    			actionUnAudit.setEnabled(true);
    		}
    		
    	}
        
    }

    @Override
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = tblMain.getSelectManager();
    	IRichInvoiceRequest irr = RichInvoiceRequestFactory.getRemoteInstance();
    	RichInvoiceRequestInfo rrinfo = null;
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			rrinfo = irr.getRichInvoiceRequestInfo(new ObjectUuidPK((String)tblMain.getCell(j,"id").getValue()));
    			if(BillState.SUBMIT.equals(rrinfo.getBillState())){
    				irr.audit(rrinfo);
    			}
    		}
    	}
    	
    	MsgBox.showInfo("审核成功！");
    	actionRefresh_actionPerformed(e);
    }
    IFMIsqlFacade iff = FMIsqlFacadeFactory.getRemoteInstance();
    private String getDestBySrc(String srcid){
    	StringBuffer sb = new StringBuffer();
    	sb.append("select FDestObjectID from T_BOT_Relation where FSrcObjectID=?");
    	try {
    		IRowSet rs = iff.executeQuery(sb.toString(),new Object[]{srcid});
    		if(rs.next()){
    			return rs.getString("FDestObjectID");
    		}
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
    	return null;
    }
    @Override
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = tblMain.getSelectManager();
    	IRichInvoiceRequest irr = RichInvoiceRequestFactory.getRemoteInstance();
    	RichInvoiceRequestInfo rrinfo = null;
    	String reqid = null;
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			reqid = (String)tblMain.getCell(j,"id").getValue();
    			rrinfo = irr.getRichInvoiceRequestInfo(new ObjectUuidPK(reqid));
    			if(getDestBySrc(reqid) != null) {
    				MsgBox.showInfo("第"+(j+1)+"行的开票申请关联应收单，不能反审核！");
    				SysUtil.abort();
    			}
    			if(BillState.AUDIT.equals(rrinfo.getBillState())){
    				irr.unAudit(rrinfo);
    			}
    		}
    	}
    	MsgBox.showInfo("反审核成功！");
    	actionRefresh_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = tblMain.getSelectManager();
    	IRichInvoiceRequest irr = RichInvoiceRequestFactory.getRemoteInstance();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			if(BillState.AUDIT.equals(irr.getRichInvoiceRequestInfo(new ObjectUuidPK((String)tblMain.getCell(j,"id").getValue())).getBillState())){
    				MsgBox.showInfo("已审核的单据不能删除！请重新选择！");
    				SysUtil.abort();
    			}
    		}
    	}
    	
    	super.actionRemove_actionPerformed(e);
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo objectValue = new com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo();
		
        return objectValue;
    }

}