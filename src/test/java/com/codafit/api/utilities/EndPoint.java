package com.codafit.api.utilities;

public enum EndPoint{
    candidateAPI("/api/candidate"),
    Others ("");
    public void triggerAPI(){
        switch (this){
            case candidateAPI:
                break;
            case Others:
                System.out.println("End url is missing");
                break;
            default:
                break;

        }
    }
    private String text;
    EndPoint(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
}
