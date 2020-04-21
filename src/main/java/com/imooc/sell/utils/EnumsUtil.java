package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnums;
import org.omg.CORBA.PUBLIC_MEMBER;

public class EnumsUtil<E> {
    public static <T extends CodeEnums> T getByCode(Integer code,Class<T> enumClass){
        for (T codeEnums: enumClass.getEnumConstants()){
            if (codeEnums.getCode().equals(code)){
                return  codeEnums;
            }
        }
        return  null;
    }
}
