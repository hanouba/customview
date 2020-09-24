package com.hansen.mzbanner.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @author HanN on 2020/9/23 11:35
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/23 11:35
 * @updateremark:
 * @version: 2.1.67
 */
public class ScaleYTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9F;
    @Override
    public void transformPage(@NonNull View page, float position) {
        if(position < -1){
            page.setScaleY(MIN_SCALE);
        }else if(position<= 1){
            //
            float scale = Math.max(MIN_SCALE,1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);
            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        }else{
            page.setScaleY(MIN_SCALE);
        }
    }
}
