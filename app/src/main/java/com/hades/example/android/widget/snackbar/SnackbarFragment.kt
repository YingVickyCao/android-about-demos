package com.hades.example.android.widget.snackbar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hades.example.android.R
import com.hades.example.android.databinding.SnakebarBinding

private const val TAG = "SnackbarFragment"

class SnackbarFragment : Fragment() {

    private var _binding: SnakebarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            }
            .show()
    }

    private fun changeActionAndMessageTextColor() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {

            }
            .setTextColor(resources.getColor(R.color.red, context?.theme))
            .setActionTextColor(resources.getColor(R.color.red, context?.theme))
            .show()
    }

    private fun changeBackgroundColor() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {
                Toast.makeText(activity, "OK clicked", Toast.LENGTH_SHORT).show()
            }
            .setBackgroundTint(resources.getColor(R.color.red, context?.theme))
            .show()
    }

    private fun addCallback() {
        Snackbar.make(requireActivity(), binding.root, "Snackbar length is long", Snackbar.LENGTH_SHORT)
            // 增加了action，右边自动增加一个ok 文字。Snackbar.LENGTH_SHORT -> 不点ok,自动消失
            .setAction("OK") {

            }
            .addCallback(object : Snackbar.Callback() {
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
            })
            .show()
    }

    private fun test() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}