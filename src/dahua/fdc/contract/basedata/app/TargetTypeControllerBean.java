package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IApportionType;
import com.kingdee.eas.fdc.basedata.ITargetType;
import com.kingdee.eas.fdc.basedata.TargetTypeCollection;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;

public class TargetTypeControllerBean extends AbstractTargetTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.TargetTypeControllerBean");
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
    	trimBlank(model);
    	TargetTypeInfo targetTypeModel = (TargetTypeInfo) model;
    	super._checkNameDup(ctx, targetTypeModel);
        super._update(ctx, pk, model);
    }
    protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	trimBlank(model);
    	TargetTypeInfo targetTypeModel = (TargetTypeInfo) model;
		// 检查是否存在相同的编码，如存在就抛出异常
		CtrlUnitInfo rootCU = new CtrlUnitInfo();
		rootCU.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		targetTypeModel.setCU(rootCU);
		super._checkNumberDup(ctx, targetTypeModel);
		super._checkNameBlank(ctx, targetTypeModel);
		super._checkNameDup(ctx, targetTypeModel);
		super._checkNumberBlank(ctx, targetTypeModel);
		// checkLongNumberDup(ctx, targetTypeModel);
		// 取得父级节点
		TargetTypeInfo parentInfo = targetTypeModel.getParent();
		if (parentInfo != null && !parentInfo.getId().equals(targetTypeModel.getId())) {
			BOSUuid parentID = parentInfo.getId();
			parentInfo = super.getTargetTypeInfo(ctx, new ObjectUuidPK(parentID));
			IObjectPK pk = new ObjectUuidPK(parentInfo.getId().toString());
			pk = super._addnew(ctx, targetTypeModel);
	
			return pk;
		} else {
			IObjectPK pk = super._addnew(ctx, targetTypeModel);
			return pk;
		}
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
//		super._delete(ctx,pk);
		TargetTypeInfo oldModel = this.getTargetTypeInfo(ctx, pk);
			if (oldModel.isIsLeaf()) {
				IApportionType iApportionType = ApportionTypeFactory.getLocalInstance(ctx);
				ApportionTypeCollection apportionTypeCollection = iApportionType.getApportionTypeCollection("where targetType.id = '" + oldModel.getId().toString() + "'");
				if(apportionTypeCollection.size()!=0){
					//被指标引用
					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.TARGETTYPE_DEL_REFERENCE);
				}else{
					super._delete(ctx, pk);
////					同时删除相关的合同详细信息定义项
//					IContractDetailDef iContractDetailDef = ContractDetailDefFactory.getLocalInstance(ctx);
//					iContractDetailDef.delete("where contractType.id = '" + oldModel.getId().toString() + "'");
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
		TargetTypeInfo tti = this.getTargetTypeInfo(ctx, PK).getParent();
		if (tti != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", tti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getTargetTypeCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		boolean flag = false;
		ITargetType iTargetType = TargetTypeFactory.getLocalInstance(ctx);    
    	TargetTypeInfo targetTypeInfo = new TargetTypeInfo();

    	targetTypeInfo = iTargetType.getTargetTypeInfo(ctPK);
	    	if(targetTypeInfo.getParent()!=null){
	    		IObjectPK parentPK = new ObjectStringPK(targetTypeInfo.getParent().getId().toString());
	    		TargetTypeInfo parentInfo = iTargetType.getTargetTypeInfo(parentPK);
	    		if(!parentInfo.isIsEnabled()){
	    			//如果上级被禁用,给出提示并返回    			
	    			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.TARGETTYPE_PARENT_DISENABLE); 			  			
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
    /*
     */
    protected boolean changeUseStatus(Context ctx, IObjectPK PK,boolean flag) throws EASBizException, BOSException {
    	ITargetType iTargetType = TargetTypeFactory.getLocalInstance(ctx);    
    	TargetTypeInfo targetTypeInfo = iTargetType.getTargetTypeInfo(PK);
    		EntityViewInfo evi=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",targetTypeInfo.getId().toString()));
    		evi.setFilter(filter); 
    		TargetTypeCollection targetTypeCollection = iTargetType.getTargetTypeCollection(evi);
    		//如果有下级,要同时启用/禁用下级
    		if(targetTypeCollection.size()>0){
    		//先启用/禁用自己
    			targetTypeInfo .setIsEnabled(flag);
    			_update(ctx,PK,targetTypeInfo);
    			//再启用/禁用下级	
    			TargetTypeInfo childInfo ;
    			IObjectPK childPK;
    			for(int i = 0;i<targetTypeCollection.size();i++){
    				if(!Boolean.valueOf(targetTypeCollection.get(i).isIsEnabled()).equals(Boolean.valueOf(flag))){
    					childInfo = targetTypeCollection.get(i);
    					childInfo.setIsEnabled(flag);
	    				childPK = new ObjectStringPK(childInfo.getId().toString());
	    				changeUseStatus(ctx,childPK,flag);

    				}
    			}    			
    		}else{
    			//如果没有下级
    			targetTypeInfo.setIsEnabled(flag);
    			_update(ctx,PK,targetTypeInfo);
    		}
//    	}else{
    		
//    	}
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
                //filter.getFilterItems().add(new FilterItemInfo("level",new Integer(treeModel.getLevel()),CompareType.EQUALS));
                //修改，应当使用parentID，因为levle是计算生成的，不应由客户端传递。 Jacky at 2004-11-4
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
        //CU隔离
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeModel);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            if(FilterUtility.hasFilterItem(filter))
//            {
//                filter.mergeFilter(filterCU,"AND");
//            }
//            else
//            {
//                filter = filterCU;
//            }
//        }

        view.setFilter(filter);

        TreeBaseCollection results = this.getTreeBaseCollection(ctx , view);

        //DataBaseCollection results = this.getDataBaseCollection(ctx,view);

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
		
//        //CU隔离
//        FilterInfo filterCU = getFilterForDefaultCU(ctx,treeBaseInfo);
//        if(FilterUtility.hasFilterItem(filterCU))
//        {
//            lNfilter.mergeFilter(filterCU,"AND");
//        }
        
		
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
		TargetTypeInfo theModel = (TargetTypeInfo) model;
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