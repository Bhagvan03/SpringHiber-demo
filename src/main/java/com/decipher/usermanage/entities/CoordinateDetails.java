package com.decipher.usermanage.entities;

import javax.persistence.*;

/**
 * Created by decipher19 on 14/4/17.
 */
@Entity
@Table(name = "COORDINATE_DETAILS")
public class CoordinateDetails {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COORDINATE_DETAILS_ID",unique = true)
    private Integer coordinateDetailsId;
    @Column(name = "LEFT_POSITION")
    private String leftPosition;
    @Column(name = "TOP_POSITION")
    private String topPosition;

    public Integer getCoordinateDetailsId() {
        return coordinateDetailsId;
    }

    public void setCoordinateDetailsId(Integer coordinateDetailsId) {
        this.coordinateDetailsId = coordinateDetailsId;
    }

    public String getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(String leftPosition) {
        this.leftPosition = leftPosition;
    }

    public String getTopPosition() {
        return topPosition;
    }

    public void setTopPosition(String topPosition) {
        this.topPosition = topPosition;
    }
}