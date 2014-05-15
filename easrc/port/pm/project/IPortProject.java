package com.kingdee.eas.port.pm.project;

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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPortProject extends ITreeBase
{
    public PortProjectInfo getPortProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PortProjectInfo getPortProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PortProjectInfo getPortProjectInfo(String oql) throws BOSException, EASBizException;
    public PortProjectCollection getPortProjectCollection() throws BOSException;
    public PortProjectCollection getPortProjectCollection(EntityViewInfo view) throws BOSException;
    public PortProjectCollection getPortProjectCollection(String oql) throws BOSException;
}