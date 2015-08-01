/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 合同类型F7
 */
public class ContractTypeF7UI extends AbstractContractTypeF7UI
{


	private static final Logger logger = CoreUIObject.getLogger(ContractTypeF7UI.class);
    
    private boolean isCancel = false;

    /**
     * output class constructor
     */
    public ContractTypeF7UI() throws Exception
    {
        super();
    }   

    protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
    	disposeUIWindow();
    	setCancel(true);
    }
    
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }

	private void confirm() throws Exception {
		checkSelected();
    	getData();
    	setCancel(false);
	}

    public ContractTypeInfo getData() throws Exception {
		String id = getSelectedKeyValue();
    	ContractTypeInfo contractTypeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(id));
    	if(!contractTypeInfo.isIsLeaf()) {
    		MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
    		SysUtil.abort();
    	}
		disposeUIWindow();
		
		return contractTypeInfo;
	}

    public boolean isCancel() {
    	return isCancel;
    }

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getClickCount() == 2) {

            // modify to view when doubleClick row by Jacky 2005-1-7
            if (e.getType() == 0) {
                return;
            }
            
            confirm();
    	}
    }
    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    }
   
    public boolean destroyWindow() {
    	setCancel(true);
    	return super.destroyWindow();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    // 业务系统可重载，执行Query前对EntityViewInfo进行处理。
    protected void beforeExcutQuery(EntityViewInfo ev) {
    	
    	FilterInfo filter = getEnableFilter();
		
		this.getUIContext().put("F7Filter",filter);
		try {
			ev.getFilter().mergeFilter(filter, "and");
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}    	
    }

	private FilterInfo getEnableFilter() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));	
		return filter;
	}
    
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo filter = getEnableFilter();
    	return filter;
    }
    
    
    protected FilterInfo getDefaultFilterForTree()
    {
    	FilterInfo filter = super.getDefaultFilterForTree();
    	FilterInfo enableFilter = getEnableFilter();
    	if(filter == null || filter.getFilterItems().isEmpty()) return enableFilter;
    	try {
    		filter.mergeFilter(enableFilter, "and");
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
        return filter;
    }
    
    protected boolean isIgnoreTreeCUFilter() {
    	
    	return true;
    }
    
}