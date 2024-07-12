package com.hades.example.android.widget;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;
import com.hades.example.android.widget.button.TestButtonActivity;
import com.hades.example.android.widget.checkbox.CheckBoxFragment;
import com.hades.example.android.widget.custom_view.CustomViewActivity;
import com.hades.example.android.widget.custom_view.keyboard.TestKeyBoardFragment;
import com.hades.example.android.widget.drag_drop.DragDropFragment;
import com.hades.example.android.widget.edittext.TestEditTextFragment;
import com.hades.example.android.widget.imageview.KenBurnsImageFragment;
import com.hades.example.android.widget.imageview.TestImageViewFragment;
import com.hades.example.android.widget.imageview.TestImageViewScaleTypeFragment;
import com.hades.example.android.widget.layout._constraintlayout.TestConstraintLayoutFragment;
import com.hades.example.android.widget.layout._framelayout.TestFrameLayoutFragment;
import com.hades.example.android.widget.layout._viewgroup.TestViewGroupFragment;
import com.hades.example.android.widget.layout.linearlayout.TestLinearLayout4LayoutGravityAndGravityFragment;
import com.hades.example.android.widget.layout.linearlayout.TestLinearLayoutCannotChangeColor2Fragment;
import com.hades.example.android.widget.layout.linearlayout.TestLinearLayoutCannotChangeColorFragment;
import com.hades.example.android.widget.layout.linearlayout.TestLinearLayoutFragment;
import com.hades.example.android.widget.list._listview.TestListViewFragment;
import com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v1.DragAndReorderListActivity;
import com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v2.screen_size.TestViewLocationFragment;
import com.hades.example.android.widget.list._recyclerview.dummy.DummyRecyclerViewFragment;
import com.hades.example.android.widget.pickers.CalendarViewFragment;
import com.hades.example.android.widget.pickers.DatePickerFragment;
import com.hades.example.android.widget.pickers.DatePickerDialogFragment;
import com.hades.example.android.widget.pickers.NumberPickerFragment;
import com.hades.example.android.widget.pickers.TimePickerDialogFragment;
import com.hades.example.android.widget.pickers.TimePickerFragment;
import com.hades.example.android.widget.progressbar.TestProgressBarFragment;
import com.hades.example.android.widget.progressbar.TestRatingBarFragment;
import com.hades.example.android.widget.progressbar.TestSeekBarFragment;
import com.hades.example.android.widget.radiobutton.TestRadioButtonFragment;
import com.hades.example.android.widget.search_view.SearchViewFragment;
import com.hades.example.android.widget.snackbar.SnackbarFragment;
import com.hades.example.android.widget.spinner.TestSpinnerFragment;
import com.hades.example.android.widget.surfaceview.TestSurfaceViewPlayVideoFragment;
import com.hades.example.android.widget.switchBtn.TestSwitchFragment;
import com.hades.example.android.widget.tablayout.TestTabLayoutFragment;
import com.hades.example.android.widget.textview.TestTextViewActivity;
import com.hades.example.android.widget.videoview.VideoViewRotateScreenTipActivity;
import com.hades.example.android.widget.view_animator.ImageSwitcherFragment;
import com.hades.example.android.widget.view_animator.TextSwitcherFragment;
import com.hades.example.android.widget.view_animator.adapterviewflipper.v3.AdapterViewFlipper3Fragment;
import com.hades.example.android.widget.view_animator.viewflipper.ViewFlipperFragment;
import com.hades.example.android.widget.view_animator.ViewSwitcherFragment;
import com.hades.example.android._feature._web_based_contents.webview.TestWebViewFragment;

public class WidgetActivity extends AppCompatActivity {
    private static final String TAG = WidgetActivity.class.getSimpleName();
    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_layout);

        findViewById(R.id.page_CustomVew).setOnClickListener(v -> pageCustomVew());
        findViewById(R.id.pageListView).setOnClickListener(v -> pageListView());
        findViewById(R.id.pageViewLocation).setOnClickListener(v -> pageViewLocation());
        findViewById(R.id.pageConstraintLayout).setOnClickListener(v -> pageConstraintLayout());
        findViewById(R.id.pageLinearLayout).setOnClickListener(v -> pageLinearLayout());
        findViewById(R.id.pageLinearLayoutCanNotChangeColor).setOnClickListener(v -> pageLinearLayoutCanNotChangeColor());
        findViewById(R.id.pageLinearLayoutCanNotChangeColor2).setOnClickListener(v -> pageLinearLayoutCanNotChangeColor2());
        findViewById(R.id.pageLinearLayout4LayoutGravityAndGravity).setOnClickListener(v -> pageLinearLayout4LayoutGravityAndGravity());
        findViewById(R.id.pageViewGroup).setOnClickListener(v -> pageViewGroup());
        findViewById(R.id.pageFrameLayout).setOnClickListener(v -> pageFrameLayout());
        findViewById(R.id.pageWebView).setOnClickListener(v -> pageWebView());
        findViewById(R.id.pageVideoView).setOnClickListener(v -> pageVideoView());
        findViewById(R.id.pageSurfaceViewPlayVideo).setOnClickListener(v -> pageSurfaceViewPlayVideo());
        findViewById(R.id.pageTextView).setOnClickListener(v -> pageTextView());
        findViewById(R.id.pageButton).setOnClickListener(v -> pageButton());
        findViewById(R.id.page_ImageButton).setOnClickListener(v -> page_ImageButton());
        findViewById(R.id.pageImageView).setOnClickListener(v -> pageImageView());
        findViewById(R.id.pageKenBurnsImage).setOnClickListener(v -> pageKenBurnsImage());
        findViewById(R.id.pageImageViewScaleType).setOnClickListener(v -> pageImageViewScaleType());
        findViewById(R.id.pageRatingBar).setOnClickListener(v -> pageRatingBar());
        findViewById(R.id.pageSeekBar).setOnClickListener(v -> pageSeekBar());
        findViewById(R.id.pageProgressbar).setOnClickListener(v -> pageProgressbar());
        findViewById(R.id.jumpViewSwitcher).setOnClickListener(v -> testViewSwitcher());
        findViewById(R.id.jumpImageSwitcher).setOnClickListener(v -> testImageSwitcher());
        findViewById(R.id.jumpTextSwitcher).setOnClickListener(v -> testTextSwitcher());

        findViewById(R.id.pageRecyclerView).setOnClickListener(v -> pageRecyclerView());
        findViewById(R.id.recyclerView2).setOnClickListener(v -> recyclerView2());
        findViewById(R.id.pageRecyclerView4DragReorderList).setOnClickListener(v -> pageRecyclerView4DragReorderList());
        findViewById(R.id.testDragAndDrop).setOnClickListener(v -> testDragAndDrop());

        findViewById(R.id.page_ViewFlipper).setOnClickListener(v -> page_ViewFlipper());
        findViewById(R.id.page_AdapterViewFlipper).setOnClickListener(v -> page_AdapterViewFlipper());
        findViewById(R.id.testCalendarView).setOnClickListener(v -> testCalendarView());
        findViewById(R.id.pageDatePicker).setOnClickListener(v -> pageDatePicker());
        findViewById(R.id.pageTimePicker).setOnClickListener(v -> pageTimePicker());
        findViewById(R.id.pageDatePickerDialog).setOnClickListener(v -> pageDatePickerDialog());
        findViewById(R.id.pageTimePickerDialog).setOnClickListener(v -> pageTimePickerDialog());
        findViewById(R.id.testNumberPicker).setOnClickListener(v -> testNumberPicker());
        findViewById(R.id.testSearchView).setOnClickListener(v -> testSearchView());
        findViewById(R.id.pageSpinner).setOnClickListener(v -> pageSpinner());
        findViewById(R.id.pageTabLayout).setOnClickListener(v -> pageTabLayout());
        findViewById(R.id.pageRadioButton).setOnClickListener(v -> pageRadioButton());
        findViewById(R.id.pageCheckBox).setOnClickListener(v -> pageCheckBox());
        findViewById(R.id.pageSwitch).setOnClickListener(v -> pageSwitch());
        findViewById(R.id.page_ToggleButton).setOnClickListener(v -> page_ToggleButton());
        findViewById(R.id.pageEditText).setOnClickListener(v -> pageEditText());
        findViewById(R.id.pageKeyBoardView).setOnClickListener(v -> pageKeyBoardView());
        findViewById(R.id.pageZAxis).setOnClickListener(v -> page_z_axis());
        findViewById(R.id.pageTextClock).setOnClickListener(v -> pageTextClock());
        findViewById(R.id.page_Snackbar).setOnClickListener(v -> page_Snackbar());

        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    FragmentUtils.remove(WidgetActivity.this, R.id.fragmentRoot);
                    visibilityHelper.showBtns();
                } else {
                    finish();
                }
            }
        });
    }

    private void pageViewLocation() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestViewLocationFragment(), TestViewLocationFragment.class.getSimpleName());
    }

    private void pageListView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestListViewFragment(), TestListViewFragment.class.getSimpleName());
    }

    private void pageCustomVew() {
        visibilityHelper.hideBtns();
        startActivity(new Intent(this, CustomViewActivity.class));
    }

    private void pageConstraintLayout() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestConstraintLayoutFragment(), TestConstraintLayoutFragment.class.getSimpleName());
    }

    private void pageLinearLayout() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestLinearLayoutFragment(), TestLinearLayoutFragment.class.getSimpleName());
    }

    private void pageLinearLayoutCanNotChangeColor() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestLinearLayoutCannotChangeColorFragment(), TestLinearLayoutCannotChangeColorFragment.class.getSimpleName());
    }

    private void pageLinearLayoutCanNotChangeColor2() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestLinearLayoutCannotChangeColor2Fragment(), TestLinearLayoutCannotChangeColor2Fragment.class.getSimpleName());
    }

    private void pageLinearLayout4LayoutGravityAndGravity() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestLinearLayout4LayoutGravityAndGravityFragment(), TestLinearLayout4LayoutGravityAndGravityFragment.class.getSimpleName());
    }

    private void pageViewGroup() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestViewGroupFragment(), TestViewGroupFragment.class.getSimpleName());
    }

    private void pageFrameLayout() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestFrameLayoutFragment(), TestFrameLayoutFragment.class.getSimpleName());
    }

    private void pageWebView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestWebViewFragment(), TestWebViewFragment.class.getSimpleName());
    }

    private void pageVideoView() {
        startActivity(new Intent(this, VideoViewRotateScreenTipActivity.class));
    }

    private void pageSurfaceViewPlayVideo() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestSurfaceViewPlayVideoFragment(), TestSurfaceViewPlayVideoFragment.class.getSimpleName());
    }

    private void pageTextView() {
        startActivity(new Intent(this, TestTextViewActivity.class));
    }

    private void pageButton() {
        startActivity(new Intent(this, TestButtonActivity.class));
    }

    private void page_ImageButton() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestImageButtonFragment(), TestImageButtonFragment.class.getSimpleName());
    }

    private void pageImageView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestImageViewFragment(), TestImageViewFragment.class.getSimpleName());
    }

    private void pageKenBurnsImage() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new KenBurnsImageFragment(), KenBurnsImageFragment.class.getSimpleName());
    }

    private void pageImageViewScaleType() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestImageViewScaleTypeFragment(), TestImageViewScaleTypeFragment.class.getSimpleName());
    }

    private void pageRatingBar() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestRatingBarFragment(), TestRatingBarFragment.class.getSimpleName());
    }

    private void pageSeekBar() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestSeekBarFragment(), TestSeekBarFragment.class.getSimpleName());
    }

    private void pageProgressbar() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestProgressBarFragment(), TestProgressBarFragment.class.getSimpleName());
    }

    private void testViewSwitcher() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ViewSwitcherFragment(), ViewSwitcherFragment.class.getSimpleName());
    }

    private void testImageSwitcher() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ImageSwitcherFragment(), ImageSwitcherFragment.class.getSimpleName());
    }

    private void testTextSwitcher() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TextSwitcherFragment(), TextSwitcherFragment.class.getSimpleName());
    }

    private void pageRecyclerView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new DummyRecyclerViewFragment(), DummyRecyclerViewFragment.class.getSimpleName());
    }

    private void recyclerView2() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new DummyRecyclerViewFragment(), DummyRecyclerViewFragment.class.getSimpleName());
    }

    private void pageRecyclerView4DragReorderList() {
        startActivity(new Intent(this, DragAndReorderListActivity.class));
    }

    private void testDragAndDrop() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new DragDropFragment(), DragDropFragment.class.getSimpleName());
    }

    private void page_ViewFlipper() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ViewFlipperFragment(), ViewFlipperFragment.class.getSimpleName());
    }

    private void page_AdapterViewFlipper() {
        visibilityHelper.hideBtns();
//        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new AdapterViewFlipper1Fragment(), AdapterViewFlipper1Fragment.class.getSimpleName());
//        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new AdapterViewFlipper2Fragment(), AdapterViewFlipper2Fragment.class.getSimpleName());
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new AdapterViewFlipper3Fragment(), AdapterViewFlipper3Fragment.class.getSimpleName());
    }


    private void testCalendarView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new CalendarViewFragment(), CalendarViewFragment.class.getSimpleName());
    }

    private void pageDatePickerDialog() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new DatePickerDialogFragment(), DatePickerDialogFragment.class.getSimpleName());
    }

    private void pageTimePickerDialog() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TimePickerDialogFragment(), TimePickerDialogFragment.class.getSimpleName());
    }

    private void pageDatePicker() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new DatePickerFragment(), DatePickerFragment.class.getSimpleName());
    }

    private void pageTimePicker() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TimePickerFragment(), TimePickerFragment.class.getSimpleName());
    }

    private void testNumberPicker() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new NumberPickerFragment(), NumberPickerFragment.class.getSimpleName());
    }

    private void testSearchView() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new SearchViewFragment(), SearchViewFragment.class.getSimpleName());
    }

    private void pageSpinner() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestSpinnerFragment(), TestSpinnerFragment.class.getSimpleName());
    }

    private void pageTabLayout() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestTabLayoutFragment(), TestTabLayoutFragment.class.getSimpleName());
    }

    private void pageRadioButton() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestRadioButtonFragment(), TestRadioButtonFragment.class.getSimpleName());
    }

    private void pageCheckBox() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new CheckBoxFragment(), CheckBoxFragment.class.getSimpleName());
    }

    private void pageSwitch() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestSwitchFragment(), TestSwitchFragment.class.getSimpleName());
    }

    private void page_ToggleButton() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestToggleButtonFragment(), TestToggleButtonFragment.class.getSimpleName());
    }

    private void pageEditText() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestEditTextFragment(), TestEditTextFragment.class.getSimpleName());
    }

    private void pageKeyBoardView() {
        visibilityHelper.hideBtns();
//        showFragment(new TestKeyBoardFragment2());
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestKeyBoardFragment(), TestKeyBoardFragment.class.getSimpleName());
    }

    private void page_z_axis() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ZAxisFragment(), ZAxisFragment.class.getSimpleName());
    }

    private void pageTextClock() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TextClockFragment(), TextClockFragment.class.getSimpleName());
    }

    private void page_Snackbar() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new SnackbarFragment(), SnackbarFragment.class.getSimpleName());
    }
}