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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface IFDCBillWFAuditInfo extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCBillWFAuditInfoInfo getFDCBillWFAuditInfoInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCBillWFAuditInfoInfo getFDCBillWFAuditInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCBillWFAuditInfoInfo getFDCBillWFAuditInfoInfo(String oql) throws BOSException, EASBizException;
    public FDCBillWFAuditInfoCollection getFDCBillWFAuditInfoCollection() throws BOSException;
    public FDCBillWFAuditInfoCollection getFDCBillWFAuditInfoCollection(EntityViewInfo view) throws BOSException;
    public FDCBillWFAuditInfoCollection getFDCBillWFAuditInfoCollection(String oql) throws BOSException;
}