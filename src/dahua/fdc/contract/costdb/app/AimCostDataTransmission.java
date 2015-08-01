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
 * ����:Ŀ��ɱ����뵼����
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
		// ����ڵ�
		String orgOrProId = getContextParameter("orgOrProId").toString();
		CurProjectInfo curProjectInfo = null;
		FullOrgUnitInfo fullOrgUnitInfo = null;
		if (getContextParameter("curProjectInfo") != null) {
			curProjectInfo = (CurProjectInfo) getContextParameter("curProjectInfo");
		} else if (getContextParameter("fullOrgUnitInfo") != null) {
			fullOrgUnitInfo = (FullOrgUnitInfo) getContextParameter("fullOrgUnitInfo");
		}
		
		// ��ǰ���°汾��
		String lastVerisonNumber = getContextParameter("lastVerisonNumber").toString();
		
		// ��ȡHEADID
		if (getContextParameter("lastFid") != null) {//�����ȡ��
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
		} else {//����û�л�ȡ��,��Ҫ����head					
			if(this.lastFid==null){//��û�л��浽
				if(this.isFirstRun){//�����һ�ν���ѭ��
					info.setOrgOrProId(orgOrProId);
					info.setVersionNumber(lastVerisonNumber);
					info.setIsLastVersion(true);
					isFirstRun = false;
				}else{
					//�Ѿ�ѭ����һ�Σ���Ҫ��ȡ�ϴβ������ݿ��ͷ
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
			}else{//������lastFid						
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

		// ��¼
		CostEntryInfo entryInfo = null;
		CostEntryCollection entryCollection = info.getCostEntry();
		entryInfo = new CostEntryInfo();
		// ��ͷ����
		entryInfo.setHead(info);
		if (curProjectInfo != null) {// ֻ�жԹ�����Ŀ�ڵ�����ʱ��Ҫ��Ʒ����
			// ������Ʒ(��Ʒ����)
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
									// ��Ʒ�����ѱ�����
									throw new TaskExternalException(getResource(ctx, "Import_ProductType_DisEnabled"));
								} else {
									if (curProjectInfo.getCurProjProductEntries() != null) {
										CurProjProductEntriesCollection cppec = curProjectInfo.getCurProjProductEntries();
										boolean isAccObj = true;//��¼���Ƿ�ѡ��"�Ƿ�������"
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
										if(!isAccObj){//������Ŀ�иò�Ʒ���Ͷ�Ӧ�ķ�¼Ϊ�Ǻ������,���ܵ���
											throw new TaskExternalException(getResource(ctx, "Import_ProductType_IsAccObj"));
										}else{
											if(!flag){//ָ��������Ŀ��Ʒ��¼����������ָ����Ʒ����,���ܵ���
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
//										if (!flag) {// ָ��������Ŀ��Ʒ��¼����������ָ����Ʒ����,���ܵ���
//											throw new TaskExternalException(getResource(ctx, "Import_CostAmount_ProjectHasNotProductType"));
//										}

									} else {
										// ָ��������Ŀû�ж����κβ�Ʒ����,���ܵ�������
									}

								}
							}
						} else {
							// ��Ʒ���Ͳ�����
							throw new TaskExternalException(getResource(ctx, "Import_ProductType_NotExist"));
						}
					}
				} else {
					// ��Ʒ����û��ָ��
//					throw new TaskExternalException(getResource(ctx, "Import_ProductType_Null"));
				}
			} catch (BOSException e) {
				throw new TaskExternalException(e.getMessage(), e.getCause());
			}
		}
		// �ɱ���Ŀ
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
								// �ɱ���Ŀ�ѱ�����
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_DisEnabled"));
							} else if (!costAccountInfo.isIsLeaf()) {
								// Ϊ�˲��ƻ����ܹ�ϵ,ֻ������ϸ��Ŀ�µ���
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_IsNotLeaf"));
							} else {
								entryInfo.setCostAccount(costAccountInfo);
							}
						}
					} else {
						// �ɱ���Ŀ������
						throw new TaskExternalException(getResource(ctx, "Import_CostAccount_NotExist"));
					}
				}
			} else {
				// �ɱ���Ŀû��ָ������
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
		// ��Ŀ���ƣ�û�ж�����,�Ժ��ﺣ���Ǳ����˶�����,���������
		String entryName = (String) ((DataToken) hsData.get("FEntryName")).data;
		info.setEntryName(entryName);
		// ������
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
			// ��ʽ����ֻ��������
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_WorkloadFormatError"));
		}
		// ����
		try {
			Object o = ((DataToken) hsData.get("FPrice")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					info.setPrice(value);
				}
			}
		} catch (NumberFormatException nex) {
			// ��ʽ����ֻ��������
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_PriceFormatError"));
		}
		// ��λ,�ﺣ��ûʹ�ñ�׼������λ���Ժ���ܻ�ģ���ʱ��Ϊ��������
		String unit = (String) ((DataToken) hsData.get("FUnit")).data;
		info.setUnit(unit);
		// Ŀ��ɱ����
		try {
			Object o = ((DataToken) hsData.get("FCostAmount")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					if(value.compareTo( new BigDecimal("0"))==0){
						//���ܵ���0����
						throw new TaskExternalException(getResource(ctx, "Import_CostAccount_AmountIsZero"));
					}else{
						info.setCostAmount(value);
					}
				}
			} else {
				// ��¼��,����Ϊ��
				throw new TaskExternalException(getResource(ctx, "Import_CostAmount_Null"));
			}
		} catch (NumberFormatException nex) {
			// ��ʽ����ֻ��������
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_CostAmountFormatError"));
		}

		// ��¼����,�ݲ��ſ�������
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
		
		//������\����\Ŀ��ɱ��������߼�
		//1		��������������ۡ�Ŀ��ɱ��������ݣ������� �����������ۣ�Ŀ��ɱ� �ĵ�ʽ���������߾����롣
		//2��   ��������������ۡ�Ŀ��ɱ��������ݣ��������� �����������ۣ�Ŀ��ɱ� �ĵ�ʽ������Ŀ��ɱ��������������۲����롣
		//3��   ���ֻ��Ŀ��ɱ������ݣ�����Ŀ��ɱ���
		//4��   ��������������۶������ݻ���һ��û�����ݣ���Ŀ��ɱ�û�����ݣ�ϵͳ����ʾ��Ŀ��ɱ�������Ϊ�ա������ݲ��ܵ��롣
		//5��   �����������������һ��û�����ݣ�Ŀ��ɱ������ݣ�����Ŀ��ɱ��������������۲����롣

		if(info.getWorkload()!=null){
			if(info.getPrice()!=null){
				if(info.getCostAmount()!=null){
					if(round(info.getWorkload().multiply(info.getPrice()),2).compareTo(info.getCostAmount())==0){//���
						//����1
					}else{//����
						info.setPrice(null);
						info.setWorkload(null);
						//����2
					}
				}
			}else{
				//����5
				info.setWorkload(null);
			}
		}else{
			if(info.getPrice()!=null){
				//����5
				info.setPrice(null);
			}else{
				//����3
			}
		}
	}

	public Hashtable exportTransmit(IRowSet rs, Context ctx) throws TaskExternalException {
		// û�е���������ʱ����
		return null;
	}

	public FilterInfo getExportFilterForQuery(Context ctx) {// ������
		return null;
	}

	public String getExportQueryInfo(Context ctx) {// ������
		return "com.kingdee.eas.fdc.aimcost.app.AimCostQuery";
	}

	// //////////////////////////
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		String orgOrProId = getContextParameter("orgOrProId").toString();
		//add by sxhong ȡ����Ŀ��ɱ��ĵ���
		ICoreBase iAimCost = getController(ctx);
		ICoreBase iDBAimCost = getController1(ctx);
		AimCostInfo info = (AimCostInfo) coreBaseInfo;// Ŀ��ɱ�
		info.setOrgOrProId(orgOrProId);
		//�ɱ����ݿ�Ĳ�ѯ�����Ѿ�ȥ����Ŀǰ��֧�ֵ��뵽Ŀ��ɱ���by��sxhong��2008-02-54 12:14:25
/*		DBAimCostInfo dbInfo;// �ɱ����ݿ�����
		// Ѱ��head,��û��,���½�
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
				// ��¼
				DBCostEntryInfo dbEntryInfo = new DBCostEntryInfo();// �ɱ����ݿ�����
				dbEntryInfo.setHead(dbInfo);// ���õ�ͷ
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
				// TODO �Զ����� catch ��
				e.printStackTrace();
			} catch (EASBizException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}*/
			
		
		// ����Ŀ��ɱ�ά������ �������Զ����� by sxhong 20070830��
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

	// ��ȡ�������͵�EntityViewInfo
	private EntityViewInfo getNFilter(String number) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}

	// ��ȡ���������͵�EntityViewInfo
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
     *   �ṩ��ȷ��С��λ�������봦��   
     *   @param   v   ��Ҫ�������������   
     *   @param   scale   С���������λ   
     *   @return   ���������Ľ��   
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
