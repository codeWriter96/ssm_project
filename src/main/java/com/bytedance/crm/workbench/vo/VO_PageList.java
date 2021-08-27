package com.bytedance.crm.workbench.vo;

public class VO_PageList {
    private String pageNo;
    private String pageSize;
    private String name;
	private String owner;
	private String startDate;
	private String endDate;
	private int skipCount;
    private int pageSizeInt;

    public int getPageSizeInt() {
        return pageSizeInt;
    }

    public void setPageSizeInt(int pageSizeInt) {
        this.pageSizeInt = pageSizeInt;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public VO_PageList() {
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
