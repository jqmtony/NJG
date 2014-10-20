package com.kingdee.eas.bpm.common;

public class TransRMB {

	/** 
	 * --------������ת�������Ľ��Ĵ�д��ʽ����ʾ��------------
	 *  ���� 123.45 --> Ҽ�۷�ʰ��Ԫ�������
	 *  TransRMB t2r = new TransRMB();
	 *  String s = t2r.cleanZero(t2r.splitNum(t2r.roundString("")));
	 */  

	
    /** 
     * �ж��û�����������Ƿ�Ϸ����û�ֻ���������������֣��������������ַ� 
     * @param s String 
     * @return ����û��������ݺϷ������� true�����򷵻� false 
     */  
    public boolean checkNum(String s) {  
        // ����û�����������з������ַ�������Ϊ�Ƿ����ݣ����� false  
        try {  
            float f = Float.valueOf(s);  
            // ��������С��������Ϊ�Ƿ����ݣ����� false  
            if(f < 0) {  
                System.out.println("�Ƿ����ݣ����飡");  
                return false;  
            }else {  
                return true;  
            }  
        } catch (NumberFormatException e) {  
            System.out.println("�Ƿ����ݣ����飡");  
            return false;  
        }     
    }  
      
    /** 
     * ���û����������С����Ϊ��ָ���������� numFormat() ���� 
     * ������Ӧ�����Ľ���д��ʽ��ת�� 
     * ע������������Ӧ���Ǿ��� roundString() ����������������������� 
     * @param s String 
     * @return ת���õ����Ľ���д��ʽ���ַ��� 
     */  
    public static String splitNum(String s) {  
        // ���������ǿմ���������ؿմ�  
        if("".equals(s)) {  
            return "";  
        }  
        // ��С����Ϊ��ָ�����ַ���  
        int index = s.indexOf(".");  
        // ��ȡ��ת�����������������  
        String intOnly = s.substring(0, index);  
        String part1 = numFormat(1, intOnly);  
        // ��ȡ��ת���������С������  
        String smallOnly = s.substring(index + 1);  
        String part2 = numFormat(2, smallOnly);  
        // ��ת�����˵��������ֺ�С����������ƴ��һ���µ��ַ���  
        String newS = part1 + part2;  
        return newS;  
    }  
          
    /** 
     * �Դ��������������������� 
     * ����ת���ɴ�д���
     * @param s String ��������������Ǹ��� 
     * @return ������������ֵ 
     */  
    public static String roundString(String s) {  
        // ���������ǿմ���������ؿմ�  
        if("".equals(s)) {  
            return "";  
        }  
        // �������ת���� double ���ͣ���������������������  
        double d = Double.parseDouble(s);  
        // �˲���������С�������λ��  
        d = (d * 100 + 0.5) / 100;  
        // �� d ���и�ʽ��  
        s = new java.text.DecimalFormat("##0.000").format(d);  
        // ��С����Ϊ��ָ�����ַ���  
        int index = s.indexOf(".");  
        // ���������������  
        String intOnly = s.substring(0, index);  
        // �涨��ֵ����󳤶�ֻ�ܵ����ڵ�λ�����򷵻� "0"  
        if(intOnly.length() > 13) {  
            System.out.println("�������ݹ��󣡣������������13λ����");  
            return "";  
        }  
        // �������С������  
        String smallOnly = s.substring(index + 1);  
        // ���С�����ִ�����λ��ֻ��ȡС�������λ  
        if(smallOnly.length() > 2) {  
            String roundSmall = smallOnly.substring(0, 2);  
            // ���������ֺ��½�ȡ��С����������ƴ������ַ���  
            s = intOnly + "." + roundSmall;  
        }  
        s = splitNum(s);
        s = cleanZero(s);
        return s;  
    }  
      
    /** 
     * �Ѵ������ת��Ϊ���Ľ���д��ʽ 
     * @param flag int ��־λ��1 ��ʾת���������֣�0 ��ʾת��С������ 
     * @param s String Ҫת�����ַ��� 
     * @return ת���õĴ���λ�����Ľ���д��ʽ 
     */  
    public static String numFormat(int flag, String s) {  
        int sLength = s.length();  
        // ���Ҵ�д��ʽ  
        String bigLetter[] = {"��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��"};  
        // ���ҵ�λ  
        String unit[] = {"Ԫ", "ʰ", "��", "Ǫ", "��",   
                // ʰ��λ��Ǫ��λ  
                "ʰ", "��", "Ǫ",  
                // ��λ������λ  
                "��", "ʰ", "��", "Ǫ", "��"};  
        String small[] = {"��", "��"};  
        // �������ת��������ַ���  
        String newS = "";  
        // ��λ�滻Ϊ���Ĵ�д��ʽ  
        for(int i = 0; i < sLength; i ++) {  
            if(flag == 1) {  
                // ת����������Ϊ���Ĵ�д��ʽ������λ��  
                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];  
            } else if(flag == 2) {  
                // ת��С�����֣�����λ��  
                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];  
            }  
        }  
        return newS;  
    }  
      
    /** 
     * ���Ѿ�ת���õ����Ľ���д��ʽ���ԸĽ������������ 
     * �������������㣬������ַ�����ø��ӿɹ� 
     * ע������������Ӧ���Ǿ��� splitNum() �������д���������� 
     * ����Ӧ���Ѿ��������Ľ���д��ʽ��ʾ�� 
     * @param s String �Ѿ�ת���õ��ַ��� 
     * @return �Ľ�����ַ��� 
     */  
    public static String cleanZero(String s) {  
        // ���������ǿմ���������ؿմ�  
        if("".equals(s)) {  
            return "";  
        }  
        // ����û���ʼ�����˺ܶ� 0 ȥ���ַ���ǰ������'��'��ʹ�俴��ȥ������ϰ��  
        while(s.charAt(0) == '��') {  
            // ���ַ����е� "��" ������Ӧ�ĵ�λȥ��  
            s = s.substring(2);  
            // ����û����������ʱ��ֻ������ 0����ֻ����һ�� "��"  
            if(s.length() == 0) {  
                return "��";  
            }  
        }  
        // �ַ����д��ڶ��'��'��һ���ʱ��ֻ����һ��'��'����ʡ�Զ���ĵ�λ  
        /* ���ڱ��˶��㷨���о�̫���ˣ�ֻ����4���������ʽȥת���ˣ���λ��Ϻ������... */  
        String regex1[] = {"��Ǫ", "���", "��ʰ"};  
        String regex2[] = {"����", "����", "��Ԫ"};  
        String regex3[] = {"��", "��", "Ԫ"};  
        String regex4[] = {"���", "���"};  
        // ��һ��ת���� "��Ǫ", ���","��ʰ"���ַ����滻��һ��"��"  
        for(int i = 0; i < 3; i ++) {  
            s = s.replaceAll(regex1[i], "��");  
        }  
        // �ڶ���ת������ "����","����","��Ԫ"�����  
        // "��","��","Ԫ"��Щ��λ��Щ����ǲ���ʡ�ģ���Ҫ��������  
        for(int i = 0; i < 3; i ++) {  
            // ����һ��ת�������п����кܶ�������һ��  
            // Ҫ�Ѻܶ���ظ�������һ����  
            s = s.replaceAll("������", "��");  
            s = s.replaceAll("����", "��");  
            s = s.replaceAll(regex2[i], regex3[i]);  
        }  
        // ������ת����"���","���"�ַ���ʡ��  
        for(int i = 0; i < 2; i ++) {  
            s = s.replaceAll(regex4[i], "");  
        }  
        // ��"��"��"��"֮��ȫ����"��"��ʱ�򣬺���"����"��λ��ֻ����һ��"��"  
        s = s.replaceAll("����", "��");  
        return s;  
    }  
}