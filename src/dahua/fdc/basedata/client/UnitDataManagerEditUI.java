/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.f7.CompanyBizUnitF7;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.BusinessTypeEnum;
import com.kingdee.eas.fdc.basedata.ControlTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DirectorCollection;
import com.kingdee.eas.fdc.basedata.DirectorInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.GetTypeEnum;
import com.kingdee.eas.fdc.basedata.InnerManagerInfo;
import com.kingdee.eas.fdc.basedata.LicenceManagerInfo;
import com.kingdee.eas.fdc.basedata.ManagerProjectEntryCollection;
import com.kingdee.eas.fdc.basedata.ManagerProjectEntryInfo;
import com.kingdee.eas.fdc.basedata.ObjectValueRenderImpl;
import com.kingdee.eas.fdc.basedata.RightManagerCollection;
import com.kingdee.eas.fdc.basedata.RightManagerInfo;
import com.kingdee.eas.fdc.basedata.SelfDirectorCollection;
import com.kingdee.eas.fdc.basedata.SelfDirectorInfo;
import com.kingdee.eas.fdc.basedata.TaxManagerCollection;
import com.kingdee.eas.fdc.basedata.TaxManagerInfo;
import com.kingdee.eas.fdc.basedata.TaxPayerTypeEnum;
import com.kingdee.eas.fdc.basedata.UnitDataManagerFactory;
import com.kingdee.eas.fdc.basedata.UnitDataManagerInfo;
import com.kingdee.eas.fdc.basedata.WatcherCollection;
import com.kingdee.eas.fdc.basedata.WatcherInfo;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:组织数据管理
 * @author jackwang  date:2007-5-17 <p>
 * @version EAS5.3
 */
public class UnitDataManagerEditUI extends AbstractUnitDataManagerEditUI {
	private static final Logger logger = CoreUIObject.getLogger(UnitDataManagerEditUI.class);

	/**
	 * output class constructor
	 */
	public UnitDataManagerEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
		this.btnAddNew.setVisible(false);
		if (STATUS_ADDNEW.equals(this.getOprtState())) {
			//			if (SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion())) {
			//				this.bizCompany.setValue(SysContext.getSysContext().getCurrentFIUnit());
			//				this.bizCompany.setEnabled(false);
			//			}else{
			//				
			//			}
		} else {
			loadDatas();
		}
		ftxtRegisterCapital.setEditable(false);
		ftxtRegisterCapital.setEnabled(false);
		ftxtRealCapital.setEnabled(false);
		ftxtRealCapital.setEditable(false);
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.UNITDATAMANAGER));
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {

		if (STATUS_EDIT.equals(this.getOprtState())) {
			if (this.editData.getLicenceManager() != null)
				this.editData.put("lmiNumber", this.editData.getLicenceManager().getId());
			if (this.editData.getInnerManager() != null)
				this.editData.put("imiNumber", this.editData.getInnerManager().getId());
		}
		//第二页
		//营业执照信息
		LicenceManagerInfo lmi = new LicenceManagerInfo();
		//		UIHelper.storeMultiLangFields(this.txtLicenseNumber, lmi, "licenseNumber");
		if (this.txtLicenseNumber.getSelectedItem() != null)
			lmi.setLicenseNumber(this.txtLicenseNumber.getSelectedItem().toString());
		//		UIHelper.storeMultiLangFields(this.txtBusinessAddress, lmi, "businessAddress");
		if (this.txtBusinessAddress.getSelectedItem() != null)
			lmi.setBusinessAddress(this.txtBusinessAddress.getSelectedItem().toString());
		lmi.setRegisterCapital(this.ftxtRegisterCapital.getBigDecimalValue());
		lmi.setRealCapital(this.ftxtRealCapital.getBigDecimalValue());
		lmi.setBusinessDateFrom(this.dateBusinessFrom.getSqlDate());
		lmi.setBusinessDateTo(this.dateBusinessTo.getSqlDate());
		lmi.setRegisterDate(this.dateRegister.getSqlDate());
		lmi.setBuildDate(this.dateBuild.getSqlDate());
		this.editData.setLicenceManager(lmi);
		//税务登记信息		
		this.editData.getTaxManager().clear();
		for (int i = 0; i < this.kdtTaxManager.getRowCount(); i++) {
			if (this.kdtTaxManager.getRow(i).getCell("bank").getValue() != null) {
				TaxManagerInfo tmi = new TaxManagerInfo();
				//			UIHelper.storeMultiLangFields(this.txtCountryTaxNum, tmi, "countryTaxNum");
				if (this.txtCountryTaxNum.getSelectedItem() != null)
					tmi.setCountryTaxNum(this.txtCountryTaxNum.getSelectedItem().toString());
				//			UIHelper.storeMultiLangFields(this.txtAreaTaxNum, tmi, "areaTaxNum");
				if (this.txtBank.getSelectedItem() != null)
					tmi.setAccountBank(this.txtBank.getSelectedItem().toString());
				if (this.txtBankNum.getSelectedItem() != null)
					tmi.setBankNum(this.txtBankNum.getSelectedItem().toString());
				if (this.txtAreaTaxNum.getSelectedItem() != null)
					tmi.setAreaTaxNum(this.txtAreaTaxNum.getSelectedItem().toString());
				tmi.setTaxPayerType((TaxPayerTypeEnum) this.boxTaxPayerType.getSelectedItem());
				tmi.setBank((AccountBankInfo) (this.kdtTaxManager.getRow(i).getCell("bank").getValue()));
				tmi.setParent(this.editData);
				this.editData.getTaxManager().add(tmi);
			}
		}
		//第三页
		//内部管理信息
		InnerManagerInfo imi = new InnerManagerInfo();
		if (this.bizOwnCompany.getValue() != null) {
			imi.setOwnCompany((CompanyOrgUnitInfo) this.bizOwnCompany.getValue());
		}
		if (this.boxControlType.getSelectedItem() != null) {
			imi.setControlType((ControlTypeEnum) this.boxControlType.getSelectedItem());
		}
		if (this.boxBusinessType.getSelectedItem() != null) {
			imi.setBusinessType((BusinessTypeEnum) this.boxBusinessType.getSelectedItem());
		}
		if (this.boxGetType.getSelectedItem() != null) {
			imi.setGetType((GetTypeEnum) this.boxGetType.getSelectedItem());
		}
		imi.setHasBuildingProject(this.chkHasBuildingProject.isSelected());
		imi.setHasUnit(this.chkHasUnit.isSelected());
		//			UIHelper.storeMultiLangFields(this.bizManagerProject, imi, "managerProject");
		
		ManagerProjectEntryCollection entrys = imi.getManagerProjectEntry();
		for(int i=0;i<entrys.size();i++){
			entrys.removeObject(i);
		}
		for (int i = 0; i < lstManagerProject.getElementCount(); i++) {
			Object element = lstManagerProject.getElement(i);
			if(element instanceof ManagerProjectEntryInfo){
				entrys.add((ManagerProjectEntryInfo)element);
			}
		}
//			imi.setManagerProject(this.bizManagerProject.getSelectedItem().toString());
		imi.setSetupDate(dateOwnCompanyBuild.getTimestamp());
		this.editData.setInnerManager(imi);
		//人员管理信息
		//董事
		this.editData.getDirector().clear();
		for (int i = 0; i < this.kdtDirector.getRowCount(); i++) {
			if (kdtDirector.getRow(i).getCell("name").getValue() != null && (!"".equals(kdtDirector.getRow(i).getCell("name").getValue().toString()))) {
				DirectorInfo di = new DirectorInfo();
				di.setName(kdtDirector.getRow(i).getCell("name").getValue().toString());
				di.setParent(this.editData);
				this.editData.getDirector().add(di);
			}
		}
		//独立董事
		this.editData.getSelfDirector().clear();
		for (int i = 0; i < this.kdtSelfDirector.getRowCount(); i++) {
			if (kdtSelfDirector.getRow(i).getCell("name").getValue() != null && (!"".equals(kdtSelfDirector.getRow(i).getCell("name").getValue().toString().trim()))) {
				SelfDirectorInfo sdi = new SelfDirectorInfo();
				sdi.setName(kdtSelfDirector.getRow(i).getCell("name").getValue().toString());
				sdi.setParent(this.editData);
				this.editData.getSelfDirector().add(sdi);
			}
		}
		//监事
		this.editData.getWatcher().clear();
		for (int i = 0; i < this.kdtWatcher.getRowCount(); i++) {
			if (kdtWatcher.getRow(i).getCell("name").getValue() != null && (!"".equals(kdtWatcher.getRow(i).getCell("name").getValue().toString()))) {
				WatcherInfo wi = new WatcherInfo();
				wi.setName(kdtWatcher.getRow(i).getCell("name").getValue().toString());
				wi.setParent(this.editData);
				this.editData.getWatcher().add(wi);
			}
		}
		//第四页
		this.editData.getRightManager().clear();
		for (int i = 0; i < this.kdtRightManager.getRowCount(); i++) {
			RightManagerInfo rmi = new RightManagerInfo();
			Boolean isInner = (Boolean) this.kdtRightManager.getRow(i).getCell("isInner").getValue();
			rmi.setIsInnerUnit(isInner.booleanValue());
			if (isInner.booleanValue()) {
				CompanyOrgUnitInfo company = ((CompanyOrgUnitInfo) this.kdtRightManager.getRow(i).getCell("rightCompany").getValue());
				rmi.setRightCompany(company);
			} else {
				if (this.kdtRightManager.getRow(i).getCell("rightCompany").getValue() != null) {
					rmi.setRightCompanyDefine(this.kdtRightManager.getRow(i).getCell("rightCompany").getValue().toString());
				}
			}
			if(rmi.getRightCompany()!=null||rmi.getRightCompanyDefine()!=null){
				if (this.kdtRightManager.getRow(i).getCell("rightScale").getValue() != null) {
					rmi.setRightScale(new BigDecimal(this.kdtRightManager.getRow(i).getCell("rightScale").getValue().toString()));
				}
				if (this.kdtRightManager.getRow(i).getCell("auditType").getValue() != null) {
					rmi.setAuditType(this.kdtRightManager.getRow(i).getCell("auditType").getValue().toString());
				}
				rmi.setDateFrom((Date) this.kdtRightManager.getRow(i).getCell("dateFrom").getValue());
				rmi.setDateTo((Date) this.kdtRightManager.getRow(i).getCell("dateTo").getValue());
				if (this.kdtRightManager.getRow(i).getCell("man").getValue() != null) {
					rmi.setMan(this.kdtRightManager.getRow(i).getCell("man").getValue().toString());
				}
				if (this.kdtRightManager.getRow(i).getCell("holdScale").getValue() != null) {
					rmi.setHoldScale(new BigDecimal(this.kdtRightManager.getRow(i).getCell("holdScale").getValue().toString()));
				}
				if (this.kdtRightManager.getRow(i).getCell("amt").getValue() != null) {
					rmi.setAmt(new BigDecimal(this.kdtRightManager.getRow(i).getCell("amt").getValue().toString()));
				}
				rmi.setParent(this.editData);
				this.editData.getRightManager().add(rmi);
			}
		}

	}

	private void beforeStoreFields() {
	}

	private void loadDatas() {
		kdtTaxManager.checkParsed();
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);//数字格式
		ObjectValueRenderImpl avr = new ObjectValueRenderImpl();//显示格式
		avr.setFormat(new BizDataFormat("name"));
		KDTextField textField = new KDTextField();//设置长度
		textField.setMaxLength(255);
		//营业执照信息
		LicenceManagerInfo lmi = this.editData.getLicenceManager();
		if (lmi != null) {
			this.txtLicenseNumber.setSelectedItemData(lmi.getLicenseNumber());
			this.txtBusinessAddress.setSelectedItemData(lmi.getBusinessAddress());
			this.ftxtRealCapital.setValue(lmi.getRealCapital());
			this.ftxtRegisterCapital.setValue(lmi.getRegisterCapital());
			this.dateRegister.setValue(lmi.getRegisterDate());
			this.dateBuild.setValue(lmi.getBuildDate());
			this.dateBusinessFrom.setValue(lmi.getBusinessDateFrom());
			this.dateBusinessTo.setValue(lmi.getBusinessDateTo());
		}
		//税务信息
		TaxManagerCollection tmc = this.editData.getTaxManager();
		if (tmc != null && tmc.size() != 0) {
			this.txtCountryTaxNum.setSelectedItemData(tmc.get(0).getCountryTaxNum());
			this.txtAreaTaxNum.setSelectedItemData(tmc.get(0).getAreaTaxNum());
			this.txtBank.setSelectedItemData(tmc.get(0).getAccountBank());
			this.txtBankNum.setSelectedItemData(tmc.get(0).getBankNum());
			this.boxTaxPayerType.setSelectedItem(tmc.get(0).getTaxPayerType());
			this.kdtTaxManager.removeRows();

			//			 将render绑定到列上（也可绑定到行或单元格上）
			//			kdtTaxManager.getColumn("bank").setRenderer(avr);			//格式
			kdtTaxManager.getColumn("bankNumber").setEditor(new KDTDefaultCellEditor(textField));//长度
			kdtTaxManager.getColumn("bankNumber").getStyleAttributes().setLocked(true);
			IRow row;
			for (int i = 0; i < tmc.size(); i++) {
				row = this.kdtTaxManager.addRow();
				//				ICell cellValueAttribute = row.getCell("bank");
				//				cellValueAttribute.setValue(null);
				//				JTextField txtEnable = new JTextField();
				//				KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
				//				ceEnable = new KDTDefaultCellEditor(createBankF7());
				//				cellValueAttribute.setEditor(ceEnable);
				// 设置工程项目f7cell
				ICell cellbank = row.getCell("bank");
				cellbank.getStyleAttributes().setLocked(false);
				cellbank.setValue(tmc.get(i).getBank());
				JTextField txtEnable1 = new JTextField();
				KDTDefaultCellEditor ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
				ceEnable1 = new KDTDefaultCellEditor(createBankF7());
				cellbank.setEditor(ceEnable1);
				row.getCell("bankNumber").setValue(tmc.get(i).getBank().getBankAccountNumber());
			}
		}
		//内部管理信息
		InnerManagerInfo imi = this.editData.getInnerManager();
		if (imi != null) {
			this.bizOwnCompany.setValue(imi.getOwnCompany());
			this.boxBusinessType.setSelectedItem(imi.getBusinessType());
			this.boxControlType.setSelectedItem(imi.getControlType());
			this.boxGetType.setSelectedItem(imi.getGetType());
			if(imi.getOwnCompany()==null||imi.getOwnCompany().getSetupDate()==null){
				this.dateOwnCompanyBuild.setValue(imi.getSetupDate());
			}else{
				this.dateOwnCompanyBuild.setValue(imi.getOwnCompany().getSetupDate());
			}
			
			ManagerProjectEntryCollection entrys = imi.getManagerProjectEntry();
			for(int i=0;i<entrys.size();i++){
				lstManagerProject.addElement(entrys.get(i));
			}
		
//			this.bizManagerProject.setSelectedItemData(imi.getManagerProject());
			this.chkHasBuildingProject.setSelected(imi.isHasBuildingProject());
			this.chkHasUnit.setSelected(imi.isHasUnit());
		}
		//人员管理信息
		//董事
		DirectorCollection dc = this.editData.getDirector();
		IRow row;
		this.kdtDirector.removeRows();
		kdtDirector.checkParsed();
		kdtDirector.getColumn("name").setEditor(new KDTDefaultCellEditor(textField));
		for (int i = 0; i < dc.size(); i++) {
			row = this.kdtDirector.addRow();
			row.getCell("name").setValue(dc.get(i).getName());
		}
		//独立董事
		SelfDirectorCollection sdc = this.editData.getSelfDirector();
		this.kdtSelfDirector.removeRows();
		kdtSelfDirector.checkParsed();
		kdtSelfDirector.getColumn("name").setEditor(new KDTDefaultCellEditor(textField));
		for (int i = 0; i < sdc.size(); i++) {
			row = this.kdtSelfDirector.addRow();
			row.getCell("name").setValue(sdc.get(i).getName());
		}
		//监事
		WatcherCollection wc = this.editData.getWatcher();
		this.kdtWatcher.removeRows();
		kdtWatcher.checkParsed();
		kdtWatcher.getColumn("name").setEditor(new KDTDefaultCellEditor(textField));
		for (int i = 0; i < wc.size(); i++) {
			row = this.kdtWatcher.addRow();
			row.getCell("name").setValue(wc.get(i).getName());
		}
		//权益管理信息
		RightManagerCollection rmc = this.editData.getRightManager();
		this.kdtRightManager.checkParsed();
		this.kdtRightManager.removeRows();
		kdtRightManager.getColumn("rightScale").setEditor(numberEditor);
		kdtRightManager.getColumn("holdScale").setEditor(numberEditor);
		kdtRightManager.getColumn("amt").setEditor(numberEditor);
		//		kdtRightManager.getColumn("rightCompany").setRenderer(avr);
		FDCHelper.formatTableNumber(kdtRightManager, "rightScale");
		FDCHelper.formatTableNumber(kdtRightManager, "holdScale");
		FDCHelper.formatTableNumber(kdtRightManager, "amt");
		KDDatePicker datePicker = new KDDatePicker();//
		datePicker.setDateEnabled(true);
		datePicker.setValue(null);
		KDTDefaultCellEditor kdtDefaultCellEditor = new KDTDefaultCellEditor(datePicker);
		String format = "yyyy-MM-dd";
		this.kdtRightManager.getColumn("dateFrom").setEditor(kdtDefaultCellEditor);
		this.kdtRightManager.getColumn("dateTo").setEditor(kdtDefaultCellEditor);
		this.kdtRightManager.getColumn("dateFrom").getStyleAttributes().setNumberFormat(format);
		this.kdtRightManager.getColumn("dateTo").getStyleAttributes().setNumberFormat(format);
		for (int i = 0; i < rmc.size(); i++) {
			row = this.kdtRightManager.addRow();
			row.getCell("isInner").setValue(Boolean.valueOf(rmc.get(i).isIsInnerUnit()));
			if (rmc.get(i).getRightCompany() != null) {
				row.getCell("rightCompany").setValue(rmc.get(i).getRightCompany());
				JTextField txtEnable1 = new JTextField();
				KDTDefaultCellEditor ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
				ceEnable1 = new KDTDefaultCellEditor(createCompanyF7());
				row.getCell("rightCompany").setEditor(ceEnable1);
			} else if (rmc.get(i).getRightCompanyDefine() != null) {
				row.getCell("rightCompany").setValue(rmc.get(i).getRightCompanyDefine());
			}
			row.getCell("rightScale").setValue(rmc.get(i).getRightScale());
			row.getCell("auditType").setValue(rmc.get(i).getAuditType());
			row.getCell("dateFrom").setValue(rmc.get(i).getDateFrom());
			row.getCell("dateTo").setValue(rmc.get(i).getDateTo());
			row.getCell("man").setValue(rmc.get(i).getMan());
			row.getCell("holdScale").setValue(rmc.get(i).getHoldScale());
			row.getCell("amt").setValue(rmc.get(i).getAmt());
		}
		this.editData.isEmpty();
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		if (this.bizCompany.getValue() == null) {
			bizCompany.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.UNITDATAMANGER_COMPANY_ISNULL);
		} else {
			if (STATUS_ADDNEW.equals(this.getOprtState())) {
				CompanyOrgUnitInfo coui = (CompanyOrgUnitInfo) this.bizCompany.getValue();
				if (UnitDataManagerFactory.getRemoteInstance().exists("select id where company.id = '" + coui.getId().toString() + "'")) {
					MsgBox.showWarning(this, "系统中已经存在该公司的记录");
					SysUtil.abort();
				}
			}else if(STATUS_EDIT.equals(this.getOprtState())){
				CompanyOrgUnitInfo coui = (CompanyOrgUnitInfo) this.bizCompany.getValue();
				UnitDataManagerInfo udmi =UnitDataManagerFactory.getRemoteInstance().getUnitDataManagerInfo(
						"select id where company.id = '" + coui.getId().toString() + "'");
				if (udmi!=null&&(!udmi.getId().toString().equals(this.editData.getId().toString()))){
					MsgBox.showWarning(this, "系统中已经存在该公司的记录");
					SysUtil.abort();
				}
			}
		}
		//日期前后大小判断
		if (this.dateBusinessFrom.getValue() != null && this.dateBusinessTo.getValue() != null) {
			if (!((Date) this.dateBusinessTo.getValue()).after((Date) this.dateBusinessFrom.getValue())) {
				//				MsgBox.showWarning(this, EASResource.getString(resourcePath, "DateBoundErrer"));
				MsgBox.showWarning(this, "第二页营业期限起始日期设置错误");
				SysUtil.abort();
			}
		}
		for (int i = 0; i < this.kdtRightManager.getRowCount(); i++) {
			ArrayList al = new ArrayList();
			if (this.kdtRightManager.getRow(i).getCell("dateFrom").getValue() != null && this.kdtRightManager.getRow(i).getCell("dateTo").getValue() != null) {
				if (!((Date) this.kdtRightManager.getRow(i).getCell("dateTo").getValue()).after((Date) this.kdtRightManager.getRow(i).getCell("dateFrom").getValue())) {
					//					MsgBox.showWarning(this, EASResource.getString(resourcePath, "DateBoundErrer"));
					i += 1;
					MsgBox.showWarning(this, "第四页第" + i + "行权益公司起始日期设置错误");
					SysUtil.abort();
				}
			}
			//			if(al.size()!=0){
			//				MsgBox.showDetailAndOK(this, "详细信息列出了无法反分配的明细情况!", alOrgAndProject.toString(), 1);
			//			}
		}
		//		if (this.bizAddress.getValue()==null) {
		//			throw new FDCBasedataException(FDCBasedataException.UNITDATAMANGER_ADDRESS_ISNULL);
		//		}
		//		// 名称是否为空
		//		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		//		if (flag) {
		//			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		//		}
		oprtState = STATUS_EDIT;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		//    	this.beforeStoreFields();
		super.storeFields();
//		oprtState = STATUS_EDIT;
		
	}

	/**
	 * output bizAddress_dataChanged method
	 */
	//    protected void bizAddress_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
	//    {
	//        if(this.bizAddress.getValue()!=null){
	//        	AddressInfo address = (AddressInfo)this.bizAddress.getValue();
	//        	this.txtPostNumber.setText(address.getPostalCode());
	//        	this.txtFax.setText(address.getFax());
	//        	this.txtLinkMan.setText(address.getLinkMan());
	//        	this.txtLinkMobile.setText(address.getPhone());
	//        	this.txtTel.setText(address.getPhone());
	//        	this.txtWeb.setText(address.getEmailAddress());        	
	//        }
	//    }
	/**
	 * output bizCompany_dataChanged method
	 */
	protected void bizCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		if (this.bizCompany.getValue() instanceof CompanyOrgUnitInfo) {
			CompanyOrgUnitInfo company = (CompanyOrgUnitInfo) this.bizCompany.getValue();
			this.txtCompanyNumber.setText(company.getLongNumber().replace('!', '.'));
			FullOrgUnitInfo foui = company.castToFullOrgUnitInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("partFI.companyDescription"));
			foui = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(foui.getId().toString()),sic);
			if(foui.getPartFI().getCompanyDescription()!=null){				
				this.txtDescription.setSelectedItemData(foui.getPartFI().getCompanyDescription());
				txtDescription.setEditable(false);
			}else{
				txtDescription.setEditable(true);
			}
//			if (((CompanyOrgUnitInfo) this.bizCompany.getValue()).getAccountBank() != null) {
//				this.txtBank.setSelectedItem(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getAccountBank().getName());
//				this.txtBankNum.setSelectedItem(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getAccountBank().getBankAccountNumber());
			if(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getRegisteredCapital()!=null){
				ftxtRegisterCapital.setValue(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getRegisteredCapital());
				ftxtRegisterCapital.setEditable(false);
			}else{
				ftxtRegisterCapital.setEditable(true);
			}
			if(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getSetupDate()!=null){
				this.dateBuild.setValue(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getSetupDate());
				dateBuild.setEnabled(false);
			}else{
				dateBuild.setEnabled(true);
			}
			if(((CompanyOrgUnitInfo) this.bizCompany.getValue()).getJuridicalPerson()!=null){
				String id = ((CompanyOrgUnitInfo) this.bizCompany.getValue()).getJuridicalPerson().getId().toString();
				sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("name"));
				PersonInfo pi = PersonFactory.getRemoteInstance().getPersonInfo(new ObjectUuidPK(id),sic);
				this.txtArtificialPerson.setSelectedItemData(pi.getName());
				txtArtificialPerson.setEditable(false);
			}else{
				txtArtificialPerson.setEditable(true);
			}
//			}
			this.cou = (CompanyOrgUnitInfo) this.bizCompany.getValue();
		}
	}

	/**
	 * output bizOwnCompany_dataChanged method
	 */
	protected void bizOwnCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		if (this.bizOwnCompany.getValue() != null) {
			this.dateOwnCompanyBuild.setValue(((CompanyOrgUnitInfo) this.bizOwnCompany.getValue()).getSetupDate());
//			this.dateOwnCompanyBuild.setEnabled(false);
		} else {
//			this.dateOwnCompanyBuild.setEnabled(true);
			this.dateOwnCompanyBuild.setValue(null);
		}
	}

	private KDBizPromptBox createCompanyF7() {
		KDBizPromptBox companyF7 = new KDBizPromptBox();
		CompanyF7 f7 = new CompanyF7();
		//		f7.setIsCUFilter(true);
		//		f7.setIsShowSub(false);		
		companyF7.setSelector(f7);
		companyF7.setEditable(false);
		return companyF7;
	}

	private KDBizPromptBox createBankF7() {
		KDBizPromptBox bankF7 = new KDBizPromptBox();
		bankF7.setDisplayFormat("$name$");
		bankF7.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
		bankF7.setEditable(false);
		return bankF7;
	}

	/**
	 * output kdtTaxManager_editStopped method
	 */
	protected void kdtTaxManager_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		KDTable table = (KDTable) e.getSource();
		IColumn col = table.getColumn(e.getColIndex());
		IRow row = table.getRow(e.getRowIndex());
		if ((col.getKey().trim().toLowerCase().equals("bank".toLowerCase()) && (row.getCell("bank").getValue() != null))) {
			row.getCell("bankNumber").setValue(((AccountBankInfo) row.getCell("bank").getValue()).getBankAccountNumber());
		}

	}

	/**
	 * output kdtRightManager_editStopped method
	 */
	protected void kdtRightManager_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		KDTable table = (KDTable) e.getSource();
		IColumn col = table.getColumn(e.getColIndex());
		IRow row = table.getRow(e.getRowIndex());
		if ((col.getKey().trim().toLowerCase().equals("isInner".toLowerCase()) && (row.getCell("isInner").getValue() != null))) {
			Boolean flag = (Boolean) row.getCell("isInner").getValue();
			ICell cellValueAttribute = row.getCell("rightCompany");
			if (flag.booleanValue()) {
				cellValueAttribute.setValue(null);
				JTextField txtEnable = new JTextField();
				KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
				ceEnable = new KDTDefaultCellEditor(createCompanyF7());
				cellValueAttribute.setEditor(ceEnable);
			} else {
				KDTextField textField = new KDTextField();//设置长度
				textField.setMaxLength(255);
				cellValueAttribute.setEditor(new KDTDefaultCellEditor(textField));
			}
			//			row.getCell("projectName").setValue(((CurProjectInfo) row.getCell("project").getValue()).getName());
		}
		//		if ((col.getKey().trim().toLowerCase().equals("costCenter".toLowerCase()) && (row.getCell("costCenter").getValue() != null))) {
		//			row.getCell("costCenterName").setValue(((CostCenterOrgUnitInfo) row.getCell("costCenter").getValue()).getName());
		//		}
	}

	/**
	 * output actionAddRightLine_actionPerformed method
	 */
	public void actionAddRightLine_actionPerformed(ActionEvent e) throws Exception {
		this.kdtRightManager.checkParsed();
		int index = -1;
		index = this.kdtRightManager.getRowCount();
		IRow row;
		if (index != -1) {
			row = kdtRightManager.addRow(index);
		} else {
			row = kdtRightManager.addRow();
		}
		row.getCell("isInner").setValue(Boolean.valueOf(false));
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setDateEnabled(true);
		datePicker.setValue(null);
		KDTDefaultCellEditor kdtDefaultCellEditor = new KDTDefaultCellEditor(datePicker);
		row.getCell("dateFrom").setEditor(kdtDefaultCellEditor);
		row.getCell("dateTo").setEditor(kdtDefaultCellEditor);
		String format = "yyyy-MM-dd";
		row.getCell("dateFrom").getStyleAttributes().setNumberFormat(format);
		row.getCell("dateTo").getStyleAttributes().setNumberFormat(format);
	}

	/**
	 * output actionDelRightLine_actionPerformed method
	 */
	public void actionDelRightLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.kdtRightManager.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
			return;
		}
		this.kdtRightManager.removeRow(i);
	}

	/**
	 * output actionAddTaxLine_actionPerformed method
	 */
	public void actionAddTaxLine_actionPerformed(ActionEvent e) throws Exception {
		this.kdtTaxManager.checkParsed();
		int index = -1;
		index = this.kdtTaxManager.getRowCount();
		IRow row;
		if (index != -1) {
			row = kdtTaxManager.addRow(index);
		} else {
			row = kdtTaxManager.addRow();
		}
		ICell cellValueAttribute = row.getCell("bank");
		cellValueAttribute.setValue(null);
		JTextField txtEnable = new JTextField();
		KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createBankF7());
		cellValueAttribute.setEditor(ceEnable);
	}

	/**
	 * output actionDelTaxLine_actionPerformed method
	 */
	public void actionDelTaxLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.kdtTaxManager.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
			return;
		}
		this.kdtTaxManager.removeRow(i);
	}

	/**
	 * output actionAddDirectorLine_actionPerformed method
	 */
	public void actionAddDirectorLine_actionPerformed(ActionEvent e) throws Exception {
		this.kdtDirector.checkParsed();
		int index = -1;
		index = this.kdtDirector.getRowCount();
		IRow row;
		if (index != -1) {
			row = kdtDirector.addRow(index);
		} else {
			row = kdtDirector.addRow();
		}
	}

	/**
	 * output actionDelDirectorLine_actionPerformed method
	 */
	public void actionDelDirectorLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.kdtDirector.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
			return;
		}
		this.kdtDirector.removeRow(i);
	}

	/**
	 * output actionAddSelfDirectorLine_actionPerformed method
	 */
	public void actionAddSelfDirectorLine_actionPerformed(ActionEvent e) throws Exception {
		this.kdtSelfDirector.checkParsed();
		int index = -1;
		index = this.kdtSelfDirector.getRowCount();
		IRow row;
		if (index != -1) {
			row = kdtSelfDirector.addRow(index);
		} else {
			row = kdtSelfDirector.addRow();
		}
	}

	/**
	 * output actionDelSelfDirectorLine_actionPerformed method
	 */
	public void actionDelSelfDirectorLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.kdtSelfDirector.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
			return;
		}
		this.kdtSelfDirector.removeRow(i);
	}

	/**
	 * output actionAddWatcherLine_actionPerformed method
	 */
	public void actionAddWatcherLine_actionPerformed(ActionEvent e) throws Exception {
		this.kdtWatcher.checkParsed();
		int index = -1;
		index = this.kdtWatcher.getRowCount();
		IRow row;
		if (index != -1) {
			row = kdtWatcher.addRow(index);
		} else {
			row = kdtWatcher.addRow();
		}
	}

	/**
	 * output actionDelWatcherLine_actionPerformed method
	 */
	public void actionDelWatcherLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.kdtWatcher.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
			return;
		}
		this.kdtWatcher.removeRow(i);
	}

	/**
	 * output wbtProductDelLine_actionPerformed method
	 */
	protected void wbtProductDelLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.wbtProductDelLine_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
        this.ftxtRegisterCapital.setVisible(true);
        this.ftxtRegisterCapital.setEnabled(false);
        this.ftxtRealCapital.setVisible(true);
        this.ftxtRealCapital.setEnabled(false);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	CompanyOrgUnitInfo cou = null;

	protected IObjectValue createNewData() {
		UnitDataManagerInfo info = new UnitDataManagerInfo();
		if (cou != null) {
			try {
				info = UnitDataManagerFactory.getRemoteInstance().getUnitDataManagerInfo("select *,company.* where company.id = '" + cou.getId().toString() + "'");
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return UnitDataManagerFactory.getRemoteInstance();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("company.*"));
		sic.add(new SelectorItemInfo("company.accountBank.*"));
		sic.add(new SelectorItemInfo("address.*"));
		sic.add(new SelectorItemInfo("taxManager.*"));
		sic.add(new SelectorItemInfo("taxManager.bank.*"));
		sic.add(new SelectorItemInfo("selfDirector.*"));
		sic.add(new SelectorItemInfo("watcher.*"));
		sic.add(new SelectorItemInfo("director.*"));
		sic.add(new SelectorItemInfo("rightManager.*"));
		sic.add(new SelectorItemInfo("rightManager.rightCompany.*"));
		sic.add(new SelectorItemInfo("innerManager.*"));
		sic.add(new SelectorItemInfo("innerManager.managerProjectEntry.curProject.displayName"));
		sic.add(new SelectorItemInfo("innerManager.ownCompany.*"));
		
		sic.add(new SelectorItemInfo("artificialPerson"));
		sic.add(new SelectorItemInfo("dutyPerson"));
		sic.add(new SelectorItemInfo("licenceManager.*"));
		sic.add(new SelectorItemInfo("unitNumber"));
		return sic;
	}

	///////////////////////////////////////////////////////////////////////////////////
	/**
	 * output btnFile1_actionPerformed method
	 */
	protected void btnFile1_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile1() != null && this.editData.getFileType1() != null) {
			if (MsgBox.showConfirm2(this, "已经有公司章程附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile1((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType1(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView1_actionPerformed method
	 */
	protected void btnFileView1_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile1() != null && this.editData.getFileType1() != null)
			AttachmentUtils.viewFile(this.editData.getFileType1(), this.editData.getFile1());
	}

	/**
	 * output btnFile2_actionPerformed method
	 */
	protected void btnFile2_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile2() != null && this.editData.getFileType2() != null) {
			if (MsgBox.showConfirm2(this, "已经有财务审计报告附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile2((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType2(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView2_actionPerformed method
	 */
	protected void btnFileView2_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile2() != null && this.editData.getFileType2() != null)
			AttachmentUtils.viewFile(this.editData.getFileType2(), this.editData.getFile2());
	}

	/**
	 * output btnFile3_actionPerformed method
	 */
	protected void btnFile3_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile3() != null && this.editData.getFileType3() != null) {
			if (MsgBox.showConfirm2(this, "已经有分红记录附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile3((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType3(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView3_actionPerformed method
	 */
	protected void btnFileView3_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile3() != null && this.editData.getFileType3() != null)
			AttachmentUtils.viewFile(this.editData.getFileType3(), this.editData.getFile3());
	}

	/**
	 * output btnFile4_actionPerformed method
	 */
	protected void btnFile4_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile4() != null && this.editData.getFileType4() != null) {
			if (MsgBox.showConfirm2(this, "已经股东出资证明书附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile4((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType4(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView4_actionPerformed method
	 */
	protected void btnFileView4_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile4() != null && this.editData.getFileType4() != null)
			AttachmentUtils.viewFile(this.editData.getFileType4(), this.editData.getFile4());
	}

	/**
	 * output btnFile5_actionPerformed method
	 */
	protected void btnFile5_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile5() != null && this.editData.getFileType5() != null) {
			if (MsgBox.showConfirm2(this, "已经有股东名册附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile5((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType5(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView5_actionPerformed method
	 */
	protected void btnFileView5_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile5() != null && this.editData.getFileType5() != null)
			AttachmentUtils.viewFile(this.editData.getFileType5(), this.editData.getFile5());
	}

	/**
	 * output btnFile6_actionPerformed method
	 */
	protected void btnFile6_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile6() != null && this.editData.getFileType6() != null) {
			if (MsgBox.showConfirm2(this, "已经有股东会会议记录附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile6((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType6(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView6_actionPerformed method
	 */
	protected void btnFileView6_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile6() != null && this.editData.getFileType6() != null)
			AttachmentUtils.viewFile(this.editData.getFileType6(), this.editData.getFile6());
	}

	/**
	 * output btnFile7_actionPerformed method
	 */
	protected void btnFile7_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile7() != null && this.editData.getFileType7() != null) {
			if (MsgBox.showConfirm2(this, "已经有董事会会议决议附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile7((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType7(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView7_actionPerformed method
	 */
	protected void btnFileView7_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile7() != null && this.editData.getFileType7() != null)
			AttachmentUtils.viewFile(this.editData.getFileType7(), this.editData.getFile7());
	}

	/**
	 * output btnFile8_actionPerformed method
	 */
	protected void btnFile8_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile8() != null && this.editData.getFileType8() != null) {
			if (MsgBox.showConfirm2(this, "已经有监事会会议决议附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile8((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType8(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView8_actionPerformed method
	 */
	protected void btnFileView8_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile8() != null && this.editData.getFileType8() != null)
			AttachmentUtils.viewFile(this.editData.getFileType8(), this.editData.getFile8());
	}

	/**
	 * output btnFile9_actionPerformed method
	 */
	protected void btnFile9_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile9() != null && this.editData.getFileType9() != null) {
			if (MsgBox.showConfirm2(this, "已经有营业执照文件附件,是否替换?") == 2) {
				return;
			}
		}

		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile9((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType9(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView9_actionPerformed method
	 */
	protected void btnFileView9_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile9() != null && this.editData.getFileType9() != null)
			AttachmentUtils.viewFile(this.editData.getFileType9(), this.editData.getFile9());
	}

	/**
	 * output btnFile10_actionPerformed method
	 */
	protected void btnFile10_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile10() != null && this.editData.getFileType10() != null) {
			if (MsgBox.showConfirm2(this, "已经有国税登记文件附件,是否替换?") == 2) {
				return;
			}
		}

		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile10((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType10(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView10_actionPerformed method
	 */
	protected void btnFileView10_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile10() != null && this.editData.getFileType10() != null)
			AttachmentUtils.viewFile(this.editData.getFileType10(), this.editData.getFile10());
	}

	/**
	 * output btnFile11_actionPerformed method
	 */
	protected void btnFile11_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile11() != null && this.editData.getFileType11() != null) {
			if (MsgBox.showConfirm2(this, "已经有地税登记文件附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile11((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType11(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView11_actionPerformed method
	 */
	protected void btnFileView11_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile11() != null && this.editData.getFileType11() != null)
			AttachmentUtils.viewFile(this.editData.getFileType11(), this.editData.getFile11());
	}

	/**
	 * output btnFile12_actionPerformed method
	 */
	protected void btnFile12_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile12() != null && this.editData.getFileType12() != null) {
			if (MsgBox.showConfirm2(this, "已经有组织机构代码证附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setFile12((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setFileType12(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output btnFileView12_actionPerformed method
	 */
	protected void btnFileView12_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if (this.editData.getFile12() != null && this.editData.getFileType12() != null)
			AttachmentUtils.viewFile(this.editData.getFileType12(), this.editData.getFile12());
	}

	/**
	 * output boxGetType_itemStateChanged method
	 */
	protected void boxGetType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		if (e.getItem().equals(GetTypeEnum.two)) {
			this.chkHasBuildingProject.setEnabled(true);
			this.chkHasUnit.setEnabled(true);
		} else {
			this.chkHasBuildingProject.setSelected(false);
			this.chkHasBuildingProject.setEnabled(false);
			this.chkHasUnit.setSelected(false);
			this.chkHasUnit.setEnabled(false);

		}
	}

	/**
	 * output actionAddPaymanager_actionPerformed method
	 */
	public void actionAddPaymanager_actionPerformed(ActionEvent e) throws Exception {
		//		ContractBillInfo contract = new ContractBillInfo();
		//		contract.setId(BOSUuid.read(this.getSelectedKeyValue()));
		//		ContractContentCollection coll = ContractContentFactory.getRemoteInstance().getContractContentCollection("select id where contract='" + contract.getId().toString() + "'");
		if (this.editData.getPayFile() != null && this.editData.getPayFileType() != null) {
			if (MsgBox.showConfirm2(this, "已经有付款管理附件,是否替换?") == 2) {
				return;
			}
		}
		Map map = new HashMap();
		map = AttachmentUtils.chooseFile(this);
		if (map != null) {
			this.editData.setPayFile((byte[]) map.get(AttachmentUtils.FILE_DATA));
			this.editData.setPayFileType(map.get(AttachmentUtils.FILE_EXT).toString());
		}
	}

	/**
	 * output actionViewPaymanager_actionPerformed method
	 */
	public void actionViewPaymanager_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getPayFile() != null && this.editData.getPayFileType() != null)
			AttachmentUtils.viewFile(this.editData.getPayFileType(), this.editData.getPayFile());
	}

	private void setBtnStatus() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
		avr.setFormat(new BizDataFormat("name"));
		KDTextField textField = new KDTextField();
		textField.setMaxLength(255);
		kdtRightManager.checkParsed();
		kdtRightManager.getColumn("rightScale").setEditor(numberEditor);
		kdtRightManager.getColumn("holdScale").setEditor(numberEditor);
		kdtRightManager.getColumn("amt").setEditor(numberEditor);
		kdtTaxManager.checkParsed();
		kdtTaxManager.getColumn("bankNumber").setEditor(new KDTDefaultCellEditor(textField));
		kdtTaxManager.getColumn("bankNumber").getStyleAttributes().setLocked(true);
		kdtDirector.checkParsed();
		kdtDirector.getColumn("name").setEditor(new KDTDefaultCellEditor(textField));
		kdtSelfDirector.checkParsed();
		kdtSelfDirector.getColumn("name").setEditor(new KDTDefaultCellEditor(textField));
		kdtWatcher.checkParsed();
		kdtWatcher.getColumn("name").setEditor(new KDTDefaultCellEditor(textField));

		//		CompanyF7 companyF7 = new CompanyF7();
		CompanyBizUnitF7 companyF7 = new CompanyBizUnitF7();
		companyF7.setRootUnitID(SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		//		companyF7.setIsCUFilter(true);

		this.bizCompany.setSelector(companyF7);
		this.bizCompany.setEditable(false);
		this.bizOwnCompany.setSelector(companyF7);
		this.bizOwnCompany.setEditable(false);
		//		this.bizAddress.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AddressQuery");
		//		this.bizAddress.setEditable(false);
		//		this.txtBank.setEnabled(false);
		//		this.txtBankNum.setEnabled(false);
		this.dateOwnCompanyBuild.setValue(null);
		//可以录入
		this.dateOwnCompanyBuild.setEnabled(true);
		this.dateBuild.setValue(null);
		this.dateRegister.setValue(null);
		this.dateBusinessFrom.setValue(null);
		this.dateBusinessTo.setValue(null);

		//
		this.btnTaxAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnTaxAddLine.setText(null);
		this.btnTaxAddLine.setToolTipText("新增行");
		this.btnTaxDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnTaxDelLine.setText(null);
		this.btnTaxDelLine.setToolTipText("删除行");
		//
		this.btnDirectorAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDirectorAddLine.setText(null);
		this.btnDirectorAddLine.setToolTipText("新增行");
		this.btnDirectorDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnDirectorDelLine.setText(null);
		this.btnDirectorDelLine.setToolTipText("删除行");
		//
		this.btnSelfDirectorAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnSelfDirectorAddLine.setText(null);
		this.btnSelfDirectorAddLine.setToolTipText("新增行");
		this.btnSelfDirectorDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnSelfDirectorDelLine.setText(null);
		this.btnSelfDirectorDelLine.setToolTipText("删除行");
		//
		this.btnWatcherAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnWatcherAddLine.setText(null);
		this.btnWatcherAddLine.setToolTipText("新增行");
		this.btnWatcherDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnWatcherDelLine.setText(null);
		this.btnWatcherDelLine.setToolTipText("删除行");
		//
		this.btnAddRightLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnAddRightLine.setText(null);
		this.btnAddRightLine.setToolTipText("新增行");
		this.btnDelRightLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnDelRightLine.setText(null);
		this.btnDelRightLine.setToolTipText("删除行");
		//
		this.btnFile1.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile1.setSize(60, 20);
		this.btnFile1.setText("上传");
		this.btnFile2.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile2.setSize(60, 20);
		this.btnFile2.setText("上传");
		this.btnFile3.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile3.setSize(60, 20);
		this.btnFile3.setText("上传");
		this.btnFile4.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile4.setSize(60, 20);
		this.btnFile4.setText("上传");
		this.btnFile5.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile5.setSize(60, 20);
		this.btnFile5.setText("上传");
		this.btnFile6.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile6.setSize(60, 20);
		this.btnFile6.setText("上传");
		this.btnFile7.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile7.setSize(60, 20);
		this.btnFile7.setText("上传");
		this.btnFile8.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile8.setSize(60, 20);
		this.btnFile8.setText("上传");
		this.btnFile9.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile9.setSize(60, 20);
		this.btnFile9.setText("上传");
		this.btnFile10.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile10.setSize(60, 20);
		this.btnFile10.setText("上传");
		this.btnFile11.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile11.setSize(60, 20);
		this.btnFile11.setText("上传");
		this.btnFile12.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		this.btnFile12.setSize(60, 20);
		this.btnFile12.setText("上传");
		this.btnAddPaymanager.setIcon(EASResource.getIcon("imgTbtn_impress"));//
		//
		this.btnFileView1.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView1.setSize(60, 20);
		this.btnFileView1.setText("查看");
		this.btnFileView2.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView2.setSize(60, 20);
		this.btnFileView2.setText("查看");
		this.btnFileView3.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView3.setSize(60, 20);
		this.btnFileView3.setText("查看");
		this.btnFileView4.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView4.setSize(60, 20);
		this.btnFileView4.setText("查看");
		this.btnFileView5.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView5.setSize(60, 20);
		this.btnFileView5.setText("查看");
		this.btnFileView6.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView6.setSize(60, 20);
		this.btnFileView6.setText("查看");
		this.btnFileView7.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView7.setSize(60, 20);
		this.btnFileView7.setText("查看");
		this.btnFileView8.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView8.setSize(60, 20);
		this.btnFileView8.setText("查看");
		this.btnFileView9.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView9.setSize(60, 20);
		this.btnFileView9.setText("查看");
		this.btnFileView10.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView10.setSize(60, 20);
		this.btnFileView10.setText("查看");
		this.btnFileView11.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView11.setSize(60, 20);
		this.btnFileView11.setText("查看");
		this.btnFileView12.setIcon(EASResource.getIcon("imgTbtn_view"));//
		this.btnFileView12.setSize(60, 20);
		this.btnFileView12.setText("查看");
		this.btnViewPaymanager.setIcon(EASResource.getIcon("imgTbtn_view"));
		//
		if (this.editData.getFile1() == null)
			this.btnFileView1.setEnabled(false);
		if (this.editData.getFile2() == null)
			this.btnFileView2.setEnabled(false);
		if (this.editData.getFile3() == null)
			this.btnFileView3.setEnabled(false);
		if (this.editData.getFile4() == null)
			this.btnFileView4.setEnabled(false);
		if (this.editData.getFile5() == null)
			this.btnFileView5.setEnabled(false);
		if (this.editData.getFile6() == null)
			this.btnFileView6.setEnabled(false);
		if (this.editData.getFile7() == null)
			this.btnFileView7.setEnabled(false);
		if (this.editData.getFile8() == null)
			this.btnFileView8.setEnabled(false);
		if (this.editData.getFile9() == null)
			this.btnFileView9.setEnabled(false);
		if (this.editData.getFile10() == null)
			this.btnFileView10.setEnabled(false);
		if (this.editData.getFile11() == null)
			this.btnFileView11.setEnabled(false);
		if (this.editData.getFile12() == null)
			this.btnFileView12.setEnabled(false);
		if (this.editData.getPayFile() == null)
			this.btnViewPaymanager.setEnabled(false);

		//
		this.chkHasBuildingProject.setEnabled(false);
		this.chkHasUnit.setEnabled(false);
		//
		//		this.btnDirectorAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		//		this.btnDirectorAddLine.setSize(20,20);
		//		this.btnDirectorDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		//		this.btnDirectorDelLine.setSize(20,20);
		//		this.actionAddDirectorLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		//		btnDirectorAddLine = (KDWorkButton) this.kDContainer1.add(this.actionAdd);
		//		this.actionDelDirectorLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		
		this.btnSave.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.menuItemCopy.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrintPreview.setVisible(false);
		this.menuItemAddNew.setVisible(false);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile1_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile1() != null)
			this.btnFileView1.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile2_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile2() != null)
			this.btnFileView2.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile3_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile3() != null)
			this.btnFileView3.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile4_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile4() != null)
			this.btnFileView4.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile5_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile5() != null)
			this.btnFileView5.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile6_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile6() != null)
			this.btnFileView6.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile7_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile7() != null)
			this.btnFileView7.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile8_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile8() != null)
			this.btnFileView8.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile9_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile9() != null)
			this.btnFileView9.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile10_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile10() != null)
			this.btnFileView10.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile11_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile11() != null)
			this.btnFileView11.setEnabled(true);
	}

	/**
	 * output btnFile1_mouseReleased method
	 */
	protected void btnFile12_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getFile12() != null)
			this.btnFileView12.setEnabled(true);
	}

	/**
	 * output btnAddPaymanager_mouseReleased method
	 */
	protected void btnAddPaymanager_mouseReleased(java.awt.event.MouseEvent e) throws Exception {
		if (this.editData.getPayFile() != null)
			this.btnViewPaymanager.setEnabled(true);
	}

	private TreeSelectDialog projectSelectDlg;
	protected void btnManagerProject_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnManagerProject_actionPerformed(e);
		if (this.projectSelectDlg == null) {
			String[] projectIds=new String[lstManagerProject.getElementCount()];
			for(int i=0;i<lstManagerProject.getElementCount();i++){
				projectIds[i]=((ManagerProjectEntryInfo)lstManagerProject.getElement(i)).getCurProject().getId().toString();
			}
			if(projectIds.length==0) projectIds=null;
			this.initProjectDlg(projectIds);
		}
		Object object = projectSelectDlg.showDialog();

		if(object instanceof List){
			List projectList = (List) object;
			lstManagerProject.removeAllElements();
			for (int i = 0; i < projectList.size(); i++) {
				if(projectList.get(i) instanceof CurProjectInfo){
					ManagerProjectEntryInfo entry=new ManagerProjectEntryInfo();
					entry.setCurProject((CurProjectInfo)projectList.get(i));
					lstManagerProject.addElement(entry);
				}
			}
		}
		
	}
	private void initProjectDlg(String[] projectIds) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		KDTree tree = new KDTree();
/*		if (this.companySelectDlg != null
				&& this.companySelectDlg.getSelectTree() != null) {
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {*/
			builder.build(this, tree, this.actionOnLoad);
//		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString",
				projectIds);
	}
}