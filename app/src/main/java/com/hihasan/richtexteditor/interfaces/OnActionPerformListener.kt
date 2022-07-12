package com.hihasan.richtexteditor.interfaces

import com.hihasan.editor.ActionType

interface OnActionPerformListener {
    fun onActionPerform(type: ActionType?, vararg values: Any?)
}