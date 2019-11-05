package com.learn.model.SA;

import java.io.Serializable;

/**
 * @author dshuyou
 * @date 2019/11/4 17:35
 */
public class VIEW_MAP_TK_BJINFO implements Serializable {
    //BJNO
    private String bino;
    //BSNUM
    private String bsnum;
    //项目名称
    private String 项目名称;
    //申请人
    private String 申请人;
    //矿山名称
    private String 矿山名称;
    //总面积
    private String 总面积;
    //原许可证号
    private String 原许可证号;
    //行政区代码
    private String 行政区代码 ;
    //区域范围
    private String 区域范围 ;
    //行政区名称
    private String 行政区名称;

    public String getBino() {
        return bino;
    }

    public void setBino(String bino) {
        this.bino = bino;
    }

    public String getBsnum() {
        return bsnum;
    }

    public void setBsnum(String bsnum) {
        this.bsnum = bsnum;
    }

    public String get项目名称() {
        return 项目名称;
    }

    public void set项目名称(String 项目名称) {
        this.项目名称 = 项目名称;
    }

    public String get申请人() {
        return 申请人;
    }

    public void set申请人(String 申请人) {
        this.申请人 = 申请人;
    }

    public String get矿山名称() {
        return 矿山名称;
    }

    public void set矿山名称(String 矿山名称) {
        this.矿山名称 = 矿山名称;
    }

    public String get总面积() {
        return 总面积;
    }

    public void set总面积(String 总面积) {
        this.总面积 = 总面积;
    }

    public String get原许可证号() {
        return 原许可证号;
    }

    public void set原许可证号(String 原许可证号) {
        this.原许可证号 = 原许可证号;
    }

    public String get行政区代码() {
        return 行政区代码;
    }

    public void set行政区代码(String 行政区代码) {
        this.行政区代码 = 行政区代码;
    }

    public String get区域范围() {
        return 区域范围;
    }

    public void set区域范围(String 区域范围) {
        this.区域范围 = 区域范围;
    }

    public String get行政区名称() {
        return 行政区名称;
    }

    public void set行政区名称(String 行政区名称) {
        this.行政区名称 = 行政区名称;
    }

    @Override
    public String toString() {
        return "VIEW_MAP_TK_BJINFO{" +
                "bino='" + bino + '\'' +
                ", bsnum='" + bsnum + '\'' +
                ", 项目名称='" + 项目名称 + '\'' +
                ", 申请人='" + 申请人 + '\'' +
                ", 矿山名称='" + 矿山名称 + '\'' +
                ", 总面积='" + 总面积 + '\'' +
                ", 原许可证号='" + 原许可证号 + '\'' +
                ", 行政区代码='" + 行政区代码 + '\'' +
                ", 区域范围='" + 区域范围 + '\'' +
                ", 行政区名称='" + 行政区名称 + '\'' +
                '}';
    }
}
