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
				String sql = " update T_CON_ContractSettlementBill set fState='"
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
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
			xml.append("<Orgunit>"+ StringUtilBPM.isNULl(Info.getOrgUnit().getName())+ "</Orgunit>\n"); // 组织
			xml.append("<CurProject>"+ StringUtilBPM.isNULl(Info.getCurProject().getName())+ "</CurProject>\n"); // 工程项目
			xml.append("<Topic>"+ StringUtilBPM.isNULl(Info.getName())+ "</Topic>\n");   //结算单名称
			xml.append("<number>审-"+ StringUtilBPM.isNULl(Info.getContractBillNumber())+ "</number>\n"); // 结算单编码
			xml.append("<contactNumber>"+ StringUtilBPM.isNULl(Info.getContractBillNumber())+ "</contactNumber>\n"); // 合同编码
			xml.append("<contractName>"+ StringUtilBPM.isNULl(Info.getContractBill().getName())+ "</contractName>\n");// 合同名称
			xml.append("<bookedDate>" + dateFormat.format(Info.getBizDate())+ "</bookedDate>\n"); // 业务日期
			xml.append("<Currency>" + Info.getCurrency().getName()+ "</Currency>\n"); // 币别
			xml.append("<constructPrice>" + Info.getConstructPrice()+ "</constructPrice>\n"); // 施工方报审价
			xml.append("<exchangerate>" + Info.getExchangeRate()+ "</exchangerate>\n");
			xml.append("<OriginalAmount>" + Info.getOriginalAmount()+ "</OriginalAmount>\n"); // 结算造价原币
			xml.append("<settlePrice>" + Info.getSettlePrice()+ "</settlePrice>\n"); // 结算造价本币
			xml.append("<unitPrice>" + Info.getUnitPrice()+ "</unitPrice>\n"); // 单位造价
			xml.append("<buildArea>" + Info.getBuildArea()+ "</buildArea>\n"); // 建筑面积
			xml.append("<getFeeCriteria>" + Info.getGetFeeCriteria()+ "</getFeeCriteria>\n"); // 取费标准
			xml.append("<infoPrice>" + Info.getInfoPrice()+ "</infoPrice>\n"); // 信息价
			xml.append("<qualitytime>" + Info.getQualityTime()+ "</qualitytime>\n"); // 保修期限
			xml.append("<qulityguaranterate>"+ Info.getQualityGuaranteRate()+ "</qulityguaranterate>\n"); // 保修金比例
			xml.append("<guaranteAmt>" + Info.getGuaranteAmt()+ "</guaranteAmt>\n"); // 保修金
			xml.append("<totalOriginalAmount>"+ Info.getTotalOriginalAmount()+ "</totalOriginalAmount>\n"); // 累计结算原币
			xml.append("<totalsettlePrice>" + Info.getTotalSettlePrice()+ "</totalsettlePrice>\n"); // 累计结算本币
			xml.append("<description>" + StringUtilBPM.isNULl(Info.getDescription())+ "</description>\n"); // 备注
			xml.append("<QualityGuarante>" + Info.getQualityGuarante()+ "</QualityGuarante>\n"); // 累计保修金
			xml.append("<isFinalSettle>" + Info.getIsFinalSettle()+ "</isFinalSettle>\n");   // 是否最终结算
			xml.append("<Creator>"+ Info.getCreator().getName()+ "</Creator>\n"); // 制单人
			xml.append("<AttenTwo>"+ Info.getOwnID() + "</AttenTwo>\n"); // 归档稿
			xml.append("</DATA>");
			
			str[1] = xml.toString();
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
				String sql = " update T_CON_ContractSettlementBill set fState='"
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
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("OwnID"));
		sic.add(new SelectorItemInfo("QualityGuarante"));
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("State"));
		return sic;
	}

	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,
			IObjectValue billInfo, String strRelatedCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] ApproveBack(Context ctx, String strBTID,
			IObjectValue billInfo, String strXML) {
		// TODO Auto-generated method stub
		return null;
	}

}
