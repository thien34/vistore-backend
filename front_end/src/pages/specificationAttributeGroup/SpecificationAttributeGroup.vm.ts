import { useState } from 'react'
import { Modal, PaginationProps } from 'antd'
import useGetAllApi from '@/hooks/use-get-all-api.ts'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'

const { confirm } = Modal

const useSpecificationAttributeGroupManageViewModel = () => {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [isSpinning, setIsSpinning] = useState(false)
    const [current, setCurrent] = useState(1)
    const onChange: PaginationProps['onChange'] = (page) => {
        console.log(page)
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
        }
    )

    const handleEdit = (record: SpecificationAttributeResponse) => {
        console.log(`Edit attribute: ${record.name}`)
    }

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

    const handleReload = async () => {
        setIsSpinning(true)
        await new Promise((resolve) => setTimeout(resolve, 1000))
        refetchGrouped()
        refetchUngrouped()
        setIsSpinning(false)
    }

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
    }
}

export default useSpecificationAttributeGroupManageViewModel
