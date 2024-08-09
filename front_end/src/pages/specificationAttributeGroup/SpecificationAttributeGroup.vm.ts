import { useState, useEffect } from 'react'
import { Modal, PaginationProps } from 'antd'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'

const { confirm } = Modal

const useSpecificationAttributeGroupManageViewModel = () => {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [selectedModalRowKeys, setSelectedModalRowKeys] = useState<React.Key[]>([]) // New state for modal
    const [isSpinning, setIsSpinning] = useState(false)
    const [current, setCurrent] = useState(1)
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [modalData, setModalData] = useState([])
    const onChange: PaginationProps['onChange'] = (page) => {
        setCurrent(page)
    }
    const {
        data,
        isLoading,
        error,
        refetch: refetchGrouped,
    } = useGetAllApi(SpecificationAttributeGroupConfigs.resourceUrl, SpecificationAttributeGroupConfigs.resourceKey)

    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
    )

    const {
        data: ungroupedAttributesData,
        isLoading: isLoadingUngrouped,
        error: errorUngrouped,
        refetch: refetchUngrouped,
    } = useGetAllApi(
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributes,
        SpecificationAttributeGroupConfigs.resourceGetUngroupedAttributesKey,
        {
            pageNo: current - 1,
        },
    )

    const handleEdit = (record: SpecificationAttributeResponse) => {
        console.log(`Edit attribute: ${record.name}`)
    }

    useEffect(() => {
        if (!isLoading && !isLoadingUngrouped) {
            refetchGrouped()
            refetchUngrouped()
        }
    }, [isLoading, isLoadingUngrouped])

    if (isLoading || isLoadingUngrouped) {
        return { isLoading: true }
    }

    if (error || errorUngrouped) {
        return { error: error?.message || errorUngrouped?.message }
    }

    const ungroupedAttributes = ungroupedAttributesData?.items || []
    const dataSource = [
        {
            id: 'ungrouped',
            name: 'Default group (non-grouped specification attributes)',
            displayOrder: 0,
            specificationAttributes: ungroupedAttributes,
        },
        ...data?.items,
    ]

    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const onModalSelectChange = (newSelectedModalRowKeys: React.Key[]) => {
        setSelectedModalRowKeys(newSelectedModalRowKeys)
    }

    const handleDelete = () => {
        confirm({
            title: 'Are you sure you want to delete the selected attributes?',
            content: 'This action cannot be undone.',
            onOk: async () => {
                deleteApi(selectedRowKeys as number[], {
                    onSuccess: () => {
                        refetchGrouped()
                        refetchUngrouped()
                        setSelectedRowKeys([])
                    },
                })
            },
            onCancel() {
                console.log('Cancel')
            },
        })
    }

    const handleModalDelete = () => {
        confirm({
            title: 'Are you sure you want to delete the selected attributes?',
            content: 'This action cannot be undone.',
            onOk: async () => {
                deleteApi(selectedModalRowKeys as number[], {
                    onSuccess: () => {
                        setIsModalVisible(false)
                        setSelectedModalRowKeys([])
                    },
                })
            },
            onCancel() {
                console.log('Cancel')
            },
        })
    }

    const showModal = (record) => {
        setModalData(record.specificationAttributes)
        setIsModalVisible(true)
    }

    const handleOk = () => {
        setIsModalVisible(false)
    }

    const handleCancel = () => {
        setIsModalVisible(false)
    }

    const rowSelection = {
        selectedRowKeys: selectedModalRowKeys,
        onChange: onModalSelectChange,
    }

    const handleReload = async () => {
        setIsSpinning(true)
        await new Promise((resolve) => setTimeout(resolve, 1000))
        refetchGrouped()
        refetchUngrouped()
        setIsSpinning(false)
    }

    const isModalDeleteButtonDisabled = selectedModalRowKeys.length === 0

    return {
        selectedRowKeys,
        dataSource,
        isLoading,
        error,
        handleEdit,
        handleDelete,
        handleReload,
        onSelectChange,
        isSpinning,
        onChange,
        current,
        ungroupedAttributesData,
        selectedModalRowKeys,
        onModalSelectChange,
        handleModalDelete,
        isModalDeleteButtonDisabled,
        showModal,
        handleOk,
        handleCancel,
        modalData,
        isModalVisible,
        rowSelection,
    }
}

export default useSpecificationAttributeGroupManageViewModel
