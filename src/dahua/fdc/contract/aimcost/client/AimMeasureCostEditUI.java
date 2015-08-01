/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectMultiPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.util.PermissionHelper;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryFactory;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.util.FDCSplitBillUtil;
import com.kingdee.eas.fdc.basedata.AccountStageCollection;
import com.kingdee.eas.fdc.basedata.AccountStageFactory;
import com.kingdee.eas.fdc.basedata.AccountStageInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.MeasureIndexCollection;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.util.CostAccountHelper;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.basedata.util.client.RENoteDataProvider;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.popup.MenuSection;
import com.kingdee.eas.framework.client.popup.PopupMenuManager;
import com.kingdee.eas.framework.config.IObjectMultiPKBuilder;
import com.kingdee.eas.framework.config.UserPreferenceData;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 注:用T_AIM_Measureentry上的simpleName存储指标,number存储汇总表的六类公摊分摊方式 output class name
 * 
 * 目标成本测算 编辑界面
 */
public class AimMeasureCostEditUI extends AbstractAimMeasureCostEditUI {

	/**
	 * 目标成本测算  页签 六类公摊及期间费 改名为 公摊成本 BT681118
	 */
	private static final String Shared_Cost = "公摊成本";

	/**
	 * 调整前合价
	 */
	private static final String COL_SUM_PRICE = "sumPrice"; 

	/**
	 * 工程量
	 */
	private static final String COL_WORKLOAD = "workload";

	/**
	 * 单价
	 */
	private static final String COL_PRICE = "price";

	private List tables = null;

	private CurProjectInfo project = null;

	private Map measureCostMap = new HashMap();

	private TreeModel costAcctTree = null;

	public Map apportionMap = new HashMap();

	private Map accoutStageMap = new HashMap();
	/** 规划指标表 */
	private PlanIndexTable planIndexTable = null;

	/** 测算汇总表 */
	private MeasureCollectTable measureCollectTable = null;

	// 是否包含禁用科目
	protected boolean isAll = false;

	private boolean isFirstLoad = true;

	private MeasureStageInfo meaStaInfo = null;

	CoreUI MeasureIncomeEditUI = null;
	List lockIds = new ArrayList();
	List lockId2s = new ArrayList();
	boolean hasMutex = true;

	private MeasureIncomeInfo miInfo=null;
	//有分科科目的子科目  add by lihaiou, 2013.08.07 fix bug R130719-0178
	private Map hasSplitCost = new HashMap();
	// 有非分摊科目的子科目
	private Map hasNoSplitCost = new HashMap();

	// modify end
	/**
	 * output class constructor
	 */
	public AimMeasureCostEditUI() throws Exception {
		super();
	}

	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if (destroyWindow) {
			// 释放
			if ("RELEASEALL".equals(getOprtState()) && hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, lockIds);
					if (lockId2s.size() > 0)
						FDCClientUtils.releaseDataObjectLock(MeasureIncomeEditUI, lockId2s);

				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}

			// 释放资源
			if (true)
				return true;
			this.removeAll();
			costAcctTree = null;
			tables = null;
			project = null;
			measureCollectTable.clear();
			measureCollectTable = null;
			planIndexTable.clear();
			planIndexTable = null;
			apportionMap = null;
			measureCostMap = null;
			tHelper = null;
			this.plTables = null;
			FDCClientHelper.clearMenuKeyboardHelper();
		}
		return destroyWindow;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
		Object objStage = comboMeasureStage.getSelectedItem();
		if (objStage instanceof MeasureStageInfo) {
			cost.setMeasureStage((MeasureStageInfo) objStage);
		} else {
			cost.setMeasureStage(null);
		}
		cost.setVersionName(this.txtVersionName.getText());
		cost.setVersionNumber(this.txtVersionNumber.getText());
		Object objPrj = prmtProject.getValue();
		if (cost.getProject() == null && objPrj instanceof CurProjectInfo) {
			cost.setProject((CurProjectInfo) objPrj);
		}

		Object objPrjType = prmtProjectType.getValue();
		if (objPrjType instanceof ProjectTypeInfo) {
			cost.setProjectType((ProjectTypeInfo) objPrjType);
		} else {
			cost.setProjectType(null);
		}
		// cost.getCostEntry().clear();
		try {
			handleAimCostAccredit(cost);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		Map splitTypeMap = measureCollectTable.getSplitTypes();
		for (int i = 3; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0).getUserObject();
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if (isDetailAcctRow(row) && !isDetailAcctHasInput(row)) {
						boolean isEmpty = true;
						for (int k = 3; k < table.getColumnCount(); k++) {
							if (!FDCHelper.isEmpty(row.getCell(k).getValue())) {
								isEmpty = false;
								break;
							}
						}
						if (isEmpty) {
							continue;
						}
					}
					MeasureEntryInfo entry = (MeasureEntryInfo) row.getUserObject();
					entry.setEntryName((String) row.getCell("acctName").getValue());
					entry.setProduct(product);
					/*
					 * entry.setApportionType((ApportionTypeInfo) row.getCell( "indexName").getValue());
					 */
					Object obj = row.getCell("indexName").getValue();
					if (obj instanceof Item) {
						Item item = (Item) obj;
						entry.setSimpleName(item.key);
						entry.setIndexName(item.toString());
						entry.setIndexValue(FDCHelper.toBigDecimal(row.getCell("index").getValue()));
						// KDComboBox
						// box=(KDComboBox)row.getCell("indexName").getEditor().getComponent();
						// box.getSelectedIndex();
					}
					entry.setCoefficientName((String) row.getCell("coefficientName").getValue());
					entry.setCoefficient((BigDecimal) row.getCell("coefficient").getValue());
					final Object value = row.getCell("unit").getValue();
					if (value instanceof IObjectValue) {
						entry.setUnit((MeasureUnitInfo) value);
					} else if (value != null) {
						entry.setName(value.toString());
					}
					entry.setWorkload((BigDecimal) row.getCell(COL_WORKLOAD)
							.getValue());
					entry
							.setPrice((BigDecimal) row.getCell(COL_PRICE)
									.getValue());
					entry.setCostAmount((BigDecimal) row.getCell(COL_SUM_PRICE)
							.getValue());
					entry.setProgram((String)row.getCell("program").getValue());
					entry.setDesc((String)row.getCell("desc").getValue());
					entry.setChangeReason((String)row.getCell("changeReason").getValue());
					entry.setDescription((String) row.getCell("description")
							.getValue());
					if(entry.getCostAccount().getType()==CostAccountTypeEnum.SIX){
						Object splitType=splitTypeMap.get(entry.getCostAccount().getId().toString());
						if(splitType!=null){
							entry.setNumber(splitType.toString());
						}
					}
					entry.setAdjustCoefficient((BigDecimal) row.getCell("adjustCoefficient").getValue());
					entry.setAdjustAmt((BigDecimal) row.getCell("adjustAmt").getValue());
					entry.setAmount((BigDecimal) row.getCell("amount").getValue());
					cost.getCostEntry().add(entry);
				}
			}
		}
		// 当本版本是最终版本时1.反审批修改,界面保持打开2.将另一版本审批为最终版本,3,再保存1.的数据后存在两个最终版本
		if (cost != null && cost.getId() != null) {
			cost.setIsLastVersion(isLastVersion(cost.getId().toString()));
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		actionAddRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		actionDeleteRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		this.btnImportApportion.setIcon(EASResource.getIcon("imgTbtn_input"));
		actionImportTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_input"));
		menuEdit.setEnabled(true);
		menuEdit.setVisible(true);
		actionExportAllToExcel.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionExportAllToExcel.setVisible(true);
		actionExportAllToExcel.setEnabled(true);

		menuItemExportAll.setMnemonic('E');
		menuItemExportAll.setText(menuItemExportAll.getText() + "(E)");
		menuItemExportAll.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));

		menuItemImportTemplate.setMnemonic('T');
		menuItemImportTemplate.setText(menuItemImportTemplate.getText() + "(T)");
		menuItemImportTemplate.setAccelerator(KeyStroke.getKeyStroke("ctrl shift T"));

		menuItemAddRow.setMnemonic('A');
		menuItemAddRow.setText(menuItemAddRow.getText() + "(A)");
		menuItemAddRow.setAccelerator(KeyStroke.getKeyStroke("alt A"));

		menuItemDeleteRow.setMnemonic('D');
		menuItemDeleteRow.setText(menuItemDeleteRow.getText() + "(D)");
		menuItemDeleteRow.setAccelerator(KeyStroke.getKeyStroke("alt D"));
		chkMenuItemSubmitAndAddNew.setEnabled(false);
		chkMenuItemSubmitAndAddNew.setVisible(false);
		// by Cassiel_peng 2009-8-19
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.menuItemSave.setVisible(true);
		// 工程项目不让选择，必须新增的时候带过来，否者请重新生成
		this.prmtProject.setEnabled(false);
	}

	protected IObjectValue createNewData() {
		MeasureCostInfo cost = new MeasureCostInfo();
		String orgId = (String) getUIContext().get("orgId");
		String prjId = (String) getUIContext().get("projectId");
		Boolean isAimMeasure = (Boolean) getUIContext().get("isAimMeasure");
		cost.setIsAimMeasure(isAimMeasure.booleanValue());
		if (getUIContext().get("MeasureEditData") instanceof MeasureCostInfo) {
			MeasureCostInfo editData1 = (MeasureCostInfo) getUIContext().get("MeasureEditData");
			cost.putAll(editData1);
			getUIContext().remove("MeasureEditData");
			return cost;
		}

		MeasureStageInfo lastStageInfo = null;
		try {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("name");
			selector.add("number");
			selector.add("longNumber");
			selector.add("projectType.*");
			selector.add("curProjProductEntries.isAccObj");
			if (prjId != null) {
				project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)), selector);
				cost.setProject(this.project);
				cost.setProjectType(project.getProjectType());
				lastStageInfo = AimCostClientHelper.getLastMeasureStage(project, isAimMeasure.booleanValue());
				
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select top 1 fdescription,fid from t_aim_measurecost where FPROJECTID ='"+prjId+"' order by fcreatetime desc");
				IRowSet executeQuery = builder.executeQuery();
				if(executeQuery.next()){
					String string = executeQuery.getString("fdescription");
					if(!StringUtil.isEmptyString(string)){
						cost.setDescription(executeQuery.getString("fdescription"));
					}else{
						cost.setDescription(executeQuery.getString("fid"));
					}
				}else{
					cost.setDescription(BOSUuid.create(cost.getBOSType()).toString());
				}
				
			}
			MeasureCostVersionHandler version = new MeasureCostVersionHandler(orgId, prjId, isAimMeasure.booleanValue(), lastStageInfo);
			cost.setVersionNumber(MeasureCostVersionHandler.getNextVersion(version.getLastVersion()));
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		FullOrgUnitInfo org = new FullOrgUnitInfo();
		org.setId(BOSUuid.read(orgId));
		cost.setOrgUnit(org);
		if (lastStageInfo == null) {
			// 系统默认三个阶段，默认最后一阶段 by hpw 2010-9-1
			lastStageInfo = (MeasureStageInfo) comboMeasureStage.getItemAt(comboMeasureStage.getItemCount() - 1);
		}
		cost.setMeasureStage(lastStageInfo);
		return cost;
	}

	/**
	 * 新增目标成本测算时，把上一版本数据初始化至当前新增版本
	 * 
	 * @return
	 */
	private MeasureCostInfo initMeasureInfo(MeasureCostInfo mcInfo) {
		mcInfo.setRecenseDate(null);// 修订日期置空
		mcInfo.setState(null);// 状态置空
		mcInfo.setMainVerNo(1);// 是主版本号
		mcInfo.setSubVerNo(0);// 非子版本号
		if(mcInfo!=null&&mcInfo.getId()!=null)
		mcInfo.setSrcMeasureCostId(mcInfo.getId().toString());
		MeasureStageInfo measureStageInfo = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
		if (measureStageInfo != null) {
			mcInfo.setMeasureStage(measureStageInfo);// 测算阶段
			MeasureCostVersionHandler version;
			try {
				version = new MeasureCostVersionHandler(
						mcInfo.getOrgUnit().getId().toString(),mcInfo.getProject().getId().toString(), mcInfo.isIsAimMeasure(),measureStageInfo);
				mcInfo.setVersionNumber(MeasureCostVersionHandler
						.getNextVersion(version.getLastVersion()));
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
			
			AccountStageCollection asCol = getAcctStaCol(measureStageInfo);
			MeasureEntryCollection meCol = mcInfo.getCostEntry();
			ConstructPlanIndexEntryCollection ceCol = mcInfo.getConstrEntrys();
			if (meCol != null && asCol != null && meCol.size() != 0 && asCol.size() != 0) {
				processAcctCol(asCol);
				for (int i = 0; i < meCol.size(); i++) {
					MeasureEntryInfo meInfo = meCol.get(i);
					meInfo.setId(null);
					meInfo.setHead(null);
					String meLongNumber = meInfo.getCostAccount().getLongNumber();
					boolean hasChange = true;
					for (int j = 0; j < asCol.size(); j++) {
						AccountStageInfo asInfo = asCol.get(j);
						String caLongNumber = asInfo.getCostAccount().getLongNumber();
						if (caLongNumber.length() > meLongNumber.length()) {
							if (caLongNumber.substring(0, meLongNumber.length()).equals(meLongNumber)) {
								if (hasChange) {
									CostAccountInfo caInfo = asInfo.getCostAccount();
									meInfo.setCostAccount(caInfo);
									hasChange = false;
								}
							}
						}
					}
				}
			}
			//建造标准
			if(ceCol!=null && ceCol.size()!=0){
				for(int i=0;i<ceCol.size();i++){
					ConstructPlanIndexEntryInfo ceInfo = ceCol.get(i);
					ceInfo.setId(null);
					ceInfo.setParent(null);
				}
			}
			if(ceCol!=null && ceCol.size()!=0){
				for(int i=0;i<ceCol.size();i++){
					ConstructPlanIndexEntryInfo ceInfo = ceCol.get(i);
					ceInfo.setId(null);
					ceInfo.setParent(null);
				}
			}
		}
		return mcInfo;
	}

	/**
	 * 获取测算阶段与成本科目对应关系值为1的成本科目信息
	 * 
	 * @return
	 * @throws BOSException
	 * @throws BOSException
	 */
	private AccountStageCollection getAcctStaCol(MeasureStageInfo measureStageInfo) {
		AccountStageCollection asCol = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.*");
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("costAccount.longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id", measureStageInfo.getId().toString(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("value", new Integer(1), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.id", project.getId().toString(), CompareType.EQUALS));
		view.setFilter(filter);
		try {
			asCol = AccountStageFactory.getRemoteInstance().getAccountStageCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return asCol;
	}

	/**
	 * 处理从关系表中获取到的成本科目，只要最明细节点
	 */
	private void processAcctCol(AccountStageCollection asCol) {
		for (int i = 0; i < asCol.size(); i++) {
			if (i == asCol.size() - 1) {// 最后一行肯定为最明细节点，不作处理
				break;
			}
			AccountStageInfo oneInfo = asCol.get(i);
			AccountStageInfo twoInfo = asCol.get(i + 1);
			if (twoInfo.getCostAccount().getLevel() > oneInfo.getCostAccount().getLevel()) {
				asCol.remove(oneInfo);
				i--;
			}
		}
	}

	/**
	 * 获取某工程项目最后增加（最后新增版本）的目标成本测算分录数据
	 * 
	 * @return
	 * @throws BOSException
	 */
	private MeasureCostInfo getLastStage() {
		if (meaStaInfo != null) {
			if (project != null) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.getSelector().add("id");
				filter.getFilterItems().add(new FilterItemInfo("project", project.getId().toString(), CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("measureStage.id", meaStaInfo.getId().toString(), CompareType.EQUALS));
				view.setFilter(filter);
				SorterItemInfo sii = new SorterItemInfo();
				sii.setPropertyName("versionNumber");
				sii.setSortType(SortType.DESCEND);
				view.getSorter().add(sii);
				MeasureCostCollection mcCol;
				MeasureCostInfo mcInfo = null;
				try {
					mcCol = MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
					if (mcCol.size() != 0) {
						mcInfo = mcCol.get(0);
						String mcId = mcInfo.getId().toString();
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("*");
						sels.add("measureStage.number");
						sels.add("measureStage.name");
						sels.add("costEntry.costAccount.*");
						sels.add("costEntry.costAccount.curProject.*");
						sels.add("costEntry.*");
						sels.add("costEntry.unit.*");
						sels.add("constrEntrys.*");
						sels.add("constrEntrys.productType.*");
						mcInfo = MeasureCostFactory.getRemoteInstance().getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(mcId)), sels);
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
				return mcInfo;
			}
		}
		return null;
	}

	/**
	 * 获取某一阶段的前一阶段
	 * 
	 * @param info
	 * @return
	 */
	private MeasureStageInfo getBeforeStage(MeasureStageInfo info) {
		String id = info.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1), CompareType.EQUALS));
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("number");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		try {
			MeasureStageCollection msCol = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
			for (int i = 0; i < msCol.size(); i++) {
				MeasureStageInfo msInfo = msCol.get(i);
				if (msInfo.getId().toString().equals(id)) {
					if (i == 0) {
						return null;
					} else {
						MeasureStageInfo measureStageInfo = msCol.get(i - 1);
						return measureStageInfo;
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MeasureCostFactory.getRemoteInstance();
	}

	private void addPanel() throws Exception {
		ChangeListener[] changeListeners = plTables.getChangeListeners();
		for (int i = 0; i < changeListeners.length; i++) {
			plTables.removeChangeListener(changeListeners[i]);
		}
		this.plTables.removeAll();
		Object obj = getUIContext().get(UIContext.ID);
		if (editData.getId() != null) {
			obj = editData.getId();
		}

		tables = new ArrayList();
		KDTable table = null;// new KDTable();
		// 添加汇总表及规划指标表
		measureCollectTable = new MeasureCollectTable(this);
		table = measureCollectTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(table, "测算汇总表");
		planIndexTable = new PlanIndexTable(getInitPlanIndexInfo(), this);
		table = planIndexTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		// ((TablePreferencesHelper)tHelper).getActionSave(table);
		this.plTables.add(planIndexTable.getContentPanel(), "规划指标表");

		table = planIndexTable.getConstructTable();
		this.tables.add(table);
		this.plTables.add(table, "建造标准");

		PlanIndexInfo info = planIndexTable.getPlanIndexInfo();

		// 公摊及产品
		table = new KDTable();
		table.setName(Shared_Cost);
		this.tables.add(table);
		FDCTableHelper.addTableMenu(table);
		FDCTableHelper.setColumnMoveable(table, true);
		this.initTable(table, CostAccountTypeEnum.SIX, null);

		BigDecimal amount = planIndexTable.getAllSellArea(info);
		info.put("allSellArea", amount);
		amount = planIndexTable.getAllBuildArea(info);
		info.put("allBuildArea", amount);

		table.getHeadRow(0).getCell(0).setUserObject(info);
		this.fillTable(table);
		setUnionData(table);
		setLastRowEnable(table);
		this.plTables.add(table, Shared_Cost);
		table.checkParsed();

		for (int i = 0; i < info.getEntrys().size(); i++) {
			PlanIndexEntryInfo entry = info.getEntrys().get(i);
			if (entry.getProduct() != null) {
				table = addProductTypeTable(entry.getProduct());

				// if(table!=null) table.setUserObject(entry);

			}
		}
		// 从界面取数
		measureCollectTable.refresh();
		/*
		 * AimProductTypeGetter getter = new AimProductTypeGetter(project.getId() .toString()); Map prodcutMap =
		 * getter.getSortedProductMap(); Set set = prodcutMap.keySet(); for (Iterator pIter = set.iterator(); pIter.hasNext();) {
		 * ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(pIter .next()); table = new KDTable(); this.tables.add(table);
		 * this.initTable(table, CostAccountTypeEnum.MAIN,product.getId().toString()); table.getHeadRow(0).setUserObject(product);
		 * this.fillTable(table); this.plTables.add(table, product.getName()); } for (int i = 0; i < tables.size(); i++) { KDTable aTable =
		 * (KDTable) tables.get(i); this.setUnionData(aTable); }
		 */
		for (int i = 0; i < changeListeners.length; i++) {
			plTables.addChangeListener(changeListeners[i]);
		}
	}

	public KDTable addProductTypeTable(ProductTypeInfo product) {
		boolean isadd = true;
		for (int i = 3; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			if (table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) {
				if (((ProductTypeInfo) table.getHeadRow(0).getUserObject()).getId().equals(product.getId())) {
					isadd = false;
					break;
				}
			}
		}
		if (!isadd)
			return null;

		KDTable table = new KDTable();
		this.tables.add(table);
		table.setName(product.getName());
		FDCTableHelper.setColumnMoveable(table, true);
		this.initTable(table, CostAccountTypeEnum.MAIN, product.getId().toString());
		table.getHeadRow(0).getCell(0).setUserObject(planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
		table.getHeadRow(0).setUserObject(product);
		try {
			this.fillTable(table);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		this.plTables.add(table, product.getName());

		KDTable constrTable = planIndexTable.getConstructTable();
		IRow headRow = constrTable.getHeadRow(0);
		boolean isHasAdd = true;
		// if(constrTable.getColumnCount()>4){
		for (int i = 4; i < constrTable.getColumnCount(); i++) {
			ProductTypeInfo type = (ProductTypeInfo) headRow.getCell(i).getUserObject();
			if (type.getId().toString().equals(product.getId().toString())) {
				isHasAdd = false;
				break;
			}
			// IColumn column = constrTable.addColumn();
			// column.setWidth(120);
			// column.setKey(product.getId().toString());
			// headRow.getCell(product.getId().toString()).setUserObject(product);
			// headRow.getCell(product.getId().toString()).setValue(product.getName());
		}
		// }
		if (isHasAdd) {
			IColumn column = constrTable.addColumn();
			column.setWidth(150);
			column.setKey(product.getId().toString());
			headRow.getCell(product.getId().toString()).setUserObject(product);
			headRow.getCell(product.getId().toString()).setValue(product.getName());
//			column.getStyleAttributes().setWrapText(true);
			PlanIndexTable.autoFitRowHeight(constrTable, product.getId().toString(), 1200);
		}

		this.setUnionData(table);
		FDCTableHelper.addTableMenu(table);
		return table;

	}

	public void addConstructIndexTable() {
		KDTable table = planIndexTable.getConstructTable();
		this.tables.add(table);
	}

	public void deleteProductTypeTable(ProductTypeInfo product) {
		for (int i = 3; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			if (table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) {
				if (((ProductTypeInfo) table.getHeadRow(0).getUserObject()).getId().equals(product.getId())) {
					tables.remove(i);
					// 以指标必须保证顺序,现在顺序变化了 by hpw
					// plTables.remove(i);
					plTables.remove(table);

					disableTableMenus(table);
					break;
				}
			}
		}
		
		//add by david_yang R110411-539 2011.04.19
		for(int j=4;j<planIndexTable.getConstructTable().getColumnCount();j++){
			IRow headRow = planIndexTable.getConstructTable().getHeadRow(0);
			if(headRow.getCell(j).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)headRow.getCell(j).getUserObject()).getId().equals(product.getId())){
					planIndexTable.getConstructTable().removeColumn(j);
					break;
				}
			}
		}
	}
	public void addTableChangeEnvent(final KDTable table) {
		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
			public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
				try {
					table_editStopped(table, e);
				} catch (Exception exc) {
					handUIExceptionAndAbort(exc);
				}
			}
		});
	}

	public void initTable(KDTable table, CostAccountTypeEnum type, String productId) {
		//初始化Table,的列
		initTableColumn(table);
		//初始化列对应的文本编辑框
		initColumnTextField(table);
		
		
		//初始化列对应的F7控件
		initColumnF7(table);
		//设置列的属性
		initColumnStyle(table, type, productId);
		setTemplateMeasureCostF7Editor(table);
		this.addTableChangeEnvent(table);
		
		addRightMenu(table);
		myTPHelper.setTablesToAppled(rbTables);
		if(rbTables.size()==1) {//所有表格共用一套设置；
			myTPHelper.setDefaultUserData(myTPHelper.getUserDataFromUI(true));
		}
	}

	MyTablePreferencesHelper myTPHelper = null;	
	List rbTables = new ArrayList();	//设置了右键的表格列表
	
	/**
	 * 为表格设置右键菜单
	 * @param table
	 */
	public void addRightMenu(KDTable table) {
		myTPHelper.addMenuToTable(table);
		
		table.setName("tblMain");
		String key = getMetaDataPK().getFullName() +"."+ table.getName();
		myTPHelper.getUiTables().put(table, key);
		rbTables.add(table);
	}
	
	/**
	 * 加载DB中的表格设置到各表
	 */
	public void loadUserConfigToTable() {
		String key = getMetaDataPK().getFullName() +".tblMain";
		UserPreferenceData userDBData = myTPHelper.getUserDataFromDB();
		if( userDBData != null && userDBData.getTableCurrentSetting(key) != null )
			myTPHelper.applyConfigFromData( userDBData.getTableCurrentSetting(key) ) ;
	}
	
	/**
	 * 重写以重设导出键的ACTION，去掉多余section
	 */
	public void addCommonMenusToTable(KDTable table) {
		PopupMenuManager mgr = this.createPopupMenuManager(table);
		MenuSection section = mgr.findMenuSection("table");
		if (section == null) {
			section = new MenuSection("table");
			mgr.addMenuSection(section);			
		}
		
		section = mgr.findMenuSection("export");
		if (section == null) {
			section = new MenuSection("export");
			mgr.addMenuSection(section);			
		}
		
		KDMenuItem menuExcel=new KDMenuItem((String)actionExportAllToExcel.getValue(ItemAction.NAME));
		menuExcel.setAction(actionExportAllToExcel);
		section.insertAfter(menuExcel);
	}
	
	 protected IObjectPK buildPK(){
        String uiClassName = getMetaDataPK().getFullName();
        Object schemaKey=getTablePreferenceSchemaKey();
        if(schemaKey!=null){
        	uiClassName=uiClassName+schemaKey;
        }
        IObjectPK pk = new ObjectMultiPK() ;
        pk.setKeyValue( "user" , SysContext.getSysContext().getCurrentUserInfo().getId()) ;
        pk.setKeyValue( "uiClassName" ,uiClassName) ;
		return pk;
	 }
	
	 /**
	  * 为使用MyTablePreferencesHelper而覆写
	  */
	 protected void initHelper(){
    	if (tHelper!=null)
    	{
    		return;
    	}
        tHelper = new MyTablePreferencesHelper( this ,
            	new IObjectMultiPKBuilder(){
        			public IObjectPK buildPK() {
        				return AimMeasureCostEditUI.this.buildPK();
        			}
        		}
        );
        myTPHelper = (MyTablePreferencesHelper) tHelper;
//	        tHelperPrint=tHelper;
	 }
	
	private void initColumnF7(KDTable table) {
		KDTextField textField;
		
		KDFormattedTextField formattedTextField;
		ICellEditor numberEditor;
		
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
//		formattedTextField.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(FDCHelper.MAX_TOTAL_VALUE));
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("amount").setEditor(numberEditor);

		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		//		formattedTextField.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(FDCHelper.MAX_TOTAL_VALUE));
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn(COL_SUM_PRICE).setEditor(numberEditor);
		table.getColumn("adjustAmt").setEditor(numberEditor);
		
		
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(6);
		formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
		formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("adjustCoefficient").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);
		
		
		// 单位只能选择,不能手工录入
		KDBizPromptBox f7Unit = new KDBizPromptBox() {
			
		};
		f7Unit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		f7Unit.setEditable(true);
		f7Unit.setDisplayFormat("$name$");
		f7Unit.setEditFormat("$number$");
		f7Unit.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Unit);
		table.getColumn("unit").setEditor(f7Editor);
		
	}

	private void initColumnStyle(KDTable table, CostAccountTypeEnum type,
			String productId) {
		

		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		Color lockColor = FDCTableHelper.cantEditColor;// new Color(0xF0AAD9);
		table.getColumn("acctNumber").getStyleAttributes().setBackground(lockColor);
		table.getColumn("index").getStyleAttributes().setLocked(true);
		table.getColumn("index").getStyleAttributes().setBackground(lockColor);
		table.getColumn("buildPart").getStyleAttributes().setLocked(true);
		table.getColumn("buildPart").getStyleAttributes().setBackground(lockColor);
		table.getColumn("sellPart").getStyleAttributes().setLocked(true);
		table.getColumn("sellPart").getStyleAttributes().setBackground(lockColor);
		table.getColumn("amount").getStyleAttributes().setLocked(true);
		table.getColumn("amount").getStyleAttributes().setBackground(lockColor);
		ICellEditor editor = getIndexEditor(type, productId);
		table.getColumn("indexName").setEditor(editor);
	}

	private void initColumnTextField(KDTable table) {
		FDCHelper
				.formatTableNumber(table, new String[] { "index", "workload", "sumPrice", "amount", "adjustAmt", "buildPart", "sellPart" });
		FDCHelper.formatTableNumber(table, "adjustCoefficient", "#,##0.000000;-#,##0.000000");
		FDCHelper.formatTableNumber(table, "coefficient", "#,##0.0000;-#,##0.0000");
		FDCHelper.formatTableNumber(table, COL_PRICE, "#,##0.0000;-#,##0.0000");
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("index").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("coefficientName").setEditor(txtEditor);

		/***
		 * 项目公司在于进行目标成本测算的时候需要将单价和系数值精确到小数点后四位数，目前只能精确到小数点后两位数 R090514-137 ——by neo
		 ****/
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(4);
		formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
		formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("coefficient").setEditor(numberEditor);
		table.getColumn(COL_PRICE).setEditor(numberEditor);

		/*******/
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn(COL_WORKLOAD).setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
	}

	private void initTableColumn(KDTable table) {
		table.getViewManager().setFreezeView(-1, 3);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		IColumn column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("acctNumber");
		column = table.addColumn();
		column.setKey("acctName");
		column = table.addColumn();
		column.setKey("indexName");
		column = table.addColumn();
		column.setKey("index");
		column = table.addColumn();
		column.setKey("coefficientName");
		column = table.addColumn();
		column.setKey("coefficient");
		column = table.addColumn();
		column.setKey(COL_WORKLOAD);
		column = table.addColumn();
		column.setKey("unit");
		column = table.addColumn();
		column.setKey(COL_PRICE);
		column = table.addColumn();
		column.setKey(COL_SUM_PRICE);
		// start调整系数三列
		column = table.addColumn();
		column.setKey("adjustCoefficient");
		column = table.addColumn();
		column.setKey("adjustAmt");
		column = table.addColumn();
		column.setKey("amount");

		// end
		column = table.addColumn();
		column.setKey("buildPart");
		column = table.addColumn();
		column.setKey("sellPart");
		column = table.addColumn();
		column.setKey("program");
		column = table.addColumn();
		column.setKey("desc");
		column = table.addColumn();
		column.setKey("changeReason");
		column = table.addColumn();
		column.setKey("description");
		IRow row = table.addHeadRow();
		row.getCell("acctNumber").setValue("科目编码");
		row.getCell("acctName").setValue("科目名称");
		row.getCell("indexName").setValue("原始指标名称");
		row.getCell("index").setValue("原始指标值");
		row.getCell("coefficientName").setValue("系数名称");
		row.getCell("coefficient").setValue("系数值");
		row.getCell(COL_WORKLOAD).setValue("工作量");
		row.getCell("unit").setValue("单位");
		row.getCell(COL_PRICE).setValue("单价");
		if (isUseAdjustCoefficient()) {
			row.getCell(COL_SUM_PRICE).setValue("调整前合价");
		} else {
			row.getCell(COL_SUM_PRICE).setValue("合价");
		}
		row.getCell("adjustCoefficient").setValue("调整系数");
		row.getCell("adjustAmt").setValue("调整金额");
		row.getCell("amount").setValue("合价");
		row.getCell("buildPart").setValue("建筑单方");
		row.getCell("sellPart").setValue("可售单方");
		row.getCell("program").setValue("合约规划");
		row.getCell("desc").setValue("备注");
		row.getCell("changeReason").setValue("变化原因");
		// row.getCell("description").setValue("描述");

		if (isUseQuality()) {
			row.getCell("description").setValue("品质特征");
		} else {
			table.getColumn("description").getStyleAttributes().setHided(true);
		}

		if (!isUseAdjustCoefficient()) {
			table.getColumn("adjustCoefficient").getStyleAttributes().setHided(true);
			table.getColumn("adjustAmt").getStyleAttributes().setHided(true);
			table.getColumn("amount").getStyleAttributes().setHided(true);
		}
	}

	public void fillTable(KDTable table) throws Exception {
		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		if (this.oprtState.equals(OprtState.EDIT)) {
			initAccoutStageMap(project.getId().toString(), getMeasureStageID());
		}
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
			child = initChild(child);
			child = initIsLeaf(child);
			fillNode(table, child);
		}
	}

	/**
	 * 按测算阶段初始化成本科目树
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode initChild(DefaultMutableTreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			CostAccountInfo costAcct = (CostAccountInfo) child.getUserObject();
			
			if (getAccoutStageMap().containsKey(costAcct.getId().toString())) {
				
				initChild(child);
				// modify bu lihaiou,2013.08.07 fix bug R130719-0178
				if (costAcct.getType() != null) {
					if (costAcct.getType().equals(CostAccountTypeEnum.SIX)) {
						hasSplitCost.put(((CostAccountInfo) node.getUserObject()).getId().toString(), Boolean.TRUE);
					} else if (costAcct.getType().equals(CostAccountTypeEnum.MAIN)) {
						hasNoSplitCost.put(((CostAccountInfo) node.getUserObject()).getId().toString(), Boolean.TRUE);
					}
				} else {//向上递归分摊属性
					Boolean value = (Boolean) hasSplitCost.get(costAcct.getId().toString());
					Boolean value2 = (Boolean) hasNoSplitCost.get(costAcct.getId().toString());
					if (value != null && value.booleanValue()) {
						hasSplitCost.put(((CostAccountInfo) node.getUserObject()).getId().toString(), Boolean.TRUE);
					}
					if (value2 != null && value2.booleanValue()) {
						hasNoSplitCost.put(((CostAccountInfo) node.getUserObject()).getId().toString(), Boolean.TRUE);
					}
				}
				// modify end
			} else {
				child.removeFromParent();
				i--;
			}
		}
		return node;
	}

	/**
	 * 按测算阶段初始化成本科目树最明细节点
	 * 
	 * @param node
	 * @return
	 */
	private DefaultMutableTreeNode initIsLeaf(DefaultMutableTreeNode node) {
		if (node.getChildCount() == 0) {
			CostAccountInfo costAcctLeaf = (CostAccountInfo) node.getUserObject();
			costAcctLeaf.setIsLeaf(true);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
				if (child.getChildCount() == 0) {
					CostAccountInfo costAcctLeaf = (CostAccountInfo) child.getUserObject();
					costAcctLeaf.setIsLeaf(true);
				} else {
					initIsLeaf(child);
				}
			}
		}
		return node;
	}

	/**
	 * 初始化成本科目与测算阶段关系值为1的成本科目ID值
	 * 
	 * @param proID
	 * @return
	 * @throws BOSException
	 */
	private void initAccoutStageMap(String proID, String msID) throws BOSException {
		getAccoutStageMap().clear();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select ca.fid costAccountID,ass.fvalue value from  T_FDC_AccountStage ass ");
		builder.appendSql("INNER join t_fdc_costAccount ca on ass.fcostAccountid = ca.fid ");
		// builder.appendSql("where cp.fid = '" + proID + "' ");
		// builder.appendSql("and ass.fmeasurestageid= '" + msID + "' ");
		builder.appendSql("where ca.FCurProject = ? ");
		builder.appendSql("and ass.fmeasurestageid= ? ");
		builder.appendSql("and ass.fvalue = 1 ");
		builder.appendSql("order by ca.flongnumber");
		// 使用预编译语句，提高效率
		builder.addParam(proID);
		builder.addParam(msID);
		IRowSet iRowSet = builder.executeQuery();
		try {
			while (iRowSet.next()) {
				getAccoutStageMap().put(iRowSet.getString("costAccountID"), iRowSet.getString("value"));
			}
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();

		// 应该先判断是否为空
		if (costAcct == null) {
			MsgBox.showError("成本科目的级别太多!");
			return;
		}
		if (!isAll) {
			if (!costAcct.isIsEnabled()) {
				return;
			}
		}
		if (costAcct.getType() != null) {
			if (table.getHeadRow(0).getUserObject() != null) {
				if (costAcct.getType().equals(CostAccountTypeEnum.SIX)) {
					return;
				}
			} else {
				if (costAcct.getType().equals(CostAccountTypeEnum.MAIN)) {
					return;
				}
			}
		} else {// modify by lihaiou,2013.08.07 fix bug R130719-0178,一级成本科目如果没有公摊或者非公摊的科目不应该显示
			if (table.getHeadRow(0).getUserObject() != null) {
				Boolean value = (Boolean) hasNoSplitCost.get(costAcct.getId().toString());
				if (value == null || !value.booleanValue()) {
					return;
				}
			} else {
				Boolean value = (Boolean) hasSplitCost.get(costAcct.getId().toString());
				if (value == null || !value.booleanValue()) {
					return;
				}
			}
		}// modify end 

		ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0).getUserObject();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		row.setUserObject(costAcct);
		if (node.isLeaf() && node.getLevel() > 0) {
			String key = costAcct.getId().toString();
			if (product != null) {
				key += product.getId().toString();
			}
			MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(key);
			if (coll != null && coll.size() > 0) {
				// modified by zhaoqin on 2013/10/15
				// 只有一行明细时，在同一行显示
				if (coll.size() == 1) {
					MeasureEntryInfo info = coll.get(0);
					// modify by lihaiou ,fix bug for data, 2013.08.19
					IRow entryRow = row;
					entryRow.setUserObject(info);
					loadRow(table, entryRow, product);
					setDetailAcctRow(entryRow);
					row.getCell("acctName").setValue(costAcct.getName());
				} else {
					row.getStyleAttributes().setLocked(true);
					
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
					row.setUserObject(costAcct);
					// for (int i = 0; i < coll.size(); i++) {	//  排序 - modified by zhaoqin on 2013/10/15
					for (int i = coll.size() - 1; i >= 0; i--) {
						MeasureEntryInfo info = coll.get(i);
						IRow entryRow = table.addRow();
						entryRow.setTreeLevel(node.getLevel());
						entryRow.setUserObject(info);
						loadRow(table, entryRow, product);
					}
				}
			} else {
				// 空科目的情况
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(costAcct);
				row.setUserObject(info);
				setTemplateMeasureCostF7Editor(table, row);
				setDetailAcctRow(row);
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		return super.getValue(pk);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("project.name");
		sels.add("project.longNumber");
		sels.add("project.number");
		sels.add("project.curProjProductEntries.isAccObj");
		sels.add("project.curProjProductEntries.*");
		sels.add("projectType.*");
		sels.add("orgUnit.id");
		sels.add("costEntry.*");
		sels.add("costEntry.costAccount.*");
		sels.add("costEntry.apportionType.*");
		sels.add("costEntry.unit.id");
		sels.add("costEntry.unit.name");
		sels.add("costEntry.unit.number");
		sels.add("measureStage.id");
		sels.add("measureStage.name");
		sels.add("measureStage.number");
		sels.add("constrEntrys.*");
		sels.add("constrEntrys.productType.*");
		return sels;
	}

	public void loadRow(KDTable table, IRow row, ProductTypeInfo product) {
		MeasureEntryInfo info = (MeasureEntryInfo) row.getUserObject();
		row.getCell("acctName").setValue(info.getEntryName());
		row.getCell("index").setValue(info.getIndexValue());
		if (info.getSimpleName() != null) {
			if (table.getColumn("indexName").getEditor() != null) {
				KDComboBox box = (KDComboBox) table.getColumn("indexName").getEditor().getComponent();
				if (box != null) {
					for (int i = 0; i < box.getItemCount(); i++) {
						if (((Item) box.getItemAt(i)).key.equals(info.getSimpleName())) {
							row.getCell("indexName").setValue(box.getItemAt(i));
							break;
						}
					}
				}
			}
		}
		row.getCell("coefficientName").setValue(info.getCoefficientName());
		BigDecimal coefficient = info.getCoefficient();
		if (coefficient != null && coefficient.compareTo(FDCHelper.ZERO) == 0) {
			coefficient = null;
		}
		// 显示四位
		row.getCell("coefficient").setValue(FDCHelper.toBigDecimal(coefficient, 4));
		BigDecimal workload = info.getWorkload();
		if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
			workload = null;
		}

		if (info.getSimpleName() != null && coefficient != null) {
			row.getCell(COL_WORKLOAD).getStyleAttributes().setLocked(true);
		}
		row.getCell(COL_WORKLOAD).setValue(workload);
		// 老数据升级　unit保存在name里面
		if (info.getUnit() == null) {
			row.getCell("unit").setValue(info.getName());
		} else {
			row.getCell("unit").setValue(info.getUnit());
		}
		BigDecimal price = info.getPrice();
		if (price != null && price.compareTo(FDCHelper.ZERO) == 0) {
			price = null;
		}
		row.getCell(COL_PRICE).setValue(FDCHelper.toBigDecimal(price,4));
		row.getCell(COL_SUM_PRICE).setValue(info.getCostAmount());
		if (workload != null && price != null) {
			row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(true);
		}
		// if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
		// BigDecimal buildPart = info.getCostAmount().divide(this.buildArea,
		// 2, BigDecimal.ROUND_HALF_UP).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		// row.getCell("buildPart").setValue(buildPart);
		// }
		/*
		 * String key = project.getId().toString() + " "; if (product != null) { key += product.getId().toString() + " "; }
		 */
		// key += ApportionTypeInfo.sellAreaType;
		// BigDecimal sellArea = (BigDecimal) this.apportionMap.get(key);
		Object obj = table.getHeadRow(0).getCell(0).getUserObject();
		if (obj instanceof PlanIndexEntryInfo) {
			BigDecimal sellArea = ((PlanIndexEntryInfo) obj).getSellArea();
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(), sellArea);
				row.getCell("sellPart").setValue(sellPart);
			} else {
				row.getCell("sellPart").setValue(null);
			}
			BigDecimal buildArea = ((PlanIndexEntryInfo) obj).getBuildArea();
			if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(), buildArea);
				row.getCell("buildPart").setValue(buildPart);
			} else {
				row.getCell("buildPart").setValue(null);
			}
		} else if (obj instanceof PlanIndexInfo) {
			// 六类公摊的单方
			BigDecimal sellArea = ((PlanIndexInfo) obj).getBigDecimal("allSellArea");
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(), sellArea);
				row.getCell("sellPart").setValue(sellPart);
			} else {
				row.getCell("sellPart").setValue(null);
			}
			BigDecimal buildArea = ((PlanIndexInfo) obj).getBigDecimal("allBuildArea");
			if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(), buildArea);
				row.getCell("buildPart").setValue(buildPart);
			} else {
				row.getCell("buildPart").setValue(null);
			}
		}
		row.getCell("amount").setValue(info.getAmount());
		row.getCell("adjustCoefficient").setValue(info.getAdjustCoefficient());
		row.getCell("adjustAmt").setValue(info.getAdjustAmt());
		row.getCell("program").setValue(info.getProgram());
		row.getCell("desc").setValue(info.getDesc());
		row.getCell("changeReason").setValue(info.getChangeReason());
		row.getCell("description").setValue(info.getDescription());
		setTemplateMeasureCostF7Editor(table, row);
	}

	private CurProjectInfo getProject(String proID) throws EASBizException, BOSException {
		return CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(proID));
	}

	public void onLoad() throws Exception {
		fetchInitParam();
		
		/**
		 * 由于框架问题，使得addnew操作受限，通过addnew1伪装后，创建了窗口后 然后再修改回来
		 */
		if (this.getOprtState().equals("ADDNEW1")) {
			this.setOprtState("ADDNEW");
		}
		/** 处理修改状态的目标成本测算阶段不允许修改测算阶段
		if (this.getOprtState().equals(OprtState.EDIT)) {
			comboMeasureStage.setEnabled(false);
		}*/
		this.getMenuManager(null);

		this.txtVersionNumber.setEnabled(false);
		this.actionImportApportion.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		boolean isEdit = false;
		if(STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())){
			isEdit = true;
		} else {
			isEdit = false;
		}
		// 获取工程项目
		String prjID = "";
		if (getUIContext().get("projectId") == null) {// 工作流进来打开查看界面 
			if (getUIContext().get(UIContext.ID) != null) {
				IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
				prjID = ((CurProjectInfo) getValue(pk).get("project")).getId().toString();
			}
		} else {
			// 如果是工作流需在消息中心打开界面时不能用如下方式，会取不到prjID
			prjID = (String) this.getUIContext().get("projectId");
		}
		if (!StringUtil.isEmptyString(prjID)) {
			project = getProject(prjID);
			// 新增情况，过滤测算阶段
			if (OprtState.ADDNEW.equals(this.getOprtState())) {
				BigDecimal fetchMeaStaMaxNumber = fetchMeaStaMaxNumber(project);
				FDCClientHelper.initComboMeasureStage(comboMeasureStage, isEdit, fetchMeaStaMaxNumber);
			} else {
				// 查看或修改情况，过滤测算阶段
				FDCClientHelper.initComboMeasureStage(comboMeasureStage, isEdit);
			}
		}
		initMeasureIndex();
		super.onLoad();

		if(editData.getDescription()==null){
			if(editData.getId()!=null){
				editData.setDescription(editData.getId().toString());
			}
		}
		txtVersionName.setMaxLength(80);
		initCtrlListener();

		Boolean isEditVersion = (Boolean) getUIContext().get("isEditVersion");
		if (isEditVersion != null && isEditVersion.booleanValue()) {
			comboMeasureStage.setEnabled(false);
		}
		Boolean isAimMeasure = (Boolean) getUIContext().get("isAimMeasure");
		if (isAimMeasure == null) {
			isAimMeasure = Boolean.TRUE;
			getUIContext().put("isAimMeasure", Boolean.TRUE);
		}
		if (!isAimMeasure.booleanValue()) {
			setUITitle("可研成本测算");
		} else {
			setUITitle("目标成本测算");
		}
		((MeasureCostInfo) editData).setIsAimMeasure(isAimMeasure.booleanValue());
		if (getOprtState().equals(OprtState.ADDNEW)) {
			actionImportTemplate.setEnabled(true);
		}
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);

		registerMeasureDefaultSplitTypeSetKey();

		actionEdit.setVisible(false);
		actionEdit.setEnabled(false);
		editVersion();

		setShowMessagePolicy(SHOW_MESSAGE_BOX_FIRST);
		actionCopy.setVisible(false);
		// 此处逻辑为：当单据为保存或提交状态时，需要判断测算阶段是否已被禁用
		// 如果已经禁用，则需将测算阶段修改为后一阶段，如果此后所有阶段都被禁用
		// 则修改为前一阶段 edit by emanon
		if (editData != null && ((MeasureCostInfo) editData).getMeasureStage() != null) {
			MeasureStageInfo msInfo = ((MeasureCostInfo) editData)
					.getMeasureStage();
			boolean isCanChange = FDCBillStateEnum.SAVED
					.equals(((MeasureCostInfo) editData).getState())
					|| FDCBillStateEnum.SUBMITTED
							.equals(((MeasureCostInfo) editData).getState());
			if (msInfo.isIsEnabled() || !isCanChange) {
				comboMeasureStage.setSelectedItem(((MeasureCostInfo) editData)
						.getMeasureStage());
			} else {
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add("*");
				view.getSorter().add(new SorterItemInfo("number"));
				MeasureStageCollection measureStageCollection = MeasureStageFactory
						.getRemoteInstance().getMeasureStageCollection(view);
				String curNum = msInfo.getNumber();
				int index = 0;
				int size = measureStageCollection.size();
				for (int i = 0; i < size; i++) {
					MeasureStageInfo measureStageInfo = measureStageCollection
							.get(i);
					String tmpNum = measureStageInfo.getNumber();
					if (curNum.equals(tmpNum)) {
						index = i;
						break;
					}
				}
				boolean isHasNext = false;
				for (int i = index + 1; i < size; i++) {
					MeasureStageInfo measureStageInfo = measureStageCollection
							.get(i);
					if (measureStageInfo.isIsEnabled()) {
						isHasNext = true;
						comboMeasureStage.setSelectedItem(measureStageInfo);
					}
				}
				if (!isHasNext) {
					for (int i = index - 1; i >= 0; i--) {
						MeasureStageInfo measureStageInfo = measureStageCollection
								.get(i);
						if (measureStageInfo.isIsEnabled()) {
							comboMeasureStage.setSelectedItem(measureStageInfo);
						}
					}
				}
			}
		}
		storeFields();// 汇总表增加afterSetUnionData退出提示
		
		MeasureCostInfo info = (MeasureCostInfo) editData;
		if (STATUS_EDIT.equals(this.oprtState)) {
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			if (info != null && info.getState() == FDCBillStateEnum.SUBMITTED) {
				actionSubmit.setEnabled(true);
				actionSave.setEnabled(false);
				btnSave.setEnabled(false);
			}
		}
		
		loadUserConfigToTable();
	}
	
	// 子类可以重载
	protected void fetchInitParam() throws Exception {
		isNeedShowTotalRow = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_TOTALCOST);
	}

	private void editVersion() throws BOSException, SQLException {
		Boolean isEditVersion = (Boolean) getUIContext().get("isEditVersion");
		if (isEditVersion != null && isEditVersion.booleanValue()) {
			// 修改版本号
			String sourceId = ((MeasureCostInfo) editData).getSourceBillId();
			if (sourceId == null) {
				sourceId = editData.getId().toString();
			}
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FVersionNumber from T_AIM_MeasureCost where fsourceBillId=?");
			builder.addParam(sourceId);
			IRowSet rowSet = builder.executeQuery();
			int lastSubVersion = 1;
			String verNo = ((MeasureCostInfo) editData).getVersionNumber();
			verNo = verNo.replaceAll("\\.", "!");
			while (rowSet.next()) {
				// 找出最大版本
				verNo = rowSet.getString("FVersionNumber");
				verNo = verNo.replaceAll("\\.", "!");
				if (verNo != null && verNo.indexOf('!') > 0) {
					int temp = Integer.parseInt(verNo.substring(verNo.indexOf('!') + 1));
					if (temp >= lastSubVersion) {
						lastSubVersion = temp + 1;
					}
				}

			}
			if (!FDCHelper.isEmpty(verNo)) {
				if (verNo.indexOf('!') > 0) {
					int index = verNo.lastIndexOf("!");
					verNo = verNo.substring(0, index + 1) + lastSubVersion;
				}
			}
			txtVersionNumber.setText(verNo.replaceAll("!", "\\."));
		}
	}

	private void registerMeasureDefaultSplitTypeSetKey() {
		String actionName = "MeasureDefaultSplitTypeSetUI";
		final UIContext uiContext = new UIContext(this);
		this.getActionMap().put(actionName, new javax.swing.AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					setCursorOfWair();
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.MODEL);
					IUIWindow wnd = fy.create(MeasureDefaultSplitTypeSetUI.class.getName(), uiContext, null, "EDIT",
							WinStyle.SHOW_KINGDEELOGO);
					wnd.show();
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				} finally {
					setCursorOfDefault();
				}
			}
		});
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift alt F12"), actionName);

	}

	private void initCtrlListener() {
		prmtProjectType.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (e.getNewValue() == null) {
					prmtProject.setData(null);
				}
				if (e.getOldValue() == null ? e.getNewValue() != null : !e.getOldValue().equals(e.getNewValue())) {
					prmtProject.setData(null);
				}

			}
		});
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		selector.add("projectType.*");
		prmtProject.setSelectorCollection(selector);
		prmtProject.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (prmtProjectType.getData() == null && e.getNewValue() != null) {
					EventListener[] listeners = prmtProjectType.getListeners(DataChangeListener.class);
					for (int i = 0; i < listeners.length; i++) {
						prmtProjectType.removeDataChangeListener((DataChangeListener) listeners[i]);
					}
					prmtProjectType.setData(((CurProjectInfo) e.getNewValue()).getProjectType());
					for (int i = 0; i < listeners.length; i++) {
						prmtProjectType.addDataChangeListener((DataChangeListener) listeners[i]);
					}
				}
			}
		});
		prmtProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				prmtProject.getQueryAgent().resetRuntimeEntityView();
				String projectTypeid = FDCHelper.getF7Id(prmtProjectType);
				EntityViewInfo view = prmtProject.getEntityViewInfo();
				if (view == null) {
					view = new EntityViewInfo();
				}
				view.setFilter(new FilterInfo());
				if (projectTypeid != null) {
					view.getFilter().appendFilterItem("projectType.id", projectTypeid);
				}
				view.getFilter().appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
				// prmtProject
				prmtProject.setEntityViewInfo(view);
			}
		});
		plTables.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// ////////////////////////////////////////////////////////////////////////
				// 使用多线程刷新页签 by skyiter_wang 2015/02/07
				FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
					public void run() {
						// 表格面板_页签切换
						plTables_stateChange();
					}
				});
				// ////////////////////////////////////////////////////////////////////////
			}

		});
	}

	/**
	 * 描述：表格面板_页签切换
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-12
	 */
	private void plTables_stateChange() {
		Object obj = plTables.getClientProperty("oldIndex");
		if (obj instanceof Integer) {
			if (((Integer) obj).intValue() == 1) {
				// //////////////////////////////////////////////////////////////////////////
				// 刷新所有测算表格(table index: 2-X)
				refreshAllMeasureTable();
				// //////////////////////////////////////////////////////////////////////////
			}
		}
		
		plTables.putClientProperty("oldIndex", new Integer(plTables.getSelectedIndex()));
		if (plTables.getSelectedIndex() == 0) {
			measureCollectTable.refresh();
		}
	}

	public void loadFields() {
		System.out.println("Start loadFields!");
		super.loadFields();
		// 只取公司的成本科目
		String orgId = (String) this.getUIContext().get("orgId");
		/*
		 * 如果是在工作流中直接打开一个AimMeasureCostEditUI而不是从序时簿上双击打开的话字段isAimMeasure极有可能为空 这样的话经过onLoad方法中的 if(isAimMeasure==null){
		 * isAimMeasure=Boolean.TRUE; getUIContext().put("isAimMeasure",Boolean.TRUE); }
		 * 处理便会使得isAimMeasure字段的值变为true.但是在配置工作流中这个字段是用来区分是可研测算工作流或是目标成本测算工作流 的依据。如此一来，就区分不了了。故现在处理一下，避免流程的混淆 by Cassiel 2009-10-30
		 */
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
		if (getUIContext().get("isAimMeasure") == null) {
			getUIContext().put("isAimMeasure", Boolean.valueOf(cost.isIsAimMeasure()));
		}
		if (orgId == null) {
			orgId = ((MeasureCostInfo) this.editData).getOrgUnit().getId().toString();
			this.getUIContext().put("orgId", orgId);
		}
		if (costAcctTree == null) {
			initAccTree(cost, orgId);
		}
		if (!OprtState.ADDNEW.equals(this.getOprtState())) {
			GlUtils.setSelectedItem(comboMeasureStage, cost.getMeasureStage());
		}
		this.prmtProject.setValue(cost.getProject());
		this.prmtProjectType.setValue(cost.getProjectType());
		// 判断版本号是否为空，如果为空，错误信息提示
		if (!(cost.getVersionNumber() == null)) {
			this.txtVersionNumber.setText(cost.getVersionNumber().replaceAll("!", "\\."));
		} else {
			MsgBox.showWarning(this, "版本号不能为空");
			SysUtil.abort();
		}
		this.txtVersionName.setText(cost.getVersionName());
		measureCostMap.clear();
		if (OprtState.ADDNEW.equals(this.getOprtState()) && !importFlag) {
			meaStaInfo = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
			if (meaStaInfo != null) {
				meaStaInfo = getBeforeStage(meaStaInfo);
			}
			MeasureCostInfo mcInfo = getLastStage();
			if (mcInfo != null) {
				MeasureCostInfo measureCostInfo = initMeasureInfo(mcInfo);
				cost.putAll(measureCostInfo);
				this.txtVersionNumber.setText(cost.getVersionNumber().replaceAll("!", "\\."));
			}
		} else {
			MeasureStageInfo stageItem = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
			if (stageItem != null && project != null) {
				acctChangeToCurrentAcct(project.getId().toString(), stageItem.getId().toString(), cost);
			}
		}
		MeasureEntryCollection costEntrys = cost.getCostEntry();
		for (int i = 0; i < costEntrys.size(); i++) {
			MeasureEntryInfo info = costEntrys.get(i);
			CostAccountInfo costAccount = info.getCostAccount();
			String key = costAccount.getId().toString();
			if (info.getProduct() != null) {
				key += info.getProduct().getId().toString();
			}
			if (measureCostMap.containsKey(key)) {
				MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(key);
				coll.add(info);
			} else {
				MeasureEntryCollection newColl = new MeasureEntryCollection();
				newColl.add(info);
				measureCostMap.put(key, newColl);
			}
		}
		try {
			addPanel();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		TimeTools.getInstance().msValuePrintln("end loadFields");

		// 互斥
		if (STATUS_EDIT.equals(getOprtState()) && cost != null && cost.getId() != null) {
			String billId = cost.getId().toString();
			lockIds.add(billId);
			String measureIncomeId = getMeasureIncomeId(billId);
			if (measureIncomeId != null) {// 未启用收入测算子联用参数或可研时
				lockId2s.add(measureIncomeId);
				try {
					FDCClientUtils.requestDataObjectLock(this, lockIds, "edit");
					Map uiContext = new HashMap();
					uiContext.put(UIContext.ID, measureIncomeId);
					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
					IUIWindow window = uiFactory.create(MeasureIncomeEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					MeasureIncomeEditUI = (CoreUI) window.getUIObject();
					if (lockId2s.size() > 0)
						FDCClientUtils.requestDataObjectLock(MeasureIncomeEditUI, lockId2s, "edit");
				} catch (Throwable e1) {
					this.handUIException(e1);
					hasMutex = FDCClientUtils.hasMutexed(e1);
				}
			}
		}
	}

	/**
	 * 初始化成本科目树
	 * 
	 * @param cost
	 * @param orgId
	 */
	private void initAccTree(MeasureCostInfo cost, String orgId) {
		if (cost == null) {
			return;
		}
		try {
			FilterInfo acctFilter = new FilterInfo();
			// if (!isAll) {
			// acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled",
			// Boolean.TRUE));
			// }
			/*
			 * 为支持测算的时候可以进行更明细科目的测算，科目必须用工程项目的 by sxhong 2009-08-25 15:11:21
			 */
			if (cost.getProject() == null // 选组织新增的
					// 老数据，老数据以前是用实体财务组织的成本科目来测算的 by sxhong
					|| (cost.getCostEntry().size() > 0 && cost.getCostEntry().get(0).getCostAccount().getCurProject() == null)) {
				acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgId));
				accreditSet = AcctAccreditHelper.handAcctAccreditFilter(null, orgId, acctFilter);
			} else {
				String prjId = cost.getProject().getId().toString();
				acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
				accreditSet = AcctAccreditHelper.handAcctAccreditFilter(null, prjId, acctFilter);
			}
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
		} catch (Exception e) {
			this.handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 获取测算阶段ID
	 * 
	 * @return
	 */
	private String getMeasureStageID() {
		MeasureStageInfo measureStage = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
		String msID = null;
		if (measureStage != null) {
			msID = measureStage.getId().toString();
		}
		return msID;
	}

	private String getMeasureIncomeId(String measureCostId) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from t_aim_measureincome where fsrcmeasurecostid=? ");
		builder.addParam(measureCostId);
		try {
			IRowSet rs = builder.executeQuery();
			if (rs != null && rs.next()) {
				return rs.getString("fid");
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return null;
	}

	private boolean isLastVersion(String measureCostId) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FIsLastVersion from t_aim_measurecost where fid=? ");
		builder.addParam(measureCostId);
		try {
			IRowSet rs = builder.executeQuery();
			if (rs != null && rs.next()) {
				return rs.getBoolean("FIsLastVersion");
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return false;
	}

	protected void table_editStopped(KDTable table, KDTEditEvent e) throws Exception {
		// //////////////////////////////////////////////////////////////////////////
		Map tempMap = FdcManagementUtil.recodeExeTimeBefore(getClass(), "table_editStopped");
		// //////////////////////////////////////////////////////////////////////////
		
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		
		/* editstop中第一次加载数据时oldValue,newValue都为空,增加上面一句  可能是为了修改某个BUG,但是却导致了另一个BUG：
		 * 规划指标表中的指标修改后六类公摊中的原始指标值不会自动更新  by cassiel_peng
		 * if(oldValue==null&&newValue==null){ return; }
		 */
		// 第一次加载数据时oldValue,newValue都为空,增加上面一句，返回
		if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
			return;
		}
		Object objTmp = table.getHeadRow(0).getCell(0).getUserObject();
		IObjectValue info = null;
		if (objTmp instanceof IObjectValue) {
			info = (IObjectValue) objTmp;
		}
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		if (oldValue == null && newValue == null) {
			if (!table.getColumnKey(columnIndex).equals("indexName")) {
				return;
			}
		}

		IRow row = table.getRow(rowIndex);
		if (table.getColumnKey(columnIndex).equals("index")
				|| table.getColumnKey(columnIndex).equals("coefficient")) {
			refreshWorkload(table,row);
		} else if (table.getColumnKey(columnIndex).equals(COL_PRICE)
				|| table.getColumnKey(columnIndex).equals(COL_WORKLOAD)) {
			if (e.getOldValue() != null && e.getValue() == null){
				// 如果是故意清空工程量或单价列，置调整前合价为0
				row.getCell(COL_SUM_PRICE).setValue(FDCNumberHelper.ZERO);
			}
			refreshSumPrice(table,row);
		} else if (table.getColumnKey(columnIndex).equals(COL_SUM_PRICE)) {
			sumPriceEditStop(table, row);
		} else if (table.getColumnKey(columnIndex).equals("indexName")) {
			Object obj = row.getCell("indexName").getValue();
			if (obj instanceof Item) {
				Item item = (Item) obj;
				// row.getCell("index").setValue(item.getValue());
				if (info != null) {
					BigDecimal amount = FDCHelper.ZERO;
					// 处理景观面积及户数
					if (info instanceof PlanIndexInfo) {
						PlanIndexInfo planIndexInfo = (PlanIndexInfo) info;
						if (item.key.equals("viewArea")) {
							// 道路用地合计+绿化用地合计
							amount = FDCHelper.toBigDecimal(planIndexInfo.getTotalRoadArea()).add(
									FDCHelper.toBigDecimal(planIndexInfo.getTotalGreenArea()));
						}
						if (item.key.equals("doors")) {
							// 户数之和
							for (int i = 0; i < planIndexInfo.getEntrys().size(); i++) {
								PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
								amount = amount.add(FDCHelper.toBigDecimal(entry.getDoors()));
							}
						}
					}
					
					/* modified by zhaoqin for R130917-0324 on 2013/11/21 start */
					if(info instanceof PlanIndexEntryInfo) {
						PlanIndexEntryInfo pieInfo = (PlanIndexEntryInfo)info;
						// 户数
						if (item.key.equals("doors"))
							amount = amount.add(FDCHelper.toBigDecimal(pieInfo.getDoors()));
						// 电梯数
						if (item.key.equals("elevators"))
							amount = amount.add(FDCHelper.toBigDecimal(pieInfo.getElevators()));
					}
					/* modified by zhaoqin for R130917-0324 on 2013/11/21 end */
					
					if (amount.compareTo(FDCHelper.ZERO) == 0) {
						row.getCell("index").setValue(getCustomIndexValue(info, item));
					} else {
						row.getCell("index").setValue(amount);
					}

					if (item.key.equals("empty")) {
						row.getCell("coefficient").setValue(null);
						row.getCell("coefficientName").setValue(null);
					}
				}
			}
			refreshWorkload(table, row);
		}

		// 调整系数
		if (table.getColumnKey(columnIndex).equals("adjustCoefficient")) {
			row.getCell(columnIndex).setUserObject("adjust");
			refreshAdjustAmount(table, row);
		}
		if (table.getColumnKey(columnIndex).equals("adjustAmt")) {
			refreshAdjustAmount(table, row);
		}
		if (isDetailAcctRow(row)) {
			if (newValue != null) {
				setDetailAcctHashInput(row);
			} else {
				boolean isEmpty = true;
				for (int i = 3; i < table.getColumnCount(); i++) {
					if (!FDCHelper.isEmpty(row.getCell(i).getValue())) {
						isEmpty = false;
						break;
					}
				}
				if (isEmpty) {
					setDetailAcctHasNotInput(row);
				}
			}
		}
		setUnionData(table);

		setDataChange(true);
		
		// //////////////////////////////////////////////////////////////////////////
		FdcManagementUtil.recodeExeTimeAfter(getClass(), "table_editStopped", tempMap);
		// //////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 调整前合价列编辑结束
	 * 
	 * @author owen_wen 2011-08-30
	 */
	private void sumPriceEditStop(KDTable table, IRow row) {
		BigDecimal price = (BigDecimal) row.getCell(COL_PRICE).getValue();
		BigDecimal workload = (BigDecimal) row.getCell(COL_WORKLOAD).getValue();
		BigDecimal sumPrice = FDCNumberHelper.toBigDecimal(row.getCell(COL_SUM_PRICE).getValue());

		if (price == null && workload != null && FDCNumberHelper.ZERO.compareTo(workload) != 0) {
			row.getCell(COL_PRICE).setValue(FDCNumberHelper.divide(sumPrice, workload));
			row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(true);
		}

		if (price != null && FDCNumberHelper.ZERO.compareTo(price) != 0 && workload == null) {
			row.getCell(COL_WORKLOAD).setValue(FDCNumberHelper.divide(sumPrice, price));
			row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(true);
		}
		
		row.getCell("adjustCoefficient").setUserObject("adjust");
		refreshAdjustAmount(table,row);
	}

	/**
	 * 刷新“六类公摊及期间费”以及各产品页签中的 调整前合价、工作量、单价列值
	 * 
	 * @param table
	 * @param row
	 */
	private void refreshSumPrice(KDTable table,IRow row) {
		BigDecimal price = (BigDecimal) row.getCell(COL_PRICE).getValue();
		BigDecimal workload = (BigDecimal) row.getCell(COL_WORKLOAD).getValue();
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		if (workload == null) {
			workload = FDCHelper.ZERO;
		}
		
		// 当且仅当“工作量”和“单价列”值不为空或0时，锁定 “调整前合价” 列
		row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(
				row.getCell(COL_PRICE).getValue() != null && row.getCell(COL_WORKLOAD).getValue() != null);

		if (price.compareTo(FDCHelper.ZERO) != 0 && workload.compareTo(FDCHelper.ZERO) != 0) { // “工作量”和“单价列”值不为空或0
			BigDecimal sumPrice = price.multiply(workload).setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell(COL_SUM_PRICE).setValue(sumPrice);
		} else { // 其中一个为空或0时，计算其价格
			BigDecimal sumPrice = FDCNumberHelper.toBigDecimal(row.getCell(COL_SUM_PRICE).getValue());
			if (row.getCell(COL_PRICE).getValue() != null && row.getCell(COL_WORKLOAD).getValue() == null) {
				row.getCell(COL_WORKLOAD).setValue(FDCNumberHelper.divide(sumPrice, price));
				row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(true);
			} else if (row.getCell(COL_PRICE).getValue() == null && row.getCell(COL_WORKLOAD).getValue() != null) {
				row.getCell(COL_PRICE).setValue(FDCNumberHelper.divide(sumPrice, workload));
				row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(true);
			} else if (row.getCell(COL_PRICE).getValue() != null && row.getCell(COL_WORKLOAD).getValue() != null) {
				row.getCell(COL_SUM_PRICE).setValue(FDCNumberHelper.ZERO);
			}
		}
		row.getCell("adjustCoefficient").setUserObject("adjust");
		refreshAdjustAmount(table,row);
	}

	private void refreshWorkload(KDTable table, IRow row) {
		//原始指标值
		BigDecimal originalIndexValue = FDCHelper.toBigDecimal(row.getCell("index").getValue());

		BigDecimal coefficient = FDCHelper.toBigDecimal(row.getCell("coefficient").getValue());

		if (originalIndexValue == null) {
			originalIndexValue = FDCHelper.ZERO;
		}
		if (coefficient == null) {
			coefficient = FDCHelper.ZERO;
		}

		if (originalIndexValue.compareTo(FDCHelper.ZERO) == 0 && coefficient.compareTo(FDCHelper.ZERO) == 0) {
			row.getCell("index").setValue(null);
			row.getCell("coefficient").setValue(null);
			row.getCell(COL_WORKLOAD).getStyleAttributes().setLocked(false);
		} else {
			BigDecimal newWorkload = originalIndexValue.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);

			/* 因为refreshMeasureTable()会调用table_editStopped()，属于伪edit_stop，
			 * 如果是伪edit_stop，当工作量未改变 时，不要重算一次调整前合价，因为算出的值可能与之前录入的不等。
			 * Added by Owen_wen 2011-11-10  R111105-0048
			*/
			if (FDCHelper.compareTo(newWorkload, row.getCell(COL_WORKLOAD).getValue()) != 0) {
				row.getCell(COL_WORKLOAD).setValue(newWorkload);
				row.getCell(COL_WORKLOAD).getStyleAttributes().setLocked(true);
				// 只有COL_WORKLOAD列值被改变了，才需要调用refreshSumPrice() Added by Owen_wen 2011-09-27
				refreshSumPrice(table, row);
			}
		}
	}

	/**
	 * 调整系数与合价的计算 合价=调整前合价+调整额。其中调整额=原有合价*调整系数,其中调整额可以手工在录入，也可以通过计算得到 by sxhong 2008-05-29 17:14:12
	 * 
	 * @param row
	 */
	private void refreshAdjustAmount(KDTable table, IRow row) {
		BigDecimal sumPrice = (BigDecimal) row.getCell(COL_SUM_PRICE).getValue();
		if (row.getCell("adjustCoefficient").getUserObject() != null) {
			BigDecimal adjustAmt = FDCNumberHelper.multiply(sumPrice, row.getCell("adjustCoefficient").getValue());
			row.getCell("adjustAmt").setValue(adjustAmt);
			row.getCell("amount").setValue(FDCNumberHelper.add(sumPrice, adjustAmt));
		} else {
			BigDecimal adjustAmt = (BigDecimal) row.getCell("adjustAmt").getValue();
			row.getCell("adjustCoefficient").setValue(FDCNumberHelper.divide(adjustAmt, sumPrice, 6, BigDecimal.ROUND_HALF_UP));
			row.getCell("amount").setValue(FDCNumberHelper.add(sumPrice, adjustAmt));
		}
		row.getCell("adjustCoefficient").setUserObject(null);

		// 算单方
		BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
		if (amount != null) {
			Object obj = table.getHeadRow(0).getCell(0).getUserObject();
			if (obj instanceof PlanIndexEntryInfo) {
				BigDecimal sellArea = ((PlanIndexEntryInfo) obj).getSellArea();
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal sellPart = amount.divide(sellArea, BigDecimal.ROUND_HALF_UP);
					row.getCell("sellPart").setValue(sellPart);
				} else {
					row.getCell("sellPart").setValue(null);
				}
				BigDecimal buildArea = ((PlanIndexEntryInfo) obj).getBuildArea();
				if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal buildPart = amount.divide(buildArea, BigDecimal.ROUND_HALF_UP);
					row.getCell("buildPart").setValue(buildPart);
				} else {
					row.getCell("buildPart").setValue(null);
				}
			} else if (obj instanceof PlanIndexInfo) {
				BigDecimal sellArea = ((PlanIndexInfo) obj).getBigDecimal("allSellArea");
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal sellPart = amount.divide(sellArea, BigDecimal.ROUND_HALF_UP);
					row.getCell("sellPart").setValue(sellPart);
				} else {
					row.getCell("sellPart").setValue(null);
				}
				BigDecimal buildArea = ((PlanIndexInfo) obj).getBigDecimal("allBuildArea");
				if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal buildPart = amount.divide(buildArea, BigDecimal.ROUND_HALF_UP);
					row.getCell("buildPart").setValue(buildPart);
				} else {
					row.getCell("buildPart").setValue(null);
				}
			} else {
				row.getCell("buildPart").setValue(null);
				row.getCell("sellPart").setValue(null);
			}

		} else {
			row.getCell("buildPart").setValue(null);
			row.getCell("sellPart").setValue(null);
		}
	}

	public void actionAddRow_actionPerformed(ActionEvent arg0) throws Exception {
		if (this.plTables.getSelectedIndex() == 0) {
			return;
		}
		if (this.plTables.getSelectedIndex() == 1) {
			planIndexTable.addRow(arg0);
			return;
		}

		if (this.plTables.getSelectedIndex() == 3) {
			Object v = prmtProjectType.getValue();
			if (v == null) {
				MsgBox.showWarning(this, "六类公摊测算必须先设置项目系列");
				return;
			}
		}
		if (this.plTables.getSelectedIndex() == 2) {
			planIndexTable.addConstrIndexRow(arg0);
			return;
		}

		KDTable table = (KDTable) this.tables.get(this.plTables.getSelectedIndex());
		if (table.getRowCount() == 0) {
			return;
		}
		int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			table.getSelectManager().set(table.getRowCount() - 1, 0);
			index = table.getRowCount() - 1;
		}
		IRow selectRow = table.getRow(index);
		if (selectRow.getUserObject() instanceof CostAccountInfo) {
			CostAccountInfo acct = (CostAccountInfo) selectRow.getUserObject();
			if (acct.isIsLeaf() && acct.getLevel() > 1) {
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel() + 1);
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(acct);
				row.setUserObject(info);
				table.setUserObject("addRow");
				setTemplateMeasureCostF7Editor(table, row);
			} else {
				this.setMessageText("非明细行或一级明细行不能添加子行!");
				this.showMessage();
			}
		} else {
			MeasureEntryInfo infoUp = (MeasureEntryInfo) selectRow.getUserObject();
			if (isDetailAcctRow(selectRow)) {
				{
					Map splitTypeMap = measureCollectTable.getSplitTypes();
					MeasureEntryInfo entry = (MeasureEntryInfo) selectRow.getUserObject();
					ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0).getUserObject();
					entry.setEntryName((String) selectRow.getCell("acctName").getValue());
					entry.setProduct(product);
					/*
					 * entry.setApportionType((ApportionTypeInfo) selectRow.getCell( "indexName").getValue());
					 */
					Object obj = selectRow.getCell("indexName").getValue();
					if (obj instanceof Item) {
						Item item = (Item) obj;
						entry.setSimpleName(item.key);
						entry.setIndexName(item.toString());
						entry.setIndexValue(FDCHelper.toBigDecimal(selectRow.getCell("index").getValue()));
						// KDComboBox
						// box=(KDComboBox)selectRow.getCell("indexName").getEditor().getComponent();
						// box.getSelectedIndex();
					}
					entry.setCoefficientName((String) selectRow.getCell("coefficientName").getValue());
					entry.setCoefficient((BigDecimal) selectRow.getCell("coefficient").getValue());
					final Object value = selectRow.getCell("unit").getValue();
					entry.setUnit((MeasureUnitInfo) value);
					// entry.setUnit(value);
					entry.setWorkload((BigDecimal) selectRow.getCell(COL_WORKLOAD).getValue());
					entry.setPrice((BigDecimal) selectRow.getCell(COL_PRICE).getValue());
					entry.setCostAmount((BigDecimal) selectRow.getCell("sumPrice").getValue());
					entry.setProgram(((String) selectRow.getCell("program").getValue()));
					entry.setDesc((String) selectRow.getCell("desc").getValue());
					entry.setChangeReason((String) selectRow.getCell("changeReason").getValue());
					entry.setDescription((String) selectRow.getCell("description").getValue());
					if (entry.getCostAccount().getType() == CostAccountTypeEnum.SIX) {
						Object splitType = splitTypeMap.get(entry.getCostAccount().getId().toString());
						if (splitType != null) {
							entry.setNumber(splitType.toString());
						}
					}
					entry.setAdjustCoefficient((BigDecimal) selectRow.getCell("adjustCoefficient").getValue());
					entry.setAdjustAmt((BigDecimal) selectRow.getCell("adjustAmt").getValue());
					entry.setAmount((BigDecimal) selectRow.getCell("amount").getValue());
				}
				// TODO 为什么增加两行?
				// 明细行录入
				IRow tempRow = table.addRow(index + 1);
				tempRow.setUserObject(infoUp);
				tempRow.setTreeLevel(selectRow.getTreeLevel() + 1);
				loadRow(table, tempRow, infoUp.getProduct());
				selectRow.setUserObject(infoUp.getCostAccount());
				selectRow.getCell(0).setUserObject(null);
				clearDetailAcctRow(table, selectRow);
				setDetailAcctRowNull(selectRow);
				selectRow.getStyleAttributes().setLocked(true);
				selectRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				index++;
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel() + 1);
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(infoUp.getCostAccount());
				row.setUserObject(info);
				table.setUserObject("addRow");
				setTemplateMeasureCostF7Editor(table, row);
				this.setUnionData(table);
			} else {
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel());
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(infoUp.getCostAccount());
				row.setUserObject(info);
				table.setUserObject("addRow");
				setTemplateMeasureCostF7Editor(table, row);
			}
		}
	}

	private void setTemplateMeasureCostF7Editor(KDTable table, final IRow row) {
		if (true) {
			return;
		}
		Boolean objBoolean = (Boolean) getUIContext().get("isAimMeasure");
		boolean isAimMeasure = true;
		if (objBoolean != null) {
			isAimMeasure = objBoolean.booleanValue();
		}
		Object obj = row.getUserObject();
		final CostAccountInfo acct;
		String indexType = null;
		if (obj instanceof MeasureEntryInfo) {
			acct = ((MeasureEntryInfo) obj).getCostAccount();
			indexType = ((MeasureEntryInfo) obj).getSimpleName();
		} else {
			return;
		}
		String orgId = (String) this.getUIContext().get("orgId");
		String acctLongNumber = acct.getLongNumber();
		String projectTypeId = null;
		String productId = null;
		Object product = table.getHeadRow(0).getUserObject();
		if (product instanceof ProductTypeInfo) {
			productId = ((ProductTypeInfo) product).getId().toString();
		}
		if (acct.getType() == CostAccountTypeEnum.SIX) {
			Object v = prmtProjectType.getValue();
			if (v instanceof ProjectTypeInfo) {
				projectTypeId = ((ProjectTypeInfo) v).getId().toString();

			}
		}

		// set cell editor
		TemplateMeasureCostPromptBox selector = new TemplateMeasureCostPromptBox(this, isAimMeasure, orgId, productId, acctLongNumber,
				null, projectTypeId, true);
		KDBizPromptBox myPrmtBox = new KDBizPromptBox() {
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if (obj != null) {
					return FDCHelper.toBigDecimal(t, 2);
				}
				return obj;
			}
		};
		myPrmtBox.setSelector(selector);
		ICellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		row.getCell(COL_PRICE).setEditor(f7Editor);
		myPrmtBox.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				if (acct.getType() == CostAccountTypeEnum.SIX) {
					Object v = prmtProjectType.getValue();
					if (v instanceof ProjectTypeInfo) {
						KDBizPromptBox my = (KDBizPromptBox) e.getSource();
						((TemplateMeasureCostPromptBox) my.getSelector()).setProjectTypeID(((ProjectTypeInfo) v).getId().toString());
					} else {
						MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
						e.setCanceled(true);
					}
				}

			}
		});
		// coefficient
		selector = new TemplateMeasureCostPromptBox(this, isAimMeasure, orgId, productId, acctLongNumber, indexType, projectTypeId, false);
		myPrmtBox = new KDBizPromptBox() {
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if (obj != null) {
					return FDCHelper.toBigDecimal(t, 2);
				}
				return obj;
			}
		};
		myPrmtBox.setSelector(selector);
		f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		row.getCell("coefficient").setEditor(f7Editor);
		myPrmtBox.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox my = (KDBizPromptBox) e.getSource();
				if (acct.getType() == CostAccountTypeEnum.SIX) {
					Object v = prmtProjectType.getValue();
					if (v instanceof ProjectTypeInfo) {
						((TemplateMeasureCostPromptBox) my.getSelector()).setProjectTypeID(((ProjectTypeInfo) v).getId().toString());
					} else {
						MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
						e.setCanceled(true);
					}
				}
				if (row.getCell("indexName").getValue() instanceof Item) {
					((TemplateMeasureCostPromptBox) my.getSelector()).setIndex(((Item) row.getCell("indexName").getValue()).key);
				} else {
					MsgBox.showWarning(getplTables(), "请先选择指标");
					e.setCanceled(true);
				}
			}
		});

	}

	public void actionDeleteRow_actionPerformed(ActionEvent arg0) throws Exception {
		if (this.plTables.getSelectedIndex() == 0) {
			return;
		}
		if (this.plTables.getSelectedIndex() == 1) {
			planIndexTable.deleteRow(arg0);
			return;
		}
		if (this.plTables.getSelectedIndex() == 2) {
			planIndexTable.deleteConstrIndexRow(arg0);
			return;
		}

		KDTable table = (KDTable) this.tables.get(this.plTables.getSelectedIndex());
		KDTSelectManager selectManager = table.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = table.getRow(j);
				if (selectRow == null) {
					continue;
				}
				if (selectRow.getUserObject() instanceof MeasureEntryInfo) {
					selectRow.getCell(COL_PRICE).setUserObject("delete");
				}
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = (IRow) table.getRow(i);
			if (row.getCell(COL_PRICE).getUserObject() != null) {
				if (isDetailAcctRow(row)) {
					clearDetailAcctRow(table, row);
					row.getCell(COL_PRICE).setUserObject(null);
				} else {
					// 判断是不是最后一行
					int j = row.getRowIndex() - 1;
					int k = row.getRowIndex() + 1;
					if (j > 0) {
						IRow parentRow = table.getRow(j);
						if (parentRow.getUserObject() instanceof CostAccountInfo) {
							if (k == table.getRowCount() || isDetailAcctRow(table.getRow(k))
							// 非明细行
									|| table.getRow(k).getUserObject() instanceof CostAccountInfo) {
								clearDetailAcctRow(table, parentRow);
								parentRow.getStyleAttributes().setBackground(Color.WHITE);
								parentRow.getStyleAttributes().setLocked(false);
								MeasureEntryInfo info = new MeasureEntryInfo();
								info.setCostAccount((CostAccountInfo) parentRow.getUserObject());
								parentRow.setUserObject(info);
								setTemplateMeasureCostF7Editor(table, parentRow);
								setDetailAcctRow(parentRow);
								parentRow.getCell("acctNumber").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("acctName").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("amount").getStyleAttributes().setLocked(true);
								parentRow.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("buildPart").getStyleAttributes().setLocked(true);
								parentRow.getCell("buildPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("sellPart").getStyleAttributes().setLocked(true);
								parentRow.getCell("sellPart").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("index").getStyleAttributes().setLocked(true);
								parentRow.getCell("index").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							}
						}
					}
					table.removeRow(row.getRowIndex());
					i--;
				}
				table.setUserObject("delteRow");
				setUnionData(table);
			}
		}

	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
		// //////////////////////////////////////////////////////////////////////////
		Map tempMap = FdcManagementUtil.recodeExeTimeBefore(getClass(), "setUnionData");
		// //////////////////////////////////////////////////////////////////////////
		
		String[] cols = new String[] {
		// "price",
				"sumPrice", "amount", "buildPart", "sellPart", "adjustAmt" };
		
		Object obj = table.getHeadRow(0).getCell(0).getUserObject();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				if (table.getRowCount() == 1) {
					IRow rowAfter = table.getRow(0);
					aimRowList.add(rowAfter);
				}
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof MeasureEntryInfo) {
						aimRowList.add(rowAfter);
					}

				}

				BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
				String columnName = null;
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = FDCHelper.ZERO;
					
					columnName = cols[j];
					/**
					 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
					 * 
					 * @author pengwei_hou Date: 2009-01-19 14:23:23
					 */
					if (columnName.equals("sellPart")) {
						
						if (amount != null) {
							if (obj instanceof PlanIndexEntryInfo) {
								BigDecimal sellArea = ((PlanIndexEntryInfo) obj).getSellArea();
								if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = sellPart;
								}
							} else if (obj instanceof PlanIndexInfo) {
								BigDecimal sellArea = ((PlanIndexInfo) obj).getBigDecimal("allSellArea");
								if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = sellPart;
								}
							}
						}

					} else if (columnName.equals("buildPart")) {
						if (amount != null) {
							if (obj instanceof PlanIndexEntryInfo) {
								BigDecimal buildArea = ((PlanIndexEntryInfo) obj).getBuildArea();
								if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = buildPart;
								}
							} else if (obj instanceof PlanIndexInfo) {
								BigDecimal buildArea = ((PlanIndexInfo) obj).getBigDecimal("allBuildArea");
								if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = buildPart;
								}
							}
						}
					} else {

						for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) aimRowList.get(rowIndex);
							Object value = rowAdd.getCell(columnName).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									sum = sum.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									sum = sum.add(new BigDecimal(((Integer) value).toString()));
								}
							}
						}

					}
					if (sum != null && sum.compareTo(FDCHelper.ZERO) == 0) {
						sum = null;
					}
					row.getCell(columnName).setValue(sum);
				}
			}
		}
		addTotalRow(table);
		
		// //////////////////////////////////////////////////////////////////////////
		FdcManagementUtil.recodeExeTimeAfter(getClass(), "setUnionData", tempMap);
		// //////////////////////////////////////////////////////////////////////////
	}

	public void actionImportApportion_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionImportApportion_actionPerformed(arg0);
	}

	// 处理不完整记录 by hpw 2010.09.27
	private boolean dealWithEmptyRow() {
		String titleStr = "存在分录数据填写不完整的记录，导致调整前合价为零或者为空。 \n \n是否系统自动删除对应记录，然后保存？\n若选择是，则系统自动将信息不完整的纪录行删除，然后保存。\n选择否，则需要返回将对应记录填写完整后才能进行保存！";
		Map detailMap = new HashMap();

		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if (isDetailAcctRow(row) && !isDetailAcctHasInput(row)) {
						// 未录入的不做检查判断
						continue;
					}
					BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
					String key = table.getName();
					if (sumPrice.compareTo(FDCHelper.ZERO) == 0) {
						if (detailMap.containsKey(key)) {
							StringBuffer detail = (StringBuffer) detailMap.get(key);
							detail.append(row.getRowIndex() + 1 + "、");
						} else {
							StringBuffer detail = new StringBuffer();
							detail.append(row.getRowIndex() + 1 + "、");
							detailMap.put(key, detail);
						}
					}

				}
			}
		}
		if (detailMap.size() > 0) {
			StringBuffer msg = new StringBuffer();

			for (Iterator iter = detailMap.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String val = ((StringBuffer) detailMap.get(key)).toString();

				msg.append("页签：");
				msg.append(key);
				msg.append("，");
				msg.append("分录行第 ");
				msg.append(val.substring(0, val.length() - 1));
				msg.append(" 行");
				msg.append("\n");
			}
			int v = MsgBox.showConfirm3(this, titleStr, msg.toString());
			if (v == MsgBox.YES) {
				for (int i = 0; i < tables.size(); i++) {
					KDTable table = (KDTable) tables.get(i);
					int rowCount = table.getRowCount();
					for (int j = rowCount; j > 0; j--) {
						IRow row = table.getRow(j - 1);
						System.out.println(j);
						if (row.getUserObject() instanceof MeasureEntryInfo) {
							if (isDetailAcctRow(row) && !isDetailAcctHasInput(row)) {
								// 未录入的不做检查判断
								continue;
							}
							BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
							if (sumPrice.compareTo(FDCHelper.ZERO) == 0) {
								//								setDetailAcctHasNotInput(row);// 置为无数据状态
								//								for (int k = 3; k < table.getColumnCount() - 1; k++) {
								//									row.getCell(k).setValue(null);// 清空数据
								//								}
								table.removeRow(j - 1);
							}

						}
					}
				}
			}
		}

		return false;
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		dealWithEmptyRow();
		if (this.txtVersionName.getText() == null || this.txtVersionName.getText().length() == 0) {
			MsgBox.showInfo("版本名称不能为空!");
			this.abort();
		}
		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if (isDetailAcctRow(row) && !isDetailAcctHasInput(row)) {
						// 未录入的不做检查判断
						continue;
					}
					int k = table.getColumnIndex(COL_WORKLOAD);
					if (row.getCell(COL_SUM_PRICE).getValue() != null) {
						BigDecimal value = (BigDecimal) row.getCell(COL_WORKLOAD)
								.getValue();
						if (value != null
								&& value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText("工作量超出最大值!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(), k);
							this.abort();
						}
					}

					k = table.getColumnIndex(COL_SUM_PRICE);
					String msg = "合价";
					if (isUseAdjustCoefficient()) {
						// k=table.getColumnIndex("amount");
						msg = "调整前合价";
					}
					//
					if (row.getCell(COL_SUM_PRICE).getValue() == null) {
						this.setMessageText(msg + "不能为空!");
						this.showMessage();
						this.plTables.setSelectedIndex(i);
						table.getSelectManager().select(0, 0);
						table.getSelectManager().select(row.getRowIndex(), k);
						this.abort();
					} else {
						BigDecimal value = (BigDecimal) row.getCell(COL_SUM_PRICE)
								.getValue();
						if (value.compareTo(FDCHelper.ZERO) == 0) {
							this.setMessageText(msg + "不能为0!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(), k);
							this.abort();
						}
						if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText(msg + "超出最大值!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(), k);
							this.abort();
						}
					}
				}
			}
		}
	}
	//add by david_yang R110411-507 2011.04.18
	public void importTemplateMeasureIncome() throws EASBizException, BOSException{
		if(miInfo!=null){
    		String measureIncomeId = getMeasureIncomeId(this.editData.getId().toString());
    		if(measureIncomeId!=null){
    			MeasureIncomeInfo measureIncomeinfo=MeasureIncomeFactory.getRemoteInstance().getMeasureIncomeInfo(new ObjectUuidPK(measureIncomeId));
    			measureIncomeinfo.getIncomeEntry().clear();
    			for(int i=0;i<miInfo.getIncomeEntry().size();i++){
    				MeasureIncomeEntryInfo entry=(MeasureIncomeEntryInfo)miInfo.getIncomeEntry().get(i).clone();
    				entry.setHead(measureIncomeinfo);
    				entry.setId(null);
    				if(!measureIncomeinfo.getProject().equals(miInfo.getProject())){
    					//R110627-0479：目标成本测算跨项目使用模板缺少收入测算. 当模板的收入科目在当前项目中不存在时，导致收入科目无法导入
    					String codingNumber =IncomeAccountFactory.getRemoteInstance().getIncomeAccountInfo(new ObjectUuidPK(miInfo.getIncomeEntry().get(i).getIncomeAccount().getId())).getCodingNumber();
    					String sql = "select * from where codingNumber='"+codingNumber+"' and curProject='"+measureIncomeinfo.getProject().getId()+"'";
    					if (!IncomeAccountFactory.getRemoteInstance().exists(sql)) {
    						//如果模板的收入科目在当前项目中不存在， 则不添加
    						continue;
    					}
    					IncomeAccountInfo account=IncomeAccountFactory.getRemoteInstance().getIncomeAccountInfo(sql);
						entry.setIncomeAccount(account);
    				}
    				measureIncomeinfo.getIncomeEntry().add(entry);
    			}
    			MeasureIncomeFactory.getRemoteInstance().save(measureIncomeinfo);
    		}
		}
	}
	/**
	 * 系统原来的设计是将btnSave看做"暂存", btnSubmit看做"保存"，并不存在"提交"。现客户要求能够在工作流中审批，故必须提供"提交"功能。
	 * 故将actionSubmit_actionPerformed()方法中的代码迁移到actionSave_actionPerformed()中.actionSubmit_actionPerformed()中则为 提交时的逻辑 by Cassiel_peng
	 * 2009-08-18
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		//检测指定分摊是否正确    BT701375   add by jian_cao
		this.measureCollectTable.checkSpecifiedShare();
		
		verifyData();
		addNewBeforeProcess(e);
		this.setOprtState("EDIT");
		/*
		 * if(editData.getId()==null){ editData.setId(BOSUuid.create(editData.getBOSType())); }
		 */
		editData.put("PlanIndex", planIndexTable.getPlanIndexInfo());
		editData.put("constrEntrys", planIndexTable.getConstrEntrys());
		Boolean isEditVersion = (Boolean) getUIContext().get("isEditVersion");
		if (isEditVersion != null && isEditVersion.booleanValue()) {
			handleVersion((MeasureCostInfo) editData);
		}
		// 检查是否可以保存
		if (editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("id", editData.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if (MeasureCostFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this, "测算已审批不能进行此操作");
				SysUtil.abort();
			}
		}
		confirmVersionOnly();

		// modified by zhaoqin for R130819-0171 on 2013/09/11
		((MeasureCostInfo) editData).setState(FDCBillStateEnum.SAVED);
		
		// super.actionSubmit_actionPerformed(e);
		super.actionSave_actionPerformed(e);
		// planIndexTable.save(editData.getId().toString());

		    	//add by david_yang R110411-507 2011.04.18
		    	importTemplateMeasureIncome();
		this.storeFields();
		this.initOldData(this.editData);
		actionImportTemplate.setEnabled(false);

		getUIContext().put("isEditVersion", null);

		setDataChange(false);
	}

	/**
	 * 在保存和提交的时候需要再次确认版本的唯一性 by Cassiel_peng 2009-8-19 版本号校验，排除当前数据，增加阶段,项目 by hpw
	 * 
	 * @throws BOSException
	 */
	public void confirmVersionOnly() throws BOSException {

		Boolean isAimMeasure = (Boolean) getUIContext().get("isAimMeasure");
		String versionNum = this.txtVersionNumber.getText();
		MeasureCostInfo info = (MeasureCostInfo) this.editData;
		CurProjectInfo prj = (CurProjectInfo) this.prmtProject.getValue();
		// 显示.数据库中!
		if (versionNum.indexOf('!') == -1) {
			versionNum = versionNum.replace('.', '!');
		}
		MeasureStageInfo stage = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
		if (isAimMeasure.booleanValue()) {
			String selectOrgId = getUIContext().get("orgId").toString();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
			builder.addParam(selectOrgId);
			if (prj != null && prj.getId() != null) {
				builder.appendSql(" and FProjectID = ? ");
				builder.addParam(prj.getId().toString());
			}
			builder.appendSql(" and FIsAimMeasure = ? ");
			builder.addParam(isAimMeasure);
			builder.appendSql(" and FVersionNumber = ? ");
			builder.addParam(versionNum);
			// 排除当前
			if (info != null && info.getId() != null) {
				builder.appendSql(" and FID <> ? ");
				builder.addParam(info.getId().toString());
			}
			// 阶段
			if (stage != null && stage.getId() != null) {
				builder.appendSql(" and FMeasureStageID =? ");
				builder.addParam(stage.getId().toString());
			}
			IRowSet row = builder.executeQuery();
			if (row.size() != 0) {
				FDCMsgBox.showWarning(this, "该版本号已经存在");
				SysUtil.abort();
			}
		} else {
			String selectOrgId = getUIContext().get("orgId").toString();

			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
			builder.addParam(selectOrgId);
			if (prj != null && prj.getId() != null) {
				builder.appendSql(" and FProjectID = ? ");
				builder.addParam(prj.getId().toString());
			}
			builder.appendSql(" and FIsAimMeasure = ? ");
			builder.addParam(isAimMeasure);
			builder.appendSql(" and FVersionNumber = ? ");
			builder.addParam(versionNum);
			if (info != null && info.getId() != null) {
				builder.appendSql(" and FID <> ? ");
				builder.addParam(info.getId().toString());
			}
			if (stage != null && stage.getId() != null) {
				builder.appendSql(" and FMeasureStageID =? ");
				builder.addParam(stage.getId().toString());
			}
			IRowSet row = builder.executeQuery();
			if (row.size() != 0) {
				FDCMsgBox.showWarning(this, "该版本号已经存在");
				SysUtil.abort();
			}
		}

	}

	protected boolean isModifySave() {
		return isModify();
	}

	
	/**
	 * 原来系统中这个方法中处理的是"保存"单据逻辑，现需要提供"提交"功能故已将该方法原有逻辑迁移至actionSave_actionPerformed()方法中 by Cassiel_peng 2009-08-18
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		//检测指定分摊是否正确    BT701375   add by jian_cao
		this.measureCollectTable.checkSpecifiedShare();
		addNewBeforeProcess(e);
		this.setOprtState("EDIT");
		/*
		 * if(editData.getId()==null){ editData.setId(BOSUuid.create(editData.getBOSType())); }
		 */
		editData.put("PlanIndex", planIndexTable.getPlanIndexInfo());
		editData.put("constrEntrys", planIndexTable.getConstrEntrys());
		Boolean isEditVersion = (Boolean) getUIContext().get("isEditVersion");
		if (isEditVersion != null && isEditVersion.booleanValue()) {
			handleVersion((MeasureCostInfo) editData);
		}
		// 检查是否可以保存

		if (editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("id", editData.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if (MeasureCostFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this, "测算已审批不能进行此操作");
				SysUtil.abort();
			}
		}
		confirmVersionOnly();

		super.actionSubmit_actionPerformed(e);
		// 提交了之后要将"导入模板"灰掉 ，只有在新增单据的时候允许使用"导入模板" by Cassiel_peng 2009-8-19
		
		//add by david_yang R110411-507 2011.04.18
    	importTemplateMeasureIncome();
    	
		actionImportTemplate.setEnabled(false);
		this.storeFields();
		this.initOldData(this.editData);
		actionImportTemplate.setEnabled(false);

		getUIContext().put("isEditVersion", null);

		setDataChange(false);
		
		actionSave.setEnabled(false);
	}
	

	private void addNewBeforeProcess(ActionEvent e) {
		if (OprtState.ADDNEW.equals(this.getOprtState()) && !importFlag) {
			MeasureCostInfo cost = (MeasureCostInfo) this.editData;
			cost.setId(null);
			cost.setIsLastVersion(false);
//			cost.setSrcMeasureCostId(null);当存在上一阶段最终版本时，initMeasureInfo中srcid需要设置为上一阶段的，bean中收入测算数据才能带入上一版本，已有功能啊哥
			if (e.getActionCommand().toString().equals(ActionSave.class.getName())) {
				cost.setState(FDCBillStateEnum.SAVED);
			} else if (e.getActionCommand().toString().equals(ActionSubmit.class.getName())) {
				cost.setState(FDCBillStateEnum.SUBMITTED);
			}
			MeasureEntryCollection costEntry = cost.getCostEntry();
			for (int i = 0; i < costEntry.size(); i++) {
				MeasureEntryInfo measureEntryInfo = costEntry.get(i);
				measureEntryInfo.setId(null);
				measureEntryInfo.setHead(cost);
			}
		}
	}
	  protected void doBeforeSubmit(ActionEvent e) throws Exception{
	        // Action前的捆绑执行动作，包括：beforeStoreFields(e)、storeFields()、verifyInput(e)
	        verify(e);

	        //2005-8-21
	        //在单据提交的时候，发现是AddNew（或者值对象的ID为空），在提交前调用如下方法：
	        doBeforeSubmitForWF(this.editData);
	        FDCSplitBillUtil.checkMeasureCostData((MeasureCostInfo)editData);
	        
	    }
	

	/**
	 * @see com.kingdee.eas.framework.client.EditUI
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI
	 * 由于该UI直接继承的框架的EditUI,没有继承CoreBillEditUI或者是FDCBillEditUI(这两个类里有重写以下两个方法)
	 * 框架中的保存和提交成功的时候提示信息不准确,故自己再重写    by Cassiel_peng  2009-8-19
	 */
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Edit"));
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}

	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Submit_OK"));
		if (this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_AddNew"));
		} else if (!this.chkMenuItemSubmitAndPrint.isSelected() && this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Edit"));
		}
		setIsShowTextOnly(false);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		showMessage();
	}

	/**
	 * 描述：刷新所有测算表格(table index: 2-X)
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-12
	 */
	private void refreshAllMeasureTable() {
		// //////////////////////////////////////////////////////////////////////////
		Map map = FdcManagementUtil.recodeExeTimeBefore(getClass(), "refreshAllMeasureTable");
		// //////////////////////////////////////////////////////////////////////////
		
		// 先刷新当前页签
		int selectedIndex = plTables.getSelectedIndex();
		if (selectedIndex >= 3 && selectedIndex < tables.size()) {
			KDTable table = (KDTable) tables.get(selectedIndex);
			refreshMeasureTable(table);
		}
		
		// 再刷新其他页签
		for (int i = 3; i < tables.size(); i++) {
			if (selectedIndex == i) {
				continue;
			}
			
			KDTable table = (KDTable) tables.get(i);
			refreshMeasureTable(table);
		}

		// //////////////////////////////////////////////////////////////////////////
		FdcManagementUtil.recodeExeTimeAfter(getClass(), "refreshAllMeasureTable", map);
		// //////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 描述：刷新测算表格
	 * 
	 * @param table
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-12
	 */
	private void refreshMeasureTable(KDTable table) {
		// //////////////////////////////////////////////////////////////////////////
		Map tempMap = FdcManagementUtil.recodeExeTimeBefore(getClass(), "refreshMeasureTable" + "_" + table.getName());
		// //////////////////////////////////////////////////////////////////////////

		if (table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) {
			ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0).getUserObject();
			table.getHeadRow(0).getCell(0).setUserObject(
					planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
			ICellEditor editor = getIndexEditor(CostAccountTypeEnum.MAIN, product.getId().toString());
			table.getColumn("indexName").setEditor(editor);
		} else {
			PlanIndexInfo planIndexInfo = planIndexTable.getPlanIndexInfo();
			ICellEditor editor = getIndexEditor(CostAccountTypeEnum.SIX, null);
			table.getColumn("indexName").setEditor(editor);
			BigDecimal amount = planIndexTable.getAllSellArea(planIndexInfo);
			planIndexInfo.put("allSellArea", amount);
			amount = planIndexTable.getAllBuildArea(planIndexInfo);
			planIndexInfo.put("allBuildArea", amount);
			table.getHeadRow(0).getCell(0).setUserObject(planIndexInfo);
		}

		for (int j = 0; j < table.getRowCount(); j++) {
			IRow row = table.getRow(j);
			if (row.getUserObject() instanceof MeasureEntryInfo) {
				try {
					table_editStopped(table, new KDTEditEvent(table, null, null, j, table.getColumnIndex("indexName"),
							false, 1));
					// table_editStopped(table, new
					// KDTEditEvent(table,null,null,j,table.getColumnIndex("sumPrice"),false,1));
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
		}

		// //////////////////////////////////////////////////////////////////////////
		FdcManagementUtil.recodeExeTimeAfter(getClass(), "refreshMeasureTable" + "_" + table.getName(), tempMap);
		// //////////////////////////////////////////////////////////////////////////
	}



	/**
	 * get new data from six or main table to measureCostMap
	 */
	void refreshMeasureCostMap() {
		storeFields();
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
		// this.txtProject.setText(cost.getProject().getName());
		// this.txtVersionNumber.setText(cost.getVersionNumber());
		// this.txtVersionName.setText(cost.getVersionName());
		measureCostMap.clear();
		MeasureEntryCollection costEntrys = cost.getCostEntry();
		for (int i = 0; i < costEntrys.size(); i++) {
			MeasureEntryInfo info = costEntrys.get(i);
			CostAccountInfo costAccount = info.getCostAccount();
			String key = costAccount.getId().toString();
			if (info.getProduct() != null) {
				key += info.getProduct().getId().toString();
			}
			if (measureCostMap.containsKey(key)) {
				MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(key);
				coll.add(info);
			} else {
				MeasureEntryCollection newColl = new MeasureEntryCollection();
				newColl.add(info);
				measureCostMap.put(key, newColl);
			}
		}
	}

	TreeModel getCostAcctTree() {
		return costAcctTree;
	}

	Map getMeasureCostMap() {
		return measureCostMap;
	}

	PlanIndexTable getPlanIndexTable() {
		return planIndexTable;
	}

	KDTabbedPane getplTables() {
		return plTables;
	}

	List getTables() {
		return tables;
	}

	/**
	 * 汇总表产品分摊使用指标+自定义指标
	 * 
	 * @return
	 */
	KDTDefaultCellEditor getCollectIndexEditor() {
		if (isMeasureIndex()) {
			return getCollectMeasureIndexEditor();
		}
		Object[] items = getCollectItems();
		KDComboBox box = new KDComboBox(items);
		return new KDTDefaultCellEditor(box);
	}

	/**
	 * 汇总表产品分摊使用指标+自定义指标
	 * 
	 * @return
	 */
	KDTDefaultCellEditor getCollectMeasureIndexEditor() {
		Object[] items = new Object[0];
		if (nameSet != null) {
			Object[] nameItems = new Object[nameSet.size()];
			int h = 0;
			for (int i = 0; i < getCollectItems().length; i++) {
				String name = (String) getCollectItems()[i].toString();
				if (nameSet.contains(name)) {
					nameItems[h] = getCollectItems()[i];
					h++;
				}
			}
			items = nameItems;

		}
		KDComboBox box = new KDComboBox(items);
		return new KDTDefaultCellEditor(box);
	}

	KDTDefaultCellEditor getIndexEditor(CostAccountTypeEnum type, String productId) {
		Object[] items = null;
		if (type == CostAccountTypeEnum.SIX) {
			items = getSixItems();
		} else if (type == CostAccountTypeEnum.MAIN) {
			items = getMainItems();
		} else {
			return null;
		}
		KDComboBox box = new KDComboBox(items);
		return new KDTDefaultCellEditor(box);
	}

	private Set nameSet = null;

	private void initMeasureIndex() throws BOSException {
		nameSet = new HashSet();
		if (isMeasureIndex()) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("type", ApportionTypeEnum.STANDARD_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.getSelector().add("name");
			MeasureIndexCollection indexNames = MeasureIndexFactory.getRemoteInstance().getMeasureIndexCollection(view);
			if (indexNames != null) {
				for (int i = 0; i < indexNames.size(); i++) {
					nameSet.add(indexNames.get(i).getName());
				}
			}
		}
	}

	private Object[] COLLECTITEMS = null;

	private Object[] getCollectItems() {

		COLLECTITEMS = null;
		if (COLLECTITEMS == null) {
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
			if (customPlanIndexs.size() == 0) {
				COLLECTITEMS = Item.SPLITITEMS;
			} else {
				List list = new ArrayList();
				Set appSet = new HashSet();
				for (int i = 0; i < customPlanIndexs.size(); i++) {
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if (!appSet.contains(appId)) {
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				COLLECTITEMS = new Item[list.size() + Item.SPLITITEMS.length];
				System.arraycopy(Item.SPLITITEMS, 0, COLLECTITEMS, 0, Item.SPLITITEMS.length);
				int i = Item.SPLITITEMS.length;
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					COLLECTITEMS[i++] = iter.next();
				}
			}
		}
		return COLLECTITEMS;
	}

	private Object[] MAINITEMS = null;

	private Object[] getMainItems() {
		MAINITEMS = null;
		if (MAINITEMS == null) {
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
			if (customPlanIndexs.size() == 0) {
				MAINITEMS = Item.PRODUCTITEMS;
			} else {
				List list = new ArrayList();
				Set appSet = new HashSet();
				for (int i = 0; i < customPlanIndexs.size(); i++) {
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if (!appSet.contains(appId)) {
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				MAINITEMS = new Item[list.size() + Item.PRODUCTITEMS.length];
				System.arraycopy(Item.PRODUCTITEMS, 0, MAINITEMS, 0, Item.PRODUCTITEMS.length);
				int i = Item.PRODUCTITEMS.length;
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					MAINITEMS[i++] = iter.next();
				}
			}
		}
		return MAINITEMS;
	}

	public static class Item {
		String key = null;
		String name = null;
		String productId = null;
		BigDecimal sellArea = null;
		boolean isCustom = false;

		Item(String key, String name) {
			this.key = key;
			this.name = name;
		}

		private Item(ApportionTypeInfo info) {
			this.key = info.getId().toString();
			this.name = info.getName();
			isCustom = true;
		}

		private static Map hashMap = null;

		public static Item getCustomItem(ApportionTypeInfo info) {
			if (hashMap == null) {
				hashMap = new HashMap();
			}
			Item item = (Item) hashMap.get(info.getId());
			if (item == null) {
				item = new Item(info);
			}
			hashMap.put(info.getId(), item);
			return item;

		}

		public boolean isCustomIndex() {
			return isCustom;
		}

		public String toString() {
			return name;
		}

		public int hashCode() {
			return super.hashCode();
		}

		/**
		 * 六类公摊使用指标
		 * TODO 后续需要优化
		 */
		public static Item [] SIXITEMS=new Item[]{
			new Item("empty",	""),	
			new Item("totalContainArea",	"总占地面积"),	
			new Item("buildArea",	"建筑用地面积"),	
			new Item("totalBuildArea",	"总建筑面积"),	
			new Item("buildContainArea",	"建筑物占地面积"),
//			new Item("buildDensity",	"建筑密度	"),
//			new Item("greenAreaRate",	"绿地率"),	
			new Item("cubageRateArea",	"计容积率面积"),	
//			new Item("cubageRate",	"容积率"),	
			new Item("totalRoadArea",	"道路用地合计"),	
			new Item("totalGreenArea",	"绿化用地合计	"),
			new Item("pitchRoad",	"沥青路面车行道"),
			new Item("concreteRoad",	"砼路面车行道（停车场）"),	
			new Item("hardRoad",	"硬质铺装车行道"),
			new Item("hardSquare",	"硬质铺装广场	"),
			new Item("hardManRoad",	"硬质铺装人行道"),
			new Item("importPubGreenArea",	"重要公共绿地	"),
			new Item("houseGreenArea",	"组团宅间绿化	"),
			new Item("privateGarden",	"底层私家花园	"),
			new Item("warterViewArea",	"水景面积"),
			new Item("viewArea",	"景观面积")//,
//			new Item("doors",	"户数")
			
		};

		/**
		 * 产品使用指标
		 */
		public static Item [] PRODUCTITEMS=new Item[]{
				new Item("empty",	""),
				new Item("containArea",	"占地面积"),	
				new Item("buildArea",	"建筑面积"),	
				new Item("sellArea",	"可售面积"),	
				new Item("cubageRate",	"容积率"),	
				new Item("productRate",	"产品比例"),	
				new Item("unitArea",	"平均每户面积"),	
				new Item("units",	"单元数"),	
				new Item("doors",	"户数"),
				new Item("elevators", "电梯"),	/* modified by zhaoqin for R130917-0324 on 2013/11/21 */
		};
		
		/**
		 * 汇总表产品分摊使用指标
		 */
		public static Item [] SPLITITEMS = new Item[] { 
				new Item("man", "指定分摊"), 
				new Item("buildArea", "建筑面积"), 
				new Item("sellArea", "可售面积"),
				new Item("containArea", "占地面积"),
				new Item("cubageRate", "容积率"), 
				new Item("productRate", "产品比例"), 
				new Item("unitArea", "平均每户面积"), 
				new Item("units", "单元数"),
				new Item("doors", "户数	"),

		};

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
		if (comboMeasureStage.getSelectedItem() == null) {
			FDCMsgBox.showWarning(this, "测算阶段不能为空");
			this.abort();
		}
		super.verifyInput(e);
		
	}

	private boolean importFlag = false;
	public void actionImportTemplate_actionPerformed(ActionEvent e) throws Exception {
		importFlag = true;
		TimeTools.getInstance().setDebug(true);
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("start");
		Map context = new HashMap();
		String orgId = (String) getUIContext().get("orgId");
		MeasureStageInfo measureStage = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
		context.put("orgUnit.id", orgId);
		context.put("measureStage", measureStage);
		context.put(UIContext.OWNER, this);
		context.put("isAimMeasure", getUIContext().get("isAimMeasure"));
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			IUIWindow classDlg = uiFactory.create("com.kingdee.eas.fdc.aimcost.client.TemplateMeasureCostListUI", context, null, null,
					WinStyle.SHOW_KINGDEELOGO);
			classDlg.show();
			TemplateMeasureCostListUI ui = (TemplateMeasureCostListUI) classDlg.getUIObject();
			if (!ui.isCancel()) {
				TemplateMeasureCostCollection data = ui.getData();
				// String id=(this.editData!=null&&this.editData.getId()!=null)?this.editData.getId().toString():null;
				if (data != null && data.size() > 0) {

					String versionName = this.txtVersionName.getText();
					String versionNumber = this.txtVersionNumber.getText();
					Object projectType = prmtProjectType.getData();
					Object project = prmtProject.getData();
					TimeTools.getInstance().msValuePrintln("start storeFromTemplate");
					// 如果选择的是工程项目则使用工程项目
					String objectId = orgId;
					if (project != null) {
						objectId = ((CurProjectInfo) project).getId().toString();
					}
					MeasureCostInfo editData2 = MeasureCostFactory.getRemoteInstance().getMeasureFromTemplate(
							data.get(0).getId().toString(), objectId);
					// MeasureCostInfo editData2 = getTemplate(data.get(0).getId().toString(), objectId);
					// TODO 通过目标成本模版ID和模版所在工程项目中的ID作为条件取出模版分录
					editData2 = getTemplate(data.get(0).getId().toString(), data.get(0).getProject().getId().toString(), editData2);
					// TODO 把模版所在工程项目中的成本科目转成所引用工程的成本科目
					MeasureStageInfo selectedItem = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
					if (selectedItem != null) {
						editData2 = acctChangeToCurrentAcct(objectId, selectedItem.getId().toString(), editData2);
					}
					editData2.setIsAimMeasure(isAimMeasure());
					editData2.setCreateTime(null);
					editData2.setCreator(null);
					// 组织
					FullOrgUnitInfo org = new FullOrgUnitInfo();
					org.setId(BOSUuid.read(orgId));
					editData2.setOrgUnit(org);
					// 跨项目
					editData2.setProject((CurProjectInfo) project);// 项目
					editData2.setProjectType(null);// 项目系列
					MeasureStageInfo measureStageInfo = (MeasureStageInfo) this.comboMeasureStage.getSelectedItem();
					if (measureStageInfo != null) {
						MeasureCostVersionHandler version = new MeasureCostVersionHandler(orgId, objectId, editData2.isIsAimMeasure(),
								measureStageInfo);
						versionNumber = MeasureCostVersionHandler.getNextVersion(version.getLastVersion());
						editData2.setVersionNumber(versionNumber);
					}

					editData.putAll(editData2);

					loadFields();

					// //////////////////////////////////////////////////////////////////////////
					// 刷新所有测算表格(table index: 2-X)
					refreshAllMeasureTable();
					// //////////////////////////////////////////////////////////////////////////

					/*
					 * if(id!=null){ this.editData.setId(BOSUuid.read(id)); }
					 */
					// setDataObject(editData);
					TimeTools.getInstance().msValuePrintln("end storeFromTemplate");
					isFirstLoad = true;
					txtVersionName.setText(versionName);
					if (!(editData2.getVersionNumber() == null)) {
						this.txtVersionNumber.setText(editData2.getVersionNumber().replaceAll("!", "\\."));
					}
					if (projectType != null) {
						prmtProjectType.setData(projectType);
					}

					if (project != null) {
						prmtProject.setData(project);
					}
				}

				setDataChange(true);
			}
			TimeTools.getInstance().msValuePrintln("end");

		} catch (BOSException ex) {
			ExceptionHandler.handle(this, ex);
			return;
		}
	}


	/**
	 * 获取当前工程项目目标成本测算模版分录数据，并转换为目标成本测算分录对象
	 * 
	 * @param headId
	 * @param projectId
	 * @return
	 */
	private MeasureCostInfo getTemplate(String headId, String projectId, MeasureCostInfo mcInfo) {
		TemplateMeasureEntryCollection tempCol = new TemplateMeasureEntryCollection();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("costAccount.*");
		view.getSelector().add("product.*");
		view.getSelector().add("head.*");
		view.getSelector().add("unit.*");
		view.getSelector().add("CU.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", headId, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.project.id", projectId, CompareType.EQUALS));
		view.setFilter(filter);
		try {
			tempCol = TemplateMeasureEntryFactory.getRemoteInstance().getTemplateMeasureEntryCollection(view);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		MeasureEntryCollection costEntry = mcInfo.getCostEntry();
		costEntry.clear();
		for (int i = 0; i < tempCol.size(); i++) {
			TemplateMeasureEntryInfo tmeInfo = tempCol.get(i);
			MeasureEntryInfo meInfo = new MeasureEntryInfo();
			meInfo.setCostAccount(tmeInfo.getCostAccount());
			meInfo.setEntryName(tmeInfo.getEntryName());
			meInfo.setWorkload(tmeInfo.getWorkload());
			meInfo.setCoefficientName(tmeInfo.getCoefficientName());
			meInfo.setCoefficient(tmeInfo.getCoefficient());
			meInfo.setPrice(tmeInfo.getPrice());
			meInfo.setCostAmount(tmeInfo.getCostAmount());
			meInfo.setProduct(tmeInfo.getProduct());
			// TemplateMeasureCostInfo head = tmeInfo.getHead();
			// if (head != null) {
			// MeasureCostInfo headInfo = new MeasureCostInfo();
			// headInfo.setId(head.getId());
			// meInfo.setHead(headInfo);
			// }
			meInfo.setAmount(tmeInfo.getAmount());
			meInfo.setAdjustAmt(tmeInfo.getAdjustAmt());
			meInfo.setAdjustCoefficient(tmeInfo.getAdjustCoefficient());
			meInfo.setUnit(tmeInfo.getUnit());
			meInfo.setProgram(tmeInfo.getProgram());
			meInfo.setDesc(tmeInfo.getDesc());
			meInfo.setChangeReason(tmeInfo.getChangeReason());

			meInfo.setName(tmeInfo.getName());
			meInfo.setNumber(tmeInfo.getNumber());
			meInfo.setDescription(tmeInfo.getDescription());
			meInfo.setSimpleName(tmeInfo.getSimpleName());

			meInfo.setCU(tmeInfo.getCU());

			costEntry.add(meInfo);
		}
		return mcInfo;
	}

	/**
	 * 把模版中关联的成本科目，转成关联本工程项目中成本科目
	 * 
	 * @param currentProjectId
	 *            本工程项目ID
	 * @param measureStageId
	 * @param mcInfo
	 * @return
	 */
	private MeasureCostInfo acctChangeToCurrentAcct(String currentProjectId, String measureStageId, MeasureCostInfo mcInfo) {
		MeasureEntryCollection costEntry = mcInfo.getCostEntry();
		Map acctMap = getAcctMap(currentProjectId, measureStageId);
		for (int i = 0; i < costEntry.size(); i++) {
			MeasureEntryInfo meInfo = costEntry.get(i);
			final CostAccountInfo acct = CostAccountHelper.getCostAccount1(acctMap, meInfo.getCostAccount().getLongNumber());
			if (acct == null) {
				continue;
			}
			meInfo.setCostAccount(acct);
			// modify by lihaiou, 2013.08.5, fix bug R130717-0252
			//			for (int j = 0; j < i; j++) {
			//				MeasureEntryInfo entry = costEntry.get(j);
			//				if ((meInfo.getProduct() == null && entry.getProduct() == null)
			//						|| (meInfo.getProduct() != null && entry.getProduct() != null && meInfo.getProduct().getId().toString().equals(
			//								entry.getProduct().getId().toString())))
			//					if (entry.getCostAccount().getId().toString().equals(acct.getId().toString())) {
			//						entry.setCostAmount(FDCHelper.add(entry.getCostAmount(), meInfo.getCostAmount()));
			//						entry.setAmount(FDCHelper.add(entry.getAmount(), meInfo.getAmount()));
			//						costEntry.remove(meInfo);
			//						i--;
			//						break;
			//					}
			//			}
			// modify end 
		}
		return mcInfo;
	}

	/**
	 * 按工程项目或组织中某测算阶段取出成本科目
	 * 
	 * @param objectId
	 * @param measureStageId
	 * @return 成本科目longNumber做key，成本科目做value的TreeMap
	 */
	private Map getAcctMap(String objectId, String measureStageId) {
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())) {
			filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.id", objectId, CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id", objectId, CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id", measureStageId, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("value", new Integer(1), CompareType.EQUALS));
		AccountStageCollection asCol1;
		try {
			asCol1 = AccountStageFactory.getRemoteInstance().getAccountStageCollection(view);
			for (Iterator iter = asCol1.iterator(); iter.hasNext();) {
				AccountStageInfo info = (AccountStageInfo) iter.next();
				map.put(info.getCostAccount().getLongNumber(), info.getCostAccount());
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		return map;
	}

	/**
	 * 找出最后一个测算阶段编码值（目标成本关联测算阶段编码最大为最后一阶段，编码数值越大，阶段越后）
	 * 
	 * @param stageInfo
	 * @param project
	 * @return
	 */
	private BigDecimal fetchMeaStaMaxNumber(CurProjectInfo project) {
		BigDecimal maxNumber = new BigDecimal("0");
		BigDecimal tempNumber;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("measureStage.number");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", project.getId().toString(), CompareType.EQUALS));
		view.setFilter(filter);
		try {
			MeasureCostCollection measureCostCol = MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
			for (int i = 0; i < measureCostCol.size(); i++) {
				MeasureCostInfo info = measureCostCol.get(i);
				String number = info.getMeasureStage().getNumber();
				if (number != null) {
					tempNumber = new BigDecimal(number);
					if (maxNumber.compareTo(tempNumber) < 0) {
						maxNumber = tempNumber;
					}
				}
			}
			return maxNumber;
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return maxNumber;
	}

	protected void comboMeasureStage_itemStateChanged(ItemEvent e) throws Exception {
		super.comboMeasureStage_itemStateChanged(e);
		MeasureStageInfo stageInfo = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
		if (stageInfo == null || project == null) {
			return;
		}
		if (STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())) {
			// String msg = AimCostClientHelper.getRes("addNew");
			Boolean isAimMeasure = (Boolean) getUIContext().get("isAimMeasure");
			// MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(project, isAimMeasure.booleanValue());
			// if (lastStageInfo != null
			// && FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO) == 1) {
			// comboMeasureStage.setSelectedItem(null);
			// StringBuffer sb = new StringBuffer();
			// sb.append("已存在 [ ").append(lastStageInfo.getNumber()).append(lastStageInfo.getName()).append(" ] 最终版本的目标成本测算,不能").append(
			// msg).append(" [ ").append(stageInfo.getNumber()).append(stageInfo.getName()).append(" ] 目标成本测算。");
			// FDCMsgBox.showWarning(sb.toString());
			// SysUtil.abort();
			// }
			/**
			 * 目标成本测算新增时，根据目标成本最终版本对应的测算阶段及版本号，生成同一测算阶段的新的版本的目标成本测算，并且允许修改测算阶段为后一阶段，同时变更相应的版本。
			 */
			MeasureCostInfo info = ((MeasureCostInfo) editData);
			if (info != null) {
				MeasureCostVersionHandler version = new MeasureCostVersionHandler(info.getOrgUnit().getId().toString(), info.getProject()
						.getId().toString(), isAimMeasure.booleanValue(), stageInfo);
				String versionNumber = version.getLastVersion();
				if (versionNumber.indexOf('!') == -1) {
					versionNumber = versionNumber.replace('.', '!');
				}
				txtVersionNumber.setText(MeasureCostVersionHandler.getNextVersion(versionNumber).replaceAll("!", "\\."));
			}

		}
		MeasureCostInfo cost = (MeasureCostInfo) this.editData;
		initAccTree(cost, project.getId().toString());
		// cost = initMeasureInfo(cost);
		if (OprtState.ADDNEW.equals(this.getOprtState()) && !importFlag) {// 新增时，修改测算阶段变更成本科目引用ID
			if (cost != null) {
				cost.getCostEntry().clear();
				MeasureCostInfo mcInfo = getLastStage();
				if (mcInfo != null) {
					MeasureCostInfo measureCostInfo = initMeasureInfo(mcInfo);
					MeasureStageInfo stageItem = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
					if (stageItem != null && project != null) {
						acctChangeToCurrentAcct(project.getId().toString(), stageItem.getId().toString(), measureCostInfo);
					}
					cost.putAll(measureCostInfo);
				}
				measureCostMap.clear();
				MeasureEntryCollection costEntrys = cost.getCostEntry();
				for (int i = 0; i < costEntrys.size(); i++) {
					MeasureEntryInfo info = costEntrys.get(i);
					CostAccountInfo costAccount = info.getCostAccount();
					String key = costAccount.getId().toString();
					if (info.getProduct() != null) {
						key += info.getProduct().getId().toString();
					}
					if (measureCostMap.containsKey(key)) {
						MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(key);
						coll.add(info);
					} else {
						MeasureEntryCollection newColl = new MeasureEntryCollection();
						newColl.add(info);
						measureCostMap.put(key, newColl);
					}
				}
			}
		} else if (OprtState.ADDNEW.equals(this.getOprtState()) && importFlag) {// 使用模版后，修改测算阶段变更成本科目引用ID
			// modified by zhaoqin on 2013/10/25
			cost.getCostEntry().clear();
			
			MeasureCostInfo mcInfo = initMeasureInfo(cost);
			if (mcInfo != null) {
				MeasureCostInfo measureCostInfo = initMeasureInfo(mcInfo);
				cost.putAll(measureCostInfo);
			}
			measureCostMap.clear();
			MeasureEntryCollection costEntrys = cost.getCostEntry();
			for (int i = 0; i < costEntrys.size(); i++) {
				MeasureEntryInfo info = costEntrys.get(i);
				CostAccountInfo costAccount = info.getCostAccount();
				String key = costAccount.getId().toString();
				if (info.getProduct() != null) {
					key += info.getProduct().getId().toString();
				}
				if (measureCostMap.containsKey(key)) {
					MeasureEntryCollection coll = (MeasureEntryCollection) measureCostMap.get(key);
					coll.add(info);
				} else {
					MeasureEntryCollection newColl = new MeasureEntryCollection();
					newColl.add(info);
					measureCostMap.put(key, newColl);
				}
			}
		}
		initAccoutStageMap(project.getId().toString(), getMeasureStageID());

		for (int i = 0; i < plTables.getComponentCount(); i++) {
			Component[] components = plTables.getComponents();
			if (components[i] instanceof KDTable) {
				KDTable table = (KDTable) components[i];
				if (table.getName().equals(Shared_Cost)) {
					fillTable(table);
					setUnionData(table);
					setLastRowEnable(table);
				}
				if (table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) {
					PlanIndexInfo info = planIndexTable.getPlanIndexInfo();
					for (int j = 0; j < info.getEntrys().size(); j++) {
						PlanIndexEntryInfo entry = info.getEntrys().get(j);
						if (entry.getProduct() != null) {
							ProductTypeInfo product = entry.getProduct();
							if (((ProductTypeInfo) table.getHeadRow(0).getUserObject()).getId().equals(product.getId())) {
								fillTable(table);
								setUnionData(table);
								setLastRowEnable(table);
							}
						}
					}
				}
			}
		}
		if (measureCollectTable != null) {
			measureCollectTable.refresh();
		}

	}

	/**
	 * 按阶段显示最明细节点成本科目行设置为可录入
	 * 
	 * @param table
	 */
	private void setLastRowEnable(KDTable table) {
		IRow row = null;
		IRow nextRow = null;
		String currentRowNumber = null;
		String nextRowNumber = null;
		StyleAttributes styleAttributes = null;
		
		Color backgroundColor = new Color(0xFFFFFF);
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			nextRow = table.getRow(i + 1);

			if (nextRow != null) {
				currentRowNumber = (String) row.getCell("acctNumber").getValue();
				nextRowNumber = (String) nextRow.getCell("acctNumber").getValue();

				styleAttributes = row.getStyleAttributes();
				if (nextRowNumber != null) {
					if (nextRowNumber.lastIndexOf('.') != -1) {
						if (!nextRowNumber.substring(0, nextRowNumber.lastIndexOf('.')).equals(currentRowNumber)) {
							styleAttributes.setLocked(false);
							styleAttributes.setBackground(backgroundColor);
						}
					} else {
						styleAttributes.setLocked(false);
						styleAttributes.setBackground(backgroundColor);
					}
				} else {
					styleAttributes.setLocked(false);
					styleAttributes.setBackground(backgroundColor);
				}
			} else {
				styleAttributes.setLocked(false);
				styleAttributes.setBackground(backgroundColor);
			}
		}
	}

	public void actionExportAllToExcel_actionPerformed(ActionEvent e) throws Exception {

		ExportManager exportM = new ExportManager();
		String path = null;
		File tempFile = File.createTempFile("eastemp", ".xls");
		path = tempFile.getCanonicalPath();

		KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[tables.size()];
		for (int i = 0; i < tables.size(); i++) {
			tablesVO[i] = new KDTables2KDSBookVO((KDTable) tables.get(i));
			String title = plTables.getTitleAt(i);
			title = title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i].setTableName(title);
		}
		KDSBook book = null;
		book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO, true, true);

		exportM.exportToExcel(book, path);

		// 尝试用excel打开
		try {
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		} catch (IOException e2) {
			// excel打开失败，保存到指定位置
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == KDFileChooser.APPROVE_OPTION) {
				// File dest = this.getFileChooser().getSelectedFile();
				File dest = fileChooser.getSelectedFile();
				try {
					// 重命名临时文件到指定目标
					File src = new File(path);
					if (dest.exists())
						dest.delete();
					src.renameTo(dest);
				} catch (Exception e3) {
					handUIException(e3);
				}
			}
		}

	}

	public void onShow() throws Exception {

		this.setQueryPreference(false);
		super.onShow();
		
		//////////////////////////////////////////////////////////////////////////
		// 使用多线程刷新页签 by skyiter_wang 2015/02/07
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
			public void run() {
				// //////////////////////////////////////////////////////////////////////////
				// 刷新所有测算表格(table index: 2-X)
				refreshAllMeasureTable();
				// //////////////////////////////////////////////////////////////////////////
				
				setDataChange(false);
			}
		});
		//////////////////////////////////////////////////////////////////////////
		
		if (!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)) {
			for (int i = 0; i < tables.size(); i++) {
				if (i == 2)
					continue;
				((KDTable) tables.get(i)).getStyleAttributes().setLocked(true);
				if (i > 2) {
					ICellEditor editor = ((KDTable) tables.get(i)).getColumn("workload").getEditor();
					if (editor != null && editor.getComponent() != null) {
						editor.getComponent().setEnabled(false);
					}
					editor = ((KDTable) tables.get(i)).getColumn("sumPrice").getEditor();
					if (editor != null && editor.getComponent() != null) {
						editor.getComponent().setEnabled(false);
					}

				}
			}
			// actionAddRow.setVisible(false);
			actionAddRow.setEnabled(false);
			// actionDeleteRow.setVisible(false);
			actionDeleteRow.setEnabled(false);

		}
		// 工程项目不让选择，必须新增的时候带过来，否者请重新生成
		this.prmtProject.setEnabled(false);
		
		// modified by zhaoqin for R130819-0171 on 2013/09/11
		activeSaveAction();
	}

	/**
	 * 修订处理
	 */
	private void handleVersion(MeasureCostInfo info) {
		if (info.getId() == null) {// 第二次进来info为修订版本，返回
			return;
		}
		// 设置源版本，如果对修订的版本进行修订则源版本与之同，否则为源版本
		if (info.getSourceBillId() == null) {
			info.setSourceBillId(info.getId().toString());
		}
		info.setSrcMeasureCostId(info.getId().toString());
		info.setId(null);
		info.setIsLastVersion(false);
		info.setState(FDCBillStateEnum.SAVED);
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		Timestamp timeStamp = null;
		try {
			timeStamp = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			handUIException(e);
		}
		info.setCreateTime(timeStamp);
		info.setLastUpdateTime(timeStamp);
		info.setCreator(user);
		info.setLastUpdateUser(user);
		for (Iterator iter = info.getCostEntry().iterator(); iter.hasNext();) {
			((MeasureEntryInfo) iter.next()).setId(null);
		}
		for (Iterator iter = info.getConstrEntrys().iterator(); iter.hasNext();) {
			((ConstructPlanIndexEntryInfo) iter.next()).setId(null);
		}
		PlanIndexInfo planIndex = (PlanIndexInfo) editData.get("PlanIndex");
		if (planIndex == null)
			return;
		planIndex.setId(null);
		for (Iterator iter = planIndex.getEntrys().iterator(); iter.hasNext();) {
			((PlanIndexEntryInfo) iter.next()).setId(null);
		}
		for (Iterator iter = planIndex.getCustomEntrys().iterator(); iter.hasNext();) {
			((CustomPlanIndexEntryInfo) iter.next()).setId(null);
		}
	}

	private boolean hasChanged = false;

	void setDataChange(boolean hasChange) {
		hasChanged = hasChange;
	}

	public boolean isModify() {
		// 查看状态不判断是否修改
		if (OprtState.VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}

	private FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) editData;
	}

	/**
	 * 得到初始的规划指标表
	 * 
	 * @throws BOSException
	 */
	private PlanIndexInfo getInitPlanIndexInfo() throws BOSException {
		if (editData == null) {
			return null;
		}
		if (editData.get("PlanIndex") == null && editData.getId() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("headID", editData.getId().toString());

			SelectorItemCollection selector = view.getSelector();
			selector.add("*");
			selector.add("entrys.*");
			selector.add("entrys.product.*");
			selector.add("customEntrys.*");
			selector.add("customEntrys.productType.id");
			selector.add("customEntrys.productType.number");
			selector.add("customEntrys.productType.name");
			selector.add("customEntrys.apportType.id");
			selector.add("customEntrys.apportType.number");
			selector.add("customEntrys.apportType.name");
			selector.add("customEntrys.value");
			view.getSorter().add(new SorterItemInfo("entrys.type"));
			view.getSorter().add(new SorterItemInfo("entrys.index"));
			PlanIndexCollection planIndexCollection = PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
			if (planIndexCollection.size() == 1) {
				PlanIndexInfo planIndexInfo = planIndexCollection.get(0);
				//下一测算阶段时，带入上一阶段指标
				if (OprtState.ADDNEW.equals(this.getOprtState()) && !importFlag) {
					planIndexInfo.setId(null);
					if(planIndexInfo!=null){
						if(planIndexInfo.getEntrys()!=null&&planIndexInfo.getEntrys().size()!=0){
							for(int i=0;i<planIndexInfo.getEntrys().size();i++){
								PlanIndexEntryInfo entryInfo = planIndexInfo.getEntrys().get(i);
								entryInfo.setId(null);
								entryInfo.setParent(null);
							}
						}
						if(planIndexInfo.getCustomEntrys()!=null&&planIndexInfo.getCustomEntrys().size()!=0){
							for(int i=0;i<planIndexInfo.getCustomEntrys().size();i++){
								CustomPlanIndexEntryInfo entryInfo = planIndexInfo.getCustomEntrys().get(i);
								entryInfo.setId(null);
								entryInfo.setParent(null);
							}
						}
					}
				} 
				editData.put("PlanIndex", planIndexInfo);
			}

		}

		return (PlanIndexInfo) editData.get("PlanIndex");
	}

	public boolean isAimMeasure() {
		Boolean isAimMeasure = (Boolean) getUIContext().get("isAimMeasure");
		if (isAimMeasure != null) {
			return isAimMeasure.booleanValue();
		} else {
			return false;
		}
	}
	
	

	private void addTotalRow(KDTable table) {
		try {
			if(isNeedShowTotalRow){
				AimCostClientHelper.setTotalCostRow(table, new String[] { COL_SUM_PRICE, "buildPart", "sellPart", "adjustAmt", "amount" }, isNeedShowTotalRow);
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	private boolean isDetailAcctRow(IRow row) {
		if (row != null && row.getCell(0).getUserObject() != null && row.getCell(0).getUserObject().equals("DetailInput")) {
			return true;
		}
		return false;
	}

	public void setDetailAcctRowNull(IRow row) {
		if (row != null && row.getCell(0).getUserObject() != null) {
			row.getCell(0).setUserObject(null);
		}
	}

	private void setDetailAcctRow(IRow row) {
		if (row != null && row.getCell(0) != null) {
			row.getCell(0).setUserObject("DetailInput");
		}
		if (row != null && row.getCell("acctName") != null) {
			row.getCell("acctName").getStyleAttributes().setLocked(true);
			row.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		}
	}

	private void setDetailAcctHashInput(IRow row) {
		if (row != null && row.getCell(1) != null) {
			row.getCell(1).setUserObject("HasInput");
		}
	}

	private boolean isDetailAcctHasInput(IRow row) {
		if (row != null && row.getCell(1).getUserObject() != null && row.getCell(1).getUserObject().equals("HasInput")) {
			return true;
		}
		return false;
	}

	public void setDetailAcctHasNotInput(IRow row) {
		if (row != null && row.getCell(1) != null) {
			row.getCell(1).setUserObject(null);
		}
	}

	public void clearDetailAcctRow(KDTable table, IRow row) {
		for (int i = 3; i < table.getColumnCount(); i++) {
			row.getCell(i).setValue(null);
		}
	}

	private void setTemplateMeasureCostF7Editor(final KDTable table) {
		KDBizPromptBox myPrmtBox = new KDBizPromptBox() {
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if (obj != null) {
					return FDCHelper.toBigDecimal(t, 4); // R090514-137（奥园）
				}
				return obj;
			}
		};

		myPrmtBox.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				IRow row = KDTableUtil.getSelectedRow(table);
				int colIdx = table.getSelectManager().getActiveColumnIndex();
				boolean isPrice = true;
				if(table.getColumnIndex(COL_PRICE)==colIdx){
					isPrice = true;
				} else {
					isPrice = false;
				}
				Object obj = row.getUserObject();
				final CostAccountInfo acct;
				if (obj instanceof MeasureEntryInfo) {
					acct = ((MeasureEntryInfo) obj).getCostAccount();
				} else {
					return;
				}

				String acctLongNumber = acct.getLongNumber();
				String projectTypeId = null;
				String productId = null;
				Object product = table.getHeadRow(0).getUserObject();
				if (product instanceof ProductTypeInfo) {
					productId = ((ProductTypeInfo) product).getId().toString();
				}
				if (acct.getType() == CostAccountTypeEnum.SIX) {
					Object v = prmtProjectType.getValue();
					if (v instanceof ProjectTypeInfo) {
						projectTypeId = ((ProjectTypeInfo) v).getId().toString();

					} else {
						MsgBox.showWarning(getplTables(), "六类公摊必须先设置项目系列");
						e.setCanceled(true);
						return;
					}
				}
				TemplateMeasureCostPromptBox selector = getTemplateMeasureCostPromptBox();
				selector.setAcctLongNumber(acctLongNumber);
				selector.setProjectTypeID(projectTypeId);
				selector.setProductId(productId);
				selector.setIsPrice(isPrice);
				if (!isPrice) {
					// 系数要先设置指标
					if (row.getCell("indexName").getValue() instanceof Item) {
						String key = ((Item) row.getCell("indexName").getValue()).key;
						if (key == null || key.equals("empty")) {
							MsgBox.showWarning(getplTables(), "请先选择指标");
							e.setCanceled(true);
							return;
						}
						selector.setIndex(key);

					} else {
						MsgBox.showWarning(getplTables(), "请先选择指标");
						e.setCanceled(true);
						return;
					}
				} else {
					selector.setIndex(null);
				}
				((KDBizPromptBox) e.getSource()).setSelector(selector);
			}
		});
		ICellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		table.getColumn(COL_PRICE).setEditor(f7Editor);
		table.getColumn("coefficient").setEditor(f7Editor);
	}

	private TemplateMeasureCostPromptBox selector = null;

	private TemplateMeasureCostPromptBox getTemplateMeasureCostPromptBox() {
		if (selector == null) {
			String orgId = (String) getUIContext().get("orgId");
			Boolean objBoolean = (Boolean) getUIContext().get("isAimMeasure");
			boolean isAimMeasure = true;
			if (objBoolean != null) {
				isAimMeasure = objBoolean.booleanValue();
			}
			Object selectedItem = this.comboMeasureStage.getSelectedItem();
			String measureStageID = null;
			if (selectedItem != null) {
				MeasureStageInfo stageInfo = (MeasureStageInfo) selectedItem;
				measureStageID = stageInfo.getId().toString();
			}
			selector = new TemplateMeasureCostPromptBox(this, isAimMeasure, orgId, measureStageID);
		}
		return selector;
	}

	/*protected MeasureCostInfo getEditData() {
		return (MeasureCostInfo) editData;
	}*/

	/**
	 * 设私有属性params(HashMap),可采取以下方式取值
	 * 
	 * <pre>
	 * if (params==null) {
	 * 			try {
	 * 				params = FDCUtils.getDefaultFDCParam(null, null);
	 * 			} catch (Exception e) {
	 * 				handUIException(e);
	 * 			}
	 * 		}
	 *         Object theValue = params.get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
	 *         if(theValue != null){
	 *         	return Boolean.valueOf(theValue.toString()).booleanValue();
	 * 		}else{
	 * 			return false;
	 * }
	 */
	private HashMap params = null;

	private HashMap getParams() {
		if (params == null) {
			HashMap hmParamIn = new HashMap();
			hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
			// 成本测算是否同步收入测算
			hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREINDEX, null);
			//报表是否显示成本合计行
	        hmParamIn.put(FDCConstants.FDC_PARAM_TOTALCOST, null);
			try {
				HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
				params = hmAllParam;
			} catch (Exception e) {
				handUIException(e);
			}
		}
		if (params == null) {
			params = new HashMap();
		}
		return params;
	}

	private boolean isMeasureIndex() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREINDEX);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * 系统参数，是否启用调整系数
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private boolean isUseAdjustCoefficient() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREADJUST);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * 启用品质特征列
	 * 
	 * @return
	 */
	private boolean isUseQuality() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * 启用自定义指标
	 * 
	 * @return
	 */
	protected boolean isUseCustomIndex() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_USECOSTOMINDEX);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * 可研成本测算及目标成本测算时，规划指标表上：人行道及广场计算在“绿化用地合计”内
	 * 
	 * @return
	 */
	protected boolean isPlanIndexLogic() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_PLANINDEXLOGIC);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * 可研成本测算、目标成本测算上项目建筑单方计算，目标成本测算导出项目建筑面积指标都使用参与分摊的产品建筑面积之和，而不是总建筑面积
	 * 
	 * @return
	 */
	protected boolean isBuildPartLogic() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	/**
	 * 成本测算与收入测算是否联用
	 * 
	 * @return
	 */
	protected boolean isIncomeJoinCost() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if (theValue != null) {
			return Boolean.valueOf(theValue.toString()).booleanValue();
		} else {
			return false;
		}
	}

	public Object[] SIXITEMS = null;

	private Object[] getSixItems() {
		SIXITEMS = null;
		if (SIXITEMS == null) {
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs(null);
			if (customPlanIndexs.size() == 0) {
				SIXITEMS = Item.SIXITEMS;
			} else {
				List list = new ArrayList();
				Set appSet = new HashSet();
				for (int i = 0; i < customPlanIndexs.size(); i++) {
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if (!appSet.contains(appId)) {
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				SIXITEMS = new Item[list.size() + Item.SIXITEMS.length];
				System.arraycopy(Item.SIXITEMS, 0, SIXITEMS, 0, Item.SIXITEMS.length);
				int i = Item.SIXITEMS.length;
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					SIXITEMS[i++] = iter.next();
				}
			}

		}
		return SIXITEMS;
	}

	public BigDecimal getCustomIndexValue(IObjectValue info, Item item) {
		if (item.isCustomIndex()) {
			CustomPlanIndexEntryInfo customEntry = null;
			if (info instanceof PlanIndexInfo) {
				customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, null);
			}
			if (info instanceof PlanIndexEntryInfo) {
				PlanIndexEntryInfo entry = (PlanIndexEntryInfo) info;
				if (entry.getProduct() == null) {
					return FDCHelper.ZERO;
				}
				String productId = entry.getProduct().getId().toString();
				customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, productId);
			}

			if (customEntry != null) {
				return customEntry.getValue();
			}
		} else {
			return info.getBigDecimal(item.key);
		}
		return null;
	}

	/**
	 * 授权科目，用于保存时做判断
	 */
	private Set accreditSet = null;

	private boolean isNeedShowTotalRow;

	/**
	 * 将原来清空所有分录数据的逻辑改成清空授权科目，这样未被授权没有显示的科目数据就不会在保存后被清除掉 by sxhong 2009-01-05 14:04:41
	 * 
	 * @param aimCost
	 * @throws BOSException
	 */
	private void handleAimCostAccredit(MeasureCostInfo measureCost) throws BOSException {
		if (!AcctAccreditHelper.hasUsed(null) || accreditSet == null || accreditSet.size() == 0) {
			// 不使用科目授权的情况下按照原来的方式进行数据清除
			measureCost.getCostEntry().clear();
			return;
		}
		
		MeasureEntryCollection costEntry = measureCost.getCostEntry();
		//MeasureEntryCollection tempCostEntry = new MeasureEntryCollection();
		MeasureEntryInfo entry = null;
		for (int i = costEntry.size() - 1; i >= 0; i--) {
			entry = costEntry.get(i);
			if (entry == null || entry.getCostAccount() == null) {
				continue;
			}
			
			// modified by zhaoqin on 2013/11/09 start
			// 如果启用了责任成本，下面的代码会导致在保存、提交时报错，现注释掉
			
			// 这部分重新从界面取值，所以先删除掉
			if (accreditSet.contains(entry.getCostAccount().getId().toString())) {
				costEntry.remove(entry);
			} else {
				entry.setId(null);
				//tempCostEntry.add(entry);
			}
		}
		/*
		if (FdcObjectCollectionUtil.isNotEmpty(tempCostEntry)) {
			costEntry.clear();
			//costEntry.addCollection(tempCostEntry);
			measureCost.put("costEntry", tempCostEntry);
		}
		*/
		// modified by zhaoqin on 2013/11/09 end
	}

	/**
	 * 根据操作及单据状态控制控件状态
	 */
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		MeasureCostInfo info = (MeasureCostInfo) editData;
		actionSubmit.setEnabled(true);
		actionSave.setEnabled(true);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			actionSubmit.setEnabled(true);
			actionSave.setEnabled(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			if (info != null && info.getState() == FDCBillStateEnum.SUBMITTED) {
				actionSubmit.setEnabled(true);
				actionSave.setEnabled(false);
				btnSave.setEnabled(false);
			} else if (info != null && info.getState() == FDCBillStateEnum.SAVED) {
				actionSubmit.setEnabled(true);
			}
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
		}

	}

	public void beforeActionPerformed(ActionEvent e) {
		super.beforeActionPerformed(e);
		String action = e.getActionCommand();
		if (action == null || action.length() == 0 || action.indexOf('$') == -1) {
			return;
		}
		// 确保每次操作每个action都调用
		handlePermissionForEachItemAction(action);
	}

	// 同一实体，相同界面，相同方法的权限处理 by hpw 2010.11.18
	private String getPermItemNameByAction(String actionName) {
		String permItemName = "ActionOnLoad";
		boolean isAimMeasure = true;
		if (getUIContext().get("isAimMeasure") != null) {
			isAimMeasure = ((Boolean) getUIContext().get("isAimMeasure")).booleanValue();
		}
		if ("ActionOnLoad".endsWith(actionName)) {
			if (isAimMeasure) {
				permItemName = "costdb_aimcost_cesuan_view";
			} else {
				permItemName = "aim_measureCost_view";
			}
		} else if ("ActionAddRow".endsWith(actionName) || "ActionDeleteRow".endsWith(actionName) || "ActionSave".endsWith(actionName)
				|| "ActionSubmit".endsWith(actionName)) {
			if (isAimMeasure) {
				permItemName = "costdb_aimcost_cesuan_edit";
			} else {
				permItemName = "aim_measureCost_edit";
			}
		} else if ("ActionImportTemplate".endsWith(actionName)) {
			if (isAimMeasure) {
				permItemName = "costdb_aimcost_useTemp";
			} else {
				permItemName = "aim_measureCost_useTemp";
			}
		} else if ("ActionPrint".endsWith(actionName) || "ActionPrintPreview".endsWith(actionName)) {
			if (isAimMeasure) {
				permItemName = "costdb_aimcost_cesuan_print";
			} else {
				permItemName = "aim_measureCost_print";
			}
		} else {// 其它权限
			if (isAimMeasure) {
				permItemName = "costdb_aimcost_cesuan_view";
			} else {
				permItemName = "aim_measureCost_view";
			}
		}
		return permItemName;
	}

	private void handlePermissionForEachItemAction(String actionName) {
		if (actionName == null || actionName.length() == 0 || actionName.indexOf('$') == -1) {
			return;
		}
		int index = actionName.indexOf('$');
		actionName = actionName.substring(index + 1, actionName.length());
		try {
			PermissionHelper.checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
					new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
					getPermItemNameByAction(actionName));
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 同一实体，同一菜单，相同方法权限问题处理 by hpw 2010.11.17
	 */

	protected void handlePermissionForItemAction(ItemAction action) {
		// super.handlePermissionForItemAction(action);
		String actionName = action.getActionName();
		handlePermissionForEachItemAction(actionName);
	}

	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {

		if (this.editData == null || this.editData.getId() == null) {
			FDCMsgBox.showConfirm2(this, "请先保存目标成本测算！");
			this.abort();
		}
		// if(this.plTables.getSelectedIndex()<3){
		// FDCMsgBox.showConfirm2(this,"请选择！");
		// this.abort();
		// }
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		if (getImportParam() != null) {
			task.invoke(getImportParam(), DatataskMode.ImpMode, true, true);
		}
		// 刷新载入最新数据
		isFirstLoad = true;
		IObjectPK pk = new ObjectUuidPK(editData.getId());
		setDataObject(getValue(pk));
		loadFields();
	}

	public void actionAll_actionPerformed(ActionEvent e) throws Exception {
		
		isAll = true;
		
		
		for (int i = 0; i < plTables.getComponentCount(); i++) {
			Component[] components = plTables.getComponents();
			if (components[i] instanceof KDTable) {
				KDTable table = (KDTable) components[i];
				if (table.getName().equals("六类公摊及期间费")) {
					fillTable(table);
					setUnionData(table);
					setLastRowEnable(table);
				}
				if (table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo) {
					PlanIndexInfo info = planIndexTable.getPlanIndexInfo();
					for (int j = 0; j < info.getEntrys().size(); j++) {
						PlanIndexEntryInfo entry = info.getEntrys().get(j);
						if (entry.getProduct() != null) {
							ProductTypeInfo product = entry.getProduct();
							if (((ProductTypeInfo) table.getHeadRow(0).getUserObject()).getId().equals(product.getId())) {
								fillTable(table);
								setUnionData(table);
								setLastRowEnable(table);
							}
						}
					}
				}
			}
		}
		if (measureCollectTable != null) {
			measureCollectTable.refresh();
		}
		
	}
	
	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		hs.put("editData", this.editData);
		editData.put("planIndex", planIndexTable.getPlanIndexInfo());

		if (editData.get("project") != null) {
			String prjId = ((CurProjectInfo) editData.get("project")).getId().toString();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("curProjProductEntries.*"));
			sic.add(new SelectorItemInfo("curProjProductEntries.curProjProEntrApporData"));
			sic.add(new SelectorItemInfo("curProjCostEntries.*"));
			CurProjectInfo paramProject = new CurProjectInfo();
			try {
				paramProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId), sic);
				hs.put("project", paramProject);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		param.setContextParam(hs);
		param.solutionName = getSolutionName();
		param.alias = getDatataskAlias();
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}

	protected String getSolutionName() {
		return "eas.fdc.costmanager.MeasureCost";
	}

	protected String getDatataskAlias() {
		return "目标成本测算";
	}

	/**
	 * 控制方式(根据目标成本控制单来控制)
	 * 
	 * @param
	 */
	public void verifyData(String costAccountId) throws Exception {
		// 数据在哪做()

		// 获取当前编辑阶段 以及 当前阶段 成本总价
		MeasureStageInfo stageInfo = (MeasureStageInfo) comboMeasureStage.getSelectedItem();

		// 获取前一阶段 以及 当前阶段 成本总价

		if (comboMeasureStage.getItemCount() - 1 != 0) {
			MeasureStageInfo stageInfoNoe = (MeasureStageInfo) comboMeasureStage.getItemAt(comboMeasureStage.getItemCount() - 1);
			StringBuffer sql = new StringBuffer("select * from T_AIM_AimCostCtrlItem where fmeasustageid ='");
			sql.append(stageInfoNoe.getId() + "'");
			FDCSQLBuilder builder = new FDCSQLBuilder(sql.toString());
			IRowSet rs = builder.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getObject("fvalue"));
			}
		}

		StringBuffer sql = new StringBuffer("select * from T_AIM_AimCostCtrlItem where fmeasustageid ='");
		sql.append(stageInfo.getId() + "'");
		FDCSQLBuilder builder = new FDCSQLBuilder(sql.toString());
		IRowSet rs = builder.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getObject("fvalue"));
		}

	}

	public void setAccoutStageMap(Map accoutStageMap) {
		this.accoutStageMap = accoutStageMap;
	}

	public Map getAccoutStageMap() {
		return accoutStageMap;
	}

	
	/**
	 * description		打印事件
	 * @author			蒲磊 <p>
	 * @createDate		2011-8-22<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.framework.client.AbstractCoreBillEditUI#actionPrint_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, "当前单据的状态不适合打印操作");
			return;
		}
		RENoteDataProvider data = new RENoteDataProvider(editData.getId().toString());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * description		打印预览
	 * @author			蒲磊 <p>
	 * @createDate		2011-8-22<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.framework.client.AbstractCoreBillEditUI#actionPrintPreview_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, "当前单据的状态不适合打印操作");
			return;

		}
		RENoteDataProvider data = new RENoteDataProvider(editData.getId().toString());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * 描述：套打模板路径	
	 * @return
	 * @Author：jian_cao
	 * @CreateTime：2012-11-13
	 */
	protected String getTDFileName() {
		return "bim/fdc/aimcost/AimMeasureCost";
	}

	/**
	 * 描述：套打query路径
	 * @return
	 * @Author：jian_cao
	 * @CreateTime：2012-11-13
	 */
	protected IMetaDataPK getTDQueryPK() {
		return MetaDataPK.create("com.kingdee.eas.fdc.aimcost.app.AimMeasureCostPrintQuery");
	}

	
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 描述：打回状态的单据可以保存 - modified by zhaoqin for R130819-0171 on 2013/09/11
	 * @Author：zhaoqin
	 * @CreateTime：2013-9-11
	 */
	private void activeSaveAction() {
		if (OprtState.EDIT.equals(getOprtState()) && ((MeasureCostInfo) editData).getState().equals(FDCBillStateEnum.SUBMITTED))
			actionSave.setEnabled(true);
	}
	
	
	 public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
	    {
	        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();

	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState())||OprtState.ADDNEW.equals(getOprtState()))
	        {
	            isEdit = true;
	        }
	        AttachmentUIContextInfo info=getAttacheInfo();
	        if (info==null)
	        	info=new AttachmentUIContextInfo();
	        if (info.getBoID() == null || info.getBoID().trim().equals("")) {
	        	String boID = null;
	        	String id = editData.getId()==null?"":editData.getId().toString();
	        	String description = editData.getDescription();
	        	if(id!=null && BoAttchAssoFactory.getRemoteInstance().exists("where  boID = '"+id+"'")){
	        		boID = id;
	        	}else{
	        		boID= description;
	        	}
	        	if(boID==null){
	        		return;
	        	}
				info.setBoID(boID);
			}
	        info.setEdit(isEdit);
	        //2007-10-26 为工作流修改附件
	        String multi=(String)getUIContext().get("MultiapproveAttachment");
	        if (multi!=null&&multi.equals("true"))
	        {
	        	acm.showAttachmentListUIByBoIDNoAlready(this,info);
	        }else
	        {
	        	acm.showAttachmentListUIByBoID(this,info);
	        }
	        //acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID 是 与附件关联的 业务对象的 ID
	    }
}