/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RealDateRelEditUI extends AbstractRealDateRelEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RealDateRelEditUI.class);
    Map<String,Integer> costDays = new HashMap<String,Integer>();
	Map<String,Integer> startDays = new HashMap<String,Integer>();
    /**
     * output class constructor
     */
    public RealDateRelEditUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	actionCopy.setVisible(false);
    	actionSubmit.setVisible(false);
    	actionAddNew.setVisible(false);
    	actionCancelCancel.setVisible(false);
    	actionCancel.setVisible(false);
    	btnSave.setIcon(btnSubmit.getIcon());
    	btnImportTemp.setIcon(EASResource.getIcon("imgTbtn_input"));
    	btnImportGroup.setIcon(EASResource.getIcon("imgTbtn_input"));
    	btnImportGroup.setText("从项目导入");
    	kdtEntrys.getColumn("pcname").getStyleAttributes().setLocked(false);
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select entry.CFPcname,entry.CFCostDays,entry.CFStartDays from CT_CON_RealDateRelEntrys entry left join CT_CON_RealDateRel rdr on rdr.fid=entry.fparentid ");
    	builder.appendSql("where rdr.FCONTROLUNITID='00000000-0000-0000-0000-000000000000CCE7AED4'");
    	IRowSet rs = builder.executeQuery();
    	while(rs.next()){
    		costDays.put(rs.getString("CFPcname"),rs.getInt("CFCostDays"));
    		startDays.put(rs.getString("CFPcname"),rs.getInt("CFStartDays"));
    	}
//    	kdtEntrys.getColumn("longNumber").getStyleAttributes().setHided(false);
    }
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	if(null==txtNumber.getText() || "".equals(txtNumber.getText().trim())){
    		MsgBox.showInfo("编码不能为空！");
    		SysUtil.abort();
    	}
    	if(null==txtName.getSelectedItemData()|| "".equals(txtName.getSelectedItemData().toString().trim())){
    		MsgBox.showInfo("名称不能为空！");
    		SysUtil.abort();
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
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
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
     * output actionImportTemp_actionPerformed
     */
    public void actionImportTemp_actionPerformed(ActionEvent e) throws Exception
    {
    	UIContext uiContext = new UIContext(this);
		uiContext.put("kdtEntrys", kdtEntrys);
		uiContext.put("costDays", costDays);
		uiContext.put("startDays", startDays);
    	IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TempImportUI.class.getName(), uiContext, null, OprtState.VIEW,
				WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		ui.show();
    }

    /**
     * output actionImportGroup_actionPerformed
     */
    public void actionImportGroup_actionPerformed(ActionEvent e) throws Exception{
    	//从项目导入
//    	UIContext uiContext = new UIContext(this);
//		uiContext.put("kdtEntrys", kdtEntrys);
//		uiContext.put("costDays", costDays);
//		uiContext.put("startDays", startDays);
//    	IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProjectImportUI.class.getName(), uiContext, null, OprtState.VIEW,
//				WinStyle.SHOW_ONLYLEFTSTATUSBAR);
//		ui.show();
    	
//    	FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("PROGRAMMING",""));
//		filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED"));
//    	filter.getFilterItems().add(new FilterItemInfo("isLatest","1"));
//		EntityViewInfo evi = new EntityViewInfo();
//		evi.setSelector(getTemplateEntrySelector());
//		evi.setFilter(filter);
    	String pid = ((CurProjectInfo)prmtcurProject.getValue()).getId().toString();
		String oql="select longNumber,sortNumber,name where PROGRAMMING in(select fid from T_CON_Programming where fstate='4AUDITTED' and fisLatest=1 and fprojectid='"+pid+"') order by sortNumber";
		ProgrammingContractCollection pcColl = 
			ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(oql);
		if(pcColl.size() == 0){
			MsgBox.showInfo("该项目下无已审核的最新版合约规划！");
			return;
		}
//		if(pcColl.size() == kdtEntrys.getRowCount3()){
//			MsgBox.showInfo("合约规划已是最新版！");
//			return;
//		}
		if(MsgBox.OK != MsgBox.showConfirm2(this,"从项目导入数据，继续么？")) {
			return;
		}
		ProgrammingContractInfo pcinfo = null;
		IRow row = null;
//		kdtEntrys.removeRows();
		if(kdtEntrys.getRowCount3() == 0){
			for(int i = 0, size = pcColl.size(); i < size; i++) {
				pcinfo = pcColl.get(i);
				row = kdtEntrys.addRow();
				row.getCell("longNumber").setValue(pcinfo.getLongNumber());
				row.getCell("pcname").setValue(pcinfo.getName());
			}
		}else{
			Map<String,Integer> indexNumber = new HashMap<String,Integer>();
			getIndexNumber(indexNumber);
			for(int i = 0, size = pcColl.size(); i < size; i++) {
				pcinfo = pcColl.get(i);
				if(!indexNumber.containsKey(pcinfo.getLongNumber())){
					// add new 
					if(i == 0){
						row = kdtEntrys.addRow(0);
					}else{
						row = kdtEntrys.addRow(indexNumber.get(pcColl.get(i-1).getLongNumber())+1);
					}
					row.getCell("longNumber").setValue(pcinfo.getLongNumber());
					row.getCell("pcname").setValue(pcinfo.getName());
					getIndexNumber(indexNumber);
				}
			}
		}
		MsgBox.showInfo(this, "导入成功！");
    }
    
    public Map<String,Integer> getIndexNumber(Map<String,Integer> indexNumber){
    	for (int i = 0; i < kdtEntrys.getRowCount3(); i++) {
			indexNumber.put((String)kdtEntrys.getCell(i,"longNumber").getValue(),i);
		}
    	return indexNumber;
    }
    
    private SelectorItemCollection getTemplateEntrySelector() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("name");
		sic.add("number");
		sic.add("id");
		sic.add("level");
		sic.add("longNumber");
		sic.add("displayName");
		sic.add("sortNumber");
		return sic;
	}

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.basedata.RealDateRelFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo objectValue = new com.kingdee.eas.fdc.contract.basedata.RealDateRelInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        CurProjectInfo cpinfo = (CurProjectInfo)getUIContext().get("treeSelectedObj");
        objectValue.setCurProject(cpinfo);
        return objectValue;
    }

}