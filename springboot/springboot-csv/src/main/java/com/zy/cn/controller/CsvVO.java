package com.zy.cn.controller;

/**
 * @program: application
 * @description:
 * @author: 狗蛋
 * @create: 2019-11-20 21:05
 */
public class CsvVO {

    /***
     * csv文件名
     */
    private String csvFileName;
    /***
     * base64编码串
     */
    private String csvBase64String;

    public CsvVO(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public CsvVO(String csvFileName, String csvBase64String) {
        this.csvFileName = csvFileName;
        this.csvBase64String = csvBase64String;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String getCsvBase64String() {
        return csvBase64String;
    }

    public void setCsvBase64String(String csvBase64String) {
        this.csvBase64String = csvBase64String;
    }
}