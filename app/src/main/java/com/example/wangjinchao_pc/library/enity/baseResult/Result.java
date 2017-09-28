package com.example.wangjinchao_pc.library.enity.baseResult;


public class Result {
    int code;
    String message;
    String openId;
    String sessionId;

    //用户信息
    String userId;
    String userName;
    //分页项
    int totalCount;
    int currentPage;

    //获取评论列表
    //List<Talk> result;
    //提交评论
    long commentId;

    //是否推荐
    int count;

    //身份列表
    //List<Identity> identList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

//    public List<Talk> getResult() {
//        return result;
//    }

//    public void setResult(List<Talk> result) {
//        this.result = result;
//    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

//    public List<Identity> getIdentList() {
//        return identList;
//    }

//    public void setIdentList(List<Identity> identList) {
//        this.identList = identList;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
