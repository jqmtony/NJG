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
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
				if ("1".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.AUDITTED);
					else {
						str[2] = "����ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("0".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.INVALID);
					else {
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.BACK);
					else {
						str[2] = "�������ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("3".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "����ʧ�ܣ��ü�¼״̬���������У�";
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
				Info = ChangeAuditBillFactory.getLocalInstance(ctx)
						.getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				xml.append("<DATA>\n");
				xml.append("<Orgunit>" + StringUtilBPM.isNULl(Info.getOrgUnit().getName())
						+ "</Orgunit>\n");        //��֯
				xml.append("<CurProject>"
						+ StringUtilBPM.isNULl(Info.getCurProject().getName())
						+ "</CurProject>\n");     //������Ŀ
				xml.append("<Number>" + StringUtilBPM.isNULl(Info.getNumber())
						+ "</Number>\n");         //���ݱ��
				xml.append("<ChangeReason>"
						+ StringUtilBPM.isNULl(Info.getChangeReason().getName())
						+ "</ChangeReason>\n");   //���ԭ��
				xml.append("<Bizdate>" + dateFormat.format(Info.getBizDate())+ "</Bizdate>\n");//ҵ������
				xml.append("<Audittype>"
						+ StringUtilBPM.isNULl(Info.getAuditType().getName())
						+ "</Audittype>\n");  //�������
				xml.append("<Changesubject>"
						+ StringUtilBPM.isNULl(Info.getChangeSubject())
						+ "</Changesubject>\n");  //�������
				xml.append("<Period>" + dateFormat.format(Info.getPeriod())
						+ "</Period>\n");      //�ڼ�
				xml.append("<Jobtype>"
						+ StringUtilBPM.isNULl(Info.getJobType().getName())
						+ "</Jobtype>\n");     //�а�����
				xml.append("<UrgentDegree>"
						+ StringUtilBPM.isNULl(Info.getUrgentDegree().getName())
						+ "</UrgentDegree>\n"); //�����̶�
				xml.append("<ConductDept>"
						+ StringUtilBPM.isNULl(Info.getConductDept().getName())
						+ "</ConductDept>\n");   // �������
				xml.append("<DesignUnit>"+StringUtilBPM.isNULl(Info.getDesignUnit().getName())+"</DesignUnit>\n"); //��Ƶ�λ
				xml.append("<ConductUnit>"       
						+ StringUtilBPM.isNULl(Info.getConductUnit().getName())
						+ "</ConductUnit>\n"); //�����λ
				xml.append("<Construnit>"+ StringUtilBPM.isNULl(Info.getConstrUnit().getName())
						   + "</Construnit>\n");//ʩ����λ
				xml.append("<ReaDesc>"+StringUtilBPM.isNULl(Info.getReaDesc())
				            +"</ReaDesc>\n");   //˵��
				xml.append("<Offer>"+StringUtilBPM.isNULl(Info.getOffer().getName())+
				           "</Offer>\n");      //�����
				xml.append("<ConstrSite>"+ StringUtilBPM.isNULl(Info.getConstrSite())
						   + "</ConstrSite>\n");  //ʩ����λ
				xml.append("<ChangeState>"+ StringUtilBPM.isNULl(Info.getChangeState().getName())
						   + "</ChangeState>\n");   //״̬
				xml.append("<SpecialtyType>"+ StringUtilBPM.isNULl(Info.getSpecialtyType().getName())
						   + "</SpecialtyType>\n");  //רҵ����
				
				
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
					
					xml.append("<Name>�Ǽ���Ϣ"); 
					    xml.append("<item>\n");
			            		      xml.append("<ContractIDName>��ͬ��</ContractIDName>\n");
			            		      xml.append("<ContractNameName>��ͬ����</ContractNameName>\n");
			            		      xml.append("<MainSuppName>���͵�λ</MainSuppName>\n");
			            		      xml.append("<CopySuppName>���͵�λ</CopySuppName>\n");
			            		      xml.append("<OriginalContactNumName>ԭʼ��ϵ����</OriginalContactNumName>\n");
			            		      xml.append("<EntrysNameName>ִ������</EntrysNameName>\n");
			            		      xml.append("<CurrencyName>�ұ�</CurrencyName>\n");
			            		      xml.append("<ExrateName>����</ExrateName>\n");
			            		      xml.append("<OriCostAmountName>������ԭ��</OriCostAmountName>\n");
			            		      xml.append("<CostAmountNameNameName>������</CostAmountNameNameName>\n");
			            		      xml.append("<CostDescriptionName>�Ƿ�ȷ��������</CostDescriptionName>\n");
			            		      xml.append("<ConstructPriceNameName>����˵��</ConstructPriceNameName>\n");
			            		      xml.append("<IsDeductName>ʩ����������</IsDeductName>\n");
			            		      xml.append("<DeductAmountName>�Ƿ����οۿλ</DeductAmountName>\n");
			            		      xml.append("<DeductReasonName>���οۿ����ע��</DeductReasonName>\n");
			            		      xml.append("<ReckonorName>�ۿ�ԭ��</ReckonorName>\n");
			            		      xml.append("<DutyOrgName>������</DutyOrgName>\n");
			            		      xml.append("<IsSureChangeAmtName>���ι�������</IsSureChangeAmtName>\n");
					    xml.append("<item>\n");
					xml.append("</Name>\n");
					xml.append("<Content>������Ϣ");
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
				str[2] = "��ȡ��������ʧ�ܣ����������Ƿ���ֵ,���鿴������log��־!";
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
				Info = ChangeAuditBillFactory.getLocalInstance(ctx)
						.getChangeAuditBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
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
