/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumnView;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.HashSearchEngine;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.fdc.basedata.VirtualProductTypeEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @version 2.0 
 * 20111103 ��ProjectIndexDataʵ���������һ���ֶ� aimMeasureID ������"Ŀ��ɱ�����"��ָ�������յ������ֶ�
 * ������ʵչ������ʱ��ȡֵʱ��Ҫע�⣬
 * һ��Ҫ���Ϲ�������filterItems.add(new FilterItemInfo("aimMeasureID",null));
 */
public class ProjectIndexDataMntUI extends AbstractProjectIndexDataMntUI {

	private static final Logger logger = CoreUIObject.getLogger(ProjectIndexDataMntUI.class);

	protected static final String TARGET_TYPE_ID = "targetType.id";

	protected static final String REMARK = "remark";

	protected static final String DESC = "desc";

	protected static final String MEASURE_UNIT_NAME = "measureUnit.name";

	protected static final String INDEX_VALUE = "indexValue";

	protected static final String APPORTION_TYPE_NAME = "apportionType.name";

	protected static final String APPORTION_TYPE_NUMBER = "apportionType.number";

	protected static final String APPORTION_TYPE_ID = "apportionType.id";
	protected static final String APPORTION_TYPE_ISCOSTSPLIT = "isCostSplit";
	/**
	 * ����ǰ�����޸Ĺ���ָ�꣬��ָ��ˢ�µ�ʱ�����
	 */
	private final Set changeApportions=new HashSet();
	boolean notChange = true;

	boolean hasData = true;

	/**
	 * output class constructor
	 */
	public ProjectIndexDataMntUI() throws Exception {
		super();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return ProjectIndexDataFactory.getRemoteInstance();
	}

	private void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);

	}

	private TreeSelectionListener treeSelectionListener;

	private ITreeBuilder treeBuilder;

	/**
	 * ����ָ��������
	 */
	protected void buildTargetTypeTree() throws Exception {
		KDTree treeMain = getTargetTypeTree();
		TreeSelectionListener[] listeners = treeMain.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(), getTreeInitialLevel(), getTreeExpandLevel(), this.getDefaultFilterForTree());

		if (getRootName() != null) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);

		} else {
			((DefaultTreeModel) treeMain.getModel()).setRoot(null);
		}

		treeBuilder.buildTree(treeMain);
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

	}

	/**
	 * ��ʼ��ILNTreeNodeCtrl��һ�㲻�����أ� �ڸ��ݱ������ݹ����ض���ʱ��������˵�����Ҫ����Ȩ�޽��й��ˣ�
	 * ������Ҫ��д�ض���ILNTreeNodeCtrl��ʵ���ࣨʵ����ı�д���Բ���DefaultLNTreeNodeCtrl)��
	 * ��ʱ�����Ҫ���أ�����Ϊʾ��������ض����ͣ���
	 */
	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}

	private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = TargetTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}

		return treeBase;
	}

	private KDTree getTargetTypeTree() {
		return treeTargetType;
	}

	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}

	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}

	/**
	 * ������ڵ���ʾ���ƣ�Ĭ�Ϸ���null����û�и���㣨���н�����������ݣ� �̳���������أ������ʾ���
	 */
	protected String getRootName() {
		return FDCClientUtils.getRes("allTargetType");
	}

	protected Object getRootObject() {
		return getRootName();
	}

	public void onLoad() throws Exception {
		KDTIndexColumnView view=new KDTIndexColumnView(getDetailTable(),FDCTableHelper.getMyKDTIndexColumn(getDetailTable()),
				getDetailTable().getColumns(),getDetailTable().getBody());
		KDTViewManager viewManager = getDetailTable().getViewManager();
		for(int i=0;i<viewManager.getViewCount();i++){
			if(viewManager.getView(i) instanceof KDTIndexColumnView){
				int vIndex=viewManager.getVerticalIndex(i);
				int hIndex=viewManager.getHorizonIndex(i);
				viewManager.setView(vIndex, hIndex, view);
			}
		}
//		getDetailTable().updateUI();
//		viewManager.setView(1, 1, view);
		
		stage = ProjectStageEnum.DYNCOST;
		EventListener[] listeners = comboProjStage.getListeners(java.awt.event.ItemListener.class);
		for(int i=0;i<listeners.length;i++){
			comboProjStage.removeItemListener((java.awt.event.ItemListener)listeners[i]);
		}
		comboProjStage.setSelectedIndex(2);
		for(int i=0;i<listeners.length;i++){
			comboProjStage.addItemListener((java.awt.event.ItemListener)listeners[i]);
			listeners[i]=null;
		}
		
		listeners=null;
		
		actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
		super.onLoad();
		FDCTableHelper.disableExportByPerimission(this, tblEntries, "ActionExport");
		buildProjectTree();
		buildTargetTypeTree();
		initTable();
		FDCHelper.setComponentPrecision(this.getComponents(), 2);
		treeProject.setShowsRootHandles(true);
		treeProject.expandAllNodes(true, (TreeNode) treeProject.getModel().getRoot());
		/*
		 * ����ѡ�и����
		 */
		treeProject.setSelectionRow(0);
		treeTargetType.setSelectionRow(0);

		FDCClientHelper.addSqlMenu(this, this.menuFile);
		getDetailTable().setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		getDetailTable().getColumn(INDEX_VALUE).setEditor(FDCClientHelper.getNumberCellEditor());

//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {

//			actionSave.setEnabled(false);
//		}

		getDetailTable().getColumn(REMARK).setEditor(FDCClientHelper.getKDTextFieldCellEditor());
		changeApportions.clear();
//		FDCTableHelper.setIndexColumnHided(getDetailTable(), true);
		//������Ŀ�׶ε� ���гɱ�ѡ��
		this.comboProjStage.removeItem(ProjectStageEnum.RESEARCH);
		this.comboProjStage.setSelectedItem(ProjectStageEnum.AIMCOST);
		
		this.txtVerRemark.setEnabled(false);
		this.txtMeasureStage.setEnabled(false);
	}

	/**
	 * 
	 * ��������ʼ�����
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-3
	 *               <p>
	 */
	protected void initTable() {
		getDetailTable().setEnabled(true);
		getDetailTable().getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		getDetailTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		FDCHelper.formatTableNumber(getDetailTable(), INDEX_VALUE);
		getDetailTable().getColumn(APPORTION_TYPE_NAME).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn(APPORTION_TYPE_NUMBER).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn(MEASURE_UNIT_NAME).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn(DESC).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		getDetailTable().getColumn(APPORTION_TYPE_ISCOSTSPLIT).getStyleAttributes().setHided(true);
		setTableLocked(false);
		getDetailTable().getViewManager().setFreezeView(-1, 3);
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {

//		 warnForSave();
		super.treeProject_valueChanged(e);

		fillProductTypeByProject();

		selectChange();
		
	}

	protected void treeTargetType_valueChanged(TreeSelectionEvent e) throws Exception {
		warnForSave();
		super.treeTargetType_valueChanged(e);
		selectChange();
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
	}

	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeTargetType.getLastSelectedPathComponent();
	}

	/**
	 * 
	 * ��������������߲�Ʒ����������ѡ��ı䣬���¹�������ִ�в�ѯ
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-25
	 *               <p>
	 */
	protected void selectChange() throws Exception {

		ProjectIndexDataInfo info = getReadyProjectIndexDataInfo(null);
		if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
			// ������ָ�겻�����޸�
			setOprtState("FINDVIW");
		} else {
			setOprtState(null);
		}
		setActionStatus(info);

		setDataObject(info);
		fillTableByColl();

		selectFirstRow();

		setTableLocked(true);
		
		changeApportions.clear();
	}

	/**
	 * ���ݹ�����Ŀ����Ʒ����combobx, �����ǰѡ�������֯,����ʾ���в�Ʒ����,
	 * ����ǹ�����Ŀ,Ҫ���ݹ�����Ŀ�Ĳ�Ʒ��¼��ʾ,���û�в�Ʒ��¼��,ֻ��ʾ��������
	 * 
	 * @throws Exception
	 */
	private void fillProductTypeByProject() throws Exception {
		String projOrOrgId = getSelectProjOrOrgId();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", projOrOrgId));
		view.setFilter(filter);
		view.getSelector().add("curProjProductEntries.productType.id");

		CurProjectCollection curProjectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);

		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		boolean noProdEntries = false;
		if (curProjectCollection != null && curProjectCollection.size() > 0) {
			CurProjectInfo projInfo = curProjectCollection.get(0);
			CurProjProductEntriesCollection curProjProductEntries = projInfo.getCurProjProductEntries();
			Set prodIds = new HashSet();
			if (curProjProductEntries != null && curProjProductEntries.size() > 0) {
				for (int i = 0; i < curProjProductEntries.size(); i++) {
					prodIds.add(curProjProductEntries.get(i).getProductType().getId().toString());
				}

				filter.getFilterItems().add(new FilterItemInfo("id", prodIds, CompareType.INCLUDE));
			} else { // û�в�Ʒ��¼
				noProdEntries = true;
			}
		}

		comboProductType.removeAllItems();

		comboProductType.addItem(VirtualProductTypeEnum.WHOLE_PROJ);

		if (!noProdEntries) {

			ProductTypeCollection productTypeCollection = ProductTypeFactory.getRemoteInstance().getProductTypeCollection(view);
			for (Iterator iter = productTypeCollection.iterator(); iter.hasNext();) {
				ProductTypeInfo element = (ProductTypeInfo) iter.next();
				comboProductType.addItem(element);
			}
		}

		comboProductType.setSelectedItem(VirtualProductTypeEnum.WHOLE_PROJ);
	}

	private void setActionStatus(ProjectIndexDataInfo info) {
		if (hasData) {
			FDCBillStateEnum state = info.getState();

			if (state == null||!info.getVerNo().equals("V1.0")) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(false);
			} else if (state == FDCBillStateEnum.AUDITTED) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
			} else if (state == FDCBillStateEnum.SUBMITTED) {
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
			}

		} else {
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}

	protected void selectFirstRow() {

		// ѡ�е�һ��
		if (getDetailTable().getRowCount()>0) {
			for(int i=0;i<getDetailTable().getRowCount();i++){
				if(!getDetailTable().getRow(i).getStyleAttributes().isHided()){
					getDetailTable().getSelectManager().select(i, 0);
					break;
				}
			}
		}

	}

	protected ProjectStageEnum getProjectStage() {
		return stage;
	}

	protected ProjectIndexDataInfo getReadyProjectIndexDataInfo(String verNo) throws EASBizException, BOSException {
		String targetTypeId = getTargetTypeId();
		FilterInfo filter = getSelectChangeFilter();
		FilterInfo appFilter = ProjectIndexDataUtils.getAppFilter(targetTypeId, false);

		ProjectIndexDataEntryCollection coll = ProjectIndexDataUtils.createBlankIndexesColl(appFilter);
		if (verNo == null) {
			filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("verNo", verNo));
		}
		ProjectIndexDataInfo info = getProjectIndexDataInfo(filter);
		if (info == null) {

			info = ProjectIndexDataUtils.constructAProjectIndexDataInfo(getSelectProjOrOrgId(), getProjectStage(), getProductTypeId());
			info.getEntries().addCollection(coll);
			hasData = false;
		} else {
			hasData = true;
		}
		ProjectIndexDataEntryCollection savedColl = info.getEntries();

		HashSearchEngine searchEngin = new HashSearchEngine(coll, new String[] { "apportionType" });
		for (Iterator iter = savedColl.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo entryInfo = (ProjectIndexDataEntryInfo) iter.next();
			if (searchEngin.evaluate(entryInfo)) {
				ProjectIndexDataEntryInfo idxInfo = (ProjectIndexDataEntryInfo) searchEngin.getResult();
				idxInfo.setIndexValue(entryInfo.getIndexValue());
				idxInfo.setRemark(entryInfo.getRemark());
			}
		}

		info.getEntries().clear();

		info.getEntries().addCollection(coll);
		return info;
	}

	protected String getProductTypeId() {
		Object selectedItem = this.comboProductType.getSelectedItem();
		if (selectedItem instanceof ProductTypeInfo) {
			ProductTypeInfo productTypeInfo = (ProductTypeInfo) selectedItem;
			return productTypeInfo.getId().toString();
		}

		return null;
	}

	protected String getTargetTypeId() {
		String targetTypeId = null;
		if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().getUserObject() instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo) getTypeSelectedTreeNode().getUserObject();
			targetTypeId = typeTreeNodeInfo.getId().toString();
		}
		return targetTypeId;
	}

	protected void setCanEdit(boolean b) {

//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			return;
//		}
		getDetailTable().getColumn(getAmountColKey()).getStyleAttributes().setLocked(!b);
		getDetailTable().getColumn(REMARK).getStyleAttributes().setLocked(!b);
		actionSave.setEnabled(b);
	}

	protected ProjectIndexDataInfo getProjectIndexDataInfo(FilterInfo filter) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();

		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("productType.id");
		view.getSelector().add("entries.*");
		view.getSelector().add("entries.targetType.id");
		view.getSelector().add("creator.name");
		view.getSelector().add("auditor.name");
		view.getSelector().add("measureStage.name");
		
		ProjectIndexDataCollection projectIndexDataCollection = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);

		if (projectIndexDataCollection.size() > 0) {
			return projectIndexDataCollection.get(0);
		}
		return null;
	}

	protected void comboProductType_itemStateChanged(ItemEvent e) throws Exception {

		if (e.getStateChange() == ItemEvent.DESELECTED) {
			warnForSave();
		}
		super.comboProductType_itemStateChanged(e);

		if (e.getStateChange() == ItemEvent.SELECTED) {
			selectChange();
		}
	}

	protected void warnForSave() throws Exception {
		if (!notChange) {
			int result = MsgBox.showConfirm2(this, "�����Ѿ��޸ģ��л��ᵼ���޸Ķ�ʧ���Ƿ񱣴棿");
			if (result == MsgBox.YES) {
				/*
				 * save(); showSaveOkMsg();
				 */
				actionSave_actionPerformed(null);
			}
		}
	}

	public void fillTableByColl() throws Exception {
		getDetailTable().removeRows(false);

		// boolean isOrg = false;
		boolean isLeafProj = false;
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			if (getProjSelectedTreeNode().getUserObject() instanceof OrgStructureInfo) {
				// isOrg = true;
				setCanEdit(false);
			}
			/*
			 * ����07.1.16�ͻ�����,ȥ�����ܹ��ܣ���Ϊ������Ŀ�����¼��������޸�
			 */
			// else if(getProjSelectedTreeNode().isLeaf()) {
			// isLeafProj = true;
			// setCanEdit(true);
			// }
			// ��Ϊ
			else {
				isLeafProj = getProjSelectedTreeNode().isLeaf();
				setCanEdit(true);
			}
			// ��������������ʱΪ���ɱ༭����֤�鿴״̬��VIEW�����ܱ༭����
			if (getOprtState() != null && getOprtState().equals("FINDVIW")) {
				setCanEdit(false);
			}
		}

		ProjectIndexDataInfo projectIndexDataInfo = (ProjectIndexDataInfo) getDataObject();
		for (Iterator iter = projectIndexDataInfo.getEntries().iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
			IRow row = getDetailTable().addRow();
			row.getCell(APPORTION_TYPE_NUMBER).setValue(element.getApportionType().getNumber());
			row.getCell(APPORTION_TYPE_NAME).setValue(element.getApportionType().getName());
			row.getCell(getAmountColKey()).setValue(element.getIndexValue());
			if (element.getApportionType().getMeasureUnit() != null) {
				row.getCell(MEASURE_UNIT_NAME).setValue(element.getApportionType().getMeasureUnit().getName());
			} else {
				row.getCell(MEASURE_UNIT_NAME).setValue(null);
			}

			row.getCell(DESC).setValue(element.getApportionType().getDescription());
			row.getCell(REMARK).setValue(element.getRemark());
			row.getCell(APPORTION_TYPE_ID).setValue(element.getApportionType().getId().toString());
			row.getCell(TARGET_TYPE_ID).setValue(element.getApportionType().getTargetType().getId().toString());
			
			row.getCell(APPORTION_TYPE_ISCOSTSPLIT).setValue(Boolean.valueOf(element.getApportionType().isForCostApportion()));
			
			// �������ʤҪ����ϸ������Ŀ�Ŀ�������ɲ�Ʒ�������������ɱ༭ by sxhong 2007-11-29
			if (isLeafProj && projectIndexDataInfo.getProductType() == null && element.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
				row.getCell(getAmountColKey()).getStyleAttributes().setLocked(true);
				row.getCell(getAmountColKey()).getStyleAttributes().setBackground(new Color(0xE8E8E3));
			}
			/*
			 * ����07.1.16�ͻ�����,ȥ�����ܹ��ܣ������¼����ܵ����ݲ��ɱ༭�Ŀ���Ҳȥ��
			 */
			// if(!isOrg && !isLeafProj) {
			// if(element.isBySum()) { //�¼����ܵ����ݣ����ɱ༭
			// row.getCell(INDEX_VALUE).getStyleAttributes().setLocked(true);
			// row.getCell(REMARK).getStyleAttributes().setLocked(true);
			// }
			// else {
			// row.getCell(INDEX_VALUE).getStyleAttributes().setLocked(false);
			// row.getCell(REMARK).getStyleAttributes().setLocked(false);
			// }
			// }
		}

		setButtonInfo();
		if(!isDisplayAreaIndex()){
			Set areaIndexSet=new HashSet();
			areaIndexSet.add(ApportionTypeInfo.buildAreaType);
			areaIndexSet.add(ApportionTypeInfo.sellAreaType);
			areaIndexSet.add(ApportionTypeInfo.placeAreaType);
			for(int i=0;i<getDetailTable().getRowCount();i++){
				ICell cell = getDetailTable().getCell(i, APPORTION_TYPE_ID);
				if(cell!=null&&cell.getValue()!=null&&areaIndexSet.contains(cell.getValue())){
					getDetailTable().getRow(i).getStyleAttributes().setHided(true);
				}
			}

		}
		notChange = true;
	}

	public void save(Map ver) throws Exception {

		final ProjectIndexDataInfo indexDataInfo = (ProjectIndexDataInfo) getDataObject();
		final ProjectIndexDataInfo old_indexDataInfo = (ProjectIndexDataInfo) indexDataInfo.clone();

		setBuildAreaDif(old_indexDataInfo);
		indexDataInfo.getEntries().clear();

		String verRemark = (String) ver.get("verRemark");
		indexDataInfo.setVerRemark(verRemark);
		
		MeasureStageInfo measureStage=(MeasureStageInfo)ver.get("measureStage");
		indexDataInfo.setMeasureStage(measureStage);
		ProjectIndexDataEntryInfo info = null;
		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
			IRow row = getDetailTable().getRow(i);

			String appTypeId = (String) row.getCell(APPORTION_TYPE_ID).getValue();
			String tarTypeId = (String) row.getCell(TARGET_TYPE_ID).getValue();
			BigDecimal indexValue = (BigDecimal) row.getCell(getAmountColKey()).getValue();
			String remark = (String) row.getCell(REMARK).getValue();
			// ����ת��Ϊ0
			// indexValue=FDCHelper.toBigDecimal(indexValue);
			// �û�û��¼�����ݻ�Ϊ0��յ��в�����
			// 0Ҫ���� by sxhong ��Ȼ���ݲ��ָܻ�����ʼ0
			/*
			 * if((indexValue == null || indexValue.signum() == 0) && (remark ==
			 * null || remark.length() == 0)) { continue; }
			 */
			if ((indexValue == null) && (remark == null || remark.length() == 0)) {
				continue;
			}
			ApportionTypeInfo appTypeInfo = new ApportionTypeInfo();
			appTypeInfo.setId(BOSUuid.read(appTypeId));
			info = new ProjectIndexDataEntryInfo();
			info.setApportionType(appTypeInfo);
			TargetTypeInfo tarType = new TargetTypeInfo();
			tarType.setId(BOSUuid.read(tarTypeId));
			info.setTargetType(tarType);
			info.setIndexValue(indexValue);
			info.setRemark(remark);

			indexDataInfo.getEntries().add(info);
		}

		if (indexDataInfo.getEntries().size() > 0) {
			if(isDisplayAreaIndex()||indexDataInfo.getState()==null){
				indexDataInfo.setState(FDCBillStateEnum.SUBMITTED);
			}
			String productType = indexDataInfo.getProductType() == null ? null : indexDataInfo.getProductType().getId().toString();
			ProjectIndexDataCollection projectIndexDataColl = ProjectIndexDataUtils.getProjectIndexDataCollByCond(getProjectStage(), productType, indexDataInfo.getProjOrOrgID().toString(), true);

			if (projectIndexDataColl.size() > 0) {
				ProjectIndexDataEntryCollection dbEntries = projectIndexDataColl.get(0).getEntries();

				Map curEntryMap = new HashMap();
				ProjectIndexDataEntryCollection curEntries = indexDataInfo.getEntries();
				for (Iterator iter = curEntries.iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
					curEntryMap.put(element.getApportionType().getId().toString(), element);
				}

				for (Iterator iter = dbEntries.iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
					String appId = element.getApportionType().getId().toString();
					if (!curEntryMap.containsKey(appId)) {
						indexDataInfo.getEntries().add(element);
					}

				}
			}
			if (isSelectLeafProject()) {
				checkSettleProduct(indexDataInfo);
			}
			IObjectPK pk = ProjectIndexDataFactory.getRemoteInstance().submit(indexDataInfo);
			indexDataInfo.setId(BOSUuid.read(pk.toString()));

		} else {
			if (isSelectLeafProject()) {
				checkSettleProduct(indexDataInfo);
			}
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("projOrOrgID", indexDataInfo.getProjOrOrgID().toString()));
			;
			filterItems.add(new FilterItemInfo("projectStage", indexDataInfo.getProjectStage().getValue()));
			;
			filterItems.add(new FilterItemInfo("productType", indexDataInfo.getProductType() == null ? null : indexDataInfo.getProductType().getId().toString()));
			boolean exists = ProjectIndexDataFactory.getRemoteInstance().exists(filter);
			if (exists) {
				ProjectIndexDataFactory.getRemoteInstance().delete(filter);
			}
		}

		String productTypeId = null;
		if (comboProductType.getSelectedItem() instanceof ProductTypeInfo) {
			productTypeId = ((ProductTypeInfo) comboProductType.getSelectedItem()).getId().toString();
		}

		// 07.1.16���ݿͻ�����,ȥ�����ܹ���
		// TreeNode node =
		// ProjectIndexDataUtils.getNodeById(indexDataInfo.getProjOrOrgID().toString());
		// ProjectIndexDataUtils.sumUp(node, productTypeId);

		notChange = true;

		// ��Ʒ��������������������
		if (isSelectLeafProject() && indexDataInfo.getProductType() != null) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					try {
						sumIndexValue2(old_indexDataInfo, indexDataInfo);
					} catch (Exception e) {
						handUIException(e);
					}
				}
			});
		}

	}

	protected void checkSettleProduct(ProjectIndexDataInfo info) throws BOSException, EASBizException {

		BigDecimal sellArea = FDCHelper.ZERO;
		String productID = null;
		if (info.getProductType() != null) {
			productID = info.getProductType().getId().toString();
		}
		String projectID = info.getProjOrOrgID().toString();

		Set status = new HashSet();
		status.add(ProjectStatusInfo.settleID);
		status.add(ProjectStatusInfo.closeID);
		FilterInfo filterEx = new FilterInfo();
		filterEx.getFilterItems().add(new FilterItemInfo("id", projectID));
		filterEx.getFilterItems().add(new FilterItemInfo("projectStatus.id", status, CompareType.INCLUDE));
		if (CurProjectFactory.getRemoteInstance().exists(filterEx)) {
			MsgBox.showError(this, "������Ŀ�Ѿ�ȫ���������㣬�����޸�ָ�����ݣ�");
			SysUtil.abort();
		}
		if (productID != null) {
			FilterInfo filterExist = new FilterInfo();
			filterExist.getFilterItems().add(new FilterItemInfo("curProject.id", projectID));
			filterExist.getFilterItems().add(new FilterItemInfo("productType.id", productID));
			filterExist.getFilterItems().add(new FilterItemInfo("isCompSettle", Boolean.TRUE));
			if (CurProjProductEntriesFactory.getRemoteInstance().exists(filterExist)) {
				MsgBox.showError(this, "��Ʒ�Ѿ�ȫ���������㣬�����޸�ָ�����ݣ�");
				SysUtil.abort();
			}
			for (Iterator iter = info.getEntries().iterator(); iter.hasNext();) {
				ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
				if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
					sellArea = FDCHelper.toBigDecimal(entry.getIndexValue());
				}
			}
			if (info.getProjectStage().equals(ProjectStageEnum.DYNCOST)) {
				BigDecimal compArea = FDCHelper.ZERO;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("curProjProductEntries.curProject.id", projectID));
				filter.getFilterItems().add(new FilterItemInfo("curProjProductEntries.productType.id", productID));
				view.setFilter(filter);
				ProductSettleBillCollection coll = ProductSettleBillFactory.getRemoteInstance().getProductSettleBillCollection(view);
				for (Iterator it = coll.iterator(); it.hasNext();) {
					ProductSettleBillInfo infoPr = (ProductSettleBillInfo) it.next();
					compArea = compArea.add(infoPr.getCompArea());
				}
				if (compArea.subtract(sellArea).compareTo(FDCHelper.ZERO) == 1) {
					MsgBox.showError(this, "�����������С���ѿ��������" + compArea.setScale(2) + "��");
					SysUtil.abort();
				}
			}
		}
	}

	protected boolean isSelectLeafProject() {
		boolean isLeaf = false;
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo) {
			isLeaf = getProjSelectedTreeNode().isLeaf();
		}
		return isLeaf;
	}

	protected void sumIndexValue(ProjectIndexDataInfo oldInfo, ProjectIndexDataInfo info) throws Exception {
		if(!isDisplayAreaIndex()){
			return;
		}
		/*
		 * �����������Ʒ�������֮�� �����������Ʒ�������+��ԭ�������-ԭ��Ʒ�������֮�ͣ�����ԭ�������Ϊ0���򣽲�Ʒ�������֮��
		 */
		BigDecimal sellArea = FDCHelper.ZERO;
		BigDecimal oldSellArea = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		BigDecimal oldBuildArea = FDCHelper.ZERO;
		for (Iterator iter = oldInfo.getEntries().iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
				oldSellArea = oldSellArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
				oldBuildArea = oldBuildArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (oldSellArea.signum() > 0 && oldBuildArea.signum() > 0) {
				break;
			}
		}

		for (Iterator iter = info.getEntries().iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
				sellArea = sellArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
				buildArea = buildArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (sellArea.signum() > 0 && buildArea.signum() > 0) {
				break;
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("projOrOrgID", info.getProjOrOrgID());
		view.getFilter().appendFilterItem("projectStage", info.getProjectStage().getName());
		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		view.getFilter().appendFilterItem("productType.id", null);
		view.getSelector().add("*");
		view.getSelector().add("entries.apportionType.id");
		view.getSelector().add("entries.indexValue");
		// ����ָ���������°汾�����������
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		if (oldSellArea.compareTo(sellArea) != 0) {
			ProjectIndexDataCollection c = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
			if (c != null && c.size() > 0) {
				boolean hasChange = false;
				for (Iterator iter = c.get(0).getEntries().iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
					if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
						if (FDCHelper.toBigDecimal(entry.getIndexValue()).compareTo(FDCHelper.ZERO) == 0) {
							BigDecimal sum = FDCHelper.ZERO;
							for (Iterator iter2 = c.get(0).getEntries().iterator(); iter2.hasNext();) {
								ProjectIndexDataEntryInfo entry2 = (ProjectIndexDataEntryInfo) iter2.next();
								sum = sum.add(FDCHelper.toBigDecimal(entry2.getIndexValue()));
							}
							entry.setIndexValue(sum);
						} else {
							entry.setIndexValue(FDCHelper.toBigDecimal(entry.getIndexValue()).add(sellArea).subtract(oldSellArea));
						}

						SelectorItemCollection selector = new SelectorItemCollection();
						selector.add("indexValue");
						ProjectIndexDataEntryFactory.getRemoteInstance().updatePartial(entry, selector);
						hasChange = true;
						break;
					}
				}

				if (!hasChange) {
					// �½�һ����¼
					ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
					entry.setIndexValue(sellArea);
					entry.setParent(c.get(0));
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.sellAreaType));
					entry.setApportionType(apport);
					ProjectIndexDataEntryFactory.getRemoteInstance().addnew(entry);
				}
			} else {
				// �½�һ������¼
				ProjectIndexDataInfo dataInfo = new ProjectIndexDataInfo();
				dataInfo.setProjOrOrgID(info.getProjOrOrgID());
				dataInfo.setProjectStage(info.getProjectStage());
				dataInfo.setVerNo("V1.0");
				dataInfo.setVerName("��Ʒ��������");
				dataInfo.setIsLatestVer(true);
				// �½�һ����¼
				ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
				entry.setIndexValue(sellArea);
				entry.setParent(dataInfo);
				ApportionTypeInfo apport = new ApportionTypeInfo();
				apport.setId(BOSUuid.read(ApportionTypeInfo.sellAreaType));
				entry.setApportionType(apport);
				dataInfo.getEntries().add(entry);
				ProjectIndexDataFactory.getRemoteInstance().addnew(dataInfo);
			}
		}

		if (oldBuildArea.compareTo(buildArea) != 0) {

			ProjectIndexDataCollection c = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
			if (c != null && c.size() > 0) {
				boolean hasChange = false;
				for (Iterator iter = c.get(0).getEntries().iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
					if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
						if (FDCHelper.toBigDecimal(entry.getIndexValue()).compareTo(FDCHelper.ZERO) == 0) {
							BigDecimal sum = FDCHelper.ZERO;
							for (Iterator iter2 = c.get(0).getEntries().iterator(); iter2.hasNext();) {
								ProjectIndexDataEntryInfo entry2 = (ProjectIndexDataEntryInfo) iter2.next();
								sum = sum.add(FDCHelper.toBigDecimal(entry2.getIndexValue()));
							}
							entry.setIndexValue(sum);
						} else {
							entry.setIndexValue(entry.getIndexValue().add(buildArea).subtract(oldBuildArea));
						}
						SelectorItemCollection selector = new SelectorItemCollection();
						selector.add("indexValue");
						ProjectIndexDataEntryFactory.getRemoteInstance().updatePartial(entry, selector);
						hasChange = true;
						break;
					}
				}

				if (!hasChange) {
					// �½�һ����¼
					ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
					entry.setIndexValue(buildArea);
					entry.setParent(c.get(0));
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
					entry.setApportionType(apport);
					ProjectIndexDataEntryFactory.getRemoteInstance().addnew(entry);
				}
			} else {
				// �½�һ������¼
				ProjectIndexDataInfo dataInfo = new ProjectIndexDataInfo();
				dataInfo.setProjOrOrgID(info.getProjOrOrgID());
				dataInfo.setProjectStage(info.getProjectStage());
				dataInfo.setVerNo("V1.0");
				dataInfo.setVerName("��Ʒ��������");
				dataInfo.setIsLatestVer(true);
				// �½�һ����¼
				ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
				entry.setIndexValue(buildArea);
				entry.setParent(dataInfo);
				ApportionTypeInfo apport = new ApportionTypeInfo();
				apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
				entry.setApportionType(apport);
				dataInfo.getEntries().add(entry);
				ProjectIndexDataFactory.getRemoteInstance().addnew(dataInfo);
			}
		}

	}

	/**
	 * ʹ����һ��ʵ�ַ�ʽ,����ָ��Ĳ���������ݿ���º�ֱ����sql����������, �ڸ���֮ǰҪ�ж���Ŀ���Ƿ��в�Ʒ��ָ���¼��,û��Ҫ���
	 * 
	 * @param oldInfo
	 * @param info
	 * @throws Exception
	 */
	protected void sumIndexValue2(ProjectIndexDataInfo oldInfo, ProjectIndexDataInfo info) throws Exception {
		if(!isDisplayAreaIndex()){
			return;
		}
		/*
		 * �����������Ʒ�������֮�� �����������Ʒ�������+��ԭ�������-ԭ��Ʒ�������֮�ͣ�����ԭ�������Ϊ0���򣽲�Ʒ�������֮��
		 */

		BigDecimal sellArea = FDCHelper.ZERO;
		BigDecimal oldSellArea = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		BigDecimal oldBuildArea = FDCHelper.ZERO;
		for (Iterator iter = oldInfo.getEntries().iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
				oldSellArea = oldSellArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
				oldBuildArea = oldBuildArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (oldSellArea.signum() > 0 && oldBuildArea.signum() > 0) {
				break;
			}
		}

		for (Iterator iter = info.getEntries().iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
				sellArea = sellArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
				buildArea = buildArea.add(FDCHelper.toBigDecimal(entry.getIndexValue()));
			}
			if (sellArea.signum() > 0 && buildArea.signum() > 0) {
				break;
			}
		}

		if (oldSellArea.compareTo(sellArea) == 0 && oldBuildArea.compareTo(buildArea) == 0) {
			return;
		}

		// ��sql���и���
		FDCSQLBuilder builder = new FDCSQLBuilder();

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("projOrOrgID", info.getProjOrOrgID());
		view.getFilter().appendFilterItem("projectStage", info.getProjectStage().getName());
		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		view.getFilter().appendFilterItem("productType.id", null);
		view.getSelector().add("*");
		view.getSelector().add("entries.apportionType.id");
		view.getSelector().add("entries.indexValue");
		// ����ָ���������°汾�����������
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		ProjectIndexDataCollection c = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		ProjectIndexDataInfo head = null;
		if (oldSellArea.compareTo(sellArea) != 0) {
			if (c != null && c.size() > 0) {
				head = c.get(0);
				boolean hasChange = false;
				for (Iterator iter = head.getEntries().iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
					if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
						hasChange = true;
						break;
					}

				}

				if (!hasChange) {
					// �½�һ����¼
					ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
					entry.setIndexValue(sellArea);
					entry.setParent(c.get(0));
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.sellAreaType));
					entry.setApportionType(apport);
					ProjectIndexDataEntryFactory.getRemoteInstance().addnew(entry);
				}
			} else {
				// �½�һ������¼
				head = new ProjectIndexDataInfo();
				head.setProjOrOrgID(info.getProjOrOrgID());
				head.setProjectStage(info.getProjectStage());
				head.setVerNo("V1.0");
				head.setVerName("��Ʒ��������");
				head.setIsLatestVer(true);
				// �½�һ����¼
				ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
				entry.setIndexValue(sellArea);
				entry.setParent(head);
				ApportionTypeInfo apport = new ApportionTypeInfo();
				apport.setId(BOSUuid.read(ApportionTypeInfo.sellAreaType));
				entry.setApportionType(apport);
				head.getEntries().add(entry);
				IObjectPK pk = ProjectIndexDataFactory.getRemoteInstance().addnew(head);
				head.setId(BOSUuid.read(pk.toString()));
			}

			// �������=��Ʒ�������֮��
			builder.appendSql("update T_FDC_ProjectIndexDataEntry set findexvalue=(");
			builder.appendSql("select sum(entry.findexvalue) from T_FDC_ProjectIndexDataEntry entry  ");
			builder.appendSql("inner join T_FDC_ProjectindexData head on head.fid=entry.fparentid and head.fprojOrOrgID=? ");
			builder.appendSql("and fprojectStage=? and fisLatestVer=1 and fproductTypeid is not null  ");
			builder.appendSql("where fapportionTypeid=? ");
			builder.appendSql(") ");
			builder.appendSql("where  fparentid=? and fapportionTypeid=?");

			builder.addParam(head.getProjOrOrgID().toString());
			builder.addParam(head.getProjectStage().getValue());
			builder.addParam(ApportionTypeInfo.sellAreaType);
			builder.addParam(head.getId().toString());
			builder.addParam(ApportionTypeInfo.sellAreaType);
			builder.execute();
		}

		if (oldBuildArea.compareTo(buildArea) != 0) {
			if (c != null && c.size() > 0) {
				head = c.get(0);
				boolean hasChange = false;
				for (Iterator iter = head.getEntries().iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
					if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
						hasChange = true;
						break;
					}
				}

				if (!hasChange) {
					// �½�һ����¼
					ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
					entry.setIndexValue(buildArea);
					entry.setParent(c.get(0));
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
					entry.setApportionType(apport);
					IObjectPK pk = ProjectIndexDataFactory.getRemoteInstance().addnew(head);
					head.setId(BOSUuid.read(pk.toString()));
				}
			} else {
				// �½�һ������¼
				if (head == null) {
					head = new ProjectIndexDataInfo();
					head.setProjOrOrgID(info.getProjOrOrgID());
					head.setProjectStage(info.getProjectStage());
					head.setVerNo("V1.0");
					head.setVerName("��Ʒ��������");
					head.setIsLatestVer(true);
					// �½�һ����¼
					ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
					entry.setIndexValue(buildArea);
					entry.setParent(head);
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
					entry.setApportionType(apport);
					head.getEntries().add(entry);
					IObjectPK pk = ProjectIndexDataFactory.getRemoteInstance().addnew(head);
					head.setId(BOSUuid.read(pk.toString()));
				} else {
					// �ڿ���������Ѿ�����
					ProjectIndexDataEntryInfo entry = new ProjectIndexDataEntryInfo();
					entry.setIndexValue(buildArea);
					entry.setParent(head);
					ApportionTypeInfo apport = new ApportionTypeInfo();
					apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
					entry.setApportionType(apport);
					ProjectIndexDataEntryFactory.getRemoteInstance().addnew(entry);
				}
			}

			// �������=��Ʒ�����������ֵ
			builder.clear();
			BigDecimal dif = FDCHelper.toBigDecimal(oldInfo.getBigDecimal("buildAreaDif"));
			builder.appendSql("update T_FDC_ProjectIndexDataEntry set findexvalue=" + dif.floatValue() + "+isNull(( ");
			builder.appendSql("select sum(entry.findexvalue) from T_FDC_ProjectIndexDataEntry entry  ");
			builder.appendSql("inner join T_FDC_ProjectindexData head on head.fid=entry.fparentid and head.fprojOrOrgID=? ");
			builder.appendSql("and fprojectStage=? and fisLatestVer=1 and fproductTypeid is not null  ");
			builder.appendSql("where fapportionTypeid=? ");
			builder.appendSql("),0)  ");
			builder.appendSql("where  fparentid=? and fapportionTypeid=?");

			builder.addParam(head.getProjOrOrgID().toString());
			builder.addParam(head.getProjectStage().getValue());
			builder.addParam(ApportionTypeInfo.buildAreaType);
			builder.addParam(head.getId().toString());
			builder.addParam(ApportionTypeInfo.buildAreaType);
			builder.execute();
		}

	}

	protected void setBuildAreaDif(ProjectIndexDataInfo old_indexDataInfo) throws Exception {
		if(!isDisplayAreaIndex()){
			return;
		}
		// �õ�֮ǰ�Ľ��������ֵ
		if (old_indexDataInfo != null && old_indexDataInfo.getId() == null) {
			// old_indexDataInfo.put("buildAreaDif", FDCHelper.ZERO);
			// old_indexDataInfo=(ProjectIndexDataInfo)getDataObject();
			if(old_indexDataInfo.getEntries() != null)
			{
				for (Iterator iter = old_indexDataInfo.getEntries().iterator(); iter.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter.next();
					if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.sellAreaType)) {
						entry.setIndexValue(FDCHelper.ZERO);
					}
					if (entry.getApportionType().getId().toString().equals(ApportionTypeInfo.buildAreaType)) {
						entry.setIndexValue(FDCHelper.ZERO);
					}
					
				}
			}
		}

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select (isNull(t1.total,0)-isNull(t2.total2,0)) as dif from  ");
		builder
				.appendSql("(select findexvalue as total from T_FDC_ProjectIndexDataEntry entry inner join T_FDC_ProjectindexData head on head.fid=entry.fparentid and head.fprojOrOrgID=? and fprojectStage=? and fisLatestVer=1 and fproductTypeid is null where fapportionTypeid=?  )t1 , ");
		builder
				.appendSql("(select sum(entry.findexvalue) as total2 from T_FDC_ProjectIndexDataEntry entry inner join T_FDC_ProjectindexData head on head.fid=entry.fparentid and head.fprojOrOrgID=? and fprojectStage=? and fisLatestVer=1 and fproductTypeid is not null where fapportionTypeid=? )t2 ");
		if(old_indexDataInfo!= null && old_indexDataInfo.getProjOrOrgID() != null)
		{
			builder.addParam(old_indexDataInfo.getProjOrOrgID().toString());
		}
		else
		{
			builder.addParam("");
		}
		
		if(old_indexDataInfo != null && old_indexDataInfo.getProjectStage() != null)
		{
			builder.addParam(old_indexDataInfo.getProjectStage().getValue());
		}
		else
		{
			builder.addParam("");
		}
		builder.addParam(ApportionTypeInfo.buildAreaType);

		if(old_indexDataInfo!=null && old_indexDataInfo.getProjOrOrgID() != null)
		{
			builder.addParam(old_indexDataInfo.getProjOrOrgID().toString());
		}
		else
		{
			builder.addParam("");
		}
		
		if(old_indexDataInfo != null && old_indexDataInfo.getProjectStage() != null)
		{
			builder.addParam(old_indexDataInfo.getProjectStage().getValue());
		}
		else
		{
			builder.addParam("");
		}
		builder.addParam(ApportionTypeInfo.buildAreaType);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() == 1) {
			rowSet.next();
			old_indexDataInfo.put("buildAreaDif", FDCHelper.toBigDecimal(rowSet.getBigDecimal("dif"), 2));
		} else {
			old_indexDataInfo.put("buildAreaDif", FDCHelper.ZERO);
		}

		if (FDCHelper.toBigDecimal(old_indexDataInfo.getBigDecimal("buildAreaDif")).signum() <= 0) {
			old_indexDataInfo.put("buildAreaDif", FDCHelper.ZERO);
		}
	}

	/**
	 * 
	 * ����������ߵ�����������ѡ��仯ʱ��ȱʡ�������ṩĬ��ʵ�֣���ͬ״̬Ϊ��ˣ�������Ը��ǣ����û��������ҲҪ����һ��new
	 * FilterInfo()������ֱ�ӷ���null��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-9-5
	 *               <p>
	 */
	protected FilterInfo getSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();

		String projOrOrgId = getSelectProjOrOrgId();

		if (projOrOrgId != null) {
			filterItems.add(new FilterItemInfo("projOrOrgId", projOrOrgId));
		}

		/*
		 * ��Ŀ����
		 */
		filterItems.add(new FilterItemInfo("projectStage", getProjectStage().getValue()));

		/*
		 * ��Ʒ����
		 */
		if (comboProductType.getSelectedItem() != null) {
			Object selectObj = comboProductType.getSelectedItem();
			if (selectObj instanceof ProductTypeInfo) {
				ProductTypeInfo productType = (ProductTypeInfo) selectObj;
				filterItems.add(new FilterItemInfo("productType.id", productType.getId().toString()));
			} else {
				filterItems.add(new FilterItemInfo("productType", null));
			}
		}
		filterItems.add(new FilterItemInfo("aimMeasureID",null));
		/*
		 * ָ��������
		 */
		// if (getTypeSelectedTreeNode() != null
		// && getTypeSelectedTreeNode().getUserObject() instanceof TreeBaseInfo)
		// {
		// TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)
		// getTypeSelectedTreeNode()
		// .getUserObject();
		// filterItems.add(new FilterItemInfo(
		// "entries.targetType.longNumber", typeTreeNodeInfo
		// .getLongNumber()
		// + "%", CompareType.LIKE));
		// }
		return filter;
	}

	protected String getSelectProjOrOrgId() {
		/*
		 * ������Ŀ��
		 */
		String projOrOrgId = null;
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			Object userObj = getProjSelectedTreeNode().getUserObject();
			if (userObj instanceof OrgStructureInfo) {
				OrgStructureInfo orgStru = (OrgStructureInfo) userObj;
				projOrOrgId = orgStru.getUnit().getId().toString();
			} else {
				CurProjectInfo curProject = (CurProjectInfo) userObj;
				projOrOrgId = curProject.getId().toString();
			}

		}
		return projOrOrgId;
	}

	/**
	 * ���������ò���KDLayout���ֵ�������"OriginalBounds"�ͻ����ԡ�KDLayout���˼������˾��Բ��ַ�ʽ��û�п���Java�д�����Բ��ֵ������
	 * ������UI�ķǳ����������ط���public void initUIContentLayout()
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-8-28
	 *               <p>
	 */
	public void setContainerLayout() {
		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10, 250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10, 733, 609));
	}

	/**
	 * ���������ز���KDLayout���ֵ�������"OriginalBounds"�ͻ����ԡ�KDLayout���˼������˾��Բ��ַ�ʽ��û�п���Java�д�����Բ��ֵ������
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-08-28
	 *               <p>
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();

		setContainerLayout();
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (notChange) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("notchange"));
			SysUtil.abort();
		}
		final ProjectIndexDataInfo info = (ProjectIndexDataInfo) getDataObject();
		if (info.getId() == null) {
			// ����Ƿ��Ѿ�����
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("projOrOrgId", info.getProjOrOrgID().toString());
			filter.appendFilterItem("projectStage", info.getProjectStage().getName());
			filter.appendFilterItem("productType.id", info.getProductType() != null ? info.getProductType().getId() : null);
			if (ProjectIndexDataFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showWarning(this, "ָ���Ѿ����ڣ���ˢ�º��������޸�");
				SysUtil.abort();
			}
		} else {
			// ����Ƿ��Ѿ�����
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("id", info.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if (isDisplayAreaIndex()&&ProjectIndexDataFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showWarning(this, "ָ���Ѿ�������������");
				SysUtil.abort();
			}
		}
		String proOrOrgId=info.getProjOrOrgID().toString();
		String productTypeId = info.getProductType() != null ? info.getProductType().getId().toString(): null;
		
		Map ver = ProjectIndexDateVersionUI.showMntDialogWindow(this, info);
		if(ver!=null){
			save(ver);
			showSaveOkMsg();
			selectChange();
		}
	}

	protected void showSaveOkMsg() {
		String okMsg = "����ɹ���";
		actionAudit.setEnabled(true);
		//ָ��ˢ�µĹ��������ָ������ṩ
		//2008-11-20 ָ��ˢ�¹��ܷſ�
		try {
			//�Ƿ����� ָ������Ƿ���ʾ���ָ�� ����
			if(isDisplayAreaIndex()){
				if (getProjectStage() == ProjectStageEnum.DYNCOST&&changeApportions.size()>0) {
					String idxRefMsg = okMsg + "\n\n���ڳɱ���̯�Ķ�̬�ɱ�ָ���Ѿ��޸�,�Ƿ����ָ��ˢ��?";
					int choose = MsgBox.showConfirm2New(this, idxRefMsg);
					if (choose == MsgBox.YES) {
						idxRefresh();
					}
				} 
//				else {
//					MsgBox.showWarning(this, okMsg);
//				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}

		MsgBox.showWarning(this, okMsg);
	}
	
	protected void idxRefresh(){
		if(getProjectStage() != ProjectStageEnum.DYNCOST){
			return;
		}
		String prjId = getSelectProjOrOrgId();
		String productId = null;
		if (comboProductType.getSelectedItem() instanceof ProductTypeInfo) {
			productId = ((ProductTypeInfo) comboProductType.getSelectedItem()).getId().toString();
		}
		//ԭ�����ѷ���
//		ProjectIndexDataUtils.idxRefresh(this, prjId, productId, changeApportions);
		Map param=new HashMap();
		param.put("companyId", SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		param.put("projId", prjId);
		List list=new ArrayList();
		Map dataMap=new HashMap();
		dataMap.put("projId", prjId);
		dataMap.put("productId", productId);
		dataMap.put("apportionsId", changeApportions);
		list.add(dataMap);
		if(productId!=null){
			Map dataMap2 = new HashMap();
			dataMap2.put("projId", prjId);
			dataMap2.put("productId", null);
			dataMap2.put("apportionsId", changeApportions);
			list.add(dataMap2);
		}
		param.put("refreshSrcList",list);
		ProjectIndexDataUtils.idxRefresh(this, param);
	}
	
	protected void checkModify() throws EASBizException, BOSException {
		if (!notChange) {
			int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Save_Exit"));

			if (result == KDOptionPane.YES_OPTION) {
				btnSave.doClick();
				abort();
			}
		}
	}

	protected void tblEntries_editStopping(KDTEditEvent e) throws Exception {

		if (e.getColIndex() == getDetailTable().getColumnIndex(INDEX_VALUE) || e.getColIndex() == getDetailTable().getColumnIndex(REMARK)) {
			Object oldValue = e.getOldValue();
			Object value = e.getValue();
			if (oldValue != null && value == null) {
				notChange = false;
			}
			if (oldValue == null && value != null) {
				notChange = false;
			}

			if (oldValue != null && value != null && !oldValue.equals(value)) {
				notChange = false;
			}

			// BigDecimal b1 = (BigDecimal)oldValue;
			// BigDecimal b2 = (BigDecimal)value;
			//    		
			// if(b1 != null && b2 != null && b1.compareTo(b2) != 0) {
			// notChange = false;
			// }
			addchangeApportionType(e.getRowIndex());

		}
	}
	
	protected void addchangeApportionType(int rowIndex){
		if(!notChange&&getProjectStage() == ProjectStageEnum.DYNCOST){
			ICell cell=tblEntries.getCell(rowIndex,APPORTION_TYPE_ID);
			ICell costSplitCell=tblEntries.getCell(rowIndex,APPORTION_TYPE_ISCOSTSPLIT);
			Boolean isCostSplit=(Boolean)costSplitCell.getValue();
			if(isCostSplit!=null&&isCostSplit.booleanValue()){
				if(cell!=null&&cell.getValue()!=null){
					changeApportions.add(cell.getValue());
				}
			}
		}
	}
	protected void setTableLocked(boolean locked) {
		getDetailTable().getStyleAttributes().setLocked(locked);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		selectChange();
	}

	protected boolean isShowAttachmentAction() {
		return false;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		btnSave.setIcon(FDCClientHelper.ICON_SAVE);
		menuItemSave.setIcon(FDCClientHelper.ICON_SAVE);
		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		btnRefresh.setIcon(FDCClientHelper.ICON_REFRESH);
		menuItemRefresh.setIcon(FDCClientHelper.ICON_REFRESH);
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemRefresh.setAccelerator(KeyStroke.getKeyStroke("F5"));
	}

	protected void setButtonInfo() {
		ProjectIndexDataInfo pid = (ProjectIndexDataInfo) getDataObject();

		if (pid.getVerNo() != null && pid.getVerNo().length() > 0)
			txtVerNo.setText(pid.getVerNo());
		if (pid.getVerName() != null && pid.getVerName().length() > 0) {
			ProjectIndexVerTypeEnum verName = ProjectIndexVerTypeEnum.getEnum(pid.getVerName());
			if(verName==null){
				txtVerName.setText(pid.getVerName());
			}else{
				txtVerName.setText(verName.getAlias());
			}
		}
		if (pid.getCreator() != null) {
			txtCreator.setText(pid.getCreator().getName());
		}
		if (pid.getCreateTime() != null) {
			txtCreateTime.setText(FDCClientHelper.formateDate(pid.getCreateTime()));
		}

		if (pid.getAuditor() != null) {
			txtAuditor.setText(pid.getAuditor().getName());
		} else {
			txtAuditor.setText("");
		}
		if (pid.getAuditTime() != null) {
			txtAuditDate.setText(FDCClientHelper.formateDate(pid.getAuditTime()));
		} else {
			txtAuditDate.setText("");
		}
		if(pid.getMeasureStage()!=null){
			this.txtMeasureStage.setText(pid.getMeasureStage().getName());
		}else{
			this.txtMeasureStage.setText("");
		}
		this.txtVerRemark.setText(pid.getVerRemark());
	}

	public boolean checkBeforeWindowClosing() {
		if (!notChange) {
			int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Save_Exit"));

			if (result == KDOptionPane.YES_OPTION) {
				// ��doclick���Ե���Ȩ��
				btnSave.doClick();

				// ActionEvent event = new ActionEvent(btnSave,
				// ActionEvent.ACTION_PERFORMED, btnSave
				// .getActionCommand());
				//				
				//				
				// actionSave.actionPerformed(event);

			} else if (result == KDOptionPane.NO_OPTION) {

				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
		return super.checkBeforeWindowClosing();
	}

	protected String getAmountColKey() {
		return INDEX_VALUE;
	}

	public KDTable getDetailTable() {
		return tblEntries;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}

	protected IObjectValue createNewData() {
		ProjectIndexDataInfo info = new ProjectIndexDataInfo();
		return info;
	}

	protected IFDCBill getFDCBillInterface() throws Exception {
		return ProjectIndexDataFactory.getRemoteInstance();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		selectChange();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// ���޶��汾��������з�����
		final ProjectIndexDataInfo info = (ProjectIndexDataInfo) getDataObject();
		if (info == null)
			return;
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql("select count(*) as count from t_fdc_projectindexdata where FProjOrOrgID =? and FProjectStage =?  and FProductTypeID ");
//		builder.addParam(info.getProjOrOrgID().toString());
//		builder.addParam(stage.getName());
//		if (info.getProductType() != null) {
//			builder.appendSql(" = ");
//			builder.appendParam(info.getProductType().getId().toString());
//		} else {
//			builder.appendSql(" is null ");
//		}
//		IRowSet rowSet = builder.executeQuery();
//		if (rowSet != null && rowSet.size() == 1) {
//			rowSet.next();
//			if (rowSet.getInt("count") > 1) {
//				MsgBox.showWarning(this, "��ָ���Ѿ����޶�������������");
//				return;
//			}
//		}
		if(!info.getVerNo().equals("V1.0")){
			MsgBox.showWarning(this, "��ָ���Ѿ����޶�������������");
			return;
		}
		super.actionUnAudit_actionPerformed(e);
		selectChange();
	}

	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {
		// TODO Auto-generated method stub
		super.checkBeforeAuditOrUnAudit(state, warning);

	}

	ProjectStageEnum stage = null;

	protected void comboProjStage_itemStateChanged(ItemEvent e) throws Exception {
		stage = (ProjectStageEnum) e.getItem();
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			warnForSave();

		}
		super.comboProjStage_itemStateChanged(e);

		if (e.getStateChange() == ItemEvent.SELECTED) {

			selectChange();
		}
	}

	private boolean displayAreaIdx=false;
	private boolean hasInit=false;
	protected boolean isDisplayAreaIndex() throws EASBizException, BOSException{
		//���������Ƿ���ʾ���ָ��
		if(!hasInit){
			HashMap param = FDCUtils.getDefaultFDCParam(null,null);
			if(param.get(FDCConstants.FDC_PARAM_PROJECTINDEX)!=null){
				displayAreaIdx = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PROJECTINDEX).toString()).booleanValue();
			}
			hasInit=true;
		}
		return displayAreaIdx;
	}
	
    protected KDTable getTableForCommon()
    {
        return tblEntries;
    }
}