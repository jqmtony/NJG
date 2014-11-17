/**
 * output package name
 */
package com.kingdee.eas.xr.client;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.scm.common.SCMBillException;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.IXRBillBase;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.WorkflowXRHelper;

/**
 * output class name
 */
public class XRBillBaseListUI extends AbstractXRBillBaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(XRBillBaseListUI.class);
    
    /**
     * output class constructor
     */
    public XRBillBaseListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.mainQuery.getSorter().clear();
	    SorterItemCollection siColl = this.mainQuery.getSorter();
	    siColl.add(new SorterItemInfo("createTime"));
	    SorterItemInfo siInfo = siColl.get(0);
	    siInfo.setSortType(SortType.DESCEND);
    }
    public void onShow() throws Exception {
    	super.onShow();
    }
    protected void initWorkButton()
    {
        super.initWorkButton();
        btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
        btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
    }
    protected void checkAudit()
    {
        int size = tblMain.getSelectManager().size();
        for(int j = 0; j < size; j++)
        {
            KDTSelectBlock selectBlock = tblMain.getSelectManager().get(j);
            if(selectBlock == null)
                continue;
            int i = selectBlock.getTop();
            for(int num = selectBlock.getEndRow(); i <= num && i >= 0; i++)
            {
                IRow row = tblMain.getRow(i);
                String baseStatus = row.getCell("baseStatus").getValue().toString();
                if(!baseStatus.trim().equalsIgnoreCase(XRBillStatusEnum.SUBMITED.toString()) && !baseStatus.trim().equalsIgnoreCase(XRBillStatusEnum.ALTERING.toString()))
                {
                    MsgBox.showInfo(EASResource.getString("com.kingdee.eas.scm.common.SDSMResource", "check_audit"));
                    SysUtil.abort();
                }
            }

        }

    }
	protected FilterInfo getDefaultFilterForQuery() {
		String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if("00000000-0000-0000-0000-000000000000CCE7AED4".equals(cuid)||"6vYAAAAAAQvM567U".equals(cuid))
			return null;
		else
			return super.getDefaultFilterForQuery();
	}
    protected void checkBillBaseStatusCanUnAudit()
    {
        int size = tblMain.getSelectManager().size();
        for(int j = 0; j < size; j++)
        {
            KDTSelectBlock selectBlock = tblMain.getSelectManager().get(j);
            if(selectBlock == null)
                continue;
            int i = selectBlock.getTop();
            for(int num = selectBlock.getEndRow(); i <= num && i >= 0; i++)
            {
                IRow row = tblMain.getRow(i);
                String baseStatus = row.getCell("baseStatus").getValue().toString();
                if(!baseStatus.trim().equalsIgnoreCase(XRBillStatusEnum.AUDITED.toString()))
                {
                    MsgBox.showInfo(EASResource.getString("com.kingdee.eas.scm.common.SDSMResource", "check_unaudit"));
                    SysUtil.abort();
                }
            }

        }

    }

    public void actionAudit_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        checkSelected();
        String billID = getSelectedKeyValue();
        if(billID == null)
            return;
        ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
        if(WorkflowXRHelper.checkInProInst(pk.toString())){
			MsgBox.showInfo("此单据记录有流程正在运行!");
			SysUtil.abort();
		}
        if(!getBizInterface().exists(pk))
        {
            refreshList();
            throw new EASBizException(EASBizException.CHECKEXIST);
        }
        SelectorItemCollection sc = new SelectorItemCollection();
        Object o = getBizInterface().getValue(pk, sc);
        XRBillBaseInfo aXRBillBaseInfo = (XRBillBaseInfo)o;
        if(aXRBillBaseInfo.getStatus() != XRBillStatusEnum.SUBMITED)
        {
            String msg = EASResource.getString("com.kingdee.eas.scm.common.SCMResource.NotAudit");
            MsgBox.showInfo(this, msg);
            return;
        }
        ((IXRBillBase)getBizInterface()).audit(pk);
        refresh(e);
    }
    public void actionUnAudit_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        checkSelected();
        if(!confirmUnAduit())
            return;
        String billID = getSelectedKeyValue();
        if(billID == null)
            return;
        ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
        if(WorkflowXRHelper.checkInProInst(pk.toString())){
			MsgBox.showInfo("此单据记录有流程正在运行!");
			SysUtil.abort();
		}
        if(!getBizInterface().exists(pk))
        {
            refreshList();
            throw new EASBizException(EASBizException.CHECKEXIST);
        } else
        {
            ((IXRBillBase)getBizInterface()).unAudit(pk);
            refresh(e);
            return;
        }
    }

    public void auditBill(ObjectUuidPK pk)
        throws Exception
    {
        ((IXRBillBase)getBizInterface()).passAudit(pk, null);
    }

    public void actionEdit_actionPerformed(java.awt.event.ActionEvent e)
    throws Exception
	{
	    checkSelected();
	    checkCanEdit();
        String billID = getSelectedKeyValue();
        if(billID == null)
            return;
        ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(billID));
        if(WorkflowXRHelper.checkInProInst(pk.toString())){
			MsgBox.showInfo("此单据记录有流程正在运行!");
			SysUtil.abort();
		}
	    super.actionEdit_actionPerformed(e);
	}
	
	protected void checkCanEdit()
	    throws Exception
	{
	    String id = getSelectedKeyValue();
	    if(id != null)
	    {
	        IObjectPK pk = new ObjectStringPK(id);
	        XRBillBaseInfo billInfo = ((IXRBillBase)getBizInterface()).getXRBillBaseInfo(pk);
	        checkCanEdit(billInfo);
	    }
	}
	
	protected void checkCanEdit(XRBillBaseInfo billInfo)
	    throws Exception
	{
	    if(getUIContext().get("BillQuery") != null)
	    {
	        if(!billInfo.getStatus().equals(XRBillStatusEnum.AUDITED))
	        {
	            MsgBox.showInfo(this, SCMClientUtils.getResource("BillAt") + billInfo.getStatus().getAlias() + SCMClientUtils.getResource("CantBeEdited"));
	            SysUtil.abort();
	        }
	        return;
	    }
	    if(billInfo.getStatus().equals(XRBillStatusEnum.AUDITED)){
        	MsgBox.showInfo(this, SCMClientUtils.getResource("BillAt") + billInfo.getStatus().getAlias() + SCMClientUtils.getResource("CantBeEdited"));
	        SysUtil.abort();
        }
//	    if(!billInfo.getStatus().equals(XRBillStatusEnum.TEMPORARILYSAVED) && !billInfo.getStatus().equals(XRBillStatusEnum.SUBMITED) && !billInfo.getStatus().equals(XRBillStatusEnum.ADD))
//	    {
//	        MsgBox.showInfo(this, SCMClientUtils.getResource("BillAt") + billInfo.getStatus().getAlias() + SCMClientUtils.getResource("CantBeEdited"));
//	        SysUtil.abort();
//	    }
	}
    public void actionRemove_actionPerformed(java.awt.event.ActionEvent e)
        throws Exception
    {
        checkSelected();
        if(confirmRemove())
        {
            ArrayList ids = super.getSelectedIdValues();
            if(ids != null)
            {
                boolean hasException = false;
                ICoreBase scmBillBase = getBizInterface();
                int i = 0;
                for(int num = ids.size(); i < num; i++)
                {
                    try
                    {
                        setOprtState("REMOVE");
                        pubFireVOChangeListener(ids.get(i).toString());
                    }
                    catch(Throwable ex)
                    {
                        if(num == 1)
                        {
                            handUIException(ex);
                            abort();
                        }
                        hasException = true;
                        continue;
                    }
                    try
                    {
                    	if(WorkflowXRHelper.checkInProInst(ids.get(i).toString())){
                  			MsgBox.showInfo("此单据记录有流程正在运行!");
                  			SysUtil.abort();
                  		}
                    	scmBillBase.delete(new ObjectUuidPK(BOSUuid.read(ids.get(i).toString())));
                    }
                    catch(Exception onfe)
                    {
                        if(num == 1)
                        {
                            refreshList();
                            throw onfe;
                        }
                        hasException = true;
                    }
                    try
                    {
                        setOprtState("RELEASEALL");
                        pubFireVOChangeListener(ids.get(i).toString());
                    }
                    catch(Throwable t) { }
                }

                if(hasException)
                {
                    refreshList();
                    throw new SCMBillException(SCMBillException.PARTOFDELETEDBILL_CANNOT_BE_DELETE);
                }
            }
            refresh(e);
        }
    }
    
    protected boolean confirmUnAduit()
    {
        return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.scm.common.SCM_COMMONResource", "IsConfirmUnAudit")));
    }
}