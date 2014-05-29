/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.port.pm.invest.PreProjectE1Info;
import com.kingdee.eas.port.pm.invest.PreProjectFactory;
import com.kingdee.eas.port.pm.invest.PreProjectInfo;
import com.kingdee.eas.port.pm.invest.PreProjectTempE1Collection;
import com.kingdee.eas.port.pm.invest.PreProjectTempE1Info;
import com.kingdee.eas.port.pm.invest.PreProjectTempInfo;
import com.kingdee.eas.port.pm.invest.ProjectBudget2Collection;
import com.kingdee.eas.port.pm.invest.ProjectBudget2Factory;
import com.kingdee.eas.port.pm.invest.ProjectEstimateCollection;
import com.kingdee.eas.port.pm.invest.ProjectEstimateFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.ClientVerifyXRHelper;

/**
 * output class name
 */
public class PreProjectEditUI extends AbstractPreProjectEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PreProjectEditUI.class);
    
    /**
     * output class constructorR
     */
    public PreProjectEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
    	detachListeners();
        super.loadFields();
		for (int i = 0; i < this.kdtE1.getRowCount(); i++) 
		{
			IRow row = this.kdtE1.getRow(i);
			PreProjectE1Info e1Info = (PreProjectE1Info)row.getUserObject();
			if(e1Info.getId()!=null){
				try {
					row.getCell("attlist").setValue(loadAttachment(e1Info.getId().toString()));
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		attachListeners();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	this.kdtE1.addColumn(1).setKey("attlist");
    	this.kdtE1.getHeadRow(0).getCell("attlist").setValue("附件");
    	this.kdtE1.getColumn("attlist").getStyleAttributes().setLocked(true);
    	this.kdtE1.getColumn("attlist").getStyleAttributes().setFontColor(Color.BLUE);
    	
    	super.onLoad();
    	contBizStatus.setVisible(false);
		comboBizStatus.setVisible(false);
		contDescription.setVisible(false);
		txtDescription.setVisible(false);
		txtNumber.setEnabled(false);
		kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		this.actionSubmitTwo.setEnabled(true);
		this.btnSubmitTwo.setEnabled(true);
		//对项目名称进行过滤项目类型为“基本建设”的项目
	    FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("NJGprojectType.name","基本建设",com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		this.prmtprojectName.setEntityViewInfo(view);
		
		if(getUIContext().get("WorkReport")!=null)
		{
			this.toolBar.removeAllToolBarComponents();
			this.toolBar.add(this.btnSubmitTwo);
			this.toolBar.add(this.btnSubmitTwo);
			this.conttempName.setEnabled(false);
			this.contprojectName.setEnabled(false);
			this.contBizDate.setEnabled(false);
			this.kdtE1.getColumn("preWorkContent").getStyleAttributes().setLocked(true);
			this.kdtE1.getColumn("workReq").getStyleAttributes().setLocked(true);
			this.kdtE1.getColumn("planStartTime").getStyleAttributes().setLocked(true);
			this.kdtE1.getColumn("planCompTime").getStyleAttributes().setLocked(true);
			this.kdtE1.getColumn("respondDepart").getStyleAttributes().setLocked(true);
			this.kdtE1.getColumn("coDepart").getStyleAttributes().setLocked(true);
			this.kdtE1.getColumn("hostPerson").getStyleAttributes().setLocked(true);
		}
		
		this.kdtE1.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	kdtE1_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		
		setEntryAction();
    }
   
    private void setEntryAction()
    {
    	KDWorkButton removeButon = this.kdtE1_detailPanel.getRemoveLinesButton();
		if(removeButon.getActionListeners()[0]!=null)
		{
			removeButon.removeActionListener(removeButon.getActionListeners()[0]);
		}
		KDWorkButton addrowButon = this.kdtE1_detailPanel.getAddNewLineButton();
		if(addrowButon.getActionListeners()[0]!=null)
		{
			addrowButon.removeActionListener(addrowButon.getActionListeners()[0]);
		}
		KDWorkButton insertButon = this.kdtE1_detailPanel.getInsertLineButton();
		if(insertButon.getActionListeners()[0]!=null)
		{
			insertButon.removeActionListener(insertButon.getActionListeners()[0]);
		}
		
		kdtE1_detailPanel.getInsertLineButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(kdtE1 == null)
					return;
				PreProjectE1Info e1Info = new PreProjectE1Info();
				e1Info.setId(BOSUuid.create(e1Info.getBOSType()));
				
				IRow row = null;
				if(kdtE1.getSelectManager().size() > 0)
				{
					int top = kdtE1.getSelectManager().get().getTop();
					if(isTableColumnSelected(kdtE1))
						row = kdtE1.addRow();
					else
						row = kdtE1.addRow(top);
				}
				else
				{
					row = kdtE1.addRow();
				}
				row.setUserObject(e1Info);
			}
		});
		
		kdtE1_detailPanel.getAddNewLineButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(kdtE1 == null)
					return;
				PreProjectE1Info e1Info = new PreProjectE1Info();
				e1Info.setId(BOSUuid.create(e1Info.getBOSType()));
				
				IRow row = kdtE1.addRow();
				row.setUserObject(e1Info);
			}
		});
		
		kdtE1_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(kdtE1 == null)
					return;
				if(kdtE1.getSelectManager().size() == 0)
				{
					MsgBox.showInfo( EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				int top = kdtE1.getSelectManager().get().getTop();
				if(kdtE1.getRow(top) == null)
				{
					MsgBox.showInfo( EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				PreProjectE1Info detailData = (PreProjectE1Info)kdtE1.getRow(top).getUserObject();
				kdtE1.removeRow(top);
				try {
					deleteAttachment(detailData.getId().toString());
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
		});
    }
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if (kdtE1.getRowCount()<1) {
			MsgBox.showInfo("分录中没数据，请检查！");
			SysUtil.abort();
		}
		for (int i = 0; i < kdtE1.getRowCount(); i++) {
			ClientVerifyXRHelper.verifyKDTCellNull(this, this.kdtE1, i, "planStartTime");
			ClientVerifyXRHelper.verifyKDTCellNull(this, this.kdtE1, i, "planCompTime");
			ClientVerifyXRHelper.verifyKDTCellNull(this, this.kdtE1, i, "respondDepart");
			ClientVerifyXRHelper.verifyKDTCellNull(this, this.kdtE1, i, "hostPerson");
			if(UIRuleUtil.getDateValue(kdtE1.getCell(i,"planStartTime").getValue()).after(UIRuleUtil.getDateValue(kdtE1.getCell(i,"planCompTime").getValue()))){
				MsgBox.showWarning("计划开始时间不能晚于计划完成时间");
				SysUtil.abort();
			}
		}
	}
    protected void prmttempName_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmttempName_dataChanged(e);
    	if(prmttempName.getValue()!=null)
    	{
    		//删除原分录附件
    		for (int i = 0; i < this.kdtE1.getRowCount(); i++) 
    		{
    			PreProjectE1Info PreE1Info = (PreProjectE1Info)kdtE1.getRow(i).getUserObject();
    			if(PreE1Info.getId()==null){continue;}
    			deleteAttachment(PreE1Info.getId().toString());
			}
    		
    		this.kdtE1.removeRows();
    		PreProjectTempInfo info = (PreProjectTempInfo)prmttempName.getValue();//得到tempNameF7控件值
    		PreProjectTempE1Collection e1coll = info.getE1();//得到PreProjectTemp分录集合信息
    		IAdminOrgUnit iadmin = AdminOrgUnitFactory.getRemoteInstance();
    		for (int i = 0; i < e1coll.size(); i++) 
    		{
    			PreProjectTempE1Info e1Info = e1coll.get(i);
    			IRow row = kdtE1.addRow(i);
    			PreProjectE1Info PreE1Info = new PreProjectE1Info();
    			PreE1Info.setId(BOSUuid.create(PreE1Info.getBOSType()));
    			row.setUserObject(PreE1Info);
    			if(e1Info.getRespondDepart()!=null)
    				row.getCell("respondDepart").setValue(iadmin.getAdminOrgUnitInfo(new ObjectUuidPK(e1Info.getRespondDepart().getId())));
				if(e1Info.getPreWorkContent()!=null)
					row.getCell("preWorkContent").setValue(e1Info.getPreWorkContent());
				if(e1Info.getWorkRequest()!=null)
					row.getCell("workReq").setValue(e1Info.getWorkRequest());
			}
    	}
    }
    
    protected void kdtE1_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtE1_editStopped(e);
    	int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		String key = kdtE1.getColumnKey(colIndex);
		
		if(key.equals("actualPlanTime")||key.equals("actualCompTime"))
		{
			IRow row = kdtE1.getRow(rowIndex);
			String s = row.getCell("preWorkContent").getValue().toString().trim();
			if(s.compareTo("工可报告") == 0)
			{
				ProjectInfo preInfo = (ProjectInfo) prmtprojectName.getValue();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectName.id",preInfo.getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				ProjectEstimateCollection procol = ProjectEstimateFactory.getRemoteInstance().getProjectEstimateCollection(view);
				if(procol.size()>0){
					if(!procol.get(0).getStatus().equals(XRBillStatusEnum.AUDITED)){
						this.kdtE1.getCell(rowIndex, "actualPlanTime").setValue(null);
						this.kdtE1.getCell(rowIndex, "actualCompTime").setValue(null);
					}
				}else{
					this.kdtE1.getCell(rowIndex, "actualPlanTime").setValue(null);
					this.kdtE1.getCell(rowIndex, "actualCompTime").setValue(null);
				}
			}
			
			if(this.kdtE1.getCell(rowIndex, "preWorkContent").getValue().equals("初步设计"))
			{
				ProjectInfo preInfo = (ProjectInfo) prmtprojectName.getValue();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectName.id",preInfo.getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				ProjectBudget2Collection procol = ProjectBudget2Factory.getRemoteInstance().getProjectBudget2Collection(view);
				if(procol.size()>0){
					if(!procol.get(0).getStatus().equals(XRBillStatusEnum.AUDITED)){
						this.kdtE1.getCell(rowIndex, "actualPlanTime").setValue(null);
						this.kdtE1.getCell(rowIndex, "actualCompTime").setValue(null);
					}
				}else{
					this.kdtE1.getCell(rowIndex, "actualPlanTime").setValue(null);
					this.kdtE1.getCell(rowIndex, "actualCompTime").setValue(null);
				}
			}
		}
    	
    }
    
    public void actionSubmitTwo_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmitTwo_actionPerformed(e);
    	storeFields();
    	
    	PreProjectFactory.getRemoteInstance().update(new ObjectUuidPK(editData.getId()), editData);
    	MsgBox.showInfo("保存成功！");
    	setOprtState("VIEW");
    	setSaved(true);
    }
    
    protected void kdtE1_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 &&e.getClickCount()==2&&
				this.kdtE1.getColumnKey(e.getColIndex()).equals("attlist")) 
		{
			if(((PreProjectE1Info)this.kdtE1.getRow(e.getRowIndex()).getUserObject()).getId()==null){return;};
			String id=((PreProjectE1Info)this.kdtE1.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true"))
	        {
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else
	        {
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtE1.getRow(e.getRowIndex()).getCell("attlist").setValue(loadAttachment(id));
		}
	}
    
	protected String loadAttachment(String id) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		
		for(int i=0;i<col.size();i++){
			if(!"".equals(name.trim()))
				name=name+"； "+col.get(i).getAttachment().getName()+"";
			else
				name=name+col.get(i).getAttachment().getName();
		}
		return name;
	}
	
	
	protected void deleteAttachment(String id) throws BOSException, EASBizException
	{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		
		if(col.size()>0)
		{
			for(int i=0;i<col.size();i++)
			{
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1)
				{
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1)
				
				{
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.PreProjectFactory.getRemoteInstance();
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
        com.kingdee.eas.port.pm.invest.PreProjectInfo objectValue = new com.kingdee.eas.port.pm.invest.PreProjectInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setBizDate(new Date());
        return objectValue;
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmttempName);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmttempName);
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}

}