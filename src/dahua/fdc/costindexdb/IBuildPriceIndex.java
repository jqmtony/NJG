package com.kingdee.eas.fdc.costindexdb;

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
import com.kingdee.eas.framework.ICoreBillBase;

public interface IBuildPriceIndex extends ICoreBillBase
{
    public BuildPriceIndexCollection getBuildPriceIndexCollection() throws BOSException;
    public BuildPriceIndexCollection getBuildPriceIndexCollection(EntityViewInfo view) throws BOSException;
    public BuildPriceIndexCollection getBuildPriceIndexCollection(String oql) throws BOSException;
    public BuildPriceIndexInfo getBuildPriceIndexInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildPriceIndexInfo getBuildPriceIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildPriceIndexInfo getBuildPriceIndexInfo(String oql) throws BOSException, EASBizException;
}