package com.campus.event_filter.request;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({MODE.UI, MODE.CAMPUTATION, MODE.IO})
@Retention(RetentionPolicy.SOURCE)
public @interface MODE_RANGE {}