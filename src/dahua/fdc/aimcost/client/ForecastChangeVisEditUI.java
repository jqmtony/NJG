/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.sql.parser.Lexer;
import com.kingdee.bos.sql.parser.SqlStmtParser;
import com.kingdee.bos.sql.parser.TokenList;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisSplitEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptProdUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptProjUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillType;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.CustomerContractUtil;
import com.kingdee.eas.fdc.contract.client.DesignChangeAuditEditUI;
import com.kingdee.eas.fdc.contract.client.ProjectChangeAuditEditUI;
import com.kingdee.eas.fdc.contract.client.TechChangeAuditEditUI;
import com.kingdee.eas.fdc.contract.client.TechEconChangeAuditEditUI;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillEditUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadSplitListUI;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.fm.common.client.FMIsqlUI;
import com.kingdee.eas.fm.common.client.FMIsqlUIHandler;
import com.kingdee.eas.fm.common.client.SQLStmtInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ForecastChangeVisEditUI extends AbstractForecastChangeVisEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ForecastChangeVisEditUI.class);
	private final static String CANTAUDIT = "cantAudit";
    private int groupIndex=0;
	private static final String CANTEDIT = "cantEdit";
	private IUIWindow acctUI=null;
    private String contractBillId=null;
	private Map parentMap = new HashMap();
    private List oldCostAccountLongNumber = new ArrayList();
	private HashMap entrysMap = new HashMap(); 
	private ForecastChangeVisSplitEntryCollection entrys = null;
    protected FDCCostSplitForSL fdcCostSplit=new FDCCostSplitForSL(null);
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo editData2;

	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
    /**
     * output class constructor
     */
    public ForecastChangeVisEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	this.prmtcontractNumber.setEnabled(false);
    	
        this.txtChangeAmount.setHorizontalAlignment(2);		
        this.txtChangeAmount.setDataType(1);		
        this.txtChangeAmount.setSupportedEmpty(true);		
        this.txtChangeAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtChangeAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtChangeAmount.setPrecision(2);	
        
        this.txtBanane.setHorizontalAlignment(2);		
        this.txtBanane.setDataType(1);		
        this.txtBanane.setSupportedEmpty(true);		
        this.txtBanane.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtBanane.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtBanane.setPrecision(2);		
        this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
    	
    	this.toolBar.addComponentAfterComponent(this.btnRevise, this.btnBananZreo);
    	this.btnRevise.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_duizsetting"));
    	super.onLoad();
    	initButtonStatus();
    	initTable();
    	setButtonStatus();
    	contractBillId = editData.getContractNumber().getId().toString();
    	
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
    	
    	this.txtversion.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtamount.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtcontractAmount.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtChangeAmount.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtSplitedAmount.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtUnSplitAmount.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtBanane.setHorizontalAlignment(JTextField.RIGHT);
//    	DecimalFormat df2 = new DecimalFormat("V###,##0.0");
//    	this.txtversion.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(df2)));
    	editData2 = editData;
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	KDTSortManager sortManager = new KDTSortManager(kdtSplitEntry){
			public void sort(int colIndex) {
				super.sort(colIndex);
			}
		};   
		sortManager.setSortAuto(false);   
		sortManager.setClickCount(10);
		for(int i = 0; i<kdtSplitEntry.getColumnCount();i++){  
		    this.kdtSplitEntry.getColumn(i).setSortable(false);   
		}  
    }
     
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		this.btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		
		initContainerButton(kDContainer1,kdtEntrys_detailPanel);
		
		setEnableAcion(new ItemAction[]{actionAddNew,actionCreateTo,actionCopy,actionLast,actionFirst,actionNext,actionPre});
		
		this.actionAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		this.actionSplitProj.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showparent"));
		this.actionSplitBotUp.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showsubflow"));
		this.actionSplitProd.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_citetree"));
		this.actionRemoveLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		this.actionAcctSelect.setEnabled(true);
		this.actionSplitProj.setEnabled(true);
		this.actionSplitProd.setEnabled(true);
		this.actionSplitBotUp.setEnabled(true);
		
		KDWorkButton btnAcctSelect = (KDWorkButton)this.kDContainer3.add(actionAcctSelect);
		btnAcctSelect.setText("�ɱ���Ŀ");
		KDWorkButton btnSplitProj = (KDWorkButton)this.kDContainer3.add(actionSplitProj);
		btnSplitProj.setText("�Զ����");
		KDWorkButton btnSplitBotUp = (KDWorkButton)this.kDContainer3.add(actionSplitBotUp);
		btnSplitBotUp.setText("ĩ�����");
		KDWorkButton btnSplitProd = (KDWorkButton)this.kDContainer3.add(actionSplitProd);
		btnSplitProd.setText("��Ʒ���");
		KDWorkButton btnRemoveLine = (KDWorkButton)this.kDContainer3.add(actionRemoveLine);
		btnRemoveLine.setText("ɾ����¼");
    }
    
    public static void setEnableAcion(ItemAction action[]){
    	for (int i = 0; i < action.length; i++) {
    		ItemAction ACTION = action[i];
    		if(ACTION==null)
    			continue;
    		ACTION.setEnabled(false);
    		ACTION.setVisible(false);
		}
    }
    
    private void initButtonStatus(){
    	this.txtNumber.setRequired(true);
    	this.pkBizDate.setRequired(true);
    	this.kdtEntrys.getColumn("itemName").setRequired(true);
    	this.kdtEntrys.getColumn("amount").setRequired(true);
    	this.actionBananZreo.setEnabled(true);
    }
    
    private void initContainerButton(KDContainer kDContainer,DetailPanel detail) {
		kDContainer.getContentPane().add(detail.getEntryTable(),BorderLayout.CENTER);
		kDContainer.addButton(detail.getAddNewLineButton());
		kDContainer.addButton(detail.getInsertLineButton());
		kDContainer.addButton(detail.getRemoveLinesButton());
		
		detail.getAddNewLineButton().setText("������¼");
		detail.getInsertLineButton().setText("�����¼");
		detail.getRemoveLinesButton().setText("ɾ����¼");
		ActionListener[] actionListeners = detail.getRemoveLinesButton().getActionListeners();
		if(actionListeners.length>0)
			detail.getRemoveLinesButton().removeActionListener(actionListeners[0]);
		detail.getRemoveLinesButton().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				btnRemoveLine_actionPerformed(e);
			}
		});
	}
    
    public void actionBananZreo_actionPerformed(ActionEvent e) throws Exception {
    	super.actionBananZreo_actionPerformed(e);
    	if(!editData.getStatus().equals(FDCBillStateEnum.AUDITTED)||editData.isBanZreo()){
    		MsgBox.showWarning("����δ��˻��Ѿ����������㣡");
    		SysUtil.abort();
    	}
    	editData.setBanZreo(true);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("banZreo");
    	ForecastChangeVisFactory.getRemoteInstance().updatePartial(editData, sic);
    	MsgBox.showInfo("�����ɹ�");
    	syncDataFromDB();
    }
    
    private void btnRemoveLine_actionPerformed(ActionEvent e) {
    	if ((kdtEntrys.getSelectManager().size() == 0)){
            FDCMsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
            return;
        }

        if(confirmRemove()){
            int top = kdtEntrys.getSelectManager().get().getBeginRow();
            int bottom = kdtEntrys.getSelectManager().get().getEndRow();
            for (int i = bottom; i >=top; i--) 
            	kdtEntrys.removeRow(i);
        }
        this.txtamount.setValue(UIRuleUtil.sum(kdtEntrys, "amount"));
        BigDecimal totalChangeAmount = BigDecimal.ZERO;
        for (int i = 0; i < kDTable1.getRowCount(); i++)
        	totalChangeAmount = FDCHelper.add(totalChangeAmount, kDTable1.getCell(i, 8).getValue());
        this.txtChangeAmount.setValue(totalChangeAmount);
        this.txtBanane.setValue(FDCHelper.subtract(this.txtamount.getBigDecimalValue(), this.txtChangeAmount.getBigDecimalValue()));
        this.txtUnSplitAmount.setValue(FDCHelper.subtract(this.txtamount.getBigDecimalValue(), this.txtSplitedAmount.getBigDecimalValue()));
	}
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	if(e.getClickCount()!=2 || e.getRowIndex()==-1)return;
    	String changeAuditId = UIRuleUtil.getString(this.kDTable1.getCell(e.getRowIndex(), 0).getValue());
    	if(UIRuleUtil.isNull(changeAuditId))return;
    	Map ctx = new UIContext(this); 
		ctx.put(UIContext.ID, changeAuditId);

		String uiName = "";
		SelectorItemCollection sic = new SelectorItemCollection();
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(changeAuditId), sic);
		if(billInfo.getBillType().getValue().equals("20"))
			uiName = DesignChangeAuditEditUI.class.getName();
		if(billInfo.getBillType().getValue().equals("30"))
			uiName = ProjectChangeAuditEditUI.class.getName();
		if(billInfo.getBillType().getValue().equals("40"))
			uiName = TechChangeAuditEditUI.class.getName();
		if(billInfo.getBillType().getValue().equals("50"))
			uiName = TechEconChangeAuditEditUI.class.getName();
		IUIWindow uiWindow = null;
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, ctx, null, OprtState.VIEW);
		uiWindow.show();
    }
    
    private void initTable(){
    	kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
    	
    	getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper.getTotalCellNumberEdit());
		getDetailTable().getColumn("price").setEditor(getCellNumberEdit());
		getDetailTable().getColumn("workLoad").setEditor(getCellNumberEdit());
		
		((KDTTransferAction) getDetailTable().getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		//���㵽�����һ��ʱ�����Զ�������
		disableAutoAddLine(getDetailTable());	
		
		initCtrlListener();
		
		//������		jelon 12/27/2006
		int idx=0;
		ForecastChangeVisSplitEntryInfo entry=null;
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			entry=(ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
		
		disableAutoAddLine(getDetailTable());
		disableAutoAddLineDownArrow(getDetailTable());
		disableEnterFocusTravel();
		Object[] listeners = getDetailTable().getListenerList().getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == KDTSelectListener.class)
			{
				getDetailTable().getSelectManager().removeKDTSelectListener(((KDTSelectListener) listeners[i + 1]));
			}
		}
    }
    
    protected void initCtrlListener(){
		//�������delete�¼�
		getDetailTable().setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					for (int i = 0; i < getDetailTable().getSelectManager().size(); i++)
					{
						KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int amount_index=getDetailTable().getColumnIndex("amount");
								int directAmt_index=getDetailTable().getColumnIndex("directAmt");
								int price_index=getDetailTable().getColumnIndex("price");
								int workLoad_index=getDetailTable().getColumnIndex("workLoad");
								//����в���������л��ߵ�Ԫ�������˵Ļ�����ȡ���¼�
								if((colIndex!=amount_index&&colIndex!=directAmt_index&&colIndex!=price_index&&colIndex!=workLoad_index)||(getDetailTable().getCell(rowIndex, colIndex).getStyleAttributes().isLocked())) {
									e.setCancel(true);
									continue;
								}
								try
								{
									getDetailTable().getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									kdtSplitEntry_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, 
											rowIndex, colIndex,false,1));
								} catch (Exception e1)
								{
									handUIExceptionAndAbort(e1);
								}
							}
//							e.setCancel(true);
						}
					}

				}
				else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
/*					int col=getDetailTable().getSelectManager().getActiveColumnIndex();
					int row=getDetailTable().getSelectManager().getActiveRowIndex();
					if(col<0||row<0||getDetailTable().getCell(row, col).getStyleAttributes().isLocked()){
						e.setCancel(true);
					}*/
//					e.setCancel(true);
					getDetailTable().putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
		getDetailTable().setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					getDetailTable().putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable��KDTEditListener���ڱ༭��ʱ�򴥷���
		 * KDTPropertyChangeListener������ɾ����ճ���ȵ��µ�Ԫ��value�����仯��ʱ�򶼻ᴥ����
		 */
		getDetailTable().addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // ���嵥Ԫ��ֵ�����仯
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(getDetailTable().getClientProperty("ACTION_PASTE")!=null){
			    		//����editStop�¼�
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		KDTEditEvent event=new KDTEditEvent(getDetailTable());
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = getDetailTable().getCell(rowIndex,colIndex);
			    		if(cell==null){
			    			return;
			    		}
			    		event.setValue(cell.getValue());
			    		try {
			    			kdtSplitEntry_editStopped(event);			    			
			    		} catch (Exception e1) {
			    			handUIExceptionAndAbort(e1);
			    		}
			    	}
			    }
			}
		});
//		getDetailTable().getSortMange().setEnableSortable(false);
	}
    
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED_MILLION);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	public KDTDefaultCellEditor getScaleCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(10);
        kdc.setMinimumValue(FDCHelper._ONE_HUNDRED_MILLION);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED_MILLION);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
    	FDCClientVerifyHelper.verifyEmpty(this, pkBizDate);
    	FDCClientVerifyHelper.verifyEmpty(this,kdtEntrys);
    	
    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if(UIRuleUtil.isNull(row.getCell("itemName").getValue())){
				MsgBox.showWarning("�������Ʋ���Ϊ�գ�����ִ�д˲�����");
				this.kdtEntrys.getEditManager().editCellAt(i, this.kdtEntrys.getColumnIndex("itemName"));
	    		SysUtil.abort();
			}
			if(UIRuleUtil.isNull(row.getCell("amount").getValue())){
				MsgBox.showWarning("Ԥ������Ϊ�ջ��㣬����ִ�д˲�����");
				this.kdtEntrys.getEditManager().editCellAt(i, this.kdtEntrys.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
    	if(UIRuleUtil.sum(kdtSplitEntry, "splitScale")!=100){
    		MsgBox.showWarning("��ֱ���������100������ִ�д˲�����");
    		SysUtil.abort();
    	}
    	
    	BigDecimal banale = UIRuleUtil.getBigDecimal(this.txtBanane.getBigDecimalValue());
    	if(banale.compareTo(BigDecimal.ZERO)==-1){
    		MsgBox.showWarning("Ԥ�����ǩ֤����С���ѷ���������ִ�д˲�����");
    		SysUtil.abort();
    	}
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	//modify by yxl
    	if(prmtcontractNumber.getValue()!=null && CustomerContractUtil.checkPcQkFromContract(((ContractBillInfo)prmtcontractNumber.getValue()).getId().toString())){
    		MsgBox.showInfo(this,CustomerContractUtil.CONTRACTINFO);
			abort();
    	}
    	super.actionSubmit_actionPerformed(e);
    	this.lockUIForViewStatus();
    	this.setOprtState("VIEW");
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeEditOrRemove(CANTEDIT);
    	super.actionEdit_actionPerformed(e);
    	setSaveActionStatus();
    }
    
	protected void checkBeforeEditOrRemove(String warning) throws BOSException {
		FDCBillStateEnum state = editData.getStatus();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception{
		//��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null
				&& editData.getStatus() != null
				&& editData.getStatus().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		 if(getOprtState().equals(STATUS_EDIT)) {
			 String warn = null;
			 if(state.equals(FDCBillStateEnum.AUDITTED)) {
				 warn = CANTUNAUDITEDITSTATE;
			 }
			 else {
				 warn = CANTAUDITEDITSTATE;
			 }
			 MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			 SysUtil.abort();
		 }
	}
	
	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        setDisplay();
        setSaveActionStatus();
        
        loadTable(kDTable1, getSql());
        
        kDTable1.setEnabled(false);
        kDTable1.getColumn(0).getStyleAttributes().setHided(true);
        BigDecimal totalChangeAmount = BigDecimal.ZERO;
        for (int i = 0; i < kDTable1.getRowCount(); i++) {
        	if(kDTable1.getCell(i, 7).getValue()!=null)
        		kDTable1.getCell(i, 7).setValue(ChangeBillStateEnum.getEnum(UIRuleUtil.getString(kDTable1.getCell(i, 7).getValue())));
        	if(kDTable1.getCell(i, 9).getValue()!=null)
        		kDTable1.getCell(i, 9).setValue(ChangeAuditBillType.getEnum(UIRuleUtil.getString(kDTable1.getCell(i, 9).getValue())));
        	
        	totalChangeAmount = FDCHelper.add(totalChangeAmount, kDTable1.getCell(i, 8).getValue());
		}
        
        this.txtChangeAmount.setValue(totalChangeAmount);
        this.txtBanane.setValue(FDCHelper.subtract(this.txtamount.getBigDecimalValue(), this.txtChangeAmount.getBigDecimalValue()));
    }
    
    
    private String getSql(){
    	StringBuffer sb = new StringBuffer();
    	BOSUuid contractId = editData.getContractNumber().getId();
    	sb.append(" SELECT CHANGEAUDITBILL.FID AS ID,  ");
    	sb.append(" PERIOD.FNumber AS �ڼ�,  ");
    	sb.append(" ORGUNIT.FName_l2 AS ��֯,  ");
    	sb.append(" CHANGEAUDITBILL.FNumber AS ���ǩ֤����,  ");
    	sb.append(" CHANGEAUDITBILL.FName AS ���ǩ֤����,  ");
    	sb.append(" CHANGEAUDITBILL.FBookedDate AS ҵ������,  ");
    	sb.append(" CURPROJECT.FName_l2 AS ������Ŀ,  ");
    	sb.append(" CHANGEAUDITBILL.FChangeState AS ״̬,  ");
    	sb.append(" SUPPENTRY.FCostAmount AS ������,  ");
    	sb.append(" CHANGEAUDITBILL.CFBillType AS ��������,  ");
    	sb.append(" CREATOR.FName_l2 AS �Ƶ���,  ");
    	sb.append(" CHANGEAUDITBILL.FCreateTime AS �Ƶ�����,  ");
    	sb.append(" AUDITOR.FName_l2 AS �����,  ");
    	sb.append(" CHANGEAUDITBILL.FAuditTime AS ������� ");
    	sb.append(" FROM T_CON_ChangeAuditBill AS CHANGEAUDITBILL ");
    	sb.append(" LEFT OUTER JOIN T_FDC_ChangeType AS AUDITTYPE ON CHANGEAUDITBILL.FAuditTypeID = AUDITTYPE.FID ");
    	sb.append(" LEFT OUTER JOIN T_PM_User AS AUDITOR ON CHANGEAUDITBILL.FAuditorID = AUDITOR.FID ");
    	sb.append(" LEFT OUTER JOIN T_FDC_JobType AS JOBTYPE ON CHANGEAUDITBILL.FJobTypeID = JOBTYPE.FID ");
    	sb.append(" LEFT OUTER JOIN T_PM_User AS CREATOR ON CHANGEAUDITBILL.FCreatorID = CREATOR.FID ");
    	sb.append(" LEFT OUTER JOIN T_FDC_CurProject AS CURPROJECT ON CHANGEAUDITBILL.FCurProjectID = CURPROJECT.FID ");
    	sb.append(" LEFT OUTER JOIN T_FDC_ChangeReason AS CHANGEREASON ON CHANGEAUDITBILL.FChangeReasonID = CHANGEREASON.FID ");
    	sb.append(" LEFT OUTER JOIN T_FDC_SpecialtyType AS SPECIALTYTYPE ON CHANGEAUDITBILL.FSpecialtyTypeID = SPECIALTYTYPE.FID ");
    	sb.append(" LEFT OUTER JOIN T_FDC_InvalidCostReason AS INVALIDCOSTREASON ON CHANGEAUDITBILL.FInvalidCostReasonID = INVALIDCOSTREASON.FID ");
    	sb.append(" LEFT OUTER JOIN T_BD_Period AS PERIOD ON CHANGEAUDITBILL.FPeriodId = PERIOD.FID ");
    	sb.append(" LEFT OUTER JOIN T_ORG_BaseUnit AS ORGUNIT ON CHANGEAUDITBILL.FOrgUnitID = ORGUNIT.FID ");
    	sb.append(" LEFT OUTER JOIN T_CON_ChangeSupplierEntry AS SUPPENTRY ON CHANGEAUDITBILL.FID = SUPPENTRY.FParentID ");
    	sb.append(" LEFT OUTER JOIN T_CON_ContractBill AS CONTRACTBILL ON SUPPENTRY.FContractBillID = CONTRACTBILL.FID "); 
    	sb.append(" LEFT OUTER JOIN T_BD_Supplier AS PARTB ON CONTRACTBILL.FPartBID = PARTB.FID ");
    	sb.append(" where 1= 1 and SUPPENTRY.CFforecastChangeVi in (select fid from CT_AIM_ForecastChangeVis where CFContractNumberID='"+contractId+"')");
//    	sb.append(" and CONTRACTBILL.fid='").append(contractId).append("' and CHANGEAUDITBILL.CFBillType is not null and CHANGEAUDITBILL.CFBillType<>'10'");
//    	sb.append(" and CHANGEAUDITBILL.FChangeState='7Visa'");
    	return sb.toString();
    }
    
	/**
	 * ���������÷�̯��׼��ȫ����
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    protected void setDisplay(){
    	ForecastChangeVisSplitEntryInfo entry=null;
    	IRow row=null;
    	initDirectMap.clear();
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			row=kdtSplitEntry.getRow(i);
			entry = (ForecastChangeVisSplitEntryInfo)row.getUserObject();
			if(entry.getLevel()==0){
				setOneTreeDisplay(i);
				//�����ͬ��ֱ���ʱ�����ͷ�Ѳ��
				calcAmount(i);
			}
		}
		initDirectAssign();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        editData2 = editData;
    }

    /**
     * �޶�
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
    	if(editData.getId() == null) {
    		FDCMsgBox.showWarning("���ȱ��浥�ݣ�");
			return;
    	}
    	if(!editData.getStatus().equals(FDCBillStateEnum.AUDITTED)){
			FDCMsgBox.showWarning("����δ�������������޶���");
			return;
		}
		if(!editData.isIsLast()){
			FDCMsgBox.showWarning("�������°棬�������޶���");
			return;
		}
    	ForecastChangeVisInfo info = getSelectorsForBillInfo(editData.getId().toString());
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractNumber.id",info.getContractNumber().getId()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ForecastChangeVisFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"�������޶���");
			return;
		}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("ForInfo", info);
		uiContext.put("IsModify", true);
		uiContext.put("verson", FDCHelper.add(info.getVersion(), BigDecimal.ONE));
		
		IUIWindow ui = UIFactory.createUIFactory().create(getEditUIName(), uiContext, null,	OprtState.ADDNEW);
		ui.show();
    }
    public ForecastChangeVisInfo getSelectorsForBillInfo(String id) throws EASBizException, BOSException {
    	SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isLast"));
        sic.add(new SelectorItemInfo("banZreo"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractNumber.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractNumber.id"));
        	sic.add(new SelectorItemInfo("contractNumber.number"));
        	sic.add(new SelectorItemInfo("contractNumber.name"));
		}
        sic.add(new SelectorItemInfo("contractName"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("remake"));
        sic.add(new SelectorItemInfo("auditDate"));
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("entrys.itemName"));
    	sic.add(new SelectorItemInfo("entrys.amount"));
    	sic.add(new SelectorItemInfo("entrys.remake"));
    	sic.add(new SelectorItemInfo("splitEntry.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("splitEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("splitEntry.amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("splitEntry.product.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("splitEntry.product.id"));
			sic.add(new SelectorItemInfo("splitEntry.product.name"));
        	sic.add(new SelectorItemInfo("splitEntry.product.number"));
		}
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.curProject.id"));
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.id"));
    	sic.add(new SelectorItemInfo("splitEntry.level"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionType.name"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValue"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmount"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValueTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmountTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.otherRatioTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.splitType"));
    	sic.add(new SelectorItemInfo("splitEntry.workLoad"));
    	sic.add(new SelectorItemInfo("splitEntry.price"));
    	sic.add(new SelectorItemInfo("splitEntry.splitScale"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("contractAmount"));
        sic.add(new SelectorItemInfo("SplitedAmount"));
        sic.add(new SelectorItemInfo("UnSplitAmount"));
        sic.add("splitEntry.costAccount.curProject.isLeaf");
        sic.add("splitEntry.costAccount.curProject.longNumber");
        sic.add("splitEntry.costAccount.curProject.number");
        sic.add("splitEntry.costAccount.curProject.name");
        sic.add("splitEntry.costAccount.curProject.displayName");
        sic.add("splitEntry.costAccount.name");
        sic.add("splitEntry.costAccount.longNumber");
        sic.add("splitEntry.costAccount.displayName");
    	return ForecastChangeVisFactory.getRemoteInstance().getForecastChangeVisInfo(new ObjectUuidPK(id),sic);
    }
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(!editData.isIsLast()){
    		FDCMsgBox.showInfo("�������°棬���ܷ�������");
			this.abort();
    	}
    	checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
    	super.actionUnAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if (isModify()) {
			FDCMsgBox.showInfo("�����ѱ��޸ģ������ύ��");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
    	super.actionAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
    }
    
	protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	
	protected void setSaveActionStatus() {
		if (editData.getStatus() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
		setButtonStatus();
	}
     
	protected void syncDataFromDB() throws Exception {
		//�ɴ��ݹ�����ID��ȡֵ����
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return new ForecastChangeVisSplitEntryInfo();
    }
    
	public void actionAcctSelect_actionPerformed(ActionEvent e) throws Exception {
		CostAccountCollection accts=null;
		if(acctUI==null){
			Map map = getUIContext();
			//��UIContext�л�õ�ǰID
			String costBillId = editData.getContractNumber().getId().toString();
			CurProjectInfo curProject = editData.getContractNumber().getCurProject();
			/* modified by zhaoqin for R130927-0088 on 2013/12/23 end */			
			
			//��ñ���ͬ������ڹ�����Ϣ������UIContext��������ѡ���Ŀ
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",curProject);
			if (contractBillId != null) {
				uiContext.put("contractBillId", contractBillId);
			}
			/************* ���Ϻ�ͬ���²�� *************/
//			uiContext.put("txtCostBillNumber", txtCostBillNumber.getText());
			/************* ���Ϻ�ͬ���²�� *************/
			// ����ģʽ���������븶���ֲ���ʾ�ɲ��ѡ��
//			if (isFinacial()) {
//				uiContext.put("isFinacial", Boolean.TRUE);
//			}
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)map.get("node");
			//���Ϊ��������������ֱ�Ӵ�CURRENT.VO�л��CurProjectInfo
			if(costBillId==null){
				if(map.get("CURRENT.VO") instanceof ConChangeSplitInfo){
					ConChangeSplitInfo info = (ConChangeSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof ContractCostSplitInfo){
					ContractCostSplitInfo info = (ContractCostSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof PaymentSplitInfo){
					PaymentSplitInfo info = (PaymentSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				
			}
//			//��uiContext�л����FDCSplitListUI�б���Ľڵ���Ϣ
//			if(node!=null && (node.getUserObject() instanceof OrgStructureInfo)){
//				OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
//			if(node!=null && node.getUserObject() instanceof CurProjectInfo){
//				CurProjectInfo info = (CurProjectInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
			
			
//			uiContext.put("curProject",editData.getCurProject());
//			uiContext.put("isMeasureSplit", isMeasureContract()?Boolean.TRUE:null);
			acctUI=UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(),	uiContext, null , null);       
		}else{
			((CostSplitAcctUI) acctUI.getUIObject()).actionNoneSelect_actionPerformed(null);
		}
		acctUI.show();
		IUIWindow uiWin=acctUI;
		
		if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {	
			accts=((CostSplitAcctUI) uiWin.getUIObject()).getData();
			parentMap = ((CostSplitAcctUI) uiWin.getUIObject()).getParentIdMap();
		}else{
			return;
		}
		

		CostAccountInfo acct=null;
		
		ForecastChangeVisSplitEntryInfo entry=null;
		IRow row=null;
		boolean isExist=false;
		// �ڲ���һ�廯����ģʽ�����˲��� ɾ������ϸ��Ŀ
//		removeParentCostAccount(accts);
		
		for(Iterator iter=accts.iterator(); iter.hasNext();){
			acct = (CostAccountInfo)iter.next();
			
			//�жϿ�Ŀ�Ƿ����
			isExist=false;
			for(int i=0; i<kdtSplitEntry.getRowCount(); i++){			
				entry=(ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
								
				//����ѡ����������ַ������Ѵ��ڵĿ�Ŀ		jelon 12/6/06
				//if(entry.getCostAccount().getId().equals(acct.getId())){
				if(entry.getLevel()==0 && entry.getCostAccount().getId().equals(acct.getId())){
					isExist=true;
					break;
				}
			}
			if(!isExist){
				
				//entry=new FDCSplitBillEntryInfo();
				entry=(ForecastChangeVisSplitEntryInfo)createNewDetailData(kdtSplitEntry);
				entry.setCostAccount(acct);  
				entry.setLevel(0);
				entry.setIsLeaf(true);		//Jelon 	Dec 11, 2006
				entry.setAmount(FDCHelper.ZERO);
				
				//������	jelon 12/27/2006
				groupIndex++;
				entry.setIndex(groupIndex);
				
				row=addEntry(entry);
				setDisplay(row.getRowIndex());

			}				
		}
		setMenuSplitState();
		// ��ֱ�ӽ��ı���ɫ���óɰ�ɫ
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName())
				|| className.equals(PaymentSplitListUI.class.getName())) {
			for (int k = kdtSplitEntry.getRowCount() - 1; k > 0; k--) {
				if (kdtSplitEntry.getRow(k) != null && kdtSplitEntry.getRow(k).getCell("directAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
				if (kdtSplitEntry.getRow(k) != null
						&& kdtSplitEntry.getRow(k).getCell("directPayedAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directPayedAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
			}
		} else if (className.equals(WorkLoadConfirmBillListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			for (int k = kdtSplitEntry.getRowCount() - 1; k > 0; k--) {
				if (kdtSplitEntry.getRow(k) != null && kdtSplitEntry.getRow(k).getCell("directAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(new Color(0xffffff));
			}
		}
		setOneEntryAmt(txtamount.getBigDecimalValue());
	}
	
	 /**
     * ���������һ����Ŀ����������Զ����������Ĺ���
     * �������ܻ���󵽻�����֧�����в��
     * 
     * @param shouldSplitAmt:Ӧ����
     */
	private void setOneEntryAmt(BigDecimal shouldSplitAmt) throws Exception{
//		if(kdtSplitEntry.getRowCount()==1){
//			KDTEditEvent event = new KDTEditEvent(
//					kdtSplitEntry, null, null, 0,
//					kdtSplitEntry.getColumnIndex("amount"), true, 1);
//			final IRow row = kdtSplitEntry.getRow(0);
//			row.getCell("amount").setValue(shouldSplitAmt);
//			event.setValue(shouldSplitAmt);
//			kdtSplitEntry_editStopped(event);
//		}
	}
	
	public void setMenuSplitState() {
		// �µĳɱ���Ŀ���뼯��
		List newCostAccountLongNumber = new ArrayList();
		// �ж��Ƿ񹤳�����ֺ͸����ִ�
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillListUI.class.getName()) || className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			// �������γɱ������µĳɱ���Ŀ����
			String longNumber = null;
			PaymentSplitEntryInfo info = null;
			for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
				info = (PaymentSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
				// �жϵ�ǰ�Ƿ�ɱ���Ŀ
				if (info.getCostAccount() instanceof CostAccountInfo) {
					newCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
				}
			}
			// �жϾɳɱ���Ŀ������³ɱ���Ŀ�����Ƿ�һ��
			if (!oldCostAccountLongNumber.containsAll(newCostAccountLongNumber)) {
				this.getUIContext().put("isCanEnable", Boolean.FALSE);
				PaymentSplitEntryInfo tmpInfo = null;
				// �����µı�������ж��Ƿ�ȫ��������ϸ��Ŀ
				for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
					tmpInfo = (PaymentSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
					// �жϵ�ǰ���Ƿ�ɱ���Ŀ
					if (tmpInfo.getCostAccount() instanceof CostAccountInfo) {
						// �ж��Ƿ�����ϸ�ɱ���Ŀ��������Ǿ����ð�ť�ɱ༭״̬����������ֺ͸�����
						if (!tmpInfo.getCostAccount().isIsLeaf()) {
							this.getUIContext().put("isCanEnable", Boolean.FALSE);
							return;
						} 
					}
				}
			}
		}
	}
	
    private void setDisplay(int rowIndex){
    	initDirectMap.clear();
    	setOneTreeDisplay(rowIndex);
    	initDirectAssign();
    	
    	setDisplay();
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selectors = super.getSelectors();
    	selectors.add("splitEntry.costAccount.curProject.isLeaf");
    	selectors.add("splitEntry.costAccount.curProject.longNumber");
    	selectors.add("splitEntry.costAccount.curProject.number");
    	selectors.add("splitEntry.costAccount.curProject.name");
    	selectors.add("splitEntry.costAccount.curProject.displayName");
    	selectors.add("splitEntry.costAccount.name");
    	selectors.add("splitEntry.costAccount.longNumber");
    	selectors.add("splitEntry.costAccount.displayName");
    	return selectors;
    }
    
    
    private void setOneTreeDisplay(int rowIndex){
    	ForecastChangeVisSplitEntryInfo topEntry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();    	
    	int topLevel=topEntry.getLevel();		
        CostAccountInfo topAcct=topEntry.getCostAccount();

        ForecastChangeVisSplitEntryInfo entry=null;
        IRow row=null;
        ICell cell=null;
        String display=null;
        int level=0;
        
        CostAccountInfo acct=null;
        CurProjectInfo proj=null;
        
        

    	NodeClickListener nodeClickListener = new NodeClickListener(){
    		public void doClick(CellTreeNode source, ICell cell, int type)	{
    			//��Ŀչ������Ʒչ��Ӧ�ý������� by sxhong 2009/02/05
    			if(source!=null&&!source.isCollapse()&&source.isHasChildren()){
    				//�����¼����е�+���-
    				int level=source.getTreeLevel();
    				for(int i=cell.getRowIndex()+1;i<getDetailTable().getRowCount();i++){
    					if(cell.getColumnIndex()==getDetailTable().getColumnIndex("costAccount.curProject.name")){
    						ICell cell2 = getDetailTable().getCell(i, "costAccount.curProject.name");
    						if(cell2.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell2.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    						ICell cell3 = getDetailTable().getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							node.setCollapse(false);
    						}
    					}else if(cell.getColumnIndex()==getDetailTable().getColumnIndex("costAccount.name")){
    						ICell cell3 = getDetailTable().getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    					}
    				}
    			}
     		}
    	};
        
        for(int i=rowIndex; i<kdtSplitEntry.getRowCount(); i++){
        	row=kdtSplitEntry.getRow(i);   
			entry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			
			level=entry.getLevel();
			
			acct=entry.getCostAccount();
			if(acct==null){
				proj=null;
			}else{
				proj=acct.getCurProject();
			}
				
			if(level>=topLevel){
				if(level==topLevel && i!=rowIndex){
					//��һ��������
					break;	
				}
				
				//���롢����
				if(entry.getCostAccount().getCurProject()!=null){
					//����
					row.getCell("costAccount.curProject.number").setValue(
							entry.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
					row.getCell("costAccount.number").setValue(
							entry.getCostAccount().getLongNumber().replace('!','.'));

					//����
					if(level==0){
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getDisplayName().replace('_','\\'));
						
					}else if(entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT && entry.isIsLeaf()){
						//��Ʒ�����ϸ
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						row.getCell("costAccount.name").setValue("");
						
					}else if(entry.isIsAddlAccount()){
						//���ӿ�Ŀ��ֱ�ӷ���
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						//row.getCell("costAccount.number").setValue(entry.getCostAccount().getLongNumber());
						row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());
						
					}else{
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getName());
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getName());	
					}		
					
					
					//��������
					if(level>=topLevel){
						CellTreeNode node = new CellTreeNode();
						node.addClickListener(nodeClickListener);			
						cell=row.getCell("costAccount.curProject.name");			
						// �ڵ��ֵ
						node.setValue(cell.getValue());
						// �Ƿ����ӽڵ�
						//if(entry.getCostAccount().getLongNumber().equals(topAcct.getLongNumber()) && !entry.getCostAccount().getCurProject().isIsLeaf()){
						/*if(entry.getCostAccount().getLongNumber().replace('!','.').equals(topAcct.getLongNumber().replace('!','.')) 
								&& !entry.getCostAccount().getCurProject().isIsLeaf()
								&& !isProdSplitLeaf(entry)){*/
						/*
						 * ���������Ƿ��к��ӽڵ� by 29 // if (!entry.isIsLeaf() // &&
						 * !entry.getCostAccount().getCurProject().isIsLeaf() //
						 * && //
						 * entry.getCostAccount().getLongNumber().replace('!',
						 * // '.').equals( //
						 * topAcct.getLongNumber().replace('!', '.'))) { //
						 * node.setHasChildren(true); // } else { // //
						 * node.setHasChildren(false); // }
						 */
						
						//node.setHasChildren(!entry.isIsAddlAccount());
						// �ڵ��������
						node.setTreeLevel(entry.getLevel());
						cell.getStyleAttributes().setLocked(false);
						cell.setValue(node);
						
						if(level!=topLevel){
							node = new CellTreeNode();
							node.addClickListener(nodeClickListener);			
							cell=row.getCell("costAccount.name");			
							// �ڵ��ֵ
							node.setValue(cell.getValue());
							/*
							 * ���������Ƿ��к��ӽڵ� by 29// �Ƿ����ӽڵ� //
							 * node.setHasChildren(!entry.getCostAccount(). //
							 * isIsLeaf() // || (!entry.isIsLeaf() && //
							 * entry.getCostAccount().isIsLeaf() && //
							 * entry.getSplitType()!=null && //
							 * entry.getSplitType() //
							 * .equals(CostSplitTypeEnum.PRODSPLIT)) );
							 */
							// �ڵ��������
							//node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel()+1);	
							}else{
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							}
											
							cell.getStyleAttributes().setLocked(false);
							cell.setValue(node);							
						}
						//end
					}
				}
								
								
				//��ɫ
				if(level==0){
					row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
					row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));					
					row.getCell("splitScale").getStyleAttributes().setBackground(new Color(0xFFFFFF));
				}else{
					if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT) && entry.getProduct()==null){
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
					}else{
						row.getStyleAttributes().setBackground(new Color(0xE8E8E3));
					}					
					row.getCell("amount").getStyleAttributes().setLocked(true);
					//�ǿ�Ŀ�в��ܱ༭ by hpw 2010-06-25
					row.getCell("splitScale").getStyleAttributes().setLocked(true);
					
					//���ӿ�Ŀ��������¼���
					/*
					if(entry.isIsAddlAccount() && entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
						if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)){						
						}else{
							row.getCell("amount").getStyleAttributes().setLocked(false);
							row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));									
						}					
					}*/
					if(entry.isIsAddlAccount() 
							&& proj!=null && proj.isIsLeaf()
							&& acct!=null && acct.isIsLeaf()
							&& !isProdSplitLeaf(entry)){
						row.getCell("amount").getStyleAttributes().setLocked(false);
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));								
					}
				}					
				if(isMeasureContract()){
//					row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
//					row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					setMeasureCtrl(row);
				}
				//ֱ�ӹ���
				initDirectAssign(row);		
				
			}else{
				break;
			}
			
        }
        for(int i=rowIndex;i<kdtSplitEntry.getRowCount();i++){
        	row=kdtSplitEntry.getRow(i);
			entry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()==0&&i!=rowIndex){
				break;
			}
			IRow rowNext = kdtSplitEntry.getRow(i + 1);
			// ȡ�¼��зǿ������Ҫ�ж�
			if (rowNext == null) {
				continue;
			}
			if (!entry.isIsLeaf() && rowNext.getStyleAttributes().isHided()) {
				Object obj = row.getCell("costAccount.name").getValue();
				CellTreeNode node=null;
				if(obj instanceof CellTreeNode){
					node=(CellTreeNode)obj;
					node.setCollapse(true);
				}
				
			}
        }
        //������׼
        setStandard(rowIndex);
    }
    
	public void actionSplitBotUp_actionPerformed(ActionEvent arg0) throws Exception {
		splitCost(CostSplitTypeEnum.BOTUPSPLIT);
	}
	
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!actionRemoveLine.isEnabled()||!actionRemoveLine.isVisible()) return;
        //if ((kdtSplitEntry.getSelectManager().size() == 0) || isTableColumnSelected(kdtSplitEntry))
    	if ((kdtSplitEntry.getSelectManager().size() == 0))
        {
            FDCMsgBox.showInfo(this, EASResource
                    .getString(FrameWorkClientUtils.strResource
                            + "Msg_NoneEntry"));

            //FDCMsgBox.showInfo(this,"û��ѡ�з�¼���޷�ɾ����");
            return;
        }

        //[begin]����ɾ����¼����ʾ����
        if(confirmRemove())
        {
            int top = kdtSplitEntry.getSelectManager().get().getBeginRow();
            int bottom = kdtSplitEntry.getSelectManager().get().getEndRow();
            
            int idx=0;
            int idx1,idx2;
            
            boolean isTrue=false;
            ForecastChangeVisSplitEntryInfo entry=null;
            
            for(int i =bottom ;i>=top ;i--)
            {
            	idx=i;
            	
            	idx1=idx;
            	idx2=idx;
            	
            	//�������һ��
            	isTrue=false;
            	for(int j=i+1; j<kdtSplitEntry.getRowCount(); j++){
            		entry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			idx2=j-1;
            			isTrue=true;
            			break;
            		}
            	}
            	if(!isTrue){
            		idx2=kdtSplitEntry.getRowCount()-1;
            	}
            	if(idx2<idx){
            		idx2=idx;
            	}
            	
            	//�����һ����ǰɾ����ֱ��Level=0
            	for(int j=idx2; j>=0; j--){
            		idx1=j;
            		
            		entry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			removeEntry(j);
            			break;
            		}else{
            			removeEntry(j);
            		}
            	}
            	
            	//i=idx1-1;
            	i=idx1;
            }            
        }

        
        if(kdtSplitEntry.getRowCount()>0){
        	calcAmount(0);
        }else{
        	txtSplitedAmount.setValue(FDCHelper.ZERO);    
        }        	
        

		//������		jelon 12/28/2006
		int idx=0;
		ForecastChangeVisSplitEntryInfo entry=null;
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			entry=(ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
    }
    
    protected void calcAmount(int rowIndex){
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		BigDecimal amount = FDCHelper.ZERO;
		
		ForecastChangeVisSplitEntryInfo entry = null;
		
		//�������ܽ��
		for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			/*if (kdtSplitEntry.getRow(i).getCell(COLAMOUNT).getValue()!=null){
				amount = amount.add(new BigDecimal(kdtSplitEntry.getRow(i).getCell(COLAMOUNT).getValue().toString()));
			}*/
			entry = (ForecastChangeVisSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
			
			if (entry.getLevel() == 0) {
				amount = entry.getAmount();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}
		amountTotal = amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (txtSplitedAmount.getBigDecimalValue() != null
				&& amountTotal.compareTo(FDCHelper.toBigDecimal(txtSplitedAmount.getBigDecimalValue())
						.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) {
			try {
				txtSplitedAmount_dataChanged(null);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			txtSplitedAmount.setValue(amountTotal);
		}    	
    } 
    
	protected void txtSplitedAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(e==null) return;
		super.txtSplitedAmount_dataChanged(e);

		BigDecimal amount = FDCHelper.toBigDecimal(txtamount.getValue());

		BigDecimal amtSplited = FDCHelper.toBigDecimal(e.getNewValue());

		txtUnSplitAmount.setValue(FDCHelper.subtract(amount, amtSplited));
	}
    
    public void actionSplitProd_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSplitProd_actionPerformed(e);
    	splitCost(CostSplitTypeEnum.PRODSPLIT);
    }
    
	public void actionSplitProj_actionPerformed(ActionEvent arg0) throws Exception {
		splitCost(CostSplitTypeEnum.PROJSPLIT);
	}
	
	private void splitCost(CostSplitTypeEnum costSplitType) throws Exception {

		//----------------------------------------------------------------------------------------
		//ѡ����

        if ((kdtSplitEntry.getSelectManager().size() == 0)
                || isTableColumnSelected(kdtSplitEntry))
        {
            FDCMsgBox.showInfo(this, "û��ѡ�з�¼���޷����ò�ַ�����");
            return;
        }
		
		
		int topIdx=-1;		
		int[] selectRows = KDTableUtil.getSelectedRows(kdtSplitEntry);        
        if(selectRows.length >0){
        	topIdx = selectRows[0];
        }
        if(!(topIdx>=0)){
        	return;
        }        	        
        

		//----------------------------------------------------------------------------------------
        //��ֶ���
        IRow topRow=kdtSplitEntry.getRow(topIdx);         
		//FDCSplitBillEntryInfo selectEntry=editData.getEntrys().get(selectIdx);
        ForecastChangeVisSplitEntryInfo topEntry=(ForecastChangeVisSplitEntryInfo)topRow.getUserObject();
        
        

		int topLevel=topEntry.getLevel();			
		BOSUuid topId=topEntry.getId();		
		CostAccountInfo topAcct=topEntry.getCostAccount();			
		if(topAcct==null){
			return;
		}
		String topAcctNo=topEntry.getCostAccount().getLongNumber();
		        
        //�������
		CostSplitTypeEnum splitType=topEntry.getSplitType();
		
		
		boolean isTrue=true;	
		
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){			//��Ʒ���	
			if(!isProdSplitEnabled(topEntry)){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷����ò�Ʒ��̯������");			
				FDCMsgBox.showWarning(this,"��ѡ��¼�����ϲ�Ʒ�������,��ѡ����ϸ��¼���в���");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)){	//�Զ����	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT)){					
					isTrue=false;					
				}
			}
			
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue=false;
			}
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷������Զ���ַ�����");		
				FDCMsgBox.showWarning(this, "��ѡ��¼�������Զ��������,��ѡ��һ������ϸ��¼���в���");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	//ĩ�����	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				//if(topEntry.getSplitType().equals(CostSplitType.PROJSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
					//isTrue=false;
										
					//����ǰ���Զ����ת����ĩ�����	jelon 12/6/06
					if(topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) && topEntry.getLevel()==0){						
						if (!FDCMsgBox.isYes(FDCMsgBox
				                .showConfirm2(this,FDCSplitClientHelper.getRes("sure")))){
							return;
						}							
					}else{
						isTrue=false;
					}
				}
			}
			
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(topAcct.isIsLeaf() && topAcct.getCurProject().isIsLeaf()){
				isTrue=false;
			}
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue = false;
			}
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷�����ĩ����ַ�����");		
				FDCMsgBox.showWarning(this, "��ѡ��¼������ĩ���������,��ѡ��һ������ϸ��¼���в���");
				return;
			}
		}
				
		//topEntry.setSplitType(costSplitType);
        
        int level=0;

		
		//----------------------------------------------------------------------------------------
		//׼������
        ForecastChangeVisSplitEntryCollection entrys=new ForecastChangeVisSplitEntryCollection();
		entrys.add(topEntry);
				
		ForecastChangeVisSplitEntryInfo entry=null;
		for(int i=topIdx+1; i<kdtSplitEntry.getRowCount(); i++){
			entry=(ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();		
			
			if(entry.getLevel()>topLevel){
				entrys.add(entry);
			}else{
				break;
			}
		}
				

		//----------------------------------------------------------------------------------------
		//�������UI
		
		FDCSplitBillEntryCollection arfterOldEntrys = new FDCSplitBillEntryCollection();
		for (int i = 0; i < entrys.size(); i++) {
			FDCSplitBillEntryInfo oldNewEntryInfo = new FDCSplitBillEntryInfo();
			oldNewEntryInfo.putAll(entrys.get(i));
			arfterOldEntrys.add(oldNewEntryInfo);
		}
		
		UIContext uiContext = new UIContext(this); 
		//uiContext.put("costSplit", editData.getEntrys());		
		uiContext.put("costSplit", arfterOldEntrys);			
		uiContext.put("splitType", costSplitType);		
		uiContext.put("entryClass", getSplitBillEntryClassName());		
		uiContext.put("parentMap", parentMap);
		String apptUiName;
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			apptUiName=CostSplitApptProdUI.class.getName();
		}else{
			apptUiName=CostSplitApptProjUI.class.getName();
		}
		
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				apptUiName,	uiContext, null ,STATUS_ADDNEW );       
		uiWin.show();	
			
		if (!((CostSplitApptUI) uiWin.getUIObject()).isOk()) {
			return;
		}

		//����ֵ
		entrys=new ForecastChangeVisSplitEntryCollection();
		FDCSplitBillEntryCollection oldEntrys =(FDCSplitBillEntryCollection) ((CostSplitApptUI) uiWin.getUIObject()).getData() ;
		for (int i = 0; i < oldEntrys.size(); i++) {
			ForecastChangeVisSplitEntryInfo newInfo = new ForecastChangeVisSplitEntryInfo();
			newInfo.putAll(oldEntrys.get(i));
			entrys.add(newInfo);
		}
//		entrys =oldEntrys.c;

		//		for (int i = 0; i < entrys.size(); i++) {
		//			if (entrys.get(i).getLevel() > 1) {
		//				entrys.get(i).setLevel(1);
		//			}
		//		}

		//----------------------------------------------------------------------------------------
		//ɾ��ԭ���Ĳ��
		int index=0;
		for(int i=topIdx+1; i<kdtSplitEntry.getRowCount(); i++){
			entry=(ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()>topLevel){
				index=i;
			}else{
				break;
			}			
		}
		for(int i=index; i>topIdx ; i--){
			entry=(ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()==topLevel){
				break;
			}
			else
			{
				removeEntry(i);
			}
		}
				
				
		
		//----------------------------------------------------------------------------------------
		
		//�ɱ���Ŀ
		CostAccountInfo acct=null;
		acct=entrys.get(0).getCostAccount();
					
		//�������
		splitType=costSplitType;	//CostSplitType.BOTUPSPLIT;
				
		//��̯����
		ApportionTypeInfo apportionType;
		apportionType = entrys.get(0).getApportionType();  
		
		//���ӿ�Ŀ
		boolean isAddlAcct=entrys.get(0).isIsAddlAccount();
		
		topEntry.setSplitType(splitType);
		topEntry.setApportionType(apportionType);	
		topEntry.setIsLeaf(false);			
		topEntry.setProduct(null);
		
		topRow.getCell("standard").setValue(splitType.toString());
		topRow.getCell("product").setValue("");
		topRow.getCell("product").getStyleAttributes().setLocked(true);
		
		//���ԡ�begin
		if(apportionType!=null){
			topRow.getCell("apportionType.name").setValue(apportionType.getName());
		}
		topRow.getCell("splitType").setValue(splitType);
		//���ԡ�end
		
		IRow row;				
		
		//��Ʒ��֣�ɾ��ȫ�������
		if(entrys.size()==1){	
			topEntry.setIsLeaf(true);
			
			if(topEntry.getLevel()==0){
				topEntry.setSplitType(CostSplitTypeEnum.MANUALSPLIT);
				topEntry.setApportionType(null);
				
				topRow.getCell("splitType").setValue(CostSplitTypeEnum.MANUALSPLIT);
				//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
				

				//topRow.getCell("standard").setValue("");
				setDisplay(topIdx);
				
			}else{
				for(int i=topIdx-1; i>=0; i--){
					row=kdtSplitEntry.getRow(i);
					entry=(ForecastChangeVisSplitEntryInfo) row.getUserObject();
					
					if(entry.getLevel()==topEntry.getLevel()-1){
						topEntry.setSplitType(entry.getSplitType());
						topEntry.setApportionType(null);

						topRow.getCell("splitType").setValue(entry.getSplitType());
						//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
						
						setDisplay(i);
						
						break;
					}
				}
				
			}
			return;
		}
		//�����µĲ����
		int idxCurr=topIdx;
		
		for(int i=1; i<entrys.size(); i++){
			entry=entrys.get(i);				

			//������	jelon 12/27/2006
			entry.setIndex(topEntry.getIndex());
						
			//entry.setSplitType(splitType);
			if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
				//��Ŀ����а����Ĳ�Ʒ���
			}else{
				entry.setSplitType(splitType);
			}
			
			
			if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
				//��Ŀ����а����Ĳ�Ʒ���
				entry.setIsAddlAccount(isAddlAcct);
			}
									
			idxCurr++;
			row=insertEntry(idxCurr,entry);			
			
			row.getCell("costAccount.curProject.name").setValue(entry.getCostAccount().getCurProject().getName());
			row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());	
		}
		
		//----------------------------------------------------------------------------------------
		
		//���������	
		//calcApportionData(topIdx,costSplitType);	//ʹ���½ӿڡ�jelon 12/26/2006
		totApptValue(topIdx);

		//��̯�ɱ�
		//calcApportionAmount(topIdx,costSplitType);	//ʹ���½ӿڡ�jelon 12/26/2006
		apptAmount(topIdx);
			

		//������ʾ
		index=topIdx;
		
		//��Ʒ��֣��Ӳ�����ĸ��ڵ㿪ʼ����
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			row=kdtSplitEntry.getRow(topIdx);
			entry=(ForecastChangeVisSplitEntryInfo)row.getUserObject();
			if(entry.getLevel()!=0){
				for(int i=topIdx-1; i>=0; i--){
					row=kdtSplitEntry.getRow(i);
					entry=(ForecastChangeVisSplitEntryInfo)row.getUserObject();
					if(entry.getLevel()==0){
						index=i;
						break;
					}
				}
			}
		}
		setDisplay(index);		
	}
	
    protected IRow insertEntry(int rowIndex, IObjectValue detailData)
    {
        IRow row = null;
        
        row = kdtSplitEntry.addRow(rowIndex);

        loadLineFields(kdtSplitEntry, row, detailData);
        
        return row;
    }
	
    private void totApptValue(int rowIndex){
    	ForecastChangeVisSplitEntryInfo entry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();

    	//�޸ĵ��ýӿ�	jelon 12/26/2006
		/*CostSplitType splitType=entry.getSplitType();
		calcApportionData(rowIndex,splitType);*/
		

		//fdcCostSplit.totApptValue((IObjectCollection)editData.get("entrys"),entry);
		fdcCostSplit.totApptValue(getEntrys(),entry);
						
		int level=entry.getLevel();
		IRow row=null;
		BigDecimal amount=null;
		
		for(int i=rowIndex; i<kdtSplitEntry.getRowCount(); i++){				
			row=kdtSplitEntry.getRow(i);
			entry=(ForecastChangeVisSplitEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level
					|| (entry.getLevel()==level && i==rowIndex)){
				amount=entry.getApportionValueTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("apportionValueTotal").setValue(amount);   	 

				amount=entry.getDirectAmountTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("directAmountTotal").setValue(amount); 

				amount=entry.getOtherRatioTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("otherRatioTotal").setValue(amount);
				
			}else{
				break;
			}
		}
    }
	
    protected void removeEntry(int idxRow)
    {    	
        IObjectValue detailData = (IObjectValue) kdtSplitEntry.getRow(idxRow).getUserObject();
        kdtSplitEntry.removeRow(idxRow);
        
        IObjectCollection collection = (IObjectCollection) kdtSplitEntry.getUserObject();
        if (collection == null)
        {
        	return;
        }
        else
        {
            if( detailData != null ) {
                collection.removeObject(detailData);
            }
        }
    }
    
    protected ForecastChangeVisSplitEntryCollection getEntrys(){
		entrysMap.clear();
		entrys = new ForecastChangeVisSplitEntryCollection();
		for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			ForecastChangeVisSplitEntryInfo info = (ForecastChangeVisSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
			// entrysMap.put(String.valueOf(info.getSeq()), info);
			String key = getEntrysMapKey(info);
			entrysMap.put(key, info);
			entrys.add(info);
		}
    	
    	return entrys;
	}
    
	protected String getEntrysMapKey(ForecastChangeVisSplitEntryInfo entryInfo) {
		String key = entryInfo.getSeq() + "";

		return key;
	}
	
    protected void apptAmount(int rowIndex){
    	ForecastChangeVisSplitEntryInfo topEntry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();
		fdcCostSplit.apptAmount(getEntrys(),topEntry);
		
		int level=topEntry.getLevel();
		IRow row=null;
		boolean isMeasureContract=isMeasureContract();
		
		Object value = kdtSplitEntry.getCell(rowIndex, "amount").getValue();
		//�ѷ�̯�ܽ��
		BigDecimal totalAmt = FDCHelper.ZERO;
		for(int i=rowIndex+1; i<kdtSplitEntry.getRowCount(); i++){				
			row=kdtSplitEntry.getRow(i);
			ForecastChangeVisSplitEntryInfo entry=(ForecastChangeVisSplitEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level){
				BigDecimal amount=entry.getAmount();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("amount").setValue(amount);
				if (entry.getLevel() == level + 1) {
					totalAmt = FDCHelper.add(totalAmt, amount);
				}
				if (i == kdtSplitEntry.getRowCount() - 1 && FDCHelper.compareTo(value, totalAmt) != 0) {
					row.getCell("amount").setValue(
							FDCHelper.add(row.getCell("amount").getValue(), FDCHelper.subtract(value, totalAmt)));
				}
				
				if(isMeasureContract&&isProdSplitLeaf(entry)){
					entry.setPrice(topEntry.getPrice());
					row.getCell("price").setValue(topEntry.getPrice());	
					row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				}
			}else{
				break;
			}
		}
    }
	
	protected String getSplitBillEntryClassName(){
		return ConChangeSplitEntryInfo.class.getName();
	}
    
	protected boolean isMeasureContract(){
		return false;
	}
    
	protected void setMeasureCtrl(final IRow row) {
		Color cantEditColor=new Color(0xF5F5E6);
		row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		BigDecimal amount=FDCHelper.toBigDecimal(row.getCell("amount").getValue());
		BigDecimal price=FDCHelper.toBigDecimal(row.getCell("price").getValue());
		BigDecimal workLoad=FDCHelper.toBigDecimal(row.getCell("workLoad").getValue());
		if(price.signum()!=0||workLoad.signum()!=0){
			row.getCell("amount").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("amount").getStyleAttributes().setLocked(true);
		}else if (amount.signum()!=0){
			row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("price").getStyleAttributes().setLocked(true);
			row.getCell("workLoad").getStyleAttributes().setLocked(true);
		}else{
			row.getCell("amount").getStyleAttributes().setLocked(false);
			row.getCell("price").getStyleAttributes().setLocked(false);
			row.getCell("workLoad").getStyleAttributes().setLocked(false);
			row.getCell("amount").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("price").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("workLoad").getStyleAttributes().setBackground(Color.WHITE);
		}
		if(row.getUserObject() instanceof ForecastChangeVisSplitEntryInfo){
			ForecastChangeVisSplitEntryInfo entry=(ForecastChangeVisSplitEntryInfo)row.getUserObject();
			if(isProdSplitLeaf(entry)){
				row.getCell("price").setValue(entry.getPrice());	
				row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("price").getStyleAttributes().setLocked(true);
				row.getCell("workLoad").getStyleAttributes().setLocked(true);
			}
		}
	}
	
    protected boolean isProdSplitLeaf(ForecastChangeVisSplitEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
	
   private void setStandard(int index){
	   ForecastChangeVisSplitEntryInfo curEntry = (ForecastChangeVisSplitEntryInfo)kdtSplitEntry.getRow(index).getUserObject();    
    	
    	int level=curEntry.getLevel();	
    	
    	//1. ��ָ��ݽڵ㣬ʹ�ò��������Ϊ������׼
		if(level==0){
			//Jelon Dec 13, 2006			
			/*if(curEntry.getSplitType()!=null && curEntry.getSplitType()!=CostSplitType.MANUALSPLIT){
				kdtSplitEntry.getRow(index).getCell("standard").setValue(curEntry.getSplitType());			
			}*/
			if(curEntry.getSplitType()==null || curEntry.getSplitType()==CostSplitTypeEnum.MANUALSPLIT){
				kdtSplitEntry.getRow(index).getCell("standard").setValue("");	
			}else{
				kdtSplitEntry.getRow(index).getCell("standard").setValue(curEntry.getSplitType().toString());			
			}
		}
    	
		//2. ������ֽ�㣬ʹ�ø����ķ�̯������Ϊ������׼
    	String apptType=null;
    	if(curEntry.getApportionType()!=null){
    		apptType=curEntry.getApportionType().getName();
    	}
    	ForecastChangeVisSplitEntryInfo entry=null;
		IRow row=null;
		
		for(int i=index+1; i<kdtSplitEntry.getRowCount(); i++){
			row=kdtSplitEntry.getRow(i);
			entry = (ForecastChangeVisSplitEntryInfo)row.getUserObject();
			
						
			if(entry.getLevel()==level+1){	
				if(entry.isIsAddlAccount()){
					if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						row.getCell("standard").setValue(apptType);
					}else{
						row.getCell("standard").setValue("ֱ�ӷ���");
					}
				}else{
					row.getCell("standard").setValue(apptType);
				}
				
				if(!entry.isIsLeaf()){
					setStandard(i);
				}
			}
			else if(entry.getLevel()<=level){
				break;
			}		
			
		}	   					
	}
    
    private Map initDirectMap=new HashMap();
    private void  initDirectAssign(){
    	if(initDirectMap==null||initDirectMap.size()==0){
    		return;
    	}
    	
    	Map projProdMap=new HashMap();
		//��Ʒ����		
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",new HashSet(initDirectMap.values()),CompareType.INCLUDE));
    	//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("IsAccObj", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("productType.*"); 		//ʹ�á�*���������б��е����ݺͷ�¼�е�����ƥ��
    	view.getSorter().add(new SorterItemInfo("productType.number"));
		try {   	    	    
			CurProjProductEntriesCollection c=CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);
			for(int i=0;i<c.size();i++){
				String prjId=c.get(i).getCurProject().getId().toString();
				CurProjProductEntriesCollection temp=(CurProjProductEntriesCollection)projProdMap.get(prjId);
				if(temp==null){
					temp=new CurProjProductEntriesCollection();
				}
				temp.add(c.get(i));
				projProdMap.put(prjId, temp);
			}
		}catch(BOSException e){
			handUIExceptionAndAbort(e);
		}
    	for(Iterator iter=initDirectMap.keySet().iterator();iter.hasNext();	){
    		Integer idx=(Integer)iter.next();
    		String prjId=(String)initDirectMap.get(idx);
    		if(idx==null){
    			continue;
    		}
    		IRow row=getDetailTable().getRow(idx.intValue());
    		CurProjProductEntriesCollection coll=(CurProjProductEntriesCollection)projProdMap.get(prjId);
			if(coll==null){
				coll=new CurProjProductEntriesCollection();
			}
			ProductTypeCollection collProd=new ProductTypeCollection();
			//����
			ProductTypeInfo prod=new ProductTypeInfo();
			prod.setName(null);
			//prod.setName("��");
	        collProd.insertObject(-1,prod);		
	        
	        //��ǰ��Ŀȫ����Ʒ
			for (Iterator iter2 = coll.iterator(); iter2.hasNext();)
			{
				prod = ((CurProjProductEntriesInfo)iter2.next()).getProductType();	        
				if(prod!=null){
					collProd.add(prod);
				}
	        }

			KDComboBox cbo = new KDComboBox();    	    	    	
	        cbo.addItems(collProd.toArray());			
	        row.getCell("product").setEditor(new KDTDefaultCellEditor(cbo));  
    	}
    }
    
    protected KDTable getDetailTable() {
        return kdtSplitEntry;
	}
    
	
	protected void setButtonStatus() {
		boolean flse = (getOprtState().equals("ADDNEW")||getOprtState().equals("EDIT"))?true:false;
		this.actionAcctSelect.setEnabled(flse);
		this.actionSplitProj.setEnabled(flse);
		this.actionSplitProd.setEnabled(flse);
		this.actionSplitBotUp.setEnabled(flse);
		this.actionRemoveLine.setEnabled(flse);
		if(this.kdtEntrys_detailPanel!=null&&this.kdtEntrys_detailPanel.getAddNewLineButton()!=null)
			this.kdtEntrys_detailPanel.getAddNewLineButton().setEnabled(flse);
		if(this.kdtEntrys_detailPanel!=null&&this.kdtEntrys_detailPanel.getInsertLineButton()!=null)
			this.kdtEntrys_detailPanel.getInsertLineButton().setEnabled(flse);
		if(this.kdtEntrys_detailPanel!=null&&this.kdtEntrys_detailPanel.getRemoveLinesButton()!=null)
			this.kdtEntrys_detailPanel.getRemoveLinesButton().setEnabled(flse);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setButtonStatus();
	}

    public void initDirectAssign(IRow row){
    	ForecastChangeVisSplitEntryInfo entry;
		entry = (ForecastChangeVisSplitEntryInfo)row.getUserObject();
		
		
		boolean isTrue=false;
		isTrue=isProdSplitEnabled(entry);
    	
		if(!isTrue || !entry.isIsLeaf()){
			row.getCell("product").getStyleAttributes().setLocked(true);
			return;
		}else{
			row.getCell("product").getStyleAttributes().setBackground(new Color(0xFFFFFF));
			initDirectMap.put(new Integer(row.getRowIndex()), entry.getCostAccount().getCurProject().getId().toString());
		}
    }
    
	private boolean isProdSplitEnabled(ForecastChangeVisSplitEntryInfo entry){		
		boolean isTrue=false;
		if (entry != null && entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && entry.getCostAccount().getCurProject().isIsLeaf()) {
			
			if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				isTrue=true;
			}else if(entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) || entry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
				isTrue=true;
	    	}else if(entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    		if(!entry.isIsLeaf()){
	    			isTrue=true;
	    		}
	    	}			
		}
		
		return isTrue;
	}
	
    protected IRow addEntry(IObjectValue detailData)
    {
        IRow row = kdtSplitEntry.addRow();
        ((ForecastChangeVisSplitEntryInfo)detailData).setSeq(row.getRowIndex()+1);
        loadLineFields(kdtSplitEntry, row, detailData);
        afterAddLine(kdtSplitEntry, detailData);
        
        return row;
    }

    protected void kdtSplitEntry_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtSplitEntry_editStopped(e);
		final IRow row = kdtSplitEntry.getRow(e.getRowIndex());
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("amount")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount=FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				ForecastChangeVisSplitEntryInfo entry;
				entry = (ForecastChangeVisSplitEntryInfo)row.getUserObject();
				String key = getEntrysMapKey(entry);
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...������˵��
				if (entrysMap.get(key) != null) {//modified by ken_liu...������˵��
					// modified by zhaoqin on 2013/11/09 start, ¼����ʱ��NullPointException
					// entry = (FDCSplitBillEntryInfo) entrysMap.get(String.valueOf(entry.getSeq()));
					entry = (ForecastChangeVisSplitEntryInfo) entrysMap.get(key);
					// modified by zhaoqin on 2013/11/09 end
				}
				//amount=new BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal=row.getCell("amount").getValue();
				if(cellVal!=null){
					amount=new BigDecimal(cellVal.toString());
				}
				entry.setAmount(amount);
				//��ֱ���
				if(entry.getLevel()==0){
					if(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						splitScale=FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), txtamount.getBigDecimalValue(),10,BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
					row.getCell("splitScale").setValue(splitScale);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				//��̯
				/*CostSplitType splitType=entry.getSplitType();
				calcApportionAmount(e.getRowIndex(),splitType);*/				
				apptAmount(e.getRowIndex());	
				
				
				//����
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}else if(entry.isIsLeaf() && isAddlAcctLeaf(entry)){
				}
			}
		}
		
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("splitScale")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				ForecastChangeVisSplitEntryInfo entry;
				entry = (ForecastChangeVisSplitEntryInfo)row.getUserObject();
				
				// modified by zhaoqin on 2013/11/09, Ӧ����ͳһ�ķ���
				// String key = entry.getCostAccount().getId().toString() + String.valueOf(entry.getSeq());
				String key = getEntrysMapKey(entry);
								
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...������˵��
				if (entrysMap.get(key) != null) {//modified by ken_liu...������˵��
					entry = (ForecastChangeVisSplitEntryInfo) entrysMap.get(key);
				}
				Object cellVal=row.getCell("splitScale").getValue();
				if(cellVal!=null){
					splitScale=FDCHelper.toBigDecimal(cellVal);
				}
				if(entry.getLevel()==0){
					entry.setSplitScale(splitScale);
					amount = FDCHelper.divide(FDCHelper.multiply(txtamount.getBigDecimalValue(), splitScale),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
					row.getCell("amount").setValue(amount);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				
				apptAmount(e.getRowIndex());				
				//����
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}
			}
		}
		
		//���ӿ�Ŀ����
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("amount")){
			BigDecimal value=UIRuleUtil.getBigDecimal(e.getValue());
			BigDecimal oldValue=e.getOldValue()==null?FDCHelper.ZERO:UIRuleUtil.getBigDecimal(e.getOldValue());
			BigDecimal changeAmt=value.subtract(oldValue);
			if (changeAmt.compareTo(FDCHelper.ZERO)!=0){
				ForecastChangeVisSplitEntryInfo entry=(ForecastChangeVisSplitEntryInfo)row.getUserObject();
				if(entry.isIsLeaf()&&entry.isIsAddlAccount()){
					totAddlAcct(entry.getCostAccount().getCurProject(), entry.getCostAccount(), changeAmt, e.getRowIndex());
					entry.setApportionValue(value);
					row.getCell("apportionValue").setValue(value);
				}
			}
		}
		//���Ӳ�Ʒ
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("product")){
			ForecastChangeVisSplitEntryInfo entry = (ForecastChangeVisSplitEntryInfo)row.getUserObject();
			Object product = row.getCell("product").getValue();
			//if(product!=null&&product instanceof ProductTypeInfo && ((ProductTypeInfo)product).getId()!=null ){
			if(product!=null&& product.toString()!=null ){
				entry.setProduct((ProductTypeInfo)product);
			}else{
				entry.setProduct(null);
			}
		}
		//������������ĩ����ֵ���ɱ���Ŀʱ����������¼�룬���½�����
		if(editData.getBoolean("isMeasureSplit")){
			handleMeasureCalc(e, row);
		}
	}
    
    protected boolean isAddlAcctLeaf(ForecastChangeVisSplitEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsAddlAccount() 
    			&& entry.getCostAccount()!=null && entry.getCostAccount().isIsLeaf()
    			&& entry.getCostAccount().getCurProject()!=null && entry.getCostAccount().getCurProject().isIsLeaf()
    			&& !isProdSplitLeaf(entry)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    
    protected void totAddlAcct(CurProjectInfo prj,CostAccountInfo acct,BigDecimal amount,int end) {
		IRow row=null;
    	CurProjectInfo curPrj=null;
		CostAccountInfo curAcct=null;
		BigDecimal sum=null; 
    	for (int i = end-1; i >=0 ; i--) {
			row = getDetailTable().getRow(i);
			ForecastChangeVisSplitEntryInfo entry=(ForecastChangeVisSplitEntryInfo)(row.getUserObject());
			if(entry.getLevel()==0){
				break;
			}
			if(!entry.isIsAddlAccount()){
				continue;
			}
			
			curAcct=entry.getCostAccount();
			curPrj=entry.getCostAccount().getCurProject();
			//�����ϼ�������Ŀ����ͬ��Ŀ,ע:�ó��������ж�
			if(prj.getParent()!=null&&prj.getParent().getId().equals(curPrj.getId())
					&&acct.getLongNumber().equals(curAcct.getLongNumber())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
			}
//			������ͬ������Ŀ���ϼ���Ŀ,���ݹ鴦��
			if(prj.getId().equals(curPrj.getId())
					&&acct.getParent()!=null
					&&acct.getParent().getId().equals(curAcct.getId())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
				
				totAddlAcct(curPrj,curAcct,amount,i);
			}
		}

	}
    
	protected void handleMeasureCalc(KDTEditEvent e, final IRow row) {
		//���ۻ���
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("workLoad")
			||e.getColIndex()==kdtSplitEntry.getColumnIndex("price")){
			FDCSplitBillEntryInfo entry= (FDCSplitBillEntryInfo)row.getUserObject();
			BigDecimal oldAmt=entry.getAmount();
			BigDecimal amount = FDCHelper.multiply(row.getCell("workLoad").getValue(), row.getCell("price").getValue());
			row.getCell("amount").setValue(amount);
			entry.setWorkLoad((BigDecimal)row.getCell("workLoad").getValue());
			entry.setPrice((BigDecimal)row.getCell("price").getValue());
			entry.setAmount(amount);
			try{
				kdtSplitEntry_editStopped(new KDTEditEvent(e.getSource(), oldAmt, amount, 
					row.getRowIndex(), row.getCell("amount").getColumnIndex(),false,1));
			}catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
				handUIExceptionAndAbort(e1);
			}
			calcAmount(0);
		}

		setMeasureCtrl(row);
	}
	
	private void loadTable(KDTable table,String sql){
		try {
			table.checkParsed();
			table.removeColumns();
			table.removeRows();
			IFMIsqlFacade isql = FMIsqlFacadeFactory.getRemoteInstance();
			IRowSet executeQuery = isql.executeQuery(sql, null);
			String nsql = sql;
			if(sql.startsWith("/*dialect*/"))
				nsql = sql.substring("/*dialect*/".length());
			SQLStmtInfo sqlStmtInfo;
			try
			{
				Lexer lexer = new Lexer(nsql);
				TokenList _tokList = new TokenList(lexer);
				com.kingdee.bos.sql.dom.stmt.SqlStmt sqlStmt = (new SqlStmtParser(_tokList)).stmt();
				sqlStmtInfo = FMIsqlUIHandler.getSQLStmtInfo(sqlStmt);
			}
			catch(ParserException pe)
			{
				sqlStmtInfo = new SQLStmtInfo();
				sqlStmtInfo.canAudoUpdate = false;
				sqlStmtInfo.isSelect = false;
			}
			FMIsqlUI.fillData(table,executeQuery ,sqlStmtInfo);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		String key = this.kdtEntrys.getColumnKey(e.getColIndex());
		if(key==null||!key.equals("amount"))
			return;
		this.txtamount.setValue(UIRuleUtil.sum(kdtEntrys, "amount"));
		
		BigDecimal totalChangeAmount = BigDecimal.ZERO;
        for (int i = 0; i < kDTable1.getRowCount(); i++)
        	totalChangeAmount = FDCHelper.add(totalChangeAmount, kDTable1.getCell(i, 8).getValue());
        this.txtChangeAmount.setValue(totalChangeAmount);
        this.txtBanane.setValue(FDCHelper.subtract(this.txtamount.getBigDecimalValue(), this.txtChangeAmount.getBigDecimalValue()));
        this.txtUnSplitAmount.setValue(FDCHelper.subtract(this.txtamount.getBigDecimalValue(), this.txtSplitedAmount.getBigDecimalValue()));
        
        //��Ҫ������Ҫ������
        for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			IRow row = kdtSplitEntry.getRow(i);
			if(!row.getCell("splitScale").getStyleAttributes().getBackground().equals(Color.white))
				continue;
			runCalAmount(row, i);
			runCalAmount(row, i);
		}
	}
	
	private void runCalAmount(IRow row,int i) throws Exception{
		KDTEditEvent event = new KDTEditEvent(kdtSplitEntry, null, null, i,kdtSplitEntry.getColumnIndex("splitScale"), true, 1);
    	event.setValue(row.getCell("splitScale").getValue());
    	event.setOldValue("1.2222");
    	kdtSplitEntry_editStopped(event);
	}
	
    
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	if(getUIContext().get("ForInfo")!=null){
    		ForecastChangeVisInfo info = (ForecastChangeVisInfo) getUIContext().get("ForInfo");
    		info.setVersion(getUIContext().get("verson").toString());
    		info.setId(null);
    		info.setIsLast(false);
    		for (int i = 0; i < info.getEntrys().size(); i++)
    			info.getEntrys().get(i).setId(null);
    		for (int i = 0; i < info.getSplitEntry().size(); i++)
    			info.getSplitEntry().get(i).setId(null);
    		return info;
    	}else{
    		com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo objectValue = new com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo();
    		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    		if(getUIContext().get("contractInfo")==null){
    			MsgBox.showWarning("��ͬ����Ϊ�գ�");
    			SysUtil.abort();
    		}
    		ContractBillInfo conInfo = (ContractBillInfo)getUIContext().get("contractInfo");
    		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    		objectValue.setContractNumber(conInfo);
    		objectValue.setContractName(conInfo.getName());
    		objectValue.setContractAmount(conInfo.getAmount());
    		objectValue.setVersion("1");
    		objectValue.setBizDate(new Date());
    		objectValue.setAmount(BigDecimal.ZERO);
    		return objectValue;
    	}
    }

}