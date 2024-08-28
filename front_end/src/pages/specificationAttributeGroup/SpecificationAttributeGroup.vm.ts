import { useState, useEffect, useMemo, useCallback } from 'react'
import { Modal } from 'antd'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'

const { confirm } = Modal

const useSpecificationAttributeGroupManageViewModel = () => {
    const [selectedModalRowKeys, setSelectedModalRowKeys] = useState<React.Key[]>([]) // State for modal
    const [isSpinning, setIsSpinning] = useState(false)
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [modalData, setModalData] = useState([])

    // State for filters
    const [groupedFilter, setGroupedFilter] = useState({ pageNo: 1, pageSize: 6 })
    const [ungroupedFilter, setUngroupedFilter] = useState({ pageNo: 1, pageSize: 6 })

    // Grouped API
    const {
        data: groupedData,
        isLoading,
        refetch: refetchGrouped,
    } = useGetAllApi(SpecificationAttributeGroupConfigs.resourceUrl, SpecificationAttributeGroupConfigs.resourceKey, {
        pageNo: groupedFilter.pageNo,
    })

    // Ungrouped API
    const {
        data: ungroupedData,
        isLoading: isLoadingUngrouped,
        error: errorUngrouped,
        refetch: refetchUngrouped,
    } = useGetAllApi(
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributes,
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributesKey,
        {
            pageNo: ungroupedFilter.pageNo,
        },
    )

    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )

    // Handle filters change
    const handleGroupedTableChangeFilter = useCallback((pagination: { current: number; pageSize: number }) => {
        setGroupedFilter({ pageNo: pagination.current, pageSize: pagination.pageSize })
    }, [])

    const handleUngroupedTableChangeFilter = useCallback((pagination: { current: number; pageSize: number }) => {
        setUngroupedFilter({ pageNo: pagination.current, pageSize: pagination.pageSize })
    }, [])

    useEffect(() => {
        if (!isLoading && !isLoadingUngrouped) {
            refetchGrouped()
            refetchUngrouped()
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [isLoading, isLoadingUngrouped, groupedFilter, ungroupedFilter])

    const handleEdit = useCallback((record: SpecificationAttributeResponse) => {
        console.log(`Edit attribute: ${record.name}`)
    }, [])

    const onModalSelectChange = useCallback((newSelectedModalRowKeys: React.Key[]) => {
        setSelectedModalRowKeys(newSelectedModalRowKeys)
    }, [])

    const handleModalDelete = useCallback(() => {
        confirm({
            title: 'Are you sure you want to delete the selected attributes?',
            content: 'This action cannot be undone.',
            onOk: async () => {
                deleteApi(selectedModalRowKeys as number[], {
                    onSuccess: () => {
                        setIsModalVisible(false)
                        setSelectedModalRowKeys([])
                        refetchGrouped()
                        refetchUngrouped()
                    },
                })
            },
            onCancel() {
                console.log('Cancel')
            },
        })
    }, [selectedModalRowKeys, deleteApi, refetchGrouped, refetchUngrouped])

    const showModal = useCallback((record: SpecificationAttributeResponse) => {
        setModalData(record.specificationAttributes)
        setIsModalVisible(true)
    }, [])

    const handleOk = useCallback(() => {
        setIsModalVisible(false)
    }, [])

    const handleCancel = useCallback(() => {
        setIsModalVisible(false)
    }, [])

    const handleReload = useCallback(async () => {
        setIsSpinning(true)
        await new Promise((resolve) => setTimeout(resolve, 1000))
        refetchGrouped()
        refetchUngrouped()
        setIsSpinning(false)
    }, [refetchGrouped, refetchUngrouped])

    const isModalDeleteButtonDisabled = useMemo(() => selectedModalRowKeys.length === 0, [selectedModalRowKeys])

    return {
        groupedData,
        ungroupedData,
        isLoading,
        errorUngrouped,
        handleEdit,
        handleReload,
        isSpinning,
        isModalVisible,
        modalData,
        showModal,
        handleOk,
        handleCancel,
        selectedModalRowKeys,
        rowSelection: {
            selectedRowKeys: selectedModalRowKeys,
            onChange: onModalSelectChange,
        },
        handleModalDelete,
        isModalDeleteButtonDisabled,
        groupedFilter,
        ungroupedFilter,
        handleGroupedTableChangeFilter,
        handleUngroupedTableChangeFilter,
    }
}

export default useSpecificationAttributeGroupManageViewModel
