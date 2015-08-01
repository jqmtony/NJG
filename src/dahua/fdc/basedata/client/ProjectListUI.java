/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:工程项目叙事簿界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class ProjectListUI extends AbstractProjectListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectListUI.class);

	// private EntityViewInfo eviTemp = null;
	private FullOrgUnitInfo orgTemp = null;
	//明细可研项目
	private boolean isLeafNotDevPrj = false;
	/**
	 * output class constructor
	 */
	public ProjectListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		this.tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						// do something
						String strTemp = "";
						//2008-11-13 更改i<tblMain.getRowCount();
//						for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
						for (int i = 0; i < tblMain.getRowCount(); i++){
//							Object value = tblMain.getRow(i).getCell(1).getValue();
							//getCell(1)替换为getCell("longNumber") 避免更改顺序后出错
							Object value = tblMain.getRow(i).getCell("longNumber").getValue();
							if(value==null){
								continue;
							}
							strTemp = value
									.toString();
							strTemp = strTemp.replace('!', '.');
							tblMain.getRow(i).getCell(1).setValue(strTemp);
						}
					}
				});
		super.onLoad();
		//update by david_yang R110216-033 2011.05.09
//		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
//		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
//		sqlBuilder.appendSql("select fid from T_FDC_CurProject where fisleaf=0 and fsortno!=0 and fcontrolunitid=?");
//		sqlBuilder.addParam(cuId);
//		IRowSet rowSet = sqlBuilder.executeQuery();
//		if(rowSet.size()!=0){
//			CurProjectFactory.getRemoteInstance().updateSortNo(cuId);
//		}
		FDCTableHelper.setColumnMoveable(tblMain, true);
		buildProjectTree();
		actionEnabled.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_staruse"));
		actionDisEnabled.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_forbid"));
		// orgTemp =
		// FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new
		// ObjectUuidPK(OrgConstants.DEF_CU_ID));;

		if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext
				.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
				|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext
						.getSysContext().getCurrentCostUnit().isIsBizUnit())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.actionEnabled.setVisible(true);
			this.actionDisEnabled.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			this.btnVersionRedact.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.actionEnabled.setVisible(false);
			this.actionDisEnabled.setVisible(false);
			this.btnVersionRedact.setEnabled(false);
		}
		this.treeMain.setSelectionRow(0);

		actionIdxRefresh.setEnabled(true);
		btnIdxRefresh.setEnabled(true);

		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		menuItemProjectType.setVisible(false);
		//设置可以保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.actionVersionRedact.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_historyedition"));
		btnIdxRefresh.setIcon(FDCClientHelper.ICON_AUTOCOUNT);
		menuItemProjectType.setIcon(FDCClientHelper.ICON_AUTOCOUNT);
		actionIdxRefresh.setEnabled(false);
		actionIdxRefresh.setVisible(false);
		actionVersionRedact.setEnabled(false);
		actionVersionRedact.setVisible(false);
		this.btnItemImport.setIcon(EASResource.getIcon("imgTbtn_input"));
	}

	/**
	 * 初始化时构造Tree，几乎不用重载
	 */
	protected void initTree() throws Exception {

	}

	private void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(true);
		projectTreeBuilder.setDevPrjFilter(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);

		// tblMain.removeRows();// 触发新查询
		// // for (int i = 0; i < tblMain.getRowCount(); i++) {// 定位节点相应的叙事簿明细
		// // if
		// (projectInfo.getId().toString().equals(tblMain.getRow(i).getCell("id").getValue().toString()))
		// {
		// // tblMain.getSelectManager().select(i, 2);
		// // break;
		// // }
		// // }
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// 添加双击响应事件,方便查看 add by jianxing_zhou 2007-8-27
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			}
			ActionEvent evt = new ActionEvent(btnView, 0, "Double Clicked");
			ItemAction actView = getActionFromActionEvent(evt);
			actView.actionPerformed(evt);
		}

		// super.tblMain_tableClicked(e);
		// if (getSelectedKeyValue() == null)
		// return;
		// TreeBaseInfo selectRow = (TreeBaseInfo)
		// this.getBizInterface().getValue(new
		// ObjectUuidPK(getSelectedKeyValue()));
		// // 清除过滤。
		// String fullLN = selectRow.getLongNumber();
		// treeMain.setSelectionRow(0);
		// DefaultKingdeeTreeNode selectNode = getSelectedTreeNode1();
		// if (!(selectNode.getUserObject() instanceof TreeBaseInfo)) {
		// selectNode = getSelectedTreeNode1();
		// }
		// getSelectNode(fullLN, selectNode);
	}

	public DefaultKingdeeTreeNode getSelectedTreeNode1() {
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// treeMain.getLastSelectedPathComponent();
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			int i = this.tblMain.getSelectManager().getActiveRowIndex();
			if (this.tblMain.getRow(i) != null
					&& this.tblMain.getRow(i).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(
						this.tblMain.getSelectManager().getActiveRowIndex())
						.getCell("isEnabled").getValue()).booleanValue();
				// 随着每一行的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);
				// //刷新编辑按钮状态
				// if
				// (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("cu.id")
				// != null) {
				//
				// boolean isEnabled =
				// this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("cu.id").getValue().toString().equals(
				// SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
				//
				// changeEditEnabeld(isEnabled);
				// } else {
				// disabledEdit();
				// }
			}
		} else {
			disabledWBT();
		}
	}

	/**
	 * 随着每一行的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		// if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId())){
		this.actionEnabled.setEnabled(!isEnabled);
		this.actionDisEnabled.setEnabled(isEnabled);
		// }

	}

	/**
	 * 把启用/禁止按钮disabled
	 */
	private void disabledWBT() {
		// if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId())){
		this.actionEnabled.setEnabled(false);
		this.actionDisEnabled.setEnabled(false);
		// }
	}

	// 递归调用选中节点。
	private void getSelectNode(String fullLN, DefaultKingdeeTreeNode selectNode)
			throws Exception {
		if (selectNode.getChildCount() == 0) {
			return;
		}
		// if(selectNode.getChildCount()!=null){
		for (int j = 0; j < selectNode.getChildCount(); j++) {
			// if(selectNode.getChildAt(j)!=null){
			DefaultKingdeeTreeNode tempNode = (DefaultKingdeeTreeNode) selectNode
					.getChildAt(j);
			if (tempNode.getUserObject() instanceof OrgStructureInfo) {
				getSelectNode(fullLN, tempNode);
			} else {
				TreeBaseInfo tempTree = (TreeBaseInfo) tempNode.getUserObject();
				// if (StringUtility.isMatch(tempTree.getLongNumber(), fullLN,
				// true)) {
				if (tempTree.getLongNumber().equals(fullLN)) {
					// TreeSelectionModel mode = new
					// DefaultTreeSelectionModel();
					// mode.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
					// treeMain.setSelectionModel(mode);
					// ((DefaultKingdeeTreeNode)tempNode.getParent()).notify();
					treeMain
							.setSelectionNode((DefaultKingdeeTreeNode) tempNode);

					break;
				} else {
					getSelectNode(fullLN, tempNode);
				}
			}
			// }
		}
		// }
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("startDate"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("sortNo"));
		sic.add(new SelectorItemInfo("landDeveloper.name"));
		sic.add(new SelectorItemInfo("fullOrgUnit.*"));
		sic.add(new SelectorItemInfo("CU.*"));
		sic.add(new SelectorItemInfo("parent.name"));
		sic.add(new SelectorItemInfo("parent.id"));
		return sic;
	}

	/**
	 * output treeMain_mouseClicked method
	 */
	protected void treeMain_mouseClicked(java.awt.event.MouseEvent e)
			throws Exception {
		lastSelectNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
	}

	// 树形的CU过滤处理。
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter = getDefaultFilterForQuery();
		return filter;
	}

	// 默认进行当前CU的过滤。子类可重载。
	protected FilterInfo getDefaultFilterForQuery() {
		// FilterInfo filter = new FilterInfo();
		//
		// if (isIgnoreCUFilter()) {
		// return filter;
		// }
		// if (SysContext.getSysContext().getCurrentCtrlUnit() == null) {
		// return filter;
		// }
		// CostCenterOrgUnitInfo info =
		// SysContext.getSysContext().getCurrentCostUnit();
		// String longNumber = info.getLongNumber().toString();
		// HashSet lnUps = new HashSet();
		// lnUps.add(info.getId().toString());
		// try {
		// FullOrgUnitCollection fullOrgUnitCollection =
		// FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection("select
		// id where longNumber like '" + longNumber + "!%'");
		// for (int i = 0; i < fullOrgUnitCollection.size(); i++) {
		// lnUps.add(fullOrgUnitCollection.get(i).getId().toString());
		// }
		//
		// } catch (BOSException e1) {
		// handUIException(e1);
		// }
		// FilterInfo filterInfo = new FilterInfo();
		// if (lnUps.size() != 0) {
		// filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",
		// lnUps, CompareType.INCLUDE));
		// filterInfo.setMaskString(" #0 ");
		// this.mainQuery.setFilter(filterInfo);
		// }

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("fullOrgUnit.*"));
			try {
				projectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectStringPK(projectInfo.getId().toString()),
						sic);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			} 
			FullOrgUnitInfo info = projectInfo.getFullOrgUnit();
			String longNumber = projectInfo.getLongNumber();
			HashSet lnUps = new HashSet();
			lnUps.add(projectInfo.getId().toString());
			try {
				CurProjectCollection curProjectCollection = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection(
								"select id where longNumber like '"
										+ longNumber + "!%'");
				for (int i = 0; i < curProjectCollection.size(); i++) {
					lnUps.add(curProjectCollection.get(i).getId().toString());
				}
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			}
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", info.getId(),
							CompareType.EQUALS));
			if (lnUps.size() != 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
				return filterInfo;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			tblMain.getSelectManager().select(0, 0);
			if ((node.getChildCount() > 0)
					&& (((DefaultKingdeeTreeNode) node.getChildAt(0))
							.getUserObject() instanceof OrgStructureInfo)) {// 选择结点不适合新增,灰掉新增按钮
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				FullOrgUnitInfo info = oui.getUnit();
				// String longNumber = info.getLongNumber().toString();
				HashSet lnUps = new HashSet();
				genLeafNodesIdSet(node, lnUps);
				lnUps.add(info.getId().toString());
				FilterInfo filterInfo = new FilterInfo();
				if (lnUps.size() != 0) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("fullOrgUnit.id", lnUps,
									CompareType.INCLUDE));
					filterInfo.setMaskString(" #0 ");
					return filterInfo;
				}
			} else {
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				FullOrgUnitInfo info = oui.getUnit();
				String id = info.getId().toString();
				// try {
				// FullOrgUnitInfo info1 =
				// FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo("select
				// CU.id where id='" + id + "'");
				// info.setCU(info1.getCU());
				// } catch (EASBizException e1) {
				// handUIException(e1);
				// } catch (BOSException e2) {
				// handUIException(e2);
				// }
				// info.setLongNumber(oui.getLongNumber());
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(
						new FilterItemInfo("fullOrgUnit.id", id,
								CompareType.EQUALS));
				filterInfo.setMaskString(" #0 ");
				return filterInfo;
			}
		}
		return null;
	}

	private DefaultKingdeeTreeNode lastSelectNode = null;

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		// tblMain.removeRows();// 触发新查询
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			// return;
			if (lastSelectNode != null) {
				node = this.lastSelectNode;
			} else {
				return;
			}
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("fullOrgUnit.*"));
			projectInfo = CurProjectFactory.getRemoteInstance()
					.getCurProjectInfo(
							new ObjectStringPK(projectInfo.getId().toString()),
							sic);
			FullOrgUnitInfo info = projectInfo.getFullOrgUnit();
			String longNumber = projectInfo.getLongNumber();
			HashSet lnUps = new HashSet();
			lnUps.add(projectInfo.getId().toString());
			try {
				CurProjectCollection curProjectCollection = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection(
								"select id where longNumber like '"
										+ longNumber + "!%'");
				for (int i = 0; i < curProjectCollection.size(); i++) {
					lnUps.add(curProjectCollection.get(i).getId().toString());
				}
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			}
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", info.getId(),
							CompareType.EQUALS));
			if (lnUps.size() != 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
				// filterInfo.setMaskString(" #0 ");
				this.mainQuery.setFilter(filterInfo);
			}
			// execQuery();
			updateMainQueryOrder(); //add by zhiyuan_tang
			tblMain.removeRows();// 触发新查询
			for (int i = 0; i < tblMain.getRowCount(); i++) {// 定位节点相应的叙事簿明细
				if (projectInfo.getId().toString().equals(
						tblMain.getRow(i).getCell("id").getValue().toString())) {
					tblMain.getSelectManager().select(i, 2);
					break;
				}
			}
			this.btnAddNew.setEnabled(true);
			if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
				if (this.tblMain.getRow(
						this.tblMain.getSelectManager().getActiveRowIndex())
						.getCell("isEnabled") != null) {
					boolean status = ((Boolean) this.tblMain
							.getRow(
									this.tblMain.getSelectManager()
											.getActiveRowIndex()).getCell(
									"isEnabled").getValue()).booleanValue();
					// 随着每一行的isEnabled的值改变，两个WBT的状态也改变
					changeWBTEnabeld(status);
					// //刷新编辑按钮状态
				}
			} else {
				disabledWBT();
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			// TreeNode nodetmp = (TreeNode)
			// treeMain.getLastSelectedPathComponent();
			tblMain.getSelectManager().select(0, 0);
			if ((node.getChildCount() > 0)
					&& (((DefaultKingdeeTreeNode) node.getChildAt(0))
							.getUserObject() instanceof OrgStructureInfo)) {// 选择结点不适合新增,灰掉新增按钮
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				FullOrgUnitInfo info = oui.getUnit();
				// String longNumber = info.getLongNumber().toString();
				HashSet lnUps = new HashSet();
				genLeafNodesIdSet(node, lnUps);
				lnUps.add(info.getId().toString());
				// try {
				// FullOrgUnitCollection fullOrgUnitCollection =
				// FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection("select
				// * where longNumber like '" + longNumber + "!%'");
				//					
				// for (int i = 0; i < fullOrgUnitCollection.size(); i++) {
				// if(fullOrgUnitCollection.get(i).isIsCostOrgUnit()&&fullOrgUnitCollection.get(i).isIsCompanyOrgUnit()){
				// lnUps.add(fullOrgUnitCollection.get(i).getId().toString());
				// }
				// }
				// } catch (BOSException e1) {
				// handUIException(e1);
				// }
				FilterInfo filterInfo = new FilterInfo();
				if (lnUps.size() != 0) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("fullOrgUnit.id", lnUps,
									CompareType.INCLUDE));
					// filterInfo.setMaskString("(#0 or #1 or #2) AND #3");
					filterInfo.setMaskString(" #0 ");
					this.mainQuery.setFilter(filterInfo);
				}
				// eviTemp = this.mainQuery;
				// orgTemp = info;
				updateMainQueryOrder(); //add by zhiyuan_tang
				execQuery();
				if (node == node.getFirstLeaf()) {// 如果该节点已经是叶结点,允许新增
					this.btnAddNew.setEnabled(true);
				} else {// 如果该节点不是最末级叶结点,不允许新增
					this.btnAddNew.setEnabled(false);
				}
			} else {
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				FullOrgUnitInfo info = oui.getUnit();
				String id = info.getId().toString();
				// try {
				// FullOrgUnitInfo info1 =
				// FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo("select
				// CU.id where id='" + id + "'");
				// info.setCU(info1.getCU());
				// } catch (EASBizException e1) {
				// handUIException(e1);
				// } catch (BOSException e2) {
				// handUIException(e2);
				// }
				// info.setLongNumber(oui.getLongNumber());
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(
						new FilterItemInfo("fullOrgUnit.id", id,
								CompareType.EQUALS));
				filterInfo.setMaskString(" #0 ");
				this.mainQuery.setFilter(filterInfo);
				// eviTemp = this.mainQuery;
				// orgTemp = info;
				updateMainQueryOrder(); //add by zhiyuan_tang
				execQuery();
				this.btnAddNew.setEnabled(true);
			}
			disabledWBT();
		}
		if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext
				.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
				|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext
						.getSysContext().getCurrentCostUnit().isIsBizUnit())) {

		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.actionEnabled.setVisible(false);
			this.actionDisEnabled.setVisible(false);
		}
	}

	/**
	 * 
	 * 描述：生成组织树节点集合
	 * 
	 * @param node
	 * @param leafNodesIdSet
	 * @author:jackwang
	 *            <p>
	 */
	public static void genLeafNodesIdSet(DefaultKingdeeTreeNode node,
			Set leafNodesIdSet) {

		int count = node.getChildCount();
		// 如果没有下级节点，说明当前组织是实体，把当前组织id返回即可
		if (count == 0) {

			OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node)
					.getUserObject());

			String oid = orgStructureInfo.getUnit().getId().toString();
			leafNodesIdSet.add(oid);
			return;
		}
		DefaultKingdeeTreeNode treeNode = null;
		for (int i = 0; i < count; i++) {
			treeNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
			if (treeNode.getUserObject() instanceof OrgStructureInfo) {
				if (treeNode.isLeaf()) {
					String id = ((OrgStructureInfo) treeNode.getUserObject())
							.getUnit().getId().toString();
					leafNodesIdSet.add(id);
				} else {
					genLeafNodesIdSet(treeNode, leafNodesIdSet);
				}
			} else {
				OrgStructureInfo orgStructureInfo = ((OrgStructureInfo) ((DefaultKingdeeTreeNode) node)
						.getUserObject());

				String oid = orgStructureInfo.getUnit().getId().toString();
				leafNodesIdSet.add(oid);

			}
		}

	}

	/**
	 * 描述：版本修订
	 * 
	 * @author:jackwang
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractProjectListUI#actionVersionRedact_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionVersionRedact_actionPerformed(ActionEvent e)
			throws Exception {
		MsgBox.showInfo(this, "请到\"成本数据库-数据导入-指标修订\"菜单进行指标修订！");
		// if (tblMain.getSelectManager().size() == 0) {
		// // 给出提示，须指定欲修订的工程项目！
		// } else {
		// HashMap map = new HashMap();
		// map.put("Owner", this);
		// UIContext uiContext = new UIContext(this);
		// prepareUIContext(uiContext, e);//
		// ////////////////////////////////////////////////////////////////////
		// uiContext.put(UIContext.ID, getSelectedKeyValue());
		// IUIFactory uiFactory = null;
		// uiFactory =
		// UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		// // 以模态对话框方式启动
		// IUIWindow uiWindow =
		// uiFactory.create("com.kingdee.eas.fdc.basedata.client.ProjectVersionRedactUI",
		// uiContext, null, OprtState.EDIT);
		// uiWindow.show();
		// // actionRefresh_actionPerformed(e);
		// }
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
		super.checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iCurProject.enabled(pk)) {
			this.showResultMessage(EASResource.getString(
					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			updateMainQueryOrder(); //add by zhiyuan_tang
			tblMain.removeRows();// 触发新查询
			this.actionEnabled.setEnabled(false);
			this.actionDisEnabled.setEnabled(true);
		}
	}

	/**
	 * output actionDisEnabled_actionPerformed method
	 */
	public void actionDisEnabled_actionPerformed(ActionEvent e)
			throws Exception {
		super.checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iCurProject.disEnabled(pk)) {
			this.showResultMessage(EASResource.getString(
					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
					"DisEnabled_OK"));
			updateMainQueryOrder(); //add by zhiyuan_tang
			tblMain.removeRows();// 触发新查询
			this.actionDisEnabled.setEnabled(false);
			this.actionEnabled.setEnabled(true);
		}
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
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
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo info = (CurProjectInfo) node.getUserObject();
				FDCClientUtils.checkProjUsed(this, info.getId().toString());
			}
		}
		prepareUIContext(e);
		super.actionAddNew_actionPerformed(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		// add cacheService清除 by Jacky 2005-1-6
		// this.isOrderBy = false;
		CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
		// if(getSelectedKeyValue() != null)
		// {
		// CacheServiceFactory.getInstance().discardType(
		// getBizInterface().getValue(new ObjectUuidPK(getSelectedKeyValue())).
		// getBOSType());
		// CacheServiceFactory.getInstance().discardType(new
		// BOSObjectType(getSelectedKeyValue().substring(36,44)));
		// }
		updateMainQueryOrder(); //add by zhiyuan_tang
		execQuery();
		buildProjectTree();
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	public void actionSetProjectType_actionPerformed(ActionEvent e) throws Exception
	{
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		try {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create("com.kingdee.eas.fdc.basedata.client.ProjectTypeSetUI", uiContext, null, OprtState.EDIT);
		} catch (UIException e2) {
			logger.error(e2.getMessage(), e2);
			this.handUIExceptionAndAbort(e2);
		}
		
		if(uiWindow!=null)
			uiWindow.show();
		if(uiWindow!= null && uiWindow.getUIObject() != null)
		{
			ProjectTypeSetUI ui = (ProjectTypeSetUI)uiWindow.getUIObject();
			if(ui.isComfirm()){
				this.refreshList();
			}
		}
	}
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

		// String selectedKeyValue = getSelectedKeyValue();
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("curProject.id",
		// selectedKeyValue));
		// view.setFilter(filter);
		// view.getSelector().add("costCenterOU.id");
		//		
		// ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection =
		// ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		//		
		// if(projectWithCostCenterOUCollection != null &&
		// projectWithCostCenterOUCollection.size() > 0) {
		// ProjectWithCostCenterOUInfo info =
		// projectWithCostCenterOUCollection.get(0);
		// CostCenterOrgUnitInfo currentCostUnit =
		// SysContext.getSysContext().getCurrentCostUnit();
		// if(currentCostUnit != null) {
		// if(currentCostUnit.getId().equals(info.getCostCenterOU().getId())) {
		// MsgBox.showWarning(this, "");
		// }
		// }
		//			
		// MsgBox.showWarning(this, "");
		// }
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select 1 from t_fdc_curproject where fisleaf = 1 and fisdevprj = 0 and ");
		builder.appendParam("fid", idList.toArray());
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			isLeafNotDevPrj = true;
		}
		this.lastSelectNode = null;
		super.actionRemove_actionPerformed(e);

	}

	/**
     * 删除确认对话框的默认实现，继承类可以重载
     * 重载ListUI方法，删除明细可研项目时给出提示
     * @return
     */
    protected boolean confirmRemove() {
		if (isLeafNotDevPrj) {
			int v = FDCMsgBox.showConfirm2New(this, "将删除可研项目及其测算信息，请确认是否删除?");
			if (v == FDCMsgBox.NO) {
				SysUtil.abort();
			}
		} else {
			if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Confirm_Delete")))) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
    

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain	.getLastSelectedPathComponent();
		if(node != null){
			if (node.getUserObject() != null) {
				if (node.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo info = (CurProjectInfo) node.getUserObject();
					try {
						if (info.getProjectType() != null && info.getProjectType().getId() != null) {
							info.setProjectType(ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(
									new ObjectUuidPK(info.getProjectType().getId())));
						}
					} catch (Exception exc) {
						handUIExceptionAndAbort(exc);
					}
					hs.put("parentInfo", info);
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					// 选择节点不是明细组织时退出
					if ((node.getChildCount() > 0)
							&& (((DefaultKingdeeTreeNode) node.getChildAt(0)).getUserObject() instanceof OrgStructureInfo)) {
						FDCMsgBox.showWarning(this, "选择组织不是明细组织。");
						SysUtil.abort();
					}
					// 选择节点为空或其所在组织为空时退出
					OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
					if (oui == null || oui.getUnit() == null){
						FDCMsgBox.showWarning(this, "选择组织结构的组织单元为空。");
						SysUtil.abort();
					}
					hs.put("parentInfo", oui.getUnit());
				}
			}
		}
		try {
			hs.put("projectStatus", ProjectStatusInfo.getProjectStatueById(ProjectStatusInfo.notGetID));
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		param.setContextParam(hs);//
		param.solutionName = "eas.fdc.basedata.REProject";
		param.alias = "工程项目";
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}
	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProjectEditUI.class.getName();
	}

	public void actionIdxRefresh_actionPerformed(ActionEvent e)
			throws Exception {
		String warning = EASResource.getString(
				"com.kingdee.eas.fdc.basedata.client.BasedataResource",
				"IdxRefreshWarning");
		int i = MsgBox.showConfirm3a(this, warning, "该操作不可撤销,请慎用!");

		if (i == MsgBox.YES) {
			idxRefresh();
		}
	}

	/**
	 * 指标刷新
	 * 
	 * @param projId
	 */
	private void idxRefresh() throws Exception {
		checkSelected();

		String id = getSelectedKeyValue();

		int rtnCode = CurProjectFactory.getRemoteInstance().idxRefresh(id);

		if (rtnCode == 0) {
			FDCClientUtils.showOprtOK(this);
		} else {
			FDCClientUtils.showOprtFailed(this);
		}

	}

	public String getUITitle() {
		return super.getUITitle();
//		return EASResource.getString(
//				"com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Project");
	}

	protected boolean isShowAttachmentAction() {
		return true;
	}

	protected void prepareUIContext(ActionEvent e) {
		//如果当前焦点在左边树上，则取树上当前选中节点为操作对象
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		ItemAction act = getActionFromActionEvent(e);

		if (node == null) {
			return;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo info = (CurProjectInfo) node.getUserObject();

				if (!act.equals(actionView)) {
					try{
						if(info.getProjectType()!=null&&info.getProjectType().getId()!=null){
							info.setProjectType(ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(info.getProjectType().getId())));
						}
					}catch(Exception exc){
						handUIExceptionAndAbort(exc);
					}
				}
				this.getUIContext().put("parentInfo", info);
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				//选择结点不是明细组织时退出
				if ((node.getChildCount() > 0)
						&& (((DefaultKingdeeTreeNode) node.getChildAt(0))
								.getUserObject() instanceof OrgStructureInfo)) {
					return;
				}
				//选择结点为空或其所在组织为空时退出
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				FullOrgUnitInfo info = oui.getUnit();
				this.getUIContext().put("parentInfo", info);
			}
		} else {
			return;
		}
	}
	
	protected String getEditUIModal() {
		// return UIFactoryName.MODEL;
		// return UIFactoryName.NEWWIN;
		// 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.MODEL;
		}
	}
	
	/**
	 * R101014-334: 根据参数"工程项目树和序时簿列表按照开发顺序排序"来设置mainQuery的排序字段
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void updateMainQueryOrder() throws BOSException, EASBizException {
		String paramValue = ParamControlFactory.getRemoteInstance().getParamValue(null, FDCConstants.FDC_PARAM_PROJECTTREESORTBYSORTNO);
		if (Boolean.valueOf(paramValue).booleanValue()) {
			SorterItemCollection sorter = new SorterItemCollection();
			sorter.add(new SorterItemInfo("sortNo"));
			this.mainQuery.getSorter().clear();
			this.mainQuery.setSorter(sorter);
		}
	}
	
	/**
     * 定位字段有：工程项目名称,工程简码 - R130314-0089
     * @author zhaoqin
     * @date 2013/12/2
     */
	protected String[] getLocateNames()
    {
		return new String[] {IFWEntityStruct.dataBase_Name, IFWEntityStruct.tree_LongNumber};
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