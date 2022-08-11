package com.arcade.exception;

public class UserErrorResponse {

    private int status;
    private String message;
    private long timeStamp;

    public UserErrorResponse() {

    }

    /**
     * @param status    HTTP status value
     * @param message   Custom message (e.g., User with ID 5 not found)
     * @param timeStamp Time in Epoch (nr. of seconds from midnight 1/1/1970 until now)
     */
    public UserErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
