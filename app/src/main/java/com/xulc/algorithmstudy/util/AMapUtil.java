package com.xulc.algorithmstudy.util;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.xulc.algorithmstudy.MyApplication;

/**
 * 高德地图工具类
 * Created by xulc on 2018/1/14.
 */
public class AMapUtil {
    private static AMapUtil util;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    private AMapLocationListener mLocationListener;

    //设置定位回调监听
    public static AMapUtil getInstance() {
        if (util == null) {
            synchronized (AMapUtil.class) {
                if (util == null) {
                    util = new AMapUtil();
                }
            }
        }
        return util;
    }


    /**
     * 获取当前的位置，单次定位
     * @param listener 定位结果回调
     */
    public void getCurrentLocation(AMapLocationListener listener) {
        this.mLocationListener = listener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(MyApplication.getContext());
            //初始化AMapLocationClientOption对象
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

            /**
             * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
             * 我们这定位默认无场景即可，所以注释掉
             */
//            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
//            if(null != mLocationClient){
//                mLocationClient.setLocationOption(mLocationOption);
//                //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//                mLocationClient.stopLocation();
//                mLocationClient.startLocation();
//            }
            /**
             * 精度方面我们使用高精度
             */
            // 设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            //高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//            //不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//            //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//            //不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            /**
             * 我们采取获取一次定位结果
             */
            //获取一次定位结果，默认为false。
            mLocationOption.setOnceLocation(true);
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);
            /**
             * 因为是一次定位结果，所以定位间隔也不会生效
             */
//            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//            mLocationOption.setInterval(2000);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否允许模拟软件Mock位置结果，多为模拟GPS定位结果，默认为true，允许模拟位置。
            mLocationOption.setMockEnable(true);
            //设置定位请求超时时间，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(30000);
            /**
             * 缓存的话我们就不需要了
             */
            //设置是否开启定位缓存机制
            //缓存机制默认开启，可以通过以下接口进行关闭。
            //当开启定位缓存功能，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存，不区分单次定位还是连续定位。GPS定位结果不会被缓存。
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //设置定位回调监听
        }
        mLocationClient.setLocationListener(mLocationListener);

        //启动定位
        mLocationClient.startLocation();

    }
    /**
     * 定位完成后取消注册监听
     */
    public void removeListener(){
        mLocationClient.unRegisterLocationListener(mLocationListener);

    }

    /**
     * 单次的定位模式下，一般我们不需要调用该方法
     */
    public void stopLocation(){
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    /**
     * 单次的定位模式下，一般我们不需要调用该方法
     */
    public void destoryLocationClient(){
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

}
