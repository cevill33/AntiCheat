package me.anticheat.ban;

import java.util.UUID;

/**
 * Created by Jakob on 21.03.2016.
 */
public class BanStats {

    private ExclusionType exclusionType;
    private Reason reasonType;
    private long end;
    private UUID banner;
    private String extrareason;

    public BanStats(ExclusionType exclusionType, Reason reasonType, long end, UUID banner, String extrareason) {
        this.exclusionType = exclusionType;
        this.reasonType = reasonType;
        this.end = end;
        this.banner = banner;
        this.extrareason = extrareason;
    }

    public ExclusionType getExclusionType() {
        return exclusionType;
    }

    public void setExclusionType(ExclusionType exclusionType) {
        this.exclusionType = exclusionType;
    }

    public Reason getReasonType() {
        return reasonType;
    }

    public void setReasonType(Reason reasonType) {
        this.reasonType = reasonType;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public UUID getBanner() {
        return banner;
    }

    public void setBanner(UUID banner) {
        this.banner = banner;
    }

    public String getExtraReason() {
        return this.extrareason;
    }

    public void setExtraReason(String extrareason) {
        this.extrareason = extrareason;
    }
}
