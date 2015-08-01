/*
 * @(#)HashSearchEngine.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fm.common.FMHelper;

public class HashSearchEngine {
	private String[] destKeys;

	private Map map = null;
	private String[] srcKeys;

	private IObjectValue vo;

	public HashSearchEngine(IObjectCollection iObjCol, String[] keys) {
		this(keys);
		int colSize = iObjCol.size();
		for (int i = 0; i < colSize; i++) {
			add(iObjCol.getObject(i));
		}
	}

	public HashSearchEngine(IObjectValue[] coll, String[] keys) {
		this(keys);
		for (int i = 0; i < coll.length; i++) {
			add(coll[i]);

		}
	}

	public HashSearchEngine(String[] keys) {
		this.srcKeys = keys;
		this.setDestKeys(keys);
		this.map = new HashMap();
	}

	/**
	 * 描述：添加元素
	 * 
	 * @param iObjVal
	 *            liupd 创建时间：2005-5-12
	 */
	public void add(IObjectValue iObjVal) {
		String keyString = FMHelper.getKeyString(iObjVal, srcKeys);
		if (map.containsKey(keyString)) {
			throw new IllegalArgumentException("key values is repeat"
					+ keyString);
		}
		map.put(keyString, iObjVal);
	}

	/**
	 * 
	 * 描述：清除所有元素
	 * 
	 * liupd 创建时间：2005-5-12
	 */
	public void clear() {
		map.clear();
	}

	public boolean evaluate(IObjectValue iter) {
		String key =FMHelper.getKeyString(iter, getDestKeys());
		vo = (IObjectValue) map.get(key);
		if (vo != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return Returns the dstKeys.
	 */
	public String[] getDestKeys() {
		return destKeys;
	}

	public Map getHashMap() {
		return map;
	}


	public IObjectValue getResult() {
		return vo;

	}

	/**
	 * 描述：删除指定元素
	 * 
	 * @param iObjVal
	 *            liupd 创建时间：2005-5-12
	 */
	public void remove(IObjectValue iObjVal) {
		String keyString = FMHelper.getKeyString(iObjVal, srcKeys);
		if (!map.containsKey(keyString)) {
			throw new IllegalArgumentException("key not found:" + keyString);
		}
		map.remove(keyString);
	}

	/**
	 * @param dstKeys
	 *            The dstKeys to set.
	 */
	public void setDestKeys(String[] dstKeys) {
		this.destKeys = dstKeys;
	}
	public void cat(IObjectCollection coll){
		for(Iterator iter = map.values().iterator(); iter.hasNext(); ){
			IObjectValue info = (IObjectValue)iter.next();
			coll.addObject(info);
		}
	}
}
