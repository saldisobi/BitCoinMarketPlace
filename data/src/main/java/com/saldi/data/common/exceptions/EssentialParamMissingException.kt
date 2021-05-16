package com.saldi.data.common.exceptions


/**
 * Created by Sourabh on 10/5/21
 * Exception thrown when an essential parameter is missing in the backend/network response.
 */
class EssentialParamMissingException(missingParams: String, rawObject: Any) :
    RuntimeException("The params: $missingParams are missing in received object: $rawObject")
