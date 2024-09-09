import { useState, useCallback } from 'react'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'
import { getSpecAttrGroupCols, getSpecAttrUnGroupCols } from './SpecAttrGroupCols'
import { SpecificationAttributeGroupResponse } from '@/model/SpecificationAttributeGroup'

const useSpecificationAttributeGroupManageViewModel = () => {
    // State for filters
    const [groupedFilter, setGroupedFilter] = useState({ pageNo: 1, pageSize: 6 })
    const [ungroupedFilter, setUngroupedFilter] = useState({ pageNo: 1, pageSize: 6 })

    // Grouped API
    const { data: groupedData } = useGetAllApi<SpecificationAttributeGroupResponse>(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
        { pageNo: groupedFilter.pageNo },
    )

    // Ungrouped API
    const { data: ungroupedData } = useGetAllApi<SpecificationAttributeResponse>(
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributes,
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributesKey,
        {
            pageNo: ungroupedFilter.pageNo,
        },
    )

    // Handle filters change
    const handleGroupedTableChangeFilter = useCallback((pagination: { current: number; pageSize: number }) => {
        setGroupedFilter({ pageNo: pagination.current, pageSize: pagination.pageSize })
    }, [])

    const handleUngroupedTableChangeFilter = useCallback((pagination: { current: number; pageSize: number }) => {
        setUngroupedFilter({ pageNo: pagination.current, pageSize: pagination.pageSize })
    }, [])

    const groupColumns = getSpecAttrGroupCols
    const unGroupColumns = getSpecAttrUnGroupCols

    return {
        groupedData,
        ungroupedData,
        groupColumns,
        unGroupColumns,
        groupedFilter,
        ungroupedFilter,
        handleGroupedTableChangeFilter,
        handleUngroupedTableChangeFilter,
    }
}

export default useSpecificationAttributeGroupManageViewModel
