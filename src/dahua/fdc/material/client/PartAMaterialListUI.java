/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.PartAMaterialCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * ������������ϸ������
 * ����ʱ�䣺2008-7-24<p>
 * @version EAS 6.0              
 */
public class PartAMaterialListUI extends AbstractPartAMaterialListUI
{ 
    private static final Logger logger = CoreUIObject.getLogger(PartAMaterialListUI.class);
    
    /**
     * output class constructor
     */
    public PartAMaterialListUI() throws Exception
    {
        super(); 
    }

    /**
     * 
     * ����������ѡ��ĺ�ͬ��ʾ�����б�
     * @param e 
     * @throws BOSException 
     */    
    protected void  displayBillByContract(EntityViewInfo view) throws BOSException {
    	if(view == null){
    		return;
    	}
    	
    	PartAMaterialCollection partAMaterialCollection = PartAMaterialFactory.getRemoteInstance().getPartAMaterialCollection(view);
    	for (Iterator iter = partAMaterialCollection.iterator(); iter.hasNext();) {
    		PartAMaterialInfo partAMaterialInfo = (PartAMaterialInfo) iter.next();
    		IRow row = getBillListTable().addRow();
    		row.getCell(PartAMaterialContants.ID).setValue(partAMaterialInfo.getId().toString());
    		row.getCell(PartAMaterialContants.STATE).setValue(partAMaterialInfo.getState());
    		row.getCell(PartAMaterialContants.VERSION).setValue(partAMaterialInfo.getVersion() + ""); //added by owen_wen 2010-08-23
    		row.getCell(PartAMaterialContants.PARTA_MATERIAL_NUMBER).setValue(partAMaterialInfo.getNumber()); //added by owen_wen 2010-08-23
    		row.getCell("creator").setValue(partAMaterialInfo.getCreator().getName()); //added by owen_wen 2010-12-22
    		row.getCell("createDate").setValue(partAMaterialInfo.getCreateTime()); 
    		if (partAMaterialInfo.getAuditor() != null)
    			row.getCell("auditor").setValue(partAMaterialInfo.getAuditor().getName()); 
    		row.getCell("auditDate").setValue(partAMaterialInfo.getAuditTime()); //added by owen_wen 2010-12-22
    	}	
    }
    
    public String GerneraterFormatString(int perc){
    	StringBuffer str =new StringBuffer("###.00");
    	for(int i=2;i<perc;i++){
    		str.append("#");
    	}
    	return str.toString();
    }
    
    /**
     * ɸѡ��������ϸ����������Ϣ
     * @author owen_wen
     * @return sic SelectorItemCollection
     */
	private SelectorItemCollection getInsiderSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("contractBill.*");
		sic.add("entrys.*");
		sic.add("entrys.material.*");
		sic.add("entrys.material.baseUnit.*");
		sic.add("entrys.mainContractBill.*");
		return sic;
	}

	/**
	 * �����ѡ�еĲ�����ϸ��
	 * @author owen_wen
	 * @return PartAMaterialInfo
	 * @throws EASBizException
	 * @throws BOSException
	 */
    private PartAMaterialInfo getSelectedInfo() throws EASBizException, BOSException  {    	
    	int[] rowID = KDTableUtil.getSelectedRows(tblMaterial);
		if (rowID.length < 1)
			return null;
		String selectId = tblMaterial.getRow(rowID[0]).getCell("id").getValue().toString().trim();
		return PartAMaterialFactory.getRemoteInstance().getPartAMaterialInfo(new ObjectUuidPK(BOSUuid.read(selectId)), getInsiderSelectors());
	}
    
    /**
     * ����selectedInfo�����µĲ�����ϸ����������
     * @author owen_wen
     * @param selectedInfo Ҫ�޶��Ĳ�����ϸ��
     * @return newInfo �µĲ�����ϸ��
     */
    private PartAMaterialInfo getNewPartAMaterialInfo(PartAMaterialInfo selectedInfo){
    	PartAMaterialInfo newInfo = new PartAMaterialInfo();
    	newInfo.setId(BOSUuid.create(selectedInfo.getBOSType()));
    	newInfo.setContractBill(selectedInfo.getContractBill());
    	newInfo.setNumber(selectedInfo.getNumber());
    	newInfo.setVersion(selectedInfo.getVersion() + 1);
    	
    	PartAMaterialEntryCollection pamec = selectedInfo.getEntrys();
    	PartAMaterialEntryCollection new_pamec = newInfo.getEntrys();
    	
    	for (int i = 0; i < pamec.size(); i++){
    		PartAMaterialEntryInfo partAMatEntry = pamec.get(i);
    		PartAMaterialEntryInfo new_partAMatEntry = new PartAMaterialEntryInfo();
    		
    		new_partAMatEntry.setId(BOSUuid.create(partAMatEntry.getBOSType()));
    		
    		new_partAMatEntry.setMainContractBill(partAMatEntry.getMainContractBill());
    		new_partAMatEntry.setMaterial(partAMatEntry.getMaterial());
    		new_partAMatEntry.setParent(selectedInfo);
    		
    		new_partAMatEntry.setAmount(partAMatEntry.getAmount());
    		new_partAMatEntry.setOriginalAmount(partAMatEntry.getOriginalAmount());
    		
    		new_partAMatEntry.setPrice(partAMatEntry.getPrice());
    		new_partAMatEntry.setOriginalPrice(partAMatEntry.getOriginalPrice());
    		
    		new_partAMatEntry.setQuantity(partAMatEntry.getQuantity());
    		new_partAMatEntry.setSeq(partAMatEntry.getSeq());
    		new_partAMatEntry.setArriveDate(partAMatEntry.getArriveDate());
    		new_partAMatEntry.setDescription(partAMatEntry.getDescription());    		
    		
    		new_pamec.add(new_partAMatEntry);
    	}
    	return newInfo;
    }
    
    /**
     * �޶�ǰ�ļ�飺δ�����ĵ��ݲ����޶���������߰汾�ĵ��ݲ����޶�
     * @author owen_wen 2010-8-24
     * @throws EASBizException
     * @throws BOSException
     */
    private void checkBeforeRevise() throws EASBizException, BOSException{
    	IRow row = FDCTableHelper.getSelectedRow(tblMaterial);
    	FDCBillStateEnum billState = (FDCBillStateEnum) row.getCell("state").getValue();
		if (!FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			FDCMsgBox.showInfo("δ�������ݲ������޶���");
			SysUtil.abort();
		}
		
		FilterInfo filter = new FilterInfo();
		String contractID = FDCTableHelper.getSelectedRow(tblMain).getCell("id").getValue().toString();
		filter.getFilterItems().add(new FilterItemInfo("contractBill", contractID, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("version",row.getCell(PartAMaterialContants.VERSION).getValue(), CompareType.GREATER));
		if (PartAMaterialFactory.getRemoteInstance().exists(filter)) {
			FDCMsgBox.showInfo("�ɰ汾�����޶���");
			SysUtil.abort();
		}
    }
    /**
     * �޶�
     * @author owen_wen 2010-8-24
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	checkBeforeRevise();
    	UIContext uiContext = new UIContext(this);
    	PartAMaterialInfo selectedInfo = getSelectedInfo();
    	PartAMaterialInfo newInfo = getNewPartAMaterialInfo(selectedInfo);
    	
    	uiContext.put(UIContext.INIT_DATAOBJECT, newInfo);
		uiContext.put("willRevise", Boolean.TRUE);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);	
		IUIWindow curDialog = uiFactory.create(getEditUIName(), uiContext, null, OprtState.ADDNEW);
		curDialog.show();
    }
	   
    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());
    	String billId = getSelectedKeyValue(getMainTable());
		PartAMaterialCollection partAMaterialColl = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billId));
		view.setFilter(filter);
		
		partAMaterialColl = PartAMaterialFactory.getRemoteInstance().getPartAMaterialCollection(view);
		if (partAMaterialColl != null && partAMaterialColl.size() > 0) {
			MsgBox.showWarning(this, PartAUtils.getRes("existPartABill"));//�ú�ͬ�Ѿ����ڲ�����ϸ������������
			SysUtil.abort();
		}
		
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select hasSettled where id = '" + billId + "'");
		if(contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, PartAUtils.getRes("hasSettled"));//��ͬ�ѽ��㣬��������������ϸ��!
			SysUtil.abort();
		}
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());
    	String billId = getSelectedKeyValue(getMainTable());
		PartAMaterialCollection partAMaterialColl = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billId));
		view.setFilter(filter);
		
		partAMaterialColl = PartAMaterialFactory.getRemoteInstance().getPartAMaterialCollection(view);
		
		if (partAMaterialColl.size() < 1) {
			MsgBox.showWarning(this, PartAUtils.getRes("shouldAddNew"));//"�ú�ͬ����û�в�����ϸ������������");
			SysUtil.abort();
		}
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());
    	String billId = getSelectedKeyValue(getMainTable());
		PartAMaterialCollection partAMaterialColl = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billId));
		view.setFilter(filter);
		
		partAMaterialColl = PartAMaterialFactory.getRemoteInstance().getPartAMaterialCollection(view);
		
		if (partAMaterialColl.size() < 1) {
			MsgBox.showWarning(this, PartAUtils.getRes("shouldAddNew"));
			SysUtil.abort();
		}
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select hasSettled where id = '" + billId + "'");
		if(contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, PartAUtils.getRes("hasSettled"));
			SysUtil.abort();
		}
        super.actionEdit_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	// TODO �Զ����ɷ������
    	checkSelected(getMainTable());
    	String billId = getSelectedKeyValue(getMainTable());
    	ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select hasSettled where id = '" + billId + "'");
		if(contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, PartAUtils.getRes("hasSettled"));
			SysUtil.abort();
		}
    	super.actionRemove_actionPerformed(e);
    }
    /**
	 * 
	 * ����������Զ�̽ӿڣ��������ʵ�֣�
	 * @return
	 * @throws BOSException
	 * ����ʱ�䣺2008-7-21 <p>
	 */
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return PartAMaterialFactory.getRemoteInstance();
	}

	/**
	 * 
	 * ���������ͨ�����������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * @param ids
	 * @throws Exception
	 * ����ʱ�䣺2008-7-21 <p>
	 */
	protected void audit(List ids) throws Exception {
		String conId = getSelectedKeyValue(getMainTable());
		
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select state, hasSettled where id = '" + conId + "'");
		
		if(contractBillInfo.getState() == FDCBillStateEnum.CANCEL) {
			MsgBox.showWarning(this, PartAUtils.getRes("hasSettled"));
			SysUtil.abort();
		}
		
		if(contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, PartAUtils.getRes("hasSettled"));
			SysUtil.abort();
		}
		PartAMaterialFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * ����������ˣ��������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * @param ids
	 * @throws Exception
	 * ����ʱ�䣺2008-7-21 <p>
	 */
	protected void unAudit(List ids) throws Exception {
		PartAMaterialFactory.getRemoteInstance().unAudit(ids);
	}

	/**
	 * @return ��¼���
	 */
	protected KDTable getBillListTable() {
		return this.tblMaterial;
	}

	protected boolean isFootVisible() {
		return false;
	}
	
	/**
	 * @return ����
	 */
	protected String getEditUIName() {
		return PartAMaterialEditUI.class.getName();
	}
	
	/**
	 * ����������Զ�̽ӿ�ʵ��
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return PartAMaterialFactory.getRemoteInstance();
	}
	
	/**
	 * ��������ʾ����
	 */
	public void onShow() throws Exception{
		super.onShow();
		//ֻ�ܷ�������,��Ч
		this.actionAuditResult.setVisible(false);
		this.actionAuditResult.setVisible(true);
		this.actionWorkFlowG.setVisible(true);
		this.actionAudit.setVisible(true);
		this.actionUnAudit.setVisible(true);
	}

	/**
	 * @return selectors
	 */
	protected SelectorItemCollection genBillQuerySelector() {		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("creator.name");
		selectors.add("auditor.name");
		selectors.add("*");
		return selectors;
	}
		
	//����������ť
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemRevision.setAccelerator(KeyStroke.getKeyStroke("ctrl shift R"));
	}
	
	/**
	 * Ҫ��ʾ�ĺ�ͬ��״̬����,���ڹ��˺�ͬ
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set = super.getContractBillStateSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		set.add(FDCBillStateEnum.SAVED_VALUE);
		set.add(FDCBillStateEnum.SUBMITTED_VALUE);
		set.add(FDCBillStateEnum.AUDITTING_VALUE);
		return set;
	}
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = super.getTreeSelectChangeFilter();
		filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon",Boolean.TRUE));
		return filter;
	}
	
	/**
	 * ���ض�λ�ֶεļ���<p>
	 * ���Ӷ�λ�ֶΣ�״̬����ͬ���γɷ�ʽ����ͬ���ʡ� Modified By Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "state", "partB.name", 
				"contractType.name", "signDate", "amount", "contractSource", "contractPropert"};
	}
}