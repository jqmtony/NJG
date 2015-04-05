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
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.port.pm.base.InvestYearInfo;
import com.kingdee.eas.port.pm.invest.ProjectEstimateInfo;
import com.kingdee.eas.port.pm.invest.client.YearInvestPlanEditUI;
import com.kingdee.eas.port.pm.invest.investplan.IProgramming;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingException;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invest.uitls.CreateProTableRow;
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
    private CreateProTableRow create = new CreateProTableRow(dataBinder);//分录操作对象
	private DataChangeListener dataChangeListener = null;
	private EntryTreeSumField sumClass = new EntryTreeSumField();
    private final String LONGNUMBER = "longNumber";//长编码
    private final String HEADNUMBER = "headNumber";//长级长编码
    private final String BALANCE = "balance";
	private final String AMOUNT = "amount";
	private KDFormattedTextField investZ = null;//投资总金额
	private KDFormattedTextField investB = null;//本年度投资金额
	private KDFormattedTextField investL = null;//累计投资
	private KDFormattedTextField investY = null;//投资余额
	private KDFormattedTextField investGS = null;//估算金额
	private KDFormattedTextField investTZ = null;//调整金额
	InvestYearInfo   investYearInfo   = null;
	private String isAdjust = "0";
	
    public KDWorkButton btnAddnewLine;
    public KDWorkButton btnInsertLines;
    public KDWorkButton btnRemoveLines;
    public KDWorkButton btnDetails;
    public KDWorkButton btnImports;
    public KDWorkButton btnExports;
    public BigDecimal totalBuildArea;
    
    public static InvestPlanBaseInfo planInfo = new InvestPlanBaseInfo();
    private ProjectEstimateInfo EstimateInfo = null;
    private YearInvestPlanEditUI YearInvestPlanEditUI = null;
	public ProgrammingEditUI() throws Exception {
		super();
	}
    public void onLoad() throws Exception {
    	if(kdtCompareEntry!=null){
    		this.kdtCompareEntry.checkParsed();
        	this.kdtVerCompareEntry.checkParsed();
        	this.kdtVerCompareEntry.setVisible(false);
    		this.txtBuildArea.setEnabled(false);
    		this.kdtEntries.getColumn("investProportion").getStyleAttributes().setNumberFormat("#,##0.00 %");
    		this.kDTable1.getColumn("proportion").getStyleAttributes().setNumberFormat("#,##0.00 %");
    		txtSaleArea.setEnabled(false);
    		kDTabbedPane1.remove(pnlCostAccount);
    		kDTabbedPane1.remove(kDPanel1);
    		this.kdtCostAccount.getStyleAttributes().setLocked(true);
    		this.kdtCostAccount.getStyleAttributes().setHided(true);
    		
    		this.kdtCompareEntry.getColumn("programmingContract").getStyleAttributes().setLocked(true);
    		this.kdtCompareEntry.getColumn("content").getStyleAttributes().setLocked(true);
    		this.kdtCompareEntry.getColumn("programmingContract").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
    		this.kdtCompareEntry.getColumn("content").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
    		this.kdtEntries.getColumn("costAccount").getStyleAttributes().setHided(true);
    		this.kdtEntries.getColumn("costAccount").getStyleAttributes().setLocked(true);
    		this.kdtEntries.getColumn("isInvite").getStyleAttributes().setHided(true);
    		this.kdtEntries.getColumn("balance").getStyleAttributes().setLocked(true);
    		this.kdtEntries.getColumn("investProportion").getStyleAttributes().setLocked(true);
    		this.kdtEntries.getColumn("isCiting").getStyleAttributes().setHided(true);
    		this.kdtEntries.getColumn("cumulativeInvest").getStyleAttributes().setLocked(true);
    		this.kdtEntries.getColumn("contractType").getStyleAttributes().setHided(true);
    		this.kdtEntries.getColumn("price").getStyleAttributes().setHided(true);
    		this.kdtEntries.getColumn("quantities").getStyleAttributes().setHided(true);
    		this.kdtEntries.getColumn("unit").getStyleAttributes().setHided(true);
    		
        	super.onLoad();
        	txtVersion.setPrecision(1);
    		initTable();
    		setAttachmentRenderer();
        	setSmallButton();
        	setSmallBtnEnable();
        	initData();
        	setMouseClick();
        	
        	investZ = (KDFormattedTextField) getUIContext().get("proInvestAmount");
        	investB = (KDFormattedTextField) getUIContext().get("proAmount");
        	investL = (KDFormattedTextField) getUIContext().get("proAddAmount");
        	investY = (KDFormattedTextField) getUIContext().get("proBalance");
        	investGS = (KDFormattedTextField) getUIContext().get("proEstimate");
        	investTZ = (KDFormattedTextField) getUIContext().get("txtchancedAmount");
        	investYearInfo  = (InvestYearInfo) getUIContext().get("investYearInfo");
        	
        	isAdjust = (String)getUIContext().get("isAdjust");

    		if (this.getUIContext().get("modify") != null) {
    			this.actionImport.setEnabled(false);
    			this.txtDescription.setText(null);
    			this.kdtCompareEntry.removeRows();
    		}
    		KDWorkButton compare=new KDWorkButton();
    		this.actionCompare.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
    		compare = (KDWorkButton)this.conCompare.add(this.actionCompare);
    		compare.setText("提取");
    		compare.setSize(new Dimension(140, 19));
    		if(this.txtVersion.getBigDecimalValue()!=null&&this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))==0){
    			this.kdtEntries.getColumn("amount").getStyleAttributes().setLocked(false);
    		}
    		else
    		{
//    			this.kdtEntries.getColumn("amount").getStyleAttributes().setLocked(true);
//    			conProgramming.removeButton(btnInsertLines);
//    			conProgramming.removeButton(btnAddnewLine);
//    			conProgramming.removeButton(btnRemoveLines);
//    			conProgramming.removeButton(btnImports);
//    			conProgramming.removeButton(btnExports);
    		}
    		
//    		FlowLayout flowLayout = new FlowLayout();
//    		flowLayout.setAlignment(FlowLayout.LEFT);
//    		flowLayout.setHgap(10);
//    		colorPanel.setLayout(flowLayout);
//    		drawALogo("增加",new Color(248,171,166));
//    		drawALogo("减少",new Color(163,207,98));
//    		this.kDTabbedPane1.remove(this.conCompare);
//    		this.kDTabbedPane1.remove(this.kDScrollPane1);
    		colorPanel.setVisible(false);
    	    kDTable1.removeRows();
    	}
    }
    
    public void drawALogo(String name, Color color) {
		KDLabel lable = new KDLabel(name);
		KDLabel colorLable = new KDLabel();
		Dimension d = new Dimension(40, 10);
		colorLable.setPreferredSize(d);
		colorLable.setOpaque(true);
		colorLable.setBackground(color);
//		colorPanel.add(lable);
//		colorPanel.add(colorLable);

	}
    public void initListener() {
    	super.initListener();
    	dataChangeListener = new DataChangeListener() {
    		public void dataChanged(DataChangeEvent e) {
    		}
    	};
    }
   

	/**
	 * 改变目标成本或是加目标成本
	 * 
	 * 方案：
	 * 
	 * 1.更新各成本构成中"目标成本"、"待分配"的值
	 * 
	 * 新"目标成本"： 重新通过新关联的"目标成本"和各成本构成中"工程项目"、"成本科目"作为条件过滤出新的
	 * 
	 * 新"待分配":新"目标成本"-旧"已分配"
	 * 
	 * 2.更新后，把成本构中带有负"待分配"值所对应的投资规划在框架界面分录中用红色标志出来（整行用红色字体）
	 * 
	 * 3.分录中有红色标志的行时保存，提交按钮提示“不可保存…………”,即不可保存不可提交
	 * 
	 * 需要更改的值如下：
	 * 
	 * 界面所本的目标成本版本号
	 * 
	 * 成本构成 :目标成本，待分配例
	 * 
	 * 二期增加内容（2011.04.13）：
	 * 
	 * 若 原"本投资规划分配" = 原"目标成本" 则动态更新 "本投资规划分配" = 新"目标成本"，
	 * 
	 */
    private void changeAimCost() {
    }
	/**
	 * 存在合同引用关系，把目标成本f7控件置灰
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
		if (STATUS_ADDNEW.equals(this.oprtState)&&actionCompare!=null) {
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
	 * 设置附件列显示格式
	 */
	private void setAttachmentRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					return "查看";
				}
				return null;
			}
		});
		this.kdtEntries.getColumn("attachment").setRenderer(objectValueRender);
	}
	//前一版本框架投资规划ID
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
//				editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
			}
		}
		
    }
	
    
	//修订时置空字段值
    public void setFieldsNull(AbstractObjectValue newData) {
    	ProgrammingInfo info = (ProgrammingInfo) newData;
    	String number = getDateString();
    	info.setNumber(number);
		txtNumber.setText(info.getNumber());
    	txtVersion.setText(info.getVersion().toString());
    	info.setState(FDCBillStateEnum.SAVED);
    }
	
    /**
     * 设置分录失去焦点及分录按钮是否可用
     */
	private void setKDTableLostFocus(){
		kdtEntries.getEditManager().stopEditing();
		kdtEntries.getSelectManager().remove();
		kdtEntries.getSelectManager().setActiveRowIndex(-1);
		btnAddnewLine.setEnabled(false);
		btnRemoveLines.setEnabled(false);
		btnDetails.setEnabled(false);
//		btnImports.setEnabled(false);
		btnExports.setEnabled(false);
	}

	/**
	 * 加载时对表格进行设置
	 */
	private void initTable() {
		kdtEntries.checkParsed();
		kdtEntries.getColumn("id").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("level").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("longName").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isCiting").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isWTCiting").getStyleAttributes().setHided(true);
		// 规划余额、控制余额数字格式化两位小数
		cellToFormattedText(kdtEntries, "balance");
		cellToFormattedText(kdtEntries, "investAmount");
		cellToFormattedText(kdtEntries , AMOUNT);
		cellToFormattedText(kdtEntries , "cumulativeInvest");
		cellToFormattedText(kdtEntries , "price");
		cellToFormattedText(kdtEntries , "quantities");
		
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
	}
	public void actionEditInvite_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("是否确定修改？") == MsgBox.CANCEL) {
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
		//加载数据时按长编码排序
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		//单元格编码模式
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
//	    		editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
	    	}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (this.getUIContext().get("modify") == null) {
			setHeadRowColor();
		}
	}
	private void setBuildPrice() throws BOSException{
	}
	
	/**
	 * 设置金额字段显示格式
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
		kdtEntries.getColumn("price").setRenderer(objectValueRender);
		kdtEntries.getColumn("quantities").setRenderer(objectValueRender);
	}
	
	private void cellAttachment() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object idObj = kdtEntries.getCell(i, "id").getValue();
			if (idObj != null) {
				String id = idObj.toString();
				StringBuffer allAttachmentName = getAllAttachmentName(id);
				if (!FDCHelper.isEmpty(allAttachmentName)) {
					kdtEntries.getCell(i, "attachment").setValue("存在附件");
				}
			}
		}
	}

	/**
	 * 获取投资规划框架所有附件名称字符串，名称与乐称以","相隔
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
		//禁用表头排序
		kdtEntries.addKDTMouseListener(new KDTSortManager(kdtEntries));
		kdtEntries.getSortMange().setSortAuto(false);
		
		setAuditBtnEnable();
		kdtEntries.getFootManager().getFootRow(0).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("estimateAmount").getStyleAttributes().setHided(true);
		
		// by shilei ,项目启动申请 新增项目带不出默认的年度累计投资金额
		if(OprtState.ADDNEW.equals(getOprtState()))
		{
			if(investB!=null && !"1".equals(isAdjust))
				investB.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("investAmount").getValue()));//本年度投资金额
			
		}
	}
	
	/**
	 * 设置审批按钮显示
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
	
	public void updateButtonStatus() {
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
     * 在面签中添加新增、插入、删除、详细信息按钮
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
    	
//    	setButtonStyle(btnAddnewLine,"新增行","imgTbtn_addline");
//    	setButtonStyle(btnRemoveLines,"删除行","imgTbtn_deleteline");
//    	setButtonStyle(btnInsertLines,"插入行","imgTbtn_insert");
//    	setButtonStyle(btnDetails,"详细信息","imgTbtn_particular");
    	
    	setButtonStyle(btnInsertLines,"新增框架投资规划(同级)","imgTbtn_insert");
    	setButtonStyle(btnAddnewLine,"新增下级框架投资规划","imgTbtn_addline");
    	setButtonStyle(btnRemoveLines,"删除行","imgTbtn_deleteline");
    	setButtonStyle(btnDetails,"详细信息","imgTbtn_particular");
    	setButtonStyle(btnImports, "导入模板", "imgTbtn_input");
    	setButtonStyle(btnExports, "另存为模板", "imgTbtn_output");
    }
    
    private void setButtionEnable(boolean isEnable){
    	btnAddnewLine.setEnabled(isEnable);
		btnInsertLines.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
		btnImports.setEnabled(isEnable);
		btnExports.setEnabled(isEnable);
    }
    
    //设置按钮显示效果
    private void setButtonStyle(KDWorkButton button , String text , String icon){
    	button.setText(text);
    	button.setToolTipText(text);
    	button.setVisible(true);
    	button.setIcon(EASResource.getIcon(icon));
    	conProgramming.addButton(button);
    }
    
    //设置新增、删除、插入分录按钮是否可用
    private void setSmallBtnEnable(){
    	btnExports.setEnabled(true);
    	btnImports.setEnabled(true);
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
     * 修改为新增下级框架投资规划
     * @param e
     * @throws Exception
     */
    public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception{
    	//校验目标成本版本号必填
    	checkAimCostNotNull();
    	
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntries.getRowCount();
    	
    	int row = -1;
    	if(rowIndex < 0){
    		//下标为0则正常新增
    		IObjectValue detailData = create.addLine(kdtEntries, 1);
    		setAddRow((ProgrammingEntryInfo) detailData);
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
    		for(int i = rowIndex +1 ; i < kdtEntries.getRowCount(); i++){//下级被引用则也不能新增下级
    			if(o.equals(kdtEntries.getCell(i, HEADNUMBER).getValue())){
    				isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue();
    				if(isCiting){
    					break;
    				}
    			}
    		}
        	if(isCiting){
        		MsgBox.showInfo("框架投资规划被引用无法新增下级！");
        		return;
        	}
        	
    		ProgrammingEntryInfo headObject = (ProgrammingEntryInfo)kdtEntries.getRow(rowIndex).getUserObject();
    		headObject.setContractType((ContractTypeInfo)kdtEntries.getCell(rowIndex, "contractType").getValue());
    		int newLevel = 0;
    		//新增时判断数据是否合法
    		if(o == null || o.toString().trim().length() == 0){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划编码不能为空！");
    			return;
    		}else if((o.toString().trim()+".").length() >= 80){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划编码过长\n请修改后再新增子级框架投资规划！");
    			return;
    		}else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划名称不能为空！");
    			return;
    		}else{
    			String ln = o.toString();
    			//
    			row = getInsertRowIndex(o , rowIndex , rowCount);
    			newLevel = new Integer(headlevel.toString()).intValue()+1 ;
    			//如果已经有下级了则不clone上级的数据，
    			//如果 （插入行 = 当前选择行 + 1） 则 需要clone选择行的数据带到插入行。否则不做任何处理 
    			if(rowIndex +1 == row){
    				create.insertLine(kdtEntries , row , newLevel, headObject);//clone
    			}else{
    				create.insertSameLine(kdtEntries , row , newLevel, headObject);//不带数据
    			}
    			setAddRow(headObject);
    			kdtEntries.getCell(row, HEADNUMBER).setValue(o);
    			if(longName != null)
    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
    		}
    		//重新汇总到上级
    		caclTotalAmount(row, kdtEntries.getColumnIndex("amount"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("cumulativeInvest"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("balance"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("investAmount"), newLevel);
			kdtEntries.getCell(row, "isInvite").setValue(isInvite);
    	}
    	//设置编码列的编辑格式、限制上级编码不可修改
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
     * 点击插入行按钮
     * 2011.11.30修改为新增同级投资规划框架 
     * 逻辑说明：
     * 1、当没有投资规划框架是 新增一行
     * 2、当选中了一条记录 就是新增同级合同框架
     * @param e
     * @throws Exception
     */
	 public void actionInsertLines_actionPerformed(ActionEvent e) throws Exception{
	    	checkAimCostNotNull();
	    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
	    	int rowCount = kdtEntries.getRowCount();
	    	int row = -1;
	    	
	    	if(rowIndex < 0){
	    		//下标为0则正常新增
	    		IObjectValue detailData =create.addLine(kdtEntries, 1);
	    		setAddRow((ProgrammingEntryInfo) detailData);
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
	    		if(activelevel == 1 ){ //如果是一级则增加一行到最后
	    			IObjectValue detailData =create.insertSameLine(kdtEntries , rowCount , 1 , null);
	    			setAddRow((ProgrammingEntryInfo) detailData);
	    			row = rowCount;
	    		}else if(o == null || o.toString().trim().length() == 0){//新增时判断数据是否合法
	    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划编码不能为空！");
	    			return;
	    		}else if((o.toString().trim()+".").length() >= 80){
	    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划编码过长\n请修改后再新增子级框架投资规划！");
	    			return;
	    		}else if(name == null || StringUtils.isEmpty(name.toString())){
	    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划名称不能为空！");
	    			return;
	    		} else{
//	    			if(isCiting){
//	            		MsgBox.showInfo("框架投资规划被引用无法新增同级！");
//	            		return;
//	            	}
	    			String ln = o.toString();
	    			if(ln.length() == (head.toString().length() + 1)){
	    				MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架投资规划编码不能为空！");
	        			return;
	    			}
	    			if(rowIndex+1 <kdtEntries.getRowCount()){
	    				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
	    				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
	    				if(oldLongNumber.equals(nextHeadNumber)){
	    					FDCMsgBox.showInfo("非明细目录不能新增同级！");
	    					return;
	    				}
	    			}
	    			row = getInsertRowIndexSameLevel(rowIndex , rowCount);
//	    			create.insertSameLine(kdtEntries , row ,activelevel , headObject.getParent());
	    			
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
		        	
		        	setAddRow(newDetailInfo);
		            
	    			kdtEntries.getCell(row, HEADNUMBER).setValue(head);
	    			if(longName != null)
	    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
	    			
	    			
	    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), activelevel);
	    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), activelevel);
	    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), activelevel);
	    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), activelevel);
	    		}
	    	}
	    	//设置编码列的编辑格式、限制上级编码不可修改
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

	/**
     * 点击删除行按钮
     * @param e
     * @throws Exception
     */
    public void actionRemoveLines_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = this.kdtEntries.getSelectManager().getActiveRowIndex();
    	Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	Object h = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	
    	boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(rowIndex, "isWTCiting").getValue()).booleanValue();
    	if(isCiting){
    		MsgBox.showInfo("存在被引用的框架投资规划“"+longNumber.toString()+"”,无法删除！");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	if(longNumber != null){
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString() , rowIndex , list);
    	}
    	
    	int oldLevel = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		boolean isHasSomeLevel = false;// 默认没有同级节点
		int someIndex = -1;// 有同级节点时对应下标
		int SomeLevel = 0;// 有同级节点时对应级数
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
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"您当前删除的父节点“"+longNumber.toString()+"”下还有其他的框架投资规划，确定要一起删除吗？")){
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
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"是否确认删除数据？")){
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
	 * 点击详细信息，弹出分录单据
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
			FDCMsgBox.showInfo("请选择行");
			return;
		}
		if(oldLongNumber == null){
			FDCMsgBox.showInfo("编码不能为空！");
			return;
		}
		//校验是否有下级，如果有是没有明细的
		if(rowIndex+1 <kdtEntries.getRowCount()){
			Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
			if(oldLongNumber.equals(nextHeadNumber)){
				FDCMsgBox.showInfo("非明细目录没有详细信息！");
				return;
			}
		}
		Object headNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
		if(headNumber!=null && oldLongNumber !=null){
			if(headNumber.toString().length()+1 == oldLongNumber.toString().length()){
				FDCMsgBox.showInfo("请完整填写编码！");
				return;
			}
		}
		if(name == null){
			FDCMsgBox.showInfo("名称不能为空！");
			return;
		}
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingEntryInfo rowObject = (ProgrammingEntryInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingEntryCollection pcCollection = getPCCollection();
		ProjectInfo project = (ProjectInfo) this.getUIContext().get("treeSelectedObj");
		uiContext.put("programmingContract", rowObject);// 规划投资规划
		uiContext.put("pcCollection", pcCollection);// 规划投资规划集合
		uiContext.put("project", project);// 工程项目
		
		uiContext.put("table", this.kDTable1);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ViewPlanDetailUI.class.getName(), uiContext, null,OprtState.ADDNEW);
		uiWindow.show();
		
		
		// 绑定数据到分录上
		dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
		// 更新长名称
		setEntriesNameCol(rowIndex, level);
		// 更新长编码
		Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldLongNumber != null && newLongNumber != null) {
			if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
				setEntriesNumberCol(rowIndex, level);
			}
		}
		// 更新规划金额,规划余额,控制金额，控制余额的汇总
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("investAmount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("buildPerSquare"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("soldPerSquare"), level);
		// 重新判断行是否带有错误数据，错的则字体用红颜色，对的则字体用黑颜色
		setMyFontColor();
	}

	/**
	 * 重新判断行是否带有错误数据，错的则字体用红颜色，对的则字体用黑颜色
	 * @param rowIndex
	 */
	private void setMyFontColor() {
	}
    
	/**
	 * 获取所选行的所有子级节点、并判断是否有被引用的框架投资规划
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
			    		MsgBox.showInfo("存在被引用的框架投资规划“"+l.toString()+"”,无法删除！");
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
     * 加载时生成树形
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
     * 获取要新增行的位置（下标）新增同级的时候使用
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    public int getInsertRowIndexSameLevel(int rowIndex , int rowCount){
    	int row = 0;
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
		for(int i = rowIndex ; i < rowCount ; ++i){//寻找本级的最后一列
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 < level) {
				return i;
			}
		}
		return row == 0 ? rowCount : row;
    
    }
    /**
     * 获取要新增行的位置（下标）
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    public int getInsertRowIndex(Object o , int rowIndex , int rowCount){
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
    
    public void kdtEntries_editStarting(KDTEditEvent e) throws Exception {
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		Object longNumber = kdtEntries.getCell(e.getRowIndex(), LONGNUMBER).getValue();
			if(longNumber != null && longNumber.toString().trim().length() > 80){
				MsgBox.showInfo("分录第 "+(e.getRowIndex()+1)+" 行，框架投资规划编码超长\n请修改上级编码后再进行编辑！");
				kdtEntries.getEditManager().cancelEditing();
				e.setCancel(true);
			}
    	}
    }
    
    public void kdtEntries_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	setSmallBtnEnable();
    }
    
    public void kdtEntries_editStarted(KDTEditEvent e) throws Exception {
    	//如果编辑的是编码列则需求获取编码列的编辑器、控制上级编码不可以被删除、修改
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
    
    public void kdtEntries_editStopped(KDTEditEvent e) throws Exception {
    	//设置编码
    	Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue == null && newValue == null){
			return;
		}
		if("".equals(year) || year==null){
			FDCMsgBox.showInfo("请先选择投资年度！");
			SysUtil.abort();
		}
		this.dataBinder.storeFields();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		int colIndex = e.getColIndex();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		IRow row = this.kdtEntries.getRow(rowIndex);
		int newLevel = UIRuleUtil.getInt(row.getCell("level").getValue());
		//------------------------------
		String key = kdtEntries.getColumnKey(e.getColIndex());
		if (key.equals("price")||key.equals("quantities")||key.equals("amount")||key.equals("investAmount")) {
			Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if(oldLongNumber == null){
				FDCMsgBox.showInfo("请填写编码！");
				kdtEntries.getRow(rowIndex).getCell(key).setValue(e.getOldValue());
				return;
			}
			Object nextHeadNumber = kdtEntries.getRowCount()!=rowIndex+1?kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue():"";
			if(oldLongNumber.equals(nextHeadNumber)){
				FDCMsgBox.showInfo("非明细目录不能录入金额！");
				kdtEntries.getRow(rowIndex).getCell(key).setValue(e.getOldValue());
				kdtEntries.getRow(rowIndex).getCell(key).getStyleAttributes().setFontColor(Color.gray);
				return;
			}
			
			BigDecimal amountBig = UIRuleUtil.getBigDecimal((row.getCell("amount").getValue()));//投资总金额
			BigDecimal cumulative = UIRuleUtil.getBigDecimal(row.getCell( "cumulativeInvest").getValue());//累计投资
			BigDecimal q = UIRuleUtil.getBigDecimal(row.getCell( "quantities").getValue());//数量
			BigDecimal p = UIRuleUtil.getBigDecimal(row.getCell( "price").getValue());//单价
			if(key.equals("amount")){
				if((UIRuleUtil.getBigDecimal(row.getCell("investAmount").getValue()).intValue()==0))
					row.getCell( "investAmount").setValue(UIRuleUtil.getBigDecimal(row.getCell( "amount").getValue()));
			}
			if(key.equals("quantities")||key.equals("price"))
			{
				if((UIRuleUtil.getBigDecimal(row.getCell("price").getValue()).intValue()!=0 
						&& UIRuleUtil.getBigDecimal(row.getCell("quantities").getValue()).intValue()!=0))
				{
					row.getCell( "investAmount").setValue(p.multiply(q));
				}
			}
			BigDecimal investAm = UIRuleUtil.getBigDecimal(row.getCell( "investAmount").getValue());//本年度投资金额
			row.getCell("balance").setValue(amountBig.subtract(cumulative).subtract(investAm));
			if((UIRuleUtil.getBigDecimal(row.getCell("price").getValue()).intValue()!=0 
							&& UIRuleUtil.getBigDecimal(row.getCell("quantities").getValue()).intValue()!=0))
				row.getCell("investAmount").getStyleAttributes().setLocked(true);
			else
				row.getCell("investAmount").getStyleAttributes().setLocked(false);
			if(UIRuleUtil.getBigDecimal(row.getCell( "investAmount").getValue()).compareTo(UIRuleUtil.getBigDecimal((row.getCell("amount").getValue())))>0)
			{
				FDCMsgBox.showInfo("本年度金额不能超过总金额！");
				row.getCell("quantities").setValue(BigDecimal.ZERO);
				row.getCell("investAmount").setValue(BigDecimal.ZERO);
				row.getCell("balance").setValue(amountBig.subtract(cumulative).subtract(UIRuleUtil.getBigDecimal(row.getCell("investAmount").getValue())));
				row.getCell("investProportion").setValue(0);
				caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("investAmount"), newLevel);
				caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("balance"), newLevel);
				caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("amount"), newLevel);
					
				return;
			}
			if(row.getCell("cumulativeInvest").getValue()!=null){
				if((UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"investAmount").getValue()))).
						add(UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"cumulativeInvest").getValue()))).
						compareTo(UIRuleUtil.getBigDecimal((kdtEntries.getCell(rowIndex,"amount").getValue())))>0)
				{
					FDCMsgBox.showInfo("本年度金额与累计金额之和不能超过总金额！");
					row.getCell("quantities").setValue(0);
					row.getCell("investAmount").setValue(0);
					row.getCell("balance").setValue(amountBig.subtract(cumulative).
							subtract(UIRuleUtil.getBigDecimal(row.getCell("investAmount").getValue())));
					row.getCell("investProportion").setValue(0);
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("investAmount"), newLevel);
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("balance"), newLevel);
					return;
				}
			}
			
			caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("investAmount"), newLevel); //本年度投资金额在修改时自动向上汇总
			caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("balance"), newLevel);// 投资余额在修改时自动向上汇总
			caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("amount"), newLevel);// 投资总金额在修改时自动向上汇总
			
			//插入子分录是
			for(int i=0;i<kdtEntries.getRowCount();i++){
				IRow irow = kdtEntries.getRow(i);
				setEntryEditDetail(irow,i);
			}
			
			//将分录部分金额值传递到年度投资计划
			if(investZ!=null)
				investZ.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("amount").getValue()));//投资总金额
			if(investB!=null && !"1".equals(isAdjust))
				investB.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("investAmount").getValue()));//本年度投资金额
			if(investTZ!=null)
				investTZ.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("investAmount").getValue()));//调整金额
			if(investL!=null)
				investL.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("cumulativeInvest").getValue()));//累计投资
			if(investY!=null)
				investY.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("balance").getValue()));//投资余额
			if(investGS!=null)
				investGS.setValue(UIRuleUtil.getBigDecimal(this.kdtEntries.getFootRow(0).getCell("investAmount").getValue()));//估算金额
			
		}
		//------
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
					FDCMsgBox.showInfo("请填写编码！");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
				Object nextHeadNumber = kdtEntries.getRowCount()!=rowIndex+1?kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue():"";
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("非明细目录没有合同类型！");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
			}
		}
		for(int i=0;i<kdtEntries.getRowCount();i++){
			row = kdtEntries.getRow(i);
			newLevel = UIRuleUtil.getInt(row.getCell("level").getValue());
			BigDecimal amountBig = UIRuleUtil.getBigDecimal((row.getCell("amount").getValue()));//投资总金额
			BigDecimal cumulative = UIRuleUtil.getBigDecimal(row.getCell( "cumulativeInvest").getValue());//累计投资
			BigDecimal q = UIRuleUtil.getBigDecimal(row.getCell( "quantities").getValue());//数量
			BigDecimal p = UIRuleUtil.getBigDecimal(row.getCell( "price").getValue());//单价
			if(row.getCell("cumulativeInvest").getValue()!=null){
				if((UIRuleUtil.getBigDecimal((kdtEntries.getCell(i,"investAmount").getValue()))).
						add(UIRuleUtil.getBigDecimal((kdtEntries.getCell(i,"cumulativeInvest").getValue()))).
						compareTo(UIRuleUtil.getBigDecimal((kdtEntries.getCell(i,"amount").getValue())))>0)
				{
					row.getCell("quantities").setValue(0);
					row.getCell("investAmount").setValue(0);
					row.getCell("balance").setValue(amountBig.subtract(cumulative).
							subtract(UIRuleUtil.getBigDecimal(row.getCell("investAmount").getValue())));
					row.getCell("investProportion").setValue(0);
					caclTotalAmount(i, kdtEntries.getColumnIndex("investAmount"), newLevel);
					caclTotalAmount(i, kdtEntries.getColumnIndex("balance"), newLevel);
				}
			}
			BigDecimal investAm = UIRuleUtil.getBigDecimal(row.getCell( "investAmount").getValue());//本年度投资金额
			row.getCell("investProportion").setValue(amountBig.compareTo(BigDecimal.ZERO)==0?0:(investAm.divide(amountBig ,4, RoundingMode.HALF_UP)));
		}
    }
    public static String year = "";
    private void setEntryEditDetail(IRow row,int k) throws EASBizException, BOSException
    {
    	for (int i = 0; i < this.kdtEntries.getRowCount(); i++) 
    	{
    		IRow irow = this.kdtEntries.getRow(i);
    		Object oldLongNumber = irow.getCell(LONGNUMBER).getValue();
    		Object nextHeadNumber = kdtEntries.getRowCount()!=i+1?this.kdtEntries.getCell(i+1,HEADNUMBER).getValue():"";
    		if(oldLongNumber!=null && oldLongNumber.equals(nextHeadNumber))
    		{
    			this.kdtEntries.getCell(i, "isLeaf").setValue(false);
    		}else{
    			this.kdtEntries.getCell(i, "isLeaf").setValue(true);
    		}
    		ProgrammingEntryInfo entryInfo = editData.getEntries().get(i);
    		entryInfo.setInvestAmount(UIRuleUtil.getBigDecimal(irow.getCell("investAmount").getValue()));
    		entryInfo.setBalance(UIRuleUtil.getBigDecimal(irow.getCell("balance").getValue()));
    		entryInfo.setInvestProportion(Double.parseDouble(irow.getCell("investProportion").getValue().toString()));
    		entryInfo.setCumulativeInvest(UIRuleUtil.getBigDecimal(irow.getCell("cumulativeInvest").getValue()));
		}
    	String projectName = txtprojectName.getText();
		String projectNumber = txtprojectNumber.getText();
		ProgrammingEntryInfo entryInfo = editData.getEntries().get(k);
		
		String feeNumber = UIRuleUtil.getString(row.getCell("longNumber").getValue());
		String feename = UIRuleUtil.getString(row.getCell("name").getValue());
		BigDecimal GoalCost = UIRuleUtil.getBigDecimal(row.getCell("amount").getValue());
		BigDecimal Assigned = UIRuleUtil.getBigDecimal(row.getCell("cumulativeInvest").getValue());
		BigDecimal ContractAssign = UIRuleUtil.getBigDecimal(row.getCell("investAmount").getValue());
		BigDecimal Assigning = UIRuleUtil.getBigDecimal(row.getCell("balance").getValue());
		BigDecimal Proportion = UIRuleUtil.getBigDecimal(row.getCell("investProportion").getValue());
		Boolean isLeaf = UIRuleUtil.getBoolean(row.getCell("isLeaf").getValue());
		Map map = new HashMap();
		for(int j=0;j<entryInfo.getCostEntries().size();j++){
			ProgrammingEntryCostEntryInfo costEntryInfo = entryInfo.getCostEntries().get(j);
			if(year.equals(costEntryInfo.getYear()) || costEntryInfo.getYear()==null){
				costEntryInfo.setContract(entryInfo);
				costEntryInfo.setYear(year);
				costEntryInfo.setProject(projectName);
				costEntryInfo.setNumber(projectNumber);
				costEntryInfo.setFeeNumber(feeNumber);
				costEntryInfo.setFeeName(feename);
				costEntryInfo.setProportion(Proportion);//投资比例
				costEntryInfo.setProportion(Proportion);//投资比例
				costEntryInfo.setGoalCost(GoalCost);//投资总额
				costEntryInfo.setAssigned(Assigned);// 累计投资（不含本年）
				costEntryInfo.setContractAssign(ContractAssign);//本年度投资金额
				costEntryInfo.setAssigning(Assigning);//投资余额
				costEntryInfo.setProportion(Proportion);//投资比例
				costEntryInfo.setDescription(UIRuleUtil.getString(row.getCell("remark").getValue()));
				costEntryInfo.setIsLast(isLeaf);
			}
			map.put(costEntryInfo.getYear(), costEntryInfo.getYear());
		}
		if(map.get(year)==null){
			ProgrammingEntryCostEntryInfo newCostEntryInfo = new ProgrammingEntryCostEntryInfo();
			newCostEntryInfo.setContract(entryInfo);
			newCostEntryInfo.setYear(year);
			newCostEntryInfo.setProject(projectName);
			newCostEntryInfo.setNumber(projectNumber);
			newCostEntryInfo.setFeeNumber(feeNumber);
			newCostEntryInfo.setFeeName(feename);
			newCostEntryInfo.setGoalCost(GoalCost);//投资总额
			newCostEntryInfo.setAssigned(Assigned);// 累计投资（不含本年）
			newCostEntryInfo.setContractAssign(ContractAssign);//本年度投资金额
			newCostEntryInfo.setAssigning(Assigning);//投资余额
			newCostEntryInfo.setProportion(Proportion);//投资比例
			newCostEntryInfo.setDescription(UIRuleUtil.getString(row.getCell("remark").getValue()));
			newCostEntryInfo.setIsLast(isLeaf);
			entryInfo.getCostEntries().add(newCostEntryInfo);
		}
		BigDecimal assigned = new BigDecimal(0);
		for(int j=0;j<entryInfo.getCostEntries().size();j++){
			ProgrammingEntryCostEntryInfo costEntryInfo = entryInfo.getCostEntries().get(j);
			if(Integer.parseInt(year)>Integer.parseInt(costEntryInfo.getYear())){
				assigned = assigned.add(UIRuleUtil.getBigDecimal(costEntryInfo.getContractAssign()));
			}
		}
		row.getCell("cumulativeInvest").setValue(assigned);// 累计投资（不含本年）
    }
    
    
    private void setAddRow(ProgrammingEntryInfo programmingEntry) throws EASBizException, BOSException
    {
    	ProgrammingEntryCostEntryInfo newCostEntryInfo = new ProgrammingEntryCostEntryInfo();
		newCostEntryInfo.setId(BOSUuid.create(newCostEntryInfo.getBOSType()));
		newCostEntryInfo.setProject(planInfo.getProjectName());
		newCostEntryInfo.setContract(programmingEntry);
		newCostEntryInfo.setNumber(planInfo.getNumber());
		newCostEntryInfo.setInvestYear(investYearInfo);
		newCostEntryInfo.setGoalCost(BigDecimal.ZERO);//投资总额
		newCostEntryInfo.setAssigned(BigDecimal.ZERO);// 累计投资（不含本年）
		newCostEntryInfo.setContractAssign(BigDecimal.ZERO);//本年度投资金额
		newCostEntryInfo.setAssigning(BigDecimal.ZERO);//投资余额
		newCostEntryInfo.setProportion(BigDecimal.ZERO);//投资比例
		programmingEntry.getCostEntries().add(newCostEntryInfo);
    }
    
    /**
     * 锁定非明细节点且设置颜色
     */
    private void setHeadRowColor()
    {
    	Color color = new Color(192,192,192);
    	for (int i = 0; i < this.kdtEntries.getRowCount(); i++) 
    	{
    		IRow row = this.kdtEntries.getRow(i);
    		Object oldLongNumber = row.getCell(LONGNUMBER).getValue();
    		Object nextHeadNumber = kdtEntries.getRowCount()!=i+1?this.kdtEntries.getCell(i+1,HEADNUMBER).getValue():"";
    		if(oldLongNumber!=null && oldLongNumber.equals(nextHeadNumber))
    		{
    			for (int j = 0; j < this.kdtEntries.getColumnCount(); j++) 
    			{
    				String key = this.kdtEntries.getColumnKey(j);
    				if(key.equals("longNumber")||key.equals("name")){
    					continue;
    				}
    				ICell Icell = this.kdtEntries.getCell(i, j);
    				Icell.getStyleAttributes().setBackground(color);
    				Icell.getStyleAttributes().setLocked(true);
				}
    			this.kdtEntries.getCell(i, "contractType").getStyleAttributes().setBackground(color);
    			this.kdtEntries.getCell(i, "contractType").getStyleAttributes().setLocked(true);
    			this.kdtEntries.getCell(i, "isLeaf").setValue(false);
    		}else{
    			this.kdtEntries.getCell(i, "isLeaf").setValue(true);
    		}
		}
    }
	/**
	 * 金额类字段在修改时自动向上汇总
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
				parentNumber = UIRuleUtil.isNotNull(kdtEntries.getCell(loop, HEADNUMBER).getValue())?kdtEntries.getCell(loop, HEADNUMBER).getValue().toString():"";
			}
			dbSum = FDCHelper.ZERO;
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
				Object h = kdtEntries.getCell(i, HEADNUMBER).getValue();
				int cacl_Level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				String number = "";
				//判断要汇总的下标值
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
				//计算汇总值
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
    public void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kdtEntries_tableClicked(e);
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
		uiContext.put("programmingContract", rowObject);// 规划投资规划
		uiContext.put("pcCollection", pcCollection);// 规划投资规划集合
		uiContext.put("project", project);// 工程项目

		// 双击编辑附件
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntries.getColumnIndex("attachment")) {
			boolean isEdit = false;// 默认为查看状态
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
					kdtEntries.getCell(rowIndex, "attachment").setValue("存在附件");
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
				FDCMsgBox.showInfo("请填写编码！");
				return;
			}
			//校验是否有下级，如果有是没有明细的
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("非明细目录没有详细信息！");
					return;
				}
			}
			
			uiContext.put("yearPlanId", this.txtDescription.getText());
			uiContext.put("table", this.kDTable1);
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ViewPlanDetailUI.class.getName(), uiContext, null,OprtState.ADDNEW);
			uiWindow.show();
			// 绑定数据到分录上
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
			// 更新长名称
			setEntriesNameCol(rowIndex, level);
			// 更新长编码
			Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldLongNumber != null && newLongNumber != null) {
				if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
					setEntriesNumberCol(rowIndex, level);
				}
			}
			// 更新规划金额,规划余额,控制金额，控制余额的汇总
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("investAmount"), level);
			
			// 重新判断行颜色
			setMyFontColor();
			
		
			if(OprtState.VIEW.equals(getOprtState())){
				return;
			}
			
		}
    }

	/**
	 * 获取有框架所有投资规划
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
	 * 编码列更改更新数据、更新下级节点编码
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
     * 更改名称时调用、更改子节点长名称
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
	 * 修改下级编码、并重新加载编码器
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
	 * 为分录设置编辑器
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
			if(level > 1 && subNumber!=null){
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
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * 设置投资规划单据头信息
	 * 
	 * @param rowIndex
	 * @param rowObject
	 */
	private void setContractToEditData(int rowIndex, ProgrammingEntryInfo rowObject) {
	}

	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * 判断已规划金额是否大于总目标成本金额
	 * 
	 * @return 大于返回true
	 */
	private boolean verifyAmountVSAimCost() {
		return false;
	}
	
	/**
	 * 投资规划规划保存
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		veryfyForSave();
		
		super.actionSave_actionPerformed(e);
		sumClass.appendProFootRow(null, null);
		setHeadRowColor();
	}

	/**
	 * 保存校验
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public void veryfyForSave() throws EASBizException, BOSException {
		String name_txt = txtName.getText();
		String errrMsg = "投资规划框架版本名称";
		if (StringUtils.isEmpty(txtVersion.getText())) {
//			throw new EASBizException(new NumericExceptionSubItem("1", "版本号不能为空"));
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
//		if(ProgrammingFactory.getRemoteInstance().exists(filter)) {
//			txtName.requestFocus(true);
//			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + name_txt + "已经存在，不能重复！"));
//		}
		// 保存时去掉分录名称的前后空格
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
			
			Object number = kdtEntries.getCell(i, "longNumber").getValue();
    		Object head = UIRuleUtil.isNotNull(kdtEntries.getCell(i, HEADNUMBER).getValue())?kdtEntries.getCell(i, HEADNUMBER).getValue():"";
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
		
		verifyDataBySave();
		//保存时去掉分录名称的前后空格
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
		super.actionSubmit_actionPerformed(e);
		if (isBillModify()) {
			((IProgramming) getBizInterface()).billModify(versionGroup, curVersion); // 维护最新版本字段
		}
		setAuditBtnEnable();
		sumClass.appendProFootRow(null, null);
		setHeadRowColor();
		if (isBillModify()) {
			ProgrammingListUI parentUI = (ProgrammingListUI) getUIContext().get("parent");
			parentUI.refreshList();
			this.getUIContext().remove(ProgrammingListUI.IS_MODIFY);
		}
	}
	// 是否修订
	private boolean isBillModify() {
		Boolean isSet = (Boolean) getUIContext().get(ProgrammingListUI.IS_MODIFY);
		return isSet != null && isSet.booleanValue();
	}
    
	/**
	 * 提交前校验数据，名称、分录编码及名称
	 * @throws Exception
	 */
    public void verifyDataBySave() throws Exception {
    	veryfyForSave();
    	int rowCount = kdtEntries.getRowCount();
		//目标成本不为空20111130
		
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
				throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，编码超长，请重新输入！"));
			}
    		
    		Object proName = kdtEntries.getCell(i, "name").getValue();
    		if(proName == null || proName.toString().trim() == null){
    			throw new ProgrammingException(ProgrammingException.NAME_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		if(proName != null && proName.toString().trim().length() > 80){
    			throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，框架投资规划名称超长！"));
        	}
    		
    		Object longName = kdtEntries.getCell(i, "longName").getValue();
    		if(longName != null && !StringUtils.isEmpty(longName.toString())){
    			if(longName.toString().length() > 255){
    				throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，框架投资规划长名称超长\n请修改框架投资规划名称数据！"));
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
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "编码"});
        			}
        		}
        		
        		if(proName_2 != null && proName_2.toString().trim().length() > 0){
        			if(name.equals(proName_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "名称"});
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
//    					throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，合同类型不能为空！"));
    				}
    			}
    		}
    		else
    		{
    			if (kdtEntries.getCell(i, "contractType").getValue()== null){
//					throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，合同类型不能为空！"));
				}
    		}
    	}
    	for(int i=0;i<kdtEntries.getRowCount();i++){
			IRow row = kdtEntries.getRow(i);
			BigDecimal amountBig = UIRuleUtil.getBigDecimal((row.getCell("amount").getValue()));//投资总金额
			BigDecimal investAm = UIRuleUtil.getBigDecimal(row.getCell( "investAmount").getValue());//本年度投资金额
			row.getCell("investProportion").setValue(amountBig.compareTo(BigDecimal.ZERO)==0?0:(investAm.divide(amountBig ,4, RoundingMode.HALF_UP)));
		}
    }
    
    /**
     * 获取当前时间字符串，用于设置编码
     */
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
    
    /**
     * 设置名称列显示缩进效果、在前面加空格
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
    
    //覆盖掉父类的方法
    public void setAuditButtonStatus(String oprtType) {
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
			throw new EASBizException(new NumericExceptionSubItem("1", "存在更高版本不能进行此操作"));
		}
	}

    /**
     * output actionAudit_actionPerformed
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
			FDCMsgBox.showInfo("保存状态单据不允许审批！");
			SysUtil.abort();
		}
		if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				ProgrammingFactory.getRemoteInstance().audit(editData.getId());
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
				actionEdit.setEnabled(false);
				editData.setState(FDCBillStateEnum.AUDITTED);
				MsgBox.showInfo("审批成功！");
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
					throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能反审批"));
				}
				checkHighVersion();
				
				ProgrammingFactory.getRemoteInstance().unAudit(editData.getId());
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
				if(this.getOprtState().equals(STATUS_VIEW))
					actionEdit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				MsgBox.showInfo("反审批成功！");
			}
		}
		handleOldData();
	}
    
    /**
     * 全局侦听，鼠标点击分录外时，分录需要失去焦点
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

	public void attachListeners() {
		
	}

	public void detachListeners() {
		
	}

	public ICoreBase getBizInterface() throws Exception {
		return ProgrammingFactory.getRemoteInstance();
	}

	public KDTable getDetailTable() {
		return kdtEntries;
	}

	public KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	public String getEditUIName()
    {
        return com.kingdee.eas.port.pm.invest.investplan.client.ProgrammingEditUI.class.getName();
    }
	
	public SelectorItemCollection getSelectors() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("projectNumber"));
		sic.add(new SelectorItemInfo("projectName"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("soldArea"));
		sic.add(new SelectorItemInfo("aimCost.*"));
		sic.add(new SelectorItemInfo("aimCost.measureStage.id"));
		
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("entries.longNumber"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.controlAmount"));
		sic.add(new SelectorItemInfo("entries.parent.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.paymentType.*"));
		
		sic.add(new SelectorItemInfo("entries.costEntries.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costEntries.id"));
		sic.add(new SelectorItemInfo("entries.costEntries.project"));
		sic.add(new SelectorItemInfo("entries.costEntries.number"));
		sic.add(new SelectorItemInfo("entries.costEntries.investYear.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.goalCost"));
		sic.add(new SelectorItemInfo("entries.costEntries.assigned"));
		sic.add(new SelectorItemInfo("entries.costEntries.contractAssign"));
		sic.add(new SelectorItemInfo("entries.costEntries.assigning"));
		sic.add(new SelectorItemInfo("entries.costEntries.proportion"));
		sic.add(new SelectorItemInfo("entries.costEntries.description"));
		sic.add(new SelectorItemInfo("entries.costEntries.contract.*"));
		
		//取预估金额 20120420
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
		
		sic.add(new SelectorItemInfo("entries.quantities"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.unit.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entries.unit.id"));
			sic.add(new SelectorItemInfo("entries.unit.name"));
        	sic.add(new SelectorItemInfo("entries.unit.number"));
		}
    	sic.add(new SelectorItemInfo("entries.price"));
		
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
    public IObjectValue createNewData()
    {
       com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo objectValue = new com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo();
       objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
       objectValue.setVersionGroup(Uuid.create().toString());
       if(getUIContext().get("programmingInfo")!=null)
    	   objectValue = (ProgrammingInfo)getUIContext().get("programmingInfo");
       objectValue.setSourceBillId(getUIContext().get("yearPlanId").toString());
       objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
       objectValue.setVersion(null);
        return objectValue;
    }
    
    private void setProTableToSumField() {
		HashMap sumFields = getProSumFields();
		if (sumFields == null) return;
		//取出所有的KDTable及合计列信息
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[])sumFields.get(table);
			sumClass.setProTableToSumField(table, sumColNames);			
		}
	}
    
    private HashMap getProSumFields(){
    	HashMap sumFields = new HashMap();
    	sumFields.put(this.kdtEntries, new String[] {"balance", "amount","investAmount","cumulativeInvest" });
    	return sumFields;
    }
    
    public void handleOldData() {
		if(!(getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
    
    public void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
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
    	// 解决刷新数据合计不对问题
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
	}
	
	/**
	 * 从模板导入功能
	 */
	public void actionImports_actionPerformed(ActionEvent e) throws Exception {
		ProgrammingEntryCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
			if (entry.isIsCiting()||entry.isIsWTCiting()) {
				throw new EASBizException(new NumericExceptionSubItem("1", 
				"投资规划框架中存在被引用的框架投资规划\n不允许此操作"));
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("editData", editData);
  		uiContext.put("project", curProject);
  		uiContext.put("dataChangeListener", dataChangeListener);
  		uiContext.put("planInfo", planInfo);
  		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingImportUI.class.getName(), uiContext, null,	OprtState.ADDNEW , WinStyle.SHOW_ONLYLEFTSTATUSBAR);
  		ui.show();
		setDataObject(editData);
		loadFields();
		if (this.oprtState.equals(STATUS_ADDNEW)) {
			setNameDisplay();
		}
		setHeadRowColor();
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
	 * 一个根节点下的所有节点
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
	 * 从叶节点递归向上逐级汇总
	 * @param leafSet
	 */
	private void totalAmout(Set leafSet) {
		if (leafSet.isEmpty()) {
			return;
		}
		
		Set leafParentSet = new HashSet(); // 当前叶节点的父节点
		for (Iterator it = leafSet.iterator(); it.hasNext();) {
			BigDecimal leafAmount = FDCHelper.ZERO; // 当前叶节点金额
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
	 * 所有非叶节点数据清零，直接从叶节点汇总。
	 * @param leafSet
	 */
	private void cleanNotLeafAmout(Set leafSet) {
		ProgrammingEntryCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			if (!leafSet.contains(longNumber)) {
				// 不包含子节点的根节点不删除
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
	 * 另存为模板功能
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

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		Set<ProgrammingEntryInfo> set = new HashSet<ProgrammingEntryInfo>();
		for (int i = 0; i < editData.getEntries().size(); i++) 
		{
			ProgrammingEntryInfo programmingEntry = editData.getEntries().get(i);
			Map<String, ProgrammingEntryCostEntryInfo> map = new HashMap<String, ProgrammingEntryCostEntryInfo>();
			for (int j = 0; j < programmingEntry.getCostEntries().size(); j++) 
			{
				ProgrammingEntryCostEntryInfo entryInfo = programmingEntry.getCostEntries().get(j);
				String yearName = entryInfo.getInvestYear().getName();
				map.put(String.valueOf(Integer.parseInt(yearName)+1), entryInfo);
			}
			BigDecimal GoalCost = BigDecimal.ZERO;
			BigDecimal Assigned = BigDecimal.ZERO;
			BigDecimal ssigning = BigDecimal.ZERO;
			
			if(map.get(investYearInfo.getName())!=null)
			{
				ProgrammingEntryCostEntryInfo entryInfo = map.get(investYearInfo.getName());
				GoalCost = entryInfo.getGoalCost()!=null?entryInfo.getGoalCost():BigDecimal.ZERO;
				Assigned = GoalCost.subtract(entryInfo.getAssigning()!=null?entryInfo.getAssigning():BigDecimal.ZERO);
				ssigning = GoalCost.subtract(Assigned);
			}
			programmingEntry.setCumulativeInvest(Assigned);//累计投资
			if("年度投资计划V".equals(planInfo.getTitleName())){
				programmingEntry.setInvestAmount(BigDecimal.ZERO);//本年投资
				programmingEntry.setInvestProportion(0);//比例
				programmingEntry.setPrice(BigDecimal.ZERO);//单价
				programmingEntry.setQuantities(BigDecimal.ZERO);//数量
			}
			ProgrammingEntryCostEntryInfo newCostEntryInfo = new ProgrammingEntryCostEntryInfo();
			newCostEntryInfo.setId(BOSUuid.create(newCostEntryInfo.getBOSType()));
			newCostEntryInfo.setProject(planInfo.getProjectName());
			newCostEntryInfo.setContract(programmingEntry);
			newCostEntryInfo.setNumber(planInfo.getNumber());
			newCostEntryInfo.setInvestYear(investYearInfo);
			newCostEntryInfo.setGoalCost(GoalCost);//投资总额
			newCostEntryInfo.setAssigned(Assigned);// 累计投资（不含本年）
			newCostEntryInfo.setContractAssign(BigDecimal.ZERO);//本年度投资金额
			newCostEntryInfo.setAssigning(ssigning);//投资余额
			newCostEntryInfo.setProportion(BigDecimal.ZERO);//投资比例
			programmingEntry.getCostEntries().add(newCostEntryInfo);
			set.add(programmingEntry);
			
		}
		editData.setSourceBillId((String) getUIContext().get("yearPlanId"));
		Iterator<ProgrammingEntryInfo> iteror = set.iterator();
		int index = 0;
		while(iteror.hasNext())
		{
			dataBinder.loadLineFields(this.kdtEntries, this.kdtEntries.getRow(index), iteror.next());
			index+=1;
		}
		
		ModfInitEntryRow();
		if(this.txtVersion.getBigDecimalValue()!=null&&this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))==0){
			this.kdtEntries.getColumn("amount").getStyleAttributes().setLocked(false);
		}
		else
		{
//			this.kdtEntries.getColumn("amount").getStyleAttributes().setLocked(true);
//			conProgramming.removeButton(btnInsertLines);
//			conProgramming.removeButton(btnAddnewLine);
//			conProgramming.removeButton(btnRemoveLines);
//			conProgramming.removeButton(btnImports);
//			conProgramming.removeButton(btnExports);
			
		}
	}
	
	private void ModfInitEntryRow()
	{
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++)
		{
			IRow row = this.kdtEntries.getRow(i);
			int newLevel = UIRuleUtil.getInt(row.getCell("level").getValue());
			caclTotalAmount(i, kdtEntries.getColumnIndex("cumulativeInvest"), newLevel);
		}
		
		setHeadRowColor();
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