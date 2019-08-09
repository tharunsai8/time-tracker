package com.yurwar.trainingcourse.model.dao;

import com.yurwar.trainingcourse.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao(DaoConnection connection);

    public abstract ActivityDao createActivityDao(DaoConnection connection);

    public abstract ActivityRequestDao createActivityRequestDao(DaoConnection connection);

    public abstract DaoConnection getConnection();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
