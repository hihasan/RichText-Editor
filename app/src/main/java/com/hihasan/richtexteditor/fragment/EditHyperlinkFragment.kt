package com.hihasan.richtexteditor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class EditHyperlinkFragment : Fragment() {
    @BindView(R.id.et_address)
    var etAddress: EditText? = null

    @BindView(R.id.et_display_text)
    var etDisplayText: EditText? = null
    private var mOnHyperlinkListener: OnHyperlinkListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_edit_hyperlink, null)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    @OnClick(R.id.iv_back)
    fun onClickBack() {
        fragmentManager!!.beginTransaction().remove(this).commit()
    }

    @OnClick(R.id.btn_ok)
    fun onClickOK() {
        if (mOnHyperlinkListener != null) {
            mOnHyperlinkListener!!.onHyperlinkOK(etAddress!!.text.toString(),
                etDisplayText!!.text.toString())
            onClickBack()
        }
    }

    fun setOnHyperlinkListener(mOnHyperlinkListener: OnHyperlinkListener?) {
        this.mOnHyperlinkListener = mOnHyperlinkListener
    }

    interface OnHyperlinkListener {
        fun onHyperlinkOK(address: String?, text: String?)
    }
}