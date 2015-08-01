/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ILandDeveloper;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:甲方(开发商)序时簿界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class LandDeveloperListUI extends AbstractLandDeveloperListUI {
	private static final Logger logger = CoreUIObject.getLogger(LandDeveloperListUI.class);

	/**
	 * output class constructor
	 */
	public LandDeveloperListUI() throws Exception {
		super();
	}

	/**
	 * 甲方支持在虚体\实体财务组织下录入甲方。added by andy_liu 2012-4-8
	 */
	public void onLoad() throws Exception {
		super.onLoad();

//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
		if (SysContext.getSysContext().getCurrentOrgUnit() != null
				//isIsCompanyOrgUnit()如果是财务组织就是公司。所以这行可以不要。added by andy_liu 2012-4-5
				//&& SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()
				//&& SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
				//甲方支持在虚体\实体财务组织下录入甲方added by andy_liu 2012-4-3
				&& SysContext.getSysContext().getCurrentFIUnit() != null
				//由于下面俩个对象不同，但是编码是相同的，编码不可以重复所以这里使用编码判断added by andy_liu 2012-4-5
				&& SysContext.getSysContext().getCurrentOrgUnit().getNumber().equals(
						SysContext.getSysContext().getCurrentFIUnit().getNumber())
				) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
		}else{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected String getEditUIName() {
		return LandDeveloperEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return LandDeveloperFactory.getRemoteInstance();
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
		actionRefresh_actionPerformed(e);
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
		if(iLandDeveloper.enabled(new ObjectUuidPK(id))){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
		}	
	}
	
	
	//启用功能实现
	 public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
	 {
	        IRow row = checkSelected(e);
			if (row == null)
				return;
			String id = row.getCell("id").getValue().toString().trim();
			ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
			if(iLandDeveloper.enabled(new ObjectUuidPK(id))){
				setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				showMessage();
			}	
			
			actionRefresh_actionPerformed(e);
	 }
	 
	 //禁用功能实现
	 public void actionCancel_actionPerformed(ActionEvent e) throws Exception
	 {
	    	IRow row = checkSelected(e);
			if (row == null)
				return;
			String id = row.getCell("id").getValue().toString().trim();
			ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
			if(iLandDeveloper.disEnabled(new ObjectUuidPK(id))){
				setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				showMessage();
			}	
			
			actionRefresh_actionPerformed(e);
	 }

	public void actionDisEnabled_actionPerformed(ActionEvent e) throws Exception {
		actionRefresh_actionPerformed(e);
		
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
		if(iLandDeveloper.disEnabled(new ObjectUuidPK(id))){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
		}		
		
	}

	private IRow checkSelected(ActionEvent e) {
		IRow row = null;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定一条记录
			MsgBox.showWarning("请先指定一条记录!");
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}
		return row;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			// boolean status = false;
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);				
			}
		}
	}

	/**
	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancel.setEnabled(isEnabled);
		this.actionCancelCancel.setEnabled(!isEnabled);
	}
	
    protected boolean isIgnoreCUFilter()
    {
        return true;
    }

    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }

	/**
	 * 新需求。数据共享与隔离：
	 *	1)	上下级数据共享。即，下级可查看上级建的甲方，上级可查看到下级建的甲方。
	 *	2)	同级的数据隔离。（如下图所示：华南区不能查看到西部区的数据，深圳地产不能查看到广州地产的数据）。
	 * 增加了orgUnit字段，对其长编码及编码进行过滤。added by andy_liu 2012-4-8
	 */
    protected void beforeExcutQuery(EntityViewInfo ev) {
    	super.beforeExcutQuery(ev);    
     	if (SysContext.getSysContext().getCurrentOrgUnit() != null) {
			FilterInfo filter = new FilterInfo();
			String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
			
			/* modified by zhaoqin for BT876982 on 2015/04/24 start */
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber + "!%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber));
			int index = longNumber.lastIndexOf('!');
			Set numberSet = new HashSet();
			while(index != -1) {
				longNumber = longNumber.substring(0, index);
				numberSet.add(longNumber);
				index = longNumber.lastIndexOf('!');
			}
			//Set numberSet = new HashSet(Arrays.asList(longNumber.split("!")));
			if(numberSet.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.number", numberSet, CompareType.INCLUDE));
				filter.setMaskString("#0 or #1 or #2");
			} else {
				filter.setMaskString("#0 or #1");
			}
			if(null != ev.getFilter()) {
				try {
					ev.getFilter().mergeFilter(filter, "and");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			} else {
				ev.setFilter(filter);
			}
			/* modified by zhaoqin for BT876982 on 2015/04/24 end */
		}
    }
    

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}