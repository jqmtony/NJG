package com.kingdee.eas.port.pm.base;

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

public interface IJudgesTree extends ITreeBase
{
    public JudgesTreeInfo getJudgesTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public JudgesTreeInfo getJudgesTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public JudgesTreeInfo getJudgesTreeInfo(String oql) throws BOSException, EASBizException;
    public JudgesTreeCollection getJudgesTreeCollection() throws BOSException;
    public JudgesTreeCollection getJudgesTreeCollection(EntityViewInfo view) throws BOSException;
    public JudgesTreeCollection getJudgesTreeCollection(String oql) throws BOSException;
}