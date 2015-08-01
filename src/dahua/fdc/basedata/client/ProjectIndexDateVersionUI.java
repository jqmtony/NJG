/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectIndexDateVersionUI extends AbstractProjectIndexDateVersionUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectIndexDateVersionUI.class);
    
    private Map retValue = null;

    public boolean isOK = false;
    
    /**
     * output class constructor
     */
    public ProjectIndexDateVersionUI() throws Exception
    {
        super();
    }

   public void onLoad() throws Exception {
	   
	   super.onLoad();
	   txtVerName.setRequired(true);
	   pkVerDate.setValue(new java.util.Date());
	   
	   EntityViewInfo view = new EntityViewInfo();
	   FilterInfo filter = new FilterInfo();
	   String proOrOrgId = (String)getUIContext().get("proOrOrgId");
	   ProjectStageEnum projectStage = (ProjectStageEnum)getUIContext().get("projectStage");
	   String productTypeId = (String)getUIContext().get("productTypeId");
	   
	   filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", proOrOrgId));
	   filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
	   filter.getFilterItems().add(new FilterItemInfo("productType", productTypeId));
	   filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
	   view.getSelector().add("verNo");
	   view.getSelector().add("verName");
	   view.setFilter(filter);
	   ProjectIndexDataCollection projectIndexDataCollection = null;
	   synchronized (this) {
		   projectIndexDataCollection = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
	   }
	   
	   String nextVerNo = "V1.0";
	   String verName=null;
	   if(projectIndexDataCollection.size() > 0) {
		   String verNo = projectIndexDataCollection.get(0).getVerNo();
		   if(!FDCHelper.isEmpty(verNo)) {
			   nextVerNo = getNextVerNoByCur(verNo);   
		   }
		   verName=projectIndexDataCollection.get(0).getVerName();
		   
	   }
	   
	   txtVerNo.setText(nextVerNo);
	
		if (!isDisplayAreaIndex()) {
			if(verName!=null){
				ProjectIndexVerTypeEnum type=ProjectIndexVerTypeEnum.getEnum(verName);
				if(type!=null){
					txtVerName.setSelectedItem(type);
				}else{
					txtVerName.addItem(verName);
					txtVerName.setSelectedItem(verName);
				}
			}
			txtVerName.setEditable(false);
			txtVerName.setEnabled(false);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					txtVerName.requestFocus();
				}
			});
		}
	 
   }
    
   public static String getNextVerNoByCur(String curNo) {
	   String v = curNo.substring(1, curNo.indexOf("."));
	   int i = Integer.parseInt(v);
	   i++;
	   
	   return "V"+i+".0";
   }
    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	verify();
        super.btnConfirm_actionPerformed(e);
        
        isOK = true;
        
        retValue = new HashMap();
        retValue.put("verNo", txtVerNo.getText());
        Object obj = txtVerName.getSelectedItem();
        if(obj instanceof ProjectIndexVerTypeEnum){
        	String verName= ((ProjectIndexVerTypeEnum)obj).getValue();
        	retValue.put("verName", verName);
        }
        retValue.put("verRemark", txtVerRemark.getText());
       
        this.destroyWindow();
    }
    
    private void verify() throws Exception{
    	FDCClientVerifyHelper.verifyRequire(this);
    	//校验指标版本类型
    	if(!isDisplayAreaIndex()){
    		return;
    	}
        Object obj = txtVerName.getSelectedItem();
        if(obj instanceof ProjectIndexVerTypeEnum){
        	ProjectIndexVerTypeEnum type = (ProjectIndexVerTypeEnum)obj;
     	   String proOrOrgId = (String) getUIContext().get("proOrOrgId");
			ProjectStageEnum projectStage = (ProjectStageEnum) getUIContext().get("projectStage");
			String productTypeId = (String) getUIContext().get("productTypeId");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", proOrOrgId));
			filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
			filter.getFilterItems().add(new FilterItemInfo("productType", productTypeId));
        	if(type==ProjectIndexVerTypeEnum.AIMCOSTAREA){
        		Set set=new HashSet();
        		set.add(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE);
        		set.add(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
        		filter.getFilterItems().add(new FilterItemInfo("verName",set,CompareType.INCLUDE));
        		if(ProjectIndexDataFactory.getRemoteInstance().exists(filter)){
        			MsgBox.showError(this,"已经存在“预售查丈面积”或“竣工查丈面积”的面积指标,不能将版本名称指定为“目标成本面积”");
        			txtVerName.requestFocus();
        			SysUtil.abort();
        		}
        	}
        	if(type==ProjectIndexVerTypeEnum.PRESELLAREA){
        		Set set=new HashSet();
        		set.add(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE);
        		filter.getFilterItems().add(new FilterItemInfo("verName",set,CompareType.INCLUDE));
        		if(ProjectIndexDataFactory.getRemoteInstance().exists(filter)){
        			MsgBox.showError(this,"已经存在“竣工查丈面积”的面积指标,不能将版本名称指定为“预售查丈面积”");
        			txtVerName.requestFocus();
        			SysUtil.abort();
        		}
			}
			if (type == ProjectIndexVerTypeEnum.COMPLETEAREA) {

			}
        }else{
        	MsgBox.showError(this, "版本名称必须选择");
        	SysUtil.abort();
        }
    }
    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
        super.btnCancel_actionPerformed(e);
    }
    
    public Map getValue() {
        return retValue;
    }
    
    public boolean isOK() {
        return isOK;
    }
    
    public static Map showDialogWindow(IUIObject ui, String proOrOrgId, ProjectStageEnum projStage, String productTypeId) {
        UIContext uiContext = new UIContext(ui);
        uiContext.put("proOrOrgId", proOrOrgId);
        uiContext.put("projectStage", projStage);
        uiContext.put("productTypeId", productTypeId);
        IUIWindow uiWindow = null;

        try {
            uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
            		ProjectIndexDateVersionUI.class.getName(),
                    uiContext,
                    null,
                    OprtState.EDIT);
            
            uiWindow.show();
        } catch (UIException e) {
        	//@AbortException
            ExceptionHandler.handle(e);
        }
        if (((ProjectIndexDateVersionUI) uiWindow.getUIObject()).isOK()) {
            return ((ProjectIndexDateVersionUI) uiWindow.getUIObject()).getValue();
        }
        return null;
    }
    
	private boolean displayAreaIdx=false;
	private boolean hasInit=false;
	protected boolean isDisplayAreaIndex() throws EASBizException, BOSException{
		//参数控制是否显示面积指标
		if(!hasInit){
			displayAreaIdx = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_PROJECTINDEX);
			hasInit=true;
		}
		return displayAreaIdx;
	}
}