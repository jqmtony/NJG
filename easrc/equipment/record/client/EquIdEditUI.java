/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.assistant.AddressFactory;
import com.kingdee.eas.basedata.assistant.AddressInfo;
import com.kingdee.eas.basedata.assistant.client.F7MeasureUnitTreeDetailListUI;
import com.kingdee.eas.basedata.master.cssp.client.F7SupplierSimpleSelector;
import com.kingdee.eas.basedata.master.material.client.MaterialClientTools;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.fa.basedata.FaCatFactory;
import com.kingdee.eas.fi.fa.basedata.FaCatInfo;
import com.kingdee.eas.fi.fa.basedata.FaUseStatusFactory;
import com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo;
import com.kingdee.eas.fi.fa.basedata.client.FaCatPromptBox;
import com.kingdee.eas.fi.fa.manage.FaCurCardFactory;
import com.kingdee.eas.fi.fa.manage.FaCurCardInfo;
import com.kingdee.eas.fi.fa.manage.FaManageUtils;
import com.kingdee.eas.fi.fa.manage.IFaCurCard;
import com.kingdee.eas.fi.fa.manage.client.CommonQueryWithResultDialog;
import com.kingdee.eas.fi.fa.manage.client.FACommonProcessor;
import com.kingdee.eas.fi.fa.manage.client.FaClientUtils;
import com.kingdee.eas.framework.BillBaseInfo;
import com.kingdee.eas.port.equipment.insurance.EquInsuranceAccidentCollection;
import com.kingdee.eas.port.equipment.insurance.EquInsuranceAccidentFactory;
import com.kingdee.eas.port.equipment.insurance.IEquInsuranceAccident;
import com.kingdee.eas.port.equipment.insurance.IInsuranceCoverageE1;
import com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Collection;
import com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Factory;
import com.kingdee.eas.port.equipment.maintenance.IRepairOrder;
import com.kingdee.eas.port.equipment.maintenance.RepairOrderCollection;
import com.kingdee.eas.port.equipment.maintenance.RepairOrderFactory;
import com.kingdee.eas.port.equipment.operate.EqmAccidentCollection;
import com.kingdee.eas.port.equipment.operate.EqmAccidentFactory;
import com.kingdee.eas.port.equipment.operate.EqmIOCollection;
import com.kingdee.eas.port.equipment.operate.EqmIOFactory;
import com.kingdee.eas.port.equipment.operate.EqmScrapCollection;
import com.kingdee.eas.port.equipment.operate.EqmScrapFactory;
import com.kingdee.eas.port.equipment.operate.IEqmAccident;
import com.kingdee.eas.port.equipment.operate.IEqmIO;
import com.kingdee.eas.port.equipment.operate.IEqmScrap;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdSpareInfoCollection;
import com.kingdee.eas.port.equipment.record.EquIdSpareInfoInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryCollection;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryFactory;
import com.kingdee.eas.port.equipment.special.IAnnualYearDetailEntry;
import com.kingdee.eas.port.equipment.special.client.SpecialChangeEditUI;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.ListenersXRHelper;
import com.kingdee.eas.xr.helper.Tool;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */

public class EquIdEditUI extends AbstractEquIdEditUI {
	
	static final String Coll_CU_ID = "NJP";
	private static final Logger logger = CoreUIObject
			.getLogger(EquIdEditUI.class);
	private String companyId;
	/**
	 * output class constructor
	 */
	public EquIdEditUI() throws Exception {
		super();
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		detachListeners();
		super.loadFields();
		attachListeners();
		tzsbStatus.setEnabled(true);
		contdayone.setVisible(false);
		contdaytow.setVisible(false);
		pkactrueTime.setEnabled(false);
		prmtjhOrgUnit.setEnabled(false);
		prmtjhOrgUnit.setVisible(false);
		prmtwxOrgUnit.setEnabled(false);
		prmtwxOrgUnit.setVisible(false);
		txtsize.setEnabled(false);
		txtsize.setVisible(false);
		consize.setVisible(false);
		conwxOrgUnit.setVisible(false);
		
		try {
			loadSupplierAttachList();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		tzsbStatus.setEnabled(false);
		combosbStatus.setEnabled(false);
		pktextDate1.setEnabled(false);
		testDay.setEnabled(false);
		prmtjhOrgUnit.setEnabled(false);
		prmtjhOrgUnit.setVisible(false);
		prmtwxOrgUnit.setEnabled(false);
		prmtwxOrgUnit.setVisible(false);
		txtsize.setEnabled(false);
		txtsize.setVisible(false);
		chkyibiao.setVisible(false);
		contactrueTime.setVisible(false);
		pkactrueTime.setVisible(false);
		 this.kdtE3.getColumn("seq").getStyleAttributes().setHided(true);
		combonowStatus.setEnabled(false);
		this.txtcityPeriod.setPrecision(0);	
		this.txtportPeriod.setPrecision(0);	
		txtnowAmount.setEnabled(false);
		txtoldYear.setEnabled(false);
		txtzuzhijigou.setEnabled(false);
		txtyouzhengbianma.setEnabled(false);
		contresponsible.setVisible(false);
		txtresponsible.setVisible(false);
		conttelePhoneNumber.setVisible(false);
		txttelePhoneNumber.setVisible(false);
		contzzsShortName.setVisible(false);
		txtzzsShortName.setVisible(false);
		super.onLoad();
		FaCatPromptBox facatBox = new FaCatPromptBox();
		facatBox.setACompanyOrgUnitInfo(SysContext.getSysContext().getCurrentFIUnit());
		this.prmttype.setSelector(facatBox);
	
		
		if (OprtState.ADDNEW.equals(getOprtState())) {
			this.prmtssOrgUnit.setValue(SysContext.getSysContext().getCurrentAdminUnit());
			this.prmtuseUnit.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		}
		
		 EntityViewInfo evi = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
		 String idaa = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		 filter.getFilterItems().add(new FilterItemInfo("company.id",idaa ,CompareType.EQUALS));
		 evi.setFilter(filter);
		 prmtasset.setEntityViewInfo(evi);
		
		setTzsbState(false);
		//根据使用组织带出单位所在地址
		if(prmtuseUnit.getValue() != null){
			String id = ((AdminOrgUnitInfo)prmtuseUnit.getData()).getId().toString();
			AdminOrgUnitInfo aoInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(id));
			if(aoInfo.getAddress() != null){
				String id1 = ((AddressInfo)aoInfo.getAddress()).getId().toString();
				AddressInfo addInfo = AddressFactory.getRemoteInstance().getAddressInfo(new ObjectUuidPK(id1));
				prmtaddress.setValue(addInfo);
			}else{
				prmtaddress.setValue(null);
			}
			if(aoInfo.getOrgCode() != null){
				txtzuzhijigou.setText(aoInfo.getOrgCode());
			}else{
				txtzuzhijigou.setText(null);
			}
			
			if(aoInfo.getZipCode() !=null){
				txtyouzhengbianma.setText(aoInfo.getZipCode());
			}else{
				txtyouzhengbianma.setText(null);
			}
		}
		
		
		
		/**
		 * zhangjuan
		 * 2014-5-14
		 * 实现功能：添加菜单下拉按钮，展现相关联单据；
		 */
		KDWorkButton kdworkButton = new KDWorkButton("历史记录关联查看");
		KDMenuItem kdmenuItem1 = new KDMenuItem("维保任务单");
		KDMenuItem kdmenuItem2 = new KDMenuItem("设备事故单");
		KDMenuItem kdmenuItem3 = new KDMenuItem("设备投保明细表");
		KDMenuItem kdmenuItem4 = new KDMenuItem("设备保险事故记录");
		KDMenuItem kdmenuItem5 = new KDMenuItem("特种设备检测明细表");
		KDMenuItem kdmenuItem6 = new KDMenuItem("设备调入调出");
		KDMenuItem kdmenuItem7 = new KDMenuItem("设备报废单");
		
		//添加按钮事件
		//设备修理单
		kdmenuItem1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				upRepairOrder();
			}
		});
		
		//设备事故单
		kdmenuItem2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EqmAccident();
			}
		});
		
		//设备投保明细表
		kdmenuItem3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				upInsuranceCoverage();
			}
		});
		
		//设备保险事故记录
		kdmenuItem4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EquInsuranceAccident();
			}
		});
		
		//特种设备检测明细表
		kdmenuItem5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				AnnualYearDetail();
			}
		});
		

		//设备调入调出
		kdmenuItem6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EqmIO();
			}
		});
		
		//设备报废单
		kdmenuItem7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EqmScrap();
			}
		});
		
		
		kdworkButton.addAssistMenuItem(kdmenuItem1);
		kdworkButton.addAssistMenuItem(kdmenuItem2);
		kdworkButton.addAssistMenuItem(kdmenuItem3);
		kdworkButton.addAssistMenuItem(kdmenuItem4);
		kdworkButton.addAssistMenuItem(kdmenuItem5);
		kdworkButton.addAssistMenuItem(kdmenuItem6);
		kdworkButton.addAssistMenuItem(kdmenuItem7);
		
		this.toolBar.add(kdworkButton,8);
	
		if(editData.isSpecial()){
			txttzdaNumber.setEnabled(true);
			txtcityPeriod.setEnabled(true);
			txtzzsShortName.setEnabled(true);
			prmttextType.setEnabled(true);
			txtresponsible.setEnabled(true);
			chkcityTest.setEnabled(true);
			chkportTest.setEnabled(true);
			txtcode.setEnabled(true);
			prmtspecialType.setEnabled(true);
			txttelePhoneNumber.setEnabled(true);
			prmtequTypeone.setEnabled(true);
			tzsbStatus.setEnabled(true);
		}
		if(chkspecial.getSelected() ==16){
			txttzdaNumber.setEnabled(false);
			txttzdaNumber.setText(null);
			txtcityPeriod.setEnabled(false);
			txtcityPeriod.setValue(null);
			txtzzsShortName.setEnabled(false);
			txtzzsShortName.setText(null);
			prmttextType.setEnabled(false);
			prmttextType.setValue(null);
			txtresponsible.setEnabled(false);
			txtresponsible.setText(null);
			chkcityTest.setEnabled(false);
			chkcityTest.setSelected(false);
			chkportTest.setEnabled(false);
			chkportTest.setSelected(false);
			txtcode.setEnabled(false);
			txtcode.setText(null);
			prmtspecialType.setEnabled(false);
			prmtspecialType.setValue(null);
			txttelePhoneNumber.setEnabled(false);
			txttelePhoneNumber.setText(null);
			prmtequTypeone.setEnabled(false);
			prmtequTypeone.setValue(null);
			tzsbStatus.setEnabled(false);
		}
		
		chkspecial.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(e.getSource()!=null)
				{
					initDataStatus();
				}
			}
			
		});
		
		Tool.setRespDeptF7(this.prmtjhOrgUnit, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setRespDeptF7(this.prmtwxOrgUnit, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setRespDeptF7(this.prmtwxDept, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setRespDeptF7(this.prmtssOrgUnit, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setRespDeptF7(this.prmtuseUnit, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setRespDeptF7(this.prmtusingDept, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		Tool.setPersonF7(this.prmtresPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		
		this.prmtsupplier.setSelector(new F7SupplierSimpleSelector(this,this.prmtsupplier));
		this.prmtinstaller.setSelector(new F7SupplierSimpleSelector(this,this.prmtinstaller));
		
		MaterialClientTools.setMeasureUnitF7(this, this.prmtunit);
		
		this.prmtasset.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
			}
			
		});
		
		
		if(getUIContext().get("anid")!=null){
			this.toolBar.setVisible(false);
			this.toolBar.removeAllToolBarComponents();
		}
		if (prmtasset.getValue() !=null ) {
			String id = ((FaCurCardInfo)prmtasset.getData()).getId().toString();
			FaCurCardInfo facur = FaCurCardFactory.getRemoteInstance().getFaCurCardInfo(new ObjectUuidPK(id));
			if(facur.getAssetValue() != null){
				txtassetValue.setValue(facur.getAssetValue());
			}
			if(facur.getNeatValue()!=null){
				txtnowAmount.setValue(facur.getNeatValue());
			}
		    if(facur.getUseYears()!=null){
		    	txtoldYear.setValue(facur.getUseYears());
		    }
		    if(facur.getUseStatus() != null){
		    	FaUseStatusInfo info = FaUseStatusFactory.getRemoteInstance().getFaUseStatusInfo(new ObjectUuidPK(facur.getUseStatus().getId()));
				prmtassetStatus.setValue(info);
		    }
		
		}
	
		
		InitAttactByTable();
		
	}
	
	private void SelectorFaCard() throws Exception
	{
		CommonQueryWithResultDialog dialog = initDialog();
		if(dialog.show() || dialog.hasKeyValue())
		{
			ArrayList rowSet = dialog.getSearchResult();
			IFaCurCard iFaCurCard = FaCurCardFactory.getRemoteInstance();
			if(rowSet != null && rowSet.size() > 0)
            {
				IRow result = (IRow)rowSet.get(0);
				String cardId = result.getCell("id").getValue().toString();
				FaCurCardInfo cardInfo = iFaCurCard.getFaCurCardInfo(new ObjectUuidPK(cardId));
				
				this.prmtasset.setValue(cardInfo);
            }
		} else
		{
			SysUtil.abort();
		}
	}
	
    private CommonQueryWithResultDialog initDialog()throws Exception
    {
    	CommonQueryWithResultDialog dialog = new CommonQueryWithResultDialog();
    	if(getUIWindow() == null)
    		dialog.setOwner((Component)getUIContext().get("OwnerWindow"));
    	else
    		dialog.setOwner(this);
    	HashMap hmParam = gethmParamD();
    	boolean isRange = "true".equals(hmParam.get("FA_040").toString());
    	FACommonProcessor processor = new FACommonProcessor();
    	processor.setRange(isRange);
    	dialog.setProcessor(processor);
    	dialog.setParentUIClassName(getClass().getName());
    	dialog.setEntityViewInfo(new EntityViewInfo());
    	dialog.setQueryObjectPK(new MetaDataPK("com.kingdee.eas.fi.fa.manage", "FaCurCardQuery"));
    	dialog.setTitle(getUITitle());
    	dialog.setHeight(525);
    	dialog.setSelectMode(10);
    	dialog.setOpenType(0);
    	return dialog;
    }
    
    public HashMap gethmParamD()
    {
    	HashMap hmParam = new HashMap();
    	try
        {
    		hmParam = FaManageUtils.getFAParameter(BOSUuid.read(getCurrentCompanyID()));
        }
    	catch(Exception e)
        {
    		handUIException(e);
        }
    	return hmParam;
    }
    
    protected String getCurrentCompanyID()
    {
    	if(FaClientUtils.isFromWorkflow(getUIContext()))
        {
    		BillBaseInfo initObj = null;
    		if(getUIContext().get("ID") != null)
            {
    			String id = getUIContext().get("ID").toString();
    			try
                {
    				initObj = (BillBaseInfo)getBizInterface().getValue((new StringBuilder()).append("select company.id where id='").append(id).append("'").toString());
                }
    			catch(Exception e)
                {
    				handleException(e);
    				SysUtil.abort();
                }
            }
    		companyId = initObj == null ? SysContext.getSysContext().getCurrentFIUnit().getId().toString() : initObj.getCompany().getId().toString();
        } else
        	if(FaClientUtils.isFromLink(this))
        		companyId = (String)getUIContext().get("COMPANY_ID");
        	else
        		companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
    	return companyId;
    }
	
	//市检值改变事件
	protected void txtcityPeriod_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtcityPeriod_dataChanged(e);
			if(pktextDate1.getValue() !=null && txtcityPeriod.getBigDecimalValue() !=null){
					Calendar ca = Calendar.getInstance();
					ca.setTime(this.pktextDate1.getSqlDate());
					ca.add(Calendar.YEAR,txtcityPeriod.getBigIntegerValue().intValue());
					pkdayone.setValue(ca.getTime());
			}
		
	}
	
//	//港检值改变事件
//	protected void txtportPeriod_dataChanged(DataChangeEvent e)
//			throws Exception {
//		super.txtportPeriod_dataChanged(e);
//		if(testDay.getValue() !=null && txtportPeriod.getBigDecimalValue() !=null){
//			Calendar ca = Calendar.getInstance();
//			ca.setTime(this.testDay.getSqlDate());
//			ca.add(Calendar.YEAR,txtportPeriod.getBigIntegerValue().intValue());
//			pkdaytow.setValue(ca.getTime());
//	}
//	}
	
	 protected void initWorkButton()
	    {
	        super.initWorkButton();
	        btnRegistChange.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
	    }
	
	//实现功能：下拉按钮弹窗展现历史相关联设备修理单单据。
	private void upRepairOrder(){
		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select parent.id where E1.equNameOne.id='"+editData.getId().toString()+"'";
			IRepairOrder IRepairOrder = RepairOrderFactory.getRemoteInstance();
			Vector v1 = new Vector();

			
			RepairOrderCollection repairOrderColl = IRepairOrder.getRepairOrderCollection(oql);
			for (int i = 0; i < repairOrderColl.size(); i++) {
				v1.add(repairOrderColl.get(i).getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联维保任务单！");
				SysUtil.abort();
			}
			idList.put("F96E9B71",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//实现功能：下拉按钮弹窗展现历史相关联设备事故单单据。
	private void EqmAccident(){
		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select id where eqmName.id='"+editData.getId().toString()+"'";
			IEqmAccident IEqmAccident = EqmAccidentFactory.getRemoteInstance();
			Vector v1 = new Vector();
			EqmAccidentCollection EqmAccidentColl = IEqmAccident.getEqmAccidentCollection(oql);
			for (int i = 0; i < EqmAccidentColl.size(); i++) {
				v1.add(EqmAccidentColl.get(i).getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联设备事故单！");
				SysUtil.abort();
			}
			idList.put("DB643291",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//实现功能：下拉按钮弹窗展现历史相关联设备保险事故记录单据。
	private void EquInsuranceAccident(){
		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select id where equNumber.id='"+editData.getId().toString()+"'";
			IEquInsuranceAccident IEquInsuranceAccident = EquInsuranceAccidentFactory.getRemoteInstance();
			Vector v1 = new Vector();
			EquInsuranceAccidentCollection EquInsuranceAccidentColl = IEquInsuranceAccident.getEquInsuranceAccidentCollection(oql);
			for (int i = 0; i < EquInsuranceAccidentColl.size(); i++) {
				v1.add(EquInsuranceAccidentColl.get(i).getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联设备保险事故记录！");
				SysUtil.abort();
			}
			idList.put("85931329",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	//实现功能：下拉按钮弹窗展现历史相关联设备保险投保明细表单据。
	private void upInsuranceCoverage() {

		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select parent.id where equNumber.id='"+editData.getId().toString()+"'";
			IInsuranceCoverageE1 IInsuranceCoverageE1 = InsuranceCoverageE1Factory.getRemoteInstance();
			Vector v1 = new Vector();
			
			InsuranceCoverageE1Collection repairOrderColl = IInsuranceCoverageE1.getInsuranceCoverageE1Collection(oql);
			for (int i = 0; i < repairOrderColl.size(); i++) {
				v1.add(repairOrderColl.get(i).getParent().getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联保险投保明细表！");
				SysUtil.abort();
			}
			idList.put("46F6E919",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}

	//实现功能：下拉按钮弹窗展现历史相关联特种设备检测明细表单据。
	private void AnnualYearDetail(){
		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select parent.id where zdaNumber.id='"+editData.getId().toString()+"'";
			IAnnualYearDetailEntry IAnnualYearDetailEntry = AnnualYearDetailEntryFactory.getRemoteInstance();
			Vector v1 = new Vector();
			
			AnnualYearDetailEntryCollection AnnualYearDetailEntryColl = IAnnualYearDetailEntry.getAnnualYearDetailEntryCollection(oql);
			for (int i = 0; i < AnnualYearDetailEntryColl.size(); i++) {
				v1.add(AnnualYearDetailEntryColl.get(i).getParent().getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联特种设备检测明细表！");
				SysUtil.abort();
			}
			idList.put("22366297",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//实现功能：下拉按钮弹窗展现历史相关联设备调入调出单据。
	private void EqmIO(){
		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select id where eqmNumber.id='"+editData.getId().toString()+"'";
			IEqmIO IEqmIO = EqmIOFactory.getRemoteInstance();
			Vector v1 = new Vector();
			EqmIOCollection EqmIOColl = IEqmIO.getEqmIOCollection(oql);
			for (int i = 0; i < EqmIOColl.size(); i++) {
				v1.add(EqmIOColl.get(i).getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联设备调入调出！");
				SysUtil.abort();
			}
			idList.put("A4EDF708",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//实现功能：下拉按钮弹窗展现历史相关联设备报废单单据。
	private void EqmScrap(){
		try 
		{
			if(editData.getId()==null)
			{
				MsgBox.showWarning("请先保存单据！");SysUtil.abort();
			}
			String oql = "select id where eqmNumber.id='"+editData.getId().toString()+"'";
			IEqmScrap IEqmScrap = EqmScrapFactory.getRemoteInstance();
			Vector v1 = new Vector();
			EqmScrapCollection EqmScrapColl = IEqmScrap.getEqmScrapCollection(oql);
			for (int i = 0; i < EqmScrapColl.size(); i++) {
				v1.add(EqmScrapColl.get(i).getId().toString());
			}
			HashMap idList = new HashMap();
			if(v1.size()<1){
				MsgBox.showWarning("当前所选数据没有关联设备报废单！");
				SysUtil.abort();
			}
			idList.put("08D6068F",v1 );
			ToolHelp.showTraceUI(this, idList, 0);
			
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		} 
		catch (BOSException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	protected void chkspecial_stateChanged(ChangeEvent e) throws Exception {
		if(chkspecial.getSelected()==KDCheckBox.SELECTED){
			setTzsbState(true);
		}else {
			setTzsbState(false);
		}
//		if(chkspecial.getSelected() == 16){
//			
//		}
	}
	
	protected void setTzsbState(boolean state){
//		txttzdaNumber.setEnabled(state);
//		chkcityTest.setEnabled(state);
//		chkportTest.setEnabled(state);
//		testDay.setEnabled(state);
		
	}

	protected void prmtasset_dataChanged(DataChangeEvent e) throws Exception {
		if (prmtasset.getValue() == null) {
			return;
		}
		FaCurCardInfo facur = (FaCurCardInfo) prmtasset.getValue();
		if(facur.getAssetValue() != null){
			txtassetValue.setValue(facur.getAssetValue());
		}
		if(facur.getNeatValue()!=null){
			txtnowAmount.setValue(facur.getNeatValue());
		}
	    if(facur.getUseYears()!=null){
	    	txtoldYear.setValue(facur.getUseYears());
	    }
		FaUseStatusInfo info = FaUseStatusFactory.getRemoteInstance().getFaUseStatusInfo(new ObjectUuidPK(facur.getUseStatus().getId()));
		prmtassetStatus.setValue(info);
		
	}

	protected void prmttype_dataChanged(DataChangeEvent e) throws Exception {
		if (prmttype.getValue() == null) {
			return;
		}
		FaCatInfo facat = (FaCatInfo) prmttype.getValue();
		if (!facat.isIsLeaf()) {
			MsgBox.showWarning("只能选择最末级节点！");
			prmttype.setValue(null);
			txtEqmCategory.setText("");
			SysUtil.abort();
		} else{
			String eqmcat = FaCatFactory.getRemoteInstance().getFaCatInfo(new ObjectUuidPK(facat.getParent().getId())).getName();
			txtEqmCategory.setText(eqmcat);
		}
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(
			java.awt.event.ItemEvent e) throws Exception {
		super.menuItemEnterToNextRow_itemStateChanged(e);
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
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionToolBarCustom_actionPerformed
	 */
	public void actionToolBarCustom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionToolBarCustom_actionPerformed(e);
	}

	/**
	 * output actionCloudFeed_actionPerformed
	 */
	public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception {
		super.actionCloudFeed_actionPerformed(e);
	}

	/**
	 * output actionCloudShare_actionPerformed
	 */
	public void actionCloudShare_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudShare_actionPerformed(e);
	}

	/**
	 * output actionCloudScreen_actionPerformed
	 */
	public void actionCloudScreen_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudScreen_actionPerformed(e);
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
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
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
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
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

	protected void initDataStatus() {
		super.initDataStatus();
			if(chkspecial.isSelected()){
				txttzdaNumber.setEnabled(true);
				txtcityPeriod.setEnabled(true);
				txtzzsShortName.setEnabled(true);
				prmttextType.setEnabled(true);
				txtresponsible.setEnabled(true);
				chkcityTest.setEnabled(true);
				chkportTest.setEnabled(true);
				txtcode.setEnabled(true);
				prmtspecialType.setEnabled(true);
				txttelePhoneNumber.setEnabled(true);
				prmtequTypeone.setEnabled(true);
				tzsbStatus.setEnabled(true);
			}
			else
			{
				txttzdaNumber.setEnabled(false);
				txttzdaNumber.setText(null);
				txtcityPeriod.setEnabled(false);
				txtcityPeriod.setValue(null);
				txtzzsShortName.setEnabled(false);
				txtzzsShortName.setText(null);
				prmttextType.setEnabled(false);
				prmttextType.setValue(null);
				txtresponsible.setEnabled(false);
				txtresponsible.setText(null);
				chkcityTest.setEnabled(false);
				chkcityTest.setSelected(false);
				chkportTest.setEnabled(false);
				chkportTest.setSelected(false);
				txtcode.setEnabled(false);
				txtcode.setText(null);
				prmtspecialType.setEnabled(false);
				prmtspecialType.setValue(null);
				txttelePhoneNumber.setEnabled(false);
				txttelePhoneNumber.setText(null);
				prmtequTypeone.setEnabled(false);
				prmtequTypeone.setValue(null);
				tzsbStatus.setEnabled(false);
		}
	}
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
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
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionNumberSign_actionPerformed
	 */
	public void actionNumberSign_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNumberSign_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.port.equipment.record.EquIdFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * 集团下不允许新建单据
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.port.equipment.record.EquIdInfo objectValue = new com.kingdee.eas.port.equipment.record.EquIdInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		Tool.checkGroupAddNew();
		return objectValue;
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = "false";
		if (StringUtils.isEmpty(selectorAll)) {
			selectorAll = "true";
		}
		if (selectorAll.equalsIgnoreCase("true")) {
		} else {
			sic.add(new SelectorItemInfo("creator.id"));
			sic.add(new SelectorItemInfo("creator.number"));
			sic.add(new SelectorItemInfo("creator.name"));
		}
		sic.add(new SelectorItemInfo("createTime"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		} else {
			sic.add(new SelectorItemInfo("lastUpdateUser.id"));
			sic.add(new SelectorItemInfo("lastUpdateUser.number"));
			sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("CU.*"));
		} else {
			sic.add(new SelectorItemInfo("CU.id"));
			sic.add(new SelectorItemInfo("CU.number"));
			sic.add(new SelectorItemInfo("CU.name"));
		}
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("description"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("auditor.*"));
		} else {
			sic.add(new SelectorItemInfo("auditor.id"));
			sic.add(new SelectorItemInfo("auditor.number"));
			sic.add(new SelectorItemInfo("auditor.name"));
		}
		sic.add(new SelectorItemInfo("status"));
		sic.add(new SelectorItemInfo("bizStatus"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("special"));
		sic.add(new SelectorItemInfo("isMainEqm"));
		sic.add(new SelectorItemInfo("isbaoxian"));
		sic.add(new SelectorItemInfo("parent"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("nowAmount"));
		sic.add(new SelectorItemInfo("oldYear"));
		sic.add(new SelectorItemInfo("deadline"));
		sic.add(new SelectorItemInfo("debuger"));
		sic.add(new SelectorItemInfo("cpgjzs"));
		sic.add(new SelectorItemInfo("cpsyqh"));
		sic.add(new SelectorItemInfo("cpsbh"));
		sic.add(new SelectorItemInfo("chuanCheck"));
		sic.add(new SelectorItemInfo("name"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("ssOrgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("ssOrgUnit.id"));
			sic.add(new SelectorItemInfo("ssOrgUnit.number"));
			sic.add(new SelectorItemInfo("ssOrgUnit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("equTypeone.*"));
		} else {
			sic.add(new SelectorItemInfo("equTypeone.id"));
			sic.add(new SelectorItemInfo("equTypeone.number"));
			sic.add(new SelectorItemInfo("equTypeone.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("textType.*"));
		} else {
			sic.add(new SelectorItemInfo("textType.id"));
			sic.add(new SelectorItemInfo("textType.number"));
			sic.add(new SelectorItemInfo("textType.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("jhOrgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("jhOrgUnit.id"));
			sic.add(new SelectorItemInfo("jhOrgUnit.number"));
			sic.add(new SelectorItemInfo("jhOrgUnit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("wxOrgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("wxOrgUnit.id"));
			sic.add(new SelectorItemInfo("wxOrgUnit.number"));
			sic.add(new SelectorItemInfo("wxOrgUnit.name"));
		}
		sic.add(new SelectorItemInfo("model"));
		sic.add(new SelectorItemInfo("size"));
		sic.add(new SelectorItemInfo("weight"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("wxDept.*"));
		} else {
			sic.add(new SelectorItemInfo("wxDept.id"));
			sic.add(new SelectorItemInfo("wxDept.number"));
			sic.add(new SelectorItemInfo("wxDept.name"));
		}
		sic.add(new SelectorItemInfo("qyDate"));
		sic.add(new SelectorItemInfo("serialNumber"));
		sic.add(new SelectorItemInfo("sbStatus"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("unit.*"));
		} else {
			sic.add(new SelectorItemInfo("unit.id"));
			sic.add(new SelectorItemInfo("unit.number"));
			sic.add(new SelectorItemInfo("unit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("eqmType.*"));
		} else {
			sic.add(new SelectorItemInfo("eqmType.id"));
			sic.add(new SelectorItemInfo("eqmType.number"));
			sic.add(new SelectorItemInfo("eqmType.name"));
		}
		sic.add(new SelectorItemInfo("eqmCategory"));
		sic.add(new SelectorItemInfo("innerNumber"));
		sic.add(new SelectorItemInfo("nowStatus"));
		sic.add(new SelectorItemInfo("zzsShortName"));
		sic.add(new SelectorItemInfo("dependable"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("parent.*"));
		} else {
			sic.add(new SelectorItemInfo("parent.id"));
			sic.add(new SelectorItemInfo("parent.number"));
			sic.add(new SelectorItemInfo("parent.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("address.*"));
		} else {
			sic.add(new SelectorItemInfo("address.id"));
			sic.add(new SelectorItemInfo("address.number"));
			sic.add(new SelectorItemInfo("address.name"));
			sic.add(new SelectorItemInfo("address.detailAddress"));
		}
		sic.add(new SelectorItemInfo("location"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("usingDept.*"));
		} else {
			sic.add(new SelectorItemInfo("usingDept.id"));
			sic.add(new SelectorItemInfo("usingDept.number"));
			sic.add(new SelectorItemInfo("usingDept.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("resPerson.*"));
		} else {
			sic.add(new SelectorItemInfo("resPerson.id"));
			sic.add(new SelectorItemInfo("resPerson.number"));
			sic.add(new SelectorItemInfo("resPerson.name"));
		}
		sic.add(new SelectorItemInfo("mader"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("madedCountry.*"));
		} else {
			sic.add(new SelectorItemInfo("madedCountry.id"));
			sic.add(new SelectorItemInfo("madedCountry.number"));
			sic.add(new SelectorItemInfo("madedCountry.name"));
		}
		sic.add(new SelectorItemInfo("madeDate"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("supplier.*"));
		} else {
			sic.add(new SelectorItemInfo("supplier.id"));
			sic.add(new SelectorItemInfo("supplier.number"));
			sic.add(new SelectorItemInfo("supplier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useUnit.id"));
        	sic.add(new SelectorItemInfo("useUnit.number"));
        	sic.add(new SelectorItemInfo("useUnit.name"));
		}
		sic.add(new SelectorItemInfo("reachedDate"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("installer.*"));
		} else {
			sic.add(new SelectorItemInfo("installer.id"));
			sic.add(new SelectorItemInfo("installer.number"));
			sic.add(new SelectorItemInfo("installer.name"));
		}
		sic.add(new SelectorItemInfo("checkDate"));
		sic.add(new SelectorItemInfo("sourceUnit"));
		sic.add(new SelectorItemInfo("portTest"));
		sic.add(new SelectorItemInfo("cityTest"));
		sic.add(new SelectorItemInfo("testDay"));
		sic.add(new SelectorItemInfo("isccCheck"));
		sic.add(new SelectorItemInfo("carGuanSuo"));
		sic.add(new SelectorItemInfo("yibiao"));
		sic.add(new SelectorItemInfo("tzdaNumber"));
		sic.add(new SelectorItemInfo("tzsbStatus"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("asset.*"));
		} else {
			sic.add(new SelectorItemInfo("asset.id"));
			sic.add(new SelectorItemInfo("asset.number"));
			sic.add(new SelectorItemInfo("asset.assetName"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("assetStatus.*"));
		} else {
			sic.add(new SelectorItemInfo("assetStatus.id"));
			sic.add(new SelectorItemInfo("assetStatus.number"));
			sic.add(new SelectorItemInfo("assetStatus.name"));
			sic.add(new SelectorItemInfo("assetStatus.isDefault"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("specialType.id"));
        	sic.add(new SelectorItemInfo("specialType.number"));
        	sic.add(new SelectorItemInfo("specialType.name"));
		}
		sic.add(new SelectorItemInfo("assetValue"));
		sic.add(new SelectorItemInfo("installCost"));
		sic.add(new SelectorItemInfo("TechnologyPar.seq"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("TechnologyPar.*"));
		} else {
		}
		sic.add(new SelectorItemInfo("TechnologyPar.parName"));
		sic.add(new SelectorItemInfo("TechnologyPar.parValue"));
		sic.add(new SelectorItemInfo("TechnologyPar.parInfo"));
		sic.add(new SelectorItemInfo("SpareInfo.seq"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("SpareInfo.*"));
		} else {
		}
		sic.add(new SelectorItemInfo("SpareInfo.materialName"));
		sic.add(new SelectorItemInfo("SpareInfo.speModel"));
    	sic.add(new SelectorItemInfo("SpareInfo.shuliangone"));
    	sic.add(new SelectorItemInfo("SpareInfo.useyong"));
    	sic.add(new SelectorItemInfo("SpareInfo.fachangjia"));
    	sic.add(new SelectorItemInfo("SpareInfo.noteone"));
    	sic.add(new SelectorItemInfo("SpareInfo.attachone"));
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E3.csmingcheng"));
    	sic.add(new SelectorItemInfo("E3.csmodel"));
    	sic.add(new SelectorItemInfo("E3.shuliang"));
    	sic.add(new SelectorItemInfo("E3.power"));
    	sic.add(new SelectorItemInfo("E3.speed"));
    	sic.add(new SelectorItemInfo("E3.chuandong"));
    	sic.add(new SelectorItemInfo("E3.zidong"));
    	sic.add(new SelectorItemInfo("E3.madeFac"));
    	sic.add(new SelectorItemInfo("E3.noteoo"));
		sic.add(new SelectorItemInfo("ccNumber"));
		sic.add(new SelectorItemInfo("tzdaNumber"));
		sic.add(new SelectorItemInfo("cityPeriod"));
		sic.add(new SelectorItemInfo("portPeriod"));
		sic.add(new SelectorItemInfo("code"));
		sic.add(new SelectorItemInfo("engineNumber"));
		sic.add(new SelectorItemInfo("carNumber"));
		sic.add(new SelectorItemInfo("parent"));
		sic.add(new SelectorItemInfo("ratedWeight"));
		sic.add(new SelectorItemInfo("assetValue"));
		sic.add(new SelectorItemInfo("sbdescription"));
		sic.add(new SelectorItemInfo("textDate1"));
		sic.add(new SelectorItemInfo("daytow"));
		sic.add(new SelectorItemInfo("dayone"));
		sic.add(new SelectorItemInfo("telePhoneNumber"));
		sic.add(new SelectorItemInfo("actrueTime"));
		sic.add(new SelectorItemInfo("responsible"));
		sic.add(new SelectorItemInfo("inStreet"));
		sic.add(new SelectorItemInfo("inspectorOne"));
		sic.add(new SelectorItemInfo("gongzuojibie"));
		sic.add(new SelectorItemInfo("kuadu"));
		sic.add(new SelectorItemInfo("xuanbichangdu"));
		sic.add(new SelectorItemInfo("qishengaodu"));
		sic.add(new SelectorItemInfo("dacheguidao"));
		sic.add(new SelectorItemInfo("qizhongliju"));
		sic.add(new SelectorItemInfo("zuida"));
		sic.add(new SelectorItemInfo("zuixiao"));
		sic.add(new SelectorItemInfo("edusudu"));
		sic.add(new SelectorItemInfo("zuzhijigou"));
		sic.add(new SelectorItemInfo("youzhengbianma"));
		return sic;
	}
	
	protected void attachListeners() {
		addDataChangeListener(prmttype);
		addDataChangeListener(prmtasset);
		addDataChangeListener(txtcityPeriod);
		addDataChangeListener(txtportPeriod);
	}

	protected void detachListeners() {
		removeDataChangeListener(prmttype);
		removeDataChangeListener(prmtasset);
		removeDataChangeListener(txtcityPeriod);
		removeDataChangeListener(txtportPeriod);
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	protected void beforeStoreFields(ActionEvent arg0) throws Exception {
	}
	
	/**
	 * zhangjuan
	 * 2014-5-14
	 * 实现功能：校验固定资产卡片类型与设备档案的设备类型是否一致
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
	
          if(editData.getSourceBillId()!=null){
        	  FaCurCardInfo fccInfo = FaCurCardFactory.getRemoteInstance().getFaCurCardInfo(new ObjectUuidPK(editData.getSourceBillId().toString()));
        	  if(fccInfo.getAssetCat().getId() !=null && ((FaCatInfo)prmttype.getData()).getId()!=null){
        	     if(fccInfo.getAssetCat().getId() != ((FaCatInfo)prmttype.getData()).getId()){
        	    	 MsgBox.showInfo("固定资产卡片类型与设备类型不一致！");
     				SysUtil.abort();
        	     }
        	  }
		}
          if(editData.isSpecial()){
        	  if(!editData.isCityTest()&&!editData.isPortTest()&&!editData.isChuanCheck()&&!editData.isCarGuanSuo()&&!editData.isYibiao()){
        		  MsgBox.showInfo("当前设备为检验设备，请勾选检验设备属性的市检、港检、船检、车管所或者仪表！");
   				  SysUtil.abort();
        	  }
        	  if(editData.isCityTest()&&editData.isPortTest()&&editData.isChuanCheck()&&editData.isCarGuanSuo()&&editData.isYibiao()){
        		  MsgBox.showInfo("不能同时勾选市检、港检、船检、车管所和仪表！");
   				  SysUtil.abort();
        	  }
        	  if(editData.isCityTest()){
        		  if(editData.getCityPeriod() ==null){
	        		  MsgBox.showInfo("请填写周期！");
	   				  SysUtil.abort();
        		  }
        	 
        	  }
        	  if(editData.isPortTest()){
        		  if(editData.getCityPeriod() ==null){
	        		  MsgBox.showInfo("请填写周期！");
	   				  SysUtil.abort();
        		  }
        	 
        	  }
        	
          }

          if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
 			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
 		}
     	if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(combosbStatus.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"设备状态"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmttype.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"设备类别"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtssOrgUnit.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"所属单位"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtuseUnit.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"使用单位"});
		}
      	super.verifyInput(e);
	}
	
	
	public void actionRegistChange_actionPerformed(ActionEvent e)throws Exception {
		super.actionRegistChange_actionPerformed(e);
		if(chkspecial.getSelected() ==16){
			 MsgBox.showInfo("检验设备才能变更！");
				SysUtil.abort();
		}
		IUIWindow uiWindow = null;
		UIContext context = new UIContext(this);
		context.put("EquID", editData.getId().toString());
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SpecialChangeEditUI.class.getName(), context, null, OprtState.ADDNEW);
		uiWindow.show(); 
	}
	
	 
	  
	public void actionExcel_actionPerformed(ActionEvent e) throws Exception {

	}
	
	protected void prmtuseUnit_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtuseUnit_dataChanged(e);
		//根据使用单位带出单位所在地址
		if(prmtuseUnit.getValue() != null){
			String id = ((AdminOrgUnitInfo)prmtuseUnit.getData()).getId().toString();
			AdminOrgUnitInfo aoInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(id));
			if(aoInfo.getAddress() != null){
				String id1 = ((AddressInfo)aoInfo.getAddress()).getId().toString();
				AddressInfo addInfo = AddressFactory.getRemoteInstance().getAddressInfo(new ObjectUuidPK(id1));
				prmtaddress.setValue(addInfo);
			}else{
				prmtaddress.setValue(null);
			}
			if(aoInfo.getOrgCode() != null){
				txtzuzhijigou.setText(aoInfo.getOrgCode());
			}else{
				txtzuzhijigou.setText(null);
			}
			
			if(aoInfo.getZipCode() !=null){
				txtyouzhengbianma.setText(aoInfo.getZipCode());
			}else{
				txtyouzhengbianma.setText(null);
			}
		}
	}
	
	private void InitAttactByTable() {
		KDWorkButton btnaddnew = this.kdtSpareInfo_detailPanel.getAddNewLineButton();
		KDWorkButton btninsert = this.kdtSpareInfo_detailPanel.getInsertLineButton();
		KDWorkButton btnremove = this.kdtSpareInfo_detailPanel.getRemoveLinesButton();
		
		if(btnaddnew.getActionListeners()[0]!=null)
			btnaddnew.removeActionListener(btnaddnew.getActionListeners()[0]);
		if(btninsert.getActionListeners()[0]!=null)
			btninsert.removeActionListener(btninsert.getActionListeners()[0]);
		if(btnremove.getActionListeners()[0]!=null)
			btnremove.removeActionListener(btnremove.getActionListeners()[0]);
		
		btnaddnew.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					actionAttachListAddLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		btninsert.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				try {
					actionAttachListInsertLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		btnremove.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				try {
					actionAttachListRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		 this.kdtSpareInfo.getColumn("attachone").getStyleAttributes().setLocked(true);
		 this.kdtSpareInfo.getColumn("attachone").getStyleAttributes().setFontColor(Color.BLUE);
		 this.kdtSpareInfo.getColumn("attachone").getStyleAttributes().setUnderline(true);
	}
	
	public void actionAttachListAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSpareInfo);
	}
	public void actionAttachListInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSpareInfo);
	}
	public void actionAttachListRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSpareInfo);
	}
	
	  private void kdTableAddRow(KDTable table) {
			if(!getOprtState().equals(OprtState.VIEW)){
				IRow row=table.addRow();
				EquIdSpareInfoInfo info=new EquIdSpareInfoInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}
		}
	  
	  
		private void kdTableInsertLine(KDTable table){
			if(!getOprtState().equals(OprtState.VIEW)){
				if(table == null)
		            return;
		        IRow row = null;
		        if(table.getSelectManager().size() > 0)
		        {
		            int top = table.getSelectManager().get().getTop();
		            if(isTableColumnSelected(table))
		                row = table.addRow();
		            else
		                row = table.addRow(top);
		        } else
		        {
		            row = table.addRow();
		        }
		        EquIdSpareInfoInfo info=new EquIdSpareInfoInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}
		}
		protected final boolean TableColumnSelected(KDTable table)
	    {
	        if(table.getSelectManager().size() > 0)
	        {
	            KDTSelectBlock block = table.getSelectManager().get();
	            if(block.getMode() == 4 || block.getMode() == 8)
	                return true;
	        }
	        return false;
	    }
		private boolean confirmRemove(Component comp){
			return MsgBox.isYes(MsgBox.showConfirm2(comp, "是否删除所选分录？"));
		}
		private void kdTableDeleteRow(KDTable table) {
			if(!getOprtState().equals(OprtState.VIEW)){
		        if(table.getSelectManager().size() == 0 || isTableColumnSelected(table)){
		            MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
		            return;
		        }
		        if(confirmRemove(this)){
		            int top = table.getSelectManager().get().getBeginRow();
		            int bottom = table.getSelectManager().get().getEndRow();
		            for(int i = top; i <= bottom; i++){
		                if(table.getRow(top) == null)
		                {
		                    MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
		                    return;
		                }
		                try {
							deleteAttachment(((EquIdSpareInfoInfo)table.getRow(top).getUserObject()).getId().toString());
						} catch (EASBizException e) {
							e.printStackTrace();
						} catch (BOSException e) {
							e.printStackTrace();
						}
		                table.removeRow(top);
		            }
		        }
			}
		}
		
		protected void deleteAttachment(String id) throws BOSException, EASBizException{
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("boID" , id));
			view.setFilter(filter);
			BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
			if(col.size()>0){
				for(int i=0;i<col.size();i++){
					EntityViewInfo attview=new EntityViewInfo();
					FilterInfo attfilter = new FilterInfo();
					
					attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
					attview.setFilter(attfilter);
					BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
					if(attcol.size()==1){
						BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
					}else if(attcol.size()>1){
						BoAttchAssoFactory.getRemoteInstance().delete(filter);
					}
				}
			}
		}
	
	protected void kdtSpareInfo_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2&&
				this.kdtSpareInfo.getColumnKey(e.getColIndex()).equals("attachone")) {
			if(((EquIdSpareInfoInfo)this.kdtSpareInfo.getRow(e.getRowIndex()).getUserObject()).getId()==null){return;};
			String id=((EquIdSpareInfoInfo)this.kdtSpareInfo.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true")){
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else{
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtSpareInfo.getRow(e.getRowIndex()).getCell("attachone").setValue(loadAttachment(id));
		}
	}
	
    protected void storeSupplierAttachList(){
    	
    }
    	
    	
    	protected void loadSupplierAttachList() throws BOSException{
    		for (int i = 0; i <this.kdtSpareInfo.getRowCount(); i++) 
    		{
    			IRow row = this.kdtSpareInfo.getRow(i);
    			if(row.getUserObject()!=null)
    				row.getCell("attachone").setValue(loadAttachment(((EquIdSpareInfoInfo)row.getUserObject()).getId().toString()));
			}
    	}
	
    	
    	protected String loadAttachment(String id) throws BOSException{
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("boID", id));
    		view.setFilter(filter);
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("attachment.name");
    		view.setSelector(sels);
    		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
    		String name="";
    		for(int i=0;i<col.size();i++){
    			name=name+"("+(i+1)+"）."+col.get(i).getAttachment().getName()+"；";
    		}
    		return name;
    	}
	
//	protected void prmtssOrgUnit_dataChanged(DataChangeEvent e)
//			throws Exception {
//		super.prmtssOrgUnit_dataChanged(e);
//		//根据所属组织带出单位所在地址
//		if(prmtssOrgUnit.getValue() != null){
//			String id = ((AdminOrgUnitInfo)prmtssOrgUnit.getData()).getId().toString();
//			AdminOrgUnitInfo aoInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(id));
//			if(aoInfo.getAddress() != null){
//				String id1 = ((AddressInfo)aoInfo.getAddress()).getId().toString();
//				AddressInfo addInfo = AddressFactory.getRemoteInstance().getAddressInfo(new ObjectUuidPK(id1));
//				prmtaddress.setValue(addInfo);
//			}else{
//				prmtaddress.setValue(null);
//			}
//		}
//	}
    	
    	 protected IMetaDataPK getTDQueryPK() {
    	    	return new MetaDataPK("com.kingdee.eas.port.equipment.record.app.EquPrientQuery");
    		}
	
}