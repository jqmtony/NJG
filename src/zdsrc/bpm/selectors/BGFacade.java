package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.ChangeVisaAppFactory;
import com.kingdee.eas.bpmdemo.ChangeVisaAppInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBill;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class BGFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ContractChangeBillInfo Info=(ContractChangeBillInfo)billInfo;
		//ChangeAuditBillInfo Info = (ChangeAuditBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ContractChangeBillFactory.getLocalInstance(ctx)
						.getContractChangeBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
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
				String sql = " update T_CON_ContractChangeBill set fState='"
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
				log.setName("变更签证申请单据ID：" + Info.getId() + "; 编号："
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
		ContractChangeBillInfo Info = (ContractChangeBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = ContractChangeBillFactory.getLocalInstance(ctx)
						.getContractChangeBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
			xml.append("<Number>" + StringUtilBPM.isNULl(Info.getNumber())
					+ "</Number>\n"); // 申请单编码
			xml.append("<Name>" + StringUtilBPM.isNULl(Info.getName())
					+ "</Name>\n"); //名称
			if(Info.getChangeType()!=null)
			xml.append("<ChangeType>" + StringUtilBPM.isNULl(Info.getChangeType().getName())
					+ "</ChangeType>\n"); // 变更类型
			if(Info.getChangeReason()!=null)
			xml.append("<Changereason>" + StringUtilBPM.isNULl(Info.getChangeReason().getName())
					+ "</Changereason>\n"); // 变更原因
			
			xml.append("<amount>" + Info.getAmount()
					+ "</amount>\n"); //变更金额 
			if(Info.getConductDept()!=null)
			xml.append("<ConductDept>" + StringUtilBPM.isNULl(Info.getConductDept().getName())
					+ "</ConductDept>\n"); // 提出部门
			
			
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID：" + Info.getId() + "; 编号："
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
		ContractChangeBillInfo Info = (ContractChangeBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ContractChangeBillFactory.getLocalInstance(ctx)
						.getContractChangeBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update T_CON_ContractChangeBill set fState='"
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
				log.setName("合同单据ID：" + Info.getId() + "; 编号："
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
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("Name"));
		//sic.add(new SelectorItemInfo("ChangeType.id"));
		sic.add(new SelectorItemInfo("ChangeType.name"));
		//sic.add(new SelectorItemInfo("Audittype.number"));
		//sic.add(new SelectorItemInfo("Changereason.id"));
		sic.add(new SelectorItemInfo("Changereason.name"));
		//sic.add(new SelectorItemInfo("Changereason.number"));
		//sic.add(new SelectorItemInfo("ConductDept.id"));
		sic.add(new SelectorItemInfo("ConductDept.name"));
		//sic.add(new SelectorItemInfo("ConductDept.number"));
		sic.add(new SelectorItemInfo("amount"));
		return sic;
	}


	public String[] ApproveBack(Context ctx, String strBTID, String strBOID,
			String strXML) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,
			IObjectValue billInfo, String strRelatedCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] ApproveBack(Context ctx, String strBTID,
			IObjectValue billInfo, String strXML) {
		return null;
	}




}
