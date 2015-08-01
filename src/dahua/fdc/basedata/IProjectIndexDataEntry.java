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
import com.kingdee.eas.framework.IObjectBase;

public interface IProjectIndexDataEntry extends IObjectBase
{
    public ProjectIndexDataEntryInfo getProjectIndexDataEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectIndexDataEntryInfo getProjectIndexDataEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectIndexDataEntryInfo getProjectIndexDataEntryInfo(String oql) throws BOSException, EASBizException;
    public ProjectIndexDataEntryCollection getProjectIndexDataEntryCollection() throws BOSException;
    public ProjectIndexDataEntryCollection getProjectIndexDataEntryCollection(EntityViewInfo view) throws BOSException;
    public ProjectIndexDataEntryCollection getProjectIndexDataEntryCollection(String oql) throws BOSException;
}