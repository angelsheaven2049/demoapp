/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.basicsample.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

object BindingAdapters {


    @BindingAdapter("app:hideIfEmpty")
    @JvmStatic fun hideIfEmpty(view: View, text:String){
        view.visibility = if(text.isEmpty()) View.GONE else View.VISIBLE
    }

    @BindingAdapter("app:error")
    @JvmStatic fun error(view: View,text: String?){
        if(view is TextInputEditText){
            view.error = text
        }
    }
}