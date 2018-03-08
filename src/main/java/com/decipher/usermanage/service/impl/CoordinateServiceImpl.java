package com.decipher.usermanage.service.impl;

import com.decipher.usermanage.dao.CoordinateDao;
import com.decipher.usermanage.entities.CoordinateDetails;
import com.decipher.usermanage.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by decipher19 on 15/4/17.
 */
@Service
public class CoordinateServiceImpl implements CoordinateService {

    @Autowired
    private CoordinateDao coordinateDao;
    @Override
    public Boolean saveOrUpdatePosition(CoordinateDetails coordinateDetails) {
        return coordinateDao.saveOrUpdatePosition(coordinateDetails);
    }

    @Override
    public CoordinateDetails getCoordinateDetailsById(Integer coordinateDetailsId) {
        return coordinateDao.getCoordinateDetailsById(coordinateDetailsId);
    }
}
