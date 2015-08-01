/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
abstract public class FDCBaseDataListUI extends AbstractFDCBaseDataListUI {
	private static final Logger logger = CoreUIObject.getLogger(FDCBaseDataListUI.class);
	public FDCBaseDataListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
			public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
				_tblMain_tableSelectChanged(e);
			}
		});
		//�����в�ѯ�������ʲ��ֶ����ñ��淽����
//		//���ñ��浱ǰ��ʽ
//		tHelper = new TablePreferencesHelper(this);
	}

	 public void onShow() throws Exception {
		super.onShow();
		getMainTable().setColumnMoveable(true);
		// ���ý��ð�ť
		btnCancel.setVisible(true);
		btnCancelCancel.setVisible(true);
	}
	 
	 public void checkTableCancel() {

	}
	 
	 public void endTableCancel() {

	}

	final protected void _tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		checkTableCancel();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex != -1) {
			if (this.tblMain.getRow(activeRowIndex).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getCell(activeRowIndex, "isEnabled").getValue()).booleanValue();
				actionCancelCancel.setEnabled(!status);
				actionCancel.setEnabled(status);
			}
		} else {
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(false);
		}
		endTableCancel();
	}

	/**
	 * ɾ������ ��������� ��S��������ϵĿ���
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// ���������ϵͳԤ������
		if (isSystemDefaultData(activeRowIndex)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		//by tim_gao 2010-10-29
		String selectID = this.tblMain.getCell(activeRowIndex,"id").getValue().toString();
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID("ɾ��",selectID)){//�ж��Ƿ����ý���
    		return;
    	}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * �޸Ĳ��� ��������� ��S��������ϵĿ���
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// ���������ϵͳԤ������
		if (isSystemDefaultData(activeRowIndex)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		//by tim_gao 2010-10-29
		String selectID = this.tblMain.getCell(activeRowIndex,"id").getValue().toString();
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID("�޸�",selectID)){//�ж��Ƿ����ý���
    		return;
    	}
		super.actionEdit_actionPerformed(e);
	}
	 /**
     * @author tim_gao
	 * @throws Exception 
     * @date 2010-10-29
     * @description ����ID����������ж��Ƿ�����
     */
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words,String selectID) throws Exception{
    	boolean flag=false;
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",selectID));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));//�ж��Ƿ�����
    	if(getBizInterface().exists(filter)){//�жϼ�¼�Ƿ����
    		MsgBox.showWarning("����¼�Ѿ����ã�����"+words+"!");
    		flag=true;
    	}
		return flag;
    }
	protected void setIsEnabled(boolean flag) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// ���������ϵͳԤ������
		if (!flag && !FDCBaseDataEditUI.canCancel)
			if (isSystemDefaultData(activeRowIndex)) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		FDCDataBaseInfo info = getBaseDataInfo();
		info.setId(BOSUuid.read(id));
		info.setIsEnabled(flag);
		//�������á�����ʱ�õ����뼰����
		String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
		String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
		info.setNumber(number);
		info.setName(name);
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().enabled(new
		// ObjectUuidPK(id), info);
		else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().disEnabled(new
		// ObjectUuidPK(id), info);

		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
	}

	/**
	 * ���ò���
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		setIsEnabled(false);
	}

	/**
	 * ���ò���
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		setIsEnabled(true);
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.MODEL;
		}
	}

	/**
	 * �ж��Ƿ�ϵͳԤ������
	 * 
	 * @return
	 */
	protected boolean isSystemDefaultData(int activeRowIndex) {
		ICell cell = tblMain.getCell(activeRowIndex, "CU.id");
		if (cell == null)
			cell = tblMain.getCell(activeRowIndex, "CU.ID");
		String CU = (String) cell.getValue();
		if (OrgConstants.SYS_CU_ID.equals(CU)) {
			return true;
		}
		return false;
	}
    
    protected String getControlType() {
    	return getCtrler().getControlType();
    }
	/**
	 * ��ȡ�û�������ʵ�����
	 * 
	 * @return �û�������ʵ�����
	 */
	abstract protected FDCDataBaseInfo getBaseDataInfo();
	private FDCBaseDataClientCtrler ctrler=null;
	protected FDCBaseDataClientCtrler getCtrler(){
		if(ctrler==null){
			try {
				ctrler=new FDCBaseDataClientCtrler(this,getBizInterface());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}
	

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ƿ�֧��EAS�߼�ͳ��(EAS800�����Ĺ���)
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