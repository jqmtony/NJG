package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.xr.IXRBillBase;

public interface IAnnualYearDetail extends IXRBillBase
{
    public void addnew(IObjectPK pk, AnnualYearDetailInfo model) throws BOSException, EASBizException;
    public IObjectPK addnew(AnnualYearDetailInfo model) throws BOSException, EASBizException;
    public void audit(IObjectPK pk) throws BOSException, EASBizException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public AnnualYearDetailCollection getAnnualYearDetailCollection() throws BOSException;
    public AnnualYearDetailCollection getAnnualYearDetailCollection(EntityViewInfo view) throws BOSException;
    public AnnualYearDetailCollection getAnnualYearDetailCollection(String oql) throws BOSException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public AnnualYearDetailInfo getAnnualYearDetailInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AnnualYearDetailInfo getAnnualYearDetailInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AnnualYearDetailInfo getAnnualYearDetailInfo(String oql) throws BOSException, EASBizException;
    public void unAudit(IObjectPK pk) throws BOSException, EASBizException;
    public void unAudit(IObjectPK[] pks) throws BOSException, EASBizException;
    public void update(IObjectPK pk, AnnualYearDetailInfo model) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, AnnualYearDetailInfo model) throws BOSException;
    public void updatePartial(AnnualYearDetailInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public String getBindingProperty() throws BOSException;
    public void entry(AnnualYearDetailInfo model) throws BOSException;
    public void confirmation(AnnualYearDetailInfo model) throws BOSException;
    public void unConfirmation(AnnualYearDetailInfo model) throws BOSException;
    public void confirm(AnnualYearDetailInfo model) throws BOSException;
    public void unConfirm(AnnualYearDetailInfo model) throws BOSException;
    public void xiada(AnnualYearDetailInfo model) throws BOSException;
    public void fanxiada(AnnualYearDetailInfo model) throws BOSException;
}