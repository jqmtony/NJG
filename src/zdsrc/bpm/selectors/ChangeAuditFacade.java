package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.CopySupplierEntryCollection;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.CopySupplierEntryInfo;

public class ChangeAuditFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ChangeAuditBillInfo Info = (ChangeAuditBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			  try {
				Info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			     } 
			     catch (EASBizException e) {
				  str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				  e.printStackTrace();
		        	}
			try {
				if ("1".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
						Info.setChangeState(ChangeBillStateEnum.Audit);
						//Info.setState(FDCBillStateEnum.AUDITTED);
					
					
					else {
						str[2] = "审批通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if ("0".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
						//Info.setChangeState(ChangeBillStateEnum.INVALID);
				     	Info.setChangeState(ChangeBillStateEnum.Saved);
						//Info.setState(FDCBillStateEnum.INVALID);
					else {
						str[2] = "审批不通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
						Info.setChangeState(ChangeBillStateEnum.Saved);   //被打回 --保存
						//Info.setState(FDCBillStateEnum.BACK);
					else {
						str[2] = "审批打回失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if ("3".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
						Info.setChangeState(ChangeBillStateEnum.Saved);
						//Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "撤销失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				String sql = " update T_CON_ChangeAuditBill set fChangeState='"
						+ Info.getChangeState().getValue() + "' where fid='"
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
				Info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			} catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
		    xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</OrgName>\n");
			xml.append("<DeptName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</DeptName>\n");
			xml.append("<ApplyDate>"+ dateFormat.format(Info.getCreateTime())+ "</ApplyDate>\n");
			xml.append("<Applicant>"+ StringUtilBPM.isNULl(Info.getCreator().getName())+ "</Applicant>\n");
			xml.append("<Position>CEO秘书</Position>\n");
			xml.append("<Topic>" + StringUtilBPM.isNULl(Info.getName())+ "</Topic>\n");
			xml.append("<Phase>"+ StringUtilBPM.isNULl(Info.getCurProject().getName())+ "</Phase>\n");
			xml.append("<OrgCode>"+ StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0]) + "</OrgCode>\n");
			xml.append("<orgunit>"+ StringUtilBPM.isNULl(Info.getOrgUnit().getName())+ "</orgunit>\n"); // 组织
			xml.append("<curproject>"+ StringUtilBPM.isNULl(Info.getCurProject().getName())+ "</curproject>\n"); // 工程项目
			xml.append("<number>" + StringUtilBPM.isNULl(Info.getNumber())+ "</number>\n"); // 单据编号
			xml.append("<name>" + StringUtilBPM.isNULl(Info.getName())+ "</name>\n"); // 单据名称
			xml.append("<changeReason>"+ StringUtilBPM.isNULl(Info.getChangeReason().getName())+ "</changeReason>\n"); // 变更原因
			xml.append("<audittype>"+ StringUtilBPM.isNULl(Info.getAuditType().getName())+ "</audittype>\n"); // 变更类型
			xml.append("<changesubject>"+ StringUtilBPM.isNULl(Info.getChangeSubject())+ "</changesubject>\n"); // 变更主题
			xml.append("<period>" + dateFormat.format(Info.getPeriod().getNumber())+ "</period>\n"); // 期间
			xml.append("<jobtype>"+ StringUtilBPM.isNULl(Info.getJobType().getName())+ "</jobtype>\n"); // 承包类型
			xml.append("<urgentDegree>"+ StringUtilBPM.isNULl(Info.getUrgentDegree().getName())+ "</urgentDegree>\n"); // 紧急程度
			if(Info.getConductDept()!=null)
			xml.append("<conductDept>"+ Info.getConductDept().getName()+ "</conductDept>\n"); // 提出部门
			if(Info.getDesignUnit()!=null)
			xml.append("<designUnit>"+ StringUtilBPM.isNULl(Info.getDesignUnit().getName())+ "</designUnit>\n"); // 设计单位
			if(Info.getConductUnit()!=null)
			xml.append("<conductUnit>"+Info.getConductUnit().getName()+ "</conductUnit>\n"); // 提出单位
			if(Info.getConstrUnit()!=null)
			xml.append("<construnit>"+ Info.getConstrUnit().getName()+ "</construnit>\n");// 施工单位
			xml.append("<reaDesc>"+ StringUtilBPM.isNULl(Info.getReaDesc())+ "</reaDesc>\n"); // 说明
			xml.append("<offer>"+ StringUtilBPM.isNULl(Info.getOffer().getAlias())+ "</offer>\n"); // 提出人
			xml.append("<constrSite>"+ StringUtilBPM.isNULl(Info.getConstrSite())+ "</constrSite>\n"); // 施工部位
//				xml.append("<changeState>"+ StringUtilBPM.isNULl(Info.getChangeState().getAlias())+ "</changeState>\n"); // 状态
			xml.append("<specialtyType>"+ Info.getSpecialName() + "</specialtyType>\n"); // 专业类型
		    if(false==Info.isIsImportChange())
		    	xml.append("<isImportChange>否</isImportChange>\n"); // 是否重大变更
		    else
		    {
		    	xml.append("<isImportChange>是</isImportChange>\n"); // 是否重大变更
		    }

		    xml.append("<bizdate>"+dateFormat.format(Info.getBookedDate())+"</bizdate>\n"); // 业务日期
		    if(Info.getPeriod()!=null)
			xml.append("<billEntries>");
			for (int i = 0; i < Info.getEntrys().size(); i++) {
				ChangeAuditEntryInfo entry = Info.getEntrys().get(i);
				entry = ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryInfo(new ObjectUuidPK(entry.getId()));
				xml.append("<item>");
				xml.append("<number>"+ StringUtilBPM.isNULl(entry.getNumber())+ "</number>\n");
				xml.append("<changecontent>"+ StringUtilBPM.isNULl(entry.getChangeContent())+ "</changecontent>\n");
				if(false==entry.isIsBack())
				xml.append("<isback>否</isback>\n");
				else
				{
					xml.append("<isback>是</isback>\n");
				}
				xml.append("</item>\n");
			}
			xml.append("</billEntries>\n");
				
//				xml.append("<SuppEntry>");
//				for (int i = 0; i < Info.getSuppEntry().size(); i++) {
//					ChangeSupplierEntryInfo changeSuppentry = Info.getSuppEntry().get(i);
//					changeSuppentry = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryInfo(new ObjectUuidPK(changeSuppentry.getId()));
//					changeSuppentry.getParent();
//					xml.append("<item>");
//					xml.append("<SuppID>" + changeSuppentry.getSeq() + "</SuppID>\n");
//					
//					  EntityViewInfo Myavevi = new EntityViewInfo();
//				      FilterInfo Myavfilter = new FilterInfo();
//				      Myavfilter.getFilterItems().add(new FilterItemInfo("id",changeSuppentry.getContractBill().getId(),CompareType.EQUALS));
//				      Myavevi.setFilter(Myavfilter);
//				      ContractBillCollection myavc=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(Myavevi);
//				      if(myavc.size()>0)
//				      {
//				        for(int j=0;j< myavc.size();j++){  	     
//		         	    ContractBillInfo info=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(changeSuppentry.getContractBill().getId()));
//		         	    xml.append("<ContractID>" + info.getNumber()+ "</ContractID>\n");
//						xml.append("<ContractName>" + info.getName()+ "</ContractName>\n");
//						//xml.append("<MainSupp>" + info.getPartB().getName()+ "</MainSupp>\n");//  主送单位
//						//xml.append("<Currency>" + info.getCurrency().getName()+ "</Currency>\n");//币别
//						//xml.append("<Exrate>" + info.getExRate() + "</Exrate>\n");
//						xml.append("<Currency>人民币</Currency>\n");//币别
//						xml.append("<MainSupp>中国建筑第八工程局</MainSupp>\n");//  主送单位
//						xml.append("<Exrate>1</Exrate>\n");
//						
//				        }
//				      }
//				      EntityViewInfo Myavevi3 = new EntityViewInfo();
//				      FilterInfo Myavfilter3 = new FilterInfo();
//				      Myavfilter3.getFilterItems().add(new FilterItemInfo("parent",changeSuppentry.getId(),CompareType.EQUALS));
//				      Myavevi3.setFilter(Myavfilter3);
//				      CopySupplierEntryCollection copycol=CopySupplierEntryFactory.getLocalInstance(ctx).getCopySupplierEntryCollection(Myavevi3);
//				      for(int n=0;n<copycol.size();n++)
//				      {  
//				    	  CopySupplierEntryInfo info=CopySupplierEntryFactory.getLocalInstance(ctx).getCopySupplierEntryInfo(new ObjectUuidPK(copycol.get(i).getId()));
//				    	  xml.append("<CopySupp>" +info.getCopySupp().getName()+ "</CopySupp>\n");   //抄送单位
//				      }
//		 			
//					xml.append("<OriginalContactNum>"+ changeSuppentry.getOriginalContactNum()+ "</OriginalContactNum>\n");
//					ChangeAuditEntryInfo entrycontext = ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryInfo(new ObjectUuidPK(changeSuppentry.getParent().getId()));
//					xml.append("<ZContext>"+entrycontext.getChangeContent()+"</ZContext>\n");   //
//					xml.append("<OriCostAmount>" + changeSuppentry.getOriCostAmount()+ "</OriCostAmount>\n");
//					xml.append("<CostAmount>" + changeSuppentry.getCostAmount()+ "</CostAmount>\n");
//					xml.append("<CostDescription>" + changeSuppentry.getCostDescription()+ "</CostDescription>\n");
//					xml.append("<ConstructPrice>" + changeSuppentry.getConstructPrice()+ "</ConstructPrice>\n");
//					if(false==changeSuppentry.isIsDeduct())
//					{
//					xml.append("<IsDeduct>否</IsDeduct>\n");
//					}
//					else
//					{
//						xml.append("<IsDeduct>是</IsDeduct>\n");
//				    }
//					xml.append("<DeductAmount>" + changeSuppentry.getDeductAmount()+ "</DeductAmount>\n");
//					xml.append("<DeductReason>" + changeSuppentry.getDeductReason()+ "</DeductReason>\n");
//					xml.append("<Reckonor>" + changeSuppentry.getReckonor().getName()+ "</Reckonor>\n"); //
//					if(changeSuppentry.getDutyOrg()!=null)
//					xml.append("<DutyOrg>" + changeSuppentry.getDutyOrg().getName()+ "</DutyOrg>\n"); //
//					if(false==changeSuppentry.isIsSureChangeAmt())
//					{
//					xml.append("<IsSureChangeAmt>否</IsSureChangeAmt>\n");
//					}
//					else
//					{
//						xml.append("<IsSureChangeAmt>是</IsSureChangeAmt>\n");
//					}
//					xml.append("</item>\n");
//				}
//				xml.append("</SuppEntry>\n");			
//				xml.append("<TotalCost>" + Info.getTotalCost()+ "</TotalCost>\n");   //测算金额汇总
//				xml.append("<DutyAmout>" + Info.getAmountDutySupp()+ "</DutyAmout>\n");   //测算金额汇总
//				
//				if(false==Info.isIsNoUse())
//				{
//				xml.append("<NoUse>否</NoUse>\n");   //是否存在无效成本
//				}else
//				{
//					xml.append("<NoUse>是</NoUse>\n");   //是否存在无效成本
//				}
//				xml.append("<NoUseAmount>" + Info.getCostNouse()+ "</NoUseAmount>\n");   //无效成本金额
//				xml.append("<Reason>" + Info.getReason()+ "</Reason>\n");   //无效成本原因  --无效成本原因ID  数据库没存值 //
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
				Info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				Info.setChangeState(ChangeBillStateEnum.Auditting);
				String sql = " update T_CON_ChangeAuditBill set fChangeState='"
				+ Info.getChangeState().getValue() + "'" + ", fDescription='"
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
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("ChangeReason.id"));
		sic.add(new SelectorItemInfo("ChangeReason.name"));
		sic.add(new SelectorItemInfo("ChangeReason.number"));
		sic.add(new SelectorItemInfo("bizdate"));
		sic.add(new SelectorItemInfo("Audittype.id"));
		sic.add(new SelectorItemInfo("Audittype.name"));
		sic.add(new SelectorItemInfo("Audittype.number"));
		sic.add(new SelectorItemInfo("Changesubject"));
		sic.add(new SelectorItemInfo("Period.number"));
		sic.add(new SelectorItemInfo("Jobtype.id"));
		sic.add(new SelectorItemInfo("Jobtype.name"));
		sic.add(new SelectorItemInfo("Jobtype.number"));
		sic.add(new SelectorItemInfo("urgentDegree"));
		sic.add(new SelectorItemInfo("ConductDept.name"));
		sic.add(new SelectorItemInfo("DesignUnit.name"));
		sic.add(new SelectorItemInfo("ConductUnit.id"));
		sic.add(new SelectorItemInfo("ConductUnit.name"));
		sic.add(new SelectorItemInfo("ConductUnit.number"));
		sic.add(new SelectorItemInfo("Construnit.id"));
		sic.add(new SelectorItemInfo("Construnit.name"));
		sic.add(new SelectorItemInfo("Construnit.number"));
		sic.add(new SelectorItemInfo("ReaDesc"));
		sic.add(new SelectorItemInfo("Offer"));
		sic.add(new SelectorItemInfo("ConstrSite"));
		sic.add(new SelectorItemInfo("ChangeState"));
		sic.add(new SelectorItemInfo("specialName"));
		sic.add(new SelectorItemInfo("isImportChange"));

		
		
		
		
		sic.add(new SelectorItemInfo("Entrys.Number"));
		sic.add(new SelectorItemInfo("Entrys.ChangeContent"));
		sic.add(new SelectorItemInfo("Entrys.IsBack"));

		sic.add(new SelectorItemInfo("SuppEntry.Seq"));
		sic.add(new SelectorItemInfo("SuppEntry.ContractBill.id"));
		sic.add(new SelectorItemInfo("SuppEntry.ContractBill.name"));
		sic.add(new SelectorItemInfo("SuppEntry.ContractBill.number"));
		sic.add(new SelectorItemInfo("SuppEntry.IsSureChangeAmt"));
		sic.add(new SelectorItemInfo("SuppEntry.mainSupp.id"));
		sic.add(new SelectorItemInfo("SuppEntry.mainSupp.name"));
		sic.add(new SelectorItemInfo("SuppEntry.mainSupp.number"));
		sic.add(new SelectorItemInfo("SuppEntry.CopySupp.id"));
		sic.add(new SelectorItemInfo("SuppEntry.CopySupp.name"));
		sic.add(new SelectorItemInfo("SuppEntry.OriginalContactNum"));
		sic.add(new SelectorItemInfo("SuppEntry.Entrys.changeContext"));
		sic.add(new SelectorItemInfo("SuppEntry.Currency.id"));
		sic.add(new SelectorItemInfo("SuppEntry.Currency.name"));
		sic.add(new SelectorItemInfo("SuppEntry.getExRate"));
		sic.add(new SelectorItemInfo("SuppEntry.getOriCostAmount"));
		sic.add(new SelectorItemInfo("SuppEntry.getCostAmount"));
		sic.add(new SelectorItemInfo("SuppEntry.getCostDescription"));
		sic.add(new SelectorItemInfo("SuppEntry.getConstructPrice"));
		sic.add(new SelectorItemInfo("SuppEntry.isIsDeduct"));
		sic.add(new SelectorItemInfo("SuppEntry.DeductAmount"));
		sic.add(new SelectorItemInfo("SuppEntry.DeductReason"));
		sic.add(new SelectorItemInfo("SuppEntry.Reckonor.id"));
		sic.add(new SelectorItemInfo("SuppEntry.Reckonor.name"));
		sic.add(new SelectorItemInfo("SuppEntry.Reckonor.number"));
		sic.add(new SelectorItemInfo("SuppEntry.DutyOrg.id"));
		sic.add(new SelectorItemInfo("SuppEntry.DutyOrg.name"));
		sic.add(new SelectorItemInfo("SuppEntry.DutyOrg.number"));
		sic.add(new SelectorItemInfo("SuppEntry.IsSureChangeAmt"));
		
		
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("State"));
		sic.add(new SelectorItemInfo("BookedDate"));
		sic.add(new SelectorItemInfo("ChangeState"));
		
		sic.add(new SelectorItemInfo("TotalCost"));
		sic.add(new SelectorItemInfo("AmountDutySupp"));
		sic.add(new SelectorItemInfo("CostNouse"));
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
