package com.saldi.data.features.bitcoin.mappers

import com.saldi.data.common.constants.DataConstants.BLOCKCHAIN_API_OK_STATUS
import com.saldi.data.common.exceptions.EssentialParamMissingException
import com.saldi.data.common.mappers.BaseMapper
import com.saldi.data.features.bitcoin.entities.BitcoinDataRaw
import com.saldi.data.features.bitcoin.entities.BitcoinValueRaw
import com.saldi.domain.features.bitcoin.entities.BitcoinData
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import javax.inject.Inject


/**
 * Created by Sourabh on 10/5/21
 */
@Suppress("UNCHECKED_CAST")
class BitcoinDataMapper @Inject constructor() :
    BaseMapper<BitcoinDataRaw, BitcoinValue> {

    companion object {

        const val statusParam = "status"
        const val nameParam = "name"
        const val unitParam = "unit"
        const val periodParam = "period"
        const val descriptionParam = "description"
        const val valuesParam = "values"
    }

    override fun invoke(input: BitcoinDataRaw) = assertEssentialParams(input)

    /**
     * Function used to check the essential parameters and to instantiate the mapped object in case
     * that everything went ok.
     *
     * @param raw The raw entity to be checked and parsed.
     * @return Returns a mapped and checked [BitcoinValue] in case that the
     * assertions went well.
     */
    @Suppress("UNCHECKED_CAST")
    private fun assertEssentialParams(raw: BitcoinDataRaw): BitcoinValue {

        val checkedParamsMap = mutableMapOf<String, Any>()
        val missingParamsList = mutableListOf<String>()

        with(raw) {

            checkBaseValues(
                checkedParamsMap,
                missingParamsList,
                raw.name,
                raw.unit,
                raw.period,
                raw.description
            )

            // Values list
            return checkValueList(checkedParamsMap, missingParamsList, raw)
        }
    }

    private fun BitcoinDataRaw.checkValueList(
        checkedParamsMap: MutableMap<String, Any>,
        missingParamsList: MutableList<String>,
        raw: BitcoinDataRaw
    ): BitcoinValue {
        val filteredValueList = values.mapAndFilter()
        if (filteredValueList.isNotEmpty()) {
            checkedParamsMap[valuesParam] = filteredValueList
        } else {
            missingParamsList.add(valuesParam)
        }

        // We check whether everything went ok, and we take the necessary action.
        if (missingParamsList.isNotEmpty()) {
            throw EssentialParamMissingException(missingParamsList.toString(), raw)
        } else {
            return BitcoinValue(
                status = checkedParamsMap[statusParam] as String,
                name = checkedParamsMap[nameParam] as String,
                unit = checkedParamsMap[unitParam] as String,
                period = checkedParamsMap[periodParam] as String,
                description = checkedParamsMap[descriptionParam] as String,
                values = checkedParamsMap[valuesParam] as List<BitcoinData>
            )
        }
    }

    private fun BitcoinDataRaw.checkBaseValues(
        checkedParamsMap: MutableMap<String, Any>,
        missingParamsList: MutableList<String>,
        name: String?,
        unit: String?,
        period: String?,
        description: String?
    ) {
        // Status
        if (status != null && status.equals(BLOCKCHAIN_API_OK_STATUS, true)) {
            checkedParamsMap[statusParam] = status
        } else {
            missingParamsList.add(statusParam)
        }

        // Name
        if (name != null && name.isNotEmpty()) {
            checkedParamsMap[nameParam] = name
        } else {
            missingParamsList.add(nameParam)
        }

        // Unit
        if (unit != null && unit.isNotEmpty()) {
            checkedParamsMap[unitParam] = unit
        } else {
            missingParamsList.add(unitParam)
        }

        // Period
        if (period != null && period.isNotEmpty()) {
            checkedParamsMap[periodParam] = period
        } else {
            missingParamsList.add(periodParam)
        }

        // Description
        if (description != null && description.isNotEmpty()) {
            checkedParamsMap[descriptionParam] = description
        } else {
            missingParamsList.add(descriptionParam)
        }
    }

    /**
     * Function that will map a list of nullable [BitcoinValueRaw] to a safe list of
     * [BitcoinData].
     * IMPORTANT: For the sake of this sample app, we will just get rid of any entry that has
     * parameter issues. In a real production app, that behaviour would probably be different.
     *
     * @return Returns the safe list of [BitcoinData].
     */
    private fun List<BitcoinValueRaw?>?.mapAndFilter(): List<BitcoinData> =
        this?.mapNotNull {
            if (it?.timestamp != null && it.yAxisValue != null) {
                BitcoinData(it.timestamp, it.yAxisValue)
            } else {
                null
            }
        } ?: emptyList()
}
