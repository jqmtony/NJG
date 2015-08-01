package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author ÀÔ∫£¡¡
 */
public class XTable implements Serializable {

    private Map table = new TreeMap ();

    private List columnKeys;

    private List pkColumn;

    public XTable() {
        this.columnKeys = new ArrayList();
        this.pkColumn = new ArrayList();
    }

    public XTable(IObjectCollection collection, String[] columnKeys) {
        this.columnKeys = new ArrayList();
        if (columnKeys != null) {
            for (int i = 0; i < columnKeys.length; i++) {
                this.addColumn(columnKeys[i]);
            }
        }
        int size = collection.size();
        for (int i = 0; i < size; i++) {
            IObjectValue object = collection.getObject(i);
            Map row = this.addRow(object.getString("id"));
            if (columnKeys != null) {
                for (int j = 0; j < columnKeys.length; j++) {
                    Object value = object.get(columnKeys[j]);
                    if (value instanceof IObjectValue) {
                        row.put(columnKeys[j], ((IObjectValue) value).get("id").toString());
                    } else {
                        row.put(columnKeys[j], value);
                    }
                }
            }
            this.setUserObject(row, object);
        }
    }

    public XTable(IObjectCollection collection, String columnKey) {
        this.columnKeys = new ArrayList();
        if (columnKey != null) {
            this.addColumn(columnKey);
        }
        int size = collection.size();
        for (int i = 0; i < size; i++) {
            IObjectValue object = collection.getObject(i);
            Map row = this.addRow(object.getString("id"));
            if (columnKey != null) {
                Object value = object.get(columnKey);
                if (value instanceof IObjectValue) {
                    row.put(columnKey, ((IObjectValue) value).get("id").toString());
                } else {
                    row.put(columnKey, value);
                }
            }
            this.setUserObject(row, object);
        }
    }

    public void setUserObject(Map row, Object object) {
        this.setRowProperty(row, "UserObject", object);
    }

    public void getUserObject(Map row) {
        this.getRowProperty(row, "UserObject");
    }

    public XTable(IRowSet rs, String[] keyFields) throws SQLException {
            this.createByRowSet(rs, keyFields);
    }

    public void deleteRow(String rowKey) {
        table.remove(rowKey);
    }

    public String[] getRowKeys() {
        return (String[]) table.keySet().toArray(new String[this.getRowCount()]);
    }

    public boolean existColumn(String columnKey) {
        return columnKeys.contains(columnKey);
    }

    public void addColumn(String columnKey, Map column) {
        if (existColumn(columnKey)) {
            return;
        }
        addColumn(columnKey);
        Set keys = column.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Map row = getRow(key);
            if (row == null) {
                row = this.addRow(key);
            }
            row.put(columnKey, column.get(key));

        }
    }

    public void addColumn(String columnKey) {
        this.columnKeys.add(columnKey);
        for (int i = 0; i < this.getRowCount(); i++) {
            Map row = getRow(i);
            row.put(columnKey, null);
        }
    }

    public String getRowKey(int index) {
        return (String) table.keySet().toArray()[index];
    }

    public Map getRow(int index) {

        return (Map) table.values().toArray()[index];
    }

    public Map getRow(String rowKey) {
        return (Map) table.get(rowKey);
    }

    public List getRows() {
        List rows = new ArrayList();
        for (int i = 0; i < this.getRowCount(); i++) {
            rows.add(this.getRow(i));
        }
        return rows;
    }

    public List getContainRows(String columnKey, String[] values) {
        Set valueSet = new HashSet();
        for (int i = 0; i < values.length; i++) {
            valueSet.add(values[i]);
        }
        FilterItemCollection items = new FilterItemCollection();
        items.add(new FilterItemInfo(columnKey, valueSet, CompareType.INCLUDE));
        return this.getRows(items);
    }

    public List getEqualRows(String columnKey, String value) {
        FilterItemCollection items = new FilterItemCollection();
        items.add(new FilterItemInfo(columnKey, value));
        return this.getRows(items);
    }

    public List getRows(FilterItemCollection items) {
        if (items.size() == 0) {
            return this.getRows();
        }
        List rows = new ArrayList();
        int count = getRowCount();
        for (int i = 0; i < count; i++) {
            Map row = this.getRow(i);
            boolean isAccord = true;
            for (int j = 0; j < items.size(); j++) {
                FilterItemInfo item = items.get(j);
                String colKey = item.getPropertyName();
                Object value = item.getCompareValue();
                if (item.getCompareType().equals(CompareType.EQUALS)) {
                    if (!row.get(colKey).equals(value)) {
                        isAccord = false;
                    }
                } else if (item.getCompareType().equals(CompareType.INCLUDE)) {

                    if (!((Set) value).contains(row.get(colKey))) {
                        isAccord = false;
                    }
                } else if (item.getCompareType().equals(CompareType.GREATER)) {
                    Object o = row.get(colKey);
                    if (o instanceof Date) {
                        if (!((Date) o).after((Date) value)) {
                            isAccord = false;
                        }
                    } else if (o instanceof BigDecimal) {
                        if (((BigDecimal) o).compareTo((BigDecimal) value) <= 0) {
                            isAccord = false;
                        }
                    } else if (o instanceof Integer) {
                        if (((Integer) o).compareTo((Integer) value) <= 0) {
                            isAccord = false;
                        }
                    }

                } else if (item.getCompareType().equals(CompareType.LESS)) {
                    Object o = row.get(colKey);
                    if (o instanceof Date) {
                        if (!((Date) o).before((Date) value)) {
                            isAccord = false;
                        }
                    } else if (o instanceof BigDecimal) {
                        if (((BigDecimal) o).compareTo((BigDecimal) value) >= 0) {
                            isAccord = false;
                        }
                    } else if (o instanceof Integer) {
                        if (((Integer) o).compareTo((Integer) value) >= 0) {
                            isAccord = false;
                        }
                    }

                } else if (item.getCompareType().equals(CompareType.GREATER_EQUALS)) {
                    Object o = row.get(colKey);
                    if (o instanceof Date) {
                        if (((Date) o).before((Date) value)) {
                            isAccord = false;
                        }
                    } else if (o instanceof BigDecimal) {
                        if (((BigDecimal) o).compareTo((BigDecimal) value) < 0) {
                            isAccord = false;
                        }
                    } else if (o instanceof Integer) {
                        if (((Integer) o).compareTo((Integer) value) < 0) {
                            isAccord = false;
                        }
                    }
                }
            }
            if (isAccord) {
                rows.add(row);
            }
        }
        return rows;
    }

    public int getRowCount() {
        return table.size();
    }

    public Map addRow(String rowKey) {
        if (hasRowKey(rowKey)) {
            return this.getRow(rowKey);
        }
        Map row = new HashMap();
        for (int i = 0; i < columnKeys.size(); i++) {
            row.put(columnKeys.get(i), null);
        }
        table.put(rowKey, row);
        return row;
    }

    //    public int getRowCount(String rowKey) {
    //        int count = 0;
    //        while (count < getRowCount()) {
    //            if (table.get(rowKey + count) != null) {
    //                count++;
    //            } else {
    //                break;
    //            }
    //        }
    //        return count;
    //    }

    /**
     * @param rowKey
     * @return
     */
    private boolean hasRowKey(String rowKey) {
        return this.table.containsKey(rowKey);
    }

    public Object getCell(int rowIndex, String columnKey) {
        Map row = getRow(rowIndex);
        return row.get(columnKey);
    }

    public Object getCell(String rowKey, String columnKey) {
        Map row = getRow(rowKey);
        return row.get(columnKey);
    }

    public Map getColumn(String columnKey) {
        Map column = new HashMap();
        for (int i = 0; i < getRowCount(); i++) {
            column.put(getRowKey(i), getCell(i, columnKey));
        }
        return column;
    }

    public void setRowProperty(Map row, String propertyName, Object propertyValue) {
        row.put(propertyName, propertyValue);
    }

    public Object getRowProperty(Map row, String propertyName) {
        return row.get(propertyName);
    }

    public String getColumnKey(int i) {
        return (String) this.columnKeys.get(i);
    }

    public String[] getColumnKeys() {
        return (String[]) this.columnKeys.toArray(new String[this.getColumnCount()]);
    }

    public void createByRowSet(IRowSet rowSet, String[] keyFields) throws SQLException {
        if (keyFields == null) {
            return;
        }
        ResultSetMetaData meta = rowSet.getMetaData();
        int fieldCount = meta.getColumnCount();
        this.columnKeys = new ArrayList();
        this.pkColumn = new ArrayList();
        for (int i = 0; i < keyFields.length; i++) {

            pkColumn.add(keyFields);
        }
        for (int i = 0; i < fieldCount; i++) {
            String columnKey = meta.getColumnName(i + 1).toLowerCase();
            this.columnKeys.add(columnKey);
        }
        while (rowSet.next()) {
            String rowKey = "";
            for (int i = 0; i < keyFields.length; i++) {
                String keyAdd = rowSet.getString(keyFields[i]);
                rowKey += keyAdd;
            }
            Map row = addRow(rowKey);
            for (int i = 0; i < columnKeys.size(); i++) {
                row.put(getColumnKey(i), rowSet.getObject(getColumnKey(i)));
            }
        }
    }

    public boolean isPrimaryKey(int index) {
        return this.pkColumn.contains(this.getColumnKey(index));
    }

    public int getColumnCount() {
        return this.columnKeys.size();
    }

    public void mergeWithXTable(XTable xTable) {
        for (int i = 0; i < xTable.getColumnCount(); i++) {
            String columnKey = xTable.getColumnKey(i);
            if (!existColumn(columnKey)) {
                this.addColumn(columnKey, xTable.getColumn(columnKey));
            }
        }
    }

    public void unionWithXTable(XTable xTable) {
        String[] rowKeys = xTable.getRowKeys();
        for (int i = 0; i < xTable.getRowCount(); i++) {
            Map row = xTable.getRow(0);
            String rowKey = rowKeys[i];
            rowKey = rowKey.substring(0, rowKey.length() - 1);
            Map rowAdd = getRow(rowKey);
            if (rowAdd == null) {
                rowAdd = this.addRow(rowKey);
            }
            for (int j = 0; j < this.getColumnCount(); j++) {
                if (!isPrimaryKey(j)) {
                    BigDecimal value = (BigDecimal) rowAdd.get(getColumnKey(j));
                    if (value == null) {
                        rowAdd.put(getColumnKey(j), row.get(getColumnKey(j)));
                    } else {
                    	value = value.add((BigDecimal) row.get(getColumnKey(j)));
						rowAdd.put(getColumnKey(j), value);
                    }
                }
            }
        }
    }

    public String toString() {
        String description = "\n" + this.getRowCount() + "\n";
        for (int i = 0; i < this.getRowCount(); i++) {
            Map row = this.getRow(i);
            for (int j = 0; j < this.columnKeys.size(); j++) {
                description += row.get(this.columnKeys.get(j)) + " ";
            }
            description += "\n";
        }
        description += "\n" + this.getColumnCount() + "\n";
        for (int i = 0; i < getColumnKeys().length; i++) {
            description += getColumnKeys()[i] + "\n";
        }
        return super.toString() + description;
    }
    public void resetRowKey(){
        
    }
}