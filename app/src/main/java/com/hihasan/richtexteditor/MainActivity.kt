package com.hihasan.richtexteditor

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.TypedValue
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.hihasan.editor.ActionType
import com.hihasan.editor.RichEditorAction
import com.hihasan.editor.RichEditorCallback
import com.hihasan.richtexteditor.fragment.EditorMenuFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    @BindView(R.id.wv_container)
    val mWebView: WebView? = null

    @BindView(R.id.fl_action)
    val flAction: FrameLayout? = null

    @BindView(R.id.ll_action_bar_container)
    val llActionBarContainer: LinearLayout? = null

    var isKeyboardShowing = false
    val htmlContent = "<p>Hello World</p>"

    var mRichEditorAction: RichEditorAction? = null
    var mRichEditorCallback: RichEditorCallback? = null

    var mEditorMenuFragment: EditorMenuFragment? = null

    val mActionTypeList: List<ActionType> =
        Arrays.asList<ActionType>(ActionType.BOLD, ActionType.ITALIC, ActionType.UNDERLINE,
            ActionType.STRIKETHROUGH, ActionType.SUBSCRIPT, ActionType.SUPERSCRIPT,
            ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
            ActionType.H5, ActionType.H6, ActionType.INDENT, ActionType.OUTDENT,
            ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
            ActionType.JUSTIFY_FULL, ActionType.ORDERED, ActionType.UNORDERED, ActionType.LINE,
            ActionType.BLOCK_CODE, ActionType.BLOCK_QUOTE, ActionType.CODE_VIEW)

    val mActionTypeIconList = Arrays.asList(R.drawable.ic_format_bold, R.drawable.ic_format_italic,
        R.drawable.ic_format_underlined, R.drawable.ic_format_strikethrough,
        R.drawable.ic_format_subscript, R.drawable.ic_format_superscript,
        R.drawable.ic_format_para, R.drawable.ic_format_h1, R.drawable.ic_format_h2,
        R.drawable.ic_format_h3, R.drawable.ic_format_h4, R.drawable.ic_format_h5,
        R.drawable.ic_format_h6, R.drawable.ic_format_indent_decrease,
        R.drawable.ic_format_indent_increase, R.drawable.ic_format_align_left,
        R.drawable.ic_format_align_center, R.drawable.ic_format_align_right,
        R.drawable.ic_format_align_justify, R.drawable.ic_format_list_numbered,
        R.drawable.ic_format_list_bulleted, R.drawable.ic_line, R.drawable.ic_code_block,
        R.drawable.ic_format_quote, R.drawable.ic_code_review)

    val REQUEST_CODE_CHOOSE = 0

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initImageLoader()
        initView()
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f,
            getResources().getDisplayMetrics()).toInt()
        val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9f,
            getResources().getDisplayMetrics()).toInt()
        var i = 0
        val size = mActionTypeList.size
        while (i < size) {
            val actionImageView = ActionImageView(this)
            actionImageView.setLayoutParams(LinearLayout.LayoutParams(width, width))
            actionImageView.setPadding(padding, padding, padding, padding)
            actionImageView.setActionType(mActionTypeList[i])
            actionImageView.setTag(mActionTypeList[i])
            actionImageView.setActivatedColor(R.color.colorAccent)
            actionImageView.setDeactivatedColor(R.color.tintColor)
            actionImageView.setRichEditorAction(mRichEditorAction)
            actionImageView.setBackgroundResource(R.drawable.btn_colored_material)
            actionImageView.setImageResource(mActionTypeIconList[i])
            actionImageView.setOnClickListener(View.OnClickListener { actionImageView.command() })
            llActionBarContainer!!.addView(actionImageView)
            i++
        }
        mEditorMenuFragment = EditorMenuFragment()
        mEditorMenuFragment.setActionClickListener(MOnActionPerformListener(mRichEditorAction))
        val fm: FragmentManager = getSupportFragmentManager()
        fm.beginTransaction()
            .add(R.id.fl_action, mEditorMenuFragment, EditorMenuFragment::class.java.getName())
            .commit()
        KeyboardUtils.registerSoftInputChangedListener(this) { height ->
            isKeyboardShowing = height > 0
            if (height > 0) {
                flAction!!.visibility = View.INVISIBLE
                val params = flAction.layoutParams
                params.height = height
                flAction.layoutParams = params
            } else if (flAction!!.visibility != View.VISIBLE) {
                flAction.visibility = View.GONE
            }
        }
    }

    /**
     * ImageLoader for insert Image
     */
    open fun initImageLoader() {
        val imagePicker: ImagePicker = ImagePicker.getInstance()
        imagePicker.setImageLoader(GlideImageLoader())
        imagePicker.setShowCamera(true)
        imagePicker.setCrop(false)
        imagePicker.setMultiMode(false)
        imagePicker.setSaveRectangle(true)
        imagePicker.setStyle(CropImageView.Style.RECTANGLE)
        imagePicker.setFocusWidth(800)
        imagePicker.setFocusHeight(800)
        imagePicker.setOutPutX(256)
        imagePicker.setOutPutY(256)
    }

    open fun initView() {
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest,
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        mWebView.webChromeClient = CustomWebChromeClient()
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.domStorageEnabled = true
        mRichEditorCallback = MRichEditorCallback()
        mWebView.addJavascriptInterface(mRichEditorCallback, "MRichEditor")
        mWebView.loadUrl("file:///android_asset/richEditor.html")
        mRichEditorAction = RichEditorAction(mWebView)
    }

    class CustomWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                if (!TextUtils.isEmpty(htmlContent)) {
                    mRichEditorAction.insertHtml(htmlContent)
                }
                KeyboardUtils.showSoftInput(this@RichEditorActivity)
            }
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
        }
    }

    @OnClick(R.id.iv_action)
    fun onClickAction() {
        if (flAction!!.visibility == View.VISIBLE) {
            flAction.visibility = View.GONE
        } else {
            if (isKeyboardShowing) {
                KeyboardUtils.hideSoftInput(this@RichEditorActivity)
            }
            flAction.visibility = View.VISIBLE
        }
    }

    val onGetHtmlListener: OnGetHtmlListener = label@ OnGetHtmlListener { html ->
        if (TextUtils.isEmpty(html)) {
            Toast.makeText(this@RichEditorActivity, "Empty Html String", Toast.LENGTH_SHORT)
                .show()
            return@label
        }
        Toast.makeText(this@RichEditorActivity, html, Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.iv_get_html)
    fun onClickGetHtml() {
        mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener)
    }

    @OnClick(R.id.iv_action_undo)
    fun onClickUndo() {
        mRichEditorAction.undo()
    }

    @OnClick(R.id.iv_action_redo)
    fun onClickRedo() {
        mRichEditorAction.redo()
    }

    @OnClick(R.id.iv_action_txt_color)
    fun onClickTextColor() {
        mRichEditorAction.foreColor("blue")
    }

    @OnClick(R.id.iv_action_txt_bg_color)
    fun onClickHighlight() {
        mRichEditorAction.backColor("red")
    }

    @OnClick(R.id.iv_action_line_height)
    fun onClickLineHeight() {
        mRichEditorAction.lineHeight(20)
    }

    @OnClick(R.id.iv_action_insert_image)
    fun onClickInsertImage() {
        val intent = Intent(this, ImageGridActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CHOOSE)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null && requestCode == REQUEST_CODE_CHOOSE) {
            val images: ArrayList<ImageItem>? =
                data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>?
            if (images != null && !images.isEmpty()) {

                //1.Insert the Base64 String (Base64.NO_WRAP)
                val imageItem: ImageItem = images[0]
                mRichEditorAction.insertImageData(imageItem.name,
                    encodeFileToBase64Binary(imageItem.path))

                //2.Insert the ImageUrl
                //mRichEditorAction.insertImageUrl(
                //    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }

    open fun encodeFileToBase64Binary(filePath: String): String? {
        val bytes: ByteArray = FileIOUtils.readFile2BytesByStream(filePath)
        val encoded = Base64.encode(bytes, Base64.NO_WRAP)
        return String(encoded)
    }

    @OnClick(R.id.iv_action_insert_link)
    fun onClickInsertLink() {
        KeyboardUtils.hideSoftInput(this@RichEditorActivity)
        val fragment = EditHyperlinkFragment()
        fragment.setOnHyperlinkListener { address, text ->
            mRichEditorAction.createLink(text,
                address)
        }
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fl_container, fragment, EditHyperlinkFragment::class.java.getName())
            .commit()
    }

    @OnClick(R.id.iv_action_table)
    fun onClickInsertTable() {
        KeyboardUtils.hideSoftInput(this@RichEditorActivity)
        val fragment = EditTableFragment()
        fragment.setOnTableListener { rows, cols -> mRichEditorAction.insertTable(rows, cols) }
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fl_container, fragment, EditHyperlinkFragment::class.java.getName())
            .commit()
    }

    fun onResume() {
        super.onResume()
    }

    fun onPause() {
        super.onPause()
        if (flAction!!.visibility == View.INVISIBLE) {
            flAction.visibility = View.GONE
        }
    }

    fun onDestroy() {
        super.onDestroy()
    }

    class MRichEditorCallback : RichEditorCallback() {
        fun notifyFontStyleChange(type: ActionType?, value: String?) {
            val actionImageView: ActionImageView? =
                llActionBarContainer!!.findViewWithTag(type) as ActionImageView?
            if (actionImageView != null) {
                actionImageView.notifyFontStyleChange(type, value)
            }
            if (mEditorMenuFragment != null) {
                mEditorMenuFragment.updateActionStates(type, value)
            }
        }
    }

    class MOnActionPerformListener(mRichEditorAction: RichEditorAction) :
        OnActionPerformListener {
        private val mRichEditorAction: RichEditorAction
        fun onActionPerform(type: ActionType?, vararg values: Any) {
            if (mRichEditorAction == null) {
                return
            }
            var value = ""
            if (values != null && values.size > 0) {
                value = values[0] as String
            }
            when (type) {
                SIZE -> mRichEditorAction.fontSize(value.toDouble())
                LINE_HEIGHT -> mRichEditorAction.lineHeight(value.toDouble())
                FORE_COLOR -> mRichEditorAction.foreColor(value)
                BACK_COLOR -> mRichEditorAction.backColor(value)
                FAMILY -> mRichEditorAction.fontName(value)
                IMAGE -> onClickInsertImage()
                LINK -> onClickInsertLink()
                TABLE -> onClickInsertTable()
                BOLD, ITALIC, UNDERLINE, SUBSCRIPT, SUPERSCRIPT, STRIKETHROUGH, JUSTIFY_LEFT, JUSTIFY_CENTER, JUSTIFY_RIGHT, JUSTIFY_FULL, CODE_VIEW, ORDERED, UNORDERED, INDENT, OUTDENT, BLOCK_QUOTE, BLOCK_CODE, NORMAL, H1, H2, H3, H4, H5, H6, LINE -> {
                    val actionImageView: ActionImageView =
                        llActionBarContainer!!.findViewWithTag(type)
                    if (actionImageView != null) {
                        actionImageView.performClick()
                    }
                }
                else -> {}
            }
        }

        init {
            this.mRichEditorAction = mRichEditorAction
        }
    }
}