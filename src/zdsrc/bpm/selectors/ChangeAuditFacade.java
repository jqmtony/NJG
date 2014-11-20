package com.kingdee.eas.bpm.selectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.measure.unit.BaseUnit;
import javax.print.DocFlavor.STRING;
import javax.sql.RowSet;

import org.apache.tools.ant.taskdefs.SQLExec;
import org.eclipse.swt.custom.Bullet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.bi.model.DB.DBUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutor;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.form.SqlExecutorFacade;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.ba.app.ContructRptReportTree;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonCollection;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
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
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.opensymphony.xwork2.config.entities.ActionConfig.Builder;

public class ChangeAuditFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ChangeAuditBillInfo Info = (ChangeAuditBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		String sql="";
		try {
			  try {
				Info = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			     } 
			     catch (EASBizException e) {
				  str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				  e.printStackTrace();
		        	}
			try {
				if ("1".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
						Info.setChangeState(ChangeBillStateEnum.Audit);
//					Info.setState(FDCBillStateEnum.SUBMITTED);
//					CompanyOrgUnitInfo company = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));
//					AdminOrgUnitInfo admin=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));                            
//					ContextUtil.setCurrentFIUnit(ctx, company);
//					ContextUtil.setCurrentOrgUnit(ctx, admin);
//				    ContractBillFactory.getLocalInstance(ctx).audit(Info.getId());
  	//		    Info.setState(FDCBillStateEnum.AUDITTED); 
					
					
					else {
						str[2] = "����ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("0".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
					{
				     	Info.setChangeState(ChangeBillStateEnum.Saved);
					    sql = " update T_CON_ChangeAuditBill set fDescription='BPM�ܾ�' where fid='"+Info.getId()+"'";
					    FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
					    bu.appendSql(sql);
					    bu.executeUpdate(ctx);
					}
					else {
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
						Info.setChangeState(ChangeBillStateEnum.Saved);   //����� --����
						//Info.setState(FDCBillStateEnum.BACK);
					else {
						str[2] = "�������ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("3".equals(processInstanceResult)) {
					if (ChangeBillStateEnum.Auditting.equals(Info.getChangeState()))
					{
							Info.setChangeState(ChangeBillStateEnum.Saved);
							sql = " update T_CON_ChangeAuditBill set fDescription='' where fid='"+Info.getId()+"'";
							FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
							bu.appendSql(sql);
							bu.executeUpdate(ctx);
					}
					else {
						str[2] = "����ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				sql = " update T_CON_ChangeAuditBill set fChangeState='"
						+ Info.getChangeState().getValue() + "' where fid='"
						+ Info.getId() + "'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			} catch (BOSException e) {
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("���ǩ֤���뵥��ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("BPM�����" + processInstanceResult
						+ "; EAS���:" + str[0] + "; ������Ϣ" + str[1] + str[2]);
				log.setSimpleName("BPM���̺ţ�" + procInstID + ";BPM������Ϣ:"
						+ strComment);
				log.setBeizhu("���ýӿڷ�����ApproveClose");
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
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
			xml.append("<DATA>\n");
		    xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getDisplayName())+"</OrgName>\n");
			xml.append("<DeptName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</DeptName>\n");
			xml.append("<ApplyDate>"+ dateFormat.format(Info.getCreateTime())+ "</ApplyDate>\n");
			xml.append("<Applicant>"+ StringUtilBPM.isNULl(Info.getCreator().getName())+ "</Applicant>\n");
			xml.append("<Position>CEO����</Position>\n");
			xml.append("<Topic>" + StringUtilBPM.isNULl(Info.getName())+ "</Topic>\n");
			xml.append("<Phase>"+ StringUtilBPM.isNULl(Info.getCurProject().getDisplayName())+ "</Phase>\n");
			xml.append("<OrgCode>"+ StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0]) + "</OrgCode>\n");
			xml.append("<orgunit>"+ StringUtilBPM.isNULl(Info.getOrgUnit().getName())+ "</orgunit>\n"); // ��֯
			xml.append("<curproject>"+ StringUtilBPM.isNULl(Info.getCurProject().getName())+ "</curproject>\n"); // ������Ŀ
			xml.append("<number>" + StringUtilBPM.isNULl(Info.getNumber())+ "</number>\n"); // ���ݱ��
			xml.append("<name>" + StringUtilBPM.isNULl(Info.getName())+ "</name>\n"); // ��������
			xml.append("<changeReason>"+ StringUtilBPM.isNULl(Info.getChangeReason().getName())+ "</changeReason>\n"); // ���ԭ��
			xml.append("<audittype>"+ StringUtilBPM.isNULl(Info.getAuditType().getName())+ "</audittype>\n"); // �������
			xml.append("<changesubject>"+ StringUtilBPM.isNULl(Info.getChangeSubject())+ "</changesubject>\n"); // �������
            Object m=Info.getPeriod().getNumber();
            m=m.toString().substring(0, 4);
            Object nz=Info.getPeriod().getNumber();
            nz=nz.toString().substring(4);
			xml.append("<period>"+m+"��"+nz+"��</period>\n"); // �ڼ�
			xml.append("<jobtype>"+ StringUtilBPM.isNULl(Info.getJobType().getName())+ "</jobtype>\n"); // �а�����
			xml.append("<urgentDegree>"+ StringUtilBPM.isNULl(Info.getUrgentDegree().getName())+ "</urgentDegree>\n"); // �����̶�
			if(Info.getConductDept()!=null)
			xml.append("<conductDept>"+ Info.getConductDept().getName()+ "</conductDept>\n"); // �������
			if(Info.getDesignUnit()!=null)
			xml.append("<designUnit>"+ StringUtilBPM.isNULl(Info.getDesignUnit().getName())+ "</designUnit>\n"); // ��Ƶ�λ
			if(Info.getConductUnit()!=null)
			xml.append("<conductUnit>"+Info.getConductUnit().getName()+ "</conductUnit>\n"); // �����λ
			if(Info.getConstrUnit()!=null)
			xml.append("<construnit>"+ Info.getConstrUnit().getName()+ "</construnit>\n");// ʩ����λ
			xml.append("<reaDesc>"+ StringUtilBPM.isNULl(Info.getReaDesc())+ "</reaDesc>\n"); // ˵��
			xml.append("<offer>"+ StringUtilBPM.isNULl(Info.getOffer().getAlias())+ "</offer>\n"); // �����
			xml.append("<constrSite>"+ StringUtilBPM.isNULl(Info.getConstrSite())+ "</constrSite>\n"); // ʩ����λ
//				xml.append("<changeState>"+ StringUtilBPM.isNULl(Info.getChangeState().getAlias())+ "</changeState>\n"); // ״̬
			xml.append("<specialtyType>"+ Info.getSpecialName().substring(0, Info.getSpecialName().length()-1)+ "</specialtyType>\n"); // רҵ����
		    if(false==Info.isIsImportChange())
		    	xml.append("<isImportChange>��</isImportChange>\n"); // �Ƿ��ش���
		    else
		    {
		    	xml.append("<isImportChange>��</isImportChange>\n"); // �Ƿ��ش���
		    }

		    xml.append("<bizdate>"+dateFormat.format(Info.getBookedDate())+"</bizdate>\n"); // ҵ������
		    if(Info.getPeriod()!=null)
			xml.append("<billEntries>");
			for (int i = 0; i < Info.getEntrys().size(); i++) {
				ChangeAuditEntryInfo entry = Info.getEntrys().get(i);
				entry = ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryInfo(new ObjectUuidPK(entry.getId()));
				xml.append("<item>");
				xml.append("<number>"+ StringUtilBPM.isNULl(entry.getNumber())+ "</number>\n");
				xml.append("<changecontent>"+ StringUtilBPM.isNULl(entry.getChangeContent())+ "</changecontent>\n");
				if(false==entry.isIsBack())
				xml.append("<isback>��</isback>\n");
				else
				{
					xml.append("<isback>��</isback>\n");
				}
				xml.append("</item>\n");
			}
			xml.append("</billEntries>\n");
				xml.append("<SuppEntry>");
				for (int i = 0; i < Info.getSuppEntry().size(); i++) {
					ChangeSupplierEntryInfo changeSuppentry = Info.getSuppEntry().get(i);
					changeSuppentry = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryInfo(new ObjectUuidPK(changeSuppentry.getId()));
					changeSuppentry.getParent();
					xml.append("<item>");
					xml.append("<SuppID>" + changeSuppentry.getSeq() + "</SuppID>\n");
					for(int mj=0;mj<changeSuppentry.getEntrys().size();mj++)
					 {
						  SupplierContentEntryInfo supperContent =changeSuppentry.getEntrys().get(mj);
						  EntityViewInfo Myavevi = new EntityViewInfo();
					      FilterInfo Myavfilter = new FilterInfo();
					      Myavfilter.getFilterItems().add(new FilterItemInfo("id",supperContent.getContent().getId(),CompareType.EQUALS));
					      Myavevi.setFilter(Myavfilter);
					      ChangeAuditEntryCollection myavc=ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryCollection(Myavevi);
					      if(myavc.size()>0)
					      {
					        for(int j=0;j< myavc.size();j++){
					        	ChangeAuditEntryInfo changeAudit = myavc.get(j);
					        	ChangeAuditEntryInfo info=ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryInfo(new ObjectUuidPK(changeAudit.getId()));
					        	xml.append("<ZContext>"+info.getChangeContent()+"</ZContext>\n");  //�������   
					        }
					      }
					 }
					  EntityViewInfo Myavevi = new EntityViewInfo();
				      FilterInfo Myavfilter = new FilterInfo();
				      Myavfilter.getFilterItems().add(new FilterItemInfo("id",changeSuppentry.getContractBill().getId(),CompareType.EQUALS));
				      Myavevi.setFilter(Myavfilter);
				      ContractBillCollection myavc=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(Myavevi);
				      if(myavc.size()>0)
				      {
					        for(int j=0;j< myavc.size();j++){ 
		         	          ContractBillInfo info=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(myavc.get(j).getId()));
		         	          xml.append("<ContractID>" + info.getNumber()+ "</ContractID>\n");
						      xml.append("<ContractName>" + info.getName()+ "</ContractName>\n");
						      EntityViewInfo Myavevis = new EntityViewInfo();
						      FilterInfo Myavfilters = new FilterInfo();
						      Myavfilters.getFilterItems().add(new FilterItemInfo("id",info.getProgrammingContract().getId(),CompareType.EQUALS));
						      Myavevis.setFilter(Myavfilters);
						      ProgrammingContractCollection progColl=ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractCollection(Myavevis);
						      if(progColl.size()>0)
						      {
						    	  for(int pro=0;pro<progColl.size();pro++)
						    	  {
						    		  ProgrammingContractInfo proinfo=ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(progColl.get(pro).getId()));
						    		  xml.append("<ProgrammingName>" + proinfo.getName()+ "</ProgrammingName>\n");//��Լ�������
						    		  xml.append("<ControlBalance>" + proinfo.getControlBalance()+ "</ControlBalance>\n");//���ƽ��
						    		  if(info.getProgrammingContract()!=null)
						    		  {
					    			  String sql="select a.SFCostAmount as SFa,b.SFceremonyb as SFb from  ";
						    		  sql+="(select sum(c.FCostAmount) as SFCostAmount,a.flongNumber as fida from T_CON_ProgrammingContract a left join  T_CON_ContractBill b on a.fid=b.FProgrammingContract ";
						    		  sql+="left join (select b.FCostAmount as FCostAmount,b.FcontractBillID as FcontractBillID,a.FChangeState as FChangeState from T_CON_ChangeAuditBill a left join ";
						    		  sql+="T_CON_ChangeSupplierEntry b on a.fid=b.fparentid)as c on b.fid=c.FcontractBillID where c.FchangeState = '4Auditting' and b.fid='"+info.getProgrammingContract().getId()+"' group by a.flongnumber) a  ";
						    		  sql+="right join (select sum(b.Fceremonyb) as SFceremonyb,a.flongNumber as fidb  from T_CON_ProgrammingContract a left join  T_CON_ContractBill b on a.fid=b.FProgrammingContract left ";
						    		  sql+="join (select b.FCostAmount as FCostAmount,b.FcontractBillID as FcontractBillID,a.FChangeState as FChangeState from T_CON_ChangeAuditBill a left join ";
						    		  sql+="T_CON_ChangeSupplierEntry b on a.fid=b.fparentid)as c on b.fid=c.FcontractBillID where b.Fstate='3AUDITTING' group by a.flongnumber) b on a.fida= b.fidb";
						    		  FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						    		  builder.appendSql(sql);
					                  IRowSet Rowset=builder.executeQuery();
					                  if(Rowset.size()==1)
					                  {
					                   Rowset.next();  
					                   xml.append("<BGMoney>" +FDCHelper.toBigDecimal(Rowset.getBigDecimal("SFa")) + "</BGMoney>\n");//��;������
								       xml.append("<HTMoney>" +FDCHelper.toBigDecimal(Rowset.getBigDecimal("SFb"))+"</HTMoney>\n");//��;���������
					                  }
					                  builder.clear();
						    		  }
						    	  }
						       }
						      
					        }
				      }
					SupplierInfo MainSuppInfos =SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(changeSuppentry.getMainSupp().getId()));
		    		xml.append("<MainSupp>" +StringUtilBPM.isNULl(MainSuppInfos.getName())+ "</MainSupp>\n");//  ���͵�λ
		    		CurrencyInfo currInfo= CurrencyFactory.getLocalInstance(ctx).getCurrencyInfo(new ObjectUuidPK(changeSuppentry.getCurrency().getId()));
		    		xml.append("<Currency>" +currInfo.getName()+"</Currency>\n");
		    		xml.append("<CostDescription>" +StringUtilBPM.isNULl(changeSuppentry.getCostDescription())+ "</CostDescription>\n");//����˵��
		    		xml.append("<Exrate>" +changeSuppentry.getExRate()+ "</Exrate>\n");//����
		    		if(changeSuppentry.getConstructPrice()!=null)
		    		{
		    		 xml.append("<ConstructPrice>" +changeSuppentry.getConstructPrice()+ "</ConstructPrice>\n");//ʩ�����󱨽��
		    		}
		    		else
		    		{
		    			xml.append("<ConstructPrice>0</ConstructPrice>\n");//ʩ�����󱨽��
		    		}
		    		xml.append("<CostDescription>" +StringUtilBPM.isNULl(changeSuppentry.getCostDescription())+ "</CostDescription>\n");//����˵��
		    		if(changeSuppentry.getCostAmount()!=null)
		    		{
		    		xml.append("<CostAmount>" +changeSuppentry.getCostAmount()+"</CostAmount>\n");//������ԭ��
		    		}
		    		else
		    		{
		    			xml.append("<CostAmount>0</CostAmount>\n");//������ԭ��
		    		}
		    		if(changeSuppentry.getOriCostAmount()!=null)
		    		{
		    		xml.append("<OriCostAmount>" +changeSuppentry.getOriCostAmount()+ "</OriCostAmount>\n");//������
		    		}
		    		else
		    		{
		    			xml.append("<OriCostAmount>0</OriCostAmount>\n");//������
		    		}
		    		if(false==changeSuppentry.isIsDeduct())
					{
					xml.append("<IsDeduct>��</IsDeduct>\n");
					}
					else
					{
						xml.append("<IsDeduct>��</IsDeduct>\n");
				    }
		    		xml.append("<OriginalContactNum>"+StringUtilBPM.isNULl(changeSuppentry.getOriginalContactNum())+ "</OriginalContactNum>\n");
		    		if(changeSuppentry.getDeductAmount()!=null)
		    		{
					xml.append("<DeductAmount>" + changeSuppentry.getDeductAmount()+ "</DeductAmount>\n");
		    		}
		    		else
		    		{
		    			xml.append("<DeductAmount>0</DeductAmount>\n");
		    		}
					xml.append("<DeductReason>" + StringUtilBPM.isNULl(changeSuppentry.getDeductReason())+ "</DeductReason>\n");
					UserInfo userinfo=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(changeSuppentry.getReckonor().getId()));
                    xml.append("<Reckonor>" +userinfo.getName()+ "</Reckonor>\n");
					if(changeSuppentry.getDutyOrg().getId()!=null)
					xml.append("<DutyOrg>" +AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(changeSuppentry.getDutyOrg().getId())).getName()+"</DutyOrg>\n");
					if(false==changeSuppentry.isIsSureChangeAmt())
					{
					xml.append("<IsSureChangeAmt>��</IsSureChangeAmt>\n");
					}
					else
					{
						xml.append("<IsSureChangeAmt>��</IsSureChangeAmt>\n");
					}
		    		
		    		
		    		
		    		
		    		
		    		
//				      EntityViewInfo My = new EntityViewInfo();
//				      FilterInfo Myfilter = new FilterInfo();
//				      Myfilter.getFilterItems().add(new FilterItemInfo("Parent",Info.getId(),CompareType.EQUALS));
//				      
//				      My.setFilter(Myfilter);
//				      ChangeSupplierEntryCollection ChengeMy=ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryCollection(My);
//				      if(ChengeMy.size()>0)
//				      {
//				    	for(int a=0;a<ChengeMy.size();a++)
//				    	{
//				    		ChangeSupplierEntryInfo Cinfo=ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryInfo(new ObjectUuidPK(ChengeMy.get(a).getId()));
//				    		
//				    		SupplierInfo MainSuppInfo =SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(Cinfo.getMainSupp().getId()));
//				    		xml.append("<MainSupp>" +StringUtilBPM.isNULl(MainSuppInfo.getName())+ "</MainSupp>\n");//  ���͵�λ
//				    		CurrencyInfo currInfo= CurrencyFactory.getLocalInstance(ctx).getCurrencyInfo(new ObjectUuidPK(Cinfo.getCurrency().getId()));
//				    		xml.append("<Currency>" +currInfo.getName()+"</Currency>\n");
//				    		xml.append("<Exrate>" +Cinfo.getExRate()+ "</Exrate>\n");//����
//				    		if(Cinfo.getConstructPrice()!=null)
//				    		{
//				    		 xml.append("<ConstructPrice>" +Cinfo.getConstructPrice()+ "</ConstructPrice>\n");//ʩ�����󱨽��
//				    		}
//				    		else
//				    		{
//				    			xml.append("<ConstructPrice>0</ConstructPrice>\n");//ʩ�����󱨽��
//				    		}
//				    		xml.append("<CostDescription>" +StringUtilBPM.isNULl(Cinfo.getCostDescription())+ "</CostDescription>\n");//����˵��
//				    		if(Cinfo.getCostAmount()!=null)
//				    		{
//				    		xml.append("<CostAmount>" +Cinfo.getCostAmount()+"</CostAmount>\n");//������ԭ��
//				    		}
//				    		else
//				    		{
//				    			xml.append("<CostAmount>0</CostAmount>\n");//������ԭ��
//				    		}
//				    		if(Cinfo.getOriCostAmount()!=null)
//				    		{
//				    		xml.append("<OriCostAmount>" +Cinfo.getOriCostAmount()+ "</OriCostAmount>\n");//������
//				    		}
//				    		else
//				    		{
//				    			xml.append("<OriCostAmount>0</OriCostAmount>\n");//������
//				    		}
//				    		if(false==Cinfo.isIsDeduct())
//							{
//							xml.append("<IsDeduct>��</IsDeduct>\n");
//							}
//							else
//							{
//								xml.append("<IsDeduct>��</IsDeduct>\n");
//						    }
//				    		xml.append("<OriginalContactNum>"+StringUtilBPM.isNULl(Cinfo.getOriginalContactNum())+ "</OriginalContactNum>\n");
//				    		if(Cinfo.getDeductAmount()!=null)
//				    		{
//							xml.append("<DeductAmount>" + Cinfo.getDeductAmount()+ "</DeductAmount>\n");
//				    		}
//				    		else
//				    		{
//				    			xml.append("<DeductAmount>0</DeductAmount>\n");
//				    		}
//							xml.append("<DeductReason>" + StringUtilBPM.isNULl(Cinfo.getDeductReason())+ "</DeductReason>\n");
//							UserInfo userinfo=UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(Cinfo.getReckonor().getId()));
//                            xml.append("<Reckonor>" +userinfo.getName()+ "</Reckonor>\n");
//        					if(Cinfo.getDutyOrg().getId()!=null)
//        					xml.append("<DutyOrg>" +AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(Cinfo.getDutyOrg().getId())).getName()+"</DutyOrg>\n");
//        					if(false==Cinfo.isIsSureChangeAmt())
//        					{
//        					xml.append("<IsSureChangeAmt>��</IsSureChangeAmt>\n");
//        					}
//        					else
//        					{
//        						xml.append("<IsSureChangeAmt>��</IsSureChangeAmt>\n");
//        					}
//				    	}
//				      }
				      EntityViewInfo Myavevi3 = new EntityViewInfo();
				      FilterInfo Myavfilter3 = new FilterInfo();
				      Myavfilter3.getFilterItems().add(new FilterItemInfo("parent.id",changeSuppentry.getId(),CompareType.EQUALS));
				      Myavevi3.setFilter(Myavfilter3);
				      CopySupplierEntryCollection copycol=CopySupplierEntryFactory.getLocalInstance(ctx).getCopySupplierEntryCollection(Myavevi3);
				      if(copycol.size()>0)
				      {
				       for(int n=0;n<copycol.size();n++)
				       {  
				    	  CopySupplierEntryInfo CopySuppinfo=copycol.get(n);
 				    	  CopySupplierEntryInfo info=CopySupplierEntryFactory.getLocalInstance(ctx).getCopySupplierEntryInfo(new ObjectUuidPK(CopySuppinfo.getId()));
				    	  xml.append("<CopySupp>" +SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(info.getCopySupp().getId())).getName()+ "</CopySupp>\n");   //���͵�λ
				       }
				      }
						xml.append("</item>\n");
				}
				xml.append("</SuppEntry>\n");

				xml.append("<TotalCost>" + Info.getTotalCost()+ "</TotalCost>\n");   //���������
				xml.append("<DutyAmout>" + Info.getAmountDutySupp()+ "</DutyAmout>\n");   //���������
				
				if(false==Info.isIsNoUse())
				{
				xml.append("<NoUse>��</NoUse>\n");   //�Ƿ������Ч�ɱ�
				}else
				{
					xml.append("<NoUse>��</NoUse>\n");   //�Ƿ������Ч�ɱ�
				}
				if(Info.getCostNouse()==null)
				{
					xml.append("<NoUseAmount>0</NoUseAmount>\n"); 
				}
				else
				{
				 xml.append("<NoUseAmount>" + Info.getCostNouse()+ "</NoUseAmount>\n");   //��Ч�ɱ����
				}
				if(Info.getInvalidCostReason()!=null)
				{
					  EntityViewInfo Myavevi3 = new EntityViewInfo();
				      FilterInfo Myavfilter3 = new FilterInfo();
				      Myavfilter3.getFilterItems().add(new FilterItemInfo("id",Info.getInvalidCostReason().getId(),CompareType.EQUALS));
				      Myavevi3.setFilter(Myavfilter3);
				      InvalidCostReasonCollection CostReason=InvalidCostReasonFactory.getLocalInstance(ctx).getInvalidCostReasonCollection(Myavevi3);
				      if(CostReason.size()>0)
				      {
				       for(int b=0;b<CostReason.size();b++)
				       {  
				    	  InvalidCostReasonInfo CostReasoninfo=CostReason.get(b);
				    	  InvalidCostReasonInfo info=InvalidCostReasonFactory.getLocalInstance(ctx).getInvalidCostReasonInfo(new ObjectUuidPK(CostReasoninfo.getId()));
				    	  xml.append("<Reason>" + StringUtilBPM.isNULl(info.getName())+ "</Reason>\n");   //��Ч�ɱ�ԭ��  --��Ч�ɱ�ԭ��ID  ���ݿ�û��ֵ //	
				       }
				      }
					
				}

				
				xml.append("</DATA>");
				str[1] = xml.toString();
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("EAS���:" + str[0] + "; ������Ϣ" + str[1]
						+ str[2]);
				log.setBeizhu("���ýӿڷ�����GetbillInfo");
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
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
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
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("BPM�����" + success + "; EAS���:" + str[0]
						+ "; ������Ϣ:" + str[1] + str[2]);
				log.setSimpleName("BPM���̺ţ�" + procInstID + ";����URL:" + procURL);
				log.setBeizhu("���ýӿڷ�����_SubmitResult");
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
		sic.add(new SelectorItemInfo("Orgunit.DisplayName"));
		sic.add(new SelectorItemInfo("CurProject.id"));
		sic.add(new SelectorItemInfo("CurProject.Name"));
		sic.add(new SelectorItemInfo("CurProject.number"));
		sic.add(new SelectorItemInfo("CurProject.DisplayName"));
		
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
        sic.add(new SelectorItemInfo("InvalidCostReason"));
        sic.add(new SelectorItemInfo("IsNoUse"));
		
		
		
		sic.add(new SelectorItemInfo("Entrys.Number"));
		sic.add(new SelectorItemInfo("Entrys.ChangeContent"));
		sic.add(new SelectorItemInfo("Entrys.IsBack"));

		sic.add(new SelectorItemInfo("suppEntry.Seq"));
		sic.add(new SelectorItemInfo("suppEntry.ContractBill.id"));
		sic.add(new SelectorItemInfo("suppEntry.ContractBill.name"));
		sic.add(new SelectorItemInfo("suppEntry.ContractBill.number"));
		sic.add(new SelectorItemInfo("suppEntry.IsSureChangeAmt"));
		sic.add(new SelectorItemInfo("suppEntry.mainSupp.id"));
		sic.add(new SelectorItemInfo("suppEntry.CopySupp.id"));
		sic.add(new SelectorItemInfo("suppEntry.CopySupp.name"));
		sic.add(new SelectorItemInfo("suppEntry.OriginalContactNum"));
		sic.add(new SelectorItemInfo("suppEntry.Entrys.changeContext"));
		//sic.add(new SelectorItemInfo("SuppEntry.Currency.id"));
		sic.add(new SelectorItemInfo("suppEntry.Currency.name"));
		sic.add(new SelectorItemInfo("suppEntry.ExRate"));
		sic.add(new SelectorItemInfo("suppEntry.OriCostAmount"));
		sic.add(new SelectorItemInfo("SuppEntry.CostAmount"));
		sic.add(new SelectorItemInfo("suppEntry.CostDescription"));
		sic.add(new SelectorItemInfo("suppEntry.ConstructPrice"));
		sic.add(new SelectorItemInfo("suppEntry.isIsDeduct"));
		sic.add(new SelectorItemInfo("suppEntry.DeductAmount"));
		sic.add(new SelectorItemInfo("suppEntry.DeductReason"));
		sic.add(new SelectorItemInfo("suppEntry.Reckonor.id"));
		sic.add(new SelectorItemInfo("suppEntry.DutyOrg.name"));
		sic.add(new SelectorItemInfo("suppEntry.DutyOrg.id"));
		sic.add(new SelectorItemInfo("suppEntry.IsSureChangeAmt"));
		
		
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
