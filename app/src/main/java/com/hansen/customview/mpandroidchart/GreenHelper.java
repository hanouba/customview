package com.hansen.customview.mpandroidchart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hansen.customview.BaseApp;
import com.hansen.customview.greendao.DaoMaster;
import com.hansen.customview.greendao.DaoSession;
import com.hansen.customview.greendao.VideoDateInfoBeanDao;

import java.util.List;

/**
 * @author HanN on 2020/6/18 11:04
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/6/18 11:04
 * @updateremark:
 * @version: 2.1.67
 */
public class GreenHelper {
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    private final VideoDateInfoBeanDao videoDateInfoBeanDao;

    public static GreenHelper getInstance() {
        return GreenHelper.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static GreenHelper instance = new GreenHelper();
    }

    public GreenHelper() {

        setupDatabase();
        videoDateInfoBeanDao = mDaoSession.getVideoDateInfoBeanDao();

    }

    private void setupDatabase() {
        //创建数据库,DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApp.getInstance(),"testVideo.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象,DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者,DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    public void insertVideoInfo(String date,String data ,String areaid,String areaName) {
        videoDateInfoBeanDao.insert(new VideoDateInfoBean(areaName,data,date));
    }

    public void clearDb() {
        videoDateInfoBeanDao.deleteAll();
    }

    public  List<VideoDateInfoBean> getSameDateData(String areaDate) {
        List<VideoDateInfoBean> list = videoDateInfoBeanDao.queryBuilder().where(VideoDateInfoBeanDao.Properties.AreaDate.eq(areaDate)).list();

        return list;
    }
}
