package tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.drawletter.BuildConfig;
import com.example.apple.drawletter.R;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.MyApplication;

/**
 * @author WuYaKun
 */

public class Utils {

    public static final String NET_ERROR = "网络不给力呀~";
    public static final String NO_MORE = "没有更多了哦~";
    public static final String NO_INFO = "没有相应数据~";
    public static final String INFO_ERROR = "获取信息失败，请稍候再试";
    public static boolean isLog = BuildConfig.DEBUG;
    public static MyApplication application;
    public static Toast toast = null;

    public static final String sevenPackageName = "com.stc_android";

    /**
     * 不需要context，能全局用的Toast
     *
     * @param str
     */

    public static void MyToast(Object str) {
        if (str == null) {
            str = "";
        }
        if (toast == null) {
            if (application == null) {
                application = MyApplication.getInstance();
            }
            toast = Toast.makeText(application, str.toString(),
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(str.toString());
        }
        Logs(str.toString());
        toast.show();
    }

    /**
     * 光标定尾部
     *
     * @param et
     * @param str
     */

    public static void setEditEnd(EditText et, String str) {
        et.setText(str);
        et.setSelection(et.getText().length());
    }

    /**
     * 将字符串转换为int类型
     *
     * @param str
     * @return
     */

    public static int toInt(String str) {
        if (isInteger(str)) {
            return Integer.valueOf(str);
        } else {
            return 0;
        }
    }

    /**
     * 提取edittext里面的内容,已经被删除空格了
     *
     * @param editText
     * @return
     */

    public static String editTextToString(TextView editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 检查传过来的String 数组是不是空的
     *
     * @param strings
     * @return
     */

    public static boolean isNulls(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 列表是不是空的
     *
     * @return
     */

    public static boolean isListNulls(List<?> objects) {
        return objects == null || objects.size() == 0;
    }

    /**
     * 提取edittext里面的内容直接转换为int,如果为空，直接返回0
     *
     * @param editText
     * @return if null 0
     */

    public static int toIntByEditText(TextView editText) {
        String str = editTextToString(editText);
        return toInt(isNulls(str) ? "0" : str);
    }

    /**
     * 获取手机固件版本号
     */

    public static int getPhoneSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 回收图片
     *
     * @param drawable
     */
    public static void recycleDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }

        drawable.setCallback(null);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        // 如果图片还未回收，先强制回收该图片
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            drawable = null;
        }
    }


    /**
     * 替换掉字符串中间的字符变成****
     */

    public static String getPhoneStr(String str) {
        if (TextUtils.isEmpty(str)) return "";
        String empStr = "***";
        int length = str.length();
        if (length == 1) return str + empStr;
        // phone code
        if (length == 11) return str.substring(0, 3) + empStr + str.substring(11 - 4, 11);
        if (length >= 2) return str.substring(0, 1) + empStr + str.substring(length - 1, length);
        return str;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 密码验证
     *
     * @param str
     */

    public static boolean isPwd(String str) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 关闭输入法
     *
     * @param context
     */
    public static void closeInputKeyBoard(Activity context) {
        if (!isInputKeyBoardVisible(context)) return;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (context.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 查看输入法是否打开
    private static boolean isInputKeyBoardVisible(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    /**
     * 输入法打开则关闭，关闭则打开
     *
     * @param context
     */

    public static void openInputKeyBoard(Activity context) {
        InputMethodManager m = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 进一法
     *
     * @param max
     * @param min
     * @return
     */
    public static int jinyifa(int max, int min) {
        double c = (((double) max) / min);
        return (int) Math.ceil(c);
    }

    /**
     * 转换时间格式
     *
     * @return
     */

    public static String toDate(String time) {
        if (time.equals("")) {
            return "";
        }
        time = time.substring(6, time.length() - 2);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 格式化double
     *
     * @param d
     */

    public static double formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String setPrice(double price) {
        return "￥" + formatDouble(price);
    }

    public static String setPriceYuan(double price) {
        return formatDouble(price) + "元";
    }


    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input) {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    /**
     * 获取程序的名字
     *
     * @return
     */

    public static String getAppName(Context context) {
        return context.getResources().getString(R.string.app_name);
    }

    /**
     * 打印log
     *
     * @param str
     */

    public static void Logs(Object str) {
        if (isLog) {
            Log.d("////////////////", str == null ? "空的" : str.toString());
        }
    }

    /**
     * 打印多个
     *
     * @param objects
     */

    public static void LogMore(Object... objects) {
        StringBuffer stringBuffer = null;
        for (Object object : objects) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            stringBuffer.append(object.toString());
        }
        if (stringBuffer != null) {
            Logs(stringBuffer.toString());
        }
    }

    /**
     * 获取个位百位千位
     */

    public static void getNum() {
        int s = 1831;
        int g = s % 10;
        int sw = s / 10 % 10;
        int b = s / 100 % 10;
        int q = s / 1000 % 10;
        System.out.println("个位数是:" + g + ";十位数是:" + sw + ";百位数是：" + b
                + ";千位数是：" + q);
    }

    /**
     * 获取当前时间时间戳
     */

    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前时间到今天结束时间
     *
     * @return
     */

    public static String getNowToEndTime() {
        String str = getNowTime();
        str = str.substring(0, str.length() - 8);
        str = str + "23:59:59";
        String endTime = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(str);
            Date d2 = new Date(System.currentTimeMillis());
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);
            long ss = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
            endTime = hours + "," + minutes + "," + ss;
        } catch (Exception e) {
        }
        return endTime;
    }

    /**
     * 获得当前日期的毫秒数，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */

    public static long getMilliscond(String date) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        long timeStart = 0;
        try {
            timeStart = mSimpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStart;
    }

    /**
     * 自定义格式获取毫秒数
     *
     * @param format
     * @param date
     * @return
     */

    public static long getMilliscond(String format, String date) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        long timeStart = 0;
        try {
            timeStart = mSimpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStart;
    }

    /**
     * 小时转化成毫秒
     */

    public static long getMilliscondFromHour(String hour, String format) {
        return getMilliscond(format, hour);
    }

    /**
     * 取指定日期为星期几.
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String 星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "星期日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "星期日";
                break;
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param milliseconds the milliseconds
     * @param format       格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            thisDateTime = mSimpleDateFormat.format(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    /**
     * 判断集合是不是为空或者size=0
     *
     * @param <E>
     * @return true 说明是空的
     */

    public static <E> boolean isListNullOr0(Collection<? extends E> collection) {
        if (null == collection || collection.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 字符串转换为URI地址
     *
     * @param uri
     * @return
     */

    public static Uri getUri(String uri) {
        return Uri.parse(uri);
    }

    /**
     * 从html里面获取字符串
     *
     * @return
     */

    public static String fromHtml(String htmlsString) {
        return Html.fromHtml(htmlsString).toString();
    }

    /**
     * 获取不重复的字符串
     *
     * @return
     */

    public static String getUUid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断Android系统API的版本
     *
     * @return
     */
    public static int getAPIVersion() {
        int APIVersion;
        try {
            APIVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            APIVersion = 0;
        }
        return APIVersion;
    }

    /**
     * dp转px
     *
     * @param dip
     * @param mContext
     * @return
     */

    public static Integer dip2px(int dip, Context mContext) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }

    /**
     * TextView单行 android:ellipsize="end" android:singleLine="true"
     */

    /**
     * 传递intent
     *
     * intent.putExtra("goodsClasses", (Serializable) goodsClasses);
     * goodsClasses = (List<GoodsClass>) getIntent().getSerializableExtra(
     * "goodsClasses");
     */

    /**
     * scrollView.smoothScrollTo(0, 20);
     *
     * android:listSelector="#00000000"//点击没颜色
     * android:cacheColorHint="#00000000"//滚动没颜色
     */

    /**
     * TextView单行 android:ellipsize="end" android:singleLine="true"
     */

    /**
     * 传递intent
     *
     * intent.putExtra("goodsClasses", (Serializable) goodsClasses);
     * goodsClasses = (List<GoodsClass>) getIntent().getSerializableExtra(
     * "goodsClasses");
     */

    /**
     * scrollView.smoothScrollTo(0, 20);
     *
     * android:listSelector="#00000000"//点击没颜色
     * android:cacheColorHint="#00000000"//滚动没颜色
     */

    /**
     * 友盟
     * qianjidaojia@163.com/qianjidaojia321
     */

    /**
     *
     new Handler().postDelayed(new Runnable() {
     public void run() {
     getData(AppConfig.REFRESH);
     }
     }, 100);
     *
     */

//    cd /Users/wuyakun/AndroidStudioProjects/SVN/QJDJA1/app
//    adb install app -armeabi-release.apk
//    adb uninstall com.june.qianjidaojia.a1

}
