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
	 * @param curProjectIds:需要同步的项目id
	 */
	protected void _synData(Context ctx, Set curProjectIds) throws BOSException, EASBizException {
		Iterator it = curProjectIds.iterator();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		while (it.hasNext()) {
			sql.clear();
			Object curProjectId = it.next();
			//1、获取项目的版本号。
			sql
					.appendSql("SELECT PD.FVersionNumber FVersionNumber,PD.FVersionName FVersionName,PD.FMainVerNo FMainVerNo,PD.FSubVerNo FSubVerNo,CP.FNAME_"
							+ ctx.getLocale()
							+ "  CURNAME FROM T_FNC_ProjectDynaticFPP PD LEFT OUTER JOIN T_FDC_CurProject CP ON CP.FID=PD.FCurProjectID  WHERE PD.FCurProjectID=? and PD.FIsLastVersion=1 ");
			sql.addParam(curProjectId);
			//2、设置新版本号。
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
					//3、设置原项目为非最终版本。
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
			//4、获取新数据。
			//4.1获取最新的“合约规划”。
			PayPlanNewCollection ppnc = PayPlanNewFactory
					.getLocalInstance(ctx)
					.getPayPlanNewCollection(
							"select *,Data.*,programming.*,programming.programming.project.*  where programming.programming.isLatest=1 and programming.programming.project.id='"
									+ curProjectId + "' order by programming.sortNumber");
			//ConPayPlanCollection cppc = ConPayPlanFactory.getLocalInstance(ctx).getConPayPlanCollection("select * where curProject.id='"+curProjectId+"' ");
			//4.3获取“待签订合同付款计划”（后台）数据
			//4.4更新“项目动态资金支付计划”列表中的数据。待签订如果计算出的值为负数，则显示为0。
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
			//设置主版本号
			info.setMainVerNo(mainVerNo);
			//设置子版本号
			info.setSubVerNo(subVerNo);
			//设置版本号
			info.setVersionNumber(versionNumber);
			//设置修订日期
			info.setRecenseDate(new Date());
			CurProjectInfo curPInfo = new CurProjectInfo();
			curPInfo.setId(BOSUuid.read(curProjectId.toString()));
			info.setCurProject(curPInfo);
			
//			List monthKeyLst = new ArrayList(proContractMap.keySet());
//			// 排序
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
				//已签订的
				ProjectDynaticFundPayPlanEntryInfo eInfo = new ProjectDynaticFundPayPlanEntryInfo();
				//设置合约规划
				eInfo.setProgramming(programmingContractInfo);
				//设置规划金额
				eInfo.setProgrammingAmount(programmingContractInfo.getAmount());
				info.getEntry().add(eInfo);
				
				
				if (ppnc != null && ppnc.size() > 0) {
					for (int i = 0; i < ppnc.size(); i++) {

						PayPlanNewInfo payPlanNewInfo = ppnc.get(i);
						if(payPlanNewInfo.getProgramming().getSortNumber()!=proConId){
							continue;
						}
						//4.2获取最新的“合同付款计划”

						ConPayPlanDataCollection conPayPlan = ConPayPlanDataFactory.getLocalInstance(ctx).getConPayPlanDataCollection(
								"select * where parent0.contractBill.id in(select fid from T_CON_Contractbill where FProgrammingContract ='"
										+ payPlanNewInfo.getProgramming().getId() + "') order by payMonth ");

						//已签订的
//						ProjectDynaticFundPayPlanEntryInfo eInfo = new ProjectDynaticFundPayPlanEntryInfo();
//						//设置合约规划
//						eInfo.setProgramming(payPlanNewInfo.getProgramming());
//						//设置规划金额
//						eInfo.setProgrammingAmount(payPlanNewInfo.getProgramming().getAmount());
						//设置签约情况
						eInfo.setSignState(SignStateEnum.SIGNED);

						//4.2获取最新的“合同付款计划”
						PayPlanNewUnsignCollection payPlanUnsign = PayPlanNewUnsignFactory.getLocalInstance(ctx).getPayPlanNewUnsignCollection(
								"select * where parent.programming.id='" + payPlanNewInfo.getProgramming().getId() + "' order by payMonth ");

						//未签订
						ProjectDynaticFundPayPlanEntryInfo eInfo2 = new ProjectDynaticFundPayPlanEntryInfo();
						//设置合约规划
						eInfo2.setProgramming(payPlanNewInfo.getProgramming());
						//设置规划金额
						eInfo2.setProgrammingAmount(payPlanNewInfo.getProgramming().getAmount());
						//设置签约情况
						eInfo2.setSignState(SignStateEnum.UNSIGN);
						//已签约金额
						BigDecimal signSum = BigDecimal.ZERO;
						//未签约金额
						BigDecimal unSignSum = BigDecimal.ZERO;
						//填充付款数据
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
								
								//已签订的数据
								ProjectDynaticFundPayPlanDetailEntryInfo deInfo = new ProjectDynaticFundPayPlanDetailEntryInfo();
								deInfo.setPayMonth(payMonth);
								eInfo.getDetailEntry().add(deInfo);

								//未签订的数据
								ProjectDynaticFundPayPlanDetailEntryInfo deInfo2 = new ProjectDynaticFundPayPlanDetailEntryInfo();
								deInfo2.setPayMonth(payMonth);
								eInfo2.getDetailEntry().add(deInfo2);
								//获取相同月份的已付款数据。
								BigDecimal signSum2 = BigDecimal.ZERO;
								BigDecimal unSignSum2 = BigDecimal.ZERO;
								//由于同一合约规划，存在不同合同的数据，因此循环叠加。
								for (int k = 0; k < conPayPlan.size(); k++) {
									//月份相同
									if (conPayPlan.get(k).getPayMonth() == payMonth) {
										if (conPayPlan.get(k).getPayAmount() != null) {
											signSum2 = signSum2.add(conPayPlan.get(k).getPayAmount());
											signSum = signSum.add(conPayPlan.get(k).getPayAmount());

										}
									}
								}

								//如果没关联合同，则直接取合约规划中的数据
								if (conPayPlan.size() == 0) {
									for (int k = 0; k < payPlanNewInfo.getData().size(); k++) {
										PayPlanNewDataInfo dataInfo = payPlanNewInfo.getData().get(k);
										//月份相同
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
										//月份相同
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
//								//已签订的数据
//								ProjectDynaticFundPayPlanDetailEntryInfo deInfo = new ProjectDynaticFundPayPlanDetailEntryInfo();
//								deInfo.setPayMonth(payMonth);
//								eInfo.getDetailEntry().add(deInfo);
//
//								//未签订的数据
//								ProjectDynaticFundPayPlanDetailEntryInfo deInfo2 = new ProjectDynaticFundPayPlanDetailEntryInfo();
//								deInfo2.setPayMonth(payMonth);
//								eInfo2.getDetailEntry().add(deInfo2);
//								//获取相同月份的已付款数据。
//								BigDecimal signSum2 = BigDecimal.ZERO;
//								BigDecimal unSignSum2 = BigDecimal.ZERO;
//								//由于同一合约规划，存在不同合同的数据，因此循环叠加。
//								for (int k = 0; k < conPayPlan.size(); k++) {
//									//月份相同
//									if (conPayPlan.get(k).getPayMonth() == payMonth) {
//										if (conPayPlan.get(k).getPayAmount() != null) {
//											signSum2 = signSum2.add(conPayPlan.get(k).getPayAmount());
//											signSum = signSum.add(conPayPlan.get(k).getPayAmount());
//
//										}
//									}
//								}
//
//								//如果没关联合同，则直接取合约规划中的数据
//								if (conPayPlan.size() == 0) {
//									for (int k = 0; k < payPlanNewInfo.getData().size(); k++) {
//										PayPlanNewDataInfo dataInfo = payPlanNewInfo.getData().get(k);
//										//月份相同
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
//										//月份相同
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
//								//已签订的数据
//								ProjectDynaticFundPayPlanDetailEntryInfo deInfo = new ProjectDynaticFundPayPlanDetailEntryInfo();
//								deInfo.setPayMonth(payMonth);
//								eInfo.getDetailEntry().add(deInfo);
//
//								BigDecimal signSum2 = BigDecimal.ZERO;
//								//由于同一合约规划，存在不同合同的数据，因此循环叠加。
//								for (int k = 0; k < conPayPlan.size(); k++) {
//									//月份相同
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
			
			
			//设置为最终版本
			info.setIsLastVersion(true);
			//设置orgUnit
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