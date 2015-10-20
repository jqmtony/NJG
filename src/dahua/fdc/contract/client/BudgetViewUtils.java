package com.kingdee.eas.fdc.contract.client;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillFactory;

/**
 * 预算查看工具类<br>
 * Author ： 马拴宝<br>
 * Date ：2010/09/20<br>
 */
public class BudgetViewUtils {

	/*** 参数KEY定义 ***/
	public static final String KEY_CURPROJECT = "curProject";
	public static final String KEY_BILLNAME = "billName";
	public static final String KEY_BILLTYPE = "bill_Type";
	public static final String KEY_BILLID = "ID";
	public static final String KEY_CONTID = "contractId";
	public static final String KEY_BIZDATE = "bizDate";
	public static final String KEY_CURPORJECT="curProject";
	public static final String KEY_CURDEPT = "curDept";
	public static final String KEY_FILTERS = "filters";
	
	public static final String BILL_PAYREQUEST ="PayRequestBill";
	public static final String BILL_PAYMENTBILL="PaymentBill";
	
	// 打开的预算查看窗口类全名
	public static final String BILL_NAME = BudgetViewListUI.class.getName();

	public BudgetViewUtils(){
	}
	
	/**
	 * 显示预算查看窗口，数据未处理
	 * @param uiContext
	 * @throws Exception
	 */
//	public static void showBudgetViewUI(Map uiContext)throws Exception{
//		String billType = uiContext.get(KEY_BILLTYPE).toString();
//		String billId = uiContext.get(KEY_BILLID).toString();
//		if(BILL_PAYREQUEST.equals(billType)){
//			dealPayPlanBillData(uiContext,billId);
//		}
//		else if(BILL_PAYMENTBILL.equals(billType)){
//			dealPaymentBillData(uiContext,billId);
//		}
//		showModelUI(uiContext);
//	}
	
	/**
	 * 处理付款单数据
	 * @param dataMap
	 * @param billId
	 */
//	static void dealPaymentBillData(Map dataMap,String billId)throws Exception{
//		
//		IPaymentBill iPayment = getPaymentInterface();
//		PaymentBillCollection colls = null;
//		PaymentBillInfo info = null;
//		PayRequestBillInfo payReqInfo = null;
//		
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",billId));
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add(new SelectorItemInfo("id"));
//		sic.add(new SelectorItemInfo("bizDate"));
//		sic.add(new SelectorItemInfo("contractBillId"));
//		sic.add(new SelectorItemInfo("fdcPayReqID"));
//		
//		addSICByProp(sic,"curProject");
//		
//
//		EntityViewInfo view = new EntityViewInfo();
//		view.setSelector(sic);
//		view.setFilter(filter);
//		
//		colls = iPayment.getPaymentBillCollection(view);
//		if(colls.isEmpty()){
//			return;
//		}
//		info = colls.get(0);
//		String payReqId = info.getFdcPayReqID();
//		payReqInfo = getPayReqInfo(payReqId);
//		
//		dataMap.put(BudgetViewUtils.KEY_CONTID, info.getContractBillId());
//		dataMap.put(BudgetViewUtils.KEY_BIZDATE, info.getBizDate());
////		dataMap.put(BudgetViewUtils.KEY_CURPORJECT,info.getCurProject());
//		dataMap.put(BudgetViewUtils.KEY_CURDEPT, getDeptment(payReqInfo));
//		
//		if (payReqInfo != null) {
//			String payPlanId = "";
//			if (payReqInfo.getFdcDepConPayPlan() != null) {
//				payPlanId = payReqInfo.getFdcDepConPayPlan().getId().toString();
//				FDCDepConPayPlanBillInfo plan = payReqInfo.getFdcDepConPayPlan();
//				SelectorItemCollection sic2 = new SelectorItemCollection();
//				sic.add("id");
//				sic.add("number");
//				sic.add("name");
//				plan = FDCDepConPayPlanBillFactory.getRemoteInstance()
//						.getFDCDepConPayPlanBillInfo(
//								new ObjectUuidPK(plan.getId()), sic2);
//				dataMap.put(BudgetViewUtils.KEY_CURPORJECT, plan);
//			} else if (payReqInfo.getNewContractPayPlan() != null) {
//				payPlanId = payReqInfo.getNewContractPayPlan().getId().toString();
//				
//				INewContractPayPlan dao = NewContractPayPlanFactory.getRemoteInstance();
//				SelectorItemCollection sic2 = new SelectorItemCollection();
//				sic2.add("id");
//				sic2.add("name");
//				sic2.add("number");
//				sic2.add("contractBill.respDept.id");
//				sic2.add("contractBill.respDept.name");
//				sic2.add("contractBill.respDept.number");
//				NewContractPayPlanInfo info2 = dao.getNewContractPayPlanInfo(new ObjectUuidPK(payPlanId), sic2);
//				dataMap.put(BudgetViewUtils.KEY_CURPORJECT,info2);
//			}else{
//				MsgBox.showInfo("当前合同没有编制付款计划，不能查看预算！");
//				SysUtil.abort();
//			}
//			dataMap.put("payPlanId", payPlanId);
//		}
//	}
	
	/**
	 * 获取付款申请单数据
	 * @param dataMap
	 * @param billId
	 */
//	static void dealPayPlanBillData(Map dataMap,String billId)throws Exception{
//		PayRequestBillInfo info = getPayReqInfo(billId);
//		
//		dataMap.put(BudgetViewUtils.KEY_CONTID, info.getContractId());
//		dataMap.put(BudgetViewUtils.KEY_BIZDATE,info.getBookedDate());
//		dataMap.put(BudgetViewUtils.KEY_CURPORJECT,info.getCurProject());
//		dataMap.put(BudgetViewUtils.KEY_CURDEPT, getDeptment(info));
//	}
	
	/**
	 * 获取入款申请单
	 * @param billId
	 * @return
	 * @throws Exception
	 */
	static PayRequestBillInfo getPayReqInfo(String billId)throws Exception{
		IPayRequestBill iPayReq = getPayReqInterface();
		PayRequestBillCollection colls = null;
		PayRequestBillInfo info = null;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billId));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("bookedDate"));
		sic.add(new SelectorItemInfo("contractId"));
		sic.add("fdcdepconpayplan");
		sic.add("newcontractpayplan");
		addSICByProp(sic,"curProject");
		addSICByProp(sic,"fdcDepConPayPlan");
		addSICByProp(sic,"fdcDepConPayPlan.deptment");
		addSICByProp(sic,"newContractPayPlan.contractBill.respDept");
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(sic);
		view.setFilter(filter);
		
		colls = iPayReq.getPayRequestBillCollection(view);
		if(!colls.isEmpty()){
			info = colls.get(0);
		}
		return info;
	}
	
	/**
	 * 获取付款申请单的业务部门
	 * @param info
	 * @return
	 */
//	static AdminOrgUnitInfo getDeptment(PayRequestBillInfo info)throws Exception{
//		// 处理计划部门
//		AdminOrgUnitInfo dept = null;
//		if (info.getFdcDepConPayPlan() != null) {
//			String payPlanId = info.getFdcDepConPayPlan().getId().toString();
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add("id");
//			sic.add("name");
//			sic.add("number");
//			sic.add("deptment.id");
//			sic.add("deptment.name");
//			sic.add("deptment.number");
//			IFDCDepConPayPlanBill dao = FDCDepConPayPlanBillFactory.getRemoteInstance();
//			
//			FDCDepConPayPlanBillInfo data = dao.getFDCDepConPayPlanBillInfo(new ObjectUuidPK(payPlanId),sic);
//			dept = data.getDeptment();
//		} else if (info.getNewContractPayPlan() != null) {
//			String payPlanId = info.getNewContractPayPlan().getId().toString();
//			INewContractPayPlan dao = NewContractPayPlanFactory.getRemoteInstance();
//			
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add("id");
//			sic.add("name");
//			sic.add("number");
//			sic.add("contractBill.respDept.id");
//			sic.add("contractBill.respDept.name");
//			sic.add("contractBill.respDept.number");
//			NewContractPayPlanInfo data = dao.getNewContractPayPlanInfo(new ObjectUuidPK(payPlanId), sic);
//			ContractBillInfo contract = data.getContractBill();
//			if (contract != null) {
//				dept = contract.getRespDept();
//			}
//		}
//		return dept;
//	}
	public static IPayRequestBill getPayReqInterface()throws Exception{
		return PayRequestBillFactory.getRemoteInstance();
	}
	
	public static IPaymentBill getPaymentInterface()throws Exception{
		return PaymentBillFactory.getRemoteInstance();
	}
	/**
	 * 显示预算查看窗口，数据已处理
	 * @param uiContext
	 * @throws Exception
	 */
	public static void showModelUI(Map uiContext)throws Exception {
		String uiName = BILL_NAME;
		String uiModel = UIFactoryName.EDITWIN;
		showUI(uiModel,uiName,uiContext);
	}

	/**
	 * 打开指定的UI
	 * @param uiModel 打开模式
	 * @param uiName 窗口名称
	 * @param uiContext 上下文参数
	 * @throws Exception
	 */
	public static void showUI(String uiModel, String uiName, Map uiContext)
			throws Exception {

		IUIWindow uiWindow = UIFactory.createUIFactory(uiModel)
									  .create(uiName,uiContext,null,OprtState.VIEW);
		uiWindow.show();
	}
	
	/**
	 * 根据一个连接属性生成一个包含id,name,number的Selector
	 * @param prop
	 * @return
	 */
	public static void addSICByProp(SelectorItemCollection sic,String prop){
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add(prop+".id");
		selectors.add(prop+".name");
		selectors.add(prop+".number");
		sic.addObjectCollection(selectors);
	}
	/**
	 * 获取指定日期的第一天
	 * @param date
	 * @return 日期如2010-5-1 00:00:00.000
	 * @see Calendar#roll(int, int)
	 */
	public static Date getFirstDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE,0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}
	
	
	/**
	 * 获取指定日期的最后一天
	 * @param date
	 * @return 日期如：2010-5-31 23:59:59.999
	 * @see Calendar#roll(int, int)
	 */
	public static Date getLastDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.roll(Calendar.DAY_OF_MONTH,-1);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MILLISECOND, 999);
		
		return ca.getTime();
	}
}
