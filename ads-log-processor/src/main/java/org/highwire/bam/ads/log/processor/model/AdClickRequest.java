package org.highwire.bam.ads.log.processor.model;

public class AdClickRequest {
    private int ad;
    private boolean adclick;
    private String url;

    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    public boolean isAdclick() {
        return adclick;
    }

    public void setAdclick(boolean adclick) {
        this.adclick = adclick;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}