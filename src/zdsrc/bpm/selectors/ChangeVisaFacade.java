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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;

public class ChangeVisaFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ChangeAuditBillInfo Info = (ChangeAuditBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
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
		ChangeAuditBillInfo Info = (ChangeAuditBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = ChangeAuditBillFactory.getLocalInstance(ctx)
						.getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				xml.append("<DATA>\n");
				xml.append("<Orgunit>" + StringUtilBPM.isNULl(Info.getOrgUnit().getName())
						+ "</Orgunit>\n");        //组织
				xml.append("<CurProject>"
						+ StringUtilBPM.isNULl(Info.getCurProject().getName())
						+ "</CurProject>\n");     //工程项目
				xml.append("<Number>" + StringUtilBPM.isNULl(Info.getNumber())
						+ "</Number>\n");         //单据编号
				xml.append("<ChangeReason>"
						+ StringUtilBPM.isNULl(Info.getChangeReason().getName())
						+ "</ChangeReason>\n");   //变更原因
				xml.append("<Bizdate>" + dateFormat.format(Info.getBizDate())+ "</Bizdate>\n");//业务日期
				xml.append("<Audittype>"
						+ StringUtilBPM.isNULl(Info.getAuditType().getName())
						+ "</Audittype>\n");  //变更类型
				xml.append("<Changesubject>"
						+ StringUtilBPM.isNULl(Info.getChangeSubject())
						+ "</Changesubject>\n");  //变更主题
				xml.append("<Period>" + dateFormat.format(Info.getPeriod())
						+ "</Period>\n");      //期间
				xml.append("<Jobtype>"
						+ StringUtilBPM.isNULl(Info.getJobType().getName())
						+ "</Jobtype>\n");     //承包类型
				xml.append("<UrgentDegree>"
						+ StringUtilBPM.isNULl(Info.getUrgentDegree().getName())
						+ "</UrgentDegree>\n"); //紧急程度
				xml.append("<ConductDept>"
						+ StringUtilBPM.isNULl(Info.getConductDept().getName())
						+ "</ConductDept>\n");   // 提出部门
				xml.append("<DesignUnit>"+StringUtilBPM.isNULl(Info.getDesignUnit().getName())+"</DesignUnit>\n"); //设计单位
				xml.append("<ConductUnit>"       
						+ StringUtilBPM.isNULl(Info.getConductUnit().getName())
						+ "</ConductUnit>\n"); //提出单位
				xml.append("<Construnit>"+ StringUtilBPM.isNULl(Info.getConstrUnit().getName())
						   + "</Construnit>\n");//施工单位
				xml.append("<ReaDesc>"+StringUtilBPM.isNULl(Info.getReaDesc())
				            +"</ReaDesc>\n");   //说明
				xml.append("<Offer>"+StringUtilBPM.isNULl(Info.getOffer().getName())+
				           "</Offer>\n");      //提出人
				xml.append("<ConstrSite>"+ StringUtilBPM.isNULl(Info.getConstrSite())
						   + "</ConstrSite>\n");  //施工部位
				xml.append("<ChangeState>"+ StringUtilBPM.isNULl(Info.getChangeState().getName())
						   + "</ChangeState>\n");   //状态
				xml.append("<SpecialtyType>"+ StringUtilBPM.isNULl(Info.getSpecialtyType().getName())
						   + "</SpecialtyType>\n");  //专业类型
				
				
				xml.append("<billEntries>\n");
				for (int i = 0; i < Info.getEntrys().size(); i++) {
					ChangeAuditEntryInfo entry = Info.getEntrys().get(i);
					entry = ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryInfo(
									new ObjectUuidPK(entry.getId()));
					xml.append("<item>\n");
					xml.append("<Number>" + StringUtilBPM.isNULl(entry.getNumber())
							+ "</Number>\n");
					xml.append("<ChangeContent>"
							+ StringUtilBPM.isNULl(entry.getChangeContent())
							+ "</ChangeContent>\n");
					xml.append("<IsBack>"
							+ entry.isIsBack()
							+ "</IsBack>\n");
					xml.append("</item>\n");
				}
				xml.append("</billEntries>\n");
				xml.append("<SuppEntry>\n");
				for (int i = 0; i < Info.getSuppEntry().size(); i++) {
					ChangeSupplierEntryInfo entry = Info.getSuppEntry().get(i);
					entry = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryInfo(
									new ObjectUuidPK(entry.getId()));
					xml.append("<item>\n");
					xml.append("<Supp>" + entry.getSeq()
							+ "</Supp>\n");
					
					xml.append("<Name>登记信息"); 
					    xml.append("<item>\n");
			            		      xml.append("<ContractIDName>合同号</ContractIDName>\n");
			            		      xml.append("<ContractNameName>合同名称</ContractNameName>\n");
			            		      xml.append("<MainSuppName>主送单位</MainSuppName>\n");
			            		      xml.append("<CopySuppName>抄送单位</CopySuppName>\n");
			            		      xml.append("<OriginalContactNumName>原始联系单号</OriginalContactNumName>\n");
			            		      xml.append("<EntrysNameName>执行内容</EntrysNameName>\n");
			            		      xml.append("<CurrencyName>币别</CurrencyName>\n");
			            		      xml.append("<ExrateName>汇率</ExrateName>\n");
			            		      xml.append("<OriCostAmountName>测算金额原币</OriCostAmountName>\n");
			            		      xml.append("<CostAmountNameNameName>测算金额</CostAmountNameNameName>\n");
			            		      xml.append("<CostDescriptionName>是否确定变更金额</CostDescriptionName>\n");
			            		      xml.append("<ConstructPriceNameName>测算说明</ConstructPriceNameName>\n");
			            		      xml.append("<IsDeductName>施工方报审金额</IsDeductName>\n");
			            		      xml.append("<DeductAmountName>是否责任扣款单位</DeductAmountName>\n");
			            		      xml.append("<DeductReasonName>责任扣款金额（备注）</DeductReasonName>\n");
			            		      xml.append("<ReckonorName>扣款原因</ReckonorName>\n");
			            		      xml.append("<DutyOrgName>测算人</DutyOrgName>\n");
			            		      xml.append("<IsSureChangeAmtName>责任归属部门</IsSureChangeAmtName>\n");
					    xml.append("<item>\n");
					xml.append("</Name>\n");
					xml.append("<Content>测算信息");
					    xml.append("<item>\n");
                 					    xml.append("<ContractID>"+ entry.getContractBill().getId()+ "</ContractID>\n");
          		                        xml.append("<ContractName>"+ entry.getContractBill().getName()+ "</ContractName>\n");
          		                        xml.append("<MainSupp>"+ entry.getMainSupp().getName()+ "</MainSupp>\n");
          		                        xml.append("<CopySupp>"+ entry.getCopySupp()+ "</CopySupp>\n");
          		                        xml.append("<OriginalContactNum>"+ entry.getOriginalContactNum()+ "</OriginalContactNum>\n");
          		                        xml.append("<Entrys>"+ entry.getEntrys().get(1)+ "</Entrys>\n");
          		                       xml.append("<Currency>"+ entry.getCurrency().getName()+ "</Currency>\n");
          		                       xml.append("<Exrate>"+ entry.getExRate()+ "</Exrate>\n");
          		                       xml.append("<OriCostAmount>"+ entry.getOriCostAmount()+ "</OriCostAmount>\n");
          		                       xml.append("<CostAmount>"+ entry.getCostAmount()+ "</CostAmount>\n");
          		                       xml.append("<CostDescription>"+ entry.getCostDescription()+ "</CostDescription>\n");
          		                       xml.append("<ConstructPrice>"+ entry.getConstructPrice()+ "</ConstructPrice>\n");
          		                       xml.append("<IsDeduct>"+ entry.isIsDeduct()+ "</IsDeduct>\n");
          		                       xml.append("<DeductAmount>"+ entry.getDeductAmount()+ "</DeductAmount>\n");
          		                       xml.append("<DeductReason>"+ entry.getDeductReason()+ "</DeductReason>\n");
          		                       xml.append("<Reckonor>"+ entry.getReckonor().getName()+ "</Reckonor>\n");
          		                       xml.append("<DutyOrg>"+ entry.getDutyOrg().getName()+ "</DutyOrg>\n");
          		                       xml.append("<IsSureChangeAmt>"+ entry.isIsSureChangeAmt()+ "</IsSureChangeAmt>\n");
				         xml.append("<item>\n");	
				    xml.append("</Content>\n");
					xml.append("</item>\n");
				}
				xml.append("</SuppEntry>\n");
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
		ChangeAuditBillInfo Info = (ChangeAuditBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ChangeAuditBillFactory.getLocalInstance(ctx)
						.getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
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
		sic.add(new SelectorItemInfo("Orgunit.id"));
		sic.add(new SelectorItemInfo("Orgunit.name"));
		sic.add(new SelectorItemInfo("Orgunit.number"));
		sic.add(new SelectorItemInfo("CurProject.id"));
		sic.add(new SelectorItemInfo("CurProject.Name"));
		sic.add(new SelectorItemInfo("CurProject.number"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("ChangeReason.id"));
		sic.add(new SelectorItemInfo("ChangeReason.name"));
		sic.add(new SelectorItemInfo("ChangeReason.number"));
		sic.add(new SelectorItemInfo("bizdate"));
		sic.add(new SelectorItemInfo("Audittype.id"));
		sic.add(new SelectorItemInfo("Audittype.name"));
		sic.add(new SelectorItemInfo("Audittype.number"));
		sic.add(new SelectorItemInfo("Changesubject"));
		sic.add(new SelectorItemInfo("Period"));
		sic.add(new SelectorItemInfo("Jobtype.id"));
		sic.add(new SelectorItemInfo("Jobtype.name"));
		sic.add(new SelectorItemInfo("Jobtype.number"));
		sic.add(new SelectorItemInfo("UrgentDegree.id"));
		sic.add(new SelectorItemInfo("UrgentDegree.name"));
		sic.add(new SelectorItemInfo("UrgentDegree.number"));
		sic.add(new SelectorItemInfo("ConductDept.id"));
		sic.add(new SelectorItemInfo("ConductDept.name"));
		sic.add(new SelectorItemInfo("ConductDept.number"));
		sic.add(new SelectorItemInfo("DesignUnit.id"));
		sic.add(new SelectorItemInfo("DesignUnit.name"));
		sic.add(new SelectorItemInfo("DesignUnit.number"));
		sic.add(new SelectorItemInfo("ConductUnit.id"));
		sic.add(new SelectorItemInfo("ConductUnit.name"));
		sic.add(new SelectorItemInfo("ConductUnit.number"));
		sic.add(new SelectorItemInfo("Construnit.id"));
		sic.add(new SelectorItemInfo("Construnit.name"));
		sic.add(new SelectorItemInfo("Construnit.number"));
		sic.add(new SelectorItemInfo("ReaDesc"));
		sic.add(new SelectorItemInfo("Offer.id"));
		sic.add(new SelectorItemInfo("Offer.name"));
		sic.add(new SelectorItemInfo("Offer.number"));
		sic.add(new SelectorItemInfo("ConstrSite"));
		sic.add(new SelectorItemInfo("ChangeState.id"));
		sic.add(new SelectorItemInfo("ChangeState.name"));
		sic.add(new SelectorItemInfo("ChangeState.number"));
		sic.add(new SelectorItemInfo("SpecialtyType.id"));
		sic.add(new SelectorItemInfo("SpecialtyType.name"));
		sic.add(new SelectorItemInfo("SpecialtyType.number"));
		
		sic.add(new SelectorItemInfo("Entrys.Number"));
		sic.add(new SelectorItemInfo("Entrys.ChangeContent"));
		sic.add(new SelectorItemInfo("Entrys.IsBack"));
		
		
		sic.add(new SelectorItemInfo("SuppEntry.Seq"));
		sic.add(new SelectorItemInfo("SuppEntry.ContractBill.id"));
		sic.add(new SelectorItemInfo("SuppEntry.ContractBill.name"));
		sic.add(new SelectorItemInfo("SuppEntry.ContractBill.number"));
		sic.add(new SelectorItemInfo("Entrys.IsSureChangeAmt.id"));
		sic.add(new SelectorItemInfo("Entrys.IsSureChangeAmt.name"));
		sic.add(new SelectorItemInfo("Entrys.IsSureChangeAmt.number"));
		sic.add(new SelectorItemInfo("Entrys.CopySupp"));
		sic.add(new SelectorItemInfo("Entrys.OriginalContactNum"));
		
		sic.add(new SelectorItemInfo("Entrys.Entrys"));   ///
		
		sic.add(new SelectorItemInfo("Entrys.Currency"));
		sic.add(new SelectorItemInfo("Entrys.getExRate"));
		
		sic.add(new SelectorItemInfo("Entrys.getExRate"));
		
		sic.add(new SelectorItemInfo("Entrys.getOriCostAmount"));
		sic.add(new SelectorItemInfo("Entrys.getCostAmount"));
		sic.add(new SelectorItemInfo("Entrys.getCostDescription"));
		sic.add(new SelectorItemInfo("Entrys.getConstructPrice"));
		sic.add(new SelectorItemInfo("Entrys.isIsDeduct"));
		sic.add(new SelectorItemInfo("Entrys.DeductAmount"));
		sic.add(new SelectorItemInfo("Entrys.DeductReason"));
		sic.add(new SelectorItemInfo("Entrys.Reckonor.id"));
		sic.add(new SelectorItemInfo("Entrys.Reckonor.name"));
		sic.add(new SelectorItemInfo("Entrys.Reckonor.number"));
		sic.add(new SelectorItemInfo("Entrys.DutyOrg.id"));
		sic.add(new SelectorItemInfo("Entrys.DutyOrg.name"));
		sic.add(new SelectorItemInfo("Entrys.DutyOrg.number"));
		sic.add(new SelectorItemInfo("Entrys.IsSureChangeAmt"));
		return sic;
	}

	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,
			IObjectValue billInfo, String strRelatedCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
