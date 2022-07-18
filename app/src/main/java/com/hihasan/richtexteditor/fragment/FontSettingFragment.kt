package com.hihasan.richtexteditor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hihasan.richtexteditor.adapter.FontSettingAdapter
import java.util.*

class FontSettingFragment : Fragment() {
    private val fontFamilyList =
        Arrays.asList("Arial", "Arial Black", "Comic Sans MS", "Courier New", "Helvetica Neue",
            "Helvetica", "Impact", "Lucida Grande", "Tahoma", "Times New Roman", "Verdana")
    private val fontSizeList =
        Arrays.asList("12", "14", "16", "18", "20", "22", "24", "26", "28", "36")
    private val fontLineHeightList = Arrays.asList("1.0", "1.2", "1.4", "1.6", "1.8", "2.0", "3.0")

    @BindView(R.id.rv_container)
    var rvContainer: RecyclerView? = null
    private var mAdapter: FontSettingAdapter? = null
    private var mOnResultListener: OnResultListener? = null
    private var dataSourceList = fontSizeList
    private var type = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_font_setting, null)
        ButterKnife.bind(this, rootView)
        type = arguments!!.getInt(TYPE)
        if (type == TYPE_SIZE) {
            dataSourceList = fontSizeList
        } else if (type == TYPE_LINE_HEIGHT) {
            dataSourceList = fontLineHeightList
        } else if (type == TYPE_FONT_FAMILY) {
            dataSourceList = fontFamilyList
        }
        initRecyclerView()
        return rootView
    }

    private fun initRecyclerView() {
        rvContainer!!.layoutManager = LinearLayoutManager(context)
        mAdapter = FontSettingAdapter(dataSourceList)
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            if (mOnResultListener != null) {
                mOnResultListener!!.onResult(dataSourceList[position])
                val fm = fragmentManager
                fm!!.beginTransaction()
                    .remove(this@FontSettingFragment)
                    .show(fm.findFragmentByTag(EditorMenuFragment::class.java.name)!!)
                    .commit()
            }
        }
        rvContainer!!.adapter = mAdapter
    }

    interface OnResultListener {
        fun onResult(result: String?)
    }

    fun setOnResultListener(mOnResultListener: OnResultListener?) {
        this.mOnResultListener = mOnResultListener
    }

    companion object {
        const val TYPE = "type"
        const val TYPE_SIZE = 0
        const val TYPE_LINE_HEIGHT = 1
        const val TYPE_FONT_FAMILY = 2
    }
}