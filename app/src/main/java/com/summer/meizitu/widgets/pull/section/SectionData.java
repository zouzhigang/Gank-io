package com.summer.meizitu.widgets.pull.section;


public class SectionData<T> {
    public boolean isHeader;
    public int headerIndex;//用于索引ABC...的index定位
    public T t;
    public String header;

    public SectionData(boolean isHeader, int headerIndex, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.headerIndex = headerIndex;
        this.t = null;
    }

    public SectionData(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }
}
