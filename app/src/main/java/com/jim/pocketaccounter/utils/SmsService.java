package com.jim.pocketaccounter.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jim.pocketaccounter.PocketAccounter;
import com.jim.pocketaccounter.PocketAccounterApplication;
import com.jim.pocketaccounter.database.DaoSession;
import com.jim.pocketaccounter.database.SmsParseObject;
import com.jim.pocketaccounter.database.SmsParseObjectDao;
import com.jim.pocketaccounter.database.SmsParseSuccess;
import com.jim.pocketaccounter.database.TemplateSms;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by root on 10/16/16.
 */

public class SmsService extends Service {
    @Inject
    DaoSession daoSession;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ((PocketAccounter) getApplicationContext()).component((PocketAccounterApplication)getApplication()).inject(this);
        SmsParseObject smsParseObject =daoSession.getSmsParseObjectDao().queryBuilder()
                .where(SmsParseObjectDao.Properties.Number.eq(intent.getStringExtra("number"))).list().get(0);
        if (smsParseObject != null) {
            SmsParseSuccess smsParseSuccess = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(intent.getLongExtra("date", 0));
            for (TemplateSms templateSms : smsParseObject.getTemplates()) {
                Pattern pattern = Pattern.compile(templateSms.getRegex());
                Matcher matcher = pattern.matcher(intent.getStringExtra("body"));
                matcher.matches();
                if (matcher.matches()) {
                    smsParseSuccess = new SmsParseSuccess();
                    smsParseSuccess.setBody(intent.getStringExtra("body"));
                    double summ = 0;
                    try {
                        smsParseSuccess.setDate(calendar);
                        smsParseSuccess.setCurrency(smsParseObject.getCurrency());
                        smsParseSuccess.setAccount(smsParseObject.getAccount());
                        smsParseSuccess.setNumber(intent.getStringExtra("number"));
                        smsParseSuccess.setSmsParseObjectId(smsParseObject.getId());
                        if (matcher.group(templateSms.getPosAmountGroup()) != null
                                && !matcher.group(templateSms.getPosAmountGroup()).isEmpty()) {
                            try {
                                summ = Double.parseDouble(matcher.group(templateSms.getPosAmountGroup()));
                                smsParseSuccess.setAmount(summ);
                                smsParseSuccess.setIsSuccess(true);
                                smsParseSuccess.setType(templateSms.getType());
                            } catch (Exception e) {
                                try {
                                    if (matcher.group(templateSms.getPosAmountGroupSecond()) != null
                                            && !matcher.group(templateSms.getPosAmountGroupSecond()).isEmpty()) {
                                        summ = Double.parseDouble(matcher.group(templateSms.getPosAmountGroupSecond()));
                                        smsParseSuccess.setAmount(summ);
                                        smsParseSuccess.setIsSuccess(true);
                                        smsParseSuccess.setType(templateSms.getType());
                                    }
                                } catch (Exception e1) {
                                    smsParseSuccess.setIsSuccess(false);
                                }
                            }
                        } else if (matcher.group(templateSms.getPosAmountGroupSecond()) != null
                                && !matcher.group(templateSms.getPosAmountGroupSecond()).isEmpty()) {
                            try {
                                summ = Double.parseDouble(matcher.group(templateSms.getPosAmountGroupSecond()));
                                smsParseSuccess.setAmount(summ);
                                smsParseSuccess.setIsSuccess(true);
                                smsParseSuccess.setType(templateSms.getType());
                            } catch (Exception e1) {
                                smsParseSuccess.setIsSuccess(false);
                            }
                        } else {
                            smsParseSuccess.setIsSuccess(false);
                        }
                        smsParseSuccess.setAmount(summ);
                        smsParseSuccess.setType(templateSms.getType());
                        smsParseSuccess.setIsSuccess(true);
                    } catch (Exception e) {
                        smsParseSuccess.setIsSuccess(false);
                    }
                    break;
                }
            }
            if (smsParseSuccess == null) {
                smsParseSuccess = new SmsParseSuccess();
                smsParseSuccess.setNumber("number");
                smsParseSuccess.setDate(calendar);
                smsParseSuccess.setSmsParseObjectId(smsParseObject.getId());
                smsParseSuccess.setAccount(smsParseObject.getAccount());
                smsParseSuccess.setCurrency(smsParseObject.getCurrency());
                smsParseSuccess.setBody("number");
                smsParseSuccess.setIsSuccess(false);
            }
            daoSession.getSmsParseSuccessDao().insertOrReplace(smsParseSuccess);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}