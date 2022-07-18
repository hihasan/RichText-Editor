package com.hihasan.richtexteditor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class EditTableFragment : Fragment() {
    @BindView(R.id.et_rows)
    var etRows: EditText? = null

    @BindView(R.id.et_cols)
    var etCols: EditText? = null

    private var mOnTableListener: OnTableListener? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?, ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_edit_table, null)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    @OnClick(R.id.iv_back)
    fun onClickBack() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    @OnClick(R.id.btn_ok)
    fun onClickOK() {
        if (mOnTableListener != null) {
            mOnTableListener!!.onTableOK(etRows!!.text.toString().toInt(),
                etCols!!.text.toString().toInt())
            onClickBack()
        }
    }

    fun setOnTableListener(mOnTableListener: OnTableListener?) {
        this.mOnTableListener = mOnTableListener
    }

    interface OnTableListener {
        fun onTableOK(rows: Int, cols: Int)
    }
}