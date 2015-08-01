/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;

/**
 * output class name
 */
public class ContractBillFullUI extends AbstractContractBillFullUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillFullUI.class);
    
    /**
     * output class constructor
     */
    public ContractBillFullUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);		
		 FDCHelper.formatTableNumber(tblMain, "amount");
		 FDCHelper.formatTableNumber(tblMain, "originalAmount");
		 FDCHelper.formatTableDate(tblMain, "bookedDate");
		 FDCHelper.formatTableDate(tblMain, "createTime");
		
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("number");
		selector.add("mainContractNumber");
		selector.add("contractpropert");
		selector.add("curproject.id");
		ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)),selector);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("number");
		selector.add("name");
		selector.add("amount");
		selector.add("originalAmount");
		selector.add("hasSettled");
		selector.add("bookedDate");
		selector.add("state");
		selector.add("createTime");
		selector.add("mainContractNumber");
		
		selector.add("currency.name");
		selector.add("contractType.name");
		selector.add("partB.name");
		selector.add("period.number");
		selector.add("period.periodNumber");
		selector.add("period.periodYear");
		selector.add("landDeveloper.name");
		selector.add("respDept.name");
		selector.add("respPerson.name");
		selector.add("creator.name");
		view.setSelector(selector);
		if (!ContractPropertyEnum.SUPPLY.equals(contractBill.getContractPropert())) {
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("mainContractNumber",contractBill.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("curproject.id", contractBill.getCurProject().getId().toString()));
		
		filter.getFilterItems().add(new FilterItemInfo("id", contractId,CompareType.NOTEQUALS));
		
		ContractBillCollection contractBillCollection = ContractBillFactory.getRemoteInstance()
				.getContractBillCollection(view);
		fillTableData(contractBillCollection);
		} else {
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", contractId, CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("number", contractBill.getMainContractNumber()));
			filter.getFilterItems().add(new FilterItemInfo("curproject.id", contractBill.getCurProject().getId().toString()));
			view.setFilter(filter);
			ContractBillCollection contractBillCollection1 = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);

			fillTableData(contractBillCollection1);
		}
	}

	private void fillTableData(ContractBillCollection contractBillCollection) {
		for (int i = 0; i < contractBillCollection.size(); i++) {
			ContractBillInfo info = contractBillCollection.get(i);
			IRow row = this.tblMain.addRow();
			
			row.setUserObject(info.getId().toString());
			row.getCell("id").setValue(info.getId().toString());
			
			row.getCell("bookedDate").setValue(info.getBookedDate());
			
			if(info.getPeriod()!=null){
				row.getCell("period").setValue(info.getPeriod());
			}
			row.getCell("state").setValue(info.getState());
			//2009-1-7 修改"是否结算"字段取值错误
			if(info.isHasSettled()){
				row.getCell("hasSettled").setValue(Boolean.TRUE);				
			}else{
				row.getCell("hasSettled").setValue(Boolean.FALSE);
			}
//			row.getCell("hasSettled").setValue(info.getName());
			if(info.getContractType()!=null){
				row.getCell("contractType").setValue(info.getContractType().getName());
			}

			if(info.getCurrency()!=null){
				row.getCell("currency").setValue(info.getCurrency().getName());
			}
			
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());

			row.getCell("originalAmount").setValue(info.getOriginalAmount());			
			row.getCell("amount").setValue(info.getAmount());
			
			if(info.getLandDeveloper()!=null){
				row.getCell("partA").setValue(info.getLandDeveloper());
			}
			
			if(info.getPartB()!=null){
				row.getCell("partB").setValue(info.getPartB().getName());
			}
			
			if(info.getRespDept()!=null){
				row.getCell("dedutePart").setValue(info.getRespDept().getName());
			}
			if(info.getRespPerson()!=null){
				row.getCell("dedutePerson").setValue(info.getRespPerson().getName());
			}
			if(info.getCreator()!=null){
				row.getCell("creator").setValue(info.getCreator().getName());
			}
			if(info.getCreateTime()!=null){
				row.getCell("createTime").setValue(info.getCreateTime());
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) return ;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, row.getUserObject());
			
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(ContractBillEditUI.class.getName(),
							uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
	
	protected String[] getNotOrderColumns() {
		return new String[]{"attachment","content","isLonelyCal"};
	}

}