package com.iis.soft.models;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class DepWorker {
    private int id;
    private String depCode;
    private String depJob;
    private String depDescription;

    public DepWorker(String depCode, String depJob, String depDescription) {
        this.depCode = depCode;
        this.depJob = depJob;
        this.depDescription = depDescription;
    }

    public String getDepCode() {
        return depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public String getDepDescription() {
        return depDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepWorker)) return false;

        DepWorker depWorker = (DepWorker) o;

        if (depCode != null ? !depCode.equals(depWorker.depCode) : depWorker.depCode != null) return false;
        return depJob != null ? depJob.equals(depWorker.depJob) : depWorker.depJob == null;
    }

    @Override
    public int hashCode() {
        int result = depCode != null ? depCode.hashCode() : 0;
        result = 31 * result + (depJob != null ? depJob.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DepWorker{" +
                "id=" + id +
                ", depCode='" + depCode + '\'' +
                ", depJob='" + depJob + '\'' +
                ", depDescription='" + depDescription + '\'' +
                '}';
    }
}
