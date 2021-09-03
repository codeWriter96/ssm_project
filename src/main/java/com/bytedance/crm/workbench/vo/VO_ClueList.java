package com.bytedance.crm.workbench.vo;

public class VO_ClueList {
    private String fullname;	//全名（人的名字）
    private String owner;	//所有者
    private String company;	//公司名称
    private String phone;	//公司电话
    private String mphone;	//手机
    private String source;	//来源
    private String state;	//状态
    private int skipCount;
    private int pageSizeInt;
    private String pageNo;
    private String pageSize;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public int getPageSizeInt() {
        return pageSizeInt;
    }

    public void setPageSizeInt(int pageSizeInt) {
        this.pageSizeInt = pageSizeInt;
    }

    public VO_ClueList() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
