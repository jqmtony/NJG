/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.basedata.PcDepTypeCollection;
import com.kingdee.eas.fdc.contract.basedata.PcDepTypeFactory;
import com.kingdee.eas.fdc.contract.basedata.PcDepTypeInfo;
import com.kingdee.eas.fdc.contract.programming.IPcType;
import com.kingdee.eas.fdc.contract.programming.PcTypeCollection;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryCollection;
import com.kingdee.eas.fdc.contract.programming.PcTypeEntryInfo;
import com.kingdee.eas.fdc.contract.programming.PcTypeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class PcTypeEditUI extends AbstractPcTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PcTypeEditUI.class);
    private Map<String,PcDepTypeInfo> depTypeMap = new HashMap<String,PcDepTypeInfo>();
    private static final String fieldName = "fieldName";
    private static final String ckDate = "ckDate";
    private static final String tqDays = "tqDays";
    private static final String strongControl = "strongControl";
    private static final String recordSeq = "recordSeq";
    private static final String depType = "depType";
    
    /**
     * output class constructor
     */
    public PcTypeEditUI() throws Exception
    {
        super();
        contCreator.setVisible(false);
        contCreateTime.setVisible(false);
        contLastUpdateUser.setVisible(false);
        contLastUpdateTime.setVisible(false);
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	initEntrys();
    	initPanelByDeptype();
    	actionSubmit.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionCreateTo.setVisible(false);
    	actionCreateFrom.setVisible(false);
    	actionWorkFlowG.setVisible(false);
    	actionNextPerson.setVisible(false);
    	actionMultiapprove.setVisible(false);
    	actionAuditResult.setVisible(false);
    }
    
    public void initEntrys() {
    	kdtEntrys_detailPanel.setVisible(false);
    }
    
    //根据部门类型初始化页签
    public void initPanelByDeptype() {
    	try {
    		PcDepTypeCollection pcs = PcDepTypeFactory.getRemoteInstance().getPcDepTypeCollection(" order by number");
    		if(pcs.size() > 0){
    			Map<String, PcTypeEntryInfo> sortEntry = parseEntryCollection();
    			PcDepTypeInfo pinfo = null;
    			KDContainer cont = null;
    			KDWorkButton addLine = null;
    			KDWorkButton insetLine = null;
    			KDWorkButton removeLine = null;
    			MyActionListener myal = null;
    			KDTable table = null;
    			for (int i = 0; i < pcs.size(); i++) {
    				pinfo = pcs.get(i);
    				depTypeMap.put(pinfo.getNumber(),pinfo);
    				cont = new KDContainer();
    				cont.setTitle(pinfo.getName());
    				cont.getContentPane().setLayout(new BorderLayout(0,0));
    				table = initTable(pinfo.getNumber(),sortEntry);
    				cont.getContentPane().add(table,BorderLayout.CENTER);
    				addLine = new KDWorkButton("新增行");
    				insetLine = new KDWorkButton("插入行");
    				removeLine = new KDWorkButton("删除行");
    				addLine.setName("addLine");
    				insetLine.setName("insetLine");
    				removeLine.setName("removeLine");
    				addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
    				insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
    				removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
    				myal = new MyActionListener(table);
    				addLine.addActionListener(myal);
    				insetLine.addActionListener(myal);
    				removeLine.addActionListener(myal);
    				cont.addButton(addLine);
    				cont.addButton(insetLine);
    				cont.addButton(removeLine);
    				kdtabpan.addTab(pinfo.getName(),cont);
				}
    		}
		} catch (BOSException e) {
			handUIException(e);
		}
    }
    
    public KDTable initTable(String ptNumber,Map<String, PcTypeEntryInfo> sortEntry) {
    	final KDTable table = new KDTable();
    	table.getActionMap().remove("Paste");
    	table.setName(ptNumber);
    	IColumn column = table.addColumn();
    	column.setKey(fieldName);
    	column.setWidth(200);
    	column = table.addColumn();
    	column.setKey(ckDate);
    	column.setWidth(150);
    	column = table.addColumn();
    	column.setKey(tqDays);
    	column = table.addColumn();
    	column.setKey(strongControl);
		IRow row = table.addHeadRow();
		row.getCell(0).setValue("字段名称");
		row.getCell(1).setValue("参照时间");
		row.getCell(2).setValue("提前天数");
		row.getCell(3).setValue("是否强控");
		KDTextField kdtEntrys_fieldName_TextField = new KDTextField();
        kdtEntrys_fieldName_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_fieldName_CellEditor = new KDTDefaultCellEditor(kdtEntrys_fieldName_TextField);
        table.getColumn(fieldName).setEditor(kdtEntrys_fieldName_CellEditor);
        KDComboBox kdtEntrys_ckDate_ComboBox = new KDComboBox();
        kdtEntrys_ckDate_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.programming.CKDate").toArray());
        KDTDefaultCellEditor kdtEntrys_ckDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_ckDate_ComboBox);
        table.getColumn(ckDate).setEditor(kdtEntrys_ckDate_CellEditor);
        KDFormattedTextField kdtEntrys_tqDays_TextField = new KDFormattedTextField();
        kdtEntrys_tqDays_TextField.setEditable(true);
        kdtEntrys_tqDays_TextField.setHorizontalAlignment(2);
        kdtEntrys_tqDays_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntrys_tqDays_CellEditor = new KDTDefaultCellEditor(kdtEntrys_tqDays_TextField);
        table.getColumn(tqDays).setEditor(kdtEntrys_tqDays_CellEditor);
        KDCheckBox kdtEntrys_strongControl_CheckBox = new KDCheckBox();
        KDTDefaultCellEditor kdtEntrys_strongControl_CellEditor = new KDTDefaultCellEditor(kdtEntrys_strongControl_CheckBox);
        table.getColumn(strongControl).setEditor(kdtEntrys_strongControl_CellEditor);
        // load table datas
        if(sortEntry.size() > 0){
        	String key = null;
        	PcTypeEntryInfo einfo = null;
        	for(Iterator<String> it=sortEntry.keySet().iterator(); it.hasNext();){
        		key = it.next();
        		if(key.indexOf(ptNumber) != -1){
        			row = table.addRow();
        			einfo = sortEntry.get(key);
        			row.getCell(fieldName).setValue(einfo.getFieldName());
        			row.getCell(ckDate).setValue(einfo.getCkDate());
        			row.getCell(tqDays).setValue(einfo.getTqDays());
        			row.getCell(strongControl).setValue(einfo.isStrongControl());
        		}
        	}
        }
        
        // add listener
        table.addKDTEditListener(new KDTEditAdapter(){
        	@Override
        	public void editStopped(KDTEditEvent e) {
        		table_editStopped(table,e);
        	}
        });
    	return table;
    }
    
    private Map<String,PcTypeEntryInfo> parseEntryCollection(){
    	Map<String,PcTypeEntryInfo> sortEntry = new TreeMap<String,PcTypeEntryInfo>();
    	PcTypeEntryCollection ptcoll = editData.getEntrys();
        PcTypeEntryInfo einfo = null;
        for(int i=ptcoll.size()-1; i>=0; i--){
        	einfo = ptcoll.get(i);
        	sortEntry.put(einfo.getRecordSeq(),einfo);
        }
        return sortEntry;
    }
    
    private void loadTableDatas(){
    	
    }
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	if(null==txthyType.getText() || "".equals(txthyType.getText().trim())){
    		FDCMsgBox.showInfo("合约类型不能为空！");
    		txthyType.grabFocus();
			SysUtil.abort();
    	}
    	IPcType ipt = PcTypeFactory.getRemoteInstance();
    	PcTypeCollection pcoll = null;
    	if(editData.getId() == null)
    		pcoll = ipt.getPcTypeCollection(" where hyType='"+txthyType.getText()+"'");
    	else
    		pcoll = ipt.getPcTypeCollection(" where hyType='"+txthyType.getText()+"' and id<>'"+editData.getId().toString()+"'");
    	if(pcoll.size() > 0){
    		FDCMsgBox.showInfo("合约类型:'"+txthyType.getText()+"'已存在！");
    		txthyType.grabFocus();
			SysUtil.abort();
    	}
    	
    }
    
    private void table_editStopped(KDTable table,KDTEditEvent e) {
    	int rowIndex = e.getRowIndex();
    	String columnKey = table.getColumnKey(e.getColIndex());
    	String recordSeq = table.getName()+rowIndex;
    	kdtEntrys.getCell(getEntrysRowIndex(recordSeq),columnKey).setValue(table.getCell(rowIndex,columnKey).getValue());
    }
    
    private int getEntrysRowIndex(String recordSeq){
    	int rowindex = -1;
    	for (int i = kdtEntrys.getRowCount3()-1; i >=0 ; i--) {
    		if(recordSeq.equals((String)kdtEntrys.getCell(i,"recordSeq").getValue())){
    			rowindex = i;
    			break;
    		}
		}
    	return rowindex;
    }
    
    class MyActionListener implements ActionListener{
    	private KDTable table;
    	
    	public MyActionListener() {
		}
    	public MyActionListener(KDTable tab) {
    		table = tab;
		}
    	public void actionPerformed(ActionEvent e) {
    		String type = ((KDWorkButton)e.getSource()).getName();
    		IRow row = null;
    		if("addLine".equals(type)){
    			row = table.addRow();
    			row.getCell(strongControl).setValue(Boolean.FALSE);
    			IRow entryRow = kdtEntrys.addRow();
    			entryRow.getCell(depType).setValue(depTypeMap.get(table.getName()));
    			entryRow.getCell(recordSeq).setValue(table.getName()+row.getRowIndex());
    		}else if("insetLine".equals(type)){
    			if(table.getSelectManager().size() > 0) {
    	            int top = table.getSelectManager().get().getTop();
    	            if(isTableColumnSelected(table))
    	            	row = table.addRow();
    	            else
    	            	row = table.addRow(top);
    	        } else {
    	        	row = table.addRow();
    	        }
    			row.getCell(strongControl).setValue(Boolean.FALSE);
    			//先删除数据分录的关于这个部门的所有行，再把这个部门的所有行增加到数据分录
    			rebuildEntrys(table);
    		}else{
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        if(table.getRow(top) == null){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        table.removeRow(top);
    	        rebuildEntrys(table);
    		}
//    		if(row != null){
//    			row.getCell("strongControl").setValue(Boolean.FALSE);
//    			IRow entryRow = kdtEntrys.addRow();
//    			entryRow.getCell("depType").setValue(depTypeMap.get(table.getName()));
//    			entryRow.getCell("recordSeq").setValue(table.getName()+row.getRowIndex());
//    		}
    	}
    }
    
    private void rebuildEntrys(KDTable table){
    	for (int i = 0; i < kdtEntrys.getRowCount3(); i++) {
			if(((String)kdtEntrys.getCell(i,recordSeq).getValue()).indexOf(table.getName()) != -1){
				kdtEntrys.removeRow(i);
				i = -1;
			}
		}
		IRow entryRow = null;
		for(int i = table.getRowCount3()-1; i >=0 ; i--){
			entryRow = kdtEntrys.addRow();
			entryRow.getCell(depType).setValue(depTypeMap.get(table.getName()));
			entryRow.getCell(recordSeq).setValue(table.getName()+i);
			entryRow.getCell(fieldName).setValue(table.getCell(i,fieldName).getValue());
			entryRow.getCell(ckDate).setValue(table.getCell(i,ckDate).getValue());
			entryRow.getCell(tqDays).setValue(table.getCell(i,tqDays).getValue());
			entryRow.getCell(strongControl).setValue(table.getCell(i,strongControl).getValue());
		}
    }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
        actionAttachment.setVisible(false);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
        actionAttachment.setVisible(false);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
        actionAttachment.setVisible(false);
        for (int i = 0; i < kdtabpan.getTabCount(); i++) {
        	KDContainer kdc = (KDContainer)kdtabpan.getComponentAt(i);
        	if(kdc.getContentPane().getComponent(0) instanceof KDTable){
        		((KDTable)kdc.getContentPane().getComponent(0)).removeRows();
        	}
		}
        
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        actionAttachment.setVisible(false);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
        actionAttachment.setVisible(false);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.programming.PcTypeFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.programming.PcTypeInfo objectValue = new com.kingdee.eas.fdc.contract.programming.PcTypeInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }

}