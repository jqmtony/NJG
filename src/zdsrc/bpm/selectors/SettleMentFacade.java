package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.derby.iapi.store.raw.Compensation;
import org.omg.CORBA.Object;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.ContractsettlementAssEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementAssEntryInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementJlEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementJlEntryInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.CompensationBill;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.be.ws.PaymentInfo;

import elite.lang.Decimal;

public class SettleMentFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ContractSettlementBillInfo Info = (ContractSettlementBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ContractSettlementBillFactory.getLocalInstance(ctx)
						.getContractSettlementBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[2] = "根据单据Selectors获取对象数据，请检查Selectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				if ("1".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.AUDITTED);
					else {
						str[2] = "审批通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if ("0".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.INVALID);
					else {
						str[2] = "审批不通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.BACK);
					else {
						str[2] = "审批打回失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if ("3".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "撤销失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				String sql = " update t_con_contractbill set fState='"
						+ Info.getState().getValue() + "' where fid='"
						+ Info.getId() + "'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			} catch (BOSException e) {
				str[2] = "根据单据state值更新状态sql失败，请检查getState方法是否有值,并查看服务器log日志！";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同结算单据ID：" + Info.getId() + "; 编号："
						+ Info.getNumber());
				log.setDescription("BPM结果：" + processInstanceResult
						+ "; EAS结果:" + str[0] + "; 错误信息" + str[1] + str[2]);
				log.setSimpleName("BPM流程号：" + procInstID + ";BPM反馈信息:"
						+ strComment);
				log.setBeizhu("调用接口方法：ApproveClose");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
		ContractSettlementBillInfo Info = (ContractSettlementBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = ContractSettlementBillFactory.getLocalInstance(ctx)
						.getContractSettlementBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				xml.append("<DATA>\n");
				xml.append("<Orgunit>"
						+ StringUtilBPM.isNULl(Info.getOrgUnit().getName())
						+ "</Orgunit>\n"); // 组织
				xml.append("<CurProject>"
						+ StringUtilBPM.isNULl(Info.getCurProject()
								+ "</CurProject>\n")); // 工程项目
				// xml.append("<ApplyDate>"
				// + dateFormat.format(Info.getCreateTime())
				// + "</ApplyDate>\n");
				xml.append("<number>审-"
						+ StringUtilBPM.isNULl(Info.getContractBillNumber())
						+ "</number>\n"); // --自己定义的number结算单编码
				xml.append("<ContractBillNumber>"
						+ StringUtilBPM.isNULl(Info.getContractBillNumber())
						+ "</ContractBillNumber>\n"); // 合同编码
				xml.append("<ContractBill>"
						+ StringUtilBPM
								.isNULl(Info.getContractBill().getName())
						+ "</ContractBill>\n");// 合同名称
				xml.append("<BizDate>" + dateFormat.format(Info.getBizDate())
						+ "</BizDate>\n"); // 业务日期
				xml.append("<Currency>" + Info.getCurrency().getName()
						+ "</Currency>\n"); // 币别
				xml.append("<constructPrice>" + Info.getConstructPrice()
						+ "</constructPrice>\n"); // 施工方报审价
				xml.append("<exchangerate>" + Info.getExchangeRate()
						+ "</exchangerate>\n");
				xml.append("<settlePrice>" + Info.getSettlePrice()
						+ "</settlePrice>\n"); // 结算造价原币
				xml.append("<unitPrice>" + Info.getUnitPrice()
						+ "</unitPrice>\n"); // 单位造价
				xml.append("<buildArea>" + Info.getBuildArea()
						+ "</buildArea>\n"); // 建筑面积
				xml.append("<getFeeCriteria>" + Info.getGetFeeCriteria()
						+ "</getFeeCriteria>\n"); // 取费标准
				xml.append("<infoPrice>" + Info.getInfoPrice()
						+ "</infoPrice>\n"); // 信息价
				xml.append("<qualitytime>" + Info.getQualityTime()
						+ "</qualitytime>\n"); // 保修期限
				xml.append("<qulityguaranterate>"
						+ Info.getQualityGuaranteRate()
						+ "</qulityguaranterate>\n"); // 保修金比例
				xml.append("<guaranteAmt>" + Info.getGuaranteAmt()
						+ "</guaranteAmt>\n"); // 保修金
				xml.append("<totalOriginalAmount>"
						+ Info.getTotalOriginalAmount()
						+ "</totalOriginalAmount>\n"); // 累计结算原币
				xml.append("<totalsettlePrice>" + Info.getTotalSettlePrice()
						+ "</totalsettlePrice>\n"); // 累计结算本币
				xml.append("<description>" + Info.getDescription()
						+ "</description>\n"); // 备注
				xml.append("<isFinalSettle>" + Info.getIsFinalSettle()
						+ "</isFinalSettle>\n");   // 是否最终结算
				xml.append("<Creator>" 
						+ Info.getCreator() + "</Creator>\n"); // 制单人

				xml.append("<PaymentBill>\n"); // 付款信息
				EntityViewInfo Paymentavevi = new EntityViewInfo();
				FilterInfo Paymentavfilter = new FilterInfo();
				Paymentavfilter.getFilterItems().add(
						new FilterItemInfo("contractNum", Info
								.getContractBill()));
				Paymentavevi.setFilter(Paymentavfilter);
				PaymentBillCollection Paymentmyavc = PaymentBillFactory
						.getRemoteInstance().getPaymentBillCollection(
								Paymentavevi);
				for (int i = 0; i < Paymentmyavc.size(); i++) {
					PaymentBillInfo payInfo = Paymentmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<BillStatus>"
							+ payInfo.getBillStatus().getName()
							+ "</BillStatus>\n"); // 状态
					xml.append("<PaymentNumebr>" + payInfo.getNumber()
							+ "</PaymentNumebr>\n"); // 付款单编码
					xml.append("<RequestBillNumber>" + payInfo.getNumber()
							+ "</RequestBillNumber>\n"); // 申请单编码
					xml.append("<VoucherNumber>" + payInfo.getVoucherNumber()
							+ "</VoucherNumber>\n"); // 凭证号
					xml.append("<Currency>" + payInfo.getCurrency().getName()
							+ "</Currency>\n"); // 凭证号
					xml.append("<ExchangeRate>" + payInfo.getExchangeRate()
							+ "</ExchangeRate>\n"); // 汇率
					xml.append("<BizDate>" + payInfo.getBizDate()
							+ "</BizDate>\n"); // 业务日期
					xml.append("<PayDate>" + payInfo.getPayDate()
							+ "</PayDate>\n"); // 付款日期
					xml.append("<Creator>" + payInfo.getCreator()
							+ "</Creator>\n"); // 制单人
					xml.append("<CreateTime>" + payInfo.getCreateTime()
							+ "</CreateTime>\n"); // 制单时间
					xml.append("<PayeeBank>" + payInfo.getPayeeBank()
							+ "</PayeeBank>\n"); // 收款银行
					xml.append("<PayeeAccountBankO>"
							+ payInfo.getPayeeAccountBankO().getName()
							+ "</PayeeAccountBankO>\n"); // 收款银行账户
					xml.append("<Auditor>" + payInfo.getAuditor()
							+ "</Auditor>\n"); // 审核人
					xml.append("<AuditDate>" + payInfo.getAuditDate()
							+ "</AuditDate>\n"); // 审核日期
					xml.append("<Amount>" + payInfo.getAmount()
									+ "</Amount>\n"); // 原币金额
					xml.append("<LocalAmt>" + payInfo.getLocalAmt()
							+ "</LocalAmt>\n"); // 本位币金额

					EntityViewInfo entryavevi = new EntityViewInfo();    
					FilterInfo entryavfilter = new FilterInfo();
					entryavfilter.getFilterItems().add(
							new FilterItemInfo("parent", payInfo.getEntries()));
					entryavevi.setFilter(entryavfilter);
					PaymentPrjPayEntryCollection entryavc = PaymentPrjPayEntryFactory
							.getRemoteInstance()
							.getPaymentPrjPayEntryCollection(entryavevi);
					for (int j = 0; j < entryavc.size(); j++) {
						PaymentPrjPayEntryInfo entryinfo = entryavc.get(i);
						xml.append("<InvoiceNumber>"
								+ entryinfo.getInvoiceNumber()
								+ "</VoucherNumber>\n"); // 发票号码
						xml.append("<InvoiceAmt >" + entryinfo.getInvoiceAmt()
								+ "</VoucherNumber>\n"); // 发票金额本位币
					}
					xml.append("<ActFdcPayeeName>"
							+ payInfo.getActFdcPayeeName().getName()
							+ "</ActFdcPayeeName>\n"); // 收款单位
					xml.append("<SJActFdcPayeeName>"
							+ payInfo.getActFdcPayeeName().getName()
							+ "</SJActFdcPayeeName>\n"); // 实际收款单位
					xml.append("<Description>" + payInfo.getDescription()
							+ "</Description>\n"); // 说明
					xml.append("</item>\n");
				}
				xml.append("</PaymentBill>\n");

				xml.append("<ContractBill>\n"); // 合同
				EntityViewInfo Contractavevi = new EntityViewInfo();
				FilterInfo Contractavfilter = new FilterInfo();
				Contractavfilter.getFilterItems().add(
						new FilterItemInfo("CodingNumber", Info
								.getContractBill()));
				Contractavevi.setFilter(Contractavfilter);
				ContractBillCollection Contractmyavc = ContractBillFactory
						.getRemoteInstance().getContractBillCollection(
								Contractavevi);
				for (int i = 0; i < Contractmyavc.size(); i++) {
					ContractBillInfo avInfo = Contractmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<BizDate>" + avInfo.getBizDate()
							+ "</BizDate>\n"); // 业务日期
					xml.append("<PeriodId>" + avInfo.getPeriod()
							+ "</PeriodId>\n"); // 期间
					xml.append("<State>" + avInfo.getState().getName()
							+ "</State>\n"); // 状态
					xml.append("<HasSettled>" + avInfo.isHasSettled()
							+ "</HasSettled>\n"); // 已结算
					xml.append("<ContractType>"
							+ avInfo.getContractType().getName()
							+ "</ContractType>\n"); // 合同类型
					xml.append("<CodingNumber>" + avInfo.getCodingNumber()
							+ "</CodingNumber>\n"); // 编码
					xml.append("<Name>" + avInfo.getName() + "</Name>\n"); // 名称
					xml.append("<Currency>" + avInfo.getCurrency().getName()
							+ "</Currency>\n"); // 币别
					xml.append("<OriginalAmount>" + avInfo.getOriginalAmount()
							+ "</OriginalAmount>\n"); // 原币金额
					xml.append("<Amount>" + avInfo.getAmount() + "</Amount>\n"); // 本位币金额
					xml.append("<Developer>"
							+ avInfo.getLandDeveloper().getName()
							+ "</Developer>\n"); // 甲方
					xml.append("<PartB>" + avInfo.getPartB().getName()
							+ "</PartB>\n"); // 乙方
					xml.append("<RespDept>" + avInfo.getRespDept().getName()
							+ "</RespDept>\n"); // 责任部门
					xml.append("<RespPerson>"
							+ avInfo.getRespPerson().getName()
							+ "</RespPerson>\n"); // 责任人
					xml.append("<Creator>" + avInfo.getCreator()
							+ "</Creator>\n"); // 制单人
					xml.append("<CreateTime>" + avInfo.getCreateTime()
							+ "</CreateTime>\n"); // 制单时间
					xml.append("</item>\n");
				}
				xml.append("</ContractBill>\n");

				xml.append("<PaymentPrjPayEntry>\n"); // 工程付款情况表
				EntityViewInfo PaymentPrjPayavevi = new EntityViewInfo();
				FilterInfo PaymentPrjPayavfilter = new FilterInfo();
				PaymentPrjPayavfilter.getFilterItems().add(
						new FilterItemInfo("contractNum", Info
								.getContractBill()));
				PaymentPrjPayavevi.setFilter(PaymentPrjPayavfilter);
				ContractBillCollection PaymentPrjPayEntryavc = ContractBillFactory
						.getRemoteInstance().getContractBillCollection(
								PaymentPrjPayavevi);
				for (int i = 0; i < PaymentPrjPayEntryavc.size(); i++) {
					ContractBillInfo payInfo = PaymentPrjPayEntryavc.get(i);
					xml.append("<conitem>\n");
					xml.append("<ContractBillName>"+ payInfo.getName()+ "</ContractBillName>\n"); // 合同名称
					xml.append("<OriginalAmount>"+ payInfo.getOriginalAmount()+ "</OriginalAmount>\n"); // 原币金额
					xml.append("<Amount>"+ payInfo.getAmount()+ "</Amount>\n"); // 本位币金额
					//xml.append("<PaidPartAMatlAmt>"+ payInfo.getPaidPartAMatlAmt()+ "</PaidPartAMatlAmt>\n"); // 应扣甲供财款
					xml.append("</conitem>\n");
				}
				
				
				
				EntityViewInfo Paymentavevi2 = new EntityViewInfo();
				FilterInfo Paymentavfilter2 = new FilterInfo();
				Paymentavfilter2.getFilterItems().add(
						new FilterItemInfo("contractNum", Info
								.getContractBill()));
				Paymentavevi2.setFilter(Paymentavfilter2);
				PaymentBillCollection Paymentmyavc2 = PaymentBillFactory
						.getRemoteInstance().getPaymentBillCollection(
								Paymentavevi);
				for (int i = 0; i < Paymentmyavc2.size(); i++) {
					PaymentBillInfo payInfo = Paymentmyavc.get(i);
					xml.append("<paymentitem>\n");
					xml.append("<CurPaid>"+ payInfo.getCurPaid()+ "</CurPaid>\n"); //实付款
					xml.append("<PayPartAMatlAmt>"+ payInfo.getPayPartAMatlAmt()+ "</PayPartAMatlAmt>\n");// 应扣甲供财款
					xml.append("<CurPlannedPayment>"+ payInfo.getCurPlannedPayment()+ "</CurPlannedPayment>\n");// 本期计划付款
					xml.append("<CurBackPay>"+ payInfo.getCurBackPay()+ "</CurBackPay>\n");// 本期欠付款					
					xml.append("</paymentitem>\n");
					
					
					EntityViewInfo entryavevi = new EntityViewInfo();
					FilterInfo entryavfilter = new FilterInfo();
					entryavfilter.getFilterItems().add(
							new FilterItemInfo("parent", payInfo.getEntries()));
					entryavevi.setFilter(entryavfilter);
					PaymentPrjPayEntryCollection entryavc = PaymentPrjPayEntryFactory
							.getRemoteInstance()
							.getPaymentPrjPayEntryCollection(entryavevi);
					for (int j = 0; j < entryavc.size(); j++) {
						PaymentPrjPayEntryInfo entryinfo = entryavc.get(j);
						xml.append("<entryitem>\n");
						xml.append("<LstAdvanceAllPaid>"
								+ entryinfo.getLstAdvanceAllPaid()
								+ "</LstAdvanceAllPaid>\n"); // 预付款截止上期累计实付
						xml.append("<LstAdvanceAllReq>"
								+ entryinfo.getLstAdvanceAllReq()
								+ "</LstAdvanceAllReq>\n"); // 预付款截止上期累计申请
						xml.append("<AdvanceAllReq>"
								+ entryinfo.getAdvanceAllReq()
								+ "</AdvanceAllReq>\n"); // 预付款截止本期累计申请
						xml.append("<AdvanceAllPaid>"
								+ entryinfo.getAdvanceAllPaid()
								+ "</AdvanceAllPaid>\n"); // 预付款截止本期累计实付
						xml.append("</entryitem>\n");
					}
					
					
					
					

				}
				

				xml.append("</PaymentPrjPayEntry>\n");

				// 违约金
				xml.append("<CompensationBill>\n");
				EntityViewInfo avevi = new EntityViewInfo();
				FilterInfo avfilter = new FilterInfo();
				avfilter.getFilterItems().add(
						new FilterItemInfo("ContractBill", Info
								.getContractBill()));
				avevi.setFilter(avfilter);
				CompensationBillCollection myavc = CompensationBillFactory
						.getRemoteInstance().getCompensationBillCollection(
								avevi);
				for (int i = 0; i < myavc.size(); i++) {
					CompensationBillInfo avInfo = myavc.get(i);
					xml.append("<item>\n");
					xml.append("<select>" + avInfo.getId() + "</select>\n");
					xml.append("<number>" + avInfo.getNumber() + "</number>\n");
					xml.append("<name>" + avInfo.getName() + "</name>\n");
					xml.append("<amount>" + avInfo.getAmount() + "</amount>\n"); // 本币金额
					xml.append("<CompensationType>"
							+ avInfo.getCompensationType().getName()
							+ "</CompensationType>\n");
					xml.append("<DeductMode>" + avInfo.getDeductMode()
							+ "</DeductMode>\n");
					xml.append("<creator>" + avInfo.getCreator()
							+ "</creator>\n");
					xml.append("<CreateTime>" + avInfo.getCreateTime()
							+ "</CreateTime>\n");
					xml.append("</item>\n");
				}
				xml.append("</CompensationBill>\n");

				// 扣款
				xml.append("<DeductBillEntry>\n");
				EntityViewInfo Deductavevi = new EntityViewInfo();
				FilterInfo Deductavfilter = new FilterInfo();
				Deductavfilter.getFilterItems()
						.add(
								new FilterItemInfo("contractid", Info
										.getContractBill()));
				Deductavevi.setFilter(Deductavfilter);
				DeductBillEntryCollection Deductmyavc = DeductBillEntryFactory
						.getRemoteInstance().getDeductBillEntryCollection(
								Deductavevi);
				for (int i = 0; i < Deductmyavc.size(); i++) {
					DeductBillEntryInfo deductavInfo = Deductmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<select>" + deductavInfo + "</select>\n");
					xml.append("<Number>" + deductavInfo.getNumber()
							+ "</Number>\n"); // 编码
					xml.append("<DeductType>"
							+ deductavInfo.getDeductType().getName()
							+ "</DeductType>\n");
					xml.append("<DeductItem>" + deductavInfo.getDeductItem()
							+ "</DeductItem>\n"); // 扣款事项
					xml.append("<DeductOriginalAmt>"
							+ deductavInfo.getDeductOriginalAmt()
							+ "</DeductOriginalAmt>\n");
					xml.append("<DeductMode>"
							+ dateFormat.format(deductavInfo.getDeductDate())
							+ "</DeductMode>\n"); // 扣款日期

					EntityViewInfo Deductavevi2 = new EntityViewInfo();
					FilterInfo Deductavfilter2 = new FilterInfo();
					Deductavfilter2.getFilterItems().add(
							new FilterItemInfo("entry", deductavInfo
									.getParent()));
					Deductavevi2.setFilter(Deductavfilter2);
					DeductBillCollection Deductmyavc2 = DeductBillFactory
							.getRemoteInstance().getDeductBillCollection(
									Deductavevi2);
					for (int j = 0; j < Deductmyavc2.size(); j++) {
						DeductBillInfo deductavInfo2 = Deductmyavc2.get(i);
						xml.append("<creator>" + deductavInfo2.getCreator()
								+ "</creator>\n");
						xml.append("<CreateTime>"
								+ deductavInfo2.getCreateTime()
								+ "</CreateTime>\n");
					}

					xml.append("</item>\n");
				}
				xml.append("</DeductBillEntry>\n");

				// 奖励
				xml.append("<GuerdonBill>\n");
				EntityViewInfo guerdonavevi = new EntityViewInfo();
				FilterInfo guerdonavfilter = new FilterInfo();
				guerdonavfilter.getFilterItems().add(
						new FilterItemInfo("Contract", Info.getContractBill()));
				guerdonavevi.setFilter(guerdonavfilter);
				GuerdonBillCollection guerdonmyavc = GuerdonBillFactory
						.getRemoteInstance().getGuerdonBillCollection(
								guerdonavevi);
				for (int i = 0; i < guerdonmyavc.size(); i++) {
					GuerdonBillInfo avInfo = guerdonmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<select>" + avInfo.getId() + "</select>\n");
					xml.append("<number>" + avInfo.getNumber() + "</number>\n");
					xml.append("<name>" + avInfo.getName() + "</name>\n");
					xml.append("<amount>" + avInfo.getAmount() + "</amount>\n"); // 本币金额
					xml.append("<GuerdonThings>" + avInfo.getGuerdonThings()
							+ "</GuerdonThings>\n");
					xml.append("<PutOutType>"
							+ avInfo.getPutOutType().getName()
							+ "</PutOutType>\n");
					xml.append("<creator>" + avInfo.getCreator()
							+ "</creator>\n");
					xml.append("<CreateTime>" + avInfo.getCreateTime()
							+ "</CreateTime>\n");
					xml.append("</item>\n");
				}
				xml.append("</GuerdonBill>\n");
				xml.append("</DATA>");
				str[1] = xml.toString();
			} catch (BOSException e) {
				str[0] = "N";
				str[2] = "获取对象属性失败，请检查属性是否有值,并查看服务器log日志!";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("结算单单据ID：" + Info.getId() + "; 编号："
						+ Info.getNumber());
				log.setDescription("EAS结果:" + str[0] + "; 错误信息" + str[1]
						+ str[2]);
				log.setBeizhu("调用接口方法：GetbillInfo");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID,
			String procURL, String strMessage) {
		ContractSettlementBillInfo Info = (ContractSettlementBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";

		try {
			try {
				Info = ContractSettlementBillFactory.getLocalInstance(ctx)
						.getContractSettlementBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getselector获取对象数据，请检查getselector方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update t_con_contractbill set fState='"
						+ Info.getState().getValue() + "'" + ", fDescription='"
						+ procURL + "' " + ", FSourceFunction='" + procInstID
						+ "' where fid='" + Info.getId() + "'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			} catch (BOSException e) {
				str[0] = "N";
				str[2] = "根据单据state值更新状态sql失败，请检查getState方法是否有值,并查看服务器log日志！";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("结算单单据ID：" + Info.getId() + "; 编号："
						+ Info.getNumber());
				log.setDescription("BPM结果：" + success + "; EAS结果:" + str[0]
						+ "; 错误信息:" + str[1] + str[2]);
				log.setSimpleName("BPM流程号：" + procInstID + ";流程URL:" + procURL);
				log.setBeizhu("调用接口方法：_SubmitResult");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("Orgunit.id")); // 组织
		sic.add(new SelectorItemInfo("Orgunit.name"));
		sic.add(new SelectorItemInfo("Orgunit.number"));
		sic.add(new SelectorItemInfo("CurProject")); // 工程项目
		sic.add(new SelectorItemInfo("ContractBillNumber"));
		sic.add(new SelectorItemInfo("ContractBill.id"));
		sic.add(new SelectorItemInfo("ContractBill.name"));
		sic.add(new SelectorItemInfo("ContractBill.number"));
		sic.add(new SelectorItemInfo("BizDate"));
		sic.add(new SelectorItemInfo("Currency.id"));
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("Currency.number"));
		sic.add(new SelectorItemInfo("constructPrice"));
		sic.add(new SelectorItemInfo("exchangerate"));
		sic.add(new SelectorItemInfo("settlePrice"));
		sic.add(new SelectorItemInfo("unitPrice"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("getFeeCriteria"));
		sic.add(new SelectorItemInfo("infoPrice"));
		sic.add(new SelectorItemInfo("qualitytime"));
		sic.add(new SelectorItemInfo("qulityguaranterate"));
		sic.add(new SelectorItemInfo("guaranteAmt"));
		sic.add(new SelectorItemInfo("totalOriginalAmount"));
		sic.add(new SelectorItemInfo("totalsettlePrice"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("isFinalSettle"));
		sic.add(new SelectorItemInfo("Creator"));
		return sic;
	}

}
