/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.AbstractObjectValue;
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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.uiframe.client.BodyUI;
import com.kingdee.eas.base.uiframe.client.NewWinMainUI;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.PositionPromptBox2;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.IAimCost;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.basedata.PcDepTypeCollection;
import com.kingdee.eas.fdc.contract.basedata.PcDepTypeFactory;
import com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo;
import com.kingdee.eas.fdc.contract.programming.CKDate;
import com.kingdee.eas.fdc.contract.programming.IPcTypeEntry;
import com.kingdee.eas.fdc.contract.programming.IProgramming;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryCollection;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryFactory;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryInfo;
import com.kingdee.eas.fdc.contract.programming.PcTypeInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFxbdEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.contract.programming.SendContWay;
import com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryCollection;
import com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewUnsignCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewUnsignInfo;
import com.kingdee.eas.fdc.invite.InviteFormEnum;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.Uuid;
import com.kingdee.util.enums.EnumUtils;

/**
 * ��Լ�滮 �б���༭��Ͻ��� �����������
 */
public class ProgrammingUI extends AbstractProgrammingUI {
	/**
	 * ���滮���
	 */
	private static final String ASSIGNING = "assigning";
	/**
	 * �ѹ滮���
	 */
	private static final String ASSIGNED = "assigned";
	/**
	 * Ŀ��ɱ����
	 */
	private static final String AIM_COST = "aimCost";

	private static final String TREE_SELECTED_OBJ = "treeSelectedObj";
	/**�滮���*/
	private static final String CONTROL_BALANCE = "controlBalance";
	/**  �ѷ������    */
	private static final String OCCUR_AMOUNT = "occurAmount";
	private static final String AMOUNT_CHANGE_FLAG = "amount_change_flag";
	private static final String MODIFY = "modify";
	private static final String HAS_HISTRY_VERSION = "hasHistryVersion";
	private static final String ID = "ID";
	private static final Logger logger = CoreUIObject.getLogger(ProgrammingUI.class);
	private CreateProTableRow create = new CreateProTableRow(dataBinder);// ��¼��������
	private DataChangeListener dataChangeListener = null;
	private EntryTreeSumField sumClass = new EntryTreeSumField();
	private final String LONGNUMBER = "longNumber";// ������
	private final String HEADNUMBER = "headNumber";// ����������
	private final String BALANCE = "balance";
	private final String AMOUNT = "amount";
	private final String CONTROLAMOUNT = "controlAmount";
	protected KDWorkButton btnAddnewLine;
	protected KDWorkButton btnInsertLines;
	protected KDWorkButton btnRemoveLines;
	protected KDWorkButton btnDetails;
	protected KDWorkButton btnSplit;
	protected KDWorkButton btnScontract;
	protected KDWorkButton updateSchduleTime;
	// ��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;

	// �Ƿ����¼��سɱ���Ŀҳǩ
	private boolean isRewLoadCostAccountTab = true;
	public static final String IS_MODIFY = "isModify";
	// modify by yxl �Ƿ����¼��ظ����ҳǩ
	private boolean isReloadFxbdTab = true;
	private KDContainer contFxbd;
	private KDTable kdfxbd;

	// modified by zhaoqin on 2013/10/12
	// ��Լ�滮��Ŀ��ɱ��ֶ��Ƿ�Ϊ��
	private boolean has_aimCost = true;
	/**
	 * FDC228_ISSTRICTCONTROL ��ͬǩԼ������֮�����ĺ�Լ�滮���ʱ�Ƿ��ϸ���� ������Ϊ���ϸ���ơ�ʱ0����ͬ�ύʱ���������Լ�滮����ͬ�����ڿ��ƽ��ʱ����ͬ�������ύ��
	 * ������Ϊ����ʾ���ơ�ʱ1����ͬ�ύʱ���������Լ�滮����ͬ�����ں�Լ���ƽ��ʱ����ͬ�����ύ�� ������Ϊ�������ơ�ʱ2�������κ��жϡ�Ĭ��ֵΪ����ʾ����
	 */
	private String paramIsstrictcontrol = "1";
	private PcDepTypeCollection pcs = null;
	
	public ProgrammingUI() throws Exception {
		super();
		txtYjDays.setEnabled(false);
//		prmtYjPerson.setEnabled(false);
		prmtYjCost.setEnabled(false);
		prmtYjDesign.setEnabled(false);
		prmtYjMaterial.setEnabled(false);
		prmtYjProject.setEnabled(false);
//		PositionF7PromptBox pf = new PositionF7PromptBox();
//		prmtYjDesign.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.PositionF7UI2");
//		prmtYjDesign.setSelector(pf);
		
	}

	public void onLoad() throws Exception {
		kdtEntries.checkParsed();
		kdtCostAccount.checkParsed();
		
		showOnlyOneTab();
		txtAllAimCost.setEnabled(false);
		txtBuildArea.setEnabled(false);
		txtSaleArea.setEnabled(false);
		super.onLoad();
		
		//---------
		setOprtState(OprtState.VIEW);
		initTable();
		setAttachmentRenderer();
		setSmallButton();
		setSmallBtnEnable();
		initData();
		setAimCostFilter();
		setMouseClick();
		txtVersion.setPrecision(1);

		/*
		 * if (this.getUIContext().get("modify") != null) { // �޶�����¸���Լ����ID
		 * ProgrammingContractInfo programmingContractInfo = new
		 * ProgrammingContractInfo(); for (int i = 0; i <
		 * kdtEntries.getRowCount(); i++) { kdtEntries.getCell(i,
		 * "id").setValue(BOSUuid.create(programmingContractInfo.getBOSType()));
		 * ProgrammingContractInfo proConInfo = (ProgrammingContractInfo)
		 * kdtEntries.getRow(i).getUserObject(); kdtEntries.getCell(i,
		 * "isCiting").setValue(Boolean.valueOf(proConInfo.isIsCiting())); } }
		 */
		buildProjectTree();
		treeSelectionListeners = treeMain.getTreeSelectionListeners();
		switchButtonEnabled();
		menuItemAddNew.setText("�޸ĸ���滮");
		// modify by yxl 20150814
		txtYjDays.setRequired(true);
		
//		prmtYjPerson.setRequired(true);
//		prmtYjPerson.setDisplayFormat("$name$");
//		prmtYjPerson.setEditFormat("$number$");
//		prmtYjPerson.setCommitFormat("$number$");
//		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
//		if(treeNode!=null && treeNode.getUserObject() instanceof OrgStructureInfo){
//			OrgStructureInfo osinfo = (OrgStructureInfo)treeNode.getUserObject();
//			FullOrgUnitInfo fullorg = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(osinfo.getUnit().getId()));
//			setPersonF7(fullorg.getId().toString(),true);
//		}
		
//		HashMap map = new HashMap();
//		map.put("ALL_ADMIN", SysContext.getSysContext().getCurrentCtrlUnit());
//		map.put("ADMIN_ORGRANGE", "BaseDataPosition");
		PositionPromptBox2 ppb = new PositionPromptBox2(this);
		ppb.setCurrentCUAdminFilter();
//		EntityViewInfo evi = new EntityViewInfo();
//        FilterInfo filter = new FilterInfo();
//        filter.getFilterItems().add(new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId()));
//        evi.setFilter(filter);
//        prmtYjDesign.setEntityViewInfo(evi);
        prmtYjDesign.setSelector(ppb);
        prmtYjCost.setSelector(ppb);
        prmtYjProject.setSelector(ppb);
        prmtYjMaterial.setSelector(ppb);
	}

	private void initFxdbTable() throws BOSException {
		// modify by yxl
		contFxbd = new KDContainer();
		kdfxbd = new KDTable();
		contFxbd.setTitle("�����");
		kDTabbedPane1.add(contFxbd,"�����");
		contFxbd.getContentPane().setLayout(new BorderLayout(0,0));
		contFxbd.getContentPane().add(kdfxbd,BorderLayout.CENTER);
		IColumn icol = kdfxbd.addColumn();
		icol.setKey("name");
		icol.setWidth(200);
		icol = kdfxbd.addColumn();
		icol.setKey("htrange");
		icol.setWidth(150);
		//�ݴ�������������ڸ����������
		icol = kdfxbd.addColumn();
		icol.setKey("maxSize");
		icol.getStyleAttributes().setHided(true);
		IRow row0 = kdfxbd.addHeadRow();
		row0.getCell("name").setValue("�滮��ͬ����");
		row0.getCell("htrange").setValue("��ͬ��Χ");
		IRow row1 = kdfxbd.addHeadRow();
		row1.getCell("name").setValue("�滮��ͬ����");
		row1.getCell("htrange").setValue("��ͬ��Χ");
		pcs = PcDepTypeFactory.getRemoteInstance().getPcDepTypeCollection(" order by number");
		PcDepTypeInfo pinfo = null;
		for (int i = 0; i < pcs.size(); i++) {
			pinfo = pcs.get(i);
			icol = kdfxbd.addColumn();
			icol.setKey(pinfo.getNumber());
			icol.setWidth(160);
			row0.getCell(pinfo.getNumber()).setValue(pinfo.getName());
			row1.getCell(pinfo.getNumber()).setValue("�ؼ���������ƻ����ʱ�� ");
		}
		kdfxbd.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
		kdfxbd.setEditable(false);
		kdfxbd.addKDTMouseListener(new KDTMouseListener(){
			 public void tableClicked(KDTMouseEvent e){
				 kdfxbd_tableClicked(e);
			 }
		});
	}

	protected void kdfxbd_tableClicked(KDTMouseEvent e) {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (e.getType() != KDTStyleConstants.BODY_ROW) {
			kdfxbd.getSelectManager().remove();
			kdfxbd.getSelectManager().setActiveRowIndex(-1);
			return;
		}
		if (colIndex == kdfxbd.getColumnIndex("name") || e.getClickCount() == 1) {
			ICell cell = kdfxbd.getCell(rowIndex, colIndex);
			if (cell != null) {
				Object value = cell.getValue();
				if (value != null) {
					if (value instanceof CellTreeNode) {
						CellTreeNode node = (CellTreeNode) value;
						//row.getCell("maxSize").setValue(mxSize);
						if(kdfxbd.getCell(rowIndex,"maxSize").getValue() != null){
							rowIndex = ((Integer)kdfxbd.getCell(rowIndex,"maxSize").getValue()).intValue()+rowIndex-1;
						}
						node.doTreeClick(kdfxbd, kdfxbd.getCell(rowIndex, colIndex));
					}
				}
			}
		}
	}

	public void setPersonF7(String cuId,boolean isSingle) {
		HashMap param = new HashMap();
		if(cuId!=null){
			param.put(PersonF7UI.CU_ID, cuId);
		}else{
			param.put(PersonF7UI.ALL_ADMIN, "YES");
		}
		PersonPromptBox pmt = new PersonPromptBox(this,param);
//        prmtYjPerson.setEnabledMultiSelection(isSingle);
//        map.put(PersonF7UI.ALL_ADMIN, "YES");
        pmt.setEnabledMultiSelection(isSingle);
//        prmtYjPerson.setSelector(pmt);
        //ͨ��F7�ǿ������������,ֻҪbizPromptBox.setEntityViewInfo(view)��ȷ JinXP  20071217
        if (cuId!=null) {
            EntityViewInfo view = new EntityViewInfo();
    		SorterItemCollection sorc = view.getSorter();
    		SorterItemInfo sort = new SorterItemInfo("number");
    		sorc.add(sort);

            FilterInfo filter = new FilterInfo();
            view.setFilter(filter);
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("CU2.id", cuId));
            fic.add(new FilterItemInfo("CU2.id", null,CompareType.NOTEQUALS ));
            fic.add(new FilterItemInfo("CU.id", cuId));
            filter.setMaskString("(#0 and #1) or #2 ");

//            prmtYjPerson.setEntityViewInfo(view);
        }else{
            //��ֹ����������ͨ��F7��ѡ�������޷���������
//            ((KDBizPromptBox)box).setQueryInfo(null);
        }
	}
	/**
	 * 
	 */
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (editData != null && !FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			kdtEntries.setEditable(true);
		} else {
			kdtEntries.setEditable(false);
		}
	}
	protected void initListener() {
		super.initListener();
		dataChangeListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				//dataChangeMethod(e); /* modified by zhaoqin for R140507-0214 on 2014/05/15 */
			}
		};
		prmtAimCost.addDataChangeListener(dataChangeListener);
	}

	/**
	 * Ŀ��ɱ������¼�
	 * 
	 * @param e
	 */
	private void dataChangeMethod(DataChangeEvent e) {
		AimCostInfo oldInfo = (AimCostInfo) e.getOldValue();
		AimCostInfo newInfo = (AimCostInfo) e.getNewValue();
		if (oldInfo != null) {
			if (newInfo == null) { // ��Ŀ��ɱ�
				if (kdtEntries.getRowCount() > 0) {
					int i = FDCMsgBox.showConfirm2("�Ƿ����Ŀ��ɱ�,����󽫻��֮ǰ���滮�Ľ����Ϊ�㣡");
					if (FDCMsgBox.OK == i) {
						clearAimCost();
						txtAimCoustVersion.setText(null);
					}
					if (FDCMsgBox.CANCEL == i) {
						prmtAimCost.removeDataChangeListener(dataChangeListener);
						prmtAimCost.setValue(oldInfo);
						prmtAimCost.addDataChangeListener(dataChangeListener);
					}
				}
			} else {
				if (kdtEntries.getRowCount() > 0) {
					if (oldInfo.getId().toString().equals(newInfo.getId().toString())) {
						return;
					} else {// ��Ŀ��ɱ�
						kdtEntries.getEditManager().editingStopped();
						int i = FDCMsgBox.showConfirm2("�Ƿ����Ŀ��ɱ������ĺ󽫻����֮ǰ���滮�Ľ�");
						if (FDCMsgBox.OK == i) {
							changeAimCost(newInfo);
						}
						if (FDCMsgBox.CANCEL == i) {
							prmtAimCost.removeDataChangeListener(dataChangeListener);
							prmtAimCost.setValue(oldInfo);
							prmtAimCost.addDataChangeListener(dataChangeListener);
						}
					}
				}
			}
		} else {
			if (kdtEntries.getRowCount() > 0) {
				int i = FDCMsgBox.showConfirm2("�Ƿ����Ŀ��ɱ������ĺ󽫻����֮ǰ���滮�Ľ�");
				if (FDCMsgBox.OK == i) {
					changeAimCost(newInfo);
				}
				if (FDCMsgBox.CANCEL == i) {
					prmtAimCost.removeDataChangeListener(dataChangeListener);
					prmtAimCost.setValue(oldInfo);
					prmtAimCost.addDataChangeListener(dataChangeListener);
				}
			}
		}

		if (e.getNewValue() != null) {
			txtAimCoustVersion.setText(((AimCostInfo) e.getNewValue()).getVersionNumber());
			String id = ((AimCostInfo) e.getNewValue()).getId().toString();
			getCostAmount(id);
		} else {
			txtAimCoustVersion.setText(null);
			txtAllAimCost.setValue(null);
		}
	}

	/**
	 * ��ȡĿ��ɱ����ܽ��
	 * 
	 * @param id
	 */
	private void getCostAmount(String id) {
		StringBuffer sql = new StringBuffer("select sum(fcostamount) as amount from T_AIM_CostEntry where fheadid ='" + id + "'");
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sql.toString());
		IRowSet rs;
		try {
			rs = sqlBuilder.executeQuery();

			if (rs.next()) {
				txtAllAimCost.setValue(rs.getBigDecimal("amount"));
			}
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		} catch (SQLException e2) {
			handUIExceptionAndAbort(e2);
		}
	}

	/**
	 * ��Ҫ���ĵ�ֵ���£�
	 * 
	 * ����������Ŀ��ɱ��汾�����
	 * 
	 * �ɱ����� :Ŀ��ɱ����ѷ��䣬�����䣬����Լ���� ��0
	 * 
	 * �滮��Լ�����0,�ɹ����ƽ����0,�滮�����0�����������0
	 * 
	 * ���������������0
	 */
	private void clearAimCost() {
		txtAimCoustVersion.setText(null);
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			ProgrammingContractEconomyCollection economyEntries = programmingContractInfo.getEconomyEntries();
			// �ɱ�����
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				// Ŀ��ɱ����ѷ��䣬�����䣬����Լ���� ��0
				pccInfo.setGoalCost(FDCHelper.ZERO);
				pccInfo.setAssigned(FDCHelper.ZERO);
				pccInfo.setAssigning(FDCHelper.ZERO);
				pccInfo.setContractAssign(FDCHelper.ZERO);
			}
			// �滮��Լ�����0,�ɹ����ƽ����0,�滮�����0�����������0
			programmingContractInfo.setAmount(FDCHelper.ZERO);
			programmingContractInfo.setControlAmount(FDCHelper.ZERO);
			programmingContractInfo.setBalance(FDCHelper.ZERO);
			programmingContractInfo.setControlBalance(FDCHelper.ZERO);
			// ��������
			for (int k = 0; k < economyEntries.size(); k++) {
				ProgrammingContractEconomyInfo pceInfo = economyEntries.get(k);
				// ��������0
				pceInfo.setAmount(FDCHelper.ZERO);
			}
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(i), programmingContractInfo);
			kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
		}
	}

	/**
	 * �ı�Ŀ��ɱ����Ǽ�Ŀ��ɱ�
	 * 
	 * ������
	 * 
	 * 1.���¸��ɱ�������"Ŀ��ɱ�"��"������"��ֵ
	 * 
	 * ��"Ŀ��ɱ�"�� ����ͨ���¹�����"Ŀ��ɱ�"�͸��ɱ�������"������Ŀ"��"�ɱ���Ŀ"��Ϊ�������˳��µ�
	 * 
	 * ��"������":��"Ŀ��ɱ�"-��"�ѷ���"
	 * 
	 * 2.���º󣬰ѳɱ����д��и�"������"ֵ����Ӧ�ĺ�Լ�ڿ�ܽ����¼���ú�ɫ��־�����������ú�ɫ���壩
	 * 
	 * 3.��¼���к�ɫ��־����ʱ���棬�ύ��ť��ʾ�����ɱ��桭��������,�����ɱ��治���ύ
	 * 
	 * ��Ҫ���ĵ�ֵ���£�
	 * 
	 * ����������Ŀ��ɱ��汾��
	 * 
	 * �ɱ����� :Ŀ��ɱ�����������
	 * 
	 * �����������ݣ�2011.04.13����
	 * 
	 * �� ԭ"����Լ����" = ԭ"Ŀ��ɱ�" ��̬���� "����Լ����" = ��"Ŀ��ɱ�"��
	 * 
	 * @modified By Owen_wen
	 *           ����ProgrammingImportUI�еķ�����ʹ�����ȵ���Ŀ��ɱ��ٵ���ģ���Ч��һ���������ǱߵĴ���
	 * 
	 */
	private void changeAimCost(AimCostInfo aimCost) {
		kdtEntries.getEditManager().editingStopped();

		try {
			ProgrammingImportUI importUI = new ProgrammingImportUI(this.editData, aimCost);
			importUI.computeAim();
			editData.setAimCost(aimCost);
			clearAndDisplay(editData.getEntries());
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}
	}

	/**
	 * ���ں�ͬ���ù�ϵ����Ŀ��ɱ�f7�ؼ��û�
	 * 
	 * @return
	 */
	private boolean isCiting() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object value = kdtEntries.getCell(i, "isCiting").getValue();
			if (value instanceof Boolean) {
				Boolean b = (Boolean) value;
				if (b.booleanValue()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ���㽨�����������۵���
	 */
	private void calcSquare(int index) {
		BigDecimal totalBuildArea = txtBuildArea.getBigDecimalValue();
		BigDecimal sellArea = txtSaleArea.getBigDecimalValue();

		if (index == 0) {
			int size = this.kdtEntries.getRowCount();
			for (int i = 0; i < size; i++) {
				BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(i, "amount").getValue());
				if (amount.compareTo(FDCHelper.ZERO) != 0) {
					if (totalBuildArea != null && totalBuildArea.compareTo(FDCHelper.ZERO) != 0) {
						kdtEntries.getCell(i, "buildPerSquare").setValue(amount.divide(totalBuildArea, 4, BigDecimal.ROUND_HALF_UP));
					}
					if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
						kdtEntries.getCell(i, "soldPerSquare").setValue(amount.divide(sellArea, 4, BigDecimal.ROUND_HALF_UP));
					}
				} else {
					kdtEntries.getCell(i, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(i, "soldPerSquare").setValue(FDCHelper.ZERO);
				}
			}
		} else {
			BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(index, "amount").getValue());
			if (amount.compareTo(FDCHelper.ZERO) != 0) {
				if (totalBuildArea != null && totalBuildArea.compareTo(FDCHelper.ZERO) != 0) {
					kdtEntries.getCell(index, "buildPerSquare").setValue(amount.divide(totalBuildArea, 4, BigDecimal.ROUND_HALF_UP));
				}
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					kdtEntries.getCell(index, "soldPerSquare").setValue(amount.divide(sellArea, 4, BigDecimal.ROUND_HALF_UP));
				}
			} else {
				kdtEntries.getCell(index, "buildPerSquare").setValue(FDCHelper.ZERO);
				kdtEntries.getCell(index, "soldPerSquare").setValue(FDCHelper.ZERO);
			}
		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			changeActoinState(false);
			actionImport.setEnabled(true);
			actioinImportProject.setEnabled(true);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			changeActoinState(false);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		}
	}

	/**
	 * ���ø�������ʾ��ʽ
	 */
	private void setAttachmentRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					return "�鿴";
				}
				return null;
			}
		});
		this.kdtEntries.getColumn("attachment").setRenderer(objectValueRender);
	}

	private void initData() throws Exception {
		this.btnExport.setEnabled(true);
		Object node = getUIContext().get(TREE_SELECTED_OBJ);
		if (node != null)
			curProject = (CurProjectInfo) node;

		if (editData.getProject() == null) {
			if (curProject != null) {
				editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
			}
		}
		if (editData.getProject() != null) {
			StringBuffer sql = new StringBuffer("select FID from T_AIM_MeasureCost where fprojectid ");
			sql.append(" = '" + editData.getProject().getId().toString() + "' and fstate = '4AUDITTED' ");
			sql.append(" order BY FMAINVERNO desc , FSUBVERNO desc");
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sql.toString());
			IRowSet rs = sqlBuilder.executeQuery();
			if (rs.next()) {
				String measureCostID = rs.getString("FID");
				if (measureCostID != null && measureCostID.length() > 1) {
					StringBuffer sql_2 = new StringBuffer(
							"select a.FTotalBuildArea ,  isnull(b.FSellArea ,0) as FSellArea from T_AIM_PlanIndex as a ");
					sql_2.append(" left outer join T_AIM_PlanIndexEntry as b on a.FID = b.FParentID ");
					sql_2.append(" where a.FHEADID = '" + measureCostID + "'");
					sqlBuilder = new FDCSQLBuilder(sql_2.toString());
					IRowSet rs_2 = sqlBuilder.executeQuery();
					BigDecimal totalBuildArea = FDCHelper.ZERO;
					BigDecimal sellArea = FDCHelper.ZERO;
					while (rs_2.next()) {
						totalBuildArea = rs_2.getBigDecimal("FTotalBuildArea");
						sellArea = sellArea.add(rs_2.getBigDecimal("FSellArea"));
					}
					txtBuildArea.setValue(totalBuildArea);
					txtSaleArea.setValue(sellArea);
				}
			}
		}
	}

	// �޶�ʱ�ÿ��ֶ�ֵ
	protected void setFieldsNull(AbstractObjectValue newData) {
		ProgrammingInfo info = (ProgrammingInfo) newData;
		String number = getDateString();
		info.setNumber(number);
		txtNumber.setText(number);
		inputVersion(info);
		info.setState(FDCBillStateEnum.SAVED);
	}

	/**
	 * ���÷�¼ʧȥ���㼰��¼��ť�Ƿ����
	 */
	private void setKDTableLostFocus() {
		kdtEntries.getEditManager().stopEditing();
		kdtEntries.getSelectManager().remove();
		kdtEntries.getSelectManager().setActiveRowIndex(-1);
		btnInsertLines.setEnabled(false);
		btnRemoveLines.setEnabled(false);
		btnDetails.setEnabled(false);
		btnSplit.setEnabled(false);
	}

	/**
	 * Ŀ��ɱ���������
	 */
	private void setAimCostFilter() {
		if (editData.getProject() != null) {
			EntityViewInfo entityView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			// �������Ŀ���ˣ����ڸ�Ϊ�Զ����������޸�
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", editData.getProject().getId().toString()));
			entityView.setFilter(filter);
			prmtAimCost.setEntityViewInfo(entityView);
			if (prmtAimCost.getValue() != null) {
				txtAimCoustVersion.setText(((AimCostInfo) prmtAimCost.getValue()).getVersionNumber());
			}
		}
	}

	/**
	 * ����ʱ�Ա���������
	 */
	private void initTable() {
		kdtEntries.getColumn("id").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("level").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("longName").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isCiting").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isHasPlan").getStyleAttributes().setLocked(true);
		// �滮������������ָ�ʽ����λС��
		cellToFormattedText(kdtEntries, BALANCE);
		cellToFormattedText(kdtEntries, CONTROL_BALANCE);
		cellToFormattedText(kdtEntries, OCCUR_AMOUNT);
		cellToFormattedText(kdtEntries, AMOUNT);
		cellToFormattedText(kdtEntries, CONTROLAMOUNT);
		cellToFormattedText(kdtEntries, "reservedChangeRate");
		
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor.getComponent();
		kdftf.setDataType(1);
		kdftf.setPrecision(4);
		kdftf.setSupportedEmpty(true);
		kdftf.setMaximumValue(100);
		kdftf.setMinimumValue(0);
		kdftf.setNegatived(false);
		kdtEntries.getColumn("reservedChangeRate").setEditor(cellEditor);
		
		cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(1024);
		kdtEntries.getColumn("remark").setEditor(cellEditor);
		// kdtEntries.getColumn("headNumber").getStyleAttributes().setHided(false);
		kdtEntries.getColumn(AMOUNT).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn(CONTROLAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn(BALANCE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn(CONTROL_BALANCE).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn(OCCUR_AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		/*** �滮�������޸�, add  by lihaiou,2013.09.22, for R130828-0384*******/
		//kdtEntries.getColumn(AMOUNT).getStyleAttributes().setLocked(true);	// modified by zhaoqin on 2013/10/17
		/**** modify end ****************/
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("reservedChangeRate").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		kdtEntries.getColumn("buildPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("soldPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		kdtEntries.getColumn("reservedChangeRate").getStyleAttributes().setNumberFormat("0.00%");

		KDTDefaultCellEditor inviteEditor = new KDTDefaultCellEditor(new KDComboBox());
		KDComboBox kdCombox = (KDComboBox) inviteEditor.getComponent();
		kdCombox.addItem(InviteFormEnum.PUBLICINVITE);
		kdCombox.addItem(InviteFormEnum.INVITATORYINVITE);
		kdCombox.addItem(InviteFormEnum.OTHER);
		kdCombox.addItem(InviteFormEnum.TENDERDISCUSSION);
		kdCombox.setSelectedIndex(-1);
		kdtEntries.getColumn("inviteWay").setEditor(inviteEditor);
		// /////////////////////////////////////////////////////////////////////
		// start�����Ž�������Լ�滮����ϸ���ݼ��б�������4���ֶΡ�������ʽ��������Ͷ�귽ʽ������Ԥ�Ʒ�����ʼʱ�䡱����Ԥ�Ʒ�������ʱ�䡱���ĸ��ֶ�
		// by skyiter_wang 20131009
		// /////////////////////////////////////////////////////////////////////
		kdtEntries.getColumn("estimateAwardStartDate").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("estimateAwardEndDate").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("inviteMode").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("jobType").getStyleAttributes().setLocked(true);
		createCostCentertF7();
		kdtEntries.getColumn(CONTROL_BALANCE).getStyleAttributes().setHided(false);
		/**
			�����ĸ��ֶΣ�Ϊ��Լ��������F7 ����ö��  ���ʱ��
			�ѷ������occurAmount �滮���balance �������controlBalance ����ƻ�isHasPlan
			��Լ����hyType �Ƽ۷�ʽpriceWay ������ʽsendPage ʩ��ͼsgDate
			��ͬǩ��csDate ����startDate ����endDate ��ͬ���csendDate
			by yxl 20150811
		*/
		kdtEntries.getColumn("iscse").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("iscse").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("iscse").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof Boolean){
					if((Boolean)obj){
						return "��ǩ��";
					}else{
						return "δǩ��";
					}
				}
				return super.getText(obj);
			}
		});
		kdtEntries.getColumn("occurAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("balance").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("controlBalance").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("isHasPlan").getStyleAttributes().setHided(true);
		final KDBizPromptBox hyType = new KDBizPromptBox();
		hyType.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.PcTypeQuery");
		hyType.setDisplayFormat("$hyType$");
		hyType.setEditFormat("$hyType$");
		hyType.setCommitFormat("$hyType$");
        KDTDefaultCellEditor hyTypeCellEditor = new KDTDefaultCellEditor(hyType);
        kdtEntries.getColumn("hyType").setEditor(hyTypeCellEditor);
        ObjectValueRender hyType_OVR = new ObjectValueRender();
        hyType_OVR.setFormat(new BizDataFormat("$hyType$"));
        kdtEntries.getColumn("hyType").setRenderer(hyType_OVR);
		KDComboBox priceSend = new KDComboBox();
		KDTDefaultCellEditor priceSendEditor = new KDTDefaultCellEditor(priceSend);
		priceSend.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.programming.PriceWay").toArray());
		kdtEntries.getColumn("priceWay").setEditor(priceSendEditor);
		priceSend = new KDComboBox();
		priceSendEditor = new KDTDefaultCellEditor(priceSend);
		priceSend.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.programming.SendContWay").toArray());
		kdtEntries.getColumn("sendPage").setEditor(priceSendEditor);
		KDDatePicker datePicker = new KDDatePicker();
        datePicker.setEditable(true);
        KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
        kdtEntries.getColumn("sgDate").setEditor(dateEditor);
        kdtEntries.getColumn("csDate").setEditor(dateEditor);
        kdtEntries.getColumn("startDate").setEditor(dateEditor);
        kdtEntries.getColumn("endDate").setEditor(dateEditor);
        kdtEntries.getColumn("csendDate").setEditor(dateEditor);
        kdtEntries.getColumn("sgDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
        kdtEntries.getColumn("csDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
        kdtEntries.getColumn("startDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
        kdtEntries.getColumn("endDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
        kdtEntries.getColumn("csendDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
        // delete after debug
//        kdtEntries.getColumn("level").getStyleAttributes().setHided(false);
//        kdtEntries.getColumn("number").getStyleAttributes().setHided(false);
//        kdtEntries.getColumn("longNumber").getStyleAttributes().setHided(false);
//        kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setHided(false);
//        ObjectValueRender date_OVR = new ObjectValueRender();
//        kdtEntries.getColumn("sgDate").setRenderer(date_OVR);
//        datePicker = new KDDatePicker();
//        datePicker.setEditable(true);
//        dateEditor = new KDTDefaultCellEditor(datePicker);
//        kdtEntries.getColumn("csDate").setEditor(dateEditor);
//        datePicker = new KDDatePicker();
//        datePicker.setEditable(true);
//        dateEditor = new KDTDefaultCellEditor(datePicker);
//        kdtEntries.getColumn("startDate").setEditor(dateEditor);
//        datePicker = new KDDatePicker();
//        datePicker.setEditable(true);
//        dateEditor = new KDTDefaultCellEditor(datePicker);
//        kdtEntries.getColumn("endDate").setEditor(dateEditor);
//        datePicker = new KDDatePicker();
//        datePicker.setEditable(true);
//        dateEditor = new KDTDefaultCellEditor(datePicker);
//        kdtEntries.getColumn("csendDate").setEditor(dateEditor);
	}

	private void cellToFormattedText(KDTable table, String column) {
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor.getComponent();
		kdftf.setDataType(1);
		kdftf.setPrecision(2);
		kdftf.setSupportedEmpty(true);
		kdftf.setNegatived(false);
		table.getColumn(column).setEditor(cellEditor);
	}

	public void loadFields() {
		super.loadFields();
		
		isRewLoadCostAccountTab = true;
		isReloadFxbdTab = true;
		// ��������ʱ������������
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		// ��Ԫ�����ģʽ
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		kdtEntries.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		if (!OprtState.ADDNEW.equals(getOprtState())) {
			createTree();
			setNameDisplay();
			handleOldData();
			AimCostInfo aimCost = editData.getAimCost();
			if (aimCost != null && aimCost.getId() != null && aimCost.getId().toString().length() > 1)
				getCostAmount(editData.getAimCost().getId().toString());
		}
		if (!OprtState.VIEW.equals(getOprtState())) {
			setCellEditorForTable();
		}
		setTableFontColor();
		cellAttachment();
		setTableRenderer();
		sumClass.caclTotalAmount(kdtEntries);
		setTableOccurAmount();
		setMyFontColor();
		try {
			initData();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		txtDisplayVersion.setText(getDisplayName());
		switchButtonEnabled();
	}
	/**
	 * �����ѷ������
	 */
	private void setTableOccurAmount() {
		int rowCount = this.kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			setRowOccurAmount(i);
		}
	}

	private void setRowOccurAmount(int rowIndex) {
		BigDecimal amount = FDCNumberHelper.toBigDecimal(kdtEntries.getCell(rowIndex, AMOUNT).getValue());
		BigDecimal balance = FDCNumberHelper.toBigDecimal(kdtEntries.getCell(rowIndex, BALANCE).getValue());
		kdtEntries.getCell(rowIndex, OCCUR_AMOUNT).setValue(amount.subtract(balance));
	}

	private SimpleDateFormat fmt_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat fmt_yyyy = new SimpleDateFormat("yyyy");
	private DecimalFormat numberFormat = new DecimalFormat("##.0");

	/**
	 * ���ý���ֶ���ʾ��ʽ
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
		kdtEntries.getColumn("controlAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("amount").setRenderer(objectValueRender);
		kdtEntries.getColumn(BALANCE).setRenderer(objectValueRender);
		kdtEntries.getColumn(CONTROL_BALANCE).setRenderer(objectValueRender);
		kdtEntries.getColumn(OCCUR_AMOUNT).setRenderer(objectValueRender);
		kdtEntries.getColumn("signUpAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("changeAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("settleAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("buildPerSquare").setRenderer(objectValueRender);
		kdtEntries.getColumn("soldPerSquare").setRenderer(objectValueRender);
	}

	private void cellAttachment() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object idObj = kdtEntries.getCell(i, "id").getValue();
			if (idObj != null) {
				String id = idObj.toString();
				StringBuffer allAttachmentName = getAllAttachmentName(id);
				if (!FDCHelper.isEmpty(allAttachmentName)) {
					kdtEntries.getCell(i, "attachment").setValue("���ڸ���");
				}
			}
		}
	}

	/**
	 * ��ȡ��Լ������и��������ַ������������ֳ���","���
	 * 
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" SELECT DISTINCT boAt.FBoID, at.FID, at.FSimpleName, at.FName_l2, at.FCreateTime FROM T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		logger.info("sql:" + fdcBuilder.getSql().toString());
		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (FDCHelper.isEmpty(rs.getString("FSimpleName"))) {
					sb.append(rs.getString("FName_l2") + ";");
				} else {
					if (rs.isLast()) {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
					} else {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
		return sb;
	}

	public void onShow() throws Exception {
		setProTableToSumField();
		// add by lihaiou 2013.7.25 fix bug R130718-0286
		setAccountsTableToSumField();
		// modify end
		sumClass.appendProFootRow(null, null);

		super.onShow();
		// ���ñ�ͷ����
		kdtEntries.addKDTMouseListener(new KDTSortManager(kdtEntries));
		kdtEntries.getSortMange().setSortAuto(false);

		setAuditBtnEnable();
		FDCTableHelper.generateFootRow(kdtEntries).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 start */
		if(this.editData.getState() == FDCBillStateEnum.SAVED || this.editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionRemove.setEnabled(true);
		} else {
			actionRemove.setEnabled(false);
		}
		setAimCostVersionMsg();
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 start */
		initFxdbTable();
	}

	/**
	 * ����������ť��ʾ
	 */
	private void setAuditBtnEnable() {
		if (editData.getState() == null) {
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(false);
			actionSave.setEnabled(false);
		} else if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
		}
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
		if (this.oprtState.equals(STATUS_EDIT)) {
			// boolean lastVersion = ((IProgramming)
			// getBizInterface()).isLastVersion(
			// new ObjectUuidPK(editData.getId()));
			boolean isAudit = FDCBillStateEnum.AUDITTED.equals(editData.getState()) || FDCBillStateEnum.AUDITTING.equals(editData.getState());
			if (!isBillModify()) {
				actionAudit.setEnabled(!isAudit);
				actionUnAudit.setEnabled(isAudit);
				actionImport.setEnabled(!isAudit);
				actioinImportProject.setEnabled(!isAudit);
			}
		}
	}

	private void changeActoinState(boolean flag) {
		actionAudit.setEnabled(flag);
		actionUnAudit.setEnabled(flag);
		actionSubmit.setEnabled(flag);
		actionImport.setEnabled(flag);
		actioinImportProject.setEnabled(flag);
	}

	/**
	 * ����ǩ��������������롢ɾ������ϸ��Ϣ��ť
	 */
	private void setSmallButton() {
		btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		btnImportProject.setIcon(EASResource.getIcon("imgTbtn_input"));
		menuItemImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		btnExport.setIcon(EASResource.getIcon("imgTbtn_output"));
		menuItemExport.setEnabled(true);
		menuItemExport.setIcon(EASResource.getIcon("imgTbtn_output"));
		btnRefresh.setEnabled(true);
		menuItemRefresh.setEnabled(true);
		btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		menuItemRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		btnAddnewLine = new KDWorkButton();
		btnInsertLines = new KDWorkButton();
		btnRemoveLines = new KDWorkButton();
		btnDetails = new KDWorkButton();
		btnSplit = new KDWorkButton();
		btnScontract = new KDWorkButton();
		updateSchduleTime = new KDWorkButton();

		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDetails_actionPerformed(e);
				} catch (Exception e1) {
					logger.error("detials", e1);
					handUIExceptionAndAbort(e1);
				}
			}
		});

		btnAddnewLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});

		btnInsertLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLines_actionPerformed(arg0);
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}

			}

		});

		btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLines_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
		
		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionSplit_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
		btnScontract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionScontract_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
		
		updateSchduleTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateSchduleTime_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
//		btnScontract.setVisible(false);
		btnScontract.setEnabled(false);
		updateSchduleTime.setEnabled(false);
		setButtonStyle(btnScontract, "��ͬδǩ��", "imgTbtn_readin");
		setButtonStyle(updateSchduleTime, "���½���ʱ��", "imgTbtn_adduction");
		setButtonStyle(btnAddnewLine, "������", "imgTbtn_addline");
		setButtonStyle(btnRemoveLines, "ɾ����", "imgTbtn_deleteline");
		setButtonStyle(btnInsertLines, "������", "imgTbtn_insert");
		setButtonStyle(btnDetails, "��ϸ��Ϣ", "imgTbtn_particular");
		setButtonStyle(btnSplit, "�ֽ�", "imgTbtn_particular");
	}

	private void setButtionEnable(boolean isEnable) {
		btnAddnewLine.setEnabled(isEnable);
		btnInsertLines.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
		btnSplit.setEnabled(isEnable);
	}

	// ���ð�ť��ʾЧ��
	private void setButtonStyle(KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		conProgramming.addButton(button);
	}

	/**
	 * ����������ɾ���������¼��ť�Ƿ����
	 */
	private void setSmallBtnEnable() {
		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
			if (kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0) {
				btnDetails.setEnabled(false);
			} else {
				btnDetails.setEnabled(true);
			}
		} else {
			btnAddnewLine.setEnabled(true);
			if (kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0) {
				btnInsertLines.setEnabled(false);
				btnRemoveLines.setEnabled(false);
				btnDetails.setEnabled(false);
				btnSplit.setEnabled(false);
			} else {
				btnInsertLines.setEnabled(true);
				btnRemoveLines.setEnabled(true);
				btnDetails.setEnabled(true);
				btnSplit.setEnabled(true);
			}
		}
	}
	
	public void updateSchduleTime_actionPerformed(ActionEvent e) throws Exception {
		Set pcids = new HashSet();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			pcids.add(kdtEntries.getCell(i,"id").getValue());
		}
		if(pcids.size() > 0){
			DahuaScheduleEntryCollection dscoll = null;//" where progamming.id='"+id+"'"
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("progamming.id",pcids,CompareType.INCLUDE));
			evi.setFilter(filter);
			dscoll=DahuaScheduleEntryFactory.getRemoteInstance().getDahuaScheduleEntryCollection(evi);
			if(dscoll.size() == 0){
				FDCMsgBox.showInfo("��Լ�滮����Դ������δ������");
				return;
			}
		}
	}
	
	//���ÿ����ϸ��Լ������ ��ͬ�Ƿ�ǩ�� ��ť��Ĭ����ʾ����ͬδǩ�ꡱ by yxl 20150820
	public void actionScontract_actionPerformed(ActionEvent e) throws Exception {
		FDCTableHelper.checkSelectedAndAbort(this, kdtEntries);
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		//����˽ڵ��к��ӽڵ㣬���ӽڵ���ű仯
		FDCSQLBuilder builder = new FDCSQLBuilder();
//		String id = ((BOSUuid)kdtEntries.getCell(rowIndex,"id").getValue()).toString();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		Set ids = new HashSet();
		if((Boolean)kdtEntries.getCell(rowIndex,"iscse").getValue()){
			btnScontract.setText("��ͬδǩ��");
			//�жϴ˽ڵ����޸��ڵ㣬�и��ڵ㣬����ű仯
			kdtEntries.getCell(rowIndex,"iscse").setValue(Boolean.FALSE);
			ids.add(kdtEntries.getCell(rowIndex,"id").getValue());
			if(level > 1){
				String longNumber = (String)kdtEntries.getCell(rowIndex,LONGNUMBER).getValue();
				while(longNumber.lastIndexOf(".") != -1){
					longNumber = longNumber.substring(0,longNumber.lastIndexOf("."));
					for (int i = 0; i < kdtEntries.getRowCount(); i++) {
						if(longNumber.equals(kdtEntries.getCell(i,LONGNUMBER).getValue()) && (Boolean)kdtEntries.getCell(i,"iscse").getValue()){
							ids.add(kdtEntries.getCell(i,"id").getValue());
							kdtEntries.getCell(i,"iscse").setValue(Boolean.FALSE);
							break;
						}
					}
				}
			}
			builder.appendSql("update T_CON_ProgrammingContract set cfiscse=0 where fid in("+setToString(ids)+")");
			builder.executeUpdate();
		}else{
			btnScontract.setText("��ͬ��ǩ��");
//			kdtEntries.getCell(rowIndex,"iscse").setValue(Boolean.TRUE);
			//�жϴ˽ڵ����޸��ڵ㣬�и��ڵ㣬���жϺ��ӽڵ��Ƿ���ǩ��
			kdtEntries.getCell(rowIndex,"iscse").setValue(Boolean.TRUE);
			ids.add(kdtEntries.getCell(rowIndex,"id").getValue());
			if(level > 1){
				String longNumber = (String)kdtEntries.getCell(rowIndex,LONGNUMBER).getValue();
				String currentLN = null;
				while(longNumber.lastIndexOf(".") != -1){
					longNumber = longNumber.substring(0,longNumber.lastIndexOf("."));
					int parentIndex = -1;
					boolean flag = true;
					for(int i = 0; i < kdtEntries.getRowCount(); i++) {
						currentLN = (String)kdtEntries.getCell(i,LONGNUMBER).getValue();
						if(longNumber.equals(currentLN))
							parentIndex = i;
						if(currentLN.startsWith(longNumber+".") && !(Boolean)kdtEntries.getCell(i,"iscse").getValue()){
							flag = false;
							break;
						}
					}
					if(flag){
						kdtEntries.getCell(parentIndex,"iscse").setValue(Boolean.TRUE);
						ids.add(kdtEntries.getCell(parentIndex,"id").getValue());
					}else{
						break;
					}
				}
			}
			builder.appendSql("update T_CON_ProgrammingContract set cfiscse=1 where fid in("+setToString(ids)+")");
			builder.executeUpdate();
		}
	}
	
	private String setToString(Set idSet){
		StringBuffer buffer = new StringBuffer();
		Object[] it = idSet.toArray();
    	for(int i=it.length-1; i>=0; i--){
    		buffer.append("'");
    		buffer.append(((BOSUuid)it[i]).toString());
    		if(i==0){
    			buffer.append("' ");
    		}else
    			buffer.append("', ");
    	}
    	return buffer.toString();
	}
	
	/**
	 * ��������а�ť
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		int rowCount = kdtEntries.getRowCount();
		int row = -1;
		if (rowIndex < 0) {
			// �±�Ϊ0����������
			create.addLine(kdtEntries, 1);
			if (rowCount == 0)
				row = 0;
			else
				row = rowCount;
			String number = gainNumberByDate(null);
			kdtEntries.getCell(row, LONGNUMBER).setValue(number);
			kdtEntries.getCell(row, "number").setValue(number);
		} else {
			String o = (String) kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			Object head = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
			Object headlevel = kdtEntries.getCell(rowIndex, "level").getValue();
			Object name = getCellValue(kdtEntries, rowIndex, "name");
			Object longName = kdtEntries.getCell(rowIndex, "longName").getValue();
			
			boolean isCiting = ((Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue();
			if (isCiting) {
				FDCMsgBox.showInfo("����Լ�滮�ѱ���ͬ����,�����������¼�.");
				return;
			}
			
			
			ProgrammingContractInfo headObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
			// ����ʱ�ж������Ƿ�Ϸ�
			if (o == null || o.toString().trim().length() == 0) {
				MsgBox.showInfo("��¼�� " + (rowIndex + 1) + " �У���ܺ�Լ���벻��Ϊ�գ�");
				return;
			} else if ((o.toString().trim() + ".").length() >= 80) {
				MsgBox.showInfo("��¼�� " + (rowIndex + 1) + " �У���ܺ�Լ�������\n���޸ĺ��������Ӽ���ܺ�Լ��");
				return;
			} else if (name == null || StringUtils.isEmpty(name.toString())) {
				MsgBox.showInfo("��¼�� " + (rowIndex + 1) + " �У���ܺ�Լ���Ʋ���Ϊ�գ�");
				return;
			} else if (head == null) {
				int level = new Integer(headlevel.toString()).intValue();
				if (level == 1) {
					row = getInsertRowIndex(o, rowIndex, rowCount);
					create.insertLine(kdtEntries, row, new Integer(headlevel.toString()).intValue() + 1, headObject);
					kdtEntries.getCell(row, HEADNUMBER).setValue(o);
					if (name != null)
						kdtEntries.getCell(row, "longName").setValue(name.toString().trim() + ".");
					String number = gainNumberByDate(o);
					kdtEntries.getCell(row, LONGNUMBER).setValue(o + "." + number);
					kdtEntries.getCell(row, "number").setValue(number);
				}
			} else {
				String ln = o.toString();

				if (ln.length() == (head.toString().length() + 1)) {
					MsgBox.showInfo("��¼�� " + (rowIndex + 1) + " �У���ܺ�Լ���벻��Ϊ�գ�");
					return;
				}
				row = getInsertRowIndex(o, rowIndex, rowCount);

				create.insertLine(kdtEntries, row, new Integer(headlevel.toString()).intValue() + 1, headObject);
				kdtEntries.getCell(row, HEADNUMBER).setValue(o);
				if (longName != null)
					kdtEntries.getCell(row, "longName").setValue(longName.toString().trim() + ".");
				String number = gainNumberByDate(o);
				kdtEntries.getCell(row, LONGNUMBER).setValue(o + "." + number);
				kdtEntries.getCell(row, "number").setValue(number);
			}
		}
		// ���ñ����еı༭��ʽ�������ϼ����벻���޸�
		/*
		 * KDTextField txtLongNumber = new KDTextField(); LimitedTextDocument
		 * document = new LimitedTextDocument("");
		 * txtLongNumber.setMaxLength(80); txtLongNumber.setDocument(document);
		 * KDTDefaultCellEditor cellEditorNumber = new
		 * KDTDefaultCellEditor(txtLongNumber); kdtEntries.getCell(row,
		 * LONGNUMBER).setEditor(cellEditorNumber);
		 */
		
		ProgrammingContractInfo rowUserObject = (ProgrammingContractInfo) kdtEntries.getRow(row).getUserObject();
		if (rowUserObject == null) {
			rowUserObject = new ProgrammingContractInfo();
			rowUserObject.setId(BOSUuid.create(rowUserObject.getBOSType()));
			kdtEntries.getRow(row).setUserObject(rowUserObject);
		}
		rowUserObject.setProgramming(editData);

		formatName(row);

		createTree();

		setSmallBtnEnable();
		createCostCentertF7();
		
		// modified by zhaoqin on 2013/10/17 start
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex + 1).getUserObject();
		setkdtEntries_AmountState1(rowIndex + 1, rowObject);
		// modified by zhaoqin on 2013/10/17 end
		
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 */
		setSaved(false);
	}
	
	protected void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		this.kdtEntries.getEditManager().editingStopped();
		Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		int level = kdtEntries.getRow(rowIndex).getTreeLevel();

		if (rowIndex == -1) {
			FDCMsgBox.showInfo("��ѡ����");
			return;
		}

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();

		IRow nextRow = kdtEntries.getRow(rowIndex + 1);
		if (nextRow != null) {
			int nLevel = nextRow.getTreeLevel();
			if (level < nLevel) {
				FDCMsgBox.showInfo("ֻ��ѡ��ϸ����Լ�滮.");
				return;
			}

		}
		
		boolean isCiting = ((Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue();
		if (isCiting) {
			FDCMsgBox.showInfo("����Լ�滮�ѱ���ͬ����,������ֽ�.");
			return;
		}
		
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("programmingContract.baseId", rowObject.getBaseId()));
//		if (rowObject.getBaseId()!=null && ContractBillFactory.getRemoteInstance().exists(filter)) {
//			FDCMsgBox.showInfo("����Լ�滮�ѱ���ͬ����,������ֽ�.");
//			return;
//		}

		uiContext.put("programmingContract", rowObject);// �滮��Լ
		uiContext.put("pcCollection", pcCollection);// �滮��Լ����
		uiContext.put("project", project);// ������Ŀ
		uiContext.put("aimCostInfo", aimCostInfo);// Ŀ��ɱ�

		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingSplitUI.class.getName(), uiContext, null, oprtState);
		ProgrammingSplitUI splitUI = (ProgrammingSplitUI) uiWindow.getUIObject();
		uiWindow.show();

		List returnLst = splitUI.getReturnValues();
		int rowCount = kdtEntries.getRowCount();
		if(rowIndex+1==rowCount){
			for (int i = returnLst.size() - 1; i >= 0; i--) {
				int newIndex = getInsertRowIndex(oldLongNumber, rowIndex, rowCount);
				IRow row = kdtEntries.addRow(newIndex);
				create.loadLineFields(kdtEntries, row, (ProgrammingContractInfo) returnLst.get(i));
			}
		}else{
			for (int i = 0; i <returnLst.size(); i++) {
				int newIndex = getInsertRowIndex(oldLongNumber, rowIndex, rowCount);
				IRow row = kdtEntries.addRow(newIndex);
				create.loadLineFields(kdtEntries, row, (ProgrammingContractInfo) returnLst.get(i));
			}
		}

		dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);

		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(BALANCE), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
		//		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("buildPerSquare"), level);
		//		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("soldPerSquare"), level);
		//		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("purControlAmount"), level);

		createTree();

		setSmallBtnEnable();

	}

	private void formatName(int rowIndex) {
		KDTextField txtName = new KDTextField();
		txtName.setMaxLength(80);
		txtName.setDocument(new LimitedLengthDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("^\\.+$")) {
					return;
				}
				super.insertString(offs, str, a);
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);
			}
		});
		KDTDefaultCellEditor cellEditorName = new KDTDefaultCellEditor(txtName);
		kdtEntries.getCell(rowIndex, "name").setEditor(cellEditorName);
	}

	/**
	 * ��������а�ť
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionInsertLines_actionPerformed(ActionEvent e) throws Exception {
		FDCTableHelper.checkSelectedAndAbort(this, kdtEntries);
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		Object name = getCellValue(kdtEntries, rowIndex, "name");
		// Object name = kdtEntries.getCell(rowIndex, "name").getValue();
		if (o == null || o.toString().trim().length() == 0) {
			MsgBox.showInfo("��¼�� " + (rowIndex + 1) + " �У���ܺ�Լ���벻��Ϊ�գ�");
			return;
		} else if (name == null || StringUtils.isEmpty(name.toString())) {
			MsgBox.showInfo("��¼�� " + (rowIndex + 1) + " �У���ܺ�Լ���Ʋ���Ϊ�գ�");
			return;
		}
		String headNumber = (String) kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
		Object level = kdtEntries.getCell(rowIndex, "level").getValue();
		ProgrammingContractInfo headObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		create.insertLine(kdtEntries, rowIndex, new Integer(level.toString()).intValue(), headObject.getParent());
		kdtEntries.getCell(rowIndex, HEADNUMBER).setValue(headNumber);
		if (headObject.getParent() != null && headObject.getParent().getDisplayName() != null) {
			kdtEntries.getCell(rowIndex, "longName").setValue(headObject.getParent().getDisplayName().toString().trim() + ".");
		}
		// KDTextField txtLongNumber = new KDTextField();
		// LimitedTextDocument document = new LimitedTextDocument("");
		// txtLongNumber.setDocument(document);
		// KDTDefaultCellEditor cellEditorNumber = new
		// KDTDefaultCellEditor(txtLongNumber);
		// kdtEntries.getCell(rowIndex, LONGNUMBER).setEditor(cellEditorNumber);
		String number = null;
		if (headNumber == null || headNumber.toString().length() == 0) {
			number = gainNumberByDate(headNumber);
			kdtEntries.getCell(rowIndex, LONGNUMBER).setValue(number);
		} else {
			number = gainNumberByDate(headNumber);
			kdtEntries.getCell(rowIndex, LONGNUMBER).setValue(headNumber + "." + number);
		}
		kdtEntries.getCell(rowIndex, "number").setValue(number);
		
		
		ProgrammingContractInfo rowUserObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		if (rowUserObject == null) {
			rowUserObject = new ProgrammingContractInfo();
			rowUserObject.setId(BOSUuid.create(rowUserObject.getBOSType()));
			kdtEntries.getRow(rowIndex).setUserObject(rowUserObject);
		}
		rowUserObject.setProgramming(editData);
		
		formatName(rowIndex);
		createTree();
		
		// modified by zhaoqin on 2013/10/17 start
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setkdtEntries_AmountState1(rowIndex, rowObject);
		// modified by zhaoqin on 2013/10/17 end
	}

	/**
	 * ���ɾ���а�ť
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionRemoveLines_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.kdtEntries.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
		Object h = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
		boolean isCiting = ((Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue();
		if (isCiting) {
			MsgBox.showInfo("���ڱ����õĿ�ܺ�Լ��" + name.toString() + "��,�޷�ɾ����");
			return;
		}

		ArrayList list = new ArrayList();
		if (longNumber != null) {
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString(), rowIndex, list);
		}

		int oldLevel = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		boolean isHasSomeLevel = false;// Ĭ��û��ͬ���ڵ�
		int someIndex = -1;// ��ͬ���ڵ�ʱ��Ӧ�±�
		int SomeLevel = 0;// ��ͬ���ڵ�ʱ��Ӧ����
		if (h != null) {
			for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
				if (i == rowIndex) {
					continue;
				}
				Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
				if (l_2 != null) {
					if (h.toString().equals(l_2.toString())) {
						isHasSomeLevel = true;
						someIndex = i;
						SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
						break;
					}
				}
			}
		}

		if (list.size() > 1) {
			if (MsgBox.OK == MsgBox.showConfirm2New(null, "����ǰɾ���ĸ��ڵ㡰" + name.toString() + "���»��������Ŀ�ܺ�Լ��ȷ��Ҫһ��ɾ����")) {
				create.removeLine(kdtEntries, list);
				if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, BALANCE).setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, CONTROL_BALANCE).setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, OCCUR_AMOUNT).setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "buildPerSquare").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "soldPerSquare").setValue(FDCHelper.ZERO);
					}
				} else if (isHasSomeLevel && oldLevel == 2) {
					if (h != null) {
						for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
							Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
							if (l_2 != null) {
								if (h.toString().equals(l_2.toString())) {
									someIndex = i;
									SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
									break;
								}
							}
						}
					}
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(BALANCE), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(CONTROL_BALANCE), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(OCCUR_AMOUNT), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
		} else {
			if (MsgBox.OK == MsgBox.showConfirm2New(null, "�Ƿ�ȷ��ɾ�����ݣ�")) {
				create.removeLine(kdtEntries, rowIndex);
				if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, BALANCE).setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, CONTROL_BALANCE).setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, OCCUR_AMOUNT).setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "buildPerSquare").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "soldPerSquare").setValue(FDCHelper.ZERO);
					}
				} else if (isHasSomeLevel && oldLevel == 2) {
					if (h != null) {
						for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
							Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
							if (l_2 != null) {
								if (h.toString().equals(l_2.toString())) {
									someIndex = i;
									SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
									break;
								}
							}
						}
					}
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(BALANCE), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(CONTROL_BALANCE), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(OCCUR_AMOUNT), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
		}

		if (h != null) {
			for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
				Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
				if (l_2 != null) {
					if (h.toString().equals(l_2.toString())) {
						someIndex = i;
						SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
						break;
					}
				}
			}
		}

		// ɾ��ʱ��Ҫ���¶Խ����л���
		int loop = 0;
		if (h == null) {
			loop = -1;
		} else {
			loop = getLoop(rowIndex, h);
		}
		if (loop >= 0) {
			int level = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			ProgrammingContractInfo proConInfo = (ProgrammingContractInfo) kdtEntries.getRow(loop).getUserObject();
			ProgrammingContracCostCollection costEntries = proConInfo.getCostEntries();
			if (costEntries.size() == 0) {
				if (!isHasSomeLevel) {
					kdtEntries.getCell(loop, "amount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlAmount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, BALANCE).setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, CONTROL_BALANCE).setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, OCCUR_AMOUNT).setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "soldPerSquare").setValue(FDCHelper.ZERO);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(BALANCE), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
				} else {
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(BALANCE), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(CONTROL_BALANCE), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex(OCCUR_AMOUNT), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {

				if (!isHasSomeLevel) {
					BigDecimal amount = FDCHelper.ZERO;
					BigDecimal controlAmount = FDCHelper.ZERO;
					BigDecimal balance = FDCHelper.ZERO;
					BigDecimal contral = FDCHelper.ZERO;
					BigDecimal occur = FDCHelper.ZERO;
					//					BigDecimal amount = FDCHelper.ZERO;
					//					BigDecimal amount = FDCHelper.ZERO;
					for (int i = 0; i < costEntries.size(); i++) {
						amount = amount.add(FDCHelper.toBigDecimal(costEntries.get(i).getContractAssign()));
					}
					kdtEntries.getCell(loop, "amount").setValue(amount);
					kdtEntries.getCell(loop, "controlAmount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, BALANCE).setValue(amount);
					kdtEntries.getCell(loop, CONTROL_BALANCE).setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, OCCUR_AMOUNT).setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "soldPerSquare").setValue(FDCHelper.ZERO);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(BALANCE), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);

				} else {
					caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(BALANCE), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
				}

			}
		}
		sumClass.caclTotalAmount(kdtEntries);
		sumClass.appendProFootRow(null, null);
		setSmallBtnEnable();
		dataBinder.storeFields();
		createTree();
		
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 */
		setSaved(false);
	}

	private int getLoop(int rowIndex, Object h) {
		int loop = 0;
		boolean isHasSame = false;
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			if (i == rowIndex)
				break;
			Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
			if (l_2 != null) {
				if (h.toString().equals(l_2.toString())) {
					loop = i;
					isHasSame = true;
					break;
				}
			}
		}

		if (!isHasSame) {
			if (rowIndex == kdtEntries.getRowCount()) {
				loop = rowIndex - 1;
			} else {
				loop = rowIndex - 1;
			}
		}
		return loop;
	}

	/**
	 * �����ϸ��Ϣ��������¼����
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionDetails_actionPerformed(ActionEvent e) throws Exception {
		
		FDCTableHelper.checkSelectedAndAbort(this, kdtEntries);
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		this.kdtEntries.getEditManager().editingStopped();
		Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (rowIndex == -1) {
			FDCMsgBox.showInfo("��ѡ����");
			return;
		}
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		if(project==null){
			DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			project = (CurProjectInfo) projectNode.getUserObject();
		}
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		/***** ��ȡ���еĹ�����ĿID, modify by lihaiou,2013.09.23, for bug R130827-0309**********/
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
		Set idSets = new HashSet();
		getChildProject(rootNode, idSets);
		uiContext.put("projectIDs", idSets);
		uiContext.put("programmingContract", rowObject);// �滮��Լ
		uiContext.put("pcCollection", pcCollection);// �滮��Լ����
		uiContext.put("project", project);// ������Ŀ
		uiContext.put("aimCostInfo", aimCostInfo);// Ŀ��ɱ�

		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, oprtState);
		uiWindow.show();
		
		if (oprtState.equals(OprtState.VIEW))
			return;
		// �����ݵ���¼��
		dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
		setRowOccurAmount(rowIndex);
		// ���³�����
		setEntriesNameCol(rowIndex, level);
		// ���³�����
		Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldLongNumber != null && newLongNumber != null) {
			if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
				setEntriesNumberCol(rowIndex, level);
			}
		}
		calcSquare(rowIndex);
		// ���¹滮���,�滮���,�ɹ����ƽ��������Ļ���
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(BALANCE), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("buildPerSquare"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("soldPerSquare"), level);
		// �����ж����Ƿ���д������ݣ�����������ú���ɫ���Ե��������ú���ɫ
		setMyFontColor();
		
		// modified by zhaoqin on 2013/10/17
		setkdtEntries_AmountState1(rowIndex, rowObject);
		
		
		/* modified by zhaoqin for R131127-0266 on 2013/12/11 start */
		if(OprtState.ADDNEW.equals(getOprtState()) || OprtState.EDIT.equals(getOprtState())) {
			if(getBizInterface().exists(new ObjectUuidPK(this.editData.getId()))) {
				Boolean isModified = (Boolean)(uiWindow.getUIObject().getUIContext().get("isModified"));
				if(null != isModified && isModified.booleanValue()) {
					getBizInterface().save(this.editData);
				}
			}
		}
		/* modified by zhaoqin for R131127-0266 on 2013/12/11 end */
	}

	protected void getChildProject(DefaultKingdeeTreeNode treeNode, Set ids) {
		if (treeNode != null && treeNode.isLeaf()) {
			Object userObject = treeNode.getUserObject();
			if (userObject != null && userObject instanceof CurProjectInfo) {
				ids.add(((CurProjectInfo) userObject).getId().toString());
			}
			return;
		} else {
			if (treeNode == null) {
				return;
			}
			Object userObject = treeNode.getUserObject();
			if (userObject == null) {
				return;
			}
			if (userObject != null && userObject instanceof CurProjectInfo) {
				ids.add(((CurProjectInfo) userObject).getId().toString());
			}
			for (int i = 0; i < treeNode.getChildCount(); i++) {
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) treeNode.getChildAt(i);
				getChildProject(childNode, ids);
			}
		}
	}
	/**
	 * �����ж����Ƿ���д������ݣ�����������ú���ɫ���Ե��������ú���ɫ
	 * 
	 * @param rowIndex
	 */
	private void setMyFontColor() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			boolean flag = false;// falseĬ�ϱ�ʾû�д�������
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection pccCollection = programmingContractInfo.getCostEntries();
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);
				BigDecimal assigning = pccInfo.getAssigning();// ������
				BigDecimal contractAssign = pccInfo.getContractAssign();// ����Լ����
				// ��"����Լ����">"������"����ʾ�д�������
				if (FDCHelper.compareTo(contractAssign, assigning) > 0) {
					flag = true;
				}
			}
			if (flag) {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.RED);
			} else {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
				Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
				if (blanceValue != null) {
					BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
					if (blance.compareTo(FDCHelper.ZERO) > 0) {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.green);
					} else {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
					}
				}
			}

			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			if (blanceValue != null) {
				BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
				if (blance.compareTo(FDCHelper.ZERO) == 0) {
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.black);
				}
			}
		}
	}

	/**
	 * ��ȡ��ѡ�е������Ӽ��ڵ㡢���ж��Ƿ��б����õĿ�ܺ�Լ
	 * 
	 * @param longNumber
	 * @param rowIndex
	 * @param list
	 */
	private void getSublevel(String longNumber, int rowIndex, ArrayList list) {
		int rowCount = kdtEntries.getRowCount();
		for (int i = rowIndex + 1; i < rowCount; i++) {
			Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();

			if (headNumber != null && headNumber.toString() != null) {
				if (headNumber.toString().startsWith(longNumber)) {
					boolean isCiting = ((Boolean) kdtEntries.getCell(i, "isCiting").getValue()).booleanValue();
					if (isCiting) {
						MsgBox.showInfo("���ڱ����õĿ�ܺ�Լ��" + l.toString() + "��,�޷�ɾ����");
						SysUtil.abort();
					}
					list.add(new Integer(i));
				} else {
					break;
				}
			}
		}
	}

	/**
	 * ����ʱ��������
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

	/**
	 * ��ȡҪ�����е�λ�ã��±꣩
	 * 
	 * @param o
	 * @param rowIndex
	 * @param rowCount
	 * @return
	 */
	protected int getInsertRowIndex(Object o, int rowIndex, int rowCount) {
		int row = 0;
		String longNumber = o.toString();
		if (rowIndex + 1 == rowCount) {
			return rowIndex + 1;
		}

		for (int i = rowIndex + 1; i < rowCount; i++) {
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 == level || level_2 < level) {
				return i;
			}
			Object n = kdtEntries.getCell(i, LONGNUMBER).getValue();
			if (n != null && n.toString() != null) {
				if (!n.toString().startsWith(longNumber)) {
					row = i;
					break;
				}
			} else {
				return i + 1;
			}

			if (rowIndex + 2 == rowCount) {
				return rowCount;
			}
		}
		return row == 0 ? rowCount : row;
	}

	protected void kdtEntries_editStarting(KDTEditEvent e) throws Exception {
		if (e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)) {
			Object longNumber = kdtEntries.getCell(e.getRowIndex(), LONGNUMBER).getValue();
			if (longNumber != null && longNumber.toString().trim().length() > 80) {
				MsgBox.showInfo("��¼�� " + (e.getRowIndex() + 1) + " �У���ܺ�Լ���볬��\n���޸��ϼ�������ٽ��б༭��");
				kdtEntries.getEditManager().cancelEditing();
				e.setCancel(true);
			}
		}
	}

	protected void kdtEntries_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		setSmallBtnEnable();
	}

	protected void kdtEntries_editStarted(KDTEditEvent e) throws Exception {
		// ����༭���Ǳ������������ȡ�����еı༭���������ϼ����벻���Ա�ɾ�����޸�
		if (e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)) {
			int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
			ICellEditor editor = kdtEntries.getCell(rowIndex, LONGNUMBER).getEditor();
			if (editor != null) {
				if (editor instanceof KDTDefaultCellEditor) {
					int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
					KDTDefaultCellEditor de = (KDTDefaultCellEditor) editor;
					KDTextField txtLongNumber = (KDTextField) de.getComponent();
					LimitedTextDocument doc = (LimitedTextDocument) txtLongNumber.getDocument();
					txtLongNumber.setMaxLength(80);
					String txt = "";
					Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
					Object subNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
					if (longNumber == null || longNumber.toString().trim().length() == 0) {
						if (subNumber != null && subNumber.toString().trim().length() > 0) {
							txt = subNumber.toString().trim();
							kdtEntries.getCell(rowIndex, LONGNUMBER).setValue(txt);
						}
					} else {
						txt = longNumber.toString().trim();
					}
					if (level > 1) {
						doc.setLimitedText(subNumber == null ? "" : subNumber.toString().trim() + ".");
						doc.setIsOnload(true);
						doc.setIsAutoUpdate(true);
						txtLongNumber.setText(txt);
						doc.setIsOnload(false);
						doc.setIsAutoUpdate(false);
					} else {
						doc.setIsAutoUpdate(true);
						doc.setIsOnload(true);
						txtLongNumber.setText(txt);
						doc.setIsAutoUpdate(false);
						doc.setIsOnload(false);
					}
				}
			}
		}
	}

	protected void kdtEntries_editStopped(KDTEditEvent e) throws Exception {
		// ���ñ���
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if (oldValue == null && newValue == null) {
			return;
		}
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 */
		setSaved(false);
		this.dataBinder.storeFields();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0)
			return;
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		int colIndex = e.getColIndex();

		if (colIndex == kdtEntries.getColumnIndex(LONGNUMBER)) {
			if (oldValue != null && newValue != null) {
				if (oldValue.equals(newValue)) {
					return;
				}
			}
			setEntriesNumberCol(rowIndex, level);
		}

		if (colIndex == kdtEntries.getColumnIndex("name")) {
			setEntriesNameCol(rowIndex, level);
		}

		// �жϹ滮����Ƿ�����ѷ�������ɹ����ƽ��
		if (colIndex == kdtEntries.getColumnIndex("amount")) {
			Object newAmountObj = e.getValue();// �¹滮���
			Object oldAmountObj = e.getOldValue();// ԭ�滮���
			Object oldControlAmountObj = kdtEntries.getCell(rowIndex, "controlAmount").getValue();
			Object oldBalanceObj = kdtEntries.getCell(rowIndex, BALANCE).getValue();
			BigDecimal oldAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldAmountObj);
			BigDecimal newAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(newAmountObj);
			BigDecimal oldBalance = oldBalanceObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldBalanceObj);

			BigDecimal oldControlAmount = oldBalanceObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldControlAmountObj);
			// �ѷ������
			BigDecimal occurredAmount = oldAmount.subtract(oldBalance);
			if (newAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� �ɹ����ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			}
			Boolean isCiting = (Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue();
			if (Boolean.FALSE == isCiting) {
				return;
			}
			if (newAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� �ɹ��ɹ����ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			} else if (newAmount.compareTo(occurredAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� �������");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			}

			calcSquare(e.getRowIndex());
			setMyFontColor();
		}
		// �жϹ滮����Ƿ�����ѷ�������ɹ��ɹ����ƽ��2
		if (colIndex == kdtEntries.getColumnIndex("controlAmount")) {
			Object newControlAmountObj = e.getValue();// �²ɹ����ƽ��
			Object oldControlAmountObj = e.getOldValue();// ԭ�ɹ����ƽ��
			Object oldAmountObj = kdtEntries.getCell(rowIndex, "amount").getValue();
			BigDecimal oldControlAmount = oldControlAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldControlAmountObj);
			BigDecimal newControlAmount = newControlAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(newControlAmountObj);
			BigDecimal oldAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldAmountObj);
			if (oldAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� �ɹ����ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "controlAmount").setValue(oldControlAmount);
				return;
			}
			Boolean isCiting = (Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue();
			if (Boolean.FALSE == isCiting) {
				return;
			}
			if (oldAmount.compareTo(newControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� �ɹ����ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "controlAmount").setValue(oldControlAmount);
				return;
			}

			setMyFontColor();
		}
		if (colIndex == kdtEntries.getColumnIndex("amount") || colIndex == kdtEntries.getColumnIndex("controlAmount")) {
			if (oldValue != null && newValue != null) {
				if ((new BigDecimal(oldValue.toString())).compareTo(new BigDecimal(newValue.toString())) == 0) {
					return;
				}
			}
			// ���¹滮���Ϳ������
			if (colIndex == kdtEntries.getColumnIndex("amount")) {
				if (oldValue != null && newValue != null) {
					Object balanceObj = kdtEntries.getCell(rowIndex, BALANCE).getValue();
					if (balanceObj != null) {
						BigDecimal balance = FDCHelper.toBigDecimal(balanceObj);
						kdtEntries.getCell(rowIndex, BALANCE).setValue(
								balance.add((FDCHelper.toBigDecimal(newValue).subtract(FDCHelper.toBigDecimal(oldValue)))));
					}
				}
				if (level != 1) {
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex(BALANCE), level);
				}
			}

			if (colIndex == kdtEntries.getColumnIndex("controlAmount")) {
				if (oldValue != null && newValue != null) {
					Object controlBalanceObj = kdtEntries.getCell(rowIndex, CONTROL_BALANCE).getValue();
					if (controlBalanceObj != null) {
						BigDecimal controlBalance = FDCHelper.toBigDecimal(controlBalanceObj);
						kdtEntries.getCell(rowIndex, CONTROL_BALANCE).setValue(
								controlBalance.add((FDCHelper.toBigDecimal(newValue).subtract(FDCHelper.toBigDecimal(oldValue)))));
					}
				}
				if (level != 1) {
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
				}
				
				Object amout = kdtEntries.getCell(rowIndex, "amount").getValue();
				if (amout != null) {
					kdtEntries.getCell(rowIndex, "reservedChangeRate").setValue(
							FDCHelper.subtract(FDCHelper.ONE, FDCHelper.divide(newValue, amout, 4, BigDecimal.ROUND_HALF_UP)));
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("controlAmount"), level);
				}
			}
			caclTotalAmount(e.getRowIndex(), e.getColIndex(), level);
		}
		//����Ԥ�����ǩ֤��
		if (e.getColIndex() == kdtEntries.getColumnIndex("reservedChangeRate")) {
			if (FDCHelper.compareTo(e.getOldValue(), e.getValue()) != 0) {
				kdtEntries.getCell(e.getRowIndex(), e.getColIndex()).setValue(FDCHelper.divide(e.getValue(), FDCHelper.ONE_HUNDRED,4,BigDecimal.ROUND_HALF_UP));
			}
		}
		
		
		//����ɹ����Ƽ�
		if (colIndex == kdtEntries.getColumnIndex("amount") || e.getColIndex() == kdtEntries.getColumnIndex("reservedChangeRate")) {
			BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(e.getRowIndex(), "amount").getValue());
			Object reservedChangeRate = kdtEntries.getCell(e.getRowIndex(), "reservedChangeRate").getValue();
			Object old = kdtEntries.getCell(e.getRowIndex(), "controlAmount").getValue();
			if (FDCHelper.isNullZero(amount)) {
				kdtEntries.getCell(e.getRowIndex(), "controlAmount").setValue(amount);
			} else {
				kdtEntries.getCell(e.getRowIndex(), "controlAmount").setValue(
						FDCHelper.toBigDecimal(FDCHelper.multiply(amount, FDCHelper.subtract(FDCHelper.ONE, reservedChangeRate)), 2));
			}
			Object newS = kdtEntries.getCell(e.getRowIndex(), "controlAmount").getValue();
			Object controlBalanceObj = kdtEntries.getCell(rowIndex, CONTROL_BALANCE).getValue();
			if (controlBalanceObj != null) {
				BigDecimal controlBalance = FDCHelper.toBigDecimal(controlBalanceObj);
				kdtEntries.getCell(rowIndex, CONTROL_BALANCE).setValue(
						controlBalance.add((FDCHelper.toBigDecimal(newS).subtract(FDCHelper.toBigDecimal(old)))));
			}
			if (level != 1) {
				caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
			}
			
			caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("controlAmount"), level);
		}
		
		// ���ù滮�����ɫ
		if (colIndex == kdtEntries.getColumnIndex(BALANCE)) {
			Object blanceValue = kdtEntries.getCell(rowIndex, BALANCE).getValue();
			if (oldValue != null && newValue != null) {
				BigDecimal newV = new BigDecimal(newValue.toString());
				BigDecimal oldV = new BigDecimal(oldValue.toString());
				if (newV.compareTo(oldV) == 0) {
					return;
				}
			}
			if (blanceValue != null) {
				BigDecimal blance = new BigDecimal(blanceValue.toString());
				if (blance.compareTo(FDCHelper.ZERO) > 0) {
					kdtEntries.getCell(rowIndex, BALANCE).getStyleAttributes().setFontColor(Color.green);
				} else {
					kdtEntries.getCell(rowIndex, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
		}
		//modify by yxl 20150814 ���ݺ�Լ���ʹ����Ƽ۷�ʽ�ͷ�����ʽ
		//��Ʋ�ʱ�����ڳɱ��������ڹ��̲�        ���Ϲ���˾��ʱ��Ӧ���ڿ���ʱ��N��  sendPage
		if(colIndex==kdtEntries.getColumnIndex("hyType")){
			if(newValue == null){
				ProgrammingContractInfo	pcinfo = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
				pcinfo.getFxbdEntry().clear();
				kdtEntries.getCell(rowIndex,"priceWay").setValue(null);
				kdtEntries.getCell(rowIndex,"sendPage").setValue(null);
			}else{
//				PcTypeInfo ptinfo=(PcTypeInfo)kdtEntries.getCell(rowIndex,colIndex).getValue();
				PcTypeInfo oldInfo = (PcTypeInfo)oldValue;
				PcTypeInfo newInfo = (PcTypeInfo)newValue;
				if (oldInfo != null && newInfo.getId().toString().equals(oldInfo.getId().toString())) {
					return;
				}
				kdtEntries.getCell(rowIndex,"priceWay").setValue(newInfo.getPriceWay());
				kdtEntries.getCell(rowIndex,"sendPage").setValue(newInfo.getSendContWay());
				ProgrammingContractInfo	pcinfo = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
				pcinfo.getFxbdEntry().clear();
			}
		}else if(colIndex==kdtEntries.getColumnIndex("sendPage") && newValue != null){
			if (oldValue != null && oldValue.equals(newValue)) {
				return;
			}
			if(kdtEntries.getCell(rowIndex,"hyType").getValue() != null){
				PcTypeInfo hytype = (PcTypeInfo)kdtEntries.getCell(rowIndex,"hyType").getValue();
				if(hytype.getSendContWay().equals((SendContWay)newValue))
					return;
			}
			if("ZJWT".equals(((SendContWay)newValue).getName())){
				kdtEntries.getCell(rowIndex,colIndex).getStyleAttributes().setBackground(Color.red);
			}else
				kdtEntries.getCell(rowIndex,colIndex).getStyleAttributes().setBackground(Color.white);
		}else if(colIndex==kdtEntries.getColumnIndex("sgDate")){
			if(newValue == null){
				fxbdChangeByMain(rowIndex,"SGT",null);
				return;
			}
			if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
				return;
			}
			Timestamp sgDate = new Timestamp(((Date)newValue).getTime());
			if(kdtEntries.getCell(rowIndex,"csDate").getValue()!=null){
				Timestamp csDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"csDate").getValue()).getTime());
				if(sgDate.compareTo(csDate) > 0){
					FDCMsgBox.showInfo("��Ʋ�ʱ��Ӧ���ڳɱ�������");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
//					kdtEntries.getEditManager().editCellAt(rowIndex,colIndex);
					fxbdChangeByMain(rowIndex,"SGT",null);
					return;
				}
			}
			if(kdtEntries.getCell(rowIndex,"startDate").getValue()!=null){
				Timestamp startDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"startDate").getValue()).getTime());
				if(sgDate.compareTo(startDate) > 0){
					FDCMsgBox.showInfo("��Ʋ�ʱ��Ӧ���ڹ��̲���");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"SGT",null);
					return;
				}
			}
			fxbdChangeByMain(rowIndex,"SGT",(Date)newValue);
//			if(kdtEntries.getCell(rowIndex,"endDate").getValue()!=null){
//				Timestamp endDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"endDate").getValue()).getTime());
//				if(sgDate.compareTo(endDate) < 0){
//					FDCMsgBox.showInfo("��Ʋ�ʱ��Ӧ���ڹ��̲���");
//					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
//					return;
//				}
//			}
		}else if(colIndex==kdtEntries.getColumnIndex("csDate")){
			if(newValue == null){
				fxbdChangeByMain(rowIndex,"CSD",null);
				return;
			}
			if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
				return;
			}
			Timestamp csDate = new Timestamp(((Date)newValue).getTime());
			if(kdtEntries.getCell(rowIndex,"sgDate").getValue()!=null){
				Timestamp sgDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"sgDate").getValue()).getTime());
				if(csDate.compareTo(sgDate) < 0){
					FDCMsgBox.showInfo("�ɱ�����Ӧ������Ʋ�ʱ�䣡");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"CSD",null);
					return;
				}
			}
			fxbdChangeByMain(rowIndex,"CSD",(Date)newValue);
		}else if(colIndex==kdtEntries.getColumnIndex("startDate")){
			if(newValue == null){
				fxbdChangeByMain(rowIndex,"SWD",null);
				return;
			}
			if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
				return;
			}
			Timestamp startDate = new Timestamp(((Date)newValue).getTime());
			if(kdtEntries.getCell(rowIndex,"sgDate").getValue()!=null){
				Timestamp sgDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"sgDate").getValue()).getTime());
				if(startDate.compareTo(sgDate) < 0){
					FDCMsgBox.showInfo("���̲�Ӧ������Ʋ�ʱ�䣡");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"SWD",null);
					return;
				}
			}
			if(kdtEntries.getCell(rowIndex,"endDate").getValue()!=null){
				Timestamp endDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"endDate").getValue()).getTime());
				if(startDate.compareTo(endDate) > 0){
					FDCMsgBox.showInfo("����ʱ��Ӧ���ڿ���ʱ�䣡");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"SWD",null);
					return;
				}
			}
			fxbdChangeByMain(rowIndex,"SWD",(Date)newValue);
		}else if(colIndex==kdtEntries.getColumnIndex("endDate")){
			if(newValue == null){
				fxbdChangeByMain(rowIndex,"EWD",null);
				return;
			}
			if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
				return;
			}
			Timestamp endDate = new Timestamp(((Date)newValue).getTime());
			if(kdtEntries.getCell(rowIndex,"sgDate").getValue()!=null){
				Timestamp sgDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"sgDate").getValue()).getTime());
				if(endDate.compareTo(sgDate) < 0){
					FDCMsgBox.showInfo("���̲�Ӧ������Ʋ�ʱ�䣡");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"EWD",null);
					return;
				}
			}
			if(kdtEntries.getCell(rowIndex,"startDate").getValue()!=null){
				Timestamp startDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"startDate").getValue()).getTime());
				if(startDate.compareTo(endDate) > 0){
					FDCMsgBox.showInfo("����ʱ��Ӧ���ڿ���ʱ�䣡");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"EWD",null);
					return;
				}
			}
			fxbdChangeByMain(rowIndex,"EWD",(Date)newValue);
		}else if(colIndex==kdtEntries.getColumnIndex("csendDate")){
			if(newValue == null){
				fxbdChangeByMain(rowIndex,"CSED",null);
				return;
			}
			if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
				return;
			}
			Timestamp csendDate = new Timestamp(((Date)newValue).getTime());
			if(kdtEntries.getCell(rowIndex,"startDate").getValue()!=null){
				Timestamp startDate = new Timestamp(((Date)kdtEntries.getCell(rowIndex,"startDate").getValue()).getTime());
				if(startDate.compareTo(csendDate) < 0){
					FDCMsgBox.showInfo("���Ϲ���˾ʱ��Ӧ���ڿ���ʱ�䣡");
					kdtEntries.getCell(rowIndex,colIndex).setValue(null);
					fxbdChangeByMain(rowIndex,"CSED",null);
					return;
				}
			}
			fxbdChangeByMain(rowIndex,"CSED",(Date)newValue);
		}
	}

	/**
	 * ������ֶ����޸�ʱ�Զ����ϻ���
	 * 
	 * @param index
	 * @param colIndex
	 * @param level
	 */
	private void caclTotalAmount(int index, int colIndex, int level) {
		if (level == 1) {
			return;
		}
		int loop = index;
		int loopLevel = level;
		while (loop >= 0) {
			int parentIndex = 0;
			BigDecimal dbSum = FDCHelper.ZERO;
			int curLevel = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			if (curLevel > loopLevel) {
				loop--;
				continue;
			}
			String parentNumber = "";
			if (curLevel == 1) {
				parentNumber = kdtEntries.getCell(loop, LONGNUMBER).getValue().toString();
			} else {
				parentNumber = kdtEntries.getCell(loop, HEADNUMBER).getValue().toString();
			}
			dbSum = FDCHelper.ZERO;
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
				Object h = kdtEntries.getCell(i, HEADNUMBER).getValue();
				int cacl_Level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				String number = "";
				// �ж�Ҫ���ܵ��±�ֵ
				if (l != null) {
					if (parentNumber.equals(l.toString())) {
						parentIndex = i;
					}
					number = l.toString();
				} else if (h != null) {
					if (parentNumber.equals(h.toString())) {
						for (int j = 0; j < kdtEntries.getRowCount(); j++) {
							if (j == i)
								continue;
							int j_Level = new Integer(kdtEntries.getCell(j, "level").getValue().toString()).intValue();
							if (cacl_Level == j_Level) {
								Object l_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
								if (l_2 != null) {
									if (h.toString().equals(l_2.toString())) {
										parentIndex = j;
									}
								}
							}
						}
					}
					number = h.toString();
				}
				// �������ֵ
				if (loopLevel == cacl_Level && number.startsWith(parentNumber)) {
					ICell cell = kdtEntries.getRow(i).getCell(colIndex);
					String cellValue = kdtEntries.getCellDisplayText(cell);
					if (cellValue != null)
						cellValue = cellValue.toString().replaceAll(",", "");

					if (!StringUtility.isNumber(cellValue)) {
						Object cellObj = cell.getValue();
						if (cellObj != null)
							cellValue = cellObj.toString();
						if (!StringUtility.isNumber(cellValue))
							continue;
					}
					BigDecimal bigdem = new BigDecimal(String.valueOf(cellValue).trim());
					dbSum = dbSum.add(bigdem);
				}
			}
			String strSum = null;
			if (!(parentIndex == kdtEntries.getRowCount() - 1)) {
				strSum = dbSum.toString();
				kdtEntries.getCell(parentIndex, colIndex).setValue(strSum);
				calcSquare(parentIndex);
			}
			loop--;
			loopLevel--;
		}
	}

	private String attachMentTempID = null;

	protected void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (e.getType() != KDTStyleConstants.BODY_ROW) {
			kdtEntries.getSelectManager().remove();
			kdtEntries.getSelectManager().setActiveRowIndex(-1);
			return;
		}
		if (colIndex == kdtEntries.getColumnIndex("name") || e.getClickCount() == 1) {
			ICell cell = kdtEntries.getCell(rowIndex, colIndex);
			if (cell != null) {
				Object value = cell.getValue();
				if (value != null) {
					if (value instanceof CellTreeNode) {
						CellTreeNode node = (CellTreeNode) value;
						node.doTreeClick(kdtEntries, kdtEntries.getCell(rowIndex, colIndex));
					}
				}
			}
		}

		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		uiContext.put("programmingContract", rowObject);// �滮��Լ
		uiContext.put("pcCollection", pcCollection);// �滮��Լ����
		uiContext.put("project", project);// ������Ŀ
		uiContext.put("aimCostInfo", aimCostInfo);// Ŀ��ɱ�
		//ѡ�к�Լ�滮��¼ʱ�����·��Զ���ʾģ���иú�Լ�Ĺ���˵��    modify by yxl
		if(rowObject.getWorkContent()==null){
			labelExplain.setText("�滮˵����");
		}else{
			labelExplain.setText("�滮˵����"+rowObject.getWorkContent());
		}
		if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
//			btnScontract.setVisible(true);
			if(rowObject.isIscse()){
				btnScontract.setText("��ͬ��ǩ��");
			}else{
				btnScontract.setText("��ͬδǩ��");
			}
			if(isLeaf(rowObject.getLongNumber(),kdtEntries,HEADNUMBER)){
				btnScontract.setEnabled(true);
			}else{
				btnScontract.setEnabled(false);
			}
		}

		// ˫���༭����
		if ( e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntries.getColumnIndex("attachment")) {
			boolean isEdit = false;// Ĭ��Ϊ�鿴״̬
			// if (oprtState.equals(OprtState.ADDNEW) ||
			// oprtState.equals(OprtState.EDIT)) {
			// isEdit = true;
			// }
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			AttachmentUIContextInfo info = getAttacheInfo();
			if (info == null) {
				info = new AttachmentUIContextInfo();
			}
			if (FDCHelper.isEmpty(info.getBoID())) {
				String boID = rowObject.getId().toString();
				if (boID == null) {
					if (!isEdit) {
						if (attachMentTempID == null) {
							boID = acm.getAttID().toString();
							attachMentTempID = boID;
						} else {
							boID = attachMentTempID;
						}
					} else {
						return;
					}
				}
				info.setBoID(boID);
				acm.showAttachmentListUIByBoID(boID, this, isEdit);
				Object idObj = kdtEntries.getCell(rowIndex, "id").getValue();
				StringBuffer allAttachmentName = getAllAttachmentName(idObj.toString());
				if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
					kdtEntries.getCell(rowIndex, "attachment").setValue("���ڸ���");
				} else {
					kdtEntries.getCell(rowIndex, "attachment").setValue(null);
				}
			}
			SysUtil.abort();
		}

		if ( e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, oprtState);
			uiWindow.show();
			if (oprtState.equals(OprtState.VIEW))
				return;
			// �����ݵ���¼��
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
			setRowOccurAmount(rowIndex);
			// ���³�����
			setEntriesNameCol(rowIndex, level);
			// ���³�����
			Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldLongNumber != null && newLongNumber != null) {
				if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
					setEntriesNumberCol(rowIndex, level);
				}
			}
			// ���¹滮���,�滮���,�ɹ����ƽ��������Ļ���
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(BALANCE), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
			calcSquare(rowIndex);
			// �����ж�����ɫ
			setMyFontColor();
		}
	}

	/**
	 * ��ȡ�п�����к�Լ
	 * 
	 * @return
	 */
	private ProgrammingContractCollection getPCCollection() {
		ProgrammingContractCollection pcCollection = new ProgrammingContractCollection();
		ProgrammingContractInfo pcInfo = null;
		int columnCount = kdtEntries.getRowCount();
		for (int i = 0; i < columnCount; i++) {
			pcInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			setContractToEditData(i, pcInfo);
			pcCollection.add(pcInfo);
		}
		return pcCollection;
	}

	/**
	 * �����и��ĸ������ݡ������¼��ڵ����
	 * 
	 * @param rowIndex
	 * @param level
	 */
	private void setEntriesNumberCol(int rowIndex, int level) {
		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if (longNumber != null && longNumber.toString().trim().length() > 0) {
			String lnumber = longNumber.toString();
			if (level == 1) {
				kdtEntries.getCell(rowIndex, "number").setValue(lnumber);
			} else {
				String number = lnumber.substring(lnumber.lastIndexOf(".") + 1, lnumber.length());
				kdtEntries.getCell(rowIndex, "number").setValue(number);
			}
			for (int i = rowIndex + 1; i < kdtEntries.getRowCount(); i++) {
				Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
				Object longNumber_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}

				String[] editString = lnumber.split("\\.");
				if (longNumber_2 != null && longNumber_2.toString().trim().length() > 0) {
					String hNumber_2 = headNumber.toString();
					String lNumber_2 = longNumber_2.toString();
					String[] newL = lNumber_2.split("\\.");
					String[] newH = hNumber_2.split("\\.");
					for (int j = 0; j < editString.length; j++) {
						newL[j] = editString[j];
						newH[j] = editString[j];
					}
					StringBuffer str = new StringBuffer();
					for (int j = 0; j < newL.length; j++) {
						str.append(newL[j]).append(".");
					}
					if (newL.length < level_2)
						str.append(".");
					StringBuffer str2 = new StringBuffer();
					for (int j = 0; j < newH.length; j++) {
						str2.append(newH[j]).append(".");
					}
					setkdtEntriesNumber(i, str.substring(0, str.length() - 1), str2.substring(0, str2.length() - 1));
				}
			}
		}
	}

	/**
	 * ��������ʱ���á������ӽڵ㳤����
	 * 
	 * @param rowIndex
	 * @param level
	 */
	private void setEntriesNameCol(int rowIndex, int level) {
		Object name = getCellValue(kdtEntries, rowIndex, "name");
		// Object name = kdtEntries.getCell(rowIndex , "name").getValue();
		if (name != null && name.toString().trim().length() > 0) {
			String nameStr = name.toString().trim();
			String blank = setNameIndent(level);
			kdtEntries.getCell(rowIndex, "name").setValue(blank + nameStr);
			if (level == 1) {
				kdtEntries.getCell(rowIndex, "longName").setValue(nameStr);
			} else {
				Object lo = kdtEntries.getCell(rowIndex, "longName").getValue();
				String displayName = lo == null ? "" : lo.toString();
				int dotIndex = displayName.lastIndexOf(".");
				String ln = displayName.substring(0, dotIndex > 0 ? dotIndex : 0) + ".";
				kdtEntries.getCell(rowIndex, "longName").setValue(ln + nameStr);
			}

			Object lo = kdtEntries.getCell(rowIndex, "longName").getValue();
			String displayName = lo == null ? "" : lo.toString();
			if (level == 1) {
				displayName = displayName + ".";
			}
			String[] l = displayName.split("\\.");
			for (int i = rowIndex + 1; i < kdtEntries.getRowCount(); i++) {
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				Object l2 = kdtEntries.getCell(i, "longName").getValue();

				String l3[] = (l2 == null ? "" : l2).toString().split("\\.");
				for (int j = 0; j < l.length; j++) {
					if (l3[j] != null && l3[j].length() > 0) {
						l3[j] = l[j];
					}
				}
				StringBuffer str = new StringBuffer();
				for (int j = 0; j < l3.length; j++) {
					str.append(l3[j]).append(".");
				}
				Object n2 = getCellValue(kdtEntries, rowIndex, "name");
				// Object n2 = kdtEntries.getCell(i ,"name").getValue();
				if (n2 == null) {
					str.append(".");
				}
				kdtEntries.getCell(i, "longName").setValue(str.substring(0, str.length() - 1));
				displayName = null;
			}
		}
	}

	/**
	 * �޸��¼����롢�����¼��ر�����
	 * 
	 * @param i
	 * @param lnumber
	 * @param hNumber
	 */
	private void setkdtEntriesNumber(int i, String lnumber, String hNumber) {
		kdtEntries.getCell(i, HEADNUMBER).setValue(hNumber);
		kdtEntries.getCell(i, LONGNUMBER).setValue(lnumber);

		ICellEditor editor = kdtEntries.getCell(i, LONGNUMBER).getEditor();
		if (editor != null) {
			if (editor instanceof KDTDefaultCellEditor) {
				KDTDefaultCellEditor de = (KDTDefaultCellEditor) editor;
				KDTextField txtLongNumber = (KDTextField) de.getComponent();
				LimitedTextDocument doc = (LimitedTextDocument) txtLongNumber.getDocument();
				doc.setIsAutoUpdate(true);
				doc.setIsOnload(true);
				txtLongNumber.setText(lnumber);
				doc.setIsAutoUpdate(false);
				doc.setIsOnload(false);
			}
		}
	}

	/**
	 * Ϊ��¼���ñ༭��
	 */
	private void setCellEditorForTable() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			KDTextField txtLongNumber = new KDTextField();
			String txt = "";
			Object longNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
			Object subNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (longNumber == null || longNumber.toString().trim().length() == 0) {
				if (subNumber != null && subNumber.toString().trim().length() > 0) {
					txt = subNumber.toString().trim() + ".";
				}
			} else {
				txt = longNumber.toString().trim();
			}
			if (level > 1) {
				LimitedTextDocument document = new LimitedTextDocument(subNumber == null ? "" : subNumber.toString().trim() + ".", true);
				txtLongNumber.setMaxLength(80);
				txtLongNumber.setDocument(document);
				txtLongNumber.setText(txt);
				document.setIsOnload(false);
			} else {
				LimitedTextDocument document = new LimitedTextDocument("");
				txtLongNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtLongNumber.setText(txt);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}

			KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
			kdtEntries.getCell(i, LONGNUMBER).setEditor(cellEditorNumber);
			String name = getCellValue(kdtEntries, i, "name");
			// String name = (String) kdtEntries.getCell(i, "name").getValue();
			formatName(i);
			kdtEntries.getCell(i, "name").setValue(name);
		}
	}

	private void setTableFontColor() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			if (blanceValue != null) {
				BigDecimal blance = (BigDecimal) blanceValue;
				if (blance.compareTo(FDCHelper.ZERO) > 0) {
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.green);
				} else {
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
			if(kdtEntries.getCell(i,"sendPage").getValue() != null){
				if(kdtEntries.getCell(i,"hyType").getValue() != null){
					PcTypeInfo hytype = (PcTypeInfo)kdtEntries.getCell(i,"hyType").getValue();
					if(hytype.getSendContWay()!=null && hytype.getSendContWay().equals((SendContWay)kdtEntries.getCell(i,"sendPage").getValue()))
						continue;
				}
				if("ZJWT".equals(((SendContWay)kdtEntries.getCell(i,"sendPage").getValue()).getName())){
					kdtEntries.getCell(i,"sendPage").getStyleAttributes().setBackground(Color.red);
				}
			}
		}
	}

	/**
	 * ������ǰ��ӿո���ʾ����Ч��
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
	 * ���ú�Լ����ͷ��Ϣ
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
					rowObject.setLongNumber(subNumber.toString().trim() + ".");// ������
				}
			} else {
				rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// ������
			}
		} else {
			rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// ������
		}
		rowObject.setDisplayName((String) kdtEntries.getCell(rowIndex, "longName").getValue());// ������
		rowObject.setNumber((String) kdtEntries.getCell(rowIndex, "number").getValue());// ����
		rowObject.setName(getCellValue(kdtEntries, rowIndex, "name"));// ����
		// rowObject.setName((String) kdtEntries.getCell(rowIndex,
		// "name").getValue());// ����

		rowObject.setWorkContent((String) kdtEntries.getCell(rowIndex, "workContent").getValue());// ��������
		rowObject.setSupMaterial((String) kdtEntries.getCell(rowIndex, "supMaterial").getValue());// �׹�����ָ����
		rowObject.setInviteWay((InviteFormEnum) kdtEntries.getCell(rowIndex, "inviteWay").getValue());// �б귽ʽ
		if (!FDCHelper.isEmpty(kdtEntries.getCell(rowIndex, "inviteOrg").getValue())) {
			Object value = kdtEntries.getCell(rowIndex, "inviteOrg").getValue();
			rowObject.setInviteOrg((CostCenterOrgUnitInfo) value);// �б���֯
		}

		Object amount = kdtEntries.getCell(rowIndex, AMOUNT).getValue();
		if (!FDCHelper.isEmpty(amount)) {
			rowObject.setAmount(new BigDecimal(amount.toString()));// �滮���
		}
		Object controlAmount = kdtEntries.getCell(rowIndex, CONTROLAMOUNT).getValue();
		if (!FDCHelper.isEmpty(controlAmount)) {
			rowObject.setControlAmount(new BigDecimal(controlAmount.toString())); // �ɹ����ƽ��
		}
		Object signUpAmount = kdtEntries.getCell(rowIndex, "signUpAmount").getValue();
		if (!FDCHelper.isEmpty(signUpAmount)) {
			rowObject.setSignUpAmount(new BigDecimal(signUpAmount.toString())); // ǩԼ���
		}
		Object changeAmount = kdtEntries.getCell(rowIndex, "changeAmount").getValue();
		if (!FDCHelper.isEmpty(changeAmount)) {
			rowObject.setChangeAmount(new BigDecimal(changeAmount.toString())); // ������
		}
		Object settleAmount = kdtEntries.getCell(rowIndex, "settleAmount").getValue();
		if (!FDCHelper.isEmpty(settleAmount)) {
			rowObject.setSettleAmount(new BigDecimal(settleAmount.toString())); // ������
		}
		Object balance = kdtEntries.getCell(rowIndex, BALANCE).getValue();
		if (!FDCHelper.isEmpty(balance)) {
			rowObject.setBalance(new BigDecimal(balance.toString())); // �滮���
		}
		Object controlBalance = kdtEntries.getCell(rowIndex, CONTROL_BALANCE).getValue();
		if (!FDCHelper.isEmpty(controlBalance)) {
			rowObject.setControlBalance(new BigDecimal(controlBalance.toString())); // �������
		}
		Object citeVersionObj = kdtEntries.getCell(rowIndex, "citeVersion").getValue();
		if (!FDCHelper.isEmpty(citeVersionObj)) {
			rowObject.setCiteVersion(new Integer(kdtEntries.getCell(rowIndex, "citeVersion").getValue().toString()).intValue());// ���ð汾
		}

		rowObject.setDescription((String) kdtEntries.getCell(rowIndex, "remark").getValue());// ��ע
		
		rowObject.setReservedChangeRate(FDCHelper.toBigDecimal(kdtEntries.getCell(rowIndex, "reservedChangeRate").getValue()));
		
		rowObject.setProgramming(editData);
	}

	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	public void inputVersion(ProgrammingInfo info) {
		if (info == null) {
			return;
		}
		if (info.getVersion() == null) {
			info.setVersion(new BigDecimal("1.0"));
		} else {
			BigDecimal version = info.getVersion();
			editData.setVersion(version.add(new BigDecimal("1.0")));
		}
	}

	/**
	 * ����ǰ�Ѽ�����"����Լ����"���� "������"���
	 */
	private void verifyRedData() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				BigDecimal contractAssign = pccInfo.getContractAssign();
				BigDecimal assigning = pccInfo.getAssigning();
				// if (contractAssign.compareTo(assigning) > 0) {	// NullPointExcetpion
				if (FDCHelper.compareTo(contractAssign, assigning) > 0) {	// modified by zhaoqin on 2013/11/08
					Color fontColor = kdtEntries.getRow(i).getStyleAttributes().getFontColor();
					Color red = new Color(255, 0, 0);
					if (fontColor.equals(red)) {
						FDCMsgBox.showInfo("�ɱ������д���\"����Լ����\"���� \"������\"�������ύ��");
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * �ж��ѹ滮����Ƿ������Ŀ��ɱ����
	 * 
	 * @return ���ڷ���true
	 */
	private boolean verifyAmountVSAimCost() {
		kdtEntries.getEditManager().editingStopped();
		Object aimCostAccountTotalObj = txtAllAimCost.getNumberValue();
		BigDecimal aimCostAccountTotal = FDCHelper.ZERO;// ��Ŀ��ɱ�
		BigDecimal amountTotal = FDCHelper.ZERO;// �ܹ滮���
		if (!FDCHelper.isEmpty(aimCostAccountTotalObj)) {
			aimCostAccountTotal = FDCHelper.toBigDecimal(aimCostAccountTotalObj);
		}
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level == 1) {
				Object amountSingleObj = kdtEntries.getCell(i, "amount").getValue();
				BigDecimal amountSingle = FDCHelper.ZERO;
				if (amountSingleObj != null) {
					amountSingle = FDCHelper.toBigDecimal(amountSingleObj);
				}
				amountTotal = amountTotal.add(amountSingle);
			}
		}
		// �ѹ滮��������Ŀ��ɱ���� return true
		if (amountTotal.compareTo(aimCostAccountTotal) > 0) {
			return true;
		}
		return false;
	}

	private void verifyControlParam() {
		if (prmtAimCost.getData() != null) {
			ObjectUuidPK pk = new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId());
			String param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC229_ISSTRICTCONTROL", pk);
			if (!com.kingdee.util.StringUtils.isEmpty(param)) {
				int i = Integer.parseInt(param);
				switch (i) {
				case 0:
					// ������
					break;
				case 1:
					// ��ʾ����
					if (verifyAmountVSAimCost()) {
						int isSubmit = FDCMsgBox.showConfirm2("�ܹ滮��������Ŀ��ɱ����Ƿ�Ҫ�ύ?");
						if (FDCMsgBox.OK == isSubmit) {
							break;
						} else {
							SysUtil.abort();
						}
					}
				case 2:
					// �ϸ����
					if (verifyAmountVSAimCost()) {
						FDCMsgBox.showWarning(this, "�ܹ滮��������Ŀ��ɱ����������ύ");
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * ��Լ�滮����
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
//		if(prmtYjPerson.getValue() == null)
//			throw new EASBizException(new NumericExceptionSubItem("1", "Ԥ����Ա����Ϊ�գ�"));
//		if(txtYjDays.getIntegerValue() <= 0)
//			throw new EASBizException(new NumericExceptionSubItem("1", "Ԥ������Ӧ�����㣡"));
		veryfyForSave();
		verifyDataBySave();
		editData.setState(FDCBillStateEnum.SAVED); // modified by zhaoqin for R130819-0171 on 2013/09/11
		fillFxbdByHyType();
		super.actionSave_actionPerformed(e);
		sumClass.appendProFootRow(null, null);
		setNameDisplay();
		switchButtonEnabled();
		
		setkdtEntries_AmountState(); // modified by zhaoqin on 2013/10/17
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/15 */
		setAimCostVersionMsg();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		
		int rowCount = kdtEntries.getRowCount();
		
		if ("1".equals(paramIsstrictcontrol)) { // ��ʾ����
			for (int i = 0; i < rowCount; i++) {
				IRow row = kdtEntries.getRow(i);
				if (FDCHelper.compareTo(row.getCell("controlAmount").getValue(), row.getCell("occurAmount").getValue()) < 0) {
					FDCMsgBox.showWarning("��" + (i + 1) + "�к�Լ���ƽ���С���ѷ������");
				}
			}
		} else if ("0".equals(paramIsstrictcontrol)) { // �ϸ����
			for (int i = 0; i < rowCount; i++) {
				IRow row = kdtEntries.getRow(i);
				if (FDCHelper.compareTo(row.getCell("controlAmount").getValue(), row.getCell("occurAmount").getValue()) < 0) {
					FDCMsgBox.showWarning("��" + (i + 1) + "�к�Լ���ƽ���С���ѷ������");
					abort();
				}
			}
		}
		// Ϊ2��ʱ����ʾ��
		
	}

	/**
	 * ����У��
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void veryfyForSave() throws EASBizException, BOSException {
		String name_txt = txtName.getText();
		String errrMsg = "��Լ��ܰ汾����";
		if (name_txt == null || name_txt.trim().equals("")) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + "����Ϊ�գ�"));
		}
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "������Ŀ����Ϊ��"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "�汾�Ų���Ϊ��"));
		}
		
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name_txt, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingFactory.getRemoteInstance().exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + name_txt + "�Ѿ����ڣ������ظ���"));
		}
		// ����ʱȥ����¼���Ƶ�ǰ��ո�
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = getCellValue(kdtEntries, i, "name");
			// Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}

			row.getCell("sortNumber").setValue(new Integer(i));
		}

		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
	}
	
	//��������ύʱ�����ݺ�Լ������丱�����(û��ȥ��ϸ�༭�����ҳǩ��ֱ�ӱ�������)
	public void fillFxbdByHyType() throws Exception{
		// modify by yxl 20150908
		ProgrammingContractCollection entries = editData.getEntries();
		if (entries != null && entries.size() > 0) {
			ProgrammingContractInfo pcinfo = null;
			ProgrammingContractFxbdEntryCollection fxbdcoll = null;
			ProgrammingContractFxbdEntryInfo fxbdinfo = null;
			String hyTypeId = null;
			Map<String,Date> ckDates = new HashMap<String,Date>();
			Calendar c1 = Calendar.getInstance();
			IPcTypeEntry ipt = PcTypeEntryFactory.getRemoteInstance();
			PcTypeEntryCollection pcoll = null;
			PcTypeEntryInfo pcentryinfo = null;
			for (int i = 0; i < entries.size(); i++) {
				pcinfo = entries.get(i);
				fxbdcoll = pcinfo.getFxbdEntry();
				if(pcinfo.getHyType() == null || fxbdcoll.size() > 0)
					continue;
				ckDates.put("SGT",pcinfo.getSgtDate());
				ckDates.put("CSD",pcinfo.getContSignDate());
				ckDates.put("SWD",pcinfo.getStartDate());
				ckDates.put("EWD",pcinfo.getEndDate());
				ckDates.put("CSED",pcinfo.getCsendDate());
				hyTypeId = pcinfo.getHyType().getId().toString();
				pcoll=ipt.getPcTypeEntryCollection("select depType.id,depType.number,depType.name,fieldName,ckDate,tqDays,recordSeq where parent.id='"+hyTypeId+"'");
				for (int j = pcoll.size()-1; j >= 0; j--) {
					pcentryinfo = pcoll.get(j);
					fxbdinfo = new ProgrammingContractFxbdEntryInfo();
					fxbdinfo.setRecordSeq(pcentryinfo.getRecordSeq());
					fxbdinfo.setDepType(pcentryinfo.getDepType());
					fxbdinfo.setItemName(pcentryinfo.getFieldName());
					if(pcentryinfo.getCkDate() != null && ckDates.get(pcentryinfo.getCkDate().getName()) != null){
						c1.setTime(ckDates.get(pcentryinfo.getCkDate().getName()));
						c1.add(Calendar.DATE,-pcentryinfo.getTqDays());
						fxbdinfo.setPlanDate(c1.getTime());
					}
					fxbdcoll.add(fxbdinfo);
				}
				ckDates.clear();
			}
		}
	}

	//�����ʱ��ı䣬������ű仯
	public void fxbdChangeByMain(int rowIndex,String ckdate, Date realDate) throws Exception{
		ProgrammingContractInfo	pcinfo = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
		ProgrammingContractFxbdEntryCollection fxcoll = pcinfo.getFxbdEntry();
		if(fxcoll.size() > 0 && pcinfo.getHyType() != null){
			PcTypeEntryCollection pcoll = null;
			IPcTypeEntry ipt = PcTypeEntryFactory.getRemoteInstance();
			Calendar c1 = Calendar.getInstance();
			pcoll=ipt.getPcTypeEntryCollection("select fieldName,ckDate,tqDays,recordSeq where parent.id='"+pcinfo.getHyType().getId().toString()+"'");
			PcTypeEntryInfo einfo = null;
			for (int j = pcoll.size()-1; j >=0; j--) {
				einfo = pcoll.get(j);
				if(einfo.getCkDate()!=null && ckdate.equals(einfo.getCkDate().getName())){
					for (int i = fxcoll.size()-1; i >=0; i--) {
						if(fxcoll.get(i).getRecordSeq().equals(einfo.getRecordSeq())){
							if(realDate == null)
								fxcoll.get(i).setPlanDate(null);
							else{
								c1.setTime(realDate);
								c1.add(Calendar.DATE,-einfo.getTqDays());
								fxcoll.get(i).setPlanDate(c1.getTime());
							}
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		if(prmtYjPerson.getValue() == null)
//			throw new EASBizException(new NumericExceptionSubItem("1", "Ԥ����Ա����Ϊ�գ�"));
		if(txtYjDays.getIntegerValue() <= 0)
			throw new EASBizException(new NumericExceptionSubItem("1", "Ԥ������Ӧ�����㣡"));
		verifyControlParam();
		verifyRedData();
		verifyDataBySave();
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "������Ŀ����Ϊ��"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "�汾�Ų���Ϊ��"));
		}
		// ����ʱȥ����¼���Ƶ�ǰ��ո�
		int rowCount = kdtEntries.getRowCount();
		if (rowCount == 0) {
			MsgBox.showWarning("û�м�¼���������ύ");
			SysUtil.abort();
		}
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = getCellValue(kdtEntries, i, "name");
			// Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}
			row.getCell("sortNumber").setValue(new Integer(i));
		}
		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
		String curVersion = txtVersion.getText();
		String versionGroup = txtVersionGroup.getText();
		fillFxbdByHyType();
		super.actionSubmit_actionPerformed(e);
		if (isBillModify()) {
			getServiceInterface2().billModify(versionGroup, curVersion); // ά�����°汾�ֶ�
			// ProgrammingListUI parentUI = (ProgrammingListUI)
			// getUIContext().get("parent");
			// parentUI.refreshList();
			this.getUIContext().remove(IS_MODIFY);
		}
		setAuditBtnEnable();
		sumClass.appendProFootRow(null, null);
		
		setkdtEntries_AmountState(); // modified by zhaoqin on 2013/10/17
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/15 */
		setAimCostVersionMsg();
	}

	// �Ƿ��޶�
	private boolean isBillModify() {
		//		Boolean isSet = (Boolean) getUIContext().get(IS_MODIFY);
		//		return isSet != null && isSet.booleanValue();
		return getUIContext().containsKey(IS_MODIFY);
	}

	// �ɹ����ƽ��ܴ��ڹ滮���
	// private void checkAmount(int rowIndex) throws EASBizException {
	// if (rowIndex < 0) {
	// return;
	// }
	//		
	// IRow row = kdtEntries.getRow(rowIndex);
	// Object cellValue = row.getCell("amount").getValue();
	// BigDecimal amount = checkNumber(cellValue, rowIndex, "amount", 0); //
	// �滮���
	//		
	//
	// cellValue = row.getCell("controlAmount").getValue();
	// BigDecimal ctrlAmount = checkNumber(cellValue, rowIndex, "controlAmount",
	// 0); // �ɹ����ƽ��
	//		
	// if (ctrlAmount.compareTo(amount) > 0) {
	// StringBuffer msg = new StringBuffer();
	// msg.append("�� ").append(rowIndex + 1).append("�� �ɹ����ƽ��ܴ��ڹ滮���");
	// throw new EASBizException(new NumericExceptionSubItem("1",
	// msg.toString()));
	// }
	// }

	/**
	 * �ύǰУ�����ݣ����ơ���¼���뼰����
	 * 
	 * @throws Exception
	 */
	public void verifyDataBySave() throws Exception {
		
		Map contractScale = new HashMap();
		//		ProgrammingContracCostCollection proCostCol = new ProgrammingContracCostCollection();
		ProgrammingContractCollection entries = editData.getEntries();
		ProgrammingContractInfo programmingContractInfo = null;
		if (entries != null && entries.size() > 0) {
			for (int i = 0; i < entries.size(); i++) {
				programmingContractInfo = entries.get(i);
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();

				if (costEntries != null && costEntries.size() > 0) {
					for (int j = 0; j < costEntries.size(); j++) {
						ProgrammingContracCostInfo costInfo = costEntries.get(j);

						// modified by zhaoqin for R130718-0266 on 2013/07/25 start
						BigDecimal aimCost = costInfo.getGoalCost();
						String assignedName = costInfo.getCostAccount().getId().toString() + costInfo.getCostAccount().getName();

						if (costInfo.getCostAccount() != null && contractScale.get(costInfo.getCostAccount().getId().toString()) != null) {
							BigDecimal scale = FDCHelper.add(contractScale.get(costInfo.getCostAccount().getId().toString()), costInfo
									.getContractScale());
							BigDecimal assigned = FDCHelper.add(contractScale.get(assignedName), costInfo.getContractAssign());
							if (scale.compareTo(FDCHelper.ONE_HUNDRED) == 1 && assigned.compareTo(aimCost) > 0) {
								throw new EASBizException(new NumericExceptionSubItem("1", "�ɱ���Ŀ" + costInfo.getCostAccount().getName()
										+ "����Լ�����������100%�����޸�"));
							}
							contractScale.put(costInfo.getCostAccount().getId().toString(), scale);
							contractScale.put(assignedName, assigned);
						} else if (costInfo.getCostAccount() != null) {
							BigDecimal scale = FDCHelper.toBigDecimal(costInfo.getContractScale());
							BigDecimal assigned = costInfo.getContractAssign();
							// if (scale.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
							if (scale.compareTo(FDCHelper.ONE_HUNDRED) == 1 && assigned.compareTo(aimCost) > 0) {
								throw new EASBizException(new NumericExceptionSubItem("1", "�ɱ���Ŀ " + costInfo.getCostAccount().getName()
										+ "����Լ�����������100%�����޸�"));
							}
							contractScale.put(costInfo.getCostAccount().getId().toString(), scale);
							contractScale.put(assignedName, assigned);
							// modified by zhaoqin for R130718-0266 on 2013/07/25 start
						}
					}
				}
			}
		}

		int rowCount = kdtEntries.getRowCount();
		String name_txt = txtName.getText();
		String errrMsg = "��Լ��ܰ汾����";
		if (name_txt == null || name_txt.trim().equals("")) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + "����Ϊ�գ�"));
		}

		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name_txt, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingFactory.getRemoteInstance().exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + name_txt + "�Ѿ����ڣ������ظ���"));
		}

		for (int i = 0; i < rowCount; i++) {
			// checkAmount(i);

			Object number = kdtEntries.getCell(i, "longNumber").getValue();
			Object head = kdtEntries.getCell(i, HEADNUMBER).getValue();
			if (number == null || number.toString().trim() == null) {
				throw new ProgrammingException(ProgrammingException.NUMBER_NULL, new Object[] { new Integer(i + 1) });
			}

			Object level = kdtEntries.getCell(i, "level").getValue();
			int level_int = new Integer(level.toString()).intValue();
			if (level_int != 1) {
				String ln = number.toString();
				if (ln.length() == (head.toString().length() + 1)) {
					throw new ProgrammingException(ProgrammingException.NUMBER_NULL, new Object[] { new Integer(i + 1) });
				}
			}

			String longNumber = number.toString().trim();
			if (longNumber.length() > 80) {
				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��" + (i + 1) + "�У����볬�������������룡"));
			}

			Object proName = getCellValue(kdtEntries, i, "name");
			// Object proName = kdtEntries.getCell(i, "name").getValue();
			if (proName == null || proName.toString().trim().equals("")) {
				throw new ProgrammingException(ProgrammingException.NAME_NULL, new Object[] { new Integer(i + 1) });
			}

			if (proName != null && proName.toString().trim().length() > 80) {
				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��" + (i + 1) + "�У���ܺ�Լ���Ƴ�����"));
			}

			Object longName = kdtEntries.getCell(i, "longName").getValue();
			if (longName != null && !StringUtils.isEmpty(longName.toString())) {
				if (longName.toString().length() > 255) {
					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��" + (i + 1) + "�У���ܺ�Լ�����Ƴ���\n���޸Ŀ�ܺ�Լ�������ݣ�"));
				}
			}
			//modify by yxl 20150914 У���Լ���ͺ͹滮����Ϊ��
//			if(kdtEntries.getCell(i, "hyType").getValue() == null)
//				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��" + (i + 1) + "�У���Լ���Ͳ���Ϊ�գ�"));
//			if(FDCHelper.isEmpty(kdtEntries.getCell(i,"amount").getValue(),false))
//				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��" + (i + 1) + "�У��滮����Ϊ�ջ��㣡"));
			
			String lnumber = number.toString();
			String name = proName.toString().trim();

			for (int j = 0; j < rowCount; j++) {
				if (j == i)
					continue;

				Object number_2 = kdtEntries.getCell(j, "longNumber").getValue();
				// Object proName_2 = kdtEntries.getCell(j, "name").getValue();
				Object proName_2 = getCellValue(kdtEntries, j, "name");

				if (number_2 != null && number_2.toString().trim().length() > 0) {
					if (lnumber.equals(number_2.toString().trim())) {
						throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT, new Object[] { new Integer(i + 1),
								new Integer(j + 1), "����" });
					}
				}

				if (proName_2 != null && proName_2.toString().trim().length() > 0) {
					if (name.equals(proName_2.toString().trim())) {
						throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT, new Object[] { new Integer(i + 1),
								new Integer(j + 1), "����" });
					}
				}
			}

			Object controlAmount = kdtEntries.getCell(i, "controlAmount").getValue();
			Object amount = kdtEntries.getCell(i, "amount").getValue();
			if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.toBigDecimal(controlAmount)) == -1) {
				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��" + (i + 1) + "�У��ɹ����ƽ��ô��ڹ滮���\n���޸Ŀ�ܺ�Լ�������ݣ�"));
			}

		}
	}

	/**
	 * ��ȡ��ǰʱ���ַ������������ñ���
	 */
	private String getDateString() {
		Calendar cal = Calendar.getInstance();
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		Date bizDate = new Date(ts.getTime());
		return bizDate.toString();
	}

	/**
	 * ������������ʾ����Ч������ǰ��ӿո�
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

	// ���ǵ�����ķ���
	protected void setAuditButtonStatus(String oprtType) {
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (!isSelectedProjectNode()) {
			MsgBox.showWarning("��ѡ�񹤳���Ŀ����");
			return;
		}
		if (hasHistoryVersion()) {// �оͱ༭
			/*if (isBillStateEquals(FDCBillStateEnum.SUBMITTED)) {// TODO dcwei
				MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ��");
				return;
			}*/
			checkHighVersion2();
			super.actionEdit_actionPerformed(e);
			
			// modified by zhaoqin on 2013/10/22
			// updateProgrammingContract();
			updateProgrammingContract1(this.editData.getId().toString());
			switchButtonEnabled();
			updateSchduleTime.setEnabled(true);
		} else {// û�о�����
			setOprtState(OprtState.ADDNEW);
			ProgrammingInfo newData = (ProgrammingInfo) createNewData();
			newData.setNumber(getDateString());
			DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
			if (treeNode != null && treeNode.getUserObject() instanceof CurProjectInfo) {
				// ���ù���
				newData.setProject((CurProjectInfo) treeNode.getUserObject());
				newData.setName(newData.getProject().getName() + "_��Լ�滮_" + newData.getNumber());
				StringBuffer oql = new StringBuffer(256);
				// modified by zhaoqin on 2013/10/28, ȡ���°汾��Ŀ��ɱ� isLastVersion=1
				oql.append("where orgOrProId='").append(newData.getProject().getId().toString())
					.append("' and isLastVersion=1 order by versionNumber desc");
				AimCostCollection aimCostCollection = AimCostFactory.getRemoteInstance().getAimCostCollection(oql.toString());
				if (aimCostCollection.size() > 0) {
					newData.setAimCost(aimCostCollection.get(0));
				}
			}
			setDataObject(newData);
			this.loadFields();
			txtName.setText(getDisplayName());
		}
		setSmallBtnEnable();
		setCellEditorForTable();
		setNameDisplay();
		handleOldData();
		
		// modified by zhaoqin for R130819-0171 on 2013/09/11
		activeSaveAction();
		
		setkdtEntries_AmountState(); // modified by zhaoqin on 2013/10/17
	}

	/**
	 * ���ɱ�Ŀ���Ƿ����°汾 ����ȷ�ϸ���
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void updateProgrammingContract() throws BOSException, EASBizException {
		AimCostInfo latestAimCost = getGreaterCurrentVersionAimCostInfo();
		if (latestAimCost == null)
			return;
		if (MsgBox.showConfirm2("Ŀ��ɱ����°汾���Ƿ���Ҫ���º�Լ�滮ֵ��") != MsgBox.YES)
			return;
		ProgrammingContractCollection entries = getKdtEntriesUserObjects();
		SelectorItemCollection selecter = new SelectorItemCollection();
		selecter.add("*");
		selecter.add("costEntry.*");
		selecter.add("costEntry.costAccount.*");
		selecter.add("costEntry.costAccount.curProject.*");
		IAimCost service = AimCostFactory.getRemoteInstance();
		latestAimCost = service.getAimCostInfo(new ObjectUuidPK(latestAimCost.getId()), selecter);//���°汾Ŀ��ɱ�

		AimCostInfo currentAimCost = editData.getAimCost();
		currentAimCost = service.getAimCostInfo(new ObjectUuidPK(currentAimCost.getId()), selecter);//��ǰ�汾��ӦĿ��ɱ�

		for (int i = 0; i < entries.size(); i++) {
			updateProgrammingContractCost(entries.get(i), latestAimCost.getCostEntry(), currentAimCost.getCostEntry());
		}
		editData.setAimCost(latestAimCost);
		// ��Ӱ���ܺ�Լ�Ժ�ɫ������ʾ
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			if (pcInfo.containsKey(AMOUNT_CHANGE_FLAG)) {
				kdtEntries.getCell(i, AMOUNT).getStyleAttributes().setFontColor(Color.red);
				kdtEntries.getCell(i, AMOUNT).setValue(pcInfo.getAmount());
				kdtEntries.getCell(i, CONTROLAMOUNT).setValue(pcInfo.getControlAmount());
				BigDecimal occurAmount = FDCHelper.ZERO;
				Object occurAmountObj = kdtEntries.getCell(i, OCCUR_AMOUNT).getValue();
				if (occurAmountObj != null) {
					occurAmount = (BigDecimal) occurAmountObj;
				}
				kdtEntries.getCell(i, BALANCE).setValue(pcInfo.getAmount().subtract(occurAmount));
				kdtEntries.getCell(i, CONTROL_BALANCE).setValue(pcInfo.getControlAmount().subtract(occurAmount));
			}
			Object value = kdtEntries.getCell(i, "level").getValue();
			int level = 1;
			if (value != null) {
				level = new Integer(value.toString()).intValue();
			}
			caclTotalAmount(i, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex("controlAmount"), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex(BALANCE), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex("buildPerSquare"), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex("soldPerSquare"), level);
		}
		prmtAimCost.removeDataChangeListener(dataChangeListener);
		prmtAimCost.setValue(latestAimCost);
		prmtAimCost.addDataChangeListener(dataChangeListener);

	}

	private void checkHighVersion2() throws Exception {
		/* modified by zhaoqin for R140507-0295 on 2014/05/14 start */
		//BigDecimal version = editData.getVersion().add(FDCHelper.ONE);
		BigDecimal version = editData.getVersion();
		String versionGroup = editData.getVersionGroup();
		//String oql = "where version = '".concat(version.toString()).concat("' and versionGroup = '").concat(versionGroup).concat("'");
		String oql = "where version > ".concat(version.toString()).concat(" and versionGroup = '").concat(versionGroup).concat("'");
		/* modified by zhaoqin for R140507-0295 on 2014/05/14 end */
		
		if (getBizInterface().exists(oql)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "���ڸ��߰汾���ܽ��д˲���"));
		}
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getState() == null) {
			FDCMsgBox.showInfo("����״̬���ݲ�����������");
			SysUtil.abort();
		}
		if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
			FDCMsgBox.showInfo("����״̬���ݲ�����������");
			SysUtil.abort();
		}
		if (!FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				ProgrammingFactory.getRemoteInstance().audit(editData.getId());
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
				actionEdit.setEnabled(false);
				//editData.setState(FDCBillStateEnum.AUDITTED);
				MsgBox.showInfo("�����ɹ���");
				kdtEntries.getColumn("iscse").getStyleAttributes().setHided(false);
			}
		}
		handleOldData();
		setOprtState(OprtState.VIEW);
		refreshCurrentData();
		switchButtonEnabled();
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				boolean isLastVersion = ProgrammingFactory.getRemoteInstance().isLastVersion(new ObjectUuidPK(editData.getId().toString()));
				if (!isLastVersion) {
					throw new EASBizException(new NumericExceptionSubItem("1", "�����°汾���ܷ�����"));
				}
				checkHighVersion2();

				ProgrammingFactory.getRemoteInstance().unAudit(editData.getId());
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
				if (this.getOprtState().equals(STATUS_VIEW))
					actionEdit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				MsgBox.showInfo("�������ɹ���");
				kdtEntries.getColumn("iscse").getStyleAttributes().setHided(true);
			}
		}
		handleOldData();
		setOprtState(OprtState.VIEW);
		refreshCurrentData();
		switchButtonEnabled();
	}

	/**
	 * ȫ���������������¼��ʱ����¼��Ҫʧȥ����
	 */
	private void setMouseClick() {
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntries)) {
					setKDTableLostFocus();
				}
			}
		});

		Component[] com = this.getComponents();
		for (int i = 0; i < com.length; i++) {
			if (!com[i].equals(kdtEntries)) {
				com[i].addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent arg0) {
					}

					public void mouseEntered(MouseEvent arg0) {
					}

					public void mouseExited(MouseEvent arg0) {
					}

					public void mousePressed(MouseEvent arg0) {
					}

					public void mouseReleased(MouseEvent arg0) {
						if (!arg0.getComponent().equals(kdtEntries)) {
							setKDTableLostFocus();
						}
					}
				});
			}
		}

		this.txtName.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntries)) {
					setKDTableLostFocus();
				}
			}
		});

		this.txtProjectName.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntries)) {
					setKDTableLostFocus();
				}
			}
		});

		this.txtVersion.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntries)) {
					setKDTableLostFocus();
				}
			}
		});

		this.txtAimCoustVersion.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntries)) {
					setKDTableLostFocus();
				}
			}
		});
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
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
		sic.add(new SelectorItemInfo("entries.parent.*"));
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
		sic.add(new SelectorItemInfo("entries.parent.id"));
		sic.add(new SelectorItemInfo("entries.sortNumber"));
		sic.add(new SelectorItemInfo("entries.displayName"));
		sic.add(new SelectorItemInfo("entries.workcontent"));
		sic.add(new SelectorItemInfo("entries.supMaterial"));
		sic.add(new SelectorItemInfo("entries.inviteWay"));
		sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		sic.add(new SelectorItemInfo("entries.buildPerSquare"));
		sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		sic.add(new SelectorItemInfo("entries.reservedChangeRate"));
		sic.add(new SelectorItemInfo("entries.baseId"));

		// /////////////////////////////////////////////////////////////////////
		// start�����Ž�������Լ�滮����ϸ���ݼ��б�������4���ֶΡ�������ʽ��������Ͷ�귽ʽ������Ԥ�Ʒ�����ʼʱ�䡱����Ԥ�Ʒ�������ʱ�䡱���ĸ��ֶ�
		// by skyiter_wang 20131009
		// /////////////////////////////////////////////////////////////////////

		sic.add(new SelectorItemInfo("entries.estimateAwardStartDate"));
		sic.add(new SelectorItemInfo("entries.estimateAwardEndDate"));
		sic.add(new SelectorItemInfo("entries.inviteMode.id"));
		sic.add(new SelectorItemInfo("entries.inviteMode.number"));
		sic.add(new SelectorItemInfo("entries.inviteMode.name"));
		sic.add(new SelectorItemInfo("entries.jobType.id"));
		sic.add(new SelectorItemInfo("entries.jobType.number"));
		sic.add(new SelectorItemInfo("entries.jobType.name"));
		// yxl
		sic.add(new SelectorItemInfo("entries.hyType.id"));
		sic.add(new SelectorItemInfo("entries.hyType.hyType"));
		sic.add(new SelectorItemInfo("entries.hyType.sendContWay"));
		sic.add(new SelectorItemInfo("entries.iscse"));
		sic.add(new SelectorItemInfo("entries.contractContUI"));
		sic.add(new SelectorItemInfo("entries.attachWork"));
		sic.add(new SelectorItemInfo("entries.attContract"));
		sic.add(new SelectorItemInfo("yjCost.id"));
    	sic.add(new SelectorItemInfo("yjCost.number"));
    	sic.add(new SelectorItemInfo("yjCost.name"));
    	sic.add(new SelectorItemInfo("yjProject.id"));
    	sic.add(new SelectorItemInfo("yjProject.number"));
    	sic.add(new SelectorItemInfo("yjProject.name"));
    	sic.add(new SelectorItemInfo("yjDesign.id"));
    	sic.add(new SelectorItemInfo("yjDesign.number"));
    	sic.add(new SelectorItemInfo("yjDesign.name"));
    	sic.add(new SelectorItemInfo("yjMaterial.id"));
    	sic.add(new SelectorItemInfo("yjMaterial.number"));
    	sic.add(new SelectorItemInfo("yjMaterial.name"));
    	sic.add(new SelectorItemInfo("yjDays"));
    	sic.add(new SelectorItemInfo("entries.FxbdEntry.id"));
    	sic.add(new SelectorItemInfo("entries.FxbdEntry.recordSeq"));
    	sic.add(new SelectorItemInfo("entries.FxbdEntry.itemName"));
    	sic.add(new SelectorItemInfo("entries.FxbdEntry.planDate"));
    	sic.add(new SelectorItemInfo("entries.FxbdEntry.isYj"));

		// /////////////////////////////////////////////////////////////////////
		// end
		// /////////////////////////////////////////////////////////////////////

		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("versionGroup"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		
		return sic;
	}

	/**
	 * output createNewData method
	 */
	protected IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.programming.ProgrammingInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setVersion(new BigDecimal("1.0"));
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setVersionGroup(Uuid.create().toString());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));

		return objectValue;
	}

	protected void setTableToSumField() {
		// modify by lihaiou 2013.7.25, for bug R130718-0286
		//		HashMap sumFields = getSumFields();
		//		if (sumFields == null)
		//			return;
		//		// ȡ�����е�KDTable���ϼ�����Ϣ
		//		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		//		if (sumFieldsIterator.hasNext()) {
		//			KDTable table = (KDTable) sumFieldsIterator.next();
		//			String[] sumColNames = (String[]) sumFields.get(table);
		//			super.setTableToSumField(table, sumColNames);
		//		}
		// modify end
		super.setTableToSumField();
	}

	protected HashMap getSumFields() {
		HashMap sumFields = new HashMap();
		// Ŀ��ɱ�ҳǩ�ϼ���
		sumFields.put(this.kdtCostAccount, new String[] { AIM_COST, ASSIGNED, ASSIGNING });
		return sumFields;
	}

	/**
	 * 
	 * ������ fix bug R130718-0286
	 * @Author��haiou_li
	 * @CreateTime��2013-7-25
	 */
	private void setAccountsTableToSumField() {
		HashMap sumFields = getSumFields();
		if (sumFields == null)
			return;
		// ȡ�����е�KDTable���ϼ�����Ϣ
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[]) sumFields.get(table);
			sumClass.setProTableToSumField(table, sumColNames);
		}
	}

	private void setProTableToSumField() {
		HashMap sumFields = getProSumFields();
		if (sumFields == null)
			return;
		// ȡ�����е�KDTable���ϼ�����Ϣ
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[]) sumFields.get(table);
			sumClass.setProTableToSumField(table, sumColNames);
		}
	}

	private HashMap getProSumFields() {
		HashMap sumFields = new HashMap();
		sumFields.put(this.kdtEntries, new String[] { "amount", "controlAmount", "signUpAmount", "changeAmount", "settleAmount", BALANCE,
				OCCUR_AMOUNT, CONTROL_BALANCE, "buildPerSquare", "soldPerSquare" });
		return sumFields;
	}

	protected void handleOldData() {
		if (!(getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}

	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		if (kDTabbedPane1.getSelectedIndex() == 1) {
			// �鿴ʱ����ÿ������ˢ������
			boolean flag = OprtState.EDIT.equals(this.getOprtState()) || isRewLoadCostAccountTab;

			/* modified by zhaoqin for R131127-0394 on 2014/05/12 start */
			if(!checkHasAimCost()) {
				kdtCostAccount.removeRows();
				MsgBox.showWarning(this, "�ù�����Ŀ��û��Ŀ��ɱ�!");
				return;
			}
			/* modified by zhaoqin for R131127-0394 on 2014/05/12 end */
			
			if (flag) {
				loadCostAccountToCostEntry();
				isRewLoadCostAccountTab = false;
			}
		}else if (kDTabbedPane1.getSelectedIndex() == 2) {
			// �鿴ʱ����ÿ������ˢ������  modify by yxl 20150824
			boolean flag = OprtState.EDIT.equals(getOprtState()) || isReloadFxbdTab;
			CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
			if(null == project || null == project.getId())
				return;
			if (flag) {
				kdfxbd.removeRows();
				IRow row = null;
				IRowSet rsfxbd = null;
				String hyTypeId = null;
				FDCSQLBuilder builder = new FDCSQLBuilder();
				PcTypeEntryCollection pcoll = null;
				String depNumber = null;
				PcTypeEntryInfo einfo = null;
				ProgrammingContractInfo pcinfo = null;
				ProgrammingContractFxbdEntryCollection fxcoll = null;
				ProgrammingContractFxbdEntryInfo feinfo = null;
				Map<String,ProgrammingContractFxbdEntryInfo> fxentrys = new HashMap<String,ProgrammingContractFxbdEntryInfo>();
				Map<String,PcTypeEntryInfo> ptentrys = new HashMap<String,PcTypeEntryInfo>();
				Map<String,Date> ckDates = new HashMap<String,Date>();
				IPcTypeEntry ipt = PcTypeEntryFactory.getRemoteInstance();
				Calendar c1 = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Color bgc = new Color(227,227,227);
				int rowFlag = 1;
				Object pcname = null;
				String htrange = null;
				for (int i = 0; i < kdtEntries.getRowCount(); i++) {
					pcinfo = (ProgrammingContractInfo)kdtEntries.getRow(i).getUserObject();
					fxcoll = pcinfo.getFxbdEntry();
					ckDates.put("SGT",pcinfo.getSgtDate());
					ckDates.put("CSD",pcinfo.getContSignDate());
					ckDates.put("SWD",pcinfo.getStartDate());
					ckDates.put("EWD",pcinfo.getEndDate());
					ckDates.put("CSED",pcinfo.getCsendDate());
					pcname = kdtEntries.getCell(i,"name").getValue();
					htrange = pcinfo.getContractRange();
					if(pcinfo.getHyType() == null){
						row = kdfxbd.addRow();
						if(pcname instanceof CellTreeNode)
							row.getCell("name").setValue(pcname);
						else if(pcname!=null && ((String)pcname).trim().length()>0)
								row.getCell("name").setValue(createCellTreeNode(((String)pcname),pcinfo.getLevel(),isLeaf(pcinfo.getLongNumber(),kdtEntries,HEADNUMBER)));
						row.getCell("htrange").setValue(htrange);
						if(rowFlag%2 == 0)
							row.getStyleAttributes().setBackground(bgc);
						rowFlag++;
					}else if(fxcoll.size() == 0){
						hyTypeId = pcinfo.getHyType().getId().toString();
						pcoll=ipt.getPcTypeEntryCollection("select fieldName,ckDate,tqDays,recordSeq where parent.id='"+hyTypeId+"'");
						for (int j = pcoll.size()-1; j >=0; j--) {
							einfo = pcoll.get(j);
							ptentrys.put(einfo.getRecordSeq(),einfo);
						}
						builder.appendSql("select max(count(fid)) from CT_CON_PcTypeEntry where fparentid='"+hyTypeId+"' group by CFDepTypeID");
						rsfxbd = builder.executeQuery();
						int mxSize = 0;
						if(rsfxbd.next()){
							mxSize = rsfxbd.getInt(1);
						}
						for(int k=0; k<mxSize; k++){
							row = kdfxbd.addRow();
							if(pcname instanceof CellTreeNode)
								row.getCell("name").setValue(pcname);
							else if(pcname!=null && ((String)pcname).trim().length()>0)
									row.getCell("name").setValue(createCellTreeNode(((String)pcname),pcinfo.getLevel(),isLeaf(pcinfo.getLongNumber(),kdtEntries,HEADNUMBER)));
							row.getCell("htrange").setValue(htrange);
							row.getCell("maxSize").setValue(Integer.valueOf(mxSize));
							for (int j = 0; j < pcs.size(); j++) {
								depNumber = pcs.get(j).getNumber();
								einfo = ptentrys.get(depNumber+k);
								if(einfo != null && einfo.getFieldName()!=null){
									if(einfo.getCkDate() != null && ckDates.get(einfo.getCkDate().getName()) != null){
										c1.setTime(ckDates.get(einfo.getCkDate().getName()));
										c1.add(Calendar.DATE,-einfo.getTqDays());
										row.getCell(depNumber).setValue(einfo.getFieldName()+":"+sdf.format(c1.getTime()));
									}else{
										row.getCell(depNumber).setValue(einfo.getFieldName()+":");
									}
								}
							}
							if(rowFlag%2 == 0)
								row.getStyleAttributes().setBackground(bgc);
						}
						builder.clear();
						ptentrys.clear();
						rowFlag++;
						//���ø��еı�����ɫ 
						kdfxbd.getMergeManager().mergeBlock(kdfxbd.getRowCount3()-mxSize,0,kdfxbd.getRowCount3()-1,0);
						kdfxbd.getMergeManager().mergeBlock(kdfxbd.getRowCount3()-mxSize,1,kdfxbd.getRowCount3()-1,1);
					}else if(fxcoll.size() > 0){
						hyTypeId = pcinfo.getHyType().getId().toString();
						pcoll=ipt.getPcTypeEntryCollection("select fieldName,ckDate,tqDays,recordSeq where parent.id='"+hyTypeId+"'");
						for (int j = pcoll.size()-1; j >=0; j--) {
							einfo = pcoll.get(j);
							ptentrys.put(einfo.getRecordSeq(),einfo);
						}
						for (int j = 0; j < fxcoll.size(); j++) {
							feinfo = fxcoll.get(j);
							fxentrys.put(feinfo.getRecordSeq(),feinfo);
						}
						builder.appendSql("select max(count(fid)) from CT_CON_PcTypeEntry where fparentid='"+hyTypeId+"' group by CFDepTypeID");
						rsfxbd = builder.executeQuery();
						int mxSize = 0;
						if(rsfxbd.next()){
							mxSize = rsfxbd.getInt(1);
						}
						for(int k=0; k<mxSize; k++){
							row = kdfxbd.addRow();
							if(pcname instanceof CellTreeNode)
								row.getCell("name").setValue(pcname);
							else if(pcname!=null && ((String)pcname).trim().length()>0)
									row.getCell("name").setValue(createCellTreeNode(((String)pcname),pcinfo.getLevel(),isLeaf(pcinfo.getLongNumber(),kdtEntries,HEADNUMBER)));
							row.getCell("htrange").setValue(htrange);
							row.getCell("maxSize").setValue(Integer.valueOf(mxSize));
							if(rowFlag%2 == 0)
								row.getStyleAttributes().setBackground(bgc);
							for (int j = 0; j < pcs.size(); j++) {
								depNumber = pcs.get(j).getNumber();
								feinfo = fxentrys.get(depNumber+k);
								if(feinfo!=null && feinfo.getItemName() != null){
									row.getCell(depNumber).setValue(feinfo.getItemName()+":"+(feinfo.getPlanDate()==null?"":feinfo.getPlanDate()));
									if(isDateChange(ptentrys.get(depNumber+k),feinfo.getPlanDate(),c1,ckDates))
										row.getCell(depNumber).getStyleAttributes().setBackground(Color.red);
								}
							}
						}
						builder.clear();
						ptentrys.clear();
						fxentrys.clear();
						rowFlag++;
						//���ø��еı�����ɫ 
						kdfxbd.getMergeManager().mergeBlock(kdfxbd.getRowCount3()-mxSize,0,kdfxbd.getRowCount3()-1,0);
						kdfxbd.getMergeManager().mergeBlock(kdfxbd.getRowCount3()-mxSize,1,kdfxbd.getRowCount3()-1,1);
					}
					ckDates.clear();
				}
				//��������
				isReloadFxbdTab = false;
			}
		}
	}
	
	private boolean isDateChange(PcTypeEntryInfo einfo,Date editDate,Calendar c1,Map<String,Date> ckDates){
		if(einfo != null){
			if(editDate==null)
				return false;
			CKDate ckItem = einfo.getCkDate();
			if(ckItem==null)
				return false;
			Date ckdate = ckDates.get(ckItem.getName());
			if(ckdate==null)
				return false;
//			CKDate ckItem = einfo.getCkDate();
//			if(ckItem==null && editDate==null)
//				return false;
//			if(ckItem==null && editDate!=null)
//				return true;
//			Date ckdate = ckDates.get(ckItem.getName());
//			if(ckdate==null && editDate==null)
//				return false;
//			if(ckdate==null ^ editDate==null)
//				return true;
			c1.setTime(ckdate);
			c1.add(Calendar.DATE,-einfo.getTqDays());
			if(new Timestamp(c1.getTime().getTime()).compareTo(new Timestamp(editDate.getTime())) != 0)
				return true;
		}
		return false;
	}
	
	/**
	 * modified for R131127-0394 - û��Ŀ��ɱ��Ĳ���ʾ"Ŀ��ɱ�"��������
	 * @author RD_zhaoqin
	 * @date 2014/05/12
	 */
	private boolean checkHasAimCost() throws BOSException, EASBizException {
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		if(null == project || null == project.getId())
			return false;
		String curID = project.getId().toString();
		StringBuffer oql = new StringBuffer("select 1 where orgOrProId='").append(curID).append("'").append(
				" and isLastVersion=1 and state='").append(FDCBillStateEnum.AUDITTED_VALUE).append("'").append(" order by versionNumber desc");
		return AimCostFactory.getRemoteInstance().exists(oql.toString());
	}
	

	/**
	 * �л����ɱ�ҳǩʱ��������
	 */
	private void loadCostAccountToCostEntry() {
		long startTime = System.currentTimeMillis();

		//////////////////////////////////////////////////////////////////

		loadCostAccountTabData();
		setCostNumberFormat();
		createCostTree();

		List rows = this.kdtCostAccount.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtCostAccount.getColumnIndex("costNumber"), KDTSortManager.SORT_ASCEND));
		
		// �����ε�table��������ϻ���
		FDCTableHelper.calcTotalAmount(kdtCostAccount, 0, new String[] { AIM_COST, ASSIGNED, ASSIGNING }, "costAccount.id", "costAccount.parent.id");
		
		kdtCostAccount.setRefresh(false);
		// add bu lihaiou,2013.7.25 ,fix bug for R130718-0286
		//setAccountsTableToSumField();
		// modify end
		setCostAccountDisplay();

		//////////////////////////////////////////////////////////////////

		long endTime = System.currentTimeMillis();
		double exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("ProgrammingUI.loadCostAccountToCostEntry(),exeTime:" + exeTime + "��");
	}

	/**
	 * ���óɱ�ҳǩ�ĳɱ���Ŀ��������ʾ��ʽ
	 */
	private void setCostNumberFormat() {
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return null;
			}
		});
		kdtCostAccount.getColumn("costNumber").setRenderer(render);
	}

	/**
	 * ���óɱ�ҳǩ������
	 */
	private void createCostTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtCostAccount.getRowCount()];
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			IRow row = kdtCostAccount.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtCostAccount.getTreeColumn().setDepth(maxLevel);
		kdtCostAccount.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	/**
	 * ���سɱ���Ŀҳǩ������ʾ
	 */
	private void loadCostAccountTabData() {
		kdtCostAccount.removeRows();
		kdtCostAccount.getStyleAttributes().setLocked(true);
		// modify by lihaiou ,2013.7.25 for bug R130718-0286
		//		kdtCostAccount.getFootRow(0).getCell(AIM_COST).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//		kdtCostAccount.getFootRow(0).getCell(ASSIGNED).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//		kdtCostAccount.getFootRow(0).getCell(ASSIGNING).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//		
		//		kdtCostAccount.getFootRow(0).getCell(AIM_COST).setValue(null);
		//		kdtCostAccount.getFootRow(0).getCell(ASSIGNED).setValue(null);
		//		kdtCostAccount.getFootRow(0).getCell(ASSIGNING).setValue(null);
		// modify end 
		int rowCount = kdtEntries.getRowCount();
		// modify by lihaiou. 2013.07.30, fix bug R130723-0087,ѭ�������ݿ⣬������������
		//ȡ��ǰ��Ŀ������Ŀ��ɱ���Ŀ
		Map costInfoMap = getCostInfoMap();
		for (int i = 0; i < rowCount; i++) {
			ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			// Object name = kdtEntries.getCell(i, "name").getValue();//��ܺ�Լģ������
			Object name = getCellValue(kdtEntries, i, "name");
			String proName = "";
			String oldName = "";
			if (name != null && name.toString().trim().length() > 0) {
				proName = name.toString().trim();
				oldName = name.toString().trim();
			}
			createCostEntriesRow(i, rowObject, proName, oldName, costInfoMap);
		}
		
		/* modified by zhaoqin for ���� on 2014/05/19 */
		displayOtherCostInfo(costInfoMap);
		
		KDTableHelper.autoFitColumnWidth(kdtCostAccount, kdtCostAccount.getColumnIndex("proName"), 5);
		// modify end
	}

	/**
	 * ������һ���Ի�ȡ��ǰ��Ŀ���еĳɱ���Ŀ
	 * @param costInfoMap
	 * @Author��haiou_li
	 * @CreateTime��2013-7-31
	 */
	private Map getCostInfoMap() {
		/* modified by zhaoqin for ���� on 2014/05/19 start */
		// ֻ�鱾��ǰ������Ŀ�µĳɱ���Ŀ
		//DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		String projectId = this.editData.getProject().getId().toString();
		Map costInfoMap = new HashMap();
		try {
			/*Set keySet = new HashSet();
			for (int j = 0; j < kdtEntries.getRowCount(); j++) {
				ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(j).getUserObject();
				if (rowObject != null) {
					ProgrammingContracCostCollection proCol = rowObject.getCostEntries();
					if (proCol.size() > 0) {
						for (int i = 0; i < proCol.size(); i++) {
							ProgrammingContracCostInfo info = proCol.get(i);
							CostAccountInfo costInfo = info.getCostAccount();
							if (costInfo != null && costInfo.getCurProject() != null) {
								keySet.add(costInfo.getCurProject().getId().toString());
							}
						}
					}
				}
			}*/

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			SelectorItemCollection sic = view.getSelector();
			sic.add("*");
			sic.add("curProject.name");
			//filter.getFilterItems().add(new FilterItemInfo("curProject", keySet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("curProject", projectId, CompareType.EQUALS));
			/* modified by zhaoqin for ���� on 2014/05/19 end */
			
			//filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID, CompareType.EQUALS));
			view.getSorter().add(new SorterItemInfo("longNumber"));
			
			CostAccountCollection costInfoCol = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			for (int i = 0; costInfoCol != null && i < costInfoCol.size(); i++) {
				CostAccountInfo costaccountinfo = costInfoCol.get(i);
				if (costaccountinfo != null) {
					costInfoMap.put(costaccountinfo.getId().toString(), costaccountinfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
		return costInfoMap;
	}

	/**
	 * ��ѯ���ڵĳɱ���Ŀ������Ϣ - ����
	 * @param costIds
	 * @return
	 * @author RD_zhaoqin
	 * @date 2014/05/19
	 */
	private void displayOtherCostInfo(Map costInfoMap) {
		if(null == this.editData || null == this.editData.getProject() || null == this.editData.getProject().getId() ||
				null == this.editData.getAimCost() || null == this.editData.getAimCost().getId())
			return;
		String projectId = this.editData.getProject().getId().toString();
		String aimCostId = this.editData.getAimCost().getId().toString();
		if(StringUtils.isEmpty(projectId) && StringUtils.isEmpty(aimCostId))
			return;
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select cp.fname_l2 projectName, cp.fid projectId,pc.fid conId,pc.fname_l2 conName,pcc.fid,pcc.fgoalcost,pcc.fcontractassign,pcc.fcostaccountid,pcc.fcontractScale ");
			builder.appendSql(" from T_CON_Programming p join T_CON_ProgrammingContract pc on pc.fprogrammingid = p.fid ");
			builder.appendSql(" join T_CON_ProgrammingContracCost pcc on pcc.fcontractid = pc.fid join T_FDC_CostAccount ca on ca.fid = pcc.fcostAccountId ");
			builder.appendSql(" join T_FDC_CurProject cp on cp.fid = p.fprojectid join (select max(fversion) fversion, fprojectid from T_CON_Programming group by fprojectid ");
			builder.appendSql(" ) t on (t.fversion = p.fversion and t.fprojectid = p.fprojectid) ");
			builder.appendSql(" where p.fprojectid <> '").appendSql(projectId).appendSql("' ");
			builder.appendSql(" and ca.fcurproject = '").appendSql(projectId).appendSql("' ");
			builder.appendSql(" and pcc.faimCostId = '").appendSql(aimCostId).appendSql("' ");
			builder.appendSql(" order by cp.flongnumber");
			ProgrammingContractCollection conList = new ProgrammingContractCollection();
			Map conMap = new HashMap();
			IRowSet rs = builder.executeQuery();
			while(rs.next()) {
				String conId = rs.getString("conId");
				ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)conMap.get(conId);
				if(null == pcInfo) {
					pcInfo = new ProgrammingContractInfo();
					pcInfo.setId(BOSUuid.read(conId));
					pcInfo.setName(rs.getString("conName"));
					conList.add(pcInfo);
					conMap.put(conId, pcInfo);
				}
				String costAccountId = rs.getString("fcostaccountid");
				CostAccountInfo ca = (CostAccountInfo)conMap.get(costAccountId);
				if(null == ca) {
					ca = new CostAccountInfo();
					ca.setId(BOSUuid.read(costAccountId));
					conMap.put(costAccountId, ca);
				}
				ProgrammingContracCostInfo pccInfo = new ProgrammingContracCostInfo();
				pccInfo.setId(BOSUuid.read(rs.getString("fid")));
				pccInfo.setGoalCost(rs.getBigDecimal("fgoalcost"));
				pccInfo.setContractAssign(rs.getBigDecimal("fcontractassign"));
				pccInfo.setContractScale(rs.getBigDecimal("fcontractScale"));
				pccInfo.put("projectNameOfOtherProg", rs.getString("projectName"));
				pccInfo.setCostAccount(ca);
				pcInfo.getCostEntries().add(pccInfo);
			}
			
			for(int i = 0; i < conList.size(); i++) {
				String proName = conList.get(i).getName();
				String oldName = conList.get(i).getName();
				createCostEntriesRow(i, conList.get(i), proName, oldName, costInfoMap);
			}
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		} catch (SQLException e2) {
			handUIExceptionAndAbort(e2);
		}
	}

	/**
	 * �����ɱ�ҳǩ��¼��
	 * 
	 * @param i
	 * @param rowObject
	 * @param proName
	 * @param oldName
	 */
	private void createCostEntriesRow(int i, ProgrammingContractInfo rowObject, String proName, String oldName, Map costInfoMap) {
		if (rowObject != null) {
			ProgrammingContracCostCollection proCol = rowObject.getCostEntries();
			if (proCol.size() > 0) {
				addRowForCost(proName, oldName, proCol, costInfoMap);
			}
		}
	}

	/**
	 * ����е��ɱ���¼
	 * 
	 * @param proName
	 * @param oldName
	 * @param proCol
	 */
	private void addRowForCost(String proName, String oldName, ProgrammingContracCostCollection proCol, Map costInfoMap) {
		for (int i = 0; i < proCol.size(); i++) {
			boolean isHas = false;
			ProgrammingContracCostInfo info = proCol.get(i);
			
			/* modified by zhaoqin for ���� on 2014/05/19 start */
			if (curProject == null || (null != info.getCostAccount().getCurProject() && 
					!curProject.getId().equals(info.getCostAccount().getCurProject().getId()))) {
				return;
			}
			CostAccountInfo costInfo = (CostAccountInfo) costInfoMap.get(info.getCostAccount().getId().toString());
			if (costInfo == null) {
				MsgBox.showWarning(this, "��ܺ�Լ��Ӧ�ĳɱ���Ŀ�Ѿ������ı�!");
				SysUtil.abort();
			}
			/* modified by zhaoqin for ���� on 2014/05/19 end */
			
			BigDecimal contractAssign = info.getContractAssign();// ����Լ����
			BigDecimal goalCost = info.getGoalCost();// Ŀ��ɱ�
			
			/* modified by zhaoqin for R131119-0321 on 2013/12/3 start */
			BigDecimal contractScale = info.getContractScale();
			if(null != contractScale)
				contractScale = contractScale.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				contractScale = FDCHelper.ZERO;
			proName = oldName + "(" + contractScale + "%)";
			/*
			if (contractAssign != null && goalCost != null) {
				if (goalCost.compareTo(FDCHelper.ZERO) > 0) {
					BigDecimal percent = contractAssign.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED);
					percent = percent.divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
					if (percent.intValue() <= 0) {
						proName = oldName;
					} else if (percent.intValue() <= 100) {
						proName = oldName + percent + "%";
					}
				}
			}
			*/
			/* modified by zhaoqin for R131119-0321 on 2013/12/3 end */
			
			/* modified by zhaoqin for ���� on 2014/05/19 start */
			// modify by lihaiou. 2013.07.30, fix bug R130723-0087,ѭ�������ݿ⣬������������
			//CostAccountInfo costInfo = (CostAccountInfo) costInfoMap.get(info.getCostAccount().getId().toString());
			//if (costInfo == null) {
				//				SelectorItemCollection sic = new SelectorItemCollection();
				//				sic.add("*");
				//				sic.add("curProject.name");
				//				try {
				//					costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getCostAccount().getId()),
				//							sic);
				//				} catch (EASBizException e) {
				//					logger.error(e);
				//					handUIExceptionAndAbort(e);
				//				} catch (BOSException e) {
				//					logger.error(e);
				//					handUIExceptionAndAbort(e);
				//				}
				//MsgBox.showWarning(this, "��ܺ�Լ��Ӧ�ĳɱ���Ŀ�Ѿ������ı�!");
				//SysUtil.abort();
			//}
			// modify end
			//if (curProject == null || !curProject.getId().equals(costInfo.getCurProject().getId())) {
				//proName = costInfo.getCurProject().getName() + "." + proName;
			//}
			if(null != info.get("projectNameOfOtherProg")) {
				proName = (String)info.get("projectNameOfOtherProg") + "." + proName;
			}
			/* modified by zhaoqin for ���� on 2014/05/19 end */

			String name = null;
			for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
				name = null;
				IRow row_k = kdtCostAccount.getRow(j);
				String number = row_k.getCell("costNumber").getValue().toString();
				Object name_1 = row_k.getCell("proName").getValue();
				if (name_1 != null)
					name = name_1.toString();
				if (number.equals(costInfo.getLongNumber())) {
					isHas = true;
					if (name == null) {
						row_k.getCell("proName").setValue(proName);
					} else {
						row_k.getCell("proName").setValue(name + ",\n" + proName);	/* modified by zhaoqin for R140507-0246 on 2014/05/13 */
					}
					Object ass = row_k.getCell(ASSIGNED).getValue();
					BigDecimal assigned = FDCHelper.ZERO;
					if (ass != null && !StringUtils.isEmpty(ass.toString())) {
						assigned = new BigDecimal(ass.toString());
						assigned = assigned.add(contractAssign);
						row_k.getCell(ASSIGNED).setValue(assigned);
					}
					Object aim = row_k.getCell(AIM_COST).getValue();
					BigDecimal aimCost = FDCHelper.ZERO;
					if (aim != null && !StringUtils.isEmpty(aim.toString())) {
						aimCost = new BigDecimal(aim.toString());
					}
					row_k.getCell(ASSIGNING).setValue(aimCost.subtract(assigned));
				}
			}
			if (!isHas) {
				List list = new ArrayList();
				getParentCostAccountInfo(costInfo, list, costInfoMap);
				addCostAccountParent(list);
				IRow row = kdtCostAccount.addRow();
				row.getCell("costNumber").setValue(costInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costInfo.getLevel()) + costInfo.getName());
				row.getCell(AIM_COST).setValue(goalCost);
				row.getCell(ASSIGNED).setValue(contractAssign);
				row.getCell(ASSIGNING).setValue(goalCost.subtract(contractAssign));
				row.getCell("level").setValue(costInfo.getLevel() + "");
				row.getCell("proName").setValue(proName);
				row.getCell("costAccount.id").setValue(costInfo.getId().toString());
				row.getCell("costAccount.parent.id").setValue(costInfo.getParent().getId().toString());
				row.setUserObject(costInfo);
			}
		}
	}

	/**
	 * �ݹ��ȡ�ɱ���Ŀ�������ϼ��ɱ���Ŀ
	 * @modify lihaiou.2013.0731, fix bug R130723-0087,�������,�������ݿ��ѯ����
	 * @param info
	 * @param list
	 * @return
	 */
	private CostAccountInfo getParentCostAccountInfo(CostAccountInfo info, List list, Map costInfoMap) {
		if (info != null && info.getParent() != null) {
			String parentId = info.getParent().getId().toString();
			
			/* modified by zhaoqin for R131210-0246 on 2013/12/16 start */
			info = (CostAccountInfo) costInfoMap.get(parentId);
			//if (costInfoMap.get(parentId) != null) {
			if (null != info) {
				//info = (CostAccountInfo) costInfoMap.get(parentId);
				//list.add(costInfoMap.get(parentId));
				list.add(info);
			}
			/* modified by zhaoqin for R131210-0246 on 2013/12/16 end */
			
			return getParentCostAccountInfo(info, list, costInfoMap);
		}
		return null;
	}

	/**
	 * ��������ϼ��ĳɱ���Ŀ���������ϼ��ɱ���Ŀ
	 * 
	 * @param list
	 */
	private void addCostAccountParent(List list) {
		for (int i = 0; i < list.size(); i++) {
			CostAccountInfo costAccountInfo = (CostAccountInfo) list.get(i);
			boolean isHas = false;
			for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
				IRow row = kdtCostAccount.getRow(j);
				String number = row.getCell("costNumber").getValue().toString();
				if (number.equals(costAccountInfo.getLongNumber())) {
					isHas = true;
				}
			}
			if (!isHas) {
				IRow row = kdtCostAccount.addRow();
				row.getCell("costNumber").setValue(costAccountInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costAccountInfo.getLevel()) + costAccountInfo.getName());
				row.getCell("level").setValue(costAccountInfo.getLevel() + "");
				row.getCell("costAccount.id").setValue(costAccountInfo.getId().toString());
				if (costAccountInfo.getParent() != null) {
					row.getCell("costAccount.parent.id").setValue(costAccountInfo.getParent().getId().toString());
				}
				row.setUserObject(costAccountInfo);
			}
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		
		//��Լ�滮���ƽ��棬ѡ��ĳ��Ŀ�㡰ˢ�¡���ť����Ŀ����λ�����ڵ㣬Ӧ�ö�λ��ԭѡ����Ŀ��ֻˢ���Ҳ��б����ݡ� add by zkyan
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
				editData = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(editData.getId().toString()), getSelectors());
				if (editData == null)
					return;
				setDataObject(editData);
				loadFields();
			}
		}
		this.dataBinder.storeFields();
		setKDTableLostFocus();
		createTree();
		// loadCostAccountToCostEntry();
		// ���ˢ�����ݺϼƲ�������
		setProTableToSumField();
		sumClass.appendProFootRow(null, null);
		
		setkdtEntries_AmountState(); // modified by zhaoqin on 2013/10/17
	}

	/**
	 * ����Ŀ���빦��
	 */
	public void actioinImportProject_actionPerformed(ActionEvent e) throws Exception {
		
	}
	
	/**
	 * ��ģ�嵼�빦��
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		ProgrammingContractCollection entries = getKdtEntriesUserObjects();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			if (entry.isIsCiting()) {
				throw new EASBizException(new NumericExceptionSubItem("1", "��Լ����д��ڱ����õĿ�ܺ�Լ\n������˲���"));
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("editData", editData);
		uiContext.put("project", getUIContext().get(TREE_SELECTED_OBJ));
		uiContext.put(AIM_COST, prmtAimCost.getData());
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingImportUI.class.getName(), uiContext, null, OprtState.VIEW,
				WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		ui.show();

		clearAndDisplay(entries);
		
		setkdtEntries_AmountState(); // modified by zhaoqin on 2013/10/17
	}

	/**
	 * 
	 * ����������ؼ���ԭ�������ݣ�������loadField��ʾ
	 * 
	 * @param entries
	 *            �����ˣ�owen_wen ����ʱ�䣺2011-10-14
	 */
	private void clearAndDisplay(ProgrammingContractCollection entries) {
		// ��ĩ�������ϻ���
		// �滮��� = �¼��滮������
		//		Set leafSet = findLeafSet();
		//		cleanNotLeafAmout(leafSet);
		//		totalAmout(leafSet);
		// �����滮��� = �滮���
		//		for (int i = 0, size = entries.size(); i < size; i++) {
		//			ProgrammingContractInfo entry = entries.get(i);
		//			entry.setBalance(entry.getAmount());
		//		}

		setDataObject(editData);
		loadFields();
		setNameDisplay();
	}

	private Set findLeafSet() {
		Set leafNumberSet = new HashSet();
		ProgrammingContractCollection entries = getKdtEntriesUserObjects();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo info = entries.get(i);
			String longNumber = info.getLongNumber();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				ProgrammingContractInfo infoJ = entries.get(j);
				String longNumberJ = infoJ.getLongNumber();
				if (longNumberJ.startsWith(longNumber)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf && longNumber.indexOf('.') != -1) {
				leafNumberSet.add(longNumber);
			}
		}
		return leafNumberSet;
	}

	/**
	 * һ�����ڵ��µ����нڵ�
	 * 
	 * @param numberList
	 * @return
	 */
	private Set findLeafList(List numberList) {
		Set leafNumberList = new HashSet();
		for (int i = 0, size = numberList.size(); i < size; i++) {
			String number = numberList.get(i).toString();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				String numberJ = numberList.get(j).toString();
				if (numberJ.startsWith(number)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf) {
				leafNumberList.add(number);
			}
		}
		return leafNumberList;
	}

	/**
	 * ��Ҷ�ڵ�ݹ������𼶻���
	 * 
	 * @param leafSet
	 */
	private void totalAmout(Set leafSet) {
		if (leafSet.isEmpty()) {
			return;
		}

		Set leafParentSet = new HashSet(); // ��ǰҶ�ڵ�ĸ��ڵ�
		for (Iterator it = leafSet.iterator(); it.hasNext();) {
			BigDecimal leafAmount = FDCHelper.ZERO; // ��ǰҶ�ڵ���
			String leafLongNumber = it.next().toString();
			ProgrammingContractCollection entries = getKdtEntriesUserObjects();
			for (int j = 0, sizeJ = entries.size(); j < sizeJ; j++) {
				ProgrammingContractInfo entry = entries.get(j);
				if (leafLongNumber.equals(entry.getLongNumber())) {
					leafAmount = entry.getAmount();
					break;
				}
			}
			ProgrammingContractInfo parentEntry = getParentEntry(leafLongNumber);
			if (parentEntry != null) {
				parentEntry.setAmount(leafAmount.add(parentEntry.getAmount()));
				leafParentSet.add(parentEntry.getLongNumber());
			}
		}

		List nodeList = new ArrayList();
		nodeList.addAll(leafParentSet);
		totalAmout(findLeafList(nodeList));
	}

	/**
	 * ���з�Ҷ�ڵ��������㣬ֱ�Ӵ�Ҷ�ڵ���ܡ�
	 * 
	 * @param leafSet
	 */
	private void cleanNotLeafAmout(Set leafSet) {
		ProgrammingContractCollection entries = getKdtEntriesUserObjects();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			if (!leafSet.contains(longNumber)) {
				// �������ӽڵ�ĸ��ڵ㲻ɾ��
				boolean hasChild = false;
				for (int j = 0; j < size; j++) {
					if (i == j) {
						continue;
					}
					if (entries.get(j).getLongNumber().startsWith(longNumber)) {
						hasChild = true;
						break;
					}
				}
				if (hasChild) {
					entry.setAmount(FDCHelper.ZERO);
				}
			}
		}
	}

	private ProgrammingContractInfo getParentEntry(String subLongNumber) {
		ProgrammingContractCollection entries = getKdtEntriesUserObjects();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			if (entry.getLongNumber().equals(subLongNumber)) {
				return entry.getParent();
			}
		}
		return null;
	}

	/**
	 * ���Ϊģ�幦��
	 */
	public void actionExportPro_actionPerformed(ActionEvent e) throws Exception {
		verifyDataBySave();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			row.getCell("sortNumber").setValue(new Integer(i));
		}
		this.dataBinder.storeFields();
		verifyRedData();
		UIContext uiContext = new UIContext(this);
		uiContext.put("Programming", editData);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingExportUI.class.getName(), uiContext, null, OprtState.EDIT);
		ui.show();
	}

	private void createCostCentertF7() {
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setVisible(true);
		f7.setEnabled(true);
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		EntityViewInfo view = f7.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		if (view.getFilter() == null) {
			view.setFilter(new FilterInfo());
		}
		Set idSet = null;
		try {
			idSet = FDCUtils.getAuthorizedOrgs(null);
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("COSTCENTERORGUNIT.id", idSet, CompareType.INCLUDE));
		f7.setEntityViewInfo(view);

		KDTDefaultCellEditor kdtEntriese_ORG_CellEditor = new KDTDefaultCellEditor(f7);
		kdtEntries.getColumn("inviteOrg").setEditor(kdtEntriese_ORG_CellEditor);
		ObjectValueRender kdtEntries_ORG_OVR = new ObjectValueRender();
		kdtEntries_ORG_OVR.setFormat(new BizDataFormat("$name$"));
		kdtEntries.getColumn("inviteOrg").setRenderer(kdtEntries_ORG_OVR);
	}

	/**
	 * ������Ŀ��
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

	//added by ken_liu....�����Ա���rpc��ȡ��Ŀ��ɱ���
	AimCostInfo temp = null;

	/**
	 * �������ڵ�ѡ��仯
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 start */
		if((getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT)) 
				&& !isSaved()) {
			if(MsgBox.isYes(MsgBox.showConfirm2(this, "����δ����,�Ƿ񱣴�?"))) {
				actionSave_actionPerformed(null);
			}
		}
		// modify by yxl 20150814
		btnScontract.setEnabled(false);
		txtYjDays.setEnabled(false);
//		prmtYjPerson.setEnabled(false);
		prmtYjCost.setEnabled(false);
		prmtYjDesign.setEnabled(false);
		prmtYjMaterial.setEnabled(false);
		prmtYjProject.setEnabled(false);
		updateSchduleTime.setEnabled(false);
		
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 start */
		prmtAimCost.removeDataChangeListener(dataChangeListener);// ˢ��ǰȡ������
		clearUIContext();
		setOprtState(OprtState.VIEW);
		if (isSelectedProjectNode()) {
			DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			
			/* modified by zhaoqin for ���� on 2014/05/19 */
			getUIContext().put(TREE_SELECTED_OBJ,projectNode.getUserObject());
			
			ProgrammingInfo info = getProgrammingInfo(projectNode);
			if (info != null) {// info��Ϊ�գ���������ʷ�汾
				getUIContext().put(HAS_HISTRY_VERSION, info.getVersion());
				setDataObject(info);
				this.getUIContext().put(ID, editData.getId().toString());
				handleHisotryData();
				refreshCurrentData();// ����ˢ�·���������ϼ�������
				if (info.getAimCost() == null) {
					String curID = ((CurProjectInfo) projectNode.getUserObject()).getId().toString();
					// modified by zhaoqin on 2013/10/28, ȡ���°汾��Ŀ��ɱ� isLastVersion=1
					StringBuffer oql = new StringBuffer("select * where orgOrProId='").append(curID).append("'").append(
							" and isLastVersion=1 and state='").append(FDCBillStateEnum.AUDITTED_VALUE).append("'").append(" order by versionNumber desc");
					AimCostCollection coll = AimCostFactory.getRemoteInstance().getAimCostCollection(oql.toString());
					if (coll != null && coll.size() > 0) {
						prmtAimCost.setValue(coll.get(0));
						editData.setAimCost(coll.get(0));
						temp = coll.get(0);
					}
				}

				setNameDisplay();
			} else {
				setDataObject(new ProgrammingInfo());
				loadFields();
				setProTableToSumField();
				sumClass.appendProFootRow(null, null);
				txtDisplayVersion.setText("");
				txtProjectName.setText("");
			}
			getUIContext().put(TREE_SELECTED_OBJ, projectNode.getUserObject());

			if (isBillStateEquals(FDCBillStateEnum.AUDITTED)) {
				changeAddNewButton(true);
			} else {
				changeAddNewButton(false);
			}

		} else {
			setDataObject(new ProgrammingInfo());
			loadFields();
			setProTableToSumField();
			sumClass.appendProFootRow(null, null);
			txtDisplayVersion.setText("");
			txtProjectName.setText("");
			getUIContext().remove(TREE_SELECTED_OBJ);

			changeAddNewButton(false);
		}
		kDTabbedPane1.setSelectedIndex(0);
		initHistoryMenuBar();
		prmtAimCost.addDataChangeListener(dataChangeListener);
		setSmallBtnEnable();

		if (editData != null && !FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			if (STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())) {
				kdtEntries.setEditable(true);
			} else {
				kdtEntries.setEditable(false);
			}
		} else {
			kdtEntries.setEditable(false);
		}
		
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 start */
		if(this.editData.getState() == FDCBillStateEnum.SAVED || this.editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionRemove.setEnabled(true);
		} else {
			actionRemove.setEnabled(false);
		}
		if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			kdtEntries.getColumn("iscse").getStyleAttributes().setHided(false);
		}else{
			kdtEntries.getColumn("iscse").getStyleAttributes().setHided(true);
		}
		setAimCostVersionMsg();
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 start */
		labelExplain.setText("�滮˵����");
		
	}

	/**
	 * ������
	 * @Author��keyan_zhao
	 * @CreateTime��2013-8-22
	 */
	private void changeAddNewButton(boolean b) {
		actionAddNew.setVisible(b);
		actionAddNew.setEnabled(b);
		btnAddNew.setText("�޸ĸ���滮");
		btnAddNew.setToolTipText("�޸ĸ���滮");
		btnAddNew.setEnabled(false);
		btnAddNew.setVisible(false);
		menuItemAddNew.setEnabled(b);
		menuItemAddNew.setVisible(b);
//		menuItemAddNew.setIcon(defaultIcon)
		menuItemAddNew.setIcon(EASResource.getIcon("tbtn_setting"));
	}

	private void clearUIContext() {
		getUIContext().remove(ID);
		getUIContext().remove(IS_MODIFY);
		getUIContext().remove(MODIFY);
		getUIContext().remove(HAS_HISTRY_VERSION);
	}

	/**
	 * ��ù�����Ϣ
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
		}
		return filter;
	}

	private TreeSelectionListener[] treeSelectionListeners;

	/**
	 * �������ѡ�������
	 */
	protected void attachListeners() {
		if (treeSelectionListeners != null) {
			for (int i = 0; i < treeSelectionListeners.length; i++) {
				this.treeMain.addTreeSelectionListener(treeSelectionListeners[i]);
			}
		}
	}

	/**
	 * ȡ������ѡ�������
	 */
	protected void detachListeners() {
		if (treeSelectionListeners != null) {
			for (int i = 0; i < treeSelectionListeners.length; i++) {
				this.treeMain.removeTreeSelectionListener(treeSelectionListeners[i]);
			}
		}
	}

	/**
	 * ����head�Ƿ�Ϊ�գ����ɲ�ͬ���ȵ�number
	 * 
	 * @param head
	 * @return
	 */
	private String gainNumberByDate(String head) {
		String result = null;
		if (StringUtils.isEmpty(head)) {
			result = String.valueOf(System.currentTimeMillis());
		} else {
			Random random = new Random();
			do {
				result = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(random.nextInt(1000)), 5, "0");
			} while (!checkNewNumberValid(result));
		}
		return result;
	}

	private boolean checkNewNumberValid(String number) {
		if (StringUtils.isEmpty(number))
			return false;
		int rowCount = kdtEntries.getRowCount();
		String value = null;
		for (int i = 0; i < rowCount; i++) {
			value = (String) kdtEntries.getCell(i, "number").getValue();
			if (value == null)
				continue;
			if (number.equals(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �޶�
	 */
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		if (!isSelectedProjectNode()) {
			MsgBox.showWarning("��ѡ�񹤳���Ŀ");
			return;
		}
		checkAudited2();
		checkLastVersion2();
		checkHighVersion2();
		setOprtState(OprtState.ADDNEW);
		getUIContext().put(IS_MODIFY, IS_MODIFY);
		ProgrammingInfo info = getProgrammingInfo(getTreeNode(treeMain.getSelectionPath()));
		setDataObject(info);
		this.getUIContext().put(ID, editData.getId().toString());
		prmtAimCost.removeDataChangeListener(dataChangeListener);// ˢ��ǰȡ������
		refreshCurrentData();// ����ˢ�·���������ϼ�������
		//added by ken_liu....refreshCurrentData�����prmtAimtCost��ֵ��
		if (prmtAimCost.getData() == null) {
			prmtAimCost.setData(temp);
		}
		prmtAimCost.addDataChangeListener(dataChangeListener);

		setNameDisplay();
		setCellEditorForTable();

		//add by zkyan �޶���������
		BOSUuid newID = BOSUuid.create(editData.getBOSType());
		BOSUuid oldID = editData.getId();
		editData.setId(newID);

		EntityViewInfo views = new EntityViewInfo();
		FilterInfo filters = new FilterInfo();
		SelectorItemCollection sics = new SelectorItemCollection();
		sics.add("*");
		sics.add("attachment.id");
		filters.getFilterItems().add(new FilterItemInfo("boID", oldID.toString()));
		views.setFilter(filters);
		views.setSelector(sics);
		IBoAttchAsso attchAssos = BoAttchAssoFactory.getRemoteInstance();
		BoAttchAssoCollection collections = attchAssos.getBoAttchAssoCollection(views);
		if (collections != null && collections.size() > 0) {
			for (int j = 0; j < collections.size(); j++) {
				BoAttchAssoInfo assoInfo = collections.get(j);
				BoAttchAssoInfo clone = (BoAttchAssoInfo) assoInfo.clone();
				clone.setId(null);
				clone.setBoID(newID.toString());
				attchAssos.addnew(clone);
			}
		}
		
		//modify by yxl �޶�ʱ����ͬ�Ƿ�ǩ�갴ť���ò�����
		btnScontract.setEnabled(false);
		editData.setNumber(getDateString());
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 */
		//editData.setVersion(editData.getVersion().add(new BigDecimal("1")));
		
		editData.put("state", null);
		editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		editData.setLastUpdateUser(null);
		editData.setLastUpdateTime(null);
		editData.setName(getDisplayName());

		getUIContext().remove(ID);
		txtNumber.setText(editData.getNumber());
		txtName.setText(editData.getName());
		//txtVersion.setValue(editData.getVersion());
		txtState.setText(null);
		// txtState.setText(editData.getState());
		kDDatePicker1.setValue(editData.getCreateTime());
		kDDatePicker2.setValue(editData.getLastUpdateTime());

		ProgrammingContractCollection entries = getKdtEntriesUserObjects();

		Map oldNewKeys = new HashMap();
		Set parents = new HashSet();
		Map oldKeyNewKey = new HashMap();
		for (int i = 0; i < entries.size(); i++) {
			ProgrammingContractInfo pc = entries.get(i);
			BOSUuid oldKey = pc.getId();
			pc.setSrcId(oldKey.toString());
			pc.setBaseId(pc.getBaseId());
			BOSUuid newKey = BOSUuid.create(pc.getBOSType());
			pc.setId(newKey);
			oldKeyNewKey.put(oldKey.toString(), newKey.toString());
			// modify lihaiou end
//			pc.setIscse(false);
			oldNewKeys.put(pc.getLongNumber() + pc.getName(), newKey);
			parents.add(pc.getParent());
			ProgrammingContractEconomyCollection economyEntries = pc.getEconomyEntries();
			for (int j = 0; j < economyEntries.size(); j++) {
				economyEntries.get(j).setId(BOSUuid.create(economyEntries.get(j).getBOSType()));
			}
			ProgrammingContracCostCollection costEntries = pc.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				costEntries.get(j).setId(BOSUuid.create(costEntries.get(j).getBOSType()));
			}
			
			copyPayPlanNew(pc, oldKey.toString());
		}
		// modify by lihaiou,2013.07.31,һ���Բ�����еĸ���
		copyAttachMent(oldKeyNewKey);
		// modify lihaiou end
		Iterator its = parents.iterator();
		for (; its.hasNext();) {
			ProgrammingContractInfo parentPcInfo = (ProgrammingContractInfo) its.next();
			if (parentPcInfo != null) {
				Object object = oldNewKeys.get(parentPcInfo.getLongNumber() + parentPcInfo.getName());
				parentPcInfo.setId((BOSUuid) object);
			}
		}
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			kdtEntries.getCell(i, "id").setValue(((ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject()).getId());
			// ������޶�Ȼ��ֱ���ύ����Լ��ܵı����þͻ���ʧ����ע���˴�	
			//			kdtEntries.getCell(i, "isCiting").setValue(Boolean.FALSE);
			kdtEntries.getCell(i, "iscse").setValue(Boolean.FALSE);
		}
		kdtEntries.getColumn("iscse").getStyleAttributes().setHided(true);

		// modified by zhaoqin on 2013/10/22
		// updateProgrammingContract();
		updateProgrammingContract1(oldID.toString());
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 start */
		txtDisplayVersion.setText(getDisplayName());
		txtName.setText(getDisplayName());
		txtVersion.setValue(editData.getVersion());
		editData.setName(getDisplayName());
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 end */
		
		switchButtonEnabled();
		//add by zkyan ��Լ�滮�У�ֻҪ�ڱ༭״̬�£������оͿ���ʹ��
		btnAddnewLine.setEnabled(true);

		setNameDisplay();
		
		setkdtEntries_AmountState(); // modified by zhaoqin on 2013/10/17
		
		/* modified by zhaoqin for R140507-0214 on 2014/05/13 */
		setSaved(false);
	}
	
	
	private void copyPayPlanNew(ProgrammingContractInfo pc, String srcId) {

		try {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programming.id", srcId));
			evi.setFilter(filter);

			evi.setSelector(getPayPlanNewSelectors());

			PayPlanNewCollection coll = PayPlanNewFactory.getRemoteInstance().getPayPlanNewCollection(evi);

			if (coll != null && coll.get(0) != null) {
				PayPlanNewInfo srcInfo = coll.get(0);

				PayPlanNewInfo info = (PayPlanNewInfo) srcInfo.clone();

				info.setId(null);
				PayPlanNewByScheduleCollection sColl = info.getBySchedule();
				for (int i = 0; sColl != null && i < sColl.size(); i++) {
					PayPlanNewByScheduleInfo sInfo = sColl.get(i);
					sInfo.setSrcID(sInfo.getId());
					sInfo.setId(null);

					PayPlanNewByScheduleTaskCollection task = sInfo.getTask();
					for (int j = 0; j < task.size(); j++) {
						PayPlanNewByScheduleTaskInfo taskInfo = task.get(j);
						taskInfo.setId(null);
					}

					PayPlanNewByScheduleTaskNameCollection taskName = sInfo.getTaskName();
					for (int j = 0; j < taskName.size(); j++) {
						PayPlanNewByScheduleTaskNameInfo taskInfo = taskName.get(j);
						taskInfo.setId(null);
					}

					PayPlanNewByScheduleDatazCollection dataz = sInfo.getDataz();
					for (int j = 0; j < dataz.size(); j++) {
						PayPlanNewByScheduleDatazInfo datazInfo = dataz.get(j);
						datazInfo.setId(null);
					}
				}


				PayPlanNewDataCollection data = info.getData();
				for (int i = 0; i < data.size(); i++) {
					PayPlanNewDataInfo dataInfo = data.get(i);
					dataInfo.setId(null);
				}

				PayPlanNewUnsignCollection unsign = info.getUnsign();
				for (int i = 0; i < unsign.size(); i++) {
					PayPlanNewUnsignInfo dataInfo = unsign.get(i);
					dataInfo.setId(null);
				}

				info.setProgramming(pc);
				pc.put("PayPlan", info);
			}
		} catch (BOSException e) {
			this.handUIExceptionAndAbort(e);
		}
	}

	public SelectorItemCollection getPayPlanNewSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Data.*");
		sic.add("BySchedule.Task.task.name");
		sic.add("BySchedule.Task.task.start");
		sic.add("BySchedule.Task.task.end");
		sic.add("BySchedule.TaskName.*");
		sic.add("BySchedule.Dataz.*");
		sic.add("ByMonth.*");
		sic.add("ByMonth.costAccount.number");
		sic.add("BySchedule.paymentType.*");
		sic.add("BySchedule.costAccount.*");
		sic.add("BySchedule.*");
		sic.add("ByMonth.costAccount.*");
		return sic;
	}

	/**
	 * ������һ���Ը������еĸ��������Ч�� fix bug R130723-0087
	 * @param oldKeyNewKey
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author��haiou_li
	 * @CreateTime��2013-7-31
	 */
	private void copyAttachMent(Map oldKeyNewKey) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("attachment.id");
		Set idSet = new HashSet();
		for (Iterator it = oldKeyNewKey.keySet().iterator(); it.hasNext();) {
			String id = (String) it.next();
			idSet.add(id);
		}
		filter.getFilterItems().add(new FilterItemInfo("boID", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.setSelector(sic);
		IBoAttchAsso attchAsso = BoAttchAssoFactory.getRemoteInstance();
		BoAttchAssoCollection collection = attchAsso.getBoAttchAssoCollection(view);
		if (collection != null && collection.size() > 0) {
			for (int j = 0; j < collection.size(); j++) {
				BoAttchAssoInfo assoInfo = collection.get(j);
				BoAttchAssoInfo clone = (BoAttchAssoInfo) assoInfo.clone();
				clone.setId(null);
				clone.setBoID((String) oldKeyNewKey.get(clone.getBoID()));
				attchAsso.addnew(clone);
			}
		}
	}

	private void checkAudited2() throws Exception {
		String id = (String) getUIContext().get(ID);
		ProgrammingInfo info = getServiceInterface2().getProgrammingInfo(new ObjectUuidPK(id), getSelectors2());
		if (!FDCUtils.isBillAudited(info)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "���������ݲ����޶�"));
		}

	}

	private void checkLastVersion2() throws Exception {
		String id = (String) getUIContext().get(ID);
		boolean isLastVersion = getServiceInterface2().isLastVersion(new ObjectUuidPK(id));
		if (!isLastVersion) {
			throw new EASBizException(new NumericExceptionSubItem("1", "�����°汾�����޶�"));
		}
	}

	private IProgramming getServiceInterface2() throws Exception {
		return (IProgramming) getBizInterface();
	}

	/**
	 * ����TreePath���DefaultKingdeeTreeNode
	 * 
	 * @param path
	 * @return
	 */
	public static DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}

	/**
	 * ����DefaultKingdeeTreeNode���ProgrammingInfo
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
			historyVersions = coll;
			if (coll.size() > 0) {
				result = coll.get(0);
			}
		}
		return result;
	}

	public SelectorItemCollection getSelectors2() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("aimCost.versionName"));
		sic.add(new SelectorItemInfo("aimCost.versionNumber"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		sic.add(new SelectorItemInfo("versionGroup"));
		sic.add(new SelectorItemInfo("isLatest"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		return sic;
	}

	/**
	 * �޶�ǰ����
	 */
	protected void prepareModifyUIContext() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj instanceof CurProjectInfo) {
				getUIContext().put(TREE_SELECTED_OBJ, obj);
			}
		}
		getUIContext().put(IS_MODIFY, Boolean.TRUE);
		getUIContext().put(MODIFY, MODIFY);
	}

	/**
	 * ������Ԫ�����ڵ�
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
	 * �Ƿ�ѡ����Ŀ���̽ڵ�
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
	 * �ж��Ƿ�ΪҶ�ӽڵ�
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
				break;
			}
		}
		return result;
	}

	/**
	 * ��ȡKDTableָ��rowIndex��colName�е�ֵ�������CellTreeNode�����CellTreeNode��ȡ��ֵ
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

	/**
	 * ���óɱ�ҳǩ��+-��ʾ
	 */
	private void setCostAccountDisplay() {
		int rowCount = this.kdtCostAccount.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtCostAccount.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			String costName = getCellValue(kdtCostAccount, i, "costName");
			if (costName != null) {
				costName = costName.trim();
			}
			if (costName != null && costName.toString().trim().length() > 0) {
				boolean isLeaf = true;
				if (i + 1 < rowCount) {
					String curCostNumber = getCellValue(kdtCostAccount, i, "costNumber");
					String nextCostNumber = getCellValue(kdtCostAccount, i + 1, "costNumber");
					if (nextCostNumber != null && curCostNumber != null && nextCostNumber.startsWith(curCostNumber + "!"))
						isLeaf = false;
				}
				row.getCell("costName").setValue(createCellTreeNode(costName, level, isLeaf));
			}
		}
	}

	/**
	 * �ɱ�ҳǩ���ڵ�cell����¼�
	 */
	protected void kdtCostAccount_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (colIndex == kdtCostAccount.getColumnIndex("costName") || e.getClickCount() == 1) {
			ICell cell = kdtCostAccount.getCell(rowIndex, colIndex);
			if (cell == null)
				return;
			Object value = cell.getValue();
			if (value == null)
				return;
			if (value instanceof CellTreeNode) {
				CellTreeNode node = (CellTreeNode) value;
				node.doTreeClick(kdtCostAccount, kdtCostAccount.getCell(rowIndex, colIndex));
			}
		}
	}

	/**
	 * �����Ƿ�Ϊָ��״̬
	 * 
	 * @return
	 */
	private boolean isBillStateEquals(FDCBillStateEnum state) {
		if (editData.getState() == null || state == null)
			return false;
		return editData.getState().equals(state);
	}

	/**
	 * �Ƿ������ʷ�汾
	 * @return false��ʾû����ʷ�汾
	 */
	private boolean hasHistoryVersion() {
		return getUIContext().containsKey(HAS_HISTRY_VERSION);
	}

	/**
	 * �л�������ť״̬
	 */
	private void switchButtonEnabled() {
		switchButtonVisible();
		if (!isSelectedProjectNode()) {//û��ѡ����Ŀ���̽ڵ�
			switchButtonEnableState(false);
			return;
		}

		switchButtonEnableState(true);
		if (oprtState.equals(OprtState.VIEW)) {
			btnImport.setEnabled(false);
			btnImportProject.setEnabled(false);
			if (!hasHistoryVersion()) {//û����ʷ����
				switchButtonEnableState(false);
				btnEdit.setEnabled(true);
			} else if (isBillStateEquals(FDCBillStateEnum.SAVED)) {
				btnModify.setEnabled(false);
				btnUnAudit.setEnabled(false);
			} else if (isBillStateEquals(FDCBillStateEnum.SUBMITTED)) {
				btnModify.setEnabled(false);
				btnUnAudit.setEnabled(false);
			} else if (isBillStateEquals(FDCBillStateEnum.AUDITTING)) {
				btnEdit.setEnabled(false);
				btnModify.setEnabled(false);
				actionNextPerson.setEnabled(true);
			} else if (isBillStateEquals(FDCBillStateEnum.AUDITTED)) {
				btnEdit.setEnabled(false);
				btnAudit.setEnabled(false);
				actionNextPerson.setVisible(false);
				actionNextPerson.setEnabled(false);
			}
			txtYjDays.setEnabled(false);
//			prmtYjPerson.setEnabled(false);
			prmtYjCost.setEnabled(false);
			prmtYjDesign.setEnabled(false);
			prmtYjMaterial.setEnabled(false);
			prmtYjProject.setEnabled(false);
		} else if (oprtState.equals(OprtState.ADDNEW)) {
			btnEdit.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			txtYjDays.setEnabled(true);
//			prmtYjPerson.setEnabled(true);
			prmtYjCost.setEnabled(true);
			prmtYjDesign.setEnabled(true);
			prmtYjMaterial.setEnabled(true);
			prmtYjProject.setEnabled(true);
		} else if (oprtState.equals(OprtState.EDIT)) {
			if (isBillStateEquals(FDCBillStateEnum.SUBMITTED)) {
				btnSave.setEnabled(false);
			}
			btnEdit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			txtYjDays.setEnabled(true);
//			prmtYjPerson.setEnabled(true);
			prmtYjCost.setEnabled(true);
			prmtYjDesign.setEnabled(true);
			prmtYjMaterial.setEnabled(true);
			prmtYjProject.setEnabled(true);
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#actionAddNew_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCTableHelper.checkSelectedAndAbort(this, kdtEntries);
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		this.kdtEntries.getEditManager().editingStopped();
		Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (rowIndex == -1) {
			FDCMsgBox.showInfo("��ѡ����");
			return;
		}
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();

		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
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
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("programming.id"));

		rowObject = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(rowObject.getId()), sic);

		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get(TREE_SELECTED_OBJ);
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		uiContext.put("programmingContract", rowObject);// �滮��Լ
		uiContext.put("pcCollection", pcCollection);// �滮��Լ����
		uiContext.put("project", project);// ������Ŀ
		uiContext.put("aimCostInfo", aimCostInfo);// Ŀ��ɱ�

		uiContext.put("editPayPlan", true);

		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null,
				STATUS_VIEW);
		uiWindow.show();
	}

	/**
	 * ���������ݽ���״̬��ȷ����ť�Ŀɼ���
	 * @modfiedBy��owen_wen
	 */
	private void switchButtonVisible() {
		boolean isViewOprtState = oprtState.equals(OprtState.VIEW);
		btnEdit.setVisible(isViewOprtState);
		btnModify.setVisible(isViewOprtState);
		btnSave.setVisible(!isViewOprtState);
		btnSubmit.setVisible(!isViewOprtState);
		btnAuditResult.setVisible(isViewOprtState);
		btnImport.setVisible(!isViewOprtState);
		btnImportProject.setVisible(!isViewOprtState);
		actionNextPerson.setVisible(isViewOprtState);//�鿴״̬�²ſɼ� added by Owen_wen 2012-3-30
	}

	private void switchButtonEnableState(boolean enabled) {
		btnEdit.setEnabled(enabled);
		btnSave.setEnabled(enabled);
		btnModify.setEnabled(enabled);
		btnSubmit.setEnabled(enabled);
		btnWorkFlowG.setEnabled(enabled);
		btnAuditResult.setEnabled(enabled);
		btnImport.setEnabled(enabled);
		btnImportProject.setEnabled(enabled);
		btnNextPerson.setEnabled(enabled);
		btnAudit.setEnabled(enabled);
		btnUnAudit.setEnabled(enabled);
		btnExport.setEnabled(enabled);

	}

	/**
	 * ������ʾ�İ汾��
	 * @return  
	 */
	private String getDisplayName() {
		if (editData == null || editData.getProject() == null 
				|| editData.getCreateTime() == null
				|| editData.getVersion()==null)
			return "";

		StringBuffer sb = new StringBuffer(80);
		String createTimeStr = "";
		if (editData.getCreateTime() != null) {
			createTimeStr = fmt_yyyyMMdd.format(new Date(editData.getCreateTime().getTime()));
		}
		String version = numberFormat.format(editData.getVersion());
		sb.append(editData.getProject().getName()).append("_").append(createTimeStr).append("_").append(version).append("��");
		return sb.toString();
	}

	private ProgrammingCollection historyVersions = null;

	/**
	 * ��ʷ�汾
	 */
	private void initHistoryMenuBar() {
		btnViewHistoryList.removeAllAssistButton();
		if (historyVersions == null || historyVersions.size() <= 1) {
			btnViewHistoryList.setEnabled(false);
			return;
		}
		if (!isSelectedProjectNode())
			return;
		btnViewHistoryList.setEnabled(true);
		KDMenuItem item = null;
		ProgrammingInfo info = null;
		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
		KDMenu menu = null;
		for (int i = 0; i < historyVersions.size(); i++) {
			info = historyVersions.get(i);
			if (info == null)
				continue;
			String text = treeNode.getText();
			StringBuffer sb = new StringBuffer(80);
			sb.append(text).append("_").append(fmt_yyyyMMdd.format(new Date(info.getCreateTime().getTime()))).append("_").append(
					numberFormat.format(info.getVersion())).append("��");
			item = new KDMenuItem();
			item.setText(sb.toString());
			item.setAccelerator(null);
			item.addActionListener(actionHistoryVersion);
			item.setUserObject(info);
			if (i > 9) {
				if (menu == null) {
					menu = new KDMenu();
					menu.setText(fmt_yyyy.format(new Date(info.getCreateTime().getTime())) + "��汾");
					btnViewHistoryList.addAssistMenuItem(menu);
				}
				menu.add(item);
			} else {
				btnViewHistoryList.addAssistMenuItem(item);
			}
		}
		historyVersions = null;// ������menu����Ϊnull
	}

	public void actionHistoryVersion_actionPerformed(ActionEvent e) throws Exception {
		if (e.getSource() instanceof KDMenuItem) {
			ProgrammingInfo info = (ProgrammingInfo) ((KDMenuItem) e.getSource()).getUserObject();
			if (info == null)
				return;
			getUIContext().put(HAS_HISTRY_VERSION, info.getVersion());
			setDataObject(info);
			this.getUIContext().put(ID, editData.getId().toString());
			prmtAimCost.removeDataChangeListener(dataChangeListener);// ˢ��ǰȡ������
			refreshCurrentData();// ����ˢ�·���������ϼ�������
			prmtAimCost.addDataChangeListener(dataChangeListener);
			setNameDisplay();
			setOprtState(OprtState.VIEW);
			switchButtonEnabled();
			
			/* modified by zhaoqin for R140507-0295 on 2014/05/15 */
			setAimCostVersionMsg();
		}
	}

	/**
	 * ��ȥ�ص�ǰ�汾���µ�Ŀ��ɱ�
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private AimCostInfo getGreaterCurrentVersionAimCostInfo() throws EASBizException, BOSException {
		AimCostInfo aimCost = editData.getAimCost();
		if (aimCost == null) {
			editData.setAimCost(temp);	// modified by zhaoqin on 2013/10/12
			this.has_aimCost = false;
			return this.temp;
		}
		//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		//		String curID = null;
		//		if (node.getUserObject() instanceof CurProjectInfo) {
		//			curID = ((CurProjectInfo) node.getUserObject()).getId().toString();
		//		}
		
		// modified by zhaoqin on 2013/10/28, ȡ���°汾��Ŀ��ɱ� isLastVersion=1
		StringBuffer oql = new StringBuffer("select id,versionNumber where orgOrProId='").append(aimCost.getOrgOrProId()).append("'")
		.append(" isLastVersion=1 and state='").append(FDCBillStateEnum.AUDITTED_VALUE).append("'")
		.append(" order by versionNumber desc");;
		AimCostCollection coll = AimCostFactory.getRemoteInstance().getAimCostCollection(oql.toString());
		BigDecimal currentVersion = FDCHelper.ZERO;
		//		if (aimCost != null) {
		currentVersion = new BigDecimal(aimCost.getVersionNumber());
		//		}
		BigDecimal loopVerson = FDCHelper.ZERO;
		for (int i = 0; i < coll.size(); i++) {
			loopVerson = new BigDecimal(coll.get(i).getVersionNumber());
			if (loopVerson.compareTo(currentVersion) > 0)
				return coll.get(i);
		}
		return null;
	}

	/**
	 * ��ȡ��Լ��ܼ���
	 * @return
	 */
	private ProgrammingContractCollection getKdtEntriesUserObjects() {
		ProgrammingContractCollection entries = new ProgrammingContractCollection();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo uObject = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			uObject.setLongNumber((String) kdtEntries.getCell(i, LONGNUMBER).getValue());
			entries.add(uObject);
		}
		return entries;
	}

	/**
	 * ���º�Լ�滮�滮���
	 * @param latestCostEntryColl
	 * @param curCostEntryColl 
	 * @param entries
	 */
	private void updateProgrammingContractCost(ProgrammingContractInfo pcInfo, CostEntryCollection latestCostEntryColl,
			CostEntryCollection curCostEntryColl) {
		if (pcInfo == null || latestCostEntryColl == null || curCostEntryColl == null)
			return;
		ProgrammingContracCostCollection pccColl = pcInfo.getCostEntries();
		BigDecimal amount2 = pcInfo.getAmount(); //��ǰ�滮���
		// ���º�Լ�滮 �ɱ����� ����Լ����
		BigDecimal currentTotalChange = FDCHelper.ZERO; //��¼��ǰ�仯��
		for (int i = 0; i < pccColl.size(); i++) {
			ProgrammingContracCostInfo pccInfo = pccColl.get(i);
			//			CostEntryInfo lastestCeInfo = getTargetCostEntryByCostAccount(latestCostEntryColl, pccInfo.getCostAccount());
			//			CostEntryInfo curCeInfo = getTargetCostEntryByCostAccount(curCostEntryColl, pccInfo.getCostAccount());
			//			BigDecimal lastAmount =  getTargetCostEntryByCostAccount(latestCostEntryColl, pccInfo.getCostAccount());
			//			BigDecimal curAmount =  getTargetCostEntryByCostAccount(curCostEntryColl, pccInfo.getCostAccount());
			//			if (lastestCeInfo != null && curCeInfo!=null) {
			BigDecimal lastestCostAmount = getTargetCostEntryByCostAccount(latestCostEntryColl, pccInfo.getCostAccount());// Ŀ��ɱ�
			BigDecimal currentCostAmount = getTargetCostEntryByCostAccount(curCostEntryColl, pccInfo.getCostAccount());// Ŀ��ɱ�
				if(lastestCostAmount.compareTo(currentCostAmount)==0 && has_aimCost)	// modified by zhaoqin on 2013/10/12
					continue;
				BigDecimal contractAssign = pccInfo.getContractAssign()==null ? FDCHelper.ZERO :pccInfo.getContractAssign(); //ԭ  ������Լ���䡯
				BigDecimal contractScale =pccInfo.getContractScale() == null?FDCHelper.ZERO :pccInfo.getContractScale(); // ����Լ����
				if(contractScale.compareTo(FDCHelper.ZERO)==0  ){
					//BigDecimal oldCostAmount = curCeInfo.getCostAccount()==null?FDCHelper.ZERO : curCeInfo.getCostAmount();// Ŀ��ɱ�
					BigDecimal oldCostAmount = currentCostAmount;
					
					contractScale = FDCHelper.toBigDecimal(FDCHelper.divide(contractAssign, oldCostAmount, 12, BigDecimal.ROUND_HALF_UP))
						.multiply(FDCHelper.ONE_HUNDRED);
				}
				BigDecimal newContractAssign = contractScale.multiply(lastestCostAmount).divide(FDCHelper.ONE_HUNDRED,2,BigDecimal.ROUND_HALF_UP); //���º�  ������Լ���䡯
				currentTotalChange=currentTotalChange.add(newContractAssign.subtract(contractAssign));
				contractScale=contractScale.divide(FDCHelper.ONE,0,BigDecimal.ROUND_HALF_UP);
				pccInfo.setCostAccount(pccInfo.getCostAccount()); //�ɱ���Ŀ
				pccInfo.setContractAssign(newContractAssign); //����Լ����
//				pccInfo.setAssigned(newContractAssign); 
				pccInfo.setContractScale(contractScale);
				pccInfo.setGoalCost(lastestCostAmount);
				
				//add by zkyan ����Ϊʲô����д
				//				pccInfo.setAssigning(lastestCostAmount.subtract(pccInfo.getAssigned()));

			BigDecimal multiply = FDCHelper.toBigDecimal(FDCHelper.divide(pccInfo.getAssigned(), currentCostAmount)).multiply(
					lastestCostAmount);//�ѷ�����ȱ�������
			BigDecimal multiply2 = FDCHelper.ZERO;
				
			//�޶�ʱ�����ԭ��Ŀ��Ϊ0���޶����������Ϊ�µ�Ŀ��ɱ�
			if (currentCostAmount.compareTo(FDCHelper.ZERO) == 0 || has_aimCost){	// modified by zhaoqin on 2013/10/12 
				multiply2 = lastestCostAmount;
			} else {
				multiply2 = FDCHelper.toBigDecimal(FDCHelper.divide(pccInfo.getAssigning(), currentCostAmount)).multiply(lastestCostAmount);//��������ȱ�������
			}
			pccInfo.setAssigning(multiply2);
			pccInfo.setAssigned(multiply);
			//			}
		}
		if (currentTotalChange.compareTo(FDCHelper.ZERO) != 0) {
			pcInfo.setAmount(FDCHelper.toBigDecimal(amount2).add(currentTotalChange));
			BigDecimal newControlAmount = FDCHelper.multiply(FDCHelper.add(amount2, currentTotalChange),FDCHelper.subtract(FDCHelper.ONE, pcInfo.getReservedChangeRate())); 
//			pcInfo.setControlBalance(FDCHelper.add(pcInfo.getControlBalance(), FDCHelper.subtract(newControlAmount, pcInfo.getControlAmount())));
			pcInfo.setControlAmount(newControlAmount);
			pcInfo.put(AMOUNT_CHANGE_FLAG, currentTotalChange);//��Ӱ��ĺ�Լ�滮
		}
	}

	/**
	 * ���ݡ��ɱ���Ŀ����Ŀ��ɱ���Ŀ�����л�ö�Ӧ�ġ�Ŀ��ɱ���Ŀ��
	 * @param costEntryColl
	 * @param costAccount
	 *  add by zkyan ����ͬһ��Ŀ�������ݵ���� ���޸�Ϊ������BigDecimal
	 * @return
	 */
	private BigDecimal getTargetCostEntryByCostAccount(CostEntryCollection costEntryColl, CostAccountInfo costAccount) {
		BigDecimal costAmount = FDCHelper.ZERO;
		if (costEntryColl == null || costEntryColl.size() == 0 || costAccount == null)
			return costAmount;
		for (int i = 0; i < costEntryColl.size(); i++) {
			CostEntryInfo ceInfo = costEntryColl.get(i);
			if (ceInfo == null || ceInfo.getCostAccount() == null)
				continue;
			CostAccountInfo costAccount2 = ceInfo.getCostAccount();
			if (costAccount2.getId().equals(costAccount.getId())) {
				if (costAmount.compareTo(FDCHelper.ZERO) != 0) {
					costAmount = FDCHelper.add(costAmount, ceInfo.getCostAmount());
				} else {
					costAmount = FDCHelper.toBigDecimal(ceInfo.getCostAmount());
				}
			}

		}
		return costAmount;
	}

	private void handleHisotryData() throws BOSException, EASBizException {
		Object object = getUIContext().get("ID");
		if (object != null) {
			IProgramming service = ProgrammingFactory.getRemoteInstance();
			SelectorItemCollection selectors = new SelectorItemCollection();
			// modify by lihaiou .2013.0730. fix bug R130723-0087,����̫��
			selectors.add("id");
			selectors.add("entries.workContent");
			selectors.add("entries.supMaterial");
			selectors.add("entries.description");
			selectors.add("entries.programming");
			selectors.add("entries.parent");
			selectors.add("entries.id");
			selectors.add("project.id");
			// modify end
			ProgrammingInfo pInfo = (ProgrammingInfo) service.getCoreBillBaseInfo(new ObjectUuidPK(object.toString()), selectors);
			ProgrammingContractCollection entries = pInfo.getEntries();
			boolean isChanged = false;
			for (int i = 0; i < entries.size(); i++) {
				ProgrammingContractInfo pcInfo = entries.get(i);
				StringBuffer sb = new StringBuffer();
				if (pcInfo.getWorkContent() != null && !pcInfo.getWorkContent().trim().equals("")) {
					sb.append(pcInfo.getWorkContent());
				}
				if (pcInfo.getSupMaterial() != null && !pcInfo.getSupMaterial().trim().equals("")) {
					sb.append("\n").append(pcInfo.getSupMaterial());
					isChanged = true;
					pcInfo.setSupMaterial(null);
				}
				if (pcInfo.getDescription() != null && !pcInfo.getDescription().trim().equals("")) {
					sb.append("\n").append(pcInfo.getDescription());
					isChanged = true;
					pcInfo.setDescription(null);
				}
				pcInfo.setWorkContent(sb.toString());
			}
			if (isChanged) {
				service.save(pInfo);
			}
		}
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
						if (c2[j] instanceof ProgrammingUI) {
							ProgrammingUI ptUI = (ProgrammingUI) c2[j];
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
		com.kingdee.eas.base.uiframe.client.NewMainFrame main = ((com.kingdee.eas.base.uiframe.client.NewMainFrame) getUIContext().get("Owner"));
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
	
	/**
	 * ���������״̬�ĵ��ݿ��Ա��� - modified by zhaoqin for R130819-0171 on 2013/09/11
	 * @Author��zhaoqin
	 * @CreateTime��2013-9-11
	 */
	private void activeSaveAction() {
		if (OprtState.EDIT.equals(getOprtState()) && editData.getState().equals(FDCBillStateEnum.SUBMITTED))
			actionSave.setEnabled(true);
	}
	
	/**
	 * ���ÿ�ܺ�Լ�б���ĩ����ܺ�Լ�Ĺ滮���״̬
	 * @author zhaoqin
	 * @date 2013/10/17
	 */
	private void setkdtEntries_AmountState() {
		if (!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT))
			return;
		
		for(int i = 0; i < this.editData.getEntries().size(); i++) {
			ProgrammingContractInfo pcInfo = this.editData.getEntries().get(i);
			setkdtEntries_AmountState1(i, pcInfo);
		}
	}
	
	/**
	 * ���ÿ�ܺ�Լ�б���ĩ����ܺ�Լ�Ĺ滮���״̬
	 * @author zhaoqin
	 * @date 2013/10/17
	 */
	private void setkdtEntries_AmountState1(int rowIndex, ProgrammingContractInfo pcInfo) {
//		if(null == pcInfo)
//			return;
//		if(null != pcInfo.getCostEntries() && pcInfo.getCostEntries().size() > 0)
//			this.kdtEntries.getCell(rowIndex, AMOUNT).getStyleAttributes().setLocked(true);
//		else
//			this.kdtEntries.getCell(rowIndex, AMOUNT).getStyleAttributes().setLocked(false);
	}
	
	/**
	 * ���º�Լ�滮�滮��� - modified by zhaoqin on 2013/10/22
	 * @author zhaoqin
	 * @date 2013/10/22
	 */
	private void updateProgrammingContractCost(ProgrammingContractInfo pcInfo, AimCostInfo latestAimCost,
			AimCostInfo curAimCost) throws BOSException {
		CostEntryCollection latestCostEntryColl = latestAimCost.getCostEntry();
		if (pcInfo == null || latestCostEntryColl == null)
			return;
		
		CostEntryCollection curCostEntryColl = null;
		if(null != curAimCost) {
			curCostEntryColl = curAimCost.getCostEntry();
		}
		
		ProgrammingContracCostCollection pccColl = pcInfo.getCostEntries();
		BigDecimal amount2 = pcInfo.getAmount(); //��ǰ�滮���
		// ���º�Լ�滮 �ɱ����� ����Լ����
		BigDecimal currentTotalChange = FDCHelper.ZERO; //��¼��ǰ�仯��
		for (int i = 0; i < pccColl.size(); i++) {
			ProgrammingContracCostInfo pccInfo = pccColl.get(i);
			String curProjectId = pccInfo.getCostAccount().getCurProject().getId().toString();

			// ��ǰ�ɱ���Ŀ�Ƿ����ڴ˹�����Ŀ��Ӧ��Ŀ��ɱ�
			if(!curProjectId.equals(latestAimCost.getOrgOrProId()))
				continue;
			pccInfo.setAimCostId(latestAimCost.getId().toString());	// ���°汾��Ŀ��ɱ�
			
			BigDecimal lastestCostAmount = getTargetCostEntryByCostAccount(latestCostEntryColl, pccInfo.getCostAccount());// Ŀ��ɱ�
			BigDecimal currentCostAmount = getTargetCostEntryByCostAccount(curCostEntryColl, pccInfo.getCostAccount());// Ŀ��ɱ�
			if(lastestCostAmount.compareTo(currentCostAmount)==0 && has_aimCost)	// modified by zhaoqin on 2013/10/12
				continue;
			BigDecimal contractAssign = pccInfo.getContractAssign()==null ? FDCHelper.ZERO :pccInfo.getContractAssign(); //ԭ  ������Լ���䡯
			
			/* modified by zhaoqin for R131210-0246 on 2013/12/16 start */
			/*
			BigDecimal contractScale =pccInfo.getContractScale() == null?FDCHelper.ZERO :pccInfo.getContractScale(); // ����Լ����
			if(contractScale.compareTo(FDCHelper.ZERO)==0  ){
				BigDecimal oldCostAmount = currentCostAmount;
				contractScale = FDCHelper.toBigDecimal(FDCHelper.divide(contractAssign, oldCostAmount, 12, BigDecimal.ROUND_HALF_UP))
					.multiply(FDCHelper.ONE_HUNDRED);
			}
			*/
			//��� contractAssign Ϊ 0����contractScale�򱣳�ԭֵ���� - R141028-0053 on 2014/12/26
			BigDecimal contractScale = null;
			if(FDCHelper.compareTo(contractAssign, FDCHelper.ZERO) == 0) {
				contractScale = null == pccInfo.getContractScale() ? FDCHelper.ZERO : pccInfo.getContractScale(); // ����Լ����
			} else {
				contractScale = FDCHelper.toBigDecimal(FDCHelper.divide(contractAssign.multiply(FDCHelper.ONE_HUNDRED), 
						currentCostAmount, 12, BigDecimal.ROUND_HALF_UP));
			}
			contractScale = null == contractScale ? FDCHelper.ZERO : contractScale;
			/* modified by zhaoqin for R131210-0246 on 2013/12/16 end */
			
			BigDecimal newContractAssign = contractScale.multiply(lastestCostAmount).divide(
					FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP); //���º�  ������Լ���䡯
			//currentTotalChange=currentTotalChange.add(newContractAssign.subtract(contractAssign));
			//contractScale=contractScale.divide(FDCHelper.ONE,0,BigDecimal.ROUND_HALF_UP);
			pccInfo.setCostAccount(pccInfo.getCostAccount()); //�ɱ���Ŀ
			pccInfo.setContractAssign(newContractAssign); //����Լ����
			pccInfo.setContractScale(contractScale);
			pccInfo.setGoalCost(lastestCostAmount);
			/*
			BigDecimal multiply = FDCHelper.toBigDecimal(FDCHelper.divide(pccInfo.getAssigned(),
					currentCostAmount)).multiply(lastestCostAmount);//�ѷ�����ȱ�������
			BigDecimal multiply2;
			if (currentCostAmount.compareTo(FDCHelper.ZERO) == 0 || multiply.compareTo(FDCHelper.ZERO) == 0
					|| contractScale.compareTo(FDCHelper.ONE_HUNDRED) == 0){
				multiply2 = lastestCostAmount;
			} else {
				multiply2 = FDCHelper.toBigDecimal(FDCHelper.divide(pccInfo.getAssigning(), 
						currentCostAmount)).multiply(lastestCostAmount);//��������ȱ�������
			}
			pccInfo.setAssigning(multiply2);
			pccInfo.setAssigned(multiply);
			*/
			// �ѷ������
			BigDecimal allAssignedScale = getAllContractAssignScale(this.editData.getProject(), 
					pcInfo, pccInfo.getCostAccount());
			// �ѷ���
			BigDecimal assigned = lastestCostAmount.multiply(allAssignedScale).divide(
					FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
			// ������
			BigDecimal assigning = lastestCostAmount.subtract(assigned);
			pccInfo.setAssigned(assigned);
			pccInfo.setAssigning(assigning);
			BigDecimal assingingSacle = FDCHelper.ONE_HUNDRED.subtract(allAssignedScale);
			// "������"���� С��ԭ "����Լ����" or "������" С��"����Լ����"
			if(contractScale.compareTo(assingingSacle) > 0 || newContractAssign.compareTo(assigning) > 0) {
				pccInfo.setContractScale(assingingSacle);
				pccInfo.setContractAssign(assigning);
			}
			currentTotalChange=currentTotalChange.add(pccInfo.getContractAssign().subtract(contractAssign));
		}
		if(currentTotalChange.compareTo(FDCHelper.ZERO)!=0){
			pcInfo.setAmount(FDCHelper.add(amount2, currentTotalChange));
			pcInfo.put(AMOUNT_CHANGE_FLAG, currentTotalChange);//��Ӱ��ĺ�Լ�滮
		}
	}
	
	/**
	 * ��ȡ��ǰ��ܺ�Լ�ĳɱ������б������й�����Ŀid
	 * @author zhaoqin
	 * @date 2013/10/22
	 */
	private Map getAllProjectAndAimCostIds(String progId) throws BOSException {
		// CurProject.ID - AimCostInfo
		Map proAimCostid = new HashMap();
		
		SelectorItemCollection selector = new SelectorItemCollection();
		//selector.add("id");
		selector.add("aimCostId");
		selector.add("costAccount.curProject.id");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.programming.id", progId));
		view.setFilter(filter);
		view.setSelector(selector);
		
		ProgrammingContracCostCollection coll = ProgrammingContracCostFactory.getRemoteInstance().getProgrammingContracCostCollection(view);
		if(null == coll || coll.size() == 0)
			return proAimCostid;

		Set aimCostids = new HashSet();
		for(int i = 0; i < coll.size(); i++) {
			ProgrammingContracCostInfo pccInfo = coll.get(i);
			if(null == pccInfo.getCostAccount() || null == pccInfo.getCostAccount().getCurProject())
				continue;
			if(!StringUtils.isEmpty(pccInfo.getAimCostId()))
				aimCostids.add(pccInfo.getAimCostId());
			else // ��ʷ�����У�����ֶ�aimCostIdû��ֵ
				proAimCostid.put(pccInfo.getCostAccount().getCurProject().getId().toString(), null);
		}
		
		if(aimCostids.size() == 0)
			return proAimCostid;
		
		selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("versionNumber");
		selector.add("orgOrProId");
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", aimCostids, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(selector);
		AimCostCollection colls = AimCostFactory.getRemoteInstance().getAimCostCollection(view);
		for(int j = 0; j < colls.size(); j++) {
			AimCostInfo info = colls.get(j);
			// CurProject.ID - AimCostInfo
			proAimCostid.put(info.getOrgOrProId(), info);
		}
		// CurProject.ID - AimCostInfo
		return proAimCostid;
	}
	
	/**
	 * ��ȥ�ȵ�ǰ�汾���µ�Ŀ��ɱ�
	 * @author zhaoqin
	 * @date 2013/10/22
	 */
	private Map getGreaterCurrentVersionAimCostInfo(Map proAimCostid) throws EASBizException, BOSException {
		// newAimCostId - oldAimCostId
		Map newerAimCostIDs = new HashMap();
		AimCostInfo aimCost = editData.getAimCost();
		
		// proAimCostid.size() > 0: ��ǰ������Ŀû�й�����Լ�滮�����п���Ŀ��Ŀ��ɱ�
		if (aimCost == null && proAimCostid.size() == 0) {
			if(null == temp) {
				return newerAimCostIDs;
			}
			editData.setAimCost(temp);
			this.has_aimCost = false;
			newerAimCostIDs.put(this.temp.getId().toString(), this.temp.getId().toString());
			proAimCostid.put(this.editData.getProject().getId().toString(), this.temp);
			
			updateAimCostIdForCostEntries(this.editData.getProject(), this.temp);
			return newerAimCostIDs;
		}
		// curProjectId - AimCost
		if(null != aimCost) {
			proAimCostid.put(aimCost.getOrgOrProId(), aimCost);
			updateAimCostIdForCostEntries(this.editData.getProject(), aimCost);
		}
		
		Set projectIDs = new HashSet();
		Iterator itr = proAimCostid.keySet().iterator();
		while(itr.hasNext()) {
			projectIDs.add(itr.next());
		}
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("versionNumber");
		selector.add("orgOrProId");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", projectIDs, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
		view.setFilter(filter);
		view.setSelector(selector);
		AimCostCollection coll = AimCostFactory.getRemoteInstance().getAimCostCollection(view);
		
		Iterator iterator = proAimCostid.keySet().iterator();
		while(iterator.hasNext()) {
			String projectid = (String)iterator.next();
			AimCostInfo info = (AimCostInfo)proAimCostid.get(projectid);
			for(int i=0;i<coll.size();i++){
				AimCostInfo temp = coll.get(i);
				if(!temp.getOrgOrProId().equals(projectid))
					continue;
				if(null == info) {	// ������ʷ����
					newerAimCostIDs.put(temp.getId().toString(), null);
				} else if(!info.getId().toString().equals(temp.getId().toString())) {
					// newAimCostId - oldAimCostId
					newerAimCostIDs.put(temp.getId().toString(), info.getId().toString());
				}
				break;
			}
		}
		return newerAimCostIDs;
	}
	
	/**
	 * ���ɱ�Ŀ���Ƿ����°汾 ����ȷ�ϸ��� (�������Ŀ�ĺ�Լ�滮)
	 * @author zhaoqin
	 * @date 2013/10/22
	 */
	private void updateProgrammingContract1(String progId) throws BOSException, EASBizException {
		Map proAimCostid = getAllProjectAndAimCostIds(progId);
		Map newerAimCostIDs = getGreaterCurrentVersionAimCostInfo(proAimCostid);
		
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 start */
		String isModify = (String)getUIContext().get(IS_MODIFY);
		if (newerAimCostIDs.size() == 0) {
			if(!StringUtils.isEmpty(isModify) && isModify.equals(IS_MODIFY)) {
				editData.setVersion(editData.getVersion().add(new BigDecimal("0.1")));
			}
			return;
		}
		if (MsgBox.showConfirm2("Ŀ��ɱ����°汾���Ƿ���Ҫ���º�Լ�滮ֵ��") != MsgBox.YES) {
			if(!StringUtils.isEmpty(isModify) && isModify.equals(IS_MODIFY)) {
				editData.setVersion(editData.getVersion().add(new BigDecimal("0.1")));
			}
			return;
		} else {
			if(!StringUtils.isEmpty(isModify) && isModify.equals(IS_MODIFY)) {
				BigDecimal version = editData.getVersion();
				version = version.setScale(0, BigDecimal.ROUND_DOWN).add(new BigDecimal("1.0"));
				editData.setVersion(version);
			}
		}
		/* modified by zhaoqin for R140507-0295 on 2014/05/13 end */
		
		AimCostInfo latestAimCost = this.editData.getAimCost();
		
		SelectorItemCollection selecter = new SelectorItemCollection();
		selecter.add("*");
		selecter.add("costEntry.*");
		selecter.add("costEntry.costAccount.*");
		selecter.add("costEntry.costAccount.curProject.*");
		IAimCost service = AimCostFactory.getRemoteInstance();
		ProgrammingContractCollection entries = getKdtEntriesUserObjects();
		Iterator iterator = newerAimCostIDs.keySet().iterator();
		while(iterator.hasNext()) {
			String newId = (String)iterator.next();	// ���°汾Ŀ��ɱ�id
			String curId = (String)newerAimCostIDs.get(newId);	// ��ǰ�汾��ӦĿ��ɱ�id
			AimCostInfo info = service.getAimCostInfo(new ObjectUuidPK(newId), selecter);//���°汾Ŀ��ɱ�
			AimCostInfo currentAimCost = null;	//��ǰ�汾��ӦĿ��ɱ�
			if(!StringUtils.isEmpty(curId)) {
				currentAimCost = service.getAimCostInfo(new ObjectUuidPK(curId), selecter);
			}
			
			if(this.editData.getProject().getId().toString().equals(info.getOrgOrProId()))
				latestAimCost = info;
			for (int i = 0; i < entries.size(); i++) {
				updateProgrammingContractCost(entries.get(i), info, currentAimCost);
			}
		}

		editData.setAimCost(latestAimCost);
		// ��Ӱ���ܺ�Լ�Ժ�ɫ������ʾ
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++) {
			//ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContractInfo pcInfo = entries.get(i);
			if (pcInfo.containsKey(AMOUNT_CHANGE_FLAG)) {
				kdtEntries.getCell(i, AMOUNT).getStyleAttributes().setFontColor(Color.red);
				kdtEntries.getCell(i, AMOUNT).setValue(pcInfo.getAmount());
				BigDecimal occurAmount=FDCHelper.ZERO;
				Object occurAmountObj = kdtEntries.getCell(i, OCCUR_AMOUNT).getValue();
				if(occurAmountObj!=null){
					occurAmount=(BigDecimal)occurAmountObj;
				}
				kdtEntries.getCell(i, BALANCE).setValue(pcInfo.getAmount().subtract(occurAmount));
			}
			Object value = kdtEntries.getCell(i, "level").getValue();
			int level=1;
			if(value!=null){
				level=new Integer(value.toString()).intValue();
			}
			caclTotalAmount(i, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex("controlAmount"), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex(BALANCE), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex(CONTROL_BALANCE), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex(OCCUR_AMOUNT), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex("buildPerSquare"), level);
			caclTotalAmount(i, kdtEntries.getColumnIndex("soldPerSquare"), level);
		}
		prmtAimCost.removeDataChangeListener(dataChangeListener);
		prmtAimCost.setValue(latestAimCost);
		prmtAimCost.addDataChangeListener(dataChangeListener);
	}
	
	// ���³ɱ����ɷ�¼��aimCostId�ֶ�
	private void updateAimCostIdForCostEntries(CurProjectInfo project, AimCostInfo aimCost) {
		for(int i = 0; i < this.editData.getEntries().size(); i++) {
			ProgrammingContractInfo pcInfo = this.editData.getEntries().get(i);
			if(null == pcInfo) 
				continue;
			for(int j = 0; j < pcInfo.getCostEntries().size(); j++) {
				ProgrammingContracCostInfo pccInfo = pcInfo.getCostEntries().get(j);
				if(null != pccInfo && null != pccInfo.getCostAccount() 
						&& null != pccInfo.getCostAccount().getCurProject()
						&& project.getId().toString().equals(pccInfo.getCostAccount().getCurProject().getId().toString()))
					pccInfo.setAimCostId(aimCost.getId().toString());
			}
		}
	}
	
	/**
	 * ȡ�óɱ���Ŀ��"����Լ����"����֮��
	 * @author zhaoqin
	 * @date 2013/10/25
	 */
	private BigDecimal getAllContractAssignScale(CurProjectInfo project, 
			ProgrammingContractInfo pcInfo, CostAccountInfo caInfo) throws BOSException {
		String projectId = project.getId().toString();
		String costAccountId = caInfo.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
		/* modified by zhaoqin for R131212-0339 on 2013/12/16 */
		//builder.appendSql("select sum(cost.fcontractAssign) as assigned,sum(cost.FContractScale) as assignedScale ");
		builder.appendSql("select cost.fcontractAssign as contractAssign,cost.FContractScale as ContractScale ,cost.FGoalCost as GoalCost ");
		
		builder.appendSql("from T_CON_ProgrammingContracCost as cost ");
		builder.appendSql("join T_CON_ProgrammingContract as con on con.fid= cost.FContractID ");
		builder.appendSql("join T_CON_Programming as pro on pro.fid = con.FProgrammingID ");
		builder.appendSql("join (select max(pro.fversion) as fver,pro.fprojectid as proid");
		//builder.appendSql(" from T_CON_ProgrammingContracCost as cost");
		//builder.appendSql(" join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
		//builder.appendSql(" join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
		builder.appendSql(" from T_CON_Programming as pro ");
		builder.appendSql(" where pro.fprojectid<>'").appendSql(projectId).appendSql("' ");	// ��������ǰ������Ŀ
		//builder.appendSql(" and cost.fcostAccountid='").appendSql(costAccountId).appendSql("' ");
		builder.appendSql(" group by pro.fprojectid");
		builder.appendSql(") t on t.fver=pro.fversion and t.proid = pro.fprojectid ");
		builder.appendSql("where cost.fcostAccountid='").appendSql(costAccountId).appendSql("'");

		BigDecimal allContractScale = FDCHelper.ZERO;
		IRowSet rowSet = builder.executeQuery();
		try {
			/* modified by zhaoqin for R131212-0339 on 2013/12/16 start */
			/*if (rowSet.next()) {
				allContractScale = FDCHelper.toBigDecimal(rowSet.getBigDecimal("assignedScale"));
			}*/
			while(rowSet.next()) {
				BigDecimal contractAssign = rowSet.getBigDecimal("contractAssign");
				BigDecimal goalCost = rowSet.getBigDecimal("GoalCost");
				if(null == goalCost || goalCost.signum() == 0) {
					if (null != rowSet.getBigDecimal("ContractScale")) {
						allContractScale = allContractScale.add(rowSet.getBigDecimal("ContractScale"));
					}
				} else {
					allContractScale = allContractScale.add(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, 
							FDCHelper.divide(contractAssign, goalCost, 10, BigDecimal.ROUND_HALF_UP)));
				}
			}
			/* modified by zhaoqin for R131212-0339 on 2013/12/16 end */
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		if(null == allContractScale)
			allContractScale = FDCHelper.ZERO;
		
		ProgrammingContractCollection pcCollection = getKdtEntriesUserObjects();
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (programmingContractInfo.getId().toString().equals(pcInfo.getId().toString())) 
				continue;
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
				if (null != costAccountInfo && null != costAccountInfo.getId()) {
					if (costAccountInfo.getId().toString().equals(caInfo.getId().toString())) {
						/* modified by zhaoqin for R131212-0339 on 2013/12/24 start */
						BigDecimal contractScale = pccInfo.getContractScale();
						BigDecimal contractAssign = pccInfo.getContractAssign();
						BigDecimal goalCost = pccInfo.getGoalCost();
						if(null == goalCost || goalCost.signum() == 0) {
							if (null != contractScale) {
								allContractScale = allContractScale.add(contractScale);
							}
						} else {
							allContractScale = allContractScale.add(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, 
									FDCHelper.divide(contractAssign, goalCost, 10, BigDecimal.ROUND_HALF_UP)));
						}
						/* modified by zhaoqin for R131212-0339 on 2013/12/24 end */
					}
				}
			}
		}
		return allContractScale;
	}
	
	/**
	 * ��̬���±���Լ�滮�� �ɱ�����"������"ֵ
	 * @author zhaoqin
	 * @date 2013/11/8
	 */
	private void dynamicUpdateAssigned() throws BOSException  {
		// ��̬���³ɱ�����"������"��ֵ
		ProgrammingContractCollection pcCollection = getKdtEntriesUserObjects();
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo pcInfo = pcCollection.get(i);
			ProgrammingContracCostCollection pccCollection = pcInfo.getCostEntries();// �ɱ����ɼ���
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);// �ɱ�����
				BigDecimal oldContractAssign = pccInfo.getContractAssign();// ����Լ����
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();// �ɱ���Ŀ
				BigDecimal goalCost = pccInfo.getGoalCost();// Ŀ��ɱ�
				if (costAccountInfo != null) {
					BigDecimal allAssignScale = getAllContractAssignScale(this.editData.getProject(),pcInfo,costAccountInfo);
					if (oldContractAssign != null) {
						BigDecimal newAssigned = goalCost.multiply(allAssignScale).divide(
								FDCHelper.ONE_HUNDRED, 12, BigDecimal.ROUND_HALF_UP);
						pccInfo.setAssigned(newAssigned);
						pccInfo.setAssigning(goalCost.subtract(newAssigned));
					}
				}
			}
		}
	}
	
	/**
	 * modified for R140507-0214
	 * @author RD_zhaoqin
	 * @date 2014/05/13
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		treeMain_valueChanged(null);
	}
	
	/**
	 * ��ʾĿ��ɱ��汾��Ϣ - modified for R140507-0295
	 * @author RD_zhaoqin
	 * @throws  
	 * @date 2014/05/14
	 */
	private void setAimCostVersionMsg() throws BOSException, EASBizException {
		//modified by yxl 20150811
		if(null == this.editData || null == this.editData.getId()){
			txtCostVersionInfo.setText(null);
			return;
		}
		String id = this.editData.getId().toString();
//		StringBuilder msg = new StringBuilder("Ŀ��ɱ��汾��Ϣ��");
		StringBuilder msg = new StringBuilder();
		int initLeng = msg.length();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select ac.fversionnumber,ac.fversionname from T_CON_Programming p join T_AIM_AimCost ac on ac.fid = p.faimcostid ");
		builder.appendSql("where p.fid = '").appendSql(id).appendSql("' ");
		builder.appendSql("union ");
		builder.appendSql("select ac.fversionnumber,ac.fversionname from T_CON_Programming p join T_CON_ProgrammingContract pc on pc.fprogrammingid = p.fid ");
		builder.appendSql("join T_CON_ProgrammingContracCost pcc on pcc.fcontractid = pc.fid join T_AIM_AimCost ac on ac.fid = pcc.faimcostid ");
		builder.appendSql("where p.fid = '").appendSql(id).appendSql("' ");
		builder.appendSql("order by fversionname");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()) {
				msg.append(rs.getString("fversionname")).append(rs.getString("fversionnumber")).append(", ");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		if(msg.length() > initLeng) {
			msg.delete(msg.length() - 2, msg.length() - 1);
		}
//		labelExplain.setText(msg.toString());
		txtCostVersionInfo.setText(msg.toString());
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitParam()
	 */
	@Override
	protected void fetchInitParam() throws Exception {
		// TODO Auto-generated method stub
		super.fetchInitParam();

		if (company == null) {
			// return null;
			return;
		}
		
		/**
		 * FDC228_ISSTRICTCONTROL ��ͬǩԼ������֮�����ĺ�Լ�滮���ʱ�Ƿ��ϸ���� ������Ϊ���ϸ���ơ�ʱ0����ͬ�ύʱ���������Լ�滮����ͬ�����ڿ��ƽ��ʱ����ͬ�������ύ��
		 * ������Ϊ����ʾ���ơ�ʱ1����ͬ�ύʱ���������Լ�滮����ͬ�����ں�Լ���ƽ��ʱ����ͬ�����ύ�� ������Ϊ�������ơ�ʱ2�������κ��жϡ�Ĭ��ֵΪ����ʾ����
		 */
		Map paramItem = (Map) ActionCache.get("FDCBillEditUIHandler.comParamItem");
		if (paramItem == null || paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) == null) {
			paramIsstrictcontrol = FDCUtils.getFDCParamByKey(null, company.getId().toString(),
					FDCConstants.FDC_PARAM_CONTRACT_PROGRAM_AMOUNT);

			
		} else if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			paramIsstrictcontrol = paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString();
		}
	}
}