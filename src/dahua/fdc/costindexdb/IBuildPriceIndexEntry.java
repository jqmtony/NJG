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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IBuildPriceIndexEntry extends ICoreBillEntryBase
{
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(String oql) throws BOSException, EASBizException;
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection() throws BOSException;
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(EntityViewInfo view) throws BOSException;
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(String oql) throws BOSException;
}