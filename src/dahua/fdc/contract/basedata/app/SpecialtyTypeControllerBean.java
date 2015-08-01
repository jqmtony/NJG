package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.app.DbUtil;

public class SpecialtyTypeControllerBean extends AbstractSpecialtyTypeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.SpecialtyTypeControllerBean");

	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		String sql = "update T_FDC_SpecialtyType set  FIsEnabled = ?  where FID = ? ";
		Object[] params = new Object[] { new Integer(1), ctPK.toString() };
		DbUtil.execute(ctx, sql, params);
		return true;
	}

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
		String sql = "update T_FDC_SpecialtyType set  FIsEnabled = ?  where FID = ? ";
		Object[] params = new Object[] { new Integer(0), ctPK.toString() };
		DbUtil.execute(ctx, sql, params);
		return true;
	}

	protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, model);
		this._checkNameBlank(ctx, model);
		this._checkNumberDup(ctx, model);
		this._checkNameDup(ctx, model);
	}

	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, model);
		this._checkNameBlank(ctx, model);
		DataBaseInfo oldModel = this.getDataBaseInfo(ctx, pk);
		if (!((DataBaseInfo) model).getNumber().equals(oldModel.getNumber())) {
			this._checkNumberDup(ctx, model);
		}

		if (!((DataBaseInfo) model).getName().equals(oldModel.getName())) {
			this._checkNameDup(ctx, model);
		}
	}
	
    protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException
	{
	    	
    	SpecialtyTypeInfo dataBaseInfo = (SpecialtyTypeInfo) model;
        FilterInfo filter = new FilterInfo();
        FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name ,
            dataBaseInfo.getName() , CompareType.EQUALS);
        filter.getFilterItems().add(filterItem);
        
        if(dataBaseInfo.getChangeType()!=null){
	        filterItem = new FilterItemInfo("changeType.id" ,dataBaseInfo.getChangeType().getId().toString());
	        filter.getFilterItems().add(filterItem);
        }
            
        if (dataBaseInfo.getId() != null)
        {
            filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID ,
                                            dataBaseInfo.getId() ,  CompareType.NOTEQUALS);
            filter.getFilterItems().add(filterItem);
            filter.setMaskString("#0 and #1 and #2");
        }
     
        EntityViewInfo view = new EntityViewInfo();
        view.setFilter(filter);
        SorterItemCollection sorter = new SorterItemCollection();
        sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
        //@todo 由于getPKList效率很低，使用exists()替代。
        //IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

        //if (pks.length > 0)
        if (super._exists(ctx,filter))
        {
            String name = this._getPropertyAlias(ctx,dataBaseInfo,IFWEntityStruct.dataBase_Name) + dataBaseInfo.getName();
            throw new EASBizException(EASBizException.CHECKDUPLICATED ,   new Object[]  {name});

        }

	}
   
}