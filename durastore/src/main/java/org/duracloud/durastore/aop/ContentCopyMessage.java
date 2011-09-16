/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.durastore.aop;

public class ContentCopyMessage {

    private String storeId;
    private String sourceSpaceId;
    private String sourceContentId;
    private String destSpaceId;
    private String destContentId;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ContentCopyMessage[");
        sb.append("storeId:'" + storeId + "'");
        sb.append("|sourceSpaceId:'" + sourceSpaceId + "'");
        sb.append("|sourceContentId:'" + sourceContentId + "'");
        sb.append("|destSpaceId:'" + destSpaceId + "'");
        sb.append("|destContentId:'" + destContentId + "'");
        sb.append("]\n");
        return sb.toString();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSourceSpaceId() {
        return sourceSpaceId;
    }

    public void setSourceSpaceId(String sourceSpaceId) {
        this.sourceSpaceId = sourceSpaceId;
    }

    public String getSourceContentId() {
        return sourceContentId;
    }

    public void setSourceContentId(String sourceContentId) {
        this.sourceContentId = sourceContentId;
    }

    public String getDestSpaceId() {
        return destSpaceId;
    }

    public void setDestSpaceId(String destSpaceId) {
        this.destSpaceId = destSpaceId;
    }

    public String getDestContentId() {
        return destContentId;
    }

    public void setDestContentId(String destContentId) {
        this.destContentId = destContentId;
    }

    /*
     * Generated by IntelliJ
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContentCopyMessage that = (ContentCopyMessage) o;

        if (destContentId != null ? !destContentId.equals(that.destContentId) :
            that.destContentId != null) {
            return false;
        }
        if (destSpaceId != null ? !destSpaceId.equals(that.destSpaceId) :
            that.destSpaceId != null) {
            return false;
        }
        if (sourceContentId != null ? !sourceContentId
            .equals(that.sourceContentId) : that.sourceContentId != null) {
            return false;
        }
        if (sourceSpaceId != null ? !sourceSpaceId.equals(that.sourceSpaceId) :
            that.sourceSpaceId != null) {
            return false;
        }
        if (storeId != null ? !storeId.equals(that.storeId) :
            that.storeId != null) {
            return false;
        }

        return true;
    }

    /*
     * Generated by IntelliJ
     */
    @Override
    public int hashCode() {
        int result = storeId != null ? storeId.hashCode() : 0;
        result = 31 * result +
            (sourceSpaceId != null ? sourceSpaceId.hashCode() : 0);
        result = 31 * result +
            (sourceContentId != null ? sourceContentId.hashCode() : 0);
        result =
            31 * result + (destSpaceId != null ? destSpaceId.hashCode() : 0);
        result = 31 * result +
            (destContentId != null ? destContentId.hashCode() : 0);
        return result;
    }
}