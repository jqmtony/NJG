/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.client.AimCostHandler;
import com.kingdee.eas.fdc.aimcost.client.AimCostVersionHandler;
import com.kingdee.eas.fdc.aimcost.client.ExpressionUI;
import com.kingdee.eas.fdc.aimcost.client.VersionInputUI;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fm.common.AmountUnitEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AimCostImportUI extends AbstractAimCostImportUI {

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();

	public static String resourcePath = "com.kingdee.eas.fdc.costdb.CostDBResource";

	private DBAimCostHandler handler;
	
	private DateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

	public AimCostImportUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		// super.tblMain_tableClicked(e);
	}

	private Set getProductTypeSet(String selectObjId) throws BOSException {
		BOSObjectType bosType = BOSUuid.read(selectObjId).getType();
		Set set = new HashSet();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", selectObjId));
			// 产品类型
			view.getSelector().add("productType.*");
			// 分摊数据
			view.getSelector().add("curProjProEntrApporData.apportionType.*");
			view.getSelector().add("curProjProEntrApporData.value");

			CurProjProductEntriesCollection collObj = CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);

			for (int i = 0; i < collObj.size(); i++) {
				String productId = collObj.get(i).getProductType().getId().toString();
				set.add(productId);
			}

		}
		return set;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		DyProductTypeGetter proGetter = new DyProductTypeGetter(selectObjId);
		Set productTypeSet = FDCHelper.getSetByArray(proGetter.getProductTypeIds());
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		filter.getFilterItems().add(new FilterItemInfo("id", productTypeSet, CompareType.INCLUDE));
		view.setFilter(filter);
		f7Product.setEntityViewInfo(view);
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		tblMain.getColumn("product").setEditor(f7Editor);
		if (handler != null && selectObjId.equals(this.handler.aimCost.getOrgOrProId())) {
			return;
		}
		if (handler != null) {
			this.verifySaved(e.getOldLeadSelectionPath());
		}
		handler = new DBAimCostHandler(selectObjId, null);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	/**
	 * 设置表格属性
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		if (currentOrg != null && currentOrg.isIsBizUnit()) {
			table.getStyleAttributes().setLocked(false);
		} else {
			table.getStyleAttributes().setLocked(true);
		}
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		Color lockColor = new Color(0xF0AAD9);
		table.getColumn("acctNumber").getStyleAttributes().setBackground(lockColor);
		table.getColumn("sumAimCost").getStyleAttributes().setLocked(true);
		table.getColumn("sumAimCost").getStyleAttributes().setBackground(lockColor);
		table.getColumn("offset").getStyleAttributes().setLocked(true);
		table.getColumn("offset").getStyleAttributes().setBackground(lockColor);
		table.getColumn("aimCostCap").getStyleAttributes().setLocked(true);
		table.getColumn("aimCostCap").getStyleAttributes().setBackground(lockColor);
		table.getColumn("sumAimCostCap").getStyleAttributes().setLocked(true);
		table.getColumn("sumAimCostCap").getStyleAttributes().setBackground(lockColor);
		table.getColumn("buildPart").getStyleAttributes().setLocked(true);
		table.getColumn("buildPart").getStyleAttributes().setBackground(lockColor);
		table.getColumn("sellPart").getStyleAttributes().setLocked(true);
		table.getColumn("sellPart").getStyleAttributes().setBackground(lockColor);

		table.getColumn("workload").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("sumAimCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("offset").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("aimCostCap").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("sumAimCostCap").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("buildPart").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("sellPart").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		table.getColumn("workload").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(8));
		table.getColumn("aimCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("sumAimCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("aimCostCap").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(4));
		table.getColumn("sumAimCostCap").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(4));
		table.getColumn("offset").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("buildPart").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		table.getColumn("sellPart").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("workload").setEditor(numberEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("unit").setEditor(txtEditor);

		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(8);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("price").setEditor(numberEditor);

		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("aimCost").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);
		table.getScriptManager().setAutoRun(true);
		table.setUserObject(null);
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	private void fillText() throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		String selectObjId = this.getSelectObjId();
		if(selectObjId==null){
			return;
		}
//		String[] projectIds = null;
//		if (node.getUserObject() instanceof CurProjectInfo) {
//			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
//			// projectIds = FDCHelper.getProjectLeafIds(project);
//			projectIds = new String[] { project.getId().toString() };
//		} else {
//			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
//			if (oui.getUnit() == null) {
//				return;
//			}
//			FullOrgUnitInfo info = oui.getUnit();
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			view.setFilter(filter);
//			String[] leafIds = FDCHelper.getFullOrgUnitLeafIds(info);
//			if (leafIds != null && leafIds.length != 0) {
//				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit", FDCHelper.getSetByArray(leafIds), CompareType.INCLUDE));
//				filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
//				CurProjectCollection projects = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
//				projectIds = new String[projects.size()];
//				for (int i = 0; i < projectIds.length; i++) {
//					projectIds[i] = projects.get(i).getId().toString();
//				}
//			}
//		}
		BigDecimal buildArea = FDCHelper.getApportionValue(selectObjId, ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
		this.txtSumArea.setText(buildArea.toString());
		handler.buildArea = buildArea;
		BigDecimal sellArea = FDCHelper.getApportionValue(selectObjId, ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
		this.txtSellArea.setText(sellArea.toString());
		handler.sellArea = sellArea;
		if (this.handler.aimCost.getCreator() != null) {
			this.txtCreator.setText(this.handler.aimCost.getCreator().getName());
		} else {
			this.txtCreator.setText(null);
		}
		if (this.handler.aimCost.getRecenseDate() != null) {
			this.txtCreateTime.setText(formatDay.format(this.handler.aimCost.getRecenseDate()));
		} else {
			this.txtCreateTime.setText(null);
		}
		if (this.handler.aimCost.getAuditor() != null) {
			this.txtAuditor.setText(this.handler.aimCost.getAuditor().getName());
		} else {
			this.txtAuditor.setText(null);
		}
		if (this.handler.aimCost.getAuditDate() != null) {
			this.txtAuditDate.setText(formatDay.format(this.handler.aimCost.getAuditDate()));
		} else {
			this.txtAuditDate.setText(null);
		}
		this.txtVersionNumber.setText(this.handler.aimCost.getVersionNumber());
		this.txtVersionName.setText(this.handler.aimCost.getVersionName());
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	private void setVersionButton() {
		String curShowVersion = handler.aimCost.getVersionNumber();
		if (curShowVersion.equals(handler.versionHandler.getLastVersion())) {
			this.actionNextVersion.setEnabled(false);
			this.actionLastVersion.setEnabled(false);
			if (currentOrg != null && currentOrg.isIsBizUnit()) {
				this.actionSubmit.setEnabled(true);
				if (handler.aimCost.getAuditor() == null) {
					if (handler.aimCost.getId() != null) {
						this.actionAudit.setEnabled(true);
					}
					this.actionUnAudit.setEnabled(false);
				} else {
					this.actionAudit.setEnabled(false);
					this.actionUnAudit.setEnabled(true);
				}

			}
		} else {
			this.actionNextVersion.setEnabled(true);
			this.actionLastVersion.setEnabled(true);
			this.actionSubmit.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		if (curShowVersion.equals(handler.versionHandler.getFirstVersion())) {
			this.actionPreVersion.setEnabled(false);
			this.actionFirstVersion.setEnabled(false);
			if ((handler.aimCost.getCostEntry() == null || handler.aimCost.getCostEntry().size() == 0)) {
				this.actionRecense.setEnabled(false);
			} else {
				this.actionRecense.setEnabled(true);
			}
		} else {
			this.actionPreVersion.setEnabled(true);
			this.actionFirstVersion.setEnabled(true);
		}
		this.setAmountUnit(AmountUnitEnum.yuan);
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		// this.actionAttachment.setVisible(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		AimCostInfo aimCost = (AimCostInfo) ((Map) this.getUIContext().get(
				"DATAOBJECTS")).get("billInfo");
		if (aimCost != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain
					.getModel().getRoot();
			DefaultKingdeeTreeNode node = findNode(root, aimCost
					.getOrgOrProId());
			if (node != null) {
				this.treeMain.setSelectionNode(node);
				this.pnlMain.setDividerLocation(0);
				this.treeMain.setVisible(false);
			}
		}
		this.btnRefresh.setVisible(true);
		this.btnRefresh.setEnabled(true);
		this.btnImportData.setIcon(EASResource.getIcon("imgTbtn_input"));
		//
//		String selectObjId = getSelectObjId();
//		handler = new DBAimCostHandler(selectObjId, handler.aimCost.getVersionNumber());
//		this.setVersionButton();
//		fillText();
//		fillTable();
	}

	private void initControl() {
		this.menuItemImportData.setVisible(true);
		this.menuItemImportData.setEnabled(true);
		this.btnRefresh.setVisible(true);
		this.btnRefresh.setEnabled(true);
		// this.menuBiz.setVisible(true);
		// this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionView.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		if (currentOrg != null) {
			if (!currentOrg.isIsBizUnit()) {
				// this.actionSubmit.setEnabled(false);
				// this.actionAddRow.setEnabled(false);
				// this.actionDeleteRow.setEnabled(false);
				// this.actionRecense.setEnabled(false);
				// this.actionExpression.setEnabled(false);
				// this.actionRevert.setEnabled(false);
				// this.actionAudit.setEnabled(false);
				// this.actionUnAudit.setEnabled(false);
//				this.menuItemImportData.setEnabled(false);
			}
		} else {
//			this.menuItemImportData.setEnabled(false);
		}
		// this.txtVersionName.setEnabled(false);
		// List unitList = (List) AmountUnitEnum.getEnumList();
		// for (int i = 0; i < unitList.size(); i++) {
		// final AmountUnitEnum unitEnum = (AmountUnitEnum) unitList.get(i);
		// KDMenuItem menuItem = new KDMenuItem(unitEnum.getAlias());
		// menuItem.setAction(new ItemAction() {
		// public void actionPerformed(ActionEvent e) {
		// setAmountUnit(unitEnum);
		// }
		// });
		// menuItem.setText(unitEnum.getAlias());
		// menuItem.setToolTipText(unitEnum.getAlias());
		// this.btnAmountUnit.addAssistMenuItem(menuItem);
		// }
		this.txtVersionName.setEnabled(false);
		List unitList = (List) AmountUnitEnum.getEnumList();
		for (int i = 0; i < unitList.size(); i++) {
			final AmountUnitEnum unitEnum = (AmountUnitEnum) unitList.get(i);
			KDMenuItem menuItem = new KDMenuItem(unitEnum.getAlias());
			menuItem.setAction(new ItemAction() {
				public void actionPerformed(ActionEvent e) {
					setAmountUnit(unitEnum);
				}
			});
			menuItem.setText(unitEnum.getAlias());
			menuItem.setToolTipText(unitEnum.getAlias());
			this.btnAmountUnit.addAssistMenuItem(menuItem);
		}
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		KDTable table = this.tblMain;
		String[] cols = new String[] { "aimCost", "buildPart", "sellPart" };
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				List sumAimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						aimRowList.add(rowAfter);
					}
					if ((CostAccountInfo) rowAfter.getCell("acctNumber").getUserObject() != null && ((CostAccountInfo) rowAfter.getCell("acctNumber").getUserObject()).isIsLeaf()) {
						sumAimRowList.add(rowAfter);
					}
				}
				for (int j = 0; j < cols.length; j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(cols[j]).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}
					row.getCell(cols[j]).setValue(aimCost);
				}
				BigDecimal aimCost = (BigDecimal) row.getCell("aimCost").getValue();
				if (proNode.isLeaf()) {
					row.getCell("sumAimCost").setValue(aimCost);
				} else {
					BigDecimal sumAimCost = FMConstants.ZERO;
					if (sumAimRowList.size() > 0) {
						for (int rowIndex = 0; rowIndex < sumAimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) sumAimRowList.get(rowIndex);
							Object value = rowAdd.getCell("sumAimCost").getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									sumAimCost = sumAimCost.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									sumAimCost = sumAimCost.add(new BigDecimal(((Integer) value).toString()));
								}
							}
						}
						row.getCell("sumAimCost").setValue(sumAimCost);
					}
				}
				
				
				
				BigDecimal sumAimCost = (BigDecimal) row.getCell("sumAimCost").getValue();

				if(sumAimCost==null){
					sumAimCost=FDCHelper.ZERO;
				}
				// 设置差额项
				row.getCell("offset").setValue(aimCost.subtract(sumAimCost));
				// 设置差额项
//				if(row.getCell("offset")!=null&&sumAimCost!=null)
//					row.getCell("offset").setValue(aimCost.subtract(sumAimCost));
			}
		}
	}

	private void setAmountUnit(AmountUnitEnum unit) {
		for (int i = 0; i < this.btnAmountUnit.getAssistButtonCount(); i++) {
			KDMenuItem assitButton = (KDMenuItem) this.btnAmountUnit.getAssitButton(i);
			if (assitButton.getText().equals(unit.getAlias())) {
				assitButton.setIcon(EASResource.getIcon("imgTbtn_affirm"));
			} else {
				assitButton.setIcon(null);
			}
		}
		if (unit.equals(AmountUnitEnum.yuan)) {
			this.tblMain.getColumn("price").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("unit").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("workload").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("aimCost").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("sumAimCost").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("aimCostCap").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("sumAimCostCap").getStyleAttributes().setHided(true);
		} else {
			this.tblMain.getColumn("price").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("unit").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("workload").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("aimCost").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("sumAimCost").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("aimCostCap").getStyleAttributes().setHided(false);
			ICell cell = this.tblMain.getHeadRow(0).getCell("aimCostCap");
			String text = (String) cell.getValue();
			if (text.indexOf("(") != -1) {
				text = text.substring(0, text.indexOf("("));
			}
			cell.setValue(text + "(" + unit.getAlias() + ")");
			this.tblMain.getColumn("sumAimCostCap").getStyleAttributes().setHided(false);
			cell = this.tblMain.getHeadRow(0).getCell("sumAimCostCap");
			text = (String) cell.getValue();
			if (text.indexOf("(") != -1) {
				text = text.substring(0, text.indexOf("("));
			}
			cell.setValue(text + "(" + unit.getAlias() + ")");
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				IRow row = tblMain.getRow(i);
				Object value = row.getCell("aimCost").getValue();
				if (value != null) {
					row.getCell("aimCostCap").setValue(((BigDecimal) value).divide(new BigDecimal(unit.getValue()), 4, BigDecimal.ROUND_HALF_UP));
				}
				value = row.getCell("sumAimCost").getValue();
				if (value != null) {
					row.getCell("sumAimCostCap").setValue(((BigDecimal) value).divide(new BigDecimal(unit.getValue()), 4, BigDecimal.ROUND_HALF_UP));
				}

			}
		}

	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node, String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode((DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddRow_actionPerformed(e);
		if (this.tblMain.getRowCount() == 0) {
			return;
		}
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			this.tblMain.getSelectManager().set(this.tblMain.getRowCount() - 1, 0);
			index = this.tblMain.getRowCount() - 1;
		}
		IRow selectRow = this.tblMain.getRow(index);
		if (selectRow.getUserObject() == null) {
			CostAccountInfo acct = (CostAccountInfo) selectRow.getCell("acctNumber").getUserObject();
			if (acct.isIsLeaf()) {
				IRow row = this.tblMain.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel() + 1);
				CostEntryInfo info = new CostEntryInfo();
				info.setCostAccount(acct);
				row.setUserObject(info);
				this.tblMain.setUserObject("addRow");
			} else {
				this.setMessageText(DBAimCostHandler.getResource("NotLeafAcct"));
				this.showMessage();
			}
		} else {
			CostEntryInfo infoUp = (CostEntryInfo) selectRow.getUserObject();
			IRow row = this.tblMain.addRow(index + 1);
			row.setTreeLevel(selectRow.getTreeLevel());
			CostEntryInfo info = new CostEntryInfo();
			info.setCostAccount(infoUp.getCostAccount());
			row.setUserObject(info);
			this.tblMain.setUserObject("addRow");
		}

	}

	public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		super.actionDeleteRow_actionPerformed(e);
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow selectRow = this.tblMain.getRow(index);
		if (selectRow.getUserObject() == null) {
			return;
		} else {
			this.tblMain.removeRow(selectRow.getRowIndex());
			this.tblMain.setUserObject("delteRow");
			setUnionData();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (handler.aimCost.getAuditor() != null) {
			MsgBox.showError(DBAimCostHandler.getResource("HasAudit"));
			return;
		}
		// 校验不通过,直接返回
		if (!this.verify()) {
			return;
		}
		// 没有修改直接返回
		if (!handler.isEditedTable(this.tblMain)) {
			return;
		}
		// 修改老版本数据,提示
		if (handler.aimCost.getVersionNumber().equals(handler.versionHandler.getLastVersion())) {
			this.handler.submitTableData(this.tblMain);
			refresh();
		} else {
			if (MsgBox.showConfirm2(this, DBAimCostHandler.getResource("OldVersionNoSubmit")) == MsgBox.YES) {
				this.actionRecense_actionPerformed(null);
			}
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnRecense.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.btnFirstVersion.setIcon(EASResource.getIcon("imgTbtn_first"));
		this.btnPreVersion.setIcon(EASResource.getIcon("imgTbtn_previous"));
		this.btnNextVersion.setIcon(EASResource.getIcon("imgTbtn_next"));
		this.btnLastVersion.setIcon(EASResource.getIcon("imgTbtn_last"));
		this.btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnExpression.setIcon(EASResource.getIcon("imgTbtn_assistantaccountdetail"));
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.btnVersionInfo.setIcon(EASResource.getIcon("imgTbtn_particular"));
		this.btnApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		this.btnAmountUnit.setIcon(EASResource.getIcon("imgTbtn_unit"));
		setButtonDefaultStyl(this.btnFirstVersion);
		setButtonDefaultStyl(this.btnPreVersion);
		setButtonDefaultStyl(this.btnNextVersion);
		setButtonDefaultStyl(this.btnLastVersion);
		this.menuItemFirstVersion.setIcon(EASResource.getIcon("imgTbtn_first"));
		this.menuItemPreVersion.setIcon(EASResource.getIcon("imgTbtn_previous"));
		this.menuItemNextVersion.setIcon(EASResource.getIcon("imgTbtn_next"));
		this.menuItemLastVersion.setIcon(EASResource.getIcon("imgTbtn_last"));
		this.menuItemAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.menuItemDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.menuItemApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemRecense.setIcon(EASResource.getIcon("imgTbtn_review"));
		this.menuItemExpression.setIcon(EASResource.getIcon("imgTbtn_assistantaccountdetail"));
		this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.menuItemVersionInfo.setIcon(EASResource.getIcon("imgTbtn_particular"));
	}

	public boolean verify() {
		int count = 0;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				// if (row.getCell("acctName").getValue() == null) {
				// this.setMessageText(DBAimCostHandler
				// .getResource("AcctNameNull"));
				// this.showMessage();
				// tblMain.getSelectManager().select(0, 0);
				// tblMain.getSelectManager().select(row.getRowIndex(), 1);
				// return false;
				// }
				if (row.getCell("aimCost").getValue() == null) {
					this.setMessageText(DBAimCostHandler.getResource("CostAmountNull"));
					this.showMessage();
					tblMain.getSelectManager().select(0, 0);
					tblMain.getSelectManager().select(row.getRowIndex(), 5);
					return false;
				} else {
					BigDecimal value = (BigDecimal) row.getCell("aimCost").getValue();
					if (value.compareTo(FDCHelper.ZERO) == 0) {
						this.setMessageText(DBAimCostHandler.getResource("CostAmountNull"));
						this.showMessage();
						tblMain.getSelectManager().select(0, 0);
						tblMain.getSelectManager().select(row.getRowIndex(), 5);
						return false;
					}
					if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
						this.setMessageText(DBAimCostHandler.getResource("MaxValue"));
						this.showMessage();
						tblMain.getSelectManager().select(0, 0);
						tblMain.getSelectManager().select(row.getRowIndex(), 5);
						return false;
					}
				}
				count++;
			}
		}
		if (count == 0) {
			this.setMessageText(DBAimCostHandler.getResource("NoDetailData"));
			this.showMessage();
			return false;
		}
		return true;
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	public void actionNextVersion_actionPerformed(ActionEvent e) throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		String nextVersion = AimCostVersionHandler.getNextVersion(this.handler.aimCost.getVersionNumber());
		handler = new DBAimCostHandler(selectObjId, nextVersion);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionPreVersion_actionPerformed(ActionEvent e) throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		String preVersion = AimCostVersionHandler.getPreVersion(this.handler.aimCost.getVersionNumber());
		handler = new DBAimCostHandler(selectObjId, preVersion);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionRecense_actionPerformed(ActionEvent e) throws Exception {
		// 没有修改不必修订
		if (!handler.isEditedTable(this.tblMain)) {
			MsgBox.showInfo(DBAimCostHandler.getResource("NoChangeNoRecense"));
			return;
		}
		if (!this.verify()) {
			this.abort();
		}
		if (this.handler.recenseTableDate(this.tblMain)) {
			refresh();
		}
	}

	private void refresh() throws Exception, BOSException, SQLException {
		String selectObjId = getSelectObjId();
		handler = new DBAimCostHandler(selectObjId, null);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	private void fillTable() throws Exception {
		handler.fillTable(this.tblMain);
		this.setUnionData();
	}

	public void actionExpression_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		int colIndex = this.tblMain.getSelectManager().getActiveColumnIndex();
		if (rowIndex == -1) {
			return;
		}
		if (this.tblMain.getColumn(colIndex).getEditor() != null && this.tblMain.getColumn(colIndex).getEditor().getComponent() instanceof KDFormattedTextField) {
			ICell cell = this.tblMain.getRow(rowIndex).getCell(colIndex);
			if (cell.getStyleAttributes().isLocked()) {
				return;
			}
			String expression = (String) cell.getUserObject();
			if (expression == null) {
				if (cell.getValue() != null && cell.getValue() instanceof BigDecimal) {
					expression = cell.getValue().toString();
				}
			}
			String re = ExpressionUI.showExpressionUI(expression);
			if (re == null || re.length() == 0) {
				return;
			}
			BigDecimal result = null;
			try {
				result = new BigDecimal(FDCHelper.getScriptResult(re).toString());
			} catch (Exception e1) {
				e1.printStackTrace();
				MsgBox.showError(DBAimCostHandler.getResource("ExpressionErrer"));
			}
			if (result != null) {
				cell.setUserObject(re);
				cell.setValue(result);
				KDTEditEvent editEvent = new KDTEditEvent(cell);
				editEvent.setRowIndex(rowIndex);
				editEvent.setColIndex(colIndex);
				this.tblMain_editStopped(editEvent);
			}
		}
	}

	protected void tblMain_editStopping(KDTEditEvent e) throws Exception {
		super.tblMain_editStopping(e);

	}

	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.tblMain_editStopped(e);
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblMain.getRow(rowIndex);
		if (tblMain.getColumnKey(columnIndex).equals("workload") || tblMain.getColumnKey(columnIndex).equals("price")) {
			BigDecimal workload = (BigDecimal) row.getCell("workload").getValue();
			BigDecimal price = (BigDecimal) row.getCell("price").getValue();
			if (workload == null) {
				workload = FDCHelper.ZERO;
			}
			if (price == null) {
				price = FDCHelper.ZERO;
			}
			if (workload.compareTo(FDCHelper.ZERO) == 0 && price.compareTo(FDCHelper.ZERO) == 0) {
				row.getCell("aimCost").getStyleAttributes().setLocked(false);
				row.getCell("workload").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workload.multiply(price);
				row.getCell("aimCost").setValue(aimCost);
				row.getCell("aimCost").getStyleAttributes().setLocked(true);
				if (aimCost != null) {
					if (handler.buildArea != null && handler.buildArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal buildPart = aimCost.divide(handler.buildArea, BigDecimal.ROUND_HALF_UP);
						row.getCell("buildPart").setValue(buildPart);
					}
					if (handler.sellArea != null && handler.sellArea.compareTo(FDCHelper.ZERO) != 0) {
						BigDecimal sellPart = aimCost.divide(handler.sellArea, BigDecimal.ROUND_HALF_UP);
						row.getCell("sellPart").setValue(sellPart);
					}
				} else {
					row.getCell("buildPart").setValue(null);
					row.getCell("sellPart").setValue(null);
				}
				setUnionData();
			}
		} else if (tblMain.getColumnKey(columnIndex).equals("aimCost")) {
			BigDecimal aimCost = (BigDecimal) row.getCell("aimCost").getValue();
			if (aimCost != null) {
				if (handler.buildArea != null && handler.buildArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal buildPart = aimCost.divide(handler.buildArea, BigDecimal.ROUND_HALF_UP);
					row.getCell("buildPart").setValue(buildPart);
				}
				if (handler.sellArea != null && handler.sellArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal sellPart = aimCost.divide(handler.sellArea, BigDecimal.ROUND_HALF_UP);
					row.getCell("sellPart").setValue(sellPart);
				}
			} else {
				row.getCell("buildPart").setValue(null);
				row.getCell("sellPart").setValue(null);
			}
			setUnionData();
		}
	}

	public void actionFirstVersion_actionPerformed(ActionEvent e) throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		handler = new DBAimCostHandler(selectObjId, handler.versionHandler.getFirstVersion());
		this.setVersionButton();
		fillText();
		fillTable();
	}

	private void verifySaved(TreePath treePath) throws EASBizException, BOSException {
		this.tblMain.getSelectManager().select(0, 0);
		if (handler == null) {
			return;
		}
		if (handler.isEditedTable(this.tblMain)) {
			if (MsgBox.showConfirm2(this, DBAimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				if (!this.verify()) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					this.abort();
				}
				if (handler.aimCost.getAuditor() != null) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					MsgBox.showError(DBAimCostHandler.getResource("HasAudit"));
					this.abort();
				}
				if (handler.aimCost.getVersionNumber().equals(handler.versionHandler.getLastVersion())) {
					this.handler.submitTableData(this.tblMain);
				} else {
					if (MsgBox.showConfirm2(this, DBAimCostHandler.getResource("OldVersionNoSubmit")) == MsgBox.YES) {
						this.handler.recenseTableDate(this.tblMain);
					}
				}
			}
		}
	}

	public void actionLastVersion_actionPerformed(ActionEvent e) throws Exception {
		verifySaved(null);
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		handler = new DBAimCostHandler(selectObjId, null);
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (handler.aimCost.getAuditor() != null) {
			MsgBox.showInfo(DBAimCostHandler.getResource("HasAudit"));
			return;
		}
		verifySaved(null);
		if (this.handler.aimCost.getId() != null) {
			AimCostFactory.getRemoteInstance().audit(this.handler.aimCost.getId());
			MsgBox.showInfo(DBAimCostHandler.getResource("AuditSucces"));
			refresh();
		} else {
			MsgBox.showInfo(DBAimCostHandler.getResource("NoSaveNotAudit"));
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		verifySaved(null);
		AimCostFactory.getRemoteInstance().unaudit(this.handler.aimCost.getId());
		MsgBox.showInfo(DBAimCostHandler.getResource("UnauditSucces"));
		refresh();
	}

	public void actionApportion_actionPerformed(ActionEvent e) throws Exception {
		super.actionApportion_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		handler = new DBAimCostHandler(selectObjId, handler.aimCost.getVersionNumber());
		this.setVersionButton();
		fillText();
		fillTable();
	}

	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		super.actionRevert_actionPerformed(e);
		// 没有修改直接返回
		if (!handler.isEditedTable(this.tblMain)) {
			return;
		}
		fillTable();
	}

	public void actionVersionInfo_actionPerformed(ActionEvent e) throws Exception {
		super.actionVersionInfo_actionPerformed(e);
		String curShowVersion = handler.aimCost.getVersionNumber();
		if (!curShowVersion.equals(handler.versionHandler.getLastVersion())) {
			VersionInputUI.showViewUI(handler.aimCost.getVersionNumber(), handler.aimCost.getVersionName(), handler.aimCost.getDescription(), null,null);
		} else {
			String id = null;
			if (handler.aimCost.getId() != null) {
				id = handler.aimCost.getId().toString();
			}
			//TODO 建发成本修改了方法，先传null by hpw
			Map map = VersionInputUI.showEditUI(handler.aimCost.getOrgOrProId(), id, handler.aimCost.getVersionNumber(), handler.aimCost.getVersionName(), handler.aimCost.getDescription(), null,null);
			if (map.get("isConfirm") == null) {
				return;
			}
			handler.submitVersionData((String) map.get("versionName"), (String) map.get("description"),(Date) map.get("recenseDate"));
			this.fillText();
		}
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		if (handler == null) {
			return;
		}
		if (this.handler.aimCost.getId() == null) {
			MsgBox.showInfo(DBAimCostHandler.getResource("NoSaveNotAttach"));
			return;
		}
		String boID = this.handler.aimCost.getId().toString();
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		if (boID == null) {
			return;
		}
		acm.showAttachmentListUIByBoID(boID, this);

	}

	public boolean destroyWindow() {
//		verifySaved(null);
		return super.destroyWindow();
	}

	/*
	 * 导入参数设置 jackwang 2006/11/16
	 */
	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		// 当前节点
		Hashtable hs = new Hashtable();
		String orgOrProId = this.getSelectObjId();
		hs.put("orgOrProId", orgOrProId);
		//
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo curProjectInfo = (CurProjectInfo) node.getUserObject();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("curProjProductEntries.*"));
				sic.add(new SelectorItemInfo("curProjProductEntries.curProjProEntrApporData"));
				sic.add(new SelectorItemInfo("curProjCostEntries.*"));
				CurProjectInfo paramProject = new CurProjectInfo();
				try {
					paramProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(curProjectInfo.getId().toString()), sic);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				hs.put("curProjectInfo", paramProject);
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui.getUnit() == null) {

				}
				FullOrgUnitInfo fullOrgUnitInfo = oui.getUnit();
				hs.put("fullOrgUnitInfo", fullOrgUnitInfo);
			}
		}
		// 当前最新版本号
		AimCostVersionHandler versionHandler;
		String lastVerisonNumber = "1.0";
		try {
			versionHandler = new AimCostVersionHandler(orgOrProId);
			lastVerisonNumber = versionHandler.getLastVersion();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hs.put("lastVerisonNumber", lastVerisonNumber);
		// 最新headID
		String lastFid = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", orgOrProId));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.valueOf(true)));
		// String sql = "select top 1 FID from T_Aim_AimCost " + "where
		// FOrgOrProId='" + orgOrProId + " and FIsLastVersion = 1 '" ;//+ "order
		// by to_number(FVersionNumber) desc";
		evi.setFilter(filter);
		try {
			AimCostCollection acc;
			acc = AimCostFactory.getRemoteInstance().getAimCostCollection(evi);
			if (acc != null && acc.size() != 0) {
				lastFid = acc.get(0).getId().toString();// .getString("FID");
				hs.put("lastFid", lastFid);
			}
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		// IRowSet rs;
		// try {
		// rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		// if (rs.next()) {
		// lastFid = rs.getString("FID");
		// hs.put("lastFid", lastFid);
		// }
		// } catch (BOSException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		param.setContextParam(hs);//
		String solutionName = "eas.fdc.costmanager.costdb.aimcost";
		param.solutionName = solutionName;
		param.alias = EASResource.getString(resourcePath, "AimCost");
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}

	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		// super.actionImportData_actionPerformed(e);
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		if (getImportParam() != null) {
			task.invoke(getImportParam(), DatataskMode.ImpMode, true, true);
		}
		actionRefresh_actionPerformed(e);
	}
}