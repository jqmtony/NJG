/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillFactory;
import com.kingdee.eas.fdc.aimcost.AimCostCtrlBillInfo;
import com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill;
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
public class AimCostCtrlBillListUI extends AbstractAimCostCtrlBillListUI {
	
	private static final Logger logger = CoreUIObject.getLogger(AimCostCtrlBillListUI.class);


	public static final String IS_MODIFY = "isModify";
	

	/**
	 * output class constructor
	 */
	public AimCostCtrlBillListUI() throws Exception {
		super();
		
		this.menuItemAudit.setEnabled(true);
        this.menuItemUnAudit.setEnabled(true);
		
        KDMenuItem  menuAmendment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        menuAmendment.setName("menuAmendment");
		
		menuAmendment.setAction((IItemAction)ActionProxyFactory.getProxy(
				this.actionAmendment, new Class[] { IItemAction.class }, getServiceContext()));;		
        menuAmendment.setText("修订");		
        menuAmendment.setToolTipText("修订");
        menuAmendment.setEnabled(true);
        menuAmendment.setIcon(EASResource.getIcon("imgTbtn_particular"));
        
        this.menuEdit.add(menuAmendment);
        
	}
	
	protected void updateButtonStatus() {
		
	}
	

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return AimCostCtrlBillFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getColumn("verNumber").getStyleAttributes().setNumberFormat("#,##0.0");
		init();
		initButton();

	}

	protected String getEditUIName() {
		return AimCostCtrlBillEditUI.class.getName();
	}
	
	private void initButton(){
		this.btnImportTemplate.setVisible(false);
		this.btnImportTemplate.setEnabled(false);
	}

	public void init() {
		// /虚体成本中心问题，后续修改
		this.btnAddNew.setVisible(true);
		this.btnAddNew.setEnabled(true);
		this.btnEdit.setEnabled(true);
		this.btnEdit.setVisible(true);
		this.btnAmendment.setVisible(true);
		this.btnAmendment.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(true);
		this.btnRemove.setVisible(true);
		this.btnRemove.setEnabled(true);
		this.btnAmendment.setIcon(EASResource.getIcon("imgTbtn_particular"));

	}

	protected void checkBeforeAddNew(ActionEvent e) {

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		//判断当前有没有打开新增的窗体
		IUIWindow win = FDCClientUtils.findUIWindow(this.getEditUIName(), this.getUIContext(), dataObjects, OprtState.ADDNEW);
		if (null != win) {
			//显示窗体
			win.show();
			//显示后试图关闭，如果新增的窗体有新增数据就会提示用户，如果没有就直接关掉打开了一个新的
			win.close();
		}
		
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		Object obj = node.getUserObject();
		String proId = ((CurProjectInfo) obj).getId().toString();
		boolean isExist = getBizInterface().exists("where project.id = '".concat(proId).concat("'"));
		if (isExist) {
			throw new EASBizException(new NumericExceptionSubItem("1", "此工程项目下已存在目标成本控制单\n不允许再新增"));
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * 修订
	 */
	public void actionAmendment_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkAudited();
		checkLastVersion();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		
		// uiContext.put(IS_MODIFY, Boolean.TRUE);
		uiContext.put("modify", "modify");
		uiContext.put("info",getSelectedInfo());
		// uiContext.put("parent", this);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null, OprtState.EDIT);
		ui.show();
		setLocatePre(false);
		refresh(e);
		setPreSelecteRow();
		setLocatePre(true);
	}
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String keyValue = getSelectedKeyValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		AimCostCtrlBillInfo info = AimCostCtrlBillFactory.getRemoteInstance().getAimCostCtrlBillInfo(new ObjectUuidPK(keyValue), sic);
		if (!(FDCBillStateEnum.SAVED.equals(info.getState()) || FDCBillStateEnum.SUBMITTED.equals(info.getState()))) {
			FDCMsgBox.showWarning("该状态单据不能修改");
			abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	
	

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String keyValue = getSelectedKeyValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		AimCostCtrlBillInfo info = AimCostCtrlBillFactory.getRemoteInstance().getAimCostCtrlBillInfo(new ObjectUuidPK(keyValue), sic);
		if (!(FDCBillStateEnum.SAVED.equals(info.getState()) || FDCBillStateEnum.SUBMITTED.equals(info.getState()))) {
			FDCMsgBox.showWarning("该状态单据不能删除");
			abort();
		}
		super.actionRemove_actionPerformed(e);
		
	}
	
	
	
	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);
	}

	private void checkLastVersion() throws Exception {
		if (!getIsLastVersion()) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能修订"));
		}
	}

	private void checkAudited() throws Exception {
		AimCostCtrlBillInfo info = getSelectedInfo();
		if (!FDCUtils.isBillAudited(info)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非审批单据不能修订"));
		}
	}
	
	
	private boolean getIsLastVersion() throws Exception{
		AimCostCtrlBillInfo info=getSelectedInfo();
		return info.isIsLatestVer();
	}
	
	
	

	private IAimCostCtrlBill getServiceInterface() throws Exception {
		IAimCostCtrlBill service = (IAimCostCtrlBill) getBizInterface();
		return service;
	}

	private AimCostCtrlBillInfo getSelectedInfo() throws Exception {
		checkSelected();
		return getServiceInterface().getAimCostCtrlBillInfo(new ObjectUuidPK(getSelectedKeyValue()), getSelectors());
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
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));
		return sic;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();//added by ken_liu...增加检查选中行
		int rowIndex = 	tblMain.getSelectManager().getActiveRowIndex();
	    IRow row = tblMain.getRow(rowIndex);
	    Boolean   isLastVersion  = (Boolean)row.getCell("isLatestVer").getValue();
		if (!isLastVersion.booleanValue()) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能反审批"));
		}
		super.actionUnAudit_actionPerformed(e);
	}

	protected void audit(List ids) throws Exception {
		AimCostCtrlBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		AimCostCtrlBillFactory.getRemoteInstance().unAudit(ids);
	}
	

}