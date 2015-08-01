/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IContractType;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @(#)			ContractTypeListUI			
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		合同类型叙事簿界面
 *		
 * @author		蒲磊		<p>
 * @createDate	2011-8-15	<p>	 
 * @version		EAS7.0		
 * @see					
 */
public class ContractTypeListUI extends AbstractContractTypeListUI {
	/** */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(ContractTypeListUI.class);

	/**
	 * output class constructor
	 */
	public ContractTypeListUI() throws Exception {
		super();
		//去掉查询功能，省去由此引起的一堆bugs
		this.btnQuery.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
	}

	/**
	 * 
	 */
	public void onLoad() throws Exception {
		FDCTableHelper.setColumnMoveable(tblMain, true);
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				// do something
				String strTemp = "";
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					strTemp = tblMain.getRow(i).getCell(0).getValue().toString();
					strTemp = strTemp.replace('!', '.');
					tblMain.getRow(i).getCell(0).setValue(strTemp);
				}
			}
		});
		super.onLoad();
		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.actionCancelCancel.setVisible(true);
			this.actionCancel.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.actionCancelCancel.setVisible(false);
			this.actionCancel.setVisible(false);			
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		FDCHelper.formatTableNumber(this.tblMain, "payScale");
		FDCHelper.formatTableNumber(this.tblMain, "stampTaxRate");
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		this.tblMain.getHeadRow(0).getCell("payScale").setValue("付款比例(%)");
		this.tblMain.getHeadRow(0).getCell("stampTaxRate").setValue("印花税率(%)");
	}

	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue())
						.booleanValue();
				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);
			}
		} else {
			disabledWBT();

		}
	}

	/**
	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancelCancel.setEnabled(!isEnabled);
		this.actionCancel.setEnabled(isEnabled);
	}

	/**
	 * 把启用/禁止按钮disabled
	 */
	private void disabledWBT() {
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setEnabled(false);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof ContractTypeInfo) {
			ContractTypeInfo cti = (ContractTypeInfo) node.getUserObject();
			HashSet lnUps = new HashSet();
			ContractTypeCollection ctc = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(
					"select id where longNumber like '" + cti.getLongNumber() + "!%'");
			lnUps.add(cti.getId().toString());
			for (int i = 0; i < ctc.size(); i++) {
				lnUps.add(ctc.get(i).getId().toString());
			}
			FilterInfo filterInfo = new FilterInfo();
			if (lnUps.size() != 0) {
				filterInfo.getFilterItems().add(new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
				this.mainQuery.setFilter(filterInfo);
			}
		} else {
			this.mainQuery.setFilter(new FilterInfo());
		}

		FilterInfo filter = (FilterInfo) this.getUIContext().get("F7Filter");
		if (filter != null) {
			this.mainQuery.getFilter().mergeFilter(filter, "and");
		}
		tblMain.removeRows();// 触发新查询
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		ContractTypeInfo cti = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		} else {
			if (node.getUserObject() instanceof ContractTypeInfo) {
				cti = (ContractTypeInfo) node.getUserObject();
			}
		}
		if (cti != null) {
			if ((!this.isHasSubLevel(cti)) && this.isReferenced(cti)) {
				if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
						"ContractType_Add_CopyData")))) {
					// 不继续
					return;
				}
			}
		}

		super.actionAddNew_actionPerformed(e);
	}

	/*
	 * 是否有下级
	 */
	private boolean isHasSubLevel(ContractTypeInfo cti) throws EASBizException, BOSException {
		boolean flag = true;
		if (cti != null) {
			flag = ContractTypeFactory.getRemoteInstance().exists("select id where parent.id = '" + cti.getId().toString() + "'");
		}
		return flag;
	}

	/*
	 * 是否被引用
	 */
	private boolean isReferenced(ContractTypeInfo cti) throws EASBizException, BOSException {
		boolean flag = false;
		if (cti != null) {
			flag = ContractBillFactory.getRemoteInstance().exists("select id where contractType.id = '" + cti.getId().toString() + "'");

		}
		return flag;
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		this.verifyNotAccepted(this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()));
		super.actionRemove_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return ContractTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ContractTypeEditUI.class.getName();
	}

	// 重写返回“根节点”名称的方法
	protected String getRootName() {
		return EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.CONTRACTTYPE);
	}
	// 支持编码、名称的定位功能
	protected String[] getLocateNames()
    {
        String[] locateNames = new String[2];
        locateNames[0] = IFWEntityStruct.tree_LongNumber;
        locateNames[1] = IFWEntityStruct.dataBase_Name;
        return locateNames;
    }
	/**
     * 启用操作
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IContractType iContractType = ContractTypeFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iContractType.enabled(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
			tblMain.removeRows();// 触发新查询
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(true);
		}
    }
	
	/**
     * 禁用操作
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
        IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IContractType iContractType = ContractTypeFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iContractType.disEnabled(pk)) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
			tblMain.removeRows();// 触发新查询
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
		}
        
    }
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	/**
	 * 
	 * 检查是否删除的是系统预设信息
	 */

	private void verifyNotAccepted(IRow row) {
		if (row.getCell("CU.id") != null && row.getCell("CU.id").getValue() != null
				&& (OrgConstants.SYS_CU_ID.equals(row.getCell("CU.id").getValue().toString()))) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Delete_SysData"));
			SysUtil.abort();
		}
	}

	/**
	 * 检查是否编辑的是系统预设信息
	 */
	private boolean verifySysDataEdit() {
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return false;
		} else {
			int i = this.tblMain.getSelectManager().getActiveRowIndex();
			if (OrgConstants.SYS_CU_ID.equals(this.tblMain.getRow(i).getCell("CU.id").getValue().toString())) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Update_SysData"));
				SysUtil.abort();
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("isCost"));
		sic.add(new SelectorItemInfo("payScale"));
		sic.add(new SelectorItemInfo("dutyOrgUnit.name"));
		sic.add(new SelectorItemInfo("stampTaxRate"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("isRefProgram"));
		return sic;
	}

	protected String getEditUIModal() {
		// return UIFactoryName.MODEL;
		// return UIFactoryName.NEWWIN;
		// 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}
	protected boolean isShowAttachmentAction() {
		return true;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}