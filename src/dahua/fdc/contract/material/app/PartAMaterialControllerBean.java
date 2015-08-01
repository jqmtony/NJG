package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.InviteProjectEntryCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialException;
import com.kingdee.eas.fdc.material.PartAMaterialCollection;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class PartAMaterialControllerBean extends AbstractPartAMaterialControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.PartAMaterialControllerBean");
    
    /**
     * 审核后更新单据的是否最新版本属性
     * 描述：
     * @param ctx
     * @param billId
     * @throws BOSException
     * @throws EASBizException
     * 创建时间：2010-9-14
     * 创建人：zhiqiao_yang
     */
    private void updateIsLatestVer(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		IObjectPK pk = new ObjectUuidPK(billId);
		if (PartAMaterialFactory.getLocalInstance(ctx).exists(pk)) {
			SelectorItemCollection items = new SelectorItemCollection();
			items.add("id");
			items.add("contractBill.id");
			PartAMaterialInfo billInfo = PartAMaterialFactory.getLocalInstance(ctx).getPartAMaterialInfo(pk, items);
			EntityViewInfo view = new EntityViewInfo();
			view.setSelector(new SelectorItemCollection());
			view.getSelector().add("id");
			view.getSelector().add("state");
			view.getSelector().add("version");
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString(), CompareType.EQUALS));
			PartAMaterialCollection coll = PartAMaterialFactory.getLocalInstance(ctx).getPartAMaterialCollection(view);
			PartAMaterialInfo maxVersion = null;
			for (int i = 0; i < coll.size(); ++i) {
				coll.get(i).setIsLatestVer(false);
				if (FDCBillStateEnum.AUDITTED.equals(coll.get(i).getState())) {
					if (maxVersion == null) {
						maxVersion = coll.get(i);
					} else if (coll.get(i).getVersion() > maxVersion.getVersion()) {
						maxVersion = coll.get(i);
					}
				}
			}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx, "update T_PAM_PartAMaterial set FIsLatestVer=? where FContractBillID=?");
			builder.addParam(Boolean.FALSE);
			builder.addParam(billInfo.getContractBill().getId().toString());
			builder.executeUpdate();
			if (maxVersion != null) {
				builder.clear();
				builder.appendSql("update T_PAM_PartAMaterial set FIsLatestVer=? where FID=?");
				builder.addParam(Boolean.TRUE);
				builder.addParam(maxVersion.getId().toString());
				builder.executeUpdate();
			}
		}
	}
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {		
			super._audit(ctx, billId);
			updateIsLatestVer(ctx, billId);

	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
		EASBizException {
		PartAMaterialInfo billInfo = PartAMaterialFactory.getLocalInstance(ctx).getPartAMaterialInfo(new ObjectUuidPK(billId));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("version", String.valueOf(billInfo.getVersion()), CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString(), CompareType.EQUALS));
		
		if (_exists(ctx, filter)){
			throw new MaterialException(MaterialException.EXIST_RECET_VERSION);
		}
		super._unAudit(ctx, billId);
		updateIsLatestVer(ctx, billId);
	}
	
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);

		checkNumberDup(ctx, FDCBillInfo);

		checkNameDup(ctx, FDCBillInfo);
	}
	/**
	 * 重写检查编号是否重复的方法，如果是修订，不需要检查编号是否重复
	 * @author owen_wen 2010-8-31
	 */
	protected void checkNumberDup(Context ctx, FDCBillInfo Info) throws BOSException, EASBizException {
		PartAMaterialInfo billInfo =(PartAMaterialInfo) Info;
		
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
		
		// 非同一个合同下的材料明细才做编码重复性校验
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString(), CompareType.NOTEQUALS));
		
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
		return super._submit(ctx, model);
	}
	
	protected IObjectPK _addnew(Context ctx , IObjectValue model) throws
    BOSException , EASBizException
	{
    	IObjectPK test = super._addnew(ctx,model);
   
	    return test;
	}
	//单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		Map initMap = super._fetchInitData(ctx,paramMap);		
		//合同单据
		String contractBillId = (String)paramMap.get("contractBillId");
		if(contractBillId!=null && initMap.get(FDCConstants.FDC_INIT_CONTRACT)==null){
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("exRate");
			selectors.add("orgUnit.id");
			selectors.add("currency.number");
			selectors.add("currency.name");
			selectors.add("CU.id");
			selectors.add("grtRate");
			selectors.add("curProject.id");
			selectors.add("isCoseSplit");
			selectors.add("isPartAMaterialCon");
			selectors.add("amount");
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();
			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
				getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selectors);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
			}
		}
		if(contractBillId!=null && initMap.get(FDCConstants.FDC_INIT_CONTRACT)==null){
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("*");
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("orgUnit.id");
			selectors.add("contractBill.id");
			selectors.add("contractBill.name");
			selectors.add("contractBill.number");
			selectors.add("contractBill.amount");
			BOSObjectType  partAinfoObjectType=new PartAMaterialInfo().getBOSType();
			
			if(BOSUuid.read(contractBillId).getType().equals(partAinfoObjectType)){
				ContractBillInfo contractBill =PartAMaterialFactory.getLocalInstance(ctx).getPartAMaterialInfo(new ObjectUuidPK(contractBillId), selectors).getContractBill();;
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
			}
		}
		
		return initMap;
	}
	//空实现，去掉对相同名称的校验
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo){
		
	}

	protected Map _fetchBackData(Context ctx, Map paramMap) throws BOSException {
		Map initMap = new HashMap();
		Set contractSetId = new HashSet();	//将合同id保存在set中
		Set materialSetId = new HashSet();	//将物料id保存在set中
//		Set creatTimeSet = new HashSet();	//将时间保存在set中
		Set set = paramMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Map key = (Map)paramMap.get((String)it.next());
			String contractId = (String)key.get("contractbillId");
			String materialId = (String)key.get("materialId");
//			String createTime = (String)key.get("createTime");
			contractSetId.add(contractId);
			materialSetId.add(materialId);
//			creatTimeSet.add(createTime);
		}

		FilterInfo filterInfo = new FilterInfo();
		EntityViewInfo evInfo = new EntityViewInfo();
		if(contractSetId.size()>0 && materialSetId.size()>0){
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.mainContractBill.id", contractSetId, CompareType.INCLUDE));
			filterInfo.getFilterItems().add(new FilterItemInfo("material.id", materialSetId, CompareType.INCLUDE));
//			filterInfo.getFilterItems().add(new FilterItemInfo("parent.createTime", creatTimeSet, CompareType.INCLUDE));	
		}
		
		evInfo.setFilter(filterInfo);
		evInfo.getSelector().add("price");
		evInfo.getSelector().add(("parent.mainContractBill.id"));
		evInfo.getSelector().add("material.id");
		evInfo.getSelector().add("parent.createTime");

		
		MaterialConfirmBillEntryCollection c = MaterialConfirmBillEntryFactory.getLocalInstance(ctx).getMaterialConfirmBillEntryCollection(evInfo);
		Iterator itc = c.iterator();
		Map timeMap = new HashMap();	//保存创建时间
		while(itc.hasNext()){
			MaterialConfirmBillEntryInfo info = (MaterialConfirmBillEntryInfo)itc.next();
			String timeCompare = info.getParent().getCreateTime().toString();
			String contractId = info.getParent().getMainContractBill().getId().toString();
			String materialId = info.getMaterial().getId().toString();
//			String createTime = (String)key.get("createTime");
			String Id = contractId + materialId;
			Map map=new HashMap();
			BigDecimal rPrice=FDCHelper.ZERO;
			map.put("contractbillId", contractId);
			map.put("materialId", materialId);
//			if(info.getBigDecimal("price")!=null){
//				rPrice = info.getBigDecimal("price");
			if(info.getPrice()!=null){
				rPrice = info.getPrice();
			}
			map.put("price", rPrice);
    		//在相同合同与物料单中，取时间最大的确认单的单价
//    		if(Id.equals(initMap.get("Id")) && (time.compareTo(timeCompare)<0)){
			if(initMap.containsKey(Id)){
				if(timeMap.get(Id).toString().compareTo(timeCompare)<0){
					map.put("price", rPrice);
					initMap.put(Id, map);
					timeMap.put(Id,timeCompare);
				}
    		}
    		else{
    			map.put("Id", Id);
    			initMap.put(Id, map);
//    			time = timeCompare;
    			timeMap.put(Id,timeCompare);
    		}

		}
		
		return initMap;
	}
	
	/**
	 * 当分录存在被引用时，不能删除本明细单;<p>
	 * @author pengwei_hou Date 2008-9-24
	 */
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
	
		String[] pk = new String[arrayPK.length];
		for(int i=0;i<arrayPK.length;i++){
			pk[i] = arrayPK[i].toString();
		}
		FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
    	builder2.appendSql("select FID from T_PAM_PartAMaterialEntry where FParentId in (");
    	builder2.appendParam(pk);
    	builder2.appendSql(")");
    	
    	
    	IRowSet set2 = builder2.executeQuery();
    	Set detailSet = new HashSet();
    	try {
			while(set2.next()){
				String fid = set2.getString("FID");
				detailSet.add(fid);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select FPartAMaterialEntryId from T_PAM_MaterialConfirmBillEntry");
    	
    	IRowSet set = builder.executeQuery();
    	Set confmSet = new HashSet();
    	try {
			while(set.next()){
				   String fid = set.getString("FPartAMaterialEntryId");
				   confmSet.add(fid);
			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
        int a = 8;
    	for(Iterator iter=detailSet.iterator(); iter.hasNext();){
    	   if(confmSet.contains(iter.next().toString())){
    		   throw new MaterialException(MaterialException.REF_NOT_DELETE);
    	   }
    	}
    	super._delete(ctx, arrayPK);
	}
/***
 * 获取招标立项分录的物料明细信息
 * @param contractBillId 合同ID
 * @exception BOSException MaterialException
 * @return 所有物料信息
 */
	protected Map _getMaterialFromInviteProject(Context ctx,
			BOSUuid contractBillId) throws BOSException, MaterialException {
		// TODO Auto-generated method stub
		
		if(contractBillId == null){
			return null;
		}
		Map map = new HashMap();
		String inviteProjectId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select finviteprojectid from t_inv_acceptanceletter where fid =(select fsourcebillid from t_con_contractbill where fid = ?)");
		builder.addParam(contractBillId.toString());
		RowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				inviteProjectId = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}
		if(inviteProjectId != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("entry.material.number"));
			sic.add(new SelectorItemInfo("entry.material.id"));
			sic.add(new SelectorItemInfo("entry.material.name"));
			sic.add(new SelectorItemInfo("entry.material.pricePrecision"));
			sic.add(new SelectorItemInfo("entry.size"));
			sic.add(new SelectorItemInfo("entry.amount"));
			sic.add(new SelectorItemInfo("entry.measureUnit.qtyPrecision"));
			sic.add(new SelectorItemInfo("entry.measureUnit.name"));
			sic.add(new SelectorItemInfo("inviteForm"));
			sic.add(new SelectorItemInfo("entry.inputDate"));
			sic.add(new SelectorItemInfo("entry.description"));
//			sic.add(new SelectorItemInfo("entry."));
			try {
				InviteProjectInfo info = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(BOSUuid.read(inviteProjectId)), sic);
//				if(info != null && info.getInviteForm().getValue().equals("4TENDERDISCUSSION")){
				
				//招标形式为议标的招标立项才可引入。 需要其它的招标形式（如公开招标， 邀请招标及其它）也可引入 by 黄志明 2011-08-19
				if(info != null){
					InviteProjectEntryCollection cols = info.getEntry();
					map.put("cols", cols);
				}
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return map;
	}
	
	
}