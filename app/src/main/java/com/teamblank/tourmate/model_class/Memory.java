package com.teamblank.tourmate.model_class;

public class Memory {
    String userID,memoryTitle,memoryDetails,memoryImage;

    public Memory() {
    }

    public Memory(String userID, String memoryTitle, String memoryDetails, String memoryImage) {
        this.userID = userID;
        this.memoryTitle = memoryTitle;
        this.memoryDetails = memoryDetails;
        this.memoryImage = memoryImage;
    }

    public String getUserID() {
        return userID;
    }

    public String getMemoryTitle() {
        return memoryTitle;
    }

    public String getMemoryDetails() {
        return memoryDetails;
    }

    public String getMemoryImage() {
        return memoryImage;
    }
}
