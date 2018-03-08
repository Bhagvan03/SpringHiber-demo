package com.decipher.usermanage.dao;

import com.decipher.usermanage.entities.CoordinateDetails;

/**
 * Created by decipher19 on 15/4/17.
 */
public interface CoordinateDao {
    Boolean saveOrUpdatePosition(CoordinateDetails coordinateDetails);
    CoordinateDetails getCoordinateDetailsById(Integer coordinateDetailsId);
}
