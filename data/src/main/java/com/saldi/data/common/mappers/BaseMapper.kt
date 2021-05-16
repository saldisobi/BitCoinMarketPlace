package com.saldi.data.common.mappers

/**
 * Created by Sourabh on 10/5/21
 */
interface BaseMapper<in I, out O> {
    operator fun invoke(input: I): O
}