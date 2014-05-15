package com.kingdee.eas.port.markesupplier.subill.app;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.hr.contract.ContractException;
import com.kingdee.eas.port.markesupplier.subase.SupplierState;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class MarketSupplierStockControllerBean extends AbstractMarketSupplierStockControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.markesupplier.subill.app.MarketSupplierStockControllerBean");
    
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
    	super._audit(ctx, model);
    	MarketSupplierStockInfo info = (MarketSupplierStockInfo)model;
		info.setState(SupplierState.audit);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		try 
		{
			info.setAuditDate(SysUtil.getAppServerTime(ctx));
			MarketSupplierStockFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		}
    }
    
    protected void _unAudit(Context ctx, IObjectValue model)throws BOSException {
    	super._unAudit(ctx, model);
    	MarketSupplierStockInfo info = (MarketSupplierStockInfo)model;
		info.setState(SupplierState.submit);
		info.setAuditor(null);
		try 
		{
			info.setAuditDate(null);
			MarketSupplierStockFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
		} 
		catch (EASBizException e) 
		{
			e.printStackTrace();
		}
    }
    
	protected IObjectPK _save(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		MarketSupplierStockInfo info = (MarketSupplierStockInfo)model;
		info.setState(SupplierState.Save);
		checkBill(ctx,model);
		return super._save(ctx, model);
	}
    
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		MarketSupplierStockInfo info = (MarketSupplierStockInfo)model;
		info.setState(SupplierState.submit);
		checkBill(ctx,model);
		return super._submit(ctx, model);
	}
    
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MarketSupplierStockInfo info = ((MarketSupplierStockInfo) model);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
		if (info.getId() != null) 
		{
			filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(),CompareType.NOTEQUALS));
		}
		
		if (_exists(ctx, filter)) 
		{
			throw new ContractException(new NumericExceptionSubItem("100", "编码不能重复"));
		}
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplierName", info.getSupplierName()));
		
		if(info.getInviteType()!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id", info.getInviteType().getId().toString()));
		}
		if(info.getPurchaseOrgUnit()!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id", info.getPurchaseOrgUnit().getId().toString()));
		}
		if (info.getId() != null) 
		{
			filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(),CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) 
		{
			throw new ContractException(new NumericExceptionSubItem("100", "供应商名称+采购类别+所属组织不能重复！"));
		}
	}
	
	protected void _addToSysSupplier(Context ctx, IObjectValue objectValue)throws BOSException, EASBizException {
		super._addToSysSupplier(ctx, objectValue);
		MarketSupplierStockInfo info = (MarketSupplierStockInfo)objectValue;
		if(info==null)
		{
			return;
		}
		else
		{
			SelectorItemCollection supplier = new SelectorItemCollection();
			supplier.add("*");
			supplier.add("sysSupplier.*");
			supplier.add("purchaseOrgUnit.*");
			info=MarketSupplierStockFactory.getLocalInstance(ctx).getMarketSupplierStockInfo(new ObjectUuidPK(info.getId()),supplier);
		}
		SupplierInfo supplier = info.getSysSupplier();
    	
    	if(supplier == null||supplier.getId()==null)
    	{
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		view.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("name", info.getSupplierName()));
    		
    		SupplierCollection col=SupplierFactory.getLocalInstance(ctx).getSupplierCollection(view);
    		if(col.size()>0)
    		{
    			supplier=col.get(0);
    		}
    		else
    		{
    			supplier = new SupplierInfo();
        		supplier.setId(BOSUuid.create(supplier.getBosType()));
        		setSysSupplierValue(ctx, info, supplier);
    			
        		if(supplier.getId()!=null)
        		{
        			EntityViewInfo view1 = new EntityViewInfo();
        			FilterInfo filter1 = new FilterInfo();
        			filter1.getFilterItems().add(new FilterItemInfo("supplier",supplier.getId()));
        			SelectorItemCollection coll = new SelectorItemCollection();
        			coll.add("companyOrgUnit.id");
        			coll.add("id");
        			coll.add("supplier");
        			coll.add("CU.*");
        			view1.setFilter(filter1);
        			view1.setSelector(coll);
        			SupplierCompanyInfoCollection suppliercoll = SupplierCompanyInfoFactory.getLocalInstance(ctx).getSupplierCompanyInfoCollection(view1);
        			SupplierCompanyInfoInfo com = null;
        			if(suppliercoll.size()<1)
        			{
        				if(com==null)
        				{
        					com = new SupplierCompanyInfoInfo();
        					com.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
        					com.setSupplier(supplier);
        					com.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
        					SupplierCompanyInfoFactory.getLocalInstance(ctx).addnew(com);
        				}
        			}
        			else
        			{
        				boolean flag =true;
        				for(int i =0;i<suppliercoll.size();i++)
        				{
        					SupplierCompanyInfoInfo info1 = suppliercoll.get(i);
        					if(info1.getCompanyOrgUnit()!=null)
        					{
        						if(!(ContextUtil.getCurrentFIUnit(ctx).getId().equals(info1.getCompanyOrgUnit().getId())))
        						{
        							continue;
        						}
        						else
        						{
        							flag=false;
        							break;
        						}
        					}
        				}
        				if(flag)
        				{
        					SupplierCompanyInfoInfo info1 = suppliercoll.get(0);
        					info1.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
        					SelectorItemCollection selcol = new SelectorItemCollection();
        					selcol.add("companyOrgUnit.id");
        					CustomerCompanyInfoFactory.getLocalInstance(ctx).updatePartial(info1, selcol);
        				}
        			}
        		}
        		CtrlUnitInfo cu = new CtrlUnitInfo();
        		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
        		
        		CSSPGroupInfo groupInfo = null;
        		
        		view = new EntityViewInfo();
        		filter = new FilterInfo();
        		view.setFilter(filter);
        		filter.getFilterItems().add(new FilterItemInfo("name", "南京港供应商"));
        		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
        		
        		CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
        		if(sheGroupCol.isEmpty())
        		{
        			CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
        			strd.setId(BOSUuid.create(strd.getBOSType()));
        			strd.setNumber("NJGsupplierGstrd");
        			strd.setName("南京港供应商分类标准");
        			strd.setType(2);
        			strd.setIsBasic(StandardTypeEnum.defaultStandard);
        			strd.setCU(cu);
        			
        			CSSPGroupStandardFactory.getLocalInstance(ctx).addnew(strd);
        			
        			CSSPGroupInfo gr = new CSSPGroupInfo();
        			gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
        			gr.setId(BOSUuid.create(gr.getBOSType()));
        			gr.setNumber("NJGsupplierG");
        			gr.setName("南京港供应商");
        			gr.setCU(cu);
        			gr.setGroupStandard(strd);
        			
        			CSSPGroupFactory.getLocalInstance(ctx).addnew(gr);
        			
        			groupInfo = gr;
        		}
        		else
        		{
        			groupInfo = sheGroupCol.get(0);
        		}
        		supplier.setBrowseGroup(groupInfo);
        		
        		SupplierGroupDetailInfo Gdinfo = new SupplierGroupDetailInfo();
        		Gdinfo.setSupplierGroup(groupInfo);
        		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
        		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
        		supplier.getSupplierGroupDetails().add(Gdinfo);
        		
        		view = new EntityViewInfo();
        		filter = new FilterInfo();
        		view.setFilter(filter);
        		filter.getFilterItems().add(new FilterItemInfo("description", "%南京港%",CompareType.LIKE));
        		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
        		
        		sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
        		if(sheGroupCol.size()>0)
        		{
        			groupInfo = sheGroupCol.get(0);
        			
        			Gdinfo = new SupplierGroupDetailInfo();
            		Gdinfo.setSupplierGroup(groupInfo);
            		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
            		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
            		supplier.getSupplierGroupDetails().add(Gdinfo);
        		}
    			SupplierFactory.getLocalInstance(ctx).addnew(supplier);
    			
    			Set cuIds = getSupplierMgeCu(ctx,supplier.getAdminCU().getId().toString());
    	    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); )
    	    	{
    	    		String cuId = (String) itor.next();
    	    		SupplierFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(supplier.getAdminCU().getId()), new ObjectUuidPK(supplier.getId()), new ObjectUuidPK(cuId));
    	    	}
    		}
	    	info.setSysSupplier(supplier);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sysSupplier");
			this.updatePartial(ctx, info, sels);
    	}
    	else
    	{
    		updateSysSupplier(ctx, info, supplier);
    	}
	}
	
	private Set getSupplierMgeCu(Context ctx,String cuId) throws BOSException {
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE));
		view.setFilter(filter);
		view.setSelector(sel);
		CtrlUnitCollection orgColl = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(view);
		for (int i = 0; i < orgColl.size(); i++) 
		{
			if(cuId.equals(orgColl.get(i).getId().toString()))
			{
				continue;
			}
			set.add(orgColl.get(i).getId().toString());
		}
		return set;
	}
	
	private void updateSysSupplier(Context ctx, MarketSupplierStockInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		setSysSupplierValue(ctx, info, supplier);
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("name");
		selCol.add("address");
		selCol.add("province");
		selCol.add("city");
		selCol.add("description");
		selCol.add("adminCU");
		selCol.add("CU");
		selCol.add("browseGroup");
		selCol.add("customerGroupDetails");
		selCol.add("bizRegisterNo");
		selCol.add("artificialPerson");
		selCol.add("busiLicence");
		selCol.add("taxRegisterNo");
		selCol.add("version");
		selCol.add("usedStatus");
		selCol.add("effectedStatus");
		selCol.add("simpleName");
		selCol.add("isInternalCompany");
		SupplierFactory.getLocalInstance(ctx).updatePartial(supplier, selCol);
	}
    
	private void setSysSupplierValue(Context ctx, MarketSupplierStockInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		supplier.setNumber("Sys-" + info.getNumber());
		supplier.setName(info.getSupplierName());
		supplier.setAddress(info.getAddress());
		supplier.setDescription(info.getDescription());
		supplier.setArtificialPerson(info.getEnterpriseMaster());
		supplier.setBizRegisterNo(info.getBizRegisterNo());
		supplier.setBusiLicence(info.getBusinessNum());
		supplier.setTaxRegisterNo(info.getTaxRegisterNo());
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		supplier.setCU(cu);
		supplier.setAdminCU(cu);
		
		supplier.setVersion(1);
		supplier.setUsedStatus(UsedStatusEnum.APPROVED);
		supplier.setEffectedStatus(EffectedStatusEnum.EFFECTED);
		supplier.setIsInternalCompany(false);
		if(info.getPurchaseOrgUnit()!=null)
		{
			supplier.setSimpleName(info.getPurchaseOrgUnit().getName());
		}
	}
}