package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IContractDetailDef;
import com.kingdee.eas.fdc.basedata.IContractType;
import com.kingdee.eas.fdc.basedata.IInvalidCostReason;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.DbUtil;

public class InvalidCostReasonControllerBean extends AbstractInvalidCostReasonControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.InvalidCostReasonControllerBean");
    
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		InvalidCostReasonInfo invalidCostReasonModel = (InvalidCostReasonInfo) model;
		// 检查是否存在相同的编码，如存在就抛出异常
		CtrlUnitInfo rootCU = new CtrlUnitInfo();
		rootCU.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		invalidCostReasonModel.setCU(rootCU);
		super._checkNumberDup(ctx, invalidCostReasonModel);
		super._checkNameBlank(ctx, invalidCostReasonModel);
		super._checkNameDup(ctx, invalidCostReasonModel);
		super._checkNumberBlank(ctx, invalidCostReasonModel);

		// 取得父级节点
		InvalidCostReasonInfo parentInfo = invalidCostReasonModel.getParent();
		if (parentInfo != null && !parentInfo.getId().equals(invalidCostReasonModel.getId())) {
			BOSUuid parentID = parentInfo.getId();
			parentInfo = super.getInvalidCostReasonInfo(ctx, new ObjectUuidPK(parentID));
			IObjectPK pk = new ObjectUuidPK(parentInfo.getId().toString());
			invalidCostReasonModel.setIsEnabled(parentInfo.isIsEnabled());
			pk = super._addnew(ctx, invalidCostReasonModel);
			return pk;
		} else {
			IObjectPK pk = super._addnew(ctx, invalidCostReasonModel);
			return pk;
		}
	}

    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
    	trimBlank(model);
    	super._update(ctx, pk, model);
    }
    
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// 郑邦弘说：删除后剩下全部为禁用状态也不可以
		InvalidCostReasonInfo invalidCostReasonInfo = this.getInvalidCostReasonInfo(ctx, pk);
		InvalidCostReasonInfo cti = invalidCostReasonInfo.getParent();
		if (invalidCostReasonInfo.isIsEnabled() && cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			evi.setFilter(filter);
			if (this.getInvalidCostReasonCollection(ctx, evi).size() > 1) {
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				if(this.getInvalidCostReasonCollection(ctx, evi).size() <= 1)
					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
			}
		}	

		InvalidCostReasonInfo oldModel = invalidCostReasonInfo;

			if (oldModel.isIsLeaf()) {
				IChangeAuditBill iChangeAuditBill = ChangeAuditBillFactory.getLocalInstance(ctx);
				ChangeAuditBillCollection changeAuditBillCollection = iChangeAuditBill.getChangeAuditBillCollection("where invalidCostReason.id = '" + oldModel.getId().toString() + "'");
				if(changeAuditBillCollection.size()!=0){
					//被引用
					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.INVALIDCOSTREASON_DEL_REFERENCE);
				}else{
					super._delete(ctx, pk);
				}
			}else{
				//非叶结点，不允许删除
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DELETE_ISPARENT_FAIL);
			}
	}

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		if (this.checkIsOnlyOneEnabled(ctx, ctPK)) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
		}
		if(changeUseStatus(ctx,ctPK,false))
			return true;
		else 
			return false;
	}
	private boolean checkIsOnlyOneEnabled(Context ctx, IObjectPK PK) throws BOSException, EASBizException {
		InvalidCostReasonInfo cti = this.getInvalidCostReasonInfo(ctx, PK).getParent();
		if (cti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getInvalidCostReasonCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		boolean flag = false;
		IInvalidCostReason iInvalidCostReason = InvalidCostReasonFactory.getLocalInstance(ctx);    
    	InvalidCostReasonInfo invalidCostReasonInfo = new InvalidCostReasonInfo();

    	invalidCostReasonInfo = iInvalidCostReason.getInvalidCostReasonInfo(ctPK);
	    	if(invalidCostReasonInfo.getParent()!=null){
	    		IObjectPK parentPK = new ObjectStringPK(invalidCostReasonInfo.getParent().getId().toString());
	    		InvalidCostReasonInfo parentInvalidCostReasonInfo = iInvalidCostReason.getInvalidCostReasonInfo(parentPK);
	    		if(!parentInvalidCostReasonInfo.isIsEnabled()){
	    			//如果上级被禁用,给出提示并返回    			
	    			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.INVALIDCOSTREASON_PARENT_DISENABLE); 			  			
	    		}else{
	    			if(changeUseStatus(ctx,ctPK,true))
	    				flag = true;
	    		}
	    			
	    	}else{
				if(changeUseStatus(ctx,ctPK,true))
					flag = true;
	    	}				

    	return flag;
	}
	
	/**
	 * 禁用/启用无效成本原因
	 * @param PK 无效成本原因的PK
	 * @param flag 禁用还是启用
	 */
    protected boolean changeUseStatus(Context ctx, IObjectPK PK,boolean flag) throws EASBizException, BOSException {
    	IInvalidCostReason iInvalidCostReason = InvalidCostReasonFactory.getLocalInstance(ctx);    
    	InvalidCostReasonInfo invalidCostReasonInfo = iInvalidCostReason.getInvalidCostReasonInfo(PK);    
    	
    		EntityViewInfo evi=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",invalidCostReasonInfo.getId().toString()));
    		evi.setFilter(filter); 
    		InvalidCostReasonCollection invalidCostReasonCollection = iInvalidCostReason.getInvalidCostReasonCollection(evi);
    		//如果有下级,要同时启用/禁用下级
    		if(invalidCostReasonCollection.size()>0){
    		//先启用/禁用自己
    			invalidCostReasonInfo .setIsEnabled(flag);
    			_update(ctx,PK,invalidCostReasonInfo);
    			//再启用/禁用下级	
    			InvalidCostReasonInfo childInvalidCostReasonInfo ;
    			IObjectPK childPK;
    			for(int i = 0;i<invalidCostReasonCollection.size();i++){
    				if(!Boolean.valueOf(invalidCostReasonCollection.get(i).isIsEnabled()).equals(Boolean.valueOf(flag))){
    					childInvalidCostReasonInfo = invalidCostReasonCollection.get(i);
    					childInvalidCostReasonInfo.setIsEnabled(flag);
	    				childPK = new ObjectStringPK(childInvalidCostReasonInfo.getId().toString());
	    				changeUseStatus(ctx,childPK,flag);

    				}
    			}    			
    		}else{
    			//如果没有下级
    			invalidCostReasonInfo.setIsEnabled(flag);
    			_update(ctx,PK,invalidCostReasonInfo);
    		}
    		return true;
    }
    /**
     * 相同的父节点下不能相同的编码。
     * @param ctx Context
     * @param model DataBaseInfo
     * @throws BOSException
     * @throws EASBizException
     */
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws
        BOSException , EASBizException
    {
        TreeBaseInfo treeModel = (TreeBaseInfo) model;

        //if no parent,no need to check
        FilterInfo filter = new FilterInfo();
        FilterItemInfo filterItem = null;
        //父节点为空时检查根对象编码是否重复。
        if (treeModel.innerGetParent() == null)
        {
            filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number ,
                                            treeModel.getNumber() ,
                                            CompareType.EQUALS);
            filter.getFilterItems().add(filterItem);
            filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent ,null ,CompareType.EQUALS));
            filter.setMaskString("#0 and #1");
            if (treeModel.getId() != null)
            {
                filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
                                                treeModel.getId().
                                                toString() ,
                                                CompareType.NOTEQUALS);
                filter.getFilterItems().add(filterItem);
                filter.setMaskString("#0 and #1 and #2");
            }
        }
        else
        {
            filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number ,
                                            treeModel.getNumber() ,
                                            CompareType.EQUALS);
            filter.getFilterItems().add(filterItem);
            if (treeModel.innerGetParent().getId() != null)
            {
                filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent ,
                                                treeModel.innerGetParent().
                                                getId().
                                                toString() , CompareType.EQUALS);
                filter.getFilterItems().add(filterItem);
                filter.setMaskString("#0 and #1");
            }
            if (treeModel.getId() != null)
            {
                filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
                                                treeModel.getId().
                                                toString() ,
                                                CompareType.NOTEQUALS);
                filter.getFilterItems().add(filterItem);
                if (treeModel.innerGetParent().getId() != null)
                {
                    filter.setMaskString("#0 and #1 and #2");
                }
                else
                {
                    filter.setMaskString("#0 and #1");
                }
            }
        }

        EntityViewInfo view = new EntityViewInfo();
        view.setFilter(filter);
        TreeBaseCollection results = this.getTreeBaseCollection(ctx , view);
        if (results != null && results.size() > 0)
        {
            throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
                                        new Object[]
                                        {treeModel.getNumber()});

        }

    }
    /**
     * 默认实现对于相 同的长编码，但ID不同进行处理。子类可按需要覆盖实现。
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		//如果数据库存在相同longNumber但ID不同的数据，则异常。
		FilterInfo lNfilter = new FilterInfo();
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_LongNumber,treeBaseInfo.getLongNumber()));
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID,treeBaseInfo.getId().toString(),CompareType.NOTEQUALS));
		lNfilter.setMaskString("#0 AND #1");
		
		if(exists(ctx,lNfilter))
		{
			 throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED ,
		            new Object[]
		            {treeBaseInfo.getNumber()});
		}
	}
	
	/**
	 * 清除前后空格
	 * 
	 * @param id
	 * @return
	 */
	private IObjectValue trimBlank(IObjectValue model){
		InvalidCostReasonInfo theModel = (InvalidCostReasonInfo) model;
		if(theModel.getNumber() != null){
			theModel.setNumber(theModel.getNumber().trim());
		}
		if(theModel.getName() != null){
			theModel.setName(theModel.getName().trim());
		}
		if(theModel.getDescription() != null){
			theModel.setDescription(theModel.getDescription().trim());
		}
		return model;
	}
}