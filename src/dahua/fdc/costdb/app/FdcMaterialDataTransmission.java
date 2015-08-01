package com.kingdee.eas.fdc.costdb.app;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.IMaterial;
import com.kingdee.eas.basedata.master.material.IMaterialCompanyInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialCompanyInfoCollection;
import com.kingdee.eas.basedata.master.material.MaterialCompanyInfoFactory;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.costdb.FdcMaterialCollection;
import com.kingdee.eas.fdc.costdb.FdcMaterialFactory;
import com.kingdee.eas.fdc.costdb.FdcMaterialInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:���ز������豸����
 * 
 * @author jackwang date:2006-11-22
 *         <p>
 * @version EAS5.2
 */
public class FdcMaterialDataTransmission extends AbstractDataTransmission {
	private static String resource = "com.kingdee.eas.fdc.costdb.CostDBResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return FdcMaterialFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	// Import data from file.
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		// ����ڵ�
		CurProjectInfo node = (CurProjectInfo) getContextParameter("projectNode");
		CurProjectInfo projectNode = null;
		try {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("fullOrgUnit.*"));
			projectNode = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(node.getId().toString()),selector);//new ObjectUuidPK(node.getId().toString())
		} catch (EASBizException e1) {	
			e1.printStackTrace();
		} catch (BOSException e1) {			
			e1.printStackTrace();
		}
		String cuId = projectNode.getFullOrgUnit().getCU().getId().toString();
		FdcMaterialInfo info = new FdcMaterialInfo();
		// ���ϣ����ݱ����ȡ
		MaterialInfo materialInfo = null;
		try {
			Object data = ((DataToken) hsData.get("FMaterialNumber")).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {

					IMaterial iMaterial = MaterialFactory.getLocalInstance(ctx);
					EntityViewInfo initialevi = getNFilter(str);
					//					
					FilterInfo filter1 = iMaterial.getDatabaseDFilter(new ObjectUuidPK(cuId), "id", "adminCU.id");
					initialevi.getFilter().mergeFilter(filter1, "AND");
					MaterialCollection collection = iMaterial.getMaterialCollection(initialevi);
					if (collection != null && collection.size() > 0) {
						materialInfo = collection.get(0);
						if (materialInfo != null) {
							EntityViewInfo evi = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("material.id", materialInfo.getId().toString()));
							filter.getFilterItems().add(new FilterItemInfo("project.id", projectNode.getId().toString()));
							evi.setFilter(filter);
							FdcMaterialCollection fmc = FdcMaterialFactory.getLocalInstance(ctx).getFdcMaterialCollection(evi);
							if (fmc.size() != 0) {
								// ϵͳ���Ѿ����ڸ����϶�Ӧ��¼,���ɵ���
								throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_MaterialHasExist"));
							} else {
								EntityViewInfo evi1 = new EntityViewInfo();
								FilterInfo filterInfo = iMaterial.getDatabaseDFilter(new ObjectUuidPK(cuId), "id", "adminCU.id");
								//R101216-109�����豸���뱨��
								if(FDCHelper.isFDCDebug()){
									System.out.print("hpw: "+filterInfo);
								}
								FilterInfo tempFilter = new FilterInfo();
								tempFilter.getFilterItems().add(new FilterItemInfo("status", new Integer(1), CompareType.EQUALS));
								filterInfo.mergeFilter(tempFilter, "AND");
								if(FDCHelper.isFDCDebug()){
									System.out.print("hpw: "+filterInfo);
								}
//								if (filterInfo.getFilterItems().size() == 1) {
//									filterInfo.setMaskString("#0 and #1");
//								} else {
//									filterInfo.setMaskString("(#0 or #1) and #2");
//								}
//								filterInfo.getFilterItems().add(new FilterItemInfo("status", new Integer(1), CompareType.EQUALS));
								evi1.setFilter(filterInfo);
								MaterialCollection mc = iMaterial.getMaterialCollection(evi1);
								// MaterialCollection mc =
								// this.getMaterialCollectionByComID(ctx,projectNode.getFullOrgUnit().getId().toString());
								if (mc.contains(materialInfo.getId())) {// ��ǰ������֯�¿ɼ��ù�Ӧ��
									info.setMaterial(materialInfo);
								} else {// ���ɼ�,��ʾ ָ��������Ŀ�깤����Ŀ���ڵĲ�����֯�²���ά��
									throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_MaterialIsNotAssign"));
								}
							}
						} else {
							// ָ�����ϲ�����
							throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_MaterialIsNotExist"));
						}
					} else {
						// ָ�����ϲ�����
						throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_MaterialIsNotExist"));
					}
				}
			} else {
				// û��ָ������
				throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_MaterialIsNull"));
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		// ������Ŀ�����ݳ������ȡ(��ǰ�ɱ������µ�)��ע������Ŀ������ȫ��Ψһ
		// CurProjectInfo curProjectInfo = null;
		// try {
		// Object data = ((DataToken) hsData.get("FProjectNumber")).data;
		// if (data != null && data.toString().length() > 0) {
		// String str = data.toString();
		// if (str != null && str.length() > 0) {
		// ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		// EntityViewInfo evi = getLNFilter(str);//
		// evi.getFilter().getFilterItems().add(new
		// FilterItemInfo("fullOrgUnit.id",
		// ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		// CurProjectCollection collection =
		// iCurProject.getCurProjectCollection(evi);
		// if (collection != null && collection.size() > 0) {
		// curProjectInfo = collection.get(0);
		// if (curProjectInfo != null) {
		// if (!curProjectInfo.isIsEnabled()) {
		// // ������Ŀ�ѱ�����
		// throw new TaskExternalException(getResource(ctx,
		// "Import_Fdcmaterial_ProjectIsDisEnabled"));
		// } else {
		// info.setProject(curProjectInfo);
		// }
		// }
		// } else {
		// // ������Ŀ������
		// throw new TaskExternalException(getResource(ctx,
		// "Import_Fdcmaterial_ProjectIsNotExist"));
		// }
		// }
		// } else {
		// // û��ָ��������Ŀ
		// throw new TaskExternalException(getResource(ctx,
		// "Import_Fdcmaterial_ProjectIsNull"));
		// }
		//
		// } catch (BOSException e) {
		// throw new TaskExternalException(e.getMessage(), e.getCause());
		// }
		String number = projectNode.getLongNumber();
		number = number.replace('!', '.');
		Object data1 = ((DataToken) hsData.get("FProjectNumber")).data;
		if (data1 != null && data1.toString().length() > 0) {
			String str = data1.toString();
			if (str != null && str.length() > 0) {
				if (!str.equals(number)) {
					throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_ProjectAssign"));
				} else {
					info.setProject(projectNode);
				}
			}
		} else {
			// û��ָ��������Ŀ
			throw new TaskExternalException(getResource(ctx, "Import_Fdcmaterial_ProjectIsNull"));
		}
		// ��Ӧ��,���ݳ������ȡ
		SupplierInfo supplierInfo = null;
		try {
			Object data = ((DataToken) hsData.get("FSupplierNumber")).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {
					ISupplier ISupplier = SupplierFactory.getLocalInstance(ctx);
					EntityViewInfo initialevi = getNFilter(str);
					FilterInfo filter1 = SupplierFactory.getLocalInstance(ctx).getDatabaseDFilter(new ObjectUuidPK(cuId), "id", "adminCU.id");
					initialevi.getFilter().mergeFilter(filter1, "AND");
					// initialevi.getFilter().getFilterItems().add(new
					// FilterItemInfo("CU.id", cuId));
					SupplierCollection collection = ISupplier.getSupplierCollection(initialevi);
					if (collection != null && collection.size() > 0) {
						supplierInfo = collection.get(0);
						if (supplierInfo == null) {
							// ��Ӧ�̲�����
							throw new TaskExternalException(getResource(ctx, "Import_Fdcmaterial_SupplierIsNotExist"));
						} else {
							try {
								EntityViewInfo evi = new EntityViewInfo();
								FilterInfo filter = SupplierFactory.getLocalInstance(ctx).getDatabaseDFilter(new ObjectUuidPK(cuId), "id", "adminCU.id");
								//R101216-109�����豸���뱨��
//								if (filter.getFilterItems().size() == 1) {
//									filter.setMaskString("(#0 and #1");
//								} else {
//									filter.setMaskString("(#0 or #1) and #2");
//								}
								if(FDCHelper.isFDCDebug()){
									System.out.print("hpw: "+filter);
								}
								FilterInfo tempFilter = new FilterInfo();
								tempFilter.getFilterItems().add(new FilterItemInfo("usedStatus", new Integer(1), CompareType.EQUALS));
								filter.mergeFilter(tempFilter, "AND");
								if(FDCHelper.isFDCDebug()){
									System.out.print("hpw: "+filter);
								}
								
								evi.setFilter(filter);
								SupplierCollection sc = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(evi);// .getSupplierCollectionByInter(ContextUtil.getCurrentFIUnit(ctx).getId().toString());
								// SupplierCollection sc =
								// SupplierFactory.getLocalInstance(ctx).getSupplierCollectionByInter(projectNode.getFullOrgUnit().getId().toString());
								if (sc.contains(supplierInfo.getId())) {// ��ǰ������֯�¿ɼ��ù�Ӧ��
									info.setSupplier(supplierInfo);
								} else {// ���ɼ�,��,��ʾ ָ����Ӧ����Ŀ�깤����Ŀ���ڵĲ�����֯�²���ά��
									throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_SupplierIsNotAssign"));
								}
							} catch (EASBizException e) { // ��ȡ�ɼ�������,����������ʾ
								// e.printStackTrace();
								throw new TaskExternalException(getResource(ctx, "Import_Fdcmaterial_SupplierIsNotExist"));
							}
						}
					} else {
						// ��Ӧ�̲�����
						throw new TaskExternalException(getResource(ctx, "Import_Fdcmaterial_SupplierIsNotExist"));
					}
				}
			} else {
				// û��ָ����Ӧ��
				throw new TaskExternalException(getResource(ctx, "Import_Fdcmaterial_SupplierIsNull"));
			}

		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (EASBizException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}

		// �ͺ�FModel,�ݲ�֧�ֶ�����
		// String FModel_l1 = (String) ((DataToken)
		// hsData.get("Fname_L1")).data;
		// if (FModel_l1 != null && FModel_l1.trim().length() != 0) {
		// info.setModel(FModel_l1, new Locale("L1"));
		// }
		String FModel_l2 = (String) ((DataToken) hsData.get("FModel")).data;
		if (FModel_l2 != null && FModel_l2.trim().length() != 0) {
			info.setModel(FModel_l2);
		}
		// String FModel_l3 = (String) ((DataToken)
		// hsData.get("Fname_L3")).data;
		// if (FModel_l3 != null && FModel_l3.trim().length() != 0) {
		// info.setModel(FModel_l3, new Locale("L3"));
		// }

		// ��ϸ�������FDetailType���ݲ�֧�ֶ�����
		// String FDetailType_l1 = (String) ((DataToken)
		// hsData.get("Fname_L1")).data;
		// if (FDetailType_l1 != null && FDetailType_l1.trim().length() != 0) {
		// info.setDetailType(FDetailType_l1, new Locale("L1"));
		// }
		String FDetailType_l2 = (String) ((DataToken) hsData.get("FDetailType")).data;
		if (FDetailType_l2 != null && FDetailType_l2.trim().length() != 0) {
			info.setDetailType(FDetailType_l2);
		}
		// String FDetailType_l3 = (String) ((DataToken)
		// hsData.get("Fname_L3")).data;
		// if (FDetailType_l3 != null && FDetailType_l3.trim().length() != 0) {
		// info.setDetailType(FDetailType_l3, new Locale("L3"));
		// }

		// ����FProductArea,�ݲ�֧�ֶ�����
		// String FProductArea_l1 = (String) ((DataToken)
		// hsData.get("Fname_L1")).data;
		// if (FProductArea_l1 != null && FProductArea_l1.trim().length() != 0)
		// {
		// info.setProductArea(FProductArea_l1, new Locale("L1"));
		// }
		String FProductArea_l2 = (String) ((DataToken) hsData.get("FProductArea")).data;
		if (FProductArea_l2 != null && FProductArea_l2.trim().length() != 0) {
			info.setProductArea(FProductArea_l2);
		}
		// String FProductArea_l3 = (String) ((DataToken)
		// hsData.get("Fname_L3")).data;
		// if (FProductArea_l3 != null && FProductArea_l3.trim().length() != 0)
		// {
		// info.setProductArea(FProductArea_l3, new Locale("L3"));
		// }

		// Ʒ��FBrand,�ݲ�֧�ֶ�����
		// String FBrand_l1 = (String) ((DataToken)
		// hsData.get("Fname_L1")).data;
		// if (FBrand_l1 != null && FBrand_l1.trim().length() != 0) {
		// info.setBrand(FBrand_l1, new Locale("L1"));
		// }
		String FBrand_l2 = (String) ((DataToken) hsData.get("FBrand")).data;
		if (FBrand_l2 != null && FBrand_l2.trim().length() != 0) {
			info.setBrand(FBrand_l2);
		}
		// String FBrand_l3 = (String) ((DataToken)
		// hsData.get("Fname_L3")).data;
		// if (FBrand_l3 != null && FBrand_l3.trim().length() != 0) {
		// info.setBrand(FBrand_l3, new Locale("L3"));
		// }

		// ���ϵ���,����Ϊ�գ�ע��ΪBigDecimalУ��
		BigDecimal value;
		try {
			Object o = ((DataToken) hsData.get("FMaterialPrice")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					info.setMaterialPrice(value);
				}
			}
		} catch (NumberFormatException nex) {
			// ���ϵ��۸�ʽ����ֻ��������
			throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_MaterialPriceFormatError"));
		}

		// ʩ������FProjectPrice������Ϊ�գ�ע��BigDecimalУ��
		try {
			Object o = ((DataToken) hsData.get("FProjectPrice")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					info.setProjectPrice(value);
				}
			}
		} catch (NumberFormatException nex) {
			// ʩ�����۸�ʽ����ֻ��������
			throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_ProjectlPriceFormatError"));
		}
		// �ۺϵ���FIntegratePrice������Ϊ�գ�ע��BigDecimalУ��
		try {
			Object o = ((DataToken) hsData.get("FIntegratePrice")).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					info.setIntegratePrice(value);
				}
			}
		} catch (NumberFormatException nex) {
			// �ۺϵ��۸�ʽ����ֻ��������
			throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_IntegratePriceFormatError"));
		}

		// ����ʱ��FDecideData,����Ϊ��,ע��ʱ���ʽУ��
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Object o = ((DataToken) hsData.get("FDecideDate")).data;
		if (o != null && o.toString().length() > 0) {
			try {
				o = df.parse(o.toString());
			} catch (ParseException pex) {
				throw new TaskExternalException(getResource(ctx, "Import_FdcMaterial_DecideDataFormatError"));
			}
			if (o != null && o instanceof Date) {
				Date d = (Date) o;
				if (d != null) {
					info.setDecideDate(d);
				}
			}
		}
		// ��ע,����Ϊ��,Ĭ��֧�ֶ�����
		// String description_l1 = (String) ((DataToken)
		// hsData.get("FDescription_L1")).data;
		// if (description_l1 != null && description_l1.trim().length() != 0) {
		// info.setDescription(description_l1, new Locale("L1"));
		// }

		String description_l2 = (String) ((DataToken) hsData.get("FDescription")).data;
		if (description_l2 != null && description_l2.trim().length() != 0) {
			info.setDescription(description_l2, new Locale("L2"));
		}

		// String description_l3 = (String) ((DataToken)
		// hsData.get("FDescription_L3")).data;
		// if (description_l3 != null && description_l3.trim().length() != 0) {
		// info.setDescription(description_l3, new Locale("L3"));
		// }
		return info;
	}

	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		ICoreBase iFdcMaterial = getController(ctx);
		try {
			if (coreBaseInfo.getId() == null || !iFdcMaterial.exists(new ObjectUuidPK(coreBaseInfo.getId()))) {
				iFdcMaterial.addnew(coreBaseInfo); // insert
			} else {
				iFdcMaterial.update(new ObjectUuidPK(coreBaseInfo.getId()), coreBaseInfo); // update
			}
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex);
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
		return "com.kingdee.eas.fdc.costdb.app.FdcMaterialQuery";
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
	private EntityViewInfo getLNFilter(String longNumber) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber.replace('.', '!'), CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}

	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}

	// �õ�ά����ָ��������֯�������Ե����Ͻ��
	private MaterialCollection getMaterialCollectionByComID(Context ctx, String comId) throws BOSException {
		MaterialCollection colletionByComOrg = new MaterialCollection();
		MaterialCompanyInfoCollection companyInfoCollection = null;

		IMaterialCompanyInfo iMaterialCompanyInfo = MaterialCompanyInfoFactory.getLocalInstance(ctx);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo tmpFilterInfo = new FilterInfo();
		tmpFilterInfo.getFilterItems().add(new FilterItemInfo("company", comId, CompareType.EQUALS));
		view.setFilter(tmpFilterInfo);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("customer.*"));
		companyInfoCollection = iMaterialCompanyInfo.getMaterialCompanyInfoCollection(view);
		for (int i = 0; i < companyInfoCollection.size(); i++) {
			colletionByComOrg.add(companyInfoCollection.get(i).getMateial());
		}
		return colletionByComOrg;
	}

}
