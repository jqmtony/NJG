package com.kingdee.eas.bpm;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBPMLog extends IDataBase
{
    public BPMLogInfo getBPMLogInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BPMLogInfo getBPMLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BPMLogInfo getBPMLogInfo(String oql) throws BOSException, EASBizException;
    public BPMLogCollection getBPMLogCollection() throws BOSException;
    public BPMLogCollection getBPMLogCollection(EntityViewInfo view) throws BOSException;
    public BPMLogCollection getBPMLogCollection(String oql) throws BOSException;
}