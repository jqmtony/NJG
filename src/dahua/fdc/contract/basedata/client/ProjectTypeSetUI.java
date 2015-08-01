/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProjectTypeSetUI extends AbstractProjectTypeSetUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectTypeSetUI.class);
    
    boolean isComfirm = false;
    
	public boolean isComfirm() {
		return isComfirm;
	}

	public void setComfirm(boolean isComfirm) {
		this.isComfirm = isComfirm;
	}
	
    /**
     * output class constructor
     */
    public ProjectTypeSetUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionOK_actionPerformed
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    	boolean isOk = doOK();
    	
    	if(isOk){
    		CacheServiceFactory.getInstance().discardType(new BOSObjectType("F9E5E92B"));
    		setComfirm(true);
    		actionExitCurrent_actionPerformed(e);
    	}
    }

    /**
     * output acionCancel_actionPerformed
     */
    public void acionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	setComfirm(false);
    	super.actionExitCurrent_actionPerformed(e);
    }

	public void onLoad() throws Exception {
		super.onLoad();
		
		btnOK.setEnabled(true);
		btnCancel.setEnabled(true);
		tblMain.checkParsed();
		tblMain.getColumn("id").getStyleAttributes().setLocked(true);
		tblMain.getColumn("number").getStyleAttributes().setLocked(true);
		tblMain.getColumn("name").getStyleAttributes().setLocked(true);
		tblMain.getColumn("isEnabled").getStyleAttributes().setLocked(true);
				
		tblMain.getColumn("projectType").setEditor(getCellEditor());
		
		loadData() ;
	}
	
    public static ICellEditor getCellEditor() {
        KDBizPromptBox bizPromptBox = new KDBizPromptBox();
        bizPromptBox.setEditable(true);
        bizPromptBox.setEditFormat("$number$");
        bizPromptBox.setDisplayFormat("$name$");
        // 编码和名称都可以匹配
        bizPromptBox.setCommitFormat("$number$;$name$");

        bizPromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProjectTypeQuery");
        //bizPromptBox.setDefaultF7UIName(asstActInfo.getDefaultF7UI());
        EntityViewInfo view = new EntityViewInfo();
       
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
        view.setFilter(filter);
        bizPromptBox.setEntityViewInfo(view);
        
        return new KDTDefaultCellEditor(bizPromptBox);

    }
	
	public void loadData() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("isEnabled");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("projectType.number");
		view.getSelector().add("projectType.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("level",new Integer(1)));
		//filter.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		//filter.getFilterItems().add(new FilterItemInfo("projectType",null));
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
		
		CurProjectCollection col = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
		
		for(int i=0,size = col.size();i<size;i++){
			CurProjectInfo info  = col.get(i);
			IRow row = tblMain.addRow();
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
			row.getCell("isEnabled").setValue(Boolean.valueOf(info.isIsEnabled()));
			row.getCell("projectType").setValue(info.getProjectType());
			
			if(info.isIsEnabled()){
				row.getStyleAttributes().setLocked(true);
			}
		}
		
	}	

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	protected boolean doOK() throws Exception {
		 Map projectTypeMap = new HashMap();
		for(int i=0,size = tblMain.getRowCount();i<size;i++){
			IRow row = tblMain.getRow(i);
			String id = (String)row.getCell("id").getValue();
			ProjectTypeInfo info =  (ProjectTypeInfo)row.getCell("projectType").getValue();
			Boolean isEnable = (Boolean)row.getCell("isEnabled").getValue();
			if(!isEnable.booleanValue()){
				projectTypeMap.put(id,info);
			}
		}
		
		return CurProjectFactory.getRemoteInstance().setProjectTpe(projectTypeMap);
	}

}