package com.kingdee.eas.port.pm.contract.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillListUI;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.client.CompensationBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractSettlementBillContants;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.port.pm.contract.ContractSettlementBillCollection;
import com.kingdee.eas.port.pm.contract.ContractSettlementBillFactory;
import com.kingdee.eas.port.pm.contract.ContractSettlementBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;


public class SettlementDetailHelper {
	private ContractSettlementBillEditUI settleUI=null;
	private KDTable tblCompensationBill=null;
	private KDTable tblGuerdon=null;
	private KDTable tblDeduct=null;
	private KDTable tblSettlementBill=null;
	private  String contractId=null;
	SettlementDetailHelper(ContractSettlementBillEditUI settleUI){
		this.settleUI=settleUI;
		this.tblCompensationBill=settleUI.tblCompensationBill;
		this.tblDeduct = settleUI.tblDeduct;
		this.tblGuerdon = settleUI.tblGuerdon;
		this.tblSettlementBill = settleUI.tblSettlementBill;
		initTable();
		
		freezeTableColumn(tblCompensationBill);
		freezeTableColumn(tblDeduct);
		freezeTableColumn(tblGuerdon);
		freezeTableColumn(tblSettlementBill);
	}
	
	protected void freezeTableColumn(KDTable table) {
		
		if(table!=null){
			
			table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(table.getColumn("createTime")!=null){
				FDCHelper.formatTableDate(table, "createTime");
			}
			if(table.getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(table, "auditTime");
			}
			if(table.getColumn("auditorTime")!=null){
				FDCHelper.formatTableDate(table, "auditorTime");
			}

			if(table.getColumn("amount")!=null){
				FDCHelper.formatTableNumber(table, "amount");
			}
			
			if(table.getColumn("originalAmount")!=null){
				FDCHelper.formatTableNumber(table, "originalAmount");
			}
			
			if(table.getColumn("settlePrice")!=null){
				FDCHelper.formatTableNumber(table, "settlePrice");
			}
			
			if(table.getColumn("qualityGuarante")!=null){
				FDCHelper.formatTableNumber(table, "qualityGuarante");
			}
			
			
			if(table.getColumn("unitPrice")!=null){
				FDCHelper.formatTableNumber(table, "unitPrice");
			}
			
			
			if(table.getColumn("buildArea")!=null){
				FDCHelper.formatTableNumber(table, "buildArea");
			}
		}
	
	}
	
	private void initTable(){
        this.tblCompensationBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblCompensationBill_tableClicked(e);
                } catch (Exception exc) {
                    settleUI.handUIException(exc);
                } finally {
                }
            }

        });
        this.tblDeduct.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	tblDeduct_tableClicked(e);
                } catch (Exception exc) {
                    settleUI.handUIException(exc);
                } finally {
                }
            }
        });
        
        this.tblGuerdon.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	tblGuerdon_tableClicked(e);
                } catch (Exception exc) {
                    settleUI.handUIException(exc);
                } finally {
                }
            }
        });
        
        this.tblSettlementBill.getStyleAttributes().setLocked(true);
        this.tblSettlementBill.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	tblSettlementBill_tableClicked(e);
                } catch (Exception exc) {
                    settleUI.handUIException(exc);
                } finally {
                }
            }
        });
	}
	private void tblCompensationBill_tableClicked(KDTMouseEvent e) {
		if (e.getColIndex() == 0 && isFinalSettle) {
			if (settleUI.getOprtState().equals("ADDNEW")
					|| settleUI.getOprtState().equals("EDIT")) {
				IRow row = this.tblCompensationBill.getRow(e.getRowIndex());
				if (row == null) {
					return;
				}
				ICell cell= row.getCell("select");
				if(cell==null||cell.getUserObject()==null||cell.getUserObject().equals(Boolean.FALSE)){
					return;
				}
				Boolean b = (Boolean) cell.getValue();
				cell.setValue(Boolean.valueOf(!b.booleanValue()));
			}
		}
	}
	
	private void tblGuerdon_tableClicked(KDTMouseEvent e) {
		if (e.getColIndex() == 0 && isFinalSettle) {
			if (settleUI.getOprtState().equals("ADDNEW")
					|| settleUI.getOprtState().equals("EDIT")) {
				IRow row =this.tblGuerdon.getRow(e.getRowIndex());
				if (row == null) {
					return;
				}
				ICell cell= row.getCell("select");
				if(cell==null||cell.getUserObject()==null||cell.getUserObject().equals(Boolean.FALSE)){
					return;
				}
				Boolean b = (Boolean) cell.getValue();
				cell.setValue(Boolean.valueOf(!b.booleanValue()));
			}
		}
	}
	private void tblDeduct_tableClicked(KDTMouseEvent e) {
		if (e.getColIndex() == 0 && isFinalSettle) {
			if (settleUI.getOprtState().equals("ADDNEW")
					|| settleUI.getOprtState().equals("EDIT")) {
				IRow row = this.tblDeduct.getRow(e.getRowIndex());
				if (row == null) {
					return;
				}
				ICell cell= row.getCell("select");
				if(cell==null||cell.getUserObject()==null||cell.getUserObject().equals(Boolean.FALSE)){
					return;
				}
				Boolean b = (Boolean) cell.getValue();
				cell.setValue(Boolean.valueOf(!b.booleanValue()));
			}
		}
	}
	private String getContractId(){
		if(contractId==null){
			contractId = (String) settleUI.getUIContext().get("contractBillId");
			if (contractId == null) {
				String settId = settleUI.getUIContext().get(UIContext.ID).toString();
				if (settId != null) {
					SelectorItemCollection selector=new SelectorItemCollection();
					selector.add("contractBill.id");
					ContractSettlementBillInfo sett;
					try {
						sett = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(settId)),selector);
						contractId = sett.getContractBill().getId().toString();
					} catch (Exception e) {
						settleUI.handUIException(e);
					}
				}
			}
		}
		return contractId;
	}
	
	boolean isFinalSettle = true;
	public void unLock(boolean isFinalSettle)  throws Exception{
		this.isFinalSettle = isFinalSettle ;
//		tblCompensationBill.getStyleAttributes().setLocked(lock);
//		tblDeduct.getStyleAttributes().setLocked(lock);
//		tblGuerdon.getStyleAttributes().setLocked(lock);
		
		fillTabBack(tblCompensationBill);
		fillTabBack(tblDeduct);
		fillTabBack(tblGuerdon);
	}
	
	public void fill(){
		try{
//			fillCollectPanel();
			fillTabCompensation();
			fillTabDeduct();
			fillTabGuerdon();
			fillTabSettlementBill() ;
		}catch(Exception e){
			settleUI.handUIException(e);
		}
	}
	void save() throws EASBizException, BOSException{
		saveTabCompensation();
		saveTabDeduct();
		saveTabGuerdon();
	}
	void fillCollectPanel()  throws Exception{
		try{
//			settleUI.setCursorOfWair();
			settleUI.panelCollection.setLayout(new BorderLayout());
	//		if(true) return;
			UIContext context=new UIContext(settleUI);
			context.put("addSettlePanel",Boolean.FALSE);
			context.put(UIContext.ID, getContractId());
			String uiName=com.kingdee.eas.fdc.contract.client.ContractFullInfoUI.class.getName();
			IUIObject uiObj = UIFactoryHelper.initUIObject(uiName,context, null, "VIEW");
			settleUI.panelCollection.add((CoreUI)uiObj, BorderLayout.CENTER);
		}finally{
//			settleUI.setCursorOfDefault();
		}
	}
	
	private void fillTabBack(KDTable table) throws Exception{
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			
			Boolean isLock =(Boolean) row.getCell("number").getUserObject();
//			row.getCell("select").setUserObject(isLock);
			row.getCell("select").setValue(isLock);
		}
	}
	
	private void fillTabCompensation() throws Exception{}
	private void fillTabDeduct() throws Exception{}
	private void fillTabGuerdon() throws Exception{}

	private void saveTabCompensation() throws EASBizException, BOSException {
		KDTable table=this.tblCompensationBill;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("isCompensated");
		for(int i=0;i<table.getRowCount();i++){
			ICell cell= table.getCell(i, "select");
			if(cell==null||cell.getUserObject()==null||cell.getUserObject().equals(Boolean.FALSE)){
				continue;
			}
			Object value =cell.getValue();
			CompensationBillInfo info=(CompensationBillInfo)table.getRow(i).getUserObject();
			if(value!=null&&!value.equals(Boolean.valueOf(info.isIsCompensated()))){
				info.setIsCompensated(((Boolean)value).booleanValue());
				CompensationBillFactory.getRemoteInstance().updatePartial(info, selector);
			}
		}
	}

	private void saveTabDeduct() throws EASBizException, BOSException {
		KDTable table=this.tblDeduct;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("hasApplied");
		for(int i=0;i<table.getRowCount();i++){
			ICell cell= table.getCell(i, "select");
			if(cell==null||cell.getUserObject()==null||cell.getUserObject().equals(Boolean.FALSE)){
				continue;
			}
			Object value =cell.getValue();
			DeductBillEntryInfo entry=(DeductBillEntryInfo)table.getRow(i).getUserObject();
			if(value!=null&&!value.equals(Boolean.valueOf(entry.isHasApplied()))){
				entry.setHasApplied(((Boolean)value).booleanValue());
				DeductBillEntryFactory.getRemoteInstance().updatePartial(entry, selector);
			}
		}
		
	}

	private void saveTabGuerdon() throws EASBizException, BOSException {
		KDTable table=this.tblGuerdon;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("isGuerdoned");
		for(int i=0;i<table.getRowCount();i++){
			ICell cell= table.getCell(i, "select");
			if(cell==null||cell.getUserObject()==null||cell.getUserObject().equals(Boolean.FALSE)){
				continue;
			}
			Object value =cell.getValue();
			GuerdonBillInfo info=(GuerdonBillInfo)table.getRow(i).getUserObject();
			if(value!=null&&!value.equals(Boolean.valueOf(info.isIsGuerdoned()))){
				info.setIsGuerdoned(((Boolean)value).booleanValue());
				GuerdonBillFactory.getRemoteInstance().updatePartial(info, selector);
			}
		}
	}
	
	//颜色
	/**
	 * 不可编辑色
	 */
	public static final Color cantEditColor = new Color(0xE8E8E3);
	/**
	 * 警告色
	 */
	public static final Color warnColor = new Color(0xFFEA67);
	
	//结算单列表
	private void fillTabSettlementBill() throws Exception{
		tblSettlementBill.removeRows();
		
	 	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", getContractId()));
    	view.setFilter(filter);
    
    	SelectorItemCollection selectors = genBillQuerySelector();
    	view.getSelector().addObjectCollection(selectors);
    	
    	view.getSorter().add(new SorterItemInfo("createTime"));
    	
		ContractSettlementBillCollection contractSettlementBillCollection = ContractSettlementBillFactory
			.getRemoteInstance().getContractSettlementBillCollection(view);
		for (Iterator iter = contractSettlementBillCollection.iterator(); iter
				.hasNext();) {
			ContractSettlementBillInfo element = (ContractSettlementBillInfo) iter.next();
		
//			String contractId = element.getContractBill().getId().toString();
//			EntityViewInfo conView = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			conView.setFilter(filter);
//			filter.getFilterItems().add(
//					new FilterItemInfo("contract.Id", contractId));
			IRow row = tblSettlementBill.addRow();
		
			row.getCell("bookedDate").setValue(element.getBookedDate());
			row.getCell("period").setValue(element.getPeriod());
			
			row.getCell(ContractSettlementBillContants.COL_ID).setValue(
					element.getId().toString());
			row.getCell(ContractSettlementBillContants.COL_STATE).setValue(
					element.getState());
			row.getCell(ContractSettlementBillContants.COL_NUMBER).setValue(
					element.getNumber());
			row.getCell(ContractSettlementBillContants.COL_BILLNAME).setValue(
					element.getName());
		
		//	row.getCell(ContractSettlementBillContants.COL_CONTRACTNUMBER)
		//			.setValue(element.getContractBill().getNumber());
		//	row.getCell(ContractSettlementBillContants.COL_CONTRACTNAME)
		//			.setValue(element.getContractBill().getName());
		
			if (element.getOriginalAmount() != null
					&& FDCHelper.ZERO.compareTo(element.getOriginalAmount()) != 0) {
				row.getCell("originalAmount")
						.setValue(element.getCurSettlePrice());
			}
			
			if (element.getSettlePrice() != null
					&& FDCHelper.ZERO.compareTo(element.getSettlePrice()) != 0) {
				row.getCell(ContractSettlementBillContants.COL_SETTLEPRICE)
						.setValue(element.getCurSettlePrice());
			}
		/**
		 * 需求约定：结算列表显示每个结算单各自的保修金，而非累计保修金   by Cassiel_peng
		 */
//			if (element.getQualityGuarante() != null
//					&& FDCHelper.ZERO.compareTo(element.getQualityGuarante()) != 0) {
//				row.getCell(ContractSettlementBillContants.COL_QUALITYGUARANTE)
//						.setValue(element.getQualityGuarante());
//			}
			if (element.getGuaranteAmt() != null
					&& FDCHelper.ZERO.compareTo(element.getGuaranteAmt()) != 0) {
				row.getCell(ContractSettlementBillContants.COL_QUALITYGUARANTE)
						.setValue(element.getGuaranteAmt());
			}
			if (element.getBuildArea() != null
					&& FDCHelper.ZERO.compareTo(element.getBuildArea()) != 0) {
				row.getCell(ContractSettlementBillContants.COL_BUILDAREA)
						.setValue(element.getBuildArea());
			}
			if (element.getUnitPrice() != null
					&& FDCHelper.ZERO.compareTo(element.getUnitPrice()) != 0) {
				row.getCell(ContractSettlementBillContants.COL_UNITPRICE)
						.setValue(element.getUnitPrice());
			}
			row.getCell(ContractSettlementBillContants.COL_INFOPRICE).setValue(
					element.getInfoPrice());
		
			row.getCell(ContractSettlementBillContants.COL_GETFEECRITERIA)
					.setValue(element.getGetFeeCriteria());
//			row.getCell(ContractSettlementBillContants.COL_ISFINALSETTLE)
//					.setValue(element.getIsFinalSettle());
		
			row.getCell(ContractSettlementBillContants.COL_CREATOR).setValue(
					element.getCreator().getName());
			row.getCell(ContractSettlementBillContants.COL_CREATETIME)
					.setValue(element.getCreateTime());
		
			if (element.getAuditor() != null)
				row.getCell(ContractSettlementBillContants.COL_AUDITOR)
						.setValue(element.getAuditor().getName());
			row.getCell("auditorTime").setValue(element.getAuditTime());
		
			row.getCell(ContractSettlementBillContants.COL_DESC).setValue(
					element.getDescription());
		
			if (element.getVoucher() != null)
				row.getCell(ContractSettlementBillContants.COL_VOUCHERNUMBER)
						.setValue(element.getVoucher().getNumber());
			
			row.getCell(FDCBillListUI.COL_DATE).setValue(element.getBookedDate());
			row.getCell(FDCBillListUI.COL_PERIOD).setValue(element.getPeriod());
		}
	}
	
	private void tblSettlementBill_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			UIContext uiContext = new UIContext(this);
			
			IRow row =this.tblSettlementBill.getRow(e.getRowIndex());
			if(row!=null){
				String id = (String)row.getCell("id").getValue();
				uiContext.put(UIContext.ID, id);
				
		        IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(settleUI.getEditUIName(), uiContext, null,
		                    OprtState.VIEW);		        

		        uiWindow.show();
			}			
		}
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");

		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		selectors.add("creator.name");
		selectors.add("auditor.name");
		selectors.add("voucher.number");
		
		selectors.add("period.number");
		selectors.add("period.periodNumber");
		selectors.add("period.periodYear");
		
		return selectors;
	}
}
