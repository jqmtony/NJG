package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.DbUtil;
/**
 * 描述:合同详细信息定义
 * @author jackwang  date:2006-8-8 <p>
 * @version EAS5.1
 */
public class ContractDetailDefControllerBean extends AbstractContractDetailDefControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ContractDetailDefControllerBean");
    /**
     * 新增逻辑检查 1.检查编码名称是否为空。 2.检查编码名称是否重复。
     * 
     * @param ctx
     * @param model
     * @throws BOSException
     * @throws EASBizException
     */
    protected void addnewCheck(Context ctx, IObjectValue model)
            throws BOSException, EASBizException {
        this._checkNumberBlank(ctx, model);
        this._checkNameBlank(ctx, model);
        this._checkNumberDup(ctx, model);
//        this._checkNameDup(ctx, model);
    }
    /**
     * 修改逻辑检查 1.检查编码名称是否为空。 2.检查编码名称是否重复。
     * 
     * @param ctx
     * @param pk
     * @param model
     * @throws BOSException
     * @throws EASBizException
     */
    protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model)
            throws BOSException, EASBizException {
        this._checkNumberBlank(ctx, model);
        this._checkNameBlank(ctx, model);
        DataBaseInfo oldModel = this.getDataBaseInfo(ctx, pk);
        if (!((DataBaseInfo) model).getNumber().equals(oldModel.getNumber())) {
            this._checkNumberDup(ctx, model);
        }

        if (!((DataBaseInfo) model).getName().equals(oldModel.getName())) {
//            this._checkNameDup(ctx, model);
        }
    }
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
        try {
            this._isReferenced(ctx, pk);           
        } catch (ObjectReferedException ex) {
        	throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DELETE_ISREFERENCE_FAIL);
        }
        FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("detailDefID", pk.toString()));
		if(ContractBillEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
		}
        super._delete(ctx, pk);
		
//		try {
//			
//		} catch (Exception e) {
//			ExceptionHandler.handle(e);
//		}
		
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ContractdetailDef set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(1), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ContractdetailDef set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(0), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
}