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
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBill;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISettNoCostSplit extends IFDCNoCostSplitBill
{
    public SettNoCostSplitInfo getSettNoCostSplitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SettNoCostSplitInfo getSettNoCostSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SettNoCostSplitInfo getSettNoCostSplitInfo(String oql) throws BOSException, EASBizException;
    public SettNoCostSplitCollection getSettNoCostSplitCollection() throws BOSException;
    public SettNoCostSplitCollection getSettNoCostSplitCollection(EntityViewInfo view) throws BOSException;
    public SettNoCostSplitCollection getSettNoCostSplitCollection(String oql) throws BOSException;
}