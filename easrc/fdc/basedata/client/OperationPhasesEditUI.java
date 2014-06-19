package com.kingdee.eas.fdc.basedata.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.EconomicIndexCollection;
import com.kingdee.eas.fdc.basedata.EconomicIndexInfo;
import com.kingdee.eas.fdc.basedata.EconomicIndicatorsCollection;
import com.kingdee.eas.fdc.basedata.EconomicIndicatorsInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.InvestIndexCollection;
import com.kingdee.eas.fdc.basedata.InvestIndexFactory;
import com.kingdee.eas.fdc.basedata.InvestIndexInfo;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesFactory;
import com.kingdee.eas.fdc.basedata.OperationPhasesImageInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesInvestEntryCollection;
import com.kingdee.eas.fdc.basedata.OperationPhasesInvestEntryInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesInvestListEntryCollection;
import com.kingdee.eas.fdc.basedata.OperationPhasesInvestListEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.hr.emp.client.PhotoPanel;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class OperationPhasesEditUI extends AbstractOperationPhasesEditUI {
	private static final Logger logger = CoreUIObject.getLogger(OperationPhasesEditUI.class);
	
	public boolean isSaveAction = false;
	private final static String CANTAUDIT = "cantAudit";
	private static final String CANTEDIT = "cantEdit";
	private static final String CANTREMOVE = "cantRemove";
	private final static String CANTUNAUDIT = "cantUnAudit";
	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";
	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	private final static int PRECSION = 2;
	private Map economicIndexTable=new HashMap();
	public OperationPhasesEditUI() throws Exception {
		super();
	}
	
	public void storeFields(){
    	this.editData.getEntry().clear();
    	for(int i=0;i<this.panelInvest.getComponentCount();i++){
			if(this.panelInvest.getComponent(i) instanceof KDContainer){
				KDContainer con=(KDContainer) this.panelInvest.getComponent(i);
				OperationPhasesInvestListEntryInfo entry=(OperationPhasesInvestListEntryInfo)con.getUserObject();
				
				entry.getInvestEntry().clear();
				for(int j=0;j<con.getContentPane().getComponentCount();j++){
					if(con.getContentPane().getComponent(j) instanceof KDTable){
						KDTable table=(KDTable) con.getContentPane().getComponent(j);
						for(int k=0;k<table.getRowCount();k++){
							IRow row=table.getRow(k);
							OperationPhasesInvestEntryInfo investEntry=(OperationPhasesInvestEntryInfo)row.getUserObject();
							investEntry.setIndexName((InvestIndexInfo)row.getCell("number").getValue());
							investEntry.setIndexValue((BigDecimal)row.getCell("amount").getValue());
							investEntry.setIndexDescription((String)row.getCell("description").getValue());
							
							entry.getInvestEntry().add(investEntry);
						}
					}
				}
				this.editData.getEntry().add(entry);
			}
		}
    	this.editData.getEconomicEntry().clear();
    	for(int i=0;i<this.paneleEonomic.getComponentCount();i++){
			if(this.paneleEonomic.getComponent(i) instanceof KDContainer){
				KDContainer con=(KDContainer) this.paneleEonomic.getComponent(i);
				EconomicIndexInfo entry=(EconomicIndexInfo)con.getUserObject();
				
				entry.getIndicaEntry().clear();
				EconomicIndexTable table=(EconomicIndexTable) this.economicIndexTable.get(entry.getId().toString());
				if(table!=null){
//			    	EconomicIndicatorsInfo economicIndicatorsInfo = table.getEconomicIndicatorsInfo();
//			    	entry.getIndicaEntry().add(economicIndicatorsInfo);
				}
				this.editData.getEconomicEntry().add(entry);
			}
		}
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	initTable();
		
		super.onLoad();
		FDCClientHelper.setUIMainMenuAsTitle(this);

		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		btnSubmit.setToolTipText(FDCClientUtils.getRes("submit"));

		updateButtonStatus();

		// 设置所有金额控件KDFormattedTextField，BasicNumberTextField的最大最小值，精度，右对齐
		FDCHelper.setComponentPrecision(this.getComponents(), PRECSION);

		//
		setNumberTextEnabled();

		AmusementUI.addHideMenuItem(this, this.menuBiz);
		
		setAuditButtonStatus(this.getOprtState());
		initControl();
	}
    public void initControl(){
		this.actionCopy.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionAttachment.setVisible(true);
		
    	this.txtNumber.setRequired(true);
    	this.txtName.setRequired(true);
    	
    	this.actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		this.actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		this.actionSave.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_save"));
		this.actionSubmit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_submit"));
    }
    public EntityViewInfo getOrgEntityInfo(){
    	EntityViewInfo evInfo = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("level",Integer.valueOf(4)));	
		filter.getFilterItems().add(new FilterItemInfo("number","B%",CompareType.LIKE));
		evInfo.setFilter(filter);
		return evInfo;
    }
    public EntityViewInfo getFIEntityInfo(){
    	EntityViewInfo evInfo = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("partFI.isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("number","B%",CompareType.LIKE));
		evInfo.setFilter(filter);
		return evInfo;
    }
    protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
	}
    public void initTable(){
		KDWorkButton btnAddIndexVersion = new KDWorkButton();
		this.actionAddIndexVersion.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddIndexVersion = (KDWorkButton)this.contInvestEntryList.add(this.actionAddIndexVersion);
		btnAddIndexVersion.setText("新增指标版本");
		btnAddIndexVersion.setSize(new Dimension(140, 19));
		
		KDWorkButton btnRemoveIndexVersion = new KDWorkButton();
		this.actionRemoveIndexVersion.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnRemoveIndexVersion = (KDWorkButton)this.contInvestEntryList.add(this.actionRemoveIndexVersion);
		btnRemoveIndexVersion.setText("删除指标版本");
		btnRemoveIndexVersion.setSize(new Dimension(140, 19));
		
        this.kdtImageEntry.checkParsed();
    	this.kdtImageEntry.setEnabled(false);
    	KDWorkButton btnImageAdd = new KDWorkButton();
		KDWorkButton btnImageRemove = new KDWorkButton();

		this.actionImageAdd.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnImageAdd = (KDWorkButton)this.contImageEntry.add(this.actionImageAdd);
		btnImageAdd.setText("新增");
		btnImageAdd.setSize(new Dimension(140, 19));
		
		this.actionImageRemove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnImageRemove = (KDWorkButton)this.contImageEntry.add(this.actionImageRemove);
		btnImageRemove.setText("删除");
		btnImageRemove.setSize(new Dimension(140, 19));
		
		
		KDWorkButton btnAddEonomic = new KDWorkButton();
		this.actionAddEonomic.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddEonomic = (KDWorkButton)this.contEconomicEntryList.add(this.actionAddEonomic);
		btnAddEonomic.setText("新增经济指标");
		btnAddEonomic.setSize(new Dimension(140, 19));
		
		KDWorkButton btnRemoveEonomic = new KDWorkButton();
		this.actionRemoveEonomic.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnRemoveEonomic = (KDWorkButton)this.contEconomicEntryList.add(this.actionRemoveEonomic);
		btnRemoveEonomic.setText("删除经济指标");
		btnRemoveEonomic.setSize(new Dimension(140, 19));
		
    }
    protected void createEconomicIndex(EconomicIndexInfo entry) throws EASBizException, BOSException{
    	EconomicIndicatorsInfo economicIndicatorsInfo = new EconomicIndicatorsInfo();
		EconomicIndicatorsCollection coll = entry.getIndicaEntry();
		if(coll.size()>0||coll.get(0)!=null){
			economicIndicatorsInfo = coll.get(0);
		}
//		EconomicIndexTable initTable = new EconomicIndexTable(economicIndicatorsInfo);
//		economicIndexTable.put(entry.getId().toString(), initTable);
//		KDTable table  = initTable.getTable();
		
    	KDContainer contEntry = new KDContainer();
    	contEntry.setUserObject(entry);
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
//		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionEconomicAddRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionEconomicAddRow);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionEconomicRemoveRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionEconomicRemoveRow);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		String measureStageName=null;
		if(entry.getMeasureStage()!=null){
			measureStageName=entry.getMeasureStage().getName();
		}
		this.paneleEonomic.add(contEntry,measureStageName);
		
		KDBizPromptBox prmtMeasureStage=new KDBizPromptBox();
		prmtMeasureStage.setValue(entry.getMeasureStage());
		prmtMeasureStage.setQueryInfo("com.kingdee.eas.fdc.basedata.app.MeasureStageQuery");
		prmtMeasureStage.setEditFormat("$number$");		
		prmtMeasureStage.setDisplayFormat("$name$");		
		prmtMeasureStage.setCommitFormat("$number$");
		prmtMeasureStage.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
	            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
	                try {
	                	prmtMeasureStage_dataChanged(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		prmtMeasureStage.setEntityViewInfo(view);
		
		KDLabelContainer contIndexVersion=new KDLabelContainer();
		contIndexVersion.setBoundEditor(prmtMeasureStage);
		contIndexVersion.setBoundLabelText("测算阶段");		
		contIndexVersion.setBoundLabelLength(100);		
		contIndexVersion.setBoundLabelUnderline(true);
	        
		contIndexVersion.setBounds(50, 2, 270, 19);
		contEntry.add(contIndexVersion);
    }
    protected KDTable createTable(OperationPhasesInvestListEntryInfo entry){
    	KDTable table=new KDTable();
		table.checkParsed();
		IRow headRow=table.addHeadRow();
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		IColumn column=table.addColumn();
		column.setKey("number");
		headRow.getCell("number").setValue("指标编码");
		
		column=table.addColumn();
		column.setKey("name");
		headRow.getCell("name").setValue("指标名称");
		
		column=table.addColumn();
		column.setKey("amount");
		headRow.getCell("amount").setValue("指标值");
		
		column=table.addColumn();
		column.setKey("description");
		headRow.getCell("description").setValue("指标说明");
		
    	final KDBizPromptBox kdtInvestEntry_PromptBox = new KDBizPromptBox();
        kdtInvestEntry_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.InvestIndexQuery");
        kdtInvestEntry_PromptBox.setVisible(true);
        kdtInvestEntry_PromptBox.setEditable(true);
        kdtInvestEntry_PromptBox.setEditFormat("$longNumber$");
        kdtInvestEntry_PromptBox.setCommitFormat("$longNumber$");
        kdtInvestEntry_PromptBox.setDisplayFormat("$longNumber$");
        KDTDefaultCellEditor kdtInvestEntry_CellEditor = new KDTDefaultCellEditor(kdtInvestEntry_PromptBox);
        table.getColumn("number").setEditor(kdtInvestEntry_CellEditor);
        table.getColumn("number").setRenderer(new ObjectValueRender(){
        	public String getText(Object obj) {
        		if(obj instanceof InvestIndexInfo){
        			return ((InvestIndexInfo)obj).getNumber();
        		}
        		return null;
        	}
        }
        );
        table.getColumn("name").getStyleAttributes().setLocked(true);
        table.getColumn("name").setWidth(250);
        table.getColumn("amount").setEditor(amountEditor);
    	TableUtils.changeTableNumberFormat(table, new String[]{"amount"});
    	
        table.getColumn("description").setWidth(250);
        
    	table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
    	
    	KDContainer contEntry = new KDContainer();
    	contEntry.setUserObject(entry);
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionEntryAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionEntryAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionEntryInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionEntryInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionEntryRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionEntryRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		String indexVersionName=null;
		if(entry.getIndexVersion()!=null){
			indexVersionName=entry.getIndexVersion().getName();
		}
		this.panelInvest.add(contEntry, indexVersionName);
		
		KDBizPromptBox prmtIndexVersion=new KDBizPromptBox();
		prmtIndexVersion.setValue(entry.getIndexVersion());
		prmtIndexVersion.setQueryInfo("com.kingdee.eas.fdc.basedata.app.IndexVersionQuery");
		prmtIndexVersion.setEditFormat("$number$");		
		prmtIndexVersion.setDisplayFormat("$name$");		
		prmtIndexVersion.setCommitFormat("$number$");
		prmtIndexVersion.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
	            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
	                try {
	                	prmtIndexVersion_dataChanged(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		prmtIndexVersion.setEntityViewInfo(view);
		
		KDLabelContainer contIndexVersion=new KDLabelContainer();
		contIndexVersion.setBoundEditor(prmtIndexVersion);
		contIndexVersion.setBoundLabelText("指标版本");		
		contIndexVersion.setBoundLabelLength(100);		
		contIndexVersion.setBoundLabelUnderline(true);
	        
		contIndexVersion.setBounds(50, 2, 270, 19);
		contEntry.add(contIndexVersion);
		
		return table;
    }
    private void prmtMeasureStage_dataChanged(DataChangeEvent e) {
    	MeasureStageInfo info=(MeasureStageInfo) e.getNewValue();
    	KDContainer selectCom=(KDContainer) this.paneleEonomic.getSelectedComponent();
		if(info!=null){
			for(int i=0;i<this.paneleEonomic.getComponentCount();i++){
				if(this.paneleEonomic.getComponent(i) instanceof KDContainer&&!selectCom.equals(this.paneleEonomic.getComponent(i))){
					KDContainer con=(KDContainer) this.paneleEonomic.getComponent(i);
					MeasureStageInfo comInfo=((EconomicIndexInfo)con.getUserObject()).getMeasureStage();
					if(comInfo!=null&&comInfo.getId().toString().equals(info.getId().toString())){
						FDCMsgBox.showWarning(this,info.getName()+"已经存在，请选择其他测算阶段！");
						((KDBizPromptBox)e.getSource()).setValue(e.getOldValue());
						return;
					}
				}
			}
			((EconomicIndexInfo)selectCom.getUserObject()).setMeasureStage(info);
			this.paneleEonomic.setTitleAt(this.paneleEonomic.getSelectedIndex(), info.getName());
		}else{
			((EconomicIndexInfo)selectCom.getUserObject()).setMeasureStage(null);
			this.paneleEonomic.setTitleAt(this.paneleEonomic.getSelectedIndex(), null);
		}
    }
    private void prmtIndexVersion_dataChanged(DataChangeEvent e) {
    	IndexVersionInfo info=(IndexVersionInfo) e.getNewValue();
    	KDContainer selectCom=(KDContainer) this.panelInvest.getSelectedComponent();
		if(info!=null){
			for(int i=0;i<this.panelInvest.getComponentCount();i++){
				if(this.panelInvest.getComponent(i) instanceof KDContainer&&!selectCom.equals(this.panelInvest.getComponent(i))){
					KDContainer con=(KDContainer) this.panelInvest.getComponent(i);
					IndexVersionInfo comInfo=((OperationPhasesInvestListEntryInfo)con.getUserObject()).getIndexVersion();
					if(comInfo!=null&&comInfo.getId().toString().equals(info.getId().toString())){
						FDCMsgBox.showWarning(this,info.getName()+"已经存在，请选择其他指标版本！");
						((KDBizPromptBox)e.getSource()).setValue(e.getOldValue());
						return;
					}
				}
			}
			((OperationPhasesInvestListEntryInfo)selectCom.getUserObject()).setIndexVersion(info);
			this.panelInvest.setTitleAt(this.panelInvest.getSelectedIndex(), info.getName());
		}else{
			((OperationPhasesInvestListEntryInfo)selectCom.getUserObject()).setIndexVersion(null);
			this.panelInvest.setTitleAt(this.panelInvest.getSelectedIndex(), null);
		}
	}
    private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		int rowIndex = e.getRowIndex();
		String key = table.getColumnKey(e.getColIndex());
		if("number".equals(key)){
			InvestIndexInfo info = (InvestIndexInfo)table.getCell(rowIndex, "number").getValue();
			if(info!=null){
				table.getRow(rowIndex).getCell("name").setValue(info.getName());
				table.getRow(rowIndex).getCell("description").setValue(info.getDescription());
			}else{
				table.getRow(rowIndex).getCell("name").setValue(null);
				table.getRow(rowIndex).getCell("description").setValue(null);
			}
		}
    }
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel=super.getSelectors();
		sel.add("id");
		sel.add("entry.*");
		sel.add("entry.indexVersion.*");
		sel.add("entry.investEntry.*");
		sel.add("entry.investEntry.indexName.*");
		sel.add("isNewProject");
		sel.add("economicEntry.*");
		
		sel.add("projectBase.*");
		sel.add("economicEntry.indicaEntry.*");
		sel.add("economicEntry.measureStage.name");
		sel.add("economicEntry.indicaEntry.entrys.*");
		sel.add("entry.investEntry.indexName.*");
		sel.add("economicEntry.indicaEntry.entrys.productType.*");
		sel.add("economicEntry.indicaEntry.entrys.propertyRightsNew.*");
		return sel;
	}
	public void loadFields(){
		super.loadFields();
		OperationPhasesInvestListEntryCollection col=this.editData.getEntry();
//		CRMHelper.sortCollection(col, "indexVersion.number", true);
		this.panelInvest.removeAll();
		
//		for(int i=0;i<col.size();i++){
//			OperationPhasesInvestEntryCollection entryCol=col.get(i).getInvestEntry();
//			CRMHelper.sortCollection(entryCol, "seq", true);
//			
//			KDTable table=createTable(col.get(i));
//			for(int j=0;j<entryCol.size();j++){
//				IRow row=table.addRow();
//				row.setUserObject(entryCol.get(j));
//				if(entryCol.get(j).getIndexName()!=null){
//					row.getCell("number").setValue(entryCol.get(j).getIndexName());
//					row.getCell("name").setValue(entryCol.get(j).getIndexName().getName());
//				}
//				row.getCell("amount").setValue(entryCol.get(j).getIndexValue());
//				row.getCell("description").setValue(entryCol.get(j).getIndexDescription());
//			}
//		}
		this.economicIndexTable=new HashMap();
		EconomicIndexCollection ecCol=this.editData.getEconomicEntry();
//		CRMHelper.sortCollection(ecCol, "measureStage.number", true);
		this.paneleEonomic.removeAll();
		
		for(int i=0;i<ecCol.size();i++){
			EconomicIndexInfo entry=ecCol.get(i);
			try {
				createEconomicIndex(entry);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.editData.getImgEntry().size()==0){
			this.backgroundPanel.setViewportView(new PhotoPanel());
		}
		setSaveActionStatus();
	}
	protected void kdtImageEntry_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = kdtImageEntry.getSelectManager().getActiveRowIndex();
		if(kdtImageEntry.getRow(rowIndex)!=null&&kdtImageEntry.getRow(rowIndex).getCell("imageFile").getValue()!=null)
		{
			byte[] bytes = (byte[])kdtImageEntry.getRow(rowIndex).getCell("imageFile").getValue();
        	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bfg = ImageIO.read(bais);
            PhotoPanel pPanel=new PhotoPanel();
            pPanel.setSelectImage(bfg);
            bais.close();
            this.backgroundPanel.setViewportView(pPanel);
		}
	}
	protected boolean checkCanSubmit() throws Exception {
		if(editData.getId()==null){
			return true;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		if(OperationPhasesFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))){
			OperationPhasesInfo billInfo = OperationPhasesFactory.getRemoteInstance().getOperationPhasesInfo(new ObjectUuidPK(editData.getId().toString()),selector);
			if(FDCBillStateEnum.AUDITTED.equals(billInfo.getState())
					|| FDCBillStateEnum.AUDITTING.equals(billInfo.getState())){
				return false;	
			}else{
				return true;
			}
		}
		return true;
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
	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}
	protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
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
	protected void checkBeforeEditOrRemove(String warning) {
		FDCBillStateEnum state = this.editData.getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	protected void setSaveActionStatus() {
		if (this.editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
		setSaveActionStatus();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTREMOVE);
		super.actionRemove_actionPerformed(e);
	}
	protected void showSaveSuccess(){
        setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
        setShowMessagePolicy(0);
        setIsShowTextOnly(false);
        showMessage();
    }
	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}
	public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}

		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}

		boolean b = true;

		if (!b) {
			return b;
		} else {

			// 校验storeFields()是否抛出异常
			// Exception err = verifyStoreFields();

			// storeFields()抛出异常或者editdata有改变，询问是否保存退出
			if (isModify()) {
				// editdata有改变
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));

				if (result == KDOptionPane.YES_OPTION) {

					try {
						if (this.editData.getState() == null
								|| this.editData.getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSave,
									ActionEvent.ACTION_PERFORMED, btnSave
											.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSubmit,
									ActionEvent.ACTION_PERFORMED, btnSubmit
											.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
							// actionSubmit_actionPerformed(event);
						}
						// return true;
					} catch (Exception exc) {
						// handUIException(exc);
						return false;
					}

				} else if (result == KDOptionPane.NO_OPTION) {
					// stopTempSave();
					return true;
				} else {
					return false;
				}
			} else {
				// stopTempSave();
				return true;
			}

		}

	}
    protected void showSubmitSuccess(){
        setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Submit_OK"));
        if(chkMenuItemSubmitAndAddNew.isSelected())
            setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
        else
        if(!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
            setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
        else
            setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
        setIsShowTextOnly(false);
        setShowMessagePolicy(0);
        showMessage();
    }
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(true);
        super.actionSave_actionPerformed(e);
        handleOldData();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);

		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审核中或者已审核，不能再提交");
			SysUtil.abort();
		}

		super.actionSubmit_actionPerformed(e);

		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
		}
		handleOldData();
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception{
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = this.editData.getState() != null
				&& this.editData.getState() != null
				&& this.editData.getState().equals(state);
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
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}
		
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		String id = getSelectBOID();
		if (id != null) {
			OperationPhasesFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
		
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		String id = getSelectBOID();
		if (id != null) {
			OperationPhasesFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
		
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionEntryAddLine.setEnabled(false);
			this.actionEntryRemoveLine.setEnabled(false);
			this.actionEntryInsertLine.setEnabled(false);
			this.actionAddIndexVersion.setEnabled(false);
			this.actionRemoveIndexVersion.setEnabled(false);
			this.actionImageAdd.setEnabled(false);
			this.actionImageRemove.setEnabled(false);
			this.actionAddEonomic.setEnabled(false);
			this.actionRemoveEonomic.setEnabled(false);
			this.actionEconomicAddRow.setEnabled(false);
			this.actionEconomicRemoveRow.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionEntryAddLine.setEnabled(true);
			this.actionEntryRemoveLine.setEnabled(true);
			this.actionEntryInsertLine.setEnabled(true);
			this.actionAddIndexVersion.setEnabled(true);
			this.actionRemoveIndexVersion.setEnabled(true);
			this.actionImageAdd.setEnabled(true);
			this.actionImageRemove.setEnabled(true);
			this.actionAddEonomic.setEnabled(true);
			this.actionRemoveEonomic.setEnabled(true);
			this.actionEconomicAddRow.setEnabled(true);
			this.actionEconomicRemoveRow.setEnabled(true);
		}
		setAuditButtonStatus(oprtType);
	}
	protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		OperationPhasesInfo bill = (OperationPhasesInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    }
	public void actionAddIndexVersion_actionPerformed(ActionEvent e) throws Exception {
		InvestIndexCollection col = InvestIndexFactory.getRemoteInstance().getInvestIndexCollection();
//		CRMHelper.sortCollection(col, "longNumber", true);
		if(col.size()>0){
			OperationPhasesInvestListEntryInfo entry=new OperationPhasesInvestListEntryInfo();
    		entry.setId(BOSUuid.create(entry.getBOSType()));
    		
    		KDTable table=createTable(entry);
    		for(int i=0;i<col.size();i++){
    			IRow row=table.addRow();
    			OperationPhasesInvestEntryInfo investEntry=new OperationPhasesInvestEntryInfo();
    			InvestIndexInfo investInfo = col.get(i);
    			row.setUserObject(investEntry);
    			row.getCell("number").setValue(investInfo);
    			row.getCell("name").setValue(investInfo.getName());
    			row.getCell("description").setValue(investInfo.getDescription());
	    	}
		}
		this.panelInvest.setSelectedIndex(this.panelInvest.getTabCount()-1);
	}
	public void actionRemoveIndexVersion_actionPerformed(ActionEvent e) throws Exception {
		KDContainer selectCom=(KDContainer) this.panelInvest.getSelectedComponent();
		if(selectCom==null){
			FDCMsgBox.showWarning(this,"请先选择指标版本页签！");
			return;
		}
		if (confirmRemove(this)) {
			this.panelInvest.remove(selectCom);
		}
	}
	public void actionEntryAddLine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.getSelectTable();
		if(table==null){
			FDCMsgBox.showWarning(this,"请先新增指标版本！");
			return;
		}
		this.actionALine(table,new OperationPhasesInvestEntryInfo());
	}
	public void actionEntryInsertLine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.getSelectTable();
		if(table==null){
			FDCMsgBox.showWarning(this,"请先新增指标版本！");
			return;
		}
		this.actionILine(table,new OperationPhasesInvestEntryInfo());
	}
	public void actionEntryRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.getSelectTable();
		if(table==null){
			FDCMsgBox.showWarning(this,"请先新增指标版本！");
			return;
		}
		this.actionRLine(table);
	}
	public void actionImageAdd_actionPerformed(ActionEvent e) throws Exception {
		KDFileChooser chooser = new KDFileChooser();
		chooser.resetChoosableFileFilters();
		chooser.removeChoosableFileFilter(chooser.getFileFilter());
    	chooser.addChoosableFileFilter(new MyFileFilter(".jpg","JPG格式图片"));
    	int i=chooser.showOpenDialog(null);
    	if(i==KDFileChooser.APPROVE_OPTION){//当选择了图片后才进行下面操作
	    	File file = chooser.getSelectedFile();
	        if(file!=null)
	        {
	        	long len = file.length();
				byte[] bytes = new byte[(int)len];
				
				BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
				bufferedInputStream.read(bytes);
				bufferedInputStream.close();
				
	        	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                BufferedImage bfg = ImageIO.read(bais);
                PhotoPanel pPanel=new PhotoPanel();
                pPanel.setSelectImage(bfg);
                this.backgroundPanel.setViewportView(pPanel);
                
                IRow row = null;
        		if(this.kdtImageEntry != null)
        		{
        			row = actionALine(kdtImageEntry,new OperationPhasesImageInfo());
        		}
			    row.getCell("imageName").setValue(file.getName());
			    row.getCell("imagePath").setValue(file.getPath());
			    row.getCell("imageFile").setValue(bytes);
			    this.kdtImageEntry.getSelectManager().select(row.getRowIndex(),0);
	        }
    	}
	}
	public void actionImageRemove_actionPerformed(ActionEvent e)throws Exception {
		actionRLine(kdtImageEntry);
		if(kdtImageEntry.getSelectManager().get()==null||kdtImageEntry.getRowCount()==0){
			this.backgroundPanel.setViewportView(new PhotoPanel());
		}
	}
	protected KDTable getSelectTable(){
		for(int i=0;i<((KDContainer)this.panelInvest.getSelectedComponent()).getContentPane().getComponentCount();i++){
			if(((KDContainer)this.panelInvest.getSelectedComponent()).getContentPane().getComponent(i) instanceof KDTable){
				return (KDTable) ((KDContainer)this.panelInvest.getSelectedComponent()).getContentPane().getComponent(i);
			}
		}
		return null;
	}
	protected EconomicIndexTable getSelectEconmicTable(){
		if(this.economicIndexTable!=null&&this.paneleEonomic.getSelectedComponent()!=null){
			KDContainer con=(KDContainer) this.paneleEonomic.getSelectedComponent();
			EconomicIndexInfo entry=(EconomicIndexInfo) con.getUserObject();
			return (EconomicIndexTable) this.economicIndexTable.get(entry.getId().toString());
		}
		return null;
	}
	public IRow actionALine(KDTable table,IObjectValue entry) throws Exception {
		IRow row = table.addRow();
		row.setUserObject(entry);
		return row;
	}
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if(table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();
            if(block.getMode() == 4 || block.getMode() == 8)
                return true;
        }
        return false;
    }
	public IRow actionILine(KDTable table,IObjectValue entry) throws Exception {
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)){
				row = table.addRow();
			}else{
				row = table.addRow(top);
			}
				
		} else {
			row = table.addRow();
		}
		row.setUserObject(entry);
		return row;
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionRLine(KDTable table) throws Exception {
		if (table.getSelectManager().size() == 0 || isTableColumnSelected(table)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = table.getSelectManager().get().getBeginRow();
			int bottom = table.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (table.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				table.removeRow(top);
			}
		}
	}
	/**
	 * 文件内部类
	 * */
	class MyFileFilter extends FileFilter{
		private String ext;
		private String des;
	    public MyFileFilter(String ext,String des){
	    	this.ext=ext;
	    	this.des=des;
	    }
	    public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	        if(f.getName().toLowerCase().endsWith(ext)){
	        	return true;
	        }
	        return false;
	    }

	    public String getDescription() {
	        return des;
	    }
	}
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected IObjectValue createNewData() {
		OperationPhasesInfo info=new OperationPhasesInfo();
		info.setIsNewProject(true);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBuildDate(now);
		
		BOSUuid newId=(BOSUuid) this.getUIContext().get("newId");
		info.setId(newId);
		ProjectBaseInfo projectBase=(ProjectBaseInfo) this.getUIContext().get("projectBase");
		info.setProjectBase(projectBase);
		
		OperationPhasesInfo parent=(OperationPhasesInfo)this.getUIContext().get("parent");
		info.setParent(parent);
		
		try {
			InvestIndexCollection col = InvestIndexFactory.getRemoteInstance().getInvestIndexCollection();
//			CRMHelper.sortCollection(col, "longNumber", true);
			if(col.size()>0){
				OperationPhasesInvestListEntryInfo entry=new OperationPhasesInvestListEntryInfo();
	    		entry.setId(BOSUuid.create(entry.getBOSType()));
	    		for(int i=0;i<col.size();i++){
		    		InvestIndexInfo investInfo = col.get(i);
		    		OperationPhasesInvestEntryInfo investEntry=new OperationPhasesInvestEntryInfo();
		    		investEntry.setIndexName(investInfo);
		    		investEntry.setIndexDescription(investInfo.getDescription());
		    		
		    		entry.getInvestEntry().add(investEntry);
		    	}
	    		info.getEntry().add(entry);
			}
	    	
		} catch (BOSException e) {
			e.printStackTrace();
		}
		EconomicIndexInfo entry=new EconomicIndexInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		
		info.getEconomicEntry().add(entry);
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return OperationPhasesFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	public void actionAddEonomic_actionPerformed(ActionEvent e) throws Exception {
		EconomicIndexInfo entry=new EconomicIndexInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		createEconomicIndex(entry);
		this.paneleEonomic.setSelectedIndex(this.paneleEonomic.getTabCount()-1);
	}
	public void actionRemoveEonomic_actionPerformed(ActionEvent e) throws Exception {
		KDContainer selectCom=(KDContainer) this.paneleEonomic.getSelectedComponent();
		if(selectCom==null){
			FDCMsgBox.showWarning(this,"请先选择指标版本页签！");
			return;
		}
		if (confirmRemove(this)) {
			this.paneleEonomic.remove(selectCom);
		}
	}
	public void actionEconomicAddRow_actionPerformed(ActionEvent e) throws Exception {
		EconomicIndexTable table=this.getSelectEconmicTable();
		if(table==null){
			FDCMsgBox.showWarning(this,"请先新增经济指标！");
			return;
		}
//		table.addRow(e);
	}
	public void actionEconomicRemoveRow_actionPerformed(ActionEvent e) throws Exception {
		EconomicIndexTable table=this.getSelectEconmicTable();
		if(table==null){
			FDCMsgBox.showWarning(this,"请先新增经济指标！");
			return;
		}
//		table.deleteRow(e);
	}
	public RequestContext prepareActionSubmit(IItemAction itemAction)throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		return request;
	}
	protected void setFieldsNull(AbstractObjectValue newData) {
		OperationPhasesInfo info = (OperationPhasesInfo) newData;
		info.setNumber(null);
		info.setName(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setCreateTime(null);
		info.setCreator(null);
		info.setLastUpdateTime(null);
		info.setLastUpdateUser(null);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
	}
}