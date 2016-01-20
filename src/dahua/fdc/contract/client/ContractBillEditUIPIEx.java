package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.minghua.contract.ZcbhtDocFactory;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

public class ContractBillEditUIPIEx extends ContractBillEditUI{
	
	private ContractTypeInfo typeInfo;
	private boolean isShiGong = false;
	private boolean isFenBao = false;
	private boolean isZongBao = false;
	
	public ContractBillEditUIPIEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		prmtlandDeveloper.removeSelectorListener(prmtlandDeveloper.getSelectorListeners()[0]);
		prmtlandDeveloper.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				FilterInfo filter = new FilterInfo();
				String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber + "%", CompareType.LIKE));
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				EntityViewInfo ev = new EntityViewInfo();
				ev.setFilter(filter);
				f7.setEntityViewInfo(ev);
			}
		});
		//�༭״̬�£�ʩ����ͬ�����������ֶβ����޸�
		
		typeInfo = (ContractTypeInfo)prmtcontractType.getValue();
		
		if(typeInfo != null ) {
			if("[ʩ��]".equals(typeInfo.getName())) {
				isShiGong = true;
				isFenBao = true;
			}else if("[����]".equals(typeInfo.getName()) || "[�ְ�]".equals(typeInfo.getName())) {
				isFenBao = true;
			}else if("[����]".equals(typeInfo.getName())) {
				isZongBao = true;
			}
		}
		if(isShiGong) {
			tblDetail.getCell(0,"content").getStyleAttributes().setLocked(true);
			tblDetail.getColumn("desc").setRequired(true);
			if("ADDNEW".equals(getOprtState())){
				tblDetail.getCell(0,"content").setValue("�������ۣ�ԭ�ҽ���Ԫ");
				IRow row1 = tblDetail.addRow(1);
				IRow row2 = tblDetail.addRow(2);
				IRow row3 = tblDetail.addRow(3);
				row1.getCell("detail").setValue("��ע2");
				row2.getCell("detail").setValue("��ע3");
				row3.getCell("detail").setValue("��ע4");
				row1.getCell("content").setValue("�ֽ�Ԥ���������֣�");
				row2.getCell("content").setValue("��Ʒ��Ԥ�������������ף�");
				row3.getCell("content").setValue("��Ʒɰ��Ԥ�����������");
			}
		}
		if(isFenBao) {
			prmtlandDeveloper.setEnabled(false);
			prmtcontractType.setEnabled(false);
			tblDetail.addKDTEditListener(new KDTEditAdapter(){
				public void editStopped(KDTEditEvent e) {
					tblDetail_editStopped(e);
				}
			});
		}
		typeAndPartbState();
	}

	private void typeAndPartbState() throws Exception {
		if(isZongBao) {
			prmtcontractType.setEnabled(false);
			if("EDIT".equals(getOprtState())) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getCurProject().getId()));
				filter.getFilterItems().add(new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[ʩ��]"));
				filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[����]"));
				filter.getFilterItems().add(new FilterItemInfo("contractType.name", "[�ְ�]"));
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				SelectorItemCollection sic = view.getSelector();
				sic.add("id");
				if(ContractBillFactory.getRemoteInstance().getContractBillCollection(view).size() > 0) {
					prmtpartB.setEnabled(false);
				}else if(CustomerContractUtil.currentDCenterIsSync()){
					try {
						ZcbhtDocFactory.getRemoteInstance().getZcbhtDocInfo("select id where sourcebillid='"+editData.getId().toString()+"'");
						prmtpartB.setEnabled(false);
					} catch (ObjectNotFoundException e) {
					}
				}
			}
		}
	}
	
	public void onShow() throws Exception {
		super.onShow();
	}
	
	protected void contractPropert_itemStateChanged(ItemEvent e) throws Exception {
		super.contractPropert_itemStateChanged(e);
		if(e.getStateChange() == 1 && (isFenBao || isZongBao)) {
			if(ContractPropertyEnum.SUPPLY.equals(contractPropert.getSelectedItem())) {
				if(isFenBao) {
					prmtpartB.setEnabled(false);
				}
//				for(int i=0; i<tblDetail.getRowCount(); i++) {
//					if("�Ƿ񵥶�����".equals(tblDetail.getCell(i,"detail").getValue())) {
//						tblDetail.getCell(i,"content").setValue(BooleanEnum.FALSE);
//						//tblDetail.getCell(i,"content").getStyleAttributes().setLocked(true);
//						break;
//					}
//				}
			}else if(ContractPropertyEnum.DIRECT.equals(contractPropert.getSelectedItem())){
				prmtpartB.setEnabled(true);
			}else {
				prmtpartB.setEnabled(true);
			}
		}
	}
	
	private void initDataInAddNew(IObjectValue ov) throws Exception {
		BOSUuid typeId = (BOSUuid)getUIContext().get("contractTypeId");
		if(typeId != null) {
			typeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(typeId));
			String typeName = null;
			if(typeInfo != null)
				typeName = typeInfo.getName();
			if("[ʩ��]".equals(typeName) || "[����]".equals(typeName) || "[�ְ�]".equals(typeName)) {
				BOSUuid proId = (BOSUuid)getUIContext().get("projectId");
				String billId = CustomerContractUtil.getIdByProAndType(proId.toString());
				if(billId != null){
					ContractBillInfo billInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select number,partB.name where id='"+billId+"'");
					String supplier = billInfo.getPartB().getName();
					LandDeveloperInfo land = null;
					try {
						land = LandDeveloperFactory.getRemoteInstance().getLandDeveloperInfo("where name='"+supplier+"' ");
					} catch (Exception e) {
						MsgBox.showInfo("���ڼ׷��������̣�����������ά����"+supplier);
						SysUtil.abort();
					}
					((ContractBillInfo)ov).setLandDeveloper(land);
					prmtlandDeveloper.setEnabled(false);
					
				}
				prmtcontractType.setEnabled(false);
			}else if("[����]".equals(typeName)) {
				prmtcontractType.setEnabled(false);
			}
		}
		
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(CustomerContractUtil.currentDCenterIsSync()) {
			List ids = new ArrayList();
			ids.add(editData.getId().toString());
			CustomerContractUtil.syncData(ids);
		}
		super.actionAudit_actionPerformed(e);
		//BOSUuid proId = (BOSUuid)getUIContext().get("projectId");
		//BOSUuid typeId = (BOSUuid)getUIContext().get("contractTypeId");
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(isShiGong) {
			for(int i=0;i<4;i++){
				String desc = (String)tblDetail.getCell(i,"desc").getValue();
				String content = (String)tblDetail.getCell(i,"content").getValue();
				if(desc == null || "".equals(desc)) {
					if(ContractPropertyEnum.DIRECT.equals(contractPropert.getSelectedItem()))
						showErrorInformation(i,content);
				}else {
					int index = desc.indexOf(".");
					if(index == -1) {
						if(!desc.matches("^[0-9]*$")){
							showErrorInformation(i,content);
						}
					}
					else if(index == desc.length()-1){
						showErrorInformation(i,content);
					}
					else {
						String leftstr = desc.substring(0,index);
						if(!leftstr.matches("^[0-9]*$")) {
							showErrorInformation(i,content);
						}
						String rightstr = desc.substring(index+1);
						if(rightstr.indexOf(".") != -1 || !rightstr.matches("^[0-9]*$")) {
							showErrorInformation(i,content);
						}
					}
				}
			}
		}
		if(typeInfo != null && "[����]".equals(typeInfo.getName())) {
			if(contractPropert.getSelectedItem().equals(ContractPropertyEnum.DIRECT)) {
				if(prmtpartB.getValue() == null) {
					MsgBox.showWarning("�ҷ�����Ϊ�գ�");
					prmtpartB.grabFocus();
					SysUtil.abort();
				}
				BOSUuid proId = null;
				if("ADDNEW".equals(getOprtState())) {
					proId = (BOSUuid)getUIContext().get("projectId");
					if(CustomerContractUtil.getIdByProAndType(proId.toString()) != null ) {
						MsgBox.showInfo("һ����Ŀ��Ӧһ���ܰ���ͬ���뷵�غ�ͬ¼���������ѡ����Ŀ���ߺ�ͬ���ͣ�");
						SysUtil.abort();
					}
					
				}
				else if("EDIT".equals(getOprtState())) {
					proId = editData.getCurProject().getId();
					String mainId = CustomerContractUtil.getIdByProAndType(proId.toString());
					if(mainId != null && !mainId.equals(editData.getId().toString())) {
						MsgBox.showInfo("һ����Ŀ��Ӧһ���ܰ���ͬ��������ѡ���ͬ���ʣ�");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	protected void tblDetail_editStopped(KDTEditEvent evt) {
		if(evt.getOldValue() != null && evt.getOldValue().equals(evt.getValue())) {
			return;
		}
		if(evt.getValue() == null) {
			return;
		}
		String detail = (String)tblDetail.getCell(evt.getRowIndex(),"detail").getValue();
		if(("��ע".equals(detail) || "��ע2".equals(detail) || "��ע3".equals(detail) || "��ע4".equals(detail)) 
				&& evt.getColIndex() == tblDetail.getColumnIndex("desc")) {
			if(isShiGong)
				tblDetail_desc(evt.getRowIndex());
		}
		if("��Ӧ����ͬ����".equals(detail) && evt.getColIndex() == tblDetail.getColumnIndex("content")) {
			ContractBillInfo cbinfo = (ContractBillInfo)evt.getValue();
			try {
				prmtpartB.setValue(SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(cbinfo.getPartB().getId())));
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			}
		}
	}

	private void tblDetail_desc(int rowIndex) {
		String desc = (String)tblDetail.getCell(rowIndex,"desc").getValue();
		String content = (String)tblDetail.getCell(rowIndex,"content").getValue();
		boolean flag = false;
		if(desc != null && !"".equals(desc)) {
			int index = desc.indexOf(".");
			if(index == -1) {
				if(!desc.matches("^[0-9]*$")){
					flag = true;
				}
			}
			else if(index == desc.length()-1){
				flag = true;
			}
			else {
				String leftstr = desc.substring(0,index);
				if(!leftstr.matches("^[0-9]*$")) {
					flag = true;
				}
				String rightstr = desc.substring(index+1);
				if(rightstr.indexOf(".") != -1 || !rightstr.matches("^[0-9]*$")) {
					flag = true;
				}
			}
			if(flag) {
				MsgBox.showInfo(desc+"��ʽ������������д��");
				tblDetail.getSelectManager().select(rowIndex,tblDetail.getColumnIndex("desc"),rowIndex,tblDetail.getColumnIndex("desc"));
				return;
			}
//			tblDetail.getCell(rowIndex,"content").setValue("�������ۣ�ԭ�ҽ���"+FDCClientHelper.getChineseFormat(new BigDecimal(desc), false));
		}else {
			tblDetail.getCell(rowIndex,"content").setValue(content);
		}
	}
	
	private void showErrorInformation(int i ,String des) {
		MsgBox.showInfo(des+"��ʽ������������д��");
		tblDetail.getSelectManager().select(i,tblDetail.getColumnIndex("desc"),i,tblDetail.getColumnIndex("desc"));
		SysUtil.abort();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		optionNewInit();
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		boolean isFirstSave = false;
		
		if("ADDNEW".equals(getOprtState()) && isFenBao ) {
			isFirstSave = true;
		}
		super.actionSave_actionPerformed(e);
		if(isFirstSave) {
			String billId = CustomerContractUtil.getIdByProAndType(editData.getCurProject().getId().toString());
			if(billId != null) {
				ContractBillInfo billInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select number,partB.name where id='"+billId+"'");
				txtNumber.setText(billInfo.getNumber()+editData.getContractType().getName().substring(1,3)+"000");
			}
		}
		if(isZongBao || isFenBao ) {
			prmtcontractType.setEnabled(false);
		}
		
	}
	
	public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
		
		if("ADDNEW".equals(getOprtState()) && isFenBao) {
			if(prmtpartB.getValue() == null) {
				MsgBox.showWarning("�ҷ�����Ϊ�գ�");
				prmtpartB.grabFocus();
				SysUtil.abort();
			}
			if(prmtRespDept.getValue() == null) {
				MsgBox.showWarning("���β��Ų���Ϊ�գ�");
				prmtRespDept.grabFocus();
				SysUtil.abort();
			}
			if(prmtRespPerson.getValue() == null) {
				MsgBox.showWarning("�����˲���Ϊ�գ�");
				prmtRespPerson.grabFocus();
				SysUtil.abort();
			}
//			super.actionSave_actionPerformed(arg0);
			String billId = CustomerContractUtil.getIdByProAndType(editData.getCurProject().getId().toString());
			if(billId != null) {
				ContractBillInfo billInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select number,partB.name where id='"+billId+"'");
				txtNumber.setText(billInfo.getNumber()+editData.getContractType().getName().substring(1,3)+"000");
			}
		}
		super.actionSubmit_actionPerformed(arg0);
		optionNewInit();
		
	}

	private void optionNewInit() throws Exception {
		if(isShiGong && tblDetail.getRowCount() > 0) {
			tblDetail.getCell(0,"content").getStyleAttributes().setLocked(true);
			tblDetail.getColumn("desc").setRequired(true);
			tblDetail.getCell(0,"content").setValue("�������ۣ�ԭ�ҽ���Ԫ");
		}
		if(isZongBao || isFenBao ) {
			prmtcontractType.setEnabled(false);
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);
		if(isFenBao) {
			prmtlandDeveloper.setEnabled(false);
			prmtcontractType.setEnabled(false);
			if(isShiGong) {
				tblDetail.getCell(0,"content").getStyleAttributes().setLocked(true);
				tblDetail.getColumn("desc").setRequired(true);
			}
		}
		typeAndPartbState();
	}
	
	public void storeFields() {
		super.storeFields();
	}
	
	protected IObjectValue createNewData() {
		IObjectValue ov = super.createNewData();
		ov.getBOSType();
		try {
			initDataInAddNew(ov);
		} catch (Exception e) {
			handUIException(e);
		}
		return ov;
	}
	
	
}
