/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.port.pm.invest.investplan.IProgramming;
import com.kingdee.eas.port.pm.invest.investplan.InvestPlanDetailFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingException;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invest.uitls.CreateProTableRow;
import com.kingdee.eas.port.pm.utils.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.Uuid;

/** 
 * output class name
 */
public class ProgrammingEditUI extends AbstractProgrammingEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingEditUI.class);
    private CreateProTableRow create = new CreateProTableRow(dataBinder);//��¼��������
	private DataChangeListener dataChangeListener = null;
	private EntryTreeSumField sumClass = new EntryTreeSumField();
    private final String LONGNUMBER = "longNumber";//������
    private final String HEADNUMBER = "headNumber";//����������
    private final String BALANCE = "balance";
	private final String AMOUNT = "amount";
	private final String CONTROLAMOUNT = "controlAmount";
    protected KDWorkButton btnAddnewLine;
    protected KDWorkButton btnInsertLines;
    protected KDWorkButton btnRemoveLines;
    protected KDWorkButton btnDetails;
    protected KDWorkButton btnImports;
    protected KDWorkButton btnExports;
    protected BigDecimal totalBuildArea;
    
	public ProgrammingEditUI() throws Exception {
		super();
	}
    public void onLoad() throws Exception {
    	this.kdtCompareEntry.checkParsed();
    	this.kdtVerCompareEntry.checkParsed();
    	this.kdtVerCompareEntry.setVisible(false);
		this.kdtCompareEntry.getColumn("programmingContract").getStyleAttributes().setLocked(true);
		this.kdtCompareEntry.getColumn("content").getStyleAttributes().setLocked(true);
		this.kdtCompareEntry.getColumn("programmingContract").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
		this.kdtCompareEntry.getColumn("content").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
		txtBuildArea.setEnabled(false);
		this.kdtEntries.getColumn("investProportion").getStyleAttributes().setNumberFormat("#,##0.00 %");
		txtSaleArea.setEnabled(false);
		kDTabbedPane1.remove(pnlCostAccount);
		this.kdtCostAccount.getStyleAttributes().setLocked(true);
		this.kdtCostAccount.getStyleAttributes().setHided(true);
		this.kdtEntries.getColumn("costAccount").getStyleAttributes().setHided(true);
		this.kdtEntries.getColumn("costAccount").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("isInvite").getStyleAttributes().setHided(true);
		this.kdtEntries.getColumn("balance").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("investProportion").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("isCiting").getStyleAttributes().setHided(true);
		this.kdtEntries.getColumn("cumulativeInvest").getStyleAttributes().setLocked(true);
    	super.onLoad();
    	txtVersion.setPrecision(1);
		initTable();
		setAttachmentRenderer();
    	setSmallButton();
    	setSmallBtnEnable();
    	initData();
    	setMouseClick();

		if (this.getUIContext().get("modify") != null) {
			// �޶�����¸�Ͷ�ʹ滮����ID
			ProgrammingEntryInfo programmingEntryInfo = new ProgrammingEntryInfo();
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				String longNumber=kdtEntries.getCell(i, "longNumber").getValue().toString();
				ProgrammingEntryInfo proConInfo = (ProgrammingEntryInfo) kdtEntries.getRow(i).getUserObject();
				BOSUuid newid=BOSUuid.create(programmingEntryInfo.getBOSType());
				kdtEntries.getCell(i, "id").setValue(newid);
				kdtEntries.getCell(i, "isCiting").setValue(Boolean.valueOf(proConInfo.isIsCiting()));
				kdtEntries.getCell(i, "isWTCiting").setValue(Boolean.valueOf(proConInfo.isIsWTCiting()));
				for(int j=0;j<kdtEntries.getRowCount();j++){
					ProgrammingEntryInfo parent=((ProgrammingEntryInfo)kdtEntries.getRow(j).getUserObject()).getParent();
					if(parent!=null&&parent.getLongNumber().equals(longNumber)){
						((ProgrammingEntryInfo)kdtEntries.getRow(j).getUserObject()).setParent(proConInfo);
					}
				}
				
			}
			this.actionImport.setEnabled(false);
			
			this.txtDescription.setText(null);
			this.kdtCompareEntry.removeRows();
		}
		KDWorkButton compare=new KDWorkButton();
		this.actionCompare.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		compare = (KDWorkButton)this.conCompare.add(this.actionCompare);
		compare.setText("��ȡ");
		compare.setSize(new Dimension(140, 19));
		if(this.txtVersion.getBigDecimalValue()!=null&&this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))==0){
			this.kDTabbedPane1.remove(this.conCompare);
			this.kDTabbedPane1.remove(this.kDScrollPane1);
		}
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(10);
		colorPanel.setLayout(flowLayout);
		drawALogo("����",new Color(248,171,166));
		drawALogo("����",new Color(163,207,98));
		
    }
    
    protected void drawALogo(String name, Color color) {
		KDLabel lable = new KDLabel(name);
		KDLabel colorLable = new KDLabel();
		Dimension d = new Dimension(40, 10);
		colorLable.setPreferredSize(d);
		colorLable.setOpaque(true);
		colorLable.setBackground(color);
		colorPanel.add(lable);
		colorPanel.add(colorLable);

	}
    protected void initListener() {
    	super.initListener();
    	dataChangeListener = new DataChangeListener() {
    		public void dataChanged(DataChangeEvent e) {
    		}
    	};
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
	 * 2.���º󣬰ѳɱ����д��и�"������"ֵ����Ӧ��Ͷ�ʹ滮�ڿ�ܽ����¼���ú�ɫ��־�����������ú�ɫ���壩
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
	 * �� ԭ"��Ͷ�ʹ滮����" = ԭ"Ŀ��ɱ�" ��̬���� "��Ͷ�ʹ滮����" = ��"Ŀ��ɱ�"��
	 * 
	 */
    private void changeAimCost() {
		kdtEntries.getEditManager().editingStopped();
		Map isAssignAimCost=new HashMap();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingEntryInfo programmingEntryInfo = (ProgrammingEntryInfo) kdtEntries.getRow(i).getUserObject();
			programmingEntryInfo.setLongNumber((String) kdtEntries.getCell(i, LONGNUMBER).getValue());
			programmingEntryInfo.setName((String) kdtEntries.getCell(i, "name").getValue());
			programmingEntryInfo.setAmount(FDCHelper.toBigDecimal(kdtEntries.getCell(i, "amount").getValue()));
			programmingEntryInfo.setControlAmount(FDCHelper.toBigDecimal(kdtEntries.getCell(i, "controlAmount").getValue()));
			programmingEntryInfo.setControlBalance(FDCHelper.toBigDecimal(kdtEntries.getCell(i, "controlBalance").getValue()));
			ProgrammingEntryCostEntryCollection costEntries = programmingEntryInfo.getCostEntries();
			ProgrammingEntryEconomyEntryCollection economyEntries = programmingEntryInfo.getEconomyEntries();

			BigDecimal newAmount = FDCHelper.ZERO;// �滮���
			BigDecimal oldAmount = programmingEntryInfo.getAmount();
			BigDecimal oldBalance = programmingEntryInfo.getBalance();// ԭ�滮���

			if (costEntries.size() == 0) {
				newAmount = programmingEntryInfo.getAmount();// ԭ�滮���
			} else {
				int flagAllNoChange = 0;
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingEntryCostEntryInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccount = pccInfo.getCostAccount();// �ɱ���Ŀ
					// ��ȡԭ"�ѷ���"��ԭ"Ŀ��ɱ�","��Ͷ�ʹ滮����"
					BigDecimal oldAssigned = pccInfo.getAssigned();
					BigDecimal oldGoalCost = pccInfo.getGoalCost();
					BigDecimal oldContractAssign = pccInfo.getContractAssign();
					// ��ȡ��"Ŀ��ɱ�"
					BigDecimal newGoalCost = FDCHelper.ZERO;
						Set isAssignCostAccount=new HashSet();
						isAssignCostAccount.add(costAccount.getId().toString());
					if (oldGoalCost.compareTo(oldContractAssign) == 0) {
						// ��ԭ"Ŀ��ɱ�"=ԭ"��Ͷ�ʹ滮����"������"��Ͷ�ʹ滮����" = ��"Ŀ��ɱ�"
						pccInfo.setContractAssign(newGoalCost);
						// ��� ��"������" = ��"Ŀ��ɱ�"
						pccInfo.setAssigning(newGoalCost);
						newAmount = newAmount.add(newGoalCost);
					} else {
						flagAllNoChange++;
						// ��ԭ"Ŀ��ɱ�"!=ԭ"��Ͷ�ʹ滮����"������"��Ͷ�ʹ滮����" ����
						// ��� ��"������" = ��"Ŀ��ɱ�" - ԭ"�ѷ���"
						BigDecimal newAssigning = newGoalCost.subtract(oldAssigned);
						pccInfo.setAssigning(newAssigning);
						newAmount = newAmount.add(pccInfo.getContractAssign());
						if (costEntries.size() == flagAllNoChange) {
							newAmount = oldAmount;
						}
					}
				}
				// �滮��̬����
				programmingEntryInfo.setAmount(newAmount);
				// ���ƽ�� = �滮���
				programmingEntryInfo.setControlAmount(newAmount);
				// �滮��̬����
				
				programmingEntryInfo.setBalance(oldBalance.add(newAmount.subtract(oldAmount)));
				// ��������"������"��̬����
				for (int k = 0; k < economyEntries.size(); k++) {
					ProgrammingEntryEconomyEntryInfo pceInfo = economyEntries.get(k);
					BigDecimal scale = pceInfo.getScale();
					pceInfo.setAmount(FDCHelper.divide(newAmount.multiply(scale), FDCHelper.ONE_HUNDRED));
				}
				dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(i), programmingEntryInfo);
				int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level != 1) {
					// ����
					caclTotalAmount(i, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(i, kdtEntries.getColumnIndex("balance"), level);
				}
				setMyFontColor();
			}
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
	
	
    
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			this.actionCompare.setEnabled(true);
			this.actionComAddRow.setEnabled(true);
			this.actionComInsertRow.setEnabled(true);
			this.actionComRemoveRow.setEnabled(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			this.actionCompare.setEnabled(true);
			this.actionComAddRow.setEnabled(true);
			this.actionComInsertRow.setEnabled(true);
			this.actionComRemoveRow.setEnabled(true);
			changeActoinState(false);
			actionImport.setEnabled(true);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			this.actionCompare.setEnabled(false);
			this.actionComAddRow.setEnabled(false);
			this.actionComInsertRow.setEnabled(false);
			this.actionComRemoveRow.setEnabled(false);
			changeActoinState(false);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
			this.actionCompare.setEnabled(false);
			this.actionComAddRow.setEnabled(false);
			this.actionComInsertRow.setEnabled(false);
			this.actionComRemoveRow.setEnabled(false);
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
	//ǰһ�汾���Ͷ�ʹ滮ID
	private List oldProgId = new ArrayList();
	private void initData() throws Exception {
		if (isBillModify()) {
			for(Iterator it = editData.getEntries().iterator() ; it.hasNext();){
				oldProgId.add(((ProgrammingEntryInfo)it.next()).getId());
			}
			actionCopy_actionPerformed(null);
			int cou = 0;
			for(Iterator it = editData.getEntries().iterator(); it.hasNext();){
				ProgrammingEntryInfo entry=(ProgrammingEntryInfo)it.next();
				entry.setSrcId(oldProgId.get(cou).toString());
				cou++;
			}
			createTree();
		}
		this.btnExport.setEnabled(true);
		Object node = getUIContext().get("treeSelectedObj");
    	if(node != null)
    		curProject = (ProjectInfo)node;
		
		if(editData.getCurProject() == null){
			if(curProject != null){
				editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
			}
		}
		
    }
	
    
	//�޶�ʱ�ÿ��ֶ�ֵ
    protected void setFieldsNull(AbstractObjectValue newData) {
    	ProgrammingInfo info = (ProgrammingInfo) newData;
    	String number = getDateString();
    	info.setNumber(number);
    	txtNumber.setText(number);
    	inputVersion(info);
    	txtVersion.setText(info.getVersion().toString());
    	info.setState(FDCBillStateEnum.SAVED);
    }
	
    /**
     * ���÷�¼ʧȥ���㼰��¼��ť�Ƿ����
     */
	private void setKDTableLostFocus(){
		kdtEntries.getEditManager().stopEditing();
		kdtEntries.getSelectManager().remove();
		kdtEntries.getSelectManager().setActiveRowIndex(-1);
		btnAddnewLine.setEnabled(false);
		btnRemoveLines.setEnabled(false);
		btnDetails.setEnabled(false);
		btnImports.setEnabled(false);
		btnExports.setEnabled(false);
	}

	/**
	 * ����ʱ�Ա���������
	 */
	private void initTable() {
		kdtEntries.checkParsed();
		kdtEntries.getColumn("id").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("level").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("longName").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isCiting").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isWTCiting").getStyleAttributes().setHided(true);
		// �滮������������ָ�ʽ����λС��
		cellToFormattedText(kdtEntries, "balance");
		cellToFormattedText(kdtEntries, "investAmount");
		cellToFormattedText(kdtEntries , AMOUNT);
		cellToFormattedText(kdtEntries , "cumulativeInvest");
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(1024);
		kdtEntries.getColumn("remark").setEditor(cellEditor);
//		kdtEntries.getColumn("headNumber").getStyleAttributes().setHided(false);
		
		kdtEntries.getColumn(AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("cumulativeInvest").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("balance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("investAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		kdtEntries.getColumn("buildPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("soldPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setSelector(new ContractTypePromptSelector(this));
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntries.getColumn("contractType").setEditor(f7Editor);
		this.kdtEntries.getColumn("contractType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		KDCheckBox hit = new KDCheckBox();
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtEntries.getColumn("isInvite").setEditor(editor);
 		if(FDCBillStateEnum.AUDITTED.equals(this.editData.getState())&&this.oprtState.equals(OprtState.VIEW)
 				&&this.editData.isIsLatest()){
 			this.kdtEntries.setEditable(true);
 			this.actionEditInvite.setEnabled(true);
 			for(int i=0;i<this.kdtEntries.getColumnCount();i++){
 				if(this.kdtEntries.getColumnKey(i).equals("isInvite")){
 					this.kdtEntries.getColumn(i).getStyleAttributes().setLocked(false);
 				}else{
 					this.kdtEntries.getColumn(i).getStyleAttributes().setLocked(true);
 				}
 			}
 		}else{
 			this.kdtEntries.getColumn("isInvite").getStyleAttributes().setLocked(true);
 			this.actionEditInvite.setEnabled(false);
 		}
// 		kdtEntries.getColumn("buildPrice").getStyleAttributes().setLocked(true);
// 		kdtEntries.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
// 		kdtEntries.getColumn("quantities").getStyleAttributes().setLocked(true);
// 		kdtEntries.getColumn("quantities").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
// 		kdtEntries.getColumn("unit").getStyleAttributes().setLocked(true);
// 		kdtEntries.getColumn("price").getStyleAttributes().setLocked(true);
// 		kdtEntries.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}
	public void actionEditInvite_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("�Ƿ�ȷ���޸ģ�") == MsgBox.CANCEL) {
			return;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("entries.isInvite"));
		ProgrammingFactory.getRemoteInstance().updatePartial(editData, sic);
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
		//��������ʱ������������
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		//��Ԫ�����ģʽ
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		kdtEntries.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		if (!OprtState.ADDNEW.equals(getOprtState())) {
			createTree();
			setNameDisplay();
			handleOldData();
		}
		if (!OprtState.VIEW.equals(getOprtState())) {
			setCellEditorForTable();
		}
		setTableFontColor();
		cellAttachment();
		
		setTableRenderer();
		sumClass.caclTotalAmount(kdtEntries);
		setMyFontColor();
		
		for(int i=0;i<this.kdtEntries.getRowCount();i++){
			String compare=(String)this.kdtEntries.getRow(i).getCell("compare").getValue();
			if(compare!=null){
				if(compare.equals("red")){
					this.kdtEntries.getRow(i).getStyleAttributes().setBackground(new Color(248,171,166));
				}else{
					this.kdtEntries.getRow(i).getStyleAttributes().setBackground(new Color(163,207,98));
				}
			}
		}
		try {
			setBuildPrice();
			
			
			Object node = getUIContext().get("treeSelectedObj");
	    	if(node != null)
	    	{
	    		curProject = (ProjectInfo)node;
	    		editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
	    	}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		
		setHeadRowColor();
	}
	private void setBuildPrice() throws BOSException{
		if(OprtState.VIEW.equals(getOprtState())){
			return;
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
//		if(this.editData.getCurProject()==null){
//			Object node = getUIContext().get("treeSelectedObj");
//	    	filter.getFilterItems().add(new FilterItemInfo("project.id",((ProjectInfo)node).getId().toString()));
//		}else{
//			filter.getFilterItems().add(new FilterItemInfo("project.id",this.editData.getProject().getId().toString()));
//		}
		
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		view.setFilter(filter);
		for(int i=0;i<this.kdtEntries.getRowCount();i++){
			if(i+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					continue;
				}
				Object nextHeadNumber = kdtEntries.getCell(i+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					continue;
				}
			}
//			kdtEntries.getRow(i).getCell("buildPrice").setValue(FDCHelper.divide(kdtEntries.getRow(i).getCell("amount").getValue(), this.totalBuildArea, 2, BigDecimal.ROUND_HALF_UP));
//			if((Boolean)kdtEntries.getRow(i).getCell("isInput").getValue()){
//		 		kdtEntries.getRow(i).getCell("quantities").getStyleAttributes().setLocked(false);
//		 		kdtEntries.getRow(i).getCell("unit").getStyleAttributes().setLocked(false);
//		 		kdtEntries.getRow(i).getCell("quantities").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
//		 		kdtEntries.getRow(i).getCell("unit").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
//			}else{
//				kdtEntries.getRow(i).getCell("quantities").setValue(null);
//				kdtEntries.getRow(i).getCell("unit").setValue(null);
//				kdtEntries.getRow(i).getCell("price").setValue(null);
//				kdtEntries.getRow(i).getCell("quantities").getStyleAttributes().setBackground(Color.WHITE);
//		 		kdtEntries.getRow(i).getCell("unit").getStyleAttributes().setBackground(Color.WHITE);
//			}
		}
	}
	
	/**
	 * ���ý���ֶ���ʾ��ʽ
	 */
	private void setTableRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					if("0.00".equals(o.toString())){
						return "0";
					}
				}else{
					return "0";
				}
				return o.toString();
			}
		});
		kdtEntries.getColumn("investAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("amount").setRenderer(objectValueRender);
		kdtEntries.getColumn("balance").setRenderer(objectValueRender);
		kdtEntries.getColumn("cumulativeInvest").setRenderer(objectValueRender);
		kdtEntries.getColumn("signUpAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("changeAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("settleAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("buildPerSquare").setRenderer(objectValueRender);
		kdtEntries.getColumn("soldPerSquare").setRenderer(objectValueRender);	
		kdtEntries.getColumn("estimateAmount").setRenderer(objectValueRender);	
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
	 * ��ȡͶ�ʹ滮������и��������ַ������������ֳ���","���
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		System.out.println("sql:" + fdcBuilder.getSql().toString());
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
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sb;
	}
	
	public void onShow() throws Exception {
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
		
		super.onShow();
		//���ñ�ͷ����
		kdtEntries.addKDTMouseListener(new KDTSortManager(kdtEntries));
		kdtEntries.getSortMange().setSortAuto(false);
		
		setAuditBtnEnable();
		kdtEntries.getFootManager().getFootRow(0).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("estimateAmount").getStyleAttributes().setHided(true);
	}
	
	/**
	 * ����������ť��ʾ
	 */
	private void setAuditBtnEnable(){
		if(editData.getState() == null){
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}else if(FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(false);
			actionSave.setEnabled(false);
		}else if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
		}
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		if (this.oprtState.equals(STATUS_EDIT)) {
			// boolean lastVersion = ((IProgramming) getBizInterface()).isLastVersion(
			// new ObjectUuidPK(editData.getId()));
			boolean isAudit = FDCBillStateEnum.AUDITTED.equals(editData.getState())
					|| FDCBillStateEnum.AUDITTING.equals(editData.getState());
			if (!isBillModify()) {
				actionAudit.setEnabled(!isAudit);
				actionUnAudit.setEnabled(isAudit);
				actionImport.setEnabled(!isAudit);
			}
		}
	}
	
	private void changeActoinState(boolean flag) {
		actionAudit.setEnabled(flag);
		actionUnAudit.setEnabled(flag);
		actionSubmit.setEnabled(flag);
		actionImport.setEnabled(flag);
	}
	
    /**
     * ����ǩ��������������롢ɾ������ϸ��Ϣ��ť
     */
    private void setSmallButton(){
    	btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
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
    	btnImports = new KDWorkButton();
    	btnExports = new KDWorkButton();
    	
    	
    	btnDetails.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    actionDetails_actionPerformed(e);
                }
                catch (Exception e1){
                	logger.error("detials" , e1);
                }
            }});
    	
    	btnImports.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    actionImports_actionPerformed(e);
                }
                catch (Exception e1){
                	logger.error("imports" , e1);
                }
            }});
    	
    	btnExports.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    actionExportPro_actionPerformed(e);
                }
                catch (Exception e1){
                	logger.error("exports" , e1);
                }
            }});
    	
    	btnAddnewLine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                	actionAddnewLine_actionPerformed(e);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }});
    	
    	btnInsertLines.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLines_actionPerformed(arg0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
    		
    	});
    	
    	btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLines_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
    	
//    	setButtonStyle(btnAddnewLine,"������","imgTbtn_addline");
//    	setButtonStyle(btnRemoveLines,"ɾ����","imgTbtn_deleteline");
//    	setButtonStyle(btnInsertLines,"������","imgTbtn_insert");
//    	setButtonStyle(btnDetails,"��ϸ��Ϣ","imgTbtn_particular");
    	
    	setButtonStyle(btnInsertLines,"�������Ͷ�ʹ滮(ͬ��)","imgTbtn_insert");
    	setButtonStyle(btnAddnewLine,"�����¼����Ͷ�ʹ滮","imgTbtn_addline");
    	setButtonStyle(btnRemoveLines,"ɾ����","imgTbtn_deleteline");
    	setButtonStyle(btnDetails,"��ϸ��Ϣ","imgTbtn_particular");
    	setButtonStyle(btnImports, "����ģ��", "imgTbtn_input");
    	setButtonStyle(btnExports, "���Ϊģ��", "imgTbtn_output");
    }
    
    private void setButtionEnable(boolean isEnable){
    	btnAddnewLine.setEnabled(isEnable);
		btnInsertLines.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
		btnImports.setEnabled(isEnable);
		btnExports.setEnabled(isEnable);
    }
    
    //���ð�ť��ʾЧ��
    private void setButtonStyle(KDWorkButton button , String text , String icon){
    	button.setText(text);
    	button.setToolTipText(text);
    	button.setVisible(true);
    	button.setIcon(EASResource.getIcon(icon));
    	conProgramming.addButton(button);
    }
    
    //����������ɾ���������¼��ť�Ƿ����
    private void setSmallBtnEnable(){
    	btnExports.setEnabled(true);
    	if(OprtState.VIEW.equals(getOprtState()))
    	{
    		setButtionEnable(false);
    		if(kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0){
    			btnDetails.setEnabled(false);
    		}else{
    			btnDetails.setEnabled(true);
    		}
    	}else{
    		btnInsertLines.setEnabled(true);
    		btnImports.setEnabled(true);
    		if(kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0){
    			btnAddnewLine.setEnabled(false);
    			btnRemoveLines.setEnabled(false);
    			btnDetails.setEnabled(false);
    		}else{
    			btnAddnewLine.setEnabled(true);
    			btnRemoveLines.setEnabled(true);
    			btnDetails.setEnabled(true);
    		}
    	}
    }
    
    
    Map bkMap = new HashMap();
    /**
     * 
     * �޸�Ϊ�����¼����Ͷ�ʹ滮
     * @param e
     * @throws Exception
     */
    public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception{
    	//У��Ŀ��ɱ��汾�ű���
    	checkAimCostNotNull();
    	
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntries.getRowCount();
    	
    	int row = -1;
    	if(rowIndex < 0){
    		//�±�Ϊ0����������
    		create.addLine(kdtEntries, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
    		this.storeFields();
    		
    		Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntries.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntries.getCell(rowIndex, "longName").getValue();
    		Boolean isInvite=(Boolean)kdtEntries.getRow(rowIndex).getCell("isInvite").getValue();
    		boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(rowIndex, "isWTCiting").getValue()).booleanValue();
    		for(int i = rowIndex +1 ; i < kdtEntries.getRowCount(); i++){//�¼���������Ҳ���������¼�
    			if(o.equals(kdtEntries.getCell(i, HEADNUMBER).getValue())){
    				isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue();
    				if(isCiting){
    					break;
    				}
    			}
    		}
        	if(isCiting){
        		MsgBox.showInfo("���Ͷ�ʹ滮�������޷������¼���");
        		return;
        	}
        	
    		ProgrammingEntryInfo headObject = (ProgrammingEntryInfo)kdtEntries.getRow(rowIndex).getUserObject();
    		headObject.setContractType((ContractTypeInfo)kdtEntries.getCell(rowIndex, "contractType").getValue());
    		int newLevel = 0;
    		//����ʱ�ж������Ƿ�Ϸ�
    		if(o == null || o.toString().trim().length() == 0){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���벻��Ϊ�գ�");
    			return;
    		}else if((o.toString().trim()+".").length() >= 80){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮�������\n���޸ĺ��������Ӽ����Ͷ�ʹ滮��");
    			return;
    		}else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���Ʋ���Ϊ�գ�");
    			return;
    		}else{
    			String ln = o.toString();
    			//
    			row = getInsertRowIndex(o , rowIndex , rowCount);
    			newLevel = new Integer(headlevel.toString()).intValue()+1 ;
    			//����Ѿ����¼�����clone�ϼ������ݣ�
    			//��� �������� = ��ǰѡ���� + 1�� �� ��Ҫcloneѡ���е����ݴ��������С��������κδ��� 
    			if(rowIndex +1 == row){
    				create.insertLine(kdtEntries , row , newLevel, headObject);//clone
    			}else{
    				create.insertSameLine(kdtEntries , row , newLevel, headObject);//��������
    			}
    			if(kdtEntries.getCell(rowIndex+1, HEADNUMBER)!=null){
    				Object checkHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
    				if( checkHeadNumber!=null && ln.equals(checkHeadNumber)){
        				create.insertSameLine(kdtEntries , row , newLevel, headObject);
        			} else {
        				create.insertLine(kdtEntries , row , newLevel, headObject);
        			}
    			} else {
    				create.insertLine(kdtEntries , row , newLevel, headObject);
    			}
    			
    			kdtEntries.getCell(row, HEADNUMBER).setValue(o);
    			if(longName != null)
    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
    		}
    		//���»��ܵ��ϼ�
    		caclTotalAmount(row, kdtEntries.getColumnIndex("amount"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("controlAmount"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("balance"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("controlBalance"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("buildPerSquare"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("soldPerSquare"), newLevel);
			
			kdtEntries.getCell(row, "isInvite").setValue(isInvite);
    	}
    	//���ñ����еı༭��ʽ�������ϼ����벻���޸�
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntries.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
		
		formatName(row);
		
    	createTree();

    	setSmallBtnEnable();
    	
    	setHeadRowColor();
	}

	private void checkAimCostNotNull() {
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
     * 2011.11.30�޸�Ϊ����ͬ��Ͷ�ʹ滮��� 
     * �߼�˵����
     * 1����û��Ͷ�ʹ滮����� ����һ��
     * 2����ѡ����һ����¼ ��������ͬ����ͬ���
     * @param e
     * @throws Exception
     */
    public void actionInsertLines_actionPerformed(ActionEvent e) throws Exception{
    	checkAimCostNotNull();
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntries.getRowCount();
    	int row = -1;
    	
    	if(rowIndex < 0){
    		//�±�Ϊ0����������
    		create.addLine(kdtEntries, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
    		this.storeFields();
    		
    		Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntries.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntries.getCell(rowIndex, "longName").getValue();
    		boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue();
    		
    		ProgrammingEntryInfo headObject = (ProgrammingEntryInfo)kdtEntries.getRow(rowIndex).getUserObject();
    		
    		int activelevel = new Integer(headlevel.toString()).intValue();
    		if(activelevel == 1 ){ //�����һ��������һ�е����
    			create.insertSameLine(kdtEntries , rowCount , 1 , null);
    			row = rowCount;
    		}else if(o == null || o.toString().trim().length() == 0){//����ʱ�ж������Ƿ�Ϸ�
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���벻��Ϊ�գ�");
    			return;
    		}else if((o.toString().trim()+".").length() >= 80){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮�������\n���޸ĺ��������Ӽ����Ͷ�ʹ滮��");
    			return;
    		}else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���Ʋ���Ϊ�գ�");
    			return;
    		} else{
//    			if(isCiting){
//            		MsgBox.showInfo("���Ͷ�ʹ滮�������޷�����ͬ����");
//            		return;
//            	}
    			String ln = o.toString();
    			if(ln.length() == (head.toString().length() + 1)){
    				MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���벻��Ϊ�գ�");
        			return;
    			}
    			if(rowIndex+1 <kdtEntries.getRowCount()){
    				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
    				if(oldLongNumber.equals(nextHeadNumber)){
    					FDCMsgBox.showInfo("����ϸĿ¼��������ͬ����");
    					return;
    				}
    			}
    			row = getInsertRowIndexSameLevel(rowIndex , rowCount);
//    			create.insertSameLine(kdtEntries , row ,activelevel , headObject.getParent());
    			
    			ProgrammingEntryInfo newDetailInfo = new ProgrammingEntryInfo();
		        newDetailInfo.setId(BOSUuid.create("ECE079DB"));
		        newDetailInfo.setLevel(activelevel);
		        newDetailInfo.setBalance(FDCHelper.ZERO);
			    newDetailInfo.setControlBalance(FDCHelper.ZERO);
			    newDetailInfo.setAmount(FDCHelper.ZERO);
			    newDetailInfo.setControlAmount(FDCHelper.ZERO);
			    newDetailInfo.setSignUpAmount(FDCHelper.ZERO);
			    newDetailInfo.setChangeAmount(FDCHelper.ZERO);
			    newDetailInfo.setSettleAmount(FDCHelper.ZERO);
	        	newDetailInfo.setParent(headObject.getParent());
    		        
	        	IRow addRow = kdtEntries.addRow(row);
	        	create.loadLineFields(kdtEntries, addRow, newDetailInfo);
	            
    			kdtEntries.getCell(row, HEADNUMBER).setValue(head);
    			if(longName != null)
    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
    			
    			
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), activelevel);
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), activelevel);
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), activelevel);
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), activelevel);
    		}
    	}
    	//���ñ����еı༭��ʽ�������ϼ����벻���޸�
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntries.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
		
		formatName(row);
		
    	createTree();

    	setSmallBtnEnable();
		
		
	
//    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
//    	Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
//    	Object name = kdtEntries.getCell(rowIndex, "name").getValue();
//    	if(o == null || o.toString().trim().length() == 0){
//			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���벻��Ϊ�գ�");
//			return;
//		}else if(name == null || StringUtils.isEmpty(name.toString())){
//			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У����Ͷ�ʹ滮���Ʋ���Ϊ�գ�");
//			return;
//		}
//    	Object headNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
//		Object level = kdtEntries.getCell(rowIndex, "level").getValue();
//		ProgrammingEntryInfo headObject = (ProgrammingEntryInfo)kdtEntries.getRow(rowIndex).getUserObject();
//    	create.insertLine(kdtEntries , rowIndex , new Integer(level.toString()).intValue() , headObject.getParent());
//		kdtEntries.getCell(rowIndex, HEADNUMBER).setValue(headNumber);
//		if(headObject.getParent() != null && headObject.getParent().getDisplayName() != null){
//			kdtEntries.getCell(rowIndex, "longName").setValue(headObject.getParent().getDisplayName().toString().trim()+".");
//		}
//		KDTextField txtLongNumber = new KDTextField();
//		LimitedTextDocument document = new LimitedTextDocument("");
//		txtLongNumber.setDocument(document);
//		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
//		kdtEntries.getCell(rowIndex, LONGNUMBER).setEditor(cellEditorNumber);
//		formatName(rowIndex);
//		createTree();
    	
    	setHeadRowColor();
    }

	/**
     * ���ɾ���а�ť
     * @param e
     * @throws Exception
     */
    public void actionRemoveLines_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = this.kdtEntries.getSelectManager().getActiveRowIndex();
    	Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	Object h = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	
    	boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(rowIndex, "isWTCiting").getValue()).booleanValue();
    	if(isCiting){
    		MsgBox.showInfo("���ڱ����õĿ��Ͷ�ʹ滮��"+longNumber.toString()+"��,�޷�ɾ����");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	if(longNumber != null){
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString() , rowIndex , list);
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
    	
		if(list.size() > 1){
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"����ǰɾ���ĸ��ڵ㡰"+longNumber.toString()+"���»��������Ŀ��Ͷ�ʹ滮��ȷ��Ҫһ��ɾ����")){
				create.removeLine(kdtEntries, list);
				if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "balance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlBalance").setValue(FDCHelper.ZERO);
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
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
		}else{
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"�Ƿ�ȷ��ɾ�����ݣ�")){
		    	create.removeLine(kdtEntries,rowIndex);
		    	if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "balance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlBalance").setValue(FDCHelper.ZERO);
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
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
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
		
		//ɾ��ʱ��Ҫ���¶Խ����л���
		int loop = getLoop(rowIndex, h);
		if (loop > 0) {
			int level = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			ProgrammingEntryInfo proConInfo = (ProgrammingEntryInfo) kdtEntries.getRow(loop).getUserObject();
			ProgrammingEntryCostEntryCollection costEntries = proConInfo.getCostEntries();
			if (costEntries.size() == 0) {
				if (!isHasSomeLevel) {
					kdtEntries.getCell(loop, "amount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlAmount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "balance").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlBalance").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "soldPerSquare").setValue(FDCHelper.ZERO);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("balance"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlBalance"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
				} else {
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("balance"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("controlBalance"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
			}
		}
		sumClass.caclTotalAmount(kdtEntries);
		sumClass.appendProFootRow(null,null);
		setSmallBtnEnable();
		dataBinder.storeFields();
		createTree();
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
		
		if(!isHasSame){
			if(rowIndex == kdtEntries.getRowCount()){
				loop = rowIndex - 1 ;
			}else{
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
    public void actionDetails_actionPerformed(ActionEvent e) throws Exception{
    	checkAimCostNotNull();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		this.kdtEntries.getEditManager().editingStopped();
		Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (rowIndex == -1) {
			FDCMsgBox.showInfo("��ѡ����");
			return;
		}
		if(oldLongNumber == null){
			FDCMsgBox.showInfo("���벻��Ϊ�գ�");
			return;
		}
		//У���Ƿ����¼����������û����ϸ��
		if(rowIndex+1 <kdtEntries.getRowCount()){
			Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
			if(oldLongNumber.equals(nextHeadNumber)){
				FDCMsgBox.showInfo("����ϸĿ¼û����ϸ��Ϣ��");
				return;
			}
		}
		Object headNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
		if(headNumber!=null && oldLongNumber !=null){
			if(headNumber.toString().length()+1 == oldLongNumber.toString().length()){
				FDCMsgBox.showInfo("��������д���룡");
				return;
			}
		}
		if(name == null){
			FDCMsgBox.showInfo("���Ʋ���Ϊ�գ�");
			return;
		}
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingEntryInfo rowObject = (ProgrammingEntryInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingEntryCollection pcCollection = getPCCollection();
		ProjectInfo project = (ProjectInfo) this.getUIContext().get("treeSelectedObj");
		uiContext.put("programmingContract", rowObject);// �滮Ͷ�ʹ滮
		uiContext.put("pcCollection", pcCollection);// �滮Ͷ�ʹ滮����
		uiContext.put("project", project);// ������Ŀ
		
		String oprstate = OprtState.ADDNEW;
		
		String oql = "where sourceBillId='"+rowObject.getId()+"'";
		if(InvestPlanDetailFactory.getRemoteInstance().exists(oql))
		{
			uiContext.put("ID", InvestPlanDetailFactory.getRemoteInstance().getInvestPlanDetailCollection(oql).get(0).getId());
			oprstate = OprtState.VIEW;
		}
		else
			uiContext.put("SourceBillId", rowObject.getId());
		uiContext.put("proNumber", this.txtDescription.getText());
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InvestPlanDetailEditUI.class.getName(), uiContext, null,oprstate);
		uiWindow.show();
		
		
		// �����ݵ���¼��
		dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
		// ���³�����
		setEntriesNameCol(rowIndex, level);
		// ���³�����
		Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldLongNumber != null && newLongNumber != null) {
			if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
				setEntriesNumberCol(rowIndex, level);
			}
		}
		// ���¹滮���,�滮���,���ƽ��������Ļ���
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("buildPerSquare"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("soldPerSquare"), level);
		// �����ж����Ƿ���д������ݣ�����������ú���ɫ���Ե��������ú���ɫ
		setMyFontColor();
	}

	/**
	 * �����ж����Ƿ���д������ݣ�����������ú���ɫ���Ե��������ú���ɫ
	 * @param rowIndex
	 */
	private void setMyFontColor() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			boolean flag = false;// falseĬ�ϱ�ʾû�д�������
			ProgrammingEntryInfo programmingEntryInfo = (ProgrammingEntryInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingEntryCostEntryCollection pccCollection = programmingEntryInfo.getCostEntries();
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingEntryCostEntryInfo pccInfo = pccCollection.get(j);
				BigDecimal assigning = pccInfo.getAssigning();// ������
				BigDecimal contractAssign = pccInfo.getContractAssign();// ��Ͷ�ʹ滮����
				// ��"��Ͷ�ʹ滮����">"������"����ʾ�д�������
//				if (contractAssign.compareTo(assigning) > 0&&!programmingEntryInfo.isIsCiting()&&!programmingEntryInfo.isIsWTCiting()) {
//					flag = true;
//				}
			}
			if (flag) {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.RED);
			} else {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
				Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
				if (blanceValue != null) {
					BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
					if (blance.compareTo(FDCHelper.ZERO) > 0) {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.blue);
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
	 * ��ȡ��ѡ�е������Ӽ��ڵ㡢���ж��Ƿ��б����õĿ��Ͷ�ʹ滮
	 * @param longNumber
	 * @param rowIndex
	 * @param list
	 */
    private void getSublevel(String longNumber , int rowIndex , ArrayList list){
    	int rowCount = kdtEntries.getRowCount();
    	for(int i = rowIndex+1 ; i < rowCount ; i++){
			Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
			if(headNumber != null && headNumber.toString() != null){
				if(headNumber.toString().startsWith(longNumber)){
					boolean isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue();
			    	if(isCiting){
			    		MsgBox.showInfo("���ڱ����õĿ��Ͷ�ʹ滮��"+l.toString()+"��,�޷�ɾ����");
			    		SysUtil.abort();
			    	}
					list.add(new Integer(i));
				}else{
					break;
				}
			}
    	}
    }
    
    /**
     * ����ʱ��������
     */
    private void createTree(){
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
     * ��ȡҪ�����е�λ�ã��±꣩����ͬ����ʱ��ʹ��
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    protected int getInsertRowIndexSameLevel(int rowIndex , int rowCount){
    	int row = 0;
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
		for(int i = rowIndex ; i < rowCount ; ++i){//Ѱ�ұ��������һ��
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 < level) {
				return i;
			}
		}
		return row == 0 ? rowCount : row;
    
    }
    /**
     * ��ȡҪ�����е�λ�ã��±꣩
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    protected int getInsertRowIndex(Object o , int rowIndex , int rowCount){
    	int row = 0;
    	String longNumber = o.toString();
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
    	
		for(int i = rowIndex+1 ; i < rowCount ; i++){
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 == level || level_2 < level) {
				return i;
			}
			Object n = kdtEntries.getCell(i, LONGNUMBER).getValue();
			if(n != null && n.toString() != null){
				if(!n.toString().startsWith(longNumber)){
					row = i;
					break;
				}
			}else{
				return i+1;
			}
			
			if(rowIndex + 2 == rowCount){
				return rowCount;
			}
		}
		return row == 0 ? rowCount : row;
    }
    
    protected void kdtEntries_editStarting(KDTEditEvent e) throws Exception {
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		Object longNumber = kdtEntries.getCell(e.getRowIndex(), LONGNUMBER).getValue();
			if(longNumber != null && longNumber.toString().trim().length() > 80){
				MsgBox.showInfo("��¼�� "+(e.getRowIndex()+1)+" �У����Ͷ�ʹ滮���볬��\n���޸��ϼ�������ٽ��б༭��");
				kdtEntries.getEditManager().cancelEditing();
				e.setCancel(true);
			}
    	}
    }
    
    protected void kdtEntries_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	setSmallBtnEnable();
    }
    
    protected void kdtEntries_editStarted(KDTEditEvent e) throws Exception {
    	//����༭���Ǳ������������ȡ�����еı༭���������ϼ����벻���Ա�ɾ�����޸�
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    		ICellEditor editor = kdtEntries.getCell(rowIndex, LONGNUMBER).getEditor();
    		if(editor != null){
    			if(editor instanceof KDTDefaultCellEditor){
		    		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    	    		KDTextField txtLongNumber = (KDTextField) de.getComponent();
    	    		LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
    	    		txtLongNumber.setMaxLength(80);
    	    		String txt = "";
    	    		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	    		Object subNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	    		if(longNumber == null || longNumber.toString().trim().length() == 0){
    	    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    	    				txt = subNumber.toString().trim()+".";
    	    				kdtEntries.getCell(rowIndex, LONGNUMBER).setValue(txt);
    	    			}
    	    		}
    	    		else{
    	    			txt = longNumber.toString().trim();
    	    		}
    	    		if(level > 1){
    	    			doc.setLimitedText(subNumber.toString().trim()+".");
    	    			doc.setIsOnload(true);
    	    			doc.setIsAutoUpdate(true);
    	    			txtLongNumber.setText(txt);
    	    			doc.setIsOnload(false);
    	    			doc.setIsAutoUpdate(false);
    	    		}else{
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
    	//���ñ���
    	Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue == null && newValue == null){
			return;
		}
		
		
		this.dataBinder.storeFields();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		int colIndex = e.getColIndex();
		
		//------------------------------
		String key = kdtEntries.getColumnKey(e.getColIndex());
		if (key.equals("amount")||key.equals("investAmount")||key.equals("cumulativeInvest")) {
			Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if(oldLongNumber == null){
				FDCMsgBox.showInfo("����д���룡");
				kdtEntries.getRow(rowIndex).getCell(key).setValue(e.getOldValue());
				return;
			}
			Object nextHeadNumber = kdtEntries.getRowCount()!=rowIndex+1?kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue():"";
			if(oldLongNumber.equals(nextHeadNumber)){
				FDCMsgBox.showInfo("����ϸĿ¼����¼���");
				kdtEntries.getRow(rowIndex).getCell(key).setValue(e.getOldValue());
				kdtEntries.getRow(rowIndex).getCell(key).getStyleAttributes().setFontColor(Color.gray);
				return;
			}
			IRow row = this.kdtEntries.getRow(e.getRowIndex());
			int newLevel = UIRuleUtil.getInt(row.getCell("level").getValue());
			
			
			caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex(key), newLevel);
			caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("balance"), newLevel);
			
			if(UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"investAmount").getValue())).
					compareTo(UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"amount").getValue())))>0)
			{
				FDCMsgBox.showInfo("����Ƚ��ܳ����ܽ�");
				this.kdtEntries.getCell(rowIndex,"investAmount").setValue(new BigDecimal (0.00));
				this.kdtEntries.getCell(rowIndex+1, HEADNUMBER).setValue(0);
			}
			
			if((UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"investAmount").getValue()))).
					add(UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"cumulativeInvest").getValue()))).
					compareTo(UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"amount").getValue())))>0)
			{
				FDCMsgBox.showInfo("����Ƚ�����ۼƽ��֮�Ͳ��ܳ����ܽ�");
				this.kdtEntries.getCell(rowIndex,"investAmount").setValue(0);
				this.kdtEntries.getCell(rowIndex+1, HEADNUMBER).setValue(0);
			}
			
			for (int i = 0; i < this.kdtEntries.getRowCount(); i++) 
			{
				BigDecimal amountBig = UIRuleUtil.getBigDecimal((kdtEntries.getCell(i,"amount").getValue()));
				BigDecimal investAmountBig = UIRuleUtil.getBigDecimal((kdtEntries.getCell(i,"investAmount").getValue()));
				BigDecimal cumulative = UIRuleUtil.getBigDecimal(kdtEntries.getCell(i, "cumulativeInvest").getValue());
				kdtEntries.getCell(i, "balance").setValue(amountBig.subtract(cumulative).subtract(investAmountBig));
				kdtEntries.getCell(i, "investProportion").setValue(
						amountBig.compareTo(BigDecimal.ZERO)==0?0:(investAmountBig.divide(amountBig ,4, RoundingMode.HALF_UP)));
			}
		}
		//------------------------------
		
		
    	if(colIndex == kdtEntries.getColumnIndex(LONGNUMBER)){
    		if(oldValue != null && newValue != null){
    			if(oldValue.equals(newValue)){
    				return;
    			}
    		}
    		setEntriesNumberCol(rowIndex, level);
    	}
    	
    	
    	if(colIndex == kdtEntries.getColumnIndex("name")){
    		setEntriesNameCol(rowIndex, level);
    	}
		if (colIndex == kdtEntries.getColumnIndex("contractType")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
				Object nextHeadNumber = kdtEntries.getRowCount()!=rowIndex+1?kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue():"";
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼û�к�ͬ���ͣ�");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
			}
		}
    }
    
    /**
     * ��������ϸ�ڵ���������ɫ
     */
    private void setHeadRowColor()
    {
    	Color color = new Color(192,192,192);
    	
    	for (int i = 0; i < this.kdtEntries.getRowCount(); i++) 
    	{
    		IRow row = this.kdtEntries.getRow(i);
    		
    		Object oldLongNumber = row.getCell(LONGNUMBER).getValue();
    		Object nextHeadNumber = kdtEntries.getRowCount()!=i+1?this.kdtEntries.getCell(i+1,HEADNUMBER).getValue():"";
    		
    		if(oldLongNumber.equals(nextHeadNumber))
    		{
    			for (int j = 0; j < this.kdtEntries.getColumnCount(); j++) 
    			{
    				String key = this.kdtEntries.getColumnKey(j);
    				if(key.equals("longNumber")||key.equals("name"))
    					continue;
    				ICell Icell = this.kdtEntries.getCell(i, j);
    				Icell.getStyleAttributes().setBackground(color);
    				Icell.getStyleAttributes().setLocked(true);
				}
    			this.kdtEntries.getCell(i, "contractType").getStyleAttributes().setBackground(color);
    			this.kdtEntries.getCell(i, "contractType").getStyleAttributes().setLocked(true);
    		}
		}
    }
	/**
	 * ������ֶ����޸�ʱ�Զ����ϻ���
	 * 
	 * @param index
	 * @param colIndex
	 * @param level
	 */
    private void caclTotalAmount(int index , int colIndex , int level) {
		if(level == 1){
			return;
		}
		int loop = index;
		int loopLevel = level;
		while(loop >= 0){
			int parentIndex = 0;
			BigDecimal dbSum = FDCHelper.ZERO;
			int curLevel = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			if(curLevel > loopLevel){
				loop--;
				continue;
			}
			String parentNumber = "";
			if(curLevel == 1){
				parentNumber = kdtEntries.getCell(loop, LONGNUMBER).getValue().toString();
			}else{
				parentNumber = kdtEntries.getCell(loop, HEADNUMBER).getValue().toString();
			}
			dbSum = FDCHelper.ZERO;
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
				Object h = kdtEntries.getCell(i, HEADNUMBER).getValue();
				int cacl_Level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				String number = "";
				//�ж�Ҫ���ܵ��±�ֵ
				if (l != null) {
					if (parentNumber.equals(l.toString())) {
						parentIndex = i;
					}
					number = l.toString();
				}else if(h != null){
					if (parentNumber.equals(h.toString())) {
						for (int j = 0; j < kdtEntries.getRowCount(); j++) {
							if(j == i)
								continue;
							int j_Level = new Integer(kdtEntries.getCell(j, "level").getValue().toString()).intValue();
							if(cacl_Level == j_Level){
								Object l_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
								if(l_2 != null){
									if(h.toString().equals(l_2.toString())){
										parentIndex = j;
									}
								}
							}
						}
					}
					number = h.toString();
				}
				//�������ֵ
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
			if(!(parentIndex == kdtEntries.getRowCount() - 1)){
				strSum = dbSum.toString();
				kdtEntries.getCell(parentIndex, colIndex).setValue(strSum);
			}
			loop--;
			loopLevel--;
		}
	}
    
	private String attachMentTempID = null;
    protected void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		if (e.getClickCount() < 2 || e.getType() == KDTStyleConstants.HEAD_ROW || e.getColIndex() == 0) {
			return;
		}
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingEntryInfo rowObject = (ProgrammingEntryInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingEntryCollection pcCollection = getPCCollection();
		ProjectInfo project = (ProjectInfo) this.getUIContext().get("treeSelectedObj");
		uiContext.put("programmingContract", rowObject);// �滮Ͷ�ʹ滮
		uiContext.put("pcCollection", pcCollection);// �滮Ͷ�ʹ滮����
		uiContext.put("project", project);// ������Ŀ

		// ˫���༭����
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntries.getColumnIndex("attachment")) {
			boolean isEdit = false;// Ĭ��Ϊ�鿴״̬
			// if (oprtState.equals(OprtState.ADDNEW) || oprtState.equals(OprtState.EDIT)) {
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


		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			checkAimCostNotNull();
			Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if(oldLongNumber == null){
				FDCMsgBox.showInfo("����д���룡");
				return;
			}
			//У���Ƿ����¼����������û����ϸ��
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼û����ϸ��Ϣ��");
					return;
				}
			}
			
			String oprstate = OprtState.ADDNEW;
			
			String oql = "where sourceBillId='"+rowObject.getId()+"'";
			if(InvestPlanDetailFactory.getRemoteInstance().exists(oql))
			{
				uiContext.put("ID", InvestPlanDetailFactory.getRemoteInstance().getInvestPlanDetailCollection(oql).get(0).getId());
				oprstate = OprtState.VIEW;
			}
			else
				uiContext.put("SourceBillId", rowObject.getId());
			uiContext.put("proNumber", this.txtDescription.getText());
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InvestPlanDetailEditUI.class.getName(), uiContext, null,oprstate);
			uiWindow.show();
			// �����ݵ���¼��
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
			// ���³�����
			setEntriesNameCol(rowIndex, level);
			// ���³�����
			Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldLongNumber != null && newLongNumber != null) {
				if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
					setEntriesNumberCol(rowIndex, level);
				}
			}
			// ���¹滮���,�滮���,���ƽ��������Ļ���
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("investAmount"), level);
			// �����ж�����ɫ
			setMyFontColor();
			
		
			if(OprtState.VIEW.equals(getOprtState())){
				return;
			}
//			kdtEntries.getRow(rowIndex).getCell("buildPrice").setValue(FDCHelper.divide(kdtEntries.getRow(rowIndex).getCell("amount").getValue(), this.totalBuildArea, 2, BigDecimal.ROUND_HALF_UP));
//			kdtEntries.getRow(rowIndex).getCell("price").setValue(FDCHelper.divide(kdtEntries.getRow(rowIndex).getCell("amount").getValue(), kdtEntries.getRow(rowIndex).getCell("quantities").getValue(), 2, BigDecimal.ROUND_HALF_UP));
			
		}
    }

	/**
	 * ��ȡ�п������Ͷ�ʹ滮
	 * 
	 * @return
	 */
	private ProgrammingEntryCollection getPCCollection() {
		ProgrammingEntryCollection pcCollection = new ProgrammingEntryCollection();
		ProgrammingEntryInfo pcInfo = null;
		int columnCount = kdtEntries.getRowCount();
		for (int i = 0; i < columnCount; i++) {
			pcInfo = (ProgrammingEntryInfo) kdtEntries.getRow(i).getUserObject();
			setContractToEditData(i, pcInfo);
			pcCollection.add(pcInfo);
		}
		return pcCollection;
	}
	
	/**
	 * �����и��ĸ������ݡ������¼��ڵ����
	 * @param rowIndex
	 * @param level
	 */
    private void setEntriesNumberCol(int rowIndex, int level) {
		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if(longNumber != null && longNumber.toString().trim().length() > 0){
			String lnumber = longNumber.toString();
			if(level == 1){
				kdtEntries.getCell(rowIndex, "number").setValue(lnumber);
			}else{
				String number = lnumber.substring(lnumber.lastIndexOf(".")+1 , lnumber.length());
				kdtEntries.getCell(rowIndex, "number").setValue(number);
			}
			for(int i = rowIndex+1 ; i < kdtEntries.getRowCount() ; i++){
				Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
				Object longNumber_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				
				String[] editString = lnumber.split("\\.");
				if(longNumber_2 != null && longNumber_2.toString().trim().length() > 0){
					String hNumber_2 = headNumber.toString();
					String lNumber_2 = longNumber_2.toString();
					String[] newL = lNumber_2.split("\\.");
					String[] newH = hNumber_2.split("\\.");
					for(int j = 0 ; j < editString.length ; j++){
						newL[j] = editString[j];newH[j] = editString[j];
					}
					StringBuffer str = new StringBuffer();
					for(int j = 0 ; j < newL.length ; j++){
						str.append(newL[j]).append(".");
					}
					if(newL.length < level_2)
						str.append(".");
					StringBuffer str2 = new StringBuffer();
					for(int j = 0 ; j < newH.length ; j++){
						str2.append(newH[j]).append(".");
					}
					setkdtEntriesNumber(i , str.substring(0, str.length() - 1) , str2.substring(0, str2.length() - 1));
				}
			}
		}
	}

    /**
     * ��������ʱ���á������ӽڵ㳤����
     * @param rowIndex
     * @param level
     */
	private void setEntriesNameCol(int rowIndex, int level) {
		Object name =  kdtEntries.getCell(rowIndex , "name").getValue();
		if(name != null && name.toString().trim().length() > 0){
			String nameStr = name.toString().trim();
			String blank = setNameIndent(level);
			kdtEntries.getCell(rowIndex ,"name").setValue(blank+nameStr);
			if(level == 1){
				kdtEntries.getCell(rowIndex ,"longName").setValue(nameStr);
			}else{
				Object lo = kdtEntries.getCell(rowIndex ,"longName").getValue();
				String displayName = lo == null ? "" : lo.toString();
				String ln = displayName.substring(0 , displayName.lastIndexOf("."))+".";
				kdtEntries.getCell(rowIndex ,"longName").setValue(ln + nameStr);
			}
			
			Object lo = kdtEntries.getCell(rowIndex ,"longName").getValue();
			String displayName = lo == null ? "" : lo.toString();
			if(level == 1){
				displayName = displayName+".";
			}
			String [] l = displayName.split("\\.");
			for(int i = rowIndex+1 ; i < kdtEntries.getRowCount() ; i++){
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				Object l2 = kdtEntries.getCell(i ,"longName").getValue();
				String l3[] = l2.toString().split("\\.");
				for(int j = 0 ; j < l.length ; j++){
					if(l3[j] != null && l3[j].length() > 0){
						l3[j] = l[j];
					}
				}
				StringBuffer str = new StringBuffer();
				for(int j = 0 ; j < l3.length ; j++){
					str.append(l3[j]).append(".");
				}
				Object n2 = kdtEntries.getCell(i ,"name").getValue();
				if(n2 == null){
					str.append(".");
				}
				kdtEntries.getCell(i ,"longName").setValue(str.substring(0, str.length() - 1));
				displayName = null;
			}
		}
	}
	
	/**
	 * �޸��¼����롢�����¼��ر�����
	 * @param i
	 * @param lnumber
	 * @param hNumber
	 */
	private void setkdtEntriesNumber(int i, String lnumber, String hNumber) {
		kdtEntries.getCell(i, HEADNUMBER).setValue(hNumber);
		kdtEntries.getCell(i, LONGNUMBER).setValue(lnumber);	
		
		ICellEditor editor = kdtEntries.getCell(i, LONGNUMBER).getEditor();
		if(editor != null){
			if(editor instanceof KDTDefaultCellEditor){
	    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    			KDTextField txtLongNumber = (KDTextField) de.getComponent();
    			LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
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
	private void setCellEditorForTable(){
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			KDTextField txtLongNumber = new KDTextField();
			String txt = "";
			Object longNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
			Object subNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if(longNumber == null || longNumber.toString().trim().length() == 0){
    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    				txt = subNumber.toString().trim()+".";
    			}
			}
			else{
				txt = longNumber.toString().trim();
			}
			if(level > 1){
				LimitedTextDocument document = new LimitedTextDocument(subNumber.toString().trim()+"." , true);
				txtLongNumber.setMaxLength(80);
				txtLongNumber.setDocument(document);
				txtLongNumber.setText(txt);
				document.setIsOnload(false);
			}else{
				LimitedTextDocument document = new LimitedTextDocument("");
				txtLongNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtLongNumber.setText(txt);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
			
			KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
			kdtEntries.getCell(i , LONGNUMBER).setEditor(cellEditorNumber);
			String name = (String) kdtEntries.getCell(i, "name").getValue();
			formatName(i);
			kdtEntries.getCell(i, "name").setValue(name);
		}
    }
	
	private void setTableFontColor(){
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			boolean isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue();
			if(blanceValue != null){
				BigDecimal blance = (BigDecimal)blanceValue;
				if(blance.compareTo(FDCHelper.ZERO) > 0){
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.blue);
				}else{
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
			if(((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue()){
				kdtEntries.getRow(i).getStyleAttributes().setBackground(new java.awt.Color(128,255,128));
			}
			if(isCiting){
				kdtEntries.getRow(i).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
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
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * ����Ͷ�ʹ滮����ͷ��Ϣ
	 * 
	 * @param rowIndex
	 * @param rowObject
	 */
	private void setContractToEditData(int rowIndex, ProgrammingEntryInfo rowObject) {
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
		rowObject.setName((String) kdtEntries.getCell(rowIndex, "name").getValue());// ����

		rowObject.setWorkContent((String) kdtEntries.getCell(rowIndex, "workContent").getValue());// ��������
		rowObject.setSupMaterial((String) kdtEntries.getCell(rowIndex, "supMaterial").getValue());// �׹�����ָ����
		rowObject.setIsInvite((Boolean) kdtEntries.getCell(rowIndex, "isInvite").getValue());
//		rowObject.setInviteWay((InviteFormEnum) kdtEntries.getCell(rowIndex, "inviteWay").getValue());// �б귽ʽ
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
			rowObject.setControlAmount(new BigDecimal(controlAmount.toString())); // ���ƽ��
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
		Object balance = kdtEntries.getCell(rowIndex, "balance").getValue();
		if (!FDCHelper.isEmpty(balance)) {
			rowObject.setBalance(new BigDecimal(balance.toString())); // �滮���
		}
		Object controlBalance = kdtEntries.getCell(rowIndex, "controlBalance").getValue();
		if (!FDCHelper.isEmpty(controlBalance)) {
			rowObject.setControlBalance(new BigDecimal(controlBalance.toString())); // �������
		}
		Object citeVersionObj = kdtEntries.getCell(rowIndex, "citeVersion").getValue();
		if (!FDCHelper.isEmpty(citeVersionObj)) {
			rowObject.setCiteVersion(new Integer(kdtEntries.getCell(rowIndex, "citeVersion").getValue().toString()).intValue());// ���ð汾
		}

		rowObject.setDescription((String) kdtEntries.getCell(rowIndex, "remark").getValue());// ��ע
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
			info.setVersion(version.add(new BigDecimal("1.0")));
		}
	}


	/**
	 * �ж��ѹ滮����Ƿ������Ŀ��ɱ����
	 * 
	 * @return ���ڷ���true
	 */
	private boolean verifyAmountVSAimCost() {
		kdtEntries.getEditManager().editingStopped();
		BigDecimal aimCostAccountTotal = FDCHelper.ZERO;// ��Ŀ��ɱ�
		BigDecimal amountTotal = FDCHelper.ZERO;// �ܹ滮���
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
			FDCMsgBox.showWarning(this,"�ܹ滮���ܴ�����Ŀ��ɱ���");
			this.abort();
		} else if(amountTotal.compareTo(aimCostAccountTotal)<0){
			FDCMsgBox.showWarning(this,"�ܹ滮����С����Ŀ��ɱ���");
			this.abort();
		} 
		return false;
	}
	
	/**
	 * Ͷ�ʹ滮�滮����
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		veryfyForSave();
		super.actionSave_actionPerformed(e);
		sumClass.appendProFootRow(null, null);
	}

	/**
	 * ����У��
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void veryfyForSave() throws EASBizException, BOSException {
		String name_txt = txtName.getText();
		String errrMsg = "Ͷ�ʹ滮��ܰ汾����";
		if (name_txt == null || name_txt.trim().equals("")) {
			txtName.requestFocus(true);
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + "����Ϊ�գ�"));
			
		}
//		if (StringUtils.isEmpty(txtProjectName.getText())) {
//			throw new EASBizException(new NumericExceptionSubItem("1", "������Ŀ����Ϊ��"));
//		}
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
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if(ProgrammingFactory.getRemoteInstance().exists(filter)) {
			txtName.requestFocus(true);
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + name_txt + "�Ѿ����ڣ������ظ���"));
		}
		// ����ʱȥ����¼���Ƶ�ǰ��ո�
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
			
			Object number = kdtEntries.getCell(i, "longNumber").getValue();
    		Object head = kdtEntries.getCell(i, HEADNUMBER).getValue();
    		if(number == null || number.toString().trim() == null){
	    		throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		Object level = kdtEntries.getCell(i, "level").getValue();
    		int level_int = new Integer(level.toString()).intValue();
			if(level_int != 1){
	    		String ln = number.toString();
	    		if(ln.length() == (head.toString().length() + 1)){
	    			throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
				}
			}
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}
			row.getCell("sortNumber").setValue(new Integer(i));
		}

		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
	}
	
    /**
     * output actionSubmit_actionPerformed
     */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
//		verifyControlParam();
		verifyDataBySave();
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "������Ŀ����Ϊ��"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "�汾�Ų���Ϊ��"));
		}
		//����ʱȥ����¼���Ƶ�ǰ��ո�
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
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
		
		if(this.txtVersion.getBigDecimalValue()!=null&&this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))>0){
			if(this.txtDescription.getText()==null||"".equals(this.txtDescription.getText().trim())){
				FDCMsgBox.showWarning(this,"����˵������Ϊ�գ�");
				SysUtil.abort();
			}
			if(this.kdtCompareEntry.getRowCount()!=this.kdtVerCompareEntry.getRowCount()){
				FDCMsgBox.showWarning(this,"���Ƚ��е���ԭ����ȡ������");
				SysUtil.abort();
			}
			for(int i=0;i<this.kdtCompareEntry.getRowCount();i++){
				String srcPc=this.kdtCompareEntry.getRow(i).getCell("programmingContract").getValue().toString();
				String srcContent=this.kdtCompareEntry.getRow(i).getCell("content").getValue().toString();
				
				String pc=this.kdtVerCompareEntry.getRow(i).getCell("programmingContract").getValue().toString();
				String content=this.kdtVerCompareEntry.getRow(i).getCell("content").getValue().toString();
				if(!pc.equals(srcPc)||!content.equals(srcContent)){
					FDCMsgBox.showWarning(this,"���Ƚ��е���ԭ����ȡ������");
					SysUtil.abort();
				}
			}
			ProgrammingCollection col=ProgrammingFactory.getRemoteInstance().getProgrammingCollection("select aimCost.measureStage.id from where versionGroup = '" + this.editData.getVersionGroup()+"' and version="+(this.txtVersion.getBigDecimalValue().subtract(new BigDecimal(1))));
			if(col.size()>0){
				for(int i=0;i<this.kdtCompareEntry.getRowCount();i++){
					String content=(String)this.kdtCompareEntry.getRow(i).getCell("content").getValue();
					String reson=(String)this.kdtCompareEntry.getRow(i).getCell("reason").getValue();
					if (content != null) {
						if(content.indexOf("�滮�������")>=0){
							BigDecimal amount=new BigDecimal(content.substring(content.indexOf("�滮�������")+6, content.indexOf("Ԫ")));
							if(amount.compareTo(new BigDecimal(1000000))>=0&&(reson == null||"".equals(reson.trim()))){
								FDCMsgBox.showWarning(this,"��"+(i+1)+"�е���ԭ����Ϊ�գ�");
								this.kdtCompareEntry.getEditManager().editCellAt(i, this.kdtCompareEntry.getColumnIndex("reason"));
								SysUtil.abort();
							}
						}else{
							BigDecimal amount=new BigDecimal(content.substring(content.indexOf("�滮������")+6, content.indexOf("Ԫ")));
							if(amount.compareTo(new BigDecimal(1000000))>=0&&(reson == null||"".equals(reson.trim()))){
								FDCMsgBox.showWarning(this,"��"+(i+1)+"�е���ԭ����Ϊ�գ�");
								this.kdtCompareEntry.getEditManager().editCellAt(i, this.kdtCompareEntry.getColumnIndex("reason"));
								SysUtil.abort();
							}
						}
					} 
				}
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		if (isBillModify()) {
			((IProgramming) getBizInterface()).billModify(versionGroup, curVersion); // ά�����°汾�ֶ�
		}
		
		setAuditBtnEnable();
		sumClass.appendProFootRow(null, null);
		
		if (isBillModify()) {
			ProgrammingListUI parentUI = (ProgrammingListUI) getUIContext().get("parent");
			parentUI.refreshList();
			this.getUIContext().remove(ProgrammingListUI.IS_MODIFY);
		}
	}

	// �Ƿ��޶�
	private boolean isBillModify() {
		Boolean isSet = (Boolean) getUIContext().get(ProgrammingListUI.IS_MODIFY);
		return isSet != null && isSet.booleanValue();
	}
    
	/**
	 * �ύǰУ�����ݣ����ơ���¼���뼰����
	 * @throws Exception
	 */
    public void verifyDataBySave() throws Exception {
    	veryfyForSave();
    	int rowCount = kdtEntries.getRowCount();
		//Ŀ��ɱ���Ϊ��20111130
		
    	for(int i = 0 ; i < rowCount ; i++){
			// checkAmount(i);
    		
    		Object number = kdtEntries.getCell(i, "longNumber").getValue();
    		Object head = kdtEntries.getCell(i, HEADNUMBER).getValue();
    		if(number == null || number.toString().trim() == null){
	    		throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		Object level = kdtEntries.getCell(i, "level").getValue();
    		int level_int = new Integer(level.toString()).intValue();
			if(level_int != 1){
	    		String ln = number.toString();
	    		if(ln.length() == (head.toString().length() + 1)){
	    			throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
				}
			}
			
			String longNumber = number.toString().trim();
			if(longNumber.length() > 80){
				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У����볬�������������룡"));
			}
    		
    		Object proName = kdtEntries.getCell(i, "name").getValue();
    		if(proName == null || proName.toString().trim() == null){
    			throw new ProgrammingException(ProgrammingException.NAME_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		if(proName != null && proName.toString().trim().length() > 80){
    			throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У����Ͷ�ʹ滮���Ƴ�����"));
        	}
    		
    		Object longName = kdtEntries.getCell(i, "longName").getValue();
    		if(longName != null && !StringUtils.isEmpty(longName.toString())){
    			if(longName.toString().length() > 255){
    				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У����Ͷ�ʹ滮�����Ƴ���\n���޸Ŀ��Ͷ�ʹ滮�������ݣ�"));
    			}
    		}
    		
    		String lnumber = number.toString();
    		String name = proName.toString().trim();
    		
    		for(int j = 0 ; j < rowCount ; j++){
    			if(j == i)
    				continue;
    			Object number_2 = kdtEntries.getCell(j, "longNumber").getValue();
        		Object proName_2 = kdtEntries.getCell(j, "name").getValue();
        		
        		if(number_2 != null && number_2.toString().trim().length() > 0){
        			if(lnumber.equals(number_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "����"});
        			}
        		}
        		
        		if(proName_2 != null && proName_2.toString().trim().length() > 0){
        			if(name.equals(proName_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "����"});
        			}
        		}
    		}
    	}
    	for(int i=0;i<this.kdtEntries.getRowCount();i++)
    	{
    		Object oldLongNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
    		if(i+1 <kdtEntries.getRowCount())
    		{
    			Object nextHeadNumber = kdtEntries.getCell(i+1, HEADNUMBER).getValue();
    			if(!oldLongNumber.equals(nextHeadNumber))
    			{
    				if (kdtEntries.getCell(i, "contractType").getValue()== null)
    				{
    					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���ͬ���Ͳ���Ϊ�գ�"));
    				}
    			}
    		}
    		else
    		{
    			if (kdtEntries.getCell(i, "contractType").getValue()== null){
					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���ͬ���Ͳ���Ϊ�գ�"));
				}
    		}
    	}
    }
    
    /**
     * ��ȡ��ǰʱ���ַ������������ñ���
     */
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
    
    /**
     * ������������ʾ����Ч������ǰ��ӿո�
     */
    private void setNameDisplay(){
		int rowCount = kdtEntries.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString());
			}
		}
	}
    
    //���ǵ�����ķ���
    protected void setAuditButtonStatus(String oprtType) {
    }
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkHighVersion();
        super.actionEdit_actionPerformed(e);
        setSmallBtnEnable();
        setCellEditorForTable();
//		if (isCiting()) {
//			prmtAimCost.setEnabled(false);
//		} else {
//			prmtAimCost.setEnabled(true);
//		}
        handleOldData();
        
		setBuildPrice();
    }
    
    private void checkHighVersion() throws Exception {
		BigDecimal version = editData.getVersion().add(FDCHelper.ONE);
		String versionGroup = editData.getVersionGroup();
		
		String oql = "where version = '".concat(version.toString()).concat("' and versionGroup = '")
			.concat(versionGroup).concat("'");
		if (getBizInterface().exists(oql)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "���ڸ��߰汾���ܽ��д˲���"));
		}
	}

    /**
     * output actionAudit_actionPerformed
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
			FDCMsgBox.showInfo("����״̬���ݲ�����������");
			SysUtil.abort();
		}
		if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				ProgrammingFactory.getRemoteInstance().audit(editData.getId());
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
				actionEdit.setEnabled(false);
				editData.setState(FDCBillStateEnum.AUDITTED);
				MsgBox.showInfo("�����ɹ���");
			}
		}
		handleOldData();
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
				checkHighVersion();
				
				ProgrammingFactory.getRemoteInstance().unAudit(editData.getId());
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
				if(this.getOprtState().equals(STATUS_VIEW))
					actionEdit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				MsgBox.showInfo("�������ɹ���");
			}
		}
		handleOldData();
	}
    
    /**
     * ȫ���������������¼��ʱ����¼��Ҫʧȥ����
     */
    private void setMouseClick() {
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	Component[] com = this.getComponents();
    	for(int i = 0 ; i < com.length ; i++){
    		if(!com[i].equals(kdtEntries)){
    			com[i].addMouseListener(new MouseListener(){
    				public void mouseClicked(MouseEvent arg0) {
    				}
    				public void mouseEntered(MouseEvent arg0) {
    				}
    				public void mouseExited(MouseEvent arg0) {
    				}
    				public void mousePressed(MouseEvent arg0) {
    				}
    				public void mouseReleased(MouseEvent arg0) {
    					if(!arg0.getComponent().equals(kdtEntries)){
    						setKDTableLostFocus();
    					}
    				}
    	    	});
    		}
    	}
    	
    	this.txtName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtProjectName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtVersion.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
	}

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
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
	
	protected String getEditUIName()
    {
        return com.kingdee.eas.port.pm.invest.investplan.client.ProgrammingEditUI.class.getName();
    }
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("soldArea"));
		sic.add(new SelectorItemInfo("aimCost.*"));
		sic.add(new SelectorItemInfo("aimCost.measureStage.id"));
		
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
		//ȡԤ����� 20120420
		sic.add(new SelectorItemInfo("entries.estimateAmount"));
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
		
		sic.add(new SelectorItemInfo("entries.contractType.*"));
		sic.add(new SelectorItemInfo("entries.programming.*"));
		
		sic.add(new SelectorItemInfo("isLatest"));
		sic.add(new SelectorItemInfo("entries.isInvite"));
		
		sic.add(new SelectorItemInfo("compareEntry.*"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("sourceBillId"));
		
		return sic;
	}      

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
       com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo objectValue = new com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
        objectValue.setVersion(new BigDecimal("1.0"));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setVersionGroup(Uuid.create().toString());
        if(getUIContext().get("PlanNumber")!=null)
        {
        	objectValue.setSourceBillId(getUIContext().get("PlanNumber").toString());
        	objectValue.setDescription(getUIContext().get("PlanNumber").toString());
        }
        return objectValue;
    }
    
    
    
    private void setProTableToSumField() {
		HashMap sumFields = getProSumFields();
		if (sumFields == null) return;
		//ȡ�����е�KDTable���ϼ�����Ϣ
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[])sumFields.get(table);
			sumClass.setProTableToSumField(table, sumColNames);			
		}
	}
    
    private HashMap getProSumFields(){
    	HashMap sumFields = new HashMap();
//    	sumFields.put(this.kdtEntries, new String[] {"amount", "controlAmount", "signUpAmount", "changeAmount", "settleAmount", "balance",
//    			"controlBalance", "buildPerSquare", "soldPerSquare","investAmount","cumulativeInvest" });
    	sumFields.put(this.kdtEntries, new String[] {"balance", "amount","investAmount","cumulativeInvest" });
    	return sumFields;
    }
    
    protected void handleOldData() {
		if(!(getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
    
    protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
    }
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId() != null){
			if(ProgrammingFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))){
				editData = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(editData.getId().toString()) , getSelectors());
				if(editData == null )return;
				setDataObject(editData);
				loadFields();
			}
		}
    	this.dataBinder.storeFields();
		setKDTableLostFocus();
    	createTree();
    	// ���ˢ�����ݺϼƲ�������
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
	}
	
	/**
	 * ��ģ�嵼�빦��
	 */
	public void actionImports_actionPerformed(ActionEvent e) throws Exception {
		ProgrammingEntryCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
			if (entry.isIsCiting()||entry.isIsWTCiting()) {
				throw new EASBizException(new NumericExceptionSubItem("1", 
				"Ͷ�ʹ滮����д��ڱ����õĿ��Ͷ�ʹ滮\n������˲���"));
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("editData", editData);
		uiContext.put("project", curProject);
		uiContext.put("dataChangeListener", dataChangeListener);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingImportUI.class.getName(), uiContext, null,	OprtState.VIEW , WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		ui.show();
		
		// ��ĩ�������ϻ���
		// �滮��� = �¼��滮������
		Set leafSet = findLeafSet();
		cleanNotLeafAmout(leafSet);
		totalAmout(leafSet);
		// �����滮��� = �滮��� = ���ƽ��
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
			entry.setBalance(entry.getAmount());
		}
		
		setDataObject(editData);
		loadFields();
		if (this.oprtState.equals(STATUS_ADDNEW)) {
			setNameDisplay();
		}
	}
	
	private Set findLeafSet() {
		Set leafNumberSet = new HashSet();
		ProgrammingEntryCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo info = entries.get(i);
			String longNumber = info.getLongNumber();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				ProgrammingEntryInfo infoJ = entries.get(j);
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
	 * @param numberList
	 * @return
	 */
	private Set findLeafList(List numberList) {
		Set leafNumberList = new HashSet();
		for (int i = 0, size = numberList.size(); i <size; i++) {
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
			ProgrammingEntryCollection entries = editData.getEntries();
			for (int j = 0, sizeJ = entries.size(); j < sizeJ; j++) {
				ProgrammingEntryInfo entry = entries.get(j);
				if (leafLongNumber.equals(entry.getLongNumber())) {
					leafAmount = entry.getAmount();
					break;
				}
			}
			ProgrammingEntryInfo parentEntry = getParentEntry(leafLongNumber);
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
	 * @param leafSet
	 */
	private void cleanNotLeafAmout(Set leafSet) {
		ProgrammingEntryCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
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
	
	private ProgrammingEntryInfo getParentEntry(String subLongNumber) {
		ProgrammingEntryCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
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
		UIContext uiContext = new UIContext(this);
		uiContext.put("Programming", editData);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingExportUI.class.getName(), uiContext, null,	OprtState.EDIT);
		ui.show();
	}

	
	public void actionComAddRow_actionPerformed(ActionEvent e) throws Exception {
	}
	public void actionComInsertRow_actionPerformed(ActionEvent e)throws Exception {
	}
	public void actionCompare_actionPerformed(ActionEvent e) throws Exception {
	}
	public void actionComRemoveRow_actionPerformed(ActionEvent e) throws Exception {
	}
	
}