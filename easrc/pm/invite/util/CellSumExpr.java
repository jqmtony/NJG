package com.kingdee.eas.port.pm.invite.util;

import com.kingdee.bos.ctrl.kdf.table.ICell;

public class CellSumExpr {
	/**
     * 给单元格添加sum表达式
     * @param cell
     * @param from
     * @param to
     */
    public static void setCellSumExpr(ICell cell, int from, int to) {
        cell.setExpressions(getSumExpr(from, to));
    }
    
    /**
     * 给单元格添加IF表达式
     * @param cell
     * @param condExpr
     * @param expr1
     * @param expr2
     */
    public static void setCellIFExpr(ICell cell,String condExpr,String expr1,String expr2){
        cell.setExpressions(getIFExpr(condExpr, expr1, expr2));
    }
    
    /**
     * 给单元格添加Add表达式
     * @param cell
     * @param a
     * @param b
     */
    public static void setCellAddExpr(ICell cell,int a,int b){
        cell.setExpressions(getAddExpr(a, b));
    }
    
    /**
     * 给单元格添加Add表达式，允许不连续区域求和
     * @param cell
     * @param range
     */
    public static void setCellAddRangeExpr(ICell cell,int[] range){
        cell.setExpressions(getAddRangeExpr(range));
    }
    
    /**
     * 给单元格添加Substract表达式
     * @param cell
     * @param a
     * @param b
     */
    public static void setCellSubExpr(ICell cell,int a,int b){
        cell.setExpressions(getSubExpr(a, b));
    }

    // return =sum(from:to);
    public static String getSumExpr(int from, int to) {
        StringBuffer buff = new StringBuffer();
        buff.append("=SUM(").append(getExcelColumnLabel(from));
        buff.append(":").append(getExcelColumnLabel(to));
        buff.append(")");
        return buff.toString().intern();
    }

    // return =a+b
    public static String getAddExpr(int a, int b) {
        StringBuffer buff = new StringBuffer();
        buff.append("=").append(getExcelColumnLabel(a));
        buff.append("+").append(getExcelColumnLabel(b));
        return buff.toString().intern();
    }

    // return =range[0]+range[1]+...+range[n]
    public static String getAddRangeExpr(int[] range) {
        StringBuffer buff = new StringBuffer();
        buff.append("=");

        boolean flag = false;
        for (int i = 0, n = range.length; i < n; i++) {
            if (flag) {
                buff.append("+");
            }
            buff.append(getExcelColumnLabel(range[i]));
            flag = true;
        }
        return buff.toString().intern();
    }

    // return =a-b
    public static String getSubExpr(int a, int b) {
        StringBuffer buff = new StringBuffer();
        buff.append("=").append(getExcelColumnLabel(a));
        buff.append("-").append(getExcelColumnLabel(b));
        return buff.toString().intern();
    }

    // retur =IF(condExpr,expr1,expr2);
    public static String getIFExpr(String condExpr, String expr1, String expr2) {
        StringBuffer buff = new StringBuffer();
        buff.append("=IF(").append(condExpr).append(",");
        buff.append(expr1).append(",").append(expr2);
        buff.append(")");
        return buff.toString();
    }

    /** 根据列的的位置获取列标，如A、AA、AB...采用类似进制转换的算法 */
    public static String getExcelColumnLabel(int colCount) {
        String rs = "";
        do {
            colCount--;
            rs = ((char) (colCount % 26 + (int) 'A')) + rs;
            colCount = (int) ((colCount - colCount % 26) / 26);
        } while (colCount > 0);
        return rs;
    }

    /** 根据列标获取列的索引，采用类似进制转换的算法 */
    public static int getExcelColumnIndex(String colName) {
        if (colName == null || colName.equals("")) {
            return -1;
        }
        colName = colName.toUpperCase();

        int count = -1;
        char[] cs = colName.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }
}
