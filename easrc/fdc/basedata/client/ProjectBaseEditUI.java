/**
 * output package name
 */
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

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.InvestIndexCollection;
import com.kingdee.eas.fdc.basedata.InvestIndexFactory;
import com.kingdee.eas.fdc.basedata.InvestIndexInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseImageInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInvestEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectBaseInvestEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInvestListEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectBaseInvestListEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseSoilEntryInfo;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.hr.emp.client.PhotoPanel;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ProjectBaseEditUI extends AbstractProjectBaseEditUI
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(ProjectBaseEditUI.class);

    public ProjectBaseEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
    	this.editData.getEntry().clear();
    	for(int i=0;i<this.panelInvest.getComponentCount();i++){
			if(this.panelInvest.getComponent(i) instanceof KDContainer){
				KDContainer con=(KDContainer) this.panelInvest.getComponent(i);
				ProjectBaseInvestListEntryInfo entry=(ProjectBaseInvestListEntryInfo)con.getUserObject();
				
				entry.getInvestEntry().clear();
				for(int j=0;j<con.getContentPane().getComponentCount();j++){
					if(con.getContentPane().getComponent(j) instanceof KDTable){
						KDTable table=(KDTable) con.getContentPane().getComponent(j);
						for(int k=0;k<table.getRowCount();k++){
							IRow row=table.getRow(k);
							ProjectBaseInvestEntryInfo investEntry=(ProjectBaseInvestEntryInfo)row.getUserObject();
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
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	initTable();
		super.onLoad();
		initControl();
	}
    public void initControl(){
    	this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
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
		
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionCalculator.setVisible(false);
		
    	this.txtBuildArea.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	this.txtCapacityRate.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	this.txtTotalUseArea.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	this.txtVolumeBuildArea.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	
    	
    	this.txtNumber.setRequired(true);
    	this.txtName.setRequired(true);
    	this.cityProject.setRequired(true);
    	this.engineeProject.setRequired(true);
        
        this.cityProject.setEntityViewInfo(getOrgEntityInfo());
		this.engineeProject.setEntityViewInfo(getFIEntityInfo());
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
		FDCClientVerifyHelper.verifyEmpty(this, this.cityProject);
		FDCClientVerifyHelper.verifyEmpty(this, this.engineeProject);
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
	}
    public void initTable(){
    	this.kdtSoilEntry.checkParsed();
    	KDWorkButton btnSoilAddRow = new KDWorkButton();
		KDWorkButton btnSoilInsertRow = new KDWorkButton();
		KDWorkButton btnSoilRemoveRow = new KDWorkButton();

		this.actionEntryAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnSoilAddRow = (KDWorkButton)this.contSoilEntry.add(this.actionEntryAddLine);
		btnSoilAddRow.setText("新增行");
		btnSoilAddRow.setSize(new Dimension(140, 19));

		this.actionEntryInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnSoilInsertRow = (KDWorkButton) this.contSoilEntry.add(this.actionEntryInsertLine);
		btnSoilInsertRow.setText("插入行");
		btnSoilInsertRow.setSize(new Dimension(140, 19));

		this.actionEntryRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnSoilRemoveRow = (KDWorkButton) this.contSoilEntry.add(this.actionEntryRemoveLine);
		btnSoilRemoveRow.setText("删除行");
		btnSoilRemoveRow.setSize(new Dimension(140, 19));
		
		TableUtils.changeTableNumberFormat(this.kdtSoilEntry,  new String[]{"useArea","soilPrice","buildingArea","redLineBuildLand","greenbeltRate","capacityRate","budingHigh","buildingDimension","useYearLimit"});
    	
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
    }
    protected KDTable createTable(ProjectBaseInvestListEntryInfo entry){
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
    private void prmtIndexVersion_dataChanged(DataChangeEvent e) {
    	IndexVersionInfo info=(IndexVersionInfo) e.getNewValue();
    	KDContainer selectCom=(KDContainer) this.panelInvest.getSelectedComponent();
		if(info!=null){
			for(int i=0;i<this.panelInvest.getComponentCount();i++){
				if(this.panelInvest.getComponent(i) instanceof KDContainer&&!selectCom.equals(this.panelInvest.getComponent(i))){
					KDContainer con=(KDContainer) this.panelInvest.getComponent(i);
					IndexVersionInfo comInfo=((ProjectBaseInvestListEntryInfo)con.getUserObject()).getIndexVersion();
					if(comInfo!=null&&comInfo.getId().toString().equals(info.getId().toString())){
						FDCMsgBox.showWarning(this,info.getName()+"已经存在，请选择其他指标版本！");
						((KDBizPromptBox)e.getSource()).setValue(e.getOldValue());
						return;
					}
				}
			}
			((ProjectBaseInvestListEntryInfo)selectCom.getUserObject()).setIndexVersion(info);
			this.panelInvest.setTitleAt(this.panelInvest.getSelectedIndex(), info.getName());
		}else{
			((ProjectBaseInvestListEntryInfo)selectCom.getUserObject()).setIndexVersion(null);
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
		return sel;
	}
	public void loadFields(){
		super.loadFields();
		TableUtils.getFootRow(kdtSoilEntry, new String[]{"useArea","soilPrice","buildingArea","redLineBuildLand","replaceUseArea","buildingUseArea","houseUseArea","businessUseArea","otherUseArea"});
		
		ProjectBaseInvestListEntryCollection col=this.editData.getEntry();
//		CRMHelper.sortCollection(col, "seq", true);
		this.panelInvest.removeAll();
		
		for(int i=0;i<col.size();i++){
			ProjectBaseInvestEntryCollection entryCol=col.get(i).getInvestEntry();
//			CRMHelper.sortCollection(entryCol, "seq", true);
			
			KDTable table=createTable(col.get(i));
			for(int j=0;j<entryCol.size();j++){
				IRow row=table.addRow();
				row.setUserObject(entryCol.get(j));
				if(entryCol.get(j).getIndexName()!=null){
					row.getCell("number").setValue(entryCol.get(j).getIndexName());
					row.getCell("name").setValue(entryCol.get(j).getIndexName().getName());
				}
				row.getCell("amount").setValue(entryCol.get(j).getIndexValue());
				row.getCell("description").setValue(entryCol.get(j).getIndexDescription());
			}
		}
		if(this.editData.getImageEntry().size()==0){
			this.backgroundPanel.setViewportView(new PhotoPanel());
		}
//		FDCSQLBuilder sql = new FDCSQLBuilder();
//		sql.appendSql(" select max(flevel) as flevel from t_bd_strategyindex ");
//		IRowSet rowSet = sql.executeQuery();
//		int level = 0;
//		while(rowSet.next()){
//			level = Integer.parseInt(rowSet.getObject("flevel").toString());
//		}
//		for(int i=0; i<kdtInvestEntry.getRowCount();i++){
//			InvestIndexInfo info = (InvestIndexInfo)kdtInvestEntry.getCell(i, "indexNo").getValue();
//			if(info!=null)
//			{
//				kdtInvestEntry.getRow(i).getCell("indexName").setValue(info.getName());
//			}
//		}
	
	}
	
	protected void kdtSoilEntry_editStopped(KDTEditEvent e) throws Exception {
		String key = kdtSoilEntry.getColumnKey(e.getColIndex());
		if("useArea".equals(key) || "soilPrice".equals(key)|| "buildingArea".equals(key)|| "redLineBuildLand".equals(key)){
			TableUtils.getFootRow(kdtSoilEntry, new String[]{key});
		}
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
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
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
		} else {
			this.unLockUI();
			this.actionEntryAddLine.setEnabled(true);
			this.actionEntryRemoveLine.setEnabled(true);
			this.actionEntryInsertLine.setEnabled(true);
			this.actionAddIndexVersion.setEnabled(true);
			this.actionRemoveIndexVersion.setEnabled(true);
			this.actionImageAdd.setEnabled(true);
			this.actionImageRemove.setEnabled(true);
		}
	}
	public void actionAddIndexVersion_actionPerformed(ActionEvent e) throws Exception {
		InvestIndexCollection col = InvestIndexFactory.getRemoteInstance().getInvestIndexCollection();
//		CRMHelper.sortCollection(col, "longNumber", true);
		if(col.size()>0){
			ProjectBaseInvestListEntryInfo entry=new ProjectBaseInvestListEntryInfo();
    		entry.setId(BOSUuid.create(entry.getBOSType()));
    		
    		KDTable table=createTable(entry);
    		for(int i=0;i<col.size();i++){
    			IRow row=table.addRow();
    			ProjectBaseInvestEntryInfo investEntry=new ProjectBaseInvestEntryInfo();
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
		if(this.panelSoil.isShowing()){
			this.actionALine(this.kdtSoilEntry,new ProjectBaseSoilEntryInfo());
		}else{
			KDTable table=this.getSelectTable();
			if(table==null){
				FDCMsgBox.showWarning(this,"请先新增指标版本！");
				return;
			}
			this.actionALine(table,new ProjectBaseInvestEntryInfo());
		}
	}
	public void actionEntryInsertLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.panelSoil.isShowing()){
			this.actionILine(this.kdtSoilEntry,new ProjectBaseSoilEntryInfo());
		}else{
			KDTable table=this.getSelectTable();
			if(table==null){
				FDCMsgBox.showWarning(this,"请先新增指标版本！");
				return;
			}
			this.actionILine(table,new ProjectBaseInvestEntryInfo());
		}
	}
	public void actionEntryRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.panelSoil.isShowing()){
			this.actionRLine(this.kdtSoilEntry);
		}else{
			KDTable table=this.getSelectTable();
			if(table==null){
				FDCMsgBox.showWarning(this,"请先新增指标版本！");
				return;
			}
			this.actionRLine(table);
		}
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
        			row = actionALine(kdtImageEntry,new ProjectBaseImageInfo());
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
	public IRow actionALine(KDTable table,IObjectValue entry) throws Exception {
		IRow row = table.addRow();
		row.setUserObject(entry);
		return row;
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
		ProjectBaseInfo info=new ProjectBaseInfo();
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
		FullOrgUnitInfo cityProject=(FullOrgUnitInfo) this.getUIContext().get("cityProject");
		info.setCityProject(cityProject);
		try {
			InvestIndexCollection col = InvestIndexFactory.getRemoteInstance().getInvestIndexCollection();
//			CRMHelper.sortCollection(col, "longNumber", true);
			if(col.size()>0){
				ProjectBaseInvestListEntryInfo entry=new ProjectBaseInvestListEntryInfo();
	    		entry.setId(BOSUuid.create(entry.getBOSType()));
	    		for(int i=0;i<col.size();i++){
		    		InvestIndexInfo investInfo = col.get(i);
		    		ProjectBaseInvestEntryInfo investEntry=new ProjectBaseInvestEntryInfo();
		    		investEntry.setIndexName(investInfo);
		    		investEntry.setIndexDescription(investInfo.getDescription());
		    		
		    		entry.getInvestEntry().add(investEntry);
		    	}
	    		info.getEntry().add(entry);
			}
	    	
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ProjectBaseFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected  void fetchInitData() throws Exception{
		
	}
}