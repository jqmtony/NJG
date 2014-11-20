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

public interface IBPMServerConfig extends IDataBase
{
    public BPMServerConfigInfo getBPMServerConfigInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BPMServerConfigInfo getBPMServerConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BPMServerConfigInfo getBPMServerConfigInfo(String oql) throws BOSException, EASBizException;
    public BPMServerConfigCollection getBPMServerConfigCollection() throws BOSException;
    public BPMServerConfigCollection getBPMServerConfigCollection(EntityViewInfo view) throws BOSException;
    public BPMServerConfigCollection getBPMServerConfigCollection(String oql) throws BOSException;
}