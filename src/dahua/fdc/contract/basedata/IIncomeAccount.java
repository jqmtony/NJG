package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;

public interface IIncomeAccount extends ITreeBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public IncomeAccountInfo getIncomeAccountInfo(IObjectPK pk) throws BOSException, EASBizException;
    public IncomeAccountInfo getIncomeAccountInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public IncomeAccountInfo getIncomeAccountInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(IncomeAccountInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, IncomeAccountInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, IncomeAccountInfo model) throws BOSException, EASBizException;
    public void updatePartial(IncomeAccountInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, IncomeAccountInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public IncomeAccountCollection getIncomeAccountCollection() throws BOSException;
    public IncomeAccountCollection getIncomeAccountCollection(EntityViewInfo view) throws BOSException;
    public IncomeAccountCollection getIncomeAccountCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean assign(IObjectPK pk) throws BOSException, EASBizException;
    public boolean disassign(IObjectPK pk) throws BOSException, EASBizException;
    public boolean enable(IObjectPK pk) throws BOSException, EASBizException;
    public boolean disable(IObjectPK pk) throws BOSException, EASBizException;
    public void importDatas(IncomeAccountCollection cac, BOSUuid addressId) throws BOSException, EASBizException;
    public ArrayList importTemplateDatas(IncomeAccountCollection cac) throws BOSException, EASBizException;
}