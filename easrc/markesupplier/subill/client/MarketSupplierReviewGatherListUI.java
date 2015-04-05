/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeFactory;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierState;
import com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketSupplierReviewGatherListUI extends AbstractMarketSupplierReviewGatherListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierReviewGatherListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierReviewGatherListUI() throws Exception
    {
        super();
    }

    protected void buildInviteTypeTree() throws Exception {
    	FilterInfo filter = new FilterInfo();
		TreeModel model = MarketSupplierStockListUI.createDataTree(SupplierInvoiceTypeTreeFactory.getRemoteInstance(), filter, "供应商类别");
		this.kDTree2.setModel(model);
		this.kDTree2.setSelectionRow(0);
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "非采购组织不能操作");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, MarketSupplierStockListUI.getActionPK(this.actionOnLoad));
		this.kDTree1.setModel(orgTreeModel);
		this.kDTree1.setSelectionRow(0);
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	protected void supplierTypeTreeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		this.refresh(null);
	}
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
	}
	protected String getEditUIModal(){
		return UIFactoryName.NEWTAB;
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
			}
		return null;
	}
	private FilterInfo getTreeFilter() throws Exception {
    	FilterInfo filter = new FilterInfo();
    	FilterItemCollection filterItems = filter.getFilterItems();
    	
    	DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(kDTree2);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(kDTree1);
    	Object TypeInfo = null;
    	//是否选中
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		TypeInfo = TypeNode.getUserObject();
    		//设置容器的title
    		kDContainer1.setTitle(TypeNode.getText());
    	}
    	if (TypeInfo instanceof SupplierInvoiceTypeTreeInfo) {
		    String longNumber = ((SupplierInvoiceTypeTreeInfo)TypeInfo).getLongNumber();
			filterItems.add(new FilterItemInfo("supplier.inviteType.longNumber", longNumber+"%",CompareType.LIKE));
		}
    	if(OrgNode.getUserObject() instanceof OrgStructureInfo){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("longNumber");
    		FullOrgUnitInfo org=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo)OrgNode.getUserObject()).getUnit().getId()),sel);
    		String longNumber=org.getLongNumber();
    		filterItems.add(new FilterItemInfo("orgUnit.longNumber", longNumber+"%",CompareType.LIKE));
    	}
    	return filter;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("IDSET")!=null){
				this.kDContainer1.setTitle("");
				filter.getFilterItems().add(new FilterItemInfo("id", (Set)this.getUIContext().get("IDSET"),CompareType.INCLUDE));
			}else{
				filter = getTreeFilter();
				if(filter==null){
					filter = new FilterInfo();
				}
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		if(this.getUIContext().get("IDSET")==null){
			buildOrgTree();
			buildInviteTypeTree();
		}else{
			this.contOrg.setVisible(false);
			this.contSupplierType.setVisible(false);
			kDSplitPane1.setDividerLocation(0);
			this.kDContainer1.setTitle("供应商评审");
		}
		
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnaudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.kDTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    orgTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTree2.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    supplierTypeTreeTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			MarketSupplierStockListUI.checkBillInWorkflow(this, id.get(i).toString());
			if (!SupplierState.submit.equals(getBizState(id.get(i).toString()))) {
				MsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IMarketSupplierReviewGather)getBizInterface()).audit((MarketSupplierReviewGatherInfo) getBizInterface().getValue(new ObjectUuidPK(BOSUuid.read(id.get(i).toString()))));
		}
		MsgBox.showInfo("操作成功！");
		this.refresh(e);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			MarketSupplierStockListUI.checkBillInWorkflow(this, id.get(i).toString());
			if (!SupplierState.audit.equals(getBizState(id.get(i).toString()))) {
				MsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IMarketSupplierReviewGather)getBizInterface()).unAudit((MarketSupplierReviewGatherInfo) getBizInterface().getValue(new ObjectUuidPK(BOSUuid.read(id.get(i).toString()))));
		}
		MsgBox.showInfo("操作成功！");
		this.refresh(e);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		MarketSupplierStockListUI.checkBillInWorkflow(this, id);
		
		SupplierState state = getBizState(id);
		if (!SupplierState.Save.equals(state)&&!SupplierState.submit.equals(state)) {
			MsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		MarketSupplierStockListUI.checkBillInWorkflow(this, id);
		
		SupplierState state = getBizState(id);
		if (!SupplierState.Save.equals(state)&&!SupplierState.submit.equals(state)) {
			MsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	public SupplierState getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((MarketSupplierReviewGatherInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(kDTree1);
		if(OrgNode!=null&&OrgNode.getUserObject()!=null){
			if(OrgNode.getUserObject() instanceof OrgStructureInfo){
				uiContext.put("org", OrgNode.getUserObject());
			}else{
				uiContext.put("org", null);
			}
		}
	}
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo objectValue = new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo();
		
        return objectValue;
    }

}