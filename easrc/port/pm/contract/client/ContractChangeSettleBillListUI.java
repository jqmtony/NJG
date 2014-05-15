/**
 * output package name
 */
package com.kingdee.eas.port.pm.contract.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.IContractChangeSettleBill;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.common.PortProjectTreeBuilder;

/**
 * output class name
 */
public class ContractChangeSettleBillListUI extends AbstractContractChangeSettleBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChangeSettleBillListUI.class);
    
    /**
     * output class constructor
     */
    public ContractChangeSettleBillListUI() throws Exception
    {
        super();
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return ContractChangeSettleBillFactory.getRemoteInstance();
    }

    protected String getEditUIName() {
    	return ContractChangeSettleBillEditUI.class.getName();
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWWIN;
    }
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception {
    	super.actionView_actionPerformed(e);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	uiContext.put("parent", this);
    }
    public void doRefresh() throws Exception{
    	refresh(null);
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	buildProjectTree();
    	projectTree.setSelectionRow(0);
    	
    	this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    }
    
  //获取有权限的组织
	protected Set authorizedOrgs = null;
    public void buildProjectTree() throws Exception {

    	PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();

		projectTreeBuilder.build(this, projectTree, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		
	}
    
    protected void projectTree_valueChanged(TreeSelectionEvent e)
    		throws Exception {
    	treeSelectChange();
    }
    
    protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		mainQuery.setFilter(getTreeSelectFilter(project));

		execQuery();

//		tblMain.removeRows();
		tblMain.getSelectManager().select(0, 0);
	}
    
    public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) projectTree
				.getLastSelectedPathComponent();
	}
    
    /**
	 * 选择工程项目节点后的选择事件
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
//				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				filter.setMaskString("#0 and #1 ");
				
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			} else {
				filterItems.add(new FilterItemInfo("id", "nullnull"));
			}

		}
		
		return filter;
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsInWorkflow();
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}
	
	
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
	    List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
	    if(idList.size()<1)return;
	    ContractChangeSettleBillInfo  info =  ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(idList.get(0).toString()));
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(states[i].equals(info.getState().getValue())) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
    }
    
    /**
     * 
     * 描述：单据可修改、删除的状态
     * @return
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			ContractChangeSettleBillInfo  info =  ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.SUBMITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IContractChangeSettleBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			ContractChangeSettleBillInfo  info =  ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IContractChangeSettleBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
    
}