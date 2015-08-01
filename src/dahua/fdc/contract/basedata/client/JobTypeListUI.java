/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.JobTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/** 
 * ����:�а�����
 * @author jackwang  date:2007-2-5 <p>
 * @version EAS5.2
 */
public class JobTypeListUI extends AbstractJobTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(JobTypeListUI.class);

	/**
	 * output class constructor
	 */
	public JobTypeListUI() throws Exception {
		super();
	}

	/**
	 * 
	 */
//	public void onLoad() throws Exception {
//		super.onLoad();
//		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
//		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
//			this.btnAddNew.setEnabled(true);
//			this.btnEdit.setEnabled(true);
//			this.btnRemove.setEnabled(true);
//			this.btnEnabled.setVisible(true);
//			this.btnDisEnabled.setVisible(true);
//			this.menuItemAddNew.setEnabled(true);
//			this.menuItemEdit.setEnabled(true);
//			this.menuItemRemove.setEnabled(true);
//			//			this.menuItemCancel.setv(true)
//		} else {
//			this.btnAddNew.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.btnRemove.setEnabled(false);
//			this.btnEnabled.setVisible(false);
//			this.btnDisEnabled.setVisible(false);
//			this.menuItemAddNew.setEnabled(false);
//			this.menuItemEdit.setEnabled(false);
//			this.menuItemRemove.setEnabled(false);
//		}
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//	}

	protected String getEditUIName() {
		return JobTypeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return JobTypeFactory.getRemoteInstance();
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
//	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = checkSelected(e);
//		if (row == null)
//			return;
//		String id = row.getCell("id").getValue().toString().trim();
//		IJobType iJobType = JobTypeFactory.getRemoteInstance();
//		if (iJobType.enabled(new ObjectUuidPK(id))) {
//			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//			showMessage();
//		}
//		actionRefresh_actionPerformed(e);
//	}

	/**
	 * output actionDisEnabled_actionPerformed method
	 */
//	public void actionDisEnabled_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = checkSelected(e);
//		if (row == null)
//			return;
//		String id = row.getCell("id").getValue().toString().trim();
//		IJobType iJobType = JobTypeFactory.getRemoteInstance();
//		if (iJobType.disEnabled(new ObjectUuidPK(id))) {
//			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
//			showMessage();
//		}
//		actionRefresh_actionPerformed(e);
//	}

//	private IRow checkSelected(ActionEvent e) {
//		IRow row = null;
//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
//			// ����ָ��һ����¼
//			MsgBox.showWarning("����ָ��һ����¼!");
//		} else {
//			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
//		}
//		return row;
//	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
//	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
//		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
//			// boolean status = false;
//			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
//				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
//				// ����ÿһ�й����isEnabled��ֵ�ı䣬����WBT��״̬Ҳ�ı�
//				changeWBTEnabeld(status);
//			}
//		} else {
//			disabledWBT();
//
//		}
//	}

	/**
	 * ����ÿһ�й����isEnabled��ֵ�ı䣬����btn��״̬Ҳ�ı�
	 * 
	 * @param isEnabled
	 *            boolean
	 */
//	private void changeWBTEnabeld(boolean isEnabled) {
//		this.btnEnabled.setEnabled(!isEnabled);
//		this.btnDisEnabled.setEnabled(isEnabled);
//
//	}

	/**
	 * ������/��ֹ��ťdisabled
	 */
//	private void disabledWBT() {
//		this.btnEnabled.setEnabled(false);
//		this.btnDisEnabled.setEnabled(false);
//
//	}

	/**
	 * ����Ƿ�༭����ϵͳԤ����Ϣ
	 */
//	private boolean verifySysDataEdit() {
//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
//			return false;
//		} else {
//			int i = this.tblMain.getSelectManager().getActiveRowIndex();
//			if (OrgConstants.SYS_CU_ID.equals(this.tblMain.getRow(i).getCell("CU.id").getValue().toString())) {
//				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Update_SysData"));
//				return false;
//			} else {
//				return true;
//			}
//		}
//
//	}


	/**
	 * 
	 * ����Ƿ�ɾ������ϵͳԤ����Ϣ
	 */

//	private void verifyNotAccepted(IRow row) {
//		if (row.getCell("CU.id").getValue() != null && (OrgConstants.SYS_CU_ID.equals(row.getCell("CU.id").getValue().toString()))) {
//			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Delete_SysData"));
//			SysUtil.abort();
//		}
//	}
//    protected String getEditUIModal()
//    {
//        // return UIFactoryName.MODEL;
//        // return UIFactoryName.NEWWIN;
//        // 2006-4-29 ����Ҫ������������������ȡUI�򿪷�ʽ��
//        String openModel = UIConfigUtility.getOpenModel();
//        if (openModel != null)
//        {
//            return openModel;
//        }
//        else
//        {
//            return UIFactoryName.MODEL;
//        }
//    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new JobTypeInfo();
	}
}