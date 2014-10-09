package com.kingdee.eas.bpmdemo;

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

public interface IChangeVisaAppEntry extends ICoreBillEntryBase
{
    public ChangeVisaAppEntryInfo getChangeVisaAppEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChangeVisaAppEntryInfo getChangeVisaAppEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChangeVisaAppEntryInfo getChangeVisaAppEntryInfo(String oql) throws BOSException, EASBizException;
    public ChangeVisaAppEntryCollection getChangeVisaAppEntryCollection() throws BOSException;
    public ChangeVisaAppEntryCollection getChangeVisaAppEntryCollection(EntityViewInfo view) throws BOSException;
    public ChangeVisaAppEntryCollection getChangeVisaAppEntryCollection(String oql) throws BOSException;
}