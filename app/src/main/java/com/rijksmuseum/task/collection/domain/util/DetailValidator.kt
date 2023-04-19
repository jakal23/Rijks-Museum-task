package com.rijksmuseum.task.collection.domain.util

import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParamsModel
import com.rijksmuseum.task.util.validator.Errors
import com.rijksmuseum.task.util.validator.Validator

class DetailValidator : Validator<CollectionDetailParamsModel> {

    private val objectNumberPattern = Regex("[a-zA-Z0-9-.]*")

    override fun validate(param: CollectionDetailParamsModel): Errors {
        val errors = Errors(param)

        if (param.objectNumber.isEmpty()) {
            errors.addError("The field is required", "objectNumber")
        } else if (!param.objectNumber.matches(objectNumberPattern)) {
            errors.addError(
                "The field must contain numbers or latter's or minus symbol",
                "objectNumber"
            )
        }

        return errors
    }

}