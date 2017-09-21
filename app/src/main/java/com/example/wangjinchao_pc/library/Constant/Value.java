package com.example.wangjinchao_pc.library.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.wangjinchao_pc.library.enity.Token;
import com.example.wangjinchao_pc.library.enity.domain.Raking;
import com.example.wangjinchao_pc.library.util.Logger;

import java.util.List;

/**
 * Created by wangjinchao-PC on 2017/7/9.
 */

public class Value {
    public static final int startPage = 1;

    public static List<Raking> rakingList1=null,rakingList2=null,rakingList3=null,rakingList4=null;
    public static boolean refresh1=false,refresh2=false,refresh3=false,refresh4=false;


    public static void setRankList(List<Raking> rakingList1,List<Raking> rakingList2,List<Raking> rakingList3,List<Raking> rakingList4){
        Log.d("==wjc==","setRankList");
        Value.rakingList1 = rakingList1;
        Value.rakingList2 = rakingList2;
        Value.rakingList3 = rakingList3;
        Value.rakingList4=rakingList4;
        Value.refresh1=true;
        Value.refresh2=true;
        Value.refresh3=true;
        Value.refresh4=true;
    }


    public static List<Raking> getRakingList1() {
        return rakingList1;
    }

    public static void setRakingList1(List<Raking> rakingList1) {
        Value.rakingList1 = rakingList1;
    }

    public static List<Raking> getRakingList2() {
        return rakingList2;
    }

    public static void setRakingList2(List<Raking> rakingList2) {
        Value.rakingList2 = rakingList2;
    }

    public static List<Raking> getRakingList3() {
        return rakingList3;
    }

    public static void setRakingList3(List<Raking> rakingList3) {
        Value.rakingList3 = rakingList3;
    }

    public static boolean isRefresh1() {
        return refresh1;
    }

    public static void setRefresh1(boolean refresh1) {
        Value.refresh1 = refresh1;
    }

    public static boolean isRefresh2() {
        return refresh2;
    }

    public static void setRefresh2(boolean refresh2) {
        Value.refresh2 = refresh2;
    }

    public static boolean isRefresh3() {
        return refresh3;
    }

    public static void setRefresh3(boolean refresh3) {
        Value.refresh3 = refresh3;
    }

    public static List<Raking> getRakingList4() {
        return rakingList4;
    }

    public static void setRakingList4(List<Raking> rakingList4) {
        Value.rakingList4 = rakingList4;
    }

    public static boolean isRefresh4() {
        return refresh4;
    }

    public static void setRefresh4(boolean refresh4) {
        Value.refresh4 = refresh4;
    }

}
