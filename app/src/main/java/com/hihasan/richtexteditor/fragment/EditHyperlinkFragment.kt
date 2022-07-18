package com.hihasan.richtexteditor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hihasan.richtexteditor.databinding.FragmentEditHyperlinkBinding

class EditHyperlinkFragment : Fragment() {

    private lateinit var binding : FragmentEditHyperlinkBinding

    private var mOnHyperlinkListener: OnHyperlinkListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentEditHyperlinkBinding.inflate(inflater, container, false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        back()

        binding.btnOk.setOnClickListener{
            if (mOnHyperlinkListener != null) {
                mOnHyperlinkListener!!.onHyperlinkOK(binding.etAddress.text.toString(),
                    binding.etDisplayText.text.toString())
                back()
            }
        }
    }

    private fun back(){
        binding.ivBack.setOnClickListener { childFragmentManager.beginTransaction().remove(this).commit() }
    }



    fun setOnHyperlinkListener(mOnHyperlinkListener: OnHyperlinkListener?) {
        this.mOnHyperlinkListener = mOnHyperlinkListener
    }

    interface OnHyperlinkListener {
        fun onHyperlinkOK(address: String?, text: String?)
    }
}