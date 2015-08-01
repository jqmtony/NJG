/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectPromptBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:房地产项目概况查询过滤自定义界面
 * @author jackwang  date:2007-6-7 <p>
 * @version EAS5.3
 */
public class ProjectAttachmentFilterUI extends AbstractProjectAttachmentFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectAttachmentFilterUI.class);
	private static final String PROJECT_IDS = "projectIds"; 
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";
    /**
     * output class constructor
     */
    public ProjectAttachmentFilterUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		super.onLoad();
	
		initStatus();
		ProjectPromptBox xx = new ProjectPromptBox();
//		this.bizProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		this.bizProject.setSelector(xx);//.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		this.bizProject.setEditable(false);
		this.bizProject.setRequired(true);
	}
    public void initData() throws Exception {
    	
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output pbAccount_focusLost method
     */
    protected void pbAccount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
        super.pbAccount_focusLost(e);
    }
	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if(para.getString(PROJECT_IDS)!=null){
			try {
				this.bizProject.setValue(CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(para.getString(PROJECT_IDS))));
			} catch (EASBizException e) {			
			} catch (BOSException e) {		
			}
		}
		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if(this.bizProject.getValue()==null){
			MsgBox.showWarning(this, EASResource.getString(resourcePath, "SelectCurProj"));
			return false;
		}
		return true;
	}
    public HashMap getResult() throws Exception {
        HashMap result = new HashMap();
////////////////////////
        if(this.bizProject.getValue()!=null){
        	result.put(PROJECT_IDS,((CurProjectInfo)this.bizProject.getValue()).getId().toString());
		}else{
			//给出提示
		}
        return result;
    }
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

//		String[] projIds = (String[]) this.txtProject.getUserObject();
//		if (!FDCHelper.isEmpty(projIds)) {
//			param.add(PROJECT_IDS, projIds);
//		}
		if(this.bizProject.getValue()!=null){
			param.add(PROJECT_IDS,((CurProjectInfo)this.bizProject.getValue()).getId().toString());
		}else{
			//给出提示
		}

		return param.getCustomerParams();
	}
	/**
	 * output class constructor
	 */
	public void clear() {
		this.bizProject.setValue(null);
		initStatus();
	}
	private void initStatus(){

	}
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
        FilterInfo fi = new FilterInfo();
        FilterItemCollection fic = fi.getFilterItems();
        fic.add(new FilterItemInfo("boAttchAsso.boID", para.get(PROJECT_IDS), CompareType.EQUALS));
		return fi;
	}
}