package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.DynCostCtrlBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.jdbc.rowset.IRowSet;

public class DynCostCtrlBillControllerBean extends AbstractDynCostCtrlBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.DynCostCtrlBillControllerBean");
    
    private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("curProject.id");
		sic.add("entries.id");
		return sic;
	}
    
    
   
    
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._audit(ctx, billId);
		DynCostCtrlBillInfo info = getDynCostCtrlBillInfo(ctx, new ObjectUuidPK(billId), getSelectors());
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		if (info.getCurProject()!= null) {
			StringBuffer sql = new StringBuffer();

			// 取消原最新版本
			sql.append(" update T_AIM_DynCostCtrlBill set fisLatestVer = 0 where fisLatestVer  = 1 and FCurProjectID = '");
			sql.append(info.getCurProject().getId().toString()).append("'");
			fdcSB.addBatch(sql.toString());
			sql.setLength(0);
			// 更新最新版本
			sql.append("update T_AIM_DynCostCtrlBill set fisLatestVer = 1 where fid = '").append(billId.toString()).append("'");
			fdcSB.addBatch(sql.toString());
			fdcSB.executeBatch();
		}
    }
    protected void _audit(Context ctx, List idList) throws BOSException, EASBizException {
    	super._audit(ctx, idList);
    }
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._unAudit(ctx, billId);
    	DynCostCtrlBillInfo info = getDynCostCtrlBillInfo(ctx, new ObjectUuidPK(billId), getSelectors());
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		if (info.getCurProject() != null) {
			StringBuffer sql = new StringBuffer("");
			sql.append("update T_AIM_DynCostCtrlBill set fisLatestVer = 0 where fid = '").append(billId.toString()).append("'");
			fdcSB.addBatch(sql.toString());
			sql.setLength(0);

			sql.append("update T_AIM_DynCostCtrlBill  set  fisLatestVer  = 1 where fid  in ");
			sql.append(" (select top 1 fid from T_AIM_DynCostCtrlBill  cynCtrl  where cynCtrl.fstate = '").append(FDCBillStateEnum.AUDITTED_VALUE)
					.append("'");
			sql.append(" and cynCtrl.FCurProjectID='").append(info.getCurProject().getId().toString()).append("'");
			sql.append(" order by  cynCtrl.fvernumber  desc )");
			fdcSB.addBatch(sql.toString());

			fdcSB.executeBatch();
		}
    	
    }
    


	protected BigDecimal _getMaxVerNumber(Context ctx, String projectId) throws BOSException {
		BigDecimal verNumber = null;
		if (projectId != null) {
			FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
			StringBuffer sql = new StringBuffer();
			sql.append("select max(dynCtrl.FVerNumber) verNumber from T_AIM_DynCostCtrlBill dynCtrl ");
			sql.append(" where FCurProjectID = ? ");
			fdcSB.setPrepareStatementSql(sql.toString());
			fdcSB.addParam(projectId.toString());
			IRowSet rowSet = fdcSB.executeQuery();
			try {
				if (rowSet.next()) {
					verNumber = rowSet.getBigDecimal("verNumber");
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		return verNumber;
	}
	
	/**
	 * 取消单据名称 40个字符限制
	 */
	protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
		}
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		super.checkBill(ctx, model);
	}

	/**
	 * 
	 * 描述：检查名称重复
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-8-24
	 *               <p>
	 */
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if (!isUseName())
			return;
		if (billInfo.get("verNumber") != null
				&& FDCHelper.toBigDecimal(billInfo.get("verNumber")).compareTo(
						FDCHelper.ONE) == 1) {
			return;
		} else {
			FilterInfo filter = new FilterInfo();

			filter.getFilterItems().add(
					new FilterItemInfo("name", billInfo.getName()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID,
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
							.getId()));
			if (billInfo.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", billInfo.getId().toString(),
								CompareType.NOTEQUALS));
			}

			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.NAME_DUP);
			}
		}
	}

	/**
	 * 
	 * 描述：检查编码重复
	 * <p>
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author liupd 创建时间：2006-8-24
	 *         <p>
	 */
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		if (!isUseNumber())
			return;
		FilterInfo filter = new FilterInfo();

		if (billInfo.get("verNumber") != null
				&& FDCHelper.toBigDecimal(billInfo.get("verNumber")).compareTo(
						FDCHelper.ONE) == 1) {
			return;
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("number", billInfo.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID,
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
							.getId()));
			if (billInfo.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", billInfo.getId().toString(),
								CompareType.NOTEQUALS));
			}

			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.NUMBER_DUP);
			}
		}
	}
}