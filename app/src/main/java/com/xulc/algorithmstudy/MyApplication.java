package com.xulc.algorithmstudy;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Date：2017/12/13
 * Desc：
 * Created by xuliangchun.
 */

public class MyApplication extends TinkerApplication {


    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.xulc.algorithmstudy.MyApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);    }


}
