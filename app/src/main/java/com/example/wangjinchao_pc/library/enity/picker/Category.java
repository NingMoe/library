package com.example.wangjinchao_pc.library.enity.picker;

import com.example.wangjinchao_pc.library.enity.domain.Hobby;

import java.io.Serializable;

/**
 * Created by wangjinchao-PC on 2017/9/8.
 */

public class Category implements Serializable {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Hobby hobby) {
        this.id = Integer.parseInt(hobby.getHobbyid());
        this.name = hobby.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        //重写该方法，作为选择器显示的名称
        return name;
    }
}
