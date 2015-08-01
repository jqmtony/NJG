/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class DynCostCtrlBillListUI extends AbstractDynCostCtrlBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DynCostCtrlBillListUI.class);
    
    
    /**
     * output class constructor
     */
    public DynCostCtrlBillListUI() throws Exception  
    {	
        super();
        this.menuItemAudit.setEnabled(true);
        this.menuItemUnAudit.setEnabled(true);
        
        this.menuItemAddNew.setVisible(true);
        this.menuItemRemove.setVisible(true);
        
        KDMenuItem  menuAmendment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        menuAmendment.setName("menuAmendment");
		
		menuAmendment.setAction((IItemAction)ActionProxyFactory.getProxy(
				this.actionRevise, new Class[] { IItemAction.class }, getServiceContext()));;		
        menuAmendment.setText(resHelper.getString("btnRevise.text"));		
        menuAmendment.setToolTipText(resHelper.getString("btnRevise.toolTipText"));
        menuAmendment.setEnabled(true);
        menuAmendment.setIcon(EASResource.getIcon("imgTbtn_particular"));
        
        this.menuEdit.add(menuAmendment);
        
    }
    
    protected ICoreBase getRemoteInterface() throws BOSException {
		return DynCostCtrlBillFactory.getRemoteInstance();
	}
    
    protected void updateButtonStatus() {
		
	}
    
 
    public void onLoad() throws Exception {
	    super.onLoad();
	    this.tblMain.getColumn("verNumber").getStyleAttributes().setNumberFormat("#,##0.0");
	    this.btnAddNew.setVisible(true);
	    this.btnAddNew.setEnabled(true);
	    this.btnView.setVisible(true);
	    this.btnView.setEnabled(true);
	    this.btnEdit.setVisible(true);
	    this.btnEdit.setEnabled(true);
	    this.btnRemove.setVisible(true);
	    this.btnRemove.setEnabled(true);
	    this.btnAuditResult.setVisible(false);
	    this.btnAudit.setVisible(true);
	    this.btnAudit.setEnabled(true);
	    this.btnUnAudit.setVisible(true);
	    this.btnUnAudit.setEnabled(true);
	    this.btnRevise.setEnabled(true);
	    this.btnImportTemplate.setEnabled(false);
	    this.btnImportTemplate.setVisible(false);
	    this.btnRevise.setIcon(EASResource.getIcon("imgTbtn_particular"));
	    
     }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

     protected String getEditUIName() {
    	return DynCostCtrlBillEditUI.class.getName();
    }
     
     
     protected String getEditUIModal() {
    	return super.getEditUIModal();
    }
    
	protected void audit(List ids) throws Exception {
		checkSelected();
		DynCostCtrlBillFactory.getRemoteInstance().audit(BOSUuid.read(ids.get(0).toString()));
	}
	
	protected void unAudit(List ids) throws Exception {
		checkSelected();
		DynCostCtrlBillFactory.getRemoteInstance().unAudit(BOSUuid.read(ids.get(0).toString()));
	}
	
	public void actionImportTemplate_actionPerformed(ActionEvent e) throws Exception {
		this.getUIContext().put("modify", "modify");
		actionAddNew_actionPerformed(e);
	
	}
	
	//added by ken_liu...增加删除判断。
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String keyValue = getSelectedKeyValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		
		DynCostCtrlBillInfo info = DynCostCtrlBillFactory.getRemoteInstance().getDynCostCtrlBillInfo(new ObjectUuidPK(keyValue), sic);
		if ( !FDCBillStateEnum.SAVED.equals(info.getState())  ) {
			FDCMsgBox.showWarning("该状态单据不能删除");
			abort();
		}
		super.actionRemove_actionPerformed(e);
		
	}
	
	/**
	 * @author jie_chen
	 * 修订
	 */
	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkAudited();
		checkLastVersion();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("modify", "modify");
		uiContext.put("parent", this);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.EDIT);
		ui.show();
		setLocatePre(false);
		refresh(e);
		setPreSelecteRow();
		setLocatePre(true);
	}
	
	
	/**
	 * 审批
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		refresh(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}
	
	
	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = 	tblMain.getSelectManager().getActiveRowIndex();
	    IRow row = tblMain.getRow(rowIndex);
	    Boolean   isLastVersion  = (Boolean)row.getCell("isLatestVer").getValue();
		if (!isLastVersion.booleanValue()) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能反审批"));
		}
		super.actionUnAudit_actionPerformed(e);
	}
	
	
	protected void checkBeforeAddNew(ActionEvent e) {
		
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		Object obj = node.getUserObject();
		String proId = ((CurProjectInfo) obj).getId().toString();
		boolean isExist = getBizInterface().exists("where CurProject.id = '".concat(proId).concat("'"));
		if (isExist) {
			throw new EASBizException(new NumericExceptionSubItem("1", "此工程项目下已存在成本控制单\n不允许再新增"));
		}
		super.actionAddNew_actionPerformed(e);
	}
	
	private void checkAudited() throws Exception {
		DynCostCtrlBillInfo info = getSelectedInfo();
		if (!FDCUtils.isBillAudited(info)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非审批单据不允许修订"));
		}
	}

	private void checkLastVersion() throws Exception {
	    int rowIndex = 	tblMain.getSelectManager().getActiveRowIndex();
	    IRow row = tblMain.getRow(rowIndex);
	    Boolean   isLastVersion  = (Boolean)row.getCell("isLatestVer").getValue();
		if (!isLastVersion.booleanValue()) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不允许修订"));
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("isLatestVer"));
		sic.add(new SelectorItemInfo("verNumber"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("isTemp"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));
		return sic;
	}
	
	protected void beforeExcutQuery(EntityViewInfo ev) {
		SorterItemCollection coll = ev.getSorter();
		if(coll==null){
			coll  = new SorterItemCollection();
		}
		SorterItemInfo sorterInfo = new SorterItemInfo("verNumber");
		sorterInfo.setSortType(SortType.DESCEND);
		coll.add(sorterInfo);
		
//		coll.add(new SorterItemInfo("isLatestVer"));
//		coll.add(new SorterItemInfo("verNumber"));
//		ev.setSorter(coll);
		super.beforeExcutQuery(ev);
	}
	
	
 
	private IDynCostCtrlBill getServiceInterface() throws Exception {
		IDynCostCtrlBill service = (IDynCostCtrlBill)getBizInterface();
		return service;
	}
	
	private DynCostCtrlBillInfo getSelectedInfo() throws Exception {
		checkSelected();
		return getServiceInterface().getDynCostCtrlBillInfo(new ObjectUuidPK(getSelectedKeyValue()), getSelectors());
	}
	
}