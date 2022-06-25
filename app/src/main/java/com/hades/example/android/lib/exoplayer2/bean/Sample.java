/*
 * Copyright (C) 2019 The Android Open Source Project
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
package com.hades.example.android.lib.exoplayer2.bean;

import static com.hades.example.android.lib.exoplayer2.PlayerActivity.ACTION_VIEW_LIST;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.URI_EXTRA;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public abstract class Sample {
    public static Sample createFromIntent(Intent intent) {
        if (ACTION_VIEW_LIST.equals(intent.getAction())) {
            ArrayList<String> intentUris = new ArrayList<>();
            int index = 0;
            while (intent.hasExtra(URI_EXTRA + "_" + index)) {
                intentUris.add(intent.getStringExtra(URI_EXTRA + "_" + index));
                index++;
            }
            UriSample[] children = new UriSample[intentUris.size()];
            for (int i = 0; i < children.length; i++) {
                Uri uri = Uri.parse(intentUris.get(i));
                children[i] = UriSample.createFromIntent(uri, intent, "_" + i);
            }
            return new PlaylistSample(/* name= */ null, children);
        } else {
            return UriSample.createFromIntent(intent.getData(), intent, "");
        }
    }

    @Nullable
    public final String name;

    public Sample(String name) {
        this.name = name;
    }

    public abstract void addToIntent(Intent intent);
}
