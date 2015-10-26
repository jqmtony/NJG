/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexFactory;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BuildPriceIndexListUI extends AbstractBuildPriceIndexListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildPriceIndexListUI.class);
    //获取有权限的组织
	protected Set authorizedOrgs = null;
	protected FullOrgUnitInfo orgUnit = null;
    
    /**
     * output class constructor
     */
    public BuildPriceIndexListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
		initTable();
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(false);
//		queryContractBillQuery.setFilter(getTreeSelectFilter());
    }
    
    public void initTable(){
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	kdtCost.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//    	freezeMainTableColumn();
//    	kdtContract.setEditable(false);
//		kdtContract.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)ContractBillFactory.getRemoteInstance()).fetchInitData(param);
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
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
    }


    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex < 0){
    		MsgBox.showInfo("请选择相应的合同！");
    		return;
    	}
    	String contractId = (String)tblMain.getCell(rowIndex,getKeyFieldName()).getValue();
//    	IContractBill icbill = (IContractBill)getRemoteInterface();
//    	icbill.getContractBillCollection("select number,name");
//    	ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractId));
    	getUIContext().put("contractInfo",ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractId)));
//    	getUIContext().put("contractNumber",tblMain.getCell(rowIndex,"number").getValue());
//    	getUIContext().put("contractName",tblMain.getCell(rowIndex,"contractName").getValue());
    	super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.BuildPriceIndexFactory.getRemoteInstance();
    }
    
    protected boolean displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
    	if(view==null){
			return false ;
		}
    	BuildPriceIndexCollection bicoll=BuildPriceIndexFactory.getRemoteInstance().getBuildPriceIndexCollection(view);
    	IRow row = null;
    	BuildPriceIndexInfo bpinfo = null;
    	for (int i = 0; i < bicoll.size(); i++) {
    		bpinfo = bicoll.get(i);
    		row = kdtCost.addRow();
    		row.getCell("id").setValue(bpinfo.getId().toString());
			row.getCell("number").setValue(bpinfo.getNumber());
			row.getCell("bizDate").setValue(bpinfo.getBizDate());
			if(bpinfo.getCreator()!=null)
				row.getCell("creator.name").setValue(bpinfo.getCreator().getName());
			row.getCell("createTime").setValue(bpinfo.getCreateTime());
			if(bpinfo.getLastUpdateUser() != null)
				row.getCell("lastUpdateUser.name").setValue(bpinfo.getLastUpdateUser().getName());
			row.getCell("lastUpdateTime").setValue(bpinfo.getLastUpdateTime());
			row.getCell("contractInfo").setValue(bpinfo.getContractInfo());
			row.getCell("contractStation").setValue(bpinfo.getContractStation());
			row.getCell("orgName").setValue(bpinfo.getOrgName());
			row.getCell("projectStation").setValue(bpinfo.getProjectStation());
			row.getCell("buildPriceBillStatus").setValue(bpinfo.getBuildPriceBillStatus());
			row.getCell("beizhu").setValue(bpinfo.getBeizhu());
		}
    	return true;
    }

    protected EntityViewInfo genBillQueryView(KDTSelectEvent e) {
    	KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	String contractId = (String)getMainTable().getCell(top,getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
    	view.setFilter(filter);
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
			}
    	}
		return view;
    }
    
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo objectValue = new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo();
		
        return objectValue;
    }

	@Override
	protected void audit(List ids) throws Exception {
	}

	@Override
	protected SelectorItemCollection genBillQuerySelector() {
		 SelectorItemCollection sic = new SelectorItemCollection();
		 sic.add(new SelectorItemInfo("creator.id"));
		 sic.add(new SelectorItemInfo("creator.number"));
		 sic.add(new SelectorItemInfo("creator.name"));
		 sic.add(new SelectorItemInfo("createTime"));
		 sic.add(new SelectorItemInfo("lastUpdateUser.id"));
		 sic.add(new SelectorItemInfo("lastUpdateUser.number"));
		 sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		 sic.add(new SelectorItemInfo("lastUpdateTime"));
		 sic.add(new SelectorItemInfo("number"));
		 sic.add(new SelectorItemInfo("bizDate"));
		 sic.add(new SelectorItemInfo("auditor.id"));
		 sic.add(new SelectorItemInfo("auditor.number"));
		 sic.add(new SelectorItemInfo("auditor.name"));
		 sic.add(new SelectorItemInfo("orgName"));
		 sic.add(new SelectorItemInfo("contractInfo"));
		 sic.add(new SelectorItemInfo("beizhu"));
		 sic.add(new SelectorItemInfo("buildPriceBillStatus"));
		 sic.add(new SelectorItemInfo("projectStation"));
		 sic.add(new SelectorItemInfo("contractStation"));
	     return sic;
	}

	@Override
	protected KDTable getBillListTable() {
		return kdtCost;
	}

	@Override
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return BuildPriceIndexFactory.getRemoteInstance();
	}
	
	@Override
	protected String getBillStatePropertyName() {
		return "buildPriceBillStatus";
	}
	
	@Override
	protected void unAudit(List ids) throws Exception {
		
	}
	
	protected boolean isFootVisible() {
		return false;
	}

}