package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanDataCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataFactory;
import com.kingdee.eas.fdc.finance.ConPayPlanDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewDataInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewUnsignCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewUnsignFactory;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanInfo;
import com.kingdee.eas.fdc.finance.SignStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectDynaticFundPayPlanControllerBean extends AbstractProjectDynaticFundPayPlanControllerBean {
	private static final long serialVersionUID = 1L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ProjectDynaticFundPayPlanControllerBean");

	/**
	 * @param curProjectIds:��Ҫͬ������Ŀid
	 */
	protected void _synData(Context ctx, Set curProjectIds) throws BOSException, EASBizException {
		Iterator it = curProjectIds.iterator();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		while (it.hasNext()) {
			sql.clear();
			Object curProjectId = it.next();
			//1����ȡ��Ŀ�İ汾�š�
			sql
					.appendSql("SELECT PD.FVersionNumber FVersionNumber,PD.FVersionName FVersionName,PD.FMainVerNo FMainVerNo,PD.FSubVerNo FSubVerNo,CP.FNAME_"
							+ ctx.getLocale()
							+ "  CURNAME FROM T_FNC_ProjectDynaticFPP PD LEFT OUTER JOIN T_FDC_CurProject CP ON CP.FID=PD.FCurProjectID  WHERE PD.FCurProjectID=? and PD.FIsLastVersion=1 ");
			sql.addParam(curProjectId);
			//2�������°汾�š�
			IRowSet row = sql.executeQuery();
			int mainVerNo = 1;
			int subVerNo = 0;
			String curName = null;
			boolean bool = false;
			try {
				if (row != null && row.next()) {
					mainVerNo = row.getInt("FMainVerNo");
					subVerNo = row.getInt("FSubVerNo") + 1;
					curName = row.getString("CURNAME");
					//3������ԭ��ĿΪ�����հ汾��
					sql.clear();
					sql.appendSql("UPDATE T_FNC_ProjectDynaticFPP SET FIsLastVersion=0 WHERE FCurProjectID=? ");
					sql.addParam(curProjectId);
					sql.execute();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
			String versionNumber = mainVerNo + "." + subVerNo;
			//4����ȡ�����ݡ�
			//4.1��ȡ���µġ���Լ�滮����
			PayPlanNewCollection ppnc = PayPlanNewFactory
					.getLocalInstance(ctx)
					.getPayPlanNewCollection(
							"select *,Data.*,programming.*,programming.programming.project.*  where programming.programming.isLatest=1 and programming.programming.project.id='"
									+ curProjectId + "' order by programming.sortNumber");
			//ConPayPlanCollection cppc = ConPayPlanFactory.getLocalInstance(ctx).getConPayPlanCollection("select * where curProject.id='"+curProjectId+"' ");
			//4.3��ȡ����ǩ����ͬ����ƻ�������̨������
			//4.4���¡���Ŀ��̬�ʽ�֧���ƻ����б��е����ݡ���ǩ������������ֵΪ����������ʾΪ0��
			Map proContractMap = new TreeMap();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programming.isLatest",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("programming.project.id",curProjectId));
			view.setFilter(filter );
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("amount");
			coll.add("name");
			coll.add("sortNumber");
			coll.add("id");
			view.setSelector(coll );
			SorterItemCollection sort =new SorterItemCollection();
			sort.add(new SorterItemInfo("sortNumber"));
			view.setSorter(sort );
			ProgrammingContractCollection programmingContractCollection = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractCollection(view);
			for(int i=0;i<programmingContractCollection.size();i++){
				ProgrammingContractInfo programmingContractInfo = programmingContractCollection.get(i);
				proContractMap.put(programmingContractInfo.getSortNumber(), programmingContractInfo);
			}
			
			ProjectDynaticFundPayPlanInfo info = new ProjectDynaticFundPayPlanInfo();
			//�������汾��
			info.setMainVerNo(mainVerNo);
			//�����Ӱ汾��
			info.setSubVerNo(subVerNo);
			//���ð汾��
			info.setVersionNumber(versionNumber);
			//�����޶�����
			info.setRecenseDate(new Date());
			CurProjectInfo curPInfo = new CurProjectInfo();
			curPInfo.setId(BOSUuid.read(curProjectId.toString()));
			info.setCurProject(curPInfo);
			
//			List monthKeyLst = new ArrayList(proContractMap.keySet());
//			// ����
//			Collections.sort(monthKeyLst, new Comparator() {
//
//				public int compare(Object o1, Object o2) {
//					Integer d1 = (Integer) o1;
//					Integer d2 = (Integer) o2;
//					return d2.intValue() - d1.intValue();
//				}
//			});
			
			for(Iterator ites =proContractMap.keySet().iterator();ites.hasNext();){
				int proConId = ((Integer) ites.next()).intValue();
				ProgrammingContractInfo programmingContractInfo=(ProgrammingContractInfo) proContractMap.get(proConId);
				//��ǩ����
				ProjectDynaticFundPayPlanEntryInfo eInfo = new ProjectDynaticFundPayPlanEntryInfo();
				//���ú�Լ�滮
				eInfo.setProgramming(programmingContractInfo);
				//���ù滮���
				eInfo.setProgrammingAmount(programmingContractInfo.getAmount());
				info.getEntry().add(eInfo);
				
				
				if (ppnc != null && ppnc.size() > 0) {
					for (int i = 0; i < ppnc.size(); i++) {

						PayPlanNewInfo payPlanNewInfo = ppnc.get(i);
						if(payPlanNewInfo.getProgramming().getSortNumber()!=proConId){
							continue;
						}
						//4.2��ȡ���µġ���ͬ����ƻ���

						ConPayPlanDataCollection conPayPlan = ConPayPlanDataFactory.getLocalInstance(ctx).getConPayPlanDataCollection(
								"select * where parent0.contractBill.id in(select fid from T_CON_Contractbill where FProgrammingContract ='"
										+ payPlanNewInfo.getProgramming().getId() + "') order by payMonth ");

						//��ǩ����
//						ProjectDynaticFundPayPlanEntryInfo eInfo = new ProjectDynaticFundPayPlanEntryInfo();
//						//���ú�Լ�滮
//						eInfo.setProgramming(payPlanNewInfo.getProgramming());
//						//���ù滮���
//						eInfo.setProgrammingAmount(payPlanNewInfo.getProgramming().getAmount());
						//����ǩԼ���
						eInfo.setSignState(SignStateEnum.SIGNED);

						//4.2��ȡ���µġ���ͬ����ƻ���
						PayPlanNewUnsignCollection payPlanUnsign = PayPlanNewUnsignFactory.getLocalInstance(ctx).getPayPlanNewUnsignCollection(
								"select * where parent.programming.id='" + payPlanNewInfo.getProgramming().getId() + "' order by payMonth ");

						//δǩ��
						ProjectDynaticFundPayPlanEntryInfo eInfo2 = new ProjectDynaticFundPayPlanEntryInfo();
						//���ú�Լ�滮
						eInfo2.setProgramming(payPlanNewInfo.getProgramming());
						//���ù滮���
						eInfo2.setProgrammingAmount(payPlanNewInfo.getProgramming().getAmount());
						//����ǩԼ���
						eInfo2.setSignState(SignStateEnum.UNSIGN);
						//��ǩԼ���
						BigDecimal signSum = BigDecimal.ZERO;
						//δǩԼ���
						BigDecimal unSignSum = BigDecimal.ZERO;
						//��丶������
						PayPlanNewDataCollection datas = payPlanNewInfo.getData();
						
						Set monthSet=getAllPlanMonth(datas,conPayPlan);
						
//						if (monthSet != null && monthSet.size() > 0) {
							
							for(Iterator ite = monthSet.iterator();ite.hasNext();){
								int payMonth = ((Integer) ite.next()).intValue();
								BigDecimal payAmount = FDCHelper.ZERO;
								for (int j = 0; j < datas.size(); j++) {
									if( datas.get(j).getPayMonth()==payMonth){
										payAmount= datas.get(j).getPayAmount();
										break;
									}
								}
								
								//��ǩ��������
								ProjectDynaticFundPayPlanDetailEntryInfo deInfo = new ProjectDynaticFundPayPlanDetailEntryInfo();
								deInfo.setPayMonth(payMonth);
								eInfo.getDetailEntry().add(deInfo);

								//δǩ��������
								ProjectDynaticFundPayPlanDetailEntryInfo deInfo2 = new ProjectDynaticFundPayPlanDetailEntryInfo();
								deInfo2.setPayMonth(payMonth);
								eInfo2.getDetailEntry().add(deInfo2);
								//��ȡ��ͬ�·ݵ��Ѹ������ݡ�
								BigDecimal signSum2 = BigDecimal.ZERO;
								BigDecimal unSignSum2 = BigDecimal.ZERO;
								//����ͬһ��Լ�滮�����ڲ�ͬ��ͬ�����ݣ����ѭ�����ӡ�
								for (int k = 0; k < conPayPlan.size(); k++) {
									//�·���ͬ
									if (conPayPlan.get(k).getPayMonth() == payMonth) {
										if (conPayPlan.get(k).getPayAmount() != null) {
											signSum2 = signSum2.add(conPayPlan.get(k).getPayAmount());
											signSum = signSum.add(conPayPlan.get(k).getPayAmount());

										}
									}
								}

								//���û������ͬ����ֱ��ȡ��Լ�滮�е�����
								if (conPayPlan.size() == 0) {
									for (int k = 0; k < payPlanNewInfo.getData().size(); k++) {
										PayPlanNewDataInfo dataInfo = payPlanNewInfo.getData().get(k);
										//�·���ͬ
										if (dataInfo.getPayMonth() == payMonth) {
											if (dataInfo.getPayAmount() != null) {
												//deInfo2.setPayAmount(payAmount.subtract(payPlanUnsign.get(k).getPayAmount()));
												unSignSum = unSignSum.add(dataInfo.getPayAmount());
												unSignSum2 = unSignSum2.add(dataInfo.getPayAmount());
											}
										}
									}
								} else {
									for (int k = 0; k < payPlanUnsign.size(); k++) {
										//�·���ͬ
										if (payPlanUnsign.get(k).getPayMonth() == payMonth) {
											if (payPlanUnsign.get(k).getPayAmount() != null) {
												//deInfo2.setPayAmount(payAmount.subtract(payPlanUnsign.get(k).getPayAmount()));
												unSignSum = unSignSum.add(payPlanUnsign.get(k).getPayAmount());
												unSignSum2 = unSignSum2.add(payPlanUnsign.get(k).getPayAmount());
											}
										}
									}
								}

								deInfo.setPayAmount(signSum2);
								deInfo2.setPayAmount(unSignSum2);
								
								
							}
//							for (int j = 0; j < datas.size(); j++) {
//								
//								
//								int payMonth = datas.get(j).getPayMonth();
//								BigDecimal payAmount = datas.get(j).getPayAmount();
//								if (payAmount == null) {
//									payAmount = BigDecimal.ZERO;
//								}
//								//��ǩ��������
//								ProjectDynaticFundPayPlanDetailEntryInfo deInfo = new ProjectDynaticFundPayPlanDetailEntryInfo();
//								deInfo.setPayMonth(payMonth);
//								eInfo.getDetailEntry().add(deInfo);
//
//								//δǩ��������
//								ProjectDynaticFundPayPlanDetailEntryInfo deInfo2 = new ProjectDynaticFundPayPlanDetailEntryInfo();
//								deInfo2.setPayMonth(payMonth);
//								eInfo2.getDetailEntry().add(deInfo2);
//								//��ȡ��ͬ�·ݵ��Ѹ������ݡ�
//								BigDecimal signSum2 = BigDecimal.ZERO;
//								BigDecimal unSignSum2 = BigDecimal.ZERO;
//								//����ͬһ��Լ�滮�����ڲ�ͬ��ͬ�����ݣ����ѭ�����ӡ�
//								for (int k = 0; k < conPayPlan.size(); k++) {
//									//�·���ͬ
//									if (conPayPlan.get(k).getPayMonth() == payMonth) {
//										if (conPayPlan.get(k).getPayAmount() != null) {
//											signSum2 = signSum2.add(conPayPlan.get(k).getPayAmount());
//											signSum = signSum.add(conPayPlan.get(k).getPayAmount());
//
//										}
//									}
//								}
//
//								//���û������ͬ����ֱ��ȡ��Լ�滮�е�����
//								if (conPayPlan.size() == 0) {
//									for (int k = 0; k < payPlanNewInfo.getData().size(); k++) {
//										PayPlanNewDataInfo dataInfo = payPlanNewInfo.getData().get(k);
//										//�·���ͬ
//										if (dataInfo.getPayMonth() == payMonth) {
//											if (dataInfo.getPayAmount() != null) {
//												//deInfo2.setPayAmount(payAmount.subtract(payPlanUnsign.get(k).getPayAmount()));
//												unSignSum = unSignSum.add(dataInfo.getPayAmount());
//												unSignSum2 = unSignSum2.add(dataInfo.getPayAmount());
//											}
//										}
//									}
//								} else {
//									for (int k = 0; k < payPlanUnsign.size(); k++) {
//										//�·���ͬ
//										if (payPlanUnsign.get(k).getPayMonth() == payMonth) {
//											if (payPlanUnsign.get(k).getPayAmount() != null) {
//												//deInfo2.setPayAmount(payAmount.subtract(payPlanUnsign.get(k).getPayAmount()));
//												unSignSum = unSignSum.add(payPlanUnsign.get(k).getPayAmount());
//												unSignSum2 = unSignSum2.add(payPlanUnsign.get(k).getPayAmount());
//											}
//										}
//									}
//								}
//
//								deInfo.setPayAmount(signSum2);
//								deInfo2.setPayAmount(unSignSum2);
//							}
							
							
//						}else{
//							
//							Set payMonthSet = new HashSet();
//							for (int j = 0; j < conPayPlan.size(); j++) {
//								int payMonth = conPayPlan.get(j).getPayMonth();
//								//��ǩ��������
//								ProjectDynaticFundPayPlanDetailEntryInfo deInfo = new ProjectDynaticFundPayPlanDetailEntryInfo();
//								deInfo.setPayMonth(payMonth);
//								eInfo.getDetailEntry().add(deInfo);
//
//								BigDecimal signSum2 = BigDecimal.ZERO;
//								//����ͬһ��Լ�滮�����ڲ�ͬ��ͬ�����ݣ����ѭ�����ӡ�
//								for (int k = 0; k < conPayPlan.size(); k++) {
//									//�·���ͬ
//									if (conPayPlan.get(k).getPayMonth() == payMonth) {
//										if (conPayPlan.get(k).getPayAmount() != null) {
//											signSum2 = signSum2.add(conPayPlan.get(k).getPayAmount());
//											if(!payMonthSet.contains(payMonth)){
//												signSum = signSum.add(conPayPlan.get(k).getPayAmount());
//											}
//
//										}
//									}
//								}
//
//								payMonthSet.add(payMonth);
//								deInfo.setPayAmount(signSum2);
//							}
//							
//							
//							
//							
//						}
						eInfo.setTotalAmount(signSum);
						eInfo2.setTotalAmount(unSignSum);
//						info.getEntry().add(eInfo);
						info.getEntry().add(eInfo2);
					}
				} else {
					continue;
				}
				
			}
			
			
			//����Ϊ���հ汾
			info.setIsLastVersion(true);
			//����orgUnit
			info.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
			_save(ctx, info);
		}
	}

	private Set getAllPlanMonth(PayPlanNewDataCollection datas,
			ConPayPlanDataCollection conPayPlan) {
		Set set = new HashSet();
		for(int i=0;i<datas.size();i++){
			PayPlanNewDataInfo payPlanNewDataInfo = datas.get(i);
			set.add(payPlanNewDataInfo.getPayMonth());
		}
		for(int i=0;i<conPayPlan.size();i++){
			ConPayPlanDataInfo conPayPlanDataInfo = conPayPlan.get(i);
			set.add(conPayPlanDataInfo.getPayMonth());
		}
		
		return set;
	}


	protected boolean isUseNumber() {
		return false;
	}

	protected boolean isUseName() {
		return false;
	}
}