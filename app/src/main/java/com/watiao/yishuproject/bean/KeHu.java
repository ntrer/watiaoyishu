package com.watiao.yishuproject.bean;

import java.io.Serializable;
import java.util.List;

public class KeHu implements Serializable {

    /**
     * ret : 200
     * msg : success
     * error : null
     * data : {"customerList":[{"birthdayNum":344,"brthdayDate":"2019-05-01","cellAddress":"","createTime":"2019-05-22 15:45:57","decorationStyle":"","del":1,"enable":1,"id":3,"intentionalProduct":"","lastVisitTime":"2019-05-16 17:11:58","localHeadPortraitImage":1,"merchantId":1,"mobileNumber":"18911647193","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXr3","sex":1,"signingNum":5,"source":0,"sourceDesc":"来自客户主动搜索","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚3","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚3","wxNum":""},{"birthdayNum":315,"brthdayDate":"2019-04-02","cellAddress":"","createTime":"2019-05-22 15:46:13","decorationStyle":"","del":1,"enable":1,"id":5,"intentionalProduct":"","lastVisitTime":"2019-05-16 17:11:58","localHeadPortraitImage":13,"merchantId":1,"mobileNumber":"18911647195","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc5","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自活动分发","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚5","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚5","wxNum":""},{"birthdayNum":6,"brthdayDate":"2019-05-28","cellAddress":"","createTime":"2019-05-22 15:46:15","decorationStyle":"","del":1,"enable":1,"id":6,"intentionalProduct":"","lastVisitTime":"2019-05-16 18:10:59","localHeadPortraitImage":11,"merchantId":1,"mobileNumber":"18911647196","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc6","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自分享文章","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚6","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚6","wxNum":""},{"birthdayNum":-1,"brthdayDate":"","cellAddress":"","createTime":"2019-05-22 10:44:09","decorationStyle":"","del":1,"enable":1,"id":7,"intentionalProduct":"","lastVisitTime":"2019-05-16 18:10:59","localHeadPortraitImage":12,"merchantId":1,"mobileNumber":"18911647197","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc7","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自* 就像避免采到花的老虎*转发","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚7","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚7","wxNum":""},{"birthdayNum":-1,"brthdayDate":"","cellAddress":"","createTime":"2019-05-22 10:44:02","decorationStyle":"","del":1,"enable":1,"id":4,"intentionalProduct":"","lastVisitTime":"2019-05-16 18:10:59","localHeadPortraitImage":2,"merchantId":1,"mobileNumber":"18911647194","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc4","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自客户名片扫码","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚4","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚4","wxNum":""}],"intmaxCount":5,"intcurrentPage":1,"intpageSize":10,"intmaxPage":1}
     */

    private String ret;
    private String msg;
    private Object error;
    private DataBean data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * customerList : [{"birthdayNum":344,"brthdayDate":"2019-05-01","cellAddress":"","createTime":"2019-05-22 15:45:57","decorationStyle":"","del":1,"enable":1,"id":3,"intentionalProduct":"","lastVisitTime":"2019-05-16 17:11:58","localHeadPortraitImage":1,"merchantId":1,"mobileNumber":"18911647193","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXr3","sex":1,"signingNum":5,"source":0,"sourceDesc":"来自客户主动搜索","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚3","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚3","wxNum":""},{"birthdayNum":315,"brthdayDate":"2019-04-02","cellAddress":"","createTime":"2019-05-22 15:46:13","decorationStyle":"","del":1,"enable":1,"id":5,"intentionalProduct":"","lastVisitTime":"2019-05-16 17:11:58","localHeadPortraitImage":13,"merchantId":1,"mobileNumber":"18911647195","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc5","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自活动分发","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚5","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚5","wxNum":""},{"birthdayNum":6,"brthdayDate":"2019-05-28","cellAddress":"","createTime":"2019-05-22 15:46:15","decorationStyle":"","del":1,"enable":1,"id":6,"intentionalProduct":"","lastVisitTime":"2019-05-16 18:10:59","localHeadPortraitImage":11,"merchantId":1,"mobileNumber":"18911647196","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc6","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自分享文章","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚6","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚6","wxNum":""},{"birthdayNum":-1,"brthdayDate":"","cellAddress":"","createTime":"2019-05-22 10:44:09","decorationStyle":"","del":1,"enable":1,"id":7,"intentionalProduct":"","lastVisitTime":"2019-05-16 18:10:59","localHeadPortraitImage":12,"merchantId":1,"mobileNumber":"18911647197","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc7","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自* 就像避免采到花的老虎*转发","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚7","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚7","wxNum":""},{"birthdayNum":-1,"brthdayDate":"","cellAddress":"","createTime":"2019-05-22 10:44:02","decorationStyle":"","del":1,"enable":1,"id":4,"intentionalProduct":"","lastVisitTime":"2019-05-16 18:10:59","localHeadPortraitImage":2,"merchantId":1,"mobileNumber":"18911647194","openId":"ouUnI5SeHmSVW15DbmQl3P_IiXrc4","sex":1,"signingNum":1,"source":0,"sourceDesc":"来自客户名片扫码","tokenUuid":"","updateTime":"2019-05-20 17:02:27","userId":2,"userName":"老和尚4","visitNum":1,"wxHeadPortraitImageUrl":"","wxNickName":"老和尚4","wxNum":""}]
         * intmaxCount : 5
         * intcurrentPage : 1
         * intpageSize : 10
         * intmaxPage : 1
         */

        private int intmaxCount;
        private int intcurrentPage;
        private int intpageSize;
        private int intmaxPage;
        private List<CustomerListBean> customerList;

        public int getIntmaxCount() {
            return intmaxCount;
        }

        public void setIntmaxCount(int intmaxCount) {
            this.intmaxCount = intmaxCount;
        }

        public int getIntcurrentPage() {
            return intcurrentPage;
        }

        public void setIntcurrentPage(int intcurrentPage) {
            this.intcurrentPage = intcurrentPage;
        }

        public int getIntpageSize() {
            return intpageSize;
        }

        public void setIntpageSize(int intpageSize) {
            this.intpageSize = intpageSize;
        }

        public int getIntmaxPage() {
            return intmaxPage;
        }

        public void setIntmaxPage(int intmaxPage) {
            this.intmaxPage = intmaxPage;
        }

        public List<CustomerListBean> getCustomerList() {
            return customerList;
        }

        public void setCustomerList(List<CustomerListBean> customerList) {
            this.customerList = customerList;
        }

        public static class CustomerListBean implements Serializable {
            /**
             * birthdayNum : 344
             * brthdayDate : 2019-05-01
             * cellAddress :
             * createTime : 2019-05-22 15:45:57
             * decorationStyle :
             * del : 1
             * enable : 1
             * id : 3
             * intentionalProduct :
             * lastVisitTime : 2019-05-16 17:11:58
             * localHeadPortraitImage : 1
             * merchantId : 1
             * mobileNumber : 18911647193
             * openId : ouUnI5SeHmSVW15DbmQl3P_IiXr3
             * sex : 1
             * signingNum : 5
             * source : 0
             * sourceDesc : 来自客户主动搜索
             * tokenUuid :
             * updateTime : 2019-05-20 17:02:27
             * userId : 2
             * userName : 老和尚3
             * visitNum : 1
             * wxHeadPortraitImageUrl :
             * wxNickName : 老和尚3
             * wxNum :
             */

            private int birthdayNum;
            private String brthdayDate;
            private String cellAddress;
            private String createTime;
            private String decorationStyle;
            private int del;
            private int enable;
            private int id;
            private String intentionalProduct;
            private String lastVisitTime;
            private int localHeadPortraitImage;
            private int merchantId;
            private String mobileNumber;
            private String openId;
            private int sex;
            private int signingNum;
            private int source;
            private String sourceDesc;
            private String tokenUuid;
            private String updateTime;
            private int userId;
            private String userName;
            private int visitNum;
            private String wxHeadPortraitImageUrl;
            private String wxNickName;
            private String wxNum;
            public int getBirthdayNum() {
                return birthdayNum;
            }

            public void setBirthdayNum(int birthdayNum) {
                this.birthdayNum = birthdayNum;
            }

            public String getBrthdayDate() {
                return brthdayDate;
            }

            public void setBrthdayDate(String brthdayDate) {
                this.brthdayDate = brthdayDate;
            }

            public String getCellAddress() {
                return cellAddress;
            }

            public void setCellAddress(String cellAddress) {
                this.cellAddress = cellAddress;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDecorationStyle() {
                return decorationStyle;
            }

            public void setDecorationStyle(String decorationStyle) {
                this.decorationStyle = decorationStyle;
            }

            public int getDel() {
                return del;
            }

            public void setDel(int del) {
                this.del = del;
            }

            public int getEnable() {
                return enable;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntentionalProduct() {
                return intentionalProduct;
            }

            public void setIntentionalProduct(String intentionalProduct) {
                this.intentionalProduct = intentionalProduct;
            }

            public String getLastVisitTime() {
                return lastVisitTime;
            }

            public void setLastVisitTime(String lastVisitTime) {
                this.lastVisitTime = lastVisitTime;
            }

            public int getLocalHeadPortraitImage() {
                return localHeadPortraitImage;
            }

            public void setLocalHeadPortraitImage(int localHeadPortraitImage) {
                this.localHeadPortraitImage = localHeadPortraitImage;
            }

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getSigningNum() {
                return signingNum;
            }

            public void setSigningNum(int signingNum) {
                this.signingNum = signingNum;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public String getSourceDesc() {
                return sourceDesc;
            }

            public void setSourceDesc(String sourceDesc) {
                this.sourceDesc = sourceDesc;
            }

            public String getTokenUuid() {
                return tokenUuid;
            }

            public void setTokenUuid(String tokenUuid) {
                this.tokenUuid = tokenUuid;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getVisitNum() {
                return visitNum;
            }

            public void setVisitNum(int visitNum) {
                this.visitNum = visitNum;
            }

            public String getWxHeadPortraitImageUrl() {
                return wxHeadPortraitImageUrl;
            }

            public void setWxHeadPortraitImageUrl(String wxHeadPortraitImageUrl) {
                this.wxHeadPortraitImageUrl = wxHeadPortraitImageUrl;
            }

            public String getWxNickName() {
                return wxNickName;
            }

            public void setWxNickName(String wxNickName) {
                this.wxNickName = wxNickName;
            }

            public String getWxNum() {
                return wxNum;
            }

            public void setWxNum(String wxNum) {
                this.wxNum = wxNum;
            }
        }
    }
}
