package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.framework.CUIDGetterFacadeFactory;
import com.kingdee.eas.basedata.framework.DataBaseDException;
import com.kingdee.eas.basedata.framework.ICUIDGetterFacade;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IInvalidCostReason;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonCollection;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 无效成本原因   列表界面
 */
public class InvalidCostReasonListUI extends AbstractInvalidCostReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvalidCostReasonListUI.class);
    
    /**
     * output class constructor
     */
    public InvalidCostReasonListUI() throws Exception
    {
        super();
        this.btnQuery.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
    }
    
    /**
     * onLoad中初始化Tree
     */
    public void onLoad() throws Exception
    {
        super.onLoad();
        
        tblMain.setColumnMoveable(true);
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return InvalidCostReasonFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return InvalidCostReasonEditUI.class.getName();
	}
	
	// 重写返回“根节点”名称的方法
	protected String getRootName() {
		return EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.INVALIDCOSTREASON);
	}
	protected String getEditUIModal() {
		// return UIFactoryName.MODEL;
		// return UIFactoryName.NEWWIN;
		// 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}
	
	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof InvalidCostReasonInfo) {
			InvalidCostReasonInfo cti = (InvalidCostReasonInfo) node.getUserObject();
			HashSet lnUps = new HashSet();
			try {
				InvalidCostReasonCollection ctc = InvalidCostReasonFactory.getRemoteInstance().getInvalidCostReasonCollection(
						"select id where longNumber like '" + cti.getLongNumber() + "!%'");
				lnUps.add(cti.getId().toString());
				for (int i = 0; i < ctc.size(); i++) {
					lnUps.add(ctc.get(i).getId().toString());
				}
			} catch (BOSException e1) {
				handUIException(e1);
				SysUtil.abort();
			}
			FilterInfo filterInfo = new FilterInfo();
			if (lnUps.size() != 0) {
				filterInfo.getFilterItems().add(new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
				this.mainQuery.setFilter(filterInfo);
			}
		} else {
			this.mainQuery.setFilter(new FilterInfo());
		}

		FilterInfo filter = (FilterInfo) this.getUIContext().get("F7Filter");
		if (filter != null) {
			this.mainQuery.getFilter().mergeFilter(filter, "and");
		}
		tblMain.removeRows();// 触发新查询
	}
	
	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkPermission(ACTION_MODIFY);
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		if (id != null) {
			IObjectPK pk = new ObjectStringPK(id);
			if (((IInvalidCostReason) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				CacheServiceFactory.getInstance().discardType(new InvalidCostReasonInfo().getBOSType());
				loadFields();
			}
		}
		tblMain.removeRows();// 触发新查询
		
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkPermission(ACTION_MODIFY);
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		if (id != null) {
			IObjectPK pk = new ObjectStringPK(id);
			if (((IInvalidCostReason) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				CacheServiceFactory.getInstance().discardType(new InvalidCostReasonInfo().getBOSType());
				loadFields();
			}
		}
		tblMain.removeRows();// 触发新查询
		
	}
	
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue())
						.booleanValue();
				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
				setCancelButtonStatus(status);
			}
		} else {
			setCancelButtonStatus(false);

		}
	}
	
	/**
	 * 设置启用/禁用按钮
	 */
	private void setCancelButtonStatus(boolean isEnabled) {
		this.actionCancel.setEnabled(isEnabled);
		this.actionCancelCancel.setEnabled(!isEnabled);
	}
	
    // ================== 以下是 基础资料控制 对于S类和I类 ==================
    
    private String controlType=null;
    
    private String currentCUID=null;
    private static final String CONTROL_TYPE_NAME="controlType";
    
    private static final String FILTERFIELD_CUID="CU.id";
    private static final String FILTERFIELD_CULONGNUMBER="CU.longNumber";
    
    private static final String CONTROLTYPE_S1="S1";
    private static final String CONTROLTYPE_S2="S2";
    private static final String CONTROLTYPE_S3="S3";
    private static final String CONTROLTYPE_S4="S4";
    private static final String CONTROLTYPE_I="I";
    
    private static final String ACTION_ADDNEW="ACTION_ADDNEW";
    private static final String ACTION_DELETE="ACTION_DELETE";
    private static final String ACTION_MODIFY="ACTION_MODIFY";
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
        checkPermission(ACTION_ADDNEW);
        super.actionAddNew_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        checkPermission(ACTION_MODIFY);
        super.actionEdit_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        checkPermission(ACTION_DELETE);
        SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("isEnabled");
		if (!FDCHelper.isEmpty(getSelectedKeyValue())) {
			InvalidCostReasonInfo reasonInfo = InvalidCostReasonFactory.getRemoteInstance().getInvalidCostReasonInfo(
					new ObjectUuidPK(getSelectedKeyValue()), sic);
			if (reasonInfo.isIsEnabled()) {
				FDCMsgBox.showWarning("启用状态不能删除");
				abort();
			}
		}
        super.actionRemove_actionPerformed(e);
    }
    
    private void checkPermission(String action) throws Exception {
        if(getControlType().equalsIgnoreCase(CONTROLTYPE_S1)) {
            if(!getCurrentCUID().equals(OrgConstants.DEF_CU_ID)) {
                throwsExceptionForNoPermission(action);
            }
        } else if(getControlType().equalsIgnoreCase(CONTROLTYPE_S3) 
                || getControlType().equalsIgnoreCase(CONTROLTYPE_S4)) {
            if(!action.equals(ACTION_ADDNEW)) {
                if(!getCurrentCUID().equals(getCUIDFromBizobject())) {
                    throwsExceptionForNoPermission(action);
                }
            }
        }
    }
    
    private void throwsExceptionForNoPermission(String action) throws Exception {
        if(action.equals(ACTION_ADDNEW)) {
            throw new DataBaseDException(DataBaseDException.CAN_NOT_ADD);
        } else if(action.equals(ACTION_DELETE)) {
            throw new DataBaseDException(DataBaseDException.CAN_NOT_DELETE);
        } else if(action.equals(ACTION_MODIFY)) {
            throw new DataBaseDException(DataBaseDException.CAN_NOT_UPDATE);
        }
    }
    
    protected String getControlType() {
        if(controlType==null) {
            controlType="";
            try {
	            EntityObjectInfo eoi=MetaDataLoaderFactory.getRemoteMetaDataLoader().getEntity(getBizInterface().getType());
	            if(eoi.containsExtendedPropertyKey(CONTROL_TYPE_NAME)) {
	                controlType=eoi.getExtendedProperty(CONTROL_TYPE_NAME);
	            }
            } catch(Exception e) {
                ExceptionHandler.handle(e);
                SysUtil.abort();
            }
        }
        return controlType;
    }
    
    private String getCurrentCUID() {
        if(currentCUID==null) {
            currentCUID=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
        }
        return currentCUID;
    }
    
    private String getCUIDFromBizobject() throws Exception {
        checkSelected();
        return getICGF().getCUID(getSelectedKeyValue());
    }
    private ICUIDGetterFacade getICGF() throws Exception {
        return CUIDGetterFacadeFactory.getRemoteInstance();
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