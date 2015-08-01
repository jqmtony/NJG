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
 * ע:��T_AIM_Measureentry�ϵ�simpleName�洢ָ��,number�洢���ܱ�����๫̯��̯��ʽ output class name
 * 
 * Ŀ��ɱ����� �༭����
 */
public class AimMeasureCostEditUI extends AbstractAimMeasureCostEditUI {

	/**
	 * Ŀ��ɱ�����  ҳǩ ���๫̯���ڼ�� ����Ϊ ��̯�ɱ� BT681118
	 */
	private static final String Shared_Cost = "��̯�ɱ�";

	/**
	 * ����ǰ�ϼ�
	 */
	private static final String COL_SUM_PRICE = "sumPrice"; 

	/**
	 * ������
	 */
	private static final String COL_WORKLOAD = "workload";

	/**
	 * ����
	 */
	private static final String COL_PRICE = "price";

	private List tables = null;

	private CurProjectInfo project = null;

	private Map measureCostMap = new HashMap();

	private TreeModel costAcctTree = null;

	public Map apportionMap = new HashMap();

	private Map accoutStageMap = new HashMap();
	/** �滮ָ��� */
	private PlanIndexTable planIndexTable = null;

	/** ������ܱ� */
	private MeasureCollectTable measureCollectTable = null;

	// �Ƿ�������ÿ�Ŀ
	protected boolean isAll = false;

	private boolean isFirstLoad = true;

	private MeasureStageInfo meaStaInfo = null;

	CoreUI MeasureIncomeEditUI = null;
	List lockIds = new ArrayList();
	List lockId2s = new ArrayList();
	boolean hasMutex = true;

	private MeasureIncomeInfo miInfo=null;
	//�зֿƿ�Ŀ���ӿ�Ŀ  add by lihaiou, 2013.08.07 fix bug R130719-0178
	private Map hasSplitCost = new HashMap();
	// �зǷ�̯��Ŀ���ӿ�Ŀ
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
			// �ͷ�
			if ("RELEASEALL".equals(getOprtState()) && hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, lockIds);
					if (lockId2s.size() > 0)
						FDCClientUtils.releaseDataObjectLock(MeasureIncomeEditUI, lockId2s);

				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}

			// �ͷ���Դ
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
		// �����汾�����հ汾ʱ1.�������޸�,���汣�ִ�2.����һ�汾����Ϊ���հ汾,3,�ٱ���1.�����ݺ�����������հ汾
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
		// ������Ŀ����ѡ�񣬱���������ʱ�����������������������
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
			// ϵͳĬ�������׶Σ�Ĭ�����һ�׶� by hpw 2010-9-1
			lastStageInfo = (MeasureStageInfo) comboMeasureStage.getItemAt(comboMeasureStage.getItemCount() - 1);
		}
		cost.setMeasureStage(lastStageInfo);
		return cost;
	}

	/**
	 * ����Ŀ��ɱ�����ʱ������һ�汾���ݳ�ʼ������ǰ�����汾
	 * 
	 * @return
	 */
	private MeasureCostInfo initMeasureInfo(MeasureCostInfo mcInfo) {
		mcInfo.setRecenseDate(null);// �޶������ÿ�
		mcInfo.setState(null);// ״̬�ÿ�
		mcInfo.setMainVerNo(1);// �����汾��
		mcInfo.setSubVerNo(0);// ���Ӱ汾��
		if(mcInfo!=null&&mcInfo.getId()!=null)
		mcInfo.setSrcMeasureCostId(mcInfo.getId().toString());
		MeasureStageInfo measureStageInfo = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
		if (measureStageInfo != null) {
			mcInfo.setMeasureStage(measureStageInfo);// ����׶�
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
			//�����׼
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
	 * ��ȡ����׶���ɱ���Ŀ��Ӧ��ϵֵΪ1�ĳɱ���Ŀ��Ϣ
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
	 * ����ӹ�ϵ���л�ȡ���ĳɱ���Ŀ��ֻҪ����ϸ�ڵ�
	 */
	private void processAcctCol(AccountStageCollection asCol) {
		for (int i = 0; i < asCol.size(); i++) {
			if (i == asCol.size() - 1) {// ���һ�п϶�Ϊ����ϸ�ڵ㣬��������
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
	 * ��ȡĳ������Ŀ������ӣ���������汾����Ŀ��ɱ������¼����
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
	 * ��ȡĳһ�׶ε�ǰһ�׶�
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
		// ��ӻ��ܱ��滮ָ���
		measureCollectTable = new MeasureCollectTable(this);
		table = measureCollectTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(table, "������ܱ�");
		planIndexTable = new PlanIndexTable(getInitPlanIndexInfo(), this);
		table = planIndexTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		// ((TablePreferencesHelper)tHelper).getActionSave(table);
		this.plTables.add(planIndexTable.getContentPanel(), "�滮ָ���");

		table = planIndexTable.getConstructTable();
		this.tables.add(table);
		this.plTables.add(table, "�����׼");

		PlanIndexInfo info = planIndexTable.getPlanIndexInfo();

		// ��̯����Ʒ
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
		// �ӽ���ȡ��
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
					// ��ָ����뱣֤˳��,����˳��仯�� by hpw
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
		//��ʼ��Table,����
		initTableColumn(table);
		//��ʼ���ж�Ӧ���ı��༭��
		initColumnTextField(table);
		
		
		//��ʼ���ж�Ӧ��F7�ؼ�
		initColumnF7(table);
		//�����е�����
		initColumnStyle(table, type, productId);
		setTemplateMeasureCostF7Editor(table);
		this.addTableChangeEnvent(table);
		
		addRightMenu(table);
		myTPHelper.setTablesToAppled(rbTables);
		if(rbTables.size()==1) {//���б����һ�����ã�
			myTPHelper.setDefaultUserData(myTPHelper.getUserDataFromUI(true));
		}
	}

	MyTablePreferencesHelper myTPHelper = null;	
	List rbTables = new ArrayList();	//�������Ҽ��ı���б�
	
	/**
	 * Ϊ��������Ҽ��˵�
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
	 * ����DB�еı�����õ�����
	 */
	public void loadUserConfigToTable() {
		String key = getMetaDataPK().getFullName() +".tblMain";
		UserPreferenceData userDBData = myTPHelper.getUserDataFromDB();
		if( userDBData != null && userDBData.getTableCurrentSetting(key) != null )
			myTPHelper.applyConfigFromData( userDBData.getTableCurrentSetting(key) ) ;
	}
	
	/**
	 * ��д�����赼������ACTION��ȥ������section
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
	  * Ϊʹ��MyTablePreferencesHelper����д
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
		
		
		// ��λֻ��ѡ��,�����ֹ�¼��
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
		 * ��Ŀ��˾���ڽ���Ŀ��ɱ������ʱ����Ҫ�����ۺ�ϵ��ֵ��ȷ��С�������λ����Ŀǰֻ�ܾ�ȷ��С�������λ�� R090514-137 ����by neo
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
		// start����ϵ������
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
		row.getCell("acctNumber").setValue("��Ŀ����");
		row.getCell("acctName").setValue("��Ŀ����");
		row.getCell("indexName").setValue("ԭʼָ������");
		row.getCell("index").setValue("ԭʼָ��ֵ");
		row.getCell("coefficientName").setValue("ϵ������");
		row.getCell("coefficient").setValue("ϵ��ֵ");
		row.getCell(COL_WORKLOAD).setValue("������");
		row.getCell("unit").setValue("��λ");
		row.getCell(COL_PRICE).setValue("����");
		if (isUseAdjustCoefficient()) {
			row.getCell(COL_SUM_PRICE).setValue("����ǰ�ϼ�");
		} else {
			row.getCell(COL_SUM_PRICE).setValue("�ϼ�");
		}
		row.getCell("adjustCoefficient").setValue("����ϵ��");
		row.getCell("adjustAmt").setValue("�������");
		row.getCell("amount").setValue("�ϼ�");
		row.getCell("buildPart").setValue("��������");
		row.getCell("sellPart").setValue("���۵���");
		row.getCell("program").setValue("��Լ�滮");
		row.getCell("desc").setValue("��ע");
		row.getCell("changeReason").setValue("�仯ԭ��");
		// row.getCell("description").setValue("����");

		if (isUseQuality()) {
			row.getCell("description").setValue("Ʒ������");
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
	 * ������׶γ�ʼ���ɱ���Ŀ��
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
				} else {//���ϵݹ��̯����
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
	 * ������׶γ�ʼ���ɱ���Ŀ������ϸ�ڵ�
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
	 * ��ʼ���ɱ���Ŀ�����׶ι�ϵֵΪ1�ĳɱ���ĿIDֵ
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
		// ʹ��Ԥ������䣬���Ч��
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

		// Ӧ�����ж��Ƿ�Ϊ��
		if (costAcct == null) {
			MsgBox.showError("�ɱ���Ŀ�ļ���̫��!");
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
		} else {// modify by lihaiou,2013.08.07 fix bug R130719-0178,һ���ɱ���Ŀ���û�й�̯���߷ǹ�̯�Ŀ�Ŀ��Ӧ����ʾ
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
				// ֻ��һ����ϸʱ����ͬһ����ʾ
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
					// for (int i = 0; i < coll.size(); i++) {	//  ���� - modified by zhaoqin on 2013/10/15
					for (int i = coll.size() - 1; i >= 0; i--) {
						MeasureEntryInfo info = coll.get(i);
						IRow entryRow = table.addRow();
						entryRow.setTreeLevel(node.getLevel());
						entryRow.setUserObject(info);
						loadRow(table, entryRow, product);
					}
				}
			} else {
				// �տ�Ŀ�����
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
		// ��ʾ��λ
		row.getCell("coefficient").setValue(FDCHelper.toBigDecimal(coefficient, 4));
		BigDecimal workload = info.getWorkload();
		if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
			workload = null;
		}

		if (info.getSimpleName() != null && coefficient != null) {
			row.getCell(COL_WORKLOAD).getStyleAttributes().setLocked(true);
		}
		row.getCell(COL_WORKLOAD).setValue(workload);
		// ������������unit������name����
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
			// ���๫̯�ĵ���
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
		 * ���ڿ�����⣬ʹ��addnew�������ޣ�ͨ��addnew1αװ�󣬴����˴��ں� Ȼ�����޸Ļ���
		 */
		if (this.getOprtState().equals("ADDNEW1")) {
			this.setOprtState("ADDNEW");
		}
		/** �����޸�״̬��Ŀ��ɱ�����׶β������޸Ĳ���׶�
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
		// ��ȡ������Ŀ
		String prjID = "";
		if (getUIContext().get("projectId") == null) {// �����������򿪲鿴���� 
			if (getUIContext().get(UIContext.ID) != null) {
				IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
				prjID = ((CurProjectInfo) getValue(pk).get("project")).getId().toString();
			}
		} else {
			// ����ǹ�����������Ϣ���Ĵ򿪽���ʱ���������·�ʽ����ȡ����prjID
			prjID = (String) this.getUIContext().get("projectId");
		}
		if (!StringUtil.isEmptyString(prjID)) {
			project = getProject(prjID);
			// ������������˲���׶�
			if (OprtState.ADDNEW.equals(this.getOprtState())) {
				BigDecimal fetchMeaStaMaxNumber = fetchMeaStaMaxNumber(project);
				FDCClientHelper.initComboMeasureStage(comboMeasureStage, isEdit, fetchMeaStaMaxNumber);
			} else {
				// �鿴���޸���������˲���׶�
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
			setUITitle("���гɱ�����");
		} else {
			setUITitle("Ŀ��ɱ�����");
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
		// �˴��߼�Ϊ��������Ϊ������ύ״̬ʱ����Ҫ�жϲ���׶��Ƿ��ѱ�����
		// ����Ѿ����ã����轫����׶��޸�Ϊ��һ�׶Σ�����˺����н׶ζ�������
		// ���޸�Ϊǰһ�׶� edit by emanon
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
		storeFields();// ���ܱ�����afterSetUnionData�˳���ʾ
		
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
	
	// �����������
	protected void fetchInitParam() throws Exception {
		isNeedShowTotalRow = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_TOTALCOST);
	}

	private void editVersion() throws BOSException, SQLException {
		Boolean isEditVersion = (Boolean) getUIContext().get("isEditVersion");
		if (isEditVersion != null && isEditVersion.booleanValue()) {
			// �޸İ汾��
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
				// �ҳ����汾
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
				// ʹ�ö��߳�ˢ��ҳǩ by skyiter_wang 2015/02/07
				FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
					public void run() {
						// ������_ҳǩ�л�
						plTables_stateChange();
					}
				});
				// ////////////////////////////////////////////////////////////////////////
			}

		});
	}

	/**
	 * ������������_ҳǩ�л�
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-12
	 */
	private void plTables_stateChange() {
		Object obj = plTables.getClientProperty("oldIndex");
		if (obj instanceof Integer) {
			if (((Integer) obj).intValue() == 1) {
				// //////////////////////////////////////////////////////////////////////////
				// ˢ�����в�����(table index: 2-X)
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
		// ֻȡ��˾�ĳɱ���Ŀ
		String orgId = (String) this.getUIContext().get("orgId");
		/*
		 * ������ڹ�������ֱ�Ӵ�һ��AimMeasureCostEditUI�����Ǵ���ʱ����˫���򿪵Ļ��ֶ�isAimMeasure���п���Ϊ�� �����Ļ�����onLoad�����е� if(isAimMeasure==null){
		 * isAimMeasure=Boolean.TRUE; getUIContext().put("isAimMeasure",Boolean.TRUE); }
		 * ������ʹ��isAimMeasure�ֶε�ֵ��Ϊtrue.���������ù�����������ֶ������������ǿ��в��㹤��������Ŀ��ɱ����㹤���� �����ݡ����һ���������ֲ����ˡ������ڴ���һ�£��������̵Ļ��� by Cassiel 2009-10-30
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
		// �жϰ汾���Ƿ�Ϊ�գ����Ϊ�գ�������Ϣ��ʾ
		if (!(cost.getVersionNumber() == null)) {
			this.txtVersionNumber.setText(cost.getVersionNumber().replaceAll("!", "\\."));
		} else {
			MsgBox.showWarning(this, "�汾�Ų���Ϊ��");
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

		// ����
		if (STATUS_EDIT.equals(getOprtState()) && cost != null && cost.getId() != null) {
			String billId = cost.getId().toString();
			lockIds.add(billId);
			String measureIncomeId = getMeasureIncomeId(billId);
			if (measureIncomeId != null) {// δ����������������ò��������ʱ
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
	 * ��ʼ���ɱ���Ŀ��
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
			 * Ϊ֧�ֲ����ʱ����Խ��и���ϸ��Ŀ�Ĳ��㣬��Ŀ�����ù�����Ŀ�� by sxhong 2009-08-25 15:11:21
			 */
			if (cost.getProject() == null // ѡ��֯������
					// �����ݣ���������ǰ����ʵ�������֯�ĳɱ���Ŀ������� by sxhong
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
	 * ��ȡ����׶�ID
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
		
		/* editstop�е�һ�μ�������ʱoldValue,newValue��Ϊ��,��������һ��  ������Ϊ���޸�ĳ��BUG,����ȴ��������һ��BUG��
		 * �滮ָ����е�ָ���޸ĺ����๫̯�е�ԭʼָ��ֵ�����Զ�����  by cassiel_peng
		 * if(oldValue==null&&newValue==null){ return; }
		 */
		// ��һ�μ�������ʱoldValue,newValue��Ϊ��,��������һ�䣬����
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
				// ����ǹ�����չ������򵥼��У��õ���ǰ�ϼ�Ϊ0
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
					// ���������������
					if (info instanceof PlanIndexInfo) {
						PlanIndexInfo planIndexInfo = (PlanIndexInfo) info;
						if (item.key.equals("viewArea")) {
							// ��·�õغϼ�+�̻��õغϼ�
							amount = FDCHelper.toBigDecimal(planIndexInfo.getTotalRoadArea()).add(
									FDCHelper.toBigDecimal(planIndexInfo.getTotalGreenArea()));
						}
						if (item.key.equals("doors")) {
							// ����֮��
							for (int i = 0; i < planIndexInfo.getEntrys().size(); i++) {
								PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
								amount = amount.add(FDCHelper.toBigDecimal(entry.getDoors()));
							}
						}
					}
					
					/* modified by zhaoqin for R130917-0324 on 2013/11/21 start */
					if(info instanceof PlanIndexEntryInfo) {
						PlanIndexEntryInfo pieInfo = (PlanIndexEntryInfo)info;
						// ����
						if (item.key.equals("doors"))
							amount = amount.add(FDCHelper.toBigDecimal(pieInfo.getDoors()));
						// ������
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

		// ����ϵ��
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
	 * ����ǰ�ϼ��б༭����
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
	 * ˢ�¡����๫̯���ڼ�ѡ��Լ�����Ʒҳǩ�е� ����ǰ�ϼۡ���������������ֵ
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
		
		// ���ҽ��������������͡������С�ֵ��Ϊ�ջ�0ʱ������ ������ǰ�ϼۡ� ��
		row.getCell(COL_SUM_PRICE).getStyleAttributes().setLocked(
				row.getCell(COL_PRICE).getValue() != null && row.getCell(COL_WORKLOAD).getValue() != null);

		if (price.compareTo(FDCHelper.ZERO) != 0 && workload.compareTo(FDCHelper.ZERO) != 0) { // �����������͡������С�ֵ��Ϊ�ջ�0
			BigDecimal sumPrice = price.multiply(workload).setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell(COL_SUM_PRICE).setValue(sumPrice);
		} else { // ����һ��Ϊ�ջ�0ʱ��������۸�
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
		//ԭʼָ��ֵ
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

			/* ��ΪrefreshMeasureTable()�����table_editStopped()������αedit_stop��
			 * �����αedit_stop����������δ�ı� ʱ����Ҫ����һ�ε���ǰ�ϼۣ���Ϊ�����ֵ������֮ǰ¼��Ĳ��ȡ�
			 * Added by Owen_wen 2011-11-10  R111105-0048
			*/
			if (FDCHelper.compareTo(newWorkload, row.getCell(COL_WORKLOAD).getValue()) != 0) {
				row.getCell(COL_WORKLOAD).setValue(newWorkload);
				row.getCell(COL_WORKLOAD).getStyleAttributes().setLocked(true);
				// ֻ��COL_WORKLOAD��ֵ���ı��ˣ�����Ҫ����refreshSumPrice() Added by Owen_wen 2011-09-27
				refreshSumPrice(table, row);
			}
		}
	}

	/**
	 * ����ϵ����ϼ۵ļ��� �ϼ�=����ǰ�ϼ�+��������е�����=ԭ�кϼ�*����ϵ��,���е���������ֹ���¼�룬Ҳ����ͨ������õ� by sxhong 2008-05-29 17:14:12
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

		// �㵥��
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
				MsgBox.showWarning(this, "���๫̯���������������Ŀϵ��");
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
				this.setMessageText("����ϸ�л�һ����ϸ�в����������!");
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
				// TODO Ϊʲô��������?
				// ��ϸ��¼��
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
						MsgBox.showWarning(getplTables(), "���๫̯������������Ŀϵ��");
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
						MsgBox.showWarning(getplTables(), "���๫̯������������Ŀϵ��");
						e.setCanceled(true);
					}
				}
				if (row.getCell("indexName").getValue() instanceof Item) {
					((TemplateMeasureCostPromptBox) my.getSelector()).setIndex(((Item) row.getCell("indexName").getValue()).key);
				} else {
					MsgBox.showWarning(getplTables(), "����ѡ��ָ��");
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
					// �ж��ǲ������һ��
					int j = row.getRowIndex() - 1;
					int k = row.getRowIndex() + 1;
					if (j > 0) {
						IRow parentRow = table.getRow(j);
						if (parentRow.getUserObject() instanceof CostAccountInfo) {
							if (k == table.getRowCount() || isDetailAcctRow(table.getRow(k))
							// ����ϸ��
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
	 * ���ø���Ŀ������
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
				// ���û�����
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
					 * ���б�������е������������¼����ܹ�ϵ����Ӧ���Ǹ������εĶ����ڸ��ԵĶ�Ӧ�ɱ����Զ�Ӧ������������¼��ĵ�������
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

	// ����������¼ by hpw 2010.09.27
	private boolean dealWithEmptyRow() {
		String titleStr = "���ڷ�¼������д�������ļ�¼�����µ���ǰ�ϼ�Ϊ�����Ϊ�ա� \n \n�Ƿ�ϵͳ�Զ�ɾ����Ӧ��¼��Ȼ�󱣴棿\n��ѡ���ǣ���ϵͳ�Զ�����Ϣ�������ļ�¼��ɾ����Ȼ�󱣴档\nѡ�������Ҫ���ؽ���Ӧ��¼��д��������ܽ��б��棡";
		Map detailMap = new HashMap();

		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if (isDetailAcctRow(row) && !isDetailAcctHasInput(row)) {
						// δ¼��Ĳ�������ж�
						continue;
					}
					BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
					String key = table.getName();
					if (sumPrice.compareTo(FDCHelper.ZERO) == 0) {
						if (detailMap.containsKey(key)) {
							StringBuffer detail = (StringBuffer) detailMap.get(key);
							detail.append(row.getRowIndex() + 1 + "��");
						} else {
							StringBuffer detail = new StringBuffer();
							detail.append(row.getRowIndex() + 1 + "��");
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

				msg.append("ҳǩ��");
				msg.append(key);
				msg.append("��");
				msg.append("��¼�е� ");
				msg.append(val.substring(0, val.length() - 1));
				msg.append(" ��");
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
								// δ¼��Ĳ�������ж�
								continue;
							}
							BigDecimal sumPrice = FDCHelper.toBigDecimal(row.getCell("sumPrice").getValue());
							if (sumPrice.compareTo(FDCHelper.ZERO) == 0) {
								//								setDetailAcctHasNotInput(row);// ��Ϊ������״̬
								//								for (int k = 3; k < table.getColumnCount() - 1; k++) {
								//									row.getCell(k).setValue(null);// �������
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
			MsgBox.showInfo("�汾���Ʋ���Ϊ��!");
			this.abort();
		}
		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureEntryInfo) {
					if (isDetailAcctRow(row) && !isDetailAcctHasInput(row)) {
						// δ¼��Ĳ�������ж�
						continue;
					}
					int k = table.getColumnIndex(COL_WORKLOAD);
					if (row.getCell(COL_SUM_PRICE).getValue() != null) {
						BigDecimal value = (BigDecimal) row.getCell(COL_WORKLOAD)
								.getValue();
						if (value != null
								&& value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText("�������������ֵ!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(), k);
							this.abort();
						}
					}

					k = table.getColumnIndex(COL_SUM_PRICE);
					String msg = "�ϼ�";
					if (isUseAdjustCoefficient()) {
						// k=table.getColumnIndex("amount");
						msg = "����ǰ�ϼ�";
					}
					//
					if (row.getCell(COL_SUM_PRICE).getValue() == null) {
						this.setMessageText(msg + "����Ϊ��!");
						this.showMessage();
						this.plTables.setSelectedIndex(i);
						table.getSelectManager().select(0, 0);
						table.getSelectManager().select(row.getRowIndex(), k);
						this.abort();
					} else {
						BigDecimal value = (BigDecimal) row.getCell(COL_SUM_PRICE)
								.getValue();
						if (value.compareTo(FDCHelper.ZERO) == 0) {
							this.setMessageText(msg + "����Ϊ0!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(), k);
							this.abort();
						}
						if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText(msg + "�������ֵ!");
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
    					//R110627-0479��Ŀ��ɱ��������Ŀʹ��ģ��ȱ���������. ��ģ��������Ŀ�ڵ�ǰ��Ŀ�в�����ʱ�����������Ŀ�޷�����
    					String codingNumber =IncomeAccountFactory.getRemoteInstance().getIncomeAccountInfo(new ObjectUuidPK(miInfo.getIncomeEntry().get(i).getIncomeAccount().getId())).getCodingNumber();
    					String sql = "select * from where codingNumber='"+codingNumber+"' and curProject='"+measureIncomeinfo.getProject().getId()+"'";
    					if (!IncomeAccountFactory.getRemoteInstance().exists(sql)) {
    						//���ģ��������Ŀ�ڵ�ǰ��Ŀ�в����ڣ� �����
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
	 * ϵͳԭ��������ǽ�btnSave����"�ݴ�", btnSubmit����"����"����������"�ύ"���ֿͻ�Ҫ���ܹ��ڹ��������������ʱ����ṩ"�ύ"���ܡ�
	 * �ʽ�actionSubmit_actionPerformed()�����еĴ���Ǩ�Ƶ�actionSave_actionPerformed()��.actionSubmit_actionPerformed()����Ϊ �ύʱ���߼� by Cassiel_peng
	 * 2009-08-18
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		//���ָ����̯�Ƿ���ȷ    BT701375   add by jian_cao
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
		// ����Ƿ���Ա���
		if (editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("id", editData.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if (MeasureCostFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this, "�������������ܽ��д˲���");
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
	 * �ڱ�����ύ��ʱ����Ҫ�ٴ�ȷ�ϰ汾��Ψһ�� by Cassiel_peng 2009-8-19 �汾��У�飬�ų���ǰ���ݣ����ӽ׶�,��Ŀ by hpw
	 * 
	 * @throws BOSException
	 */
	public void confirmVersionOnly() throws BOSException {

		Boolean isAimMeasure = (Boolean) getUIContext().get("isAimMeasure");
		String versionNum = this.txtVersionNumber.getText();
		MeasureCostInfo info = (MeasureCostInfo) this.editData;
		CurProjectInfo prj = (CurProjectInfo) this.prmtProject.getValue();
		// ��ʾ.���ݿ���!
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
			// �ų���ǰ
			if (info != null && info.getId() != null) {
				builder.appendSql(" and FID <> ? ");
				builder.addParam(info.getId().toString());
			}
			// �׶�
			if (stage != null && stage.getId() != null) {
				builder.appendSql(" and FMeasureStageID =? ");
				builder.addParam(stage.getId().toString());
			}
			IRowSet row = builder.executeQuery();
			if (row.size() != 0) {
				FDCMsgBox.showWarning(this, "�ð汾���Ѿ�����");
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
				FDCMsgBox.showWarning(this, "�ð汾���Ѿ�����");
				SysUtil.abort();
			}
		}

	}

	protected boolean isModifySave() {
		return isModify();
	}

	
	/**
	 * ԭ��ϵͳ����������д������"����"�����߼�������Ҫ�ṩ"�ύ"���ܹ��ѽ��÷���ԭ���߼�Ǩ����actionSave_actionPerformed()������ by Cassiel_peng 2009-08-18
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		//���ָ����̯�Ƿ���ȷ    BT701375   add by jian_cao
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
		// ����Ƿ���Ա���

		if (editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("id", editData.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if (MeasureCostFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this, "�������������ܽ��д˲���");
				SysUtil.abort();
			}
		}
		confirmVersionOnly();

		super.actionSubmit_actionPerformed(e);
		// �ύ��֮��Ҫ��"����ģ��"�ҵ� ��ֻ�����������ݵ�ʱ������ʹ��"����ģ��" by Cassiel_peng 2009-8-19
		
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
//			cost.setSrcMeasureCostId(null);��������һ�׶����հ汾ʱ��initMeasureInfo��srcid��Ҫ����Ϊ��һ�׶εģ�bean������������ݲ��ܴ�����һ�汾�����й��ܰ���
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
	        // Actionǰ������ִ�ж�����������beforeStoreFields(e)��storeFields()��verifyInput(e)
	        verify(e);

	        //2005-8-21
	        //�ڵ����ύ��ʱ�򣬷�����AddNew������ֵ�����IDΪ�գ������ύǰ�������·�����
	        doBeforeSubmitForWF(this.editData);
	        FDCSplitBillUtil.checkMeasureCostData((MeasureCostInfo)editData);
	        
	    }
	

	/**
	 * @see com.kingdee.eas.framework.client.EditUI
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI
	 * ���ڸ�UIֱ�Ӽ̳еĿ�ܵ�EditUI,û�м̳�CoreBillEditUI������FDCBillEditUI(��������������д������������)
	 * ����еı�����ύ�ɹ���ʱ����ʾ��Ϣ��׼ȷ,���Լ�����д    by Cassiel_peng  2009-8-19
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
	 * ������ˢ�����в�����(table index: 2-X)
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-12
	 */
	private void refreshAllMeasureTable() {
		// //////////////////////////////////////////////////////////////////////////
		Map map = FdcManagementUtil.recodeExeTimeBefore(getClass(), "refreshAllMeasureTable");
		// //////////////////////////////////////////////////////////////////////////
		
		// ��ˢ�µ�ǰҳǩ
		int selectedIndex = plTables.getSelectedIndex();
		if (selectedIndex >= 3 && selectedIndex < tables.size()) {
			KDTable table = (KDTable) tables.get(selectedIndex);
			refreshMeasureTable(table);
		}
		
		// ��ˢ������ҳǩ
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
	 * ������ˢ�²�����
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
	 * ���ܱ��Ʒ��̯ʹ��ָ��+�Զ���ָ��
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
	 * ���ܱ��Ʒ��̯ʹ��ָ��+�Զ���ָ��
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
		 * ���๫̯ʹ��ָ��
		 * TODO ������Ҫ�Ż�
		 */
		public static Item [] SIXITEMS=new Item[]{
			new Item("empty",	""),	
			new Item("totalContainArea",	"��ռ�����"),	
			new Item("buildArea",	"�����õ����"),	
			new Item("totalBuildArea",	"�ܽ������"),	
			new Item("buildContainArea",	"������ռ�����"),
//			new Item("buildDensity",	"�����ܶ�	"),
//			new Item("greenAreaRate",	"�̵���"),	
			new Item("cubageRateArea",	"���ݻ������"),	
//			new Item("cubageRate",	"�ݻ���"),	
			new Item("totalRoadArea",	"��·�õغϼ�"),	
			new Item("totalGreenArea",	"�̻��õغϼ�	"),
			new Item("pitchRoad",	"����·�泵�е�"),
			new Item("concreteRoad",	"��·�泵�е���ͣ������"),	
			new Item("hardRoad",	"Ӳ����װ���е�"),
			new Item("hardSquare",	"Ӳ����װ�㳡	"),
			new Item("hardManRoad",	"Ӳ����װ���е�"),
			new Item("importPubGreenArea",	"��Ҫ�����̵�	"),
			new Item("houseGreenArea",	"����լ���̻�	"),
			new Item("privateGarden",	"�ײ�˽�һ�԰	"),
			new Item("warterViewArea",	"ˮ�����"),
			new Item("viewArea",	"�������")//,
//			new Item("doors",	"����")
			
		};

		/**
		 * ��Ʒʹ��ָ��
		 */
		public static Item [] PRODUCTITEMS=new Item[]{
				new Item("empty",	""),
				new Item("containArea",	"ռ�����"),	
				new Item("buildArea",	"�������"),	
				new Item("sellArea",	"�������"),	
				new Item("cubageRate",	"�ݻ���"),	
				new Item("productRate",	"��Ʒ����"),	
				new Item("unitArea",	"ƽ��ÿ�����"),	
				new Item("units",	"��Ԫ��"),	
				new Item("doors",	"����"),
				new Item("elevators", "����"),	/* modified by zhaoqin for R130917-0324 on 2013/11/21 */
		};
		
		/**
		 * ���ܱ��Ʒ��̯ʹ��ָ��
		 */
		public static Item [] SPLITITEMS = new Item[] { 
				new Item("man", "ָ����̯"), 
				new Item("buildArea", "�������"), 
				new Item("sellArea", "�������"),
				new Item("containArea", "ռ�����"),
				new Item("cubageRate", "�ݻ���"), 
				new Item("productRate", "��Ʒ����"), 
				new Item("unitArea", "ƽ��ÿ�����"), 
				new Item("units", "��Ԫ��"),
				new Item("doors", "����	"),

		};

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
		if (comboMeasureStage.getSelectedItem() == null) {
			FDCMsgBox.showWarning(this, "����׶β���Ϊ��");
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
					// ���ѡ����ǹ�����Ŀ��ʹ�ù�����Ŀ
					String objectId = orgId;
					if (project != null) {
						objectId = ((CurProjectInfo) project).getId().toString();
					}
					MeasureCostInfo editData2 = MeasureCostFactory.getRemoteInstance().getMeasureFromTemplate(
							data.get(0).getId().toString(), objectId);
					// MeasureCostInfo editData2 = getTemplate(data.get(0).getId().toString(), objectId);
					// TODO ͨ��Ŀ��ɱ�ģ��ID��ģ�����ڹ�����Ŀ�е�ID��Ϊ����ȡ��ģ���¼
					editData2 = getTemplate(data.get(0).getId().toString(), data.get(0).getProject().getId().toString(), editData2);
					// TODO ��ģ�����ڹ�����Ŀ�еĳɱ���Ŀת�������ù��̵ĳɱ���Ŀ
					MeasureStageInfo selectedItem = (MeasureStageInfo) comboMeasureStage.getSelectedItem();
					if (selectedItem != null) {
						editData2 = acctChangeToCurrentAcct(objectId, selectedItem.getId().toString(), editData2);
					}
					editData2.setIsAimMeasure(isAimMeasure());
					editData2.setCreateTime(null);
					editData2.setCreator(null);
					// ��֯
					FullOrgUnitInfo org = new FullOrgUnitInfo();
					org.setId(BOSUuid.read(orgId));
					editData2.setOrgUnit(org);
					// ����Ŀ
					editData2.setProject((CurProjectInfo) project);// ��Ŀ
					editData2.setProjectType(null);// ��Ŀϵ��
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
					// ˢ�����в�����(table index: 2-X)
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
	 * ��ȡ��ǰ������ĿĿ��ɱ�����ģ���¼���ݣ���ת��ΪĿ��ɱ������¼����
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
	 * ��ģ���й����ĳɱ���Ŀ��ת�ɹ�����������Ŀ�гɱ���Ŀ
	 * 
	 * @param currentProjectId
	 *            ��������ĿID
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
	 * ��������Ŀ����֯��ĳ����׶�ȡ���ɱ���Ŀ
	 * 
	 * @param objectId
	 * @param measureStageId
	 * @return �ɱ���ĿlongNumber��key���ɱ���Ŀ��value��TreeMap
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
	 * �ҳ����һ������׶α���ֵ��Ŀ��ɱ���������׶α������Ϊ���һ�׶Σ�������ֵԽ�󣬽׶�Խ��
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
			// sb.append("�Ѵ��� [ ").append(lastStageInfo.getNumber()).append(lastStageInfo.getName()).append(" ] ���հ汾��Ŀ��ɱ�����,����").append(
			// msg).append(" [ ").append(stageInfo.getNumber()).append(stageInfo.getName()).append(" ] Ŀ��ɱ����㡣");
			// FDCMsgBox.showWarning(sb.toString());
			// SysUtil.abort();
			// }
			/**
			 * Ŀ��ɱ���������ʱ������Ŀ��ɱ����հ汾��Ӧ�Ĳ���׶μ��汾�ţ�����ͬһ����׶ε��µİ汾��Ŀ��ɱ����㣬���������޸Ĳ���׶�Ϊ��һ�׶Σ�ͬʱ�����Ӧ�İ汾��
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
		if (OprtState.ADDNEW.equals(this.getOprtState()) && !importFlag) {// ����ʱ���޸Ĳ���׶α���ɱ���Ŀ����ID
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
		} else if (OprtState.ADDNEW.equals(this.getOprtState()) && importFlag) {// ʹ��ģ����޸Ĳ���׶α���ɱ���Ŀ����ID
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
	 * ���׶���ʾ����ϸ�ڵ�ɱ���Ŀ������Ϊ��¼��
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

		// ������excel��
		try {
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		} catch (IOException e2) {
			// excel��ʧ�ܣ����浽ָ��λ��
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == KDFileChooser.APPROVE_OPTION) {
				// File dest = this.getFileChooser().getSelectedFile();
				File dest = fileChooser.getSelectedFile();
				try {
					// ��������ʱ�ļ���ָ��Ŀ��
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
		// ʹ�ö��߳�ˢ��ҳǩ by skyiter_wang 2015/02/07
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
			public void run() {
				// //////////////////////////////////////////////////////////////////////////
				// ˢ�����в�����(table index: 2-X)
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
		// ������Ŀ����ѡ�񣬱���������ʱ�����������������������
		this.prmtProject.setEnabled(false);
		
		// modified by zhaoqin for R130819-0171 on 2013/09/11
		activeSaveAction();
	}

	/**
	 * �޶�����
	 */
	private void handleVersion(MeasureCostInfo info) {
		if (info.getId() == null) {// �ڶ��ν���infoΪ�޶��汾������
			return;
		}
		// ����Դ�汾��������޶��İ汾�����޶���Դ�汾��֮ͬ������ΪԴ�汾
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
		// �鿴״̬���ж��Ƿ��޸�
		if (OprtState.VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}

	private FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) editData;
	}

	/**
	 * �õ���ʼ�Ĺ滮ָ���
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
				//��һ����׶�ʱ��������һ�׶�ָ��
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
					return FDCHelper.toBigDecimal(t, 4); // R090514-137����԰��
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
						MsgBox.showWarning(getplTables(), "���๫̯������������Ŀϵ��");
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
					// ϵ��Ҫ������ָ��
					if (row.getCell("indexName").getValue() instanceof Item) {
						String key = ((Item) row.getCell("indexName").getValue()).key;
						if (key == null || key.equals("empty")) {
							MsgBox.showWarning(getplTables(), "����ѡ��ָ��");
							e.setCanceled(true);
							return;
						}
						selector.setIndex(key);

					} else {
						MsgBox.showWarning(getplTables(), "����ѡ��ָ��");
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
	 * ��˽������params(HashMap),�ɲ�ȡ���·�ʽȡֵ
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
			// �ɱ������Ƿ�ͬ���������
			hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREINDEX, null);
			//�����Ƿ���ʾ�ɱ��ϼ���
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
	 * ϵͳ�������Ƿ����õ���ϵ��
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
	 * ����Ʒ��������
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
	 * �����Զ���ָ��
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
	 * ���гɱ����㼰Ŀ��ɱ�����ʱ���滮ָ����ϣ����е����㳡�����ڡ��̻��õغϼơ���
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
	 * ���гɱ����㡢Ŀ��ɱ���������Ŀ�����������㣬Ŀ��ɱ����㵼����Ŀ�������ָ�궼ʹ�ò����̯�Ĳ�Ʒ�������֮�ͣ��������ܽ������
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
	 * �ɱ���������������Ƿ�����
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
	 * ��Ȩ��Ŀ�����ڱ���ʱ���ж�
	 */
	private Set accreditSet = null;

	private boolean isNeedShowTotalRow;

	/**
	 * ��ԭ��������з�¼���ݵ��߼��ĳ������Ȩ��Ŀ������δ����Ȩû����ʾ�Ŀ�Ŀ���ݾͲ����ڱ��������� by sxhong 2009-01-05 14:04:41
	 * 
	 * @param aimCost
	 * @throws BOSException
	 */
	private void handleAimCostAccredit(MeasureCostInfo measureCost) throws BOSException {
		if (!AcctAccreditHelper.hasUsed(null) || accreditSet == null || accreditSet.size() == 0) {
			// ��ʹ�ÿ�Ŀ��Ȩ������°���ԭ���ķ�ʽ�����������
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
			// ������������γɱ�������Ĵ���ᵼ���ڱ��桢�ύʱ������ע�͵�
			
			// �ⲿ�����´ӽ���ȡֵ��������ɾ����
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
	 * ���ݲ���������״̬���ƿؼ�״̬
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
		// ȷ��ÿ�β���ÿ��action������
		handlePermissionForEachItemAction(action);
	}

	// ͬһʵ�壬��ͬ���棬��ͬ������Ȩ�޴��� by hpw 2010.11.18
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
		} else {// ����Ȩ��
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
	 * ͬһʵ�壬ͬһ�˵�����ͬ����Ȩ�����⴦�� by hpw 2010.11.17
	 */

	protected void handlePermissionForItemAction(ItemAction action) {
		// super.handlePermissionForItemAction(action);
		String actionName = action.getActionName();
		handlePermissionForEachItemAction(actionName);
	}

	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {

		if (this.editData == null || this.editData.getId() == null) {
			FDCMsgBox.showConfirm2(this, "���ȱ���Ŀ��ɱ����㣡");
			this.abort();
		}
		// if(this.plTables.getSelectedIndex()<3){
		// FDCMsgBox.showConfirm2(this,"��ѡ��");
		// this.abort();
		// }
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		if (getImportParam() != null) {
			task.invoke(getImportParam(), DatataskMode.ImpMode, true, true);
		}
		// ˢ��������������
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
				if (table.getName().equals("���๫̯���ڼ��")) {
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
		return "Ŀ��ɱ�����";
	}

	/**
	 * ���Ʒ�ʽ(����Ŀ��ɱ����Ƶ�������)
	 * 
	 * @param
	 */
	public void verifyData(String costAccountId) throws Exception {
		// ����������()

		// ��ȡ��ǰ�༭�׶� �Լ� ��ǰ�׶� �ɱ��ܼ�
		MeasureStageInfo stageInfo = (MeasureStageInfo) comboMeasureStage.getSelectedItem();

		// ��ȡǰһ�׶� �Լ� ��ǰ�׶� �ɱ��ܼ�

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
	 * description		��ӡ�¼�
	 * @author			���� <p>
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
			MsgBox.showWarning(this, "��ǰ���ݵ�״̬���ʺϴ�ӡ����");
			return;
		}
		RENoteDataProvider data = new RENoteDataProvider(editData.getId().toString());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * description		��ӡԤ��
	 * @author			���� <p>
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
			MsgBox.showWarning(this, "��ǰ���ݵ�״̬���ʺϴ�ӡ����");
			return;

		}
		RENoteDataProvider data = new RENoteDataProvider(editData.getId().toString());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * �������״�ģ��·��	
	 * @return
	 * @Author��jian_cao
	 * @CreateTime��2012-11-13
	 */
	protected String getTDFileName() {
		return "bim/fdc/aimcost/AimMeasureCost";
	}

	/**
	 * �������״�query·��
	 * @return
	 * @Author��jian_cao
	 * @CreateTime��2012-11-13
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
	 * ���������״̬�ĵ��ݿ��Ա��� - modified by zhaoqin for R130819-0171 on 2013/09/11
	 * @Author��zhaoqin
	 * @CreateTime��2013-9-11
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
	        //2007-10-26 Ϊ�������޸ĸ���
	        String multi=(String)getUIContext().get("MultiapproveAttachment");
	        if (multi!=null&&multi.equals("true"))
	        {
	        	acm.showAttachmentListUIByBoIDNoAlready(this,info);
	        }else
	        {
	        	acm.showAttachmentListUIByBoID(this,info);
	        }
	        //acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID �� �븽�������� ҵ������ ID
	    }
}