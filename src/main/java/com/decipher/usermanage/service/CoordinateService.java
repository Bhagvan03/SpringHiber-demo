package com.decipher.usermanage.service;

import com.decipher.usermanage.entities.CoordinateDetails;

/**
 * Created by decipher19 on 15/4/17.
 */
public interface CoordinateService {
    Boolean saveOrUpdatePosition(CoordinateDetails coordinateDetails);
    CoordinateDetails getCoordinateDetailsById(Integer coordinateDetailsId);
}
