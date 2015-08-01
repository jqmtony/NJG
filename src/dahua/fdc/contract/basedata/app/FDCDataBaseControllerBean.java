package com.kingdee.eas.fdc.basedata.app;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.DbUtil;

/**
 * ����:���ز��������ϻ���
 * 
 * @author jackwang date:2006-7-7
 *         <p>
 * @version EAS5.1
 */
public class FDCDataBaseControllerBean extends AbstractFDCDataBaseControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean");

	/**
	 * �����߼���� 1.�����������Ƿ�Ϊ�ա� 2.�����������Ƿ��ظ���
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, model);
		this._checkNameBlank(ctx, model);
		this._checkNumberDup(ctx, model);
		this._checkNameDup(ctx, model);
	}

	/**
	 * ����
	 * 
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		addnewCheck(ctx, model);
		return super._addnew(ctx, model);
	}

	/**
	 * ����
	 * 
	 * @param ctx
	 * @param pk
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		addnewCheck(ctx, model);
		super._addnew(ctx, pk, model);
	}

	/**
	 * �޸��߼���� 1.�����������Ƿ�Ϊ�ա� 2.�����������Ƿ��ظ���
	 * 
	 * @param ctx
	 * @param pk
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
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

	/**
	 * �޸�
	 * 
	 * @param ctx
	 * @param pk
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		updateCheck(ctx, pk, model);
		super._update(ctx, pk, model);
	}

	/*
	 * @author ɾ���������ò�����ɾ�� ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		isSysData(ctx, pk.toString());
		try {
			this._isReferenced(ctx, pk);
		} catch (ObjectReferedException ex) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DELETE_ISREFERENCE_FAIL);
		}
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			isSysData(ctx, arrayPK[i].toString());
			_isReferenced(ctx, arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}

	/**
	 * 
	 * ���������ڻ����и÷���������������CU�����жϣ� ���ǽ���number�Ƿ��ظ���CUû�й�ϵ����ʱ����
	 * 
	 * @author:dongpeng
	 * @see com.kingdee.eas.framework.app.AbstractDataBaseControllerBean#_checkNumberDup(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		// @todo ����getPKListЧ�ʺܵͣ�ʹ��exists()�����
		// IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

		// if(pks.length>0)
		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Number) + dataBaseInfo.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}

	}

	/**
	 * ϵͳԤ������
	 * 
	 * @param id
	 * @return
	 */
	private boolean isSysData(Context ctx, String id) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", id);
		Set set = new HashSet();
		set.add(OrgConstants.SYS_CU_ID);
		// set.add(OrgConstants.DEF_CU_ID);

		filter.getFilterItems().add(new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
		// filter.appendFilterItem("CU.id",OrgConstants.SYS_CU_ID);
		boolean isSys = false;
		if (_exists(ctx, filter)) {
			isSys = true;
		} else {
			isSys = false;
		}

		if (isSys) {
			throw new FDCBasedataException(FDCBasedataException.DELETE_SYSDATA);
		}
		return isSys;
	}

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK, IObjectValue model) throws BOSException, EASBizException {
		// IFDCDataBase m=(IFDCDataBase)model;
		// FDCDataBaseInfo dataBaseInfo = m.getFDCDataBaseInfo(ctPK);
		// dataBaseInfo .setIsEnabled(false);
		// _update(ctx,ctPK,dataBaseInfo);
		return setEnable(ctx, ctPK, model, false);
	}

	protected boolean _enabled(Context ctx, IObjectPK ctPK, IObjectValue model) throws BOSException, EASBizException {
		return setEnable(ctx, ctPK, model, true);
	}

	private boolean setEnable(Context ctx, IObjectPK ctPK, IObjectValue model, boolean flag) throws EASBizException, BOSException {
		// IFDCDataBase m = (IFDCDataBase) model;
		// FDCDataBaseInfo dataBaseInfo = m.getFDCDataBaseInfo(ctPK);
		// dataBaseInfo.setIsEnabled(flag);
		// _update(ctx, ctPK, dataBaseInfo);

		IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		FDCDataBaseInfo m = (FDCDataBaseInfo) model;
		String classname = m.getClass().getName();
		int x = classname.lastIndexOf("Info");
		if (x > 0)
			classname = classname.substring(0, x);
		classname = classname.substring(0, classname.lastIndexOf('.')) + ".app" + classname.substring(classname.lastIndexOf('.'));
		IMetaDataPK pk = new MetaDataPK(classname);
		loader.getEntity(pk);
		EntityObjectInfo q = loader.getEntity(pk);
		String name = q.getTable().getName();
		String sql = "update " + name + " set  FIsEnabled = ?  where FID = ? ";
		Object[] params = new Object[] { new Integer(flag ? 1 : 0), ctPK.toString() };
		DbUtil.execute(ctx, sql, params);
		return true;
	}

	
	/**
	 * ���ǰ��ո�
	 * 
	 * @param id
	 * @return
	 */
	private IObjectValue trimBlank(IObjectValue model){
		DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
		if(dataBaseInfo.getNumber() != null){
			dataBaseInfo.setNumber(dataBaseInfo.getNumber().trim());
		}
		if(dataBaseInfo.getName() != null){
			dataBaseInfo.setName(dataBaseInfo.getName().trim());
		}
		if(dataBaseInfo.getDescription() != null){
			dataBaseInfo.setDescription(dataBaseInfo.getDescription().trim());
		}
		return model;
	}
}