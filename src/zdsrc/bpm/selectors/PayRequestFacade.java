package com.kingdee.eas.bpm.selectors;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.app.ContractTypeController;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.ma.bg.BgItemCollection;
import com.kingdee.eas.ma.bg.BgItemFactory;
import com.kingdee.eas.ma.bg.BgItemInfo;
import com.kingdee.eas.ma.bg.client.BgItemF7Selector;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class PayRequestFacade implements BillBaseSelector {
	
	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID, String procURL,
			String strMessage) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try{
				Info.setState(FDCBillStateEnum.AUDITTING);
//				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"'" +
//						", fDescription='"+procURL+"' " +
//						", FSourceFunction='"+procInstID+"' where fid='"+Info.getId()+"'";
				
				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"'" +
				", FProcess='"+procURL+"' " +
				", FSourceFunction='"+procInstID+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			}
			catch (BOSException e) {
				str[0] = "N";
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��"+Info.getId()+"; ��ţ�"+Info.getNumber());
				log.setDescription("BPM�����"+success+"; EAS���:"+str[0]+"; ������Ϣ:"+str[1]+str[2]);
				log.setSimpleName("BPM���̺ţ�"+procInstID+";����URL:"+procURL);
				log.setBeizhu("���ýӿڷ�����_SubmitResult");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	public String[] ApproveClose(Context ctx, String strBSID, IObjectValue billInfo,
			int procInstID, String processInstanceResult, String strComment,
			Date dtTime) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	String sql="";
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try{
				if("1".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
					{
						Info.setState(FDCBillStateEnum.SUBMITTED);
						CompanyOrgUnitInfo company = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));
						AdminOrgUnitInfo admin=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));                            
						ContextUtil.setCurrentFIUnit(ctx, company);
						ContextUtil.setCurrentOrgUnit(ctx, admin);
						PayRequestBillFactory.getLocalInstance(ctx).audit(Info.getId());
						Info.setState(FDCBillStateEnum.AUDITTED); 
						
					}
					else{
						str[2] = "����ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if("0".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						//Info.setState(FDCBillStateEnum.INVALID);
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if("2".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "�������ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if("3".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
					{
						Info.setState(FDCBillStateEnum.SAVED);
						//sql = " update t_con_payrequestbill set fDescription='' where fid='"+Info.getId()+"'";
						sql = " update t_con_payrequestbill set FProcess='' where fid='"+Info.getId()+"'";
						FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
						bu.appendSql(sql);
						bu.executeUpdate(ctx);
					}
					else{
						str[2] = "����ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			}
			catch (BOSException e) {
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��"+Info.getId()+"; ��ţ�"+Info.getNumber());
				log.setDescription("BPM�����"+processInstanceResult+"; EAS���:"+str[0]+"; ������Ϣ"+str[1]+str[2]);
				log.setSimpleName("BPM���̺ţ�"+procInstID+";BPM������Ϣ:"+strComment);
				log.setBeizhu("���ýӿڷ�����ApproveClose");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}


	public String[] GetbillInfo(Context ctx, String strBSID, IObjectValue billInfo) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	StringBuffer xml = new StringBuffer();
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n"); 
			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getDisplayName())+"</OrgName>\n");
			if(Info.getUseDepartment()!=null)
			xml.append("<Department>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</Department>\n");
			xml.append("<ApplyDate>"+dateFormat.format(Info.getCreateTime())+"</ApplyDate>\n");
			xml.append("<Applicant>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Applicant>\n");
			//xml.append("<OrgCode>"+StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0])+ "</OrgCode>\n");
			xml.append("<Position>��Լ������</Position>\n");
			xml.append("<Topic>"+StringUtilBPM.isNULl(Info.getUsage())+"</Topic>\n");  //����Ϊ��
		    if(Info.getOrgUnit()!=null)
			xml.append("<orgunit>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</orgunit>\n");
		    if(Info.getCurProject()!=null)
			xml.append("<curProject>"+StringUtilBPM.isNULl(Info.getCurProject().getDisplayName())+"</curProject>\n");
			xml.append("<contractNumber>"+StringUtilBPM.isNULl(Info.getContractNo())+"</contractNumber>\n");
			xml.append("<contractName>"+StringUtilBPM.isNULl(Info.getContractName())+"</contractName>\n");
			if(Info.getUseDepartment()!=null)
			xml.append("<useDepartment>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</useDepartment>\n");
			xml.append("<bizdate>"+Info.getBookedDate()+"</bizdate>\n");
			xml.append("<number>"+StringUtilBPM.isNULl(Info.getNumber())+"</number>\n");
			xml.append("<payDate>"+dateFormat.format(Info.getPayDate())+"</payDate>\n");//��������
			if(Info.getPeriod()!=null)
			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");//�����ڼ�
			if(Info.getIsDifferPlace()!=null)
			xml.append("<isdifferplace>"+StringUtilBPM.isNULl(Info.getIsDifferPlace().getAlias())+"</isdifferplace>\n");//ͬ�����
			if(Info.getPaymentType()!=null)
			xml.append("<PaymentType>"+StringUtilBPM.isNULl(Info.getPaymentType().getName())+"</PaymentType>\n");//��������
			if(Info.getSettlementType()!=null)
			xml.append("<settlementType>"+StringUtilBPM.isNULl(Info.getSettlementType().getName())+"</settlementType>\n");//��ͬ����
			if(Info.getSupplier()!=null)
			xml.append("<supplier>"+StringUtilBPM.isNULl(Info.getSupplier().getName())+"</supplier>\n");//�տλ
			
			xml.append("<recBank>"+StringUtilBPM.isNULl(Info.getRecBank())+"</recBank>\n");//�տ�����
			xml.append("<recAccount>"+StringUtilBPM.isNULl(Info.getRecAccount())+"</recAccount>\n");//�տ��˺�
			if(Info.getRealSupplier()!=null)
			xml.append("<realSupplier>"+StringUtilBPM.isNULl(Info.getRealSupplier().getName())+"</realSupplier>\n");//ʵ���տλ
			xml.append("<Desc>"+StringUtilBPM.isNULl(Info.getDescription())+"</Desc>\n");// ժҪ
			xml.append("<usage>"+StringUtilBPM.isNULl(Info.getUsage())+"</usage>\n");//��;
   
			xml.append("<currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currency>\n");//�ұ�
			xml.append("<exchangeRate>"+Info.getExchangeRate()+"</exchangeRate>\n");//����
			xml.append("<originalAmount>"+Info.getOriginalAmount()+"</originalAmount>\n");//ԭ�ҽ��--
			xml.append("<amount>"+Info.getAmount()+"</amount>\n");//���ҽ��
			if(Info.getInvoiceAmt()!=null)
			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//��Ʊ���
			else
			{
				xml.append("<invoiceAmt>0</invoiceAmt>\n");//��Ʊ���
			}
			xml.append("<allinvoiceAmt>"+Info.getAllInvoiceAmt()+"</allinvoiceAmt>\n");//�ۼƷ�Ʊ���
			//xml.append("<amount>"+Info.getAmount()+"</amount>\n");//���ҽ��
			if(Info.getGrtAmount()!=null)
			xml.append("<grtAmount>"+Info.getGrtAmount()+"</grtAmount>\n");//���ҽ��
			else
			{
				xml.append("<grtAmount>0</grtAmount>\n");//���ҽ��
			}
			xml.append("<capitalAmount>"+Info.getCapitalAmount()+"</capitalAmount>\n");//��д���
			if(false==Info.isIsRespite())
			xml.append("<isRespite>��</isRespite>\n");//�Ƿ�Ӽ�
			else
			{
				xml.append("<isRespite>��</isRespite>\n");//�Ƿ�Ӽ�
			}
			xml.append("<Process>"+StringUtilBPM.isNULl(Info.getProcess())+"</Process>\n");
			xml.append("<payTimes>"+Info.getPayTimes()+"</payTimes>\n");
			xml.append("<DepPlanState>"+Info.getDepPlanState()+"</DepPlanState>\n");
//			if(Info.getDepPlanState()==null)
//			{
//				xml.append("<DepPlanState>�޼ƻ�����</DepPlanState>\n");
//			}
//			else if(Info.getDepPlanState().getName()=="0")
//			{
//				xml.append("<DepPlanState>�޼ƻ�����</DepPlanState>\n");	
//			}
//			else if(Info.getDepPlanState().getName()=="1")
//			{
//				xml.append("<DepPlanState>�ƻ��ڸ���</DepPlanState>\n");	
//			}
//			else if(Info.getDepPlanState().getName()=="2")
//			{
//				xml.append("<DepPlanState>���ƻ�����</DepPlanState>\n");	
//			}
			if(false==Info.isIsPay())
			xml.append("<IsPay>��</IsPay>\n");//�Ƿ��ύ����
			else
			{
			 xml.append("<IsPay>��</IsPay>\n");//�Ƿ��ύ����
			}
			if(Info.getPaymentProportion()!=null)//���ȿ�����
				xml.append("<paymentProportion>"+Info.getPaymentProportion()+"</paymentProportion>\n");
			if(Info.getCompletePrjAmt()!=null)//������ɹ�����
				xml.append("<completePrjAmt>"+Info.getCompletePrjAmt()+"</completePrjAmt>\n");
			if(Info.getTotalSettlePrice()!=null)//��ʵ�ֲ�ֵ
				xml.append("<TotalSettlePrice>"+Info.getTotalSettlePrice()+"</TotalSettlePrice>\n");
				xml.append("<invoiceNumber>"+StringUtilBPM.isNULl(Info.getInvoiceNumber())+"</invoiceNumber>\n");//��Ʊ��
		
			if(Info.getInvoiceOriAmt()!=null)//��Ʊ���ԭ��
				xml.append("<invoiceOriAmt>"+Info.getInvoiceOriAmt()+"</invoiceOriAmt>\n");
			else
			{
				xml.append("<invoiceOriAmt>0</invoiceOriAmt>\n");
			}
			if(Info.getInvoiceDate()!=null)//��Ʊ����
				xml.append("<invoiceDate>"+dateFormat.format(Info.getInvoiceDate())+"</invoiceDate>\n");
			if(Info.getCompletePrjAmt()!=null)//�ۼ�����ɹ�����
				xml.append("<AllCompletePrjAmt>"+Info.getCompletePrjAmt()+"</AllCompletePrjAmt>\n");
			if(Info.getPaymentProportion()!=null)//�ۼ�Ӧ���������
				xml.append("<AllPaymentProportion>"+Info.getPaymentProportion()+"</AllPaymentProportion>\n");
			if(Info.getAllInvoiceOriAmt()!=null)//�ۼƷ�Ʊԭ��/����
				xml.append("<allinvoiceOriAmt>"+Info.getAllInvoiceOriAmt()+"</allinvoiceOriAmt>\n");
			if(Info.getPlanHasCon()!=null)
			xml.append("<PlanHasCon>"+Info.getPlanHasCon().getName()+"</PlanHasCon>\n");//Ԥ����Ŀ
			if(Info.getMoneyDesc()!=null)
				xml.append("<Remarks>"+Info.getMoneyDesc()+"</Remarks>\n");//��ע
			
			if(Info.getContractId()!=null)
			{
				String sql="select sum(pay.FAmount) SFamount  from t_con_payrequestbill pay left join ";
				sql+="t_con_contractbill con on pay.FContractId=con.fid where pay.fstate!='1SAVED' ";
				sql+="and pay.fcontractid='"+Info.getContractId()+"'";
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
	    		builder.appendSql(sql);
                IRowSet Rowset=builder.executeQuery();
                if(Rowset.size()==1)
                {
                  Rowset.next();  
                  xml.append("<allAmount>" +FDCHelper.toBigDecimal(Rowset.getBigDecimal("SFamount")) + "</allAmount>\n");//��ͬ�Ѹ�����
                }
                 builder.clear();
			}
			else
			{
				xml.append("<allAmount>0</allAmount>\n");//��ͬ�Ѹ�����
			}
			
			
			
			  EntityViewInfo Myavevi = new EntityViewInfo();
		      FilterInfo Myavfilter = new FilterInfo();
		      Myavfilter.getFilterItems().add(new FilterItemInfo("id",Info.getContractId(),CompareType.EQUALS));
		      Myavevi.setFilter(Myavfilter);
		      ContractBillCollection con=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(Myavevi);
		      if(con.size()>0)
		      {
		    	  for(int a=0;a<con.size();a++)
		    	  {
		    		  ContractBillInfo conInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(con.get(a).getId()));
		    		  xml.append("<contractTypeName>"+conInfo.getContractType().getName()+"</contractTypeName>\n");//��ͬ����
		    		  xml.append("<HTamount>"+conInfo.getAmount()+"</HTamount>\n");//��ͬ���
		    	  }
		      }
		      
		      xml.append("<CostedName>"+Info.getCostedCompany().getName()+"</CostedName>\n");//��ͬ����
		      xml.append("<OrgCode>"+Info.getCostedCompany().getNumber()+"</OrgCode>\n");//��ͬ����
		      xml.append("<CostedDept>"+Info.getCostedDept().getName()+"</CostedDept>\n");//��ͬ����
		      
		      
			  int control=1;
				  EntityViewInfo Myavevi2 = new EntityViewInfo();
			      FilterInfo Myavfilter2 = new FilterInfo();
			      Myavfilter2.getFilterItems().add(new FilterItemInfo("head",Info.getId(),CompareType.EQUALS));
			      Myavevi2.setFilter(Myavfilter2);
			      PayRequestBillBgEntryCollection contractBasecoll=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryCollection(Myavevi2);
				  if(contractBasecoll.size()>0)
			  {
				  xml.append("<billEntries>\n");
				  for(int ab=0;ab<contractBasecoll.size();ab++)
				  {
					  PayRequestBillBgEntryInfo Conbaseinfo=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryInfo(new ObjectUuidPK(contractBasecoll.get(ab).getId()));
					  xml.append("<item>\n");
					  String sql="select FName_L2 from T_BG_BgItem where fid='"+Conbaseinfo.getBgItem().getId()+"'";
					  FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		    		  builder.appendSql(sql);
	                  IRowSet Rowset=builder.executeQuery();
	                  if(Rowset.size()==1)
	                  {
	                   Rowset.next();  
	                   xml.append("<BgeItem>"+Rowset.getString("FName_L2")+"</BgeItem>\n");//Ԥ�����
	                  }
	                  builder.clear();
					  
	                  String sql2="select FName_l2 from T_BD_Currency where fid='"+Conbaseinfo.getCurrency().getId()+"'";
					  FDCSQLBuilder builders=new FDCSQLBuilder(ctx);
		    		  builders.appendSql(sql2);
	                  IRowSet Rowsets=builders.executeQuery();
	                  if(Rowsets.size()==1)
	                  {
	                   Rowsets.next();  
	                   xml.append("<BgeCurrency>"+Rowsets.getString("FName_L2")+"</BgeCurrency>\n");//�ұ�
	                  }
	                  builders.clear();
					  xml.append("<BgeRate>"+Conbaseinfo.getRate()+"</BgeRate>\n");// ����  
					  xml.append("<BgeAmount>"+Conbaseinfo.getAmount()+"</BgeAmount>\n");//  ԭ�ҽ�� 
					  xml.append("<BgeRequestAmount>"+Conbaseinfo.getRequestAmount()+"</BgeRequestAmount>\n");//���ҽ��
					  xml.append("<BgeBalance>"+Conbaseinfo.getBgBalance()+"</BgeBalance>\n");//Ԥ����� 
					  xml.append("<BgeRemark>"+StringUtilBPM.isNULl(Conbaseinfo.getRemark())+"</BgeRemark>\n");//��ע
//					  if(Conbaseinfo.getIsHasBill().equals("0"))
//					  {
//						  xml.append("<IsHasBill>��</IsHasBill>\n");//�Ƿ��е���  
//					  }
//					  else
//					  {
//						  xml.append("<IsHasBill>��</IsHasBill>\n");//�Ƿ��е���
//					  }
					  if(Conbaseinfo.getIsHasBill().equals(""))
					  {
						  xml.append("<IsHasBill></IsHasBill>\n");//�Ƿ��е���
					  }
					  xml.append("<IsHasBill>"+Conbaseinfo.getIsHasBill()+"</IsHasBill>\n");//�Ƿ��е���  
					  
				      xml.append("</item>\n");
				      BigDecimal balance=Conbaseinfo.getBgBalance()!=null?Conbaseinfo.getBgBalance():FDCHelper.ZERO;
				      if(balance.compareTo(Conbaseinfo.getRequestAmount())<0){
				    	  control=0;
				      }
				  }
				  xml.append("</billEntries>\n");
			  }
			xml.append("<controlType>"+control+"</controlType>\n");//��������
			
			xml.append("</DATA>"); 
			str[1] = xml.toString();
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��"+Info.getId()+"; ��ţ�"+Info.getNumber());
				log.setDescription("EAS���:"+str[0]+"; ������Ϣ"+str[1]+str[2]);
				log.setBeizhu("���ýӿڷ�����GetbillInfo");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}


	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("OrgUnit.id"));
		sic.add(new SelectorItemInfo("OrgUnit.number"));
		sic.add(new SelectorItemInfo("OrgUnit.name"));
		sic.add(new SelectorItemInfo("OrgUnit.DisplayName"));
		
		
		sic.add(new SelectorItemInfo("UseDepartment.name"));
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("CurProject.name"));
		sic.add(new SelectorItemInfo("CurProject.DisplayName"));
		sic.add(new SelectorItemInfo("ContractNo"));
		sic.add(new SelectorItemInfo("BizDate"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("PayDate"));
		sic.add(new SelectorItemInfo("Period.number"));
		sic.add(new SelectorItemInfo("IsDifferPlace"));
		sic.add(new SelectorItemInfo("PaymentType.name"));
		sic.add(new SelectorItemInfo("SettlementType.name"));
		sic.add(new SelectorItemInfo("Supplier.name"));
		sic.add(new SelectorItemInfo("RecBank"));
		sic.add(new SelectorItemInfo("RecAccount"));
		sic.add(new SelectorItemInfo("RealSupplier.name"));
		sic.add(new SelectorItemInfo("Description"));
		sic.add(new SelectorItemInfo("Usage"));
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("ExchangeRate"));
		sic.add(new SelectorItemInfo("ContractId"));
		
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("InvoiceAmt"));
		sic.add(new SelectorItemInfo("AllInvoiceAmt"));
		sic.add(new SelectorItemInfo("Amount"));
		sic.add(new SelectorItemInfo("GrtAmount"));
		sic.add(new SelectorItemInfo("CapitalAmount"));
		sic.add(new SelectorItemInfo("IsRespite"));
		sic.add(new SelectorItemInfo("IsPay"));
		sic.add(new SelectorItemInfo("PaymentProportion"));
		sic.add(new SelectorItemInfo("CompletePrjAmt"));
		sic.add(new SelectorItemInfo("TotalSettlePrice"));
		
		sic.add(new SelectorItemInfo("InvoiceNumber"));
		sic.add(new SelectorItemInfo("InvoiceOriAmt"));
		sic.add(new SelectorItemInfo("InvoiceDate"));
		sic.add(new SelectorItemInfo("AllReqPercent"));
		sic.add(new SelectorItemInfo("AllInvoiceOriAmt"));
		sic.add(new SelectorItemInfo("PlanHasCon.name"));
		sic.add(new SelectorItemInfo("MoneyDesc"));
		sic.add(new SelectorItemInfo("State"));
		
		sic.add(new SelectorItemInfo("CostedCompany.name"));
		sic.add(new SelectorItemInfo("CostedCompany.number"));
		sic.add(new SelectorItemInfo("CostedDept.name"));
		
		sic.add(new SelectorItemInfo("ContractName"));
		sic.add(new SelectorItemInfo("Process"));
		sic.add(new SelectorItemInfo("BookedDate"));
		sic.add(new SelectorItemInfo("PayTimes"));
		sic.add(new SelectorItemInfo("cu.id"));
		sic.add(new SelectorItemInfo("DepPlanState"));
		sic.add(new SelectorItemInfo("ContractBase"));
		sic.add(new SelectorItemInfo("id"));
		
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
