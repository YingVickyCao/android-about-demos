package com.hades.example.android.other_ui._dialog.good.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/**
 * DialogFragment:
 * height = full screen
 * width = half screen size
 * Animation enter =  down -> up. and exit = up -> down
 * Position = right.
 */
public class HalfWidthDialogFragment extends DialogFragment {
    private static final String TAG = HalfWidthDialogFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + "@" + hashCode());
        /**
         <pre>
         Show a Dialog:
         1. When Only 1) Show as a small dialog.
         <item name="android:windowFullscreen">false</item> // 2)
         windowFullscreen = true,  show status bar with all kind of status.
         windowFullscreen = false, show status bar with  blank.
         2. When Only 3) Show as a big  screen dialog. Has padding to window
         3. When 1) + 3 Show as a full screen dialog.
         </pre>
         *
         */
        // 1)
        // style,设置两处：onCreate,onStart, 否则 动画太快，看不出效果 （Enter-加速，Exist-减速）
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentShowAsFullDialog);  // 1) work when Show as a Dialog
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG, "onAttachFragment: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        Log.d(TAG, "onCreateView: " + "@" + hashCode());
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_fragment, container, false);
        view.findViewById(R.id.btn).setOnClickListener(v -> clickBtn());
        return view;
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);                                   // 2) Show as a Dialog
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (null != dialog) {
            Log.d(TAG, "onStart: dialog != null"); // Show as a Dialog
            int width = (int) (getScreenWidth() * 0.5);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Toast.makeText(getActivity(), "" + width + "," + height, Toast.LENGTH_SHORT).show();
            
            /**
             * tablet:amSung Galaxy Tab S4,Android 9(28)
             * portrait:  width =1600, height=2452
             * landscape: width = 2560,height =1492
             *
             * tablet:amSung Galaxy Tab S2,Android 7(24)
             * portrait: width = 1536, height = 2048
             * landscape:width = 2048, height = 1536
             */
            Log.d(TAG, "onStart: width = " + getScreenWidth() + ", height = " + getScreenHeight());
            if (null != dialog.getWindow()) {
                dialog.getWindow().setLayout(width, height);                                        // 3)
                dialog.getWindow().setGravity(Gravity.END);
                dialog.getWindow().setWindowAnimations(R.style.DialogFragmentShowAsFullDialog);
            }
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                /**
                 * https://stackoverflow.com/questions/7622031/dialogfragment-and-back-button
                 * 通过判断各种事件，处理监听
                 */
                if (keyCode == KeyEvent.KEYCODE_BACK && KeyEvent.ACTION_DOWN == event.getAction()) {
                    Log.d(TAG, "onKey: Back");
                    dismiss();
                    return true; // pretend we've processed it. 中断事件，不接受按键信息
                } else {
                    Log.d(TAG, "onKey: Others");
                    return false; // pass on to be processed as normal 事件继续向下传递
                }
            });
        } else {
            Log.d(TAG, "onStart: dialog = null"); // Show as an Embedded Fragment
        }
    }

    // TODO: refactor get screen width and height
    private int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        // dp = metrics.widthPixels/metrics.density
        // px
        return metrics.widthPixels;
    }

    // TODO: 2019-11-19
    private int getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + "@" + hashCode());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG, "onDismiss: Thread " + Thread.currentThread().getName()); // Thread main
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Log.d(TAG, "onCancel: Thread " + Thread.currentThread().getName()); // Thread main
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + "@" + hashCode());
    }

    private void clickBtn() {
        dismiss();
    }

}
