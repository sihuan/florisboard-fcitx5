/*
 * Copyright (C) 2021 Patrick Goldinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.patrickgold.florisboard.ime.nlp

import dev.patrickgold.florisboard.common.NativeInstanceWrapper
import dev.patrickgold.florisboard.common.NativePtr

@JvmInline
value class SuggestionList private constructor(
    private val _nativePtr: NativePtr
) : Collection<String>, NativeInstanceWrapper {
    companion object {
        fun new(maxSize: Int): SuggestionList {
            val nativePtr = nativeInitialize(maxSize)
            return SuggestionList(nativePtr)
        }

        /*external*/ fun nativeInitialize(maxSize: Int): NativePtr = TODO()
        /*external*/ fun nativeDispose(nativePtr: NativePtr) : Nothing = TODO()
        /*external*/ fun nativeAdd(nativePtr: NativePtr, word: Word, freq: Freq): Boolean = TODO()
        /*external*/ fun nativeClear(nativePtr: NativePtr) : Nothing = TODO()
        /*external*/ fun nativeContains(nativePtr: NativePtr, element: Word): Boolean = TODO()
        /*external*/ fun nativeGetOrNull(nativePtr: NativePtr, index: Int): Word? = TODO()
        /*external*/ fun nativeGetIsPrimaryTokenAutoInsert(nativePtr: NativePtr): Boolean = TODO()
        /*external*/ fun nativeSetIsPrimaryTokenAutoInsert(nativePtr: NativePtr, v: Boolean) : Nothing = TODO()
        /*external*/ fun nativeSize(nativePtr: NativePtr): Int = TODO()
    }

    override val size: Int
        get() = nativeSize(_nativePtr)

    fun add(word: Word, freq: Freq): Boolean {
        return nativeAdd(_nativePtr, word, freq)
    }

    fun clear() {
        nativeClear(_nativePtr)
    }

    override fun contains(element: Word): Boolean {
        return nativeContains(_nativePtr, element)
    }

    override fun containsAll(elements: Collection<Word>): Boolean {
        elements.forEach { if (!contains(it)) return false }
        return true
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int): Word {
        val element = getOrNull(index)
        if (element == null) {
            throw IndexOutOfBoundsException("The specified index $index is not within the bounds of this list!")
        } else {
            return element
        }
    }

    fun getOrNull(index: Int): Word? {
        return nativeGetOrNull(_nativePtr, index)
    }

    override fun isEmpty(): Boolean = size <= 0

    val isPrimaryTokenAutoInsert: Boolean
        get() = nativeGetIsPrimaryTokenAutoInsert(_nativePtr)

    override fun iterator(): Iterator<Word> {
        return SuggestionListIterator(this)
    }

    override fun nativePtr(): NativePtr {
        return _nativePtr
    }

    override fun dispose() {
        nativeDispose(_nativePtr)
    }

    class SuggestionListIterator internal constructor (
        private val suggestionList: SuggestionList
    ) : Iterator<Word> {
        var index = 0

        override fun next(): Word = suggestionList[index++]

        override fun hasNext(): Boolean = suggestionList.getOrNull(index) != null
    }
}
