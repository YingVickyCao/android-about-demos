package com.hades.example.android.widget.snackbar

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.View.TEXT_ALIGNMENT_GRAVITY
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hades.example.android.R
import com.hades.example.android.databinding.SnakebarBinding

private const val TAG = "SnackbarFragment"

class SnackbarFragment : Fragment() {

    private var _binding: SnakebarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SnakebarBinding.inflate(inflater, container, false)
        binding.showNormalSnackBar1.setOnClickListener { this.showNormalSnackBar1() }
        binding.showNormalSnackBar2.setOnClickListener { this.showNormalSnackBar2() }
        binding.showNormalSnackBar3.setOnClickListener { this.showNormalSnackBar3() }
        binding.onlyShowOneSnackBar.setOnClickListener { this.onlyShowOneSnackBar() }
        binding.lengthShort.setOnClickListener { this.lengthShort() }
        binding.lengthLong.setOnClickListener { this.lengthLong() }
        binding.lengthIndefinite.setOnClickListener { this.lengthIndefinite() }
        binding.addAction.setOnClickListener { this.addAction() }
        binding.changeActionAndMessageTextColor.setOnClickListener { this.changeActionAndMessageTextColor() }
        binding.changeBackgroundColor.setOnClickListener { this.changeBackgroundColor() }
        binding.addCallback.setOnClickListener { this.addCallback() }
        binding.setTextPosition.setOnClickListener { this.setTextPosition() }
        binding.setRadiusBorder.setOnClickListener { this.setRadiusBorder() }
        binding.test.setOnClickListener { this.test() }
        return binding.root
    }

    private fun showNormalSnackBar1() {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), "Normal Snackbar 1", Snackbar.LENGTH_SHORT).show() // way1
    }

    private fun showNormalSnackBar2() {
        Snackbar.make(binding.root, "Normal Snackbar 2", Snackbar.LENGTH_SHORT).show() // way3
    }

    private fun showNormalSnackBar3() {
        if (null != context) {
            Snackbar.make(requireContext(), binding.root, "Normal Snackbar 3", Snackbar.LENGTH_SHORT).show() // way3
        }
    }

    private fun onlyShowOneSnackBar() {
        // 系统不会同时显示多个Snackbar对象。因此，视图显示另一个Snackbar，系统会将新的Snackbar对象加入队列，并在当前Snackbar过期或关闭后显示它。
        // 同时显示1 和2， 那么1 直接被取消，然后直接显示2
        showNormalSnackBar1()
        showNormalSnackBar2()
    }

    private fun lengthShort() {
        // Snackbar.LENGTH_SHORT 自动取消
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT).show()
    }

    private fun lengthLong() {
        // Snackbar.LENGTH_LONG 自动取消
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_LONG).show()
    }


    private fun lengthIndefinite() {
        // TODO: Snackbar.LENGTH_INDEFINITE 不自动消失，要非手动取消
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun addAction() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {
                Log.d(TAG, "addAction: Click ok")
            }.show()
    }

    // snackbar_action
    // snackbar_text
    //   mSnackbar.getView().setBackgroundColor(backgroundColor);
    //        ((TextView) mSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
    //        ((Button) mSnackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
    private fun changeActionAndMessageTextColor() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {

            }.setTextColor(resources.getColor(R.color.red, context?.theme)).setActionTextColor(resources.getColor(R.color.red, context?.theme)).show()
    }

    private fun changeBackgroundColor() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {
                Toast.makeText(activity, "OK clicked", Toast.LENGTH_SHORT).show()
            }.setBackgroundTint(resources.getColor(R.color.red, context?.theme)).show()
    }

    private fun addCallback() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {

            }.addCallback(object : Snackbar.Callback() {
                /**
                 * onDismissed()方法中的 event 事件有 5 种：
                 *
                 * 滑动消失的时候调用
                 * public static final int DISMISS_EVENT_SWIPE=0;
                 *
                 * 点击 Action 消失的时候调用
                 * public static final intDISMISS_EVENT_ACTION=1;
                 *
                 * 超时消失的时候调用
                 * public static final intDISMISS_EVENT_TIMEOUT=2;
                 *
                 * 手动调用 dismiss()方法时调用
                 * public static final intDISMISS_EVENT_MANUAL=3;
                 *
                 * 一个新的 SnackBar 出现的时候调用
                 * public static final intDISMISS_EVENT_CONSECUTIVE=4;
                 */
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    Toast.makeText(activity, "onDismissed invoked,event type=$event", Toast.LENGTH_SHORT).show()
                }

                override fun onShown(sb: Snackbar?) {
                    Toast.makeText(activity, "onShown invoked", Toast.LENGTH_SHORT).show()
                }
            }).show()
    }


    private fun setTextPosition() {
        val snackbar = Snackbar.make(binding.root, "Text is center", Snackbar.LENGTH_SHORT).setAction("OK") {
            Log.d(TAG, "addAction: Click ok")
        }
        val message: TextView = snackbar.view.findViewById(R.id.snackbar_text)
        // 文字左对齐默认
        // 居中对齐
        message.textAlignment = TEXT_ALIGNMENT_CENTER
        message.gravity = Gravity.CENTER
        // 文字右对齐
        message.textAlignment = TEXT_ALIGNMENT_GRAVITY
        message.gravity = Gravity.END
        snackbar.show()
    }

    // 布局的圆角半径值及边框颜色及边框宽度
    private fun setRadiusBorder() {
        val snackbar = Snackbar.make(binding.root, "set radius", Snackbar.LENGTH_SHORT).setAction("OK") {
            Log.d(TAG, "addAction: Click ok")
        }
        val drawable: Drawable = snackbar.view.background
        var background: GradientDrawable? = null
        if (drawable is GradientDrawable) {
            background = drawable
        } else if (drawable is ColorDrawable) {
            val backgroundColor = drawable.color
            background = GradientDrawable()
            background.setColor(backgroundColor)
        }
        if (background != null) {
            background.cornerRadius = resources.getDimension(R.dimen.size_3) // 局的圆角半径值

            val strokeWidth: Int = resources.getDimensionPixelOffset(R.dimen.size_10)
            val newStrokeWidth: Int = if (strokeWidth <= 0) 1 else if (strokeWidth >= snackbar.view.findViewById<View>(R.id.snackbar_text).paddingTop) 2 else strokeWidth
            background.setStroke(newStrokeWidth, resources.getColor(R.color.red, snackbar.context.theme)) // 边框颜色及边框宽度
            snackbar.view.background = background
        }
        snackbar.show()
    }

    // 布局的圆角半径值及边框颜色及边框宽度
    private fun test() {
        val snackbar = Snackbar.make(binding.root, "set radius", Snackbar.LENGTH_SHORT).setAction("OK") {
            Log.d(TAG, "addAction: Click ok")
        }
        val drawable: Drawable = snackbar.view.background
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}