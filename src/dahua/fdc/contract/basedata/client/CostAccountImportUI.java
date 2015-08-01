/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.client.AccountClientUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:成本科目引入以及成本科目F7
 * 
 * @author Jackwang date:2006-09-21
 *         <p>
 * @version EAS5.1
 */

public class CostAccountImportUI extends AbstractCostAccountImportUI {
	private static final Logger logger = CoreUIObject.getLogger(CostAccountImportUI.class);

	private static final Color canntSelectColor = new Color(0xFEFED3);

	private CurProjectInfo addressProject = null;

	private HashSet hs = new HashSet();

	private FilterInfo filter = null;

	private CostAccountCollection cac = new CostAccountCollection();

	private KDDialog diag = null;
	
	private KDTree  tree  =  null;

	// /f7
	// Selector
	protected SelectorItemCollection userSeletor = null;

	private boolean isF7 = false;

	private CostAccountInfo[] costAccountInfos;

	protected CostAccountInfo costAccountInfo = null;

	private boolean isOnlyLeaf = false;

	private boolean isMultiSelect = false;

	private boolean isOrgFilter = false;
	private boolean isPrjEnable = false;

	protected boolean isCanceled = true;

	/**
	 * output class constructor
	 */
	public CostAccountImportUI() throws Exception {
		super();
	}

	public String getUITitle() {
		String returnValue = EASResource.getString(isF7 ? "com.kingdee.eas.fdc.basedata.FDCBaseDataResource.CostAccount"
				: "com.kingdee.eas.fdc.basedata.FDCBaseDataResource.CostAccountImport");
		return returnValue;
	}

	public void onLoad() throws Exception {
		// tblMain.checkParsed();// table解析!
		// this.tblMain.getDataRequestManager().addDataFillListener(new
		// KDTDataFillListener() {
		// public void afterDataFill(KDTDataRequestEvent e) {
		// // do something
		// String strTemp = "";
		// for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
		// strTemp = tblMain.getRow(i).getCell(1).getValue().toString();
		// strTemp = strTemp.replace('!', '.');
		// tblMain.getRow(i).getCell(1).setValue(strTemp);
		// tblMain.getRow(i).getCell(0).setValue(strTemp);
		// tblMain.getRow(i).getCell(2).setValue(strTemp);
		// }
		// }
		// });
		// tblMain.getCell(1,"").getStyleAttributes().setBackground()
		super.onLoad();
	//	this.treeMain.setVisible(false);
		if (getUIContext().get("isF7") != null) {
			this.isF7 = ((Boolean) getUIContext().get("isF7")).booleanValue();
		}
		if (getUIContext().get("isOnlyLeaf") != null) {
			this.isOnlyLeaf = ((Boolean) getUIContext().get("isOnlyLeaf")).booleanValue();
		}
		if (getUIContext().get("isMultiSelect") != null) {
			this.isMultiSelect = ((Boolean) getUIContext().get("isMultiSelect")).booleanValue();
		}
		if (getUIContext().get("isOrgFilter") != null) {
			this.isOrgFilter = ((Boolean) getUIContext().get("isOrgFilter")).booleanValue();
		}
		if (getUIContext().get("userSelector") != null) {
			this.userSeletor = (SelectorItemCollection) getUIContext().get("userSelector");
		}

		if (getUIContext().get("isPrjEnable") != null) {
			this.isPrjEnable = ((Boolean) getUIContext().get("isPrjEnable")).booleanValue();
		}
		if (isF7) {// forF7
			this.btnAllSelect.setVisible(false);
			this.btnNoneSelect.setVisible(false);
			this.kDLabelContainer2.setVisible(false);
		} else {// forImport
			this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
			// this.btnAllSelect.setText("全选");
			this.btnNoneSelect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
			// this.btnNoneSelect.setText("全清");
			if (getUIContext().get("address") != null) {
				if (getUIContext().get("address") instanceof FullOrgUnitInfo) {

				} else if (getUIContext().get("address") instanceof CurProjectInfo) {
					addressProject = (CurProjectInfo) getUIContext().get("address");
					CostAccountCollection cacOld = CostAccountFactory.getRemoteInstance().getCostAccountCollection(
							"select longNumber where curProject.id = '" + addressProject.getId().toString() + "'");
					hs = new HashSet();
					for (int i = 0; i < cacOld.size(); i++) {
						hs.add(cacOld.get(i).getLongNumber());
					}
				}

			}
		}

		//buildProjectTree();
		builderSelectProjectTree();
		//this.treeMain.setSelectionRow(0);

		this.tblMain.addKDTEditListener(new KDTEditAdapter() {
			public void editValueChanged(KDTEditEvent e) {
				if (e.getColIndex() == tblMain.getColumnIndex("select")) {
					Boolean old = (Boolean) e.getOldValue();
					Boolean now = (Boolean) e.getValue();
					select(e.getRowIndex(), old.booleanValue(), now.booleanValue());
				}
			}
		});
		// loadDatas(filter);
	}

	private void buildProjectTree() throws Exception {
//		boolean isIncludeDisabled = !isPrjEnable;
//		if (isOrgFilter) {
//			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(isIncludeDisabled);
//			projectTreeBuilder.setDevPrjFilter(false);// 所有项目
//			//treeMain.setShowsRootHandles(true);
//			//projectTreeBuilder.build(this, treeMain, actionOnLoad);
//		} else {
//			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(isIncludeDisabled, true);
//			projectTreeBuilder.setDevPrjFilter(false);// 所有项目
//			treeMain.setShowsRootHandles(true);
//			projectTreeBuilder.build(this, treeMain, actionOnLoad);
//		}

	}

	private void loadDatas(FilterInfo filter) throws BOSException {
		this.tblMain.removeRows();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evInfo = new EntityViewInfo();
		evInfo.getSelector().add("id");
		evInfo.getSelector().add("longNumber");
		evInfo.getSelector().add("name");
		SelectorItemCollection selectors = evInfo.getSelector();
		selectors.add("number");
		selectors.add("displayName");
		selectors.add("level");
		selectors.add("isLeaf");
		selectors.add("isSplit");
		selectors.add("curProject");
		selectors.add("curProject.id");
		selectors.add("curProject.number");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.name");
		selectors.add("curProject.displayName");
		selectors.add("curProject.level");
		selectors.add("curProject.isLeaf");
		evInfo.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evInfo);
		tblMain.getColumn("select").getStyleAttributes().setLocked(false);
		tblMain.getColumn("number").getStyleAttributes().setLocked(false);
		CellTreeNode node = null;
		if (costAccountCollection.size() != 0) {
			for (int i = 0; i < costAccountCollection.size(); i++) {
				IRow row = this.tblMain.addRow();
				node = new CellTreeNode();
				row.getCell("select").setValue(Boolean.valueOf(false));
				final CostAccountInfo costAccountInfo = costAccountCollection.get(i);
				row.getCell("name").setValue(costAccountInfo.getName(SysContext.getSysContext().getLocale()));
				row.getCell("id").setValue(costAccountInfo.getId().toString());
				row.setUserObject(costAccountInfo);

				node.setValue(costAccountInfo.getLongNumber().replace('!', '.'));
				final int level = costAccountInfo.getLevel() - 1;
				final boolean isLeaf = costAccountInfo.isIsLeaf();
				node.setTreeLevel(level);

				if (isLeaf) {
					node.setHasChildren(false);
					if (level > 1) {
						row.getStyleAttributes().setHided(false);
					}
				} else {
					if (level <= 1) {
						if (level == 0) {
							node.setCollapse(false);
						} else {
							node.setCollapse(false);
						}
					} else {
						node.setCollapse(false);// 是否只隐藏根结点
						row.getStyleAttributes().setHided(false);
					}
					node.addClickListener(new NodeClickListener() {
						public void doClick(CellTreeNode source, ICell cell, int type) {
							tblMain.revalidate();

						}
					});
					node.setHasChildren(true);
					row.getStyleAttributes().setBackground(canntSelectColor);
				}

				row.getCell("number").setValue(node);

			}

		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (isF7) {
			int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
			if (rowID.length > 0) {
				// 单行选择,要求支持多选。
				ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
				costAccountInfos = new CostAccountInfo[rowID.length];
				for (int i = 0; i < rowID.length; i++) {
					String strID = tblMain.getRow(rowID[i]).getCell("id").getValue().toString();
					costAccountInfos[i] = iCostAccount.getCostAccountInfo(new ObjectUuidPK(BOSUuid.read(strID)));
					if (this.isOnlyLeaf) {
						if (!costAccountInfos[i].isIsLeaf()) {
							MsgBox.showInfo(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "IsOnlyLeaf"));
							return;
						}
					}
				}
				this.isCanceled = false;
				this.getUIWindow().close();
			} else {
				MsgBox.showInfo(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "SelectCostAccount"));
				// KDOptionPane.showMessageDialog(this , "您没有选择任何记录！") ;
			}

		} else {
			IRow row;
			cac.clear();
			boolean flag = false;
			for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
				row = tblMain.getRow(i);
				if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
					CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
					// cai.setAssigned(true);
					cac.add(cai);
					flag = true;
				}
			}
			if (flag) {
				MsgBox.showWarning(diag, "温馨提示:\n引入科目与所选工程项目下编码重复的科目，将不会被引入！");
				CostAccountFactory.getRemoteInstance().importDatas(cac, this.addressProject.getId());
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Import_success"));
				destroyWindow();
			}
		}
	}

	private void tblMain_performEnterOrDblClickEvent() throws BOSException, EASBizException, UuidException {
		int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
		if (rowID.length > 0) {
			int selectIndex = KDTableUtil.getSelectedRow(this.tblMain);
			if (selectIndex == -1) {
				// String resFullName =
				// "com.kingdee.eas.basedata.master.account.client.AccountResource.SelectAccount"
				// ;
				// String strName = EASResource.getString(resFullName) ;
				MsgBox.showInfo(this, EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "SelectAccount")); //
				return;
			}
			String strID = tblMain.getRow(rowID[0]).getCell("id").getValue().toString();
			// IAccountView iAccountView =
			// AccountViewFactory.getRemoteInstance();
			costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(BOSUuid.read(strID)));

			Map map = (HashMap) this.getUIContext();

			if (isF7) {
				if (this.isOnlyLeaf) {
					if (!costAccountInfo.isIsLeaf()) {
						MsgBox.showInfo(this, EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "IsOnlyLeaf")); //
						return;
					}
				}
				this.isCanceled = false;
				this.getUIWindow().close();
			}
		} else {
			MsgBox.showInfo(this, EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "SelectAccount"));
		}
	}

	/**
	 * output actionCancel_actionPerformed method
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (isF7) {
			isCanceled = true;
			this.getUIWindow().close();
		} else {
			this.getUIWindow().close();
		}
	}

	/**
	 * output actionAllSelect_actionPerformed method
	 */
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			if (!this.tblMain.getRow(i).getCell("select").getStyleAttributes().isLocked()) {
				this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
			}
		}
	}

	/**
	 * output actionNoneSelect_actionPerformed method
	 */
	public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		//treeSelectChange();
	}

	/**
	 * 
	 * 描述：左边树选择改变，重新构造条件执行查询
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	private void treeSelectChange() throws Exception {
	//	FilterInfo filter = new FilterInfo();
	//	FilterItemCollection filterItems = filter.getFilterItems();
		/*
		 * 工程项目树
		 */
		//DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//		if (node == null) {
//			return;
//		}
//		// if (OrgViewUtils.isTreeNodeDisable(node)) {
//		// return;
//		// }
//		if (node.getUserObject() instanceof CurProjectInfo) {
//			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
//			filterItems.add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
//		} else if (node.getUserObject() instanceof OrgStructureInfo) {
//			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
//			if (oui == null || oui.getUnit() == null)
//				return;
//			FullOrgUnitInfo info = oui.getUnit();
//			filterItems.add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
//		}

		// if (getProjSelectedTreeNode() != null &&
		// getProjSelectedTreeNode().getUserObject() instanceof TreeBaseInfo) {
		// TreeBaseInfo projTreeNodeInfo = (TreeBaseInfo)
		// getProjSelectedTreeNode().getUserObject();
		// BOSUuid id = projTreeNodeInfo.getId();
		// // 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
		// if (projTreeNodeInfo instanceof FullOrgUnitInfo) {
		// // Set idSet = ContractClientUtils.genOrgUnitIdSet(id);
		// // filterItems.add(new FilterItemInfo("fullOrgUnit.id", idSet,
		// // CompareType.INCLUDE));
		// filterItems.add(new FilterItemInfo("fullOrgUnit.id", id,
		// CompareType.EQUALS));
		// }
		// // 选择的是项目，取该项目下的所有源成本科目
		// else if (projTreeNodeInfo instanceof CurProjectInfo) {
		// // Set idSet = ContractClientUtils.genProjectIdSet(id);
		// // filterItems.add(new FilterItemInfo("curProject.id", idSet,
		// // CompareType.INCLUDE));
		// filterItems.add(new FilterItemInfo("curProject.id", id,
		// CompareType.EQUALS));
		// }
		// }else{
		// return;
		// }
//		if (!isF7) {
//			filterItems.add(new FilterItemInfo("isSource", Boolean.valueOf(true), CompareType.EQUALS));
//		}
//		filterItems.add(new FilterItemInfo("isEnabled", Boolean.valueOf(true), CompareType.EQUALS));
//		this.filter = filter;
//
//		if (this.tblMain.getRowCount() > 0) {
//			tblMain.getSelectManager().select(0, 0);
//		}
		// this.loadDatas(filter);
		// tblMain.getColumn(1).getStyleAttributes().setLocked(true);
		// tblMain.getColumn(2).getStyleAttributes().setLocked(true);
	}

//	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
//		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
//	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		// if (!isF7) {
		// editedCellAfter(e);
		// } else {
		// // super.tblMain_tableClicked(e);
		// if (e.getClickCount() == 1) {
		// int columnIndex = e.getColIndex();
		// // 选中第二列(number)时
		// if (columnIndex == 1) {
		// //
		//
		// int rowIndex = e.getRowIndex();
		// IRow row = tblMain.getRow(rowIndex);
		// if (row == null) {
		// return;
		// }
		// // NumberExpandInfo numberExpandInfo = (NumberExpandInfo)
		// // row.getCell("number").getValue();
		// // if (treeRender.inRect(numberExpandInfo, e.getX(),
		// // e.getY())) {
		// // setTreeDisplayStyle(row);
		// // }
		//
		// }
		// } else if (e.getClickCount() == 2) {
		// tblMain_performEnterOrDblClickEvent();
		// }
		// }
	}

	/**
	 * 设置指定单元格的数值对象
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	private void editedCellAfter(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		// int colIndex = 0;
		Object object = this.tblMain.getRow(rowIndex).getCell(colIndex).getValue();
		// if (object == null) {
		// if (colIndex == CommonConstant.FILTER_CONDITION) {
		// resetCell(this.kdtTable.getRow(rowIndex).getCell(CommonConstant.FILTER_COMPARE_SIGN));
		// resetCell(this.kdtTable.getRow(rowIndex).getCell(CommonConstant.FILTER_COMPARE_VALUE));
		// }
		// return;
		// }

		// 解决bug BT314822 从另外一个单元引入科目时，如果上次引入了1级科目或该1级科目下级部分科目，
		// 但在下次继续引入该1级科目的其他二级科目时，系统提示该1级科目已存在，不能引入。（备注：1级科目得手工启用）
		// 解决方法 判断该结点是否已经被选用，如果已经被选用不选
		boolean opinion = true;

		String strID = tblMain.getRow(rowIndex).getCell("id").getValue().toString();
		CostAccountInfo cAInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(BOSUuid.read(strID)));
		// TreeBaseInfo projTreeNodeInfo = (TreeBaseInfo)getProjSelectedTreeNode().getUserObject();

		if (cAInfo.innerGetParent() != null) {
			cAInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(cAInfo.innerGetParent().getId()));
			;
		}

		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, cAInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent, null, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("curproject.id", addressProject.getId().toString(), CompareType.EQUALS));
		filter.setMaskString("#0 and #1 and #2 ");

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		TreeBaseCollection results = iCostAccount.getTreeBaseCollection(view);
		if (results != null && results.size() > 0) {
			opinion = false;
		}

		switch (colIndex) {
		case 0: {
			if (((Boolean) object).booleanValue()) {// 原来是选中的,欲不选
				thisCancel(rowIndex, colIndex);
			} else if (opinion) {// 原来未选中,欲选中
				thisSelect(rowIndex, colIndex);
			}
			return;
		}
		default: {
			return;
		}
		}
	}

	private void thisCancel(int r, int c) {
		// Object object = this.tblMain.getRow(r).getCell(c).getValue();
		if (tblMain.getRow(r).getCell("parent.id").getValue() != null) {
			String parentId = tblMain.getRow(r).getCell("parent.id").getValue().toString();
			String thisId = tblMain.getRow(r).getCell("id").getValue().toString();
			boolean parentSelect = false;
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				if ((tblMain.getRow(i).getCell("parent.id").getValue() != null)
						&& (parentId.equals(tblMain.getRow(i).getCell("parent.id").getValue().toString()))) {// 同父
					if (tblMain.getRow(i).getCell("select").getValue().toString().equals("true")
							&& (!thisId.equals(tblMain.getRow(i).getCell("id").getValue().toString()))) {// 是选中的
						parentSelect = true;
						break;
					}
				}
			}
			if (!parentSelect) {
				for (int i = 0; i < this.tblMain.getRowCount(); i++) {
					if (parentId.equals(tblMain.getRow(i).getCell("id").getValue().toString())) {
						tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
						break;
					}
				}
			}
		}
	}

	private void thisSelect(int r, int c) {
		// Object object = this.tblMain.getRow(r).getCell(c).getValue();
		if (tblMain.getRow(r).getCell("parent.id").getValue() != null) {
			String parentId = tblMain.getRow(r).getCell("parent.id").getValue().toString();
			// String thisId =
			// tblMain.getRow(r).getCell("id").getValue().toString();
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				if (parentId.equals(tblMain.getRow(i).getCell("id").getValue().toString())) {// 是父
					if (tblMain.getRow(i).getCell("select").getValue().toString().equals("true")) {// 已经选中了
						break;
					} else {
						tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
						thisSelect(i, c);
					}
				}
			}

		}
	}

	// 返回前当所选择的科目对象，多选。
	public CostAccountInfo[] getReturnValues() throws Exception {
		int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
		CostAccountInfo[] selectInfo = new CostAccountInfo[rowID.length];
		if (rowID.length < 1) {
			return null;
		}
		for (int i = 0; i < rowID.length; i++) {
			String id = tblMain.getRow(rowID[i]).getCell("id").getValue().toString();
			ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
			if (userSeletor != null) {
				selectInfo[i] = iCostAccount.getCostAccountInfo(new ObjectUuidPK(BOSUuid.read(id)), userSeletor);
			} else {
				selectInfo[i] = iCostAccount.getCostAccountInfo(new ObjectUuidPK(BOSUuid.read(id)));
			}
		}

		return selectInfo;
	}	
	
	private void builderSelectProjectTree() throws Exception{
		tree = new com.kingdee.bos.ctrl.swing.KDTree();		
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(true);
		projectTreeBuilder.setDevPrjFilter(false);// 所有项目
		tree.setShowsRootHandles(true);
		projectTreeBuilder.build(this, tree, actionOnLoad, OrgConstants.DEF_CU_ID);		
	}

	private CurProjectInfo selectProject() {

		if (diag == null) {
			// Frame f = (Frame) SwingUtilities.getWindowAncestor(this);

			diag = new KDDialog( new Frame(), "请选择要导入的工程项目", true);
			final KDTree mytree = new KDTree(tree.getModel(), false);
			mytree.expandAllNodes(true, (TreeNode) ((TreeModel) tree.getModel()).getRoot());
			//只选择一个by hpw
			tree.getSelectionModel().setSelectionMode(1);
			mytree.setSelectionModel(tree.getSelectionModel());
			diag.getContentPane().setLayout(new java.awt.BorderLayout());
			KDTreeView treeView = new KDTreeView(mytree, "工程项目树", true);
			diag.getContentPane().add(treeView, BorderLayout.CENTER);
			KDButton btnOk = new KDButton("确定");
			btnOk.setSize(73, 50);
			btnOk.setPreferredSize(new Dimension(73, 50));
			KDButton btnCancel = new KDButton("取消");
			btnCancel.setSize(73, 21);
			KDPanel p = new KDPanel();
			p.add(btnOk);
			p.add(btnCancel);
			diag.getContentPane().add(p, BorderLayout.SOUTH);
			
			//因是独立界面，加上鼠标事件 by hpw
			mytree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						CurProjectInfo project = null;
						DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) mytree.getLastSelectedPathComponent();
						if (node.getUserObject() instanceof CurProjectInfo) {
							CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
							project = projectInfo;
							diag.setUserObject(project);
							diag.setVisible(false);
						}
					}
				}				
			});
			
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CurProjectInfo project = null;
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) mytree.getLastSelectedPathComponent();
					if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
						project = null;
						MsgBox.showWarning(diag, "请选择工程项目");
						SysUtil.abort();
					}
					if (!node.isLeaf()) {
						project = null;
						MsgBox.showWarning(diag, ContractClientUtils
								.getRes("selectProjLeafPls"));
						SysUtil.abort();
					}
					if (node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
						project = projectInfo;
						diag.setUserObject(project);
						diag.setVisible(false);
					} 
				}
			});
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					diag.setUserObject(null);
					diag.setVisible(false);
				}
			});
			diag.setSize(300, 400);
			CtrlSwingUtilities.centerWindow(diag);

		}
		diag.setVisible(true);
		Object obj = diag.getUserObject();
		if (obj instanceof CurProjectInfo) {
			return (CurProjectInfo) obj;
		} else {
			return null;
		}
	}

	public void select(int row, boolean old, boolean now) {
		if (old == now)
			return;
		tblMain.getCell(row, "select").setValue(Boolean.valueOf(now));
		CostAccountInfo acctSelect = (CostAccountInfo) tblMain.getRow(row).getUserObject();
		CostAccountInfo acct = null;
		int level = acctSelect.getLevel();
		// 下级
		for (int i = row + 1; i < tblMain.getRowCount(); i++) {
			acct = (CostAccountInfo) tblMain.getRow(i).getUserObject();
			if (acct.getLevel() > level) {
				tblMain.getCell(i, "select").setValue(Boolean.valueOf(now));

			} else {
				break;
			}
		}

		// 上级
		int parentLevel = level - 1;
		if (now) {
			for (int i = row - 1; i >= 0; i--) {
				if (parentLevel == 0) {
					break;
				}
				acct = (CostAccountInfo) tblMain.getRow(i).getUserObject();
				if (acct.getLevel() == parentLevel) {
					ICell cell = tblMain.getCell(i, "select");
					if (cell.getValue() != Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					} else {
						break;
					}

				}
			}
		} else {

			// 不选择,检查同级是否有选择的
			boolean hasSelected = false;
			// 下面的行
			for (int i = row + 1; i < tblMain.getRowCount(); i++) {
				acct = (CostAccountInfo) tblMain.getRow(i).getUserObject();
				if (acct.getLevel() == level) {
					ICell cell = tblMain.getCell(i, "select");
					if (cell.getValue() == Boolean.TRUE) {
						hasSelected = true;
						break;
					} else if (acct.getLevel() < level) {
						break;
					}
				}
			}
			// 上面的行

			if (!hasSelected) {
				for (int i = row - 1; i >= 0; i--) {
					acct = (CostAccountInfo) tblMain.getRow(i).getUserObject();
					if (acct.getLevel() == level) {
						ICell cell = tblMain.getCell(i, "select");
						if (cell.getValue() == Boolean.TRUE) {
							hasSelected = true;
							break;
						}
					} else if (acct.getLevel() < level) {
						row = i;
						break;
					}
				}
			}

			if (!hasSelected) {

				// 设置父级
				parentLevel = level - 1;
				for (int j = row; j >= 0; j--) {
					if (parentLevel == 0) {
						break;
					}
					acct = (CostAccountInfo) tblMain.getRow(j).getUserObject();
					if (acct.getLevel() == parentLevel) {
						ICell cell = tblMain.getCell(j, "select");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
					}
				}

			}

		}

	}

	protected void btnSelectProject_actionPerformed(ActionEvent e) throws Exception {
		txtProject.setUserObject(selectProject());

		if (txtProject.getUserObject() != null) {
			CurProjectInfo project = (CurProjectInfo) txtProject.getUserObject();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE, CompareType.EQUALS));
			loadDatas(filter);
			txtProject.setStringValue(project.getName());
		}
	}
}