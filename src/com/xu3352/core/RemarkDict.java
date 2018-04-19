package com.xu3352.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 备注实体字典
 *
 * @author Yinglong Xu
 * @date 2018-04-18
 *
 * 业余爱好:[1.美食 2.K歌 3.逛街 4.游戏 5.旅游 6.运动]
 * title:[k.v k.v k.v]
 * <p>
 * <select name="hobby" data-value="<%=data == null ? "0" : data.getHobby() %>">
 *     <option value="0">-选择业余爱好-</option>
 *     <option value="1">美食</option>
 *     <option value="2">K歌</option>
 *     <option value="3">逛街</option>
 *     <option value="4">游戏</option>
 *     <option value="5">旅游</option>
 *     <option value="6">运动</option>
 * </select>
 * </p>
 */
public class RemarkDict {

    private String                    title;    // 备注标题
    private List<Map<String, String>> list;     // k-v列表

    public RemarkDict() {
    }

    public RemarkDict(String remark) {
        this.title = remark;
        this.parse();
    }

    // 按结构解析
    private void parse() {
        String[] as = title.split(":");
        if (as.length <= 1) return;

        this.title = as[0].trim();
        String strs = as[1].trim();
        if (strs.startsWith("[") && strs.endsWith("]")) {
            strs = strs.substring(1, strs.length() - 1);
            String[] kvs = strs.split("\\s+");

            this.list = new ArrayList<Map<String, String>>();
            for (String kv : kvs) {
                if (kv.contains(".")) {
                    String[] ss = kv.split("\\.");
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("k", ss[0]);
                    map.put("v", ss[1]);
                    this.list.add(map);
                }
            }
        }
    }

    /**
     * key数组长度
     */
    public int getSize() {
        if (this.list == null) return 0;
        return this.list.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RemarkDict{" +
                "title='" + title + '\'' +
                ", list=" + list +
                '}';
    }

    public static void main(String[] args) {
        String txt = "业余爱好:[1.美食 2.K歌 3.逛街 4.游戏 5.旅游 6.运动]";
        RemarkDict dict = new RemarkDict(txt);
        System.out.println(dict);
        System.out.println(dict.getSize());
    }
}
