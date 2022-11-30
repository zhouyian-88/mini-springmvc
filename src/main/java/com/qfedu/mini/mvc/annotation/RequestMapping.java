package com.qfedu.mini.mvc.annotation;

import java.lang.annotation.*;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-19:22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestMapping {
    String value() default "";
}
