package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ISupplierContentEntry extends ICoreBillEntryBase
{
    public SupplierContentEntryInfo getSupplierContentEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierContentEntryInfo getSupplierContentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierContentEntryInfo getSupplierContentEntryInfo(String oql) throws BOSException, EASBizException;
    public SupplierContentEntryCollection getSupplierContentEntryCollection() throws BOSException;
    public SupplierContentEntryCollection getSupplierContentEntryCollection(EntityViewInfo view) throws BOSException;
    public SupplierContentEntryCollection getSupplierContentEntryCollection(String oql) throws BOSException;
}