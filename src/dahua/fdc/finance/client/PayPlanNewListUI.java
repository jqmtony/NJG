/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.uiframe.client.BodyUI;
import com.kingdee.eas.base.uiframe.client.NewWinMainUI;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.client.util.PayPlanClientUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.mm.control.client.TableCellComparator;

public class PayPlanNewListUI extends AbstractPayPlanNewListUI {
	private static final String TREE_SELECTED_OBJ = "treeSelectedObj";
	private static final String ID = "ID";
	private static final Logger logger = CoreUIObject.getLogger(PayPlanNewListUI.class);
	private final String LONGNUMBER = "longNumber";// 长编码
	private final String HEADNUMBER = "headNumber";// 长级长编码
	private final String AMOUNT = "amount";
	// 获取有权限的组织
	protected Set authorizedOrgs = null;
	
	private String projectID = null;


	public PayPlanNewListUI() throws Exception {
		super();
	    this.kdtEntries.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
			public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
				try {
					kdtEntries_tableSelectChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
				}
			}
		});
	}

	public void onLoad() throws Exception {
		showOnlyOneTab();
		super.onLoad();
		setOprtState(OprtState.VIEW);
		initTable();
		buildProjectTree();
		treeSelectionListeners = treeMain.getTreeSelectionListeners();
		
	}
	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		
	}
	protected IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.programming.ProgrammingInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingInfo();

		return objectValue;
	}
	/**
	 * 
	 */
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		kdtEntries.setEditable(false);
	}






	/**
	 * 设置分录失去焦点及分录按钮是否可用
	 */
	private void setKDTableLostFocus() {
		kdtEntries.getEditManager().stopEditing();
		kdtEntries.getSelectManager().remove();
		kdtEntries.getSelectManager().setActiveRowIndex(-1);
	}


	/**
	 * 加载时对表格进行设置
	 */
	private void initTable() {
		kdtEntries.getColumn("id").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("level").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isCiting").getStyleAttributes().setLocked(true);
		cellToFormattedText(kdtEntries, AMOUNT);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(1024);
		kdtEntries.getColumn("remark").setEditor(cellEditor);

		kdtEntries.getColumn(AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

	}

	private void cellToFormattedText(KDTable table, String column) {
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor.getComponent();
		kdftf.setDataType(1);
		kdftf.setPrecision(2);
		kdftf.setSupportedEmpty(true);
		table.getColumn(column).setEditor(cellEditor);
	}

	public void loadFields() {
		super.loadFields();
		// 加载数据时按长编码排序
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		// 单元格编码模式
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		setTableRenderer();
	}

	/**
	 * 设置金额字段显示格式
	 */
	private void setTableRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					if ("0.00".equals(o.toString())) {
						return "0";
					}
				} else {
					return "0";
				}
				return o.toString();
			}
		});
		kdtEntries.getColumn("amount").setRenderer(objectValueRender);
	}

	public void onShow() throws Exception {

		super.onShow();
		// 禁用表头排序
		kdtEntries.addKDTMouseListener(new KDTSortManager(kdtEntries));
		kdtEntries.getSortMange().setSortAuto(false);

	}







	/**
	 * 加载时生成树形
	 */
	private void createTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtEntries.getRowCount()];

		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}

		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtEntries.getTreeColumn().setDepth(maxLevel);

		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.repaint();
	}




	protected void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 1) {
			return;
		}
		int rowIndex = e.getRowIndex();
		ProgrammingContractInfo rowObject = getPCInfo(new ObjectUuidPK(kdtEntries.getCell(rowIndex, "id").getValue().toString()));

		UIContext uiContext = new UIContext(this);
		ProgrammingContractInfo editData = rowObject;
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		uiContext.put("project", project);// 工程项目
		uiContext.put("programming", editData);
		//		uiContext.put("project", projectID);
		if (editData.getId() != null) {
			uiContext.put("programmingId", editData.getId().toString());
		}
		if (editData.getSrcId() != null) {
			uiContext.put("programmingSrcId", editData.getSrcId().toString());
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programming.id", editData.getId().toString()));
		if (PayPlanNewFactory.getRemoteInstance().exists(filter)) {
			oprtState = STATUS_VIEW;
		} else {
			oprtState = STATUS_ADDNEW;
		}
		
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PayPlanNewUI.class.getName(), uiContext, null,
				oprtState);
		uiWindow.show();
	}
	
	private ProgrammingContractInfo getPCInfo(IObjectPK pk) throws EASBizException, BOSException {
		SelectorItemCollection sic = getSelect();
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(pk, sic);
		return pcInfo;
	}

	/**
	 * 描述：
	 * @return
	 * @Author：keyan_zhao
	 * @CreateTime：2013-8-14
	 */
	private SelectorItemCollection getSelect() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programming.id");
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		//		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("economyEntries.*"));
		sic.add(new SelectorItemInfo("economyEntries.paymentType.*"));
		sic.add(new SelectorItemInfo("costEntries.*"));
		sic.add(new SelectorItemInfo("costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("controlAmount"));
		sic.add(new SelectorItemInfo("balance"));
		sic.add(new SelectorItemInfo("controlBalance"));
		sic.add(new SelectorItemInfo("signUpAmount"));
		sic.add(new SelectorItemInfo("changeAmount"));
		sic.add(new SelectorItemInfo("settleAmount"));
		sic.add(new SelectorItemInfo("citeVersion"));
		sic.add(new SelectorItemInfo("isCiting"));
		sic.add(new SelectorItemInfo("attachment"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("level"));
		sic.add(new SelectorItemInfo("parent.longNumber"));
		sic.add(new SelectorItemInfo("sortNumber"));
		sic.add(new SelectorItemInfo("displayName"));
		sic.add(new SelectorItemInfo("workcontent"));
		sic.add(new SelectorItemInfo("supMaterial"));
		sic.add(new SelectorItemInfo("inviteWay"));
		sic.add(new SelectorItemInfo("inviteOrg.*"));
		sic.add(new SelectorItemInfo("buildPerSquare"));
		sic.add(new SelectorItemInfo("soldPerSquare"));
		return sic;
	}



	/**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("    ");
		}
		return blank.toString();
	}

	/**
	 * 设置合约单据头信息
	 * 
	 * @param rowIndex
	 * @param rowObject
	 */
	private void setContractToEditData(int rowIndex, ProgrammingContractInfo rowObject) {
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		rowObject.setLevel(level);
		if (level > 1) {
			Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (FDCHelper.isEmpty(longNumber)) {
				String subNumber = (String) kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
				if (!FDCHelper.isEmpty(subNumber)) {
					rowObject.setLongNumber(subNumber.toString().trim() + ".");// 长编码
				}
			} else {
				rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// 长编码
			}
		} else {
			rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// 长编码
		}
		rowObject.setDisplayName((String) kdtEntries.getCell(rowIndex, "longName").getValue());// 长名称
		rowObject.setName(getCellValue(kdtEntries, rowIndex, "name"));// 名称

		Object amount = kdtEntries.getCell(rowIndex, AMOUNT).getValue();
		if (!FDCHelper.isEmpty(amount)) {
			rowObject.setAmount(new BigDecimal(amount.toString()));// 规划金额
		}

		rowObject.setDescription((String) kdtEntries.getCell(rowIndex, "remark").getValue());// 备注
	}



	/**
	 * 设置名称列显示缩进效果、在前面加空格
	 */
	private void setNameDisplay() {
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			String name = getCellValue(kdtEntries, i, "name");
			if (name != null && name.trim().length() > 0) {
				if (getOprtState().equals(OprtState.VIEW)) {
					boolean isLeaf = isLeaf(getCellValue(kdtEntries, i, LONGNUMBER), kdtEntries, HEADNUMBER);
					row.getCell("name").setValue(createCellTreeNode(name, level, isLeaf));
				} else {
					String blank = setNameIndent(level);
					row.getCell("name").setValue(blank + name);
				}
			}
		}
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
	}


	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.programming.client.ProgrammingUI.class.getName();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("soldArea"));
		sic.add(new SelectorItemInfo("aimCost.*"));
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("entries.longNumber"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.*"));
		//sic.add(new SelectorItemInfo("entries.parent.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.paymentType.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.controlAmount"));
		sic.add(new SelectorItemInfo("entries.balance"));
		sic.add(new SelectorItemInfo("entries.controlBalance"));
		sic.add(new SelectorItemInfo("entries.signUpAmount"));
		sic.add(new SelectorItemInfo("entries.changeAmount"));
		sic.add(new SelectorItemInfo("entries.settleAmount"));
		sic.add(new SelectorItemInfo("entries.citeVersion"));
		sic.add(new SelectorItemInfo("entries.isCiting"));
		sic.add(new SelectorItemInfo("entries.attachment"));
		sic.add(new SelectorItemInfo("entries.description"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.number"));
		sic.add(new SelectorItemInfo("entries.level"));
		sic.add(new SelectorItemInfo("entries.parent.longNumber"));
		sic.add(new SelectorItemInfo("entries.sortNumber"));
		sic.add(new SelectorItemInfo("entries.displayName"));
		sic.add(new SelectorItemInfo("entries.workcontent"));
		sic.add(new SelectorItemInfo("entries.supMaterial"));
		sic.add(new SelectorItemInfo("entries.inviteWay"));
		sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		sic.add(new SelectorItemInfo("entries.buildPerSquare"));
		sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("versionGroup"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		return sic;
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {

		//合约规划编制界面，选中某项目点“刷新”按钮，项目树定位到根节点，应该定位到原选中项目，只刷新右侧列表数据。 add by zkyan
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		clearUIContext();
		buildProjectTree();
		treeMain.setSelectionRow(0);
		treeMain.expandAllNodes(true, (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent());
		treeMain.setSelectionNode(node);
	}

	public void refreshCurrentData() throws Exception {
		if (editData.getId() != null) {
			if (ProgrammingFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))) {
				editData = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(editData.getId().toString()),
						getSelectors());
				if (editData == null)
					return;
				setDataObject(editData);
				loadFields();
			}
		}
		setKDTableLostFocus();
		createTree();
	}


	/**
	 * 构建项目数
	 * 
	 * @throws Exception
	 */
	public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeMain, actionOnLoad);

		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());

		}
	}


	/**
	 * 处理树节点选择变化
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		kdtEntries.removeRows();
		clearUIContext();
		setOprtState(OprtState.VIEW);
		projectID = null;
		if (isSelectedProjectNode()) {
			DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			ProgrammingInfo info = getProgrammingInfo(projectNode);
			if (info != null) {// info不为空，表明有历史版本
				setDataObject(info);
				this.getUIContext().put(ID, editData.getId().toString());
				refreshCurrentData();// 调用刷新方法，解决合计行问题
				setNameDisplay();
			getUIContext().put(TREE_SELECTED_OBJ, projectNode.getUserObject());
			projectID = ((CurProjectInfo) projectNode.getUserObject()).getId().toString();
			kdtEntries.setEditable(false);
			}
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.finance.client.AbstractPayPlanNewListUI#setOprtState(java.lang.String)
	 */
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		actionAuditResult.setVisible(false);
		actionAudit.setVisible(false);
		actionAttachment.setVisible(false);
		actionUnAudit.setVisible(false);
	}
	private void clearUIContext() {
		getUIContext().remove(ID);
	}
	/**
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#isShowAttachmentAction()
	 */
	protected boolean isShowAttachmentAction() {
		return false;
	}

	/**
	 * 获得过滤信息
	 * 
	 * @param projectNode
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CurProjectInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			String id = projTreeNodeInfo.getId().toString();
			filterItems.add(new FilterItemInfo("project.id", id));
			filterItems.add(new FilterItemInfo("isLatest", Boolean.TRUE));
		}
		return filter;
	}

	private TreeSelectionListener[] treeSelectionListeners;

	/**
	 * 添加树的选择监听器
	 */
	protected void attachListeners() {
		if (treeSelectionListeners != null) {
			for (int i = 0; i < treeSelectionListeners.length; i++) {
				this.treeMain.addTreeSelectionListener(treeSelectionListeners[i]);
			}
		}
	}

	/**
	 * 取消树的选择监听器
	 */
	protected void detachListeners() {
		if (treeSelectionListeners != null) {
			for (int i = 0; i < treeSelectionListeners.length; i++) {
				this.treeMain.removeTreeSelectionListener(treeSelectionListeners[i]);
			}
		}
	}



	/**
	 * 根据TreePath获得DefaultKingdeeTreeNode
	 * 
	 * @param path
	 * @return
	 */
	public static DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}

	/**
	 * 根据DefaultKingdeeTreeNode获得ProgrammingInfo
	 * 
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public ProgrammingInfo getProgrammingInfo(DefaultKingdeeTreeNode node) throws Exception {
		if (node == null)
			return null;
		ProgrammingInfo result = null;
		if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			EntityViewInfo mainQuery = new EntityViewInfo();
			mainQuery.setFilter(getTreeSelectFilter(node.getUserObject()));
			SorterItemCollection sorter = mainQuery.getSorter();
			sorter.clear();
			SorterItemInfo sorterVsersion = new SorterItemInfo("version");
			sorterVsersion.setSortType(SortType.DESCEND);
			sorter.add(sorterVsersion);
			ProgrammingCollection coll = ProgrammingFactory.getRemoteInstance().getProgrammingCollection(mainQuery);
			if (coll.size() > 0) {
				result = coll.get(0);
			}
		}
		return result;
	}



	/**
	 * 创建单元格树节点
	 * 
	 * @param name
	 * @param level
	 * @param isLeaf
	 * @return
	 */
	private CellTreeNode createCellTreeNode(String name, int level, boolean isLeaf) {
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(name);
		treeNode.setTreeLevel(level);
		treeNode.setCollapse(false);
		treeNode.setHasChildren(!isLeaf);
		return treeNode;
	}

	/**
	 * 是否选择项目工程节点
	 * 
	 * @return
	 */
	private boolean isSelectedProjectNode() {
		boolean result = false;
		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
		if (treeNode != null && treeNode.isLeaf()) {
			if (treeNode.getUserObject() instanceof CurProjectInfo) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 判断是否为叶子节点
	 * 
	 * @param parentLongNumber
	 * @param table
	 * @param colHeadNumber
	 * @return
	 */
	private boolean isLeaf(String parentLongNumber, KDTable table, String colHeadNumber) {
		boolean result = true;
		if (parentLongNumber == null || parentLongNumber.length() == 0)
			return result;
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String number = getCellValue(table, i, colHeadNumber);
			if (number == null)
				continue;
			if (number.startsWith(parentLongNumber)) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 获取KDTable指定rowIndex中colName中的值，如果有CellTreeNode，则从CellTreeNode中取得值
	 * 
	 * @param table
	 * @param rowIndex
	 * @param colName
	 * @return
	 */
	private static String getCellValue(KDTable table, int rowIndex, String colName) {
		if (table == null || colName == null)
			return null;
		ICell cell = table.getCell(rowIndex, colName);
		if (cell == null)
			return null;
		Object value = cell.getValue();
		String result = null;
		if (value instanceof CellTreeNode)
			result = (String) ((CellTreeNode) value).getValue();
		else
			result = value == null ? "" : value.toString();
		return result == null ? null : result.trim();
	}





	private void showOnlyOneTab() {
		NewWinMainUI mainUI = getMainUI();
		if (mainUI != null) {
			BodyUI bodyUI = mainUI.getBodyUI();
			Component[] c1 = bodyUI.getComponents();
			for (int i = 0; i < c1.length; i++) {
				if (c1[i] instanceof com.kingdee.bos.ctrl.swing.KDTabbedPane) {
					Component[] c2 = ((com.kingdee.bos.ctrl.swing.KDTabbedPane) c1[i]).getComponents();
					for (int j = 0; j < c2.length; j++) {
						if (c2[j] instanceof PayPlanNewListUI) {
							PayPlanNewListUI ptUI = (PayPlanNewListUI) c2[j];
							if (!ptUI.equals(this)) {
								ptUI.getUIWindow().close();
							}
						}
					}
				}
			}
		}
	}

	private com.kingdee.eas.base.uiframe.client.NewWinMainUI getMainUI() {
		if (!(getUIContext().get("Owner") instanceof com.kingdee.eas.base.uiframe.client.NewMainFrame)) {
			return null;
		}
		com.kingdee.eas.base.uiframe.client.NewMainFrame main = ((com.kingdee.eas.base.uiframe.client.NewMainFrame) getUIContext().get(
				"Owner"));
		Component[] c1 = main.getComponents();
		for (int i = 0; i < c1.length; i++) {
			if (c1[i] instanceof com.kingdee.bos.ctrl.swing.KDSkinRootPane) {
				Component[] c2 = ((com.kingdee.bos.ctrl.swing.KDSkinRootPane) c1[i]).getComponents();
				for (int j = 0; j < c2.length; j++) {
					if (c2[j] instanceof javax.swing.JLayeredPane) {
						Component[] c3 = ((javax.swing.JLayeredPane) c2[j]).getComponents();
						for (int k = 0; k < c3.length; k++) {
							if (c3[k] instanceof javax.swing.JPanel) {
								Component[] c4 = ((javax.swing.JPanel) c3[k]).getComponents();
								for (int m = 0; m < c4.length; m++) {
									if (c4[m] instanceof com.kingdee.eas.base.uiframe.client.NewWinMainUI) {
										return (com.kingdee.eas.base.uiframe.client.NewWinMainUI) c4[m];
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	 protected void kdtEntries_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		 payPlanTable.removeColumns();
		payPlanTable.checkParsed();
		 int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		 
		if (rowIndex >= 0) {
			String proId = kdtEntries.getCell(rowIndex, "id").getValue().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programming.id", proId));
			view.setFilter(filter);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("Data.*");
			view.setSelector(sic);
			PayPlanNewCollection collection = PayPlanNewFactory.getRemoteInstance().getPayPlanNewCollection(view);
			if (collection != null && collection.size() > 0) {
				loadDataTable(collection.get(0));
			}
		}
	}

	protected void loadDataTable(PayPlanNewInfo planinfo) {

		
		PayPlanNewDataCollection datas = planinfo.getData();

		if (datas != null && datas.size() > 0) {
			String[] columnKeys = new String[datas.size() + 1];
			Object[] head = new Object[datas.size() + 1];
			Object[][] body = new Object[1][datas.size() + 1];

			for (int i = 1; i <= datas.size(); i++) {
				PayPlanNewDataInfo info = datas.get(i - 1);
				columnKeys[i] = "" + info.getPayMonth();
				head[i] = "" + info.getPayMonth() / 100 + "年" + info.getPayMonth() % 100 + "月";
				body[0][i] = info.getPayAmount();
			}

			columnKeys[0] = "payMonth";
			head[0] = "付款时间";
			body[0][0] = "付款金额";

			KDTableHelper.initTable(payPlanTable, columnKeys, head, body);

			for (int i = 1; i <= datas.size(); i++) {
				PayPlanClientUtil.initFormattedTextCell(payPlanTable, columnKeys[i], 2);
			}
			payPlanTable.getStyleAttributes().setLocked(true);

			}
		payPlanTable.reLayoutAndPaint();

	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getNumberCtrl()
	 */
	protected KDTextField getNumberCtrl() {
		return null;
	}
 
	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#isModify()
	 */
	public boolean isModify() {
		return false;
	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#actionEdit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		ProgrammingContractInfo rowObject = getPCInfo(new ObjectUuidPK(kdtEntries.getCell(rowIndex, "id").getValue().toString()));

		UIContext uiContext = new UIContext(this);
		ProgrammingContractInfo editData = rowObject;
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		uiContext.put("project", project);// 工程项目
		uiContext.put("programming", editData);
		//		uiContext.put("project", projectID);
		if (editData.getId() != null) {
			uiContext.put("programmingId", editData.getId().toString());
		}
		if (editData.getSrcId() != null) {
			uiContext.put("programmingSrcId", editData.getSrcId().toString());
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programming.id", editData.getId().toString()));
		if (PayPlanNewFactory.getRemoteInstance().exists(filter)) {
			oprtState = STATUS_EDIT;
		} else {
			oprtState = STATUS_ADDNEW;
		}

		IUIWindow create = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PayPlanNewUI.class.getName(), uiContext, null, oprtState);
		create.show();
		
	}
}