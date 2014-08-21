/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.snapshot.dto;

import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Daniel Bernstein Date: 7/28/14
 */
public class SnapshotSummary {

    @XmlValue
    private String snapshotId;
    @XmlValue
    private String description;

    @XmlValue
    private SnapshotStatus status;
    
    public SnapshotSummary() {}
    
    public SnapshotSummary(String snapshotId, SnapshotStatus status, String description) {
        super();
        this.snapshotId = snapshotId;
        this.description = description;
        this.status = status;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SnapshotStatus getStatus() {
        return status;
    }

    public void setStatus(SnapshotStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).build();
    }
}
