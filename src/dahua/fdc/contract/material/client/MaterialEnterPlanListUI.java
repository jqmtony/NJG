/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;

/**
 * 材料进场计划 序时薄
 */
public class MaterialEnterPlanListUI extends AbstractMaterialEnterPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialEnterPlanListUI.class);
    
    /**
     * output class constructor
     */
    public MaterialEnterPlanListUI() throws Exception
    {
        super();
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		CurProjectInfo curProj = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
		this.getUIContext().put("curProject", curProj);
        super.actionAddNew_actionPerformed(e);
    }

	protected void audit(List ids) throws Exception {
		((IFDCBill)getBizInterface()).audit(ids);
	}

	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}

	protected KDTable getBillListTable() {
		return this.tblMain;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MaterialEnterPlanBillFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MaterialEnterPlanEditUI.class.getName();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return MaterialEnterPlanBillFactory.getRemoteInstance();
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void unAudit(List ids) throws Exception {
		((IFDCBill)getBizInterface()).unAudit(ids);
	}
	protected boolean isHasBillTable() {
    	return false;
    }
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
//		Set set = getContractBillStateSet();
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.STORED_VALUE));
//		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
//		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		
/*		String maskString="(#0 or #1) and #2 and #3";
		filter.setMaskString(maskString);*/
		return filter;
	}
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode,boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();
				} else {
					id = ((FullOrgUnitInfo) projTreeNodeInfo).getId();
				}

				String orgUnitLongNumber = null;
				if (orgUnit != null && id.toString().equals(orgUnit.getId().toString())) {
					orgUnitLongNumber = orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}

				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));

				f.setMaskString("#0 and #1 and #2");

				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractBill.contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}
	public void onLoad() throws Exception {
		super.onLoad();
		kDSplitPane2.add(contContrList, "bottom");
		kDSplitPane2.setDividerLocation(1.0);
		
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {

			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 */
			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
			BOSUuid typeId = null;
			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
				if(getTypeSelectedTreeNode().getUserObject() instanceof ContractTypeInfo){
					typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
				}
			}
			uiContext.put("projectId", projId);
			uiContext.put("contractTypeId", typeId);
		}
	}

	/**
	 * 添加快速定位字段：单据编号，施工合同、施工单位、所属项目、创建时间
	 * @author owen_wen 2010-09-07
	 * 
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "billName","contractBill.name", "partB.name", "curProject.name", "createTime", };
	}
}