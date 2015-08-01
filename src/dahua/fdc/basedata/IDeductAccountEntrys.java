package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.eas.framework.ICoreBase;

public interface IDeductAccountEntrys extends ICoreBase
{
    public DeductAccountEntrysInfo getDeductAccountEntrysInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DeductAccountEntrysInfo getDeductAccountEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DeductAccountEntrysInfo getDeductAccountEntrysInfo(String oql) throws BOSException, EASBizException;
    public DeductAccountEntrysCollection getDeductAccountEntrysCollection() throws BOSException;
    public DeductAccountEntrysCollection getDeductAccountEntrysCollection(EntityViewInfo view) throws BOSException;
    public DeductAccountEntrysCollection getDeductAccountEntrysCollection(String oql) throws BOSException;
}