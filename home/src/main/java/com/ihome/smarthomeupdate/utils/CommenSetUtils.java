package com.ihome.smarthomeupdate.utils;

import com.ihome.base.utils.SharedInfo;
import com.ihome.smarthomeupdate.common.KTConstant;
import com.ihome.smarthomeupdate.module.home.model.ProjectItem;
import com.ihome.smarthomeupdate.module.mine.model.LoginBean;

/**
 * @author xiongbin
 * @description:
 * @date : 2020/12/30 13:13
 */

public class CommenSetUtils {
    public static String getProjectTitle(){
        ProjectItem item = SharedInfo.getInstance().getEntity(ProjectItem.class);
        if(item!=null){

            return item.getFunctionName()+"\n"+item.getProjectName();
        }

        return "";
    }

    public static String getRecruitID(){
        ProjectItem item = SharedInfo.getInstance().getEntity(ProjectItem.class);
        if(item!=null){
            return item.getRecruitID();
        }
        return "";
    }


    public static void signOut(){
        SharedInfo.getInstance().remove(KTConstant.TOKEN);
        SharedInfo.getInstance().remove(LoginBean.class);
        SharedInfo.getInstance().remove(ProjectItem.class);
    }


}
