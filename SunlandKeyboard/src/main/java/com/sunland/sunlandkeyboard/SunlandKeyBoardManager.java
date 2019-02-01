package com.sunland.sunlandkeyboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PeitaoYe
 * On 2018/9/29
 **/

/**
 * 以下是该自定义键盘的用法,建议对有输入框的布局添加ScrollView,配合布局尺寸的变化可达到最优效果，这也是android系统默认的softInputMode的默认行为.
 * scrollView = findViewById(R.id.scrollView);
 * frameLayout = findViewById(R.id.container);
 * ComponentName componentName = new ComponentName("com.sunland.sunlandkeyboard", "com.sunland.sunlandkeyboard.目标activity"); //用于获取该activity的softInputMode
 * SunlandKeyBoard sunlandKeyBoard = new SunlandKeyBoard(this);
 * unlandKeyBoard.bindMainLayout(frameLayout,scrollView);
 * sunlandKeyBoard.bindTarget(keyboardView, editText, SunlandKeyBoard.KeyboardMode.ENGLISH, componentName);
 * sunlandKeyBoard.bindTarget(keyboardView, editText8, SunlandKeyBoard.KeyboardMode.ENGLISH, componentName);
 */


public class SunlandKeyBoardManager {


    private String TAG = "delete";
    private Context mContext;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private EditText mEditText;

    private ComponentName mComponentName;

    private InputMethodManager imManager;
    private boolean isNumKeyboard;
    private int softInputState;
    private int softInputAdjust;
    private boolean isActivated; //indicate whether the Keyboard View is showing.

    private View mainLayout;
    private EditText focusedView;

    private ScrollView scrollView;

    private List<EditText> targets; //存放目标EditText
    private boolean reset_flag = true;//用于防止onGlobalLayout()因布局变化无限循环

    private boolean isPressingDelete;
    private int cur_key_code;
    private boolean should_use_pressed;

    private EditText cur_target;
    private KeyboardMode cur_mode;
    private HashMap<EditText, KeyboardMode> modes;

    public SunlandKeyBoardManager(Context context, ComponentName componentName) {
        this.mContext = context;
        this.imManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mComponentName = componentName;
        targets = new ArrayList<>();
        modes = new HashMap<>();
    }

//    public void bindMainLayout(View view, @Nullable ScrollView scrollView) {
//        this.mainLayout = view;
//        this.scrollView = scrollView;
//    }

    public void addTarget(KeyboardView keyboardView, EditText editText, KeyboardMode mode) {
        mKeyboardView = keyboardView;
        targets.add(editText);
        modes.put(editText, mode);

        initKeyboardView();

        configEditText(editText);
//        try {
//            softInputState = getSoftInputModeState(mComponentName);
//            softInputAdjust = getSoftInputModeAdjust(mComponentName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        initGlobalLayoutListener();
    }

    private void initGlobalLayoutListener() {
        if (mainLayout == null)
            throw new NullPointerException("mainLayout should not be null");

        ViewTreeObserver observer = mainLayout.getViewTreeObserver();

        observer.addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                focusedView = (EditText) newFocus;
            }
        });

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (focusedView == null)
                    return;

                if (!reset_flag)  //必须通过操作一次KeyboardView的弹入或者弹出重置reset_flag
                    return;

                DisplayMetrics displayMetrics = WindowInfoUtils.getWindowMetrics(mContext);

                int actionBarHeight = WindowInfoUtils.getStatusBarHeight(mContext);
                if (isActivated) {
                    int height = displayMetrics.heightPixels - mKeyboardView.getHeight() - actionBarHeight - WindowInfoUtils.getActionBarHeight(mContext);
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    if (scrollView == null) {
                        mainLayout.setLayoutParams(lp);
                    } else {
                        scrollView.setLayoutParams(lp);
                    }
                } else {
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    if (scrollView == null) {
                        mainLayout.setLayoutParams(lp);
                    } else {
                        scrollView.setLayoutParams(lp);
                    }
                }
                reset_flag = false;
            }
        });
    }

    private void configEditText(final EditText editText) {

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocus();
                mEditText = (EditText) v;
                if (!editText.getText().toString().isEmpty()) {
                    if (modes.get(editText) == KeyboardMode.VEHICLE_PLATE) {
                        mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_english26);
                        refreshKey();
                        mKeyboardView.setKeyboard(mKeyboard);
                    }
                }
                return false;
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard();
                    return;
                } else {
                    mEditText = (EditText) v;
                }
                if (softInputState == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE ||
                        softInputState == WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE) {
                    showKeyboard();
                } else {
                    hideKeyboard();
                }
                ((AppCompatActivity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isActivated && event.getAction() == KeyEvent.ACTION_UP) {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur_mode = modes.get(editText);//获取当前点击目标的键盘类型
                cur_target = editText;
                if (!editText.getText().toString().isEmpty()) {
                    if (modes.get(editText) == KeyboardMode.VEHICLE_PLATE) {
                        mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_english26);
                        mKeyboardView.setKeyboard(mKeyboard);
                        refreshKey();
                    }
                } else {
                    inflateKeyboard();
                }
                hideSystemSoftInput(v);
                if (!isActivated)
                    showKeyboard();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    hideKeyboard();
                    if (modes.get(editText) == KeyboardMode.VEHICLE_PLATE && !isPressingDelete) {
                        Log.d("delete", "afterTextChanged: ");
                        mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_vehicle);
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mKeyboardView.setKeyboard(mKeyboard);
                            }
                        }, 100);
                    }
                }
            }
        });
    }


    public enum KeyboardMode {
        ENGLISH,
        IDENTITY,
        NUMBER,
        SYMBOL,
        VEHICLE_PLATE
    }

    private void initKeyboardView() {

        mKeyboardView.setEnabled(true);

        mKeyboardView.setPreviewEnabled(false);


//        mKeyboardView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (cur_key_code == Keyboard.KEYCODE_DELETE) {
//                        isPressingDelete = false;
//                        int index = targets.indexOf(cur_target);
//                        EditText mEditText = targets.get(index);
//                        Editable editable = mEditText.getEditableText();
//                        if (editable.length() == 0) {
//                            if (modes.get(cur_target) == KeyboardMode.VEHICLE_PLATE) {
//                                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_vehicle);
//                                mKeyboardView.setKeyboard(mKeyboard);
//                            }
//                        }
//                    }
//
//                }
//                return false ;
//            }
//        });
        mKeyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                Log.d(TAG, "onPress: ");

            }

            @Override
            public void onRelease(int primaryCode) {
//                if (primaryCode == Keyboard.KEYCODE_DELETE) {
//                    Log.d("delete", "onRelease: ");
//                    int index = targets.indexOf(cur_target);
//                    EditText mEditText = targets.get(index);
//                    Editable editable = mEditText.getEditableText();
//                    if (editable.length() == 0) {
//                        if (modes.get(cur_target) == KeyboardMode.VEHICLE_PLATE) {
//                            isPressingDelete = false;
//                            mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_vehicle);
//                            new android.os.Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mKeyboardView.setKeyboard(mKeyboard);
//
//                                }
//                            }, 100);
//                        }
//                    }
//                }
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log.d("delete", "onKey: ");
                cur_key_code = primaryCode;
                int index = targets.indexOf(cur_target);
                EditText mEditText = targets.get(index);
                Editable editable = mEditText.getEditableText();
                int start = mEditText.getSelectionStart();
                int end = mEditText.getSelectionEnd();

                if (primaryCode == Keyboard.KEYCODE_DELETE) {
                    isPressingDelete = true;
                    isNumKeyboard = false;
                    // 回退
                    if (editable.length() > 0) {
                        if (start < end) {
                            editable.delete(start, end);// 选中文本删除
                        } else if (start > end) {
                            editable.delete(end, start);// 选中文本删除
                        } else {
                            if (start > 0) {
                                editable.delete(start - 1, start);// 单个删除
                            }
                        }
                    }
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_CHINESE)) { //中文
                    hideKeyboard();
                    isNumKeyboard = false;
                    try {
                        showSystemSoftInputIfhide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_ENGLISH)) {// 英文
                    mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_english26);
                    refreshKey();
                    mKeyboardView.setKeyboard(mKeyboard);
                    isNumKeyboard = false;
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_SHIFT)) {//大小写切换
                    refreshKey();
                    mKeyboardView.setKeyboard(mKeyboard);
                    isNumKeyboard = false;
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_NUM)) {//数字
                    mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_number);
                    mKeyboardView.setKeyboard(mKeyboard);
                    isNumKeyboard = true;
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_SYMBOLS)) {//符号
                    mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_symbols);
                    mKeyboardView.setKeyboard(mKeyboard);
                    isNumKeyboard = false;
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_BLANK)) {
                    editable.insert(start, " ");
                } else if (primaryCode == mContext.getResources().getInteger(R.integer.KEYBORD_DONE)) {
                    hideKeyboard();
                    isNumKeyboard = false;
                } else {
                    editable.insert(start, getKeyLabel(primaryCode));
                    if (cur_mode == KeyboardMode.VEHICLE_PLATE && !isNumKeyboard) {
                        mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_english26);
                        mKeyboardView.setKeyboard(mKeyboard);
                        refreshKey();
                    }

                }
            }

            @Override
            public void onText(CharSequence text) {
                Log.d(TAG, "onText: ");
            }

            @Override
            public void swipeLeft() {
                Log.d(TAG, "swipeLeft: ");
            }

            @Override
            public void swipeRight() {
                Log.d(TAG, "swipeRight: ");
            }

            @Override
            public void swipeDown() {
                Log.d(TAG, "swipeDown: ");
            }

            @Override
            public void swipeUp() {
                Log.d(TAG, "swipeUp: ");
            }
        });
    }

    private void inflateKeyboard() {
        switch (cur_mode) {
            case NUMBER:
                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_number);
                break;
            case SYMBOL:
                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_symbols);
                break;
            case ENGLISH:
                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_english26);
                break;
            case IDENTITY:
                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_id);
                break;
            case VEHICLE_PLATE:
                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_vehicle);
                break;
            default:
                mKeyboard = new Keyboard(mContext, R.xml.keyboard_mode_english);
        }
        mKeyboardView.setKeyboard(mKeyboard);
    }

    public void showKeyboard() {
        if (isActivated)
            return;
        if (mKeyboardView != null) {
            mKeyboardView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_kb);
            mKeyboardView.startAnimation(animation);
            isActivated = true;
            reset_flag = true;
        }
    }

    public void hideKeyboard() {
        if (!isActivated)
            return;

        if (mKeyboardView != null) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_hide_kb);
            mKeyboardView.startAnimation(animation);
            mKeyboardView.setVisibility(View.GONE);
            isActivated = false;
            reset_flag = true;
        }
    }

    private void hideSystemSoftInput(View v) {
        imManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void showSystemSoftInputIfhide() {
        imManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private ActivityInfo getActivityInfo(ComponentName componentName)
            throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        return pm.getActivityInfo(componentName, 0);

    }

    private int getSoftInputModeState(ComponentName componentName)
            throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName).softInputMode & WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE;
    }

    private int getSoftInputModeAdjust(ComponentName componentName)
            throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName).softInputMode & WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST;
    }

    /**
     * 刷新英文输入法大小写
     */
    private void refreshKey() {
        List<Keyboard.Key> keylist = mKeyboard.getKeys();
        if (keylist.get(mKeyboard.getShiftKeyIndex()).label.equals("大写"))
            keylist.get(mKeyboard.getShiftKeyIndex()).label = "小写";
        else if (keylist.get(mKeyboard.getShiftKeyIndex()).label.equals("小写"))
            keylist.get(mKeyboard.getShiftKeyIndex()).label = "大写";

        for (Keyboard.Key key : keylist) {
            if (key.label != null) {
                if (isAlphaNumber(key.label.toString())) {
                    if (key.codes[0] >= 65 && key.codes[0] <= 90) {
                        key.codes[0] = key.codes[0] + 32;
                        key.label = key.label.toString().toLowerCase(Locale.CHINA);
                    } else if (key.codes[0] >= 97 && key.codes[0] <= 122) {
                        key.codes[0] = key.codes[0] - 32;
                        key.label = key.label.toString().toUpperCase(Locale.CHINA);
                    }
                }
            }
        }
    }

    private static boolean isAlphaNumber(String src) {
        String ps = "[a-zA-Z_0-9]*";
        Pattern pattern = Pattern.compile(ps);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    private String getKeyLabel(int primaryCode) {
        List<Keyboard.Key> keylist = mKeyboard.getKeys();
        for (Keyboard.Key key : keylist) {
            if (key.label != null && key.codes[0] == primaryCode) {
                return key.label.toString();
            }
        }
        return "";
    }

    public interface OnKeyDoenListener {
        void onKeyDone();
    }
}
