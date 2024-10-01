import { useState, useCallback } from 'react'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'
import { getSpecAttrGroupCols, getSpecAttrUnGroupCols } from './SpecAttrGroupCols'
import { SpecificationAttributeGroupResponse } from '@/model/SpecificationAttributeGroup'
import { TableRowSelection } from 'antd/es/table/interface'
import { Modal } from 'antd'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import SpecificationAttributeConfigs from '../specificationAttribute/SpecificationAttributeConfigs'
const useSpecificationAttributeGroupManageViewModel = () => {
    // State for filters
    const [groupedFilter, setGroupedFilter] = useState({ pageNo: 1, pageSize: 6 })
    const [ungroupedFilter, setUngroupedFilter] = useState({ pageNo: 1, pageSize: 6 })
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [groupedSelectedRowKeys, setGroupedSelectedRowKeys] = useState<React.Key[]>([])
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
    const { mutate: deleteSpecAttribute } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )
    const { mutate: deleteSpecAttributeGroup } = useDeleteByIdsApi<number>(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKeyGroup,
    )
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }
    const rowSelection: TableRowSelection<SpecificationAttributeResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }
    const handleDelete = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete these attributes?',
            content: 'This action cannot be undone.',
            onOk() {
                deleteSpecAttribute(selectedRowKeys as number[], {
                    onSuccess: () => {
                        setSelectedRowKeys([])
                    },
                })
            },
        })
    }
    const onGroupedSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setGroupedSelectedRowKeys(newSelectedRowKeys)
    }
    const groupRowSelection: TableRowSelection<SpecificationAttributeGroupResponse> = {
        selectedRowKeys: groupedSelectedRowKeys,
        onChange: onGroupedSelectChange,
    }

    const handleGroupedDelete = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete these groups?',
            content: 'This action cannot be undone.',
            onOk() {
                deleteSpecAttributeGroup(groupedSelectedRowKeys as number[], {
                    onSuccess: () => {
                        setGroupedSelectedRowKeys([])
                    },
                })
            },
        })
    }

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
        groupRowSelection,
        handleGroupedDelete,
        rowSelection,
        selectedRowKeys,
        groupedSelectedRowKeys,
        handleDelete,
        handleGroupedTableChangeFilter,
        handleUngroupedTableChangeFilter,
    }
}

export default useSpecificationAttributeGroupManageViewModel
