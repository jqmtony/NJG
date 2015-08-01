/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillExecuteDataHander;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractExecFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractExecuteData;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同成本信息
 */
public class ContractCostFullInfoUI extends AbstractContractCostFullInfoUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	/**
	 * output class constructor
	 */
	public ContractCostFullInfoUI() throws Exception {
		super();
	}
		
	private String getResouce(String resName) {
		return EASResource.getString(resourcePath, resName);
	}
	/**
	 * 当数据为0时应该不显示在单元格中  by Cassiel_peng
	 */
	private Object getValue(BigDecimal value){
    	return value.compareTo(FDCHelper.ZERO)!=0?value:null;
    }
	
	public void onLoad() throws Exception {
		
		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
				new MetaDataPK("com.kingdee.eas.fdc.contract.ContractCostFullInfoUI"),
				new MetaDataPK("ActionOnLoad") )){
//			throw new  Permission//AccountBooksException(AccountBooksException.NOSWITCH,new Object[]{scheme.getName()});
			MsgBox.showError(this,"没有成本信息查看权限");
			abort();
		}
		
		super.onLoad();
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		tblMain.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					e.setCancel(true);
				}
			}
		});
		this.tblMain.getColumn("amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.*"));
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)),sic);
		IRow row = this.tblMain.addRow();
		row.getCell("text").setValue(getResouce("contractAmount"));
		row.getCell("amount").setValue(FDCHelper.toBigDecimal(contract.getAmount(), 2));
		
		BigDecimal contractAmount = contract.getAmount();
		
		
		//增加了参数是否包含变更金额 
		boolean isIncludeChangeAudit = FDCUtils.isIncludeChangeAudit(null);
		BigDecimal changeTotal = FDCHelper.ZERO;
		Map changeMap  = null;
		EntityViewInfo view = null;
		FilterInfo filter = null;
		BigDecimal total = FDCHelper.ZERO;
		
			view = new EntityViewInfo();
			view.getSelector().add("amount");
			view.getSelector().add("balanceAmount");
			view.getSelector().add("hasSettled");
			view.getSelector().add("changeType.id");
			filter = new FilterInfo();
			
			//过滤到未审批的变更单
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.Id", contractId));
			
			 filter.getFilterItems().add(new FilterItemInfo("state","\'"+FDCBillStateEnum.ANNOUNCE_VALUE+"\','"+FDCBillStateEnum.VISA_VALUE+"\',\'"+FDCBillStateEnum.AUDITTED_VALUE+"\'",CompareType.INCLUDE));
		
			  view.setFilter(filter);
			
			ContractChangeBillCollection changes = ContractChangeBillFactory
					.getRemoteInstance().getContractChangeBillCollection(view);
			total = FDCHelper.toBigDecimal(contract.getAmount(),2);
			if (total == null) {
				total = FDCHelper.ZERO;
			}
		    changeMap = new HashMap();
			for (int i = 0; i < changes.size(); i++) {
				ContractChangeBillInfo change = changes.get(i);
				if(change.getChangeType()==null){
					continue;
				}
				String typeId = change.getChangeType().getId().toString();
				BigDecimal changeAmount = FDCHelper.toBigDecimal(change.getAmount(),2);
				if(change.isHasSettled()){
					changeAmount=FDCHelper.toBigDecimal(change.getBalanceAmount(),2);
				}
				if(changeAmount==null){
					changeAmount=FDCHelper.ZERO;
				}
				if (changeMap.containsKey(typeId)) {
					BigDecimal sum = (BigDecimal) changeMap.get(typeId);
					changeMap.put(typeId, FDCHelper.toBigDecimal(sum.add(changeAmount),2));
				} else {
					changeMap.put(typeId, changeAmount);
				}
				changeTotal = FDCHelper.toBigDecimal(changeTotal.add(changeAmount),2);
			}
		
		row = this.tblMain.addRow();
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(this.getResouce("contractChange"));
		treeNode.setTreeLevel(0);
		treeNode.setCollapse(false);
		treeNode.setHasChildren(true);
		row.getCell("text").setValue(treeNode);
		row.getCell("text").getStyleAttributes().setLocked(false);
		row.getCell("amount").setValue(changeTotal);

		total = FDCHelper.toBigDecimal(total.add(changeTotal),2);

		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		ChangeTypeCollection types = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
		for (int i = 0; i < types.size(); i++) {
			ChangeTypeInfo info = types.get(i);
			row = this.tblMain.addRow();
			treeNode = new CellTreeNode();
			treeNode.setValue(info.getName());
			treeNode.setTreeLevel(1);
			row.getCell("text").setValue(treeNode);
			row.getCell("amount").setValue(
					changeMap.get(info.getId().toString()));
		}
		
		/**
		 * 合同未结算之前,"合同最新造价"应该取合同的造价和合同的变更之和，如上文代码
		 * 合同结算之后,应该取最终结算单的累计结算金额。   --需求约定
		 * 下文代码直接取结算单的结算造价,并且在没有加上条件"合同已结算"与"结算单是最终结算单"的
		 * 前提下直接使用ContractSettlementBillInfo info = settles.get(0);来取值。
		 * 取出的值怎么会正确，晕死！ by Cassiel_peng
		 */	
		
		BigDecimal settleAmount = null;
		if (contract.isHasSettled()) {
			view = new EntityViewInfo();
//			view.getSelector().add("settlePrice");
			view.getSelector().add("totalSettlePrice");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.Id", contractId));
			// by Cassiel
			filter.getFilterItems().add(new FilterItemInfo("isSettled",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle",Boolean.TRUE));
			ContractSettlementBillCollection settles = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillCollection(
							view);
			if (settles.size() > 0) {
				ContractSettlementBillInfo info = settles.get(0);
//				settleAmount = info.getSettlePrice();
				settleAmount=info.getTotalSettlePrice();
			}
		}
		row = this.tblMain.addRow();
		row.getCell("text").setValue(this.getResouce("settleAdjust"));
		//结算调整=结算造价本位币-合同本位币-合同变更
		if (settleAmount != null) {
			row.getCell("amount").setValue(FDCHelper.toBigDecimal(settleAmount.subtract(total),2));
//			row.getCell("amount").setValue(FDCHelper.subtract(settleAmount, total));
		}
		
		//写入合同最新造价
		
		row = this.tblMain.addRow();
		row.getCell("text").setValue(this.getResouce("total"));
		if (settleAmount != null) {
				row.getCell("amount").setValue(settleAmount);
		} else {
			   if(isIncludeChangeAudit){
				   row.getCell("amount").setValue(total);
			   }else{
				   row.getCell("amount").setValue(contractAmount);
			   }
			   
		}
		/**
		 * 成本信息界面不能完全体现"成本"信息，缺少合同拆分成本、已实现产值/结算值、已付款、尚欠款等成本信息   by Cassiel_peng
         *
		 */
		FDCSQLBuilder builder=new FDCSQLBuilder();
		BigDecimal projectPriceInContract = FDCHelper.ZERO;//累计合同内工程款
		BigDecimal settleAmt=FDCHelper.ZERO;//已实现产值
		BigDecimal finalSettAmt=FDCHelper.ZERO;//最终结算金额
		BigDecimal notAllPay=FDCHelper.ZERO;//完工未付款
		if(contract.isHasSettled()){//如果是最终结算
			builder.clear();
			builder.appendSql("select  FSettlePrice \n ");
			builder.appendSql(" from t_con_contractSettlementbill where FContractBillId=?  and FIsSettled=1 and FIsFinalSettle=1 and fstate='4AUDITTED' \n ");
			builder.addParam(contractId);
			IRowSet rowSet1=builder.executeQuery();
			if(rowSet1.next()){
				settleAmt=FDCHelper.toBigDecimal(rowSet1.getBigDecimal("FSettlePrice"), 2);
				finalSettAmt=settleAmt;
			}
		}else{//非最终结算
			builder.clear();
			builder.appendSql("select sum(FCurSettlePrice) FSettlePrice  from t_con_contractSettlementbill \n ");
			builder.appendSql(" where FContractBillId=? and FIsSettled=0 and FIsFinalSettle=0 and fstate='4AUDITTED' \n ");
			builder.addParam(contractId);
			IRowSet rowSet2=builder.executeQuery();
			if(rowSet2.next()){
				settleAmt=FDCHelper.toBigDecimal(rowSet2.getBigDecimal("FSettlePrice"),2);
			}
		}
		
		builder.clear();
		
		// 见ContractBillExecuteDataHander.calcNotPay()方法注释，如下：
		// 按周鹏2010-12-24日需求， 完工未付款与未付款均减合同内工程款而不是实际已付金额
		builder.appendSql("select sum(FprojectPriceInContract) projectPriceInContract from T_CAS_PaymentBill \n ");
		builder.appendSql("where  FContractBillId=?  \n ");
	    //改成已付款状态的付款单的累计付款金额 
		builder.appendSql("and FBillStatus =15 \n");
		builder.addParam(contractId);
		IRowSet rowSet3=builder.executeQuery();
		if(rowSet3.next()){
			projectPriceInContract = FDCHelper.toBigDecimal(rowSet3.getBigDecimal("projectPriceInContract"), 2);
		}		
		
		boolean isMoreSett = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER);
		boolean allNotPaidParam = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER_ALLNOTPAID);
		BigDecimal completeProjectAmount = FDCHelper.ZERO; // 累计已经完工
		if(contract.getCurProject() != null && contract.getCurProject().getFullOrgUnit() != null){
			Set set = new HashSet();
			set.add(contractId);
			Map completePrjAmtMap = ContractExecFacadeFactory.getRemoteInstance()._getCompleteAmt(
					contract.getCurProject().getFullOrgUnit().getId().toString(), set);
			if (completePrjAmtMap.get(contractId) != null) {
				completeProjectAmount = FDCHelper.toBigDecimal(completePrjAmtMap.get(contractId));
			}
		}
		ContractExecuteData data = new ContractExecuteData();
		data.setContract(contract);
		data.setTotalSettPrice(settleAmount);
		if (contract.isHasSettled()) {
			data.setContractLastAmount(settleAmt);
		} else {
			data.setContractLastAmount(total);
		}
		data.setCompleteProjectAmount(completeProjectAmount);
		data.setProjectPriceInContract(projectPriceInContract);
		ContractBillExecuteDataHander.calcNotPay(data, isMoreSett, allNotPaidParam);
		notAllPay = data.getCompleteNotPay();
		
		row=tblMain.addRow();
		row.getCell("text").setValue(getResouce("totalSettPrice"));
		row.getCell("amount").setValue(getValue(settleAmt));
		
		/**
		 *增加累计完工工程量
		 **/
		row= tblMain.addRow();
		row.getCell("text").setValue(getResouce("totalworkamount"));
		row.getCell("amount").setValue(completeProjectAmount);
		
		
		row=tblMain.addRow();
		row.getCell("text").setValue(getResouce("finalSettAmt"));
		row.getCell("amount").setValue(getValue(finalSettAmt));
		
		/**
		 * 增加 “已付款” 
		 */
		builder.clear();
		builder.appendSql("select sum(FActPayLocAmt) hasPayAmt from T_CAS_PaymentBill \n ");
		builder.appendSql("where  FContractBillId=?  \n ");
		//改成已付款状态的付款单的累计付款金额 
		builder.appendSql("and FBillStatus =15 \n");
		builder.addParam(contractId);
		IRowSet rowSet4 = builder.executeQuery();
		BigDecimal hasPayAmt = FDCHelper.ZERO;
		if (rowSet4.next()) {
			hasPayAmt = FDCHelper.toBigDecimal(rowSet4.getBigDecimal("hasPayAmt"), 2);
		}
		row=tblMain.addRow();
		row.getCell("text").setValue(getResouce("hasPayAmt"));
		row.getCell("amount").setValue(getValue(hasPayAmt));
		
		/**
		 * 增加累计发票金额 
		 * 取数公式： 已付款状态的付款单的累计发票金额。
		 */
		row= tblMain.addRow();
		row.getCell("text").setValue(getResouce("totalinvoiceamount"));
		builder.clear();
		builder.appendSql("select isnull(sum(isnull(finvoiceAmt,0)),0) from t_con_payrequestbill ");
		builder.appendSql("where fnumber in (select FFdcPayReqNumber from T_CAS_PaymentBill where fbillstatus=15 and fcontractbillid=t_con_payrequestbill.fcontractid) ");
		builder.appendSql("and fcontractid = ? ");
		builder.addParam(contractId);
		RowSet rs = builder.executeQuery();
		while(rs.next()){
			row.getCell("amount").setValue(rs.getBigDecimal(1));
		}
		
		row=tblMain.addRow();
		row.getCell("text").setValue(getResouce("notAllPay"));
		row.getCell("amount").setValue(getValue(notAllPay));		
	}
}