package com.decipher.usermanage.util;

public class JsonResponse {
	public static final String RESULT_SUCCESS = "success";
	public static final String RESULT_FAILED = "failed";
	public static final String RESULT_ALREADY_EXISTS = "Already exists";
	public static final String RESULT_NOT_EXISTS = "Not exists";
	public static final String RESULT_INVALID_USER_NOT_EXISTS = "invalid user";
        private String status = null;
        private Object resultObject = null;
        private Boolean resultStatus = false;
        public String getStatus() {
               return status;
	        }
        public void setStatus(String status) {
	                this.status = status;
        }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public Boolean getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Boolean resultStatus) {
        this.resultStatus = resultStatus;
    }

    @Override
    public String toString(){

        return "{\"status\":\""+status+"\",\"result\":"+resultObject.toString()+"}";

    }
}
