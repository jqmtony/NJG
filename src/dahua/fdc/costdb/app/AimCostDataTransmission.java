package com.kingdee.eas.fdc.costdb.app;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.IAimCost;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.IProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.costdb.DBAimCostCollection;
import com.kingdee.eas.fdc.costdb.DBAimCostFactory;
import com.kingdee.eas.fdc.costdb.DBAimCostInfo;
import com.kingdee.eas.fdc.costdb.DBCostEntryCollection;
import com.kingdee.eas.fdc.costdb.DBCostEntryFactory;
import com.kingdee.eas.fdc.costdb.DBCostEntryInfo;
import com.kingdee.eas.fdc.costdb.IDBAimCost;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:目标成本导入导出类
 * 
 * @author jackwang date:2006-11-16
 *         <p>
 * @version EAS5.2
 */
public class AimCostDataTransmission extends AbstractDataTransmission {
	private static String resource = "com.kingdee.eas.fdc.costdb.CostDBResource";
	private boolean isFirstRun = true;
	private String lastFid = null;
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return AimCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	protected ICoreBase getController1(Context ctx) throws TaskExternalException {
		try {
			return DBAimCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	// Import data from file.
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		AimCostInfo info = new AimCostInfo();
		// 界面节点
		String orgOrProId = getContextParameter("orgOrProId").toString();
		CurProjectInfo curProjectInfo = null;
		FullOrgUnitInfo fullOrgUnitInfo = null;
		if (getContextParameter("curProjectInfo") != null) {
			curProjectInfo = (CurProjectInfo) getContextParameter("curProjectInfo");
		} else if (getContextParameter("fullOrgUnitInfo") != null) {
			fullOrgUnitInfo = (FullOrgUnitInfo) getContextParameter("fullOrgUnitInfo");
		}
		
		// 当前最新版本号
		String lastVerisonNumber = getContextParameter("lastVerisonNumber").toString();
		
		// 获取HEADID
		if (getContextParameter("lastFid") != null) {//界面获取了
			this.lastFid = getContextParameter("lastFid").toString();
			IAimCost iAimCost;
			try {
				iAimCost = AimCostFactory.getLocalInstance(ctx);
				info = iAimCost.getAimCostInfo(new ObjectUuidPK(this.lastFid));
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}				
		} else {//界面没有获取到,需要新增head					
			if(this.lastFid==null){//还没有缓存到
				if(this.isFirstRun){//导入第一次进入循环
					info.setOrgOrProId(orgOrProId);
					info.setVersionNumber(lastVerisonNumber);
					info.setIsLastVersion(true);
					isFirstRun = false;
				}else{
					//已经循环了一次，需要获取上次插入数据库的头
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("orgOrProId", orgOrProId));
					filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.valueOf(true)));
					evi.setFilter(filter);
					try {
						AimCostCollection acc;
						acc = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(evi);
						if (acc != null && acc.size() != 0) {
							info = acc.get(0);
							this.lastFid = acc.get(0).getId().toString();//									
						}
					} catch (BOSException e) {								
						e.printStackTrace();
					}
				}						
			}else{//缓存了lastFid						
				IAimCost iAimCost;
				try {
					iAimCost = AimCostFactory.getLocalInstance(ctx);
					info = iAimCost.getAimCostInfo(new ObjectUuidPK(this.lastFid));
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (EASBizException e) {
					e.printStackTrace();
				}
			}				
		}

		// 分录
		CostEntryInfo entryInfo = null;
		CostEntryCollection entryCollection = info.getCostEntry();
		entryInfo = new CostEntryInfo();
		// 单头对象
		entryInfo.setHead(info);
		if (curProjectInfo != null) {// 只有对工程项目节点引入时需要产品类型
			// 归属产品(产品类型)
			ProductTypeInfo productTypeInfo = null;
			try {
				Object data = ((DataToken) hsData.get("FProductTypeNumber")).data;
				if (data != null && data.toString().length() > 0) {
					String str = data.toString();
					if (str != null && str.length() > 0) {
						IProductType ipt = ProductTypeFactory.getLocalInstance(ctx);
						ProductTypeCollection collection = ipt.getProductTypeCollection(getNFilter(str));
						if (collection != null && collection.size() > 0) {
							productTypeInfo = collection.get(0);
							if (productTypeInfo != null) {
								if (!productTypeInfo.isIsEnabled()) {
									// 产品类型已被禁用
									throw new TaskExternalException(getResource(ctx, "Import_ProductType_DisEnabled"));
								} else {
									if (curProjectInfo.getCurProjProductEntries() != null) {
										CurProjProductEntriesCollection cppec = curProjectInfo.getCurProjProductEntries();
										boolean isAccObj = true;//分录中是否勾选了"是否核算对象"
										boolean flag = false;
										
										for(int i = 0 ;i<cppec.size();i++){											
											if(productTypeInfo.getId().toString().equals(cppec.get(i).getProductType().getId().toString())){
												if(cppec.get(i).isIsAccObj()){													
													flag = true;
													entryInfo.setProduct(productTypeInfo);
													break;
												}else{
													isAccObj = false;
													break;
												}
											}											
										}
										if(!isAccObj){//工程项目中该产品类型对应的分录为非核算对象,不能导入
											throw new TaskExternalException(getResource(ctx, "Import_ProductType_IsAccObj"));
										}else{
											if(!flag){//指定工程项目产品分录不包含导入指定产品类型,不能导入
												throw new TaskExternalException(getResource(ctx, "Import_CostAmount_ProjectHasNotProductType"));
											}
										}
//										CurProjProductEntriesCollection cppec = curProjectInfo.getCurProjProductEntries();
//										boolean flag = false;
//										for (int i = 0; i < cppec.size(); i++) {
//											if (productTypeInfo.getId().toString().equals(cppec.get(i).getProductType().getId().toString())) {
//												flag = true;
//												entryInfo.setProduct(productTypeInfo);
//												break;
//											}
//										}
//										if (!flag) {// 指定工程项目产品分录不包含导入指定产品类型,不能导入
//											throw new TaskExternalException(getResource(ctx, "Import_CostAmount_ProjectHasNotProductType"));
//										}

									} else {
										// 指定工程项目没有定义任何产品类型,不能导入数据
									}

								}
							}
						} else {
							// 产品类型不存在
							throw new TaskExternalException(getResource(ctx, "Import_ProductType_NotExist"));
						}
					}
				} else {
					// 产品类型没有指定
//					throw new TaskExternalException(getResource(ctx, "Import_ProductType_Null"));
				}
			} catch (BOSException e) {
				throw new TaskExternalException(e.getMessage(), e.getCause());
			}
		}
		// 成本科目
		CostAccountInfo costAccountInfo = null;
		try {
			Object data = ((DataToken) hsData.get("FCostAccountNumber")).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {
					ICostAccount ica = CostAccountFactory.getLocalInstance(ctx);
					CostAccountCollection collection = ica.getCostAccountCollection(getLNFilter(str, fullOrgUnitInfo, curProjectInfo));
					if (collection != null && collection.size() > 0) {
						costAccountInfo = collection.get(0);
						if (costAccountInfo != null) {
							if (!costAccountInfo.isIsEnabled()) {
								// 成本科目已被禁用
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_DisEnabled"));
							} else if (!costAccountInfo.isIsLeaf()) {
								// 为了不破坏汇总关系,只能在明细科目下导入
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_IsNotLeaf"));
							} else {
								entryInfo.setCostAccount(costAccountInfo);
							}
						}
					} else {
						// 成本科目不存在
						throw new TaskExternalException(getResource(ctx, "Import_CostAccount_NotExist"));
					}
				}
			} else {
				// 成本科目没有指定编码
				throw new TaskExternalException(getResource(ctx, "Import_CostAccount_Null"));
			}

		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}

		entryCollection.add(entryInfo);
		createEntry(ctx, entryInfo, hsData);

		this.cei = entryInfo;
		return info;
	}

	private CostEntryInfo cei;

	private void createEntry(Context ctx, CostEntryInfo info, Hashtable hsData) throws TaskExternalException {
		// 条目名称，没有多语言,以后孙海亮那边做了多语言,这里才能做
		String entryName = (String) ((DataToken) hsData.get("FEntryName")).data;
		info.setEntryName(entryName);
		// 工作量
		BigDecimal value;
		try {
			Object o = ((DataToken) hsData.get("FWorkload")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					info.setWorkload(value);
				}
			}
		} catch (NumberFormatException nex) {
			// 格式错误，只能是数字
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_WorkloadFormatError"));
		}
		// 单价
		try {
			Object o = ((DataToken) hsData.get("FPrice")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					info.setPrice(value);
				}
			}
		} catch (NumberFormatException nex) {
			// 格式错误，只能是数字
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_PriceFormatError"));
		}
		// 单位,孙海亮没使用标准计量单位，以后可能会改，届时将为连接属性
		String unit = (String) ((DataToken) hsData.get("FUnit")).data;
		info.setUnit(unit);
		// 目标成本金额
		try {
			Object o = ((DataToken) hsData.get("FCostAmount")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					if(value.compareTo( new BigDecimal("0"))==0){
						//不能倒入0数据
						throw new TaskExternalException(getResource(ctx, "Import_CostAccount_AmountIsZero"));
					}else{
						info.setCostAmount(value);
					}
				}
			} else {
				// 必录项,不能为空
				throw new TaskExternalException(getResource(ctx, "Import_CostAmount_Null"));
			}
		} catch (NumberFormatException nex) {
			// 格式错误，只能是数字
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_CostAmountFormatError"));
		}

		// 分录描述,暂不放开多语言
		// String description_l1 = (String) ((DataToken)
		// hsData.get("Fdescription_L1")).data;
		// if (description_l1 != null && description_l1.trim().length() != 0) {
		// info.setDescription(description_l1, new Locale("L1"));
		// }

		String description_l2 = (String) ((DataToken) hsData.get("FDescription")).data;
		if (description_l2 != null && description_l2.trim().length() != 0) {
			info.setDescription(description_l2, new Locale("L2"));
		}

		// String description_l3 = (String) ((DataToken)
		// hsData.get("Fdescription_L3")).data;
		// if (description_l3 != null && description_l3.trim().length() != 0) {
		// info.setDescription(description_l3, new Locale("L3"));
		// }
		
		//工作量\单价\目标成本金额控制逻辑
		//1		如果工作量、单价、目标成本都有数据，且满足 工作量×单价＝目标成本 的等式，则导入三者均导入。
		//2、   如果工作量、单价、目标成本都有数据，但不满足 工作量×单价＝目标成本 的等式，则导入目标成本，工作量、单价不导入。
		//3、   如果只有目标成本有数据，则导入目标成本。
		//4、   如果工作量、单价都有数据或有一个没有数据，但目标成本没有数据，系统给提示，目标成本不允许为空。该数据不能导入。
		//5、   如果工作量、单价有一个没有数据，目标成本有数据，则导入目标成本，工作量、单价不导入。

		if(info.getWorkload()!=null){
			if(info.getPrice()!=null){
				if(info.getCostAmount()!=null){
					if(round(info.getWorkload().multiply(info.getPrice()),2).compareTo(info.getCostAmount())==0){//相等
						//符合1
					}else{//不等
						info.setPrice(null);
						info.setWorkload(null);
						//符合2
					}
				}
			}else{
				//符合5
				info.setWorkload(null);
			}
		}else{
			if(info.getPrice()!=null){
				//符合5
				info.setPrice(null);
			}else{
				//符合3
			}
		}
	}

	public Hashtable exportTransmit(IRowSet rs, Context ctx) throws TaskExternalException {
		// 没有导出需求，暂时不作
		return null;
	}

	public FilterInfo getExportFilterForQuery(Context ctx) {// 导出用
		return null;
	}

	public String getExportQueryInfo(Context ctx) {// 导出用
		return "com.kingdee.eas.fdc.aimcost.app.AimCostQuery";
	}

	// //////////////////////////
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		String orgOrProId = getContextParameter("orgOrProId").toString();
		//add by sxhong 取掉到目标成本的导入
		ICoreBase iAimCost = getController(ctx);
		ICoreBase iDBAimCost = getController1(ctx);
		AimCostInfo info = (AimCostInfo) coreBaseInfo;// 目标成本
		info.setOrgOrProId(orgOrProId);
		//成本数据库的查询功能已经去掉，目前仅支持导入到目标成本　by　sxhong　2008-02-54 12:14:25
/*		DBAimCostInfo dbInfo;// 成本数据库数据
		// 寻找head,如没有,则新建
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", info.getOrgOrProId()));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.valueOf(true)));
		evi.setFilter(filter);
		DBAimCostCollection acc;		
			try {
				acc = ((IDBAimCost) iDBAimCost).getDBAimCostCollection(evi);
				if (acc != null && acc.size() != 0) {
					dbInfo = acc.get(0);
				} else {
					dbInfo = new DBAimCostInfo();
					dbInfo.setOrgOrProId(info.getOrgOrProId());
					dbInfo.setVersionNumber(info.getVersionNumber());
					dbInfo.setIsLastVersion(true);
				}
				// 分录
				DBCostEntryInfo dbEntryInfo = new DBCostEntryInfo();// 成本数据库数据
				dbEntryInfo.setHead(dbInfo);// 设置单头
				if (this.cei != null) {
					dbEntryInfo.setCostAccount(cei.getCostAccount());
					dbEntryInfo.setEntryName(cei.getEntryName());
					dbEntryInfo.setWorkload(cei.getWorkload());
					dbEntryInfo.setUnit(cei.getUnit());
					dbEntryInfo.setPrice(cei.getPrice());
					dbEntryInfo.setProduct(cei.getProduct());
					dbEntryInfo.setDescription(cei.getDescription());
					dbEntryInfo.setCostAmount(cei.getCostAmount());
//					dbEntryInfo.set
				}
				DBCostEntryCollection dbEntryCollection = dbInfo.getCostEntry();				
				dbEntryCollection.add(dbEntryInfo);
				dbInfo.setVersionNumber(info.getVersionNumber());
				if (dbInfo.getId() == null || !iDBAimCost.exists(new ObjectUuidPK(dbInfo.getId()))) {
					iDBAimCost.addnew(dbInfo); // insert
				} else {
//					iDBAimCost.update(new ObjectUuidPK(dbInfo.getId()), dbInfo); // update
					DBCostEntryFactory.getLocalInstance(ctx).addnew(dbEntryInfo);
				}
			} catch (BOSException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (EASBizException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}*/
			
		
		// 导入目标成本维护界面 不进行自动导入 by sxhong 20070830　
		try {
			if (coreBaseInfo.getId() == null || !iAimCost.exists(new ObjectUuidPK(coreBaseInfo.getId()))) {
				iAimCost.addnew(coreBaseInfo); // insert
			} else {
				iAimCost.update(new ObjectUuidPK(coreBaseInfo.getId()), coreBaseInfo); // update
			}
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex);
		}
	}

	// 获取编码类型的EntityViewInfo
	private EntityViewInfo getNFilter(String number) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}

	// 获取长编码类型的EntityViewInfo
	private EntityViewInfo getLNFilter(String longNumber, FullOrgUnitInfo fullOrgUnitInfo, CurProjectInfo curProjectInfo) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber.replace('.', '!'), CompareType.EQUALS));
		if (fullOrgUnitInfo != null) {
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnitInfo.getId().toString(), CompareType.EQUALS));
		}
		if (curProjectInfo != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString(), CompareType.EQUALS));
		}
		viewInfo.setFilter(filter);
		return viewInfo;
	}

	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	/**   
     *   提供精确的小数位四舍五入处理。   
     *   @param   v   需要四舍五入的数字   
     *   @param   scale   小数点后保留几位   
     *   @return   四舍五入后的结果   
     */   
   public static BigDecimal round(BigDecimal   v,int   scale){   
           if(scale<0){   
                   throw   new   IllegalArgumentException(   
                           "The   scale   must   be   a   positive   integer   or   zero");   
           }   
//           BigDecimal   b   =   new   BigDecimal(Double.toString(v));   
           BigDecimal   b   =   v;
           BigDecimal   one   =   new   BigDecimal("1");   
//           return   b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
           return   b.divide(one,scale,BigDecimal.ROUND_HALF_UP);  
   }
}
