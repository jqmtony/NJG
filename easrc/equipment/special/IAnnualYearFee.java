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

public interface IAnnualYearFee extends IXRBillBase
{
    public void addnew(IObjectPK pk, AnnualYearFeeInfo model) throws BOSException, EASBizException;
    public IObjectPK addnew(AnnualYearFeeInfo model) throws BOSException, EASBizException;
    public void audit(IObjectPK pk) throws BOSException, EASBizException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public AnnualYearFeeCollection getAnnualYearFeeCollection() throws BOSException;
    public AnnualYearFeeCollection getAnnualYearFeeCollection(EntityViewInfo view) throws BOSException;
    public AnnualYearFeeCollection getAnnualYearFeeCollection(String oql) throws BOSException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public AnnualYearFeeInfo getAnnualYearFeeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AnnualYearFeeInfo getAnnualYearFeeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AnnualYearFeeInfo getAnnualYearFeeInfo(String oql) throws BOSException, EASBizException;
    public void unAudit(IObjectPK pk) throws BOSException, EASBizException;
    public void unAudit(IObjectPK[] pks) throws BOSException, EASBizException;
    public void update(IObjectPK pk, AnnualYearFeeInfo model) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, AnnualYearFeeInfo model) throws BOSException;
    public void updatePartial(AnnualYearFeeInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public String getBindingProperty() throws BOSException;
}